package com.example.forecast_application.scheduled;

import com.example.forecast_application.model.WeatherResponse;
import com.example.forecast_application.service.ForecastService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ForecastScheduled {
    private static final Logger logger = LoggerFactory.getLogger(ForecastScheduled.class);

    private final ForecastService forecastService;

    @Scheduled(fixedRate = 36000000)
    private void getForecastForDay() {
        logger.debug("Fetching forecast data ");
        try {
            WeatherResponse weatherResponse = forecastService.fetchForecastData();
            forecastService.updateForecastMap(weatherResponse.getForecast());
        } catch (IOException | InterruptedException e) {
            logger.error("Error while fetching data: {}", e.getMessage());
        }

    }
}
