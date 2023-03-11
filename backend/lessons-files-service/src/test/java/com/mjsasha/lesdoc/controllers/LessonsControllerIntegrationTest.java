package com.mjsasha.lesdoc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mjsasha.lesdoc.repositories.LessonsRepository;
import com.mjsasha.lesdoc.services.FileStorageService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.Optional;

import static com.mjsasha.lesdoc.TestData.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class LessonsControllerIntegrationTest {

    private final ObjectWriter objectWriter;

    @Autowired
    private MockMvc mvc;
    @MockBean
    private LessonsRepository lessonsRepository;
    @MockBean
    private FileStorageService fileStorageService;

    LessonsControllerIntegrationTest() {
        this.objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @Test
    void createLesson() throws Exception {
        when(lessonsRepository.save(any())).thenReturn(MOCK_LESSON);
        String lessonJson = objectWriter.writeValueAsString(MOCK_LESSON);

        mvc.perform(MockMvcRequestBuilders
                        .post("/lessons")
                        .content(lessonJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(MOCK_LESSON.getId().toString()));

        verify(fileStorageService).createDirectory(MOCK_LESSON.getFolderName());
    }

    @Test
    void createLessonWithoutFolderName() throws Exception {
        when(lessonsRepository.save(any())).thenReturn(MOCK_LESSON_WITH_EMPTY_FOLDER_NAME);
        String lessonJson = objectWriter.writeValueAsString(MOCK_LESSON_WITH_EMPTY_FOLDER_NAME);

        mvc.perform(MockMvcRequestBuilders
                        .post("/lessons")
                        .content(lessonJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(MOCK_LESSON_WITH_EMPTY_FOLDER_NAME.getId().toString()));

        verify(fileStorageService).createDirectory(MOCK_LESSON_WITH_EMPTY_FOLDER_NAME.getName());
    }

    @Test
    void getAllExistingLessons() throws Exception {
        when(lessonsRepository.findAll()).thenReturn(LESSONS_LIST);
        String lessonJson = objectWriter.writeValueAsString(LESSONS_LIST);

        mvc.perform(get("/lessons"))
                .andExpect(status().isOk())
                .andExpect(content().json(lessonJson));
    }

    @Test
    void getAllNotExistingLessons() throws Exception {
        when(lessonsRepository.findAll()).thenReturn(Collections.emptyList());

        mvc.perform(get("/lessons"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getExistingLesson() throws Exception {
        when(lessonsRepository.findById(1)).thenReturn(Optional.of(MOCK_LESSON));
        String lessonJson = objectWriter.writeValueAsString(MOCK_LESSON);

        mvc.perform(get("/lessons/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(lessonJson));
    }

    @Test
    void getNotExistingLesson() throws Exception {
        mvc.perform(get("/lessons/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteExistingLesson() throws Exception {
        when(lessonsRepository.findById(1)).thenReturn(Optional.of(MOCK_LESSON));

        mvc.perform(delete("/lessons/1"))
                .andExpect(status().isOk());

        verify(fileStorageService).removeDirectory(MOCK_LESSON.getFolderName());
    }

    @Test
    void deleteNotExistingLesson() throws Exception {
        mvc.perform(delete("/lessons/1"))
                .andExpect(status().isNoContent());
    }
}