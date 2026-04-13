package com.airbnb.lottie.parser.moshi;

import androidx.annotation.Nullable;
import java.io.IOException;

public final class JsonEncodingException extends IOException {
    JsonEncodingException(@Nullable String message) {
        super(message);
    }
}
