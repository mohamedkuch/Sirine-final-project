package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/greeting")

public class GreetingsController {

    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hellooooo");
    }


    @GetMapping("/say-good")
    public ResponseEntity<String> sayGoood() {
        return ResponseEntity.ok("sayGoood");
    }
}
