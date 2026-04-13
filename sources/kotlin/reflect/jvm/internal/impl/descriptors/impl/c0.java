package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.h0;
import kotlin.reflect.jvm.internal.impl.descriptors.j0;
import kotlin.reflect.jvm.internal.impl.descriptors.o;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;

/* compiled from: PropertyGetterDescriptorImpl */
public class c0 extends a0 implements j0 {
    private b0 p3;
    @NotNull
    private final j0 p4;

    private static /* synthetic */ void u(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 6:
            case 7:
            case 8:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 6:
            case 7:
            case 8:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "annotations";
                break;
            case 2:
                objArr[0] = "modality";
                break;
            case 3:
                objArr[0] = "visibility";
                break;
            case 4:
                objArr[0] = "kind";
                break;
            case 5:
                objArr[0] = "source";
                break;
            case 6:
            case 7:
            case 8:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/PropertyGetterDescriptorImpl";
                break;
            default:
                objArr[0] = "correspondingProperty";
                break;
        }
        switch (i) {
            case 6:
                objArr[1] = "getOverriddenDescriptors";
                break;
            case 7:
                objArr[1] = "getValueParameters";
                break;
            case 8:
                objArr[1] = "getOriginal";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/PropertyGetterDescriptorImpl";
                break;
        }
        switch (i) {
            case 6:
            case 7:
            case 8:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 6:
            case 7:
            case 8:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public c0(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.i0 r12, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r13, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.w r14, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.a1 r15, boolean r16, boolean r17, boolean r18, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.b.a r19, @org.jetbrains.annotations.Nullable kotlin.reflect.jvm.internal.impl.descriptors.j0 r20, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.o0 r21) {
        /*
            r11 = this;
            if (r12 != 0) goto L_0x0006
            r0 = 0
            u(r0)
        L_0x0006:
            if (r13 != 0) goto L_0x000c
            r0 = 1
            u(r0)
        L_0x000c:
            if (r14 != 0) goto L_0x0012
            r0 = 2
            u(r0)
        L_0x0012:
            if (r15 != 0) goto L_0x0018
            r0 = 3
            u(r0)
        L_0x0018:
            if (r19 != 0) goto L_0x001e
            r0 = 4
            u(r0)
        L_0x001e:
            if (r21 != 0) goto L_0x0024
            r0 = 5
            u(r0)
        L_0x0024:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "<get-"
            r0.append(r1)
            kotlin.reflect.jvm.internal.impl.name.f r1 = r12.getName()
            r0.append(r1)
            java.lang.String r1 = ">"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            kotlin.reflect.jvm.internal.impl.name.f r5 = kotlin.reflect.jvm.internal.impl.name.f.k(r0)
            r0 = r11
            r1 = r14
            r2 = r15
            r3 = r12
            r4 = r13
            r6 = r16
            r7 = r17
            r8 = r18
            r9 = r19
            r10 = r21
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            if (r20 == 0) goto L_0x0059
            r0 = r20
            goto L_0x005a
        L_0x0059:
            r0 = r11
        L_0x005a:
            r1 = r11
            r1.p4 = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.descriptors.impl.c0.<init>(kotlin.reflect.jvm.internal.impl.descriptors.i0, kotlin.reflect.jvm.internal.impl.descriptors.annotations.g, kotlin.reflect.jvm.internal.impl.descriptors.w, kotlin.reflect.jvm.internal.impl.descriptors.a1, boolean, boolean, boolean, kotlin.reflect.jvm.internal.impl.descriptors.b$a, kotlin.reflect.jvm.internal.impl.descriptors.j0, kotlin.reflect.jvm.internal.impl.descriptors.o0):void");
    }

    public void J0(b0 returnType) {
        this.p3 = returnType == null ? Q().getType() : returnType;
    }

    @NotNull
    public Collection<? extends j0> d() {
        Collection<h0> A0 = super.A0(true);
        if (A0 == null) {
            u(6);
        }
        return A0;
    }

    @NotNull
    public List<w0> g() {
        List<w0> emptyList = Collections.emptyList();
        if (emptyList == null) {
            u(7);
        }
        return emptyList;
    }

    public b0 getReturnType() {
        return this.p3;
    }

    public <R, D> R w(o<R, D> visitor, D data) {
        return visitor.g(this, data);
    }

    @NotNull
    /* renamed from: I0 */
    public j0 l0() {
        j0 j0Var = this.p4;
        if (j0Var == null) {
            u(8);
        }
        return j0Var;
    }
}
