package com.mjsasha.lesdoc.controllers;

import com.mjsasha.lesdoc.entities.Lesson;
import com.mjsasha.lesdoc.services.LessonsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonsController {

    private final LessonsService lessonsService;

    public LessonsController(LessonsService lessonsService) {
        this.lessonsService = lessonsService;
    }

    @PostMapping
    public Integer create(@RequestBody Lesson lesson) {
        return lessonsService.create(lesson);
    }

    @GetMapping
    public List<Lesson> read() {
        return lessonsService.read();
    }

    @GetMapping("/{id}")
    public Lesson read(@PathVariable Integer id) {
        return lessonsService.read(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        lessonsService.delete(id);
        return ResponseEntity.ok("Lesson successfully deleted.");
    }
}
