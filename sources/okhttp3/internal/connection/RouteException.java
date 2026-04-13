package okhttp3.internal.connection;

import java.io.IOException;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: RouteException.kt */
public final class RouteException extends RuntimeException {
    @NotNull
    private final IOException firstConnectException;
    @NotNull
    private IOException lastConnectException;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public RouteException(@NotNull IOException firstConnectException2) {
        super(firstConnectException2);
        k.f(firstConnectException2, "firstConnectException");
        this.firstConnectException = firstConnectException2;
        this.lastConnectException = firstConnectException2;
    }

    @NotNull
    public final IOException getFirstConnectException() {
        return this.firstConnectException;
    }

    @NotNull
    public final IOException getLastConnectException() {
        return this.lastConnectException;
    }

    public final void addConnectException(@NotNull IOException e) {
        k.f(e, "e");
        this.firstConnectException.addSuppressed(e);
        this.lastConnectException = e;
    }
}
