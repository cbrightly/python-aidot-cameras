package okhttp3;

import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLPeerUnverifiedException;
import kotlin.TypeCastException;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.e0;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.text.w;
import kotlin.text.x;
import okio.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CertificatePinner.kt */
public final class g {
    @NotNull
    public static final g a = new a().a();
    public static final b b = new b((DefaultConstructorMarker) null);
    @NotNull
    private final Set<c> c;
    @Nullable
    private final okhttp3.internal.tls.c d;

    public g(@NotNull Set<c> pins, @Nullable okhttp3.internal.tls.c certificateChainCleaner) {
        k.f(pins, "pins");
        this.c = pins;
        this.d = certificateChainCleaner;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ g(Set set, okhttp3.internal.tls.c cVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(set, (i & 2) != 0 ? null : cVar);
    }

    @Nullable
    public final okhttp3.internal.tls.c d() {
        return this.d;
    }

    /* compiled from: CertificatePinner.kt */
    public static final class d extends l implements kotlin.jvm.functions.a<List<? extends X509Certificate>> {
        final /* synthetic */ String $hostname;
        final /* synthetic */ List $peerCertificates;
        final /* synthetic */ g this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(g gVar, List list, String str) {
            super(0);
            this.this$0 = gVar;
            this.$peerCertificates = list;
            this.$hostname = str;
        }

        @NotNull
        public final List<X509Certificate> invoke() {
            Iterable<Certificate> $this$map$iv;
            okhttp3.internal.tls.c d = this.this$0.d();
            if (d == null || ($this$map$iv = d.a(this.$peerCertificates, this.$hostname)) == null) {
                $this$map$iv = this.$peerCertificates;
            }
            ArrayList arrayList = new ArrayList(r.r($this$map$iv, 10));
            for (Certificate it : $this$map$iv) {
                if (it != null) {
                    arrayList.add((X509Certificate) it);
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
                }
            }
            return arrayList;
        }
    }

    public final void a(@NotNull String hostname, @NotNull List<? extends Certificate> peerCertificates) {
        k.f(hostname, "hostname");
        k.f(peerCertificates, "peerCertificates");
        b(hostname, new d(this, peerCertificates, hostname));
    }

    public final void b(@NotNull String hostname, @NotNull kotlin.jvm.functions.a<? extends List<? extends X509Certificate>> cleanedPeerCertificatesFn) {
        c pin;
        k.f(hostname, "hostname");
        k.f(cleanedPeerCertificatesFn, "cleanedPeerCertificatesFn");
        List<c> pins = c(hostname);
        if (!pins.isEmpty()) {
            List<X509Certificate> peerCertificates = (List) cleanedPeerCertificatesFn.invoke();
            for (X509Certificate peerCertificate : peerCertificates) {
                f sha256 = null;
                f sha1 = null;
                Iterator<c> it = pins.iterator();
                while (true) {
                    if (it.hasNext()) {
                        pin = it.next();
                        String b2 = pin.b();
                        switch (b2.hashCode()) {
                            case -903629273:
                                if (!b2.equals("sha256")) {
                                    break;
                                } else {
                                    if (sha256 == null) {
                                        sha256 = b.c(peerCertificate);
                                    }
                                    if (!k.a(pin.a(), sha256)) {
                                        break;
                                    } else {
                                        return;
                                    }
                                }
                            case 3528965:
                                if (!b2.equals("sha1")) {
                                    break;
                                } else {
                                    if (sha1 == null) {
                                        sha1 = b.b(peerCertificate);
                                    }
                                    if (!k.a(pin.a(), sha1)) {
                                        break;
                                    } else {
                                        return;
                                    }
                                }
                        }
                    }
                }
                throw new AssertionError("unsupported hashAlgorithm: " + pin.b());
            }
            StringBuilder sb = new StringBuilder();
            StringBuilder $this$buildString = sb;
            $this$buildString.append("Certificate pinning failure!");
            $this$buildString.append("\n  Peer certificate chain:");
            for (X509Certificate element : peerCertificates) {
                $this$buildString.append("\n    ");
                $this$buildString.append(b.a(element));
                $this$buildString.append(": ");
                Principal subjectDN = element.getSubjectDN();
                k.b(subjectDN, "element.subjectDN");
                $this$buildString.append(subjectDN.getName());
            }
            $this$buildString.append("\n  Pinned certificates for ");
            $this$buildString.append(hostname);
            $this$buildString.append(":");
            for (c pin2 : pins) {
                $this$buildString.append("\n    ");
                $this$buildString.append(pin2);
            }
            String message = sb.toString();
            k.b(message, "StringBuilder().apply(builderAction).toString()");
            throw new SSLPeerUnverifiedException(message);
        }
    }

    @NotNull
    public final List<c> c(@NotNull String hostname) {
        k.f(hostname, "hostname");
        Iterable $this$filterList$iv = this.c;
        List result$iv = q.g();
        for (T next : $this$filterList$iv) {
            if (((c) next).c(hostname)) {
                if (result$iv.isEmpty()) {
                    result$iv = new ArrayList();
                }
                e0.b(result$iv).add(next);
            }
        }
        return result$iv;
    }

    @NotNull
    public final g e(@NotNull okhttp3.internal.tls.c certificateChainCleaner) {
        k.f(certificateChainCleaner, "certificateChainCleaner");
        if (k.a(this.d, certificateChainCleaner)) {
            return this;
        }
        return new g(this.c, certificateChainCleaner);
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof g) && k.a(((g) other).c, this.c) && k.a(((g) other).d, this.d);
    }

