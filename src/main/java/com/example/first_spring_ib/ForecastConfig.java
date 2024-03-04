package com.example.first_spring_ib;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ForecastConfig {
    @Bean
    public Map<String, WeatherForecastData> forecastMap() {
        return new HashMap<>();
    }
}
