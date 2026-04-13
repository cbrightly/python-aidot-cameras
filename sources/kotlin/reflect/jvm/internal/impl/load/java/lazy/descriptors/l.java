package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.o0;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.v;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.h;
import kotlin.reflect.jvm.internal.impl.load.java.structure.g;
import kotlin.reflect.jvm.internal.impl.load.java.structure.p;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.utils.b;
import kotlin.sequences.o;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LazyJavaStaticClassScope.kt */
public final class l extends m {
    private final g n;
    @NotNull
    private final f o;

    /* compiled from: LazyJavaStaticClassScope.kt */
    public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<p, Boolean> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((p) obj));
        }

        public final boolean invoke(@NotNull p it) {
            k.f(it, "it");
            return it.i();
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public l(@NotNull h c2, @NotNull g jClass, @NotNull f ownerDescriptor) {
        super(c2);
        k.f(c2, "c");
        k.f(jClass, "jClass");
        k.f(ownerDescriptor, "ownerDescriptor");
        this.n = jClass;
        this.o = ownerDescriptor;
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: J */
    public f y() {
        return this.o;
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: H */
    public a m() {
        return new a(this.n, a.INSTANCE);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Set<f> l(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d kindFilter, @Nullable kotlin.jvm.functions.l<? super f, Boolean> nameFilter) {
        k.f(kindFilter, "kindFilter");
        Set G0 = y.G0(((b) u().invoke()).a());
        Set $this$apply = G0;
        l c2 = kotlin.reflect.jvm.internal.impl.load.java.descriptors.k.c(y());
        Set<f> a2 = c2 != null ? c2.a() : null;
        if (a2 == null) {
            a2 = o0.d();
        }
        $this$apply.addAll(a2);
        if (this.n.q()) {
            $this$apply.addAll(q.j(kotlin.reflect.jvm.internal.impl.resolve.c.b, kotlin.reflect.jvm.internal.impl.resolve.c.a));
        }
        return G0;
    }

    /* compiled from: LazyJavaStaticClassScope.kt */
    public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.resolve.scopes.h, Set<? extends f>> {
        public static final c INSTANCE = new c();

        c() {
            super(1);
        }

        @NotNull
        public final Set<f> invoke(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.h it) {
            k.f(it, "it");
            return it.f();
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Set<f> q(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d kindFilter, @Nullable kotlin.jvm.functions.l<? super f, Boolean> nameFilter) {
        k.f(kindFilter, "kindFilter");
        Set $this$apply = y.G0(((b) u().invoke()).b());
        I(y(), $this$apply, c.INSTANCE);
        return $this$apply;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Set<f> j(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d kindFilter, @Nullable kotlin.jvm.functions.l<? super f, Boolean> nameFilter) {
        k.f(kindFilter, "kindFilter");
        return o0.d();
    }

    @Nullable
    public kotlin.reflect.jvm.internal.impl.descriptors.h c(@NotNull f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        k.f(name, "name");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        return null;
    }

    /* access modifiers changed from: protected */
    public void o(@NotNull Collection<n0> result, @NotNull f name) {
        k.f(result, "result");
        k.f(name, "name");
        Collection<D> h = kotlin.reflect.jvm.internal.impl.load.java.components.a.h(name, L(name, y()), result, y(), t().a().c(), t().a().i().a());
        k.b(h, "resolveOverridesForStati….overridingUtil\n        )");
        result.addAll(h);
        if (!this.n.q()) {
            return;
        }
        if (k.a(name, kotlin.reflect.jvm.internal.impl.resolve.c.b)) {
            n0 d2 = kotlin.reflect.jvm.internal.impl.resolve.b.d(y());
            k.b(d2, "createEnumValueOfMethod(ownerDescriptor)");
            result.add(d2);
        } else if (k.a(name, kotlin.reflect.jvm.internal.impl.resolve.c.a)) {
            n0 e2 = kotlin.reflect.jvm.internal.impl.resolve.b.e(y());
            k.b(e2, "createEnumValuesMethod(ownerDescriptor)");
            result.add(e2);
        }
    }

    /* compiled from: LazyJavaStaticClassScope.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.resolve.scopes.h, Collection<? extends i0>> {
        final /* synthetic */ f $name;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(f fVar) {
            super(1);
            this.$name = fVar;
        }

        @NotNull
        public final Collection<? extends i0> invoke(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.h it) {
            k.f(it, "it");
            return it.e(this.$name, kotlin.reflect.jvm.internal.impl.incremental.components.d.WHEN_GET_SUPER_MEMBERS);
        }
    }

    /* access modifiers changed from: protected */
    public void p(@NotNull f name, @NotNull Collection<i0> result) {
        f fVar = name;
        Collection<i0> collection = result;
        k.f(fVar, "name");
        k.f(collection, "result");
        Iterable propertiesFromSupertypes = I(y(), new LinkedHashSet(), new b(fVar));
        if (!result.isEmpty()) {
            Collection<D> h = kotlin.reflect.jvm.internal.impl.load.java.components.a.h(name, propertiesFromSupertypes, result, y(), t().a().c(), t().a().i().a());
            k.b(h, "resolveOverridesForStati…ingUtil\n                )");
            collection.addAll(h);
            return;
        }
        Map $this$flatMap$iv = new LinkedHashMap();
        for (Object element$iv$iv : propertiesFromSupertypes) {
            Object key$iv$iv = K((i0) element$iv$iv);
            Map $this$getOrPut$iv$iv$iv = $this$flatMap$iv;
            Object value$iv$iv$iv = $this$getOrPut$iv$iv$iv.get(key$iv$iv);
            if (value$iv$iv$iv == null) {
                Object answer$iv$iv$iv = new ArrayList();
                $this$getOrPut$iv$iv$iv.put(key$iv$iv, answer$iv$iv$iv);
                value$iv$iv$iv = answer$iv$iv$iv;
            }
            ((List) value$iv$iv$iv).add(element$iv$iv);
        }
        Collection destination$iv$iv = new ArrayList();
        for (Map.Entry element$iv$iv2 : $this$flatMap$iv.entrySet()) {
            f fVar2 = name;
            v.x(destination$iv$iv, kotlin.reflect.jvm.internal.impl.load.java.components.a.h(fVar2, (Collection) element$iv$iv2.getValue(), result, y(), t().a().c(), t().a().i().a()));
        }
        collection.addAll(destination$iv$iv);
    }

    private final Set<n0> L(f name, kotlin.reflect.jvm.internal.impl.descriptors.e descriptor) {
        l staticScope = kotlin.reflect.jvm.internal.impl.load.java.descriptors.k.c(descriptor);
        if (staticScope != null) {
            return y.H0(staticScope.b(name, kotlin.reflect.jvm.internal.impl.incremental.components.d.WHEN_GET_SUPER_MEMBERS));
        }
        return o0.d();
    }

    private final <R> Set<R> I(kotlin.reflect.jvm.internal.impl.descriptors.e root, Set<R> result, kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.resolve.scopes.h, ? extends Collection<? extends R>> onJavaStaticScope) {
        kotlin.reflect.jvm.internal.impl.utils.b.b(kotlin.collections.p.b(root), d.a, new e(root, result, onJavaStaticScope));
        return result;
    }

    /* compiled from: LazyJavaStaticClassScope.kt */
    public static final class d<N> implements b.c<N> {
        public static final d a = new d();

        d() {
        }

        /* compiled from: LazyJavaStaticClassScope.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<b0, kotlin.reflect.jvm.internal.impl.descriptors.e> {
            public static final a INSTANCE = new a();

            a() {
                super(1);
            }

            @Nullable
            public final kotlin.reflect.jvm.internal.impl.descriptors.e invoke(b0 supertype) {
                kotlin.reflect.jvm.internal.impl.descriptors.h c = supertype.I0().c();
                if (!(c instanceof kotlin.reflect.jvm.internal.impl.descriptors.e)) {
                    c = null;
                }
                return (kotlin.reflect.jvm.internal.impl.descriptors.e) c;
            }
        }

        @NotNull
        /* renamed from: b */
        public final Iterable<kotlin.reflect.jvm.internal.impl.descriptors.e> a(kotlin.reflect.jvm.internal.impl.descriptors.e it) {
            k.b(it, "it");
            u0 i = it.i();
            k.b(i, "it.typeConstructor");
            Collection<b0> b = i.b();
            k.b(b, "it.typeConstructor.supertypes");
            return o.l(o.x(y.L(b), a.INSTANCE));
        }
    }

    /* compiled from: LazyJavaStaticClassScope.kt */
    public static final class e extends b.C0433b<kotlin.reflect.jvm.internal.impl.descriptors.e, x> {
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.descriptors.e a;
        final /* synthetic */ Set b;
        final /* synthetic */ kotlin.jvm.functions.l c;

        e(kotlin.reflect.jvm.internal.impl.descriptors.e $captured_local_variable$0, Set $captured_local_variable$1, kotlin.jvm.functions.l $captured_local_variable$2) {
            this.a = $captured_local_variable$0;
            this.b = $captured_local_variable$1;
            this.c = $captured_local_variable$2;
        }

        public /* bridge */ /* synthetic */ Object a() {
            e();
            return x.a;
        }

        /* renamed from: d */
        public boolean c(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e current) {
            k.f(current, "current");
            if (current == this.a) {
                return true;
            }
            kotlin.reflect.jvm.internal.impl.resolve.scopes.h staticScope = current.g0();
            k.b(staticScope, "current.staticScope");
            if (!(staticScope instanceof m)) {
                return true;
            }
            this.b.addAll((Collection) this.c.invoke(staticScope));
            return false;
        }

        public void e() {
        }
    }

    private final i0 K(@NotNull i0 $this$realOriginal) {
        b.a h = $this$realOriginal.h();
        k.b(h, "this.kind");
        if (h.isReal()) {
            return $this$realOriginal;
        }
        Iterable<i0> $this$mapTo$iv$iv = $this$realOriginal.d();
        k.b($this$mapTo$iv$iv, "this.overriddenDescriptors");
        Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (i0 it : $this$mapTo$iv$iv) {
            k.b(it, "it");
            destination$iv$iv.add(K(it));
        }
        return (i0) y.q0(y.N(destination$iv$iv));
    }
}
