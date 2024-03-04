package com.example.first_spring_ib.service;

import com.example.first_spring_ib.WeatherForecastData;
import com.example.first_spring_ib.controller.ForecastController;
import com.example.first_spring_ib.model.Days;
import com.example.first_spring_ib.model.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ForecastService {

    private static final Logger logger = LoggerFactory.getLogger(ForecastController.class);

    @Autowired
    private Map<String, WeatherForecastData> forecastMap;

    public List<String> getForecastFor3Days() {
        return forecastMap.entrySet().stream().map(this::formatForecast3DMap)
                .collect(Collectors.toList());
    }

    public String formatForecast3DMap(Map.Entry<String, WeatherForecastData> entry) {
        String day = entry.getKey();
        WeatherForecastData weatherForecastData = entry.getValue();
        return String.format("Forecast for %s: Max temperature: %.1f, Min temperature: %.1f ,Average temperature: %.1f, Condition: %s",
                day, weatherForecastData.getMaxTempC(), weatherForecastData.getMinTempC(),
                weatherForecastData.getAvgTempC(), weatherForecastData.getCondition());
    }

    public String getForecastForOneDay(String day) {
        WeatherForecastData weatherForecastData = forecastMap.get(day);
        if (weatherForecastData != null) {
            return formatForecast1D(day, weatherForecastData);
        } else {
            return "There is no provided forecast for" + day;
        }
    }

    public String formatForecast1D(String day, WeatherForecastData weatherForecastData) {
        return String.format("Forecast for %s: Max temperature: %.1f, Min temperature: %.1f, Average temperature: %.1f, Condition: %s",
                day, weatherForecastData.getMaxTempC(), weatherForecastData.getMinTempC(),
                weatherForecastData.getAvgTempC(), weatherForecastData.getCondition());
    }

    public WeatherResponse fetchForecastData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://api.weatherapi.com/v1/forecast.json?key=727bb15bfd014c1487795606242102&q=Sarajevo&days=3&aqi=no&alerts=no"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("API response : {}", response.body());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), WeatherResponse.class);
    }

    public void updateForecastMap(WeatherResponse weatherresponse) {
        forecastMap.clear();

        for (Days data : weatherresponse.getForecast().getForecastDay()) {
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
