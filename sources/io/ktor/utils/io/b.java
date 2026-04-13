package io.ktor.utils.io;

import io.ktor.utils.io.a;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.n;
import kotlin.reflect.e;
import kotlin.reflect.i;
import org.jetbrains.annotations.Nullable;

/* compiled from: ByteBufferChannel.kt */
public final /* synthetic */ class b extends n {
    public static final i INSTANCE = new b();

    b() {
    }

    public String getName() {
        return "closed";
    }

    public e getOwner() {
        return a0.b(a.class);
    }

    public String getSignature() {
        return "getClosed()Lio/ktor/utils/io/ByteBufferChannel$ClosedElement;";
    }

    @Nullable
    public Object get(@Nullable Object receiver) {
        return ((a) receiver).closed;
    }

    public void set(@Nullable Object receiver, @Nullable Object value) {
        ((a) receiver).closed = (a.C0279a) value;
    }
}
