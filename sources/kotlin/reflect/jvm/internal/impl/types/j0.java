package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.u;
import org.jetbrains.annotations.NotNull;

/* compiled from: KotlinTypeFactory.kt */
public final class j0 extends i0 {
    @NotNull
    private final u0 d;
    @NotNull
    private final List<w0> f;
    private final boolean q;
    @NotNull
    private final h x;
    private final l<i, i0> y;

    public j0(@NotNull u0 constructor, @NotNull List<? extends w0> arguments, boolean isMarkedNullable, @NotNull h memberScope, @NotNull l<? super i, ? extends i0> refinedTypeFactory) {
        k.f(constructor, "constructor");
        k.f(arguments, "arguments");
        k.f(memberScope, "memberScope");
        k.f(refinedTypeFactory, "refinedTypeFactory");
        this.d = constructor;
        this.f = arguments;
        this.q = isMarkedNullable;
        this.x = memberScope;
        this.y = refinedTypeFactory;
        if (l() instanceof u.d) {
            throw new IllegalStateException("SimpleTypeImpl should not be created for error type: " + l() + 10 + I0());
        }
    }

    @NotNull
    public u0 I0() {
        return this.d;
    }

    @NotNull
    public List<w0> H0() {
        return this.f;
    }

    public boolean J0() {
        return this.q;
    }

    @NotNull
    public h l() {
        return this.x;
    }

    @NotNull
    public g getAnnotations() {
        return g.b.b();
    }

    @NotNull
    /* renamed from: Q0 */
    public i0 O0(@NotNull g newAnnotations) {
        k.f(newAnnotations, "newAnnotations");
        if (newAnnotations.isEmpty()) {
            return this;
        }
        return new i(this, newAnnotations);
    }

    @NotNull
    /* renamed from: P0 */
    public i0 M0(boolean newNullability) {
        if (newNullability == J0()) {
            return this;
        }
        if (newNullability) {
            return new g0(this);
        }
        return new f0(this);
    }

    @NotNull
    /* renamed from: R0 */
    public i0 N0(@NotNull i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        i0 invoke = this.y.invoke(kotlinTypeRefiner);
        return invoke != null ? invoke : this;
    }
}
