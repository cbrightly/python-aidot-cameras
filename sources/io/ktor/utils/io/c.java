package io.ktor.utils.io;

import kotlin.coroutines.d;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.n;
import kotlin.reflect.e;
import kotlin.reflect.i;
import org.jetbrains.annotations.Nullable;

/* compiled from: ByteBufferChannel.kt */
public final /* synthetic */ class c extends n {
    public static final i INSTANCE = new c();

    c() {
    }

    public String getName() {
        return "readOp";
    }

    public e getOwner() {
        return a0.b(a.class);
    }

    public String getSignature() {
        return "getReadOp()Lkotlin/coroutines/Continuation;";
    }

    @Nullable
    public Object get(@Nullable Object receiver) {
        return ((a) receiver).readOp;
    }

    public void set(@Nullable Object receiver, @Nullable Object value) {
        ((a) receiver).readOp = (d) value;
    }
}
