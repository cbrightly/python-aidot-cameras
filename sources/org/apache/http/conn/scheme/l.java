package org.apache.http.conn.scheme;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.apache.http.params.HttpParams;

@Deprecated
/* compiled from: SchemeSocketFactoryAdaptor */
public class l implements k {
    private final m a;

    l(m factory) {
        this.a = factory;
    }

    public Socket g(Socket sock, InetSocketAddress remoteAddress, InetSocketAddress localAddress, HttpParams params) {
        int localPort;
        InetAddress local;
        String host = remoteAddress.getHostName();
        int port = remoteAddress.getPort();
        if (localAddress != null) {
            local = localAddress.getAddress();
            localPort = localAddress.getPort();
        } else {
            local = null;
            localPort = 0;
        }
        return this.a.e(sock, host, port, local, localPort, params);
    }

    public Socket j(HttpParams params) {
        return this.a.f();
    }

    public boolean a(Socket sock) {
        return this.a.a(sock);
    }

    public m c() {
        return this.a;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof l) {
            return this.a.equals(((l) obj).a);
        }
        return this.a.equals(obj);
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
