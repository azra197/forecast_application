package com.example.first_spring_ib;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherForecastData {
    @JsonProperty("maxtemp_c")
    private float maxTempC;
    @JsonProperty("mintemp_c")
    private float minTempC;
    @JsonProperty("avgtemp_c")
    private float avgTempC;
    private String condition;

    public WeatherForecastData(float maxTempC, float minTempC, float avgTempC, String condition) {
        this.maxTempC = maxTempC;
        this.minTempC = minTempC;
        this.avgTempC = avgTempC;
        this.condition = condition;
    }
}

