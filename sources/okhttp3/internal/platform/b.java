package okhttp3.internal.platform;

import android.os.Build;
import android.security.NetworkSecurityPolicy;
import com.google.android.libraries.places.api.model.PlaceTypes;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.TypeCastException;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import okhttp3.a0;
import okhttp3.internal.platform.android.f;
import okhttp3.internal.platform.android.g;
import okhttp3.internal.platform.android.h;
import okhttp3.internal.platform.android.i;
import okhttp3.internal.platform.android.j;
import okhttp3.internal.platform.android.k;
import okhttp3.internal.platform.android.l;
import okhttp3.internal.tls.c;
import okhttp3.internal.tls.e;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AndroidPlatform.kt */
public final class b extends h {
    /* access modifiers changed from: private */
    public static final boolean d;
    public static final a e = new a((DefaultConstructorMarker) null);
    private final List<k> f;
    private final h g;

    public b() {
        Iterable $this$filterTo$iv$iv = q.l(l.a.b(l.h, (String) null, 1, (Object) null), new j(f.b.d()), new j(i.b.a()), new j(g.b.a()));
        ArrayList arrayList = new ArrayList();
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            if (((k) element$iv$iv).isSupported()) {
                arrayList.add(element$iv$iv);
            }
        }
        this.f = arrayList;
        this.g = h.a.a();
    }

    public void f(@NotNull Socket socket, @NotNull InetSocketAddress address, int connectTimeout) {
        kotlin.jvm.internal.k.f(socket, "socket");
        kotlin.jvm.internal.k.f(address, PlaceTypes.ADDRESS);
        try {
            socket.connect(address, connectTimeout);
        } catch (ClassCastException e2) {
            if (Build.VERSION.SDK_INT == 26) {
                throw new IOException("Exception in connect", e2);
            }
            throw e2;
        }
    }

    @Nullable
    public X509TrustManager q(@NotNull SSLSocketFactory sslSocketFactory) {
        T t;
        kotlin.jvm.internal.k.f(sslSocketFactory, "sslSocketFactory");
        Iterator<T> it = this.f.iterator();
        while (true) {
            if (!it.hasNext()) {
                t = null;
                break;
            }
            t = it.next();
            if (((k) t).d(sslSocketFactory)) {
                break;
            }
        }
        k kVar = (k) t;
        if (kVar != null) {
            return kVar.c(sslSocketFactory);
        }
        return null;
    }

    public void e(@NotNull SSLSocket sslSocket, @Nullable String hostname, @NotNull List<a0> protocols) {
        T t;
        kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
        kotlin.jvm.internal.k.f(protocols, "protocols");
        Iterator<T> it = this.f.iterator();
        while (true) {
            if (!it.hasNext()) {
                t = null;
                break;
            }
            t = it.next();
            if (((k) t).a(sslSocket)) {
                break;
            }
        }
        k kVar = (k) t;
        if (kVar != null) {
            kVar.e(sslSocket, hostname, protocols);
        }
    }

    @Nullable
    public String h(@NotNull SSLSocket sslSocket) {
        T t;
        kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
        Iterator<T> it = this.f.iterator();
        while (true) {
            if (!it.hasNext()) {
                t = null;
                break;
            }
            t = it.next();
            if (((k) t).a(sslSocket)) {
                break;
            }
        }
        k kVar = (k) t;
        if (kVar != null) {
            return kVar.b(sslSocket);
        }
        return null;
    }

    @Nullable
    public Object i(@NotNull String closer) {
        kotlin.jvm.internal.k.f(closer, "closer");
        return this.g.a(closer);
    }

    public void m(@NotNull String message, @Nullable Object stackTrace) {
        kotlin.jvm.internal.k.f(message, "message");
        if (!this.g.b(stackTrace)) {
            h.l(this, message, 5, (Throwable) null, 4, (Object) null);
        }
    }

    public boolean j(@NotNull String hostname) {
        kotlin.jvm.internal.k.f(hostname, "hostname");
        int i = Build.VERSION.SDK_INT;
        if (i >= 24) {
            return NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted(hostname);
        }
        if (i < 23) {
            return true;
        }
        NetworkSecurityPolicy instance = NetworkSecurityPolicy.getInstance();
        kotlin.jvm.internal.k.b(instance, "NetworkSecurityPolicy.getInstance()");
        return instance.isCleartextTrafficPermitted();
    }

    @NotNull
    public c c(@NotNull X509TrustManager trustManager) {
        kotlin.jvm.internal.k.f(trustManager, "trustManager");
        okhttp3.internal.platform.android.b a2 = okhttp3.internal.platform.android.b.b.a(trustManager);
        return a2 != null ? a2 : super.c(trustManager);
    }

    @NotNull
    public e d(@NotNull X509TrustManager trustManager) {
        kotlin.jvm.internal.k.f(trustManager, "trustManager");
        try {
            Method method = trustManager.getClass().getDeclaredMethod("findTrustAnchorByIssuerAndSignature", new Class[]{X509Certificate.class});
            kotlin.jvm.internal.k.b(method, FirebaseAnalytics.Param.METHOD);
            method.setAccessible(true);
            return new C0470b(trustManager, method);
        } catch (NoSuchMethodException e2) {
            return super.d(trustManager);
        }
    }

    /* renamed from: okhttp3.internal.platform.b$b  reason: collision with other inner class name */
    /* compiled from: AndroidPlatform.kt */
    public static final class C0470b implements e {
        private final X509TrustManager a;
        private final Method b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof C0470b)) {
                return false;
            }
            C0470b bVar = (C0470b) obj;
            return kotlin.jvm.internal.k.a(this.a, bVar.a) && kotlin.jvm.internal.k.a(this.b, bVar.b);
        }

        public int hashCode() {
            X509TrustManager x509TrustManager = this.a;
            int i = 0;
            int hashCode = (x509TrustManager != null ? x509TrustManager.hashCode() : 0) * 31;
            Method method = this.b;
            if (method != null) {
                i = method.hashCode();
            }
            return hashCode + i;
        }

        @NotNull
        public String toString() {
            return "CustomTrustRootIndex(trustManager=" + this.a + ", findByIssuerAndSignatureMethod=" + this.b + ")";
        }

        public C0470b(@NotNull X509TrustManager trustManager, @NotNull Method findByIssuerAndSignatureMethod) {
            kotlin.jvm.internal.k.f(trustManager, "trustManager");
            kotlin.jvm.internal.k.f(findByIssuerAndSignatureMethod, "findByIssuerAndSignatureMethod");
            this.a = trustManager;
            this.b = findByIssuerAndSignatureMethod;
        }

        @Nullable
        public X509Certificate a(@NotNull X509Certificate cert) {
            kotlin.jvm.internal.k.f(cert, "cert");
            try {
                Object invoke = this.b.invoke(this.a, new Object[]{cert});
                if (invoke != null) {
                    return ((TrustAnchor) invoke).getTrustedCert();
                }
                throw new TypeCastException("null cannot be cast to non-null type java.security.cert.TrustAnchor");
            } catch (IllegalAccessException e) {
                throw new AssertionError("unable to get issues and signature", e);
            } catch (InvocationTargetException e2) {
                return null;
            }
        }
    }

    /* compiled from: AndroidPlatform.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final boolean b() {
            return b.d;
        }

        @Nullable
        public final h a() {
            if (b()) {
                return new b();
            }
            return null;
        }
    }

    static {
        boolean z = true;
        boolean z2 = false;
        if (!h.c.h()) {
            z = false;
        } else {
            int i = Build.VERSION.SDK_INT;
            if (i >= 30) {
                z = false;
            } else {
                if (i >= 21) {
                    z2 = true;
                }
                if (!z2) {
                    throw new IllegalStateException(("Expected Android API level 21+ but was " + i).toString());
                }
            }
        }
        d = z;
    }
}
