package io.ktor.server.cio.backend;

import io.ktor.utils.io.i;
import io.ktor.utils.io.l;
import java.net.SocketAddress;
import kotlin.coroutines.g;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ServerRequestScope.kt */
public final class d implements o0 {
    @NotNull
    private final g c;
    @NotNull
    private final i d;
    @NotNull
    private final l f;
    @Nullable
    private final SocketAddress q;
    @Nullable
    private final SocketAddress x;
    @Nullable
    private final w<Boolean> y;

    public d(@NotNull g coroutineContext, @NotNull i input, @NotNull l output, @Nullable SocketAddress remoteAddress, @Nullable SocketAddress localAddress, @Nullable w<Boolean> upgraded) {
        k.f(coroutineContext, "coroutineContext");
        k.f(input, "input");
        k.f(output, "output");
        this.c = coroutineContext;
        this.d = input;
        this.f = output;
        this.q = remoteAddress;
        this.x = localAddress;
        this.y = upgraded;
    }

    @NotNull
    public g getCoroutineContext() {
        return this.c;
    }

    @NotNull
    public final i a() {
        return this.d;
    }

    @NotNull
    public final l c() {
        return this.f;
    }

    @Nullable
    public final SocketAddress d() {
        return this.q;
    }

    @Nullable
    public final SocketAddress b() {
        return this.x;
    }

    @Nullable
    public final w<Boolean> e() {
        return this.y;
    }
}
