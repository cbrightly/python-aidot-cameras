package kotlin;

import org.jetbrains.annotations.Nullable;

/* compiled from: UninitializedPropertyAccessException.kt */
public final class UninitializedPropertyAccessException extends RuntimeException {
    public UninitializedPropertyAccessException() {
    }

    public UninitializedPropertyAccessException(@Nullable String message) {
        super(message);
    }

    public UninitializedPropertyAccessException(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }

    public UninitializedPropertyAccessException(@Nullable Throwable cause) {
        super(cause);
    }
}
