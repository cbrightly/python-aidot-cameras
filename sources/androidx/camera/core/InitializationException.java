package androidx.camera.core;

import androidx.annotation.Nullable;

public class InitializationException extends Exception {
    public InitializationException(@Nullable String message) {
        super(message);
    }

    public InitializationException(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }

    public InitializationException(@Nullable Throwable cause) {
        super(cause);
    }
}
