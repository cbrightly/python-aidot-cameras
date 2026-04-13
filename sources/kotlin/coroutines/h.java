package kotlin.coroutines;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.io.Serializable;
import kotlin.coroutines.g;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CoroutineContextImpl.kt */
public final class h implements g, Serializable {
    @NotNull
    public static final h INSTANCE = new h();
    private static final long serialVersionUID = 0;

    private h() {
    }

    private final Object readResolve() {
        return INSTANCE;
    }

    @Nullable
    public <E extends g.b> E get(@NotNull g.c<E> key) {
        k.e(key, CacheEntity.KEY);
        return null;
    }

    public <R> R fold(R initial, @NotNull p<? super R, ? super g.b, ? extends R> operation) {
        k.e(operation, "operation");
        return initial;
    }

    @NotNull
    public g plus(@NotNull g context) {
        k.e(context, "context");
        return context;
    }

    @NotNull
    public g minusKey(@NotNull g.c<?> key) {
        k.e(key, CacheEntity.KEY);
        return this;
    }

    public int hashCode() {
        return 0;
    }

    @NotNull
    public String toString() {
        return "EmptyCoroutineContext";
    }
}
