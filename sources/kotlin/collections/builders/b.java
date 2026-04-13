package kotlin.collections.builders;

import java.util.Arrays;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ListBuilder.kt */
public final class b {
    @NotNull
    public static final <E> E[] a(int size) {
        if (size >= 0) {
            return new Object[size];
        }
        throw new IllegalArgumentException("capacity must be non-negative.".toString());
    }

    @NotNull
    public static final <T> T[] b(@NotNull T[] $this$copyOfUninitializedElements, int newSize) {
        k.e($this$copyOfUninitializedElements, "$this$copyOfUninitializedElements");
        T[] copyOf = Arrays.copyOf($this$copyOfUninitializedElements, newSize);
        k.d(copyOf, "java.util.Arrays.copyOf(this, newSize)");
        if (copyOf != null) {
            return copyOf;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    public static final <E> void c(@NotNull E[] $this$resetAt, int index) {
        k.e($this$resetAt, "$this$resetAt");
        $this$resetAt[index] = null;
    }

    public static final <E> void d(@NotNull E[] $this$resetRange, int fromIndex, int toIndex) {
        k.e($this$resetRange, "$this$resetRange");
        for (int index = fromIndex; index < toIndex; index++) {
            c($this$resetRange, index);
        }
    }
}
