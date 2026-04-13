package okhttp3;

import com.alibaba.fastjson.asm.Opcodes;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import meshsdk.util.LDSModel;
import org.jetbrains.annotations.NotNull;

/* compiled from: CipherSuite.kt */
public final class i {
    @NotNull
    public static final i A;
    @NotNull
    public static final i A0;
    @NotNull
    public static final i B;
    @NotNull
    public static final i B0;
    @NotNull
    public static final i C;
    @NotNull
    public static final i C0;
    @NotNull
    public static final i D;
    @NotNull
    public static final i D0;
    @NotNull
    public static final i E;
    @NotNull
    public static final i E0;
    @NotNull
    public static final i F;
    @NotNull
    public static final i F0;
    @NotNull
    public static final i G;
    @NotNull
    public static final i G0;
    @NotNull
    public static final i H;
    @NotNull
    public static final i H0;
    @NotNull
    public static final i I;
    @NotNull
    public static final i I0;
    @NotNull
    public static final i J;
    @NotNull
    public static final i J0;
    @NotNull
    public static final i K;
    @NotNull
    public static final i K0;
    @NotNull
    public static final i L;
    @NotNull
    public static final i L0;
    @NotNull
    public static final i M;
    @NotNull
    public static final i M0;
    @NotNull
    public static final i N;
    @NotNull
    public static final i N0;
    @NotNull
    public static final i O;
    @NotNull
    public static final i O0;
    @NotNull
    public static final i P;
    @NotNull
    public static final i P0;
    @NotNull
    public static final i Q;
    @NotNull
    public static final i Q0;
    @NotNull
    public static final i R;
    @NotNull
    public static final i R0;
    @NotNull
    public static final i S;
    @NotNull
    public static final i S0;
    @NotNull
    public static final i T;
    @NotNull
    public static final i T0;
    @NotNull
    public static final i U;
    @NotNull
    public static final i U0;
    @NotNull
    public static final i V;
    @NotNull
    public static final i V0;
    @NotNull
    public static final i W;
    @NotNull
    public static final i W0;
    @NotNull
    public static final i X;
    @NotNull
    public static final i X0;
    @NotNull
    public static final i Y;
    @NotNull
    public static final i Y0;
    @NotNull
    public static final i Z;
    @NotNull
    public static final i Z0;
    /* access modifiers changed from: private */
    @NotNull
    public static final Comparator<String> a = new a();
    @NotNull
    public static final i a0;
    @NotNull
    public static final i a1;
    /* access modifiers changed from: private */
    public static final Map<String, i> b = new LinkedHashMap();
    @NotNull
    public static final i b0;
    @NotNull
    public static final i b1;
    @NotNull
    public static final i c;
    @NotNull
    public static final i c0;
    @NotNull
    public static final i c1;
    @NotNull
    public static final i d;
    @NotNull
    public static final i d0;
    @NotNull
    public static final i d1;
    @NotNull
    public static final i e;
    @NotNull
    public static final i e0;
    @NotNull
    public static final i e1;
    @NotNull
    public static final i f;
    @NotNull
    public static final i f0;
    @NotNull
    public static final i f1;
    @NotNull
    public static final i g;
    @NotNull
    public static final i g0;
    @NotNull
    public static final i g1;
    @NotNull
    public static final i h;
    @NotNull
    public static final i h0;
    @NotNull
    public static final i h1;
    @NotNull
    public static final i i;
    @NotNull
    public static final i i0;
    @NotNull
    public static final i i1;
    @NotNull
    public static final i j;
    @NotNull
    public static final i j0;
    @NotNull
    public static final i j1;
    @NotNull
    public static final i k;
    @NotNull
    public static final i k0;
    @NotNull
    public static final i k1;
    @NotNull
    public static final i l;
    @NotNull
    public static final i l0;
    @NotNull
    public static final i l1;
    @NotNull
    public static final i m;
    @NotNull
    public static final i m0;
    @NotNull
    public static final i m1;
    @NotNull
    public static final i n;
    @NotNull
    public static final i n0;
    @NotNull
    public static final i n1;
    @NotNull
    public static final i o;
    @NotNull
    public static final i o0;
    @NotNull
    public static final i o1;
    @NotNull
    public static final i p;
    @NotNull
    public static final i p0;
    @NotNull
    public static final i p1;
    @NotNull
    public static final i q;
    @NotNull
    public static final i q0;
    @NotNull
    public static final i q1;
    @NotNull
    public static final i r;
    @NotNull
    public static final i r0;
    public static final b r1;
    @NotNull
    public static final i s;
    @NotNull
    public static final i s0;
    @NotNull
    public static final i t;
    @NotNull
    public static final i t0;
    @NotNull
    public static final i u;
    @NotNull
    public static final i u0;
    @NotNull
    public static final i v;
    @NotNull
    public static final i v0;
    @NotNull
    public static final i w;
    @NotNull
    public static final i w0;
    @NotNull
    public static final i x;
    @NotNull
    public static final i x0;
    @NotNull
    public static final i y;
    @NotNull
    public static final i y0;
    @NotNull
    public static final i z;
    @NotNull
    public static final i z0;
    @NotNull
    private final String s1;

