package io.ktor.network.selector;

import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.n;
import kotlin.reflect.e;
import kotlin.reflect.i;
import org.jetbrains.annotations.Nullable;

/* compiled from: InterestSuspensionsMap.kt */
public final /* synthetic */ class f extends n {
    public static final i INSTANCE = new f();

    f() {
    }

    public String getName() {
        return "connectHandlerReference";
    }

    public e getOwner() {
        return a0.b(g.class);
    }

    public String getSignature() {
        return "getConnectHandlerReference()Lkotlinx/coroutines/CancellableContinuation;";
    }

    @Nullable
    public Object get(@Nullable Object receiver) {
        return ((g) receiver).connectHandlerReference;
    }

    public void set(@Nullable Object receiver, @Nullable Object value) {
        ((g) receiver).connectHandlerReference = (kotlinx.coroutines.n) value;
    }
}
