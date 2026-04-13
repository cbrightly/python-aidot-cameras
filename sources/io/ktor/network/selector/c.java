package io.ktor.network.selector;

import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.n;
import kotlin.reflect.e;
import kotlin.reflect.i;
import org.jetbrains.annotations.Nullable;

/* compiled from: InterestSuspensionsMap.kt */
public final /* synthetic */ class c extends n {
    public static final i INSTANCE = new c();

    c() {
    }

    public String getName() {
        return "readHandlerReference";
    }

    public e getOwner() {
        return a0.b(g.class);
    }

    public String getSignature() {
        return "getReadHandlerReference()Lkotlinx/coroutines/CancellableContinuation;";
    }

    @Nullable
    public Object get(@Nullable Object receiver) {
        return ((g) receiver).readHandlerReference;
    }

    public void set(@Nullable Object receiver, @Nullable Object value) {
        ((g) receiver).readHandlerReference = (kotlinx.coroutines.n) value;
    }
}
