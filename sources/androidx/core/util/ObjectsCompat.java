package androidx.core.util;

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

public class ObjectsCompat {
    private ObjectsCompat() {
    }

    public static boolean equals(@Nullable Object a, @Nullable Object b) {
        if (Build.VERSION.SDK_INT >= 19) {
            return Objects.equals(a, b);
        }
        return a == b || (a != null && a.equals(b));
    }

    public static int hashCode(@Nullable Object o) {
        if (o != null) {
            return o.hashCode();
        }
        return 0;
    }

    public static int hash(@Nullable Object... values) {
        if (Build.VERSION.SDK_INT >= 19) {
            return Objects.hash(values);
        }
        return Arrays.hashCode(values);
    }

    @Nullable
    public static String toString(@Nullable Object o, @Nullable String nullDefault) {
        return o != null ? o.toString() : nullDefault;
    }

    @NonNull
    public static <T> T requireNonNull(@Nullable T obj) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException();
    }

    @NonNull
    public static <T> T requireNonNull(@Nullable T obj, @NonNull String message) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException(message);
    }
}
