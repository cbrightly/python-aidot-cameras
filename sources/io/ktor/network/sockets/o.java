package io.ktor.network.sockets;

import io.ktor.network.selector.m;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Builders.kt */
public final class o {
    private final m a;
    @NotNull
    private q b;

    public o(@NotNull m selector, @NotNull q options) {
        k.f(selector, "selector");
        k.f(options, "options");
        this.a = selector;
        this.b = options;
    }

    @NotNull
    public q a() {
        return this.b;
    }

    @NotNull
    public final t b() {
        return new t(this.a, a().f());
    }
}
