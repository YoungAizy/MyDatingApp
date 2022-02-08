package com.softwares.techvibez.letsdate.chat;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "chats_table")
public class Chats {

    public int getID() {
        return ID;
    }

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "id")
    private int ID;

    @ColumnInfo (name = "sender")
    @NonNull
    private String senderId;

    @ColumnInfo (name = "message")
    @NonNull
    private String text;

    @ColumnInfo (name = "chatID")
    @NonNull
    private String chatId;


    public Chats(int ID, @NonNull String senderId, @NonNull String text, @NonNull String chatId) {
        this.ID = ID;
        this.senderId = senderId;
        this.text = text;
        this.chatId = chatId;
    }

    @Ignore
    public Chats(@NonNull String text, @NonNull String chatId) {
        this.text = text;
        this.chatId = chatId;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setSenderId(@NonNull String senderId) {
        this.senderId = senderId;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }

    public void setChatId(@NonNull String chatId) {
        this.chatId = chatId;
    }

    @NonNull
    public String getText() {
        return text;
    }

    @NonNull
    public String getChatId() {
        return chatId;
    }

    @NonNull
    public String getSenderId() {
        return senderId;
    }
}
