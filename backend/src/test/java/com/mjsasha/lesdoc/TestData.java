package com.mjsasha.lesdoc;

import com.mjsasha.lesdoc.data.entities.Lesson;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;

public class TestData {

    public static final ArrayList<Lesson> NOT_EMPTY_LESSONS_LIST = new ArrayList<>(Arrays.asList(
            new Lesson(1, "First lesson", "First folder", "First description"),
            new Lesson(2, "Second lesson", "Second folder", "Second description"),
            new Lesson(3, "Third lesson", "Third folder", "Third description")
    ));

    public static final MultipartFile EXISTING_FILE = new MockMultipartFile(
            "file",
            "hello.docx",
            MediaType.TEXT_PLAIN_VALUE,
            "Hello, World!".getBytes()
    );

    public static final MultipartFile MOCK_FILE = new MockMultipartFile(
            "file",
            "hello_created.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "Hello, World Created!".getBytes()
    );

    public static final MultipartFile INVALID_MOCK_FILE = new MockMultipartFile(
            "file",
            "hello..txt",
            MediaType.TEXT_PLAIN_VALUE,
            "Hello, World!".getBytes()
    );
}
