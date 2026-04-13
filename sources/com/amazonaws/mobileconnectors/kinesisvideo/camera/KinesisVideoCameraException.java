package com.amazonaws.mobileconnectors.kinesisvideo.camera;

public class KinesisVideoCameraException extends RuntimeException {
    public KinesisVideoCameraException() {
    }

    public KinesisVideoCameraException(String message) {
        super(message);
    }

    public KinesisVideoCameraException(Throwable cause) {
        super(cause);
    }

    public KinesisVideoCameraException(String message, Throwable cause) {
        super(message, cause);
    }
}
