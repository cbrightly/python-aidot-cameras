package okhttp3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.net.ssl.SSLSocket;
import kotlin.TypeCastException;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ConnectionSpec.kt */
public final class l {
    private static final i[] a;
    private static final i[] b;
    @NotNull
    public static final l c;
    @NotNull
    public static final l d;
    @NotNull
    public static final l e;
    @NotNull
    public static final l f = new a(false).a();
    public static final b g = new b((DefaultConstructorMarker) null);
    private final boolean h;
    private final boolean i;
    /* access modifiers changed from: private */
    public final String[] j;
    /* access modifiers changed from: private */
    public final String[] k;

    public l(boolean isTls, boolean supportsTlsExtensions, @Nullable String[] cipherSuitesAsString, @Nullable String[] tlsVersionsAsString) {
        this.h = isTls;
        this.i = supportsTlsExtensions;
        this.j = cipherSuitesAsString;
        this.k = tlsVersionsAsString;
    }

    public final boolean f() {
        return this.h;
    }

    public final boolean h() {
        return this.i;
    }

    @Nullable
    public final List<i> d() {
        String[] strArr = this.j;
        if (strArr == null) {
            return null;
        }
        Collection destination$iv$iv = new ArrayList(strArr.length);
        for (String it : strArr) {
            destination$iv$iv.add(i.r1.b(it));
        }
        return y.C0(destination$iv$iv);
    }

    @Nullable
    public final List<g0> i() {
        String[] strArr = this.k;
        if (strArr == null) {
            return null;
        }
        Collection destination$iv$iv = new ArrayList(strArr.length);
        for (String it : strArr) {
            destination$iv$iv.add(g0.Companion.a(it));
        }
        return y.C0(destination$iv$iv);
    }

    public final void c(@NotNull SSLSocket sslSocket, boolean isFallback) {
        k.f(sslSocket, "sslSocket");
        l specToApply = g(sslSocket, isFallback);
        if (specToApply.i() != null) {
            sslSocket.setEnabledProtocols(specToApply.k);
        }
        if (specToApply.d() != null) {
            sslSocket.setEnabledCipherSuites(specToApply.j);
        }
    }

    private final l g(SSLSocket sslSocket, boolean isFallback) {
        String[] cipherSuitesIntersection;
        String[] tlsVersionsIntersection;
        if (this.j != null) {
            String[] enabledCipherSuites = sslSocket.getEnabledCipherSuites();
            k.b(enabledCipherSuites, "sslSocket.enabledCipherSuites");
            cipherSuitesIntersection = okhttp3.internal.b.B(enabledCipherSuites, this.j, i.r1.c());
        } else {
            cipherSuitesIntersection = sslSocket.getEnabledCipherSuites();
        }
        if (this.k != null) {
            String[] enabledProtocols = sslSocket.getEnabledProtocols();
            k.b(enabledProtocols, "sslSocket.enabledProtocols");
            tlsVersionsIntersection = okhttp3.internal.b.B(enabledProtocols, this.k, kotlin.comparisons.a.e());
        } else {
            tlsVersionsIntersection = sslSocket.getEnabledProtocols();
        }
        String[] supportedCipherSuites = sslSocket.getSupportedCipherSuites();
        k.b(supportedCipherSuites, "supportedCipherSuites");
        int indexOfFallbackScsv = okhttp3.internal.b.u(supportedCipherSuites, "TLS_FALLBACK_SCSV", i.r1.c());
        if (isFallback && indexOfFallbackScsv != -1) {
            k.b(cipherSuitesIntersection, "cipherSuitesIntersection");
            String str = supportedCipherSuites[indexOfFallbackScsv];
            k.b(str, "supportedCipherSuites[indexOfFallbackScsv]");
            cipherSuitesIntersection = okhttp3.internal.b.l(cipherSuitesIntersection, str);
        }
        a aVar = new a(this);
        k.b(cipherSuitesIntersection, "cipherSuitesIntersection");
        a b2 = aVar.b((String[]) Arrays.copyOf(cipherSuitesIntersection, cipherSuitesIntersection.length));
        k.b(tlsVersionsIntersection, "tlsVersionsIntersection");
        return b2.e((String[]) Arrays.copyOf(tlsVersionsIntersection, tlsVersionsIntersection.length)).a();
    }

    public final boolean e(@NotNull SSLSocket socket) {
        k.f(socket, "socket");
        if (!this.h) {
            return false;
        }
        String[] strArr = this.k;
        if (strArr != null && !okhttp3.internal.b.r(strArr, socket.getEnabledProtocols(), kotlin.comparisons.a.e())) {
            return false;
        }
        String[] strArr2 = this.j;
        if (strArr2 == null || okhttp3.internal.b.r(strArr2, socket.getEnabledCipherSuites(), i.r1.c())) {
            return true;
        }
        return false;
    }

