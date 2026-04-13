package io.ktor.utils.io.internal;

import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.n;
import kotlin.reflect.e;
import kotlin.reflect.i;
import org.jetbrains.annotations.Nullable;

/* compiled from: RingBufferCapacity.kt */
public final /* synthetic */ class j extends n {
    public static final i INSTANCE = new j();

    j() {
    }

    public String getName() {
        return "pendingToFlush";
    }

    public e getOwner() {
        return a0.b(k.class);
    }

    public String getSignature() {
        return "getPendingToFlush()I";
    }

    @Nullable
    public Object get(@Nullable Object receiver) {
        return Integer.valueOf(((k) receiver).pendingToFlush);
    }

    public void set(@Nullable Object receiver, @Nullable Object value) {
        ((k) receiver).pendingToFlush = ((Number) value).intValue();
    }
}
