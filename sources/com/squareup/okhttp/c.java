package com.squareup.okhttp;

import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.squareup.okhttp.internal.b;
import com.squareup.okhttp.internal.e;
import com.squareup.okhttp.internal.http.i;
import com.squareup.okhttp.internal.http.k;
import com.squareup.okhttp.internal.j;
import com.squareup.okhttp.p;
import com.squareup.okhttp.v;
import com.squareup.okhttp.x;
import java.io.File;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import okio.b0;
import okio.e0;
import okio.f;
import okio.p;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: Cache */
public final class c {
    final e a;
    private final com.squareup.okhttp.internal.b b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;

    static /* synthetic */ int h(c x0) {
        int i = x0.c;
        x0.c = i + 1;
        return i;
    }

    static /* synthetic */ int i(c x0) {
        int i = x0.d;
        x0.d = i + 1;
        return i;
    }

    /* compiled from: Cache */
    public class a implements e {
        a() {
        }

        public x a(v request) {
            return c.this.k(request);
        }

        public com.squareup.okhttp.internal.http.b c(x response) {
            return c.this.l(response);
        }

        public void e(v request) {
            c.this.n(request);
        }

        public void b(x cached, x network) {
            c.this.q(cached, network);
        }

        public void d() {
            c.this.o();
        }

        public void f(com.squareup.okhttp.internal.http.c cacheStrategy) {
            c.this.p(cacheStrategy);
        }
    }

    public c(File directory, long maxSize) {
        this(directory, maxSize, com.squareup.okhttp.internal.io.a.a);
    }

    c(File directory, long maxSize, com.squareup.okhttp.internal.io.a fileSystem) {
        this.a = new a();
        this.b = com.squareup.okhttp.internal.b.E(fileSystem, directory, 201105, 2, maxSize);
    }

    private static String r(v request) {
        return j.p(request.p());
    }

