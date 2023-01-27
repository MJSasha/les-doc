package com.mjsasha.lesdoc.services;

import com.mjsasha.lesdoc.TestData;
import com.mjsasha.lesdoc.data.entities.Lesson;
import com.mjsasha.lesdoc.exceptions.EntityNotFoundException;
import com.mjsasha.lesdoc.repositories.LessonsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LessonsServiceTest {

    private LessonsService lessonsService;
    private LessonsRepository lessonsRepository;

    @BeforeEach
    void configureMocks() {
        var fileStorageService = Mockito.mock(FileStorageService.class);
        lessonsRepository = Mockito.mock(LessonsRepository.class);

        lessonsService = new LessonsService(lessonsRepository, fileStorageService);
    }

    @Test
    void readReturnAnyEntities() {
        Mockito.when(lessonsRepository.findAll()).thenReturn(TestData.NotEmptyLessonsList);

        List<Lesson> lessons = lessonsService.read();

        assertFalse(lessons.isEmpty());
    }

    @Test
    void readReturnExceptionWhenEmptyList() {
        Mockito.when(lessonsRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(EntityNotFoundException.class, () -> lessonsService.read());
    }

    @Test
    void readExistingEntity() {
        Mockito.when(lessonsRepository.findById(1)).thenReturn(Optional.of(TestData.NotEmptyLessonsList.get(0)));

        var lesson = lessonsService.read(1);

        assertNotNull(lesson);
    }

    @Test
    void readNotExistingEntity() {
        Mockito.when(lessonsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> lessonsService.read(1));
    }
}