package com.example.first_spring_ib.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class WeatherForecastData {
    @JsonProperty("maxtemp_c")
    private float maxTempC;
    @JsonProperty("mintemp_c")
    private float minTempC;
    @JsonProperty("avgtemp_c")
    private float avgTempC;
    private String condition;

    public WeatherForecastData(BigDecimal maxtempC, BigDecimal mintempC, BigDecimal avgtempC, String text) {
    }
}

