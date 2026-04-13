package okhttp3;

import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import io.netty.util.internal.StringUtil;
import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import kotlin.TypeCastException;
import kotlin.collections.o0;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import kotlin.text.x;
import okhttp3.b0;
import okhttp3.d0;
import okhttp3.internal.cache.d;
import okhttp3.internal.concurrent.e;
import okhttp3.internal.http.f;
import okhttp3.internal.platform.h;
import okhttp3.u;
import okio.b0;
import okio.e0;
import okio.f;
import okio.j;
import okio.p;
import org.glassfish.grizzly.http.server.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Cache.kt */
public final class c implements Closeable, Flushable {
    public static final b c = new b((DefaultConstructorMarker) null);
    @NotNull
    private final okhttp3.internal.cache.d d;
    private int f;
    private int q;
    private int x;
    private int y;
    private int z;

    public c(@NotNull File directory, long maxSize, @NotNull okhttp3.internal.io.b fileSystem) {
        k.f(directory, "directory");
        k.f(fileSystem, "fileSystem");
        this.d = new okhttp3.internal.cache.d(fileSystem, directory, 201105, 2, maxSize, e.a);
    }

    public final int i() {
        return this.f;
    }

    public final void n(int i) {
        this.f = i;
    }

    public final int g() {
        return this.q;
    }

