package com.mjsasha.lesdoc.services;

import com.mjsasha.lesdoc.entities.Lesson;
import com.mjsasha.lesdoc.exceptions.EntityNotFoundException;
import com.mjsasha.lesdoc.repositories.LessonsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.mjsasha.lesdoc.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LessonsServiceTest {

    private final LessonsRepository lessonsRepository = Mockito.mock(LessonsRepository.class);
    private final LessonsService lessonsService = new LessonsService(lessonsRepository, Mockito.mock(FileStorageService.class));

    @Test
    void createLesson() {
        when(lessonsRepository.save(any())).thenReturn(MOCK_LESSON);

        ArgumentCaptor<Lesson> argument = ArgumentCaptor.forClass(Lesson.class);
        lessonsService.create(MOCK_LESSON);

        verify(lessonsRepository).save(argument.capture());
        assertEquals("First folder", argument.getValue().getFolderName());
    }

    @Test
    void createLessonWithEmptyFolderName() {
        when(lessonsRepository.save(any())).thenReturn(MOCK_LESSON_WITH_EMPTY_FOLDER_NAME);

        ArgumentCaptor<Lesson> argument = ArgumentCaptor.forClass(Lesson.class);
        lessonsService.create(MOCK_LESSON_WITH_EMPTY_FOLDER_NAME);

        verify(lessonsRepository).save(argument.capture());
        assertEquals("Third lesson", argument.getValue().getFolderName());
    }

    @Test
    void readReturnAnyEntities() {
        when(lessonsRepository.findAll()).thenReturn(LESSONS_LIST);

        List<Lesson> lessons = lessonsService.read();

        assertFalse(lessons.isEmpty());
    }

    @Test
    void readReturnExceptionWhenEmptyList() {
        when(lessonsRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(EntityNotFoundException.class, lessonsService::read);
    }

    @Test
    void readExistingEntity() {
        when(lessonsRepository.findById(1)).thenReturn(Optional.of(MOCK_LESSON));

        var lesson = lessonsService.read(1);

        assertNotNull(lesson);
    }

    @Test
    void readNotExistingEntity() {
        when(lessonsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> lessonsService.read(1));
    }

    @Test
    void deleteExistingLesson() {
        when(lessonsRepository.findById(1)).thenReturn(Optional.of(MOCK_LESSON));

        assertDoesNotThrow(() -> lessonsService.delete(1));
    }

    @Test
    void deleteNotExistingLesson() {
        when(lessonsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> lessonsService.delete(1));
    }
}