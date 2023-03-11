package com.mjsasha.lesdoc.controllers;

import com.mjsasha.lesdoc.data.entities.Lesson;
import com.mjsasha.lesdoc.data.models.StatisticEvent;
import com.mjsasha.lesdoc.data.models.StatisticEventModel;
import com.mjsasha.lesdoc.kafka.StatisticProducer;
import com.mjsasha.lesdoc.services.LessonsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonsController {

    private final LessonsService lessonsService;
    private final StatisticProducer statisticProducer;

    public LessonsController(LessonsService lessonsService, StatisticProducer statisticProducer) {
        this.lessonsService = lessonsService;
        this.statisticProducer = statisticProducer;
    }

    @Operation(summary = "Used to create a lesson")
    @PostMapping
    public Integer create(@RequestBody Lesson lesson) {
        var lessonId = lessonsService.create(lesson);
        statisticProducer.sendEvent(new StatisticEventModel()
                .setStatisticEvent(StatisticEvent.LESSON_CREATED)
                .setLessonId(lessonId));
        return lessonId;
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
        statisticProducer.sendEvent(new StatisticEventModel()
                .setStatisticEvent(StatisticEvent.LESSON_DELETED)
                .setLessonId(id));
        return ResponseEntity.ok("Lesson successfully deleted.");
    }
}
