package com.squareup.moshi;

import javax.annotation.Nullable;

public final class JsonDataException extends RuntimeException {
    public JsonDataException() {
    }

    public JsonDataException(@Nullable String message) {
        super(message);
    }

    public JsonDataException(@Nullable Throwable cause) {
        super(cause);
    }

    public JsonDataException(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }
}
