package com.mjsasha.lesdoc;

import com.mjsasha.lesdoc.data.entities.Lesson;

import java.util.ArrayList;
import java.util.Arrays;

public class TestData {

    public static ArrayList<Lesson> NotEmptyLessonsList = new ArrayList<>(Arrays.asList(
            new Lesson(1, "First lesson", "First folder", "First description"),
            new Lesson(2, "Second lesson", "Second folder", "Second description"),
            new Lesson(3, "Third lesson", "Third folder", "Third description")
    ));
}
