package io.ktor.util;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CaseInsensitiveMap.kt */
public final class f<Value> implements Map<String, Value>, kotlin.jvm.internal.markers.d {
    private final Map<h, Value> c = new LinkedHashMap();

    public final /* bridge */ boolean containsKey(Object obj) {
        if (obj instanceof String) {
            return a((String) obj);
        }
        return false;
    }

    public final /* bridge */ Set<Map.Entry<String, Value>> entrySet() {
        return e();
    }

    public final /* bridge */ Value get(Object obj) {
        if (obj instanceof String) {
            return b((String) obj);
        }
        return null;
    }

    public final /* bridge */ Set<String> keySet() {
        return f();
    }

    public final /* bridge */ Value remove(Object obj) {
        if (obj instanceof String) {
            return j((String) obj);
        }
        return null;
    }

    public final /* bridge */ int size() {
        return g();
    }

    public final /* bridge */ Collection<Value> values() {
        return h();
    }

    public int g() {
        return this.c.size();
    }

    public boolean a(@NotNull String key) {
        k.f(key, CacheEntity.KEY);
        return this.c.containsKey(new h(key));
    }

    public boolean containsValue(Object value) {
        return this.c.containsValue(value);
    }

    @Nullable
    public Value b(@NotNull String key) {
        k.f(key, CacheEntity.KEY);
        return this.c.get(a0.a(key));
    }

    public boolean isEmpty() {
        return this.c.isEmpty();
    }

    public void clear() {
        this.c.clear();
    }

    @Nullable
    /* renamed from: i */
    public Value put(@NotNull String key, Value value) {
        k.f(key, CacheEntity.KEY);
        return this.c.put(a0.a(key), value);
    }

    public void putAll(@NotNull Map<? extends String, ? extends Value> from) {
        k.f(from, "from");
        for (Map.Entry $dstr$key$value : from.entrySet()) {
            put((String) $dstr$key$value.getKey(), $dstr$key$value.getValue());
        }
    }

    @Nullable
    public Value j(@NotNull String key) {
        k.f(key, CacheEntity.KEY);
        return this.c.remove(a0.a(key));
    }

    @NotNull
    public Set<String> f() {
        return new m(this.c.keySet(), c.INSTANCE, d.INSTANCE);
    }

    /* compiled from: CaseInsensitiveMap.kt */
    public static final class c extends l implements kotlin.jvm.functions.l<h, String> {
        public static final c INSTANCE = new c();

        c() {
            super(1);
        }

        @NotNull
        public final String invoke(@NotNull h $receiver) {
            k.f($receiver, "$receiver");
            return $receiver.a();
        }
    }

    /* compiled from: CaseInsensitiveMap.kt */
    public static final class d extends l implements kotlin.jvm.functions.l<String, h> {
        public static final d INSTANCE = new d();

        d() {
            super(1);
        }

        @NotNull
        public final h invoke(@NotNull String $receiver) {
            k.f($receiver, "$receiver");
            return a0.a($receiver);
        }
    }

    @NotNull
    public Set<Map.Entry<String, Value>> e() {
        return new m(this.c.entrySet(), a.INSTANCE, b.INSTANCE);
    }

    /* compiled from: CaseInsensitiveMap.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<Map.Entry<h, Value>, p<String, Value>> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final p<String, Value> invoke(@NotNull Map.Entry<h, Value> $receiver) {
            k.f($receiver, "$receiver");
            return new p<>($receiver.getKey().a(), $receiver.getValue());
        }
    }

    /* compiled from: CaseInsensitiveMap.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<Map.Entry<String, Value>, p<h, Value>> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        @NotNull
        public final p<h, Value> invoke(@NotNull Map.Entry<String, Value> $receiver) {
            k.f($receiver, "$receiver");
            return new p<>(a0.a($receiver.getKey()), $receiver.getValue());
        }
    }

    @NotNull
    public Collection<Value> h() {
        return this.c.values();
    }

    public boolean equals(@Nullable Object other) {
        if (other == null || !(other instanceof f)) {
            return false;
        }
        return k.a(((f) other).c, this.c);
    }

    public int hashCode() {
        return this.c.hashCode();
    }
}
