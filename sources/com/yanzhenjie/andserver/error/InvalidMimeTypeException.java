package com.yanzhenjie.andserver.error;

public class InvalidMimeTypeException extends IllegalArgumentException {
    private final String mMimeType;

    public InvalidMimeTypeException(String mimeType, String message) {
        super("Invalid mime type \"" + mimeType + "\": " + message);
        this.mMimeType = mimeType;
    }

    public String getMimeType() {
        return this.mMimeType;
    }
}
