package com.mjsasha.lesdoc.services;

import com.mjsasha.lesdoc.configs.ExternalApiProperties;
import com.mjsasha.lesdoc.data.definitions.FormatsForConvertingToPdf;
import com.mjsasha.lesdoc.interfaces.FileConverter;
import com.mjsasha.lesdoc.utils.Utils;
import okhttp3.*;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class PspdfkitFilesConverterClient implements FileConverter {

    private static final String DIRECTORY_FOR_CONVERTED_FILES = "converted";
    private final ExternalApiProperties externalApiProperties;
    private final FileStorageService fileStorageService;

    public PspdfkitFilesConverterClient(ExternalApiProperties externalApiProperties, FileStorageService fileStorageService) {
        this.externalApiProperties = externalApiProperties;
        this.fileStorageService = fileStorageService;

        fileStorageService.createDirectory(DIRECTORY_FOR_CONVERTED_FILES);
    }

    public Resource convertToPdf(Resource resource) throws IOException, JSONException {

        var fileExtension = Utils.getFileExtension(resource.getFilename());

        if (fileExtension.isEmpty()) throw new FileNotFoundException("Incorrect file format");
        FormatsForConvertingToPdf fileFormat = FormatsForConvertingToPdf.valueOf(fileExtension.get().toUpperCase());

        final RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                        resource.getFilename(),
                        resource.getFilename(),
                        RequestBody.create(
                                resource.getFile(),
                                MediaType.parse(fileFormat.getPspdfkitMediaTypeName())
                        )
                )
                .addFormDataPart(
                        "instructions",
                        new JSONObject()
                                .put("parts", new JSONArray()
                                        .put(new JSONObject()
                                                .put("file", resource.getFilename())
                                        )
                                ).toString()
                )
                .build();

        final Request request = new Request.Builder()
                .url("https://api.pspdfkit.com/build")
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + externalApiProperties.getApiKey())
                .build();

        final OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .build();

        final Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            var fileName = Utils.getFileNameWithOutExtension(resource.getFilename()) + ".pdf";
            fileStorageService.storeFile(response.body().byteStream(), DIRECTORY_FOR_CONVERTED_FILES + "/" + fileName);
            return fileStorageService.loadFileAsResource(
                    Utils.getFileNameWithOutExtension(resource.getFilename()) + ".pdf",
                    DIRECTORY_FOR_CONVERTED_FILES
            );
        } else {
            throw new IOException(response.body().string());
        }
    }
}
