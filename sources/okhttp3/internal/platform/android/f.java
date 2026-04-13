package okhttp3.internal.platform.android;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import okhttp3.a0;
import okhttp3.internal.platform.android.j;
import okhttp3.internal.platform.android.k;
import okhttp3.internal.platform.b;
import okhttp3.internal.platform.h;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AndroidSocketAdapter.kt */
public class f implements k {
    /* access modifiers changed from: private */
    @NotNull
    public static final j.a a;
    public static final a b;
    private final Method c;
    private final Method d;
    private final Method e;
    private final Method f;
    private final Class<? super SSLSocket> g;

    public f(@NotNull Class<? super SSLSocket> sslSocketClass) {
        k.f(sslSocketClass, "sslSocketClass");
        this.g = sslSocketClass;
        Method declaredMethod = sslSocketClass.getDeclaredMethod("setUseSessionTickets", new Class[]{Boolean.TYPE});
        k.b(declaredMethod, "sslSocketClass.getDeclar…:class.javaPrimitiveType)");
        this.c = declaredMethod;
        this.d = sslSocketClass.getMethod("setHostname", new Class[]{String.class});
        this.e = sslSocketClass.getMethod("getAlpnSelectedProtocol", new Class[0]);
        this.f = sslSocketClass.getMethod("setAlpnProtocols", new Class[]{byte[].class});
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
        return b.e.b();
    }

    public boolean a(@NotNull SSLSocket sslSocket) {
        kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
        return this.g.isInstance(sslSocket);
    }

    public void e(@NotNull SSLSocket sslSocket, @Nullable String hostname, @NotNull List<? extends a0> protocols) {
        kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
        kotlin.jvm.internal.k.f(protocols, "protocols");
        if (a(sslSocket)) {
            try {
                this.c.invoke(sslSocket, new Object[]{true});
                if (hostname != null) {
                    this.d.invoke(sslSocket, new Object[]{hostname});
                }
                this.f.invoke(sslSocket, new Object[]{h.c.c(protocols)});
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            } catch (InvocationTargetException e3) {
                throw new AssertionError(e3);
            }
        }
    }

    @Nullable
    public String b(@NotNull SSLSocket sslSocket) {
        kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
        if (!a(sslSocket)) {
            return null;
        }
        try {
            byte[] alpnResult = (byte[]) this.e.invoke(sslSocket, new Object[0]);
            if (alpnResult == null) {
                return null;
            }
            Charset charset = StandardCharsets.UTF_8;
            kotlin.jvm.internal.k.b(charset, "StandardCharsets.UTF_8");
            return new String(alpnResult, charset);
        } catch (NullPointerException e2) {
            if (kotlin.jvm.internal.k.a(e2.getMessage(), "ssl == null")) {
                return null;
            }
            throw e2;
        } catch (IllegalAccessException e3) {
            throw new AssertionError(e3);
        } catch (InvocationTargetException e4) {
            throw new AssertionError(e4);
        }
    }

    /* compiled from: AndroidSocketAdapter.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final j.a d() {
            return f.a;
        }

        /* access modifiers changed from: private */
        public final f b(Class<? super SSLSocket> actualSSLSocketClass) {
            Class possibleClass = actualSSLSocketClass;
            while (possibleClass != null && (!kotlin.jvm.internal.k.a(possibleClass.getSimpleName(), "OpenSSLSocketImpl"))) {
                possibleClass = possibleClass.getSuperclass();
                if (possibleClass == null) {
                    throw new AssertionError("No OpenSSLSocketImpl superclass of socket of type " + actualSSLSocketClass);
                }
            }
            if (possibleClass == null) {
                kotlin.jvm.internal.k.n();
            }
            return new f(possibleClass);
        }

        /* renamed from: okhttp3.internal.platform.android.f$a$a  reason: collision with other inner class name */
        /* compiled from: AndroidSocketAdapter.kt */
        public static final class C0469a implements j.a {
            final /* synthetic */ String a;

            C0469a(String $captured_local_variable$0) {
                this.a = $captured_local_variable$0;
            }

            public boolean a(@NotNull SSLSocket sslSocket) {
                kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
                String name = sslSocket.getClass().getName();
                kotlin.jvm.internal.k.b(name, "sslSocket.javaClass.name");
                return w.N(name, this.a + '.', false, 2, (Object) null);
            }

            @NotNull
            public k b(@NotNull SSLSocket sslSocket) {
                kotlin.jvm.internal.k.f(sslSocket, "sslSocket");
                return f.b.b(sslSocket.getClass());
            }
        }

        @NotNull
        public final j.a c(@NotNull String packageName) {
            kotlin.jvm.internal.k.f(packageName, "packageName");
            return new C0469a(packageName);
        }
    }

    static {
        a aVar = new a((DefaultConstructorMarker) null);
        b = aVar;
        a = aVar.c("com.google.android.gms.org.conscrypt");
    }
}
