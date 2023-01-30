package com.mjsasha.lesdoc.data.definitions;

public enum FormatsForConvertingToPdf {

    // Office
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    DOC("application/msword"),
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    XLS("application/vnd.ms-excel"),
    PPTX("application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    PPT("application/vnd.ms-powerpoint"),
    // Images
    PNG("image/png"),
    JPG("image/jpeg"),
    TIFF("image/tiff"),
    // HTML
    HTML("text/html");

    private String pspdfkitMediaTypeName;

    FormatsForConvertingToPdf(String pspdfkitMediaTypeName) {
        this.pspdfkitMediaTypeName = pspdfkitMediaTypeName;
    }

    public String getPspdfkitMediaTypeName() {
        return pspdfkitMediaTypeName;
    }

    public String getPostfix() {
        return "." + toString().toLowerCase();
    }
}
