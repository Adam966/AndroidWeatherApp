package com.project.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.weatherapp.adapter.DayForeCastAdapter;
import com.project.weatherapp.adapter.HourForeCastAdapter;
import com.project.weatherapp.database.City;
import com.project.weatherapp.database.CityDatabase;
import com.project.weatherapp.entitiy.ForeCast;
import com.project.weatherapp.other.EditResponse;
import com.project.weatherapp.other.SharedPref;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DayForeCastAdapter.OnDayItemClick {

    private SharedPref sharedPref;
    private static City city = new City();
    private CityDatabase db;
    private ImageView fav;
    private final ArrayList<ForeCast> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = SharedPref.getSharedPrefInstance(this);
        if (sharedPref.loadPrefTheme() == 1)
            setTheme(R.style.AppTheme);
        else
            setTheme(R.style.DarkTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("APP LANGUAGE", Locale.getDefault().getDisplayLanguage());

        setLanguage(Locale.getDefault().getDisplayLanguage());
        db = CityDatabase.getInstance(this);
        fav = findViewById(R.id.mainFavortiteCityIcon);

        Log.i("MAIN ACTIVITY", "ON CREATE");
        if (sharedPref.loadPrefCity() == null)
            startActivity(new Intent(this, FavoriteCity.class));
        else if(getIntent().getStringExtra("cityName") != null)
            weatherService(getIntent().getStringExtra("cityName"), sharedPref.loadPrefUnits() == 1? "imperial" : "metric", sharedPref.loadPrefLang());
        else
            weatherService(sharedPref.loadPrefCity(), sharedPref.loadPrefUnits() == 1? "imperial" : "metric", sharedPref.loadPrefLang());
    }

    private void setLanguage(String language) {
        Log.i("LANGUAGE", language);
        Log.i("LANGUAGE", sharedPref.loadPrefLang());
        switch (language) {
            case "españa":
                sharedPref.savePrefLang("es");
            case "français":
                sharedPref.savePrefLang("fr");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sharedPref.savePrefCity(city.cityName);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Log.i("MAIN ACTIVITY", "ON NEW INTENT");
        weatherService(intent.getStringExtra("cityName"), sharedPref.loadPrefUnits() == 1 ? "imperial" : "metric", sharedPref.loadPrefLang());
    }

    /////////////////////////////////////////// SET MENU ///////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.drawer_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_settings:
                startActivity(new Intent(this, Settings.class));
                break;
            case R.id.nav_favorite_city:
                startActivity(new Intent(this, FavoriteCity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //////////////////////////////////////////// SET ADAPTERS ////////////////////////////////////////////////////////////////////////
    private void setHourForeCast(ArrayList<ForeCast> list) {
        RecyclerView hourForeCastView = findViewById(R.id.hourForeCast);
        hourForeCastView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        HourForeCastAdapter adapter = new HourForeCastAdapter(list, this);
        hourForeCastView.setAdapter(adapter);
    }

    private void setDayForeCast(ArrayList<ForeCast> list) {
        RecyclerView dayForeCastView = findViewById(R.id.dayForeCast);
        dayForeCastView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        DayForeCastAdapter adapter = new DayForeCastAdapter(list, this, this);
        dayForeCastView.setAdapter(adapter);
    }

    @Override
    public void onDayItemClick(ForeCast foreCast) {
        Intent intent = new Intent(this, DayInfo.class);
        intent.putExtra("humidity", foreCast.getMainWeather().getHumidtiy());
        intent.putExtra("pressure", foreCast.getMainWeather().getPreassure());
        intent.putExtra("clouds", foreCast.getClouds());
        intent.putExtra("wind_degree", foreCast.getWindDescription().getWindDegree());
        intent.putExtra("wind_speed", foreCast.getWindDescription().getWindSpeed());
        intent.putStringArrayListExtra("graph_data", createArrayForGraph(foreCast));

        startActivity(intent);
    }

    private ArrayList<String> createArrayForGraph(ForeCast foreCast) {
        ArrayList<String> values = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDate().substring(0, 10).equals(foreCast.getDate().substring(0, 10))) {
                values.add(String.valueOf(list.get(i).getMainWeather().getTemp()));
            }
        }
        return values;
    }

    private void setMainCard(ForeCast foreCast, String city) {
        TextView temp = findViewById(R.id.mainCardTemp);
        TextView desc = findViewById(R.id.mainCardDesc);
        TextView cityName = findViewById(R.id.mainCardCityName);
        ImageView icon = findViewById(R.id.mainCardIcon);

        temp.setText(String.valueOf(foreCast.getMainWeather().getTemp()) + (sharedPref.loadPrefUnits() == 2 ? "°C" : "°F"));
        desc.setText(foreCast.getWeatherDscription().getDescritption());
        cityName.setText(city);
        Picasso.get().load("http://openweathermap.org/img/wn/" + foreCast.getWeatherDscription().getIcon() + ".png").into(icon);
    }

    public void clickFavCity(View view) {
        String text = city.cityName;
        Log.i("FAV CITY ", String.valueOf(db.userDao().existCity(text)));
        if(!(db.userDao().existCity(text))) {
            fav.setImageResource(R.drawable.ic_favorite_black_24dp);
            db.userDao().addCity(new City(text));
        }
        else {
            fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            db.userDao().delete(db.userDao().findCity(text));
        }
    }
    ////////////////////////////////////////////// SERVICE REQUEST ///////////////////////////////////////////////////////////////////
    private void weatherService(final String cityName, String units, String language) {
        city.setCityName(cityName);

        String url = String.format("http://api.openweathermap.org/data/2.5/forecast?q=%s&units=%s&lang=%s&APPID=%s", cityName, units, language, getResources().getString(R.string.API_KEY));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    EditResponse edit = new EditResponse(response.getJSONArray("list"));
                    list.addAll(edit.getList());

                    setMainCard(list.get(0), cityName);
                    setHourForeCast(list);
                    setDayForeCast(edit.getDates(list));

                    if (db.userDao().existCity(cityName))
                        fav.setImageResource(R.drawable.ic_favorite_black_24dp);
                    else
                        fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    handleError(error.networkResponse.statusCode);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        requestQueue.add(jsonRequest);
        requestQueue.start();
    }

    private void handleError(int responseCode) {
        switch (responseCode) {
            case 404:
                Toast.makeText(this, "Wrong input", Toast.LENGTH_SHORT).show();
        }
    }
}
