package com.softwares.techvibez.letsdate.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;


@Dao
public interface LMdao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContact(Contacts contacts);

    @Query("SELECT * FROM contacts_table")
    LiveData<List<Contacts>> getContacts();

    @Query("UPDATE contacts_table SET message = :lastMessage WHERE id = :Id")
    void updateMessage(String Id, String lastMessage);
}
