package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.u;
import kotlin.reflect.jvm.internal.impl.descriptors.s0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.resolve.constants.n;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.s0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KotlinTypeFactory.kt */
public final class c0 {
    @NotNull
    private static final l<i, i0> a = a.INSTANCE;
    public static final c0 b = new c0();

    /* compiled from: KotlinTypeFactory.kt */
    public static final class a extends kotlin.jvm.internal.l implements l {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @Nullable
        public final Void invoke(@NotNull i $noName_0) {
            k.f($noName_0, "<anonymous parameter 0>");
            return null;
        }
    }

    private c0() {
    }

    private final h c(u0 constructor, List<? extends w0> arguments, i kotlinTypeRefiner) {
        kotlin.reflect.jvm.internal.impl.descriptors.h descriptor = constructor.c();
        if (descriptor instanceof t0) {
            return descriptor.m().l();
        }
        if (descriptor instanceof e) {
            i refinerToUse = kotlinTypeRefiner != null ? kotlinTypeRefiner : kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.l(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.m(descriptor));
            if (arguments.isEmpty()) {
                return u.b((e) descriptor, refinerToUse);
            }
            return u.a((e) descriptor, v0.c.b(constructor, arguments), refinerToUse);
        } else if (descriptor instanceof s0) {
            h i = u.i("Scope for abbreviation: " + ((s0) descriptor).getName(), true);
            k.b(i, "ErrorUtils.createErrorSc…{descriptor.name}\", true)");
            return i;
        } else {
            throw new IllegalStateException("Unsupported classifier: " + descriptor + " for constructor: " + constructor);
        }
    }

    public static /* synthetic */ i0 i(g gVar, u0 u0Var, List list, boolean z, i iVar, int i, Object obj) {
        if ((i & 16) != 0) {
            iVar = null;
        }
        return h(gVar, u0Var, list, z, iVar);
    }

    @NotNull
    public static final i0 h(@NotNull g annotations, @NotNull u0 constructor, @NotNull List<? extends w0> arguments, boolean nullable, @Nullable i kotlinTypeRefiner) {
        k.f(annotations, "annotations");
        k.f(constructor, "constructor");
        k.f(arguments, "arguments");
        if (!annotations.isEmpty() || !arguments.isEmpty() || nullable || constructor.c() == null) {
            return k(annotations, constructor, arguments, nullable, b.c(constructor, arguments, kotlinTypeRefiner), new c(constructor, arguments, annotations, nullable));
        }
        kotlin.reflect.jvm.internal.impl.descriptors.h c2 = constructor.c();
        if (c2 == null) {
            k.n();
        }
        k.b(c2, "constructor.declarationDescriptor!!");
        i0 m = c2.m();
        k.b(m, "constructor.declarationDescriptor!!.defaultType");
        return m;
    }

    /* compiled from: KotlinTypeFactory.kt */
    public static final class c extends kotlin.jvm.internal.l implements l<i, i0> {
        final /* synthetic */ g $annotations;
        final /* synthetic */ List $arguments;
        final /* synthetic */ u0 $constructor;
        final /* synthetic */ boolean $nullable;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(u0 u0Var, List list, g gVar, boolean z) {
            super(1);
            this.$constructor = u0Var;
            this.$arguments = list;
            this.$annotations = gVar;
            this.$nullable = z;
        }

        @Nullable
        public final i0 invoke(@NotNull i refiner) {
            k.f(refiner, "refiner");
            b expandedTypeOrRefinedConstructor = c0.b.f(this.$constructor, refiner, this.$arguments);
            if (expandedTypeOrRefinedConstructor == null) {
                return null;
            }
            i0 it = expandedTypeOrRefinedConstructor.a();
            if (it != null) {
                return it;
            }
            g gVar = this.$annotations;
            u0 b = expandedTypeOrRefinedConstructor.b();
            if (b == null) {
                k.n();
            }
            return c0.h(gVar, b, this.$arguments, this.$nullable, refiner);
        }
    }

    @NotNull
    public static final i0 b(@NotNull s0 $this$computeExpandedType, @NotNull List<? extends w0> arguments) {
        k.f($this$computeExpandedType, "$this$computeExpandedType");
        k.f(arguments, "arguments");
        return new q0(s0.a.a, false).i(r0.a.a((r0) null, $this$computeExpandedType, arguments), g.b.b());
    }

