package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.Collection;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.s0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.x;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.b1;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.reflect.jvm.internal.impl.types.d0;
import kotlin.reflect.jvm.internal.impl.types.g1;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.l;
import kotlin.reflect.jvm.internal.impl.types.model.b;
import kotlin.reflect.jvm.internal.impl.types.model.d;
import kotlin.reflect.jvm.internal.impl.types.model.e;
import kotlin.reflect.jvm.internal.impl.types.model.f;
import kotlin.reflect.jvm.internal.impl.types.model.g;
import kotlin.reflect.jvm.internal.impl.types.model.h;
import kotlin.reflect.jvm.internal.impl.types.model.i;
import kotlin.reflect.jvm.internal.impl.types.model.j;
import kotlin.reflect.jvm.internal.impl.types.model.k;
import kotlin.reflect.jvm.internal.impl.types.model.n;
import kotlin.reflect.jvm.internal.impl.types.model.p;
import kotlin.reflect.jvm.internal.impl.types.r;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.types.v;
import kotlin.reflect.jvm.internal.impl.types.w0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: ClassicTypeSystemContext.kt */
public interface c extends b1, n {
    @Nullable
    h a(@NotNull g gVar);

    @NotNull
    k b(@NotNull h hVar);

    /* compiled from: ClassicTypeSystemContext.kt */
    public static final class a {
        public static boolean C(c cVar, @NotNull h hVar) {
            kotlin.jvm.internal.k.f(hVar, "$this$isClassType");
            return n.a.e(cVar, hVar);
        }

        public static boolean F(c cVar, @NotNull g gVar) {
            kotlin.jvm.internal.k.f(gVar, "$this$isDefinitelyNotNullType");
            return n.a.f(cVar, gVar);
        }

        public static boolean H(c cVar, @NotNull g gVar) {
            kotlin.jvm.internal.k.f(gVar, "$this$isDynamic");
            return n.a.g(cVar, gVar);
        }

        public static boolean L(c cVar, @NotNull h hVar) {
            kotlin.jvm.internal.k.f(hVar, "$this$isIntegerLiteralType");
            return n.a.h(cVar, hVar);
        }

        public static boolean O(c cVar, @NotNull g gVar) {
            kotlin.jvm.internal.k.f(gVar, "$this$isMarkedNullable");
            return b1.a.a(cVar, gVar);
        }

        public static boolean Q(c cVar, @NotNull g gVar) {
            kotlin.jvm.internal.k.f(gVar, "$this$isNothing");
            return n.a.i(cVar, gVar);
        }

        @NotNull
        public static h Z(c cVar, @NotNull g gVar) {
            kotlin.jvm.internal.k.f(gVar, "$this$lowerBoundIfFlexible");
            return n.a.j(cVar, gVar);
        }

        @NotNull
        public static g b0(c cVar, @NotNull g gVar) {
            kotlin.jvm.internal.k.f(gVar, "$this$makeNullable");
            return b1.a.b(cVar, gVar);
        }

        public static int f0(c cVar, @NotNull i iVar) {
            kotlin.jvm.internal.k.f(iVar, "$this$size");
            return n.a.k(cVar, iVar);
        }

        @NotNull
        public static k h0(c cVar, @NotNull g gVar) {
            kotlin.jvm.internal.k.f(gVar, "$this$typeConstructor");
            return n.a.l(cVar, gVar);
        }

        @Nullable
        public static List<h> j(c cVar, @NotNull h hVar, @NotNull k kVar) {
            kotlin.jvm.internal.k.f(hVar, "$this$fastCorrespondingSupertypes");
            kotlin.jvm.internal.k.f(kVar, "constructor");
            return n.a.a(cVar, hVar, kVar);
        }

        @NotNull
        public static j k(c cVar, @NotNull i iVar, int i) {
            kotlin.jvm.internal.k.f(iVar, "$this$get");
            return n.a.b(cVar, iVar, i);
        }

