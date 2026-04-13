package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import java.util.List;
import kotlin.n;
import kotlin.reflect.jvm.internal.impl.descriptors.a;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.f;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JavaClassConstructorDescriptor */
public class c extends f implements b {
    private Boolean Q4;
    private Boolean R4;

    private static /* synthetic */ void u(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 11:
            case 18:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 11:
            case 18:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 5:
            case 9:
            case 15:
                objArr[0] = "annotations";
                break;
            case 2:
            case 8:
            case 13:
                objArr[0] = "kind";
                break;
            case 3:
            case 6:
            case 10:
                objArr[0] = "source";
                break;
            case 7:
            case 12:
                objArr[0] = "newOwner";
                break;
            case 11:
            case 18:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/load/java/descriptors/JavaClassConstructorDescriptor";
                break;
            case 14:
                objArr[0] = "sourceElement";
                break;
            case 16:
                objArr[0] = "enhancedValueParametersData";
                break;
            case 17:
                objArr[0] = "enhancedReturnType";
                break;
            default:
                objArr[0] = "containingDeclaration";
                break;
        }
        switch (i) {
            case 11:
                objArr[1] = "createSubstitutedCopy";
                break;
            case 18:
                objArr[1] = "enhance";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/load/java/descriptors/JavaClassConstructorDescriptor";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
                objArr[2] = "createJavaConstructor";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[2] = "createSubstitutedCopy";
                break;
            case 11:
            case 18:
                break;
            case 12:
            case 13:
            case 14:
            case 15:
                objArr[2] = "createDescriptor";
                break;
            case 16:
            case 17:
                objArr[2] = "enhance";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 11:
            case 18:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    protected c(@NotNull e containingDeclaration, @Nullable c original, @NotNull g annotations, boolean isPrimary, @NotNull b.a kind, @NotNull o0 source) {
        super(containingDeclaration, original, annotations, isPrimary, kind, source);
        if (containingDeclaration == null) {
            u(0);
        }
        if (annotations == null) {
            u(1);
        }
        if (kind == null) {
            u(2);
        }
        if (source == null) {
            u(3);
        }
        this.Q4 = null;
        this.R4 = null;
    }

    @NotNull
    public static c k1(@NotNull e containingDeclaration, @NotNull g annotations, boolean isPrimary, @NotNull o0 source) {
        if (containingDeclaration == null) {
            u(4);
        }
        if (annotations == null) {
            u(5);
        }
        if (source == null) {
            u(6);
        }
        return new c(containingDeclaration, (c) null, annotations, isPrimary, b.a.DECLARATION, source);
    }

    public boolean n1() {
        Boolean bool = this.Q4;
        if (bool != null) {
            return bool.booleanValue();
        }
        throw new AssertionError("hasStableParameterNames was not set: " + this);
    }

    public void Q0(boolean hasStableParameterNames) {
        this.Q4 = Boolean.valueOf(hasStableParameterNames);
    }

    public boolean Z() {
        Boolean bool = this.R4;
        if (bool != null) {
            return bool.booleanValue();
        }
        throw new AssertionError("hasSynthesizedParameterNames was not set: " + this);
    }

    public void R0(boolean hasSynthesizedParameterNames) {
        this.R4 = Boolean.valueOf(hasSynthesizedParameterNames);
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: l1 */
    public c f1(@NotNull m newOwner, @Nullable u original, @NotNull b.a kind, @Nullable kotlin.reflect.jvm.internal.impl.name.f newName, @NotNull g annotations, @NotNull o0 source) {
        if (newOwner == null) {
            u(7);
        }
        if (kind == null) {
            u(8);
        }
        if (annotations == null) {
            u(9);
        }
        if (source == null) {
            u(10);
        }
        if (kind != b.a.DECLARATION && kind != b.a.SYNTHESIZED) {
            throw new IllegalStateException("Attempt at creating a constructor that is not a declaration: \ncopy from: " + this + "\n" + "newOwner: " + newOwner + "\n" + "kind: " + kind);
        } else if (newName == null) {
            c result = j1((e) newOwner, (c) original, kind, source, annotations);
            result.Q0(n1());
            result.R0(Z());
            return result;
        } else {
            throw new AssertionError("Attempt to rename constructor: " + this);
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    public c j1(@NotNull e newOwner, @Nullable c original, @NotNull b.a kind, @NotNull o0 sourceElement, @NotNull g annotations) {
        if (newOwner == null) {
            u(12);
        }
        if (kind == null) {
            u(13);
        }
        if (sourceElement == null) {
            u(14);
        }
        if (annotations == null) {
            u(15);
        }
        return new c(newOwner, original, annotations, this.P4, kind, sourceElement);
    }

    @NotNull
    /* renamed from: m1 */
    public c U(@Nullable b0 enhancedReceiverType, @NotNull List<l> enhancedValueParametersData, @NotNull b0 enhancedReturnType, @Nullable n<a.C0348a<?>, ?> additionalUserData) {
        if (enhancedValueParametersData == null) {
            u(16);
        }
        if (enhancedReturnType == null) {
            u(17);
        }
        c enhanced = f1(b(), (u) null, h(), (kotlin.reflect.jvm.internal.impl.name.f) null, getAnnotations(), n());
        enhanced.J0(enhancedReceiverType == null ? null : kotlin.reflect.jvm.internal.impl.resolve.b.f(enhanced, enhancedReceiverType, g.b.b()), I(), getTypeParameters(), k.a(enhancedValueParametersData, g(), enhanced), enhancedReturnType, p(), getVisibility());
        if (additionalUserData != null) {
            enhanced.M0(additionalUserData.getFirst(), additionalUserData.getSecond());
        }
        return enhanced;
    }
}
