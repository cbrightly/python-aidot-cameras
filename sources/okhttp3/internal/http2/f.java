package okhttp3.internal.http2;

import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.a0;
import okhttp3.d0;
import okhttp3.internal.b;
import okhttp3.internal.http.d;
import okhttp3.internal.http.e;
import okhttp3.internal.http.g;
import okhttp3.internal.http.i;
import okhttp3.internal.http.k;
import okhttp3.u;
import okhttp3.z;
import okio.b0;
import okio.e0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Http2ExchangeCodec.kt */
public final class f implements d {
    /* access modifiers changed from: private */
    public static final List<String> b = b.t("connection", SerializableCookie.HOST, "keep-alive", "proxy-connection", "te", "transfer-encoding", "encoding", "upgrade", ":method", ":path", ":scheme", ":authority");
    /* access modifiers changed from: private */
    public static final List<String> c = b.t("connection", SerializableCookie.HOST, "keep-alive", "proxy-connection", "te", "transfer-encoding", "encoding", "upgrade");
    public static final a d = new a((DefaultConstructorMarker) null);
    private volatile h e;
    private final a0 f;
    private volatile boolean g;
    @NotNull
    private final okhttp3.internal.connection.f h;
    private final g i;
    private final e j;

    public f(@NotNull z client, @NotNull okhttp3.internal.connection.f connection, @NotNull g chain, @NotNull e http2Connection) {
        k.f(client, "client");
        k.f(connection, "connection");
        k.f(chain, "chain");
        k.f(http2Connection, "http2Connection");
        this.h = connection;
        this.i = chain;
        this.j = http2Connection;
        List<a0> C = client.C();
        a0 a0Var = a0.H2_PRIOR_KNOWLEDGE;
        this.f = !C.contains(a0Var) ? a0.HTTP_2 : a0Var;
    }

    @NotNull
    public okhttp3.internal.connection.f getConnection() {
        return this.h;
    }

    @NotNull
    public b0 d(@NotNull okhttp3.b0 request, long contentLength) {
        k.f(request, Progress.REQUEST);
        h hVar = this.e;
        if (hVar == null) {
            k.n();
        }
        return hVar.n();
    }

    public void e(@NotNull okhttp3.b0 request) {
        k.f(request, Progress.REQUEST);
        if (this.e == null) {
            this.e = this.j.h1(d.a(request), request.a() != null);
            if (this.g) {
                h hVar = this.e;
                if (hVar == null) {
                    k.n();
                }
                hVar.f(a.CANCEL);
                throw new IOException("Canceled");
            }
            h hVar2 = this.e;
            if (hVar2 == null) {
                k.n();
            }
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            hVar2.v().g((long) this.i.i(), timeUnit);
            h hVar3 = this.e;
            if (hVar3 == null) {
                k.n();
            }
            hVar3.E().g((long) this.i.k(), timeUnit);
        }
    }

    public void g() {
        this.j.flush();
    }

    public void a() {
        h hVar = this.e;
        if (hVar == null) {
            k.n();
        }
        hVar.n().close();
    }

    @Nullable
    public d0.a f(boolean expectContinue) {
        h hVar = this.e;
        if (hVar == null) {
            k.n();
        }
        d0.a responseBuilder = d.b(hVar.C(), this.f);
        if (!expectContinue || responseBuilder.h() != 100) {
            return responseBuilder;
        }
        return null;
    }

    public long c(@NotNull d0 response) {
        k.f(response, "response");
        if (!e.c(response)) {
            return 0;
        }
        return b.s(response);
    }

    @NotNull
    public e0 b(@NotNull d0 response) {
        k.f(response, "response");
        h hVar = this.e;
        if (hVar == null) {
            k.n();
        }
        return hVar.p();
    }

    public void cancel() {
        this.g = true;
        h hVar = this.e;
        if (hVar != null) {
            hVar.f(a.CANCEL);
        }
    }

    /* compiled from: Http2ExchangeCodec.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final List<b> a(@NotNull okhttp3.b0 request) {
            k.f(request, Progress.REQUEST);
            u headers = request.f();
            ArrayList result = new ArrayList(headers.size() + 4);
            result.add(new b(b.c, request.h()));
            result.add(new b(b.d, i.a.c(request.l())));
            String host = request.d("Host");
            if (host != null) {
                result.add(new b(b.f, host));
            }
            result.add(new b(b.e, request.l().t()));
            int i = 0;
            int size = headers.size();
            while (i < size) {
                String b = headers.b(i);
                Locale locale = Locale.US;
                k.b(locale, "Locale.US");
                if (b != null) {
                    String name = b.toLowerCase(locale);
                    k.b(name, "(this as java.lang.String).toLowerCase(locale)");
                    if (!f.b.contains(name) || (k.a(name, "te") && k.a(headers.h(i), "trailers"))) {
                        result.add(new b(name, headers.h(i)));
                    }
                    i++;
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
            }
            return result;
        }

        @NotNull
        public final d0.a b(@NotNull u headerBlock, @NotNull a0 protocol) {
            k.f(headerBlock, "headerBlock");
            k.f(protocol, "protocol");
            okhttp3.internal.http.k statusLine = null;
            u.a headersBuilder = new u.a();
            int size = headerBlock.size();
            for (int i = 0; i < size; i++) {
                String name = headerBlock.b(i);
                String value = headerBlock.h(i);
                if (k.a(name, ":status")) {
                    k.a aVar = okhttp3.internal.http.k.a;
                    statusLine = aVar.a("HTTP/1.1 " + value);
                } else if (!f.c.contains(name)) {
                    headersBuilder.d(name, value);
                }
            }
            if (statusLine != null) {
                return new d0.a().p(protocol).g(statusLine.c).m(statusLine.d).k(headersBuilder.f());
            }
            throw new ProtocolException("Expected ':status' header not present");
        }
    }
}
