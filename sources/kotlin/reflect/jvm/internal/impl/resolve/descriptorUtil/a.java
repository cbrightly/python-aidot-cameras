package kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.p;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.z;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b0;
import kotlin.reflect.jvm.internal.impl.descriptors.h0;
import kotlin.reflect.jvm.internal.impl.descriptors.i;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.j;
import kotlin.reflect.jvm.internal.impl.utils.b;
import kotlin.sequences.o;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DescriptorUtils.kt */
public final class a {
    private static final kotlin.reflect.jvm.internal.impl.name.f a;

    static {
        kotlin.reflect.jvm.internal.impl.name.f f2 = kotlin.reflect.jvm.internal.impl.name.f.f("value");
        k.b(f2, "Name.identifier(\"value\")");
        a = f2;
    }

    @NotNull
    public static final kotlin.reflect.jvm.internal.impl.name.c k(@NotNull m $this$fqNameUnsafe) {
        k.f($this$fqNameUnsafe, "$this$fqNameUnsafe");
        kotlin.reflect.jvm.internal.impl.name.c m = kotlin.reflect.jvm.internal.impl.resolve.c.m($this$fqNameUnsafe);
        k.b(m, "DescriptorUtils.getFqName(this)");
        return m;
    }

    @NotNull
    public static final kotlin.reflect.jvm.internal.impl.name.b j(@NotNull m $this$fqNameSafe) {
        k.f($this$fqNameSafe, "$this$fqNameSafe");
        kotlin.reflect.jvm.internal.impl.name.b n = kotlin.reflect.jvm.internal.impl.resolve.c.n($this$fqNameSafe);
        k.b(n, "DescriptorUtils.getFqNameSafe(this)");
        return n;
    }

    @NotNull
    public static final y m(@NotNull m $this$module) {
        k.f($this$module, "$this$module");
        y g = kotlin.reflect.jvm.internal.impl.resolve.c.g($this$module);
        k.b(g, "DescriptorUtils.getContainingModule(this)");
        return g;
    }

    @Nullable
    public static final kotlin.reflect.jvm.internal.impl.descriptors.e s(@NotNull y $this$resolveTopLevelClass, @NotNull kotlin.reflect.jvm.internal.impl.name.b topLevelClassFqName, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        k.f($this$resolveTopLevelClass, "$this$resolveTopLevelClass");
        k.f(topLevelClassFqName, "topLevelClassFqName");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        if (!topLevelClassFqName.d()) {
            kotlin.reflect.jvm.internal.impl.name.b e2 = topLevelClassFqName.e();
            k.b(e2, "topLevelClassFqName.parent()");
            h l = $this$resolveTopLevelClass.e0(e2).l();
            kotlin.reflect.jvm.internal.impl.name.f g = topLevelClassFqName.g();
            k.b(g, "topLevelClassFqName.shortName()");
            kotlin.reflect.jvm.internal.impl.descriptors.h c2 = l.c(g, location);
            if (!(c2 instanceof kotlin.reflect.jvm.internal.impl.descriptors.e)) {
                c2 = null;
            }
            return (kotlin.reflect.jvm.internal.impl.descriptors.e) c2;
        }
        throw new AssertionError("Assertion failed");
    }

    @Nullable
    public static final kotlin.reflect.jvm.internal.impl.name.a i(@Nullable kotlin.reflect.jvm.internal.impl.descriptors.h $this$classId) {
        m owner;
        kotlin.reflect.jvm.internal.impl.name.a i;
        if ($this$classId == null || (owner = $this$classId.b()) == null) {
            return null;
        }
        if (owner instanceof b0) {
            return new kotlin.reflect.jvm.internal.impl.name.a(((b0) owner).e(), $this$classId.getName());
        }
        if (!(owner instanceof i) || (i = i((kotlin.reflect.jvm.internal.impl.descriptors.h) owner)) == null) {
            return null;
        }
        return i.d($this$classId.getName());
    }

