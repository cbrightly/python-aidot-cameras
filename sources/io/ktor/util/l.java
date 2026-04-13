package io.ktor.util;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.collections.l0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CopyOnWriteHashMap.kt */
public final class l<K, V> {
    private static final AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(l.class, Object.class, "current");
    private volatile Object current = l0.f();

    @Nullable
    public final V b(@NotNull K key) {
        k.f(key, CacheEntity.KEY);
        return ((Map) this.current).get(key);
    }

    @NotNull
    public final V a(@NotNull K key, @NotNull kotlin.jvm.functions.l<? super K, ? extends V> producer) {
        Map old;
        HashMap copy;
        Object newValue;
        k.f(key, CacheEntity.KEY);
        k.f(producer, "producer");
        do {
            old = (Map) this.current;
            Object it = old.get(key);
            if (it != null) {
                return it;
            }
            copy = new HashMap(old);
            newValue = producer.invoke(key);
            copy.put(key, newValue);
        } while (!a.compareAndSet(this, old, copy));
        return newValue;
    }
}
