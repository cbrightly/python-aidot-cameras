package okhttp3.internal.http1;

import com.didichuxing.doraemonkit.okgo.model.Progress;
import io.netty.util.internal.StringUtil;
import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import kotlin.text.x;
import okhttp3.b0;
import okhttp3.d0;
import okhttp3.internal.http.i;
import okhttp3.n;
import okhttp3.u;
import okhttp3.v;
import okhttp3.z;
import okio.e0;
import okio.f0;
import okio.l;
import org.glassfish.grizzly.http.server.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Http1ExchangeCodec.kt */
public final class b implements okhttp3.internal.http.d {
    public static final d b = new d((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public int c;
    /* access modifiers changed from: private */
    public final a d;
    /* access modifiers changed from: private */
    public u e;
    /* access modifiers changed from: private */
    public final z f;
    @NotNull
    private final okhttp3.internal.connection.f g;
    /* access modifiers changed from: private */
    public final okio.e h;
    /* access modifiers changed from: private */
    public final okio.d i;

    public b(@Nullable z client, @NotNull okhttp3.internal.connection.f connection, @NotNull okio.e source, @NotNull okio.d sink) {
        k.f(connection, "connection");
        k.f(source, "source");
        k.f(sink, "sink");
        this.f = client;
        this.g = connection;
        this.h = source;
        this.i = sink;
        this.d = new a(source);
    }

    @NotNull
    public okhttp3.internal.connection.f getConnection() {
        return this.g;
    }

    private final boolean s(@NotNull d0 $this$isChunked) {
        return w.y("chunked", d0.r($this$isChunked, Constants.TRANSFERENCODING, (String) null, 2, (Object) null), true);
    }

    private final boolean r(@NotNull b0 $this$isChunked) {
        return w.y("chunked", $this$isChunked.d(Constants.TRANSFERENCODING), true);
    }

    @NotNull
    public okio.b0 d(@NotNull b0 request, long contentLength) {
        k.f(request, Progress.REQUEST);
        if (request.a() != null && request.a().isDuplex()) {
            throw new ProtocolException("Duplex connections are not supported for HTTP/1");
        } else if (r(request)) {
            return t();
        } else {
            if (contentLength != -1) {
                return w();
            }
            throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
        }
    }

    public void cancel() {
        getConnection().f();
    }

    public void e(@NotNull b0 request) {
        k.f(request, Progress.REQUEST);
        i iVar = i.a;
        Proxy.Type type = getConnection().a().b().type();
        k.b(type, "connection.route().proxy.type()");
        z(request.f(), iVar.a(request, type));
    }

    public long c(@NotNull d0 response) {
        k.f(response, "response");
        if (!okhttp3.internal.http.e.c(response)) {
            return 0;
        }
        if (s(response)) {
            return -1;
        }
        return okhttp3.internal.b.s(response);
    }

    @NotNull
    public e0 b(@NotNull d0 response) {
        k.f(response, "response");
        if (!okhttp3.internal.http.e.c(response)) {
            return v(0);
        }
        if (s(response)) {
            return u(response.J().l());
        }
        long contentLength = okhttp3.internal.b.s(response);
        if (contentLength != -1) {
            return v(contentLength);
        }
        return x();
    }

    public void g() {
        this.i.flush();
    }

    public void a() {
        this.i.flush();
    }

    public final void z(@NotNull u headers, @NotNull String requestLine) {
        k.f(headers, "headers");
        k.f(requestLine, "requestLine");
        if (this.c == 0) {
            this.i.writeUtf8(requestLine).writeUtf8("\r\n");
            int size = headers.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.i.writeUtf8(headers.b(i2)).writeUtf8(": ").writeUtf8(headers.h(i2)).writeUtf8("\r\n");
            }
            this.i.writeUtf8("\r\n");
            this.c = 1;
            return;
        }
        throw new IllegalStateException(("state: " + this.c).toString());
    }

    @Nullable
    public d0.a f(boolean expectContinue) {
        int i2 = this.c;
        boolean z = true;
        if (!(i2 == 1 || i2 == 3)) {
            z = false;
        }
        if (z) {
            try {
                okhttp3.internal.http.k statusLine = okhttp3.internal.http.k.a.a(this.d.b());
                d0.a responseBuilder = new d0.a().p(statusLine.b).g(statusLine.c).m(statusLine.d).k(this.d.a());
                if (expectContinue && statusLine.c == 100) {
                    return null;
                }
                if (statusLine.c == 100) {
                    this.c = 3;
                } else {
                    this.c = 4;
                }
                return responseBuilder;
            } catch (EOFException e2) {
                String address = getConnection().a().a().l().r();
                throw new IOException("unexpected end of stream on " + address, e2);
            }
        } else {
            throw new IllegalStateException(("state: " + this.c).toString());
        }
    }

    private final okio.b0 t() {
        boolean z = true;
        if (this.c != 1) {
            z = false;
        }
        if (z) {
            this.c = 2;
            return new C0463b();
        }
        throw new IllegalStateException(("state: " + this.c).toString());
    }

    private final okio.b0 w() {
        boolean z = true;
        if (this.c != 1) {
            z = false;
        }
        if (z) {
            this.c = 2;
            return new f();
        }
        throw new IllegalStateException(("state: " + this.c).toString());
    }

    private final e0 v(long length) {
        if (this.c == 4) {
            this.c = 5;
            return new e(length);
        }
        throw new IllegalStateException(("state: " + this.c).toString());
    }

    private final e0 u(v url) {
        if (this.c == 4) {
            this.c = 5;
            return new c(this, url);
        }
        throw new IllegalStateException(("state: " + this.c).toString());
    }

    private final e0 x() {
        if (this.c == 4) {
            this.c = 5;
            getConnection().A();
            return new g();
        }
        throw new IllegalStateException(("state: " + this.c).toString());
    }

    /* access modifiers changed from: private */
    public final void q(l timeout) {
        f0 oldDelegate = timeout.j();
        timeout.k(f0.a);
        oldDelegate.a();
        oldDelegate.b();
    }

    public final void y(@NotNull d0 response) {
        k.f(response, "response");
        long contentLength = okhttp3.internal.b.s(response);
        if (contentLength != -1) {
            e0 body = v(contentLength);
            okhttp3.internal.b.J(body, Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
            body.close();
        }
    }

    /* compiled from: Http1ExchangeCodec.kt */
    public final class f implements okio.b0 {
        private final l c;
        private boolean d;

        public f() {
            this.c = new l(b.this.i.timeout());
        }

        @NotNull
        public f0 timeout() {
            return this.c;
        }

        public void write(@NotNull okio.c source, long byteCount) {
            k.f(source, "source");
            if (!this.d) {
                okhttp3.internal.b.i(source.e1(), 0, byteCount);
                b.this.i.write(source, byteCount);
                return;
            }
            throw new IllegalStateException("closed".toString());
        }

        public void flush() {
            if (!this.d) {
                b.this.i.flush();
            }
        }

        public void close() {
            if (!this.d) {
                this.d = true;
                b.this.q(this.c);
                b.this.c = 3;
            }
        }
    }

    /* renamed from: okhttp3.internal.http1.b$b  reason: collision with other inner class name */
    /* compiled from: Http1ExchangeCodec.kt */
    public final class C0463b implements okio.b0 {
        private final l c;
        private boolean d;

        public C0463b() {
            this.c = new l(b.this.i.timeout());
        }

        @NotNull
        public f0 timeout() {
            return this.c;
        }

        public void write(@NotNull okio.c source, long byteCount) {
            k.f(source, "source");
            if (!(!this.d)) {
                throw new IllegalStateException("closed".toString());
            } else if (byteCount != 0) {
                b.this.i.writeHexadecimalUnsignedLong(byteCount);
                b.this.i.writeUtf8("\r\n");
                b.this.i.write(source, byteCount);
                b.this.i.writeUtf8("\r\n");
            }
        }

        public synchronized void flush() {
            if (!this.d) {
                b.this.i.flush();
            }
        }

        public synchronized void close() {
            if (!this.d) {
                this.d = true;
                b.this.i.writeUtf8("0\r\n\r\n");
                b.this.q(this.c);
                b.this.c = 3;
            }
        }
    }

    /* compiled from: Http1ExchangeCodec.kt */
    public abstract class a implements e0 {
        @NotNull
        private final l c;
        private boolean d;

        public /* synthetic */ okio.g cursor() {
            return okio.d0.a(this);
        }

        public a() {
            this.c = new l(b.this.h.timeout());
        }

        /* access modifiers changed from: protected */
        public final boolean a() {
            return this.d;
        }

        /* access modifiers changed from: protected */
        public final void g(boolean z) {
            this.d = z;
        }

        @NotNull
        public f0 timeout() {
            return this.c;
        }

        public long read(@NotNull okio.c sink, long byteCount) {
            k.f(sink, "sink");
            try {
                return b.this.h.read(sink, byteCount);
            } catch (IOException e) {
                b.this.getConnection().A();
                c();
                throw e;
            }
        }

        public final void c() {
            if (b.this.c != 6) {
                if (b.this.c == 5) {
                    b.this.q(this.c);
                    b.this.c = 6;
                    return;
                }
                throw new IllegalStateException("state: " + b.this.c);
            }
        }
    }

    /* compiled from: Http1ExchangeCodec.kt */
    public final class e extends a {
        private long q;

        public e(long bytesRemaining) {
            super();
            this.q = bytesRemaining;
            if (bytesRemaining == 0) {
                c();
            }
        }

        public long read(@NotNull okio.c sink, long byteCount) {
            k.f(sink, "sink");
            if (!(byteCount >= 0)) {
                throw new IllegalArgumentException(("byteCount < 0: " + byteCount).toString());
            } else if (!a()) {
                long j = this.q;
                if (j == 0) {
                    return -1;
                }
                long read = super.read(sink, Math.min(j, byteCount));
                if (read != -1) {
                    long j2 = this.q - read;
                    this.q = j2;
                    if (j2 == 0) {
                        c();
                    }
                    return read;
                }
                b.this.getConnection().A();
                ProtocolException e = new ProtocolException("unexpected end of stream");
                c();
                throw e;
            } else {
                throw new IllegalStateException("closed".toString());
            }
        }

        public void close() {
            if (!a()) {
                if (this.q != 0 && !okhttp3.internal.b.p(this, 100, TimeUnit.MILLISECONDS)) {
                    b.this.getConnection().A();
                    c();
                }
                g(true);
            }
        }
    }

    /* compiled from: Http1ExchangeCodec.kt */
    public final class c extends a {
        private long q = -1;
        private boolean x = true;
        private final v y;
        final /* synthetic */ b z;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public c(@NotNull b $outer, v url) {
            super();
            k.f(url, "url");
            this.z = $outer;
            this.y = url;
        }

        public long read(@NotNull okio.c sink, long byteCount) {
            k.f(sink, "sink");
            if (!(byteCount >= 0)) {
                throw new IllegalArgumentException(("byteCount < 0: " + byteCount).toString());
            } else if (!(!a())) {
                throw new IllegalStateException("closed".toString());
            } else if (!this.x) {
                return -1;
            } else {
                long j = this.q;
                if (j == 0 || j == -1) {
                    i();
                    if (!this.x) {
                        return -1;
                    }
                }
                long read = super.read(sink, Math.min(byteCount, this.q));
                if (read != -1) {
                    this.q -= read;
                    return read;
                }
                this.z.getConnection().A();
                ProtocolException e = new ProtocolException("unexpected end of stream");
                c();
                throw e;
            }
        }

        private final void i() {
            if (this.q != -1) {
                this.z.h.d0();
            }
            try {
                this.q = this.z.h.X0();
                String d0 = this.z.h.d0();
                if (d0 != null) {
                    String extensions = x.e1(d0).toString();
                    if (this.q >= 0) {
                        if (!(extensions.length() > 0) || w.N(extensions, com.meituan.robust.Constants.PACKNAME_END, false, 2, (Object) null)) {
                            if (this.q == 0) {
                                this.x = false;
                                b bVar = this.z;
                                bVar.e = bVar.d.a();
                                z i = this.z.f;
                                if (i == null) {
                                    k.n();
                                }
                                n n = i.n();
                                v vVar = this.y;
                                u n2 = this.z.e;
                                if (n2 == null) {
                                    k.n();
                                }
                                okhttp3.internal.http.e.g(n, vVar, n2);
                                c();
                                return;
                            }
                            return;
                        }
                    }
                    throw new ProtocolException("expected chunk size and optional extensions" + " but was \"" + this.q + extensions + StringUtil.DOUBLE_QUOTE);
                }
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            } catch (NumberFormatException e) {
                throw new ProtocolException(e.getMessage());
            }
        }

        public void close() {
            if (!a()) {
                if (this.x && !okhttp3.internal.b.p(this, 100, TimeUnit.MILLISECONDS)) {
                    this.z.getConnection().A();
                    c();
                }
                g(true);
            }
        }
    }

    /* compiled from: Http1ExchangeCodec.kt */
    public final class g extends a {
        private boolean q;

        public g() {
            super();
        }

        public long read(@NotNull okio.c sink, long byteCount) {
            k.f(sink, "sink");
            if (!(byteCount >= 0)) {
                throw new IllegalArgumentException(("byteCount < 0: " + byteCount).toString());
            } else if (!(!a())) {
                throw new IllegalStateException("closed".toString());
            } else if (this.q) {
                return -1;
            } else {
                long read = super.read(sink, byteCount);
                if (read != -1) {
                    return read;
                }
                this.q = true;
                c();
                return -1;
            }
        }

        public void close() {
            if (!a()) {
                if (!this.q) {
                    c();
                }
                g(true);
            }
        }
    }

    /* compiled from: Http1ExchangeCodec.kt */
    public static final class d {
        private d() {
        }

        public /* synthetic */ d(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
