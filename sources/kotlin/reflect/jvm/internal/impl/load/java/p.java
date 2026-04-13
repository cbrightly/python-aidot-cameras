package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.List;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.load.kotlin.k;
import kotlin.reflect.jvm.internal.impl.load.kotlin.t;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.d;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JavaIncompatibilityRulesOverridabilityCondition.kt */
public final class p implements d {
    public static final a a = new a((DefaultConstructorMarker) null);

    @NotNull
    public d.b b(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a superDescriptor, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.a subDescriptor, @Nullable e subClassDescriptor) {
        k.f(superDescriptor, "superDescriptor");
        k.f(subDescriptor, "subDescriptor");
        if (c(superDescriptor, subDescriptor, subClassDescriptor)) {
            return d.b.INCOMPATIBLE;
        }
        if (a.a(superDescriptor, subDescriptor)) {
            return d.b.INCOMPATIBLE;
        }
        return d.b.UNKNOWN;
    }

    private final boolean c(kotlin.reflect.jvm.internal.impl.descriptors.a superDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.a subDescriptor, e subClassDescriptor) {
        if (!(superDescriptor instanceof b) || !(subDescriptor instanceof u) || g.h0(subDescriptor)) {
            return false;
        }
        d dVar = d.h;
        f name = ((u) subDescriptor).getName();
        k.b(name, "subDescriptor.name");
        if (!dVar.d(name)) {
            c cVar = c.f;
            f name2 = ((u) subDescriptor).getName();
            k.b(name2, "subDescriptor.name");
            if (!cVar.e(name2)) {
                return false;
            }
        }
        b overriddenBuiltin = w.j((b) superDescriptor);
        boolean x0 = ((u) subDescriptor).x0();
        u uVar = (u) (!(superDescriptor instanceof u) ? null : superDescriptor);
        if ((uVar == null || x0 != uVar.x0()) && (overriddenBuiltin == null || !((u) subDescriptor).x0())) {
            return true;
        }
        if (!(subClassDescriptor instanceof kotlin.reflect.jvm.internal.impl.load.java.descriptors.d) || ((u) subDescriptor).o0() != null || overriddenBuiltin == null || w.k(subClassDescriptor, overriddenBuiltin)) {
            return false;
        }
        if ((overriddenBuiltin instanceof u) && (superDescriptor instanceof u) && d.c((u) overriddenBuiltin) != null) {
            String c = t.c((u) subDescriptor, false, false, 2, (Object) null);
            u a2 = ((u) superDescriptor).a();
            k.b(a2, "superDescriptor.original");
            if (k.a(c, t.c(a2, false, false, 2, (Object) null))) {
                return false;
            }
        }
        return true;
    }

    @NotNull
    public d.a a() {
        return d.a.CONFLICTS_ONLY;
    }

    /* compiled from: JavaIncompatibilityRulesOverridabilityCondition.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final boolean a(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a superDescriptor, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.a subDescriptor) {
            k.f(superDescriptor, "superDescriptor");
            k.f(subDescriptor, "subDescriptor");
            if (!(subDescriptor instanceof kotlin.reflect.jvm.internal.impl.load.java.descriptors.f) || !(superDescriptor instanceof u)) {
                return false;
            }
            if (((kotlin.reflect.jvm.internal.impl.load.java.descriptors.f) subDescriptor).g().size() == ((u) superDescriptor).g().size()) {
                n0 e1 = ((kotlin.reflect.jvm.internal.impl.load.java.descriptors.f) subDescriptor).c0();
                k.b(e1, "subDescriptor.original");
                List<w0> g = e1.g();
                k.b(g, "subDescriptor.original.valueParameters");
                u a = ((u) superDescriptor).a();
                k.b(a, "superDescriptor.original");
                List<w0> g2 = a.g();
                k.b(g2, "superDescriptor.original.valueParameters");
                for (n next : y.K0(g, g2)) {
                    w0 subParameter = (w0) next.component1();
                    w0 superParameter = (w0) next.component2();
                    k.b(subParameter, "subParameter");
                    boolean isSubPrimitive = c((u) subDescriptor, subParameter) instanceof k.c;
                    kotlin.jvm.internal.k.b(superParameter, "superParameter");
                    if (isSubPrimitive != (c((u) superDescriptor, superParameter) instanceof k.c)) {
                        return true;
                    }
                }
                return false;
            }
            throw new AssertionError("External overridability condition with CONFLICTS_ONLY should not be run with different value parameters size");
        }

        private final kotlin.reflect.jvm.internal.impl.load.kotlin.k c(u f, w0 valueParameterDescriptor) {
            if (t.e(f) || b(f)) {
                b0 type = valueParameterDescriptor.getType();
                kotlin.jvm.internal.k.b(type, "valueParameterDescriptor.type");
                return t.g(kotlin.reflect.jvm.internal.impl.types.typeUtil.a.l(type));
            }
            b0 type2 = valueParameterDescriptor.getType();
            kotlin.jvm.internal.k.b(type2, "valueParameterDescriptor.type");
            return t.g(type2);
        }

        private final boolean b(u f) {
            if (f.g().size() != 1) {
                return false;
            }
            m b = f.b();
            e eVar = null;
            if (!(b instanceof e)) {
                b = null;
            }
            e classDescriptor = (e) b;
            if (classDescriptor == null) {
                return false;
            }
            List<w0> g = f.g();
            kotlin.jvm.internal.k.b(g, "f.valueParameters");
            Object q0 = y.q0(g);
            kotlin.jvm.internal.k.b(q0, "f.valueParameters.single()");
            h c = ((w0) q0).getType().I0().c();
            if (c instanceof e) {
                eVar = c;
            }
            e eVar2 = eVar;
            if (eVar2 == null) {
                return false;
            }
            e parameterClass = eVar2;
            if (!g.B0(classDescriptor) || !kotlin.jvm.internal.k.a(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.j(classDescriptor), kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.j(parameterClass))) {
                return false;
            }
            return true;
        }
    }
}