    @Nullable
    public static final kotlin.reflect.jvm.internal.impl.descriptors.e q(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e $this$getSuperClassNotAny) {
        k.f($this$getSuperClassNotAny, "$this$getSuperClassNotAny");
        for (kotlin.reflect.jvm.internal.impl.types.b0 supertype : $this$getSuperClassNotAny.m().I0().b()) {
            if (!g.d0(supertype)) {
                kotlin.reflect.jvm.internal.impl.descriptors.h superClassifier = supertype.I0().c();
                if (kotlin.reflect.jvm.internal.impl.resolve.c.w(superClassifier)) {
                    if (superClassifier != null) {
                        return (kotlin.reflect.jvm.internal.impl.descriptors.e) superClassifier;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                }
            }
        }
        return null;
    }

    @NotNull
    public static final g h(@NotNull m $this$builtIns) {
        k.f($this$builtIns, "$this$builtIns");
        return m($this$builtIns).j();
    }

    public static final boolean b(@NotNull w0 $this$declaresOrInheritsDefaultValue) {
        k.f($this$declaresOrInheritsDefaultValue, "$this$declaresOrInheritsDefaultValue");
        Boolean e2 = kotlin.reflect.jvm.internal.impl.utils.b.e(p.b($this$declaresOrInheritsDefaultValue), b.a, c.INSTANCE);
        k.b(e2, "DFS.ifAny(\n        listO…eclaresDefaultValue\n    )");
        return e2.booleanValue();
    }

    /* compiled from: DescriptorUtils.kt */
    public static final class b<N> implements b.c<N> {
        public static final b a = new b();

        b() {
        }

        @NotNull
        /* renamed from: b */
        public final List<w0> a(w0 current) {
            k.b(current, "current");
            Iterable<w0> $this$mapTo$iv$iv = current.d();
            ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (w0 p1 : $this$mapTo$iv$iv) {
                arrayList.add(p1.a());
            }
            return arrayList;
        }
    }

    /* compiled from: DescriptorUtils.kt */
    public static final /* synthetic */ class c extends kotlin.jvm.internal.h implements l<w0, Boolean> {
        public static final c INSTANCE = new c();

        c() {
            super(1);
        }

        public final String getName() {
            return "declaresDefaultValue";
        }

        public final kotlin.reflect.e getOwner() {
            return a0.b(w0.class);
        }

        public final String getSignature() {
            return "declaresDefaultValue()Z";
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((w0) obj));
        }

        public final boolean invoke(@NotNull w0 p1) {
            k.f(p1, "p1");
            return p1.v0();
        }
    }

    /* compiled from: DescriptorUtils.kt */
    public static final class f extends kotlin.jvm.internal.l implements l<m, m> {
        public static final f INSTANCE = new f();

        f() {
            super(1);
        }

        @Nullable
        public final m invoke(@NotNull m it) {
            k.f(it, "it");
            return it.b();
        }
    }

    @NotNull
    public static final kotlin.sequences.h<m> o(@NotNull m $this$parentsWithSelf) {
        k.f($this$parentsWithSelf, "$this$parentsWithSelf");
        return kotlin.sequences.m.h($this$parentsWithSelf, f.INSTANCE);
    }

    @NotNull
    public static final kotlin.sequences.h<m> n(@NotNull m $this$parents) {
        k.f($this$parents, "$this$parents");
        return o.n(o($this$parents), 1);
    }

    @NotNull
    public static final kotlin.reflect.jvm.internal.impl.descriptors.b p(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b $this$propertyIfAccessor) {
        k.f($this$propertyIfAccessor, "$this$propertyIfAccessor");
        if (!($this$propertyIfAccessor instanceof h0)) {
            return $this$propertyIfAccessor;
        }
        i0 Q = ((h0) $this$propertyIfAccessor).Q();
        k.b(Q, "correspondingProperty");
        return Q;
    }

    @Nullable
    public static final kotlin.reflect.jvm.internal.impl.name.b f(@NotNull m $this$fqNameOrNull) {
        k.f($this$fqNameOrNull, "$this$fqNameOrNull");
        kotlin.reflect.jvm.internal.impl.name.c it = k($this$fqNameOrNull);
        if (!it.f()) {
            it = null;
        }
        if (it != null) {
            return it.l();
        }
        return null;
    }

