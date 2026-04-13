package io.ktor.network.util;

import io.ktor.utils.io.pool.b;
import java.nio.ByteBuffer;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Pools.kt */
public final class a extends b<ByteBuffer> {
    private final int p0;

    public a(int bufferSize, int size) {
        super(size);
        this.p0 = bufferSize;
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: t */
    public ByteBuffer l() {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(this.p0);
        k.b(allocateDirect, "java.nio.ByteBuffer.allocateDirect(bufferSize)");
        return allocateDirect;
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: s */
    public ByteBuffer g(@NotNull ByteBuffer instance) {
        k.f(instance, "instance");
        instance.clear();
        return instance;
    }

    /* access modifiers changed from: protected */
    /* renamed from: u */
    public void r(@NotNull ByteBuffer instance) {
        k.f(instance, "instance");
        if (instance.isDirect()) {
            if (!(instance.capacity() == this.p0)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            return;
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }
}
