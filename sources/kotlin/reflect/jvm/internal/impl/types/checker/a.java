package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.Collection;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.h;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.resolve.constants.n;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.checker.c;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.g;
import kotlin.reflect.jvm.internal.impl.types.g1;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.model.d;
import kotlin.reflect.jvm.internal.impl.types.model.e;
import kotlin.reflect.jvm.internal.impl.types.model.f;
import kotlin.reflect.jvm.internal.impl.types.model.i;
import kotlin.reflect.jvm.internal.impl.types.model.j;
import kotlin.reflect.jvm.internal.impl.types.model.l;
import kotlin.reflect.jvm.internal.impl.types.model.p;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.types.v0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: ClassicTypeCheckerContext.kt */
public class a extends g implements c {
    public static final C0422a e = new C0422a((DefaultConstructorMarker) null);
    private final boolean f;
    private final boolean g;
    private final boolean h;
    @NotNull
    private final i i;

    public a(boolean errorTypeEqualsToAnything, boolean stubTypeEqualsToAnything, boolean allowedTypeVariable, @NotNull i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        this.f = errorTypeEqualsToAnything;
        this.g = stubTypeEqualsToAnything;
        this.h = allowedTypeVariable;
        this.i = kotlinTypeRefiner;
    }

    public boolean A(@NotNull kotlin.reflect.jvm.internal.impl.types.model.k c1, @NotNull kotlin.reflect.jvm.internal.impl.types.model.k c2) {
        k.f(c1, "c1");
        k.f(c2, "c2");
        return c.a.I(this, c1, c2);
    }

    public int B(@NotNull kotlin.reflect.jvm.internal.impl.types.model.k $this$parametersCount) {
        k.f($this$parametersCount, "$this$parametersCount");
        return c.a.d0(this, $this$parametersCount);
    }