    private i(String javaName) {
        this.s1 = javaName;
    }

    public /* synthetic */ i(String javaName, DefaultConstructorMarker $constructor_marker) {
        this(javaName);
    }

    @NotNull
    public final String c() {
        return this.s1;
    }

    @NotNull
    public String toString() {
        return this.s1;
    }

    /* compiled from: CipherSuite.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final Comparator<String> c() {
            return i.a;
        }

        @NotNull
        public final synchronized i b(@NotNull String javaName) {
            i result;
            k.f(javaName, "javaName");
            result = (i) i.b.get(javaName);
            if (result == null) {
                result = (i) i.b.get(e(javaName));
                if (result == null) {
                    result = new i(javaName, (DefaultConstructorMarker) null);
                }
                i.b.put(javaName, result);
            }
            return result;
        }

        private final String e(String javaName) {
            if (w.N(javaName, "TLS_", false, 2, (Object) null)) {
                StringBuilder sb = new StringBuilder();
                sb.append("SSL_");
                if (javaName != null) {
                    String substring = javaName.substring(4);
                    k.b(substring, "(this as java.lang.String).substring(startIndex)");
                    sb.append(substring);
                    return sb.toString();
                }
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else if (!w.N(javaName, "SSL_", false, 2, (Object) null)) {
                return javaName;
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("TLS_");
                if (javaName != null) {
                    String substring2 = javaName.substring(4);
                    k.b(substring2, "(this as java.lang.String).substring(startIndex)");
                    sb2.append(substring2);
                    return sb2.toString();
                }
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
        }

        /* access modifiers changed from: private */
        public final i d(String javaName, int value) {
            i suite = new i(javaName, (DefaultConstructorMarker) null);
            i.b.put(javaName, suite);
            return suite;
        }
    }

    /* compiled from: CipherSuite.kt */
    public static final class a implements Comparator<String> {
        a() {
        }

        /* renamed from: a */
        public int compare(@NotNull String a, @NotNull String b) {
            k.f(a, "a");
            k.f(b, "b");
            int i = 4;
            int limit = Math.min(a.length(), b.length());
            while (i < limit) {
                char charA = a.charAt(i);
                char charB = b.charAt(i);
                if (charA == charB) {
                    i++;
                } else if (charA < charB) {
                    return -1;
                } else {
                    return 1;
                }
            }
            int lengthA = a.length();
            int lengthB = b.length();
            if (lengthA == lengthB) {
                return 0;
            }
            if (lengthA < lengthB) {
                return -1;
            }
            return 1;
        }
    }

    static {
        b bVar = new b((DefaultConstructorMarker) null);
        r1 = bVar;
        c = bVar.d("SSL_RSA_WITH_NULL_MD5", 1);
        d = bVar.d("SSL_RSA_WITH_NULL_SHA", 2);
        e = bVar.d("SSL_RSA_EXPORT_WITH_RC4_40_MD5", 3);
        f = bVar.d("SSL_RSA_WITH_RC4_128_MD5", 4);
        g = bVar.d("SSL_RSA_WITH_RC4_128_SHA", 5);
        h = bVar.d("SSL_RSA_EXPORT_WITH_DES40_CBC_SHA", 8);
        i = bVar.d("SSL_RSA_WITH_DES_CBC_SHA", 9);
        j = bVar.d("SSL_RSA_WITH_3DES_EDE_CBC_SHA", 10);
        k = bVar.d("SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA", 17);
        l = bVar.d("SSL_DHE_DSS_WITH_DES_CBC_SHA", 18);
        m = bVar.d("SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA", 19);
        n = bVar.d("SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA", 20);
        o = bVar.d("SSL_DHE_RSA_WITH_DES_CBC_SHA", 21);
        p = bVar.d("SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA", 22);
        q = bVar.d("SSL_DH_anon_EXPORT_WITH_RC4_40_MD5", 23);
        r = bVar.d("SSL_DH_anon_WITH_RC4_128_MD5", 24);
        s = bVar.d("SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA", 25);
        t = bVar.d("SSL_DH_anon_WITH_DES_CBC_SHA", 26);
        u = bVar.d("SSL_DH_anon_WITH_3DES_EDE_CBC_SHA", 27);
        v = bVar.d("TLS_KRB5_WITH_DES_CBC_SHA", 30);
        w = bVar.d("TLS_KRB5_WITH_3DES_EDE_CBC_SHA", 31);
        x = bVar.d("TLS_KRB5_WITH_RC4_128_SHA", 32);
        y = bVar.d("TLS_KRB5_WITH_DES_CBC_MD5", 34);
        z = bVar.d("TLS_KRB5_WITH_3DES_EDE_CBC_MD5", 35);
        A = bVar.d("TLS_KRB5_WITH_RC4_128_MD5", 36);
        B = bVar.d("TLS_KRB5_EXPORT_WITH_DES_CBC_40_SHA", 38);
        C = bVar.d("TLS_KRB5_EXPORT_WITH_RC4_40_SHA", 40);
        D = bVar.d("TLS_KRB5_EXPORT_WITH_DES_CBC_40_MD5", 41);
        E = bVar.d("TLS_KRB5_EXPORT_WITH_RC4_40_MD5", 43);
        F = bVar.d("TLS_RSA_WITH_AES_128_CBC_SHA", 47);
        G = bVar.d("TLS_DHE_DSS_WITH_AES_128_CBC_SHA", 50);
        H = bVar.d("TLS_DHE_RSA_WITH_AES_128_CBC_SHA", 51);
        I = bVar.d("TLS_DH_anon_WITH_AES_128_CBC_SHA", 52);
        J = bVar.d("TLS_RSA_WITH_AES_256_CBC_SHA", 53);
        K = bVar.d("TLS_DHE_DSS_WITH_AES_256_CBC_SHA", 56);
        L = bVar.d("TLS_DHE_RSA_WITH_AES_256_CBC_SHA", 57);
        M = bVar.d("TLS_DH_anon_WITH_AES_256_CBC_SHA", 58);
        N = bVar.d("TLS_RSA_WITH_NULL_SHA256", 59);
        O = bVar.d("TLS_RSA_WITH_AES_128_CBC_SHA256", 60);
        P = bVar.d("TLS_RSA_WITH_AES_256_CBC_SHA256", 61);
        Q = bVar.d("TLS_DHE_DSS_WITH_AES_128_CBC_SHA256", 64);
        R = bVar.d("TLS_RSA_WITH_CAMELLIA_128_CBC_SHA", 65);
        S = bVar.d("TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA", 68);
        T = bVar.d("TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA", 69);
        U = bVar.d("TLS_DHE_RSA_WITH_AES_128_CBC_SHA256", 103);
        V = bVar.d("TLS_DHE_DSS_WITH_AES_256_CBC_SHA256", 106);
        W = bVar.d("TLS_DHE_RSA_WITH_AES_256_CBC_SHA256", 107);
        X = bVar.d("TLS_DH_anon_WITH_AES_128_CBC_SHA256", 108);
        b bVar2 = r1;
        Y = bVar2.d("TLS_DH_anon_WITH_AES_256_CBC_SHA256", 109);
        Z = bVar2.d("TLS_RSA_WITH_CAMELLIA_256_CBC_SHA", 132);
        a0 = bVar2.d("TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA", 135);
        b0 = bVar2.d("TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA", 136);
        c0 = bVar2.d("TLS_PSK_WITH_RC4_128_SHA", 138);
        d0 = bVar2.d("TLS_PSK_WITH_3DES_EDE_CBC_SHA", NeedPermissionEvent.PER_GET_LOCATION_BLE);
        e0 = bVar2.d("TLS_PSK_WITH_AES_128_CBC_SHA", NeedPermissionEvent.PER_ANDROID_S_BLE);
        f0 = bVar2.d("TLS_PSK_WITH_AES_256_CBC_SHA", NeedPermissionEvent.PER_ANDROID_NOTIFICATION);
        g0 = bVar2.d("TLS_RSA_WITH_SEED_CBC_SHA", 150);
        h0 = bVar2.d("TLS_RSA_WITH_AES_128_GCM_SHA256", 156);
        i0 = bVar2.d("TLS_RSA_WITH_AES_256_GCM_SHA384", 157);
        j0 = bVar2.d("TLS_DHE_RSA_WITH_AES_128_GCM_SHA256", Opcodes.IFLE);
        k0 = bVar2.d("TLS_DHE_RSA_WITH_AES_256_GCM_SHA384", Opcodes.IF_ICMPEQ);
        l0 = bVar2.d("TLS_DHE_DSS_WITH_AES_128_GCM_SHA256", Opcodes.IF_ICMPGE);
        m0 = bVar2.d("TLS_DHE_DSS_WITH_AES_256_GCM_SHA384", Opcodes.IF_ICMPGT);
        n0 = bVar2.d("TLS_DH_anon_WITH_AES_128_GCM_SHA256", 166);
        o0 = bVar2.d("TLS_DH_anon_WITH_AES_256_GCM_SHA384", Opcodes.GOTO);
        p0 = bVar2.d("TLS_EMPTY_RENEGOTIATION_INFO_SCSV", 255);
        q0 = bVar2.d("TLS_FALLBACK_SCSV", 22016);
        r0 = bVar2.d("TLS_ECDH_ECDSA_WITH_NULL_SHA", 49153);
        s0 = bVar2.d("TLS_ECDH_ECDSA_WITH_RC4_128_SHA", 49154);
        t0 = bVar2.d("TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA", 49155);
        u0 = bVar2.d("TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA", 49156);
        v0 = bVar2.d("TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA", 49157);
        w0 = bVar2.d("TLS_ECDHE_ECDSA_WITH_NULL_SHA", 49158);
        x0 = bVar2.d("TLS_ECDHE_ECDSA_WITH_RC4_128_SHA", 49159);
        y0 = bVar2.d("TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA", 49160);
        z0 = bVar2.d("TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA", 49161);
        A0 = bVar2.d("TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA", 49162);
        B0 = bVar2.d("TLS_ECDH_RSA_WITH_NULL_SHA", 49163);
        C0 = bVar2.d("TLS_ECDH_RSA_WITH_RC4_128_SHA", 49164);
        D0 = bVar2.d("TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA", 49165);
        E0 = bVar2.d("TLS_ECDH_RSA_WITH_AES_128_CBC_SHA", 49166);
        F0 = bVar2.d("TLS_ECDH_RSA_WITH_AES_256_CBC_SHA", 49167);
        G0 = bVar2.d("TLS_ECDHE_RSA_WITH_NULL_SHA", 49168);
        H0 = bVar2.d("TLS_ECDHE_RSA_WITH_RC4_128_SHA", 49169);
        I0 = bVar2.d("TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA", 49170);
        J0 = bVar2.d("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", 49171);
        K0 = bVar2.d("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", 49172);
        L0 = bVar2.d("TLS_ECDH_anon_WITH_NULL_SHA", 49173);
        M0 = bVar2.d("TLS_ECDH_anon_WITH_RC4_128_SHA", 49174);
        N0 = bVar2.d("TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA", 49175);
        O0 = bVar2.d("TLS_ECDH_anon_WITH_AES_128_CBC_SHA", 49176);
        P0 = bVar2.d("TLS_ECDH_anon_WITH_AES_256_CBC_SHA", 49177);
        Q0 = bVar2.d("TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256", 49187);
        R0 = bVar2.d("TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384", 49188);
        S0 = bVar2.d("TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256", 49189);
        T0 = bVar2.d("TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384", 49190);
        U0 = bVar2.d("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256", 49191);
        V0 = bVar2.d("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384", 49192);
        b bVar3 = r1;
        W0 = bVar3.d("TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256", 49193);
        X0 = bVar3.d("TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384", 49194);
        Y0 = bVar3.d("TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", 49195);
        Z0 = bVar3.d("TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", 49196);
        a1 = bVar3.d("TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256", 49197);
        b1 = bVar3.d("TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384", 49198);
        c1 = bVar3.d("TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", 49199);
        d1 = bVar3.d("TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", 49200);
        e1 = bVar3.d("TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256", 49201);
        f1 = bVar3.d("TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384", 49202);
        g1 = bVar3.d("TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA", 49205);
        h1 = bVar3.d("TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA", 49206);
        i1 = bVar3.d("TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256", 52392);
        j1 = bVar3.d("TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256", 52393);
        k1 = bVar3.d("TLS_DHE_RSA_WITH_CHACHA20_POLY1305_SHA256", 52394);
        l1 = bVar3.d("TLS_ECDHE_PSK_WITH_CHACHA20_POLY1305_SHA256", 52396);
        m1 = bVar3.d("TLS_AES_128_GCM_SHA256", 4865);
        n1 = bVar3.d("TLS_AES_256_GCM_SHA384", 4866);
        o1 = bVar3.d("TLS_CHACHA20_POLY1305_SHA256", LDSModel.MODEL_TEMP_CTRL);
        p1 = bVar3.d("TLS_AES_128_CCM_SHA256", 4868);
        q1 = bVar3.d("TLS_AES_128_CCM_8_SHA256", 4869);
    }
}
