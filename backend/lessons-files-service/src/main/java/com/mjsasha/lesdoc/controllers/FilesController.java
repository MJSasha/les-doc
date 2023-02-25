package com.mjsasha.lesdoc.controllers;

import com.mjsasha.lesdoc.data.entities.Lesson;
import com.mjsasha.lesdoc.data.models.UploadFileResponse;
import com.mjsasha.lesdoc.services.FileStorageService;
import com.mjsasha.lesdoc.services.LessonsService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class FilesController {

    private static final Logger logger = LoggerFactory.getLogger(FilesController.class);

    private final FileStorageService fileStorageService;
    private final LessonsService lessonsService;

    public FilesController(FileStorageService fileStorageService, LessonsService lessonsService) {
        this.fileStorageService = fileStorageService;
        this.lessonsService = lessonsService;
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam Integer lessonId) {
        Lesson lesson = lessonsService.read(lessonId);
        String fileName = fileStorageService.storeFile(file, lesson.getFolderName());

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("file") MultipartFile[] files, @RequestParam Integer lessonId) {
        return Arrays.stream(files)
                .map(file -> uploadFile(file, lessonId))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, @RequestParam Integer lessonId, HttpServletRequest request) {
        Lesson lesson = lessonsService.read(lessonId);
        Resource resource = fileStorageService.loadFileAsResource(fileName, lesson.getFolderName());

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.warn("Could not determine file type.");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/getAllFilesNames")
    public String[] getAllFilesName(@RequestParam Integer lessonId) {
        Lesson lesson = lessonsService.read(lessonId);
        return fileStorageService.getAllFilesNames(lesson.getFolderName());
    }

    @DeleteMapping
    public void deleteFile(@RequestParam String fileName, @RequestParam Integer lessonId) {
        var lesson = lessonsService.read(lessonId);
        fileStorageService.removeFile(fileName, lesson.getFolderName());
    }
}