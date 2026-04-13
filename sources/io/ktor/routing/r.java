package io.ktor.routing;

import io.ktor.application.a;
import io.ktor.application.g;
import io.ktor.routing.l;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: Routing.kt */
public final class r {
    @NotNull
    public static final l a(@NotNull a $this$routing, @NotNull l<? super l, x> configuration) {
        k.f($this$routing, "$this$routing");
        k.f(configuration, "configuration");
        l.a aVar = l.F4;
        l lVar = (l) g.a($this$routing, aVar);
        if (lVar == null) {
            return (l) g.b($this$routing, aVar, configuration);
        }
        configuration.invoke(lVar);
        return lVar;
    }
}
