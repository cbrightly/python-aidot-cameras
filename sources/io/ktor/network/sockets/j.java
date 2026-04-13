package io.ktor.network.sockets;

import io.ktor.network.sockets.q;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: JavaSocketOptions.kt */
public final class j {
    public static final void a(@NotNull SelectableChannel $this$assignOptions, @NotNull q options) {
        k.f($this$assignOptions, "$this$assignOptions");
        k.f(options, "options");
        Integer num = null;
        boolean z = false;
        if ($this$assignOptions instanceof SocketChannel) {
            Socket socket = ((SocketChannel) $this$assignOptions).socket();
            if (socket == null) {
                k.n();
            }
            if (!u.c(options.e(), u.f.a())) {
                socket.setTrafficClass(options.e() & 255);
            }
            socket.setReuseAddress(options.c());
            if (options.d()) {
                r.e.c((SocketChannel) $this$assignOptions);
            }
            if (options instanceof q.d) {
                Integer valueOf = Integer.valueOf(((q.d) options).g());
                if ((valueOf.intValue() > 0 ? 1 : 0) == 0) {
                    valueOf = null;
                }
                if (valueOf != null) {
                    socket.setReceiveBufferSize(valueOf.intValue());
                }
                Integer valueOf2 = Integer.valueOf(((q.d) options).h());
                if ((valueOf2.intValue() > 0 ? 1 : 0) == 0) {
                    valueOf2 = null;
                }
                if (valueOf2 != null) {
                    socket.setSendBufferSize(valueOf2.intValue());
                }
            }
            if ((options instanceof q.e) != 0) {
                Integer valueOf3 = Integer.valueOf(((q.e) options).j());
                if ((valueOf3.intValue() >= 0 ? 1 : 0) == 0) {
                    valueOf3 = null;
                }
                if (valueOf3 != null) {
                    socket.setSoLinger(true, valueOf3.intValue());
                }
                Boolean i = ((q.e) options).i();
                if (i != null) {
                    socket.setKeepAlive(i.booleanValue());
                }
                socket.setTcpNoDelay(((q.e) options).k());
            }
        }
        if ($this$assignOptions instanceof ServerSocketChannel) {
            ServerSocket socket2 = ((ServerSocketChannel) $this$assignOptions).socket();
            if (socket2 == null) {
                k.n();
            }
            if (options.c()) {
                socket2.setReuseAddress(true);
            }
            if (options.d()) {
                r.e.b((ServerSocketChannel) $this$assignOptions);
            }
        }
        if ($this$assignOptions instanceof DatagramChannel) {
            DatagramSocket socket3 = ((DatagramChannel) $this$assignOptions).socket();
            if (socket3 == null) {
                k.n();
            }
            if (!u.c(options.e(), u.f.a())) {
                socket3.setTrafficClass(options.e() & 255);
            }
            if (options.c()) {
                socket3.setReuseAddress(true);
            }
            if (options.d()) {
                r.e.a((DatagramChannel) $this$assignOptions);
            }
            if (options instanceof q.d) {
                Integer valueOf4 = Integer.valueOf(((q.d) options).g());
                if ((valueOf4.intValue() > 0 ? 1 : 0) == 0) {
                    valueOf4 = null;
                }
                if (valueOf4 != null) {
                    socket3.setReceiveBufferSize(valueOf4.intValue());
                }
                Integer valueOf5 = Integer.valueOf(((q.d) options).h());
                if (valueOf5.intValue() > 0) {
                    z = true;
                }
                if (z) {
                    num = valueOf5;
                }
                if (num != null) {
                    socket3.setSendBufferSize(num.intValue());
                }
            }
        }
    }
}
