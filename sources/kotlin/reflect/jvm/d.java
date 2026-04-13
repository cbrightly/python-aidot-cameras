package kotlin.reflect.jvm;

import java.lang.reflect.Type;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.w;
import kotlin.reflect.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: ReflectJvmMapping.kt */
public final class d {
    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000c, code lost:
        r0 = r0.p();
     */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.reflect.Method a(@org.jetbrains.annotations.NotNull kotlin.reflect.f<?> r3) {
        /*
            java.lang.String r0 = "$this$javaMethod"
            kotlin.jvm.internal.k.f(r3, r0)
            kotlin.reflect.jvm.internal.e r0 = kotlin.reflect.jvm.internal.h0.a(r3)
            r1 = 0
            if (r0 == 0) goto L_0x0017
            kotlin.reflect.jvm.internal.calls.d r0 = r0.p()
            if (r0 == 0) goto L_0x0017
            java.lang.reflect.Member r0 = r0.b()
            goto L_0x0018
        L_0x0017:
            r0 = r1
        L_0x0018:
            boolean r2 = r0 instanceof java.lang.reflect.Method
            if (r2 != 0) goto L_0x001d
            goto L_0x001e
        L_0x001d:
            r1 = r0
        L_0x001e:
            java.lang.reflect.Method r1 = (java.lang.reflect.Method) r1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.d.a(kotlin.reflect.f):java.lang.reflect.Method");
    }

    @NotNull
    public static final Type b(@NotNull n $this$javaType) {
        k.f($this$javaType, "$this$javaType");
        return ((w) $this$javaType).d();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0012, code lost:
        r0 = r0.b();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final kotlin.reflect.e c(@org.jetbrains.annotations.NotNull java.lang.reflect.Member r4) {
        /*
            kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.f$a r0 = kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.f.a
            java.lang.Class r1 = r4.getDeclaringClass()
            java.lang.String r2 = "declaringClass"
            kotlin.jvm.internal.k.b(r1, r2)
            kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.f r0 = r0.a(r1)
            r1 = 0
            if (r0 == 0) goto L_0x001d
            kotlin.reflect.jvm.internal.impl.load.kotlin.header.a r0 = r0.b()
            if (r0 == 0) goto L_0x001d
            kotlin.reflect.jvm.internal.impl.load.kotlin.header.a$a r0 = r0.c()
            goto L_0x001e
        L_0x001d:
            r0 = r1
        L_0x001e:
            if (r0 != 0) goto L_0x0021
            goto L_0x002c
        L_0x0021:
            int[] r3 = kotlin.reflect.jvm.c.a
            int r0 = r0.ordinal()
            r0 = r3[r0]
            switch(r0) {
                case 1: goto L_0x002d;
                case 2: goto L_0x002d;
                case 3: goto L_0x002d;
                default: goto L_0x002c;
            }
        L_0x002c:
            goto L_0x003c
        L_0x002d:
            kotlin.reflect.jvm.internal.o r0 = new kotlin.reflect.jvm.internal.o
            java.lang.Class r3 = r4.getDeclaringClass()
            kotlin.jvm.internal.k.b(r3, r2)
            r2 = 2
            r0.<init>(r3, r1, r2, r1)
            r1 = r0
            goto L_0x003d
        L_0x003c:
        L_0x003d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.d.c(java.lang.reflect.Member):kotlin.reflect.e");
    }

    /* JADX WARNING: Removed duplicated region for block: B:56:0x00cc A[EDGE_INSN: B:56:0x00cc->B:38:0x00cc ?: BREAK  , SYNTHETIC] */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final kotlin.reflect.f<?> d(@org.jetbrains.annotations.NotNull java.lang.reflect.Method r14) {
        /*
            java.lang.String r0 = "$this$kotlinFunction"
            kotlin.jvm.internal.k.f(r14, r0)
            int r0 = r14.getModifiers()
            boolean r0 = java.lang.reflect.Modifier.isStatic(r0)
            java.lang.String r1 = "declaringClass"
            r2 = 0
            if (r0 == 0) goto L_0x00d3
            kotlin.reflect.e r0 = c(r14)
            if (r0 == 0) goto L_0x005f
            java.util.Collection r1 = r0.g()
            r3 = 0
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r5 = r1
            r6 = 0
            java.util.Iterator r7 = r5.iterator()
        L_0x0028:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x003a
            java.lang.Object r8 = r7.next()
            boolean r9 = r8 instanceof kotlin.reflect.f
            if (r9 == 0) goto L_0x0028
            r4.add(r8)
            goto L_0x0028
        L_0x003a:
            r1 = r4
            r3 = 0
            java.util.Iterator r4 = r1.iterator()
        L_0x0041:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x005b
            java.lang.Object r5 = r4.next()
            r6 = r5
            kotlin.reflect.f r6 = (kotlin.reflect.f) r6
            r7 = 0
            java.lang.reflect.Method r8 = a(r6)
            boolean r6 = kotlin.jvm.internal.k.a(r8, r14)
            if (r6 == 0) goto L_0x0041
            r2 = r5
            goto L_0x005c
        L_0x005b:
        L_0x005c:
            kotlin.reflect.f r2 = (kotlin.reflect.f) r2
            return r2
        L_0x005f:
            java.lang.Class r3 = r14.getDeclaringClass()
            kotlin.jvm.internal.k.b(r3, r1)
            kotlin.reflect.c r3 = kotlin.jvm.a.e(r3)
            kotlin.reflect.c r3 = kotlin.reflect.full.b.a(r3)
            if (r3 == 0) goto L_0x00d3
            java.util.Collection r4 = kotlin.reflect.full.b.b(r3)
            r5 = 0
            java.util.Iterator r6 = r4.iterator()
        L_0x007b:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x00cb
            java.lang.Object r7 = r6.next()
            r8 = r7
            kotlin.reflect.f r8 = (kotlin.reflect.f) r8
            r9 = 0
            java.lang.reflect.Method r10 = a(r8)
            if (r10 == 0) goto L_0x00c7
            java.lang.String r11 = r10.getName()
            java.lang.String r12 = r14.getName()
            boolean r11 = kotlin.jvm.internal.k.a(r11, r12)
            if (r11 == 0) goto L_0x00c7
            java.lang.Class[] r11 = r10.getParameterTypes()
            if (r11 != 0) goto L_0x00a7
            kotlin.jvm.internal.k.n()
        L_0x00a7:
            java.lang.Class[] r12 = r14.getParameterTypes()
            java.lang.String r13 = "this.parameterTypes"
            kotlin.jvm.internal.k.b(r12, r13)
            boolean r11 = java.util.Arrays.equals(r11, r12)
            if (r11 == 0) goto L_0x00c7
            java.lang.Class r11 = r10.getReturnType()
            java.lang.Class r12 = r14.getReturnType()
            boolean r11 = kotlin.jvm.internal.k.a(r11, r12)
            if (r11 == 0) goto L_0x00c7
            r11 = 1
            goto L_0x00c8
        L_0x00c7:
            r11 = 0
        L_0x00c8:
            if (r11 == 0) goto L_0x007b
            goto L_0x00cc
        L_0x00cb:
            r7 = r2
        L_0x00cc:
            kotlin.reflect.f r7 = (kotlin.reflect.f) r7
            if (r7 == 0) goto L_0x00d3
            r1 = r7
            r2 = 0
            return r1
        L_0x00d3:
            java.lang.Class r0 = r14.getDeclaringClass()
            kotlin.jvm.internal.k.b(r0, r1)
            kotlin.reflect.c r0 = kotlin.jvm.a.e(r0)
            java.util.Collection r0 = kotlin.reflect.full.b.b(r0)
            r1 = 0
            java.util.Iterator r3 = r0.iterator()
        L_0x00e7:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0101
            java.lang.Object r4 = r3.next()
            r5 = r4
            kotlin.reflect.f r5 = (kotlin.reflect.f) r5
            r6 = 0
            java.lang.reflect.Method r7 = a(r5)
            boolean r5 = kotlin.jvm.internal.k.a(r7, r14)
            if (r5 == 0) goto L_0x00e7
            r2 = r4
            goto L_0x0102
        L_0x0101:
        L_0x0102:
            kotlin.reflect.f r2 = (kotlin.reflect.f) r2
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.d.d(java.lang.reflect.Method):kotlin.reflect.f");
    }
}
