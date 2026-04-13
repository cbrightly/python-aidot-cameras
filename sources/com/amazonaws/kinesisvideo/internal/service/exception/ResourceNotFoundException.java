package com.amazonaws.kinesisvideo.internal.service.exception;

import androidx.annotation.NonNull;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(@NonNull String message) {
        super(message);
    }
}
