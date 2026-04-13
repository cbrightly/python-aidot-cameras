package io.ktor.util.cio;

import io.ktor.utils.io.pool.b;
import java.nio.ByteBuffer;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ByteBufferPool.kt */
public final class a extends b<ByteBuffer> {
    public a() {
        super(2048);
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: t */
    public ByteBuffer l() {
        ByteBuffer allocate = ByteBuffer.allocate(4098);
        k.b(allocate, "ByteBuffer.allocate(DEFAULT_BUFFER_SIZE)");
        return allocate;
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: s */
    public ByteBuffer g(@NotNull ByteBuffer instance) {
        k.f(instance, "instance");
        instance.clear();
        return instance;
    }
}
