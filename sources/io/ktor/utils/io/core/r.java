package io.ktor.utils.io.core;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import io.ktor.utils.io.core.internal.a;
import io.ktor.utils.io.pool.d;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Packet.kt */
public abstract class r extends a {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public r(@NotNull a head, long remaining, @NotNull d<a> pool) {
        super(head, remaining, pool);
        k.f(head, CacheEntity.HEAD);
        k.f(pool, "pool");
    }
}
