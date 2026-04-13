package io.ktor.server.cio;

import io.ktor.application.a;
import io.ktor.http.cio.m;
import io.ktor.server.engine.BaseApplicationResponse;
import io.ktor.server.engine.g;
import io.ktor.utils.io.i;
import io.ktor.utils.io.l;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CIOApplicationCall.kt */
public final class b extends g {
    @NotNull
    private final d c;
    @NotNull
    private final e d;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public b(@NotNull a application, @NotNull m _request, @NotNull i input, @NotNull l output, @NotNull kotlin.coroutines.g engineDispatcher, @NotNull kotlin.coroutines.g appDispatcher, @Nullable w<Boolean> upgraded, @Nullable SocketAddress remoteAddress, @Nullable SocketAddress localAddress) {
        super(application);
        k.f(application, "application");
        k.f(_request, "_request");
        k.f(input, "input");
        k.f(output, "output");
        k.f(engineDispatcher, "engineDispatcher");
        k.f(appDispatcher, "appDispatcher");
        SocketAddress socketAddress = remoteAddress;
        SocketAddress socketAddress2 = localAddress;
        this.c = new d(this, (InetSocketAddress) (!(socketAddress instanceof InetSocketAddress) ? null : socketAddress), (InetSocketAddress) (!(socketAddress2 instanceof InetSocketAddress) ? null : socketAddress2), input, _request);
        e eVar = r0;
        e eVar2 = new e(this, output, input, engineDispatcher, appDispatcher, upgraded);
        this.d = eVar;
        g.f(this, (BaseApplicationResponse) null, 1, (Object) null);
    }

    @NotNull
    /* renamed from: g */
    public d getRequest() {
        return this.c;
    }

    @NotNull
    /* renamed from: h */
    public e d() {
        return this.d;
    }

    public final void i() {
        getRequest().h();
    }
}
