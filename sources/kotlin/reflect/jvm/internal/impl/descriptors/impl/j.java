package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import com.meituan.robust.Constants;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.b;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.renderer.c;
import org.jetbrains.annotations.NotNull;

/* compiled from: DeclarationDescriptorImpl */
public abstract class j extends b implements m {
    @NotNull
    private final f d;

    private static /* synthetic */ void u(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 2:
            case 3:
            case 5:
            case 6:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 2:
            case 3:
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
                objArr[0] = "name";
                break;
            case 2:
            case 3:
            case 5:
            case 6:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/DeclarationDescriptorImpl";
                break;
            case 4:
                objArr[0] = "descriptor";
                break;
            default:
                objArr[0] = "annotations";
                break;
        }
        switch (i) {
            case 2:
                objArr[1] = "getName";
                break;
            case 3:
                objArr[1] = "getOriginal";
                break;
            case 5:
            case 6:
                objArr[1] = "toString";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/DeclarationDescriptorImpl";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 5:
            case 6:
                break;
            case 4:
                objArr[2] = "toString";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 2:
            case 3:
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
    public j(@NotNull g annotations, @NotNull f name) {
        super(annotations);
        if (annotations == null) {
            u(0);
        }
        if (name == null) {
            u(1);
        }
        this.d = name;
    }

    @NotNull
    public f getName() {
        f fVar = this.d;
        if (fVar == null) {
            u(2);
        }
        return fVar;
    }

    @NotNull
    public m a() {
        return this;
    }

    public String toString() {
        return a0(this);
    }

    @NotNull
    public static String a0(@NotNull m descriptor) {
        if (descriptor == null) {
            u(4);
        }
        try {
            String str = c.i.r(descriptor) + Constants.ARRAY_TYPE + descriptor.getClass().getSimpleName() + "@" + Integer.toHexString(System.identityHashCode(descriptor)) + "]";
            if (str == null) {
                u(5);
            }
            return str;
        } catch (Throwable th) {
            String str2 = descriptor.getClass().getSimpleName() + " " + descriptor.getName();
            if (str2 == null) {
                u(6);
            }
            return str2;
        }
    }
}
