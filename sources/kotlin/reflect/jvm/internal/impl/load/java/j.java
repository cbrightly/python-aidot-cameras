package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.Iterator;
import java.util.List;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.f;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.g;
import kotlin.reflect.jvm.internal.impl.resolve.d;
import kotlin.reflect.jvm.internal.impl.resolve.i;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.sequences.h;
import kotlin.sequences.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ErasedOverridabilityCondition.kt */
public final class j implements d {
    @NotNull
    public d.b b(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a superDescriptor, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.a subDescriptor, @Nullable e subClassDescriptor) {
        h $this$any$iv;
        b0 it;
        k.f(superDescriptor, "superDescriptor");
        k.f(subDescriptor, "subDescriptor");
        if (subDescriptor instanceof f) {
            List<t0> typeParameters = ((f) subDescriptor).getTypeParameters();
            k.b(typeParameters, "subDescriptor.typeParameters");
            if (!(!typeParameters.isEmpty())) {
                i.j x = i.x(superDescriptor, subDescriptor);
                b0 b0Var = null;
                if ((x != null ? x.c() : null) != null) {
                    return d.b.UNKNOWN;
                }
                List<w0> g = ((f) subDescriptor).g();
                k.b(g, "subDescriptor.valueParameters");
                h<R> w = o.w(y.L(g), a.INSTANCE);
                b0 returnType = ((f) subDescriptor).getReturnType();
                if (returnType == null) {
                    k.n();
                }
                h<R> z = o.z(w, returnType);
                l0 L = ((f) subDescriptor).L();
                if (L != null) {
                    b0Var = L.getType();
                }
                Iterator<R> it2 = o.y(z, q.k(b0Var)).iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        $this$any$iv = null;
                        break;
                    }
                    b0 it3 = (b0) it2.next();
                    if (!(!it3.H0().isEmpty()) || (it3.L0() instanceof g)) {
                        it = null;
                        continue;
                    } else {
                        it = 1;
                        continue;
                    }
                    if (it != null) {
                        $this$any$iv = 1;
                        break;
                    }
                }
                if ($this$any$iv != null) {
                    return d.b.UNKNOWN;
                }
                kotlin.reflect.jvm.internal.impl.descriptors.a erasedSuper = (kotlin.reflect.jvm.internal.impl.descriptors.a) superDescriptor.c(kotlin.reflect.jvm.internal.impl.load.java.lazy.types.f.e.c());
                if (erasedSuper == null) {
                    return d.b.UNKNOWN;
                }
                if (erasedSuper instanceof n0) {
                    List<t0> typeParameters2 = ((n0) erasedSuper).getTypeParameters();
                    k.b(typeParameters2, "erasedSuper.typeParameters");
                    if (true ^ typeParameters2.isEmpty()) {
                        u build = ((n0) erasedSuper).r().o(q.g()).build();
                        if (build == null) {
                            k.n();
                        }
                        erasedSuper = build;
                    }
                }
                i.j G = i.b.G(erasedSuper, subDescriptor, false);
                k.b(G, "OverridingUtil.DEFAULT.i…er, subDescriptor, false)");
                i.j.a overridabilityResult = G.c();
                k.b(overridabilityResult, "OverridingUtil.DEFAULT.i…Descriptor, false).result");
                switch (i.a[overridabilityResult.ordinal()]) {
                    case 1:
                        return d.b.OVERRIDABLE;
                    default:
                        return d.b.UNKNOWN;
                }
            }
        }
        return d.b.UNKNOWN;
    }

    /* compiled from: ErasedOverridabilityCondition.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<w0, b0> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final b0 invoke(w0 it) {
            k.b(it, "it");
            return it.getType();
        }
    }

    @NotNull
    public d.a a() {
        return d.a.SUCCESS_ONLY;
    }
}
