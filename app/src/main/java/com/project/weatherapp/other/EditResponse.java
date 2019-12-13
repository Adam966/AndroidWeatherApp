package com.project.weatherapp.other;

import com.project.weatherapp.entitiy.ForeCast;
import com.project.weatherapp.entitiy.ForeCastList;
import com.project.weatherapp.entitiy.MainWeather;
import com.project.weatherapp.entitiy.WeatherDescription;
import com.project.weatherapp.entitiy.WindDescription;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class EditResponse {
    JSONArray json;

    public EditResponse(JSONArray json) {
        this.json = json;
    }

    public ArrayList<ForeCast> getList() {
        ArrayList<ForeCast> list = new ArrayList<>();

        try {
            for (int i = 0; i < json.length(); i++) {
                json.getJSONObject(i).getString("dt_txt");
                JSONObject joM = json.getJSONObject(i).getJSONObject("main");
                MainWeather main = new MainWeather(joM.getDouble("temp"), joM.getInt("pressure"), joM.getInt("humidity"));

                JSONArray joDA = json.getJSONObject(i).getJSONArray("weather");
                JSONObject joD = joDA.getJSONObject(0);
                WeatherDescription desc = new WeatherDescription(joD.getString("description"), joD.getString("icon"));

                JSONObject joW = json.getJSONObject(i).getJSONObject("wind");
                WindDescription Wdesc = new WindDescription(joW.getDouble("speed"), joW.getInt("deg"));

                int clouds = json.getJSONObject(i).getJSONObject("clouds").getInt("all");
                double rain = 0;
                double snow = 0;

                try {
                    rain = json.getJSONObject(i).getJSONObject("rain").getDouble("3h");
                    snow = json.getJSONObject(i).getJSONObject("snow").getDouble("3h");
                } catch (Exception e) {}

                String date = json.getJSONObject(i).getString("dt_txt");

                ForeCast foreCast = new ForeCast(main, desc, Wdesc, clouds, rain, snow, date);

                list.add(foreCast);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<ForeCast> getDates(ArrayList<ForeCast> list) {
        Pattern p = Pattern.compile("\\d{4}\\W\\d{2}\\W\\d{2}\\s12:00:00");
        ArrayList<ForeCast> foreCastElements = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (p.matcher(list.get(i).getDate()).find()) {
                foreCastElements.add(list.get(i));
            }
        }

        return foreCastElements;
    }

    public ArrayList<ForeCast> editList(ArrayList<ForeCast> list) {
        ArrayList<ForeCast> editedList = new ArrayList<>();

        for (ForeCast f : list) {


            ForeCastList l = new ForeCastList();
            for (ForeCast foreCast : list) {
                if (foreCast.getDate().substring(0, 10) == f.getDate().substring(0, 10))
                    l.getList().add(foreCast);
            }

            return l.getList();
        }
        return null;
    }

}
