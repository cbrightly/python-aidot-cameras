package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.l;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.k;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class TypeSubstitutor {
    public static final TypeSubstitutor a = g(z0.a);
    @NotNull
    private final z0 b;

    public enum c {
        NO_CONFLICT,
        IN_IN_OUT_POSITION,
        OUT_IN_IN_POSITION
    }

    private static /* synthetic */ void a(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 6:
            case 9:
            case 10:
            case 11:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 24:
            case 27:
            case 30:
            case 31:
            case 32:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 6:
            case 9:
            case 10:
            case 11:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 24:
            case 27:
            case 30:
            case 31:
            case 32:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "first";
                break;
            case 2:
                objArr[0] = "second";
                break;
            case 3:
                objArr[0] = "substitutionContext";
                break;
            case 4:
                objArr[0] = "context";
                break;
            case 6:
            case 9:
            case 10:
            case 11:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 24:
            case 27:
            case 30:
            case 31:
            case 32:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/types/TypeSubstitutor";
                break;
            case 7:
            case 12:
                objArr[0] = IjkMediaMeta.IJKM_KEY_TYPE;
                break;
            case 8:
            case 13:
                objArr[0] = "howThisTypeIsUsed";
                break;
            case 14:
            case 15:
            case 26:
                objArr[0] = "typeProjection";
                break;
            case 16:
                objArr[0] = "originalProjection";
                break;
            case 23:
                objArr[0] = "annotations";
                break;
            case 25:
            case 28:
                objArr[0] = "typeParameterVariance";
                break;
            case 29:
                objArr[0] = "projectionKind";
                break;
            default:
                objArr[0] = "substitution";
                break;
        }
        switch (i) {
            case 6:
                objArr[1] = "getSubstitution";
                break;
            case 9:
            case 10:
            case 11:
                objArr[1] = "safeSubstitute";
                break;
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                objArr[1] = "unsafeSubstitute";
                break;
            case 24:
                objArr[1] = "filterOutUnsafeVariance";
                break;
            case 27:
            case 30:
            case 31:
            case 32:
                objArr[1] = "combine";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/types/TypeSubstitutor";
                break;
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "createChainedSubstitutor";
                break;
            case 5:
                objArr[2] = "<init>";
                break;
            case 6:
            case 9:
            case 10:
            case 11:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 24:
            case 27:
            case 30:
            case 31:
            case 32:
                break;
            case 7:
            case 8:
                objArr[2] = "safeSubstitute";
                break;
            case 12:
            case 13:
            case 14:
                objArr[2] = "substitute";
                break;
            case 15:
                objArr[2] = "substituteWithoutApproximation";
                break;
            case 16:
                objArr[2] = "unsafeSubstitute";
                break;
            case 23:
                objArr[2] = "filterOutUnsafeVariance";
                break;
            case 25:
            case 26:
            case 28:
            case 29:
                objArr[2] = "combine";
                break;
            default:
                objArr[2] = "create";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 6:
            case 9:
            case 10:
            case 11:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 24:
            case 27:
            case 30:
            case 31:
            case 32:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    public static final class SubstitutionException extends Exception {
        public SubstitutionException(String message) {
            super(message);
        }
    }

    @NotNull
    public static TypeSubstitutor g(@NotNull z0 substitution) {
        if (substitution == null) {
            a(0);
        }
        return new TypeSubstitutor(substitution);
    }

    @NotNull
    public static TypeSubstitutor h(@NotNull z0 first, @NotNull z0 second) {
        if (first == null) {
            a(1);
        }
        if (second == null) {
            a(2);
        }
        return g(q.h(first, second));
    }

    @NotNull
    public static TypeSubstitutor f(@NotNull b0 context) {
        if (context == null) {
            a(4);
        }
        return g(v0.h(context.I0(), context.H0()));
    }

    protected TypeSubstitutor(@NotNull z0 substitution) {
        if (substitution == null) {
            a(5);
        }
        this.b = substitution;
    }

    public boolean k() {
        return this.b.f();
    }

    @NotNull
    public z0 j() {
        z0 z0Var = this.b;
        if (z0Var == null) {
            a(6);
        }
        return z0Var;
    }

    @NotNull
    public b0 l(@NotNull b0 type, @NotNull h1 howThisTypeIsUsed) {
        if (type == null) {
            a(7);
        }
        if (howThisTypeIsUsed == null) {
            a(8);
        }
        if (k()) {
            if (type == null) {
                a(9);
            }
            return type;
        }
        try {
            b0 type2 = s(new y0(howThisTypeIsUsed, type), 0).getType();
            if (type2 == null) {
                a(10);
            }
            return type2;
        } catch (SubstitutionException e) {
            i0 j = u.j(e.getMessage());
            if (j == null) {
                a(11);
            }
            return j;
        }
    }

    @Nullable
    public b0 n(@NotNull b0 type, @NotNull h1 howThisTypeIsUsed) {
        if (type == null) {
            a(12);
        }
        if (howThisTypeIsUsed == null) {
            a(13);
        }
        w0 projection = o(new y0(howThisTypeIsUsed, j().g(type, howThisTypeIsUsed)));
        if (projection == null) {
            return null;
        }
        return projection.getType();
    }

    @Nullable
    public w0 o(@NotNull w0 typeProjection) {
        if (typeProjection == null) {
            a(14);
        }
        w0 substitutedTypeProjection = r(typeProjection);
        if (this.b.a() || this.b.b()) {
            return kotlin.reflect.jvm.internal.impl.types.typesApproximation.c.b(substitutedTypeProjection, this.b.b());
        }
        return substitutedTypeProjection;
    }

    @Nullable
    public w0 r(@NotNull w0 typeProjection) {
        if (typeProjection == null) {
            a(15);
        }
        if (k()) {
            return typeProjection;
        }
        try {
            return s(typeProjection, 0);
        } catch (SubstitutionException e) {
            return null;
        }
    }

    @NotNull
    private w0 s(@NotNull w0 originalProjection, int recursionDepth) {
        b0 substitutedType;
        if (originalProjection == null) {
            a(16);
        }
        b(recursionDepth, originalProjection, this.b);
        if (originalProjection.b()) {
            return originalProjection;
        }
        b0 type = originalProjection.getType();
        if (type instanceof d1) {
            b0 origin = ((d1) type).A0();
            b0 enhancement = ((d1) type).a0();
            w0 substitution = s(new y0(originalProjection.c(), origin), recursionDepth + 1);
            return new y0(substitution.c(), e1.d(substitution.getType().L0(), n(enhancement, originalProjection.c())));
        } else if (s.a(type) || (type.L0() instanceof h0)) {
            return originalProjection;
        } else {
            w0 replacement = this.b.e(type);
            h1 originalProjectionKind = originalProjection.c();
            if (replacement == null && y.b(type) && !t0.d(type)) {
                v flexibleType = y.a(type);
                w0 substitutedLower = s(new y0(originalProjectionKind, flexibleType.Q0()), recursionDepth + 1);
                w0 substitutedUpper = s(new y0(originalProjectionKind, flexibleType.R0()), recursionDepth + 1);
                h1 substitutedProjectionKind = substitutedLower.c();
                if ((substitutedProjectionKind != substitutedUpper.c() || originalProjectionKind != h1.INVARIANT) && originalProjectionKind != substitutedProjectionKind) {
                    throw new AssertionError("Unexpected substituted projection kind: " + substitutedProjectionKind + "; original: " + originalProjectionKind);
                } else if (substitutedLower.getType() == flexibleType.Q0() && substitutedUpper.getType() == flexibleType.R0()) {
                    return originalProjection;
                } else {
                    return new y0(substitutedProjectionKind, c0.d(a1.a(substitutedLower.getType()), a1.a(substitutedUpper.getType())));
                }
            } else if (g.w0(type) || d0.a(type)) {
                return originalProjection;
            } else {
                if (replacement != null) {
                    c varianceConflict = e(originalProjectionKind, replacement.c());
                    if (!d.d(type)) {
                        switch (b.a[varianceConflict.ordinal()]) {
                            case 1:
                                throw new SubstitutionException("Out-projection in in-position");
                            case 2:
                                return new y0(h1.OUT_VARIANCE, type.I0().j().K());
                        }
                    }
                    k typeVariable = t0.a(type);
                    if (replacement.b()) {
                        return replacement;
                    }
                    if (typeVariable != null) {
                        substitutedType = typeVariable.c0(replacement.getType());
                    } else {
                        substitutedType = c1.q(replacement.getType(), type.J0());
                    }
                    if (!type.getAnnotations().isEmpty()) {
                        substitutedType = kotlin.reflect.jvm.internal.impl.types.typeUtil.a.m(substitutedType, new k(substitutedType.getAnnotations(), i(this.b.d(type.getAnnotations()))));
                    }
                    return new y0(varianceConflict == c.NO_CONFLICT ? d(originalProjectionKind, replacement.c()) : originalProjectionKind, substitutedType);
                }
                w0 p = p(originalProjection, recursionDepth);
                if (p == null) {
                    a(22);
                }
                return p;
            }
        }
    }

    public static /* synthetic */ class b {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[c.values().length];
            a = iArr;
            try {
                iArr[c.OUT_IN_IN_POSITION.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[c.IN_IN_OUT_POSITION.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[c.NO_CONFLICT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public static final class a implements l<kotlin.reflect.jvm.internal.impl.name.b, Boolean> {
        private static /* synthetic */ void a(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[]{"name", "kotlin/reflect/jvm/internal/impl/types/TypeSubstitutor$1", "invoke"}));
        }

        a() {
        }

        /* renamed from: b */
        public Boolean invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.b name) {
            if (name == null) {
                a(0);
            }
            return Boolean.valueOf(!name.equals(g.h.J));
        }
    }

    @NotNull
    private static kotlin.reflect.jvm.internal.impl.descriptors.annotations.g i(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.g annotations) {
        if (annotations == null) {
            a(23);
        }
        if (!annotations.I(g.h.J)) {
            return annotations;
        }
        return new kotlin.reflect.jvm.internal.impl.descriptors.annotations.l(annotations, new a());
    }

    private w0 p(w0 originalProjection, int recursionDepth) {
        b0 type = originalProjection.getType();
        h1 projectionKind = originalProjection.c();
        if (type.I0().c() instanceof t0) {
            return originalProjection;
        }
        b0 substitutedAbbreviation = null;
        i0 abbreviation = l0.b(type);
        if (abbreviation != null) {
            substitutedAbbreviation = n(abbreviation, h1.INVARIANT);
        }
        b0 substitutedType = a1.b(type, q(type.I0().getParameters(), type.H0(), recursionDepth), this.b.d(type.getAnnotations()));
        if ((substitutedType instanceof i0) && (substitutedAbbreviation instanceof i0)) {
            substitutedType = l0.h((i0) substitutedType, (i0) substitutedAbbreviation);
        }
        return new y0(projectionKind, substitutedType);
    }

    private List<w0> q(List<t0> typeParameters, List<w0> typeArguments, int recursionDepth) {
        List<TypeProjection> substitutedArguments = new ArrayList<>(typeParameters.size());
        boolean wereChanges = false;
        for (int i = 0; i < typeParameters.size(); i++) {
            t0 typeParameter = typeParameters.get(i);
            w0 typeArgument = typeArguments.get(i);
            w0 substitutedTypeArgument = s(typeArgument, recursionDepth + 1);
            switch (b.a[e(typeParameter.y(), substitutedTypeArgument.c()).ordinal()]) {
                case 1:
                case 2:
                    substitutedTypeArgument = c1.s(typeParameter);
                    break;
                case 3:
                    h1 y = typeParameter.y();
                    h1 h1Var = h1.INVARIANT;
                    if (y != h1Var && !substitutedTypeArgument.b()) {
                        substitutedTypeArgument = new y0(h1Var, substitutedTypeArgument.getType());
                        break;
                    }
            }
            if (substitutedTypeArgument != typeArgument) {
                wereChanges = true;
            }
            substitutedArguments.add(substitutedTypeArgument);
        }
        if (!wereChanges) {
            return typeArguments;
        }
        return substitutedArguments;
    }

    @NotNull
    public static h1 c(@NotNull h1 typeParameterVariance, @NotNull w0 typeProjection) {
        if (typeParameterVariance == null) {
            a(25);
        }
        if (typeProjection == null) {
            a(26);
        }
        if (!typeProjection.b()) {
            return d(typeParameterVariance, typeProjection.c());
        }
        h1 h1Var = h1.OUT_VARIANCE;
        if (h1Var == null) {
            a(27);
        }
        return h1Var;
    }

    @NotNull
    public static h1 d(@NotNull h1 typeParameterVariance, @NotNull h1 projectionKind) {
        if (typeParameterVariance == null) {
            a(28);
        }
        if (projectionKind == null) {
            a(29);
        }
        h1 h1Var = h1.INVARIANT;
        if (typeParameterVariance == h1Var) {
            if (projectionKind == null) {
                a(30);
            }
            return projectionKind;
        } else if (projectionKind == h1Var) {
            if (typeParameterVariance == null) {
                a(31);
            }
            return typeParameterVariance;
        } else if (typeParameterVariance == projectionKind) {
            if (projectionKind == null) {
                a(32);
            }
            return projectionKind;
        } else {
            throw new AssertionError("Variance conflict: type parameter variance '" + typeParameterVariance + "' and " + "projection kind '" + projectionKind + "' cannot be combined");
        }
    }

    private static c e(h1 position, h1 argument) {
        h1 h1Var = h1.IN_VARIANCE;
        if (position == h1Var && argument == h1.OUT_VARIANCE) {
            return c.OUT_IN_IN_POSITION;
        }
        if (position == h1.OUT_VARIANCE && argument == h1Var) {
            return c.IN_IN_OUT_POSITION;
        }
        return c.NO_CONFLICT;
    }

    private static void b(int recursionDepth, w0 projection, z0 substitution) {
        if (recursionDepth > 100) {
            throw new IllegalStateException("Recursion too deep. Most likely infinite loop while substituting " + m(projection) + "; substitution: " + m(substitution));
        }
    }

    private static String m(Object o) {
        try {
            return o.toString();
        } catch (Throwable th) {
            if (!kotlin.reflect.jvm.internal.impl.utils.c.a(th)) {
                return "[Exception while computing toString(): " + th + "]";
            }
            throw th;
        }
    }
}
