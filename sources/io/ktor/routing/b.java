package io.ktor.routing;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RouteSelector.kt */
public final class b extends j {
    @NotNull
    private final String b;

    public boolean equals(@Nullable Object obj) {
        if (this != obj) {
            return (obj instanceof b) && k.a(this.b, ((b) obj).b);
        }
        return true;
    }

    public int hashCode() {
        String str = this.b;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public b(@NotNull String value) {
        super(1.0d);
        k.f(value, "value");
        this.b = value;
    }

    @NotNull
    public k a(@NotNull v context, int segmentIndex) {
        k.f(context, "context");
        if (segmentIndex >= context.b().size() || !k.a(context.b().get(segmentIndex), this.b)) {
            return k.f.c();
        }
        return k.f.b();
    }

    @NotNull
    public String toString() {
        return this.b;
    }
}
