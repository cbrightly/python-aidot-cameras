package org.apache.http.conn.scheme;

import java.net.InetSocketAddress;
import java.net.Socket;
import org.apache.http.params.HttpParams;

@Deprecated
/* compiled from: SchemeLayeredSocketFactoryAdaptor2 */
public class h implements g {
    private final b a;

    h(b factory) {
        this.a = factory;
    }

    public Socket j(HttpParams params) {
        return this.a.j(params);
    }

    public Socket g(Socket sock, InetSocketAddress remoteAddress, InetSocketAddress localAddress, HttpParams params) {
        return this.a.g(sock, remoteAddress, localAddress, params);
    }

    public boolean a(Socket sock) {
        return this.a.a(sock);
    }

    public Socket b(Socket socket, String target, int port, HttpParams params) {
        return this.a.d(socket, target, port, true);
    }
}
