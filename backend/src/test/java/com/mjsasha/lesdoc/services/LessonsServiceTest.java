package com.mjsasha.lesdoc.services;

import com.mjsasha.lesdoc.data.entities.Lesson;
import com.mjsasha.lesdoc.exceptions.EntityNotFoundException;
import com.mjsasha.lesdoc.repositories.LessonsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.mjsasha.lesdoc.TestData.NOT_EMPTY_LESSONS_LIST;
import static org.junit.jupiter.api.Assertions.*;

class LessonsServiceTest {

    private final LessonsRepository lessonsRepository = Mockito.mock(LessonsRepository.class);
    private final LessonsService lessonsService = new LessonsService(lessonsRepository, Mockito.mock(FileStorageService.class));

    @Test
    void createLesson(){
        ArgumentCaptor<Lesson> argument = ArgumentCaptor.forClass(Lesson.class);
        lessonsService.create(NOT_EMPTY_LESSONS_LIST.get(0));

        verify(lessonsRepository).save(argument.capture());
        assertEquals("First folder", argument.getValue().getFolderName());
    }

    @Test
    void createLessonWithEmptyFolderName(){
        ArgumentCaptor<Lesson> argument = ArgumentCaptor.forClass(Lesson.class);
        lessonsService.create(NOT_EMPTY_LESSONS_LIST.get(2));

        verify(lessonsRepository).save(argument.capture());
        assertEquals("Third lesson", argument.getValue().getFolderName());
    }

    @Test
    void readReturnAnyEntities() {
        Mockito.when(lessonsRepository.findAll()).thenReturn(NOT_EMPTY_LESSONS_LIST);

        List<Lesson> lessons = lessonsService.read();

        assertFalse(lessons.isEmpty());
    }

    @Test
    void readReturnExceptionWhenEmptyList() {
        Mockito.when(lessonsRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(EntityNotFoundException.class, lessonsService::read);
    }

    @Test
    void readExistingEntity() {
        Mockito.when(lessonsRepository.findById(1)).thenReturn(Optional.of(NOT_EMPTY_LESSONS_LIST.get(0)));

        var lesson = lessonsService.read(1);

        assertNotNull(lesson);
    }

    @Test
    void readNotExistingEntity() {
        Mockito.when(lessonsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> lessonsService.read(1));
    }

    @Test
    void deleteExistingLesson() {
        Mockito.when(lessonsRepository.findById(1)).thenReturn(Optional.of(NOT_EMPTY_LESSONS_LIST.get(0)));

        assertDoesNotThrow(() -> lessonsService.delete(1));
    }

    @Test
    void deleteNotExistingLesson() {
        Mockito.when(lessonsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> lessonsService.delete(1));
    }
}