package com.softwares.techvibez.letsdate.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.softwares.techvibez.letsdate.chat.ChatDAO;
import com.softwares.techvibez.letsdate.chat.Chats;

import java.util.List;

public class ChatRepository {

    private ChatDAO chatDAO;
    private LiveData<List<Chats>> chat;


    public ChatRepository(Application application) {
        ChatsDatabase db = ChatsDatabase.getDatabase(application);
        chatDAO = db.chatDAO();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Chats>> getChat(@NonNull String id) {
        chat = chatDAO.getChat(id);
        return chat;
    }

    public LiveData<List<Chats>> getChats() {
        chat = chatDAO.getChats();
        return chat;
    }


    public void insert(Chats chat) {
        new insertAsyncTask(chatDAO).execute(chat);
    }

    private static class insertAsyncTask extends AsyncTask<Chats, Void, Void> {

        private ChatDAO mAsyncTaskDao;

        insertAsyncTask(ChatDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Chats... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
