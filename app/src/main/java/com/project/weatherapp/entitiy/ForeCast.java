package com.project.weatherapp.entitiy;

public class ForeCast {
    private MainWeather mainWeather;
    private WeatherDescription weatherDscription;
    private int clouds;
    private WindDescription windDescription;
    private double rainVolume;
    private double snowVolume;
    private String date;

    public ForeCast(MainWeather mainWeather, WeatherDescription weatherDescription, WindDescription windDescription, int clouds, double rainVolume, double snowVolume, String date) {
        this.mainWeather = mainWeather;
        this.weatherDscription = weatherDescription;
        this.windDescription = windDescription;
        this.clouds = clouds;
        this.rainVolume = rainVolume;
        this.snowVolume = snowVolume;
        this.date = date;
    }

    public MainWeather getMainWeather() {
        return mainWeather;
    }

    public WeatherDescription getWeatherDscription() {
        return weatherDscription;
    }

    public int getClouds() {
        return clouds;
    }

    public WindDescription getWindDescription() {
        return windDescription;
    }

    public double getRainVolume() {
        return rainVolume;
    }

    public double getSnowVolume() {
        return snowVolume;
    }

    public String getDate() {
        return date;
    }
}
