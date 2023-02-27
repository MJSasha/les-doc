package com.mjsasha.orchestrator.controllers;

import com.mjsasha.commonmodels.files.UploadFileResponse;
import com.mjsasha.commonmodels.statistic.StatisticEvent;
import com.mjsasha.commonmodels.statistic.StatisticEventModel;
import com.mjsasha.orchestrator.configs.ServicesProperties;
import com.mjsasha.orchestrator.kafka.StatisticProducer;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/files")
public class FilesController {

    private static final String CONTROLLER_URI = "/files";
    private final WebClient webClient;
    private final StatisticProducer statisticProducer;

    public FilesController(ServicesProperties servicesProperties, StatisticProducer statisticProducer) {
        webClient = WebClient.builder().baseUrl(servicesProperties.getFilesAndLessonsOrigin()).build();
        this.statisticProducer = statisticProducer;
    }

    @Operation(summary = "Use for upload one file")
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam Integer lessonId) {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", file.getResource());

        var response = webClient
                .post().uri(uriBuilder -> uriBuilder
                        .path(CONTROLLER_URI + "/uploadFile")
                        .queryParam("lessonId", lessonId)
                        .build())
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .retrieve().bodyToMono(UploadFileResponse.class).block();
        statisticProducer.sendEvent(new StatisticEventModel(StatisticEvent.FILE_UPLOADED,
                lessonId,
                String.format("Uploaded file: %s", Objects.requireNonNull(response).getFileName())));
        return response;
    }

    @Operation(summary = "Use for upload many file")
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("file") MultipartFile[] files, @RequestParam Integer lessonId) {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        Arrays.stream(files).forEach(file -> bodyBuilder.part("file", file.getResource()));

        var response = webClient
                .post().uri(uriBuilder -> uriBuilder
                        .path(CONTROLLER_URI + "/uploadMultipleFiles")
                        .queryParam("lessonId", lessonId)
                        .build())
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .retrieve().bodyToFlux(UploadFileResponse.class).toStream().toList();

        for (var fileResponse : response) {
            statisticProducer.sendEvent(new StatisticEventModel(StatisticEvent.FILE_UPLOADED,
                    lessonId,
                    String.format("Uploaded file: %s", Objects.requireNonNull(fileResponse).getFileName())));
        }

        return response;
    }

    @Operation(summary = "Use for download uploaded files")
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, @RequestParam Integer lessonId, HttpServletRequest request) {
        var response = ResponseEntity.ok(
                webClient
                        .get().uri(uriBuilder -> uriBuilder
                                .path(CONTROLLER_URI + "/downloadFile/" + fileName)
                                .queryParam("lessonId", lessonId)
                                .build())
                        .retrieve().bodyToMono(Resource.class).block());

        if (response.getStatusCode().is2xxSuccessful()) {
            statisticProducer.sendEvent(new StatisticEventModel(StatisticEvent.FILE_DOWNLOADED, lessonId, fileName));
        }
        return response;
    }

    @Operation(summary = "Used to get the names of all files in the lesson folder")
    @GetMapping("/getAllFilesNames")
    public String[] getAllFilesName(@RequestParam Integer lessonId) {
        return (String[]) webClient
                .get().uri(uriBuilder -> uriBuilder
                        .path(CONTROLLER_URI + "/getAllFilesNames")
                        .queryParam("lessonId", lessonId)
                        .build())
                .retrieve().bodyToFlux(String.class).toStream().toArray();
    }

    @Operation(summary = "Use to delete file")
    @DeleteMapping
    public void deleteFile(@RequestParam String fileName, @RequestParam Integer lessonId) {
        webClient.delete().uri(uriBuilder -> uriBuilder
                        .path(CONTROLLER_URI)
                        .queryParam("fileName", fileName)
                        .queryParam("lessonId", lessonId)
                        .build())
                .retrieve();
        statisticProducer.sendEvent(new StatisticEventModel(StatisticEvent.FILE_DELETED, lessonId, fileName));
    }
}
