package okhttp3.internal.platform;

import java.security.KeyStore;
import java.security.Provider;
import java.util.Arrays;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openjsse.javax.net.ssl.SSLParameters;
import org.openjsse.net.ssl.OpenJSSE;

/* compiled from: OpenJSSEPlatform.kt */
public final class g extends h {
    /* access modifiers changed from: private */
    public static final boolean d;
    public static final a e;
    private final Provider f;

    private g() {
        this.f = new OpenJSSE();
    }

    public /* synthetic */ g(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    @NotNull
    public SSLContext n() {
        SSLContext instance = SSLContext.getInstance("TLSv1.3", this.f);
        k.b(instance, "SSLContext.getInstance(\"TLSv1.3\", provider)");
        return instance;
    }

    @NotNull
    public X509TrustManager p() {
        TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm(), this.f);
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
        throw new UnsupportedOperationException("clientBuilder.sslSocketFactory(SSLSocketFactory) not supported with OpenJSSE");
    }

    public void e(@NotNull SSLSocket sslSocket, @Nullable String hostname, @NotNull List<a0> protocols) {
        k.f(sslSocket, "sslSocket");
        k.f(protocols, "protocols");
        if (sslSocket instanceof org.openjsse.javax.net.ssl.SSLSocket) {
            SSLParameters sSLParameters = ((org.openjsse.javax.net.ssl.SSLSocket) sslSocket).getSSLParameters();
            if (sSLParameters instanceof SSLParameters) {
                SSLParameters sSLParameters2 = sSLParameters;
                Object[] array = h.c.b(protocols).toArray(new String[0]);
                if (array != null) {
                    sSLParameters2.setApplicationProtocols((String[]) array);
                    ((org.openjsse.javax.net.ssl.SSLSocket) sslSocket).setSSLParameters(sSLParameters);
                    return;
                }
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            return;
        }
        super.e(sslSocket, hostname, protocols);
    }

    @Nullable
    public String h(@NotNull SSLSocket sslSocket) {
        k.f(sslSocket, "sslSocket");
        if (!(sslSocket instanceof org.openjsse.javax.net.ssl.SSLSocket)) {
            return super.h(sslSocket);
        }
        String protocol = ((org.openjsse.javax.net.ssl.SSLSocket) sslSocket).getApplicationProtocol();
        if (protocol != null && !k.a(protocol, "")) {
            return protocol;
        }
        return null;
    }

    /* compiled from: OpenJSSEPlatform.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final boolean b() {
            return g.d;
        }

        @Nullable
        public final g a() {
            if (b()) {
                return new g((DefaultConstructorMarker) null);
            }
            return null;
        }
    }

    static {
        a aVar = new a((DefaultConstructorMarker) null);
        e = aVar;
        boolean z = false;
        try {
            Class.forName("org.openjsse.net.ssl.OpenJSSE", false, aVar.getClass().getClassLoader());
            z = true;
        } catch (ClassNotFoundException e2) {
        }
        d = z;
    }
}
