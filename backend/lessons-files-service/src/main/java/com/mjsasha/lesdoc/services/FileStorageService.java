package com.mjsasha.lesdoc.services;

import com.mjsasha.lesdoc.configs.FileStorageProperties;
import com.mjsasha.lesdoc.exceptions.FileNotFoundException;
import com.mjsasha.lesdoc.exceptions.FileStorageException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file, String folderName) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            if (fileName.contains(".."))
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);

            Path targetLocation = Paths.get(fileStorageLocation.toString())
                    .toAbsolutePath()
                    .normalize()
                    .resolve(folderName)
                    .resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName, String folderName) {
        try {
            Path filePath = fileStorageLocation.resolve(folderName).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) return resource;
            else throw new FileNotFoundException("File not found " + fileName);
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }

    public String[] getAllFilesNames(String folderName) {
        Path filePath = fileStorageLocation.resolve(folderName);
        File[] files = filePath.toFile().listFiles();

        if (files == null) throw new FileStorageException("Directory not found");
        if (files.length == 0) throw new FileNotFoundException("No files in folder");

        return Arrays.stream(files).map(File::getName).toArray(String[]::new);
    }

    public void createDirectory(String name) {
        try {
            Files.createDirectories(fileStorageLocation.resolve(name));
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public void removeDirectory(String directoryName) {
        try {
            File[] allContents = fileStorageLocation.resolve(directoryName).toFile().listFiles();
            if (allContents != null) {
                for (File file : allContents) {
                    file.delete();
                }
            }

            Files.delete(fileStorageLocation.resolve(directoryName));
        } catch (Exception ex) {
            throw new FileStorageException("Could note remove directory", ex);
        }
    }

    public void removeFile(String fileName, String folderName) {
        try {
            Files.delete(fileStorageLocation.resolve(folderName).resolve(fileName));
        } catch (IOException ex) {
            throw new FileStorageException("Could note remove file " + fileName, ex);
        }
    }
}
