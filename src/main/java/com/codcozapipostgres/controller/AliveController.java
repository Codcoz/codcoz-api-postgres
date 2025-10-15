package com.codcozapipostgres.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alive")
public class AliveController {

    @GetMapping
    public ResponseEntity<String> checkAlive() {
        return ResponseEntity.ok("API is alive ");
    }
}