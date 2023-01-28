package com.mjsasha.lesdoc.interfaces;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.core.io.Resource;

import java.io.IOException;

public interface FileConverter {

    Resource convertToPdf(Resource file) throws JSONException, IOException;
}
