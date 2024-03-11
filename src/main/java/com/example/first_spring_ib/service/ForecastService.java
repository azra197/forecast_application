package com.example.first_spring_ib.service;

import com.example.first_spring_ib.model.MyException;
import com.example.first_spring_ib.model.WeatherForecastData;
import com.example.first_spring_ib.model.WeatherResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.auth.ApiKeyAuth;
import io.swagger.client.api.ApisApi;
import io.swagger.client.model.Forecast;
import io.swagger.client.model.ForecastForecastday;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.threeten.bp.LocalDate;

import java.io.IOException;
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

    public WeatherResponse fetchForecastData() throws IOException, MyException, InterruptedException, ApiException {
        ApiClient defaultClient = new ApiClient();

        ApiKeyAuth ApiKeyAuth = (ApiKeyAuth) defaultClient.getAuthentication("ApiKeyAuth");
        ApiKeyAuth.setApiKey(weatherApiKey);

        ApisApi apiInstance = new ApisApi(defaultClient);
        String q = "Sarajevo";
        Integer days = 3;
        LocalDate dt = LocalDate.now();
        Integer unixdt = null;
        Integer hour = null;
        String lang = null;
        String alerts = "no";
        String aqi = "no";
        Integer tp = null;

        Object result = apiInstance.forecastWeather(q, days, dt, null, null, null, alerts, aqi, tp);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(objectMapper.writeValueAsString(result), WeatherResponse.class);


    }

    public void updateForecastMap(Forecast forecast) {
        forecastMap.clear();

        for (ForecastForecastday forecastDay : forecast.getForecastday()) {
            String dayOfWeek = getDayFromDate(String.valueOf(forecastDay.getDate()));
            WeatherForecastData weatherForecastData = new WeatherForecastData(
                    forecastDay.getDay().getMaxtempC(),
                    forecastDay.getDay().getMintempC(),
                    forecastDay.getDay().getAvgtempC(),
                    forecastDay.getDay().getCondition().getText()
            );

            forecastMap.put(dayOfWeek.toLowerCase(), weatherForecastData);
        }
    }

    private String getDayFromDate(String date) {
        java.time.LocalDate localDate = java.time.LocalDate.parse(date);
        return localDate.getDayOfWeek().toString();
    }
}
