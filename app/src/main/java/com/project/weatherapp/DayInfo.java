package com.project.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.project.weatherapp.other.SharedPref;

import java.util.ArrayList;
import java.util.List;

public class DayInfo extends AppCompatActivity {

    private TextView humidity;
    private TextView pressure;
    private TextView windSpeed;
    private TextView windWay;
    private TextView clouds;
    private LineChart graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedPref = SharedPref.getSharedPrefInstance(this);
        if(sharedPref.loadPrefTheme() == 1) {
            setTheme(R.style.AppTheme);
        }
        else {
            setTheme(R.style.DarkTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_info);

        set();
        setValues();
    }

    private void setValues() {
        Intent intent = getIntent();
        humidity.setText(String.valueOf(intent.getIntExtra("humidity", 0)) + " %");
        pressure.setText(String.valueOf(intent.getIntExtra("pressure", 0)) + " HPPA");
        windSpeed.setText(intent.getDoubleExtra("wind_speed", 0) + " km/h");
        clouds.setText(String.valueOf(intent.getIntExtra("clouds", 0)) + " %");
        windWay.setText(setWindWay(intent.getIntExtra("wind_degree", 0)));

        setGraph(intent.getStringArrayListExtra("graph_data"));
    }

    private void setGraph(ArrayList<String> list) {
        List<Entry> barEntries = new ArrayList<>();

        float i = 0;

        for (String s : list) {
            barEntries.add(new Entry(i, Float.valueOf(s)));
            i+=3;
            Log.i("DATA", String.valueOf(i));
        }

        LineDataSet lineDataSet = new LineDataSet(barEntries, "Temperature");
        //lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        LineData lineData = new LineData(lineDataSet);
        graph.setData(lineData);

    }

    private void set() {
        humidity = findViewById(R.id.infoHum);
        pressure = findViewById(R.id.infoPress);
        windSpeed = findViewById(R.id.infoSpeed);
        windWay = findViewById(R.id.infoWay);
        clouds = findViewById(R.id.infoClouds);
        graph = findViewById(R.id.graph);
    }

    private String setWindWay(int degree) {
        if (degree < 11 || degree > 348)
            return "N";
        else if (degree < 33 && degree > 11)
            return "NNE";
        else if(degree < 56 && degree > 33)
            return "NE";
        else if (degree < 78 || degree > 56)
            return "ENE";
        else if (degree < 101 || degree > 78)
            return "E";
        else if (degree < 123 || degree > 101)
            return "ESE";
        else if (degree < 146 || degree > 123)
            return "SE";
        else if(degree < 168 || degree > 146)
            return "SSE";
        else if(degree < 191 || degree > 168)
            return "S";
        else if (degree < 213 || degree > 191)
            return "SSW";
        else if (degree < 236 || degree > 213)
            return "SW";
        else if (degree < 258 || degree > 236)
            return "WSW";
        else if (degree < 281 || degree > 258)
            return "W";
        else if (degree < 303 || degree > 281)
            return "WNW";
        else if (degree < 326 || degree > 303)
            return "NW";
        else if (degree < 348 || degree > 326)
            return "NNW";

        return null;
    }
}
