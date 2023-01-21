package com.mjsasha.lesdoc.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
class UsersController {

    @GetMapping
    public String findAll() {
        return "all users";
    }
}
