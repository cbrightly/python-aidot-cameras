package okhttp3.internal.platform;

import java.security.KeyStore;
import java.security.Provider;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.a0;
import org.bouncycastle.jsse.BCSSLParameters;
import org.bouncycastle.jsse.BCSSLSocket;
import org.bouncycastle.jsse.provider.BouncyCastleJsseProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BouncyCastlePlatform.kt */
public final class c extends h {
    /* access modifiers changed from: private */
    public static final boolean d;
    public static final a e;
    private final Provider f;

    private c() {
        this.f = new BouncyCastleJsseProvider();
    }

    public /* synthetic */ c(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    @NotNull
    public SSLContext n() {
        SSLContext instance = SSLContext.getInstance("TLS", this.f);
        k.b(instance, "SSLContext.getInstance(\"TLS\", provider)");
        return instance;
    }

    @NotNull
    public X509TrustManager p() {
        TrustManagerFactory factory = TrustManagerFactory.getInstance("PKIX", "BCJSSE");
        factory.init((KeyStore) null);
        k.b(factory, "factory");
        TrustManager[] trustManagers = factory.getTrustManagers();
        if (trustManagers == null) {
            k.n();
        }
        boolean z = true;
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            z = false;
        }
        if (z) {
            TrustManager trustManager = trustManagers[0];
            if (trustManager != null) {
                return (X509TrustManager) trustManager;
            }
            throw new TypeCastException("null cannot be cast to non-null type javax.net.ssl.X509TrustManager");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unexpected default trust managers: ");
        String arrays = Arrays.toString(trustManagers);
        k.b(arrays, "java.util.Arrays.toString(this)");
        sb.append(arrays);
        throw new IllegalStateException(sb.toString().toString());
    }

    @Nullable
    public X509TrustManager q(@NotNull SSLSocketFactory sslSocketFactory) {
        k.f(sslSocketFactory, "sslSocketFactory");
        throw new UnsupportedOperationException("clientBuilder.sslSocketFactory(SSLSocketFactory) not supported with BouncyCastle");
    }

    public void e(@NotNull SSLSocket sslSocket, @Nullable String hostname, @NotNull List<a0> protocols) {
        k.f(sslSocket, "sslSocket");
        k.f(protocols, "protocols");
        if (sslSocket instanceof BCSSLSocket) {
            BCSSLParameters sslParameters = ((BCSSLSocket) sslSocket).getParameters();
            Collection $this$toTypedArray$iv = h.c.b(protocols);
            k.b(sslParameters, "sslParameters");
            Object[] array = $this$toTypedArray$iv.toArray(new String[0]);
            if (array != null) {
                sslParameters.setApplicationProtocols((String[]) array);
                ((BCSSLSocket) sslSocket).setParameters(sslParameters);
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        super.e(sslSocket, hostname, protocols);
    }

    @Nullable
    public String h(@NotNull SSLSocket sslSocket) {
        k.f(sslSocket, "sslSocket");
        if (!(sslSocket instanceof BCSSLSocket)) {
            return super.h(sslSocket);
        }
        String protocol = ((BCSSLSocket) sslSocket).getApplicationProtocol();
        if (protocol != null && !k.a(protocol, "")) {
            return protocol;
        }
        return null;
    }

    /* compiled from: BouncyCastlePlatform.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final boolean b() {
            return c.d;
        }

        @Nullable
        public final c a() {
            if (b()) {
                return new c((DefaultConstructorMarker) null);
            }
            return null;
        }
    }

    static {
        a aVar = new a((DefaultConstructorMarker) null);
        e = aVar;
        boolean z = false;
        try {
            Class.forName("org.bouncycastle.jsse.provider.BouncyCastleJsseProvider", false, aVar.getClass().getClassLoader());
            z = true;
        } catch (ClassNotFoundException e2) {
        }
        d = z;
    }
}
