package com.mjsasha.lesdoc.services;

import com.mjsasha.lesdoc.configs.ExternalApiProperties;
import com.mjsasha.lesdoc.interfaces.FileConverter;
import okhttp3.*;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class PspdfkitFilesConverterClient implements FileConverter {

    private final ExternalApiProperties externalApiProperties;

    public PspdfkitFilesConverterClient(ExternalApiProperties externalApiProperties) {
        this.externalApiProperties = externalApiProperties;
    }

    public Resource convertToPdf(Resource resource) throws JSONException, IOException {

        final RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                        resource.getFilename(),
                        resource.getFile().getName(),
                        RequestBody.create(
                                resource.getFile(),
                                MediaType.parse("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
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
            Files.copy(
                    response.body().byteStream(),
                    FileSystems.getDefault().getPath("result.pdf"),
                    StandardCopyOption.REPLACE_EXISTING
            );
            return new ByteArrayResource(response.body().bytes());
        } else {
            throw new IOException(response.body().string());
        }
    }
}
