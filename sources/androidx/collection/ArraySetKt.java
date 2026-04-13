package androidx.collection;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ArraySet.kt */
public final class ArraySetKt {
    @NotNull
    public static final <T> ArraySet<T> arraySetOf() {
        return new ArraySet<>();
    }

    @NotNull
    public static final <T> ArraySet<T> arraySetOf(@NotNull T... values) {
        k.f(values, "values");
        ArraySet set = new ArraySet(values.length);
        for (Object value : values) {
            set.add(value);
        }
        return set;
    }
}
