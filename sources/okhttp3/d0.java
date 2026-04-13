package okhttp3;

import com.didichuxing.doraemonkit.okgo.model.Progress;
import java.io.Closeable;
import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.k;
import okhttp3.internal.connection.c;
import okhttp3.u;
import okio.e;
import org.glassfish.tyrus.spi.UpgradeResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Response.kt */
public final class d0 implements Closeable {
    @Nullable
    private final d0 a1;
    @Nullable
    private final d0 a2;
    private d c;
    @NotNull
    private final b0 d;
    @NotNull
    private final a0 f;
    @Nullable
    private final e0 p0;
    @Nullable
    private final d0 p1;
    private final long p2;
    private final long p3;
    @Nullable
    private final c p4;
    @NotNull
    private final String q;
    private final int x;
    @Nullable
    private final t y;
    @NotNull
    private final u z;

    @Nullable
    public final String n(@NotNull String str) {
        return r(this, str, (String) null, 2, (Object) null);
    }

    public d0(@NotNull b0 request, @NotNull a0 protocol, @NotNull String message, int code, @Nullable t handshake, @NotNull u headers, @Nullable e0 body, @Nullable d0 networkResponse, @Nullable d0 cacheResponse, @Nullable d0 priorResponse, long sentRequestAtMillis, long receivedResponseAtMillis, @Nullable c exchange) {
        b0 b0Var = request;
        a0 a0Var = protocol;
        String str = message;
        u uVar = headers;
        k.f(b0Var, Progress.REQUEST);
        k.f(a0Var, "protocol");
        k.f(str, "message");
        k.f(uVar, "headers");
        this.d = b0Var;
        this.f = a0Var;
        this.q = str;
        this.x = code;
        this.y = handshake;
        this.z = uVar;
        this.p0 = body;
        this.a1 = networkResponse;
        this.p1 = cacheResponse;
        this.a2 = priorResponse;
        this.p2 = sentRequestAtMillis;
        this.p3 = receivedResponseAtMillis;
        this.p4 = exchange;
    }

    @NotNull
    public final b0 J() {
        return this.d;
    }

    @NotNull
    public final a0 E() {
        return this.f;
    }

    @NotNull
    public final String t() {
        return this.q;
    }

    public final int j() {
        return this.x;
    }

    @Nullable
    public final t m() {
        return this.y;
    }

    @NotNull
    public final u s() {
        return this.z;
    }

    @Nullable
    public final e0 a() {
        return this.p0;
    }

    @Nullable
    public final d0 u() {
        return this.a1;
    }

    @Nullable
    public final d0 g() {
        return this.p1;
    }

    @Nullable
    public final d0 z() {
        return this.a2;
    }

    public final long P() {
        return this.p2;
    }

    public final long I() {
        return this.p3;
    }

    @Nullable
    public final c l() {
        return this.p4;
    }

    public final boolean h0() {
        int i = this.x;
        return 200 <= i && 299 >= i;
    }

