package io.ktor.util.collections;

import io.ktor.util.s;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.markers.d;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ConcurrentMap.kt */
public final class b<Key, Value> implements Map<Key, Value>, d {
    @NotNull
    private final Set<Map.Entry<Key, Value>> c;
    @NotNull
    private final Set<Key> d;
    @NotNull
    private final Collection<Value> f;
    private final s q;
    private final Map<Key, Value> x;

    public b(@NotNull s lock, @NotNull Map<Key, Value> delegate) {
        k.f(lock, "lock");
        k.f(delegate, "delegate");
        this.q = lock;
        this.x = delegate;
        this.c = new c(delegate.entrySet(), lock);
        this.d = new c(delegate.keySet(), lock);
        this.f = new a(delegate.values(), lock);
    }

    public final /* bridge */ Set<Map.Entry<Key, Value>> entrySet() {
        return a();
    }

    public final /* bridge */ Set<Key> keySet() {
        return b();
    }

    public final /* bridge */ int size() {
        return e();
    }

    public final /* bridge */ Collection<Value> values() {
        return f();
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ b(s sVar, Map map, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new s() : sVar, (i & 2) != 0 ? new LinkedHashMap() : map);
    }

    public int e() {
        s $this$withLock$iv = this.q;
        try {
            $this$withLock$iv.a();
            return this.x.size();
        } finally {
            $this$withLock$iv.b();
        }
    }

    public boolean containsKey(Object key) {
        s $this$withLock$iv = this.q;
        try {
            $this$withLock$iv.a();
            return this.x.containsKey(key);
        } finally {
            $this$withLock$iv.b();
        }
    }

    public boolean containsValue(Object value) {
        s $this$withLock$iv = this.q;
        try {
            $this$withLock$iv.a();
            return this.x.containsValue(value);
        } finally {
            $this$withLock$iv.b();
        }
    }

    @Nullable
    public Value get(Object key) {
        s $this$withLock$iv = this.q;
        try {
            $this$withLock$iv.a();
            return this.x.get(key);
        } finally {
            $this$withLock$iv.b();
        }
    }

    public boolean isEmpty() {
        s $this$withLock$iv = this.q;
        try {
            $this$withLock$iv.a();
            return this.x.isEmpty();
        } finally {
            $this$withLock$iv.b();
        }
    }

    @NotNull
    public Set<Map.Entry<Key, Value>> a() {
        return this.c;
    }

    @NotNull
    public Set<Key> b() {
        return this.d;
    }

    @NotNull
    public Collection<Value> f() {
        return this.f;
    }

    public void clear() {
        s $this$withLock$iv = this.q;
        try {
            $this$withLock$iv.a();
            this.x.clear();
            x xVar = x.a;
        } finally {
            $this$withLock$iv.b();
        }
    }

    @Nullable
    public Value put(Key key, Value value) {
        s $this$withLock$iv = this.q;
        try {
            $this$withLock$iv.a();
            return this.x.put(key, value);
        } finally {
            $this$withLock$iv.b();
        }
    }

    public void putAll(@NotNull Map<? extends Key, ? extends Value> from) {
        k.f(from, "from");
        s $this$withLock$iv = this.q;
        try {
            $this$withLock$iv.a();
            this.x.putAll(from);
            x xVar = x.a;
        } finally {
            $this$withLock$iv.b();
        }
    }

    @Nullable
    public Value remove(Object key) {
        s $this$withLock$iv = this.q;
        try {
            $this$withLock$iv.a();
            return this.x.remove(key);
        } finally {
            $this$withLock$iv.b();
        }
    }
}
