package org.apache.httpcore.impl;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.httpcore.ConnectionClosedException;
import org.apache.httpcore.config.b;
import org.apache.httpcore.entity.d;
import org.apache.httpcore.h;
import org.apache.httpcore.impl.entity.c;
import org.apache.httpcore.impl.io.e;
import org.apache.httpcore.impl.io.f;
import org.apache.httpcore.impl.io.k;
import org.apache.httpcore.impl.io.l;
import org.apache.httpcore.impl.io.m;
import org.apache.httpcore.impl.io.n;
import org.apache.httpcore.impl.io.o;
import org.apache.httpcore.impl.io.p;
import org.apache.httpcore.io.g;
import org.apache.httpcore.j;

/* compiled from: BHttpConnectionBase */
public class a implements h {
    private final o c;
    private final p d;
    private final b f;
    private final i q;
    private final d x;
    private final d y;
    private final AtomicReference<Socket> z;

    protected a(int bufferSize, int fragmentSizeHint, CharsetDecoder charDecoder, CharsetEncoder charEncoder, b messageConstraints, d incomingContentStrategy, d outgoingContentStrategy) {
        int i = bufferSize;
        b bVar = messageConstraints;
        org.apache.httpcore.util.a.h(bufferSize, "Buffer size");
        l inTransportMetrics = new l();
        l outTransportMetrics = new l();
        this.c = new o(inTransportMetrics, bufferSize, -1, bVar != null ? bVar : b.c, charDecoder);
        int i2 = fragmentSizeHint;
        this.d = new p(outTransportMetrics, bufferSize, fragmentSizeHint, charEncoder);
        this.f = bVar;
        this.q = new i(inTransportMetrics, outTransportMetrics);
        this.x = incomingContentStrategy != null ? incomingContentStrategy : org.apache.httpcore.impl.entity.b.a;
        this.y = outgoingContentStrategy != null ? outgoingContentStrategy : c.a;
        this.z = new AtomicReference<>();
    }

    /* access modifiers changed from: protected */
    public void i() {
        Socket socket = this.z.get();
        if (socket != null) {
            if (!this.c.h()) {
                this.c.c(m(socket));
            }
            if (!this.d.g()) {
                this.d.c(n(socket));
                return;
            }
            return;
        }
        throw new ConnectionClosedException();
    }

    /* access modifiers changed from: protected */
    public InputStream m(Socket socket) {
        return socket.getInputStream();
    }

    /* access modifiers changed from: protected */
    public OutputStream n(Socket socket) {
        return socket.getOutputStream();
    }

    /* access modifiers changed from: protected */
    public void O0(Socket socket) {
        org.apache.httpcore.util.a.g(socket, "Socket");
        this.z.set(socket);
        this.c.c((InputStream) null);
        this.d.c((OutputStream) null);
    }

    /* access modifiers changed from: protected */
    public g j() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public org.apache.httpcore.io.h l() {
        return this.d;
    }

    /* access modifiers changed from: protected */
    public void g() {
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
    public OutputStream c(long len, org.apache.httpcore.io.h outbuffer) {
        if (len == -2) {
            return new org.apache.httpcore.impl.io.d(2048, outbuffer);
        }
        if (len == -1) {
            return new n(outbuffer);
        }
        return new f(outbuffer, len);
    }

    /* access modifiers changed from: protected */
    public OutputStream t(org.apache.httpcore.l message) {
        return c(this.y.a(message), this.d);
    }

    /* access modifiers changed from: protected */
    public InputStream a(long len, g inBuffer) {
        if (len == -2) {
            return new org.apache.httpcore.impl.io.c(inBuffer, this.f);
        }
        if (len == -1) {
            return new m(inBuffer);
        }
        if (len == 0) {
            return k.c;
        }
        return new e(inBuffer, len);
    }

    /* access modifiers changed from: protected */
    public j s(org.apache.httpcore.l message) {
        org.apache.httpcore.entity.b entity = new org.apache.httpcore.entity.b();
        long len = this.x.a(message);
        InputStream inStream = a(len, this.c);
        if (len == -2) {
            entity.a(true);
            entity.f(-1);
            entity.e(inStream);
        } else if (len == -1) {
            entity.a(false);
            entity.f(-1);
            entity.e(inStream);
        } else {
            entity.a(false);
            entity.f(len);
            entity.e(inStream);
        }
        org.apache.httpcore.e contentTypeHeader = message.u("Content-Type");
        if (contentTypeHeader != null) {
            entity.d(contentTypeHeader);
        }
        org.apache.httpcore.e contentEncodingHeader = message.u(HttpHeaders.HEAD_KEY_CONTENT_ENCODING);
        if (contentEncodingHeader != null) {
            entity.b(contentEncodingHeader);
        }
        return entity;
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
            org.apache.httpcore.impl.io.o r1 = r2.c     // Catch:{ all -> 0x0026 }
            r1.d()     // Catch:{ all -> 0x0026 }
            org.apache.httpcore.impl.io.p r1 = r2.d     // Catch:{ all -> 0x0026 }
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
        throw new UnsupportedOperationException("Method not decompiled: org.apache.httpcore.impl.a.close():void");
    }

    /* access modifiers changed from: protected */
    public void o() {
        this.q.a();
    }

    /* access modifiers changed from: protected */
    public void r() {
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
            org.apache.httpcore.util.h.a(buffer, localAddress);
            buffer.append("<->");
            org.apache.httpcore.util.h.a(buffer, remoteAddress);
        }
        return buffer.toString();
    }
}
