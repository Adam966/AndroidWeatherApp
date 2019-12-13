package com.project.weatherapp.entitiy;

public class MainWeather {
    private double temp;
    private int preassure;
    private int humidtiy;

    public MainWeather(double temp, int pressure, int humidity) {
        this.temp = temp;
        this.preassure = pressure;
        this.humidtiy = humidity;
    }

    public double getTemp() {
        return temp;
    }

    public int getPreassure() {
        return preassure;
    }

    public int getHumidtiy() {
        return humidtiy;
    }
}
