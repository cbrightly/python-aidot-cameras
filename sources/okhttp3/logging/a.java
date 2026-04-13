package okhttp3.logging;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import kotlin.collections.o0;
import kotlin.collections.v;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.d0;
import kotlin.jvm.internal.k;
import okhttp3.b0;
import okhttp3.c0;
import okhttp3.e0;
import okhttp3.internal.http.e;
import okhttp3.j;
import okhttp3.u;
import okhttp3.w;
import okhttp3.x;
import okio.c;
import okio.m;
import org.glassfish.grizzly.http.GZipContentEncoding;
import org.jetbrains.annotations.NotNull;

/* compiled from: HttpLoggingInterceptor.kt */
public final class a implements w {
    private volatile Set<String> b;
    @NotNull
    private volatile C0473a c;
    private final b d;

    /* renamed from: okhttp3.logging.a$a  reason: collision with other inner class name */
    /* compiled from: HttpLoggingInterceptor.kt */
    public enum C0473a {
        NONE,
        BASIC,
        HEADERS,
        BODY
    }

    public a(@NotNull b logger) {
        k.f(logger, "logger");
        this.d = logger;
        this.b = o0.d();
        this.c = C0473a.NONE;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ a(b bVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? b.a : bVar);
    }

    /* compiled from: HttpLoggingInterceptor.kt */
    public interface b {
        @NotNull
        public static final b a = new b$a();
        public static final C0474a b = new C0474a((DefaultConstructorMarker) null);

        void a(@NotNull String str);

        /* renamed from: okhttp3.logging.a$b$a  reason: collision with other inner class name */
        /* compiled from: HttpLoggingInterceptor.kt */
        public static final class C0474a {
            private C0474a() {
            }

            public /* synthetic */ C0474a(DefaultConstructorMarker $constructor_marker) {
                this();
            }
        }
    }

    public final void c(@NotNull String name) {
        k.f(name, "name");
        TreeSet newHeadersToRedact = new TreeSet(kotlin.text.w.z(d0.a));
        v.x(newHeadersToRedact, this.b);
        newHeadersToRedact.add(name);
        this.b = newHeadersToRedact;
    }

    @NotNull
    public final a d(@NotNull C0473a level) {
        k.f(level, FirebaseAnalytics.Param.LEVEL);
        this.c = level;
        return this;
    }

