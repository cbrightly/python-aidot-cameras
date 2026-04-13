package io.ktor.util;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.util.HashMap;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.jvm.functions.a;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: AttributesJvm.kt */
public final class q extends c {
    @NotNull
    private final Map<a<?>, Object> a = new HashMap();

    /* access modifiers changed from: protected */
    @NotNull
    public Map<a<?>, Object> g() {
        return this.a;
    }

    @NotNull
    public <T> T f(@NotNull a<T> key, @NotNull a<? extends T> block) {
        k.f(key, CacheEntity.KEY);
        k.f(block, "block");
        Object it = g().get(key);
        if (it != null) {
            return it;
        }
        Object result = block.invoke();
        Object put = g().put(key, result);
        if (put == null) {
            put = result;
        }
        if (put != null) {
            return put;
        }
        throw new TypeCastException("null cannot be cast to non-null type T");
    }
}
