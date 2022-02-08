package com.softwares.techvibez.letsdate.chat;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatDAO {

    @Query("SELECT * FROM chats_table where :Id = chatID")
    LiveData<List<Chats>> getChat(@NonNull String Id);

    @Query("SELECT * FROM chats_table")
    LiveData<List<Chats>> getChats();

    @Insert
    void insert(Chats chat);
}
