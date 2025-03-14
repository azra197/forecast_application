package com.example.forecast_application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
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

