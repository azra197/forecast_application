package com.example.first_spring_ib;

import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/forecast")
    public List<String> forecastForThreeDays() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://api.weatherapi.com/v1/forecast.json?key=727bb15bfd014c1487795606242102&q=Sarajevo&days=3&aqi=no&alerts=no"))
                .header("Content-Type", "application/json")
                .GET()
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        WeatherResponse weatherresponse = objectMapper.readValue(response.body(), WeatherResponse.class);

        List<String>listForThreeDays = new ArrayList<>();
        for(Days data : weatherresponse.getForecast().getForecastday()) {
            String dataForcast = "Forecast for " + data.getDate() + " ,Max temeprature: " + data.getDay().getMaxtemp_c()
                    + " ,Min temeprature: " + data.getDay().getMintemp_c() + " ,Average temeprature: " + data.getDay().getAvgtemp_c()
                    + " ,Condition: " + data.getDay().getCondition().getText();
            listForThreeDays.add(dataForcast);
        }
        return listForThreeDays;

    }




}




