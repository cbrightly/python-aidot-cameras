package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.reflect.jvm.internal.impl.builtins.h;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.types.checker.c;
import kotlin.reflect.jvm.internal.impl.types.model.d;
import kotlin.reflect.jvm.internal.impl.types.model.e;
import kotlin.reflect.jvm.internal.impl.types.model.f;
import kotlin.reflect.jvm.internal.impl.types.model.g;
import kotlin.reflect.jvm.internal.impl.types.model.i;
import kotlin.reflect.jvm.internal.impl.types.model.j;
import kotlin.reflect.jvm.internal.impl.types.model.k;
import kotlin.reflect.jvm.internal.impl.types.model.l;
import kotlin.reflect.jvm.internal.impl.types.model.p;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: NewKotlinTypeChecker.kt */
public final class r implements c {
    public static final r a = new r();

    private r() {
    }

    public boolean A(@NotNull k c1, @NotNull k c2) {
        kotlin.jvm.internal.k.f(c1, "c1");
        kotlin.jvm.internal.k.f(c2, "c2");
        return c.a.I(this, c1, c2);
    }

    @Nullable
    public h C(@NotNull k $this$getPrimitiveArrayType) {
        kotlin.jvm.internal.k.f($this$getPrimitiveArrayType, "$this$getPrimitiveArrayType");
        return c.a.p(this, $this$getPrimitiveArrayType);
    }

    @NotNull
    public g D(@NotNull l $this$getRepresentativeUpperBound) {
        kotlin.jvm.internal.k.f($this$getRepresentativeUpperBound, "$this$getRepresentativeUpperBound");
        return c.a.r(this, $this$getRepresentativeUpperBound);
    }

    public boolean E(@NotNull k $this$isNothingConstructor) {
        kotlin.jvm.internal.k.f($this$isNothingConstructor, "$this$isNothingConstructor");
        return c.a.R(this, $this$isNothingConstructor);
    }

