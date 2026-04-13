package io.ktor.routing;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RoutingPath.kt */
public final class t {
    @NotNull
    private final String a;
    @NotNull
    private final u b;

    @NotNull
    public final String a() {
        return this.a;
    }

    @NotNull
    public final u b() {
        return this.b;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof t)) {
            return false;
        }
        t tVar = (t) obj;
        return k.a(this.a, tVar.a) && k.a(this.b, tVar.b);
    }

    public int hashCode() {
        String str = this.a;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        u uVar = this.b;
        if (uVar != null) {
            i = uVar.hashCode();
        }
        return hashCode + i;
    }

    @NotNull
    public String toString() {
        return "RoutingPathSegment(value=" + this.a + ", kind=" + this.b + ")";
    }

    public t(@NotNull String value, @NotNull u kind) {
        k.f(value, "value");
        k.f(kind, "kind");
        this.a = value;
        this.b = kind;
    }

    @NotNull
    public final u c() {
        return this.b;
    }

    @NotNull
    public final String d() {
        return this.a;
    }
}
