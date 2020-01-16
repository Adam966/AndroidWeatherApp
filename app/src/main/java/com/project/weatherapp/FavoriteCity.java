package com.project.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.project.weatherapp.adapter.FavoriteCityAdapter;
import com.project.weatherapp.database.City;
import com.project.weatherapp.database.CityDatabase;
import com.project.weatherapp.other.SharedPref;

import java.util.ArrayList;

public class FavoriteCity extends AppCompatActivity implements FavoriteCityAdapter.OnClickListener {

    private EditText editText;
    private ImageView send;

    @Override
    protected void onResume() {
        Log.i("FAV ACTIVITY", "ON RESUME");

        setAdapter(getCityList());
        editText.setText(getIntent().getStringExtra("cityName"));
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("FAV ACTIVITY", "ON CREATE");

        SharedPref sharedPref = SharedPref.getSharedPrefInstance(this);
        if(sharedPref.loadPrefTheme() == 1) {
            setTheme(R.style.AppTheme);
        }
        else {
            setTheme(R.style.DarkTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_city);

        setAdapter(getCityList());

        editText = findViewById(R.id.editText);
        send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("EDIT TEXT" , editText.getText().toString());
                if (!(editText.getText().toString().equals("")) && !(checkCity())) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("cityName", editText.getText().toString());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        for (City city: getCityList()) {
            System.out.println(city.getCityName());
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("cityName", getCityList().get(position).cityName);
        intent.putExtra("fav", true);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        editText.setText(intent.getStringExtra("cityName"));
    }

    ////////////////////////////////////// SET ADAPTER /////////////////////////////////////////////
    private void setAdapter(ArrayList<City> list) {
        RecyclerView favCityAdapter = findViewById(R.id.favoriteCityList);
        favCityAdapter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        FavoriteCityAdapter adapter = new FavoriteCityAdapter(list, this, this);
        favCityAdapter.setAdapter(adapter);
    }

    /////////////////////////////////////// DATABASE ///////////////////////////////////////////////
    private ArrayList<City> getCityList() {
        CityDatabase db = CityDatabase.getInstance(this);
        return (ArrayList<City>) db.userDao().getAll();
    }

    private boolean checkCity() {
        CityDatabase db = CityDatabase.getInstance(this);
        Log.i("EXIST CITY", String.valueOf(db.userDao().existCity(editText.getText().toString())));
        return db.userDao().existCity(editText.getText().toString());
    }

    public void chooseCity(View view) {
        startActivity(new Intent(this, chooseCityActivity.class));
    }
}
