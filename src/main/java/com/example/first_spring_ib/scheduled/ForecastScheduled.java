package com.example.first_spring_ib.scheduled;

import com.example.first_spring_ib.model.MyException;
import com.example.first_spring_ib.service.ForecastService;
import com.example.first_spring_ib.model.WeatherResponse;
import com.example.first_spring_ib.controller.ForecastController;
import io.swagger.client.ApiException;
import io.swagger.client.api.ApisApi;
import io.swagger.client.model.Forecast;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

        }catch (MyException e) {
            logger.error("Error in MyException", e);

        } catch (ApiException e) {
            logger.error(String.valueOf(e.getCode()));
            logger.error(e.getResponseBody());
            logger.error(e.getResponseHeaders().toString());
        } catch (Exception e) {
            logger.error("Error while fetching data: ", e);

        }
    }


}
