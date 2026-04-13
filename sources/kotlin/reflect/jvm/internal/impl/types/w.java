package kotlin.reflect.jvm.internal.impl.types;

import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.renderer.c;
import kotlin.reflect.jvm.internal.impl.renderer.i;
import kotlin.reflect.jvm.internal.impl.types.checker.g;
import org.jetbrains.annotations.NotNull;

/* compiled from: flexibleTypes.kt */
public final class w extends v implements k {
    public static boolean q;
    public static final a x = new a((DefaultConstructorMarker) null);
    private boolean y;

    /* compiled from: flexibleTypes.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public w(@NotNull i0 lowerBound, @NotNull i0 upperBound) {
        super(lowerBound, upperBound);
        k.f(lowerBound, "lowerBound");
        k.f(upperBound, "upperBound");
    }

    private final void U0() {
        if (q && !this.y) {
            this.y = true;
            if (!(!y.b(Q0()))) {
                throw new AssertionError("Lower bound of a flexible type can not be flexible: " + Q0());
            } else if (!(!y.b(R0()))) {
                throw new AssertionError("Upper bound of a flexible type can not be flexible: " + R0());
            } else if (!(true ^ k.a(Q0(), R0()))) {
                throw new AssertionError("Lower and upper bounds are equal: " + Q0() + " == " + R0());
            } else if (!g.a.d(Q0(), R0())) {
                throw new AssertionError("Lower bound " + Q0() + " of a flexible type must be a subtype of the upper bound " + R0());
            }
        }
    }

    @NotNull
    public i0 P0() {
        U0();
        return Q0();
    }

    public boolean u() {
        return (Q0().I0().c() instanceof t0) && k.a(Q0().I0(), R0().I0());
    }

    @NotNull
    public b0 c0(@NotNull b0 replacement) {
        g1 g1Var;
        k.f(replacement, "replacement");
        g1 unwrapped = replacement.L0();
        if (unwrapped instanceof v) {
            g1Var = unwrapped;
        } else if (unwrapped instanceof i0) {
            g1Var = c0.d((i0) unwrapped, ((i0) unwrapped).P0(true));
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return e1.b(g1Var, unwrapped);
    }

    @NotNull
    public g1 O0(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.g newAnnotations) {
        k.f(newAnnotations, "newAnnotations");
        return c0.d(Q0().Q0(newAnnotations), R0().Q0(newAnnotations));
    }

    @NotNull
    public String S0(@NotNull c renderer, @NotNull i options) {
        k.f(renderer, "renderer");
        k.f(options, "options");
        if (!options.j()) {
            return renderer.u(renderer.x(Q0()), renderer.x(R0()), kotlin.reflect.jvm.internal.impl.types.typeUtil.a.f(this));
        }
        return '(' + renderer.x(Q0()) + ".." + renderer.x(R0()) + ')';
    }

    @NotNull
    public g1 M0(boolean newNullability) {
        return c0.d(Q0().P0(newNullability), R0().P0(newNullability));
    }

    @NotNull
    /* renamed from: T0 */
    public v N0(@NotNull kotlin.reflect.jvm.internal.impl.types.checker.i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        b0 g = kotlinTypeRefiner.g(Q0());
        if (g != null) {
            i0 i0Var = (i0) g;
            b0 g2 = kotlinTypeRefiner.g(R0());
            if (g2 != null) {
                return new w(i0Var, (i0) g2);
            }
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
    }
}
