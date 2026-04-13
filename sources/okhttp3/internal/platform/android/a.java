package okhttp3.internal.platform.android;

import android.annotation.SuppressLint;
import android.net.ssl.SSLSockets;
import android.os.Build;
import java.io.IOException;
import java.util.List;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.a0;
import okhttp3.internal.platform.android.k;
import okhttp3.internal.platform.h;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressLint({"NewApi"})
/* compiled from: Android10SocketAdapter.kt */
public final class a implements k {
    public static final C0468a a = new C0468a((DefaultConstructorMarker) null);

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
        return SSLSockets.isSupportedSocket(sslSocket);
    }

    public boolean isSupported() {
        return a.b();
    }

    @Nullable
    @SuppressLint({"NewApi"})
    public String b(@NotNull SSLSocket sslSocket) {
        kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
        String protocol = sslSocket.getApplicationProtocol();
        if (protocol != null && !kotlin.jvm.internal.k.a(protocol, "")) {
            return protocol;
        }
        return null;
    }

    @SuppressLint({"NewApi"})
    public void e(@NotNull SSLSocket sslSocket, @Nullable String hostname, @NotNull List<? extends a0> protocols) {
        kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
        kotlin.jvm.internal.k.f(protocols, "protocols");
        try {
            SSLSockets.setUseSessionTickets(sslSocket, true);
            SSLParameters sslParameters = sslSocket.getSSLParameters();
            kotlin.jvm.internal.k.b(sslParameters, "sslParameters");
            Object[] array = h.c.b(protocols).toArray(new String[0]);
            if (array != null) {
                sslParameters.setApplicationProtocols((String[]) array);
                sslSocket.setSSLParameters(sslParameters);
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        } catch (IllegalArgumentException iae) {
            throw new IOException("Android internal error", iae);
        }
    }

    /* renamed from: okhttp3.internal.platform.android.a$a  reason: collision with other inner class name */
    /* compiled from: Android10SocketAdapter.kt */
    public static final class C0468a {
        private C0468a() {
        }

        public /* synthetic */ C0468a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @Nullable
        public final k a() {
            if (b()) {
                return new a();
            }
            return null;
        }

        public final boolean b() {
            return h.c.h() && Build.VERSION.SDK_INT >= 29;
        }
    }
}
