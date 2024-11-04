package com.weather.weather_monitoring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.weather.weather_monitoring.model.WeatherData;

@Service
public class AlertService {

    private int consecutiveBreaches = 0;
    private List<String> alerts = new ArrayList<>();

    public void checkThresholds(WeatherData data) {
        double thresholdTemp = 35.0;

        if (data.getTemp() > thresholdTemp) {
            consecutiveBreaches++;
            if (consecutiveBreaches >= 2) {
                String alertMessage = "ALERT: Temperature in " + data.getCity() + " exceeded " + thresholdTemp + "°C. Current Temp: " + data.getTemp();
                triggerAlert(alertMessage);
            }
        } else {
            consecutiveBreaches = 0;
        }
    }

    private void triggerAlert(String alertMessage) {
        alerts.add(alertMessage);
        System.out.println(alertMessage); // For server logs
    }

    public List<String> getAlerts() {
        return new ArrayList<>(alerts); // Return a copy to avoid external modification
    }
}
