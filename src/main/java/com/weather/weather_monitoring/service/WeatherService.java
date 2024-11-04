package com.weather.weather_monitoring.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.weather.weather_monitoring.model.DailyWeatherSummary;
import com.weather.weather_monitoring.model.WeatherData;
import com.weather.weather_monitoring.repository.WeatherDataRepository;
import com.weather.weather_monitoring.response.OpenWeatherResponse;

@Service
public class WeatherService {
	
	@Value("${WEATHER_API_KEY}")
	private String API_KEY;
	
	private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q={city}&appid={apikey}";
	
	@Autowired
	private WeatherDataRepository repository;
	
	@Autowired
	private AlertService alertService;

	@Autowired
	private RestTemplate restTemplate;

	public void fetchWeatherData(String city) {
		String url = API_URL.replace("{city}", city).replace("{apikey}", API_KEY);
		try {
			OpenWeatherResponse response = restTemplate.getForObject(url, OpenWeatherResponse.class);
			if (response != null) {
				WeatherData weatherData = new WeatherData();
				weatherData.setCity(city);
				weatherData.setMainCondition(response.getWeather().get(0).getMain());
				double temp = (Math.round((response.getMain().getTemp() - 273.15)*100.00))/100.00;
				double feels_like = (Math.round((response.getMain().getFeels_like() - 273.15)*100.0))/100.0;
				weatherData.setTemp(temp);
				weatherData.setFeelsLike(feels_like);
				weatherData.setTimestamp(LocalDateTime.now());
				WeatherData data = repository.save(weatherData);
				alertService.checkThresholds(data);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public List<WeatherData> getAllWeatherData() {
		return repository.findAll();
	}

	public WeatherData getWeatherDataByCity(String city) {
		PageRequest pageable = PageRequest.of(1, 1, Sort.by("timestamp").descending());
		Page<WeatherData> page = repository.findByCity(city,pageable);
		return page.getContent().get(0);
	}

	public List<WeatherData> getAllWeatherDataByCity(String city) {
		return repository.findAllByCity(city);
	}

	public List<String> getAlerts() {
		return alertService.getAlerts();
	}

	public List<WeatherData> findByCityAndDate(String city, LocalDate today) {
		System.out.println(today);
		return repository.findByCityAndDate(city, today);
	}
	
	
}
