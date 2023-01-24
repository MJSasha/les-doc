package com.mjsasha.lesdoc.controllers;

import com.mjsasha.lesdoc.data.entities.Lesson;
import com.mjsasha.lesdoc.services.LessonsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/lessons")
public class LessonsController {

    private final LessonsService lessonsService;

    public LessonsController(LessonsService lessonsService) {
        this.lessonsService = lessonsService;
    }

    @PostMapping
    public void createLesson(Lesson lesson) {
        lessonsService.create(lesson);
    }
}
