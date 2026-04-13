package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.functions.a;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.types.g1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.model.b;
import kotlin.reflect.jvm.internal.impl.types.model.c;
import kotlin.reflect.jvm.internal.impl.types.u;
import kotlin.reflect.jvm.internal.impl.types.w0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: NewCapturedType.kt */
public final class k extends i0 implements c {
    @NotNull
    private final b d;
    @NotNull
    private final l f;
    @Nullable
    private final g1 q;
    @NotNull
    private final g x;
    private final boolean y;

    public k(@NotNull b captureStatus, @NotNull l constructor, @Nullable g1 lowerType, @NotNull g annotations, boolean isMarkedNullable) {
        kotlin.jvm.internal.k.f(captureStatus, "captureStatus");
        kotlin.jvm.internal.k.f(constructor, "constructor");
        kotlin.jvm.internal.k.f(annotations, "annotations");
        this.d = captureStatus;
        this.f = constructor;
        this.q = lowerType;
        this.x = annotations;
        this.y = isMarkedNullable;
    }

    @NotNull
    /* renamed from: R0 */
    public l I0() {
        return this.f;
    }

    @Nullable
    public final g1 S0() {
        return this.q;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ k(kotlin.reflect.jvm.internal.impl.types.model.b r7, kotlin.reflect.jvm.internal.impl.types.checker.l r8, kotlin.reflect.jvm.internal.impl.types.g1 r9, kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r10, boolean r11, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
        /*
            r6 = this;
            r13 = r12 & 8
            if (r13 == 0) goto L_0x000c
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g$a r10 = kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r10 = r10.b()
            r4 = r10
            goto L_0x000d
        L_0x000c:
            r4 = r10
        L_0x000d:
            r10 = r12 & 16
            if (r10 == 0) goto L_0x0014
            r11 = 0
            r5 = r11
            goto L_0x0015
        L_0x0014:
            r5 = r11
        L_0x0015:
            r0 = r6
            r1 = r7
            r2 = r8
            r3 = r9
            r0.<init>(r1, r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.types.checker.k.<init>(kotlin.reflect.jvm.internal.impl.types.model.b, kotlin.reflect.jvm.internal.impl.types.checker.l, kotlin.reflect.jvm.internal.impl.types.g1, kotlin.reflect.jvm.internal.impl.descriptors.annotations.g, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @NotNull
    public g getAnnotations() {
        return this.x;
    }

    public boolean J0() {
        return this.y;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public k(@NotNull b captureStatus, @Nullable g1 lowerType, @NotNull w0 projection, @NotNull t0 typeParameter) {
        this(captureStatus, new l(projection, (a) null, (l) null, typeParameter, 6, (DefaultConstructorMarker) null), lowerType, (g) null, false, 24, (DefaultConstructorMarker) null);
        kotlin.jvm.internal.k.f(captureStatus, "captureStatus");
        kotlin.jvm.internal.k.f(projection, "projection");
        kotlin.jvm.internal.k.f(typeParameter, "typeParameter");
    }

    @NotNull
    public List<w0> H0() {
        return q.g();
    }

    @NotNull
    public h l() {
        h i = u.i("No member resolution should be done on captured type!", true);
        kotlin.jvm.internal.k.b(i, "ErrorUtils.createErrorSc…on captured type!\", true)");
        return i;
    }

    @NotNull
    /* renamed from: V0 */
    public k Q0(@NotNull g newAnnotations) {
        kotlin.jvm.internal.k.f(newAnnotations, "newAnnotations");
        return new k(this.d, I0(), this.q, newAnnotations, J0());
    }

    @NotNull
    /* renamed from: T0 */
    public k P0(boolean newNullability) {
        return new k(this.d, I0(), this.q, getAnnotations(), newNullability);
    }

    @NotNull
    /* renamed from: U0 */
    public k N0(@NotNull i kotlinTypeRefiner) {
        kotlin.jvm.internal.k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        b bVar = this.d;
        l i = I0().a(kotlinTypeRefiner);
        g1 it = this.q;
        return new k(bVar, i, it != null ? kotlinTypeRefiner.g(it).L0() : null, getAnnotations(), J0());
    }
}
