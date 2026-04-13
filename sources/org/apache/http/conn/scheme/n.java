package org.apache.http.conn.scheme;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

@Deprecated
/* compiled from: SocketFactoryAdaptor */
public class n implements m {
    private final k a;

    n(k factory) {
        this.a = factory;
    }

    public Socket f() {
        return this.a.j(new BasicHttpParams());
    }

    public Socket e(Socket socket, String host, int port, InetAddress localAddress, int localPort, HttpParams params) {
        InetSocketAddress local = null;
        if (localAddress != null || localPort > 0) {
            local = new InetSocketAddress(localAddress, localPort > 0 ? localPort : 0);
        }
        return this.a.g(socket, new InetSocketAddress(InetAddress.getByName(host), port), local, params);
    }

    public boolean a(Socket socket) {
        return this.a.a(socket);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof n) {
            return this.a.equals(((n) obj).a);
        }
        return this.a.equals(obj);
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
