package com.mjsasha.lesdoc.services;

import com.mjsasha.lesdoc.data.entities.Lesson;
import com.mjsasha.lesdoc.exceptions.EntityNotFoundException;
import com.mjsasha.lesdoc.repositories.LessonsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Lesson read(Integer id) {
        var lesson = lessonsRepository.findById(id).orElse(null);
        if (lesson == null) throw new EntityNotFoundException("Lesson with id " + id + " not exist");
        return lesson;
    }

    public List<Lesson> read() {
        var lessons = (List<Lesson>) lessonsRepository.findAll();
        if (lessons.isEmpty()) throw new EntityNotFoundException("No existing lessons");
        return lessons;
    }

    public void delete(Integer id) {
        try {
            lessonsRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
