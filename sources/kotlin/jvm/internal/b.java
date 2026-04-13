package kotlin.jvm.internal;

import java.util.Iterator;
import org.jetbrains.annotations.NotNull;

/* compiled from: ArrayIterator.kt */
public final class b {
    @NotNull
    public static final <T> Iterator<T> a(@NotNull T[] array) {
        k.e(array, "array");
        return new a(array);
    }
}
