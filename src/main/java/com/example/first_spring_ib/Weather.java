package com.example.first_spring_ib;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Weather {

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://api.weatherapi.com/v1/forecast.json?key=727bb15bfd014c1487795606242102&q=Sarajevo&days=3&aqi=no&alerts=no"))
                .header("Content-Type", "application/json")
                .GET()
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        WeatherResponse weatherresponse = objectMapper.readValue(response.body(), WeatherResponse.class);


        System.out.println("Forecast:");

        System.out.println((((weatherresponse.getForecast()).getForecastday()).get(0)).
                getDay().getMaxtemp_c());
        System.out.println((((weatherresponse.getForecast()).getForecastday()).get(0)).
                getDay().getCondition().getText());

    }
}

