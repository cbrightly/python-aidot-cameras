package kotlin.reflect.jvm.internal.calls;

import java.lang.reflect.Method;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.h0;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.l;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.x0;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a;
import kotlin.reflect.jvm.internal.impl.resolve.e;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.y;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: InlineClassAwareCaller.kt */
public final class h {
    public static /* synthetic */ d c(d dVar, b bVar, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return b(dVar, bVar, z);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0062, code lost:
        if (kotlin.reflect.jvm.internal.impl.resolve.e.c(r0) != true) goto L_0x0064;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006d, code lost:
        if (g(r11) == false) goto L_0x0070;
     */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0079  */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <M extends java.lang.reflect.Member> kotlin.reflect.jvm.internal.calls.d<M> b(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.calls.d<? extends M> r10, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.b r11, boolean r12) {
        /*
            java.lang.String r0 = "$this$createInlineClassAwareCallerIfNeeded"
            kotlin.jvm.internal.k.f(r10, r0)
            java.lang.String r0 = "descriptor"
            kotlin.jvm.internal.k.f(r11, r0)
            boolean r0 = kotlin.reflect.jvm.internal.impl.resolve.e.a(r11)
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x006f
            java.util.List r0 = r11.g()
            java.lang.String r3 = "descriptor.valueParameters"
            kotlin.jvm.internal.k.b(r0, r3)
            r3 = 0
            boolean r4 = r0 instanceof java.util.Collection
            if (r4 == 0) goto L_0x002d
            boolean r4 = r0.isEmpty()
            if (r4 == 0) goto L_0x002d
            r0 = r1
            goto L_0x0056
        L_0x002d:
            java.util.Iterator r4 = r0.iterator()
        L_0x0031:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x0055
            java.lang.Object r5 = r4.next()
            r6 = r5
            kotlin.reflect.jvm.internal.impl.descriptors.w0 r6 = (kotlin.reflect.jvm.internal.impl.descriptors.w0) r6
            r7 = 0
            java.lang.String r8 = "it"
            kotlin.jvm.internal.k.b(r6, r8)
            kotlin.reflect.jvm.internal.impl.types.b0 r8 = r6.getType()
            java.lang.String r9 = "it.type"
            kotlin.jvm.internal.k.b(r8, r9)
            boolean r6 = kotlin.reflect.jvm.internal.impl.resolve.e.c(r8)
            if (r6 == 0) goto L_0x0031
            r0 = r2
            goto L_0x0056
        L_0x0055:
            r0 = r1
        L_0x0056:
            if (r0 != 0) goto L_0x006f
            kotlin.reflect.jvm.internal.impl.types.b0 r0 = r11.getReturnType()
            if (r0 == 0) goto L_0x0064
            boolean r0 = kotlin.reflect.jvm.internal.impl.resolve.e.c(r0)
            if (r0 == r2) goto L_0x006f
        L_0x0064:
            boolean r0 = r10 instanceof kotlin.reflect.jvm.internal.calls.c
            if (r0 != 0) goto L_0x0070
            boolean r0 = g(r11)
            if (r0 == 0) goto L_0x0070
        L_0x006f:
            r1 = r2
        L_0x0070:
            r0 = r1
            if (r0 == 0) goto L_0x0079
            kotlin.reflect.jvm.internal.calls.g r1 = new kotlin.reflect.jvm.internal.calls.g
            r1.<init>(r11, r10, r12)
            goto L_0x007a
        L_0x0079:
            r1 = r10
        L_0x007a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.calls.h.b(kotlin.reflect.jvm.internal.calls.d, kotlin.reflect.jvm.internal.impl.descriptors.b, boolean):kotlin.reflect.jvm.internal.calls.d");
    }

    private static final boolean g(@NotNull b $this$hasInlineClassReceiver) {
        b0 e = e($this$hasInlineClassReceiver);
        return e != null && e.c(e);
    }

    @NotNull
    public static final Method f(@NotNull Class<?> $this$getUnboxMethod, @NotNull b descriptor) {
        k.f($this$getUnboxMethod, "$this$getUnboxMethod");
        k.f(descriptor, "descriptor");
        try {
            Method declaredMethod = $this$getUnboxMethod.getDeclaredMethod("unbox-impl", new Class[0]);
            k.b(declaredMethod, "getDeclaredMethod(\"unbox…FOR_INLINE_CLASS_MEMBERS)");
            return declaredMethod;
        } catch (NoSuchMethodException e) {
            throw new y("No unbox method found in inline class: " + $this$getUnboxMethod + " (calling " + descriptor + ')');
        }
    }

    @NotNull
    public static final Method d(@NotNull Class<?> $this$getBoxMethod, @NotNull b descriptor) {
        k.f($this$getBoxMethod, "$this$getBoxMethod");
        k.f(descriptor, "descriptor");
        try {
            Method declaredMethod = $this$getBoxMethod.getDeclaredMethod("box-impl", new Class[]{f($this$getBoxMethod, descriptor).getReturnType()});
            k.b(declaredMethod, "getDeclaredMethod(\"box\" …d(descriptor).returnType)");
            return declaredMethod;
        } catch (NoSuchMethodException e) {
            throw new y("No box method found in inline class: " + $this$getBoxMethod + " (calling " + descriptor + ')');
        }
    }

    @Nullable
    public static final Class<?> i(@NotNull b0 $this$toInlineClass) {
        k.f($this$toInlineClass, "$this$toInlineClass");
        return h($this$toInlineClass.I0().c());
    }

    @Nullable
    public static final Class<?> h(@Nullable m $this$toInlineClass) {
        if (!($this$toInlineClass instanceof kotlin.reflect.jvm.internal.impl.descriptors.e) || !((kotlin.reflect.jvm.internal.impl.descriptors.e) $this$toInlineClass).isInline()) {
            return null;
        }
        Class<?> l = h0.l((kotlin.reflect.jvm.internal.impl.descriptors.e) $this$toInlineClass);
        if (l != null) {
            return l;
        }
        throw new y("Class object for the class " + ((kotlin.reflect.jvm.internal.impl.descriptors.e) $this$toInlineClass).getName() + " cannot be found (classId=" + a.i((kotlin.reflect.jvm.internal.impl.descriptors.h) $this$toInlineClass) + ')');
    }

    private static final b0 e(@NotNull b $this$expectedReceiverType) {
        l0 extensionReceiver = $this$expectedReceiverType.L();
        l0 dispatchReceiver = $this$expectedReceiverType.I();
        if (extensionReceiver != null) {
            return extensionReceiver.getType();
        }
        if (dispatchReceiver == null) {
            return null;
        }
        if ($this$expectedReceiverType instanceof l) {
            return dispatchReceiver.getType();
        }
        m b = $this$expectedReceiverType.b();
        if (!(b instanceof kotlin.reflect.jvm.internal.impl.descriptors.e)) {
            b = null;
        }
        kotlin.reflect.jvm.internal.impl.descriptors.e eVar = (kotlin.reflect.jvm.internal.impl.descriptors.e) b;
        if (eVar != null) {
            return eVar.m();
        }
        return null;
    }

    @Nullable
    public static final Object a(@Nullable Object $this$coerceToExpectedReceiverType, @NotNull b descriptor) {
        b0 expectedReceiverType;
        Class<?> i;
        Method unboxMethod;
        k.f(descriptor, "descriptor");
        if (((descriptor instanceof i0) && e.d((x0) descriptor)) || (expectedReceiverType = e(descriptor)) == null || (i = i(expectedReceiverType)) == null || (unboxMethod = f(i, descriptor)) == null) {
            return $this$coerceToExpectedReceiverType;
        }
        return unboxMethod.invoke($this$coerceToExpectedReceiverType, new Object[0]);
    }
}
