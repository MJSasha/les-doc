package com.mjsasha.lesdoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class LesDocApplication {

    public static void main(String[] args) {
        SpringApplication.run(LesDocApplication.class, args);
    }

}
