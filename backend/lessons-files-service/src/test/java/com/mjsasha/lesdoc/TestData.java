package com.mjsasha.lesdoc;

import com.mjsasha.lesdoc.data.entities.Lesson;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.Arrays;

public class TestData {

    public static final Lesson MOCK_LESSON = new Lesson(1, "First lesson", "First folder", "First description");
    public static final Lesson MOCK_LESSON_WITH_EMPTY_FOLDER_NAME = new Lesson(3, "Third lesson", null, "Third description");

    public static final ArrayList<Lesson> LESSONS_LIST = new ArrayList<>(Arrays.asList(
            MOCK_LESSON,
            new Lesson(2, "Second lesson", "Second folder", "Second description"),
            MOCK_LESSON_WITH_EMPTY_FOLDER_NAME
    ));

    public static final MockMultipartFile EXISTING_FILE = new MockMultipartFile(
            "file",
            "hello.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "Hello, World!".getBytes()
    );

    public static final MockMultipartFile MOCK_FILE = new MockMultipartFile(
            "file",
            "hello_created.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "Hello, World Created!".getBytes()
    );

    public static final MockMultipartFile INVALID_MOCK_FILE = new MockMultipartFile(
            "file",
            "hello..txt",
            MediaType.TEXT_PLAIN_VALUE,
            "Hello, World!".getBytes()
    );
}
