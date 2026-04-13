package org.apache.http.conn.scheme;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.a;

@Deprecated
/* compiled from: PlainSocketFactory */
public class e implements m, k {
    private final a a = null;

    public static e h() {
        return new e();
    }

    public Socket j(HttpParams params) {
        return new Socket();
    }

    public Socket f() {
        return new Socket();
    }

    public Socket g(Socket socket, InetSocketAddress remoteAddress, InetSocketAddress localAddress, HttpParams params) {
        a.i(remoteAddress, "Remote address");
        a.i(params, "HTTP parameters");
        Socket sock = socket;
        if (sock == null) {
            sock = f();
        }
        if (localAddress != null) {
            sock.setReuseAddress(HttpConnectionParams.getSoReuseaddr(params));
            sock.bind(localAddress);
        }
        int connTimeout = HttpConnectionParams.getConnectionTimeout(params);
        try {
            sock.setSoTimeout(HttpConnectionParams.getSoTimeout(params));
            sock.connect(remoteAddress, connTimeout);
            return sock;
        } catch (SocketTimeoutException e) {
            throw new ConnectTimeoutException("Connect to " + remoteAddress + " timed out");
        }
    }

    public final boolean a(Socket sock) {
        return false;
    }

    @Deprecated
    public Socket e(Socket socket, String host, int port, InetAddress localAddress, int localPort, HttpParams params) {
        InetAddress remoteAddress;
        InetSocketAddress local = null;
        if (localAddress != null || localPort > 0) {
            local = new InetSocketAddress(localAddress, localPort > 0 ? localPort : 0);
        }
        a aVar = this.a;
        if (aVar != null) {
            remoteAddress = aVar.resolve(host);
        } else {
            remoteAddress = InetAddress.getByName(host);
        }
        return g(socket, new InetSocketAddress(remoteAddress, port), local, params);
    }
}
