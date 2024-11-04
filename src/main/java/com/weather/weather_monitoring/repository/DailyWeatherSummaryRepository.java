package com.weather.weather_monitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.weather.weather_monitoring.model.DailyWeatherSummary;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyWeatherSummaryRepository extends JpaRepository<DailyWeatherSummary, Long> {
    List<DailyWeatherSummary> findByCityAndDate(String city, LocalDate date);
    
    @Query("SELECT u FROM DailyWeatherSummary u WHERE u.city = :city AND u.date = :date")
	DailyWeatherSummary getByCityAndDate(@Param("city") String city,@Param("date") LocalDate date);
    
    @Query("SELECT d FROM DailyWeatherSummary d WHERE d.city = :city ORDER BY d.date DESC")
    List<DailyWeatherSummary> findLatestSummaries(@Param("city") String city); 
}
