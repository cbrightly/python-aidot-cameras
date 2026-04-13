package io.ktor.http;

import io.ktor.http.y;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.o0;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Parameters.kt */
public final class h implements y {
    public static final h d = new h();

    private h() {
    }

    public void a(@NotNull p<? super String, ? super List<String>, x> body) {
        k.f(body, "body");
        y.b.a(this, body);
    }

    @Nullable
    public String get(@NotNull String name) {
        k.f(name, "name");
        return y.b.b(this, name);
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

    public boolean isEmpty() {
        return true;
    }

    @NotNull
    public String toString() {
        return "Parameters " + entries();
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof y) && ((y) other).isEmpty();
    }
}
