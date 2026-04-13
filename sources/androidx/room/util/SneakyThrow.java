package androidx.room.util;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class SneakyThrow {
    public static void reThrow(@NonNull Exception e) {
        sneakyThrow(e);
    }

    private static <E extends Throwable> void sneakyThrow(@NonNull Throwable e) {
        throw e;
    }

    private SneakyThrow() {
    }
}
