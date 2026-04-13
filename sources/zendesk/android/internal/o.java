package zendesk.android.internal;

import kotlin.jvm.internal.k;
import okhttp3.d0;
import okhttp3.w;
import org.jetbrains.annotations.NotNull;

/* compiled from: ZendeskLoggingInterceptor.kt */
public final class o implements w {
    @NotNull
    public d0 intercept(@NotNull w.a chain) {
        k.e(chain, "chain");
        return chain.a(chain.g());
    }
}
