package com.softwares.techvibez.letsdate.database.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.softwares.techvibez.letsdate.database.Contacts;
import com.softwares.techvibez.letsdate.database.LMRepository;

import java.util.List;

public class LMViewModel extends AndroidViewModel {

    private LMRepository repository;
    private LiveData<List<Contacts>> mContacts;

    public LMViewModel(@NonNull Application application) {
        super(application);
        repository = new LMRepository(application);
    }

    public void updateMsg(String id, String msg){
        repository.updateMessage(id, msg);
    }

    public LiveData<List<Contacts>> getContacts(){
        mContacts = repository.getContacts();
        return mContacts;
    }

    public void  insertContacts(Contacts contacts){
        repository.insertContact(contacts);
    }
}
