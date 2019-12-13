package com.project.weatherapp.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class City {
    @PrimaryKey
    @NonNull
    public String uid;

    @ColumnInfo
    public String cityName;

    public City(String cityName) {
        this.uid = UUID.randomUUID().toString();
        this.cityName = cityName;
    }

    @Ignore
    public City() { }

    @NonNull
    public String getUid() {
        return uid;
    }

    public void setUid(@NonNull String uid) {
        this.uid = uid;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }
}
