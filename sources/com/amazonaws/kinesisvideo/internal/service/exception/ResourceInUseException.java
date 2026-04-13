package com.amazonaws.kinesisvideo.internal.service.exception;

import androidx.annotation.NonNull;

public class ResourceInUseException extends RuntimeException {
    public ResourceInUseException(@NonNull String message) {
        super(message);
    }
}
