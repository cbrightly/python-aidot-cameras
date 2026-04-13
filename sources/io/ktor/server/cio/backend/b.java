package io.ktor.server.cio.backend;

import io.ktor.utils.io.i;
import io.ktor.utils.io.l;
import java.net.SocketAddress;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ServerIncomingConnection.kt */
public final class b {
    @NotNull
    private final i a;
    @NotNull
    private final l b;
    @Nullable
    private final SocketAddress c;
    @Nullable
    private final SocketAddress d;

    public b(@NotNull i input, @NotNull l output, @Nullable SocketAddress remoteAddress, @Nullable SocketAddress localAddress) {
        k.f(input, "input");
        k.f(output, "output");
        this.a = input;
        this.b = output;
        this.c = remoteAddress;
        this.d = localAddress;
    }

    @NotNull
    public final i a() {
        return this.a;
    }

    @NotNull
    public final l c() {
        return this.b;
    }

    @Nullable
    public final SocketAddress d() {
        return this.c;
    }

    @Nullable
    public final SocketAddress b() {
        return this.d;
    }
}
