package okhttp3.internal.platform;

import java.util.Collection;
import java.util.List;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.v;
import okhttp3.a0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Jdk9Platform.kt */
public class f extends h {
    /* access modifiers changed from: private */
    public static final boolean d;
    public static final a e = new a((DefaultConstructorMarker) null);

    public void e(@NotNull SSLSocket sslSocket, @Nullable String hostname, @NotNull List<a0> protocols) {
        k.f(sslSocket, "sslSocket");
        k.f(protocols, "protocols");
        SSLParameters sslParameters = sslSocket.getSSLParameters();
        Collection $this$toTypedArray$iv = h.c.b(protocols);
        k.b(sslParameters, "sslParameters");
        Object[] array = $this$toTypedArray$iv.toArray(new String[0]);
        if (array != null) {
            sslParameters.setApplicationProtocols((String[]) array);
            sslSocket.setSSLParameters(sslParameters);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    @Nullable
    public String h(@NotNull SSLSocket sslSocket) {
        k.f(sslSocket, "sslSocket");
        try {
            String protocol = sslSocket.getApplicationProtocol();
            if (protocol == null) {
                return null;
            }
            if (k.a(protocol, "")) {
                return null;
            }
            return protocol;
        } catch (UnsupportedOperationException e2) {
            return null;
        }
    }

    @Nullable
    public X509TrustManager q(@NotNull SSLSocketFactory sslSocketFactory) {
        k.f(sslSocketFactory, "sslSocketFactory");
        throw new UnsupportedOperationException("clientBuilder.sslSocketFactory(SSLSocketFactory) not supported on JDK 9+");
    }

    /* compiled from: Jdk9Platform.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final boolean b() {
            return f.d;
        }

        @Nullable
        public final f a() {
            if (b()) {
                return new f();
            }
            return null;
        }
    }

    static {
        Integer majorVersion = null;
        String jdkVersion = System.getProperty("java.specification.version");
        if (jdkVersion != null) {
            majorVersion = v.o(jdkVersion);
        }
        boolean z = true;
        if (majorVersion == null) {
            try {
                SSLSocket.class.getMethod("getApplicationProtocol", new Class[0]);
            } catch (NoSuchMethodException e2) {
                z = false;
            }
        } else if (majorVersion.intValue() < 9) {
            z = false;
        }
        d = z;
    }
}
