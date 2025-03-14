package com.example.forecast_application.service;

import com.example.forecast_application.model.Days;
import com.example.forecast_application.model.Forecast;
import com.example.forecast_application.model.WeatherForecastData;
import com.example.forecast_application.model.WeatherResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastService {

    private static final Logger logger = LoggerFactory.getLogger(ForecastService.class);

    @Autowired
    private Map<String, WeatherForecastData> forecastMap;

    @Value("${api.key}")
    private String weatherApiKey;

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
                .uri(URI.create("http://api.weatherapi.com/v1/forecast.json?key=727bb15bfd014c1487795606242102&q=Sarajevo&days=3&aqi=no&alerts=no"))                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("API response : {}", response.body());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(response.body(), WeatherResponse.class);
    }

    public void updateForecastMap(Forecast forecast) {
        forecastMap.clear();
        for (Days days : forecast.getForecastDay()) {
            String dayOfWeek = getDayFromDate(String.valueOf(days.getDate()));
            WeatherForecastData weatherForecastData = new WeatherForecastData(
                    days.getDay().getMaxTempC(),
                    days.getDay().getMinTempC(),
                    days.getDay().getAvgTempC(),
                    days.getDay().getCondition().getText()
            );

            forecastMap.put(dayOfWeek.toLowerCase(), weatherForecastData);
        }
    }

    private String getDayFromDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate.getDayOfWeek().toString();
    }
}
