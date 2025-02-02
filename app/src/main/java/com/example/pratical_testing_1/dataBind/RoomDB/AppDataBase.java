package com.example.pratical_testing_1.dataBind.RoomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pratical_testing_1.dataBind.Model.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance;

    public abstract UserDao userDao();

    public static synchronized AppDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class, "user_database")
                    .fallbackToDestructiveMigration() // Handles migrations
                    .allowMainThreadQueries() // Use this only for testing, avoid for production
                    .build();
        }
        return instance;
    }
}
