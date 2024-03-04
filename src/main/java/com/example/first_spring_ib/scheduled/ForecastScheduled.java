package com.example.first_spring_ib.scheduled;

import com.example.first_spring_ib.service.ForecastService;
import com.example.first_spring_ib.model.WeatherResponse;
import com.example.first_spring_ib.controller.ForecastController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ForecastScheduled {
    private static final Logger logger = LoggerFactory.getLogger(ForecastController.class);

    @Autowired
    private ForecastService forecastService;

    @Scheduled(fixedRate = 36000000)
    private void getForecastForDay() {
        logger.debug("Fetching forecast data ");
        try {
            WeatherResponse weatherResponse = forecastService.fetchForecastData();
            forecastService.updateForecastMap(weatherResponse);
        } catch (IOException | InterruptedException e) {
            logger.error("Error while fetching data: " + e.getMessage());
        }
    }


}
