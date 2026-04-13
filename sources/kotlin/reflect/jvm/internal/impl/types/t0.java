package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TypeCapabilities.kt */
public final class t0 {
    public static final boolean d(@NotNull b0 $this$isCustomTypeVariable) {
        k.f($this$isCustomTypeVariable, "$this$isCustomTypeVariable");
        g1 L0 = $this$isCustomTypeVariable.L0();
        if (!(L0 instanceof k)) {
            L0 = null;
        }
        k kVar = (k) L0;
        if (kVar != null) {
            return kVar.u();
        }
        return false;
    }

    @Nullable
    public static final k a(@NotNull b0 $this$getCustomTypeVariable) {
        k.f($this$getCustomTypeVariable, "$this$getCustomTypeVariable");
        g1 L0 = $this$getCustomTypeVariable.L0();
        if (!(L0 instanceof k)) {
            L0 = null;
        }
        k it = (k) L0;
        if (it == null || !it.u()) {
            return null;
        }
        return it;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0012, code lost:
        r0 = r0.C0();
     */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final kotlin.reflect.jvm.internal.impl.types.b0 b(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.types.b0 r2) {
        /*
            java.lang.String r0 = "$this$getSubtypeRepresentative"
            kotlin.jvm.internal.k.f(r2, r0)
            kotlin.reflect.jvm.internal.impl.types.g1 r0 = r2.L0()
            boolean r1 = r0 instanceof kotlin.reflect.jvm.internal.impl.types.p0
            if (r1 != 0) goto L_0x000e
            r0 = 0
        L_0x000e:
            kotlin.reflect.jvm.internal.impl.types.p0 r0 = (kotlin.reflect.jvm.internal.impl.types.p0) r0
            if (r0 == 0) goto L_0x0019
            kotlin.reflect.jvm.internal.impl.types.b0 r0 = r0.C0()
            if (r0 == 0) goto L_0x0019
            goto L_0x001a
        L_0x0019:
            r0 = r2
        L_0x001a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.types.t0.b(kotlin.reflect.jvm.internal.impl.types.b0):kotlin.reflect.jvm.internal.impl.types.b0");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0012, code lost:
        r0 = r0.f0();
     */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final kotlin.reflect.jvm.internal.impl.types.b0 c(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.types.b0 r2) {
        /*
            java.lang.String r0 = "$this$getSupertypeRepresentative"
            kotlin.jvm.internal.k.f(r2, r0)
            kotlin.reflect.jvm.internal.impl.types.g1 r0 = r2.L0()
            boolean r1 = r0 instanceof kotlin.reflect.jvm.internal.impl.types.p0
            if (r1 != 0) goto L_0x000e
            r0 = 0
        L_0x000e:
            kotlin.reflect.jvm.internal.impl.types.p0 r0 = (kotlin.reflect.jvm.internal.impl.types.p0) r0
            if (r0 == 0) goto L_0x0019
            kotlin.reflect.jvm.internal.impl.types.b0 r0 = r0.f0()
            if (r0 == 0) goto L_0x0019
            goto L_0x001a
        L_0x0019:
            r0 = r2
        L_0x001a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.types.t0.c(kotlin.reflect.jvm.internal.impl.types.b0):kotlin.reflect.jvm.internal.impl.types.b0");
    }

    public static final boolean e(@NotNull b0 first, @NotNull b0 second) {
        k.f(first, "first");
        k.f(second, "second");
        g1 L0 = first.L0();
        p0 p0Var = null;
        if (!(L0 instanceof p0)) {
            L0 = null;
        }
        p0 p0Var2 = (p0) L0;
        if (!(p0Var2 != null ? p0Var2.l0(second) : false)) {
            g1 L02 = second.L0();
            if (L02 instanceof p0) {
                p0Var = L02;
            }
            p0 p0Var3 = p0Var;
            if (p0Var3 != null ? p0Var3.l0(first) : false) {
                return true;
            }
            return false;
        }
        return true;
    }
}
