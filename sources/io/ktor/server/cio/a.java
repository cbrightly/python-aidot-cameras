package io.ktor.server.cio;

import io.ktor.server.cio.c;
import io.ktor.server.engine.b;
import io.ktor.server.engine.f;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: CIO.kt */
public final class a implements f<c, c.a> {
    public static final a a = new a();

    private a() {
    }

    @NotNull
    /* renamed from: b */
    public c a(@NotNull b environment, @NotNull l<? super c.a, x> configure) {
        k.f(environment, "environment");
        k.f(configure, "configure");
        return new c(environment, configure);
    }
}
