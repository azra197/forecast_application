package com.example.first_spring_ib;

import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api")
public class UserController {

    User user;

    @GetMapping("/math/sum")
    public int sum(@RequestParam(name = "a") int no1, @RequestParam("b") int no2) {
        return no1 + no2;
    }

    @PostMapping("/generate/model")
    @ResponseBody
    public DateUser person(@RequestParam int day, @RequestParam int month, @RequestParam int year, @RequestBody User user) {
        DateUser du = new DateUser();
        du.setUser(user);
        du.setDate(day + "." + month + "." + year);
        return du;
    }


}




