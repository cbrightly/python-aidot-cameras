package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.c;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.load.java.s;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.b1;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.checker.r;
import kotlin.reflect.jvm.internal.impl.types.d0;
import kotlin.reflect.jvm.internal.impl.types.e1;
import kotlin.reflect.jvm.internal.impl.types.g1;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.model.g;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.a;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.types.v;
import kotlin.reflect.jvm.internal.impl.types.w0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: typeEnhancement.kt */
public final class t {
    private static final b a;
    private static final b b;

    @Nullable
    public static final b0 b(@NotNull b0 $this$enhance, @NotNull l<? super Integer, d> qualifiers) {
        k.f($this$enhance, "$this$enhance");
        k.f(qualifiers, "qualifiers");
        return e($this$enhance.L0(), qualifiers, 0).c();
    }

    public static final boolean i(@NotNull b0 $this$hasEnhancedNullability) {
        k.f($this$hasEnhancedNullability, "$this$hasEnhancedNullability");
        return j(r.a, $this$hasEnhancedNullability);
    }

    public static final boolean j(@NotNull b1 $this$hasEnhancedNullability, @NotNull g type) {
        k.f($this$hasEnhancedNullability, "$this$hasEnhancedNullability");
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        b bVar = s.k;
        k.b(bVar, "JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION");
        return $this$hasEnhancedNullability.V(type, bVar);
    }

    private static final k e(@NotNull g1 $this$enhancePossiblyFlexible, l<? super Integer, d> qualifiers, int index) {
        g1 type;
        g1 g1Var;
        boolean wereChanges = false;
        if (d0.a($this$enhancePossiblyFlexible)) {
            return new k($this$enhancePossiblyFlexible, 1, false);
        }
        if ($this$enhancePossiblyFlexible instanceof v) {
            o lowerResult = c(((v) $this$enhancePossiblyFlexible).Q0(), qualifiers, index, q.FLEXIBLE_LOWER);
            o upperResult = c(((v) $this$enhancePossiblyFlexible).R0(), qualifiers, index, q.FLEXIBLE_UPPER);
            if (lowerResult.a() == upperResult.a()) {
                if (lowerResult.d() || upperResult.d()) {
                    wereChanges = true;
                }
                b0 enhancement = e1.a(lowerResult.b());
                if (enhancement == null) {
                    enhancement = e1.a(upperResult.b());
                }
                if (!wereChanges) {
                    type = $this$enhancePossiblyFlexible;
                } else {
                    if ($this$enhancePossiblyFlexible instanceof kotlin.reflect.jvm.internal.impl.load.java.lazy.types.g) {
                        g1Var = new kotlin.reflect.jvm.internal.impl.load.java.lazy.types.g(lowerResult.b(), upperResult.b());
                    } else {
                        g1Var = c0.d(lowerResult.b(), upperResult.b());
                    }
                    type = e1.d(g1Var, enhancement);
                }
                return new k(type, lowerResult.a(), wereChanges);
            }
            throw new AssertionError("Different tree sizes of bounds: " + "lower = (" + ((v) $this$enhancePossiblyFlexible).Q0() + ", " + lowerResult.a() + "), " + "upper = (" + ((v) $this$enhancePossiblyFlexible).R0() + ", " + upperResult.a() + ')');
        } else if ($this$enhancePossiblyFlexible instanceof i0) {
            return c((i0) $this$enhancePossiblyFlexible, qualifiers, index, q.INFLEXIBLE);
        } else {
            throw new NoWhenBranchMatchedException();
        }
    }

