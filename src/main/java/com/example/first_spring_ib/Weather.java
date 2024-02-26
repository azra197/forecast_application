package com.example.first_spring_ib;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Weather {

    public static void main(String[] args)  throws IOException, InterruptedException  {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://api.weatherapi.com/v1/forecast.json?key=727bb15bfd014c1487795606242102&q=Sarajevo&days=3&aqi=no&alerts=no"))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();


            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherResponse weatherresponse = objectMapper.readValue(response.body(), WeatherResponse.class);

            List<String> listForThreeDays = new ArrayList<>();
            for (Days data : weatherresponse.getForecast().getForecastday()) {
                String dataForcast = "Forecast for " + data.getDate() + ",Max temeprature: " + data.getDay().getMaxtemp_c();
                listForThreeDays.add(dataForcast);
            }
            System.out.println(listForThreeDays);
        }
    }

//    public static void main(String[] args) throws IOException, InterruptedException {
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("http://api.weatherapi.com/v1/forecast.json?key=727bb15bfd014c1487795606242102&q=Sarajevo&days=3&aqi=no&alerts=no"))
//                .header("Content-Type", "application/json")
//                .GET()
//                .build();
//
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        ObjectMapper objectMapper = new ObjectMapper();
//        WeatherResponse weatherresponse = objectMapper.readValue(response.body(), WeatherResponse.class);
//
//
//        Days forecastForDate=null;
//        String date="2024-02-24";
//        for(Days data : weatherresponse.getForecast().getForecastday()) {
//            if(data.getDate().equals(date)) {
//                forecastForDate=data;
//                break;
//            }
//        }
//
//        if(forecastForDate!=null) {
//            float maxTemp_c = forecastForDate.getDay().getMaxtemp_c();
//
////            System.out.println("Forecast:");
////
////            System.out.println((((weatherresponse.getForecast()).getForecastday()).get(0)).
////                    getDay().getMaxtemp_c());
////            System.out.println((((weatherresponse.getForecast()).getForecastday()).get(0)).
////                    getDay().getMintemp_c());
////            System.out.println((((weatherresponse.getForecast()).getForecastday()).get(0)).
////                    getDay().getAvgtemp_c());
////            System.out.println((((weatherresponse.getForecast()).getForecastday()).get(0)).
////                    getDay().getCondition().getText());
//            System.out.println(maxTemp_c);
//        }
//
//    }


