package kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers;

import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AbstractReceiverValue */
public abstract class a implements d {
    protected final b0 a;
    private final d b;

    private static /* synthetic */ void b(int i) {
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
                objArr[0] = "kotlin/reflect/jvm/internal/impl/resolve/scopes/receivers/AbstractReceiverValue";
                break;
            default:
                objArr[0] = "receiverType";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "getType";
                break;
            case 2:
                objArr[1] = "getOriginal";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/resolve/scopes/receivers/AbstractReceiverValue";
                break;
        }
        switch (i) {
            case 1:
            case 2:
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

    public a(@NotNull b0 receiverType, @Nullable d original) {
        if (receiverType == null) {
            b(0);
        }
        this.a = receiverType;
        this.b = original != null ? original : this;
    }

    @NotNull
    public b0 getType() {
        b0 b0Var = this.a;
        if (b0Var == null) {
            b(1);
        }
        return b0Var;
    }
}
