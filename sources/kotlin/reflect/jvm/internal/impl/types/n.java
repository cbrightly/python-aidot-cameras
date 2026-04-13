package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import org.jetbrains.annotations.NotNull;

/* compiled from: SpecialTypes.kt */
public abstract class n extends i0 {
    /* access modifiers changed from: protected */
    @NotNull
    public abstract i0 R0();

    @NotNull
    public abstract n T0(@NotNull i0 i0Var);

    @NotNull
    public g getAnnotations() {
        return R0().getAnnotations();
    }

    @NotNull
    public u0 I0() {
        return R0().I0();
    }

    @NotNull
    public List<w0> H0() {
        return R0().H0();
    }

    public boolean J0() {
        return R0().J0();
    }

    @NotNull
    public h l() {
        return R0().l();
    }

    @NotNull
    /* renamed from: S0 */
    public i0 N0(@NotNull i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        b0 g = kotlinTypeRefiner.g(R0());
        if (g != null) {
            return T0((i0) g);
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
    }
}
