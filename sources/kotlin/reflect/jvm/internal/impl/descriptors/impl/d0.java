package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.h0;
import kotlin.reflect.jvm.internal.impl.descriptors.k0;
import kotlin.reflect.jvm.internal.impl.descriptors.o;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.i0;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: PropertySetterDescriptorImpl */
public class d0 extends a0 implements k0 {
    private w0 p3;
    @NotNull
    private final k0 p4;

    private static /* synthetic */ void u(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 9:
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
                objArr[0] = "parameter";
                break;
            case 7:
                objArr[0] = "setterDescriptor";
                break;
            case 8:
                objArr[0] = IjkMediaMeta.IJKM_KEY_TYPE;
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/PropertySetterDescriptorImpl";
                break;
            default:
                objArr[0] = "correspondingProperty";
                break;
        }
        switch (i) {
            case 10:
                objArr[1] = "getOverriddenDescriptors";
                break;
            case 11:
                objArr[1] = "getValueParameters";
                break;
            case 12:
                objArr[1] = "getReturnType";
                break;
            case 13:
                objArr[1] = "getOriginal";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/PropertySetterDescriptorImpl";
                break;
        }
        switch (i) {
            case 6:
                objArr[2] = "initialize";
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "createSetterParameter";
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
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
    public d0(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.i0 r12, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r13, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.w r14, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.a1 r15, boolean r16, boolean r17, boolean r18, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.b.a r19, @org.jetbrains.annotations.Nullable kotlin.reflect.jvm.internal.impl.descriptors.k0 r20, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.o0 r21) {
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
            java.lang.String r1 = "<set-"
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
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.descriptors.impl.d0.<init>(kotlin.reflect.jvm.internal.impl.descriptors.i0, kotlin.reflect.jvm.internal.impl.descriptors.annotations.g, kotlin.reflect.jvm.internal.impl.descriptors.w, kotlin.reflect.jvm.internal.impl.descriptors.a1, boolean, boolean, boolean, kotlin.reflect.jvm.internal.impl.descriptors.b$a, kotlin.reflect.jvm.internal.impl.descriptors.k0, kotlin.reflect.jvm.internal.impl.descriptors.o0):void");
    }

    public void K0(@NotNull w0 parameter) {
        if (parameter == null) {
            u(6);
        }
        if (this.p3 == null) {
            this.p3 = parameter;
            return;
        }
        throw new AssertionError();
    }

    public static k0 I0(@NotNull k0 setterDescriptor, @NotNull b0 type, @NotNull g annotations) {
        if (setterDescriptor == null) {
            u(7);
        }
        if (type == null) {
            u(8);
        }
        if (annotations == null) {
            u(9);
        }
        return new k0(setterDescriptor, (w0) null, 0, annotations, f.k("<set-?>"), type, false, false, false, (b0) null, o0.a);
    }

    @NotNull
    public Collection<? extends k0> d() {
        Collection<h0> A0 = super.A0(false);
        if (A0 == null) {
            u(10);
        }
        return A0;
    }

    @NotNull
    public List<w0> g() {
        w0 w0Var = this.p3;
        if (w0Var != null) {
            List<w0> singletonList = Collections.singletonList(w0Var);
            if (singletonList == null) {
                u(11);
            }
            return singletonList;
        }
        throw new IllegalStateException();
    }

    @NotNull
    public b0 getReturnType() {
        i0 b0 = a.h(this).b0();
        if (b0 == null) {
            u(12);
        }
        return b0;
    }

    public <R, D> R w(o<R, D> visitor, D data) {
        return visitor.e(this, data);
    }

    @NotNull
    /* renamed from: J0 */
    public k0 l0() {
        k0 k0Var = this.p4;
        if (k0Var == null) {
            u(13);
        }
        return k0Var;
    }
}
