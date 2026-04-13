package io.ktor.util.collections;

import io.ktor.util.s;
import java.util.Set;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.markers.e;
import org.jetbrains.annotations.NotNull;

/* compiled from: ConcurrentSet.kt */
public final class c<K> extends a<K> implements Set<K>, e {
    private final Set<K> f;
    private final s q;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public c(@NotNull Set<K> delegate, @NotNull s lock) {
        super(delegate, lock);
        k.f(delegate, "delegate");
        k.f(lock, "lock");
        this.f = delegate;
        this.q = lock;
    }
}
