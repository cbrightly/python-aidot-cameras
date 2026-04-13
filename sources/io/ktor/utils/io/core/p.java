package io.ktor.utils.io.core;

import io.ktor.utils.io.core.internal.a;
import io.ktor.utils.io.pool.d;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Builder.kt */
public abstract class p extends o {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public p(@NotNull d<a> pool) {
        super(pool);
        k.f(pool, "pool");
    }
}
