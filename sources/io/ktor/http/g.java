package io.ktor.http;

import io.ktor.http.o;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.o0;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Headers.kt */
public final class g implements o {
    public static final g d = new g();

    private g() {
    }

    public void a(@NotNull p<? super String, ? super List<String>, x> body) {
        k.f(body, "body");
        o.b.a(this, body);
    }

    @Nullable
    public String get(@NotNull String name) {
        k.f(name, "name");
        return o.b.b(this, name);
    }

    public boolean b() {
        return true;
    }

    @Nullable
    public List<String> c(@NotNull String name) {
        k.f(name, "name");
        return null;
    }

    @NotNull
    public Set<Map.Entry<String, List<String>>> entries() {
        return o0.d();
    }

    @NotNull
    public String toString() {
        return "Headers " + entries();
    }
}
