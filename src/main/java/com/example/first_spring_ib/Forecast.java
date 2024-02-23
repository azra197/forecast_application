package com.example.first_spring_ib;

import lombok.Data;
import java.util.List;

@Data
public class Forecast {
    private List<Days> forecastday;

}
