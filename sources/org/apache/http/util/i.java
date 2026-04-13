package org.apache.http.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/* compiled from: NetUtils */
public final class i {
    public static void a(StringBuilder buffer, SocketAddress socketAddress) {
        a.i(buffer, "Buffer");
        a.i(socketAddress, "Socket address");
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
