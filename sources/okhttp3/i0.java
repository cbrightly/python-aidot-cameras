package okhttp3;

import kotlin.jvm.internal.k;
import okio.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: WebSocketListener.kt */
public abstract class i0 {
    public abstract void a(@NotNull h0 h0Var, int i, @NotNull String str);

    public abstract void c(@NotNull h0 h0Var, @NotNull Throwable th, @Nullable d0 d0Var);

    public abstract void d(@NotNull h0 h0Var, @NotNull String str);

    public abstract void f(@NotNull h0 h0Var, @NotNull d0 d0Var);

    public void e(@NotNull h0 webSocket, @NotNull f bytes) {
        k.f(webSocket, "webSocket");
        k.f(bytes, "bytes");
    }

    public void b(@NotNull h0 webSocket, int code, @NotNull String reason) {
        k.f(webSocket, "webSocket");
        k.f(reason, "reason");
    }
}
