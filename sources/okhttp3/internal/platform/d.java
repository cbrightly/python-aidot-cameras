package okhttp3.internal.platform;

import java.security.KeyStore;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.KeyManager;
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
import org.conscrypt.Conscrypt;
import org.conscrypt.ConscryptHostnameVerifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ConscryptPlatform.kt */
public final class d extends h {
    /* access modifiers changed from: private */
    public static final boolean d;
    public static final a e;
    private final Provider f;

    /* compiled from: ConscryptPlatform.kt */
    public static final class b implements ConscryptHostnameVerifier {
        public static final b a = new b();

        b() {
        }
    }

    private d() {
        Provider build = Conscrypt.newProviderBuilder().provideTrustManager(true).build();
        k.b(build, "Conscrypt.newProviderBui…rustManager(true).build()");
        this.f = build;
    }

    public /* synthetic */ d(DefaultConstructorMarker $constructor_marker) {
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
        TrustManagerFactory $this$apply = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        $this$apply.init((KeyStore) null);
        k.b($this$apply, "TrustManagerFactory.getI…(null as KeyStore?)\n    }");
        TrustManager[] trustManagers = $this$apply.getTrustManagers();
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
                X509TrustManager x509TrustManager = (X509TrustManager) trustManager;
                Conscrypt.setHostnameVerifier(x509TrustManager, b.a);
                return x509TrustManager;
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
        return null;
    }

    public void e(@NotNull SSLSocket sslSocket, @Nullable String hostname, @NotNull List<a0> protocols) {
        k.f(sslSocket, "sslSocket");
        k.f(protocols, "protocols");
        if (Conscrypt.isConscrypt(sslSocket)) {
            Conscrypt.setUseSessionTickets(sslSocket, true);
            Object[] array = h.c.b(protocols).toArray(new String[0]);
            if (array != null) {
                Conscrypt.setApplicationProtocols(sslSocket, (String[]) array);
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        super.e(sslSocket, hostname, protocols);
    }

    @Nullable
    public String h(@NotNull SSLSocket sslSocket) {
        k.f(sslSocket, "sslSocket");
        if (Conscrypt.isConscrypt(sslSocket)) {
            return Conscrypt.getApplicationProtocol(sslSocket);
        }
        return super.h(sslSocket);
    }

    @NotNull
    public SSLSocketFactory o(@NotNull X509TrustManager trustManager) {
        k.f(trustManager, "trustManager");
        SSLContext $this$apply = n();
        $this$apply.init((KeyManager[]) null, new TrustManager[]{trustManager}, (SecureRandom) null);
        SSLSocketFactory it = $this$apply.getSocketFactory();
        Conscrypt.setUseEngineSocket(it, true);
        k.b(it, "newSSLContext().apply {\n…ineSocket(it, true)\n    }");
        return it;
    }

    /* compiled from: ConscryptPlatform.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final boolean c() {
            return d.d;
        }

        @Nullable
        public final d b() {
            if (c()) {
                return new d((DefaultConstructorMarker) null);
            }
            return null;
        }

        public final boolean a(int major, int minor, int patch) {
            Conscrypt.Version conscryptVersion = Conscrypt.version();
            if (conscryptVersion.major() != major) {
                if (conscryptVersion.major() > major) {
                    return true;
                }
                return false;
            } else if (conscryptVersion.minor() != minor) {
                if (conscryptVersion.minor() > minor) {
                    return true;
                }
                return false;
            } else if (conscryptVersion.patch() >= patch) {
                return true;
            } else {
                return false;
            }
        }
    }

    static {
        a aVar = new a((DefaultConstructorMarker) null);
        e = aVar;
        boolean z = false;
        try {
            Class.forName("org.conscrypt.Conscrypt$Version", false, aVar.getClass().getClassLoader());
            if (Conscrypt.isAvailable() && aVar.a(2, 1, 0)) {
                z = true;
            }
        } catch (ClassNotFoundException | NoClassDefFoundError e2) {
        }
        d = z;
    }
}
