package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.renderer.c;
import kotlin.reflect.jvm.internal.impl.renderer.i;
import kotlin.reflect.jvm.internal.impl.types.model.e;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.a;
import org.jetbrains.annotations.NotNull;

/* compiled from: dynamicTypes.kt */
public final class r extends v implements e {
    @NotNull
    private final g q;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public r(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.builtins.g r4, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r5) {
        /*
            r3 = this;
            java.lang.String r0 = "builtIns"
            kotlin.jvm.internal.k.f(r4, r0)
            java.lang.String r0 = "annotations"
            kotlin.jvm.internal.k.f(r5, r0)
            kotlin.reflect.jvm.internal.impl.types.i0 r0 = r4.J()
            java.lang.String r1 = "builtIns.nothingType"
            kotlin.jvm.internal.k.b(r0, r1)
            kotlin.reflect.jvm.internal.impl.types.i0 r1 = r4.K()
            java.lang.String r2 = "builtIns.nullableAnyType"
            kotlin.jvm.internal.k.b(r1, r2)
            r3.<init>(r0, r1)
            r3.q = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.types.r.<init>(kotlin.reflect.jvm.internal.impl.builtins.g, kotlin.reflect.jvm.internal.impl.descriptors.annotations.g):void");
    }

    @NotNull
    public g getAnnotations() {
        return this.q;
    }

    @NotNull
    public i0 P0() {
        return R0();
    }

    @NotNull
    /* renamed from: T0 */
    public r M0(boolean newNullability) {
        return this;
    }

    public boolean J0() {
        return false;
    }

    @NotNull
    /* renamed from: V0 */
    public r O0(@NotNull g newAnnotations) {
        k.f(newAnnotations, "newAnnotations");
        return new r(a.f(P0()), newAnnotations);
    }

    @NotNull
    public String S0(@NotNull c renderer, @NotNull i options) {
        k.f(renderer, "renderer");
        k.f(options, "options");
        return "dynamic";
    }

    @NotNull
    /* renamed from: U0 */
    public r N0(@NotNull kotlin.reflect.jvm.internal.impl.types.checker.i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }
}
