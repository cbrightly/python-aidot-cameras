package kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers;

import kotlin.reflect.jvm.internal.impl.descriptors.a;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ExtensionReceiver */
public class b extends a implements d {
    private final a c;

    private static /* synthetic */ void b(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 2:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
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
                objArr[0] = "receiverType";
                break;
            case 2:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/resolve/scopes/receivers/ExtensionReceiver";
                break;
            case 3:
                objArr[0] = "newType";
                break;
            default:
                objArr[0] = "callableDescriptor";
                break;
        }
        switch (i) {
            case 2:
                objArr[1] = "getDeclarationDescriptor";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/resolve/scopes/receivers/ExtensionReceiver";
                break;
        }
        switch (i) {
            case 2:
                break;
            case 3:
                objArr[2] = "replaceType";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
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
    public b(@NotNull a callableDescriptor, @NotNull b0 receiverType, @Nullable d original) {
        super(receiverType, original);
        if (callableDescriptor == null) {
            b(0);
        }
        if (receiverType == null) {
            b(1);
        }
        this.c = callableDescriptor;
    }

    public String toString() {
        return getType() + ": Ext {" + this.c + "}";
    }
}