        @NotNull
        public static h k0(c cVar, @NotNull g gVar) {
            kotlin.jvm.internal.k.f(gVar, "$this$upperBoundIfFlexible");
            return n.a.m(cVar, gVar);
        }

        @Nullable
        public static j m(c cVar, @NotNull h hVar, int i) {
            kotlin.jvm.internal.k.f(hVar, "$this$getArgumentOrNull");
            return n.a.c(cVar, hVar, i);
        }

        public static boolean y(c cVar, @NotNull g gVar) {
            kotlin.jvm.internal.k.f(gVar, "$this$hasFlexibleNullability");
            return n.a.d(cVar, gVar);
        }

        public static boolean G(c $this, @NotNull k $this$isDenotable) {
            kotlin.jvm.internal.k.f($this$isDenotable, "$this$isDenotable");
            if ($this$isDenotable instanceof u0) {
                return ((u0) $this$isDenotable).d();
            }
            Object $this$errorMessage$iv = $this$isDenotable;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        public static boolean M(c $this, @NotNull k $this$isIntegerLiteralTypeConstructor) {
            kotlin.jvm.internal.k.f($this$isIntegerLiteralTypeConstructor, "$this$isIntegerLiteralTypeConstructor");
            if ($this$isIntegerLiteralTypeConstructor instanceof u0) {
                return $this$isIntegerLiteralTypeConstructor instanceof kotlin.reflect.jvm.internal.impl.resolve.constants.n;
            }
            Object $this$errorMessage$iv = $this$isIntegerLiteralTypeConstructor;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @NotNull
        public static Collection<g> e0(c $this, @NotNull h $this$possibleIntegerTypes) {
            kotlin.jvm.internal.k.f($this$possibleIntegerTypes, "$this$possibleIntegerTypes");
            k typeConstructor = $this.b($this$possibleIntegerTypes);
            if (typeConstructor instanceof kotlin.reflect.jvm.internal.impl.resolve.constants.n) {
                return ((kotlin.reflect.jvm.internal.impl.resolve.constants.n) typeConstructor).k();
            }
            Object $this$errorMessage$iv = $this$possibleIntegerTypes;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @NotNull
        public static h l0(c $this, @NotNull h $this$withNullability, boolean nullable) {
            kotlin.jvm.internal.k.f($this$withNullability, "$this$withNullability");
            if ($this$withNullability instanceof i0) {
                return ((i0) $this$withNullability).P0(nullable);
            }
            Object $this$errorMessage$iv = $this$withNullability;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        public static boolean J(c $this, @NotNull g $this$isError) {
            kotlin.jvm.internal.k.f($this$isError, "$this$isError");
            if ($this$isError instanceof b0) {
                return d0.a((b0) $this$isError);
            }
            Object $this$errorMessage$iv = $this$isError;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        public static boolean W(c $this, @NotNull h $this$isStubType) {
            kotlin.jvm.internal.k.f($this$isStubType, "$this$isStubType");
            if ($this$isStubType instanceof i0) {
                return false;
            }
            Object $this$errorMessage$iv = $this$isStubType;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @Nullable
        public static g a0(c $this, @NotNull kotlin.reflect.jvm.internal.impl.types.model.c $this$lowerType) {
            kotlin.jvm.internal.k.f($this$lowerType, "$this$lowerType");
            if ($this$lowerType instanceof k) {
                return ((k) $this$lowerType).S0();
            }
            Object $this$errorMessage$iv = $this$lowerType;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        public static boolean N(c $this, @NotNull k $this$isIntersection) {
            kotlin.jvm.internal.k.f($this$isIntersection, "$this$isIntersection");
            if ($this$isIntersection instanceof u0) {
                return $this$isIntersection instanceof kotlin.reflect.jvm.internal.impl.types.a0;
            }
            Object $this$errorMessage$iv = $this$isIntersection;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        public static boolean z(c $this, @NotNull h a, @NotNull h b) {
            kotlin.jvm.internal.k.f(a, "a");
            kotlin.jvm.internal.k.f(b, "b");
            if (!(a instanceof i0)) {
                Object $this$errorMessage$iv = a;
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
            } else if (b instanceof i0) {
                return ((i0) a).H0() == ((i0) b).H0();
            } else {
                Object $this$errorMessage$iv2 = b;
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv2 + ", " + a0.b($this$errorMessage$iv2.getClass())).toString());
            }
        }

        @Nullable
        public static h g(c $this, @NotNull g $this$asSimpleType) {
            kotlin.jvm.internal.k.f($this$asSimpleType, "$this$asSimpleType");
            if ($this$asSimpleType instanceof b0) {
                g1 L0 = ((b0) $this$asSimpleType).L0();
                if (!(L0 instanceof i0)) {
                    L0 = null;
                }
                return (i0) L0;
            }
            Object $this$errorMessage$iv = $this$asSimpleType;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @Nullable
        public static f f(c $this, @NotNull g $this$asFlexibleType) {
            kotlin.jvm.internal.k.f($this$asFlexibleType, "$this$asFlexibleType");
            if ($this$asFlexibleType instanceof b0) {
                g1 L0 = ((b0) $this$asFlexibleType).L0();
                if (!(L0 instanceof v)) {
                    L0 = null;
                }
                return (v) L0;
            }
            Object $this$errorMessage$iv = $this$asFlexibleType;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @Nullable
        public static e e(c $this, @NotNull f $this$asDynamicType) {
            kotlin.jvm.internal.k.f($this$asDynamicType, "$this$asDynamicType");
            if ($this$asDynamicType instanceof v) {
                return (r) (!($this$asDynamicType instanceof r) ? null : $this$asDynamicType);
            }
            Object $this$errorMessage$iv = $this$asDynamicType;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @NotNull
        public static h j0(c $this, @NotNull f $this$upperBound) {
            kotlin.jvm.internal.k.f($this$upperBound, "$this$upperBound");
            if ($this$upperBound instanceof v) {
                return ((v) $this$upperBound).R0();
            }
            Object $this$errorMessage$iv = $this$upperBound;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @NotNull
        public static h Y(c $this, @NotNull f $this$lowerBound) {
            kotlin.jvm.internal.k.f($this$lowerBound, "$this$lowerBound");
            if ($this$lowerBound instanceof v) {
                return ((v) $this$lowerBound).Q0();
            }
            Object $this$errorMessage$iv = $this$lowerBound;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @Nullable
        public static kotlin.reflect.jvm.internal.impl.types.model.c c(c $this, @NotNull h $this$asCapturedType) {
            kotlin.jvm.internal.k.f($this$asCapturedType, "$this$asCapturedType");
            if ($this$asCapturedType instanceof i0) {
                return (k) (!($this$asCapturedType instanceof k) ? null : $this$asCapturedType);
            }
            Object $this$errorMessage$iv = $this$asCapturedType;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @Nullable
        public static d d(c $this, @NotNull h $this$asDefinitelyNotNullType) {
            kotlin.jvm.internal.k.f($this$asDefinitelyNotNullType, "$this$asDefinitelyNotNullType");
            if ($this$asDefinitelyNotNullType instanceof i0) {
                return (l) (!($this$asDefinitelyNotNullType instanceof l) ? null : $this$asDefinitelyNotNullType);
            }
            Object $this$errorMessage$iv = $this$asDefinitelyNotNullType;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        public static boolean P(c $this, @NotNull h $this$isMarkedNullable) {
            kotlin.jvm.internal.k.f($this$isMarkedNullable, "$this$isMarkedNullable");
            if ($this$isMarkedNullable instanceof i0) {
                return ((i0) $this$isMarkedNullable).J0();
            }
            Object $this$errorMessage$iv = $this$isMarkedNullable;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @NotNull
        public static k i0(c $this, @NotNull h $this$typeConstructor) {
            kotlin.jvm.internal.k.f($this$typeConstructor, "$this$typeConstructor");
            if ($this$typeConstructor instanceof i0) {
                return ((i0) $this$typeConstructor).I0();
            }
            Object $this$errorMessage$iv = $this$typeConstructor;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        public static int a(c $this, @NotNull g $this$argumentsCount) {
            kotlin.jvm.internal.k.f($this$argumentsCount, "$this$argumentsCount");
            if ($this$argumentsCount instanceof b0) {
                return ((b0) $this$argumentsCount).H0().size();
            }
            Object $this$errorMessage$iv = $this$argumentsCount;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @NotNull
        public static j l(c $this, @NotNull g $this$getArgument, int index) {
            kotlin.jvm.internal.k.f($this$getArgument, "$this$getArgument");
            if ($this$getArgument instanceof b0) {
                return ((b0) $this$getArgument).H0().get(index);
            }
            Object $this$errorMessage$iv = $this$getArgument;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        public static boolean V(c $this, @NotNull j $this$isStarProjection) {
            kotlin.jvm.internal.k.f($this$isStarProjection, "$this$isStarProjection");
            if ($this$isStarProjection instanceof w0) {
                return ((w0) $this$isStarProjection).b();
            }
            Object $this$errorMessage$iv = $this$isStarProjection;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @NotNull
        public static p v(c $this, @NotNull j $this$getVariance) {
            kotlin.jvm.internal.k.f($this$getVariance, "$this$getVariance");
            if ($this$getVariance instanceof w0) {
                h1 c = ((w0) $this$getVariance).c();
                kotlin.jvm.internal.k.b(c, "this.projectionKind");
                return e.a(c);
            }
            Object $this$errorMessage$iv = $this$getVariance;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @NotNull
        public static g t(c $this, @NotNull j $this$getType) {
            kotlin.jvm.internal.k.f($this$getType, "$this$getType");
            if ($this$getType instanceof w0) {
                return ((w0) $this$getType).getType().L0();
            }
            Object $this$errorMessage$iv = $this$getType;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        public static int d0(c $this, @NotNull k $this$parametersCount) {
            kotlin.jvm.internal.k.f($this$parametersCount, "$this$parametersCount");
            if ($this$parametersCount instanceof u0) {
                return ((u0) $this$parametersCount).getParameters().size();
            }
            Object $this$errorMessage$iv = $this$parametersCount;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @NotNull
        public static kotlin.reflect.jvm.internal.impl.types.model.l o(c $this, @NotNull k $this$getParameter, int index) {
            kotlin.jvm.internal.k.f($this$getParameter, "$this$getParameter");
            if ($this$getParameter instanceof u0) {
                t0 t0Var = ((u0) $this$getParameter).getParameters().get(index);
                kotlin.jvm.internal.k.b(t0Var, "this.parameters[index]");
                return t0Var;
            }
            Object $this$errorMessage$iv = $this$getParameter;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @NotNull
        public static Collection<g> g0(c $this, @NotNull k $this$supertypes) {
            kotlin.jvm.internal.k.f($this$supertypes, "$this$supertypes");
            if ($this$supertypes instanceof u0) {
                Collection<b0> b = ((u0) $this$supertypes).b();
                kotlin.jvm.internal.k.b(b, "this.supertypes");
                return b;
            }
            Object $this$errorMessage$iv = $this$supertypes;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @NotNull
        public static p w(c $this, @NotNull kotlin.reflect.jvm.internal.impl.types.model.l $this$getVariance) {
            kotlin.jvm.internal.k.f($this$getVariance, "$this$getVariance");
            if ($this$getVariance instanceof t0) {
                h1 y = ((t0) $this$getVariance).y();
                kotlin.jvm.internal.k.b(y, "this.variance");
                return e.a(y);
            }
            Object $this$errorMessage$iv = $this$getVariance;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        public static boolean I(c $this, @NotNull k c1, @NotNull k c2) {
            kotlin.jvm.internal.k.f(c1, "c1");
            kotlin.jvm.internal.k.f(c2, "c2");
            if (!(c1 instanceof u0)) {
                Object $this$errorMessage$iv = c1;
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
            } else if (c2 instanceof u0) {
                return kotlin.jvm.internal.k.a(c1, c2);
            } else {
                Object $this$errorMessage$iv2 = c2;
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv2 + ", " + a0.b($this$errorMessage$iv2.getClass())).toString());
            }
        }

        public static boolean D(c $this, @NotNull k $this$isClassTypeConstructor) {
            kotlin.jvm.internal.k.f($this$isClassTypeConstructor, "$this$isClassTypeConstructor");
            if ($this$isClassTypeConstructor instanceof u0) {
                return ((u0) $this$isClassTypeConstructor).c() instanceof kotlin.reflect.jvm.internal.impl.descriptors.e;
            }
            Object $this$errorMessage$iv = $this$isClassTypeConstructor;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        public static boolean E(c $this, @NotNull k $this$isCommonFinalClassConstructor) {
            kotlin.jvm.internal.k.f($this$isCommonFinalClassConstructor, "$this$isCommonFinalClassConstructor");
            if ($this$isCommonFinalClassConstructor instanceof u0) {
                kotlin.reflect.jvm.internal.impl.descriptors.h c = ((u0) $this$isCommonFinalClassConstructor).c();
                if (!(c instanceof kotlin.reflect.jvm.internal.impl.descriptors.e)) {
                    c = null;
                }
                kotlin.reflect.jvm.internal.impl.descriptors.e classDescriptor = (kotlin.reflect.jvm.internal.impl.descriptors.e) c;
                if (classDescriptor == null || !x.a(classDescriptor) || classDescriptor.h() == kotlin.reflect.jvm.internal.impl.descriptors.f.ENUM_ENTRY || classDescriptor.h() == kotlin.reflect.jvm.internal.impl.descriptors.f.ANNOTATION_CLASS) {
                    return false;
                }
                return true;
            }
            Object $this$errorMessage$iv = $this$isCommonFinalClassConstructor;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @NotNull
        public static i b(c $this, @NotNull h $this$asArgumentList) {
            kotlin.jvm.internal.k.f($this$asArgumentList, "$this$asArgumentList");
            if ($this$asArgumentList instanceof i0) {
                return (i) $this$asArgumentList;
            }
            Object $this$errorMessage$iv = $this$asArgumentList;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @Nullable
        public static h i(c $this, @NotNull h type, @NotNull b status) {
            kotlin.jvm.internal.k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
            kotlin.jvm.internal.k.f(status, "status");
            if (type instanceof i0) {
                return m.a((i0) type, status);
            }
            Object $this$errorMessage$iv = type;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        public static boolean B(c $this, @NotNull k $this$isAnyConstructor) {
            kotlin.jvm.internal.k.f($this$isAnyConstructor, "$this$isAnyConstructor");
            if ($this$isAnyConstructor instanceof u0) {
                return kotlin.reflect.jvm.internal.impl.builtins.g.H0((u0) $this$isAnyConstructor, kotlin.reflect.jvm.internal.impl.builtins.g.h.a);
            }
            Object $this$errorMessage$iv = $this$isAnyConstructor;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        public static boolean R(c $this, @NotNull k $this$isNothingConstructor) {
            kotlin.jvm.internal.k.f($this$isNothingConstructor, "$this$isNothingConstructor");
            if ($this$isNothingConstructor instanceof u0) {
                return kotlin.reflect.jvm.internal.impl.builtins.g.H0((u0) $this$isNothingConstructor, kotlin.reflect.jvm.internal.impl.builtins.g.h.b);
            }
            Object $this$errorMessage$iv = $this$isNothingConstructor;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @NotNull
        public static j h(c $this, @NotNull g $this$asTypeArgument) {
            kotlin.jvm.internal.k.f($this$asTypeArgument, "$this$asTypeArgument");
            if ($this$asTypeArgument instanceof b0) {
                return kotlin.reflect.jvm.internal.impl.types.typeUtil.a.a((b0) $this$asTypeArgument);
            }
            Object $this$errorMessage$iv = $this$asTypeArgument;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        public static boolean U(c $this, @NotNull h $this$isSingleClassifierType) {
            kotlin.jvm.internal.k.f($this$isSingleClassifierType, "$this$isSingleClassifierType");
            if ($this$isSingleClassifierType instanceof i0) {
                return !d0.a((b0) $this$isSingleClassifierType) && !(((i0) $this$isSingleClassifierType).I0().c() instanceof s0) && (((i0) $this$isSingleClassifierType).I0().c() != null || ($this$isSingleClassifierType instanceof kotlin.reflect.jvm.internal.impl.resolve.calls.inference.a) || ($this$isSingleClassifierType instanceof k) || ($this$isSingleClassifierType instanceof l) || (((i0) $this$isSingleClassifierType).I0() instanceof kotlin.reflect.jvm.internal.impl.resolve.constants.n));
            }
            Object $this$errorMessage$iv = $this$isSingleClassifierType;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @NotNull
        public static g A(c $this, @NotNull List<? extends g> types) {
            kotlin.jvm.internal.k.f(types, "types");
            return f.a(types);
        }

        @NotNull
        public static kotlin.reflect.jvm.internal.impl.types.g c0(c $this, boolean errorTypesEqualToAnything, boolean stubTypesEqualToAnything) {
            return new a(errorTypesEqualToAnything, stubTypesEqualToAnything, false, (i) null, 12, (DefaultConstructorMarker) null);
        }

        public static boolean S(c $this, @NotNull g $this$isNullableType) {
            kotlin.jvm.internal.k.f($this$isNullableType, "$this$isNullableType");
            if ($this$isNullableType instanceof b0) {
                return c1.l((b0) $this$isNullableType);
            }
            Object $this$errorMessage$iv = $this$isNullableType;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        public static boolean T(c $this, @NotNull h $this$isPrimitiveType) {
            kotlin.jvm.internal.k.f($this$isPrimitiveType, "$this$isPrimitiveType");
            if ($this$isPrimitiveType instanceof b0) {
                return kotlin.reflect.jvm.internal.impl.builtins.g.C0((b0) $this$isPrimitiveType);
            }
            Object $this$errorMessage$iv = $this$isPrimitiveType;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        public static boolean x(c $this, @NotNull g $this$hasAnnotation, @NotNull kotlin.reflect.jvm.internal.impl.name.b fqName) {
            kotlin.jvm.internal.k.f($this$hasAnnotation, "$this$hasAnnotation");
            kotlin.jvm.internal.k.f(fqName, "fqName");
            if ($this$hasAnnotation instanceof b0) {
                return ((b0) $this$hasAnnotation).getAnnotations().I(fqName);
            }
            Object $this$errorMessage$iv = $this$hasAnnotation;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @Nullable
        public static kotlin.reflect.jvm.internal.impl.types.model.l u(c $this, @NotNull k $this$getTypeParameterClassifier) {
            kotlin.jvm.internal.k.f($this$getTypeParameterClassifier, "$this$getTypeParameterClassifier");
            if ($this$getTypeParameterClassifier instanceof u0) {
                kotlin.reflect.jvm.internal.impl.descriptors.h c = ((u0) $this$getTypeParameterClassifier).c();
                if (!(c instanceof t0)) {
                    c = null;
                }
                return (t0) c;
            }
            Object $this$errorMessage$iv = $this$getTypeParameterClassifier;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        public static boolean K(c $this, @NotNull k $this$isInlineClass) {
            kotlin.jvm.internal.k.f($this$isInlineClass, "$this$isInlineClass");
            if ($this$isInlineClass instanceof u0) {
                kotlin.reflect.jvm.internal.impl.descriptors.h c = ((u0) $this$isInlineClass).c();
                if (!(c instanceof kotlin.reflect.jvm.internal.impl.descriptors.e)) {
                    c = null;
                }
                kotlin.reflect.jvm.internal.impl.descriptors.e eVar = (kotlin.reflect.jvm.internal.impl.descriptors.e) c;
                return eVar != null && eVar.isInline();
            }
            Object $this$errorMessage$iv = $this$isInlineClass;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @NotNull
        public static g r(c $this, @NotNull kotlin.reflect.jvm.internal.impl.types.model.l $this$getRepresentativeUpperBound) {
            kotlin.jvm.internal.k.f($this$getRepresentativeUpperBound, "$this$getRepresentativeUpperBound");
            if ($this$getRepresentativeUpperBound instanceof t0) {
                return kotlin.reflect.jvm.internal.impl.types.typeUtil.a.g((t0) $this$getRepresentativeUpperBound);
            }
            Object $this$errorMessage$iv = $this$getRepresentativeUpperBound;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @Nullable
        public static g s(c $this, @NotNull g $this$getSubstitutedUnderlyingType) {
            kotlin.jvm.internal.k.f($this$getSubstitutedUnderlyingType, "$this$getSubstitutedUnderlyingType");
            if ($this$getSubstitutedUnderlyingType instanceof b0) {
                return kotlin.reflect.jvm.internal.impl.resolve.e.e((b0) $this$getSubstitutedUnderlyingType);
            }
            Object $this$errorMessage$iv = $this$getSubstitutedUnderlyingType;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @Nullable
        public static kotlin.reflect.jvm.internal.impl.builtins.h q(c $this, @NotNull k $this$getPrimitiveType) {
            kotlin.jvm.internal.k.f($this$getPrimitiveType, "$this$getPrimitiveType");
            if ($this$getPrimitiveType instanceof u0) {
                kotlin.reflect.jvm.internal.impl.descriptors.h c = ((u0) $this$getPrimitiveType).c();
                if (c != null) {
                    return kotlin.reflect.jvm.internal.impl.builtins.g.U((kotlin.reflect.jvm.internal.impl.descriptors.e) c);
                }
                throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            }
            Object $this$errorMessage$iv = $this$getPrimitiveType;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @Nullable
        public static kotlin.reflect.jvm.internal.impl.builtins.h p(c $this, @NotNull k $this$getPrimitiveArrayType) {
            kotlin.jvm.internal.k.f($this$getPrimitiveArrayType, "$this$getPrimitiveArrayType");
            if ($this$getPrimitiveArrayType instanceof u0) {
                kotlin.reflect.jvm.internal.impl.descriptors.h c = ((u0) $this$getPrimitiveArrayType).c();
                if (c != null) {
                    return kotlin.reflect.jvm.internal.impl.builtins.g.Q((kotlin.reflect.jvm.internal.impl.descriptors.e) c);
                }
                throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            }
            Object $this$errorMessage$iv = $this$getPrimitiveArrayType;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        public static boolean X(c $this, @NotNull k $this$isUnderKotlinPackage) {
            kotlin.jvm.internal.k.f($this$isUnderKotlinPackage, "$this$isUnderKotlinPackage");
            if ($this$isUnderKotlinPackage instanceof u0) {
                m p1 = ((u0) $this$isUnderKotlinPackage).c();
                return p1 != null && kotlin.reflect.jvm.internal.impl.builtins.g.I0(p1);
            }
            Object $this$errorMessage$iv = $this$isUnderKotlinPackage;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }

        @NotNull
        public static kotlin.reflect.jvm.internal.impl.name.c n(c $this, @NotNull k $this$getClassFqNameUnsafe) {
            kotlin.jvm.internal.k.f($this$getClassFqNameUnsafe, "$this$getClassFqNameUnsafe");
            if ($this$getClassFqNameUnsafe instanceof u0) {
                kotlin.reflect.jvm.internal.impl.descriptors.h c = ((u0) $this$getClassFqNameUnsafe).c();
                if (c != null) {
                    return kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.k((kotlin.reflect.jvm.internal.impl.descriptors.e) c);
                }
                throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            }
            Object $this$errorMessage$iv = $this$getClassFqNameUnsafe;
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + $this$errorMessage$iv + ", " + a0.b($this$errorMessage$iv.getClass())).toString());
        }
    }
}
