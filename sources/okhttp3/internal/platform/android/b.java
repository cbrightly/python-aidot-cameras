package okhttp3.internal.platform.android;

import android.net.http.X509TrustManagerExtensions;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.X509TrustManager;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.internal.tls.c;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AndroidCertificateChainCleaner.kt */
public final class b extends c {
    public static final a b = new a((DefaultConstructorMarker) null);
    private final X509TrustManager c;
    private final X509TrustManagerExtensions d;

    public b(@NotNull X509TrustManager trustManager, @NotNull X509TrustManagerExtensions x509TrustManagerExtensions) {
        k.f(trustManager, "trustManager");
        k.f(x509TrustManagerExtensions, "x509TrustManagerExtensions");
        this.c = trustManager;
        this.d = x509TrustManagerExtensions;
    }

    @NotNull
    public List<Certificate> a(@NotNull List<? extends Certificate> chain, @NotNull String hostname) {
        k.f(chain, "chain");
        k.f(hostname, "hostname");
        Object[] array = chain.toArray(new X509Certificate[0]);
        if (array != null) {
            try {
                List<X509Certificate> checkServerTrusted = this.d.checkServerTrusted((X509Certificate[]) array, "RSA", hostname);
                k.b(checkServerTrusted, "x509TrustManagerExtensio…ficates, \"RSA\", hostname)");
                return checkServerTrusted;
            } catch (CertificateException ce) {
                SSLPeerUnverifiedException $this$apply = new SSLPeerUnverifiedException(ce.getMessage());
                $this$apply.initCause(ce);
                throw $this$apply;
            }
        } else {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof b) && ((b) other).c == this.c;
    }

    public int hashCode() {
        return System.identityHashCode(this.c);
    }

    /* compiled from: AndroidCertificateChainCleaner.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @Nullable
        public final b a(@NotNull X509TrustManager trustManager) {
            X509TrustManagerExtensions extensions;
            k.f(trustManager, "trustManager");
            try {
                extensions = new X509TrustManagerExtensions(trustManager);
            } catch (IllegalArgumentException e) {
                extensions = null;
            }
            if (extensions != null) {
                return new b(trustManager, extensions);
            }
            return null;
        }
    }
}
