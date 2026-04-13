package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.r0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.h1;
import org.jetbrains.annotations.NotNull;

/* compiled from: AbstractLazyTypeParameterDescriptor */
public abstract class b extends e {
    private static /* synthetic */ void u(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "containingDeclaration";
                break;
            case 2:
                objArr[0] = "name";
                break;
            case 3:
                objArr[0] = "variance";
                break;
            case 4:
                objArr[0] = "source";
                break;
            case 5:
                objArr[0] = "supertypeLoopChecker";
                break;
            default:
                objArr[0] = "storageManager";
                break;
        }
        objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/AbstractLazyTypeParameterDescriptor";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public b(@NotNull j storageManager, @NotNull m containingDeclaration, @NotNull f name, @NotNull h1 variance, boolean isReified, int index, @NotNull o0 source, @NotNull r0 supertypeLoopChecker) {
        super(storageManager, containingDeclaration, g.b.b(), name, variance, isReified, index, source, supertypeLoopChecker);
        if (storageManager == null) {
            u(0);
        }
        if (containingDeclaration == null) {
            u(1);
        }
        if (name == null) {
            u(2);
        }
        if (variance == null) {
            u(3);
        }
        if (source == null) {
            u(4);
        }
        if (supertypeLoopChecker == null) {
            u(5);
        }
    }

    public String toString() {
        Object[] objArr = new Object[3];
        String str = "";
        objArr[0] = t() ? "reified " : str;
        if (y() != h1.INVARIANT) {
            str = y() + " ";
        }
        objArr[1] = str;
        objArr[2] = getName();
        return String.format("%s%s%s", objArr);
    }
}
