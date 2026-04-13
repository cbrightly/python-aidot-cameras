package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.storage.g;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: VariableDescriptorWithInitializerImpl */
public abstract class m0 extends l0 {
    private final boolean y;
    protected g<kotlin.reflect.jvm.internal.impl.resolve.constants.g<?>> z;

    private static /* synthetic */ void u(int i) {
        Object[] objArr = new Object[3];
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
                objArr[0] = "compileTimeInitializer";
                break;
            default:
                objArr[0] = "containingDeclaration";
                break;
        }
        objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/VariableDescriptorWithInitializerImpl";
        switch (i) {
            case 4:
                objArr[2] = "setCompileTimeInitializer";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public m0(@NotNull m containingDeclaration, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.g annotations, @NotNull f name, @Nullable b0 outType, boolean isVar, @NotNull o0 source) {
        super(containingDeclaration, annotations, name, outType, source);
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
        this.y = isVar;
    }

    public boolean K() {
        return this.y;
    }

    @Nullable
    public kotlin.reflect.jvm.internal.impl.resolve.constants.g<?> j0() {
        g<kotlin.reflect.jvm.internal.impl.resolve.constants.g<?>> gVar = this.z;
        if (gVar != null) {
            return (kotlin.reflect.jvm.internal.impl.resolve.constants.g) gVar.invoke();
        }
        return null;
    }

    public void l0(@NotNull g<kotlin.reflect.jvm.internal.impl.resolve.constants.g<?>> compileTimeInitializer) {
        if (compileTimeInitializer == null) {
            u(4);
        }
        if (!K()) {
            this.z = compileTimeInitializer;
            return;
        }
        throw new AssertionError("Constant value for variable initializer should be recorded only for final variables: " + getName());
    }
}
