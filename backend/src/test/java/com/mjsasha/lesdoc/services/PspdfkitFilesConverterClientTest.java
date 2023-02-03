package com.mjsasha.lesdoc.services;

import com.mjsasha.lesdoc.TestData;
import com.mjsasha.lesdoc.configs.ExternalApiProperties;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.apache.tomcat.util.http.fileupload.FileUtils.deleteDirectory;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PspdfkitFilesConverterClientTest {

    private static final String NOT_EMPTY_DIRECTORY_NAME = "not-empty-dir";
    private static final String TEST_DIRECTORY_NAME = "test-dir";
    private static final Path TEST_DIRECTORY_LOCATION = Paths.get(TEST_DIRECTORY_NAME).toAbsolutePath().normalize();
    private final ExternalApiProperties externalApiProperties = Mockito.mock(ExternalApiProperties.class);
    private final FileStorageService fileStorageService = Mockito.mock(FileStorageService.class);
    private final PspdfkitFilesConverterClient pspdfkitFilesConverterClient = new PspdfkitFilesConverterClient(externalApiProperties, fileStorageService);

    @BeforeAll
    static void createTestDirectory() throws IOException {
        Files.createDirectories(TEST_DIRECTORY_LOCATION);

        Files.createDirectory(TEST_DIRECTORY_LOCATION.resolve(NOT_EMPTY_DIRECTORY_NAME));
        Files.copy(TestData.EXISTING_FILE.getInputStream(),
                TEST_DIRECTORY_LOCATION
                        .resolve(NOT_EMPTY_DIRECTORY_NAME)
                        .resolve(TestData.EXISTING_FILE.getOriginalFilename()));
    }

    @AfterAll
    static void removeTestDirectory() throws IOException {
        File[] allContents = TEST_DIRECTORY_LOCATION.toFile().listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }

        Files.delete(TEST_DIRECTORY_LOCATION);
    }

    @Test
    void convertDocxToPdf() throws IOException, JSONException {
        Mockito.when(externalApiProperties.getApiKey()).thenReturn("pdf_live_8FsHgDr733zVimwUDOBy1KUjprqiPGVhm6yrNn0urAF");

        var result = pspdfkitFilesConverterClient.convertToPdf(new UrlResource(TEST_DIRECTORY_LOCATION
                .resolve(NOT_EMPTY_DIRECTORY_NAME)
                .resolve(TestData.EXISTING_FILE.getOriginalFilename()).toUri()));

        assertTrue(result.getFilename().contains(".pdf"));
    }
}