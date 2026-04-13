package coil.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.v;
import kotlin.jvm.internal.e0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LinkedMultimap.kt */
public final class a<K, V> {
    @NotNull
    private final C0001a<K, V> a = new C0001a<>(null);
    @NotNull
    private final HashMap<K, C0001a<K, V>> b = new HashMap<>();

    public final void d(K key, V value) {
        Object answer$iv;
        Map $this$getOrPut$iv = this.b;
        Object value$iv = $this$getOrPut$iv.get(key);
        if (value$iv == null) {
            Object aVar = new C0001a(key);
            c(aVar);
            answer$iv = aVar;
            $this$getOrPut$iv.put(key, answer$iv);
        } else {
            answer$iv = value$iv;
        }
        ((C0001a) answer$iv).a(value);
    }

    @Nullable
    public final V g(K key) {
        Object answer$iv;
        Map $this$getOrPut$iv = this.b;
        Object value$iv = $this$getOrPut$iv.get(key);
        if (value$iv == null) {
            answer$iv = new C0001a(key);
            $this$getOrPut$iv.put(key, answer$iv);
        } else {
            answer$iv = value$iv;
        }
        C0001a entry = (C0001a) answer$iv;
        b(entry);
        return entry.f();
    }

    @Nullable
    public final V f() {
        C0001a last = this.a.d();
        while (!k.a(last, this.a)) {
            Object removed = last.f();
            if (removed != null) {
                return removed;
            }
            e(last);
            HashMap<K, C0001a<K, V>> hashMap = this.b;
            K b2 = last.b();
            if (hashMap != null) {
                e0.c(hashMap).remove(b2);
                last = last.d();
            } else {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap<K, V>");
            }
        }
        return null;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$toString_u24lambda_u2d2 = sb;
        $this$toString_u24lambda_u2d2.append("LinkedMultimap( ");
        C0001a current = this.a.c();
        while (!k.a(current, this.a)) {
            $this$toString_u24lambda_u2d2.append('{');
            $this$toString_u24lambda_u2d2.append(current.b());
            $this$toString_u24lambda_u2d2.append(':');
            $this$toString_u24lambda_u2d2.append(current.e());
            $this$toString_u24lambda_u2d2.append('}');
            current = current.c();
            if (!k.a(current, this.a)) {
                $this$toString_u24lambda_u2d2.append(", ");
            }
        }
        $this$toString_u24lambda_u2d2.append(" )");
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    private final void b(C0001a<K, V> entry) {
        e(entry);
        entry.h(this.a);
        entry.g(this.a.c());
        a(entry);
    }

    private final void c(C0001a<K, V> entry) {
        e(entry);
        entry.h(this.a.d());
        entry.g(this.a);
        a(entry);
    }

    private final <K, V> void a(C0001a<K, V> entry) {
        entry.c().h(entry);
        entry.d().g(entry);
    }

    private final <K, V> void e(C0001a<K, V> entry) {
        entry.d().g(entry.c());
        entry.c().h(entry.d());
    }

    /* renamed from: coil.collection.a$a  reason: collision with other inner class name */
    /* compiled from: LinkedMultimap.kt */
    public static final class C0001a<K, V> {
        @Nullable
        private final K a;
        @Nullable
        private List<V> b;
        @NotNull
        private C0001a<K, V> c = this;
        @NotNull
        private C0001a<K, V> d = this;

        public C0001a(@Nullable K key) {
            this.a = key;
        }

        @Nullable
        public final K b() {
            return this.a;
        }

        @NotNull
        public final C0001a<K, V> d() {
            return this.c;
        }

        public final void h(@NotNull C0001a<K, V> aVar) {
            k.e(aVar, "<set-?>");
            this.c = aVar;
        }

        @NotNull
        public final C0001a<K, V> c() {
            return this.d;
        }

        public final void g(@NotNull C0001a<K, V> aVar) {
            k.e(aVar, "<set-?>");
            this.d = aVar;
        }

        public final int e() {
            List<V> list = this.b;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        @Nullable
        public final V f() {
            List<V> list = this.b;
            if (list == null) {
                return null;
            }
            return v.C(list);
        }

        public final void a(V value) {
            List it = this.b;
            if (it == null) {
                it = new ArrayList();
                this.b = it;
            }
            it.add(value);
        }
    }
}
