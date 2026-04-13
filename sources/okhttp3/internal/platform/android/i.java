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
import okhttp3.internal.platform.d;
import okhttp3.internal.platform.h;
import org.conscrypt.Conscrypt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ConscryptSocketAdapter.kt */
public final class i implements k {
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
        return Conscrypt.isConscrypt(sslSocket);
    }

    public boolean isSupported() {
        return d.e.c();
    }

    @Nullable
    public String b(@NotNull SSLSocket sslSocket) {
        kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
        if (a(sslSocket)) {
            return Conscrypt.getApplicationProtocol(sslSocket);
        }
        return null;
    }

    public void e(@NotNull SSLSocket sslSocket, @Nullable String hostname, @NotNull List<? extends a0> protocols) {
        kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
        kotlin.jvm.internal.k.f(protocols, "protocols");
        if (a(sslSocket)) {
            Conscrypt.setUseSessionTickets(sslSocket, true);
            Object[] array = h.c.b(protocols).toArray(new String[0]);
            if (array != null) {
                Conscrypt.setApplicationProtocols(sslSocket, (String[]) array);
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
    }

    /* compiled from: ConscryptSocketAdapter.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final j.a a() {
            return i.a;
        }
    }

    /* compiled from: ConscryptSocketAdapter.kt */
    public static final class a implements j.a {
        a() {
        }

        public boolean a(@NotNull SSLSocket sslSocket) {
            kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
            return d.e.c() && Conscrypt.isConscrypt(sslSocket);
        }

        @NotNull
        public k b(@NotNull SSLSocket sslSocket) {
            kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
            return new i();
        }
    }
}
