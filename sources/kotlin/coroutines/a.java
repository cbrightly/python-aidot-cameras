package kotlin.coroutines;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import kotlin.coroutines.g;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CoroutineContextImpl.kt */
public abstract class a implements g.b {
    @NotNull
    private final g.c<?> key;

    public a(@NotNull g.c<?> key2) {
        k.e(key2, CacheEntity.KEY);
        this.key = key2;
    }

    public <R> R fold(R initial, @NotNull p<? super R, ? super g.b, ? extends R> operation) {
        k.e(operation, "operation");
        return g.b.a.a(this, initial, operation);
    }

    @Nullable
    public <E extends g.b> E get(@NotNull g.c<E> key2) {
        k.e(key2, CacheEntity.KEY);
        return g.b.a.b(this, key2);
    }

    @NotNull
    public g.c<?> getKey() {
        return this.key;
    }

    @NotNull
    public g minusKey(@NotNull g.c<?> key2) {
        k.e(key2, CacheEntity.KEY);
        return g.b.a.c(this, key2);
    }

    @NotNull
    public g plus(@NotNull g context) {
        k.e(context, "context");
        return g.b.a.d(this, context);
    }
}
