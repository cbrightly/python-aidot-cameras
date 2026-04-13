package coil.request;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.collections.l0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.t;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Parameters.kt */
public final class l implements Iterable<n<? extends String, ? extends c>>, kotlin.jvm.internal.markers.a {
    @NotNull
    public static final b c = new b((DefaultConstructorMarker) null);
    @NotNull
    public static final l d = new l();
    /* access modifiers changed from: private */
    @NotNull
    public final Map<String, c> f;

    public /* synthetic */ l(Map map, DefaultConstructorMarker defaultConstructorMarker) {
        this(map);
    }

    private l(Map<String, c> map) {
        this.f = map;
    }

    public l() {
        this(l0.f());
    }

    @Nullable
    public final Object f(@NotNull String key) {
        k.e(key, CacheEntity.KEY);
        c cVar = this.f.get(key);
        if (cVar == null) {
            return null;
        }
        return cVar.b();
    }

    public final boolean isEmpty() {
        return this.f.isEmpty();
    }

    @NotNull
    public final Map<String, String> b() {
        if (isEmpty()) {
            return l0.f();
        }
        Map $this$mapNotNullValues$iv = this.f;
        Map destination$iv = new LinkedHashMap();
        for (Map.Entry entry$iv : $this$mapNotNullValues$iv.entrySet()) {
            Object value$iv = ((c) entry$iv.getValue()).a();
            if (value$iv != null) {
                destination$iv.put(entry$iv.getKey(), value$iv);
            }
        }
        return destination$iv;
    }

    @NotNull
    public Iterator<n<String, c>> iterator() {
        Map $this$mapTo$iv$iv = this.f;
        ArrayList arrayList = new ArrayList($this$mapTo$iv$iv.size());
        for (Map.Entry $dstr$key$value : $this$mapTo$iv$iv.entrySet()) {
            arrayList.add(t.a((String) $dstr$key$value.getKey(), (c) $dstr$key$value.getValue()));
        }
        return arrayList.iterator();
    }

    public boolean equals(@Nullable Object other) {
        return this == other || ((other instanceof l) && k.a(this.f, ((l) other).f));
    }

    public int hashCode() {
        return this.f.hashCode();
    }

    @NotNull
    public String toString() {
        return "Parameters(map=" + this.f + ')';
    }

    @NotNull
    public final a e() {
        return new a(this);
    }

    /* compiled from: Parameters.kt */
    public static final class c {
        @Nullable
        private final Object a;
        @Nullable
        private final String b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof c)) {
                return false;
            }
            c cVar = (c) obj;
            return k.a(this.a, cVar.a) && k.a(this.b, cVar.b);
        }

        public int hashCode() {
            Object obj = this.a;
            int i = 0;
            int hashCode = (obj == null ? 0 : obj.hashCode()) * 31;
            String str = this.b;
            if (str != null) {
                i = str.hashCode();
            }
            return hashCode + i;
        }

        @NotNull
        public String toString() {
            return "Entry(value=" + this.a + ", cacheKey=" + this.b + ')';
        }

        public c(@Nullable Object value, @Nullable String cacheKey) {
            this.a = value;
            this.b = cacheKey;
        }

        @Nullable
        public final Object b() {
            return this.a;
        }

        @Nullable
        public final String a() {
            return this.b;
        }
    }

    /* compiled from: Parameters.kt */
    public static final class a {
        @NotNull
        private final Map<String, c> a;

        public a() {
            this.a = new LinkedHashMap();
        }

        public a(@NotNull l parameters) {
            k.e(parameters, "parameters");
            this.a = l0.u(parameters.f);
        }

        @NotNull
        public final a b(@NotNull String key, @Nullable Object value, @Nullable String cacheKey) {
            k.e(key, CacheEntity.KEY);
            this.a.put(key, new c(value, cacheKey));
            return this;
        }

        @NotNull
        public final l a() {
            return new l(l0.q(this.a), (DefaultConstructorMarker) null);
        }
    }

    /* compiled from: Parameters.kt */
    public static final class b {
        public /* synthetic */ b(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private b() {
        }
    }
}
