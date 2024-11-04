package com.weather.weather_monitoring.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.weather.weather_monitoring.model.DailyWeatherSummary;
import com.weather.weather_monitoring.model.WeatherData;
import com.weather.weather_monitoring.repository.DailyWeatherSummaryRepository;

@Service
public class DailyWeatherSummarySevice {
	
	@Autowired
	private DailyWeatherSummaryRepository dailyWeatherSummaryRepository;
	
	@Autowired
	private WeatherService weatherService;
	
	@Scheduled(cron = "0 59 23 * * ?")
	public void calculateDailySummary() {
		String[] cities = {"Delhi", "Mumbai", "Chennai", "Bengaluru", "Kolkata", "Hyderabad"};
        LocalDate today = LocalDate.now();
        
        for(String city : cities) {
        	List<WeatherData> dailyData = weatherService.findByCityAndDate(city, today);
        	if(!dailyData.isEmpty()) {
        		double avgTemp = dailyData.stream().mapToDouble(WeatherData::getTemp).average().orElse(0.0);
        		double maxTemp = dailyData.stream().mapToDouble(WeatherData::getTemp).max().orElse(0.0);
        		double minTemp = dailyData.stream().mapToDouble(WeatherData::getTemp).min().orElse(0.0);
        		String dominantCondition = findDominantCondition(dailyData);
        		
        		 DailyWeatherSummary summary = new DailyWeatherSummary();
                 summary.setCity(city);
                 summary.setDate(today);
                 summary.setAvgTemperature(avgTemp);
                 summary.setMaxTemperature(maxTemp);
                 summary.setMinTemperature(minTemp);
                 summary.setDominantCondition(dominantCondition);
                 dailyWeatherSummaryRepository.save(summary);
        	}
        }
	}

	private String findDominantCondition(List<WeatherData> dailyData) {
		 return dailyData.stream()
                 .collect(Collectors.groupingBy(WeatherData::getMainCondition, Collectors.counting()))
                 .entrySet()
                 .stream()
                 .max(Comparator.comparing(Map.Entry::getValue))
                 .map(Map.Entry::getKey)
                 .orElse("Clear");
	}
	
	public DailyWeatherSummary getDailySummaryByCity(String city) {
		return dailyWeatherSummaryRepository.getByCityAndDate(city,LocalDate.now());
	}
	
	public List<DailyWeatherSummary> getLast7DaysSummary(String city){
		List<DailyWeatherSummary> data = dailyWeatherSummaryRepository.findLatestSummaries(city);
		return data.stream().limit(7).collect(Collectors.toList());
	}
	
}
