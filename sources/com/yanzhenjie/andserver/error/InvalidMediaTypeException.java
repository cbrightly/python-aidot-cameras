package com.yanzhenjie.andserver.error;

public class InvalidMediaTypeException extends IllegalArgumentException {
    private String mMediaType;

    public InvalidMediaTypeException(String mediaType, String message) {
        super("Invalid media type \"" + mediaType + "\": " + message);
        this.mMediaType = mediaType;
    }

    public InvalidMediaTypeException(InvalidMimeTypeException ex) {
        super(ex.getMessage(), ex);
        this.mMediaType = ex.getMimeType();
    }

    public String getMediaType() {
        return this.mMediaType;
    }
}
