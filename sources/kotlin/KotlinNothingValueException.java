package kotlin;

import org.jetbrains.annotations.Nullable;

/* compiled from: ExceptionsH.kt */
public final class KotlinNothingValueException extends RuntimeException {
    public KotlinNothingValueException() {
    }

    public KotlinNothingValueException(@Nullable String message) {
        super(message);
    }

    public KotlinNothingValueException(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }

    public KotlinNothingValueException(@Nullable Throwable cause) {
        super(cause);
    }
}
