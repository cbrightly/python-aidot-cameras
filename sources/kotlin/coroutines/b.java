package kotlin.coroutines;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import kotlin.coroutines.g;
import kotlin.coroutines.g.b;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CoroutineContextImpl.kt */
public abstract class b<B extends g.b, E extends B> implements g.c<E> {
    private final g.c<?> c;
    private final l<g.b, E> d;

    public b(@NotNull g.c<B> baseKey, @NotNull l<? super g.b, ? extends E> safeCast) {
        k.e(baseKey, "baseKey");
        k.e(safeCast, "safeCast");
        this.d = safeCast;
        this.c = baseKey instanceof b ? ((b) baseKey).c : baseKey;
    }

    @Nullable
    public final E b(@NotNull g.b element) {
        k.e(element, "element");
        return (g.b) this.d.invoke(element);
    }

    public final boolean a(@NotNull g.c<?> key) {
        k.e(key, CacheEntity.KEY);
        return key == this || this.c == key;
    }
}