    public boolean equals(@Nullable Object other) {
        if (!(other instanceof l)) {
            return false;
        }
        if (other == this) {
            return true;
        }
        boolean z = this.h;
        if (z != ((l) other).h) {
            return false;
        }
        if (!z || (Arrays.equals(this.j, ((l) other).j) && Arrays.equals(this.k, ((l) other).k) && this.i == ((l) other).i)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (!this.h) {
            return 17;
        }
        int i2 = 17 * 31;
        String[] strArr = this.j;
        int i3 = 0;
        int result = (i2 + (strArr != null ? Arrays.hashCode(strArr) : 0)) * 31;
        String[] strArr2 = this.k;
        if (strArr2 != null) {
            i3 = Arrays.hashCode(strArr2);
        }
        return ((result + i3) * 31) + (this.i ^ true ? 1 : 0);
    }

    @NotNull
    public String toString() {
        if (!this.h) {
            return "ConnectionSpec()";
        }
        return "ConnectionSpec(" + "cipherSuites=" + Objects.toString(d(), "[all enabled]") + ", " + "tlsVersions=" + Objects.toString(i(), "[all enabled]") + ", " + "supportsTlsExtensions=" + this.i + ')';
    }

    /* compiled from: ConnectionSpec.kt */
    public static final class a {
        private boolean a;
        @Nullable
        private String[] b;
        @Nullable
        private String[] c;
        private boolean d;

        public a(boolean tls) {
            this.a = tls;
        }

        public a(@NotNull l connectionSpec) {
            k.f(connectionSpec, "connectionSpec");
            this.a = connectionSpec.f();
            this.b = connectionSpec.j;
            this.c = connectionSpec.k;
            this.d = connectionSpec.h();
        }

        @NotNull
        public final a c(@NotNull i... cipherSuites) {
            k.f(cipherSuites, "cipherSuites");
            if (this.a) {
                i[] iVarArr = cipherSuites;
                ArrayList arrayList = new ArrayList(iVarArr.length);
                for (i it : iVarArr) {
                    arrayList.add(it.c());
                }
                ArrayList arrayList2 = arrayList;
                Object[] array = arrayList.toArray(new String[0]);
                if (array != null) {
                    String[] strings = (String[]) array;
                    return b((String[]) Arrays.copyOf(strings, strings.length));
                }
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            throw new IllegalArgumentException("no cipher suites for cleartext connections".toString());
        }

        @NotNull
        public final a b(@NotNull String... cipherSuites) {
            k.f(cipherSuites, "cipherSuites");
            if (this.a) {
                if (!(cipherSuites.length == 0)) {
                    Object clone = cipherSuites.clone();
                    if (clone != null) {
                        this.b = (String[]) clone;
                        return this;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
                }
                throw new IllegalArgumentException("At least one cipher suite is required".toString());
            }
            throw new IllegalArgumentException("no cipher suites for cleartext connections".toString());
        }

        @NotNull
        public final a f(@NotNull g0... tlsVersions) {
            k.f(tlsVersions, "tlsVersions");
            if (this.a) {
                g0[] g0VarArr = tlsVersions;
                ArrayList arrayList = new ArrayList(g0VarArr.length);
                for (g0 it : g0VarArr) {
                    arrayList.add(it.javaName());
                }
                ArrayList arrayList2 = arrayList;
                Object[] array = arrayList.toArray(new String[0]);
                if (array != null) {
                    String[] strings = (String[]) array;
                    return e((String[]) Arrays.copyOf(strings, strings.length));
                }
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            throw new IllegalArgumentException("no TLS versions for cleartext connections".toString());
        }

        @NotNull
        public final a e(@NotNull String... tlsVersions) {
            k.f(tlsVersions, "tlsVersions");
            if (this.a) {
                if (!(tlsVersions.length == 0)) {
                    Object clone = tlsVersions.clone();
                    if (clone != null) {
                        this.c = (String[]) clone;
                        return this;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
                }
                throw new IllegalArgumentException("At least one TLS version is required".toString());
            }
            throw new IllegalArgumentException("no TLS versions for cleartext connections".toString());
        }

        @NotNull
        public final a d(boolean supportsTlsExtensions) {
            if (this.a) {
                this.d = supportsTlsExtensions;
                return this;
            }
            throw new IllegalArgumentException("no TLS extensions for cleartext connections".toString());
        }

        @NotNull
        public final l a() {
            return new l(this.a, this.d, this.b, this.c);
        }
    }

    /* compiled from: ConnectionSpec.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    static {
        i iVar = i.m1;
        i iVar2 = i.n1;
        i iVar3 = i.o1;
        i iVar4 = i.Y0;
        i iVar5 = i.c1;
        i iVar6 = i.Z0;
        i iVar7 = i.d1;
        i iVar8 = i.j1;
        i iVar9 = i.i1;
        i[] iVarArr = {iVar, iVar2, iVar3, iVar4, iVar5, iVar6, iVar7, iVar8, iVar9};
        a = iVarArr;
        i[] iVarArr2 = {iVar, iVar2, iVar3, iVar4, iVar5, iVar6, iVar7, iVar8, iVar9, i.J0, i.K0, i.h0, i.i0, i.F, i.J, i.j};
        b = iVarArr2;
        a c2 = new a(true).c((i[]) Arrays.copyOf(iVarArr, iVarArr.length));
        g0 g0Var = g0.TLS_1_3;
        g0 g0Var2 = g0.TLS_1_2;
        c = c2.f(g0Var, g0Var2).d(true).a();
        d = new a(true).c((i[]) Arrays.copyOf(iVarArr2, iVarArr2.length)).f(g0Var, g0Var2).d(true).a();
        e = new a(true).c((i[]) Arrays.copyOf(iVarArr2, iVarArr2.length)).f(g0Var, g0Var2, g0.TLS_1_1, g0.TLS_1_0).d(true).a();
    }
}
