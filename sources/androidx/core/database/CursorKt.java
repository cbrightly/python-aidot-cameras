package androidx.core.database;

import android.database.Cursor;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Cursor.kt */
public final class CursorKt {
    @Nullable
    public static final byte[] getBlobOrNull(@NotNull Cursor $this$getBlobOrNull, int index) {
        k.e($this$getBlobOrNull, "<this>");
        if ($this$getBlobOrNull.isNull(index)) {
            return null;
        }
        return $this$getBlobOrNull.getBlob(index);
    }

    @Nullable
    public static final Double getDoubleOrNull(@NotNull Cursor $this$getDoubleOrNull, int index) {
        k.e($this$getDoubleOrNull, "<this>");
        if ($this$getDoubleOrNull.isNull(index)) {
            return null;
        }
        return Double.valueOf($this$getDoubleOrNull.getDouble(index));
    }

    @Nullable
    public static final Float getFloatOrNull(@NotNull Cursor $this$getFloatOrNull, int index) {
        k.e($this$getFloatOrNull, "<this>");
        if ($this$getFloatOrNull.isNull(index)) {
            return null;
        }
        return Float.valueOf($this$getFloatOrNull.getFloat(index));
    }

    @Nullable
    public static final Integer getIntOrNull(@NotNull Cursor $this$getIntOrNull, int index) {
        k.e($this$getIntOrNull, "<this>");
        if ($this$getIntOrNull.isNull(index)) {
            return null;
        }
        return Integer.valueOf($this$getIntOrNull.getInt(index));
    }

    @Nullable
    public static final Long getLongOrNull(@NotNull Cursor $this$getLongOrNull, int index) {
        k.e($this$getLongOrNull, "<this>");
        if ($this$getLongOrNull.isNull(index)) {
            return null;
        }
        return Long.valueOf($this$getLongOrNull.getLong(index));
    }

    @Nullable
    public static final Short getShortOrNull(@NotNull Cursor $this$getShortOrNull, int index) {
        k.e($this$getShortOrNull, "<this>");
        if ($this$getShortOrNull.isNull(index)) {
            return null;
        }
        return Short.valueOf($this$getShortOrNull.getShort(index));
    }

    @Nullable
    public static final String getStringOrNull(@NotNull Cursor $this$getStringOrNull, int index) {
        k.e($this$getStringOrNull, "<this>");
        if ($this$getStringOrNull.isNull(index)) {
            return null;
        }
        return $this$getStringOrNull.getString(index);
    }
}