    @Nullable
    public h C(@NotNull kotlin.reflect.jvm.internal.impl.types.model.k $this$getPrimitiveArrayType) {
        k.f($this$getPrimitiveArrayType, "$this$getPrimitiveArrayType");
        return c.a.p(this, $this$getPrimitiveArrayType);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.types.model.g D(@NotNull l $this$getRepresentativeUpperBound) {
        k.f($this$getRepresentativeUpperBound, "$this$getRepresentativeUpperBound");
        return c.a.r(this, $this$getRepresentativeUpperBound);
    }

    public boolean E(@NotNull kotlin.reflect.jvm.internal.impl.types.model.k $this$isNothingConstructor) {
        k.f($this$isNothingConstructor, "$this$isNothingConstructor");
        return c.a.R(this, $this$isNothingConstructor);
    }

    @NotNull
    public Collection<kotlin.reflect.jvm.internal.impl.types.model.g> F(@NotNull kotlin.reflect.jvm.internal.impl.types.model.k $this$supertypes) {
        k.f($this$supertypes, "$this$supertypes");
        return c.a.g0(this, $this$supertypes);
    }

    @NotNull
    public Collection<kotlin.reflect.jvm.internal.impl.types.model.g> G(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h $this$possibleIntegerTypes) {
        k.f($this$possibleIntegerTypes, "$this$possibleIntegerTypes");
        return c.a.e0(this, $this$possibleIntegerTypes);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.types.model.k H(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g $this$typeConstructor) {
        k.f($this$typeConstructor, "$this$typeConstructor");
        return c.a.h0(this, $this$typeConstructor);
    }

    public boolean I(@NotNull kotlin.reflect.jvm.internal.impl.types.model.k $this$isDenotable) {
        k.f($this$isDenotable, "$this$isDenotable");
        return c.a.G(this, $this$isDenotable);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.name.c J(@NotNull kotlin.reflect.jvm.internal.impl.types.model.k $this$getClassFqNameUnsafe) {
        k.f($this$getClassFqNameUnsafe, "$this$getClassFqNameUnsafe");
        return c.a.n(this, $this$getClassFqNameUnsafe);
    }

    @Nullable
    public f K(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g $this$asFlexibleType) {
        k.f($this$asFlexibleType, "$this$asFlexibleType");
        return c.a.f(this, $this$asFlexibleType);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.types.model.g L(@NotNull List<? extends kotlin.reflect.jvm.internal.impl.types.model.g> types) {
        k.f(types, "types");
        return c.a.A(this, types);
    }

    @Nullable
    public kotlin.reflect.jvm.internal.impl.types.model.g M(@NotNull kotlin.reflect.jvm.internal.impl.types.model.c $this$lowerType) {
        k.f($this$lowerType, "$this$lowerType");
        return c.a.a0(this, $this$lowerType);
    }

    public boolean N(@NotNull kotlin.reflect.jvm.internal.impl.types.model.k $this$isClassTypeConstructor) {
        k.f($this$isClassTypeConstructor, "$this$isClassTypeConstructor");
        return c.a.D(this, $this$isClassTypeConstructor);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.types.model.h O(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h $this$withNullability, boolean nullable) {
        k.f($this$withNullability, "$this$withNullability");
        return c.a.l0(this, $this$withNullability, nullable);
    }

    public boolean P(@NotNull kotlin.reflect.jvm.internal.impl.types.model.k $this$isIntersection) {
        k.f($this$isIntersection, "$this$isIntersection");
        return c.a.N(this, $this$isIntersection);
    }

    @Nullable
    public kotlin.reflect.jvm.internal.impl.types.model.g Q(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g $this$getSubstitutedUnderlyingType) {
        k.f($this$getSubstitutedUnderlyingType, "$this$getSubstitutedUnderlyingType");
        return c.a.s(this, $this$getSubstitutedUnderlyingType);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.types.model.h R(@NotNull f $this$upperBound) {
        k.f($this$upperBound, "$this$upperBound");
        return c.a.j0(this, $this$upperBound);
    }

    @Nullable
    public kotlin.reflect.jvm.internal.impl.types.model.c S(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h $this$asCapturedType) {
        k.f($this$asCapturedType, "$this$asCapturedType");
        return c.a.c(this, $this$asCapturedType);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.types.model.h T(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g $this$lowerBoundIfFlexible) {
        k.f($this$lowerBoundIfFlexible, "$this$lowerBoundIfFlexible");
        return c.a.Z(this, $this$lowerBoundIfFlexible);
    }

    @NotNull
    public p U(@NotNull j $this$getVariance) {
        k.f($this$getVariance, "$this$getVariance");
        return c.a.v(this, $this$getVariance);
    }

    public boolean V(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g $this$hasAnnotation, @NotNull b fqName) {
        k.f($this$hasAnnotation, "$this$hasAnnotation");
        k.f(fqName, "fqName");
        return c.a.x(this, $this$hasAnnotation, fqName);
    }

    public boolean W(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g $this$isMarkedNullable) {
        k.f($this$isMarkedNullable, "$this$isMarkedNullable");
        return c.a.O(this, $this$isMarkedNullable);
    }

    @Nullable
    public kotlin.reflect.jvm.internal.impl.types.model.h X(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h type, @NotNull kotlin.reflect.jvm.internal.impl.types.model.b status) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        k.f(status, "status");
        return c.a.i(this, type, status);
    }

    @Nullable
    public d Y(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h $this$asDefinitelyNotNullType) {
        k.f($this$asDefinitelyNotNullType, "$this$asDefinitelyNotNullType");
        return c.a.d(this, $this$asDefinitelyNotNullType);
    }

    public boolean Z(@NotNull kotlin.reflect.jvm.internal.impl.types.model.k $this$isCommonFinalClassConstructor) {
        k.f($this$isCommonFinalClassConstructor, "$this$isCommonFinalClassConstructor");
        return c.a.E(this, $this$isCommonFinalClassConstructor);
    }

    @Nullable
    public kotlin.reflect.jvm.internal.impl.types.model.h a(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g $this$asSimpleType) {
        k.f($this$asSimpleType, "$this$asSimpleType");
        return c.a.g(this, $this$asSimpleType);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.types.model.g a0(@NotNull j $this$getType) {
        k.f($this$getType, "$this$getType");
        return c.a.t(this, $this$getType);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.types.model.k b(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h $this$typeConstructor) {
        k.f($this$typeConstructor, "$this$typeConstructor");
        return c.a.i0(this, $this$typeConstructor);
    }

    @Nullable
    public e b0(@NotNull f $this$asDynamicType) {
        k.f($this$asDynamicType, "$this$asDynamicType");
        return c.a.e(this, $this$asDynamicType);
    }

    public boolean c(@NotNull kotlin.reflect.jvm.internal.impl.types.model.k $this$isUnderKotlinPackage) {
        k.f($this$isUnderKotlinPackage, "$this$isUnderKotlinPackage");
        return c.a.X(this, $this$isUnderKotlinPackage);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.types.model.g c0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g $this$makeNullable) {
        k.f($this$makeNullable, "$this$makeNullable");
        return c.a.b0(this, $this$makeNullable);
    }

    public int d(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g $this$argumentsCount) {
        k.f($this$argumentsCount, "$this$argumentsCount");
        return c.a.a(this, $this$argumentsCount);
    }

    @NotNull
    public i e(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h $this$asArgumentList) {
        k.f($this$asArgumentList, "$this$asArgumentList");
        return c.a.b(this, $this$asArgumentList);
    }

    @NotNull
    public j f(@NotNull i $this$get, int index) {
        k.f($this$get, "$this$get");
        return c.a.k(this, $this$get, index);
    }

    @NotNull
    public l g(@NotNull kotlin.reflect.jvm.internal.impl.types.model.k $this$getParameter, int index) {
        k.f($this$getParameter, "$this$getParameter");
        return c.a.o(this, $this$getParameter, index);
    }

    @Nullable
    public l h(@NotNull kotlin.reflect.jvm.internal.impl.types.model.k $this$getTypeParameterClassifier) {
        k.f($this$getTypeParameterClassifier, "$this$getTypeParameterClassifier");
        return c.a.u(this, $this$getTypeParameterClassifier);
    }

    public boolean i(@NotNull j $this$isStarProjection) {
        k.f($this$isStarProjection, "$this$isStarProjection");
        return c.a.V(this, $this$isStarProjection);
    }

    @Nullable
    public List<kotlin.reflect.jvm.internal.impl.types.model.h> i0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h $this$fastCorrespondingSupertypes, @NotNull kotlin.reflect.jvm.internal.impl.types.model.k constructor) {
        k.f($this$fastCorrespondingSupertypes, "$this$fastCorrespondingSupertypes");
        k.f(constructor, "constructor");
        return c.a.j(this, $this$fastCorrespondingSupertypes, constructor);
    }

    @NotNull
    public p j(@NotNull l $this$getVariance) {
        k.f($this$getVariance, "$this$getVariance");
        return c.a.w(this, $this$getVariance);
    }

    @Nullable
    public j j0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h $this$getArgumentOrNull, int index) {
        k.f($this$getArgumentOrNull, "$this$getArgumentOrNull");
        return c.a.m(this, $this$getArgumentOrNull, index);
    }

    public boolean k(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h $this$isSingleClassifierType) {
        k.f($this$isSingleClassifierType, "$this$isSingleClassifierType");
        return c.a.U(this, $this$isSingleClassifierType);
    }

    public int l(@NotNull i $this$size) {
        k.f($this$size, "$this$size");
        return c.a.f0(this, $this$size);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.types.model.h m(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g $this$upperBoundIfFlexible) {
        k.f($this$upperBoundIfFlexible, "$this$upperBoundIfFlexible");
        return c.a.k0(this, $this$upperBoundIfFlexible);
    }

    public boolean n(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h $this$isMarkedNullable) {
        k.f($this$isMarkedNullable, "$this$isMarkedNullable");
        return c.a.P(this, $this$isMarkedNullable);
    }

    public boolean n0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g $this$hasFlexibleNullability) {
        k.f($this$hasFlexibleNullability, "$this$hasFlexibleNullability");
        return c.a.y(this, $this$hasFlexibleNullability);
    }

    public boolean o(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g $this$isError) {
        k.f($this$isError, "$this$isError");
        return c.a.J(this, $this$isError);
    }

    public boolean p(@NotNull kotlin.reflect.jvm.internal.impl.types.model.k $this$isInlineClass) {
        k.f($this$isInlineClass, "$this$isInlineClass");
        return c.a.K(this, $this$isInlineClass);
    }

    @NotNull
    public j q(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g $this$asTypeArgument) {
        k.f($this$asTypeArgument, "$this$asTypeArgument");
        return c.a.h(this, $this$asTypeArgument);
    }

    public boolean q0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h $this$isClassType) {
        k.f($this$isClassType, "$this$isClassType");
        return c.a.C(this, $this$isClassType);
    }

    @NotNull
    public j r(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g $this$getArgument, int index) {
        k.f($this$getArgument, "$this$getArgument");
        return c.a.l(this, $this$getArgument, index);
    }

    public boolean r0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g $this$isDefinitelyNotNullType) {
        k.f($this$isDefinitelyNotNullType, "$this$isDefinitelyNotNullType");
        return c.a.F(this, $this$isDefinitelyNotNullType);
    }

    @Nullable
    public h s(@NotNull kotlin.reflect.jvm.internal.impl.types.model.k $this$getPrimitiveType) {
        k.f($this$getPrimitiveType, "$this$getPrimitiveType");
        return c.a.q(this, $this$getPrimitiveType);
    }

    public boolean s0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g $this$isDynamic) {
        k.f($this$isDynamic, "$this$isDynamic");
        return c.a.H(this, $this$isDynamic);
    }

    public boolean t(@NotNull kotlin.reflect.jvm.internal.impl.types.model.k $this$isAnyConstructor) {
        k.f($this$isAnyConstructor, "$this$isAnyConstructor");
        return c.a.B(this, $this$isAnyConstructor);
    }

    public boolean u(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h $this$isStubType) {
        k.f($this$isStubType, "$this$isStubType");
        return c.a.W(this, $this$isStubType);
    }

    public boolean u0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h $this$isIntegerLiteralType) {
        k.f($this$isIntegerLiteralType, "$this$isIntegerLiteralType");
        return c.a.L(this, $this$isIntegerLiteralType);
    }

    public boolean v(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g $this$isNullableType) {
        k.f($this$isNullableType, "$this$isNullableType");
        return c.a.S(this, $this$isNullableType);
    }

    public boolean v0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g $this$isNothing) {
        k.f($this$isNothing, "$this$isNothing");
        return c.a.Q(this, $this$isNothing);
    }

    public boolean w(@NotNull kotlin.reflect.jvm.internal.impl.types.model.k $this$isIntegerLiteralTypeConstructor) {
        k.f($this$isIntegerLiteralTypeConstructor, "$this$isIntegerLiteralTypeConstructor");
        return c.a.M(this, $this$isIntegerLiteralTypeConstructor);
    }

    public boolean x(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h a, @NotNull kotlin.reflect.jvm.internal.impl.types.model.h b) {
        k.f(a, "a");
        k.f(b, "b");
        return c.a.z(this, a, b);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.types.model.h y(@NotNull f $this$lowerBound) {
        k.f($this$lowerBound, "$this$lowerBound");
        return c.a.Y(this, $this$lowerBound);
    }

    public boolean z(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h $this$isPrimitiveType) {
        k.f($this$isPrimitiveType, "$this$isPrimitiveType");
        return c.a.T(this, $this$isPrimitiveType);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ a(boolean z, boolean z2, boolean z3, i iVar, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, (i2 & 2) != 0 ? true : z2, (i2 & 4) != 0 ? true : z3, (i2 & 8) != 0 ? i.a.a : iVar);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.types.model.g x0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g type) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        if (type instanceof b0) {
            return n.b.a().h(((b0) type).L0());
        }
        throw new IllegalArgumentException(b.b(type).toString());
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.types.model.g y0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g type) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        if (type instanceof b0) {
            return this.i.g((b0) type);
        }
        throw new IllegalArgumentException(b.b(type).toString());
    }

