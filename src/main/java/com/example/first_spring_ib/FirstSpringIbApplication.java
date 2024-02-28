package com.example.first_spring_ib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FirstSpringIbApplication {

    public static void main(String[] args) {

        SpringApplication.run(FirstSpringIbApplication.class, args);


    }


}
