package kotlin.reflect.jvm.internal.impl.types;

import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import org.jetbrains.annotations.NotNull;

/* compiled from: SpecialTypes.kt */
public final class a extends n {
    @NotNull
    private final i0 d;
    @NotNull
    private final i0 f;

    public a(@NotNull i0 delegate, @NotNull i0 abbreviation) {
        k.f(delegate, "delegate");
        k.f(abbreviation, "abbreviation");
        this.d = delegate;
        this.f = abbreviation;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public i0 R0() {
        return this.d;
    }

    @NotNull
    public final i0 U0() {
        return this.f;
    }

    @NotNull
    public final i0 E() {
        return R0();
    }

    @NotNull
    /* renamed from: X0 */
    public a Q0(@NotNull g newAnnotations) {
        k.f(newAnnotations, "newAnnotations");
        return new a(R0().Q0(newAnnotations), this.f);
    }

    @NotNull
    /* renamed from: V0 */
    public a P0(boolean newNullability) {
        return new a(R0().P0(newNullability), this.f.P0(newNullability));
    }

    @NotNull
    /* renamed from: Y0 */
    public a T0(@NotNull i0 delegate) {
        k.f(delegate, "delegate");
        return new a(delegate, this.f);
    }

    @NotNull
    /* renamed from: W0 */
    public a S0(@NotNull i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        b0 g = kotlinTypeRefiner.g(R0());
        if (g != null) {
            i0 i0Var = (i0) g;
            b0 g2 = kotlinTypeRefiner.g(this.f);
            if (g2 != null) {
                return new a(i0Var, (i0) g2);
            }
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
    }
}
