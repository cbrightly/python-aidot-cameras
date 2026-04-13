package kotlin.reflect.jvm.internal.impl.resolve.calls.inference;

import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.model.c;
import kotlin.reflect.jvm.internal.impl.types.p0;
import kotlin.reflect.jvm.internal.impl.types.u;
import kotlin.reflect.jvm.internal.impl.types.w0;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: CapturedTypeConstructor.kt */
public final class a extends i0 implements p0, c {
    @NotNull
    private final w0 d;
    @NotNull
    private final b f;
    private final boolean q;
    @NotNull
    private final g x;

    public a(@NotNull w0 typeProjection, @NotNull b constructor, boolean isMarkedNullable, @NotNull g annotations) {
        k.f(typeProjection, "typeProjection");
        k.f(constructor, "constructor");
        k.f(annotations, "annotations");
        this.d = typeProjection;
        this.f = constructor;
        this.q = isMarkedNullable;
        this.x = annotations;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ a(w0 w0Var, b bVar, boolean z, g gVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(w0Var, (i & 2) != 0 ? new c(w0Var) : bVar, (i & 4) != 0 ? false : z, (i & 8) != 0 ? g.b.b() : gVar);
    }

    @NotNull
    /* renamed from: R0 */
    public b I0() {
        return this.f;
    }

    public boolean J0() {
        return this.q;
    }

    @NotNull
    public g getAnnotations() {
        return this.x;
    }

    @NotNull
    public List<w0> H0() {
        return q.g();
    }

    @NotNull
    public h l() {
        h i = u.i("No member resolution should be done on captured type, it used only during constraint system resolution", true);
        k.b(i, "ErrorUtils.createErrorSc…solution\", true\n        )");
        return i;
    }

    @NotNull
    public b0 C0() {
        h1 h1Var = h1.OUT_VARIANCE;
        i0 K = kotlin.reflect.jvm.internal.impl.types.typeUtil.a.f(this).K();
        k.b(K, "builtIns.nullableAnyType");
        return V0(h1Var, K);
    }

    @NotNull
    public b0 f0() {
        h1 h1Var = h1.IN_VARIANCE;
        i0 J = kotlin.reflect.jvm.internal.impl.types.typeUtil.a.f(this).J();
        k.b(J, "builtIns.nothingType");
        return V0(h1Var, J);
    }

    private final b0 V0(h1 variance, b0 b0Var) {
        b0 type = this.d.c() == variance ? this.d.getType() : b0Var;
        k.b(type, "if (typeProjection.proje…jection.type else default");
        return type;
    }

    public boolean l0(@NotNull b0 type) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        return I0() == type.I0();
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Captured(");
        sb.append(this.d);
        sb.append(')');
        sb.append(J0() ? "?" : "");
        return sb.toString();
    }

    @NotNull
    /* renamed from: S0 */
    public a P0(boolean newNullability) {
        if (newNullability == J0()) {
            return this;
        }
        return new a(this.d, I0(), newNullability, getAnnotations());
    }

    @NotNull
    /* renamed from: U0 */
    public a Q0(@NotNull g newAnnotations) {
        k.f(newAnnotations, "newAnnotations");
        return new a(this.d, I0(), J0(), newAnnotations);
    }

    @NotNull
    /* renamed from: T0 */
    public a N0(@NotNull i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        w0 a = this.d.a(kotlinTypeRefiner);
        k.b(a, "typeProjection.refine(kotlinTypeRefiner)");
        return new a(a, I0(), J0(), getAnnotations());
    }
}
