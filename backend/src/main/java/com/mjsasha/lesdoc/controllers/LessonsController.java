package com.mjsasha.lesdoc.controllers;

import com.mjsasha.lesdoc.data.entities.Lesson;
import com.mjsasha.lesdoc.services.LessonsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/lessons")
public class LessonsController {

    private final LessonsService lessonsService;

    public LessonsController(LessonsService lessonsService) {
        this.lessonsService = lessonsService;
    }

    @Operation(summary = "Used to create a lesson")
    @PostMapping
    public ResponseEntity<String> create(@RequestBody Lesson lesson) {
        lessonsService.create(lesson);
        return ResponseEntity.ok("Lesson with name: " + lesson.getName() + ", with folder name: " + lesson.getFolderName() + ", successfully created.");
    }

    @Operation(summary = "Used to read all lessons")
    @GetMapping
    public List<Lesson> read() {
        return lessonsService.read();
    }

    @Operation(summary = "Used to read one lesson by id")
    @GetMapping("/{id}")
    public Lesson read(@PathVariable Integer id) {
        return lessonsService.read(id);
    }

    @Operation(summary = "Used to delete a lesson")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        lessonsService.delete(id);
        return ResponseEntity.ok("Lesson successfully deleted.");
    }
}
