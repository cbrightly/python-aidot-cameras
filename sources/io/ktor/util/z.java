package io.ktor.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.n0;
import kotlin.collections.y;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: StringValues.kt */
public class z implements v {
    private final boolean d;
    @NotNull
    private final String e;
    @NotNull
    private final List<String> f;

    public z(boolean caseInsensitiveName, @NotNull String name, @NotNull List<String> values) {
        k.f(name, "name");
        k.f(values, "values");
        this.d = caseInsensitiveName;
        this.e = name;
        this.f = values;
    }

    public boolean b() {
        return this.d;
    }

    @NotNull
    public final String d() {
        return this.e;
    }

    @NotNull
    public final List<String> e() {
        return this.f;
    }

    @Nullable
    public List<String> c(@NotNull String name) {
        k.f(name, "name");
        if (w.y(this.e, name, b())) {
            return this.f;
        }
        return null;
    }

    /* compiled from: StringValues.kt */
    public static final class a implements Map.Entry<String, List<? extends String>>, kotlin.jvm.internal.markers.a {
        @NotNull
        private final String c;
        @NotNull
        private final List<String> d;
        final /* synthetic */ z f;

        public /* synthetic */ Object setValue(Object obj) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        a(z $outer) {
            this.f = $outer;
            this.c = $outer.d();
            this.d = $outer.e();
        }

        @NotNull
        /* renamed from: a */
        public String getKey() {
            return this.c;
        }

        @NotNull
        /* renamed from: b */
        public List<String> getValue() {
            return this.d;
        }

        @NotNull
        public String toString() {
            return getKey() + '=' + getValue();
        }
    }

    @NotNull
    public Set<Map.Entry<String, List<String>>> entries() {
        return n0.c(new a(this));
    }

    public boolean isEmpty() {
        return false;
    }

    public int hashCode() {
        return y.d(entries(), Boolean.valueOf(b()).hashCode() * 31);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if ((other instanceof v) && b() == ((v) other).b()) {
            return y.c(entries(), ((v) other).entries());
        }
        return false;
    }

    public void a(@NotNull p<? super String, ? super List<String>, x> body) {
        k.f(body, "body");
        body.invoke(this.e, this.f);
    }

    @Nullable
    public String get(@NotNull String name) {
        k.f(name, "name");
        if (w.y(name, this.e, b())) {
            return (String) y.U(this.f);
        }
        return null;
    }
}
