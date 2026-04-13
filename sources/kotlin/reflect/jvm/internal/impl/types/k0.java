package kotlin.reflect.jvm.internal.impl.types;

import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import org.jetbrains.annotations.NotNull;

/* compiled from: TypeWithEnhancement.kt */
public final class k0 extends n implements d1 {
    @NotNull
    private final i0 d;
    @NotNull
    private final b0 f;

    public k0(@NotNull i0 delegate, @NotNull b0 enhancement) {
        k.f(delegate, "delegate");
        k.f(enhancement, "enhancement");
        this.d = delegate;
        this.f = enhancement;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public i0 R0() {
        return this.d;
    }

    @NotNull
    public b0 a0() {
        return this.f;
    }

    @NotNull
    public g1 A0() {
        return R0();
    }

    @NotNull
    /* renamed from: Q0 */
    public i0 O0(@NotNull g newAnnotations) {
        k.f(newAnnotations, "newAnnotations");
        g1 d2 = e1.d(A0().O0(newAnnotations), a0());
        if (d2 != null) {
            return (i0) d2;
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
    }

    @NotNull
    /* renamed from: P0 */
    public i0 M0(boolean newNullability) {
        g1 d2 = e1.d(A0().M0(newNullability), a0().L0().M0(newNullability));
        if (d2 != null) {
            return (i0) d2;
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
    }

    @NotNull
    /* renamed from: V0 */
    public k0 T0(@NotNull i0 delegate) {
        k.f(delegate, "delegate");
        return new k0(delegate, a0());
    }

    @NotNull
    /* renamed from: U0 */
    public k0 S0(@NotNull i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        b0 g = kotlinTypeRefiner.g(R0());
        if (g != null) {
            return new k0((i0) g, kotlinTypeRefiner.g(a0()));
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
    }
}
