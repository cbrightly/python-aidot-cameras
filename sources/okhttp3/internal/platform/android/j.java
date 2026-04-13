package okhttp3.internal.platform.android;

import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.jvm.internal.k;
import okhttp3.a0;
import okhttp3.internal.platform.android.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DeferredSocketAdapter.kt */
public final class j implements k {
    private k a;
    private final a b;

    /* compiled from: DeferredSocketAdapter.kt */
    public interface a {
        boolean a(@NotNull SSLSocket sSLSocket);

        @NotNull
        k b(@NotNull SSLSocket sSLSocket);
    }

    public j(@NotNull a socketAdapterFactory) {
        k.f(socketAdapterFactory, "socketAdapterFactory");
        this.b = socketAdapterFactory;
    }

    @Nullable
    public X509TrustManager c(@NotNull SSLSocketFactory sslSocketFactory) {
        k.f(sslSocketFactory, "sslSocketFactory");
        return k.a.b(this, sslSocketFactory);
    }

    public boolean d(@NotNull SSLSocketFactory sslSocketFactory) {
        kotlin.jvm.internal.k.f(sslSocketFactory, "sslSocketFactory");
        return k.a.a(this, sslSocketFactory);
    }

    public boolean isSupported() {
        return true;
    }

    public boolean a(@NotNull SSLSocket sslSocket) {
        kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
        return this.b.a(sslSocket);
    }

    public void e(@NotNull SSLSocket sslSocket, @Nullable String hostname, @NotNull List<? extends a0> protocols) {
        kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
        kotlin.jvm.internal.k.f(protocols, "protocols");
        k f = f(sslSocket);
        if (f != null) {
            f.e(sslSocket, hostname, protocols);
        }
    }

    @Nullable
    public String b(@NotNull SSLSocket sslSocket) {
        kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
        k f = f(sslSocket);
        if (f != null) {
            return f.b(sslSocket);
        }
        return null;
    }

    private final synchronized k f(SSLSocket sslSocket) {
        if (this.a == null && this.b.a(sslSocket)) {
            this.a = this.b.b(sslSocket);
        }
        return this.a;
    }
}
