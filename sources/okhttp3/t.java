package okhttp3;

import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: Handshake.kt */
public final class t {
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private final g b;
    @NotNull
    private final g0 c;
    @NotNull
    private final i d;
    @NotNull
    private final List<Certificate> e;

    @NotNull
    public final List<Certificate> e() {
        return (List) this.b.getValue();
    }

    public t(@NotNull g0 tlsVersion, @NotNull i cipherSuite, @NotNull List<? extends Certificate> localCertificates, @NotNull kotlin.jvm.functions.a<? extends List<? extends Certificate>> peerCertificatesFn) {
        k.f(tlsVersion, "tlsVersion");
        k.f(cipherSuite, "cipherSuite");
        k.f(localCertificates, "localCertificates");
        k.f(peerCertificatesFn, "peerCertificatesFn");
        this.c = tlsVersion;
        this.d = cipherSuite;
        this.e = localCertificates;
        this.b = i.b(new b(peerCertificatesFn));
    }

    @NotNull
    public final g0 g() {
        return this.c;
    }

    @NotNull
    public final i a() {
        return this.d;
    }

    @NotNull
    public final List<Certificate> c() {
        return this.e;
    }

    /* compiled from: Handshake.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<List<? extends Certificate>> {
        final /* synthetic */ kotlin.jvm.functions.a $peerCertificatesFn;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(kotlin.jvm.functions.a aVar) {
            super(0);
            this.$peerCertificatesFn = aVar;
        }

