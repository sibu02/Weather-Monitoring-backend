package com.weather.weather_monitoring.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.weather.weather_monitoring.model.WeatherData;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
	@Query("SELECT w FROM WeatherData w WHERE w.city = :city")
    Page<WeatherData> findByCity(@Param("city")String city,Pageable pageable);
	
	@Query("SELECT w FROM WeatherData w WHERE w.city = :city")
	List<WeatherData> findAllByCity(@Param("city") String city);
	
	@Query(value = "SELECT * FROM weather_data u WHERE u.city = :city AND DATE(u.timestamp) = :date", nativeQuery = true)
	List<WeatherData> findByCityAndDate(@Param("city") String city, @Param("date") LocalDate date);

}