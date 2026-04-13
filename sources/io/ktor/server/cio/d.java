package io.ktor.server.cio;

import androidx.core.app.NotificationCompat;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import io.ktor.http.cio.m;
import io.ktor.http.d0;
import io.ktor.http.e0;
import io.ktor.http.o;
import io.ktor.http.u;
import io.ktor.http.y;
import io.ktor.server.engine.i;
import java.net.InetSocketAddress;
import kotlin.g;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CIOApplicationRequest.kt */
public final class d extends i {
    @NotNull
    private final g c;
    @NotNull
    private final o d;
    @NotNull
    private final g e;
    @NotNull
    private final e0 f = new b(this);
    private final InetSocketAddress g;
    /* access modifiers changed from: private */
    public final InetSocketAddress h;
    private final io.ktor.utils.io.i i;
    /* access modifiers changed from: private */
    public final m j;

    @NotNull
    public y e() {
        return (y) this.e.getValue();
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public d(@NotNull io.ktor.application.b call, @Nullable InetSocketAddress remoteAddress, @Nullable InetSocketAddress localAddress, @NotNull io.ktor.utils.io.i input, @NotNull m request) {
        super(call);
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(input, "input");
        k.f(request, Progress.REQUEST);
        this.g = remoteAddress;
        this.h = localAddress;
        this.i = input;
        this.j = request;
        kotlin.k kVar = kotlin.k.NONE;
        this.c = kotlin.i.a(kVar, new a(this));
        this.d = new io.ktor.http.cio.a(request.a());
        this.e = kotlin.i.a(kVar, new c(this));
    }

    /* compiled from: CIOApplicationRequest.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<io.ktor.request.g> {
        final /* synthetic */ d this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(d dVar) {
            super(0);
            this.this$0 = dVar;
        }

        @NotNull
        public final io.ktor.request.g invoke() {
            return new io.ktor.request.g(this.this$0);
        }
    }

    @NotNull
    public io.ktor.utils.io.i c() {
        return this.i;
    }

    @NotNull
    public o getHeaders() {
        return this.d;
    }

    /* compiled from: CIOApplicationRequest.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<y> {
        final /* synthetic */ d this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(d dVar) {
            super(0);
            this.this$0 = dVar;
        }

        @NotNull
        public final y invoke() {
            CharSequence uri = this.this$0.j.g();
            int qIdx = x.e0(uri, '?', 0, false, 6, (Object) null);
            if (qIdx == -1 || qIdx == x.Z(uri)) {
                return y.b.a();
            }
            return d0.d(uri.subSequence(qIdx + 1, uri.length()).toString(), 0, 0, 6, (Object) null);
        }
    }

    /* compiled from: CIOApplicationRequest.kt */
    public static final class b implements e0 {
        final /* synthetic */ d a;

        b(d $outer) {
            this.a = $outer;
        }

        @NotNull
        public String c() {
            return org.apache.http.l.DEFAULT_SCHEME_NAME;
        }

        @NotNull
        public String getUri() {
            return this.a.j.g().toString();
        }

        /* JADX WARNING: Removed duplicated region for block: B:13:0x0044 A[ORIG_RETURN, RETURN, SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
        @org.jetbrains.annotations.NotNull
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.String b() {
            /*
                r5 = this;
                io.ktor.server.cio.d r0 = r5.a
                java.net.InetSocketAddress r0 = r0.h
                r1 = 0
                if (r0 == 0) goto L_0x0022
                r2 = 0
                java.lang.String r3 = r0.getHostName()
                if (r3 == 0) goto L_0x0011
                goto L_0x001e
            L_0x0011:
                java.net.InetAddress r3 = r0.getAddress()
                java.lang.String r4 = "it.address"
                kotlin.jvm.internal.k.b(r3, r4)
                java.lang.String r3 = r3.getHostAddress()
            L_0x001e:
                if (r3 == 0) goto L_0x0022
                r1 = r3
                goto L_0x0041
            L_0x0022:
                io.ktor.server.cio.d r0 = r5.a
                io.ktor.http.cio.m r0 = r0.j
                io.ktor.http.cio.f r0 = r0.a()
                java.lang.String r2 = "Host"
                java.lang.CharSequence r0 = r0.e(r2)
                if (r0 == 0) goto L_0x0041
                java.lang.String r0 = r0.toString()
                if (r0 == 0) goto L_0x0041
                r2 = 2
                java.lang.String r3 = ":"
                java.lang.String r1 = kotlin.text.x.Z0(r0, r3, r1, r2, r1)
            L_0x0041:
                if (r1 == 0) goto L_0x0044
                goto L_0x0046
            L_0x0044:
                java.lang.String r1 = "localhost"
            L_0x0046:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.cio.d.b.b():java.lang.String");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:7:0x002b, code lost:
            r0 = kotlin.text.x.P0((r0 = r0.toString()), ":", "80");
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int a() {
            /*
                r3 = this;
                io.ktor.server.cio.d r0 = r3.a
                java.net.InetSocketAddress r0 = r0.h
                if (r0 == 0) goto L_0x0011
                int r0 = r0.getPort()
                java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
                goto L_0x003f
            L_0x0011:
                io.ktor.server.cio.d r0 = r3.a
                io.ktor.http.cio.m r0 = r0.j
                io.ktor.http.cio.f r0 = r0.a()
                java.lang.String r1 = "Host"
                java.lang.CharSequence r0 = r0.e(r1)
                if (r0 == 0) goto L_0x003e
                java.lang.String r0 = r0.toString()
                if (r0 == 0) goto L_0x003e
                java.lang.String r1 = ":"
                java.lang.String r2 = "80"
                java.lang.String r0 = kotlin.text.x.P0(r0, r1, r2)
                if (r0 == 0) goto L_0x003e
                int r0 = java.lang.Integer.parseInt(r0)
                java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
                goto L_0x003f
            L_0x003e:
                r0 = 0
            L_0x003f:
                if (r0 == 0) goto L_0x0046
                int r0 = r0.intValue()
                goto L_0x0048
            L_0x0046:
                r0 = 80
            L_0x0048:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.cio.d.b.a():int");
        }

        @NotNull
        public u getMethod() {
            return u.i.i(this.a.j.c().i());
        }
    }

    @NotNull
    public e0 b() {
        return this.f;
    }

    public final void h() {
        this.j.release();
    }
}
