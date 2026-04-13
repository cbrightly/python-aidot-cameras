package kotlinx.coroutines.internal;

import kotlin.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"kotlinx/coroutines/internal/SystemPropsKt__SystemPropsKt", "kotlinx/coroutines/internal/SystemPropsKt__SystemProps_commonKt"}, k = 4, mv = {1, 6, 0}, xi = 48)
public final class g0 {
    public static final int a() {
        return h0.a();
    }

    public static final int b(@NotNull String propertyName, int defaultValue, int minValue, int maxValue) {
        return i0.a(propertyName, defaultValue, minValue, maxValue);
    }

    public static final long c(@NotNull String propertyName, long defaultValue, long minValue, long maxValue) {
        return i0.b(propertyName, defaultValue, minValue, maxValue);
    }

    @Nullable
    public static final String d(@NotNull String propertyName) {
        return h0.b(propertyName);
    }

    public static final boolean e(@NotNull String propertyName, boolean defaultValue) {
        return i0.c(propertyName, defaultValue);
    }
}
