package com.example.first_spring_ib;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/math/sum")
    public int sum(@RequestParam (name="a") int no1, @RequestParam ("b") int no2) {
        return no1+no2;
    }
}



