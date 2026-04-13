package androidx.core.os;

import androidx.annotation.Nullable;
import androidx.core.util.ObjectsCompat;

public class OperationCanceledException extends RuntimeException {
    public OperationCanceledException() {
        this((String) null);
    }

    public OperationCanceledException(@Nullable String message) {
        super(ObjectsCompat.toString(message, "The operation has been canceled."));
    }
}
