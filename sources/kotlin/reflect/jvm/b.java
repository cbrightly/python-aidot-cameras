package kotlin.reflect.jvm;

import kotlin.jvm.internal.k;
import kotlin.reflect.c;
import kotlin.reflect.d;
import kotlin.reflect.jvm.internal.y;
import kotlin.reflect.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: KTypesJvm.kt */
public final class b {
    @NotNull
    public static final c<?> b(@NotNull n $this$jvmErasure) {
        c<?> a;
        k.f($this$jvmErasure, "$this$jvmErasure");
        d a2 = $this$jvmErasure.a();
        if (a2 != null && (a = a(a2)) != null) {
            return a;
        }
        throw new y("Cannot calculate JVM erasure for type: " + $this$jvmErasure);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: kotlin.reflect.n} */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0079, code lost:
        r2 = b(r1);
     */
    /* JADX WARNING: Multi-variable type inference failed */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final kotlin.reflect.c<?> a(@org.jetbrains.annotations.NotNull kotlin.reflect.d r10) {
        /*
            java.lang.String r0 = "$this$jvmErasure"
            kotlin.jvm.internal.k.f(r10, r0)
            boolean r0 = r10 instanceof kotlin.reflect.c
            if (r0 == 0) goto L_0x000f
            r0 = r10
            kotlin.reflect.c r0 = (kotlin.reflect.c) r0
            goto L_0x0087
        L_0x000f:
            boolean r0 = r10 instanceof kotlin.reflect.o
            if (r0 == 0) goto L_0x0089
            r0 = r10
            kotlin.reflect.o r0 = (kotlin.reflect.o) r0
            java.util.List r0 = r0.getUpperBounds()
            r1 = r0
            r2 = 0
            java.util.Iterator r3 = r1.iterator()
        L_0x0020:
            boolean r4 = r3.hasNext()
            r5 = 0
            if (r4 == 0) goto L_0x0069
            java.lang.Object r4 = r3.next()
            r6 = r4
            kotlin.reflect.n r6 = (kotlin.reflect.n) r6
            r7 = 0
            if (r6 == 0) goto L_0x0061
            r8 = r6
            kotlin.reflect.jvm.internal.w r8 = (kotlin.reflect.jvm.internal.w) r8
            kotlin.reflect.jvm.internal.impl.types.b0 r8 = r8.e()
            kotlin.reflect.jvm.internal.impl.types.u0 r8 = r8.I0()
            kotlin.reflect.jvm.internal.impl.descriptors.h r8 = r8.c()
            boolean r9 = r8 instanceof kotlin.reflect.jvm.internal.impl.descriptors.e
            if (r9 != 0) goto L_0x0045
            goto L_0x0046
        L_0x0045:
            r5 = r8
        L_0x0046:
            kotlin.reflect.jvm.internal.impl.descriptors.e r5 = (kotlin.reflect.jvm.internal.impl.descriptors.e) r5
            if (r5 == 0) goto L_0x005c
            kotlin.reflect.jvm.internal.impl.descriptors.f r8 = r5.h()
            kotlin.reflect.jvm.internal.impl.descriptors.f r9 = kotlin.reflect.jvm.internal.impl.descriptors.f.INTERFACE
            if (r8 == r9) goto L_0x005c
            kotlin.reflect.jvm.internal.impl.descriptors.f r8 = r5.h()
            kotlin.reflect.jvm.internal.impl.descriptors.f r9 = kotlin.reflect.jvm.internal.impl.descriptors.f.ANNOTATION_CLASS
            if (r8 == r9) goto L_0x005c
            r8 = 1
            goto L_0x005d
        L_0x005c:
            r8 = 0
        L_0x005d:
            if (r8 == 0) goto L_0x0020
            r5 = r4
            goto L_0x006a
        L_0x0061:
            kotlin.TypeCastException r3 = new kotlin.TypeCastException
            java.lang.String r5 = "null cannot be cast to non-null type kotlin.reflect.jvm.internal.KTypeImpl"
            r3.<init>(r5)
            throw r3
        L_0x0069:
        L_0x006a:
            kotlin.reflect.n r5 = (kotlin.reflect.n) r5
            if (r5 == 0) goto L_0x006f
            goto L_0x0076
        L_0x006f:
            java.lang.Object r1 = kotlin.collections.y.U(r0)
            r5 = r1
            kotlin.reflect.n r5 = (kotlin.reflect.n) r5
        L_0x0076:
            r1 = r5
            if (r1 == 0) goto L_0x0081
            kotlin.reflect.c r2 = b(r1)
            if (r2 == 0) goto L_0x0081
            r0 = r2
            goto L_0x0087
        L_0x0081:
            java.lang.Class<java.lang.Object> r2 = java.lang.Object.class
            kotlin.reflect.c r0 = kotlin.jvm.internal.a0.b(r2)
        L_0x0087:
            return r0
        L_0x0089:
            kotlin.reflect.jvm.internal.y r0 = new kotlin.reflect.jvm.internal.y
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Cannot calculate JVM erasure for type: "
            r1.append(r2)
            r1.append(r10)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.b.a(kotlin.reflect.d):kotlin.reflect.c");
    }
}
