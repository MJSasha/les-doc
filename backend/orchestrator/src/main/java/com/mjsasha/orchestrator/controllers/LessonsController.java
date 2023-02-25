package com.mjsasha.orchestrator.controllers;

import com.mjsasha.orchestrator.configs.ServicesProperties;
import com.mjsasha.orchestrator.models.Lesson;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonsController {

    private static final String CONTROLLER_URI = "/lessons";
    private final WebClient webClient;

    public LessonsController(ServicesProperties servicesProperties) {
        webClient = WebClient.builder().baseUrl(servicesProperties.getFilesAndLessonsOrigin()).build();
    }

    @Operation(summary = "Used to create a lesson")
    @PostMapping
    public ResponseEntity<String> create(@RequestBody Lesson lesson) {
        return ResponseEntity.ok(
                webClient
                        .post().uri(CONTROLLER_URI)
                        .body(Mono.just(lesson), Lesson.class)
                        .retrieve().bodyToMono(String.class).block());
    }

    @Operation(summary = "Used to read all lessons")
    @GetMapping
    public List<Lesson> read() {
        return webClient
                .get().uri(CONTROLLER_URI)
                .retrieve().bodyToFlux(Lesson.class).toStream().toList();
    }

    @Operation(summary = "Used to read one lesson by id")
    @GetMapping("/{id}")
    public Lesson read(@PathVariable Integer id) {
        return webClient
                .get().uri(CONTROLLER_URI + "/{id}", id)
                .retrieve().bodyToMono(Lesson.class).block();
    }

    @Operation(summary = "Used to delete a lesson")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(
                webClient
                        .delete().uri(CONTROLLER_URI + "/{id}", id)
                        .retrieve().bodyToMono(String.class).block());
    }
}
