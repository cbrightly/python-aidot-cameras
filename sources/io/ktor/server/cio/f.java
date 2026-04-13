package io.ktor.server.cio;

import io.ktor.network.sockets.l;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.w0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;

/* compiled from: HttpServer.kt */
public final class f {
    @NotNull
    private final z1 a;
    @NotNull
    private final z1 b;
    @NotNull
    private final w0<l> c;

    public f(@NotNull z1 rootServerJob, @NotNull z1 acceptJob, @NotNull w0<? extends l> serverSocket) {
        k.f(rootServerJob, "rootServerJob");
        k.f(acceptJob, "acceptJob");
        k.f(serverSocket, "serverSocket");
        this.a = rootServerJob;
        this.b = acceptJob;
        this.c = serverSocket;
    }

    @NotNull
    public final z1 a() {
        return this.b;
    }

    @NotNull
    public final z1 b() {
        return this.a;
    }
}
