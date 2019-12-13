package com.project.weatherapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {City.class}, version = 1 )
public abstract class CityDatabase extends RoomDatabase {
    private static CityDatabase instance;

    public static CityDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, CityDatabase.class, "favorite_city")
                    .allowMainThreadQueries()
                    .build();

            return instance;
        }
        return instance;
    }

    public abstract CityDao userDao();
}