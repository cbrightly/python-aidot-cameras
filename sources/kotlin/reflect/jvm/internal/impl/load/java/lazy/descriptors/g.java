package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.p0;
import kotlin.collections.q;
import kotlin.collections.v;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.c0;
import kotlin.reflect.jvm.internal.impl.descriptors.j0;
import kotlin.reflect.jvm.internal.impl.descriptors.k0;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.load.java.components.k;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.k;
import kotlin.reflect.jvm.internal.impl.load.java.m;
import kotlin.reflect.jvm.internal.impl.load.java.r;
import kotlin.reflect.jvm.internal.impl.load.java.s;
import kotlin.reflect.jvm.internal.impl.load.java.structure.n;
import kotlin.reflect.jvm.internal.impl.load.java.structure.p;
import kotlin.reflect.jvm.internal.impl.load.java.w;
import kotlin.reflect.jvm.internal.impl.load.kotlin.t;
import kotlin.reflect.jvm.internal.impl.resolve.i;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.reflect.jvm.internal.impl.types.u0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LazyJavaClassMemberScope.kt */
public final class g extends k {
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.storage.f<List<kotlin.reflect.jvm.internal.impl.descriptors.d>> n;
    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.storage.f<Set<kotlin.reflect.jvm.internal.impl.name.f>> o;
    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.storage.f<Map<kotlin.reflect.jvm.internal.impl.name.f, n>> p;
    private final kotlin.reflect.jvm.internal.impl.storage.d<kotlin.reflect.jvm.internal.impl.name.f, kotlin.reflect.jvm.internal.impl.descriptors.impl.g> q;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.descriptors.e r;
    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.load.java.structure.g s;
    private final boolean t;

