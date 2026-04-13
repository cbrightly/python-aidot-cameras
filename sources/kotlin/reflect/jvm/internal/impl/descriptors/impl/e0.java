package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d;
import org.jetbrains.annotations.NotNull;

/* compiled from: ReceiverParameterDescriptorImpl */
public class e0 extends c {
    private final m q;
    private final d x;

    private static /* synthetic */ void u(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 3:
            case 4:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 3:
            case 4:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "value";
                break;
            case 2:
                objArr[0] = "annotations";
                break;
            case 3:
            case 4:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/ReceiverParameterDescriptorImpl";
                break;
            case 5:
                objArr[0] = "newOwner";
                break;
            default:
                objArr[0] = "containingDeclaration";
                break;
        }
        switch (i) {
            case 3:
                objArr[1] = "getValue";
                break;
            case 4:
                objArr[1] = "getContainingDeclaration";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/ReceiverParameterDescriptorImpl";
                break;
        }
        switch (i) {
            case 3:
            case 4:
                break;
            case 5:
                objArr[2] = "copy";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 3:
            case 4:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public e0(@NotNull m containingDeclaration, @NotNull d value, @NotNull g annotations) {
        super(annotations);
        if (containingDeclaration == null) {
            u(0);
        }
        if (value == null) {
            u(1);
        }
        if (annotations == null) {
            u(2);
        }
        this.q = containingDeclaration;
        this.x = value;
    }

    @NotNull
    public d getValue() {
        d dVar = this.x;
        if (dVar == null) {
            u(3);
        }
        return dVar;
    }

    @NotNull
    public m b() {
        m mVar = this.q;
        if (mVar == null) {
            u(4);
        }
        return mVar;
    }
}
