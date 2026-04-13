package io.ktor.utils.io.pool;

import java.io.Closeable;
import org.jetbrains.annotations.NotNull;

/* compiled from: Pool.kt */
public interface d<T> extends Closeable {
    void N0(@NotNull T t);

    void dispose();

    @NotNull
    T p0();

    /* compiled from: Pool.kt */
    public static final class a {
        public static <T> void a(d<T> $this) {
            $this.dispose();
        }
    }
}
