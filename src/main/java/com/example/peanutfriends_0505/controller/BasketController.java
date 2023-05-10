package com.example.peanutfriends_0505.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BasketController {

    @PostMapping("/api/filterTest")
    public String test(){
        return "pass filter";
    }

}