        @NotNull
        public final List<Certificate> invoke() {
            try {
                return (List) this.$peerCertificatesFn.invoke();
            } catch (SSLPeerUnverifiedException e) {
                return q.g();
            }
        }
    }

    @Nullable
    public final Principal f() {
        Object U = y.U(e());
        if (!(U instanceof X509Certificate)) {
            U = null;
        }
        X509Certificate x509Certificate = (X509Certificate) U;
        if (x509Certificate != null) {
            return x509Certificate.getSubjectX500Principal();
        }
        return null;
    }

    @Nullable
    public final Principal d() {
        Object U = y.U(this.e);
        if (!(U instanceof X509Certificate)) {
            U = null;
        }
        X509Certificate x509Certificate = (X509Certificate) U;
        if (x509Certificate != null) {
            return x509Certificate.getSubjectX500Principal();
        }
        return null;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof t) && ((t) other).c == this.c && k.a(((t) other).d, this.d) && k.a(((t) other).e(), e()) && k.a(((t) other).e, this.e);
    }

    public int hashCode() {
        return (((((((17 * 31) + this.c.hashCode()) * 31) + this.d.hashCode()) * 31) + e().hashCode()) * 31) + this.e.hashCode();
    }

    @NotNull
    public String toString() {
        Iterable<Certificate> $this$mapTo$iv$iv = e();
        Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (Certificate it : $this$mapTo$iv$iv) {
            destination$iv$iv.add(b(it));
        }
        String peerCertificatesString = destination$iv$iv.toString();
        StringBuilder sb = new StringBuilder();
        sb.append("Handshake{");
        sb.append("tlsVersion=");
        sb.append(this.c);
        sb.append(' ');
        sb.append("cipherSuite=");
        sb.append(this.d);
        sb.append(' ');
        sb.append("peerCertificates=");
        sb.append(peerCertificatesString);
        sb.append(' ');
        sb.append("localCertificates=");
        Iterable<Certificate> $this$mapTo$iv$iv2 = this.e;
        Collection destination$iv$iv2 = new ArrayList(r.r($this$mapTo$iv$iv2, 10));
        for (Certificate it2 : $this$mapTo$iv$iv2) {
            destination$iv$iv2.add(b(it2));
        }
        sb.append(destination$iv$iv2);
        sb.append('}');
        return sb.toString();
    }

    private final String b(@NotNull Certificate $this$name) {
        if ($this$name instanceof X509Certificate) {
            return ((X509Certificate) $this$name).getSubjectDN().toString();
        }
        String type = $this$name.getType();
        k.b(type, IjkMediaMeta.IJKM_KEY_TYPE);
        return type;
    }

    /* compiled from: Handshake.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x003c, code lost:
            r1 = okhttp3.i.r1.b(r0);
            r2 = r9.getProtocol();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0047, code lost:
            if (r2 == null) goto L_0x0084;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x004f, code lost:
            if (kotlin.jvm.internal.k.a("NONE", r2) != false) goto L_0x007b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0051, code lost:
            r3 = okhttp3.g0.Companion.a(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
            r4 = c(r9.getPeerCertificates());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0062, code lost:
            r4 = kotlin.collections.q.g();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0083, code lost:
            throw new java.io.IOException("tlsVersion == NONE");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0091, code lost:
            throw new java.lang.IllegalStateException("tlsVersion == null".toString());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:5:0x0019, code lost:
            if (r0.equals("SSL_NULL_WITH_NULL_NULL") == false) goto L_0x003c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:7:0x0022, code lost:
            if (r0.equals("TLS_NULL_WITH_NULL_NULL") == false) goto L_0x003c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x003b, code lost:
            throw new java.io.IOException("cipherSuite == " + r0);
         */
        @org.jetbrains.annotations.NotNull
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final okhttp3.t a(@org.jetbrains.annotations.NotNull javax.net.ssl.SSLSession r9) {
            /*
                r8 = this;
                java.lang.String r0 = "$this$handshake"
                kotlin.jvm.internal.k.f(r9, r0)
                java.lang.String r0 = r9.getCipherSuite()
                if (r0 == 0) goto L_0x0092
                int r1 = r0.hashCode()
                switch(r1) {
                    case 1019404634: goto L_0x001c;
                    case 1208658923: goto L_0x0013;
                    default: goto L_0x0012;
                }
            L_0x0012:
                goto L_0x003c
            L_0x0013:
                java.lang.String r1 = "SSL_NULL_WITH_NULL_NULL"
                boolean r1 = r0.equals(r1)
                if (r1 != 0) goto L_0x0025
                goto L_0x0024
            L_0x001c:
                java.lang.String r1 = "TLS_NULL_WITH_NULL_NULL"
                boolean r1 = r0.equals(r1)
                if (r1 != 0) goto L_0x0025
            L_0x0024:
                goto L_0x003c
            L_0x0025:
                java.io.IOException r1 = new java.io.IOException
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "cipherSuite == "
                r2.append(r3)
                r2.append(r0)
                java.lang.String r2 = r2.toString()
                r1.<init>(r2)
                throw r1
            L_0x003c:
                okhttp3.i$b r1 = okhttp3.i.r1
                okhttp3.i r1 = r1.b(r0)
                java.lang.String r2 = r9.getProtocol()
                if (r2 == 0) goto L_0x0084
                java.lang.String r3 = "NONE"
                boolean r3 = kotlin.jvm.internal.k.a(r3, r2)
                if (r3 != 0) goto L_0x007b
                okhttp3.g0$a r3 = okhttp3.g0.Companion
                okhttp3.g0 r3 = r3.a(r2)
                java.security.cert.Certificate[] r4 = r9.getPeerCertificates()     // Catch:{ SSLPeerUnverifiedException -> 0x0061 }
                java.util.List r4 = r8.c(r4)     // Catch:{ SSLPeerUnverifiedException -> 0x0061 }
                goto L_0x0067
            L_0x0061:
                r4 = move-exception
                java.util.List r5 = kotlin.collections.q.g()
                r4 = r5
            L_0x0067:
                okhttp3.t r5 = new okhttp3.t
                java.security.cert.Certificate[] r6 = r9.getLocalCertificates()
                java.util.List r6 = r8.c(r6)
                okhttp3.t$a$b r7 = new okhttp3.t$a$b
                r7.<init>(r4)
                r5.<init>(r3, r1, r6, r7)
                return r5
            L_0x007b:
                java.io.IOException r3 = new java.io.IOException
                java.lang.String r4 = "tlsVersion == NONE"
                r3.<init>(r4)
                throw r3
            L_0x0084:
                r2 = 0
                java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
                java.lang.String r3 = "tlsVersion == null"
                java.lang.String r3 = r3.toString()
                r2.<init>(r3)
                throw r2
            L_0x0092:
                r0 = 0
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "cipherSuite == null"
                java.lang.String r1 = r1.toString()
                r0.<init>(r1)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.t.a.a(javax.net.ssl.SSLSession):okhttp3.t");
        }

        /* compiled from: Handshake.kt */
        public static final class b extends l implements kotlin.jvm.functions.a<List<? extends Certificate>> {
            final /* synthetic */ List $peerCertificatesCopy;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(List list) {
                super(0);
                this.$peerCertificatesCopy = list;
            }

            @NotNull
            public final List<Certificate> invoke() {
                return this.$peerCertificatesCopy;
            }
        }

        private final List<Certificate> c(@Nullable Certificate[] $this$toImmutableList) {
            if ($this$toImmutableList != null) {
                return okhttp3.internal.b.t((Certificate[]) Arrays.copyOf($this$toImmutableList, $this$toImmutableList.length));
            }
            return q.g();
        }

        @NotNull
        public final t b(@NotNull g0 tlsVersion, @NotNull i cipherSuite, @NotNull List<? extends Certificate> peerCertificates, @NotNull List<? extends Certificate> localCertificates) {
            k.f(tlsVersion, "tlsVersion");
            k.f(cipherSuite, "cipherSuite");
            k.f(peerCertificates, "peerCertificates");
            k.f(localCertificates, "localCertificates");
            return new t(tlsVersion, cipherSuite, okhttp3.internal.b.R(localCertificates), new C0477a(okhttp3.internal.b.R(peerCertificates)));
        }

        /* renamed from: okhttp3.t$a$a  reason: collision with other inner class name */
        /* compiled from: Handshake.kt */
        public static final class C0477a extends l implements kotlin.jvm.functions.a<List<? extends Certificate>> {
            final /* synthetic */ List $peerCertificatesCopy;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0477a(List list) {
                super(0);
                this.$peerCertificatesCopy = list;
            }

            @NotNull
            public final List<Certificate> invoke() {
                return this.$peerCertificatesCopy;
            }
        }
    }
}
