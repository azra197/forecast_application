package com.example.first_spring_ib;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)

public class Day {

    private float maxtemp_c;
    private float mintemp_c;
    private float avgtemp_c;
    private Condition condition;
}
