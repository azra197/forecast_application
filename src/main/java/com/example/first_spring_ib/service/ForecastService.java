package com.example.first_spring_ib.service;

import com.example.first_spring_ib.model.WeatherForecastData;
import com.example.first_spring_ib.controller.ForecastController;
import com.example.first_spring_ib.model.Days;
import com.example.first_spring_ib.model.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.Configuration;
import io.swagger.client.auth.ApiKeyAuth;
import io.swagger.client.api.ApisApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.threeten.bp.LocalDate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
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

    public WeatherResponse fetchForecastData() throws IOException, InterruptedException, ApiException {
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(weatherApiKey))
//                .header("Content-Type", "application/json")
//                .GET()
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        logger.info("API response : {}", response.body());
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readValue(response.body(), WeatherResponse.class);


        ApiClient defaultClient = new ApiClient();

// Configure API key authorization: ApiKeyAuth
        ApiKeyAuth ApiKeyAuth = (ApiKeyAuth) defaultClient.getAuthentication("ApiKeyAuth");
        ApiKeyAuth.setApiKey(weatherApiKey);
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//ApiKeyAuth.setApiKeyPrefix("Token");

        ApisApi apiInstance = new ApisApi();
        String q = "Sarajevo"; // String | Pass US Zipcode, UK Postcode, Canada Postalcode, IP address, Latitude/Longitude (decimal degree) or city name. Visit [request parameter section](https://www.weatherapi.com/docs/#intro-request) to learn more.
        Integer days = 3; // Integer | Number of days of weather forecast. Value ranges from 1 to 14
        LocalDate dt = LocalDate.now(); // LocalDate | Date should be between today and next 14 day in yyyy-MM-dd format. e.g. '2015-01-01'
        Integer unixdt = null; // Integer | Please either pass 'dt' or 'unixdt' and not both in same request. unixdt should be between today and next 14 day in Unix format. e.g. 1490227200
        Integer hour = null; // Integer | Must be in 24 hour. For example 5 pm should be hour=17, 6 am as hour=6
        String lang = null; // String | Returns 'condition:text' field in API in the desired language.<br /> Visit [request parameter section](https://www.weatherapi.com/docs/#intro-request) to check 'lang-code'.
        String alerts = "no"; // String | Enable/Disable alerts in forecast API output. Example, alerts=yes or alerts=no.
        String aqi = "no"; // String | Enable/Disable Air Quality data in forecast API output. Example, aqi=yes or aqi=no.
        Integer tp = null; // Integer | Get 15 min interval or 24 hour average data for Forecast and History API. Available for Enterprise clients only. E.g:- tp=15
        try {
            Object result = (WeatherResponse) apiInstance.forecastWeather(q, days, dt, null, null, null, alerts, aqi, tp);
//            ObjectMapper objectMapper = new ObjectMapper();
//            String jsonResponse = objectMapper.writeValueAsString(result);
//        return objectMapper.readValue(jsonResponse, WeatherResponse.class);
            return (WeatherResponse) result;

            //System.out.println(result);
        } catch (ApiException e) {
            throw new ApiException();
//            System.err.println("Exception when calling ApisApi#forecastWeather");
//            e.printStackTrace();
        }
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
