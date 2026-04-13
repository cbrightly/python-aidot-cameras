package org.apache.http.impl;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.http.ConnectionClosedException;
import org.apache.http.config.c;
import org.apache.http.entity.e;
import org.apache.http.i;
import org.apache.http.impl.entity.d;
import org.apache.http.impl.io.f;
import org.apache.http.impl.io.g;
import org.apache.http.impl.io.o;
import org.apache.http.impl.io.p;
import org.apache.http.impl.io.q;
import org.apache.http.impl.io.r;
import org.apache.http.impl.io.s;
import org.apache.http.io.h;
import org.apache.http.j;
import org.apache.http.m;
import org.apache.http.n;
import org.apache.http.util.a;

/* compiled from: BHttpConnectionBase */
public class b implements i, m {
    private final r c;
    private final s d;
    private final c f;
    private final g q;
    private final e x;
    private final e y;
    private final AtomicReference<Socket> z;

    protected b(int buffersize, int fragmentSizeHint, CharsetDecoder chardecoder, CharsetEncoder charencoder, c messageConstraints, e incomingContentStrategy, e outgoingContentStrategy) {
        int i = buffersize;
        c cVar = messageConstraints;
        a.j(buffersize, "Buffer size");
        o inTransportMetrics = new o();
        o outTransportMetrics = new o();
        this.c = new r(inTransportMetrics, buffersize, -1, cVar != null ? cVar : c.c, chardecoder);
        int i2 = fragmentSizeHint;
        this.d = new s(outTransportMetrics, buffersize, fragmentSizeHint, charencoder);
        this.f = cVar;
        this.q = new g(inTransportMetrics, outTransportMetrics);
        this.x = incomingContentStrategy != null ? incomingContentStrategy : org.apache.http.impl.entity.c.a;
        this.y = outgoingContentStrategy != null ? outgoingContentStrategy : d.a;
        this.z = new AtomicReference<>();
    }

    /* access modifiers changed from: protected */
    public void j() {
        Socket socket = this.z.get();
        if (socket != null) {
            if (!this.c.i()) {
                this.c.d(o(socket));
            }
            if (!this.d.g()) {
                this.d.c(r(socket));
                return;
            }
            return;
        }
        throw new ConnectionClosedException("Connection is closed");
    }

    /* access modifiers changed from: protected */
    public InputStream o(Socket socket) {
        return socket.getInputStream();
    }

    /* access modifiers changed from: protected */
    public OutputStream r(Socket socket) {
        return socket.getOutputStream();
    }

    /* access modifiers changed from: protected */
    public void O0(Socket socket) {
        a.i(socket, "Socket");
        this.z.set(socket);
        this.c.d((InputStream) null);
        this.d.c((OutputStream) null);
    }

    /* access modifiers changed from: protected */
    public h m() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public org.apache.http.io.i n() {
        return this.d;
    }

    /* access modifiers changed from: protected */
    public void i() {
        this.d.flush();
    }

    public boolean isOpen() {
        return this.z.get() != null;
    }

    /* access modifiers changed from: protected */
    public Socket q() {
        return this.z.get();
    }

    /* access modifiers changed from: protected */
    public OutputStream g(long len, org.apache.http.io.i outbuffer) {
        if (len == -2) {
            return new f(2048, outbuffer);
        }
        if (len == -1) {
            return new q(outbuffer);
        }
        return new org.apache.http.impl.io.h(outbuffer, len);
    }

    /* access modifiers changed from: protected */
    public OutputStream v(n message) {
        return g(this.y.a(message), this.d);
    }

    /* access modifiers changed from: protected */
    public InputStream c(long len, h inbuffer) {
        if (len == -2) {
            return new org.apache.http.impl.io.e(inbuffer, this.f);
        }
        if (len == -1) {
            return new p(inbuffer);
        }
        if (len == 0) {
            return org.apache.http.impl.io.m.c;
        }
        return new g(inbuffer, len);
    }

