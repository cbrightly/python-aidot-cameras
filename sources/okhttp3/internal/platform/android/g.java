package okhttp3.internal.platform.android;

import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.a0;
import okhttp3.internal.platform.android.j;
import okhttp3.internal.platform.android.k;
import okhttp3.internal.platform.c;
import okhttp3.internal.platform.h;
import org.bouncycastle.jsse.BCSSLParameters;
import org.bouncycastle.jsse.BCSSLSocket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BouncyCastleSocketAdapter.kt */
public final class g implements k {
    /* access modifiers changed from: private */
    @NotNull
    public static final j.a a = new a();
    public static final b b = new b((DefaultConstructorMarker) null);

    @Nullable
    public X509TrustManager c(@NotNull SSLSocketFactory sslSocketFactory) {
        k.f(sslSocketFactory, "sslSocketFactory");
        return k.a.b(this, sslSocketFactory);
    }

    public boolean d(@NotNull SSLSocketFactory sslSocketFactory) {
        kotlin.jvm.internal.k.f(sslSocketFactory, "sslSocketFactory");
        return k.a.a(this, sslSocketFactory);
    }

    public boolean a(@NotNull SSLSocket sslSocket) {
        kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
        return sslSocket instanceof BCSSLSocket;
    }

    public boolean isSupported() {
        return c.e.b();
    }

    @Nullable
    public String b(@NotNull SSLSocket sslSocket) {
        kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
        String protocol = ((BCSSLSocket) sslSocket).getApplicationProtocol();
        if (protocol != null && !kotlin.jvm.internal.k.a(protocol, "")) {
            return protocol;
        }
        return null;
    }

    public void e(@NotNull SSLSocket sslSocket, @Nullable String hostname, @NotNull List<? extends a0> protocols) {
        kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
        kotlin.jvm.internal.k.f(protocols, "protocols");
        if (a(sslSocket)) {
            BCSSLSocket bcSocket = (BCSSLSocket) sslSocket;
            BCSSLParameters sslParameters = bcSocket.getParameters();
            kotlin.jvm.internal.k.b(sslParameters, "sslParameters");
            Object[] array = h.c.b(protocols).toArray(new String[0]);
            if (array != null) {
                sslParameters.setApplicationProtocols((String[]) array);
                bcSocket.setParameters(sslParameters);
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
    }

    /* compiled from: BouncyCastleSocketAdapter.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final j.a a() {
            return g.a;
        }
    }

    /* compiled from: BouncyCastleSocketAdapter.kt */
    public static final class a implements j.a {
        a() {
        }

        public boolean a(@NotNull SSLSocket sslSocket) {
            kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
            return c.e.b() && (sslSocket instanceof BCSSLSocket);
        }

        @NotNull
        public k b(@NotNull SSLSocket sslSocket) {
            kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
            return new g();
        }
    }
}
