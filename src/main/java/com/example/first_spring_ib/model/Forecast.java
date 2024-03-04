package com.example.first_spring_ib.model;

import com.example.first_spring_ib.model.Days;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class Forecast {
    @JsonProperty("forecastday")
    private List<Days> forecastDay;

}
