package com.example.forecast_application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class Forecast {
    @JsonProperty("forecastday")
    private List<Days> forecastDay;

}