    public static /* synthetic */ kotlin.reflect.jvm.internal.impl.descriptors.b e(kotlin.reflect.jvm.internal.impl.descriptors.b bVar, boolean z, l lVar, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return d(bVar, z, lVar);
    }

    @Nullable
    public static final kotlin.reflect.jvm.internal.impl.descriptors.b d(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b $this$firstOverridden, boolean useOriginal, @NotNull l<? super kotlin.reflect.jvm.internal.impl.descriptors.b, Boolean> predicate) {
        k.f($this$firstOverridden, "$this$firstOverridden");
        k.f(predicate, "predicate");
        z result = new z();
        result.element = null;
        return (kotlin.reflect.jvm.internal.impl.descriptors.b) kotlin.reflect.jvm.internal.impl.utils.b.b(p.b($this$firstOverridden), new d(useOriginal), new e(result, predicate));
    }

    /* compiled from: DescriptorUtils.kt */
    public static final class d<N> implements b.c<N> {
        final /* synthetic */ boolean a;

        d(boolean z) {
            this.a = z;
        }

        @NotNull
        /* renamed from: b */
        public final Iterable<kotlin.reflect.jvm.internal.impl.descriptors.b> a(kotlin.reflect.jvm.internal.impl.descriptors.b current) {
            Collection<? extends kotlin.reflect.jvm.internal.impl.descriptors.b> d;
            kotlin.reflect.jvm.internal.impl.descriptors.b descriptor = this.a ? current != null ? current.a() : null : current;
            return (descriptor == null || (d = descriptor.d()) == null) ? q.g() : d;
        }
    }

    /* compiled from: DescriptorUtils.kt */
    public static final class e extends b.C0433b<kotlin.reflect.jvm.internal.impl.descriptors.b, kotlin.reflect.jvm.internal.impl.descriptors.b> {
        final /* synthetic */ z a;
        final /* synthetic */ l b;

        e(z $captured_local_variable$0, l $captured_local_variable$1) {
            this.a = $captured_local_variable$0;
            this.b = $captured_local_variable$1;
        }

        /* renamed from: e */
        public boolean c(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b current) {
            k.f(current, "current");
            return ((kotlin.reflect.jvm.internal.impl.descriptors.b) this.a.element) == null;
        }

        /* renamed from: d */
        public void b(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b current) {
            k.f(current, "current");
            if (((kotlin.reflect.jvm.internal.impl.descriptors.b) this.a.element) == null && ((Boolean) this.b.invoke(current)).booleanValue()) {
                this.a.element = current;
            }
        }

        @Nullable
        /* renamed from: f */
        public kotlin.reflect.jvm.internal.impl.descriptors.b a() {
            return (kotlin.reflect.jvm.internal.impl.descriptors.b) this.a.element;
        }
    }

