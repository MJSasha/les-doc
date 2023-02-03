package com.mjsasha.lesdoc.controllers;

import com.mjsasha.lesdoc.interfaces.FileConverter;
import com.mjsasha.lesdoc.services.FileStorageService;
import com.mjsasha.lesdoc.services.LessonsService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("filesConvertor")
public class FilesConvertorController {

    private final FileConverter fileConverter;
    private final FileStorageService fileStorageService;
    private final LessonsService lessonsService;

    public FilesConvertorController(FileConverter fileConverter, FileStorageService fileStorageService, LessonsService lessonsService) {
        this.fileConverter = fileConverter;
        this.fileStorageService = fileStorageService;
        this.lessonsService = lessonsService;
    }

    @PostMapping
    public Resource convertFile(@RequestParam @Parameter(description = "Like \"cat.png\"") String fileName, @RequestParam Integer lessonId) throws JSONException, IOException {
        var lesson = lessonsService.read(lessonId);

        return fileConverter.convertToPdf(fileStorageService.loadFileAsResource(fileName, lesson.getFolderName()));
    }
}
