package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: TypeUtils */
public class c1 {
    public static final i0 a = u.p("DONT_CARE");
    public static final i0 b = u.j("Cannot be inferred");
    @NotNull
    public static final i0 c = new a("NO_EXPECTED_TYPE");
    public static final i0 d = new a("UNIT_EXPECTED_TYPE");

    private static /* synthetic */ void a(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
            case 15:
            case 17:
            case 19:
            case 26:
            case 35:
            case 47:
            case 52:
            case 55:
            case 56:
            case 57:
            case 58:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
            case 15:
            case 17:
            case 19:
            case 26:
            case 35:
            case 47:
            case 52:
            case 55:
            case 56:
            case 57:
            case 58:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
            case 15:
            case 17:
            case 19:
            case 26:
            case 35:
            case 47:
            case 52:
            case 55:
            case 56:
            case 57:
            case 58:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/types/TypeUtils";
                break;
            case 12:
                objArr[0] = "typeConstructor";
                break;
            case 13:
                objArr[0] = "unsubstitutedMemberScope";
                break;
            case 14:
                objArr[0] = "refinedTypeFactory";
                break;
            case 16:
                objArr[0] = "parameters";
                break;
            case 20:
                objArr[0] = "subType";
                break;
            case 21:
                objArr[0] = "superType";
                break;
            case 22:
                objArr[0] = "substitutor";
                break;
            case 24:
                objArr[0] = "result";
                break;
            case 31:
            case 33:
                objArr[0] = "clazz";
                break;
            case 32:
                objArr[0] = "typeArguments";
                break;
            case 34:
                objArr[0] = "projections";
                break;
            case 36:
                objArr[0] = "a";
                break;
            case 37:
                objArr[0] = "b";
                break;
            case 39:
                objArr[0] = "typeParameters";
                break;
            case 41:
                objArr[0] = "typeParameterConstructors";
                break;
            case 42:
                objArr[0] = "specialType";
                break;
            case 43:
            case 44:
                objArr[0] = "isSpecialType";
                break;
            case 45:
                objArr[0] = "parameterDescriptor";
                break;
            case 46:
            case 50:
                objArr[0] = "numberValueTypeConstructor";
                break;
            case 48:
            case 49:
                objArr[0] = "supertypes";
                break;
            case 51:
            case 54:
                objArr[0] = "expectedType";
                break;
            case 53:
                objArr[0] = "literalTypeConstructor";
                break;
            default:
                objArr[0] = IjkMediaMeta.IJKM_KEY_TYPE;
                break;
        }
        switch (i) {
            case 4:
                objArr[1] = "makeNullableAsSpecified";
                break;
            case 6:
            case 7:
            case 9:
                objArr[1] = "makeNullableIfNeeded";
                break;
            case 11:
            case 15:
                objArr[1] = "makeUnsubstitutedType";
                break;
            case 17:
                objArr[1] = "getDefaultTypeProjections";
                break;
            case 19:
                objArr[1] = "getImmediateSupertypes";
                break;
            case 26:
                objArr[1] = "getAllSupertypes";
                break;
            case 35:
                objArr[1] = "substituteProjectionsForParameters";
                break;
            case 47:
                objArr[1] = "getDefaultPrimitiveNumberType";
                break;
            case 52:
            case 55:
            case 56:
            case 57:
            case 58:
                objArr[1] = "getPrimitiveNumberType";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/types/TypeUtils";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "makeNullable";
                break;
            case 2:
                objArr[2] = "makeNotNullable";
                break;
            case 3:
                objArr[2] = "makeNullableAsSpecified";
                break;
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
            case 15:
            case 17:
            case 19:
            case 26:
            case 35:
            case 47:
            case 52:
            case 55:
            case 56:
            case 57:
            case 58:
                break;
            case 5:
            case 8:
                objArr[2] = "makeNullableIfNeeded";
                break;
            case 10:
                objArr[2] = "canHaveSubtypes";
                break;
            case 12:
            case 13:
            case 14:
                objArr[2] = "makeUnsubstitutedType";
                break;
            case 16:
                objArr[2] = "getDefaultTypeProjections";
                break;
            case 18:
                objArr[2] = "getImmediateSupertypes";
                break;
            case 20:
            case 21:
            case 22:
                objArr[2] = "createSubstitutedSupertype";
                break;
            case 23:
            case 24:
                objArr[2] = "collectAllSupertypes";
                break;
            case 25:
                objArr[2] = "getAllSupertypes";
                break;
            case 27:
                objArr[2] = "isNullableType";
                break;
            case 28:
                objArr[2] = "acceptsNullable";
                break;
            case 29:
                objArr[2] = "hasNullableSuperType";
                break;
            case 30:
                objArr[2] = "getClassDescriptor";
                break;
            case 31:
            case 32:
                objArr[2] = "substituteParameters";
                break;
            case 33:
            case 34:
                objArr[2] = "substituteProjectionsForParameters";
                break;
            case 36:
            case 37:
                objArr[2] = "equalTypes";
                break;
            case 38:
            case 39:
                objArr[2] = "dependsOnTypeParameters";
                break;
            case 40:
            case 41:
                objArr[2] = "dependsOnTypeConstructors";
                break;
            case 42:
            case 43:
            case 44:
                objArr[2] = "contains";
                break;
            case 45:
                objArr[2] = "makeStarProjection";
                break;
            case 46:
            case 48:
                objArr[2] = "getDefaultPrimitiveNumberType";
                break;
            case 49:
                objArr[2] = "findByFqName";
                break;
            case 50:
            case 51:
            case 53:
            case 54:
                objArr[2] = "getPrimitiveNumberType";
                break;
            case 59:
                objArr[2] = "isTypeParameter";
                break;
            case 60:
                objArr[2] = "isReifiedTypeParameter";
                break;
            case 61:
                objArr[2] = "isNonReifiedTypeParameter";
                break;
            case 62:
                objArr[2] = "getTypeParameterDescriptorOrNull";
                break;
            default:
                objArr[2] = "noExpectedType";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
            case 15:
            case 17:
            case 19:
            case 26:
            case 35:
            case 47:
            case 52:
            case 55:
            case 56:
            case 57:
            case 58:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* compiled from: TypeUtils */
    public static class a extends n {
        private final String d;

        private static /* synthetic */ void U0(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 1:
                case 4:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 1:
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
                case 4:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/types/TypeUtils$SpecialType";
                    break;
                case 2:
                    objArr[0] = "delegate";
                    break;
                case 3:
                    objArr[0] = "kotlinTypeRefiner";
                    break;
                default:
                    objArr[0] = "newAnnotations";
                    break;
            }
            switch (i) {
                case 1:
                    objArr[1] = "toString";
                    break;
                case 4:
                    objArr[1] = "refine";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/types/TypeUtils$SpecialType";
                    break;
            }
            switch (i) {
                case 1:
                case 4:
                    break;
                case 2:
                    objArr[2] = "replaceDelegate";
                    break;
                case 3:
                    objArr[2] = "refine";
                    break;
                default:
                    objArr[2] = "replaceAnnotations";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 1:
                case 4:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        public a(String name) {
            this.d = name;
        }

        /* access modifiers changed from: protected */
        @NotNull
        public i0 R0() {
            throw new IllegalStateException(this.d);
        }

        @NotNull
        /* renamed from: Q0 */
        public i0 O0(@NotNull g newAnnotations) {
            if (newAnnotations == null) {
                U0(0);
            }
            throw new IllegalStateException(this.d);
        }

        @NotNull
        /* renamed from: P0 */
        public i0 M0(boolean newNullability) {
            throw new IllegalStateException(this.d);
        }

        @NotNull
        public String toString() {
            String str = this.d;
            if (str == null) {
                U0(1);
            }
            return str;
        }

        @NotNull
        public n T0(@NotNull i0 delegate) {
            if (delegate == null) {
                U0(2);
            }
            throw new IllegalStateException(this.d);
        }

        @NotNull
        /* renamed from: V0 */
        public a S0(@NotNull i kotlinTypeRefiner) {
            if (kotlinTypeRefiner == null) {
                U0(3);
            }
            return this;
        }
    }

    public static boolean v(@NotNull b0 type) {
        if (type == null) {
            a(0);
        }
        return type == c || type == d;
    }

    public static boolean k(@Nullable b0 type) {
        return type != null && type.I0() == a.I0();
    }

    @NotNull
    public static b0 o(@NotNull b0 type) {
        if (type == null) {
            a(1);
        }
        return p(type, true);
    }

    @NotNull
    public static b0 n(@NotNull b0 type) {
        if (type == null) {
            a(2);
        }
        return p(type, false);
    }

    @NotNull
    public static b0 p(@NotNull b0 type, boolean nullable) {
        if (type == null) {
            a(3);
        }
        g1 M0 = type.L0().M0(nullable);
        if (M0 == null) {
            a(4);
        }
        return M0;
    }

    @NotNull
    public static i0 r(@NotNull i0 type, boolean nullable) {
        if (type == null) {
            a(5);
        }
        if (nullable) {
            i0 P0 = type.P0(true);
            if (P0 == null) {
                a(6);
            }
            return P0;
        }
        if (type == null) {
            a(7);
        }
        return type;
    }

    @NotNull
    public static b0 q(@NotNull b0 type, boolean nullable) {
        if (type == null) {
            a(8);
        }
        if (nullable) {
            return o(type);
        }
        if (type == null) {
            a(9);
        }
        return type;
    }

    @NotNull
    public static i0 t(h classifierDescriptor, kotlin.reflect.jvm.internal.impl.resolve.scopes.h unsubstitutedMemberScope, l<i, i0> refinedTypeFactory) {
        if (!u.r(classifierDescriptor)) {
            return u(classifierDescriptor.i(), unsubstitutedMemberScope, refinedTypeFactory);
        }
        i0 j = u.j("Unsubstituted type for " + classifierDescriptor);
        if (j == null) {
            a(11);
        }
        return j;
    }

    @NotNull
    public static i0 u(@NotNull u0 typeConstructor, @NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.h unsubstitutedMemberScope, @NotNull l<i, i0> refinedTypeFactory) {
        if (typeConstructor == null) {
            a(12);
        }
        if (unsubstitutedMemberScope == null) {
            a(13);
        }
        if (refinedTypeFactory == null) {
            a(14);
        }
        i0 k = c0.k(g.b.b(), typeConstructor, g(typeConstructor.getParameters()), false, unsubstitutedMemberScope, refinedTypeFactory);
        if (k == null) {
            a(15);
        }
        return k;
    }

    @NotNull
    public static List<w0> g(@NotNull List<t0> parameters) {
        if (parameters == null) {
            a(16);
        }
        List<TypeProjection> result = new ArrayList<>(parameters.size());
        for (t0 parameterDescriptor : parameters) {
            result.add(new y0(parameterDescriptor.m()));
        }
        List<T> C0 = y.C0(result);
        if (C0 == null) {
            a(17);
        }
        return C0;
    }

    @NotNull
    public static List<b0> h(@NotNull b0 type) {
        if (type == null) {
            a(18);
        }
        TypeSubstitutor substitutor = TypeSubstitutor.f(type);
        Collection<b0> b2 = type.I0().b();
        List<KotlinType> result = new ArrayList<>(b2.size());
        for (b0 supertype : b2) {
            b0 substitutedType = e(type, supertype, substitutor);
            if (substitutedType != null) {
                result.add(substitutedType);
            }
        }
        return result;
    }

    @Nullable
    public static b0 e(@NotNull b0 subType, @NotNull b0 superType, @NotNull TypeSubstitutor substitutor) {
        if (subType == null) {
            a(20);
        }
        if (superType == null) {
            a(21);
        }
        if (substitutor == null) {
            a(22);
        }
        b0 substitutedType = substitutor.n(superType, h1.INVARIANT);
        if (substitutedType != null) {
            return q(substitutedType, subType.J0());
        }
        return null;
    }

    public static boolean l(@NotNull b0 type) {
        if (type == null) {
            a(27);
        }
        if (type.J0()) {
            return true;
        }
        if (y.b(type) && l(y.a(type).R0())) {
            return true;
        }
        if (m(type)) {
            return j(type);
        }
        u0 constructor = type.I0();
        if (!(constructor instanceof a0)) {
            return false;
        }
        for (b0 supertype : constructor.b()) {
            if (l(supertype)) {
                return true;
            }
        }
        return false;
    }

    public static boolean b(@NotNull b0 type) {
        if (type == null) {
            a(28);
        }
        if (type.J0()) {
            return true;
        }
        if (!y.b(type) || !b(y.a(type).R0())) {
            return false;
        }
        return true;
    }

    public static boolean j(@NotNull b0 type) {
        if (type == null) {
            a(29);
        }
        if (type.I0().c() instanceof e) {
            return false;
        }
        for (b0 supertype : h(type)) {
            if (l(supertype)) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public static e f(@NotNull b0 type) {
        if (type == null) {
            a(30);
        }
        h c2 = type.I0().c();
        if (c2 instanceof e) {
            return (e) c2;
        }
        return null;
    }

    public static boolean c(@Nullable b0 type, @NotNull l<g1, Boolean> isSpecialType) {
        if (isSpecialType == null) {
            a(43);
        }
        return d(type, isSpecialType, new HashSet());
    }

    private static boolean d(@Nullable b0 type, @NotNull l<g1, Boolean> isSpecialType, HashSet<b0> visited) {
        if (isSpecialType == null) {
            a(44);
        }
        if (type == null || visited.contains(type)) {
            return false;
        }
        visited.add(type);
        g1 unwrappedType = type.L0();
        if (isSpecialType.invoke(unwrappedType).booleanValue()) {
            return true;
        }
        v flexibleType = unwrappedType instanceof v ? (v) unwrappedType : null;
        if (flexibleType != null && (d(flexibleType.Q0(), isSpecialType, visited) || d(flexibleType.R0(), isSpecialType, visited))) {
            return true;
        }
        if ((unwrappedType instanceof l) && d(((l) unwrappedType).U0(), isSpecialType, visited)) {
            return true;
        }
        u0 typeConstructor = type.I0();
        if (typeConstructor instanceof a0) {
            for (b0 supertype : ((a0) typeConstructor).b()) {
                if (d(supertype, isSpecialType, visited)) {
                    return true;
                }
            }
            return false;
        }
        for (w0 projection : type.H0()) {
            if (!projection.b()) {
                if (d(projection.getType(), isSpecialType, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    @NotNull
    public static w0 s(@NotNull t0 parameterDescriptor) {
        if (parameterDescriptor == null) {
            a(45);
        }
        return new n0(parameterDescriptor);
    }

    public static boolean m(@NotNull b0 type) {
        if (type == null) {
            a(59);
        }
        if (i(type) != null) {
            return true;
        }
        type.I0();
        return false;
    }

    @Nullable
    public static t0 i(@NotNull b0 type) {
        if (type == null) {
            a(62);
        }
        if (type.I0().c() instanceof t0) {
            return (t0) type.I0().c();
        }
        return null;
    }
}
