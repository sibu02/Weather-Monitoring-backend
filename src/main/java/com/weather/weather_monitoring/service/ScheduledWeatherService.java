package com.weather.weather_monitoring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledWeatherService {

    @Autowired
    private WeatherService weatherService;

    @Scheduled(fixedRate = 300000)
    public void fetchWeatherForCities() {
        weatherService.fetchWeatherData("Delhi");
        weatherService.fetchWeatherData("Mumbai");
        weatherService.fetchWeatherData("Chennai");
        weatherService.fetchWeatherData("Bengaluru");
        weatherService.fetchWeatherData("Kolkata");
        weatherService.fetchWeatherData("Hyderabad");
    }
}
