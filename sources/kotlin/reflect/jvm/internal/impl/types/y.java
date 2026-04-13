package kotlin.reflect.jvm.internal.impl.types;

import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: flexibleTypes.kt */
public final class y {
    public static final boolean b(@NotNull b0 $this$isFlexible) {
        k.f($this$isFlexible, "$this$isFlexible");
        return $this$isFlexible.L0() instanceof v;
    }

    @NotNull
    public static final v a(@NotNull b0 $this$asFlexibleType) {
        k.f($this$asFlexibleType, "$this$asFlexibleType");
        g1 L0 = $this$asFlexibleType.L0();
        if (L0 != null) {
            return (v) L0;
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.FlexibleType");
    }

    @NotNull
    public static final i0 c(@NotNull b0 $this$lowerIfFlexible) {
        k.f($this$lowerIfFlexible, "$this$lowerIfFlexible");
        g1 $this$with = $this$lowerIfFlexible.L0();
        if ($this$with instanceof v) {
            return ((v) $this$with).Q0();
        }
        if ($this$with instanceof i0) {
            return (i0) $this$with;
        }
        throw new NoWhenBranchMatchedException();
    }

    @NotNull
    public static final i0 d(@NotNull b0 $this$upperIfFlexible) {
        k.f($this$upperIfFlexible, "$this$upperIfFlexible");
        g1 $this$with = $this$upperIfFlexible.L0();
        if ($this$with instanceof v) {
            return ((v) $this$with).R0();
        }
        if ($this$with instanceof i0) {
            return (i0) $this$with;
        }
        throw new NoWhenBranchMatchedException();
    }
}
