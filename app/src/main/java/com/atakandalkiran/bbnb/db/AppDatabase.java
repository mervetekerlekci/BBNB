package com.atakandalkiran.bbnb.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, CardDetailsModel.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userdao();
    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance (Context context){
        if(INSTANCE  == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class, "USER_INFO_DB")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

}
