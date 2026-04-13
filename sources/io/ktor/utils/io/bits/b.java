package io.ktor.utils.io.bits;

import java.nio.ByteBuffer;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: MemoryFactoryJvm.kt */
public final class b implements a {
    public static final b a = new b();

    private b() {
    }

    @NotNull
    public ByteBuffer b(int size) {
        ByteBuffer allocate = ByteBuffer.allocate(size);
        k.b(allocate, "ByteBuffer.allocate(size)");
        return c.b(allocate);
    }

    public void a(@NotNull ByteBuffer instance) {
        k.f(instance, "instance");
    }
}