    public int hashCode() {
        int result = ((37 * 41) + this.c.hashCode()) * 41;
        okhttp3.internal.tls.c cVar = this.d;
        return result + (cVar != null ? cVar.hashCode() : 0);
    }

    /* compiled from: CertificatePinner.kt */
    public static final class c {
        @NotNull
        private final String a;
        @NotNull
        private final String b;
        @NotNull
        private final f c;

        @NotNull
        public final String b() {
            return this.b;
        }

        @NotNull
        public final f a() {
            return this.c;
        }

        public final boolean c(@NotNull String hostname) {
            k.f(hostname, "hostname");
            if (w.N(this.a, "**.", false, 2, (Object) null)) {
                int suffixLength = this.a.length() - 3;
                int prefixLength = hostname.length() - suffixLength;
                if (!w.C(hostname, hostname.length() - suffixLength, this.a, 3, suffixLength, false, 16, (Object) null)) {
                    return false;
                }
                if (prefixLength == 0 || hostname.charAt(prefixLength - 1) == '.') {
                    return true;
                }
                return false;
            } else if (!w.N(this.a, "*.", false, 2, (Object) null)) {
                return k.a(hostname, this.a);
            } else {
                int suffixLength2 = this.a.length() - 1;
                int prefixLength2 = hostname.length() - suffixLength2;
                if (!w.C(hostname, hostname.length() - suffixLength2, this.a, 1, suffixLength2, false, 16, (Object) null)) {
                    return false;
                }
                if (x.j0(hostname, '.', prefixLength2 - 1, false, 4, (Object) null) == -1) {
                    return true;
                }
                return false;
            }
        }

        @NotNull
        public String toString() {
            return this.b + '/' + this.c.base64();
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if ((other instanceof c) && !(!k.a(this.a, ((c) other).a)) && !(!k.a(this.b, ((c) other).b)) && !(!k.a(this.c, ((c) other).c))) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode();
        }
    }

    /* compiled from: CertificatePinner.kt */
    public static final class a {
        @NotNull
        private final List<c> a = new ArrayList();

        @NotNull
        public final g a() {
            return new g(y.H0(this.a), (okhttp3.internal.tls.c) null, 2, (DefaultConstructorMarker) null);
        }
    }

    /* compiled from: CertificatePinner.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final f b(@NotNull X509Certificate $this$sha1Hash) {
            k.f($this$sha1Hash, "$this$sha1Hash");
            f.a aVar = f.Companion;
            PublicKey publicKey = $this$sha1Hash.getPublicKey();
            k.b(publicKey, "publicKey");
            byte[] encoded = publicKey.getEncoded();
            k.b(encoded, "publicKey.encoded");
            return f.a.h(aVar, encoded, 0, 0, 3, (Object) null).sha1();
        }

        @NotNull
        public final f c(@NotNull X509Certificate $this$sha256Hash) {
            k.f($this$sha256Hash, "$this$sha256Hash");
            f.a aVar = f.Companion;
            PublicKey publicKey = $this$sha256Hash.getPublicKey();
            k.b(publicKey, "publicKey");
            byte[] encoded = publicKey.getEncoded();
            k.b(encoded, "publicKey.encoded");
            return f.a.h(aVar, encoded, 0, 0, 3, (Object) null).sha256();
        }

        @NotNull
        public final String a(@NotNull Certificate certificate) {
            k.f(certificate, "certificate");
            if (certificate instanceof X509Certificate) {
                return "sha256/" + c((X509Certificate) certificate).base64();
            }
            throw new IllegalArgumentException("Certificate pinning requires X509 certificates".toString());
        }
    }
}
