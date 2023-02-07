package com.mjsasha.lesdoc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mjsasha.lesdoc.repositories.LessonsRepository;
import com.mjsasha.lesdoc.services.FileStorageService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.Optional;

import static com.mjsasha.lesdoc.TestData.NOT_EMPTY_LESSONS_LIST;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "dev")
class LessonsControllerIntegrationTest {

    private final ObjectWriter objectWriter;

    @Autowired
    private MockMvc mvc;
    @MockBean
    private LessonsRepository lessonsRepository;
    @MockBean
    private FileStorageService fileStorageService;

    public LessonsControllerIntegrationTest() {
        this.objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @Test
    void createLesson() throws Exception {
        var lessonForTest = NOT_EMPTY_LESSONS_LIST.get(0);
        String lessonJson = objectWriter.writeValueAsString(lessonForTest);

        mvc.perform(MockMvcRequestBuilders
                        .post("/lessons")
                        .content(lessonJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Lesson with name: " + lessonForTest.getName() + ", with folder name: " + lessonForTest.getFolderName() + ", successfully created."));

        verify(fileStorageService).createDirectory(lessonForTest.getFolderName());
    }

    @Test
    void createLessonWithoutFolderName() throws Exception {
        var lessonForTest = NOT_EMPTY_LESSONS_LIST.get(2);
        String lessonJson = objectWriter.writeValueAsString(lessonForTest);

        mvc.perform(MockMvcRequestBuilders
                        .post("/lessons")
                        .content(lessonJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Lesson with name: " + lessonForTest.getName() + ", with folder name: " + lessonForTest.getName() + ", successfully created."));

        verify(fileStorageService).createDirectory(lessonForTest.getName());
    }

    @Test
    void getAllExistingLessons() throws Exception {
        Mockito.when(lessonsRepository.findAll()).thenReturn(NOT_EMPTY_LESSONS_LIST);
        String lessonJson = objectWriter.writeValueAsString(NOT_EMPTY_LESSONS_LIST);

        mvc.perform(get("/lessons"))
                .andExpect(status().isOk())
                .andExpect(content().json(lessonJson));
    }

    @Test
    void getAllNotExistingLessons() throws Exception {
        Mockito.when(lessonsRepository.findAll()).thenReturn(Collections.emptyList());

        mvc.perform(get("/lessons"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getExistingLesson() throws Exception {
        var lessonForTest = NOT_EMPTY_LESSONS_LIST.get(0);
        Mockito.when(lessonsRepository.findById(1)).thenReturn(Optional.ofNullable(lessonForTest));
        String lessonJson = objectWriter.writeValueAsString(lessonForTest);

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
        var lessonForTest = NOT_EMPTY_LESSONS_LIST.get(0);
        Mockito.when(lessonsRepository.findById(1)).thenReturn(Optional.ofNullable(lessonForTest));

        mvc.perform(delete("/lessons/1"))
                .andExpect(status().isOk());

        verify(fileStorageService).removeDirectory(lessonForTest.getFolderName());
    }

    @Test
    void deleteNotExistingLesson() throws Exception {
        mvc.perform(delete("/lessons/1"))
                .andExpect(status().isNoContent());
    }
}