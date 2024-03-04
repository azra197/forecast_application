package com.example.first_spring_ib.model;

import com.example.first_spring_ib.model.Day;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Days {

    private String date;

    private Day day;

}