    public final void m(int i) {
        this.q = i;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public c(@NotNull File directory, long maxSize) {
        this(directory, maxSize, okhttp3.internal.io.b.a);
        k.f(directory, "directory");
    }

    @Nullable
    public final d0 c(@NotNull b0 request) {
        k.f(request, Progress.REQUEST);
        try {
            d.C0461d snapshot = this.d.v(c.b(request.l()));
            if (snapshot == null) {
                return null;
            }
            try {
                C0457c entry = new C0457c(snapshot.c(0));
                d0 response = entry.d(snapshot);
                if (entry.b(request, response)) {
                    return response;
                }
                e0 a2 = response.a();
                if (a2 != null) {
                    okhttp3.internal.b.j(a2);
                }
                return null;
            } catch (IOException e) {
                okhttp3.internal.b.j(snapshot);
                return null;
            }
        } catch (IOException e2) {
            return null;
        }
    }

    @Nullable
    public final okhttp3.internal.cache.b j(@NotNull d0 response) {
        k.f(response, "response");
        String requestMethod = response.J().h();
        if (f.a.a(response.J().h())) {
            try {
                l(response.J());
            } catch (IOException e) {
            }
            return null;
        } else if (!k.a(requestMethod, Constants.GET)) {
            return null;
        } else {
            b bVar = c;
            if (bVar.a(response)) {
                return null;
            }
            C0457c entry = new C0457c(response);
            try {
                d.b u = okhttp3.internal.cache.d.u(this.d, bVar.b(response.J().l()), 0, 2, (Object) null);
                if (u == null) {
                    return null;
                }
                d.b editor = u;
                entry.f(editor);
                return new d(this, editor);
            } catch (IOException e2) {
                a((d.b) null);
                return null;
            }
        }
    }

    public final void l(@NotNull b0 request) {
        k.f(request, Progress.REQUEST);
        this.d.P0(c.b(request.l()));
    }

    public final void s(@NotNull d0 cached, @NotNull d0 network) {
        k.f(cached, "cached");
        k.f(network, "network");
        C0457c entry = new C0457c(network);
        e0 a2 = cached.a();
        if (a2 != null) {
            try {
                d.b a3 = ((a) a2).a().a();
                if (a3 != null) {
                    d.b editor = a3;
                    entry.f(editor);
                    editor.b();
                }
            } catch (IOException e) {
                a((d.b) null);
            }
        } else {
            throw new TypeCastException("null cannot be cast to non-null type okhttp3.Cache.CacheResponseBody");
        }
    }

    private final void a(d.b editor) {
        if (editor != null) {
            try {
                editor.a();
            } catch (IOException e) {
            }
        }
    }

    public void flush() {
        this.d.flush();
    }

    public void close() {
        this.d.close();
    }

    public final synchronized void r(@NotNull okhttp3.internal.cache.c cacheStrategy) {
        k.f(cacheStrategy, "cacheStrategy");
        this.z++;
        if (cacheStrategy.b() != null) {
            this.x++;
        } else if (cacheStrategy.a() != null) {
            this.y++;
        }
    }

    public final synchronized void o() {
        this.y++;
    }

    /* compiled from: Cache.kt */
    public final class d implements okhttp3.internal.cache.b {
        private final b0 a;
        private final b0 b;
        private boolean c;
        /* access modifiers changed from: private */
        public final d.b d;
        final /* synthetic */ c e;

        public d(@NotNull c $outer, d.b editor) {
            k.f(editor, "editor");
            this.e = $outer;
            this.d = editor;
            b0 f = editor.f(1);
            this.a = f;
            this.b = new a(this, f);
        }

        public final boolean c() {
            return this.c;
        }

        public final void d(boolean z) {
            this.c = z;
        }

        /* compiled from: Cache.kt */
        public static final class a extends j {
            final /* synthetic */ d c;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(d $outer, b0 $super_call_param$1) {
                super($super_call_param$1);
                this.c = $outer;
            }

            public void close() {
                synchronized (this.c.e) {
                    if (!this.c.c()) {
                        this.c.d(true);
                        c cVar = this.c.e;
                        cVar.n(cVar.i() + 1);
                        super.close();
                        this.c.d.b();
                    }
                }
            }
        }

        public void b() {
            synchronized (this.e) {
                if (!this.c) {
                    this.c = true;
                    c cVar = this.e;
                    cVar.m(cVar.g() + 1);
                    okhttp3.internal.b.j(this.a);
                    try {
                        this.d.a();
                    } catch (IOException e2) {
                    }
                }
            }
        }

        @NotNull
        public b0 body() {
            return this.b;
        }
    }

    /* renamed from: okhttp3.c$c  reason: collision with other inner class name */
    /* compiled from: Cache.kt */
    public static final class C0457c {
        private static final String a;
        private static final String b;
        public static final a c = new a((DefaultConstructorMarker) null);
        private final String d;
        private final u e;
        private final String f;
        private final a0 g;
        private final int h;
        private final String i;
        private final u j;
        private final t k;
        private final long l;
        private final long m;

        private final boolean a() {
            return w.N(this.d, NetworkManager.MOCK_SCHEME_HTTPS, false, 2, (Object) null);
        }

        public C0457c(@NotNull e0 rawSource) {
            boolean z;
            long j2;
            g0 tlsVersion;
            k.f(rawSource, "rawSource");
            try {
                okio.e source = p.d(rawSource);
                this.d = source.d0();
                this.f = source.d0();
                u.a varyHeadersBuilder = new u.a();
                int varyRequestHeaderLineCount = c.c.c(source);
                int i2 = 0;
                while (true) {
                    z = true;
                    if (i2 >= varyRequestHeaderLineCount) {
                        break;
                    }
                    varyHeadersBuilder.c(source.d0());
                    i2++;
                }
                this.e = varyHeadersBuilder.f();
                okhttp3.internal.http.k statusLine = okhttp3.internal.http.k.a.a(source.d0());
                this.g = statusLine.b;
                this.h = statusLine.c;
                this.i = statusLine.d;
                u.a responseHeadersBuilder = new u.a();
                int responseHeaderLineCount = c.c.c(source);
                for (int i3 = 0; i3 < responseHeaderLineCount; i3++) {
                    responseHeadersBuilder.c(source.d0());
                }
                String str = a;
                String sendRequestMillisString = responseHeadersBuilder.g(str);
                String str2 = b;
                String receivedResponseMillisString = responseHeadersBuilder.g(str2);
                responseHeadersBuilder.i(str);
                responseHeadersBuilder.i(str2);
                long j3 = 0;
                if (sendRequestMillisString != null) {
                    okhttp3.internal.http.k kVar = statusLine;
                    j2 = Long.parseLong(sendRequestMillisString);
                } else {
                    okhttp3.internal.http.k kVar2 = statusLine;
                    j2 = 0;
                }
                this.l = j2;
                this.m = receivedResponseMillisString != null ? Long.parseLong(receivedResponseMillisString) : j3;
                this.j = responseHeadersBuilder.f();
                if (a()) {
                    String blank = source.d0();
                    if (blank.length() <= 0) {
                        z = false;
                    }
                    if (!z) {
                        i cipherSuite = i.r1.b(source.d0());
                        List peerCertificates = c(source);
                        List localCertificates = c(source);
                        if (!source.r0()) {
                            tlsVersion = g0.Companion.a(source.d0());
                        } else {
                            tlsVersion = g0.SSL_3_0;
                        }
                        this.k = t.a.b(tlsVersion, cipherSuite, peerCertificates, localCertificates);
                    } else {
                        throw new IOException("expected \"\" but was \"" + blank + StringUtil.DOUBLE_QUOTE);
                    }
                } else {
                    this.k = null;
                }
            } finally {
                rawSource.close();
            }
        }

        public C0457c(@NotNull d0 response) {
            k.f(response, "response");
            this.d = response.J().l().toString();
            this.e = c.c.f(response);
            this.f = response.J().h();
            this.g = response.E();
            this.h = response.j();
            this.i = response.t();
            this.j = response.s();
            this.k = response.m();
            this.l = response.P();
            this.m = response.I();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0132, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0133, code lost:
            kotlin.io.b.a(r1, r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0136, code lost:
            throw r2;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void f(@org.jetbrains.annotations.NotNull okhttp3.internal.cache.d.b r11) {
            /*
                r10 = this;
                java.lang.String r0 = "editor"
                kotlin.jvm.internal.k.f(r11, r0)
                r0 = 0
                okio.b0 r1 = r11.f(r0)
                okio.d r1 = okio.p.c(r1)
                r2 = r1
                r3 = 0
                java.lang.String r4 = r10.d     // Catch:{ all -> 0x0130 }
                okio.d r4 = r2.writeUtf8(r4)     // Catch:{ all -> 0x0130 }
                r5 = 10
                r4.writeByte(r5)     // Catch:{ all -> 0x0130 }
                java.lang.String r4 = r10.f     // Catch:{ all -> 0x0130 }
                okio.d r4 = r2.writeUtf8(r4)     // Catch:{ all -> 0x0130 }
                r4.writeByte(r5)     // Catch:{ all -> 0x0130 }
                okhttp3.u r4 = r10.e     // Catch:{ all -> 0x0130 }
                int r4 = r4.size()     // Catch:{ all -> 0x0130 }
                long r6 = (long) r4     // Catch:{ all -> 0x0130 }
                okio.d r4 = r2.writeDecimalLong(r6)     // Catch:{ all -> 0x0130 }
                r4.writeByte(r5)     // Catch:{ all -> 0x0130 }
                okhttp3.u r4 = r10.e     // Catch:{ all -> 0x0130 }
                int r4 = r4.size()     // Catch:{ all -> 0x0130 }
                r6 = r0
            L_0x0039:
                java.lang.String r7 = ": "
                if (r6 >= r4) goto L_0x0062
                okhttp3.u r8 = r10.e     // Catch:{ all -> 0x0130 }
                java.lang.String r8 = r8.b(r6)     // Catch:{ all -> 0x0130 }
                okio.d r8 = r2.writeUtf8(r8)     // Catch:{ all -> 0x0130 }
                okio.d r7 = r8.writeUtf8(r7)     // Catch:{ all -> 0x0130 }
                okhttp3.u r8 = r10.e     // Catch:{ all -> 0x0130 }
                java.lang.String r8 = r8.h(r6)     // Catch:{ all -> 0x0130 }
                okio.d r7 = r7.writeUtf8(r8)     // Catch:{ all -> 0x0130 }
                r7.writeByte(r5)     // Catch:{ all -> 0x0130 }
                int r6 = r6 + 1
                goto L_0x0039
            L_0x0062:
                okhttp3.internal.http.k r4 = new okhttp3.internal.http.k     // Catch:{ all -> 0x0130 }
                okhttp3.a0 r6 = r10.g     // Catch:{ all -> 0x0130 }
                int r8 = r10.h     // Catch:{ all -> 0x0130 }
                java.lang.String r9 = r10.i     // Catch:{ all -> 0x0130 }
                r4.<init>(r6, r8, r9)     // Catch:{ all -> 0x0130 }
                java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0130 }
                okio.d r4 = r2.writeUtf8(r4)     // Catch:{ all -> 0x0130 }
                r4.writeByte(r5)     // Catch:{ all -> 0x0130 }
                okhttp3.u r4 = r10.j     // Catch:{ all -> 0x0130 }
                int r4 = r4.size()     // Catch:{ all -> 0x0130 }
                int r4 = r4 + 2
                long r8 = (long) r4     // Catch:{ all -> 0x0130 }
                okio.d r4 = r2.writeDecimalLong(r8)     // Catch:{ all -> 0x0130 }
                r4.writeByte(r5)     // Catch:{ all -> 0x0130 }
                okhttp3.u r4 = r10.j     // Catch:{ all -> 0x0130 }
                int r4 = r4.size()     // Catch:{ all -> 0x0130 }
            L_0x008e:
                if (r0 >= r4) goto L_0x00b5
                okhttp3.u r6 = r10.j     // Catch:{ all -> 0x0130 }
                java.lang.String r6 = r6.b(r0)     // Catch:{ all -> 0x0130 }
                okio.d r6 = r2.writeUtf8(r6)     // Catch:{ all -> 0x0130 }
                okio.d r6 = r6.writeUtf8(r7)     // Catch:{ all -> 0x0130 }
                okhttp3.u r8 = r10.j     // Catch:{ all -> 0x0130 }
                java.lang.String r8 = r8.h(r0)     // Catch:{ all -> 0x0130 }
                okio.d r6 = r6.writeUtf8(r8)     // Catch:{ all -> 0x0130 }
                r6.writeByte(r5)     // Catch:{ all -> 0x0130 }
                int r0 = r0 + 1
                goto L_0x008e
            L_0x00b5:
                java.lang.String r0 = a     // Catch:{ all -> 0x0130 }
                okio.d r0 = r2.writeUtf8(r0)     // Catch:{ all -> 0x0130 }
                okio.d r0 = r0.writeUtf8(r7)     // Catch:{ all -> 0x0130 }
                long r8 = r10.l     // Catch:{ all -> 0x0130 }
                okio.d r0 = r0.writeDecimalLong(r8)     // Catch:{ all -> 0x0130 }
                r0.writeByte(r5)     // Catch:{ all -> 0x0130 }
                java.lang.String r0 = b     // Catch:{ all -> 0x0130 }
                okio.d r0 = r2.writeUtf8(r0)     // Catch:{ all -> 0x0130 }
                okio.d r0 = r0.writeUtf8(r7)     // Catch:{ all -> 0x0130 }
                long r6 = r10.m     // Catch:{ all -> 0x0130 }
                okio.d r0 = r0.writeDecimalLong(r6)     // Catch:{ all -> 0x0130 }
                r0.writeByte(r5)     // Catch:{ all -> 0x0130 }
                boolean r0 = r10.a()     // Catch:{ all -> 0x0130 }
                if (r0 == 0) goto L_0x0129
                r2.writeByte(r5)     // Catch:{ all -> 0x0130 }
                okhttp3.t r0 = r10.k     // Catch:{ all -> 0x0130 }
                if (r0 != 0) goto L_0x00f7
                kotlin.jvm.internal.k.n()     // Catch:{ all -> 0x0130 }
            L_0x00f7:
                okhttp3.i r0 = r0.a()     // Catch:{ all -> 0x0130 }
                java.lang.String r0 = r0.c()     // Catch:{ all -> 0x0130 }
                okio.d r0 = r2.writeUtf8(r0)     // Catch:{ all -> 0x0130 }
                r0.writeByte(r5)     // Catch:{ all -> 0x0130 }
                okhttp3.t r0 = r10.k     // Catch:{ all -> 0x0130 }
                java.util.List r0 = r0.e()     // Catch:{ all -> 0x0130 }
                r10.e(r2, r0)     // Catch:{ all -> 0x0130 }
                okhttp3.t r0 = r10.k     // Catch:{ all -> 0x0130 }
                java.util.List r0 = r0.c()     // Catch:{ all -> 0x0130 }
                r10.e(r2, r0)     // Catch:{ all -> 0x0130 }
                okhttp3.t r0 = r10.k     // Catch:{ all -> 0x0130 }
                okhttp3.g0 r0 = r0.g()     // Catch:{ all -> 0x0130 }
                java.lang.String r0 = r0.javaName()     // Catch:{ all -> 0x0130 }
                okio.d r0 = r2.writeUtf8(r0)     // Catch:{ all -> 0x0130 }
                r0.writeByte(r5)     // Catch:{ all -> 0x0130 }
            L_0x0129:
                kotlin.x r0 = kotlin.x.a     // Catch:{ all -> 0x0130 }
                r0 = 0
                kotlin.io.b.a(r1, r0)
                return
            L_0x0130:
                r0 = move-exception
                throw r0     // Catch:{ all -> 0x0132 }
            L_0x0132:
                r2 = move-exception
                kotlin.io.b.a(r1, r0)
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.c.C0457c.f(okhttp3.internal.cache.d$b):void");
        }

        private final List<Certificate> c(okio.e source) {
            int length = c.c.c(source);
            if (length == -1) {
                return q.g();
            }
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                ArrayList result = new ArrayList(length);
                for (int i2 = 0; i2 < length; i2++) {
                    String line = source.d0();
                    okio.c bytes = new okio.c();
                    okio.f a2 = okio.f.Companion.a(line);
                    if (a2 == null) {
                        k.n();
                    }
                    bytes.write(a2);
                    result.add(certificateFactory.generateCertificate(bytes.Y0()));
                }
                return result;
            } catch (CertificateException e2) {
                throw new IOException(e2.getMessage());
            }
        }

        private final void e(okio.d sink, List<? extends Certificate> certificates) {
            try {
                sink.writeDecimalLong((long) certificates.size()).writeByte(10);
                int size = certificates.size();
                for (int i2 = 0; i2 < size; i2++) {
                    byte[] bytes = ((Certificate) certificates.get(i2)).getEncoded();
                    f.a aVar = okio.f.Companion;
                    k.b(bytes, "bytes");
                    sink.writeUtf8(f.a.h(aVar, bytes, 0, 0, 3, (Object) null).base64()).writeByte(10);
                }
            } catch (CertificateEncodingException e2) {
                throw new IOException(e2.getMessage());
            }
        }

        public final boolean b(@NotNull b0 request, @NotNull d0 response) {
            k.f(request, Progress.REQUEST);
            k.f(response, "response");
            return k.a(this.d, request.l().toString()) && k.a(this.f, request.h()) && c.c.g(response, this.e, request);
        }

        @NotNull
        public final d0 d(@NotNull d.C0461d snapshot) {
            k.f(snapshot, "snapshot");
            String contentType = this.j.a("Content-Type");
            String contentLength = this.j.a("Content-Length");
            return new d0.a().r(new b0.a().p(this.d).i(this.f, (c0) null).h(this.e).b()).p(this.g).g(this.h).m(this.i).k(this.j).b(new a(snapshot, contentType, contentLength)).i(this.k).s(this.l).q(this.m).c();
        }

        /* renamed from: okhttp3.c$c$a */
        /* compiled from: Cache.kt */
        public static final class a {
            private a() {
            }

            public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
                this();
            }
        }

        static {
            StringBuilder sb = new StringBuilder();
            h.a aVar = h.c;
            sb.append(aVar.g().g());
            sb.append("-Sent-Millis");
            a = sb.toString();
            b = aVar.g().g() + "-Received-Millis";
        }
    }

    /* compiled from: Cache.kt */
    public static final class a extends e0 {
        private final okio.e c;
        @NotNull
        private final d.C0461d d;
        private final String f;
        private final String q;

        public a(@NotNull d.C0461d snapshot, @Nullable String contentType, @Nullable String contentLength) {
            k.f(snapshot, "snapshot");
            this.d = snapshot;
            this.f = contentType;
            this.q = contentLength;
            e0 source = snapshot.c(1);
            this.c = p.d(new C0456a(this, source, source));
        }

        @NotNull
        public final d.C0461d a() {
            return this.d;
        }

        /* renamed from: okhttp3.c$a$a  reason: collision with other inner class name */
        /* compiled from: Cache.kt */
        public static final class C0456a extends okio.k {
            final /* synthetic */ a c;
            final /* synthetic */ e0 d;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0456a(a $outer, e0 $captured_local_variable$1, e0 $super_call_param$2) {
                super($super_call_param$2);
                this.c = $outer;
                this.d = $captured_local_variable$1;
            }

            public void close() {
                this.c.a().close();
                super.close();
            }
        }

        @Nullable
        public x contentType() {
            String str = this.f;
            if (str != null) {
                return x.c.b(str);
            }
            return null;
        }

        public long contentLength() {
            String str = this.q;
            if (str != null) {
                return okhttp3.internal.b.T(str, -1);
            }
            return -1;
        }

        @NotNull
        public okio.e source() {
            return this.c;
        }
    }

    /* compiled from: Cache.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final String b(@NotNull v url) {
            k.f(url, "url");
            return okio.f.Companion.d(url.toString()).md5().hex();
        }

        public final int c(@NotNull okio.e source) {
            k.f(source, "source");
            try {
                long result = source.s0();
                String line = source.d0();
                if (result >= 0 && result <= ((long) Integer.MAX_VALUE)) {
                    if (!(line.length() > 0)) {
                        return (int) result;
                    }
                }
                throw new IOException("expected an int but was \"" + result + line + StringUtil.DOUBLE_QUOTE);
            } catch (NumberFormatException e) {
                throw new IOException(e.getMessage());
            }
        }

        public final boolean g(@NotNull d0 cachedResponse, @NotNull u cachedRequest, @NotNull b0 newRequest) {
            k.f(cachedResponse, "cachedResponse");
            k.f(cachedRequest, "cachedRequest");
            k.f(newRequest, "newRequest");
            Set<String> d = d(cachedResponse.s());
            if ((d instanceof Collection) && d.isEmpty()) {
                return true;
            }
            for (String it : d) {
                if (!k.a(cachedRequest.i(it), newRequest.e(it))) {
                    return false;
                }
            }
            return true;
        }

        public final boolean a(@NotNull d0 $this$hasVaryAll) {
            k.f($this$hasVaryAll, "$this$hasVaryAll");
            return d($this$hasVaryAll.s()).contains(org.slf4j.e.ANY_MARKER);
        }

        private final Set<String> d(@NotNull u $this$varyFields) {
            Set result = null;
            int size = $this$varyFields.size();
            for (int i = 0; i < size; i++) {
                if (w.y("Vary", $this$varyFields.b(i), true)) {
                    String value = $this$varyFields.h(i);
                    if (result == null) {
                        result = new TreeSet(w.z(kotlin.jvm.internal.d0.a));
                    }
                    for (String varyField : x.E0(value, new char[]{StringUtil.COMMA}, false, 0, 6, (Object) null)) {
                        if (varyField != null) {
                            result.add(x.e1(varyField).toString());
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                        }
                    }
                    continue;
                }
            }
            return result != null ? result : o0.d();
        }

        @NotNull
        public final u f(@NotNull d0 $this$varyHeaders) {
            k.f($this$varyHeaders, "$this$varyHeaders");
            d0 u = $this$varyHeaders.u();
            if (u == null) {
                k.n();
            }
            return e(u.J().f(), $this$varyHeaders.s());
        }

        private final u e(u requestHeaders, u responseHeaders) {
            Set varyFields = d(responseHeaders);
            if (varyFields.isEmpty()) {
                return okhttp3.internal.b.b;
            }
            u.a result = new u.a();
            int size = requestHeaders.size();
            for (int i = 0; i < size; i++) {
                String fieldName = requestHeaders.b(i);
                if (varyFields.contains(fieldName)) {
                    result.a(fieldName, requestHeaders.h(i));
                }
            }
            return result.f();
        }
    }
}
