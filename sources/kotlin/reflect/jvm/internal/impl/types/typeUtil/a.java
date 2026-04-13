package kotlin.reflect.jvm.internal.impl.types.typeUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.f;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.s0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.types.a1;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.reflect.jvm.internal.impl.types.e1;
import kotlin.reflect.jvm.internal.impl.types.g1;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.n0;
import kotlin.reflect.jvm.internal.impl.types.v;
import kotlin.reflect.jvm.internal.impl.types.w0;
import kotlin.reflect.jvm.internal.impl.types.y0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: TypeUtils.kt */
public final class a {
    @NotNull
    public static final g f(@NotNull b0 $this$builtIns) {
        k.f($this$builtIns, "$this$builtIns");
        g j = $this$builtIns.I0().j();
        k.b(j, "constructor.builtIns");
        return j;
    }

    @NotNull
    public static final b0 l(@NotNull b0 $this$makeNullable) {
        k.f($this$makeNullable, "$this$makeNullable");
        b0 o = c1.o($this$makeNullable);
        k.b(o, "TypeUtils.makeNullable(this)");
        return o;
    }

    @NotNull
    public static final b0 k(@NotNull b0 $this$makeNotNullable) {
        k.f($this$makeNotNullable, "$this$makeNotNullable");
        b0 n = c1.n($this$makeNotNullable);
        k.b(n, "TypeUtils.makeNotNullable(this)");
        return n;
    }

    public static final boolean j(@NotNull b0 $this$isTypeParameter) {
        k.f($this$isTypeParameter, "$this$isTypeParameter");
        return c1.m($this$isTypeParameter);
    }

    public static final boolean h(@NotNull b0 $this$isSubtypeOf, @NotNull b0 superType) {
        k.f($this$isSubtypeOf, "$this$isSubtypeOf");
        k.f(superType, "superType");
        return kotlin.reflect.jvm.internal.impl.types.checker.g.a.d($this$isSubtypeOf, superType);
    }

    @NotNull
    public static final b0 m(@NotNull b0 $this$replaceAnnotations, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.g newAnnotations) {
        k.f($this$replaceAnnotations, "$this$replaceAnnotations");
        k.f(newAnnotations, "newAnnotations");
        if (!$this$replaceAnnotations.getAnnotations().isEmpty() || !newAnnotations.isEmpty()) {
            return $this$replaceAnnotations.L0().O0(newAnnotations);
        }
        return $this$replaceAnnotations;
    }

