package org.apache.http.impl;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import org.apache.http.impl.io.t;
import org.apache.http.impl.io.u;
import org.apache.http.io.h;
import org.apache.http.m;
import org.apache.http.params.HttpParams;
import org.apache.http.util.a;
import org.apache.http.util.b;

@Deprecated
/* compiled from: SocketHttpClientConnection */
public class i extends a implements m {
    private volatile boolean a1;
    private volatile Socket p1 = null;

    /* access modifiers changed from: protected */
    public void s() {
        b.a(!this.a1, "Connection is already open");
    }

    /* access modifiers changed from: protected */
    public void a() {
        b.a(this.a1, "Connection is not open");
    }

    /* access modifiers changed from: protected */
    public h u(Socket socket, int buffersize, HttpParams params) {
        return new t(socket, buffersize, params);
    }

    /* access modifiers changed from: protected */
    public org.apache.http.io.i v(Socket socket, int buffersize, HttpParams params) {
        return new u(socket, buffersize, params);
    }

    /* access modifiers changed from: protected */
    public void t(Socket socket, HttpParams params) {
        a.i(socket, "Socket");
        a.i(params, "HTTP parameters");
        this.p1 = socket;
        int buffersize = params.getIntParameter("http.socket.buffer-size", -1);
        o(u(socket, buffersize, params), v(socket, buffersize, params), params);
        this.a1 = true;
    }

    public boolean isOpen() {
        return this.a1;
    }

    public InetAddress w() {
        if (this.p1 != null) {
            return this.p1.getInetAddress();
        }
        return null;
    }

    public int I0() {
        if (this.p1 != null) {
            return this.p1.getPort();
        }
        return -1;
    }

    public void y(int timeout) {
        a();
        if (this.p1 != null) {
            try {
                this.p1.setSoTimeout(timeout);
            } catch (SocketException e) {
            }
        }
    }

    public void shutdown() {
        this.a1 = false;
        Socket tmpsocket = this.p1;
        if (tmpsocket != null) {
            tmpsocket.close();
        }
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void close() {
        /*
            r2 = this;
            boolean r0 = r2.a1
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            r0 = 0
            r2.a1 = r0
            java.net.Socket r0 = r2.p1
            r2.n()     // Catch:{ all -> 0x001f }
            r0.shutdownOutput()     // Catch:{ IOException -> 0x0013, UnsupportedOperationException -> 0x0011 }
            goto L_0x0014
        L_0x0011:
            r1 = move-exception
            goto L_0x001a
        L_0x0013:
            r1 = move-exception
        L_0x0014:
            r0.shutdownInput()     // Catch:{ IOException -> 0x0018, UnsupportedOperationException -> 0x0011 }
            goto L_0x0019
        L_0x0018:
            r1 = move-exception
        L_0x0019:
        L_0x001a:
            r0.close()
            return
        L_0x001f:
            r1 = move-exception
            r0.close()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.i.close():void");
    }

    private static void x(StringBuilder buffer, SocketAddress socketAddress) {
        if (socketAddress instanceof InetSocketAddress) {
            InetSocketAddress addr = (InetSocketAddress) socketAddress;
            buffer.append(addr.getAddress() != null ? addr.getAddress().getHostAddress() : addr.getAddress());
            buffer.append(':');
            buffer.append(addr.getPort());
            return;
        }
        buffer.append(socketAddress);
    }

    public String toString() {
        if (this.p1 == null) {
            return super.toString();
        }
        StringBuilder buffer = new StringBuilder();
        SocketAddress remoteAddress = this.p1.getRemoteSocketAddress();
        SocketAddress localAddress = this.p1.getLocalSocketAddress();
        if (!(remoteAddress == null || localAddress == null)) {
            x(buffer, localAddress);
            buffer.append("<->");
            x(buffer, remoteAddress);
        }
        return buffer.toString();
    }
}
