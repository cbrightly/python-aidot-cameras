package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.types.model.f;
import kotlin.reflect.jvm.internal.impl.types.model.g;
import kotlin.reflect.jvm.internal.impl.types.model.h;
import kotlin.reflect.jvm.internal.impl.types.model.j;
import kotlin.reflect.jvm.internal.impl.types.model.m;
import org.jetbrains.annotations.NotNull;

/* compiled from: AbstractStrictEqualityTypeChecker.kt */
public final class d {
    public static final d a = new d();

    private d() {
    }

    public final boolean b(@NotNull m context, @NotNull g a2, @NotNull g b) {
        k.f(context, "context");
        k.f(a2, "a");
        k.f(b, "b");
        return c(context, a2, b);
    }

    private final boolean c(@NotNull m $this$strictEqualTypesInternal, g a2, g b) {
        if (a2 == b) {
            return true;
        }
        h simpleA = $this$strictEqualTypesInternal.a(a2);
        h simpleB = $this$strictEqualTypesInternal.a(b);
        if (simpleA != null && simpleB != null) {
            return a($this$strictEqualTypesInternal, simpleA, simpleB);
        }
        f flexibleA = $this$strictEqualTypesInternal.K(a2);
        f flexibleB = $this$strictEqualTypesInternal.K(b);
        if (flexibleA == null || flexibleB == null) {
            return false;
        }
        if (!a($this$strictEqualTypesInternal, $this$strictEqualTypesInternal.y(flexibleA), $this$strictEqualTypesInternal.y(flexibleB)) || !a($this$strictEqualTypesInternal, $this$strictEqualTypesInternal.R(flexibleA), $this$strictEqualTypesInternal.R(flexibleB))) {
            return false;
        }
        return true;
    }

    private final boolean a(@NotNull m $this$strictEqualSimpleTypes, h a2, h b) {
        if ($this$strictEqualSimpleTypes.d(a2) == $this$strictEqualSimpleTypes.d(b) && $this$strictEqualSimpleTypes.n(a2) == $this$strictEqualSimpleTypes.n(b)) {
            if (($this$strictEqualSimpleTypes.Y(a2) == null) == ($this$strictEqualSimpleTypes.Y(b) == null) && $this$strictEqualSimpleTypes.A($this$strictEqualSimpleTypes.b(a2), $this$strictEqualSimpleTypes.b(b))) {
                if ($this$strictEqualSimpleTypes.x(a2, b)) {
                    return true;
                }
                int d = $this$strictEqualSimpleTypes.d(a2);
                for (int i = 0; i < d; i++) {
                    j aArg = $this$strictEqualSimpleTypes.r(a2, i);
                    j bArg = $this$strictEqualSimpleTypes.r(b, i);
                    if ($this$strictEqualSimpleTypes.i(aArg) != $this$strictEqualSimpleTypes.i(bArg)) {
                        return false;
                    }
                    if (!$this$strictEqualSimpleTypes.i(aArg) && ($this$strictEqualSimpleTypes.U(aArg) != $this$strictEqualSimpleTypes.U(bArg) || !c($this$strictEqualSimpleTypes, $this$strictEqualSimpleTypes.a0(aArg), $this$strictEqualSimpleTypes.a0(bArg)))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