    /* access modifiers changed from: package-private */
    public x k(v request) {
        try {
            b.f snapshot = this.b.T(r(request));
            if (snapshot == null) {
                return null;
            }
            try {
                d entry = new d(snapshot.c(0));
                x response = entry.d(request, snapshot);
                if (entry.b(request, response)) {
                    return response;
                }
                j.c(response.k());
                return null;
            } catch (IOException e2) {
                j.c(snapshot);
                return null;
            }
        } catch (IOException e3) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public com.squareup.okhttp.internal.http.b l(x response) {
        String requestMethod = response.x().m();
        if (i.a(response.x().m())) {
            try {
                n(response.x());
            } catch (IOException e2) {
            }
            return null;
        } else if (!requestMethod.equals(Constants.GET) || k.g(response)) {
            return null;
        } else {
            d entry = new d(response);
            try {
                b.d editor = this.b.J(r(response.x()));
                if (editor == null) {
                    return null;
                }
                entry.f(editor);
                return new b(editor);
            } catch (IOException e3) {
                a((b.d) null);
                return null;
            }
        }
    }

    /* access modifiers changed from: private */
    public void n(v request) {
        this.b.c1(r(request));
    }

    /* access modifiers changed from: private */
    public void q(x cached, x network) {
        d entry = new d(network);
        try {
            b.d editor = ((C0207c) cached.k()).c.a();
            if (editor != null) {
                entry.f(editor);
                editor.e();
            }
        } catch (IOException e2) {
            a((b.d) null);
        }
    }

    private void a(b.d editor) {
        if (editor != null) {
            try {
                editor.a();
            } catch (IOException e2) {
            }
        }
    }

    public void j() {
        this.b.close();
    }

    /* access modifiers changed from: private */
    public synchronized void p(com.squareup.okhttp.internal.http.c cacheStrategy) {
        this.g++;
        if (cacheStrategy.a != null) {
            this.e++;
        } else if (cacheStrategy.b != null) {
            this.f++;
        }
    }

    /* access modifiers changed from: private */
    public synchronized void o() {
        this.f++;
    }

    /* compiled from: Cache */
    public final class b implements com.squareup.okhttp.internal.http.b {
        private final b.d a;
        private b0 b;
        /* access modifiers changed from: private */
        public boolean c;
        private b0 d;

        public b(b.d editor) {
            this.a = editor;
            b0 f = editor.f(1);
            this.b = f;
            this.d = new a(f, c.this, editor);
        }

        /* compiled from: Cache */
        public class a extends okio.j {
            final /* synthetic */ c c;
            final /* synthetic */ b.d d;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(b0 x0, c cVar, b.d dVar) {
                super(x0);
                this.c = cVar;
                this.d = dVar;
            }

            public void close() {
                synchronized (c.this) {
                    if (!b.this.c) {
                        boolean unused = b.this.c = true;
                        c.h(c.this);
                        super.close();
                        this.d.e();
                    }
                }
            }
        }

        public void b() {
            synchronized (c.this) {
                if (!this.c) {
                    this.c = true;
                    c.i(c.this);
                    j.c(this.b);
                    try {
                        this.a.a();
                    } catch (IOException e2) {
                    }
                }
            }
        }

        public b0 body() {
            return this.d;
        }
    }

    /* compiled from: Cache */
    public static final class d {
        private final String a;
        private final p b;
        private final String c;
        private final u d;
        private final int e;
        private final String f;
        private final p g;
        private final o h;

        public d(e0 in) {
            try {
                okio.e source = p.d(in);
                this.a = source.d0();
                this.c = source.d0();
                p.b varyHeadersBuilder = new p.b();
                int varyRequestHeaderLineCount = c.m(source);
                for (int i = 0; i < varyRequestHeaderLineCount; i++) {
                    varyHeadersBuilder.c(source.d0());
                }
                this.b = varyHeadersBuilder.e();
                com.squareup.okhttp.internal.http.p statusLine = com.squareup.okhttp.internal.http.p.a(source.d0());
                this.d = statusLine.a;
                this.e = statusLine.b;
                this.f = statusLine.c;
                p.b responseHeadersBuilder = new p.b();
                int responseHeaderLineCount = c.m(source);
                for (int i2 = 0; i2 < responseHeaderLineCount; i2++) {
                    responseHeadersBuilder.c(source.d0());
                }
                this.g = responseHeadersBuilder.e();
                if (a()) {
                    String blank = source.d0();
                    if (blank.length() <= 0) {
                        this.h = o.b(source.d0(), c(source), c(source));
                    } else {
                        throw new IOException("expected \"\" but was \"" + blank + "\"");
                    }
                } else {
                    this.h = null;
                }
            } finally {
                in.close();
            }
        }

        public d(x response) {
            this.a = response.x().p();
            this.b = k.p(response);
            this.c = response.x().m();
            this.d = response.w();
            this.e = response.o();
            this.f = response.t();
            this.g = response.s();
            this.h = response.p();
        }

        public void f(b.d editor) {
            okio.d sink = okio.p.c(editor.f(0));
            sink.writeUtf8(this.a);
            sink.writeByte(10);
            sink.writeUtf8(this.c);
            sink.writeByte(10);
            sink.writeDecimalLong((long) this.b.f());
            sink.writeByte(10);
            int size = this.b.f();
            for (int i = 0; i < size; i++) {
                sink.writeUtf8(this.b.d(i));
                sink.writeUtf8(": ");
                sink.writeUtf8(this.b.g(i));
                sink.writeByte(10);
            }
            sink.writeUtf8(new com.squareup.okhttp.internal.http.p(this.d, this.e, this.f).toString());
            sink.writeByte(10);
            sink.writeDecimalLong((long) this.g.f());
            sink.writeByte(10);
            int size2 = this.g.f();
            for (int i2 = 0; i2 < size2; i2++) {
                sink.writeUtf8(this.g.d(i2));
                sink.writeUtf8(": ");
                sink.writeUtf8(this.g.g(i2));
                sink.writeByte(10);
            }
            if (a() != 0) {
                sink.writeByte(10);
                sink.writeUtf8(this.h.a());
                sink.writeByte(10);
                e(sink, this.h.e());
                e(sink, this.h.d());
            }
            sink.close();
        }

        private boolean a() {
            return this.a.startsWith(NetworkManager.MOCK_SCHEME_HTTPS);
        }

        private List<Certificate> c(okio.e source) {
            int length = c.m(source);
            if (length == -1) {
                return Collections.emptyList();
            }
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                List<Certificate> result = new ArrayList<>(length);
                for (int i = 0; i < length; i++) {
                    String line = source.d0();
                    okio.c bytes = new okio.c();
                    bytes.write(f.decodeBase64(line));
                    result.add(certificateFactory.generateCertificate(bytes.Y0()));
                }
                return result;
            } catch (CertificateException e2) {
                throw new IOException(e2.getMessage());
            }
        }

        private void e(okio.d sink, List<Certificate> certificates) {
            try {
                sink.writeDecimalLong((long) certificates.size());
                sink.writeByte(10);
                int size = certificates.size();
                for (int i = 0; i < size; i++) {
                    sink.writeUtf8(f.of(certificates.get(i).getEncoded()).base64());
                    sink.writeByte(10);
                }
            } catch (CertificateEncodingException e2) {
                throw new IOException(e2.getMessage());
            }
        }

        public boolean b(v request, x response) {
            return this.a.equals(request.p()) && this.c.equals(request.m()) && k.q(response, this.b, request);
        }

        public x d(v request, b.f snapshot) {
            String contentType = this.g.a("Content-Type");
            String contentLength = this.g.a("Content-Length");
            return new x.b().y(new v.b().n(this.a).k(this.c, (w) null).j(this.b).g()).x(this.d).q(this.e).u(this.f).t(this.g).l(new C0207c(snapshot, contentType, contentLength)).r(this.h).m();
        }
    }

    /* access modifiers changed from: private */
    public static int m(okio.e source) {
        try {
            long result = source.s0();
            String line = source.d0();
            if (result >= 0 && result <= 2147483647L && line.isEmpty()) {
                return (int) result;
            }
            throw new IOException("expected an int but was \"" + result + line + "\"");
        } catch (NumberFormatException e2) {
            throw new IOException(e2.getMessage());
        }
    }

    /* renamed from: com.squareup.okhttp.c$c  reason: collision with other inner class name */
    /* compiled from: Cache */
    public static class C0207c extends y {
        /* access modifiers changed from: private */
        public final b.f c;
        private final okio.e d;
        private final String f;
        private final String q;

        public C0207c(b.f snapshot, String contentType, String contentLength) {
            this.c = snapshot;
            this.f = contentType;
            this.q = contentLength;
            this.d = okio.p.d(new a(snapshot.c(1), snapshot));
        }

        /* renamed from: com.squareup.okhttp.c$c$a */
        /* compiled from: Cache */
        public class a extends okio.k {
            final /* synthetic */ b.f c;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(e0 x0, b.f fVar) {
                super(x0);
                this.c = fVar;
            }

            public void close() {
                this.c.close();
                super.close();
            }
        }

        public long c() {
            try {
                String str = this.q;
                if (str != null) {
                    return Long.parseLong(str);
                }
                return -1;
            } catch (NumberFormatException e) {
                return -1;
            }
        }

        public okio.e g() {
            return this.d;
        }
    }
}
