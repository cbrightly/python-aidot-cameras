package okhttp3.internal.platform;

import android.annotation.SuppressLint;
import android.os.Build;
import android.security.NetworkSecurityPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import okhttp3.a0;
import okhttp3.internal.platform.android.b;
import okhttp3.internal.platform.android.f;
import okhttp3.internal.platform.android.g;
import okhttp3.internal.platform.android.i;
import okhttp3.internal.platform.android.j;
import okhttp3.internal.platform.android.k;
import okhttp3.internal.tls.c;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Android10Platform.kt */
public final class a extends h {
    /* access modifiers changed from: private */
    public static final boolean d = (h.c.h() && Build.VERSION.SDK_INT >= 29);
    public static final C0467a e = new C0467a((DefaultConstructorMarker) null);
    private final List<k> f;

    public a() {
        Iterable $this$filterTo$iv$iv = q.l(okhttp3.internal.platform.android.a.a.a(), new j(f.b.d()), new j(i.b.a()), new j(g.b.a()));
        ArrayList arrayList = new ArrayList();
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            if (((k) element$iv$iv).isSupported()) {
                arrayList.add(element$iv$iv);
            }
        }
        this.f = arrayList;
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

    public void e(@NotNull SSLSocket sslSocket, @Nullable String hostname, @NotNull List<? extends a0> protocols) {
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

    @SuppressLint({"NewApi"})
    public boolean j(@NotNull String hostname) {
        kotlin.jvm.internal.k.f(hostname, "hostname");
        return NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted(hostname);
    }

    @NotNull
    public c c(@NotNull X509TrustManager trustManager) {
        kotlin.jvm.internal.k.f(trustManager, "trustManager");
        b a = b.b.a(trustManager);
        return a != null ? a : super.c(trustManager);
    }

    /* renamed from: okhttp3.internal.platform.a$a  reason: collision with other inner class name */
    /* compiled from: Android10Platform.kt */
    public static final class C0467a {
        private C0467a() {
        }

        public /* synthetic */ C0467a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final boolean b() {
            return a.d;
        }

        @Nullable
        public final h a() {
            if (b()) {
                return new a();
            }
            return null;
        }
    }
}
