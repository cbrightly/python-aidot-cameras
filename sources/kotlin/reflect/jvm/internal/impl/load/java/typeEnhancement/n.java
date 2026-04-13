package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.Set;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: signatureEnhancement.kt */
public final class n {
    @NotNull
    public static final d a(@Nullable g nullability, @Nullable e mutability, boolean forWarning, boolean isAnyNonNullTypeParameter) {
        if (!isAnyNonNullTypeParameter || nullability != g.NOT_NULL) {
            return new d(nullability, mutability, false, forWarning);
        }
        return new d(nullability, mutability, true, forWarning);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0042, code lost:
        if (r0 != null) goto L_0x0046;
     */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> T b(@org.jetbrains.annotations.NotNull java.util.Set<? extends T> r3, @org.jetbrains.annotations.NotNull T r4, @org.jetbrains.annotations.NotNull T r5, @org.jetbrains.annotations.Nullable T r6, boolean r7) {
        /*
            java.lang.String r0 = "$this$select"
            kotlin.jvm.internal.k.f(r3, r0)
            java.lang.String r0 = "low"
            kotlin.jvm.internal.k.f(r4, r0)
            java.lang.String r0 = "high"
            kotlin.jvm.internal.k.f(r5, r0)
            if (r7 == 0) goto L_0x0036
            boolean r0 = r3.contains(r4)
            r1 = 0
            if (r0 == 0) goto L_0x001a
            r0 = r4
            goto L_0x0023
        L_0x001a:
            boolean r0 = r3.contains(r5)
            if (r0 == 0) goto L_0x0022
            r0 = r5
            goto L_0x0023
        L_0x0022:
            r0 = r1
        L_0x0023:
            boolean r2 = kotlin.jvm.internal.k.a(r0, r4)
            if (r2 == 0) goto L_0x0030
            boolean r2 = kotlin.jvm.internal.k.a(r6, r5)
            if (r2 == 0) goto L_0x0030
            goto L_0x0035
        L_0x0030:
            if (r6 == 0) goto L_0x0034
            r1 = r6
            goto L_0x0035
        L_0x0034:
            r1 = r0
        L_0x0035:
            return r1
        L_0x0036:
            if (r6 == 0) goto L_0x0045
            r0 = r6
            r1 = 0
            java.util.Set r2 = kotlin.collections.p0.j(r3, r6)
            java.util.Set r0 = kotlin.collections.y.H0(r2)
            if (r0 == 0) goto L_0x0045
            goto L_0x0046
        L_0x0045:
            r0 = r3
        L_0x0046:
            java.lang.Object r1 = kotlin.collections.y.r0(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.n.b(java.util.Set, java.lang.Object, java.lang.Object, java.lang.Object, boolean):java.lang.Object");
    }

    @Nullable
    public static final g c(@NotNull Set<? extends g> $this$select, @Nullable g own, boolean isCovariant) {
        k.f($this$select, "$this$select");
        g gVar = g.FORCE_FLEXIBILITY;
        return own == gVar ? gVar : (g) b($this$select, g.NOT_NULL, g.NULLABLE, own, isCovariant);
    }
}