    public boolean t0() {
        return this.f;
    }

    public boolean w0() {
        return this.g;
    }

    public boolean g0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.k a, @NotNull kotlin.reflect.jvm.internal.impl.types.model.k b) {
        k.f(a, "a");
        k.f(b, "b");
        if (!(a instanceof u0)) {
            throw new IllegalArgumentException(b.b(a).toString());
        } else if (b instanceof u0) {
            return A0((u0) a, (u0) b);
        } else {
            throw new IllegalArgumentException(b.b(b).toString());
        }
    }

    public boolean A0(@NotNull u0 a, @NotNull u0 b) {
        k.f(a, "a");
        k.f(b, "b");
        if (a instanceof n) {
            return ((n) a).i(b);
        }
        if (b instanceof n) {
            return ((n) b).i(a);
        }
        return k.a(a, b);
    }

    @NotNull
    /* renamed from: B0 */
    public g.b.a z0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h type) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        return e.a(this, type);
    }

    public boolean p0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g $this$isAllowedTypeVariable) {
        k.f($this$isAllowedTypeVariable, "$this$isAllowedTypeVariable");
        if (!($this$isAllowedTypeVariable instanceof g1) || !this.h) {
            return false;
        }
        ((g1) $this$isAllowedTypeVariable).I0();
        return false;
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.types.checker.a$a  reason: collision with other inner class name */
    /* compiled from: ClassicTypeCheckerContext.kt */
    public static final class C0422a {
        private C0422a() {
        }

        public /* synthetic */ C0422a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final g.b.a a(@NotNull c $this$classicSubstitutionSupertypePolicy, @NotNull kotlin.reflect.jvm.internal.impl.types.model.h type) {
            k.f($this$classicSubstitutionSupertypePolicy, "$this$classicSubstitutionSupertypePolicy");
            k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
            if (type instanceof i0) {
                return new C0423a($this$classicSubstitutionSupertypePolicy, v0.c.a((b0) type).c());
            }
            throw new IllegalArgumentException(b.b(type).toString());
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.types.checker.a$a$a  reason: collision with other inner class name */
        /* compiled from: ClassicTypeCheckerContext.kt */
        public static final class C0423a extends g.b.a {
            final /* synthetic */ c a;
            final /* synthetic */ TypeSubstitutor b;

            C0423a(c $receiver, TypeSubstitutor $captured_local_variable$1) {
                this.a = $receiver;
                this.b = $captured_local_variable$1;
            }

            @NotNull
            public kotlin.reflect.jvm.internal.impl.types.model.h a(@NotNull g context, @NotNull kotlin.reflect.jvm.internal.impl.types.model.g type) {
                k.f(context, "context");
                k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
                c cVar = this.a;
                TypeSubstitutor typeSubstitutor = this.b;
                kotlin.reflect.jvm.internal.impl.types.model.h T = cVar.T(type);
                if (T != null) {
                    b0 l = typeSubstitutor.l((b0) T, h1.INVARIANT);
                    k.b(l, "substitutor.safeSubstitu…ANT\n                    )");
                    kotlin.reflect.jvm.internal.impl.types.model.h a2 = cVar.a(l);
                    if (a2 == null) {
                        k.n();
                    }
                    return a2;
                }
                throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.KotlinType");
            }
        }
    }
}
