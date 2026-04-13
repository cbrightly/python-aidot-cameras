package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.internal.j;
import com.squareup.okhttp.p;
import com.squareup.okhttp.v;
import com.squareup.okhttp.x;
import com.squareup.okhttp.y;
import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import okio.b0;
import okio.d0;
import okio.e0;
import okio.f0;
import okio.l;
import okio.p;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: Http1xStream */
public final class e implements j {
    /* access modifiers changed from: private */
    public final q a;
    /* access modifiers changed from: private */
    public final okio.e b;
    /* access modifiers changed from: private */
    public final okio.d c;
    private h d;
    /* access modifiers changed from: private */
    public int e = 0;

    public e(q streamAllocation, okio.e source, okio.d sink) {
        this.a = streamAllocation;
        this.b = source;
        this.c = sink;
    }

    public void g(h httpEngine) {
        this.d = httpEngine;
    }

    public b0 b(v request, long contentLength) {
        if ("chunked".equalsIgnoreCase(request.h(Constants.TRANSFERENCODING))) {
            return p();
        }
        if (contentLength != -1) {
            return r(contentLength);
        }
        throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
    }

    public void c(v request) {
        this.d.A();
        w(request.i(), m.a(request, this.d.j().e().b().type()));
    }

    public x.b e() {
        return v();
    }

    public y f(x response) {
        return new l(response.s(), p.d(o(response)));
    }

    private e0 o(x response) {
        if (!h.l(response)) {
            return s(0);
        }
        if ("chunked".equalsIgnoreCase(response.q(Constants.TRANSFERENCODING))) {
            return q(this.d);
        }
        long contentLength = k.e(response);
        if (contentLength != -1) {
            return s(contentLength);
        }
        return t();
    }

    public void a() {
        this.c.flush();
    }

    public void w(com.squareup.okhttp.p headers, String requestLine) {
        if (this.e == 0) {
            this.c.writeUtf8(requestLine).writeUtf8("\r\n");
            int size = headers.f();
            for (int i = 0; i < size; i++) {
                this.c.writeUtf8(headers.d(i)).writeUtf8(": ").writeUtf8(headers.g(i)).writeUtf8("\r\n");
            }
            this.c.writeUtf8("\r\n");
            this.e = 1;
            return;
        }
        throw new IllegalStateException("state: " + this.e);
    }

    public x.b v() {
        p statusLine;
        x.b responseBuilder;
        int i = this.e;
        if (i == 1 || i == 3) {
            do {
                try {
                    statusLine = p.a(this.b.d0());
                    responseBuilder = new x.b().x(statusLine.a).q(statusLine.b).u(statusLine.c).t(u());
                } catch (EOFException e2) {
                    IOException exception = new IOException("unexpected end of stream on " + this.a);
                    exception.initCause(e2);
                    throw exception;
                }
            } while (statusLine.b == 100);
            this.e = 4;
            return responseBuilder;
        }
        throw new IllegalStateException("state: " + this.e);
    }

    public com.squareup.okhttp.p u() {
        p.b headers = new p.b();
        while (true) {
            String d0 = this.b.d0();
            String line = d0;
            if (d0.length() == 0) {
                return headers.e();
            }
            com.squareup.okhttp.internal.d.b.a(headers, line);
        }
    }

    public b0 p() {
        if (this.e == 1) {
            this.e = 2;
            return new c();
        }
        throw new IllegalStateException("state: " + this.e);
    }

    public b0 r(long contentLength) {
        if (this.e == 1) {
            this.e = 2;
            return new C0210e(contentLength);
        }
        throw new IllegalStateException("state: " + this.e);
    }

    public void d(n requestBody) {
        if (this.e == 1) {
            this.e = 3;
            requestBody.c(this.c);
            return;
        }
        throw new IllegalStateException("state: " + this.e);
    }

    public e0 s(long length) {
        if (this.e == 4) {
            this.e = 5;
            return new f(length);
        }
        throw new IllegalStateException("state: " + this.e);
    }

    public e0 q(h httpEngine) {
        if (this.e == 4) {
            this.e = 5;
            return new d(httpEngine);
        }
        throw new IllegalStateException("state: " + this.e);
    }

    public e0 t() {
        if (this.e == 4) {
            q qVar = this.a;
            if (qVar != null) {
                this.e = 5;
                qVar.k();
                return new g();
            }
            throw new IllegalStateException("streamAllocation == null");
        }
        throw new IllegalStateException("state: " + this.e);
    }

    /* access modifiers changed from: private */
    public void n(l timeout) {
        f0 oldDelegate = timeout.j();
        timeout.k(f0.a);
        oldDelegate.a();
        oldDelegate.b();
    }

    /* renamed from: com.squareup.okhttp.internal.http.e$e  reason: collision with other inner class name */
    /* compiled from: Http1xStream */
    public final class C0210e implements b0 {
        private final l c;
        private boolean d;
        private long f;

        private C0210e(long bytesRemaining) {
            this.c = new l(e.this.c.timeout());
            this.f = bytesRemaining;
        }

        public f0 timeout() {
            return this.c;
        }

        public void write(okio.c source, long byteCount) {
            if (!this.d) {
                j.a(source.e1(), 0, byteCount);
                if (byteCount <= this.f) {
                    e.this.c.write(source, byteCount);
                    this.f -= byteCount;
                    return;
                }
                throw new ProtocolException("expected " + this.f + " bytes but received " + byteCount);
            }
            throw new IllegalStateException("closed");
        }

