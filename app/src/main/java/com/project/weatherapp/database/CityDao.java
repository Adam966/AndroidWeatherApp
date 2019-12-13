package com.project.weatherapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CityDao {
    @Query("SELECT * FROM City")
    List<City> getAll();

    @Insert
    void addCity(City city);

    @Delete
    void delete(City city);

    @Query("SELECT * FROM City WHERE EXISTS (SELECT * FROM City WHERE cityName LIKE :name)")
    boolean existCity(String name);

    @Query("SELECT * FROM City WHERE cityName LIKE :name")
    City findCity(String name);
}
