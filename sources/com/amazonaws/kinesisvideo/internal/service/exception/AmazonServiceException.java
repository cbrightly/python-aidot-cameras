package com.amazonaws.kinesisvideo.internal.service.exception;

import androidx.annotation.NonNull;

public class AmazonServiceException extends RuntimeException {
    public AmazonServiceException(@NonNull String message) {
        super(message);
    }
}
