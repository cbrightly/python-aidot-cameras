package okhttp3;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: TlsVersion.kt */
public enum g0 {
    TLS_1_3("TLSv1.3"),
    TLS_1_2("TLSv1.2"),
    TLS_1_1("TLSv1.1"),
    TLS_1_0("TLSv1"),
    SSL_3_0("SSLv3");
    
    public static final a Companion = null;
    @NotNull
    private final String javaName;

    @NotNull
    public static final g0 forJavaName(@NotNull String str) {
        return Companion.a(str);
    }

    private g0(String javaName2) {
        this.javaName = javaName2;
    }

    @NotNull
    public final String javaName() {
        return this.javaName;
    }

    static {
        Companion = new a((DefaultConstructorMarker) null);
    }

    @NotNull
    /* renamed from: -deprecated_javaName  reason: not valid java name */
    public final String m26deprecated_javaName() {
        return this.javaName;
    }

    /* compiled from: TlsVersion.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final g0 a(@NotNull String javaName) {
            k.f(javaName, "javaName");
            switch (javaName.hashCode()) {
                case -503070503:
                    if (javaName.equals("TLSv1.1")) {
                        return g0.TLS_1_1;
                    }
                    break;
                case -503070502:
                    if (javaName.equals("TLSv1.2")) {
                        return g0.TLS_1_2;
                    }
                    break;
                case -503070501:
                    if (javaName.equals("TLSv1.3")) {
                        return g0.TLS_1_3;
                    }
                    break;
                case 79201641:
                    if (javaName.equals("SSLv3")) {
                        return g0.SSL_3_0;
                    }
                    break;
                case 79923350:
                    if (javaName.equals("TLSv1")) {
                        return g0.TLS_1_0;
                    }
                    break;
            }
            throw new IllegalArgumentException("Unexpected TLS version: " + javaName);
        }
    }
}
