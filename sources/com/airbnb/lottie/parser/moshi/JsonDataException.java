package com.airbnb.lottie.parser.moshi;

import androidx.annotation.Nullable;

public final class JsonDataException extends RuntimeException {
    JsonDataException(@Nullable String message) {
        super(message);
    }
}
