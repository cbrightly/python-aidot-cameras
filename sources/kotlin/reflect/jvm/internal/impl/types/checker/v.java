package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.reflect.jvm.internal.impl.types.d0;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.types.w0;
import kotlin.reflect.jvm.internal.impl.types.y;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TypeCheckingProcedure */
public class v {
    private final w a;

    private static /* synthetic */ void a(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 7:
            case 10:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 7:
            case 10:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
            case 16:
            case 18:
                objArr[0] = "supertype";
                break;
            case 4:
                objArr[0] = "typeCheckingProcedureCallbacks";
                break;
            case 5:
            case 8:
            case 21:
                objArr[0] = "parameter";
                break;
            case 6:
            case 9:
                objArr[0] = "argument";
                break;
            case 7:
            case 10:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/types/checker/TypeCheckingProcedure";
                break;
            case 11:
                objArr[0] = "type1";
                break;
            case 12:
                objArr[0] = "type2";
                break;
            case 13:
                objArr[0] = "typeParameter";
                break;
            case 14:
                objArr[0] = "typeArgument";
                break;
            case 19:
                objArr[0] = "subtypeArgumentProjection";
                break;
            case 20:
                objArr[0] = "supertypeArgumentProjection";
                break;
            default:
                objArr[0] = "subtype";
                break;
        }
        switch (i) {
            case 7:
                objArr[1] = "getOutType";
                break;
            case 10:
                objArr[1] = "getInType";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/types/checker/TypeCheckingProcedure";
                break;
        }
        switch (i) {
            case 5:
            case 6:
                objArr[2] = "getOutType";
                break;
            case 7:
            case 10:
                break;
            case 8:
            case 9:
                objArr[2] = "getInType";
                break;
            case 11:
            case 12:
                objArr[2] = "equalTypes";
                break;
            case 13:
            case 14:
                objArr[2] = "getEffectiveProjectionKind";
                break;
            case 15:
            case 16:
                objArr[2] = "isSubtypeOf";
                break;
            case 17:
            case 18:
                objArr[2] = "checkSubtypeForTheSameConstructor";
                break;
            case 19:
            case 20:
            case 21:
                objArr[2] = "capture";
                break;
            default:
                objArr[2] = "findCorrespondingSupertype";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 7:
            case 10:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    @Nullable
    public static b0 e(@NotNull b0 subtype, @NotNull b0 supertype) {
        if (subtype == null) {
            a(0);
        }
        if (supertype == null) {
            a(1);
        }
        return f(subtype, supertype, new u());
    }

    @Nullable
    public static b0 f(@NotNull b0 subtype, @NotNull b0 supertype, @NotNull w typeCheckingProcedureCallbacks) {
        if (subtype == null) {
            a(2);
        }
        if (supertype == null) {
            a(3);
        }
        if (typeCheckingProcedureCallbacks == null) {
            a(4);
        }
        return y.c(subtype, supertype, typeCheckingProcedureCallbacks);
    }

    @NotNull
    private static b0 i(@NotNull t0 parameter, @NotNull w0 argument) {
        if (parameter == null) {
            a(5);
        }
        if (argument == null) {
            a(6);
        }
        h1 c = argument.c();
        h1 h1Var = h1.IN_VARIANCE;
        b0 K = c == h1Var || parameter.y() == h1Var ? kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.h(parameter).K() : argument.getType();
        if (K == null) {
            a(7);
        }
        return K;
    }

    @NotNull
    private static b0 h(@NotNull t0 parameter, @NotNull w0 argument) {
        if (parameter == null) {
            a(8);
        }
        if (argument == null) {
            a(9);
        }
        h1 c = argument.c();
        h1 h1Var = h1.OUT_VARIANCE;
        b0 J = c == h1Var || parameter.y() == h1Var ? kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.h(parameter).J() : argument.getType();
        if (J == null) {
            a(10);
        }
        return J;
    }

    public v(w constraints) {
        this.a = constraints;
    }

    public boolean d(@NotNull b0 type1, @NotNull b0 type2) {
        b0 b0Var = type1;
        b0 b0Var2 = type2;
        if (b0Var == null) {
            a(11);
        }
        if (b0Var2 == null) {
            a(12);
        }
        if (b0Var == b0Var2) {
            return true;
        }
        if (y.b(type1)) {
            if (!y.b(type2)) {
                return j(b0Var2, b0Var);
            }
            if (d0.a(type1) || d0.a(type2) || !k(type1, type2) || !k(b0Var2, b0Var)) {
                return false;
            }
            return true;
        } else if (y.b(type2)) {
            return j(type1, type2);
        } else {
            if (type1.J0() != type2.J0()) {
                return false;
            }
            if (type1.J0()) {
                return this.a.e(c1.n(type1), c1.n(type2), this);
            }
            u0 constructor1 = type1.I0();
            u0 constructor2 = type2.I0();
            if (!this.a.c(constructor1, constructor2)) {
                return false;
            }
            List<w0> H0 = type1.H0();
            List<w0> H02 = type2.H0();
            if (H0.size() != H02.size()) {
                return false;
            }
            for (int i = 0; i < H0.size(); i++) {
                w0 typeProjection1 = H0.get(i);
                w0 typeProjection2 = H02.get(i);
                if (!typeProjection1.b() || !typeProjection2.b()) {
                    t0 typeParameter1 = constructor1.getParameters().get(i);
                    t0 typeParameter2 = constructor2.getParameters().get(i);
                    if (!b(typeProjection1, typeProjection2, typeParameter1) && (g(typeParameter1, typeProjection1) != g(typeParameter2, typeProjection2) || !this.a.e(typeProjection1.getType(), typeProjection2.getType(), this))) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public boolean j(b0 inflexibleType, b0 flexibleType) {
        if (!y.b(inflexibleType)) {
            return k(y.a(flexibleType).Q0(), inflexibleType) && k(inflexibleType, y.a(flexibleType).R0());
        }
        throw new AssertionError("Only inflexible types are allowed here: " + inflexibleType);
    }

    /* compiled from: TypeCheckingProcedure */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[h1.values().length];
            a = iArr;
            try {
                iArr[h1.INVARIANT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[h1.IN_VARIANCE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[h1.OUT_VARIANCE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* compiled from: TypeCheckingProcedure */
    public enum b {
        IN,
        OUT,
        INV,
        STAR;

        @NotNull
        public static b fromVariance(@NotNull h1 variance) {
            if (variance == null) {
                a(0);
            }
            switch (a.a[variance.ordinal()]) {
                case 1:
                    b bVar = INV;
                    if (bVar == null) {
                        a(1);
                    }
                    return bVar;
                case 2:
                    b bVar2 = IN;
                    if (bVar2 == null) {
                        a(2);
                    }
                    return bVar2;
                case 3:
                    b bVar3 = OUT;
                    if (bVar3 == null) {
                        a(3);
                    }
                    return bVar3;
                default:
                    throw new IllegalStateException("Unknown variance");
            }
        }
    }

    public static b g(@NotNull t0 typeParameter, @NotNull w0 typeArgument) {
        if (typeParameter == null) {
            a(13);
        }
        if (typeArgument == null) {
            a(14);
        }
        h1 a2 = typeParameter.y();
        h1 b2 = typeArgument.c();
        if (b2 == h1.INVARIANT) {
            h1 t = a2;
            a2 = b2;
            b2 = t;
        }
        h1 t2 = h1.IN_VARIANCE;
        if (a2 == t2 && b2 == h1.OUT_VARIANCE) {
            return b.STAR;
        }
        if (a2 == h1.OUT_VARIANCE && b2 == t2) {
            return b.STAR;
        }
        return b.fromVariance(b2);
    }

    public boolean k(@NotNull b0 subtype, @NotNull b0 supertype) {
        if (subtype == null) {
            a(15);
        }
        if (supertype == null) {
            a(16);
        }
        if (kotlin.reflect.jvm.internal.impl.types.t0.e(subtype, supertype)) {
            return !subtype.J0() || supertype.J0();
        }
        b0 subtypeRepresentative = kotlin.reflect.jvm.internal.impl.types.t0.b(subtype);
        b0 supertypeRepresentative = kotlin.reflect.jvm.internal.impl.types.t0.c(supertype);
        if (subtypeRepresentative == subtype && supertypeRepresentative == supertype) {
            return l(subtype, supertype);
        }
        return k(subtypeRepresentative, supertypeRepresentative);
    }

    private boolean l(b0 subtype, b0 supertype) {
        if (d0.a(subtype) || d0.a(supertype)) {
            return true;
        }
        if (!supertype.J0() && subtype.J0()) {
            return false;
        }
        if (g.x0(subtype)) {
            return true;
        }
        b0 closestSupertype = f(subtype, supertype, this.a);
        if (closestSupertype == null) {
            return this.a.d(subtype, supertype);
        }
        if (supertype.J0() || !closestSupertype.J0()) {
            return c(closestSupertype, supertype);
        }
        return false;
    }

    private boolean c(@NotNull b0 subtype, @NotNull b0 supertype) {
        h1 h1Var;
        if (subtype == null) {
            a(17);
        }
        if (supertype == null) {
            a(18);
        }
        u0 constructor = subtype.I0();
        List<w0> H0 = subtype.H0();
        List<w0> H02 = supertype.H0();
        boolean z = false;
        if (H0.size() != H02.size()) {
            return false;
        }
        List<t0> parameters = constructor.getParameters();
        int i = 0;
        while (true) {
            boolean argumentIsErrorType = true;
            if (i >= parameters.size()) {
                return true;
            }
            t0 parameter = parameters.get(i);
            w0 superArgument = H02.get(i);
            w0 subArgument = H0.get(i);
            if (!superArgument.b() && !b(subArgument, superArgument, parameter)) {
                if (!d0.a(subArgument.getType()) && !d0.a(superArgument.getType())) {
                    argumentIsErrorType = z;
                }
                if (argumentIsErrorType || parameter.y() != (h1Var = h1.INVARIANT) || subArgument.c() != h1Var || superArgument.c() != h1Var) {
                    b0 superOut = i(parameter, superArgument);
                    if (!this.a.a(i(parameter, subArgument), superOut, this)) {
                        return z;
                    }
                    b0 superIn = h(parameter, superArgument);
                    b0 subIn = h(parameter, subArgument);
                    if (superArgument.c() == h1.OUT_VARIANCE) {
                        z = false;
                        if (!g.w0(superIn)) {
                            throw new AssertionError("In component must be Nothing for out-projection");
                        }
                    } else if (!this.a.a(superIn, subIn, this)) {
                        return false;
                    } else {
                        z = false;
                    }
                } else if (!this.a.e(subArgument.getType(), superArgument.getType(), this)) {
                    return z;
                }
            }
            i++;
        }
    }

    private boolean b(@NotNull w0 subtypeArgumentProjection, @NotNull w0 supertypeArgumentProjection, @NotNull t0 parameter) {
        if (subtypeArgumentProjection == null) {
            a(19);
        }
        if (supertypeArgumentProjection == null) {
            a(20);
        }
        if (parameter == null) {
            a(21);
        }
        h1 y = parameter.y();
        h1 h1Var = h1.INVARIANT;
        if (y == h1Var && subtypeArgumentProjection.c() != h1Var && supertypeArgumentProjection.c() == h1Var) {
            return this.a.b(supertypeArgumentProjection.getType(), subtypeArgumentProjection);
        }
        return false;
    }
}
