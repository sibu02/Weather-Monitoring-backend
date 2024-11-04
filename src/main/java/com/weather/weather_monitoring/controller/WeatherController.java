package com.weather.weather_monitoring.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weather.weather_monitoring.model.DailyWeatherSummary;
import com.weather.weather_monitoring.model.WeatherData;
import com.weather.weather_monitoring.service.DailyWeatherSummarySevice;
import com.weather.weather_monitoring.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
	
	@Autowired
	private WeatherService weatherService;
	
	@Autowired
	private DailyWeatherSummarySevice dailyWeatherSummaryService;
	
    @GetMapping("/{city}")
    public WeatherData getWeatherByCity(@PathVariable String city) {
        return weatherService.getWeatherDataByCity(city);
    }

    @GetMapping()
    public List<WeatherData> getAllWeatherData() {
        return weatherService.getAllWeatherData();
    }
    
    @GetMapping("/dailySummary/{city}")
    public DailyWeatherSummary getDailysummary(@PathVariable String city) {
    	return dailyWeatherSummaryService.getDailySummaryByCity(city);
    }
    
    @GetMapping("/weekly/{city}")
    public List<DailyWeatherSummary> getWeeklySummary(@PathVariable String city) {
    	return dailyWeatherSummaryService.getLast7DaysSummary(city);
    }
    
    @GetMapping("/todays/{city}")
    public List<WeatherData> getByCityAndDate(@PathVariable String city) {
    	return weatherService.findByCityAndDate(city, LocalDate.now());
    }
    
    @GetMapping("/alerts")
    public List<String> getAlerts() {
        return weatherService.getAlerts();
    }
}