    @NotNull
    public static final Collection<kotlin.reflect.jvm.internal.impl.descriptors.e> a(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e sealedClass) {
        k.f(sealedClass, "sealedClass");
        if (sealedClass.p() != w.SEALED) {
            return q.g();
        }
        LinkedHashSet result = new LinkedHashSet();
        C0410a $fun$collectSubclasses$1 = new C0410a(sealedClass, result);
        m container = sealedClass.b();
        k.b(container, "sealedClass.containingDeclaration");
        if (container instanceof b0) {
            $fun$collectSubclasses$1.invoke(((b0) container).l(), false);
        }
        h P = sealedClass.P();
        k.b(P, "sealedClass.unsubstitutedInnerClassesScope");
        $fun$collectSubclasses$1.invoke(P, true);
        return result;
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a$a  reason: collision with other inner class name */
    /* compiled from: DescriptorUtils.kt */
    public static final class C0410a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.p<h, Boolean, x> {
        final /* synthetic */ LinkedHashSet $result;
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.descriptors.e $sealedClass;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0410a(kotlin.reflect.jvm.internal.impl.descriptors.e eVar, LinkedHashSet linkedHashSet) {
            super(2);
            this.$sealedClass = eVar;
            this.$result = linkedHashSet;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            invoke((h) obj, ((Boolean) obj2).booleanValue());
            return x.a;
        }

        public final void invoke(@NotNull h scope, boolean collectNested) {
            k.f(scope, "scope");
            for (m descriptor : j.a.a(scope, kotlin.reflect.jvm.internal.impl.resolve.scopes.d.q, (l) null, 2, (Object) null)) {
                if (descriptor instanceof kotlin.reflect.jvm.internal.impl.descriptors.e) {
                    if (kotlin.reflect.jvm.internal.impl.resolve.c.z((kotlin.reflect.jvm.internal.impl.descriptors.e) descriptor, this.$sealedClass)) {
                        this.$result.add(descriptor);
                    }
                    if (collectNested) {
                        h P = ((kotlin.reflect.jvm.internal.impl.descriptors.e) descriptor).P();
                        k.b(P, "descriptor.unsubstitutedInnerClassesScope");
                        invoke(P, collectNested);
                    }
                }
            }
        }
    }

    @Nullable
    public static final kotlin.reflect.jvm.internal.impl.descriptors.e g(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.c $this$annotationClass) {
        k.f($this$annotationClass, "$this$annotationClass");
        kotlin.reflect.jvm.internal.impl.descriptors.h c2 = $this$annotationClass.getType().I0().c();
        if (!(c2 instanceof kotlin.reflect.jvm.internal.impl.descriptors.e)) {
            c2 = null;
        }
        return (kotlin.reflect.jvm.internal.impl.descriptors.e) c2;
    }

    @Nullable
    public static final kotlin.reflect.jvm.internal.impl.resolve.constants.g<?> c(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.c $this$firstArgument) {
        k.f($this$firstArgument, "$this$firstArgument");
        return (kotlin.reflect.jvm.internal.impl.resolve.constants.g) kotlin.collections.y.T($this$firstArgument.a().values());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0011, code lost:
        r0 = (kotlin.reflect.jvm.internal.impl.types.checker.i) r0.a();
     */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final kotlin.reflect.jvm.internal.impl.types.checker.i l(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.y r1) {
        /*
            java.lang.String r0 = "$this$getKotlinTypeRefiner"
            kotlin.jvm.internal.k.f(r1, r0)
            kotlin.reflect.jvm.internal.impl.descriptors.y$a r0 = kotlin.reflect.jvm.internal.impl.types.checker.j.a()
            java.lang.Object r0 = r1.i0(r0)
            kotlin.reflect.jvm.internal.impl.types.checker.q r0 = (kotlin.reflect.jvm.internal.impl.types.checker.q) r0
            if (r0 == 0) goto L_0x001a
            java.lang.Object r0 = r0.a()
            kotlin.reflect.jvm.internal.impl.types.checker.i r0 = (kotlin.reflect.jvm.internal.impl.types.checker.i) r0
            if (r0 == 0) goto L_0x001a
            goto L_0x001c
        L_0x001a:
            kotlin.reflect.jvm.internal.impl.types.checker.i$a r0 = kotlin.reflect.jvm.internal.impl.types.checker.i.a.a
        L_0x001c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.l(kotlin.reflect.jvm.internal.impl.descriptors.y):kotlin.reflect.jvm.internal.impl.types.checker.i");
    }

    public static final boolean r(@NotNull y $this$isTypeRefinementEnabled) {
        k.f($this$isTypeRefinementEnabled, "$this$isTypeRefinementEnabled");
        kotlin.reflect.jvm.internal.impl.types.checker.q qVar = (kotlin.reflect.jvm.internal.impl.types.checker.q) $this$isTypeRefinementEnabled.i0(kotlin.reflect.jvm.internal.impl.types.checker.j.a());
        return (qVar != null ? (kotlin.reflect.jvm.internal.impl.types.checker.i) qVar.a() : null) != null;
    }
}
