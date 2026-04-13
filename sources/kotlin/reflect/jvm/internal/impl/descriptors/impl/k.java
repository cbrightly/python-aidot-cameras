package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.n;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.p;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: DeclarationDescriptorNonRootImpl */
public abstract class k extends j implements n {
    @NotNull
    private final m f;
    @NotNull
    private final o0 q;

    private static /* synthetic */ void u(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 4:
            case 5:
            case 6:
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
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/DeclarationDescriptorNonRootImpl";
                break;
            default:
                objArr[0] = "containingDeclaration";
                break;
        }
        switch (i) {
            case 4:
                objArr[1] = "getOriginal";
                break;
            case 5:
                objArr[1] = "getContainingDeclaration";
                break;
            case 6:
                objArr[1] = "getSource";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/DeclarationDescriptorNonRootImpl";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
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
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    protected k(@NotNull m containingDeclaration, @NotNull g annotations, @NotNull f name, @NotNull o0 source) {
        super(annotations, name);
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
        this.f = containingDeclaration;
        this.q = source;
    }

    @NotNull
    /* renamed from: c0 */
    public p a() {
        p pVar = (p) super.a();
        if (pVar == null) {
            u(4);
        }
        return pVar;
    }

    @NotNull
    public m b() {
        m mVar = this.f;
        if (mVar == null) {
            u(5);
        }
        return mVar;
    }

    @NotNull
    public o0 n() {
        o0 o0Var = this.q;
        if (o0Var == null) {
            u(6);
        }
        return o0Var;
    }
}
