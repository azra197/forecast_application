package com.example.first_spring_ib.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.client.model.Forecast;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class WeatherResponse {

   private Forecast forecast;

}
