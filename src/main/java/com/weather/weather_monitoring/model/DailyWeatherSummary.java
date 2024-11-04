package com.weather.weather_monitoring.model;

import javax.persistence.*;

import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class DailyWeatherSummary {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String city;
    private LocalDate date;
    private double avgTemperature;
    private double maxTemperature;
    private double minTemperature;
    private String dominantCondition;

}
