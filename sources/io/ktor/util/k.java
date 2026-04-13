package io.ktor.util;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.TypeCastException;
import kotlin.jvm.functions.a;
import org.jetbrains.annotations.NotNull;

/* compiled from: AttributesJvm.kt */
public final class k extends c {
    @NotNull
    private final ConcurrentHashMap<a<?>, Object> a = new ConcurrentHashMap<>();

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: h */
    public ConcurrentHashMap<a<?>, Object> g() {
        return this.a;
    }

    @NotNull
    public <T> T f(@NotNull a<T> key, @NotNull a<? extends T> block) {
        kotlin.jvm.internal.k.f(key, CacheEntity.KEY);
        kotlin.jvm.internal.k.f(block, "block");
        Object it = g().get(key);
        if (it != null) {
            return it;
        }
        Object result = block.invoke();
        Object putIfAbsent = g().putIfAbsent(key, result);
        if (putIfAbsent == null) {
            putIfAbsent = result;
        }
        if (putIfAbsent != null) {
            return putIfAbsent;
        }
        throw new TypeCastException("null cannot be cast to non-null type T");
    }
}
