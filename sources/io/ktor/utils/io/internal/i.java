package io.ktor.utils.io.internal;

import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.n;
import kotlin.reflect.e;
import org.jetbrains.annotations.Nullable;

/* compiled from: RingBufferCapacity.kt */
public final /* synthetic */ class i extends n {
    public static final kotlin.reflect.i INSTANCE = new i();

    i() {
    }

    public String getName() {
        return "availableForWrite";
    }

    public e getOwner() {
        return a0.b(k.class);
    }

    public String getSignature() {
        return "getAvailableForWrite()I";
    }

    @Nullable
    public Object get(@Nullable Object receiver) {
        return Integer.valueOf(((k) receiver).availableForWrite);
    }

    public void set(@Nullable Object receiver, @Nullable Object value) {
        ((k) receiver).availableForWrite = ((Number) value).intValue();
    }
}
