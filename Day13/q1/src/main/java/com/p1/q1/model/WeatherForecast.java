package com.p1.q1.model;

import java.time.LocalDate;

public class WeatherForecast {
    private LocalDate date;
    private double temperature;
    private double humidity;
    private double windSpeed;

    // Constructors
    public WeatherForecast() {}
    public WeatherForecast(LocalDate date, double temperature, double humidity, double windSpeed) {
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
}