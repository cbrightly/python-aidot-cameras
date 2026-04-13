package org.apache.httpcore.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/* compiled from: NetUtils */
public final class h {
    public static void a(StringBuilder buffer, SocketAddress socketAddress) {
        a.g(buffer, "Buffer");
        a.g(socketAddress, "Socket address");
        if (socketAddress instanceof InetSocketAddress) {
            InetSocketAddress socketaddr = (InetSocketAddress) socketAddress;
            InetAddress inetaddr = socketaddr.getAddress();
            buffer.append(inetaddr != null ? inetaddr.getHostAddress() : inetaddr);
            buffer.append(':');
            buffer.append(socketaddr.getPort());
            return;
        }
        buffer.append(socketAddress);
    }
}
