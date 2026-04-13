package io.ktor.util.cio;

import io.ktor.utils.io.pool.d;
import java.nio.ByteBuffer;
import org.jetbrains.annotations.NotNull;

/* compiled from: ByteBufferPool.kt */
public final class b {
    @NotNull
    private static final d<ByteBuffer> a = new a();

    @NotNull
    public static final d<ByteBuffer> a() {
        return a;
    }
}
