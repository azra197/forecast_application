package com.example.first_spring_ib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class ForecastController {

    private static final Logger logger = LoggerFactory.getLogger(ForecastController.class);

    private final Map<String, WeatherForecastData> forecastMap = new HashMap<>();

    @GetMapping("/forecast")
    public List<String> threeDaysForecast() {
        logger.info("GET /forecast");
        return forecastMap.entrySet().stream().map(this::getForecastFor3Days)
                .collect(Collectors.toList());
    }


    @GetMapping("/weather/{day}")
    public String dayForecast(@PathVariable String day) {
        logger.info("GET /weather/{}", day);
        WeatherForecastData weatherForecastData = forecastMap.get(day);
        if (weatherForecastData != null) {
            return getForecastForOneDay(day,weatherForecastData);
        } else {
            return "There is no provided forecast for" + day;
        }
    }

    @Scheduled(fixedRate = 36000000)
    private void getForecastForDay() {
        logger.debug("Fetching forecast data ");
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://api.weatherapi.com/v1/forecast.json?key=727bb15bfd014c1487795606242102&q=Sarajevo&days=3&aqi=no&alerts=no"))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("API response : {}", response.body());
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherResponse weatherresponse = objectMapper.readValue(response.body(), WeatherResponse.class);

            updateForecastMap(weatherresponse);
        } catch (IOException | InterruptedException e) {
            logger.error("Error while fetching data: " + e.getMessage());
        }
    }

    private String getForecastFor3Days(Map.Entry<String, WeatherForecastData> entry) {
        String day = entry.getKey();
        WeatherForecastData weatherForecastData = entry.getValue();
        return String.format("Forecast for %s: Max temperature: %.1f, Min temperature: %.1f ,Average temperature: %.1f, Condition: %s",
                day, weatherForecastData.getMaxTempC(), weatherForecastData.getMinTempC(),
                weatherForecastData.getAvgTempC(), weatherForecastData.getCondition());
    }

    private String getForecastForOneDay(String day, WeatherForecastData weatherForecastData) {
        return String.format("Forecast for %s: Max temperature: %.1f, Min temperature: %.1f, Average temperature: %.1f, Condition: %s",
                day, weatherForecastData.getMaxTempC(), weatherForecastData.getMinTempC(),
                weatherForecastData.getAvgTempC(), weatherForecastData.getCondition());
    }

    private void updateForecastMap(WeatherResponse weatherresponse) {
        forecastMap.clear();

        for (Days data : weatherresponse.getForecast().getForecastday()) {
            String dayOfWeek = getDayFromDate(data.getDate());
            WeatherForecastData weatherForecastData = new WeatherForecastData(
                    data.getDay().getMaxTempC(),
                    data.getDay().getMinTempC(),
                    data.getDay().getAvgTempC(),
                    data.getDay().getCondition().getText()
            );

            forecastMap.put(dayOfWeek.toLowerCase(), weatherForecastData);
        }
    }

    private String getDayFromDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return localDate.getDayOfWeek().toString();
    }


}





