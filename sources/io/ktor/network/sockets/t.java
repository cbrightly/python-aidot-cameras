package io.ktor.network.sockets;

import io.ktor.network.selector.m;
import io.ktor.network.sockets.q;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Builders.kt */
public final class t {
    private final m a;
    @NotNull
    private q b;

    /* compiled from: Builders.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<q.a, x> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((q.a) obj);
            return x.a;
        }

        public final void invoke(@NotNull q.a $receiver) {
            k.f($receiver, "$receiver");
        }
    }

    public t(@NotNull m selector, @NotNull q options) {
        k.f(selector, "selector");
        k.f(options, "options");
        this.a = selector;
        this.b = options;
    }

    @NotNull
    public q d() {
        return this.b;
    }

    public static /* synthetic */ l c(t tVar, String str, int i, kotlin.jvm.functions.l lVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = NetworkListener.DEFAULT_NETWORK_HOST;
        }
        if ((i2 & 2) != 0) {
            i = 0;
        }
        if ((i2 & 4) != 0) {
            lVar = a.INSTANCE;
        }
        return tVar.a(str, i, lVar);
    }

    @NotNull
    public final l a(@NotNull String hostname, int port, @NotNull kotlin.jvm.functions.l<? super q.a, x> configure) {
        k.f(hostname, "hostname");
        k.f(configure, "configure");
        return b(new InetSocketAddress(hostname, port), configure);
    }

    @NotNull
    public final l b(@Nullable SocketAddress localAddress, @NotNull kotlin.jvm.functions.l<? super q.a, x> configure) {
        k.f(configure, "configure");
        ServerSocketChannel openServerSocketChannel = this.a.a().openServerSocketChannel();
        ServerSocketChannel $this$buildOrClose = openServerSocketChannel;
        try {
            q.a options = d().a();
            configure.invoke(options);
            j.a($this$buildOrClose, options);
            g.c($this$buildOrClose);
            k.b($this$buildOrClose, "this");
            m $this$apply = new m($this$buildOrClose, this.a);
            $this$apply.getChannel().socket().bind(localAddress);
            return $this$apply;
        } catch (Throwable t$iv) {
            openServerSocketChannel.close();
            throw t$iv;
        }
    }
}
