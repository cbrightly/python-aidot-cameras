package okhttp3.internal.http;

import okhttp3.d0;
import okhttp3.internal.connection.f;
import okio.b0;
import okio.e0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ExchangeCodec.kt */
public interface d {
    public static final a a = a.a;

    void a();

    @NotNull
    e0 b(@NotNull d0 d0Var);

    long c(@NotNull d0 d0Var);

    void cancel();

    @NotNull
    b0 d(@NotNull okhttp3.b0 b0Var, long j);

    void e(@NotNull okhttp3.b0 b0Var);

    @Nullable
    d0.a f(boolean z);

    void g();

    @NotNull
    f getConnection();

    /* compiled from: ExchangeCodec.kt */
    public static final class a {
        static final /* synthetic */ a a = new a();

        private a() {
        }
    }
}
