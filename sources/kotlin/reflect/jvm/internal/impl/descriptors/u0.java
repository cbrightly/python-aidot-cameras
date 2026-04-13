package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.u;
import kotlin.sequences.h;
import kotlin.sequences.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: typeParameterUtils.kt */
public final class u0 {
    @NotNull
    public static final List<t0> d(@NotNull i $this$computeConstructorTypeParameters) {
        List list;
        Object element$iv;
        kotlin.reflect.jvm.internal.impl.types.u0 i;
        k.f($this$computeConstructorTypeParameters, "$this$computeConstructorTypeParameters");
        List declaredParameters = $this$computeConstructorTypeParameters.o();
        k.b(declaredParameters, "declaredTypeParameters");
        if (!$this$computeConstructorTypeParameters.x() && !($this$computeConstructorTypeParameters.b() instanceof a)) {
            return declaredParameters;
        }
        List parametersFromContainingFunctions = o.C(o.s(o.o(o.A(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.n($this$computeConstructorTypeParameters), a.INSTANCE), b.INSTANCE), c.INSTANCE));
        Iterator<m> it = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.n($this$computeConstructorTypeParameters).iterator();
        while (true) {
            list = null;
            if (!it.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it.next();
            if (element$iv instanceof e) {
                break;
            }
        }
        e eVar = (e) element$iv;
        if (!(eVar == null || (i = eVar.i()) == null)) {
            list = i.getParameters();
        }
        if (list == null) {
            list = q.g();
        }
        List containingClassTypeConstructorParameters = list;
        if (!parametersFromContainingFunctions.isEmpty() || !containingClassTypeConstructorParameters.isEmpty()) {
            Iterable<t0> $this$mapTo$iv$iv = y.n0(parametersFromContainingFunctions, containingClassTypeConstructorParameters);
            List $this$map$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (t0 it2 : $this$mapTo$iv$iv) {
                k.b(it2, "it");
                $this$map$iv.add(c(it2, $this$computeConstructorTypeParameters, declaredParameters.size()));
            }
            return y.n0(declaredParameters, $this$map$iv);
        }
        List<t0> o = $this$computeConstructorTypeParameters.o();
        k.b(o, "declaredTypeParameters");
        return o;
    }

    /* compiled from: typeParameterUtils.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<m, Boolean> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((m) obj));
        }

        public final boolean invoke(@NotNull m it) {
            k.f(it, "it");
            return it instanceof a;
        }
    }

    /* compiled from: typeParameterUtils.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<m, Boolean> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((m) obj));
        }

        public final boolean invoke(@NotNull m it) {
            k.f(it, "it");
            return !(it instanceof l);
        }
    }

    /* compiled from: typeParameterUtils.kt */
    public static final class c extends l implements kotlin.jvm.functions.l<m, h<? extends t0>> {
        public static final c INSTANCE = new c();

        c() {
            super(1);
        }

        @NotNull
        public final h<t0> invoke(@NotNull m it) {
            k.f(it, "it");
            List<t0> typeParameters = ((a) it).getTypeParameters();
            k.b(typeParameters, "(it as CallableDescriptor).typeParameters");
            return y.L(typeParameters);
        }
    }

    private static final c c(@NotNull t0 $this$capturedCopyForInnerDeclaration, m declarationDescriptor, int declaredTypeParametersCount) {
        return new c($this$capturedCopyForInnerDeclaration, declarationDescriptor, declaredTypeParametersCount);
    }

    @Nullable
    public static final g0 a(@NotNull b0 $this$buildPossiblyInnerType) {
        k.f($this$buildPossiblyInnerType, "$this$buildPossiblyInnerType");
        h c2 = $this$buildPossiblyInnerType.I0().c();
        if (!(c2 instanceof i)) {
            c2 = null;
        }
        return b($this$buildPossiblyInnerType, (i) c2, 0);
    }

    private static final g0 b(@NotNull b0 $this$buildPossiblyInnerType, i classifierDescriptor, int index) {
        i iVar = null;
        if (classifierDescriptor == null || u.r(classifierDescriptor)) {
            return null;
        }
        int toIndex = classifierDescriptor.o().size() + index;
        if (!classifierDescriptor.x()) {
            if (toIndex == $this$buildPossiblyInnerType.H0().size() || kotlin.reflect.jvm.internal.impl.resolve.c.E(classifierDescriptor)) {
                return new g0(classifierDescriptor, $this$buildPossiblyInnerType.H0().subList(index, $this$buildPossiblyInnerType.H0().size()), (g0) null);
            }
            throw new AssertionError(($this$buildPossiblyInnerType.H0().size() - toIndex) + " trailing arguments were found in " + $this$buildPossiblyInnerType + " type");
        }
        List argumentsSubList = $this$buildPossiblyInnerType.H0().subList(index, toIndex);
        m b2 = classifierDescriptor.b();
        if (b2 instanceof i) {
            iVar = b2;
        }
        return new g0(classifierDescriptor, argumentsSubList, b($this$buildPossiblyInnerType, iVar, toIndex));
    }
}
