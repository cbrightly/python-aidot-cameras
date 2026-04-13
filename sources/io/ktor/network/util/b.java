package io.ktor.network.util;

import io.ktor.utils.io.pool.d;
import java.nio.ByteBuffer;
import org.jetbrains.annotations.NotNull;

/* compiled from: Pools.kt */
public final class b {
    @NotNull
    private static final ThreadGroup a = new ThreadGroup("io-pool-group");
    private static final int b = 4096;
    @NotNull
    private static final d<ByteBuffer> c = new a(4096, 4096);
    @NotNull
    private static final d<ByteBuffer> d = new a(65535, 2048);

    @NotNull
    public static final d<ByteBuffer> a() {
        return c;
    }
}
