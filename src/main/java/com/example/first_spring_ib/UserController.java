package com.example.first_spring_ib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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


    private Map<String, WeatherForecastData> forecastMap = new HashMap<>();

    @GetMapping("/forecast")
    public List<String> forecastForThreeDays() throws IOException, InterruptedException {
        List<String> listForThreeDays = new ArrayList<>();
        for (Map.Entry<String, WeatherForecastData> entry : forecastMap.entrySet()) {
            String day = entry.getKey();
            WeatherForecastData weatherForecastData = entry.getValue();
            String forecast = " Forecast for " + day + ": Max temeprature: " + weatherForecastData.getMaxtemp_c()
                    + " ,Min temeprature: " + weatherForecastData.getMintemp_c() + " ,Average temeprature: " + weatherForecastData.getAvgtemp_c()
                    + " ,Condition: " + weatherForecastData.getCondition();
            listForThreeDays.add(forecast);
        }
        return listForThreeDays;
    }


    @GetMapping("/weather/{day}")
    public String forecastForDay(@PathVariable String day) throws IOException, InterruptedException {
        WeatherForecastData weatherForecastData = forecastMap.get(day);
        if (weatherForecastData != null) {
            return " Forecast for " + day + ": Max temeprature: " + weatherForecastData.getMaxtemp_c()
                    + " ,Min temeprature: " + weatherForecastData.getMintemp_c() + " ,Average temeprature: " + weatherForecastData.getAvgtemp_c()
                    + " ,Condition: " + weatherForecastData.getCondition();
        } else {
            return "There is no provided forecast for" + day;
        }
    }

    @Scheduled(fixedRate = 36000000)
    private void getForecastForDay() {
        try(HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://api.weatherapi.com/v1/forecast.json?key=727bb15bfd014c1487795606242102&q=Sarajevo&days=3&aqi=no&alerts=no"))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherResponse weatherresponse = objectMapper.readValue(response.body(), WeatherResponse.class);

            updateForecastMap(weatherresponse);
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateForecastMap(WeatherResponse weatherresponse) {
        forecastMap.clear();

        for (Days data : weatherresponse.getForecast().getForecastday()) {
            String dayOfWeek = getDayFromDate(data.getDate());
            WeatherForecastData weatherForecastData = new WeatherForecastData(
                    data.getDay().getMaxtemp_c(),
                    data.getDay().getMintemp_c(),
                    data.getDay().getAvgtemp_c(),
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





