package org.apache.http.conn;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.apache.http.l;
import org.apache.http.util.a;

@Deprecated
/* compiled from: HttpInetSocketAddress */
public class o extends InetSocketAddress {
    private static final long serialVersionUID = -6650701828361907957L;
    private final l httphost;

    public o(l httphost2, InetAddress addr, int port) {
        super(addr, port);
        a.i(httphost2, "HTTP host");
        this.httphost = httphost2;
    }

    public l getHttpHost() {
        return this.httphost;
    }

    public String toString() {
        return this.httphost.getHostName() + ":" + getPort();
    }
}