    private static final o c(@NotNull i0 $this$enhanceInflexible, l<? super Integer, d> qualifiers, int index, q position) {
        Iterable $this$mapIndexedTo$iv$iv;
        boolean shouldEnhance;
        w0 w0Var;
        i0 i0Var = $this$enhanceInflexible;
        l<? super Integer, d> lVar = qualifiers;
        q qVar = position;
        boolean shouldEnhance2 = l(position);
        if (!shouldEnhance2 && $this$enhanceInflexible.H0().isEmpty()) {
            return new o(i0Var, 1, false);
        }
        h originalClass = $this$enhanceInflexible.I0().c();
        if (originalClass != null) {
            k.b(originalClass, "constructor.declarationD…pleResult(this, 1, false)");
            d effectiveQualifiers = lVar.invoke(Integer.valueOf(index));
            c<h> d = d(originalClass, effectiveQualifiers, qVar);
            h enhancedClassifier = d.a();
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g enhancedMutabilityAnnotations = d.b();
            u0 typeConstructor = enhancedClassifier.i();
            k.b(typeConstructor, "enhancedClassifier.typeConstructor");
            int globalArgIndex = index + 1;
            boolean wereChanges = enhancedMutabilityAnnotations != null;
            Iterable H0 = $this$enhanceInflexible.H0();
            List arrayList = new ArrayList(kotlin.collections.r.r(H0, 10));
            Iterable $this$mapIndexedTo$iv$iv2 = H0;
            boolean z = wereChanges;
            int globalArgIndex2 = globalArgIndex;
            int globalArgIndex3 = 0;
            boolean wereChanges2 = z;
            for (T next : $this$mapIndexedTo$iv$iv2) {
                int index$iv$iv = globalArgIndex3 + 1;
                if (globalArgIndex3 < 0) {
                    q.q();
                }
                w0 arg = (w0) next;
                if (arg.b()) {
                    globalArgIndex2++;
                    shouldEnhance = shouldEnhance2;
                    u0 i = enhancedClassifier.i();
                    k.b(i, "enhancedClassifier.typeConstructor");
                    w0Var = c1.s(i.getParameters().get(globalArgIndex3));
                    $this$mapIndexedTo$iv$iv = $this$mapIndexedTo$iv$iv2;
                } else {
                    shouldEnhance = shouldEnhance2;
                    k enhanced = e(arg.getType().L0(), lVar, globalArgIndex2);
                    wereChanges2 = wereChanges2 || enhanced.d();
                    globalArgIndex2 += enhanced.a();
                    b0 b2 = enhanced.b();
                    k kVar = enhanced;
                    h1 c = arg.c();
                    $this$mapIndexedTo$iv$iv = $this$mapIndexedTo$iv$iv2;
                    k.b(c, "arg.projectionKind");
                    w0Var = a.e(b2, c, typeConstructor.getParameters().get(globalArgIndex3));
                }
                arrayList.add(w0Var);
                lVar = qualifiers;
                globalArgIndex3 = index$iv$iv;
                shouldEnhance2 = shouldEnhance;
                $this$mapIndexedTo$iv$iv2 = $this$mapIndexedTo$iv$iv;
            }
            Iterable iterable = $this$mapIndexedTo$iv$iv2;
            List enhancedArguments = arrayList;
            c<Boolean> h = h(i0Var, effectiveQualifiers, qVar);
            boolean enhancedNullability = h.a().booleanValue();
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g enhancedNullabilityAnnotations = h.b();
            int subtreeSize = globalArgIndex2 - index;
            if (!(wereChanges2 || enhancedNullabilityAnnotations != null)) {
                return new o(i0Var, subtreeSize, false);
            }
            int i2 = globalArgIndex2;
            i0 enhancedType = c0.i(a(q.l($this$enhanceInflexible.getAnnotations(), enhancedMutabilityAnnotations, enhancedNullabilityAnnotations)), typeConstructor, enhancedArguments, enhancedNullability, (i) null, 16, (Object) null);
            i0 enhancement = effectiveQualifiers.d() ? new f(enhancedType) : enhancedType;
            g1 result = enhancedNullabilityAnnotations != null && effectiveQualifiers.e() ? e1.d(i0Var, enhancement) : enhancement;
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g gVar = enhancedNullabilityAnnotations;
            if (result != null) {
                boolean z2 = enhancedNullability;
                return new o((i0) result, subtreeSize, true);
            }
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        }
        return new o(i0Var, 1, false);
    }

    private static final kotlin.reflect.jvm.internal.impl.descriptors.annotations.g a(@NotNull List<? extends kotlin.reflect.jvm.internal.impl.descriptors.annotations.g> $this$compositeAnnotationsOrSingle) {
        switch ($this$compositeAnnotationsOrSingle.size()) {
            case 0:
                throw new IllegalStateException("At least one Annotations object expected".toString());
            case 1:
                return (kotlin.reflect.jvm.internal.impl.descriptors.annotations.g) y.q0($this$compositeAnnotationsOrSingle);
            default:
                return new kotlin.reflect.jvm.internal.impl.descriptors.annotations.k((List<? extends kotlin.reflect.jvm.internal.impl.descriptors.annotations.g>) y.C0($this$compositeAnnotationsOrSingle));
        }
    }

    public static final boolean l(@NotNull q $this$shouldEnhance) {
        k.f($this$shouldEnhance, "$this$shouldEnhance");
        return $this$shouldEnhance != q.INFLEXIBLE;
    }

    private static final <T> c<T> k(T $this$noChange) {
        return new c<>($this$noChange, (kotlin.reflect.jvm.internal.impl.descriptors.annotations.g) null);
    }

    private static final <T> c<T> g(T $this$enhancedNullability) {
        return new c<>($this$enhancedNullability, a);
    }

    private static final <T> c<T> f(T $this$enhancedMutability) {
        return new c<>($this$enhancedMutability, b);
    }

    private static final c<h> d(@NotNull h $this$enhanceMutability, d qualifiers, q position) {
        if (!l(position)) {
            return k($this$enhanceMutability);
        }
        if (!($this$enhanceMutability instanceof e)) {
            return k($this$enhanceMutability);
        }
        c mapping = c.m;
        e b2 = qualifiers.b();
        if (b2 != null) {
            switch (s.a[b2.ordinal()]) {
                case 1:
                    if (position == q.FLEXIBLE_LOWER && mapping.o((e) $this$enhanceMutability)) {
                        return f(mapping.i((e) $this$enhanceMutability));
                    }
                case 2:
                    if (position == q.FLEXIBLE_UPPER && mapping.r((e) $this$enhanceMutability)) {
                        return f(mapping.j((e) $this$enhanceMutability));
                    }
            }
        }
        return k($this$enhanceMutability);
    }

    private static final c<Boolean> h(@NotNull b0 $this$getEnhancedNullability, d qualifiers, q position) {
        if (!l(position)) {
            return k(Boolean.valueOf($this$getEnhancedNullability.J0()));
        }
        g c = qualifiers.c();
        if (c != null) {
            switch (s.b[c.ordinal()]) {
                case 1:
                    return g(true);
                case 2:
                    return g(false);
            }
        }
        return k(Boolean.valueOf($this$getEnhancedNullability.J0()));
    }

    static {
        b bVar = s.k;
        k.b(bVar, "JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION");
        a = new b(bVar);
        b bVar2 = s.l;
        k.b(bVar2, "JvmAnnotationNames.ENHANCED_MUTABILITY_ANNOTATION");
        b = new b(bVar2);
    }
}
