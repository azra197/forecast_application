package com.example.first_spring_ib;

import lombok.Data;
@Data
    public class WeatherForecastData {
        private float maxtemp_c;
        private float mintemp_c;
        private float avgtemp_c;
        private String condition;

        public WeatherForecastData(float maxtemp_c,float mintemp_c, float avgtemp_c, String condition) {
            this.maxtemp_c=maxtemp_c;
            this.mintemp_c=mintemp_c;
            this.avgtemp_c=avgtemp_c;
            this.condition=condition;
        }
        public String toString() {
            return  ": Max temeprature: " + getMaxtemp_c()
                    + " ,Min temeprature: " + getMintemp_c() + " ,Average temeprature: " + getAvgtemp_c()
                    + " ,Condition: " + getCondition();
        }
    }

