package com.softwares.techvibez.letsdate.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;


import java.util.List;

public class LMRepository {

    private LMdao lMdao;
    private LiveData<List<Contacts>> contacts;

    public LMRepository(Application application) {
        ChatsDatabase db = ChatsDatabase.getDatabase(application);
        lMdao = db.lMdao();
    }

    public void updateMessage(String id, String msg){
        lMdao.updateMessage(id, msg);
    }

    public LiveData<List<Contacts>> getContacts(){
        contacts = lMdao.getContacts();
        return contacts;
    }

    public void insertContact(Contacts contacts){
        new insertContact(lMdao).execute(contacts);
    }

    private static class insertContact extends AsyncTask<Contacts, Void, Void> {

        private LMdao Asyncdao;

        public insertContact(LMdao asyncdao) {
            Asyncdao = asyncdao;
        }

        @Override
        protected Void doInBackground(Contacts... contacts) {
            Asyncdao.insertContact(contacts[0]);
            return null;
        }
    }
}
