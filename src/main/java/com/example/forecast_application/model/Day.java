package com.example.forecast_application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
