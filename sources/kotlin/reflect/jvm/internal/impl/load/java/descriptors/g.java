package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import java.util.List;
import kotlin.n;
import kotlin.reflect.jvm.internal.impl.descriptors.a;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.b0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.c0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.d0;
import kotlin.reflect.jvm.internal.impl.descriptors.j;
import kotlin.reflect.jvm.internal.impl.descriptors.k0;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.t;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JavaPropertyDescriptor */
public class g extends b0 implements b {
    private final boolean L4;
    @Nullable
    private final n<a.C0348a<?>, ?> M4;

    private static /* synthetic */ void u(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 21:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 21:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 8:
                objArr[0] = "annotations";
                break;
            case 2:
            case 9:
                objArr[0] = "modality";
                break;
            case 3:
            case 10:
                objArr[0] = "visibility";
                break;
            case 4:
            case 11:
                objArr[0] = "name";
                break;
            case 5:
            case 12:
            case 18:
                objArr[0] = "source";
                break;
            case 6:
            case 16:
                objArr[0] = "kind";
                break;
            case 13:
                objArr[0] = "newOwner";
                break;
            case 14:
                objArr[0] = "newModality";
                break;
            case 15:
                objArr[0] = "newVisibility";
                break;
            case 17:
                objArr[0] = "newName";
                break;
            case 19:
                objArr[0] = "enhancedValueParametersData";
                break;
            case 20:
                objArr[0] = "enhancedReturnType";
                break;
            case 21:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/load/java/descriptors/JavaPropertyDescriptor";
                break;
            default:
                objArr[0] = "containingDeclaration";
                break;
        }
        switch (i) {
            case 21:
                objArr[1] = "enhance";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/load/java/descriptors/JavaPropertyDescriptor";
                break;
        }
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                objArr[2] = "create";
                break;
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                objArr[2] = "createSubstitutedCopy";
                break;
            case 19:
            case 20:
                objArr[2] = "enhance";
                break;
            case 21:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 21:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    protected g(@NotNull m containingDeclaration, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.g annotations, @NotNull w modality, @NotNull a1 visibility, boolean isVar, @NotNull f name, @NotNull o0 source, @Nullable i0 original, @NotNull b.a kind, boolean isStaticFinal, @Nullable n<a.C0348a<?>, ?> singleUserData) {
        super(containingDeclaration, original, annotations, modality, visibility, isVar, name, kind, source, false, false, false, false, false, false);
        if (containingDeclaration == null) {
            u(0);
        }
        if (annotations == null) {
            u(1);
        }
        if (modality == null) {
            u(2);
        }
        if (visibility == null) {
            u(3);
        }
        if (name == null) {
            u(4);
        }
        if (source == null) {
            u(5);
        }
        if (kind == null) {
            u(6);
        }
        this.L4 = isStaticFinal;
        this.M4 = singleUserData;
    }

    @NotNull
    public static g U0(@NotNull m containingDeclaration, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.g annotations, @NotNull w modality, @NotNull a1 visibility, boolean isVar, @NotNull f name, @NotNull o0 source, boolean isStaticFinal) {
        if (containingDeclaration == null) {
            u(7);
        }
        if (annotations == null) {
            u(8);
        }
        if (modality == null) {
            u(9);
        }
        if (visibility == null) {
            u(10);
        }
        if (name == null) {
            u(11);
        }
        if (source == null) {
            u(12);
        }
        return new g(containingDeclaration, annotations, modality, visibility, isVar, name, source, (i0) null, b.a.DECLARATION, isStaticFinal, (n<a.C0348a<?>, ?>) null);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public b0 H0(@NotNull m newOwner, @NotNull w newModality, @NotNull a1 newVisibility, @Nullable i0 original, @NotNull b.a kind, @NotNull f newName, @NotNull o0 source) {
        if (newOwner == null) {
            u(13);
        }
        if (newModality == null) {
            u(14);
        }
        if (newVisibility == null) {
            u(15);
        }
        if (kind == null) {
            u(16);
        }
        if (newName == null) {
            u(17);
        }
        if (source == null) {
            u(18);
        }
        return new g(newOwner, getAnnotations(), newModality, newVisibility, K(), newName, source, original, kind, this.L4, this.M4);
    }

    public boolean Z() {
        return false;
    }

    @NotNull
    public b U(@Nullable kotlin.reflect.jvm.internal.impl.types.b0 enhancedReceiverType, @NotNull List<l> enhancedValueParametersData, @NotNull kotlin.reflect.jvm.internal.impl.types.b0 enhancedReturnType, @Nullable n<a.C0348a<?>, ?> additionalUserData) {
        kotlin.reflect.jvm.internal.impl.types.b0 b0Var = enhancedReceiverType;
        kotlin.reflect.jvm.internal.impl.types.b0 b0Var2 = enhancedReturnType;
        if (enhancedValueParametersData == null) {
            u(19);
        }
        if (b0Var2 == null) {
            u(20);
        }
        l0 enhancedReceiver = null;
        i0 enhancedOriginal = c0() == this ? null : c0();
        g enhanced = new g(b(), getAnnotations(), p(), getVisibility(), K(), getName(), n(), enhancedOriginal, h(), this.L4, additionalUserData);
        c0 newGetter = null;
        c0 getter = getGetter();
        if (getter != null) {
            newGetter = new c0(enhanced, getter.getAnnotations(), getter.p(), getter.getVisibility(), getter.D(), getter.isExternal(), getter.isInline(), h(), enhancedOriginal == null ? null : enhancedOriginal.getGetter(), getter.n());
            newGetter.G0(getter.o0());
            newGetter.J0(b0Var2);
        }
        d0 newSetter = null;
        k0 setter = getSetter();
        if (setter != null) {
            newSetter = new d0(enhanced, setter.getAnnotations(), setter.p(), setter.getVisibility(), setter.D(), setter.isExternal(), setter.isInline(), h(), enhancedOriginal == null ? null : enhancedOriginal.getSetter(), setter.n());
            newSetter.G0(newSetter.o0());
            newSetter.K0(setter.g().get(0));
        }
        enhanced.N0(newGetter, newSetter, s0(), M());
        enhanced.R0(O0());
        kotlin.reflect.jvm.internal.impl.storage.g<kotlin.reflect.jvm.internal.impl.resolve.constants.g<?>> gVar = this.z;
        if (gVar != null) {
            enhanced.l0(gVar);
        }
        enhanced.y0(d());
        if (b0Var != null) {
            enhancedReceiver = kotlin.reflect.jvm.internal.impl.resolve.b.f(this, b0Var, kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b.b());
        }
        enhanced.S0(b0Var2, getTypeParameters(), I(), enhancedReceiver);
        return enhanced;
    }

    public boolean isConst() {
        kotlin.reflect.jvm.internal.impl.types.b0 type = getType();
        return this.L4 && j.a(type) && (!t.i(type) || kotlin.reflect.jvm.internal.impl.builtins.g.G0(type));
    }
}
