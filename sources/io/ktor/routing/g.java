package io.ktor.routing;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.slf4j.e;

/* compiled from: RouteSelector.kt */
public final class g extends j {
    public static final g b = new g();

    private g() {
        super(0.5d);
    }

    @NotNull
    public k a(@NotNull v context, int segmentIndex) {
        k.f(context, "context");
        if (segmentIndex < context.b().size()) {
            return k.f.e();
        }
        return k.f.c();
    }

    @NotNull
    public String toString() {
        return e.ANY_MARKER;
    }
}
