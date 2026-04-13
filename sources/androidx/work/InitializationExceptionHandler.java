package androidx.work;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public interface InitializationExceptionHandler {
    void handleException(@NonNull Throwable th);
}