    @NotNull
    public static final w0 e(@NotNull b0 type, @NotNull h1 projectionKind, @Nullable t0 typeParameterDescriptor) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        k.f(projectionKind, "projectionKind");
        return new y0((typeParameterDescriptor != null ? typeParameterDescriptor.y() : null) == projectionKind ? h1.INVARIANT : projectionKind, type);
    }

    @NotNull
    public static final w0 a(@NotNull b0 $this$asTypeProjection) {
        k.f($this$asTypeProjection, "$this$asTypeProjection");
        return new y0($this$asTypeProjection);
    }

    public static final boolean c(@NotNull b0 $this$contains, @NotNull l<? super g1, Boolean> predicate) {
        k.f($this$contains, "$this$contains");
        k.f(predicate, "predicate");
        return c1.c($this$contains, predicate);
    }

    @NotNull
    public static final b0 n(@NotNull b0 $this$replaceArgumentsWithStarProjections) {
        i0 $this$replaceArgumentsWith$iv$iv;
        k.f($this$replaceArgumentsWithStarProjections, "$this$replaceArgumentsWithStarProjections");
        g1 unwrapped$iv = $this$replaceArgumentsWithStarProjections.L0();
        if (unwrapped$iv instanceof v) {
            i0 $this$replaceArgumentsWith$iv$iv2 = ((v) unwrapped$iv).Q0();
            if (!$this$replaceArgumentsWith$iv$iv2.I0().getParameters().isEmpty() && $this$replaceArgumentsWith$iv$iv2.I0().c() != null) {
                Iterable<t0> $this$mapTo$iv$iv$iv$iv = $this$replaceArgumentsWith$iv$iv2.I0().getParameters();
                k.b($this$mapTo$iv$iv$iv$iv, "constructor.parameters");
                List newArguments$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv$iv$iv, 10));
                for (t0 p1 : $this$mapTo$iv$iv$iv$iv) {
                    newArguments$iv$iv.add(new n0(p1));
                }
                $this$replaceArgumentsWith$iv$iv2 = a1.e($this$replaceArgumentsWith$iv$iv2, newArguments$iv$iv, (kotlin.reflect.jvm.internal.impl.descriptors.annotations.g) null, 2, (Object) null);
            }
            i0 $this$replaceArgumentsWith$iv$iv3 = ((v) unwrapped$iv).R0();
            if (!$this$replaceArgumentsWith$iv$iv3.I0().getParameters().isEmpty() && $this$replaceArgumentsWith$iv$iv3.I0().c() != null) {
                Iterable parameters = $this$replaceArgumentsWith$iv$iv3.I0().getParameters();
                k.b(parameters, "constructor.parameters");
                Iterable<t0> $this$map$iv$iv$iv = parameters;
                List newArguments$iv$iv2 = new ArrayList(r.r($this$map$iv$iv$iv, 10));
                for (t0 p12 : $this$map$iv$iv$iv) {
                    newArguments$iv$iv2.add(new n0(p12));
                }
                $this$replaceArgumentsWith$iv$iv3 = a1.e($this$replaceArgumentsWith$iv$iv3, newArguments$iv$iv2, (kotlin.reflect.jvm.internal.impl.descriptors.annotations.g) null, 2, (Object) null);
            }
            $this$replaceArgumentsWith$iv$iv = c0.d($this$replaceArgumentsWith$iv$iv2, $this$replaceArgumentsWith$iv$iv3);
        } else if (unwrapped$iv instanceof i0) {
            i0 $this$replaceArgumentsWith$iv$iv4 = (i0) unwrapped$iv;
            boolean isEmpty = $this$replaceArgumentsWith$iv$iv4.I0().getParameters().isEmpty();
            $this$replaceArgumentsWith$iv$iv = $this$replaceArgumentsWith$iv$iv4;
            if (!isEmpty) {
                h c = $this$replaceArgumentsWith$iv$iv4.I0().c();
                $this$replaceArgumentsWith$iv$iv = $this$replaceArgumentsWith$iv$iv4;
                if (c != null) {
                    Iterable<t0> $this$mapTo$iv$iv$iv$iv2 = $this$replaceArgumentsWith$iv$iv4.I0().getParameters();
                    k.b($this$mapTo$iv$iv$iv$iv2, "constructor.parameters");
                    List newArguments$iv$iv3 = new ArrayList(r.r($this$mapTo$iv$iv$iv$iv2, 10));
                    for (t0 p13 : $this$mapTo$iv$iv$iv$iv2) {
                        newArguments$iv$iv3.add(new n0(p13));
                    }
                    $this$replaceArgumentsWith$iv$iv = a1.e($this$replaceArgumentsWith$iv$iv4, newArguments$iv$iv3, (kotlin.reflect.jvm.internal.impl.descriptors.annotations.g) null, 2, (Object) null);
                }
            }
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return e1.b($this$replaceArgumentsWith$iv$iv, unwrapped$iv);
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.types.typeUtil.a$a  reason: collision with other inner class name */
    /* compiled from: TypeUtils.kt */
    public static final class C0428a extends kotlin.jvm.internal.l implements l<g1, Boolean> {
        public static final C0428a INSTANCE = new C0428a();

        C0428a() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((g1) obj));
        }

        public final boolean invoke(@NotNull g1 it) {
            k.f(it, "it");
            h c = it.I0().c();
            if (c != null) {
                return a.i(c);
            }
            return false;
        }
    }

    public static final boolean d(@NotNull b0 $this$containsTypeAliasParameters) {
        k.f($this$containsTypeAliasParameters, "$this$containsTypeAliasParameters");
        return c($this$containsTypeAliasParameters, C0428a.INSTANCE);
    }

    public static final boolean i(@NotNull h $this$isTypeAliasParameter) {
        k.f($this$isTypeAliasParameter, "$this$isTypeAliasParameter");
        return ($this$isTypeAliasParameter instanceof t0) && (((t0) $this$isTypeAliasParameter).b() instanceof s0);
    }

    /* compiled from: TypeUtils.kt */
    public static final class b extends kotlin.jvm.internal.l implements l<g1, Boolean> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((g1) obj));
        }

        public final boolean invoke(@NotNull g1 it) {
            k.f(it, "it");
            h it2 = it.I0().c();
            if (it2 == null) {
                return false;
            }
            if ((it2 instanceof s0) || (it2 instanceof t0)) {
                return true;
            }
            return false;
        }
    }

    public static final boolean o(@NotNull b0 $this$requiresTypeAliasExpansion) {
        k.f($this$requiresTypeAliasExpansion, "$this$requiresTypeAliasExpansion");
        return c($this$requiresTypeAliasExpansion, b.INSTANCE);
    }

    public static final boolean b(@NotNull g1 $this$canHaveUndefinedNullability) {
        k.f($this$canHaveUndefinedNullability, "$this$canHaveUndefinedNullability");
        $this$canHaveUndefinedNullability.I0();
        return ($this$canHaveUndefinedNullability.I0().c() instanceof t0) || ($this$canHaveUndefinedNullability instanceof kotlin.reflect.jvm.internal.impl.types.checker.k);
    }

    @NotNull
    public static final b0 g(@NotNull t0 $this$representativeUpperBound) {
        T t;
        k.f($this$representativeUpperBound, "$this$representativeUpperBound");
        List<b0> upperBounds = $this$representativeUpperBound.getUpperBounds();
        k.b(upperBounds, "upperBounds");
        if (!upperBounds.isEmpty()) {
            Iterable $this$firstOrNull$iv = $this$representativeUpperBound.getUpperBounds();
            k.b($this$firstOrNull$iv, "upperBounds");
            Iterator<T> it = $this$firstOrNull$iv.iterator();
            while (true) {
                t = null;
                if (!it.hasNext()) {
                    break;
                }
                T next = it.next();
                T c = ((b0) next).I0().c();
                if (c instanceof e) {
                    t = c;
                }
                e classDescriptor = (e) t;
                boolean z = false;
                if (!(classDescriptor == null || classDescriptor.h() == f.INTERFACE || classDescriptor.h() == f.ANNOTATION_CLASS)) {
                    z = true;
                    continue;
                }
                if (z) {
                    t = next;
                    break;
                }
            }
            b0 b0Var = (b0) t;
            if (b0Var != null) {
                return b0Var;
            }
            List<b0> upperBounds2 = $this$representativeUpperBound.getUpperBounds();
            k.b(upperBounds2, "upperBounds");
            Object S = y.S(upperBounds2);
            k.b(S, "upperBounds.first()");
            return (b0) S;
        }
        throw new AssertionError("Upper bounds should not be empty: " + $this$representativeUpperBound);
    }
}
