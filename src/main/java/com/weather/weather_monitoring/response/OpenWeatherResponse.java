package com.weather.weather_monitoring.response;

import java.util.List;

import lombok.Data;

@Data
public class OpenWeatherResponse {
    private Main main;
    private List<Weather> weather;
    
    @Data
    public static class Main {
        private double temp;
        private double feels_like;

    }
    
    @Data
    public static class Weather {
        private String main;
      
    }
}