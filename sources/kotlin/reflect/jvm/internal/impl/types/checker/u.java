package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.types.w0;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: TypeCheckerProcedureCallbacksImpl */
public class u implements w {
    private static /* synthetic */ void f(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 4:
                objArr[0] = "b";
                break;
            case 2:
            case 7:
                objArr[0] = "typeCheckingProcedure";
                break;
            case 5:
            case 10:
                objArr[0] = "subtype";
                break;
            case 6:
            case 11:
                objArr[0] = "supertype";
                break;
            case 8:
                objArr[0] = IjkMediaMeta.IJKM_KEY_TYPE;
                break;
            case 9:
                objArr[0] = "typeProjection";
                break;
            default:
                objArr[0] = "a";
                break;
        }
        objArr[1] = "kotlin/reflect/jvm/internal/impl/types/checker/TypeCheckerProcedureCallbacksImpl";
        switch (i) {
            case 3:
            case 4:
                objArr[2] = "assertEqualTypeConstructors";
                break;
            case 5:
            case 6:
            case 7:
                objArr[2] = "assertSubtype";
                break;
            case 8:
            case 9:
                objArr[2] = "capture";
                break;
            case 10:
            case 11:
                objArr[2] = "noCorrespondingSupertype";
                break;
            default:
                objArr[2] = "assertEqualTypes";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    u() {
    }

    public boolean e(@NotNull b0 a, @NotNull b0 b, @NotNull v typeCheckingProcedure) {
        if (a == null) {
            f(0);
        }
        if (b == null) {
            f(1);
        }
        if (typeCheckingProcedure == null) {
            f(2);
        }
        return typeCheckingProcedure.d(a, b);
    }

    public boolean c(@NotNull u0 a, @NotNull u0 b) {
        if (a == null) {
            f(3);
        }
        if (b == null) {
            f(4);
        }
        return a.equals(b);
    }

    public boolean a(@NotNull b0 subtype, @NotNull b0 supertype, @NotNull v typeCheckingProcedure) {
        if (subtype == null) {
            f(5);
        }
        if (supertype == null) {
            f(6);
        }
        if (typeCheckingProcedure == null) {
            f(7);
        }
        return typeCheckingProcedure.k(subtype, supertype);
    }

    public boolean b(@NotNull b0 type, @NotNull w0 typeProjection) {
        if (type == null) {
            f(8);
        }
        if (typeProjection != null) {
            return false;
        }
        f(9);
        return false;
    }

    public boolean d(@NotNull b0 subtype, @NotNull b0 supertype) {
        if (subtype == null) {
            f(10);
        }
        if (supertype != null) {
            return false;
        }
        f(11);
        return false;
    }
}
