package okhttp3.internal.platform;

import com.google.android.libraries.places.api.model.PlaceTypes;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.TypeCastException;
import kotlin.collections.r;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.a0;
import okhttp3.internal.b;
import okhttp3.internal.tls.c;
import okhttp3.internal.tls.e;
import okhttp3.z;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Platform.kt */
public class h {
    /* access modifiers changed from: private */
    public static volatile h a;
    private static final Logger b = Logger.getLogger(z.class.getName());
    public static final a c;

    @NotNull
    public final String g() {
        return "OkHttp";
    }

    @NotNull
    public SSLContext n() {
        SSLContext instance = SSLContext.getInstance("TLS");
        k.b(instance, "SSLContext.getInstance(\"TLS\")");
        return instance;
    }

    @NotNull
    public X509TrustManager p() {
        TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
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
        try {
            Class sslContextClass = Class.forName("sun.security.ssl.SSLContextImpl");
            k.b(sslContextClass, "sslContextClass");
            Object context = b.G(sslSocketFactory, sslContextClass, "context");
            if (context != null) {
                return (X509TrustManager) b.G(context, X509TrustManager.class, "trustManager");
            }
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public void e(@NotNull SSLSocket sslSocket, @Nullable String hostname, @NotNull List<a0> protocols) {
        k.f(sslSocket, "sslSocket");
        k.f(protocols, "protocols");
    }

    public void b(@NotNull SSLSocket sslSocket) {
        k.f(sslSocket, "sslSocket");
    }

    @Nullable
    public String h(@NotNull SSLSocket sslSocket) {
        k.f(sslSocket, "sslSocket");
        return null;
    }

    public void f(@NotNull Socket socket, @NotNull InetSocketAddress address, int connectTimeout) {
        k.f(socket, "socket");
        k.f(address, PlaceTypes.ADDRESS);
        socket.connect(address, connectTimeout);
    }

    public static /* synthetic */ void l(h hVar, String str, int i, Throwable th, int i2, Object obj) {
        if (obj == null) {
            if ((i2 & 2) != 0) {
                i = 4;
            }
            if ((i2 & 4) != 0) {
                th = null;
            }
            hVar.k(str, i, th);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: log");
    }

    public void k(@NotNull String message, int level, @Nullable Throwable t) {
        k.f(message, "message");
        b.log(level == 5 ? Level.WARNING : Level.INFO, message, t);
    }

    public boolean j(@NotNull String hostname) {
        k.f(hostname, "hostname");
        return true;
    }

    @Nullable
    public Object i(@NotNull String closer) {
        k.f(closer, "closer");
        if (b.isLoggable(Level.FINE)) {
            return new Throwable(closer);
        }
        return null;
    }

    public void m(@NotNull String message, @Nullable Object stackTrace) {
        k.f(message, "message");
        String logMessage = message;
        if (stackTrace == null) {
            logMessage = logMessage + " To see where this was allocated, set the OkHttpClient logger level to FINE: Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);";
        }
        k(logMessage, 5, (Throwable) stackTrace);
    }

    @NotNull
    public c c(@NotNull X509TrustManager trustManager) {
        k.f(trustManager, "trustManager");
        return new okhttp3.internal.tls.a(d(trustManager));
    }

    @NotNull
    public e d(@NotNull X509TrustManager trustManager) {
        k.f(trustManager, "trustManager");
        X509Certificate[] acceptedIssuers = trustManager.getAcceptedIssuers();
        k.b(acceptedIssuers, "trustManager.acceptedIssuers");
        return new okhttp3.internal.tls.b((X509Certificate[]) Arrays.copyOf(acceptedIssuers, acceptedIssuers.length));
    }

    @NotNull
    public SSLSocketFactory o(@NotNull X509TrustManager trustManager) {
        k.f(trustManager, "trustManager");
        try {
            SSLContext $this$apply = n();
            $this$apply.init((KeyManager[]) null, new TrustManager[]{trustManager}, (SecureRandom) null);
            SSLSocketFactory socketFactory = $this$apply.getSocketFactory();
            k.b(socketFactory, "newSSLContext().apply {\n…ll)\n      }.socketFactory");
            return socketFactory;
        } catch (GeneralSecurityException e) {
            throw new AssertionError("No System TLS: " + e, e);
        }
    }

    @NotNull
    public String toString() {
        String simpleName = getClass().getSimpleName();
        k.b(simpleName, "javaClass.simpleName");
        return simpleName;
    }

    /* compiled from: Platform.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final h g() {
            return h.a;
        }

        @NotNull
        public final List<String> b(@NotNull List<? extends a0> protocols) {
            k.f(protocols, "protocols");
            ArrayList arrayList = new ArrayList();
            for (T next : protocols) {
                if (((a0) next) != a0.HTTP_1_0) {
                    arrayList.add(next);
                }
            }
            Iterable<a0> $this$mapTo$iv$iv = arrayList;
            ArrayList arrayList2 = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (a0 it : $this$mapTo$iv$iv) {
                arrayList2.add(it.toString());
            }
            return arrayList2;
        }

        public final boolean h() {
            return k.a("Dalvik", System.getProperty("java.vm.name"));
        }

        private final boolean j() {
            Provider provider = Security.getProviders()[0];
            k.b(provider, "Security.getProviders()[0]");
            return k.a("Conscrypt", provider.getName());
        }

        private final boolean k() {
            Provider provider = Security.getProviders()[0];
            k.b(provider, "Security.getProviders()[0]");
            return k.a("OpenJSSE", provider.getName());
        }

        private final boolean i() {
            Provider provider = Security.getProviders()[0];
            k.b(provider, "Security.getProviders()[0]");
            return k.a("BC", provider.getName());
        }

        /* access modifiers changed from: private */
        public final h f() {
            if (h()) {
                return d();
            }
            return e();
        }

        private final h d() {
            okhttp3.internal.platform.android.c.c.b();
            h a = a.e.a();
            if (a == null && (a = b.e.a()) == null) {
                k.n();
            }
            return a;
        }

        private final h e() {
            g openJSSE;
            c bc;
            d conscrypt;
            if (j() && (conscrypt = d.e.b()) != null) {
                return conscrypt;
            }
            if (i() && (bc = c.e.a()) != null) {
                return bc;
            }
            if (k() && (openJSSE = g.e.a()) != null) {
                return openJSSE;
            }
            f jdk9 = f.e.a();
            if (jdk9 != null) {
                return jdk9;
            }
            h jdkWithJettyBoot = e.d.a();
            if (jdkWithJettyBoot != null) {
                return jdkWithJettyBoot;
            }
            return new h();
        }

        @NotNull
        public final byte[] c(@NotNull List<? extends a0> protocols) {
            k.f(protocols, "protocols");
            okio.c result = new okio.c();
            for (String protocol : b(protocols)) {
                result.writeByte(protocol.length());
                result.writeUtf8(protocol);
            }
            return result.q0();
        }
    }

    static {
        a aVar = new a((DefaultConstructorMarker) null);
        c = aVar;
        a = aVar.f();
    }
}
