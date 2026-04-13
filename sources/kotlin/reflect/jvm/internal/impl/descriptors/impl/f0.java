package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.reflect.jvm.internal.impl.descriptors.a;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SimpleFunctionDescriptorImpl */
public class f0 extends p implements n0 {
    private static /* synthetic */ void u(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 13:
            case 17:
            case 18:
            case 23:
            case 24:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 13:
            case 17:
            case 18:
            case 23:
            case 24:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 6:
            case 21:
                objArr[0] = "annotations";
                break;
            case 2:
            case 7:
                objArr[0] = "name";
                break;
            case 3:
            case 8:
            case 20:
                objArr[0] = "kind";
                break;
            case 4:
            case 9:
            case 22:
                objArr[0] = "source";
                break;
            case 10:
            case 14:
                objArr[0] = "typeParameters";
                break;
            case 11:
            case 15:
                objArr[0] = "unsubstitutedValueParameters";
                break;
            case 12:
            case 16:
                objArr[0] = "visibility";
                break;
            case 13:
            case 17:
            case 18:
            case 23:
            case 24:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/SimpleFunctionDescriptorImpl";
                break;
            case 19:
                objArr[0] = "newOwner";
                break;
            default:
                objArr[0] = "containingDeclaration";
                break;
        }
        switch (i) {
            case 13:
            case 17:
                objArr[1] = "initialize";
                break;
            case 18:
                objArr[1] = "getOriginal";
                break;
            case 23:
                objArr[1] = "copy";
                break;
            case 24:
                objArr[1] = "newCopyBuilder";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/SimpleFunctionDescriptorImpl";
                break;
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[2] = "create";
                break;
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
                objArr[2] = "initialize";
                break;
            case 13:
            case 17:
            case 18:
            case 23:
            case 24:
                break;
            case 19:
            case 20:
            case 21:
            case 22:
                objArr[2] = "createSubstitutedCopy";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 13:
            case 17:
            case 18:
            case 23:
            case 24:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    protected f0(@NotNull m containingDeclaration, @Nullable n0 original, @NotNull g annotations, @NotNull f name, @NotNull b.a kind, @NotNull o0 source) {
        super(containingDeclaration, original, annotations, name, kind, source);
        if (containingDeclaration == null) {
            u(0);
        }
        if (annotations == null) {
            u(1);
        }
        if (name == null) {
            u(2);
        }
        if (kind == null) {
            u(3);
        }
        if (source == null) {
            u(4);
        }
    }

    @NotNull
    public static f0 d1(@NotNull m containingDeclaration, @NotNull g annotations, @NotNull f name, @NotNull b.a kind, @NotNull o0 source) {
        if (containingDeclaration == null) {
            u(5);
        }
        if (annotations == null) {
            u(6);
        }
        if (name == null) {
            u(7);
        }
        if (kind == null) {
            u(8);
        }
        if (source == null) {
            u(9);
        }
        return new f0(containingDeclaration, (n0) null, annotations, name, kind, source);
    }

    @NotNull
    /* renamed from: f1 */
    public f0 J0(@Nullable l0 extensionReceiverParameter, @Nullable l0 dispatchReceiverParameter, @NotNull List<? extends t0> typeParameters, @NotNull List<w0> unsubstitutedValueParameters, @Nullable b0 unsubstitutedReturnType, @Nullable w modality, @NotNull a1 visibility) {
        if (typeParameters == null) {
            u(10);
        }
        if (unsubstitutedValueParameters == null) {
            u(11);
        }
        if (visibility == null) {
            u(12);
        }
        f0 g1 = g1(extensionReceiverParameter, dispatchReceiverParameter, typeParameters, unsubstitutedValueParameters, unsubstitutedReturnType, modality, visibility, (Map<? extends a.C0348a<?>, ?>) null);
        if (g1 == null) {
            u(13);
        }
        return g1;
    }

    @NotNull
    public f0 g1(@Nullable l0 extensionReceiverParameter, @Nullable l0 dispatchReceiverParameter, @NotNull List<? extends t0> typeParameters, @NotNull List<w0> unsubstitutedValueParameters, @Nullable b0 unsubstitutedReturnType, @Nullable w modality, @NotNull a1 visibility, @Nullable Map<? extends a.C0348a<?>, ?> userData) {
        if (typeParameters == null) {
            u(14);
        }
        if (unsubstitutedValueParameters == null) {
            u(15);
        }
        if (visibility == null) {
            u(16);
        }
        super.J0(extensionReceiverParameter, dispatchReceiverParameter, typeParameters, unsubstitutedValueParameters, unsubstitutedReturnType, modality, visibility);
        if (userData != null && !userData.isEmpty()) {
            this.N4 = new LinkedHashMap(userData);
        }
        return this;
    }

    @NotNull
    /* renamed from: e1 */
    public n0 c0() {
        n0 n0Var = (n0) super.c0();
        if (n0Var == null) {
            u(18);
        }
        return n0Var;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public p A0(@NotNull m newOwner, @Nullable u original, @NotNull b.a kind, @Nullable f newName, @NotNull g annotations, @NotNull o0 source) {
        if (newOwner == null) {
            u(19);
        }
        if (kind == null) {
            u(20);
        }
        if (annotations == null) {
            u(21);
        }
        if (source == null) {
            u(22);
        }
        return new f0(newOwner, (n0) original, annotations, newName != null ? newName : getName(), kind, source);
    }

    @NotNull
    /* renamed from: c1 */
    public n0 l0(m newOwner, w modality, a1 visibility, b.a kind, boolean copyOverrides) {
        n0 n0Var = (n0) super.B0(newOwner, modality, visibility, kind, copyOverrides);
        if (n0Var == null) {
            u(23);
        }
        return n0Var;
    }

    @NotNull
    public u.a<? extends n0> r() {
        u.a<? extends u> r = super.r();
        if (r == null) {
            u(24);
        }
        return r;
    }
}
