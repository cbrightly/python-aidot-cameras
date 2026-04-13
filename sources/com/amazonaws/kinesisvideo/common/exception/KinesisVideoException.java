package com.amazonaws.kinesisvideo.common.exception;

import androidx.annotation.NonNull;

public class KinesisVideoException extends Exception {
    public KinesisVideoException() {
    }

    public KinesisVideoException(@NonNull String message) {
        super(message);
    }

    public KinesisVideoException(@NonNull String message, @NonNull Throwable cause) {
        super(message, cause);
    }

    public KinesisVideoException(@NonNull Throwable cause) {
        super(cause);
    }
}
