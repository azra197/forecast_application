package com.example.first_spring_ib;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class WeatherResponse {

   private Forecast forecast;

}
