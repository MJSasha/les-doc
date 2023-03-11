package com.mjsasha.lesdoc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mjsasha.lesdoc.data.models.UploadFileResponse;
import com.mjsasha.lesdoc.repositories.LessonsRepository;
import com.mjsasha.lesdoc.services.FileStorageService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mjsasha.lesdoc.TestData.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
class FilesControllerIntegrationTest {

    private final ObjectWriter objectWriter;

    @Autowired
    private MockMvc mvc;
    @MockBean
    private LessonsRepository lessonsRepository;
    @MockBean
    private FileStorageService fileStorageService;

    FilesControllerIntegrationTest() {
        this.objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @Test
    void uploadFile() throws Exception {
        when(lessonsRepository.findById(1)).thenReturn(Optional.of(MOCK_LESSON));
        when(fileStorageService.storeFile(any(), any())).thenReturn(MOCK_LESSON.getFolderName());

        var expectedResponse = new UploadFileResponse(
                MOCK_LESSON.getFolderName(),
                "http://localhost/downloadFile/" + MOCK_LESSON.getFolderName().replace(" ", "%20"),
                "text/plain",
                MOCK_FILE.getSize()
        );

        mvc.perform(MockMvcRequestBuilders
                        .multipart("/files/uploadFile")
                        .file(MOCK_FILE)
                        .param("lessonId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectWriter.writeValueAsString(expectedResponse)));
    }

    @Test
    void uploadFileForNotExistingLesson() throws Exception {
        when(lessonsRepository.findById(1)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders
                        .multipart("/files/uploadFile")
                        .file(MOCK_FILE)
                        .param("lessonId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void uploadFiles() throws Exception {
        when(lessonsRepository.findById(1)).thenReturn(Optional.of(MOCK_LESSON));
        when(fileStorageService.storeFile(any(), any())).thenReturn(MOCK_LESSON.getFolderName());

        var expectedResponse = new ArrayList<>(List.of(
                new UploadFileResponse(
                        MOCK_LESSON.getFolderName(),
                        "http://localhost/downloadFile/" + MOCK_LESSON.getFolderName().replace(" ", "%20"),
                        "text/plain",
                        MOCK_FILE.getSize()
                ),
                new UploadFileResponse(
                        MOCK_LESSON.getFolderName(),
                        "http://localhost/downloadFile/" + MOCK_LESSON.getFolderName().replace(" ", "%20"),
                        "text/plain",
                        EXISTING_FILE.getSize()
                )
        ));

        mvc.perform(MockMvcRequestBuilders
                        .multipart("/files/uploadMultipleFiles")
                        .file(MOCK_FILE)
                        .file(EXISTING_FILE)
                        .param("lessonId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectWriter.writeValueAsString(expectedResponse)));
    }

    @Test
    void uploadFilesForNotExistingLesson() throws Exception {
        when(lessonsRepository.findById(1)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders
                        .multipart("/files/uploadMultipleFiles")
                        .file(MOCK_FILE)
                        .file(EXISTING_FILE)
                        .param("lessonId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}