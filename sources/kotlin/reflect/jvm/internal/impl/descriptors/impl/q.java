package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.c;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d;
import org.jetbrains.annotations.NotNull;

/* compiled from: LazyClassReceiverParameterDescriptor */
public class q extends c {
    private final e q;
    private final c x;

    private static /* synthetic */ void u(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 1:
            case 2:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/LazyClassReceiverParameterDescriptor";
                break;
            case 3:
                objArr[0] = "newOwner";
                break;
            default:
                objArr[0] = "descriptor";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "getValue";
                break;
            case 2:
                objArr[1] = "getContainingDeclaration";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/LazyClassReceiverParameterDescriptor";
                break;
        }
        switch (i) {
            case 1:
            case 2:
                break;
            case 3:
                objArr[2] = "copy";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public q(@NotNull e descriptor) {
        super(g.b.b());
        if (descriptor == null) {
            u(0);
        }
        this.q = descriptor;
        this.x = new c(descriptor, (c) null);
    }

    @NotNull
    public d getValue() {
        c cVar = this.x;
        if (cVar == null) {
            u(1);
        }
        return cVar;
    }

    @NotNull
    public m b() {
        e eVar = this.q;
        if (eVar == null) {
            u(2);
        }
        return eVar;
    }

    public String toString() {
        return "class " + this.q.getName() + "::this";
    }
}
