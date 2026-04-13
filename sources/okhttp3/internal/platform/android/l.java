package okhttp3.internal.platform.android;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.internal.b;
import okhttp3.internal.platform.h;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: StandardAndroidSocketAdapter.kt */
public final class l extends f {
    public static final a h = new a((DefaultConstructorMarker) null);
    private final Class<? super SSLSocketFactory> i;
    private final Class<?> j;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public l(@NotNull Class<? super SSLSocket> sslSocketClass, @NotNull Class<? super SSLSocketFactory> sslSocketFactoryClass, @NotNull Class<?> paramClass) {
        super(sslSocketClass);
        k.f(sslSocketClass, "sslSocketClass");
        k.f(sslSocketFactoryClass, "sslSocketFactoryClass");
        k.f(paramClass, "paramClass");
        this.i = sslSocketFactoryClass;
        this.j = paramClass;
    }

    public boolean d(@NotNull SSLSocketFactory sslSocketFactory) {
        k.f(sslSocketFactory, "sslSocketFactory");
        return this.i.isInstance(sslSocketFactory);
    }

    @Nullable
    public X509TrustManager c(@NotNull SSLSocketFactory sslSocketFactory) {
        k.f(sslSocketFactory, "sslSocketFactory");
        Object context = b.G(sslSocketFactory, this.j, "sslParameters");
        if (context == null) {
            k.n();
        }
        X509TrustManager x509TrustManager = (X509TrustManager) b.G(context, X509TrustManager.class, "x509TrustManager");
        return x509TrustManager != null ? x509TrustManager : (X509TrustManager) b.G(context, X509TrustManager.class, "trustManager");
    }

    /* compiled from: StandardAndroidSocketAdapter.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public static /* synthetic */ k b(a aVar, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = "com.android.org.conscrypt";
            }
            return aVar.a(str);
        }

        @Nullable
        public final k a(@NotNull String packageName) {
            k.f(packageName, "packageName");
            try {
                Class sslSocketClass = Class.forName(packageName + ".OpenSSLSocketImpl");
                Class sslSocketFactoryClass = Class.forName(packageName + ".OpenSSLSocketFactoryImpl");
                Class paramsClass = Class.forName(packageName + ".SSLParametersImpl");
                k.b(paramsClass, "paramsClass");
                return new l(sslSocketClass, sslSocketFactoryClass, paramsClass);
            } catch (Exception e) {
                h.c.g().k("unable to load android socket classes", 5, e);
                return null;
            }
        }
    }
}
