package com.project.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.project.weatherapp.adapter.ChooseCityAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class chooseCityActivity extends AppCompatActivity implements ChooseCityAdapter.onItemClick {
    private SearchView searchView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);

        searchView = findViewById(R.id.searchCity);
        recyclerView = findViewById(R.id.listCity);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!(s.length() < 3))
                    queryCity(s);
                return false;
            }
        });
    }

    private void queryCity(String s) {
            MapboxGeocoding geocode = MapboxGeocoding.builder()
                    .accessToken("pk.eyJ1IjoiYWRhbTk5NiIsImEiOiJjazJkbGVkY2MwMDBlM21xa3g3MmVpajNlIn0.zb0nw1Vup1BbyKq2iOrHAA")
                    .geocodingTypes("place")
                    .query(s)
                    .build();

            geocode.enqueueCall(new Callback<GeocodingResponse>() {
                @Override
                public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                    setAdapter(response.body().features());
                }

                @Override
                public void onFailure(Call<GeocodingResponse> call, Throwable t) {

                }
            });
    }

    private void setAdapter(List<CarmenFeature> features) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ChooseCityAdapter chooseCityAdapter = new ChooseCityAdapter(features, this);
        recyclerView.setAdapter(chooseCityAdapter);
    }

    @Override
    public void ItemClicked(CarmenFeature feature) {
        Intent intent = new Intent(this, FavoriteCity.class);
        intent.putExtra("cityName", feature.placeName().substring(0, feature.placeName().indexOf(',')));
        startActivity(intent);
        finish();
    }
}
