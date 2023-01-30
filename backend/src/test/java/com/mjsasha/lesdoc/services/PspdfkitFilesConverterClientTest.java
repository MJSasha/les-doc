package com.mjsasha.lesdoc.services;

import com.mjsasha.lesdoc.TestData;
import com.mjsasha.lesdoc.configs.ExternalApiProperties;
import com.mjsasha.lesdoc.data.definitions.FormatsForConvertingToPdf;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PspdfkitFilesConverterClientTest {

    private final ExternalApiProperties externalApiProperties = Mockito.mock(ExternalApiProperties.class);
    private final PspdfkitFilesConverterClient pspdfkitFilesConverterClient = new PspdfkitFilesConverterClient(externalApiProperties);

    @Test
    void convertDocxToPdf() throws IOException, JSONException {
        Mockito.when(externalApiProperties.getApiKey()).thenReturn("pdf_live_8FsHgDr733zVimwUDOBy1KUjprqiPGVhm6yrNn0urAF");

        System.out.println(FormatsForConvertingToPdf.DOCX.getPostfix());

        var result = pspdfkitFilesConverterClient.convertToPdf(TestData.MOCK_FILE.getResource());

        assertEquals(result.getFile().getName(), TestData.EXISTING_FILE.getOriginalFilename());
    }
}