    @NotNull
    public okhttp3.d0 intercept(@NotNull w.a chain) {
        String str;
        String str2;
        String requestStartMessage;
        String bodySize;
        String str3;
        String bodySize2;
        long contentLength;
        char c2;
        String str4;
        String str5;
        String str6;
        Charset charset;
        Throwable th;
        j connection;
        Charset charset2;
        w.a aVar = chain;
        k.f(aVar, "chain");
        C0473a level = this.c;
        b0 request = chain.g();
        if (level == C0473a.NONE) {
            return aVar.a(request);
        }
        boolean logBody = level == C0473a.BODY;
        boolean logHeaders = logBody || level == C0473a.HEADERS;
        c0 requestBody = request.a();
        j connection2 = chain.b();
        StringBuilder sb = new StringBuilder();
        sb.append("--> ");
        sb.append(request.h());
        sb.append(' ');
        sb.append(request.l());
        if (connection2 != null) {
            str = " " + connection2.protocol();
        } else {
            str = "";
        }
        sb.append(str);
        String requestStartMessage2 = sb.toString();
        if (logHeaders || requestBody == null) {
            str2 = "";
            requestStartMessage = requestStartMessage2;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(requestStartMessage2);
            sb2.append(" (");
            str2 = "";
            sb2.append(requestBody.contentLength());
            sb2.append("-byte body)");
            requestStartMessage = sb2.toString();
        }
        this.d.a(requestStartMessage);
        if (logHeaders) {
            u headers = request.f();
            if (requestBody != null) {
                x contentType = requestBody.contentType();
                if (contentType != null) {
                    x it = contentType;
                    if (headers.a("Content-Type") == null) {
                        b bVar = this.d;
                        StringBuilder sb3 = new StringBuilder();
                        C0473a aVar2 = level;
                        sb3.append("Content-Type: ");
                        sb3.append(it);
                        bVar.a(sb3.toString());
                    } else {
                        x xVar = it;
                    }
                }
                if (requestBody.contentLength() == -1) {
                    connection = connection2;
                    String str7 = requestStartMessage;
                } else if (headers.a("Content-Length") == null) {
                    b bVar2 = this.d;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("Content-Length: ");
                    connection = connection2;
                    String str8 = requestStartMessage;
                    sb4.append(requestBody.contentLength());
                    bVar2.a(sb4.toString());
                } else {
                    connection = connection2;
                    String str9 = requestStartMessage;
                }
            } else {
                connection = connection2;
                String str10 = requestStartMessage;
            }
            int size = headers.size();
            for (int i = 0; i < size; i++) {
                b(headers, i);
            }
            if (!logBody) {
                bodySize = str2;
                u uVar = headers;
                str3 = "UTF_8";
            } else if (requestBody == null) {
                j jVar = connection;
                bodySize = str2;
                u uVar2 = headers;
                str3 = "UTF_8";
            } else if (a(request.f())) {
                this.d.a("--> END " + request.h() + " (encoded body omitted)");
                j jVar2 = connection;
                str3 = "UTF_8";
                bodySize = str2;
            } else if (requestBody.isDuplex()) {
                this.d.a("--> END " + request.h() + " (duplex request body omitted)");
                j jVar3 = connection;
                str3 = "UTF_8";
                bodySize = str2;
            } else if (requestBody.isOneShot()) {
                this.d.a("--> END " + request.h() + " (one-shot body omitted)");
                j jVar4 = connection;
                str3 = "UTF_8";
                bodySize = str2;
            } else {
                c buffer = new c();
                requestBody.writeTo(buffer);
                x contentType2 = requestBody.contentType();
                if (contentType2 == null || (charset2 = contentType2.d(StandardCharsets.UTF_8)) == null) {
                    charset2 = StandardCharsets.UTF_8;
                    k.b(charset2, "UTF_8");
                }
                j jVar5 = connection;
                x xVar2 = contentType2;
                String str11 = str2;
                this.d.a(str11);
                if (c.a(buffer)) {
                    u uVar3 = headers;
                    this.d.a(buffer.x0(charset2));
                    b bVar3 = this.d;
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("--> END ");
                    sb5.append(request.h());
                    sb5.append(" (");
                    bodySize = str11;
                    Charset charset3 = charset2;
                    sb5.append(requestBody.contentLength());
                    sb5.append("-byte body)");
                    bVar3.a(sb5.toString());
                    str3 = "UTF_8";
                } else {
                    bodySize = str11;
                    Charset charset4 = charset2;
                    u uVar4 = headers;
                    b bVar4 = this.d;
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append("--> END ");
                    sb6.append(request.h());
                    sb6.append(" (binary ");
                    str3 = "UTF_8";
                    sb6.append(requestBody.contentLength());
                    sb6.append("-byte body omitted)");
                    bVar4.a(sb6.toString());
                }
            }
            this.d.a("--> END " + request.h());
        } else {
            j jVar6 = connection2;
            String str12 = requestStartMessage;
            str3 = "UTF_8";
            bodySize = str2;
        }
        long startNs = System.nanoTime();
        try {
            okhttp3.d0 response = aVar.a(request);
            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
            e0 responseBody = response.a();
            if (responseBody == null) {
                k.n();
            }
            b0 b0Var = request;
            long j = startNs;
            long contentLength2 = responseBody.contentLength();
            if (contentLength2 != -1) {
                bodySize2 = contentLength2 + "-byte";
            } else {
                bodySize2 = "unknown-length";
            }
            b bVar5 = this.d;
            c0 c0Var = requestBody;
            StringBuilder sb7 = new StringBuilder();
            String str13 = str3;
            sb7.append("<-- ");
            sb7.append(response.j());
            if (response.t().length() == 0) {
                contentLength = contentLength2;
                str4 = "-byte body)";
                str5 = bodySize;
                c2 = ' ';
            } else {
                String t = response.t();
                str4 = "-byte body)";
                StringBuilder sb8 = new StringBuilder();
                contentLength = contentLength2;
                c2 = ' ';
                sb8.append(String.valueOf(' '));
                sb8.append(t);
                str5 = sb8.toString();
            }
            sb7.append(str5);
            sb7.append(c2);
            sb7.append(response.J().l());
            sb7.append(" (");
            sb7.append(tookMs);
            sb7.append("ms");
            if (!logHeaders) {
                str6 = ", " + bodySize2 + " body";
            } else {
                str6 = bodySize;
            }
            sb7.append(str6);
            sb7.append(')');
            bVar5.a(sb7.toString());
            if (logHeaders) {
                u headers2 = response.s();
                int size2 = headers2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    b(headers2, i2);
                }
                if (!logBody) {
                    u uVar5 = headers2;
                    boolean z = logBody;
                    boolean z2 = logHeaders;
                } else if (!e.c(response)) {
                    String str14 = bodySize2;
                    u uVar6 = headers2;
                    boolean z3 = logBody;
                    boolean z4 = logHeaders;
                } else if (a(response.s())) {
                    this.d.a("<-- END HTTP (encoded body omitted)");
                    String str15 = bodySize2;
                    boolean z5 = logBody;
                    boolean z6 = logHeaders;
                } else {
                    okio.e source = responseBody.source();
                    source.request(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
                    c buffer2 = source.getBuffer();
                    Long gzippedLength = null;
                    if (kotlin.text.w.y(GZipContentEncoding.NAME, headers2.a(HttpHeaders.HEAD_KEY_CONTENT_ENCODING), true)) {
                        gzippedLength = Long.valueOf(buffer2.e1());
                        m mVar = new m(buffer2.clone());
                        m gzippedResponseBody = mVar;
                        try {
                            buffer2 = new c();
                            buffer2.writeAll(gzippedResponseBody);
                            kotlin.io.b.a(mVar, (Throwable) null);
                        } catch (Throwable th2) {
                            Throwable th3 = th2;
                            kotlin.io.b.a(mVar, th);
                            throw th3;
                        }
                    }
                    x contentType3 = responseBody.contentType();
                    if (contentType3 == null || (charset = contentType3.d(StandardCharsets.UTF_8)) == null) {
                        charset = StandardCharsets.UTF_8;
                        k.b(charset, str13);
                    }
                    if (!c.a(buffer2)) {
                        String str16 = bodySize2;
                        this.d.a(bodySize);
                        b bVar6 = this.d;
                        StringBuilder sb9 = new StringBuilder();
                        u uVar7 = headers2;
                        sb9.append("<-- END HTTP (binary ");
                        okio.e eVar = source;
                        sb9.append(buffer2.e1());
                        sb9.append("-byte body omitted)");
                        bVar6.a(sb9.toString());
                        return response;
                    }
                    u uVar8 = headers2;
                    okio.e eVar2 = source;
                    String bodySize3 = bodySize;
                    if (contentLength != 0) {
                        this.d.a(bodySize3);
                        this.d.a(buffer2.clone().x0(charset));
                    }
                    if (gzippedLength != null) {
                        b bVar7 = this.d;
                        StringBuilder sb10 = new StringBuilder();
                        sb10.append("<-- END HTTP (");
                        boolean z7 = logBody;
                        boolean z8 = logHeaders;
                        sb10.append(buffer2.e1());
                        sb10.append("-byte, ");
                        sb10.append(gzippedLength);
                        sb10.append("-gzipped-byte body)");
                        bVar7.a(sb10.toString());
                    } else {
                        boolean z9 = logHeaders;
                        this.d.a("<-- END HTTP (" + buffer2.e1() + str4);
                    }
                }
                this.d.a("<-- END HTTP");
            } else {
                boolean z10 = logBody;
                boolean z11 = logHeaders;
            }
            return response;
        } catch (Exception e) {
            b0 b0Var2 = request;
            long j2 = startNs;
            boolean z12 = logBody;
            boolean z13 = logHeaders;
            c0 c0Var2 = requestBody;
            Exception e2 = e;
            this.d.a("<-- HTTP FAILED: " + e2);
            throw e2;
        }
    }

    private final void b(u headers, int i) {
        String value = this.b.contains(headers.b(i)) ? "██" : headers.h(i);
        b bVar = this.d;
        bVar.a(headers.b(i) + ": " + value);
    }

    private final boolean a(u headers) {
        String contentEncoding = headers.a(HttpHeaders.HEAD_KEY_CONTENT_ENCODING);
        if (contentEncoding == null || kotlin.text.w.y(contentEncoding, "identity", true) || kotlin.text.w.y(contentEncoding, GZipContentEncoding.NAME, true)) {
            return false;
        }
        return true;
    }
}
