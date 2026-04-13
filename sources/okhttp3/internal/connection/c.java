package okhttp3.internal.connection;

import androidx.core.app.NotificationCompat;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import java.io.IOException;
import java.net.ProtocolException;
import kotlin.jvm.internal.k;
import okhttp3.b0;
import okhttp3.c0;
import okhttp3.d0;
import okhttp3.e0;
import okhttp3.internal.http.d;
import okhttp3.internal.http.h;
import okhttp3.internal.ws.d;
import okhttp3.r;
import okio.j;
import okio.p;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Exchange.kt */
public final class c {
    private boolean a;
    @NotNull
    private final f b;
    @NotNull
    private final e c;
    @NotNull
    private final r d;
    @NotNull
    private final d e;
    private final d f;

    public c(@NotNull e call, @NotNull r eventListener, @NotNull d finder, @NotNull d codec) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(eventListener, "eventListener");
        k.f(finder, "finder");
        k.f(codec, "codec");
        this.c = call;
        this.d = eventListener;
        this.e = finder;
        this.f = codec;
        this.b = codec.getConnection();
    }

    @NotNull
    public final e g() {
        return this.c;
    }

    @NotNull
    public final r i() {
        return this.d;
    }

    @NotNull
    public final d j() {
        return this.e;
    }

    public final boolean l() {
        return this.a;
    }

    @NotNull
    public final f h() {
        return this.b;
    }

    public final boolean k() {
        return !k.a(this.e.d().l().j(), this.b.a().a().l().j());
    }

    public final void v(@NotNull b0 request) {
        k.f(request, Progress.REQUEST);
        try {
            this.d.u(this.c);
            this.f.e(request);
            this.d.t(this.c, request);
        } catch (IOException e2) {
            this.d.s(this.c, e2);
            t(e2);
            throw e2;
        }
    }

    @NotNull
    public final okio.b0 c(@NotNull b0 request, boolean duplex) {
        k.f(request, Progress.REQUEST);
        this.a = duplex;
        c0 a2 = request.a();
        if (a2 == null) {
            k.n();
        }
        long contentLength = a2.contentLength();
        this.d.r(this.c);
        return new a(this, this.f.d(request, contentLength), contentLength);
    }

    public final void f() {
        try {
            this.f.g();
        } catch (IOException e2) {
            this.d.s(this.c, e2);
            t(e2);
            throw e2;
        }
    }

    public final void e() {
        try {
            this.f.a();
        } catch (IOException e2) {
            this.d.s(this.c, e2);
            t(e2);
            throw e2;
        }
    }

    public final void s() {
        this.d.z(this.c);
    }

    @Nullable
    public final d0.a q(boolean expectContinue) {
        try {
            d0.a result = this.f.f(expectContinue);
            if (result != null) {
                result.l(this);
            }
            return result;
        } catch (IOException e2) {
            this.d.x(this.c, e2);
            t(e2);
            throw e2;
        }
    }

    public final void r(@NotNull d0 response) {
        k.f(response, "response");
        this.d.y(this.c, response);
    }

    @NotNull
    public final e0 p(@NotNull d0 response) {
        k.f(response, "response");
        try {
            String contentType = d0.r(response, "Content-Type", (String) null, 2, (Object) null);
            long contentLength = this.f.c(response);
            return new h(contentType, contentLength, p.d(new b(this, this.f.b(response), contentLength)));
        } catch (IOException e2) {
            this.d.x(this.c, e2);
            t(e2);
            throw e2;
        }
    }

    @NotNull
    public final d.C0472d m() {
        this.c.z();
        return this.f.getConnection().y(this);
    }

    public final void u() {
        a(-1, true, true, (IOException) null);
    }

    public final void n() {
        this.f.getConnection().A();
    }

    public final void b() {
        this.f.cancel();
    }

    public final void d() {
        this.f.cancel();
        this.c.s(this, true, true, null);
    }

    private final void t(IOException e2) {
        this.e.h(e2);
        this.f.getConnection().H(this.c, e2);
    }

    public final <E extends IOException> E a(long bytesRead, boolean responseDone, boolean requestDone, E e2) {
        if (e2 != null) {
            t(e2);
        }
        if (requestDone) {
            if (e2 != null) {
                this.d.s(this.c, e2);
            } else {
                this.d.q(this.c, bytesRead);
            }
        }
        if (responseDone) {
            if (e2 != null) {
                this.d.x(this.c, e2);
            } else {
                this.d.v(this.c, bytesRead);
            }
        }
        return this.c.s(this, requestDone, responseDone, e2);
    }

    public final void o() {
        this.c.s(this, true, false, null);
    }

    /* compiled from: Exchange.kt */
    public final class a extends j {
        private boolean c;
        private long d;
        private boolean f;
        private final long q;
        final /* synthetic */ c x;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull c $outer, okio.b0 delegate, long contentLength) {
            super(delegate);
            k.f(delegate, "delegate");
            this.x = $outer;
            this.q = contentLength;
        }

        public void write(@NotNull okio.c source, long byteCount) {
            k.f(source, "source");
            if (!this.f) {
                long j = this.q;
                if (j == -1 || this.d + byteCount <= j) {
                    try {
                        super.write(source, byteCount);
                        this.d += byteCount;
                    } catch (IOException e) {
                        throw a(e);
                    }
                } else {
                    throw new ProtocolException("expected " + this.q + " bytes but received " + (this.d + byteCount));
                }
            } else {
                throw new IllegalStateException("closed".toString());
            }
        }

        public void flush() {
            try {
                super.flush();
            } catch (IOException e) {
                throw a(e);
            }
        }

        public void close() {
            if (!this.f) {
                this.f = true;
                long j = this.q;
                if (j == -1 || this.d == j) {
                    try {
                        super.close();
                        a((IOException) null);
                    } catch (IOException e) {
                        throw a(e);
                    }
                } else {
                    throw new ProtocolException("unexpected end of stream");
                }
            }
        }

        private final <E extends IOException> E a(E e) {
            if (this.c) {
                return e;
            }
            this.c = true;
            return this.x.a(this.d, false, true, e);
        }
    }

    /* compiled from: Exchange.kt */
    public final class b extends okio.k {
        private long c;
        private boolean d = true;
        private boolean f;
        private boolean q;
        private final long x;
        final /* synthetic */ c y;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull c $outer, okio.e0 delegate, long contentLength) {
            super(delegate);
            k.f(delegate, "delegate");
            this.y = $outer;
            this.x = contentLength;
            if (contentLength == 0) {
                a((IOException) null);
            }
        }

        public long read(@NotNull okio.c sink, long byteCount) {
            k.f(sink, "sink");
            if (!this.q) {
                try {
                    long read = delegate().read(sink, byteCount);
                    if (this.d) {
                        this.d = false;
                        this.y.i().w(this.y.g());
                    }
                    if (read == -1) {
                        a((IOException) null);
                        return -1;
                    }
                    long newBytesReceived = this.c + read;
                    long j = this.x;
                    if (j != -1) {
                        if (newBytesReceived > j) {
                            throw new ProtocolException("expected " + this.x + " bytes but received " + newBytesReceived);
                        }
                    }
                    this.c = newBytesReceived;
                    if (newBytesReceived == j) {
                        a((IOException) null);
                    }
                    return read;
                } catch (IOException e) {
                    throw a(e);
                }
            } else {
                throw new IllegalStateException("closed".toString());
            }
        }

        public void close() {
            if (!this.q) {
                this.q = true;
                try {
                    super.close();
                    a((IOException) null);
                } catch (IOException e) {
                    throw a(e);
                }
            }
        }

        public final <E extends IOException> E a(E e) {
            if (this.f) {
                return e;
            }
            this.f = true;
            if (e == null && this.d) {
                this.d = false;
                this.y.i().w(this.y.g());
            }
            return this.y.a(this.c, true, false, e);
        }
    }
}
