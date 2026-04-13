package kotlin.reflect.jvm.internal.impl.types;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TypeWithEnhancement.kt */
public final class e1 {
    @Nullable
    public static final b0 a(@NotNull b0 $this$getEnhancement) {
        k.f($this$getEnhancement, "$this$getEnhancement");
        if ($this$getEnhancement instanceof d1) {
            return ((d1) $this$getEnhancement).a0();
        }
        return null;
    }

    @NotNull
    public static final b0 c(@NotNull b0 $this$unwrapEnhancement) {
        k.f($this$unwrapEnhancement, "$this$unwrapEnhancement");
        b0 a = a($this$unwrapEnhancement);
        return a != null ? a : $this$unwrapEnhancement;
    }

    @NotNull
    public static final g1 b(@NotNull g1 $this$inheritEnhancement, @NotNull b0 origin) {
        k.f($this$inheritEnhancement, "$this$inheritEnhancement");
        k.f(origin, "origin");
        return d($this$inheritEnhancement, a(origin));
    }

    @NotNull
    public static final g1 d(@NotNull g1 $this$wrapEnhancement, @Nullable b0 enhancement) {
        k.f($this$wrapEnhancement, "$this$wrapEnhancement");
        if (enhancement == null) {
            return $this$wrapEnhancement;
        }
        if ($this$wrapEnhancement instanceof i0) {
            return new k0((i0) $this$wrapEnhancement, enhancement);
        }
        if ($this$wrapEnhancement instanceof v) {
            return new x((v) $this$wrapEnhancement, enhancement);
        }
        throw new NoWhenBranchMatchedException();
    }
}