    public static /* synthetic */ String r(d0 d0Var, String str, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = null;
        }
        return d0Var.o(str, str2);
    }

    @Nullable
    public final String o(@NotNull String name, @Nullable String defaultValue) {
        k.f(name, "name");
        String a3 = this.z.a(name);
        return a3 != null ? a3 : defaultValue;
    }

    @NotNull
    public final e0 x(long byteCount) {
        e0 e0Var = this.p0;
        if (e0Var == null) {
            k.n();
        }
        e peeked = e0Var.source().peek();
        okio.c buffer = new okio.c();
        peeked.request(byteCount);
        buffer.write(peeked, Math.min(byteCount, peeked.getBuffer().e1()));
        return e0.Companion.f(buffer, this.p0.contentType(), buffer.e1());
    }

    @NotNull
    public final a v() {
        return new a(this);
    }

    @NotNull
    public final List<h> i() {
        String str;
        u uVar = this.z;
        switch (this.x) {
            case 401:
                str = UpgradeResponse.WWW_AUTHENTICATE;
                break;
            case 407:
                str = "Proxy-Authenticate";
                break;
            default:
                return q.g();
        }
        return okhttp3.internal.http.e.b(uVar, str);
    }

    @NotNull
    public final d c() {
        d result = this.c;
        if (result != null) {
            return result;
        }
        d result2 = d.c.b(this.z);
        this.c = result2;
        return result2;
    }

    public void close() {
        e0 e0Var = this.p0;
        if (e0Var != null) {
            e0Var.close();
            return;
        }
        throw new IllegalStateException("response is not eligible for a body and must not be closed".toString());
    }

    @NotNull
    public String toString() {
        return "Response{protocol=" + this.f + ", code=" + this.x + ", message=" + this.q + ", url=" + this.d.l() + '}';
    }

    /* compiled from: Response.kt */
    public static class a {
        @Nullable
        private b0 a;
        @Nullable
        private a0 b;
        private int c;
        @Nullable
        private String d;
        @Nullable
        private t e;
        @NotNull
        private u.a f;
        @Nullable
        private e0 g;
        @Nullable
        private d0 h;
        @Nullable
        private d0 i;
        @Nullable
        private d0 j;
        private long k;
        private long l;
        @Nullable
        private c m;

        public final int h() {
            return this.c;
        }

        public a() {
            this.c = -1;
            this.f = new u.a();
        }

        public a(@NotNull d0 response) {
            k.f(response, "response");
            this.c = -1;
            this.a = response.J();
            this.b = response.E();
            this.c = response.j();
            this.d = response.t();
            this.e = response.m();
            this.f = response.s().f();
            this.g = response.a();
            this.h = response.u();
            this.i = response.g();
            this.j = response.z();
            this.k = response.P();
            this.l = response.I();
            this.m = response.l();
        }

        @NotNull
        public a r(@NotNull b0 request) {
            k.f(request, Progress.REQUEST);
            this.a = request;
            return this;
        }

        @NotNull
        public a p(@NotNull a0 protocol) {
            k.f(protocol, "protocol");
            this.b = protocol;
            return this;
        }

        @NotNull
        public a g(int code) {
            this.c = code;
            return this;
        }

        @NotNull
        public a m(@NotNull String message) {
            k.f(message, "message");
            this.d = message;
            return this;
        }

        @NotNull
        public a i(@Nullable t handshake) {
            this.e = handshake;
            return this;
        }

        @NotNull
        public a j(@NotNull String name, @NotNull String value) {
            k.f(name, "name");
            k.f(value, "value");
            this.f.j(name, value);
            return this;
        }

        @NotNull
        public a a(@NotNull String name, @NotNull String value) {
            k.f(name, "name");
            k.f(value, "value");
            this.f.a(name, value);
            return this;
        }

        @NotNull
        public a k(@NotNull u headers) {
            k.f(headers, "headers");
            this.f = headers.f();
            return this;
        }

        @NotNull
        public a b(@Nullable e0 body) {
            this.g = body;
            return this;
        }

        @NotNull
        public a n(@Nullable d0 networkResponse) {
            f("networkResponse", networkResponse);
            this.h = networkResponse;
            return this;
        }

        @NotNull
        public a d(@Nullable d0 cacheResponse) {
            f("cacheResponse", cacheResponse);
            this.i = cacheResponse;
            return this;
        }

        private final void f(String name, d0 response) {
            if (response != null) {
                d0 $this$apply = response;
                boolean z = true;
                if ($this$apply.a() == null) {
                    if ($this$apply.u() == null) {
                        if ($this$apply.g() == null) {
                            if ($this$apply.z() != null) {
                                z = false;
                            }
                            if (!z) {
                                throw new IllegalArgumentException((name + ".priorResponse != null").toString());
                            }
                            return;
                        }
                        throw new IllegalArgumentException((name + ".cacheResponse != null").toString());
                    }
                    throw new IllegalArgumentException((name + ".networkResponse != null").toString());
                }
                throw new IllegalArgumentException((name + ".body != null").toString());
            }
        }

        @NotNull
        public a o(@Nullable d0 priorResponse) {
            e(priorResponse);
            this.j = priorResponse;
            return this;
        }

        private final void e(d0 response) {
            if (response != null) {
                if (!(response.a() == null)) {
                    throw new IllegalArgumentException("priorResponse.body != null".toString());
                }
            }
        }

        @NotNull
        public a s(long sentRequestAtMillis) {
            this.k = sentRequestAtMillis;
            return this;
        }

        @NotNull
        public a q(long receivedResponseAtMillis) {
            this.l = receivedResponseAtMillis;
            return this;
        }

        public final void l(@NotNull c deferredTrailers) {
            k.f(deferredTrailers, "deferredTrailers");
            this.m = deferredTrailers;
        }

        @NotNull
        public d0 c() {
            int i2 = this.c;
            if (i2 >= 0) {
                b0 b0Var = this.a;
                if (b0Var != null) {
                    a0 a0Var = this.b;
                    if (a0Var != null) {
                        String str = this.d;
                        if (str != null) {
                            return new d0(b0Var, a0Var, str, i2, this.e, this.f.f(), this.g, this.h, this.i, this.j, this.k, this.l, this.m);
                        }
                        throw new IllegalStateException("message == null".toString());
                    }
                    throw new IllegalStateException("protocol == null".toString());
                }
                throw new IllegalStateException("request == null".toString());
            }
            throw new IllegalStateException(("code < 0: " + this.c).toString());
        }
    }
}
