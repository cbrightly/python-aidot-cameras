package kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers;

import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: TransientReceiver */
public class g extends a {
    private static /* synthetic */ void b(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 2:
                objArr[0] = "newType";
                break;
            default:
                objArr[0] = IjkMediaMeta.IJKM_KEY_TYPE;
                break;
        }
        objArr[1] = "kotlin/reflect/jvm/internal/impl/resolve/scopes/receivers/TransientReceiver";
        switch (i) {
            case 2:
                objArr[2] = "replaceType";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public g(@NotNull b0 type) {
        this(type, (d) null);
        if (type == null) {
            b(0);
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private g(@NotNull b0 type, @Nullable d original) {
        super(type, original);
        if (type == null) {
            b(1);
        }
    }

    public String toString() {
        return "{Transient} : " + getType();
    }
}
