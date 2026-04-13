package okhttp3.internal.platform.android;

import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.a0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SocketAdapter.kt */
public interface k {
    boolean a(@NotNull SSLSocket sSLSocket);

    @Nullable
    String b(@NotNull SSLSocket sSLSocket);

    @Nullable
    X509TrustManager c(@NotNull SSLSocketFactory sSLSocketFactory);

    boolean d(@NotNull SSLSocketFactory sSLSocketFactory);

    void e(@NotNull SSLSocket sSLSocket, @Nullable String str, @NotNull List<? extends a0> list);

    boolean isSupported();

    /* compiled from: SocketAdapter.kt */
    public static final class a {
        @Nullable
        public static X509TrustManager b(k $this, @NotNull SSLSocketFactory sslSocketFactory) {
            kotlin.jvm.internal.k.f(sslSocketFactory, "sslSocketFactory");
            return null;
        }

        public static boolean a(k $this, @NotNull SSLSocketFactory sslSocketFactory) {
            kotlin.jvm.internal.k.f(sslSocketFactory, "sslSocketFactory");
            return false;
        }
    }
}