    @NotNull
    public k H(@NotNull g $this$typeConstructor) {
        kotlin.jvm.internal.k.f($this$typeConstructor, "$this$typeConstructor");
        return c.a.h0(this, $this$typeConstructor);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.name.c J(@NotNull k $this$getClassFqNameUnsafe) {
        kotlin.jvm.internal.k.f($this$getClassFqNameUnsafe, "$this$getClassFqNameUnsafe");
        return c.a.n(this, $this$getClassFqNameUnsafe);
    }

    @Nullable
    public f K(@NotNull g $this$asFlexibleType) {
        kotlin.jvm.internal.k.f($this$asFlexibleType, "$this$asFlexibleType");
        return c.a.f(this, $this$asFlexibleType);
    }

    public boolean N(@NotNull k $this$isClassTypeConstructor) {
        kotlin.jvm.internal.k.f($this$isClassTypeConstructor, "$this$isClassTypeConstructor");
        return c.a.D(this, $this$isClassTypeConstructor);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.types.model.h O(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h $this$withNullability, boolean nullable) {
        kotlin.jvm.internal.k.f($this$withNullability, "$this$withNullability");
        return c.a.l0(this, $this$withNullability, nullable);
    }

    @Nullable
    public g Q(@NotNull g $this$getSubstitutedUnderlyingType) {
        kotlin.jvm.internal.k.f($this$getSubstitutedUnderlyingType, "$this$getSubstitutedUnderlyingType");
        return c.a.s(this, $this$getSubstitutedUnderlyingType);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.types.model.h R(@NotNull f $this$upperBound) {
        kotlin.jvm.internal.k.f($this$upperBound, "$this$upperBound");
        return c.a.j0(this, $this$upperBound);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.types.model.h T(@NotNull g $this$lowerBoundIfFlexible) {
        kotlin.jvm.internal.k.f($this$lowerBoundIfFlexible, "$this$lowerBoundIfFlexible");
        return c.a.Z(this, $this$lowerBoundIfFlexible);
    }

    @NotNull
    public p U(@NotNull j $this$getVariance) {
        kotlin.jvm.internal.k.f($this$getVariance, "$this$getVariance");
        return c.a.v(this, $this$getVariance);
    }

    public boolean V(@NotNull g $this$hasAnnotation, @NotNull b fqName) {
        kotlin.jvm.internal.k.f($this$hasAnnotation, "$this$hasAnnotation");
        kotlin.jvm.internal.k.f(fqName, "fqName");
        return c.a.x(this, $this$hasAnnotation, fqName);
    }

    public boolean W(@NotNull g $this$isMarkedNullable) {
        kotlin.jvm.internal.k.f($this$isMarkedNullable, "$this$isMarkedNullable");
        return c.a.O(this, $this$isMarkedNullable);
    }

    @Nullable
    public d Y(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h $this$asDefinitelyNotNullType) {
        kotlin.jvm.internal.k.f($this$asDefinitelyNotNullType, "$this$asDefinitelyNotNullType");
        return c.a.d(this, $this$asDefinitelyNotNullType);
    }

    @Nullable
    public kotlin.reflect.jvm.internal.impl.types.model.h a(@NotNull g $this$asSimpleType) {
        kotlin.jvm.internal.k.f($this$asSimpleType, "$this$asSimpleType");
        return c.a.g(this, $this$asSimpleType);
    }

    @NotNull
    public g a0(@NotNull j $this$getType) {
        kotlin.jvm.internal.k.f($this$getType, "$this$getType");
        return c.a.t(this, $this$getType);
    }

    @NotNull
    public k b(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h $this$typeConstructor) {
        kotlin.jvm.internal.k.f($this$typeConstructor, "$this$typeConstructor");
        return c.a.i0(this, $this$typeConstructor);
    }

    @Nullable
    public e b0(@NotNull f $this$asDynamicType) {
        kotlin.jvm.internal.k.f($this$asDynamicType, "$this$asDynamicType");
        return c.a.e(this, $this$asDynamicType);
    }

    public boolean c(@NotNull k $this$isUnderKotlinPackage) {
        kotlin.jvm.internal.k.f($this$isUnderKotlinPackage, "$this$isUnderKotlinPackage");
        return c.a.X(this, $this$isUnderKotlinPackage);
    }

    @NotNull
    public g c0(@NotNull g $this$makeNullable) {
        kotlin.jvm.internal.k.f($this$makeNullable, "$this$makeNullable");
        return c.a.b0(this, $this$makeNullable);
    }

    public int d(@NotNull g $this$argumentsCount) {
        kotlin.jvm.internal.k.f($this$argumentsCount, "$this$argumentsCount");
        return c.a.a(this, $this$argumentsCount);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.types.g d0(boolean errorTypesEqualToAnything, boolean stubTypesEqualToAnything) {
        return c.a.c0(this, errorTypesEqualToAnything, stubTypesEqualToAnything);
    }

    @NotNull
    public j f(@NotNull i $this$get, int index) {
        kotlin.jvm.internal.k.f($this$get, "$this$get");
        return c.a.k(this, $this$get, index);
    }

    @Nullable
    public l h(@NotNull k $this$getTypeParameterClassifier) {
        kotlin.jvm.internal.k.f($this$getTypeParameterClassifier, "$this$getTypeParameterClassifier");
        return c.a.u(this, $this$getTypeParameterClassifier);
    }

    public boolean i(@NotNull j $this$isStarProjection) {
        kotlin.jvm.internal.k.f($this$isStarProjection, "$this$isStarProjection");
        return c.a.V(this, $this$isStarProjection);
    }

    public int l(@NotNull i $this$size) {
        kotlin.jvm.internal.k.f($this$size, "$this$size");
        return c.a.f0(this, $this$size);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.types.model.h m(@NotNull g $this$upperBoundIfFlexible) {
        kotlin.jvm.internal.k.f($this$upperBoundIfFlexible, "$this$upperBoundIfFlexible");
        return c.a.k0(this, $this$upperBoundIfFlexible);
    }

    public boolean n(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h $this$isMarkedNullable) {
        kotlin.jvm.internal.k.f($this$isMarkedNullable, "$this$isMarkedNullable");
        return c.a.P(this, $this$isMarkedNullable);
    }

    public boolean p(@NotNull k $this$isInlineClass) {
        kotlin.jvm.internal.k.f($this$isInlineClass, "$this$isInlineClass");
        return c.a.K(this, $this$isInlineClass);
    }

    @NotNull
    public j r(@NotNull g $this$getArgument, int index) {
        kotlin.jvm.internal.k.f($this$getArgument, "$this$getArgument");
        return c.a.l(this, $this$getArgument, index);
    }

    @Nullable
    public h s(@NotNull k $this$getPrimitiveType) {
        kotlin.jvm.internal.k.f($this$getPrimitiveType, "$this$getPrimitiveType");
        return c.a.q(this, $this$getPrimitiveType);
    }

    public boolean v(@NotNull g $this$isNullableType) {
        kotlin.jvm.internal.k.f($this$isNullableType, "$this$isNullableType");
        return c.a.S(this, $this$isNullableType);
    }

    public boolean w(@NotNull k $this$isIntegerLiteralTypeConstructor) {
        kotlin.jvm.internal.k.f($this$isIntegerLiteralTypeConstructor, "$this$isIntegerLiteralTypeConstructor");
        return c.a.M(this, $this$isIntegerLiteralTypeConstructor);
    }

    public boolean x(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h a2, @NotNull kotlin.reflect.jvm.internal.impl.types.model.h b) {
        kotlin.jvm.internal.k.f(a2, "a");
        kotlin.jvm.internal.k.f(b, "b");
        return c.a.z(this, a2, b);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.types.model.h y(@NotNull f $this$lowerBound) {
        kotlin.jvm.internal.k.f($this$lowerBound, "$this$lowerBound");
        return c.a.Y(this, $this$lowerBound);
    }

    public boolean z(@NotNull kotlin.reflect.jvm.internal.impl.types.model.h $this$isPrimitiveType) {
        kotlin.jvm.internal.k.f($this$isPrimitiveType, "$this$isPrimitiveType");
        return c.a.T(this, $this$isPrimitiveType);
    }
}
