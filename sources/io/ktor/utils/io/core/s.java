package io.ktor.utils.io.core;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import io.ktor.utils.io.core.internal.a;
import io.ktor.utils.io.pool.d;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ByteReadPacket.kt */
public abstract class s extends r {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    protected s(@NotNull a head, long remaining, @NotNull d<a> pool) {
        super(head, remaining, pool);
        k.f(head, CacheEntity.HEAD);
        k.f(pool, "pool");
    }
}
