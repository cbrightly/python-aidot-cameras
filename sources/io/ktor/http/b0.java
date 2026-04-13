package io.ktor.http;

import io.ktor.http.y;
import java.util.List;
import kotlin.collections.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Parameters.kt */
public final class b0 {
    @NotNull
    public static final y a() {
        return y.b.a();
    }

    @NotNull
    public static final y b(@NotNull String name, @NotNull String value) {
        k.f(name, "name");
        k.f(value, "value");
        return new c0(name, p.b(value));
    }

    @NotNull
    public static final y c(@NotNull String name, @NotNull List<String> values) {
        k.f(name, "name");
        k.f(values, "values");
        return new c0(name, values);
    }

    @NotNull
    public static final y d(@NotNull y $this$plus, @NotNull y other) {
        k.f($this$plus, "$this$plus");
        k.f(other, "other");
        if ($this$plus.b() != other.b()) {
            throw new IllegalArgumentException("Cannot concatenate Parameters with case-sensitive and case-insensitive names");
        } else if ($this$plus.isEmpty()) {
            return other;
        } else {
            if (other.isEmpty()) {
                return $this$plus;
            }
            y.a aVar = y.b;
            z zVar = new z(0, 1, (DefaultConstructorMarker) null);
            z $this$build = zVar;
            $this$build.b($this$plus);
            $this$build.b(other);
            return zVar.m();
        }
    }
}
