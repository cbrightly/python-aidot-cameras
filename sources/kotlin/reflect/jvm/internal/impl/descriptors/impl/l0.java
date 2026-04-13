package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.descriptors.x0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: VariableDescriptorImpl */
public abstract class l0 extends k implements x0 {
    protected b0 x;

    private static /* synthetic */ void u(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
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
                objArr[0] = "name";
                break;
            case 3:
                objArr[0] = "source";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/VariableDescriptorImpl";
                break;
            default:
                objArr[0] = "containingDeclaration";
                break;
        }
        switch (i) {
            case 4:
                objArr[1] = "getType";
                break;
            case 5:
                objArr[1] = "getOriginal";
                break;
            case 6:
                objArr[1] = "getValueParameters";
                break;
            case 7:
                objArr[1] = "getOverriddenDescriptors";
                break;
            case 8:
                objArr[1] = "getTypeParameters";
                break;
            case 9:
                objArr[1] = "getReturnType";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/VariableDescriptorImpl";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public l0(@NotNull m containingDeclaration, @NotNull g annotations, @NotNull f name, @Nullable b0 outType, @NotNull o0 source) {
        super(containingDeclaration, annotations, name, source);
        if (containingDeclaration == null) {
            u(0);
        }
        if (annotations == null) {
            u(1);
        }
        if (name == null) {
            u(2);
        }
        if (source == null) {
            u(3);
        }
        this.x = outType;
    }

    @NotNull
    public b0 getType() {
        b0 b0Var = this.x;
        if (b0Var == null) {
            u(4);
        }
        return b0Var;
    }

    public void f0(b0 outType) {
        if (this.x == null) {
            this.x = outType;
            return;
        }
        throw new AssertionError();
    }

    @NotNull
    public List<w0> g() {
        List<w0> emptyList = Collections.emptyList();
        if (emptyList == null) {
            u(6);
        }
        return emptyList;
    }

    public boolean Z() {
        return false;
    }

    @NotNull
    public List<t0> getTypeParameters() {
        List<t0> emptyList = Collections.emptyList();
        if (emptyList == null) {
            u(8);
        }
        return emptyList;
    }

    public kotlin.reflect.jvm.internal.impl.descriptors.l0 L() {
        return null;
    }

    public kotlin.reflect.jvm.internal.impl.descriptors.l0 I() {
        return null;
    }

    @NotNull
    public b0 getReturnType() {
        b0 type = getType();
        if (type == null) {
            u(9);
        }
        return type;
    }
}