    /* compiled from: LazyJavaClassMemberScope.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<p, Boolean> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((p) obj));
        }

        public final boolean invoke(@NotNull p it) {
            k.f(it, "it");
            return !it.i();
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public g(@NotNull kotlin.reflect.jvm.internal.impl.load.java.lazy.h c2, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.e ownerDescriptor, @NotNull kotlin.reflect.jvm.internal.impl.load.java.structure.g jClass, boolean skipRefinement, @Nullable g mainScope) {
        super(c2, mainScope);
        k.f(c2, "c");
        k.f(ownerDescriptor, "ownerDescriptor");
        k.f(jClass, "jClass");
        this.r = ownerDescriptor;
        this.s = jClass;
        this.t = skipRefinement;
        this.n = c2.e().c(new f(this, c2));
        this.o = c2.e().c(new i(this));
        this.p = c2.e().c(new C0362g(this));
        this.q = c2.e().g(new j(this, c2));
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: q0 */
    public kotlin.reflect.jvm.internal.impl.descriptors.e y() {
        return this.r;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ g(kotlin.reflect.jvm.internal.impl.load.java.lazy.h hVar, kotlin.reflect.jvm.internal.impl.descriptors.e eVar, kotlin.reflect.jvm.internal.impl.load.java.structure.g gVar, boolean z, g gVar2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(hVar, eVar, gVar, z, (i2 & 16) != 0 ? null : gVar2);
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: U */
    public a m() {
        return new a(this.s, a.INSTANCE);
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: T */
    public HashSet<kotlin.reflect.jvm.internal.impl.name.f> l(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d kindFilter, @Nullable kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean> nameFilter) {
        k.f(kindFilter, "kindFilter");
        u0 i2 = y().i();
        k.b(i2, "ownerDescriptor.typeConstructor");
        Iterable<b0> $this$flatMapTo$iv = i2.b();
        k.b($this$flatMapTo$iv, "ownerDescriptor.typeConstructor.supertypes");
        HashSet hashSet = new HashSet();
        for (b0 it : $this$flatMapTo$iv) {
            v.x(hashSet, it.l().a());
        }
        HashSet $this$apply = hashSet;
        $this$apply.addAll(((b) u().invoke()).a());
        $this$apply.addAll(j(kindFilter, nameFilter));
        return hashSet;
    }

    /* compiled from: LazyJavaClassMemberScope.kt */
    public static final class f extends l implements kotlin.jvm.functions.a<List<? extends kotlin.reflect.jvm.internal.impl.descriptors.d>> {
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.load.java.lazy.h $c;
        final /* synthetic */ g this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(g gVar, kotlin.reflect.jvm.internal.impl.load.java.lazy.h hVar) {
            super(0);
            this.this$0 = gVar;
            this.$c = hVar;
        }

        @NotNull
        public final List<kotlin.reflect.jvm.internal.impl.descriptors.d> invoke() {
            Collection<kotlin.reflect.jvm.internal.impl.load.java.structure.k> constructors = this.this$0.s.f();
            ArrayList result = new ArrayList(constructors.size());
            for (kotlin.reflect.jvm.internal.impl.load.java.structure.k constructor : constructors) {
                result.add(this.this$0.y0(constructor));
            }
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l p = this.$c.a().p();
            kotlin.reflect.jvm.internal.impl.load.java.lazy.h hVar = this.$c;
            Collection $this$ifEmpty$iv = result;
            if ($this$ifEmpty$iv.isEmpty()) {
                $this$ifEmpty$iv = q.k(this.this$0.X());
            }
            return y.C0(p.b(hVar, $this$ifEmpty$iv));
        }
    }

    @NotNull
    public final kotlin.reflect.jvm.internal.impl.storage.f<List<kotlin.reflect.jvm.internal.impl.descriptors.d>> o0() {
        return this.n;
    }

    /* access modifiers changed from: protected */
    public boolean C(@NotNull kotlin.reflect.jvm.internal.impl.load.java.descriptors.f $this$isVisibleAsFunction) {
        k.f($this$isVisibleAsFunction, "$this$isVisibleAsFunction");
        if (this.s.l()) {
            return false;
        }
        return t0($this$isVisibleAsFunction);
    }

    private final boolean t0(n0 function) {
        boolean z;
        boolean z2;
        boolean z3;
        kotlin.reflect.jvm.internal.impl.name.f name = function.getName();
        k.b(name, "function.name");
        List<kotlin.reflect.jvm.internal.impl.name.f> a2 = kotlin.reflect.jvm.internal.impl.load.java.v.a(name);
        if (!(a2 instanceof Collection) || !a2.isEmpty()) {
            Iterator<T> it = a2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    n0 n0Var = function;
                    z = false;
                    break;
                }
                Set<i0> r0 = r0((kotlin.reflect.jvm.internal.impl.name.f) it.next());
                if (!(r0 instanceof Collection) || !r0.isEmpty()) {
                    Iterator<T> it2 = r0.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            n0 n0Var2 = function;
                            z2 = false;
                            continue;
                            break;
                        }
                        i0 property = (i0) it2.next();
                        if (!f0(property, new h(this, function)) || (!property.K() && r.h(function.getName().b()))) {
                            z3 = false;
                            continue;
                        } else {
                            z3 = true;
                            continue;
                        }
                        if (z3) {
                            z2 = true;
                            continue;
                            break;
                        }
                    }
                } else {
                    n0 n0Var3 = function;
                    z2 = false;
                    continue;
                }
                if (z2) {
                    z = true;
                    break;
                }
            }
        } else {
            n0 n0Var4 = function;
            z = false;
        }
        if (z) {
            return false;
        }
        return !h0(function) && !B0(function) && !j0(function);
    }

    /* compiled from: LazyJavaClassMemberScope.kt */
    public static final class h extends l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.name.f, Collection<? extends n0>> {
        final /* synthetic */ n0 $function$inlined;
        final /* synthetic */ g this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        h(g gVar, n0 n0Var) {
            super(1);
            this.this$0 = gVar;
            this.$function$inlined = n0Var;
        }

        @NotNull
        public final Collection<n0> invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.f accessorName) {
            k.f(accessorName, "accessorName");
            if (k.a(this.$function$inlined.getName(), accessorName)) {
                return kotlin.collections.p.b(this.$function$inlined);
            }
            return y.n0(this.this$0.z0(accessorName), this.this$0.A0(accessorName));
        }
    }

    private final boolean B0(@NotNull n0 $this$shouldBeVisibleAsOverrideOfBuiltInWithErasedValueParameters) {
        kotlin.reflect.jvm.internal.impl.load.java.d dVar = kotlin.reflect.jvm.internal.impl.load.java.d.h;
        kotlin.reflect.jvm.internal.impl.name.f name = $this$shouldBeVisibleAsOverrideOfBuiltInWithErasedValueParameters.getName();
        k.b(name, "name");
        if (!dVar.d(name)) {
            return false;
        }
        kotlin.reflect.jvm.internal.impl.name.f name2 = $this$shouldBeVisibleAsOverrideOfBuiltInWithErasedValueParameters.getName();
        k.b(name2, "name");
        Iterable<n0> $this$mapNotNullTo$iv$iv = p0(name2);
        List candidatesToOverride = new ArrayList();
        for (n0 it : $this$mapNotNullTo$iv$iv) {
            Object it$iv$iv = kotlin.reflect.jvm.internal.impl.load.java.d.c(it);
            if (it$iv$iv != null) {
                candidatesToOverride.add(it$iv$iv);
            }
        }
        List<u> list = candidatesToOverride;
        if (list.isEmpty()) {
            return false;
        }
        for (u candidate : list) {
            if (s0($this$shouldBeVisibleAsOverrideOfBuiltInWithErasedValueParameters, candidate)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public final Collection<n0> z0(kotlin.reflect.jvm.internal.impl.name.f name) {
        Iterable<kotlin.reflect.jvm.internal.impl.load.java.structure.q> $this$mapTo$iv$iv = ((b) u().invoke()).c(name);
        Collection destination$iv$iv = new ArrayList(kotlin.collections.r.r($this$mapTo$iv$iv, 10));
        for (kotlin.reflect.jvm.internal.impl.load.java.structure.q it : $this$mapTo$iv$iv) {
            destination$iv$iv.add(E(it));
        }
        return destination$iv$iv;
    }

    /* access modifiers changed from: private */
    public final Collection<n0> A0(kotlin.reflect.jvm.internal.impl.name.f name) {
        Iterable $this$filterNotTo$iv$iv = p0(name);
        Collection destination$iv$iv = new ArrayList();
        for (T next : $this$filterNotTo$iv$iv) {
            n0 it = (n0) next;
            if (!(w.f(it) || kotlin.reflect.jvm.internal.impl.load.java.d.c(it) != null)) {
                destination$iv$iv.add(next);
            }
        }
        return destination$iv$iv;
    }

    private final boolean h0(@NotNull n0 $this$doesOverrideRenamedBuiltins) {
        boolean z;
        kotlin.reflect.jvm.internal.impl.load.java.c cVar = kotlin.reflect.jvm.internal.impl.load.java.c.f;
        kotlin.reflect.jvm.internal.impl.name.f name = $this$doesOverrideRenamedBuiltins.getName();
        k.b(name, "name");
        List<kotlin.reflect.jvm.internal.impl.name.f> b2 = cVar.b(name);
        if (!(b2 instanceof Collection) || !b2.isEmpty()) {
            for (kotlin.reflect.jvm.internal.impl.name.f builtinName : b2) {
                Iterable $this$filterTo$iv$iv = p0(builtinName);
                List arrayList = new ArrayList();
                for (T next : $this$filterTo$iv$iv) {
                    if (w.f((n0) next)) {
                        arrayList.add(next);
                    }
                }
                List builtinSpecialFromSuperTypes = arrayList;
                if (!builtinSpecialFromSuperTypes.isEmpty()) {
                    n0 methodDescriptor = d0($this$doesOverrideRenamedBuiltins, builtinName);
                    List list = builtinSpecialFromSuperTypes;
                    if (!list.isEmpty()) {
                        Iterator it = list.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if (i0((n0) it.next(), methodDescriptor)) {
                                    z = true;
                                    continue;
                                    break;
                                }
                            } else {
                                z = false;
                                continue;
                                break;
                            }
                        }
                    } else {
                        z = false;
                        continue;
                    }
                } else {
                    n0 n0Var = $this$doesOverrideRenamedBuiltins;
                    z = false;
                    continue;
                }
                if (z) {
                    return true;
                }
            }
            n0 n0Var2 = $this$doesOverrideRenamedBuiltins;
            return false;
        }
        n0 n0Var3 = $this$doesOverrideRenamedBuiltins;
        return false;
    }

    private final boolean j0(@NotNull n0 $this$doesOverrideSuspendFunction) {
        n0 overriddenCandidate;
        n0 suspendView = e0($this$doesOverrideSuspendFunction);
        if (suspendView == null) {
            return false;
        }
        kotlin.reflect.jvm.internal.impl.name.f name = $this$doesOverrideSuspendFunction.getName();
        k.b(name, "name");
        Set<n0> p0 = p0(name);
        if ((p0 instanceof Collection) && p0.isEmpty()) {
            return false;
        }
        for (n0 overriddenCandidate2 : p0) {
            if (!overriddenCandidate2.isSuspend() || !g0(suspendView, overriddenCandidate2)) {
                overriddenCandidate = null;
                continue;
            } else {
                overriddenCandidate = 1;
                continue;
            }
            if (overriddenCandidate != null) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0057  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final kotlin.reflect.jvm.internal.impl.descriptors.n0 e0(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.n0 r9) {
        /*
            r8 = this;
            java.util.List r0 = r9.g()
            java.lang.String r1 = "valueParameters"
            kotlin.jvm.internal.k.b(r0, r1)
            java.lang.Object r0 = kotlin.collections.y.f0(r0)
            kotlin.reflect.jvm.internal.impl.descriptors.w0 r0 = (kotlin.reflect.jvm.internal.impl.descriptors.w0) r0
            r2 = 0
            if (r0 == 0) goto L_0x0097
            r3 = r0
            r4 = 0
            kotlin.reflect.jvm.internal.impl.types.b0 r5 = r3.getType()
            kotlin.reflect.jvm.internal.impl.types.u0 r5 = r5.I0()
            kotlin.reflect.jvm.internal.impl.descriptors.h r5 = r5.c()
            if (r5 == 0) goto L_0x003b
            kotlin.reflect.jvm.internal.impl.name.c r5 = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.k(r5)
            if (r5 == 0) goto L_0x003b
            r6 = r5
            r7 = 0
            boolean r6 = r6.f()
            if (r6 == 0) goto L_0x0033
            goto L_0x0034
        L_0x0033:
            r5 = r2
        L_0x0034:
            if (r5 == 0) goto L_0x003b
            kotlin.reflect.jvm.internal.impl.name.b r5 = r5.l()
            goto L_0x003c
        L_0x003b:
            r5 = r2
        L_0x003c:
            kotlin.reflect.jvm.internal.impl.load.java.lazy.h r6 = r8.t()
            kotlin.reflect.jvm.internal.impl.load.java.lazy.b r6 = r6.a()
            kotlin.reflect.jvm.internal.impl.load.java.lazy.c r6 = r6.o()
            boolean r6 = r6.a()
            boolean r5 = kotlin.reflect.jvm.internal.impl.builtins.k.a(r5, r6)
            if (r5 == 0) goto L_0x0054
            goto L_0x0055
        L_0x0054:
            r0 = r2
        L_0x0055:
            if (r0 == 0) goto L_0x0097
            kotlin.reflect.jvm.internal.impl.descriptors.u$a r2 = r9.r()
            java.util.List r3 = r9.g()
            kotlin.jvm.internal.k.b(r3, r1)
            r1 = 1
            java.util.List r3 = kotlin.collections.y.P(r3, r1)
            kotlin.reflect.jvm.internal.impl.descriptors.u$a r2 = r2.b(r3)
            kotlin.reflect.jvm.internal.impl.types.b0 r3 = r0.getType()
            java.util.List r3 = r3.H0()
            r4 = 0
            java.lang.Object r3 = r3.get(r4)
            kotlin.reflect.jvm.internal.impl.types.w0 r3 = (kotlin.reflect.jvm.internal.impl.types.w0) r3
            kotlin.reflect.jvm.internal.impl.types.b0 r3 = r3.getType()
            kotlin.reflect.jvm.internal.impl.descriptors.u$a r2 = r2.l(r3)
            kotlin.reflect.jvm.internal.impl.descriptors.u r2 = r2.build()
            kotlin.reflect.jvm.internal.impl.descriptors.n0 r2 = (kotlin.reflect.jvm.internal.impl.descriptors.n0) r2
            r3 = r2
            kotlin.reflect.jvm.internal.impl.descriptors.impl.f0 r3 = (kotlin.reflect.jvm.internal.impl.descriptors.impl.f0) r3
            if (r3 == 0) goto L_0x0096
            r3.Z0(r1)
        L_0x0096:
            return r2
        L_0x0097:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.g.e0(kotlin.reflect.jvm.internal.impl.descriptors.n0):kotlin.reflect.jvm.internal.impl.descriptors.n0");
    }

    private final n0 d0(@NotNull n0 $this$createRenamedCopy, kotlin.reflect.jvm.internal.impl.name.f builtinName) {
        u.a r2 = $this$createRenamedCopy.r();
        u.a $this$apply = r2;
        $this$apply.i(builtinName);
        $this$apply.s();
        $this$apply.k();
        Object build = r2.build();
        if (build == null) {
            k.n();
        }
        return (n0) build;
    }

    private final boolean i0(n0 superDescriptor, u subDescriptor) {
        u subDescriptorToCheck = kotlin.reflect.jvm.internal.impl.load.java.c.f.g(superDescriptor) ? subDescriptor.a() : subDescriptor;
        k.b(subDescriptorToCheck, "if (superDescriptor.isRe…iginal else subDescriptor");
        return g0(subDescriptorToCheck, superDescriptor);
    }

    private final boolean g0(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a $this$doesOverride, kotlin.reflect.jvm.internal.impl.descriptors.a superDescriptor) {
        i.j G = kotlin.reflect.jvm.internal.impl.resolve.i.b.G(superDescriptor, $this$doesOverride, true);
        k.b(G, "OverridingUtil.DEFAULT.i…erDescriptor, this, true)");
        i.j.a commonOverridabilityResult = G.c();
        k.b(commonOverridabilityResult, "OverridingUtil.DEFAULT.i…iptor, this, true).result");
        if (commonOverridabilityResult != i.j.a.OVERRIDABLE || kotlin.reflect.jvm.internal.impl.load.java.p.a.a(superDescriptor, $this$doesOverride)) {
            return false;
        }
        return true;
    }

    private final n0 l0(@NotNull i0 $this$findGetterOverride, kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, ? extends Collection<? extends n0>> functions) {
        j0 getter = $this$findGetterOverride.getGetter();
        String specialGetterName = null;
        j0 overriddenBuiltinProperty = getter != null ? (j0) w.i(getter) : null;
        if (overriddenBuiltinProperty != null) {
            specialGetterName = kotlin.reflect.jvm.internal.impl.load.java.e.e.a(overriddenBuiltinProperty);
        }
        if (specialGetterName != null && !w.k(y(), overriddenBuiltinProperty)) {
            return k0($this$findGetterOverride, specialGetterName, functions);
        }
        String b2 = r.b($this$findGetterOverride.getName().b());
        k.b(b2, "JvmAbi.getterName(name.asString())");
        return k0($this$findGetterOverride, b2, functions);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: kotlin.reflect.jvm.internal.impl.descriptors.n0} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final kotlin.reflect.jvm.internal.impl.descriptors.n0 k0(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.i0 r13, java.lang.String r14, kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, ? extends java.util.Collection<? extends kotlin.reflect.jvm.internal.impl.descriptors.n0>> r15) {
        /*
            r12 = this;
            kotlin.reflect.jvm.internal.impl.name.f r0 = kotlin.reflect.jvm.internal.impl.name.f.f(r14)
            java.lang.String r1 = "Name.identifier(getterName)"
            kotlin.jvm.internal.k.b(r0, r1)
            java.lang.Object r0 = r15.invoke(r0)
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            r1 = 0
            java.util.Iterator r2 = r0.iterator()
        L_0x0014:
            boolean r3 = r2.hasNext()
            r4 = 0
            if (r3 == 0) goto L_0x004a
            java.lang.Object r3 = r2.next()
            r5 = r3
            kotlin.reflect.jvm.internal.impl.descriptors.n0 r5 = (kotlin.reflect.jvm.internal.impl.descriptors.n0) r5
            r6 = 0
            java.util.List r7 = r5.g()
            int r7 = r7.size()
            if (r7 == 0) goto L_0x002e
            goto L_0x0045
        L_0x002e:
            r7 = r5
            r8 = 0
            kotlin.reflect.jvm.internal.impl.types.checker.g r9 = kotlin.reflect.jvm.internal.impl.types.checker.g.a
            kotlin.reflect.jvm.internal.impl.types.b0 r10 = r5.getReturnType()
            if (r10 == 0) goto L_0x0041
            kotlin.reflect.jvm.internal.impl.types.b0 r11 = r13.getType()
            boolean r9 = r9.d(r10, r11)
            goto L_0x0042
        L_0x0041:
            r9 = 0
        L_0x0042:
            if (r9 == 0) goto L_0x0045
            r4 = r5
        L_0x0045:
            if (r4 == 0) goto L_0x0049
            goto L_0x004b
        L_0x0049:
            goto L_0x0014
        L_0x004a:
        L_0x004b:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.g.k0(kotlin.reflect.jvm.internal.impl.descriptors.i0, java.lang.String, kotlin.jvm.functions.l):kotlin.reflect.jvm.internal.impl.descriptors.n0");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: kotlin.reflect.jvm.internal.impl.descriptors.n0} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final kotlin.reflect.jvm.internal.impl.descriptors.n0 m0(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.i0 r13, kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, ? extends java.util.Collection<? extends kotlin.reflect.jvm.internal.impl.descriptors.n0>> r14) {
        /*
            r12 = this;
            kotlin.reflect.jvm.internal.impl.name.f r0 = r13.getName()
            java.lang.String r0 = r0.b()
            java.lang.String r0 = kotlin.reflect.jvm.internal.impl.load.java.r.i(r0)
            kotlin.reflect.jvm.internal.impl.name.f r0 = kotlin.reflect.jvm.internal.impl.name.f.f(r0)
            java.lang.String r1 = "Name.identifier(JvmAbi.s…terName(name.asString()))"
            kotlin.jvm.internal.k.b(r0, r1)
            java.lang.Object r0 = r14.invoke(r0)
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            r1 = 0
            java.util.Iterator r2 = r0.iterator()
        L_0x0020:
            boolean r3 = r2.hasNext()
            r4 = 0
            if (r3 == 0) goto L_0x0074
            java.lang.Object r3 = r2.next()
            r5 = r3
            kotlin.reflect.jvm.internal.impl.descriptors.n0 r5 = (kotlin.reflect.jvm.internal.impl.descriptors.n0) r5
            r6 = 0
            java.util.List r7 = r5.g()
            int r7 = r7.size()
            r8 = 1
            if (r7 == r8) goto L_0x003b
            goto L_0x006f
        L_0x003b:
            kotlin.reflect.jvm.internal.impl.types.b0 r7 = r5.getReturnType()
            if (r7 == 0) goto L_0x006f
            boolean r7 = kotlin.reflect.jvm.internal.impl.builtins.g.J0(r7)
            if (r7 != 0) goto L_0x0048
            goto L_0x006f
        L_0x0048:
            r7 = r5
            r8 = 0
            kotlin.reflect.jvm.internal.impl.types.checker.g r9 = kotlin.reflect.jvm.internal.impl.types.checker.g.a
            java.util.List r10 = r5.g()
            java.lang.String r11 = "descriptor.valueParameters"
            kotlin.jvm.internal.k.b(r10, r11)
            java.lang.Object r10 = kotlin.collections.y.q0(r10)
            java.lang.String r11 = "descriptor.valueParameters.single()"
            kotlin.jvm.internal.k.b(r10, r11)
            kotlin.reflect.jvm.internal.impl.descriptors.w0 r10 = (kotlin.reflect.jvm.internal.impl.descriptors.w0) r10
            kotlin.reflect.jvm.internal.impl.types.b0 r10 = r10.getType()
            kotlin.reflect.jvm.internal.impl.types.b0 r11 = r13.getType()
            boolean r7 = r9.b(r10, r11)
            if (r7 == 0) goto L_0x006f
            r4 = r5
        L_0x006f:
            if (r4 == 0) goto L_0x0073
            goto L_0x0075
        L_0x0073:
            goto L_0x0020
        L_0x0074:
        L_0x0075:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.g.m0(kotlin.reflect.jvm.internal.impl.descriptors.i0, kotlin.jvm.functions.l):kotlin.reflect.jvm.internal.impl.descriptors.n0");
    }

    private final boolean f0(i0 property, kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, ? extends Collection<? extends n0>> functions) {
        if (c.a(property)) {
            return false;
        }
        n0 getter = l0(property, functions);
        n0 setter = m0(property, functions);
        if (getter == null) {
            return false;
        }
        if (!property.K()) {
            return true;
        }
        if (setter == null || setter.p() != getter.p()) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void o(@NotNull Collection<n0> result, @NotNull kotlin.reflect.jvm.internal.impl.name.f name) {
        Iterable $this$none$iv;
        Collection<n0> collection = result;
        kotlin.reflect.jvm.internal.impl.name.f fVar = name;
        k.f(collection, "result");
        k.f(fVar, "name");
        Iterable $this$filterTo$iv$iv = p0(fVar);
        if (!kotlin.reflect.jvm.internal.impl.load.java.c.f.e(fVar) && !kotlin.reflect.jvm.internal.impl.load.java.d.h.d(fVar)) {
            Set<n0> set = $this$filterTo$iv$iv;
            if (!(set instanceof Collection) || !set.isEmpty()) {
                Iterator<T> it = set.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (((u) it.next()).isSuspend()) {
                            $this$none$iv = null;
                            break;
                        }
                    } else {
                        $this$none$iv = 1;
                        break;
                    }
                }
            } else {
                $this$none$iv = 1;
            }
            if ($this$none$iv != null) {
                Collection destination$iv$iv = new ArrayList();
                for (T next : $this$filterTo$iv$iv) {
                    if (t0((n0) next)) {
                        destination$iv$iv.add(next);
                    }
                }
                P(collection, fVar, destination$iv$iv, false);
                return;
            }
        }
        kotlin.reflect.jvm.internal.impl.utils.j specialBuiltinsFromSuperTypes = kotlin.reflect.jvm.internal.impl.utils.j.c.a();
        Collection<D> g = kotlin.reflect.jvm.internal.impl.load.java.components.a.g(name, $this$filterTo$iv$iv, q.g(), y(), kotlin.reflect.jvm.internal.impl.serialization.deserialization.r.a, t().a().i().a());
        k.b(g, "resolveOverridesForNonSt….overridingUtil\n        )");
        Q(name, result, g, result, new b(this));
        Q(name, result, g, specialBuiltinsFromSuperTypes, new c(this));
        Collection destination$iv$iv2 = new ArrayList();
        for (T next2 : $this$filterTo$iv$iv) {
            if (t0((n0) next2)) {
                destination$iv$iv2.add(next2);
            }
        }
        P(collection, fVar, y.n0(destination$iv$iv2, specialBuiltinsFromSuperTypes), true);
    }

    /* compiled from: LazyJavaClassMemberScope.kt */
    public static final /* synthetic */ class b extends kotlin.jvm.internal.h implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.name.f, Collection<? extends n0>> {
        b(g gVar) {
            super(1, gVar);
        }

        public final String getName() {
            return "searchMethodsByNameWithoutBuiltinMagic";
        }

        public final kotlin.reflect.e getOwner() {
            return a0.b(g.class);
        }

        public final String getSignature() {
            return "searchMethodsByNameWithoutBuiltinMagic(Lorg/jetbrains/kotlin/name/Name;)Ljava/util/Collection;";
        }

        @NotNull
        public final Collection<n0> invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.f p1) {
            k.f(p1, "p1");
            return ((g) this.receiver).z0(p1);
        }
    }

    /* compiled from: LazyJavaClassMemberScope.kt */
    public static final /* synthetic */ class c extends kotlin.jvm.internal.h implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.name.f, Collection<? extends n0>> {
        c(g gVar) {
            super(1, gVar);
        }

        public final String getName() {
            return "searchMethodsInSupertypesWithoutBuiltinMagic";
        }

        public final kotlin.reflect.e getOwner() {
            return a0.b(g.class);
        }

        public final String getSignature() {
            return "searchMethodsInSupertypesWithoutBuiltinMagic(Lorg/jetbrains/kotlin/name/Name;)Ljava/util/Collection;";
        }

        @NotNull
        public final Collection<n0> invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.f p1) {
            k.f(p1, "p1");
            return ((g) this.receiver).A0(p1);
        }
    }

    private final void P(Collection<n0> result, kotlin.reflect.jvm.internal.impl.name.f name, Collection<? extends n0> functionsFromSupertypes, boolean isSpecialBuiltinName) {
        Collection<n0> collection = result;
        Collection<D> g = kotlin.reflect.jvm.internal.impl.load.java.components.a.g(name, functionsFromSupertypes, result, y(), t().a().c(), t().a().i().a());
        k.b(g, "resolveOverridesForNonSt….overridingUtil\n        )");
        if (!isSpecialBuiltinName) {
            result.addAll(g);
            return;
        }
        List allDescriptors = y.n0(result, g);
        Iterable<n0> $this$mapTo$iv$iv = g;
        Collection destination$iv$iv = new ArrayList(kotlin.collections.r.r($this$mapTo$iv$iv, 10));
        for (n0 resolvedOverride : $this$mapTo$iv$iv) {
            n0 overriddenBuiltin = (n0) w.j(resolvedOverride);
            if (overriddenBuiltin != null) {
                k.b(resolvedOverride, "resolvedOverride");
                resolvedOverride = Y(resolvedOverride, overriddenBuiltin, allDescriptors);
            }
            destination$iv$iv.add(resolvedOverride);
        }
        result.addAll(destination$iv$iv);
    }

    private final void Q(kotlin.reflect.jvm.internal.impl.name.f name, Collection<? extends n0> alreadyDeclaredFunctions, Collection<? extends n0> candidatesForOverride, Collection<n0> result, kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, ? extends Collection<? extends n0>> functions) {
        for (n0 descriptor : candidatesForOverride) {
            kotlin.reflect.jvm.internal.impl.utils.a.a(result, v0(descriptor, functions, name, alreadyDeclaredFunctions));
            kotlin.reflect.jvm.internal.impl.utils.a.a(result, u0(descriptor, functions, alreadyDeclaredFunctions));
            kotlin.reflect.jvm.internal.impl.utils.a.a(result, w0(descriptor, functions));
        }
    }

    private final n0 u0(n0 descriptor, kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, ? extends Collection<? extends n0>> functions, Collection<? extends n0> alreadyDeclaredFunctions) {
        n0 p1;
        u overriddenBuiltin = kotlin.reflect.jvm.internal.impl.load.java.d.c(descriptor);
        if (overriddenBuiltin == null || (p1 = Z(overriddenBuiltin, functions)) == null) {
            return null;
        }
        if (!t0(p1)) {
            p1 = null;
        }
        if (p1 != null) {
            return Y(p1, overriddenBuiltin, alreadyDeclaredFunctions);
        }
        return null;
    }

    private final n0 v0(n0 descriptor, kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, ? extends Collection<? extends n0>> functions, kotlin.reflect.jvm.internal.impl.name.f name, Collection<? extends n0> alreadyDeclaredFunctions) {
        n0 overriddenBuiltin = (n0) w.i(descriptor);
        if (overriddenBuiltin == null) {
            return null;
        }
        String nameInJava = w.g(overriddenBuiltin);
        if (nameInJava == null) {
            k.n();
        }
        kotlin.reflect.jvm.internal.impl.name.f f2 = kotlin.reflect.jvm.internal.impl.name.f.f(nameInJava);
        k.b(f2, "Name.identifier(nameInJava)");
        for (n0 method : (Collection) functions.invoke(f2)) {
            n0 renamedCopy = d0(method, name);
            if (i0(overriddenBuiltin, renamedCopy)) {
                return Y(renamedCopy, overriddenBuiltin, alreadyDeclaredFunctions);
            }
        }
        return null;
    }

    private final n0 w0(n0 descriptor, kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, ? extends Collection<? extends n0>> functions) {
        if (!descriptor.isSuspend()) {
            return null;
        }
        kotlin.reflect.jvm.internal.impl.name.f name = descriptor.getName();
        k.b(name, "descriptor.name");
        for (n0 overrideCandidate : (Iterable) functions.invoke(name)) {
            n0 suspendView = e0(overrideCandidate);
            if (suspendView == null || !g0(suspendView, descriptor)) {
                suspendView = null;
            }
            n0 overrideCandidate2 = suspendView;
            if (overrideCandidate2 != null) {
                return overrideCandidate2;
            }
        }
        return null;
    }

    private final n0 Y(@NotNull n0 $this$createHiddenCopyIfBuiltinAlreadyAccidentallyOverridden, kotlin.reflect.jvm.internal.impl.descriptors.a specialBuiltin, Collection<? extends n0> alreadyDeclaredFunctions) {
        n0 it;
        Collection<? extends n0> collection = alreadyDeclaredFunctions;
        boolean z = false;
        if (!(collection instanceof Collection) || !collection.isEmpty()) {
            Iterator<T> it2 = collection.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z = true;
                    break;
                }
                n0 it3 = (n0) it2.next();
                if (!(!k.a($this$createHiddenCopyIfBuiltinAlreadyAccidentallyOverridden, it3)) || it3.o0() != null || !g0(it3, specialBuiltin)) {
                    it = null;
                    continue;
                } else {
                    it = 1;
                    continue;
                }
                if (it != null) {
                    break;
                }
            }
        } else {
            z = true;
        }
        if (z) {
            return $this$createHiddenCopyIfBuiltinAlreadyAccidentallyOverridden;
        }
        Object build = $this$createHiddenCopyIfBuiltinAlreadyAccidentallyOverridden.r().h().build();
        if (build == null) {
            k.n();
        }
        return (n0) build;
    }

    private final n0 Z(u overridden, kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, ? extends Collection<? extends n0>> functions) {
        Object element$iv;
        u uVar = overridden;
        kotlin.reflect.jvm.internal.impl.name.f name = overridden.getName();
        k.b(name, "overridden.name");
        Iterator it = ((Iterable) functions.invoke(name)).iterator();
        while (true) {
            if (!it.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it.next();
            if (s0((n0) element$iv, uVar)) {
                break;
            }
        }
        n0 n0Var = (n0) element$iv;
        if (n0Var == null) {
            return null;
        }
        n0 override = n0Var;
        int i2 = 0;
        u.a r2 = override.r();
        u.a $this$apply = r2;
        int i3 = false;
        Iterable<w0> $this$mapTo$iv$iv = overridden.g();
        k.b($this$mapTo$iv$iv, "overridden.valueParameters");
        Collection destination$iv$iv = new ArrayList(kotlin.collections.r.r($this$mapTo$iv$iv, 10));
        for (w0 it2 : $this$mapTo$iv$iv) {
            int i4 = i2;
            k.b(it2, "it");
            b0 type = it2.getType();
            k.b(type, "it.type");
            destination$iv$iv.add(new kotlin.reflect.jvm.internal.impl.load.java.descriptors.l(type, it2.v0()));
            kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, ? extends Collection<? extends n0>> lVar = functions;
            i2 = i4;
            i3 = i3;
        }
        int i5 = i2;
        int i6 = i3;
        List<w0> g = override.g();
        k.b(g, "override.valueParameters");
        $this$apply.b(kotlin.reflect.jvm.internal.impl.load.java.descriptors.k.a(destination$iv$iv, g, uVar));
        $this$apply.s();
        $this$apply.k();
        return (n0) r2.build();
    }

    private final Set<n0> p0(kotlin.reflect.jvm.internal.impl.name.f name) {
        Iterable<b0> $this$flatMapTo$iv = V();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (b0 it : $this$flatMapTo$iv) {
            v.x(linkedHashSet, it.l().b(name, kotlin.reflect.jvm.internal.impl.incremental.components.d.WHEN_GET_SUPER_MEMBERS));
        }
        return linkedHashSet;
    }

    /* access modifiers changed from: protected */
    public void p(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull Collection<i0> result) {
        k.f(name, "name");
        k.f(result, "result");
        if (this.s.l()) {
            S(name, result);
        }
        Set propertiesFromSupertypes = r0(name);
        if (!propertiesFromSupertypes.isEmpty()) {
            kotlin.reflect.jvm.internal.impl.utils.j propertiesOverridesFromSuperTypes = kotlin.reflect.jvm.internal.impl.utils.j.c.a();
            R(propertiesFromSupertypes, result, new d(this));
            R(propertiesFromSupertypes, propertiesOverridesFromSuperTypes, new e(this));
            Collection<D> g = kotlin.reflect.jvm.internal.impl.load.java.components.a.g(name, p0.i(propertiesFromSupertypes, propertiesOverridesFromSuperTypes), result, y(), t().a().c(), t().a().i().a());
            k.b(g, "resolveOverridesForNonSt…rridingUtil\n            )");
            result.addAll(g);
        }
    }

    /* compiled from: LazyJavaClassMemberScope.kt */
    public static final class d extends l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.name.f, Collection<? extends n0>> {
        final /* synthetic */ g this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(g gVar) {
            super(1);
            this.this$0 = gVar;
        }

        @NotNull
        public final Collection<n0> invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.f it) {
            k.f(it, "it");
            return this.this$0.z0(it);
        }
    }

    /* compiled from: LazyJavaClassMemberScope.kt */
    public static final class e extends l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.name.f, Collection<? extends n0>> {
        final /* synthetic */ g this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(g gVar) {
            super(1);
            this.this$0 = gVar;
        }

        @NotNull
        public final Collection<n0> invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.f it) {
            k.f(it, "it");
            return this.this$0.A0(it);
        }
    }

    private final void R(Set<? extends i0> propertiesFromSupertypes, Collection<i0> result, kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, ? extends Collection<? extends n0>> functions) {
        for (i0 property : propertiesFromSupertypes) {
            kotlin.reflect.jvm.internal.impl.load.java.descriptors.g newProperty = a0(property, functions);
            if (newProperty != null) {
                result.add(newProperty);
                return;
            }
        }
    }

    private final void S(kotlin.reflect.jvm.internal.impl.name.f name, Collection<i0> result) {
        kotlin.reflect.jvm.internal.impl.load.java.structure.q method = (kotlin.reflect.jvm.internal.impl.load.java.structure.q) y.r0(((b) u().invoke()).c(name));
        if (method != null) {
            result.add(c0(this, method, (b0) null, kotlin.reflect.jvm.internal.impl.descriptors.w.FINAL, 2, (Object) null));
        }
    }

    static /* synthetic */ kotlin.reflect.jvm.internal.impl.load.java.descriptors.g c0(g gVar, kotlin.reflect.jvm.internal.impl.load.java.structure.q qVar, b0 b0Var, kotlin.reflect.jvm.internal.impl.descriptors.w wVar, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            b0Var = null;
        }
        return gVar.b0(qVar, b0Var, wVar);
    }

    private final kotlin.reflect.jvm.internal.impl.load.java.descriptors.g b0(kotlin.reflect.jvm.internal.impl.load.java.structure.q method, b0 givenType, kotlin.reflect.jvm.internal.impl.descriptors.w modality) {
        kotlin.reflect.jvm.internal.impl.load.java.descriptors.g propertyDescriptor = kotlin.reflect.jvm.internal.impl.load.java.descriptors.g.U0(y(), kotlin.reflect.jvm.internal.impl.load.java.lazy.f.a(t(), method), modality, method.getVisibility(), false, method.getName(), t().a().r().a(method), false);
        k.b(propertyDescriptor, "JavaPropertyDescriptor.c…inal = */ false\n        )");
        c0 getter = kotlin.reflect.jvm.internal.impl.resolve.b.b(propertyDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b.b());
        k.b(getter, "DescriptorFactory.create…iptor, Annotations.EMPTY)");
        propertyDescriptor.M0(getter, (k0) null);
        b0 returnType = givenType != null ? givenType : n(method, kotlin.reflect.jvm.internal.impl.load.java.lazy.a.f(t(), propertyDescriptor, method, 0, 4, (Object) null));
        propertyDescriptor.S0(returnType, q.g(), v(), (l0) null);
        getter.J0(returnType);
        return propertyDescriptor;
    }

    /* JADX WARNING: type inference failed for: r1v9, types: [kotlin.reflect.jvm.internal.impl.descriptors.w] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final kotlin.reflect.jvm.internal.impl.load.java.descriptors.g a0(kotlin.reflect.jvm.internal.impl.descriptors.i0 r14, kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, ? extends java.util.Collection<? extends kotlin.reflect.jvm.internal.impl.descriptors.n0>> r15) {
        /*
            r13 = this;
            boolean r0 = r13.f0(r14, r15)
            r1 = 0
            if (r0 != 0) goto L_0x0008
            return r1
        L_0x0008:
            kotlin.reflect.jvm.internal.impl.descriptors.n0 r0 = r13.l0(r14, r15)
            if (r0 != 0) goto L_0x0011
            kotlin.jvm.internal.k.n()
        L_0x0011:
            boolean r2 = r14.K()
            if (r2 == 0) goto L_0x0022
            kotlin.reflect.jvm.internal.impl.descriptors.n0 r2 = r13.m0(r14, r15)
            if (r2 != 0) goto L_0x0023
            kotlin.jvm.internal.k.n()
            goto L_0x0023
        L_0x0022:
            r2 = r1
        L_0x0023:
            r3 = 1
            if (r2 == 0) goto L_0x0036
            r4 = r2
            r5 = 0
            kotlin.reflect.jvm.internal.impl.descriptors.w r6 = r4.p()
            kotlin.reflect.jvm.internal.impl.descriptors.w r7 = r0.p()
            if (r6 != r7) goto L_0x0035
            goto L_0x0036
        L_0x0035:
            r3 = 0
        L_0x0036:
            if (r3 != 0) goto L_0x0076
            r3 = 0
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Different accessors modalities when creating overrides for "
            r4.append(r5)
            r4.append(r14)
            java.lang.String r5 = " in "
            r4.append(r5)
            kotlin.reflect.jvm.internal.impl.descriptors.e r5 = r13.y()
            r4.append(r5)
            java.lang.String r5 = "for getter is "
            r4.append(r5)
            kotlin.reflect.jvm.internal.impl.descriptors.w r5 = r0.p()
            r4.append(r5)
            java.lang.String r5 = ", but for setter is "
            r4.append(r5)
            if (r2 == 0) goto L_0x0069
            kotlin.reflect.jvm.internal.impl.descriptors.w r1 = r2.p()
        L_0x0069:
            r4.append(r1)
            java.lang.String r1 = r4.toString()
            java.lang.AssertionError r3 = new java.lang.AssertionError
            r3.<init>(r1)
            throw r3
        L_0x0076:
            kotlin.reflect.jvm.internal.impl.load.java.descriptors.e r3 = new kotlin.reflect.jvm.internal.impl.load.java.descriptors.e
            kotlin.reflect.jvm.internal.impl.descriptors.e r4 = r13.y()
            r3.<init>(r4, r0, r2, r14)
            kotlin.reflect.jvm.internal.impl.types.b0 r4 = r0.getReturnType()
            if (r4 != 0) goto L_0x0088
            kotlin.jvm.internal.k.n()
        L_0x0088:
            java.util.List r5 = kotlin.collections.q.g()
            kotlin.reflect.jvm.internal.impl.descriptors.l0 r6 = r13.v()
            r3.S0(r4, r5, r6, r1)
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r6 = r0.getAnnotations()
            r7 = 0
            r8 = 0
            r9 = 0
            kotlin.reflect.jvm.internal.impl.descriptors.o0 r10 = r0.n()
            r5 = r3
            kotlin.reflect.jvm.internal.impl.descriptors.impl.c0 r4 = kotlin.reflect.jvm.internal.impl.resolve.b.h(r5, r6, r7, r8, r9, r10)
            r5 = r4
            r6 = 0
            r5.G0(r0)
            kotlin.reflect.jvm.internal.impl.types.b0 r7 = r3.getType()
            r5.J0(r7)
            java.lang.String r5 = "DescriptorFactory.create…escriptor.type)\n        }"
            kotlin.jvm.internal.k.b(r4, r5)
            if (r2 == 0) goto L_0x0107
            java.util.List r1 = r2.g()
            java.lang.String r5 = "setterMethod.valueParameters"
            kotlin.jvm.internal.k.b(r1, r5)
            java.lang.Object r1 = kotlin.collections.y.U(r1)
            kotlin.reflect.jvm.internal.impl.descriptors.w0 r1 = (kotlin.reflect.jvm.internal.impl.descriptors.w0) r1
            if (r1 == 0) goto L_0x00f0
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r6 = r2.getAnnotations()
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r7 = r1.getAnnotations()
            r8 = 0
            r9 = 0
            r10 = 0
            kotlin.reflect.jvm.internal.impl.descriptors.a1 r11 = r2.getVisibility()
            kotlin.reflect.jvm.internal.impl.descriptors.o0 r12 = r2.n()
            r5 = r3
            kotlin.reflect.jvm.internal.impl.descriptors.impl.d0 r5 = kotlin.reflect.jvm.internal.impl.resolve.b.k(r5, r6, r7, r8, r9, r10, r11, r12)
            r6 = r5
            r7 = 0
            r6.G0(r2)
            r1 = r5
            goto L_0x0108
        L_0x00f0:
            java.lang.AssertionError r1 = new java.lang.AssertionError
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "No parameter found for "
            r5.append(r6)
            r5.append(r2)
            java.lang.String r5 = r5.toString()
            r1.<init>(r5)
            throw r1
        L_0x0107:
        L_0x0108:
            r5 = r3
            r6 = 0
            r5.M0(r4, r1)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.g.a0(kotlin.reflect.jvm.internal.impl.descriptors.i0, kotlin.jvm.functions.l):kotlin.reflect.jvm.internal.impl.load.java.descriptors.g");
    }

    private final Set<i0> r0(kotlin.reflect.jvm.internal.impl.name.f name) {
        Iterable<b0> $this$flatMap$iv = V();
        Collection destination$iv$iv = new ArrayList();
        for (b0 it : $this$flatMap$iv) {
            Iterable<i0> $this$mapTo$iv$iv = it.l().e(name, kotlin.reflect.jvm.internal.impl.incremental.components.d.WHEN_GET_SUPER_MEMBERS);
            ArrayList list$iv$iv = new ArrayList(kotlin.collections.r.r($this$mapTo$iv$iv, 10));
            for (i0 p2 : $this$mapTo$iv$iv) {
                list$iv$iv.add(p2);
                $this$flatMap$iv = $this$flatMap$iv;
            }
            v.x(destination$iv$iv, list$iv$iv);
            $this$flatMap$iv = $this$flatMap$iv;
        }
        return y.H0(destination$iv$iv);
    }

    private final Collection<b0> V() {
        if (!this.t) {
            return t().a().i().c().f(y());
        }
        u0 i2 = y().i();
        k.b(i2, "ownerDescriptor.typeConstructor");
        Collection<b0> b2 = i2.b();
        k.b(b2, "ownerDescriptor.typeConstructor.supertypes");
        return b2;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public k.a D(@NotNull kotlin.reflect.jvm.internal.impl.load.java.structure.q method, @NotNull List<? extends t0> methodTypeParameters, @NotNull b0 returnType, @NotNull List<? extends w0> valueParameters) {
        kotlin.jvm.internal.k.f(method, FirebaseAnalytics.Param.METHOD);
        kotlin.jvm.internal.k.f(methodTypeParameters, "methodTypeParameters");
        kotlin.jvm.internal.k.f(returnType, "returnType");
        kotlin.jvm.internal.k.f(valueParameters, "valueParameters");
        k.b propagated = t().a().q().a(method, y(), returnType, (b0) null, valueParameters, methodTypeParameters);
        kotlin.jvm.internal.k.b(propagated, "c.components.signaturePr…dTypeParameters\n        )");
        b0 d2 = propagated.d();
        kotlin.jvm.internal.k.b(d2, "propagated.returnType");
        b0 c2 = propagated.c();
        List<w0> f2 = propagated.f();
        kotlin.jvm.internal.k.b(f2, "propagated.valueParameters");
        List<t0> e2 = propagated.e();
        kotlin.jvm.internal.k.b(e2, "propagated.typeParameters");
        boolean g = propagated.g();
        List<String> b2 = propagated.b();
        kotlin.jvm.internal.k.b(b2, "propagated.errors");
        return new k.a(d2, c2, f2, e2, g, b2);
    }

    private final boolean s0(@NotNull n0 $this$hasSameJvmDescriptorButDoesNotOverride, u builtinWithErasedParameters) {
        String c2 = t.c($this$hasSameJvmDescriptorButDoesNotOverride, false, false, 2, (Object) null);
        u a2 = builtinWithErasedParameters.a();
        kotlin.jvm.internal.k.b(a2, "builtinWithErasedParameters.original");
        if (!kotlin.jvm.internal.k.a(c2, t.c(a2, false, false, 2, (Object) null)) || g0($this$hasSameJvmDescriptorButDoesNotOverride, builtinWithErasedParameters)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.load.java.descriptors.c y0(kotlin.reflect.jvm.internal.impl.load.java.structure.k constructor) {
        kotlin.reflect.jvm.internal.impl.load.java.structure.k kVar = constructor;
        kotlin.reflect.jvm.internal.impl.descriptors.e classDescriptor = y();
        kotlin.reflect.jvm.internal.impl.load.java.descriptors.c constructorDescriptor = kotlin.reflect.jvm.internal.impl.load.java.descriptors.c.k1(classDescriptor, kotlin.reflect.jvm.internal.impl.load.java.lazy.f.a(t(), kVar), false, t().a().r().a(kVar));
        kotlin.jvm.internal.k.b(constructorDescriptor, "JavaClassConstructorDesc…ce(constructor)\n        )");
        kotlin.reflect.jvm.internal.impl.load.java.lazy.h c2 = kotlin.reflect.jvm.internal.impl.load.java.lazy.a.e(t(), constructorDescriptor, kVar, classDescriptor.o().size());
        k.b valueParameters = G(c2, constructorDescriptor, constructor.g());
        List<t0> o2 = classDescriptor.o();
        kotlin.jvm.internal.k.b(o2, "classDescriptor.declaredTypeParameters");
        Iterable<kotlin.reflect.jvm.internal.impl.load.java.structure.w> $this$mapTo$iv$iv = constructor.getTypeParameters();
        Collection destination$iv$iv = new ArrayList(kotlin.collections.r.r($this$mapTo$iv$iv, 10));
        for (kotlin.reflect.jvm.internal.impl.load.java.structure.w p2 : $this$mapTo$iv$iv) {
            t0 a2 = c2.f().a(p2);
            if (a2 == null) {
                kotlin.jvm.internal.k.n();
            }
            destination$iv$iv.add(a2);
        }
        constructorDescriptor.i1(valueParameters.a(), constructor.getVisibility(), y.n0(o2, destination$iv$iv));
        constructorDescriptor.Q0(false);
        constructorDescriptor.R0(valueParameters.b());
        constructorDescriptor.Y0(classDescriptor.m());
        c2.a().g().a(kVar, constructorDescriptor);
        return constructorDescriptor;
    }

    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.descriptors.d X() {
        List valueParameters;
        boolean isAnnotation = this.s.l();
        if (this.s.D() && !isAnnotation) {
            return null;
        }
        kotlin.reflect.jvm.internal.impl.descriptors.e classDescriptor = y();
        kotlin.reflect.jvm.internal.impl.load.java.descriptors.c constructorDescriptor = kotlin.reflect.jvm.internal.impl.load.java.descriptors.c.k1(classDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b.b(), true, t().a().r().a(this.s));
        kotlin.jvm.internal.k.b(constructorDescriptor, "JavaClassConstructorDesc….source(jClass)\n        )");
        if (isAnnotation) {
            valueParameters = W(constructorDescriptor);
        } else {
            valueParameters = Collections.emptyList();
        }
        constructorDescriptor.R0(false);
        constructorDescriptor.h1(valueParameters, n0(classDescriptor));
        constructorDescriptor.Q0(true);
        constructorDescriptor.Y0(classDescriptor.m());
        t().a().g().a(this.s, constructorDescriptor);
        return constructorDescriptor;
    }

    private final a1 n0(kotlin.reflect.jvm.internal.impl.descriptors.e classDescriptor) {
        a1 visibility = classDescriptor.getVisibility();
        kotlin.jvm.internal.k.b(visibility, "classDescriptor.visibility");
        if (!kotlin.jvm.internal.k.a(visibility, kotlin.reflect.jvm.internal.impl.load.java.q.b)) {
            return visibility;
        }
        a1 a1Var = kotlin.reflect.jvm.internal.impl.load.java.q.c;
        kotlin.jvm.internal.k.b(a1Var, "JavaVisibilities.PROTECTED_AND_PACKAGE");
        return a1Var;
    }

    private final List<w0> W(kotlin.reflect.jvm.internal.impl.descriptors.impl.f constructor) {
        kotlin.n nVar;
        Collection<kotlin.reflect.jvm.internal.impl.load.java.structure.q> $this$partition$iv = this.s.v();
        ArrayList result = new ArrayList($this$partition$iv.size());
        kotlin.reflect.jvm.internal.impl.load.java.lazy.types.a attr = kotlin.reflect.jvm.internal.impl.load.java.lazy.types.d.f(kotlin.reflect.jvm.internal.impl.load.java.components.l.COMMON, true, (t0) null, 2, (Object) null);
        ArrayList first$iv = new ArrayList();
        ArrayList second$iv = new ArrayList();
        for (T next : $this$partition$iv) {
            if (kotlin.jvm.internal.k.a(((kotlin.reflect.jvm.internal.impl.load.java.structure.q) next).getName(), s.c)) {
                first$iv.add(next);
            } else {
                second$iv.add(next);
            }
        }
        kotlin.n nVar2 = new kotlin.n(first$iv, second$iv);
        List methodsNamedValue = (List) nVar2.component1();
        List<kotlin.reflect.jvm.internal.impl.load.java.structure.q> otherMethods = (List) nVar2.component2();
        int index = 0;
        if (methodsNamedValue.size() <= 1) {
            kotlin.reflect.jvm.internal.impl.load.java.structure.q methodNamedValue = (kotlin.reflect.jvm.internal.impl.load.java.structure.q) y.U(methodsNamedValue);
            if (methodNamedValue != null) {
                kotlin.reflect.jvm.internal.impl.load.java.structure.v parameterNamedValueJavaType = methodNamedValue.getReturnType();
                if (parameterNamedValueJavaType instanceof kotlin.reflect.jvm.internal.impl.load.java.structure.f) {
                    nVar = new kotlin.n(t().g().i((kotlin.reflect.jvm.internal.impl.load.java.structure.f) parameterNamedValueJavaType, attr, true), t().g().l(((kotlin.reflect.jvm.internal.impl.load.java.structure.f) parameterNamedValueJavaType).k(), attr));
                } else {
                    nVar = new kotlin.n(t().g().l(parameterNamedValueJavaType, attr), null);
                }
                kotlin.reflect.jvm.internal.impl.load.java.structure.v vVar = parameterNamedValueJavaType;
                O(result, constructor, 0, methodNamedValue, (b0) nVar.component1(), (b0) nVar.component2());
            }
            int startIndex = methodNamedValue != null ? 1 : 0;
            for (kotlin.reflect.jvm.internal.impl.load.java.structure.q method : otherMethods) {
                O(result, constructor, index + startIndex, method, t().g().l(method.getReturnType(), attr), (b0) null);
                index++;
            }
            return result;
        }
        throw new AssertionError("There can't be more than one method named 'value' in annotation class: " + this.s);
    }

    private final void O(@NotNull List<w0> $this$addAnnotationValueParameter, kotlin.reflect.jvm.internal.impl.descriptors.l constructor, int index, kotlin.reflect.jvm.internal.impl.load.java.structure.q method, b0 returnType, b0 varargElementType) {
        kotlin.reflect.jvm.internal.impl.descriptors.annotations.g b2 = kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b.b();
        kotlin.reflect.jvm.internal.impl.name.f name = method.getName();
        b0 n2 = c1.n(returnType);
        kotlin.jvm.internal.k.b(n2, "TypeUtils.makeNotNullable(returnType)");
        List<w0> list = $this$addAnnotationValueParameter;
        $this$addAnnotationValueParameter.add(new kotlin.reflect.jvm.internal.impl.descriptors.impl.k0(constructor, (w0) null, index, b2, name, n2, method.G(), false, false, varargElementType != null ? c1.n(varargElementType) : null, t().a().r().a(method)));
    }

    /* compiled from: LazyJavaClassMemberScope.kt */
    public static final class i extends l implements kotlin.jvm.functions.a<Set<? extends kotlin.reflect.jvm.internal.impl.name.f>> {
        final /* synthetic */ g this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        i(g gVar) {
            super(0);
            this.this$0 = gVar;
        }

        @NotNull
        public final Set<kotlin.reflect.jvm.internal.impl.name.f> invoke() {
            return y.H0(this.this$0.s.u());
        }
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.g$g  reason: collision with other inner class name */
    /* compiled from: LazyJavaClassMemberScope.kt */
    public static final class C0362g extends l implements kotlin.jvm.functions.a<Map<kotlin.reflect.jvm.internal.impl.name.f, ? extends n>> {
        final /* synthetic */ g this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0362g(g gVar) {
            super(0);
            this.this$0 = gVar;
        }

        @NotNull
        public final Map<kotlin.reflect.jvm.internal.impl.name.f, n> invoke() {
            Iterable $this$filterTo$iv$iv = this.this$0.s.s();
            ArrayList arrayList = new ArrayList();
            for (T next : $this$filterTo$iv$iv) {
                if (((n) next).C()) {
                    arrayList.add(next);
                }
            }
            Iterable $this$associateByTo$iv$iv = arrayList;
            Map destination$iv$iv = new LinkedHashMap(kotlin.ranges.n.b(kotlin.collections.k0.b(kotlin.collections.r.r($this$associateByTo$iv$iv, 10)), 16));
            for (Object element$iv$iv : $this$associateByTo$iv$iv) {
                destination$iv$iv.put(((n) element$iv$iv).getName(), element$iv$iv);
            }
            return destination$iv$iv;
        }
    }

    /* compiled from: LazyJavaClassMemberScope.kt */
    public static final class j extends l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.name.f, kotlin.reflect.jvm.internal.impl.descriptors.impl.g> {
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.load.java.lazy.h $c;
        final /* synthetic */ g this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        j(g gVar, kotlin.reflect.jvm.internal.impl.load.java.lazy.h hVar) {
            super(1);
            this.this$0 = gVar;
            this.$c = hVar;
        }

        @Nullable
        public final kotlin.reflect.jvm.internal.impl.descriptors.impl.g invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.f name) {
            kotlin.reflect.jvm.internal.impl.name.f fVar = name;
            kotlin.jvm.internal.k.f(fVar, "name");
            if (!((Set) this.this$0.o.invoke()).contains(fVar)) {
                n field = (n) ((Map) this.this$0.p.invoke()).get(fVar);
                if (field == null) {
                    return null;
                }
                kotlin.reflect.jvm.internal.impl.storage.f enumMemberNames = this.$c.e().c(new a(this));
                return kotlin.reflect.jvm.internal.impl.descriptors.impl.n.A0(this.$c.e(), this.this$0.y(), name, enumMemberNames, kotlin.reflect.jvm.internal.impl.load.java.lazy.f.a(this.$c, field), this.$c.a().r().a(field));
            }
            m d = this.$c.a().d();
            kotlin.reflect.jvm.internal.impl.name.a i = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.i(this.this$0.y());
            if (i == null) {
                kotlin.jvm.internal.k.n();
            }
            kotlin.reflect.jvm.internal.impl.name.a d2 = i.d(fVar);
            kotlin.jvm.internal.k.b(d2, "ownerDescriptor.classId!…createNestedClassId(name)");
            kotlin.reflect.jvm.internal.impl.load.java.structure.g it = d.a(new m.a(d2, (byte[]) null, this.this$0.s, 2, (DefaultConstructorMarker) null));
            if (it == null) {
                return null;
            }
            f p1 = new f(this.$c, this.this$0.y(), it, (kotlin.reflect.jvm.internal.impl.descriptors.e) null, 8, (DefaultConstructorMarker) null);
            this.$c.a().e().a(p1);
            return p1;
        }

        /* compiled from: LazyJavaClassMemberScope.kt */
        public static final class a extends l implements kotlin.jvm.functions.a<Set<? extends kotlin.reflect.jvm.internal.impl.name.f>> {
            final /* synthetic */ j this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(j jVar) {
                super(0);
                this.this$0 = jVar;
            }

            @NotNull
            public final Set<kotlin.reflect.jvm.internal.impl.name.f> invoke() {
                return p0.i(this.this$0.this$0.a(), this.this$0.this$0.f());
            }
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public l0 v() {
        return kotlin.reflect.jvm.internal.impl.resolve.c.l(y());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0019, code lost:
        r0 = (r0 = r0.q).invoke(r2);
     */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public kotlin.reflect.jvm.internal.impl.descriptors.h c(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.name.f r2, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b r3) {
        /*
            r1 = this;
            java.lang.String r0 = "name"
            kotlin.jvm.internal.k.f(r2, r0)
            java.lang.String r0 = "location"
            kotlin.jvm.internal.k.f(r3, r0)
            r1.x0(r2, r3)
            kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.k r0 = r1.x()
            kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.g r0 = (kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.g) r0
            if (r0 == 0) goto L_0x0022
            kotlin.reflect.jvm.internal.impl.storage.d<kotlin.reflect.jvm.internal.impl.name.f, kotlin.reflect.jvm.internal.impl.descriptors.impl.g> r0 = r0.q
            if (r0 == 0) goto L_0x0022
            java.lang.Object r0 = r0.invoke(r2)
            kotlin.reflect.jvm.internal.impl.descriptors.impl.g r0 = (kotlin.reflect.jvm.internal.impl.descriptors.impl.g) r0
            if (r0 == 0) goto L_0x0022
            goto L_0x002a
        L_0x0022:
            kotlin.reflect.jvm.internal.impl.storage.d<kotlin.reflect.jvm.internal.impl.name.f, kotlin.reflect.jvm.internal.impl.descriptors.impl.g> r0 = r1.q
            java.lang.Object r0 = r0.invoke(r2)
            kotlin.reflect.jvm.internal.impl.descriptors.h r0 = (kotlin.reflect.jvm.internal.impl.descriptors.h) r0
        L_0x002a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.g.c(kotlin.reflect.jvm.internal.impl.name.f, kotlin.reflect.jvm.internal.impl.incremental.components.b):kotlin.reflect.jvm.internal.impl.descriptors.h");
    }

    @NotNull
    public Collection<n0> b(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
        x0(name, location);
        return super.b(name, location);
    }

    @NotNull
    public Collection<i0> e(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
        x0(name, location);
        return super.e(name, location);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Set<kotlin.reflect.jvm.internal.impl.name.f> j(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d kindFilter, @Nullable kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean> nameFilter) {
        kotlin.jvm.internal.k.f(kindFilter, "kindFilter");
        return p0.i((Set) this.o.invoke(), ((Map) this.p.invoke()).keySet());
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Set<kotlin.reflect.jvm.internal.impl.name.f> q(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d kindFilter, @Nullable kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean> nameFilter) {
        kotlin.jvm.internal.k.f(kindFilter, "kindFilter");
        if (this.s.l()) {
            return a();
        }
        LinkedHashSet result = new LinkedHashSet(((b) u().invoke()).b());
        u0 i2 = y().i();
        kotlin.jvm.internal.k.b(i2, "ownerDescriptor.typeConstructor");
        Iterable<b0> $this$flatMapTo$iv = i2.b();
        kotlin.jvm.internal.k.b($this$flatMapTo$iv, "ownerDescriptor.typeConstructor.supertypes");
        for (b0 supertype : $this$flatMapTo$iv) {
            v.x(result, supertype.l().f());
        }
        return result;
    }

    public void x0(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
        kotlin.reflect.jvm.internal.impl.incremental.a.a(t().a().j(), location, y(), name);
    }

    @NotNull
    public String toString() {
        return "Lazy Java member scope for " + this.s.e();
    }
}
