package io.ktor.utils.io.internal;

import java.nio.ByteBuffer;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ReadWriteBufferState.kt */
public final class g {
    @NotNull
    private static final ByteBuffer a;
    @NotNull
    private static final k b = new k(0);

    static {
        ByteBuffer allocate = ByteBuffer.allocate(0);
        k.b(allocate, "ByteBuffer.allocate(0)");
        a = allocate;
    }

    @NotNull
    public static final ByteBuffer a() {
        return a;
    }

    @NotNull
    public static final k b() {
        return b;
    }
}
