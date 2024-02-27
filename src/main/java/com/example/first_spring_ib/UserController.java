package com.example.first_spring_ib;

import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping
public class UserController {

//    User user;

//    @GetMapping("/api/math/sum")
//    public int sum(@RequestParam(name = "a") int no1, @RequestParam("b") int no2) {
//        return no1 + no2;
//    }
//
//    @PostMapping("/api/generate/model")
//    @ResponseBody
//    public DateUser person(@RequestParam int day, @RequestParam int month, @RequestParam int year, @RequestBody User user) {
//        DateUser du = new DateUser();
//        du.setUser(user);
//        du.setDate(day + "." + month + "." + year);
//        return du;
//    }

    private final Map<String, WeatherForecastData> forecastMap=new HashMap<>();

    @GetMapping("/forecast")
    public Map<String, WeatherForecastData> forecastForThreeDays() throws IOException, InterruptedException {
        if(forecastMap.isEmpty()) {
            getForecastForDay();
        }
        return forecastMap;
    }


    @GetMapping("/weather/{day}")
    public String forecastForDay(@PathVariable String day) throws IOException, InterruptedException {
        if(forecastMap.isEmpty()) {
            getForecastForDay();
        }
        WeatherForecastData forecast=forecastMap.get(day.toLowerCase());
        if(forecast!=null) {
            return "Forecast for " + day + forecast.toString();
        } else {
            return "There is no provided forecast for" + day;
        }
    }


    private void getForecastForDay() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://api.weatherapi.com/v1/forecast.json?key=727bb15bfd014c1487795606242102&q=Sarajevo&days=3&aqi=no&alerts=no"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        WeatherResponse weatherresponse = objectMapper.readValue(response.body(), WeatherResponse.class);

        for (Days data : weatherresponse.getForecast().getForecastday()) {
            String dayOfWeek = getDayFromDate(data.getDate());
            float maxTemp_c = data.getDay().getMaxtemp_c();
            float minTemp_c = data.getDay().getMintemp_c();
            float avgTemp_c = data.getDay().getAvgtemp_c();
            String text = data.getDay().getCondition().getText();
            WeatherForecastData forecast = new WeatherForecastData(maxTemp_c, minTemp_c, avgTemp_c, text);
            forecastMap.put(dayOfWeek.toLowerCase(), forecast);

        }
    }
    private String getDayFromDate(String date) {
        LocalDate localDate=LocalDate.parse(date);
        return localDate.getDayOfWeek().toString();
    }

}




