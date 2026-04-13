package io.ktor.utils.io.pool;

import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.n;
import kotlin.reflect.e;
import kotlin.reflect.i;
import org.jetbrains.annotations.Nullable;

/* compiled from: DefaultPool.kt */
public final /* synthetic */ class a extends n {
    public static final i INSTANCE = new a();

    a() {
    }

    public String getName() {
        return "top";
    }

    public e getOwner() {
        return a0.b(b.class);
    }

    public String getSignature() {
        return "getTop()J";
    }

    @Nullable
    public Object get(@Nullable Object receiver) {
        return Long.valueOf(((b) receiver).top);
    }

    public void set(@Nullable Object receiver, @Nullable Object value) {
        ((b) receiver).top = ((Number) value).longValue();
    }
}
