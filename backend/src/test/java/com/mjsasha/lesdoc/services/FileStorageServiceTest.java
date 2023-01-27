package com.mjsasha.lesdoc.services;

import com.mjsasha.lesdoc.TestData;
import com.mjsasha.lesdoc.configs.FileStorageProperties;
import com.mjsasha.lesdoc.exceptions.FileStorageException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.apache.tomcat.util.http.fileupload.FileUtils.deleteDirectory;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileStorageServiceTest {

    private final FileStorageService fileStorageService;

    FileStorageServiceTest() {
        FileStorageProperties fileStorageProperties = Mockito.mock(FileStorageProperties.class);
        Mockito.when(fileStorageProperties.getUploadDir()).thenReturn(TestData.testDirectoryName);

        fileStorageService = new FileStorageService(fileStorageProperties);
    }

    @BeforeAll
    static void createTestDirectory() throws IOException {
        Files.createDirectories(TestData.testDirectoryLocation);
    }

    @AfterAll
    static void removeTestDirectory() throws IOException {
        File[] allContents = TestData.testDirectoryLocation.toFile().listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }

        Files.delete(TestData.testDirectoryLocation);
    }

    @Test
    void storeFileIncorrectFileName() {
        assertThrows(FileStorageException.class,
                () -> fileStorageService.storeFile(TestData.invalidMockFile, "not matter"));
    }

    @Test
    void directoryCreating() {
        String subdirectoryName = "sub-directory";

        fileStorageService.createDirectory(subdirectoryName);

        assertTrue(Files.exists(TestData.testDirectoryLocation.resolve(subdirectoryName)));
    }

    @Test
    void directoryCantBeCreated() {
        String incorrectDirectoryName = "!@#$%^&*()";

        assertThrows(FileStorageException.class,
                () -> fileStorageService.createDirectory(incorrectDirectoryName));
    }
}