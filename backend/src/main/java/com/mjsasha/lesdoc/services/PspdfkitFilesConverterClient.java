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
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PspdfkitFilesConverterClient implements FileConverter {

    private final ExternalApiProperties externalApiProperties;

    public PspdfkitFilesConverterClient(ExternalApiProperties externalApiProperties) {
        this.externalApiProperties = externalApiProperties;
    }

    public Resource convertToPdf(Resource resource) throws JSONException, IOException {

        var fileExtension = Utils.getFileExtension(resource.getFilename());

        if (fileExtension.isEmpty() || Files.notExists(Path.of(resource.getFilename())))
            throw new FileNotFoundException("Incorrect file format");
        FormatsForConvertingToPdf fileFormat = FormatsForConvertingToPdf.valueOf(fileExtension.get());

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

//        if (response.isSuccessful()) {
//            Files.copy(
//                    response.body().byteStream(),
//                    FileSystems.getDefault().getPath("result.pdf"),
//                    StandardCopyOption.REPLACE_EXISTING
//            );
//            return new ByteArrayResource(response.body().bytes());
//        } else {
//            throw new IOException(response.body().string());
//        }
        return null;
    }
}
