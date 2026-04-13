package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.n;
import kotlin.reflect.jvm.internal.impl.descriptors.a;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.c;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.k0;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.l;
import kotlin.reflect.jvm.internal.impl.load.java.s;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.constants.w;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: util.kt */
public final class k {
    @NotNull
    public static final List<w0> a(@NotNull Collection<l> newValueParametersTypes, @NotNull Collection<? extends w0> oldValueParameters, @NotNull a newOwner) {
        kotlin.jvm.internal.k.f(newValueParametersTypes, "newValueParametersTypes");
        kotlin.jvm.internal.k.f(oldValueParameters, "oldValueParameters");
        kotlin.jvm.internal.k.f(newOwner, "newOwner");
        if (newValueParametersTypes.size() == oldValueParameters.size()) {
            Collection<n> destination$iv$iv = y.K0(newValueParametersTypes, oldValueParameters);
            ArrayList arrayList = new ArrayList(r.r(destination$iv$iv, 10));
            for (n $dstr$newParameter$oldParameter : destination$iv$iv) {
                l newParameter = (l) $dstr$newParameter$oldParameter.component1();
                w0 oldParameter = (w0) $dstr$newParameter$oldParameter.component2();
                int index = oldParameter.getIndex();
                g annotations = oldParameter.getAnnotations();
                f name = oldParameter.getName();
                kotlin.jvm.internal.k.b(name, "oldParameter.name");
                b0 b = newParameter.b();
                boolean a = newParameter.a();
                boolean n0 = oldParameter.n0();
                boolean k0 = oldParameter.k0();
                b0 l = oldParameter.r0() != null ? kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.m(newOwner).j().l(newParameter.b()) : null;
                o0 n = oldParameter.n();
                kotlin.jvm.internal.k.b(n, "oldParameter.source");
                k0 k0Var = r3;
                Collection $this$map$iv = destination$iv$iv;
                ArrayList arrayList2 = arrayList;
                k0 k0Var2 = new k0(newOwner, (w0) null, index, annotations, name, b, a, n0, k0, l, n);
                arrayList2.add(k0Var);
                arrayList = arrayList2;
                destination$iv$iv = $this$map$iv;
            }
            Collection $this$map$iv2 = destination$iv$iv;
            return arrayList;
        }
        throw new AssertionError("Different value parameters sizes: Enhanced = " + newValueParametersTypes.size() + ", Old = " + oldValueParameters.size());
    }

    @Nullable
    public static final l c(@NotNull e $this$getParentJavaStaticClassScope) {
        kotlin.jvm.internal.k.f($this$getParentJavaStaticClassScope, "$this$getParentJavaStaticClassScope");
        e superClassDescriptor = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.q($this$getParentJavaStaticClassScope);
        l lVar = null;
        if (superClassDescriptor == null) {
            return null;
        }
        h g0 = superClassDescriptor.g0();
        if (g0 instanceof l) {
            lVar = g0;
        }
        l lVar2 = lVar;
        return lVar2 != null ? lVar2 : c(superClassDescriptor);
    }

    @Nullable
    public static final a b(@NotNull w0 $this$getDefaultValueFromAnnotation) {
        Object $this$safeAs$iv;
        String it;
        kotlin.jvm.internal.k.f($this$getDefaultValueFromAnnotation, "$this$getDefaultValueFromAnnotation");
        g annotations = $this$getDefaultValueFromAnnotation.getAnnotations();
        b bVar = s.n;
        kotlin.jvm.internal.k.b(bVar, "JvmAnnotationNames.DEFAULT_VALUE_FQ_NAME");
        c c = annotations.c(bVar);
        if (!(c == null || ($this$safeAs$iv = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.c(c)) == null)) {
            w wVar = (w) (!($this$safeAs$iv instanceof w) ? null : $this$safeAs$iv);
            if (!(wVar == null || (it = (String) wVar.b()) == null)) {
                return new j(it);
            }
        }
        g annotations2 = $this$getDefaultValueFromAnnotation.getAnnotations();
        b bVar2 = s.o;
        kotlin.jvm.internal.k.b(bVar2, "JvmAnnotationNames.DEFAULT_NULL_FQ_NAME");
        if (annotations2.I(bVar2)) {
            return h.a;
        }
        return null;
    }
}
