package okhttp3;

import java.util.concurrent.TimeUnit;
import okhttp3.internal.concurrent.e;
import okhttp3.internal.connection.h;
import org.jetbrains.annotations.NotNull;

/* compiled from: ConnectionPool.kt */
public final class k {
    @NotNull
    private final h a;

    public k(@NotNull h delegate) {
        kotlin.jvm.internal.k.f(delegate, "delegate");
        this.a = delegate;
    }

    @NotNull
    public final h a() {
        return this.a;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public k(int maxIdleConnections, long keepAliveDuration, @NotNull TimeUnit timeUnit) {
        this(new h(e.a, maxIdleConnections, keepAliveDuration, timeUnit));
        kotlin.jvm.internal.k.f(timeUnit, "timeUnit");
    }

    public k() {
        this(5, 5, TimeUnit.MINUTES);
    }
}
