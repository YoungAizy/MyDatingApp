package com.softwares.techvibez.letsdate.database.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.softwares.techvibez.letsdate.chat.Chats;
import com.softwares.techvibez.letsdate.database.ChatRepository;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {

    private ChatRepository repositiory;

    private LiveData<List<Chats>> chat;


    public ChatViewModel(Application application) {
        super(application);
        repositiory = new ChatRepository(application);
    }

    public LiveData<List<Chats>> getChat(@NonNull String s) {
        chat = repositiory.getChat(s);
        return chat;
    }

    public LiveData<List<Chats>> getChats() {
        chat = repositiory.getChats();
        return chat;
    }

    public void insert(Chats word) {
        repositiory.insert(word);
    }

}
