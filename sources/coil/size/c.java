package coil.size;

import kotlin.coroutines.d;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RealSizeResolver.kt */
public final class c implements f {
    @NotNull
    private final Size c;

    public c(@NotNull Size size) {
        k.e(size, "size");
        this.c = size;
    }

    @Nullable
    public Object b(@NotNull d<? super Size> $completion) {
        return this.c;
    }

    public boolean equals(@Nullable Object other) {
        return this == other || ((other instanceof c) && k.a(this.c, ((c) other).c));
    }

    public int hashCode() {
        return this.c.hashCode();
    }

    @NotNull
    public String toString() {
        return "RealSizeResolver(size=" + this.c + ')';
    }
}
