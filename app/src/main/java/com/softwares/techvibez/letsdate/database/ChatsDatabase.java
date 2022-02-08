package com.softwares.techvibez.letsdate.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.softwares.techvibez.letsdate.R;
import com.softwares.techvibez.letsdate.chat.ChatDAO;
import com.softwares.techvibez.letsdate.chat.Chats;

@Database(entities = {Chats.class, Contacts.class}, version = 2)
public abstract class ChatsDatabase extends RoomDatabase {

    public abstract ChatDAO chatDAO();
    public abstract LMdao lMdao();


    private static volatile ChatsDatabase INSTANCE;

    static ChatsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ChatsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ChatsDatabase.class, "word_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            //new PopulateDb(INSTANCE).execute();
        }
    };

    private static class PopulateDb extends AsyncTask<Void, Void, Void> {

        private final LMdao dao;

        public PopulateDb(ChatsDatabase db) {
            this.dao = db.lMdao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Contacts contact1 = new Contacts("Sam", "Hey, There", "idXF002c1", R.drawable.ic_person);
            //dao.insertContact(contact1);
            //Contacts contact2 = new Contacts("Chloe Bakers", "Yes, you can have a taste anytime", "idXF300zI", R.drawable.ic_person);
            //dao.insertContact(contact2);

            return null;
        }
    }


}
