package com.mjsasha.lesdoc.controllers;

import com.mjsasha.lesdoc.interfaces.FileConverter;
import com.mjsasha.lesdoc.services.FileStorageService;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("filesConvertor")
public class FilesConvertorController {

    private final FileConverter fileConverter;
    private final FileStorageService fileStorageService;

    public FilesConvertorController(FileConverter fileConverter, FileStorageService fileStorageService) {
        this.fileConverter = fileConverter;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping
    public Resource test() throws JSONException, IOException {
        return fileConverter.convertToPdf(fileStorageService.loadFileAsResource("test.docx", "test"));
    }
}
