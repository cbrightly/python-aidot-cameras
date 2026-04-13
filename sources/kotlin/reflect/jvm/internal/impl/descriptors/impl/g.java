package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.storage.j;
import org.jetbrains.annotations.NotNull;

/* compiled from: ClassDescriptorBase */
public abstract class g extends a {
    private final boolean p0;
    private final m y;
    private final o0 z;

    private static /* synthetic */ void c0(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 4:
            case 5:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 4:
            case 5:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "containingDeclaration";
                break;
            case 2:
                objArr[0] = "name";
                break;
            case 3:
                objArr[0] = "source";
                break;
            case 4:
            case 5:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/ClassDescriptorBase";
                break;
            default:
                objArr[0] = "storageManager";
                break;
        }
        switch (i) {
            case 4:
                objArr[1] = "getContainingDeclaration";
                break;
            case 5:
                objArr[1] = "getSource";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/ClassDescriptorBase";
                break;
        }
        switch (i) {
            case 4:
            case 5:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 4:
            case 5:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    protected g(@NotNull j storageManager, @NotNull m containingDeclaration, @NotNull f name, @NotNull o0 source, boolean isExternal) {
        super(storageManager, name);
        if (storageManager == null) {
            c0(0);
        }
        if (containingDeclaration == null) {
            c0(1);
        }
        if (name == null) {
            c0(2);
        }
        if (source == null) {
            c0(3);
        }
        this.y = containingDeclaration;
        this.z = source;
        this.p0 = isExternal;
    }

    public boolean isExternal() {
        return this.p0;
    }

    @NotNull
    public m b() {
        m mVar = this.y;
        if (mVar == null) {
            c0(4);
        }
        return mVar;
    }

    @NotNull
    public o0 n() {
        o0 o0Var = this.z;
        if (o0Var == null) {
            c0(5);
        }
        return o0Var;
    }
}
