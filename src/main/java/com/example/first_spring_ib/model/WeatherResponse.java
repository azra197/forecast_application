package com.example.first_spring_ib.model;

import com.example.first_spring_ib.model.Forecast;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class WeatherResponse {

   private Forecast forecast;

}
