package okhttp3.internal.tls;

import java.security.cert.Certificate;
import java.util.List;
import javax.net.ssl.X509TrustManager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.internal.platform.h;
import org.jetbrains.annotations.NotNull;

/* compiled from: CertificateChainCleaner.kt */
public abstract class c {
    public static final a a = new a((DefaultConstructorMarker) null);

    @NotNull
    public abstract List<Certificate> a(@NotNull List<? extends Certificate> list, @NotNull String str);

    /* compiled from: CertificateChainCleaner.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final c a(@NotNull X509TrustManager trustManager) {
            k.f(trustManager, "trustManager");
            return h.c.g().c(trustManager);
        }
    }
}