    /* access modifiers changed from: private */
    public final b f(u0 constructor, i kotlinTypeRefiner, List<? extends w0> arguments) {
        kotlin.reflect.jvm.internal.impl.descriptors.h it;
        kotlin.reflect.jvm.internal.impl.descriptors.h basicDescriptor = constructor.c();
        if (basicDescriptor == null || (it = kotlinTypeRefiner.e(basicDescriptor)) == null) {
            return null;
        }
        if (it instanceof kotlin.reflect.jvm.internal.impl.descriptors.s0) {
            return new b(b((kotlin.reflect.jvm.internal.impl.descriptors.s0) it, arguments), (u0) null);
        }
        u0 refinedConstructor = it.i().a(kotlinTypeRefiner);
        k.b(refinedConstructor, "descriptor.typeConstruct…refine(kotlinTypeRefiner)");
        return new b((i0) null, refinedConstructor);
    }

    /* compiled from: KotlinTypeFactory.kt */
    public static final class b {
        @Nullable
        private final i0 a;
        @Nullable
        private final u0 b;

        public b(@Nullable i0 expandedType, @Nullable u0 refinedConstructor) {
            this.a = expandedType;
            this.b = refinedConstructor;
        }

        @Nullable
        public final i0 a() {
            return this.a;
        }

        @Nullable
        public final u0 b() {
            return this.b;
        }
    }

    /* compiled from: KotlinTypeFactory.kt */
    public static final class d extends kotlin.jvm.internal.l implements l<i, i0> {
        final /* synthetic */ g $annotations;
        final /* synthetic */ List $arguments;
        final /* synthetic */ u0 $constructor;
        final /* synthetic */ h $memberScope;
        final /* synthetic */ boolean $nullable;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(u0 u0Var, List list, g gVar, boolean z, h hVar) {
            super(1);
            this.$constructor = u0Var;
            this.$arguments = list;
            this.$annotations = gVar;
            this.$nullable = z;
            this.$memberScope = hVar;
        }

        @Nullable
        public final i0 invoke(@NotNull i kotlinTypeRefiner) {
            k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
            b expandedTypeOrRefinedConstructor = c0.b.f(this.$constructor, kotlinTypeRefiner, this.$arguments);
            if (expandedTypeOrRefinedConstructor == null) {
                return null;
            }
            i0 it = expandedTypeOrRefinedConstructor.a();
            if (it != null) {
                return it;
            }
            g gVar = this.$annotations;
            u0 b = expandedTypeOrRefinedConstructor.b();
            if (b == null) {
                k.n();
            }
            return c0.j(gVar, b, this.$arguments, this.$nullable, this.$memberScope);
        }
    }

    @NotNull
    public static final i0 j(@NotNull g annotations, @NotNull u0 constructor, @NotNull List<? extends w0> arguments, boolean nullable, @NotNull h memberScope) {
        k.f(annotations, "annotations");
        k.f(constructor, "constructor");
        k.f(arguments, "arguments");
        k.f(memberScope, "memberScope");
        u0 u0Var = constructor;
        List<? extends w0> list = arguments;
        j0 j0Var = new j0(u0Var, list, nullable, memberScope, new d(u0Var, list, annotations, nullable, memberScope));
        if (annotations.isEmpty()) {
            return j0Var;
        }
        return new i(j0Var, annotations);
    }

    @NotNull
    public static final i0 k(@NotNull g annotations, @NotNull u0 constructor, @NotNull List<? extends w0> arguments, boolean nullable, @NotNull h memberScope, @NotNull l<? super i, ? extends i0> refinedTypeFactory) {
        k.f(annotations, "annotations");
        k.f(constructor, "constructor");
        k.f(arguments, "arguments");
        k.f(memberScope, "memberScope");
        k.f(refinedTypeFactory, "refinedTypeFactory");
        j0 j0Var = new j0(constructor, arguments, nullable, memberScope, refinedTypeFactory);
        if (annotations.isEmpty()) {
            return j0Var;
        }
        return new i(j0Var, annotations);
    }

    @NotNull
    public static final i0 g(@NotNull g annotations, @NotNull e descriptor, @NotNull List<? extends w0> arguments) {
        k.f(annotations, "annotations");
        k.f(descriptor, "descriptor");
        k.f(arguments, "arguments");
        u0 i = descriptor.i();
        k.b(i, "descriptor.typeConstructor");
        return i(annotations, i, arguments, false, (i) null, 16, (Object) null);
    }

    @NotNull
    public static final g1 d(@NotNull i0 lowerBound, @NotNull i0 upperBound) {
        k.f(lowerBound, "lowerBound");
        k.f(upperBound, "upperBound");
        if (k.a(lowerBound, upperBound)) {
            return lowerBound;
        }
        return new w(lowerBound, upperBound);
    }

    @NotNull
    public static final i0 e(@NotNull g annotations, @NotNull n constructor, boolean nullable) {
        k.f(annotations, "annotations");
        k.f(constructor, "constructor");
        List g = q.g();
        h i = u.i("Scope for integer literal type", true);
        k.b(i, "ErrorUtils.createErrorSc…eger literal type\", true)");
        return j(annotations, constructor, g, nullable, i);
    }
}
