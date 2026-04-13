package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.checker.g;
import kotlin.reflect.jvm.internal.impl.types.u0;
import org.jetbrains.annotations.NotNull;

/* compiled from: KotlinTypeCheckerImpl */
public class h implements g {
    private final v c;

    private static /* synthetic */ void e(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "procedure";
                break;
            case 2:
                objArr[0] = "subtype";
                break;
            case 3:
                objArr[0] = "supertype";
                break;
            case 4:
                objArr[0] = "a";
                break;
            case 5:
                objArr[0] = "b";
                break;
            default:
                objArr[0] = "equalityAxioms";
                break;
        }
        objArr[1] = "kotlin/reflect/jvm/internal/impl/types/checker/KotlinTypeCheckerImpl";
        switch (i) {
            case 1:
                objArr[2] = "<init>";
                break;
            case 2:
            case 3:
                objArr[2] = "isSubtypeOf";
                break;
            case 4:
            case 5:
                objArr[2] = "equalTypes";
                break;
            default:
                objArr[2] = "withAxioms";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* compiled from: KotlinTypeCheckerImpl */
    public static final class a extends u {
        final /* synthetic */ g.a a;

        private static /* synthetic */ void f(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "constructor2";
                    break;
                default:
                    objArr[0] = "constructor1";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/types/checker/KotlinTypeCheckerImpl$1";
            objArr[2] = "assertEqualTypeConstructors";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        a(g.a aVar) {
            this.a = aVar;
        }

        public boolean c(@NotNull u0 constructor1, @NotNull u0 constructor2) {
            if (constructor1 == null) {
                f(0);
            }
            if (constructor2 == null) {
                f(1);
            }
            return constructor1.equals(constructor2) || this.a.a(constructor1, constructor2);
        }
    }

    @NotNull
    public static g f(@NotNull g.a equalityAxioms) {
        if (equalityAxioms == null) {
            e(0);
        }
        return new h(new v(new a(equalityAxioms)));
    }

    protected h(@NotNull v procedure) {
        if (procedure == null) {
            e(1);
        }
        this.c = procedure;
    }

    public boolean d(@NotNull b0 subtype, @NotNull b0 supertype) {
        if (subtype == null) {
            e(2);
        }
        if (supertype == null) {
            e(3);
        }
        return this.c.k(subtype, supertype);
    }

    public boolean b(@NotNull b0 a2, @NotNull b0 b) {
        if (a2 == null) {
            e(4);
        }
        if (b == null) {
            e(5);
        }
        return this.c.d(a2, b);
    }
}
