package io.ktor.utils.io;

import kotlin.coroutines.d;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.n;
import kotlin.reflect.i;
import org.jetbrains.annotations.Nullable;

/* compiled from: ByteBufferChannel.kt */
public final /* synthetic */ class e extends n {
    public static final i INSTANCE = new e();

    e() {
    }

    public String getName() {
        return "writeOp";
    }

    public kotlin.reflect.e getOwner() {
        return a0.b(a.class);
    }

    public String getSignature() {
        return "getWriteOp()Lkotlin/coroutines/Continuation;";
    }

    @Nullable
    public Object get(@Nullable Object receiver) {
        return ((a) receiver).writeOp;
    }

    public void set(@Nullable Object receiver, @Nullable Object value) {
        ((a) receiver).writeOp = (d) value;
    }
}
