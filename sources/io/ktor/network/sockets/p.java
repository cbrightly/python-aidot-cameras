package io.ktor.network.sockets;

import io.ktor.network.selector.m;
import io.ktor.network.sockets.q;
import io.ktor.utils.io.pool.d;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SocketImpl.kt */
public final class p<S extends SocketChannel> extends k<S> implements n {
    @NotNull
    private final S p3;
    private final Socket p4;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public p(@NotNull S channel, @NotNull Socket socket, @NotNull m selector, @Nullable q.e socketOptions) {
        super(channel, selector, (d<ByteBuffer>) null, socketOptions);
        k.f(channel, "channel");
        k.f(socket, "socket");
        k.f(selector, "selector");
        this.p3 = channel;
        this.p4 = socket;
        if (!(!getChannel().isBlocking())) {
            throw new IllegalArgumentException("channel need to be configured as non-blocking".toString());
        }
    }

    @NotNull
    /* renamed from: P */
    public S getChannel() {
        return this.p3;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ p(SocketChannel socketChannel, Socket socket, m mVar, q.e eVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(socketChannel, socket, mVar, (i & 8) != 0 ? null : eVar);
    }

    @NotNull
    public SocketAddress getLocalAddress() {
        SocketAddress localSocketAddress = this.p4.getLocalSocketAddress();
        k.b(localSocketAddress, "socket.localSocketAddress");
        return localSocketAddress;
    }

    @NotNull
    public SocketAddress w() {
        SocketAddress remoteSocketAddress = this.p4.getRemoteSocketAddress();
        k.b(remoteSocketAddress, "socket.remoteSocketAddress");
        return remoteSocketAddress;
    }
}
