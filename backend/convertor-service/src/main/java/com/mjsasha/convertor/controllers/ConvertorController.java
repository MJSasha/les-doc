package com.mjsasha.convertor.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/convertor")
public class ConvertorController {

    public ConvertorController() {
    }

    @GetMapping("/example")
    public ResponseEntity<String> example() {
        return  ResponseEntity.ok("example!");
    }
}
