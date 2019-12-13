package com.project.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.project.weatherapp.other.SharedPref;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private SharedPref sharedPref;

    private Switch units;
    private Switch themeMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("SETTINGS ACTIVITY", "ON CREATE");

        sharedPref = SharedPref.getSharedPrefInstance(this);
        if(sharedPref.loadPrefTheme() == 1) {
            setTheme(R.style.AppTheme);
        }
        else {
            setTheme(R.style.DarkTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        units = findViewById(R.id.unitsSwitch);
        themeMode = findViewById(R.id.darkModeSwitch);

        //setSpinner();
        setSwitch();

        units.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    sharedPref.savePrefUnits(2);
                } else {
                    sharedPref.savePrefUnits(1);
                }
            }
        });

        themeMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sharedPref.savePrefTheme(2);
                    restartApp();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sharedPref.savePrefTheme(1);
                    restartApp();
                }
            }
        });
    }

    //////////////////////////////////// SPINNER SELECTION /////////////////////////////////////////
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        sharedPref.savePrefLang(getAbbreviation(text));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private String getAbbreviation(String text) {
        switch (text) {
            case "Arabic":
                return "ar";
            case "Bulgarian":
                return "bg";
            case "Czech":
                return "cz";
            case "German":
                return "de";
            case "Slovak":
                return "sk";
            case "Spanish":
                return "es";
        }
        return null;
    }

    private void setSpinner() {
        Spinner language = findViewById(R.id.spinnerLanguage);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        language.setAdapter(adapter);
        language.setOnItemSelectedListener(this);
    }
    ////////////////////////////////////// SET VALUES //////////////////////////////////////////////
    private void setSwitch() {
        if (sharedPref.loadPrefUnits() == 2)
            units.setChecked(true);
        else
            units.setChecked(false);

        if (sharedPref.loadPrefTheme() == 2)
            themeMode.setChecked(true);
        else
            themeMode.setChecked(false);
    }


    private void restartApp() {
        Intent intent = new Intent(getApplicationContext(), Settings.class);
        startActivity(intent);
        finish();
    }
}
