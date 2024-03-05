package com.example.first_spring_ib.controller;

import com.example.first_spring_ib.service.ForecastService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ForecastController {

    private static final Logger logger = LoggerFactory.getLogger(ForecastController.class);
    
    private final ForecastService forecastService;

    @GetMapping("/forecast")
    public List<String> threeDaysForecast() {
        logger.info("GET /forecast");
        return forecastService.getForecastFor3Days();
    }

    @GetMapping("/weather/{day}")
    public String dayForecast(@PathVariable String day) {
        logger.info("GET /weather/{}", day);
        return forecastService.getForecastForOneDay(day);
    }








}





