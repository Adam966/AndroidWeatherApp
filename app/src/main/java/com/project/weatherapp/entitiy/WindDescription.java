package com.project.weatherapp.entitiy;

public class WindDescription {
    private double windSpeed;
    private int windDegree;

    public WindDescription(double windSpeed, int windDegree) {
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getWindDegree() {
        return windDegree;
    }
}
