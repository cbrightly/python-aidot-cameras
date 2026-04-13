package kotlin.reflect.jvm.internal.impl.resolve.calls.inference;

import java.util.Collection;
import java.util.List;
import kotlin.collections.p;
import kotlin.collections.q;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.checker.l;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.w0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CapturedTypeConstructor.kt */
public final class c implements b {
    @Nullable
    private l a;
    @NotNull
    private final w0 b;

    public c(@NotNull w0 projection) {
        k.f(projection, "projection");
        this.b = projection;
        if (!(getProjection().c() != h1.INVARIANT)) {
            throw new AssertionError("Only nontrivial projections can be captured, not: " + getProjection());
        }
    }

    public /* bridge */ /* synthetic */ h c() {
        return (h) e();
    }

    @NotNull
    public w0 getProjection() {
        return this.b;
    }

    @Nullable
    public final l f() {
        return this.a;
    }

    public final void h(@Nullable l lVar) {
        this.a = lVar;
    }

    @NotNull
    public List<t0> getParameters() {
        return q.g();
    }

    @NotNull
    public Collection<b0> b() {
        b0 superType;
        if (getProjection().c() == h1.OUT_VARIANCE) {
            superType = getProjection().getType();
        } else {
            superType = j().K();
        }
        k.b(superType, "if (projection.projectio… builtIns.nullableAnyType");
        return p.b(superType);
    }

    public boolean d() {
        return false;
    }

    @Nullable
    public Void e() {
        return null;
    }

    @NotNull
    public String toString() {
        return "CapturedTypeConstructor(" + getProjection() + ')';
    }

    @NotNull
    public g j() {
        g j = getProjection().getType().I0().j();
        k.b(j, "projection.type.constructor.builtIns");
        return j;
    }

    @NotNull
    /* renamed from: g */
    public c a(@NotNull i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        w0 a2 = getProjection().a(kotlinTypeRefiner);
        k.b(a2, "projection.refine(kotlinTypeRefiner)");
        return new c(a2);
    }
}
