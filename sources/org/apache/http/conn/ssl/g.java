package org.apache.http.conn.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.conn.o;
import org.apache.http.conn.scheme.a;
import org.apache.http.conn.scheme.c;
import org.apache.http.conn.socket.b;
import org.apache.http.l;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.f;

@Deprecated
/* compiled from: SSLSocketFactory */
public class g implements b, org.apache.http.conn.scheme.g, org.apache.http.conn.scheme.b, c {
    public static final j a = new b();
    public static final j b = new c();
    public static final j c = new h();
    private final SSLSocketFactory d;
    private final a e;
    private volatile j f;
    private final String[] g;
    private final String[] h;

    public static g l() {
        return new g(f.a(), b);
    }

    public g(SSLContext sslContext, j hostnameVerifier) {
        this(((SSLContext) org.apache.http.util.a.i(sslContext, "SSL context")).getSocketFactory(), (String[]) null, (String[]) null, hostnameVerifier);
    }

    public g(SSLSocketFactory socketfactory, String[] supportedProtocols, String[] supportedCipherSuites, j hostnameVerifier) {
        this.d = (SSLSocketFactory) org.apache.http.util.a.i(socketfactory, "SSL socket factory");
        this.g = supportedProtocols;
        this.h = supportedCipherSuites;
        this.f = hostnameVerifier != null ? hostnameVerifier : b;
        this.e = null;
    }

    public Socket j(HttpParams params) {
        return i((f) null);
    }

    public Socket f() {
        return i((f) null);
    }

    public Socket g(Socket socket, InetSocketAddress remoteAddress, InetSocketAddress localAddress, HttpParams params) {
        l host;
        org.apache.http.util.a.i(remoteAddress, "Remote address");
        org.apache.http.util.a.i(params, "HTTP parameters");
        if (remoteAddress instanceof o) {
            host = ((o) remoteAddress).getHttpHost();
        } else {
            host = new l(remoteAddress.getHostName(), remoteAddress.getPort(), "https");
        }
        int socketTimeout = HttpConnectionParams.getSoTimeout(params);
        int connectTimeout = HttpConnectionParams.getConnectionTimeout(params);
        socket.setSoTimeout(socketTimeout);
        return k(connectTimeout, socket, host, remoteAddress, localAddress, (f) null);
    }

    public boolean a(Socket sock) {
        org.apache.http.util.a.i(sock, "Socket");
        org.apache.http.util.b.a(sock instanceof SSLSocket, "Socket not created by this factory");
        org.apache.http.util.b.a(!sock.isClosed(), "Socket is closed");
        return true;
    }

    public Socket b(Socket socket, String host, int port, HttpParams params) {
        return h(socket, host, port, (f) null);
    }

    public Socket d(Socket socket, String host, int port, boolean autoClose) {
        return h(socket, host, port, (f) null);
    }

    public void o(j hostnameVerifier) {
        org.apache.http.util.a.i(hostnameVerifier, "Hostname verifier");
        this.f = hostnameVerifier;
    }

    public Socket e(Socket socket, String host, int port, InetAddress local, int localPort, HttpParams params) {
        InetAddress remote;
        a aVar = this.e;
        if (aVar != null) {
            remote = aVar.resolve(host);
        } else {
            remote = InetAddress.getByName(host);
        }
        InetSocketAddress localAddress = null;
        if (local != null || localPort > 0) {
            localAddress = new InetSocketAddress(local, localPort > 0 ? localPort : 0);
        }
        return g(socket, new o(new l(host, port), remote, port), localAddress, params);
    }

    public Socket c(Socket socket, String host, int port, boolean autoClose) {
        return d(socket, host, port, autoClose);
    }

    /* access modifiers changed from: protected */
    public void n(SSLSocket socket) {
    }

    private void m(SSLSocket socket) {
        String[] strArr = this.g;
        if (strArr != null) {
            socket.setEnabledProtocols(strArr);
        }
        String[] strArr2 = this.h;
        if (strArr2 != null) {
            socket.setEnabledCipherSuites(strArr2);
        }
        n(socket);
    }

    public Socket i(f context) {
        return SocketFactory.getDefault().createSocket();
    }

    public Socket k(int connectTimeout, Socket socket, l host, InetSocketAddress remoteAddress, InetSocketAddress localAddress, f context) {
        org.apache.http.util.a.i(host, "HTTP host");
        org.apache.http.util.a.i(remoteAddress, "Remote address");
        Socket sock = socket != null ? socket : i(context);
        if (localAddress != null) {
            sock.bind(localAddress);
        }
        try {
            sock.connect(remoteAddress, connectTimeout);
            if (!(sock instanceof SSLSocket)) {
                return h(sock, host.getHostName(), remoteAddress.getPort(), context);
            }
            SSLSocket sslsock = (SSLSocket) sock;
            sslsock.startHandshake();
            p(sslsock, host.getHostName());
            return sock;
        } catch (IOException ex) {
            try {
                sock.close();
            } catch (IOException e2) {
            }
            throw ex;
        }
    }

    public Socket h(Socket socket, String target, int port, f context) {
        SSLSocket sslsock = (SSLSocket) this.d.createSocket(socket, target, port, true);
        m(sslsock);
        sslsock.startHandshake();
        p(sslsock, target);
        return sslsock;
    }

    private void p(SSLSocket sslsock, String hostname) {
        try {
            this.f.b(hostname, sslsock);
        } catch (IOException iox) {
            try {
                sslsock.close();
            } catch (Exception e2) {
            }
            throw iox;
        }
    }
}
