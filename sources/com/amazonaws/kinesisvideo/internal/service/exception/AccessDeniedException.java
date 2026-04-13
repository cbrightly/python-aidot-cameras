package com.amazonaws.kinesisvideo.internal.service.exception;

import androidx.annotation.NonNull;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(@NonNull String message) {
        super(message);
    }
}
