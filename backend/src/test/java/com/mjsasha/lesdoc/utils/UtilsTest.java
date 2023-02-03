package com.mjsasha.lesdoc.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilsTest {

    @Test
    public void getFileExtension() {
        assertEquals("png", Utils.getFileExtension("C:\\uploaded-files\\test\\cat.png").get());
        assertEquals("docx", Utils.getFileExtension("C:\\uploaded-files\\test\\test.docx").get());
        assertEquals("pdf", Utils.getFileExtension("C:\\uploaded-files\\converters\\test.pdf").get());
    }

    @Test
    public void getFileNameWithOutExtension() {
        assertEquals("test", Utils.getFileNameWithOutExtension("test.xml"));
        assertEquals("test.2", Utils.getFileNameWithOutExtension("test.2.xml"));
    }
}