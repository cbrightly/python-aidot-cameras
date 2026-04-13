package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import org.jetbrains.annotations.NotNull;

/* compiled from: TypeSubstitution.kt */
public final class a1 {
    public static /* synthetic */ b0 d(b0 b0Var, List<w0> list, g gVar, int i, Object obj) {
        if ((i & 1) != 0) {
            list = b0Var.H0();
        }
        if ((i & 2) != 0) {
            gVar = b0Var.getAnnotations();
        }
        return b(b0Var, list, gVar);
    }

    @NotNull
    public static final b0 b(@NotNull b0 $this$replace, @NotNull List<? extends w0> newArguments, @NotNull g newAnnotations) {
        k.f($this$replace, "$this$replace");
        k.f(newArguments, "newArguments");
        k.f(newAnnotations, "newAnnotations");
        if ((newArguments.isEmpty() || newArguments == $this$replace.H0()) && newAnnotations == $this$replace.getAnnotations()) {
            return $this$replace;
        }
        g1 unwrapped = $this$replace.L0();
        if (unwrapped instanceof v) {
            return c0.d(c(((v) unwrapped).Q0(), newArguments, newAnnotations), c(((v) unwrapped).R0(), newArguments, newAnnotations));
        }
        if (unwrapped instanceof i0) {
            return c((i0) unwrapped, newArguments, newAnnotations);
        }
        throw new NoWhenBranchMatchedException();
    }

    public static /* synthetic */ i0 e(i0 i0Var, List<w0> list, g gVar, int i, Object obj) {
        if ((i & 1) != 0) {
            list = i0Var.H0();
        }
        if ((i & 2) != 0) {
            gVar = i0Var.getAnnotations();
        }
        return c(i0Var, list, gVar);
    }

    @NotNull
    public static final i0 c(@NotNull i0 $this$replace, @NotNull List<? extends w0> newArguments, @NotNull g newAnnotations) {
        k.f($this$replace, "$this$replace");
        k.f(newArguments, "newArguments");
        k.f(newAnnotations, "newAnnotations");
        if (newArguments.isEmpty() && newAnnotations == $this$replace.getAnnotations()) {
            return $this$replace;
        }
        if (newArguments.isEmpty()) {
            return $this$replace.Q0(newAnnotations);
        }
        return c0.i(newAnnotations, $this$replace.I0(), newArguments, $this$replace.J0(), (i) null, 16, (Object) null);
    }

    @NotNull
    public static final i0 a(@NotNull b0 $this$asSimpleType) {
        k.f($this$asSimpleType, "$this$asSimpleType");
        g1 L0 = $this$asSimpleType.L0();
        if (!(L0 instanceof i0)) {
            L0 = null;
        }
        i0 i0Var = (i0) L0;
        if (i0Var != null) {
            return i0Var;
        }
        throw new IllegalStateException(("This is should be simple type: " + $this$asSimpleType).toString());
    }
}
