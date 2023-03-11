package com.mjsasha.lesdoc.services;

import com.mjsasha.lesdoc.configs.FileStorageProperties;
import com.mjsasha.lesdoc.exceptions.FileNotFoundException;
import com.mjsasha.lesdoc.exceptions.FileStorageException;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.mjsasha.lesdoc.TestData.*;
import static org.apache.tomcat.util.http.fileupload.FileUtils.deleteDirectory;
import static org.junit.jupiter.api.Assertions.*;

class FileStorageServiceTest {

    private static final String NOT_EMPTY_DIRECTORY_NAME = "not-empty-dir";
    private static final String TEST_DIRECTORY_NAME = "test-dir";
    private static final Path TEST_DIRECTORY_LOCATION = Paths.get(TEST_DIRECTORY_NAME).toAbsolutePath().normalize();

    private final FileStorageService fileStorageService;

    FileStorageServiceTest() {
        FileStorageProperties fileStorageProperties = Mockito.mock(FileStorageProperties.class);
        Mockito.when(fileStorageProperties.getUploadDir()).thenReturn(TEST_DIRECTORY_NAME);

        fileStorageService = new FileStorageService(fileStorageProperties);
    }

    @BeforeAll
    static void createTestDirectory() throws IOException {
        Files.createDirectories(TEST_DIRECTORY_LOCATION);

        Files.createDirectory(TEST_DIRECTORY_LOCATION.resolve(NOT_EMPTY_DIRECTORY_NAME));
        createTestFile();
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

    static void createTestFile() throws IOException {
        Files.copy(EXISTING_FILE.getInputStream(),
                TEST_DIRECTORY_LOCATION
                        .resolve(NOT_EMPTY_DIRECTORY_NAME)
                        .resolve(EXISTING_FILE.getOriginalFilename()));
    }

    @Test
    void storeFileWithIncorrectName() {
        assertThrows(FileStorageException.class,
                () -> fileStorageService.storeFile(INVALID_MOCK_FILE, "not-matter"));
    }

    @Test
    void storeFileToNotExistingFolder() {
        assertThrows(FileStorageException.class,
                () -> fileStorageService.storeFile(MOCK_FILE, "not-exist"));
    }

    @Test
    void storeFileToExistingFolder() {
        String storedFileName = fileStorageService.storeFile(MOCK_FILE, NOT_EMPTY_DIRECTORY_NAME);

        assertEquals(storedFileName, MOCK_FILE.getOriginalFilename());
        assertTrue(Files.exists(TEST_DIRECTORY_LOCATION
                .resolve(NOT_EMPTY_DIRECTORY_NAME)
                .resolve(MOCK_FILE.getOriginalFilename())));
    }

    @Test
    void loadFileFromNotExistingFolder() {
        assertThrows(FileNotFoundException.class,
                () -> fileStorageService.loadFileAsResource("not-existing-txt", "not-existing-folder-name"));
    }

    @Test
    void loadNotExistingResource() {
        assertThrows(FileNotFoundException.class,
                () -> fileStorageService.loadFileAsResource("not-existing-txt", NOT_EMPTY_DIRECTORY_NAME));
    }

    @Test
    void loadExistingResource() throws IOException {
        Resource resource = fileStorageService.loadFileAsResource(EXISTING_FILE.getOriginalFilename(), NOT_EMPTY_DIRECTORY_NAME);

        assertEquals(resource.getFile().getName(), EXISTING_FILE.getOriginalFilename());
    }

    @Test
    void storeFileIncorrectFileName() {
        assertThrows(FileStorageException.class,
                () -> fileStorageService.storeFile(INVALID_MOCK_FILE, "not matter"));
    }

    @Test
    void getFilenamesFromNotExistingFolder() {
        assertThrows(FileStorageException.class,
                () -> fileStorageService.getAllFilesNames("not-existing-folder-name"));
    }

    @Test
    void getFilenamesFromEmptyFolder() throws IOException {
        String notEmptyDirectoryName = "empty-dir";
        Files.createDirectory(TEST_DIRECTORY_LOCATION.resolve(notEmptyDirectoryName));

        assertThrows(FileNotFoundException.class,
                () -> fileStorageService.getAllFilesNames(notEmptyDirectoryName));
    }

    @Test
    void getFilenamesFromNotEmptyFolder() throws IOException {
        String[] allFilesNames = fileStorageService.getAllFilesNames(NOT_EMPTY_DIRECTORY_NAME);

        assertTrue(Arrays.stream(allFilesNames).anyMatch(name -> name.equals(EXISTING_FILE.getOriginalFilename())));
    }

    @Test
    void directoryCreating() {
        String subdirectoryName = "sub-directory";

        fileStorageService.createDirectory(subdirectoryName);

        assertTrue(Files.exists(TEST_DIRECTORY_LOCATION.resolve(subdirectoryName)));
    }

    @Test
    @Ignore
    void directoryCantBeCreated() {
        String incorrectDirectoryName = "!@#$%^&*()";

        assertThrows(FileStorageException.class,
                () -> fileStorageService.createDirectory(incorrectDirectoryName));
    }

    @Test
    void removeExistingDirectory() throws IOException {
        String subdirectoryName = "sub-directory";
        Files.createDirectories(TEST_DIRECTORY_LOCATION.resolve(subdirectoryName));

        fileStorageService.removeDirectory(subdirectoryName);

        assertFalse(Files.exists(TEST_DIRECTORY_LOCATION.resolve(subdirectoryName)));
    }

    @Test
    void removeNotExistingDirectory() {
        assertThrows(FileStorageException.class,
                () -> fileStorageService.removeDirectory("not-existing-directory"));
    }

    @Test
    void removeNotExistingFile() {
        assertThrows(FileStorageException.class,
                () -> fileStorageService.removeFile(NOT_EMPTY_DIRECTORY_NAME, "not-existing-file"));
    }

    @Test
    void removeFromNotExistingDirectory() {
        assertThrows(FileStorageException.class,
                () -> fileStorageService.removeFile("not-existing-directory", "not matter"));
    }

    @Test
    void removeExistingFile() throws IOException {
        fileStorageService.removeFile(EXISTING_FILE.getOriginalFilename(), NOT_EMPTY_DIRECTORY_NAME);

        assertTrue(Files.notExists(TEST_DIRECTORY_LOCATION.resolve(NOT_EMPTY_DIRECTORY_NAME).resolve(EXISTING_FILE.getOriginalFilename())));

        createTestFile();
    }
}