    /* access modifiers changed from: protected */
    public j u(n message) {
        org.apache.http.entity.b entity = new org.apache.http.entity.b();
        long len = this.x.a(message);
        InputStream instream = c(len, this.c);
        if (len == -2) {
            entity.setChunked(true);
            entity.setContentLength(-1);
            entity.setContent(instream);
        } else if (len == -1) {
            entity.setChunked(false);
            entity.setContentLength(-1);
            entity.setContent(instream);
        } else {
            entity.setChunked(false);
            entity.setContentLength(len);
            entity.setContent(instream);
        }
        org.apache.http.d contentTypeHeader = message.u("Content-Type");
        if (contentTypeHeader != null) {
            entity.setContentType(contentTypeHeader);
        }
        org.apache.http.d contentEncodingHeader = message.u(HttpHeaders.HEAD_KEY_CONTENT_ENCODING);
        if (contentEncodingHeader != null) {
            entity.setContentEncoding(contentEncodingHeader);
        }
        return entity;
    }

    public InetAddress w() {
        Socket socket = this.z.get();
        if (socket != null) {
            return socket.getInetAddress();
        }
        return null;
    }

    public int I0() {
        Socket socket = this.z.get();
        if (socket != null) {
            return socket.getPort();
        }
        return -1;
    }

    public void y(int timeout) {
        Socket socket = this.z.get();
        if (socket != null) {
            try {
                socket.setSoTimeout(timeout);
            } catch (SocketException e) {
            }
        }
    }

    public void shutdown() {
        Socket socket = this.z.getAndSet((Object) null);
        if (socket != null) {
            try {
                socket.setSoLinger(true, 0);
            } catch (IOException e) {
            } catch (Throwable th) {
                socket.close();
                throw th;
            }
            socket.close();
        }
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void close() {
        /*
            r2 = this;
            java.util.concurrent.atomic.AtomicReference<java.net.Socket> r0 = r2.z
            r1 = 0
            java.lang.Object r0 = r0.getAndSet(r1)
            java.net.Socket r0 = (java.net.Socket) r0
            if (r0 == 0) goto L_0x002b
            org.apache.http.impl.io.r r1 = r2.c     // Catch:{ all -> 0x0026 }
            r1.e()     // Catch:{ all -> 0x0026 }
            org.apache.http.impl.io.s r1 = r2.d     // Catch:{ all -> 0x0026 }
            r1.flush()     // Catch:{ all -> 0x0026 }
            r0.shutdownOutput()     // Catch:{ IOException -> 0x001b, UnsupportedOperationException -> 0x0019 }
            goto L_0x001c
        L_0x0019:
            r1 = move-exception
            goto L_0x0022
        L_0x001b:
            r1 = move-exception
        L_0x001c:
            r0.shutdownInput()     // Catch:{ IOException -> 0x0020, UnsupportedOperationException -> 0x0019 }
            goto L_0x0021
        L_0x0020:
            r1 = move-exception
        L_0x0021:
        L_0x0022:
            r0.close()
            goto L_0x002b
        L_0x0026:
            r1 = move-exception
            r0.close()
            throw r1
        L_0x002b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.b.close():void");
    }

    /* JADX INFO: finally extract failed */
    private int l(int timeout) {
        Socket socket = this.z.get();
        int oldtimeout = socket.getSoTimeout();
        try {
            socket.setSoTimeout(timeout);
            int f2 = this.c.f();
            socket.setSoTimeout(oldtimeout);
            return f2;
        } catch (Throwable th) {
            socket.setSoTimeout(oldtimeout);
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public boolean a(int timeout) {
        if (this.c.h()) {
            return true;
        }
        l(timeout);
        return this.c.h();
    }

    public boolean l0() {
        if (!isOpen()) {
            return true;
        }
        try {
            if (l(1) < 0) {
                return true;
            }
            return false;
        } catch (SocketTimeoutException e) {
            return false;
        } catch (IOException e2) {
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void s() {
        this.q.a();
    }

    /* access modifiers changed from: protected */
    public void t() {
        this.q.b();
    }

    public String toString() {
        Socket socket = this.z.get();
        if (socket == null) {
            return "[Not bound]";
        }
        StringBuilder buffer = new StringBuilder();
        SocketAddress remoteAddress = socket.getRemoteSocketAddress();
        SocketAddress localAddress = socket.getLocalSocketAddress();
        if (!(remoteAddress == null || localAddress == null)) {
            org.apache.http.util.i.a(buffer, localAddress);
            buffer.append("<->");
            org.apache.http.util.i.a(buffer, remoteAddress);
        }
        return buffer.toString();
    }
}
