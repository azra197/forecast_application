package com.example.first_spring_ib;

import lombok.Data;

import java.util.List;
@Data
public class ForecastDay {
    private List<Days> days;

    public List<Days> getdays() {
        return days;
    }

    public void setdays(List<Days> days) {
        this.days = days;
    }
}
