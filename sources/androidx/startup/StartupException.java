package androidx.startup;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public final class StartupException extends RuntimeException {
    public StartupException(@NonNull String message) {
        super(message);
    }

    public StartupException(@NonNull Throwable throwable) {
        super(throwable);
    }

    public StartupException(@NonNull String message, @NonNull Throwable throwable) {
        super(message, throwable);
    }
}
