package com.mjsasha.lesdoc.services;

import com.mjsasha.lesdoc.data.entities.Lesson;
import com.mjsasha.lesdoc.repositories.LessonsRepository;
import org.springframework.stereotype.Service;

@Service
public class LessonsService {

    private final LessonsRepository lessonsRepository;
    private final FileStorageService fileStorageService;

    public LessonsService(LessonsRepository lessonsRepository, FileStorageService fileStorageService) {
        this.lessonsRepository = lessonsRepository;
        this.fileStorageService = fileStorageService;
    }

    public void create(Lesson lesson) {
        fileStorageService.createDirectory(lesson.getFolderName());
        lessonsRepository.save(lesson);
    }
}
