package com.project.weatherapp.entitiy;

public class WeatherDescription {
    private String descritption;
    private String icon;

    public String getDescritption() {
        return descritption;
    }

    public String getIcon() {
        return icon;
    }

    public WeatherDescription(String description, String icon) {
        this.descritption = description;
        this.icon = icon;
    }
}
