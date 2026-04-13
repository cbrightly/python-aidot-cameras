package okhttp3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Interceptor.kt */
public interface w {
    public static final b a = b.a;

    /* compiled from: Interceptor.kt */
    public interface a {
        @NotNull
        d0 a(@NotNull b0 b0Var);

        @Nullable
        j b();

        @NotNull
        e call();

        @NotNull
        b0 g();
    }

    @NotNull
    d0 intercept(@NotNull a aVar);

    /* compiled from: Interceptor.kt */
    public static final class b {
        static final /* synthetic */ b a = new b();

        private b() {
        }
    }
}
