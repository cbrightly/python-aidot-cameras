package io.ktor.utils.io.pool;

import io.ktor.utils.io.pool.d;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Pool.kt */
public abstract class c<T> implements d<T> {
    public void close() {
        d.a.a(this);
    }

    public void N0(@NotNull T instance) {
        k.f(instance, "instance");
    }

    public void dispose() {
    }
}
