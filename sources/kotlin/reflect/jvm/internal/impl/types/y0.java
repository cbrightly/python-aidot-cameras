package kotlin.reflect.jvm.internal.impl.types;

import kotlin.reflect.jvm.internal.impl.types.checker.i;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: TypeProjectionImpl */
public class y0 extends x0 {
    private final h1 a;
    private final b0 b;

    private static /* synthetic */ void d(int i) {
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
            case 2:
                objArr[0] = IjkMediaMeta.IJKM_KEY_TYPE;
                break;
            case 3:
            case 4:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/types/TypeProjectionImpl";
                break;
            case 5:
                objArr[0] = "kotlinTypeRefiner";
                break;
            default:
                objArr[0] = "projection";
                break;
        }
        switch (i) {
            case 3:
                objArr[1] = "getProjectionKind";
                break;
            case 4:
                objArr[1] = "getType";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/types/TypeProjectionImpl";
                break;
        }
        switch (i) {
            case 3:
            case 4:
                break;
            case 5:
                objArr[2] = "refine";
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

    public y0(@NotNull h1 projection, @NotNull b0 type) {
        if (projection == null) {
            d(0);
        }
        if (type == null) {
            d(1);
        }
        this.a = projection;
        this.b = type;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public y0(@NotNull b0 type) {
        this(h1.INVARIANT, type);
        if (type == null) {
            d(2);
        }
    }

    @NotNull
    public h1 c() {
        h1 h1Var = this.a;
        if (h1Var == null) {
            d(3);
        }
        return h1Var;
    }

    @NotNull
    public b0 getType() {
        b0 b0Var = this.b;
        if (b0Var == null) {
            d(4);
        }
        return b0Var;
    }

    public boolean b() {
        return false;
    }

    @NotNull
    public w0 a(@NotNull i kotlinTypeRefiner) {
        if (kotlinTypeRefiner == null) {
            d(5);
        }
        return new y0(this.a, kotlinTypeRefiner.g(this.b));
    }
}