        public void flush() {
            if (!this.d) {
                e.this.c.flush();
            }
        }

        public void close() {
            if (!this.d) {
                this.d = true;
                if (this.f <= 0) {
                    e.this.n(this.c);
                    int unused = e.this.e = 3;
                    return;
                }
                throw new ProtocolException("unexpected end of stream");
            }
        }
    }

    /* compiled from: Http1xStream */
    public final class c implements b0 {
        private final l c;
        private boolean d;

        private c() {
            this.c = new l(e.this.c.timeout());
        }

        public f0 timeout() {
            return this.c;
        }

        public void write(okio.c source, long byteCount) {
            if (this.d) {
                throw new IllegalStateException("closed");
            } else if (byteCount != 0) {
                e.this.c.writeHexadecimalUnsignedLong(byteCount);
                e.this.c.writeUtf8("\r\n");
                e.this.c.write(source, byteCount);
                e.this.c.writeUtf8("\r\n");
            }
        }

        public synchronized void flush() {
            if (!this.d) {
                e.this.c.flush();
            }
        }

        public synchronized void close() {
            if (!this.d) {
                this.d = true;
                e.this.c.writeUtf8("0\r\n\r\n");
                e.this.n(this.c);
                int unused = e.this.e = 3;
            }
        }
    }

    /* compiled from: Http1xStream */
    public abstract class b implements e0 {
        protected final l c;
        protected boolean d;

        public /* synthetic */ okio.g cursor() {
            return d0.a(this);
        }

        private b() {
            this.c = new l(e.this.b.timeout());
        }

        public f0 timeout() {
            return this.c;
        }

        /* access modifiers changed from: protected */
        public final void a() {
            if (e.this.e == 5) {
                e.this.n(this.c);
                int unused = e.this.e = 6;
                if (e.this.a != null) {
                    e.this.a.q(e.this);
                    return;
                }
                return;
            }
            throw new IllegalStateException("state: " + e.this.e);
        }

        /* access modifiers changed from: protected */
        public final void c() {
            if (e.this.e != 6) {
                int unused = e.this.e = 6;
                if (e.this.a != null) {
                    e.this.a.k();
                    e.this.a.q(e.this);
                }
            }
        }
    }

    /* compiled from: Http1xStream */
    public class f extends b {
        private long q;

        public f(long length) {
            super();
            this.q = length;
            if (length == 0) {
                a();
            }
        }

        public long read(okio.c sink, long byteCount) {
            if (byteCount < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + byteCount);
            } else if (this.d) {
                throw new IllegalStateException("closed");
            } else if (this.q == 0) {
                return -1;
            } else {
                long read = e.this.b.read(sink, Math.min(this.q, byteCount));
                if (read != -1) {
                    long j = this.q - read;
                    this.q = j;
                    if (j == 0) {
                        a();
                    }
                    return read;
                }
                c();
                throw new ProtocolException("unexpected end of stream");
            }
        }

        public void close() {
            if (!this.d) {
                if (this.q != 0 && !j.g(this, 100, TimeUnit.MILLISECONDS)) {
                    c();
                }
                this.d = true;
            }
        }
    }

    /* compiled from: Http1xStream */
    public class d extends b {
        private long q = -1;
        private boolean x = true;
        private final h y;

        d(h httpEngine) {
            super();
            this.y = httpEngine;
        }

        public long read(okio.c sink, long byteCount) {
            if (byteCount < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + byteCount);
            } else if (this.d) {
                throw new IllegalStateException("closed");
            } else if (!this.x) {
                return -1;
            } else {
                long j = this.q;
                if (j == 0 || j == -1) {
                    g();
                    if (!this.x) {
                        return -1;
                    }
                }
                long read = e.this.b.read(sink, Math.min(byteCount, this.q));
                if (read != -1) {
                    this.q -= read;
                    return read;
                }
                c();
                throw new ProtocolException("unexpected end of stream");
            }
        }

        private void g() {
            if (this.q != -1) {
                e.this.b.d0();
            }
            try {
                this.q = e.this.b.X0();
                String extensions = e.this.b.d0().trim();
                if (this.q < 0 || (!extensions.isEmpty() && !extensions.startsWith(com.meituan.robust.Constants.PACKNAME_END))) {
                    throw new ProtocolException("expected chunk size and optional extensions but was \"" + this.q + extensions + "\"");
                } else if (this.q == 0) {
                    this.x = false;
                    this.y.r(e.this.u());
                    a();
                }
            } catch (NumberFormatException e) {
                throw new ProtocolException(e.getMessage());
            }
        }

        public void close() {
            if (!this.d) {
                if (this.x && !j.g(this, 100, TimeUnit.MILLISECONDS)) {
                    c();
                }
                this.d = true;
            }
        }
    }

    /* compiled from: Http1xStream */
    public class g extends b {
        private boolean q;

        private g() {
            super();
        }

        public long read(okio.c sink, long byteCount) {
            if (byteCount < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + byteCount);
            } else if (this.d) {
                throw new IllegalStateException("closed");
            } else if (this.q) {
                return -1;
            } else {
                long read = e.this.b.read(sink, byteCount);
                if (read != -1) {
                    return read;
                }
                this.q = true;
                a();
                return -1;
            }
        }

        public void close() {
            if (!this.d) {
                if (!this.q) {
                    c();
                }
                this.d = true;
            }
        }
    }
}
