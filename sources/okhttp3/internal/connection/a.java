package okhttp3.internal.connection;

import kotlin.jvm.internal.k;
import okhttp3.b0;
import okhttp3.d0;
import okhttp3.internal.http.g;
import okhttp3.w;
import org.jetbrains.annotations.NotNull;

/* compiled from: ConnectInterceptor.kt */
public final class a implements w {
    public static final a b = new a();

    private a() {
    }

    @NotNull
    public d0 intercept(@NotNull w.a chain) {
        k.f(chain, "chain");
        g realChain = (g) chain;
        return g.d(realChain, 0, realChain.e().r((g) chain), (b0) null, 0, 0, 0, 61, (Object) null).a(realChain.j());
    }
}
