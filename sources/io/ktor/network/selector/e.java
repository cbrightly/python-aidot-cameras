package io.ktor.network.selector;

import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.n;
import kotlin.reflect.i;
import org.jetbrains.annotations.Nullable;

/* compiled from: InterestSuspensionsMap.kt */
public final /* synthetic */ class e extends n {
    public static final i INSTANCE = new e();

    e() {
    }

    public String getName() {
        return "acceptHandlerReference";
    }

    public kotlin.reflect.e getOwner() {
        return a0.b(g.class);
    }

    public String getSignature() {
        return "getAcceptHandlerReference()Lkotlinx/coroutines/CancellableContinuation;";
    }

    @Nullable
    public Object get(@Nullable Object receiver) {
        return ((g) receiver).acceptHandlerReference;
    }

    public void set(@Nullable Object receiver, @Nullable Object value) {
        ((g) receiver).acceptHandlerReference = (kotlinx.coroutines.n) value;
    }
}
