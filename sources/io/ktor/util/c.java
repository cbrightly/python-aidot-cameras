package io.ktor.util;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import io.ktor.util.b;
import java.util.List;
import java.util.Map;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AttributesJvm.kt */
public abstract class c implements b {
    /* access modifiers changed from: protected */
    @NotNull
    public abstract Map<a<?>, Object> g();

    @NotNull
    public <T> T a(@NotNull a<T> key) {
        k.f(key, CacheEntity.KEY);
        return b.a.a(this, key);
    }

    @Nullable
    public final <T> T e(@NotNull a<T> key) {
        k.f(key, CacheEntity.KEY);
        return g().get(key);
    }

    public final <T> void b(@NotNull a<T> key, @NotNull T value) {
        k.f(key, CacheEntity.KEY);
        k.f(value, "value");
        g().put(key, value);
    }

    public final <T> void d(@NotNull a<T> key) {
        k.f(key, CacheEntity.KEY);
        g().remove(key);
    }

    @NotNull
    public final List<a<?>> c() {
        return y.C0(g().keySet());
    }
}
