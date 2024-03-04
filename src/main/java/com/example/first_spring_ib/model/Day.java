package com.example.first_spring_ib.model;

import com.example.first_spring_ib.model.Condition;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)

public class Day {

    @JsonProperty("maxtemp_c")
    private float maxTempC;
    @JsonProperty("mintemp_c")
    private float minTempC;
    @JsonProperty("avgtemp_c")
    private float avgTempC;
    private Condition condition;
}
