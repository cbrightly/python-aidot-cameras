package kotlin.reflect.jvm.internal.impl.types;

import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.renderer.c;
import kotlin.reflect.jvm.internal.impl.renderer.i;
import org.jetbrains.annotations.NotNull;

/* compiled from: TypeWithEnhancement.kt */
public final class x extends v implements d1 {
    @NotNull
    private final v q;
    @NotNull
    private final b0 x;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public x(@NotNull v origin, @NotNull b0 enhancement) {
        super(origin.Q0(), origin.R0());
        k.f(origin, "origin");
        k.f(enhancement, "enhancement");
        this.q = origin;
        this.x = enhancement;
    }

    @NotNull
    /* renamed from: T0 */
    public v A0() {
        return this.q;
    }

    @NotNull
    public b0 a0() {
        return this.x;
    }

    @NotNull
    public g1 O0(@NotNull g newAnnotations) {
        k.f(newAnnotations, "newAnnotations");
        return e1.d(A0().O0(newAnnotations), a0());
    }

    @NotNull
    public g1 M0(boolean newNullability) {
        return e1.d(A0().M0(newNullability), a0().L0().M0(newNullability));
    }

    @NotNull
    public String S0(@NotNull c renderer, @NotNull i options) {
        k.f(renderer, "renderer");
        k.f(options, "options");
        if (options.d()) {
            return renderer.x(a0());
        }
        return A0().S0(renderer, options);
    }

    @NotNull
    public i0 P0() {
        return A0().P0();
    }

    @NotNull
    /* renamed from: U0 */
    public x N0(@NotNull kotlin.reflect.jvm.internal.impl.types.checker.i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        b0 g = kotlinTypeRefiner.g(A0());
        if (g != null) {
            return new x((v) g, kotlinTypeRefiner.g(a0()));
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.FlexibleType");
    }
}
