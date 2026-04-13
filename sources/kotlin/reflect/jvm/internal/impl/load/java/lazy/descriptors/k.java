package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.k0;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.u;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.c0;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.descriptors.s;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.load.java.structure.n;
import kotlin.reflect.jvm.internal.impl.load.java.structure.q;
import kotlin.reflect.jvm.internal.impl.load.java.structure.w;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.c;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.t;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LazyJavaScope.kt */
public abstract class k extends kotlin.reflect.jvm.internal.impl.resolve.scopes.i {
    static final /* synthetic */ kotlin.reflect.k[] b;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.storage.f<Collection<m>> c;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.storage.f<b> d;
    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.storage.c<kotlin.reflect.jvm.internal.impl.name.f, Collection<n0>> e;
    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.storage.d<kotlin.reflect.jvm.internal.impl.name.f, i0> f;
    private final kotlin.reflect.jvm.internal.impl.storage.c<kotlin.reflect.jvm.internal.impl.name.f, Collection<n0>> g;
    private final kotlin.reflect.jvm.internal.impl.storage.f h;
    private final kotlin.reflect.jvm.internal.impl.storage.f i;
    private final kotlin.reflect.jvm.internal.impl.storage.f j;
    private final kotlin.reflect.jvm.internal.impl.storage.c<kotlin.reflect.jvm.internal.impl.name.f, List<i0>> k;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.load.java.lazy.h l;
    @Nullable
    private final k m;

    static {
        Class<k> cls = k.class;
        b = new kotlin.reflect.k[]{a0.h(new u(a0.b(cls), "functionNamesLazy", "getFunctionNamesLazy()Ljava/util/Set;")), a0.h(new u(a0.b(cls), "propertyNamesLazy", "getPropertyNamesLazy()Ljava/util/Set;")), a0.h(new u(a0.b(cls), "classNamesLazy", "getClassNamesLazy()Ljava/util/Set;"))};
    }

    private final Set<kotlin.reflect.jvm.internal.impl.name.f> w() {
        return (Set) kotlin.reflect.jvm.internal.impl.storage.i.a(this.h, this, b[0]);
    }

    private final Set<kotlin.reflect.jvm.internal.impl.name.f> z() {
        return (Set) kotlin.reflect.jvm.internal.impl.storage.i.a(this.i, this, b[1]);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public abstract a D(@NotNull q qVar, @NotNull List<? extends t0> list, @NotNull b0 b0Var, @NotNull List<? extends w0> list2);

    /* access modifiers changed from: protected */
    @NotNull
    public abstract Set<kotlin.reflect.jvm.internal.impl.name.f> j(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d dVar, @Nullable kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean> lVar);

    /* access modifiers changed from: protected */
    @NotNull
    public abstract Set<kotlin.reflect.jvm.internal.impl.name.f> l(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d dVar, @Nullable kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean> lVar);

    /* access modifiers changed from: protected */
    @NotNull
    public abstract b m();

    /* access modifiers changed from: protected */
    public abstract void o(@NotNull Collection<n0> collection, @NotNull kotlin.reflect.jvm.internal.impl.name.f fVar);

    /* access modifiers changed from: protected */
    public abstract void p(@NotNull kotlin.reflect.jvm.internal.impl.name.f fVar, @NotNull Collection<i0> collection);

    /* access modifiers changed from: protected */
    @NotNull
    public abstract Set<kotlin.reflect.jvm.internal.impl.name.f> q(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d dVar, @Nullable kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean> lVar);

    /* access modifiers changed from: protected */
    @Nullable
    public abstract l0 v();

    /* access modifiers changed from: protected */
    @NotNull
    public abstract m y();

    public k(@NotNull kotlin.reflect.jvm.internal.impl.load.java.lazy.h c2, @Nullable k mainScope) {
        kotlin.jvm.internal.k.f(c2, "c");
        this.l = c2;
        this.m = mainScope;
        this.c = c2.e().b(new c(this), kotlin.collections.q.g());
        this.d = c2.e().c(new g(this));
        this.e = c2.e().h(new f(this));
        this.f = c2.e().g(new e(this));
        this.g = c2.e().h(new i(this));
        this.h = c2.e().c(new h(this));
        this.i = c2.e().c(new C0364k(this));
        this.j = c2.e().c(new d(this));
        this.k = c2.e().h(new j(this));
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final kotlin.reflect.jvm.internal.impl.load.java.lazy.h t() {
        return this.l;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ k(kotlin.reflect.jvm.internal.impl.load.java.lazy.h hVar, k kVar, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(hVar, (i2 & 2) != 0 ? null : kVar);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final k x() {
        return this.m;
    }

    /* compiled from: LazyJavaScope.kt */
    public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends m>> {
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(k kVar) {
            super(0);
            this.this$0 = kVar;
        }

        @NotNull
        public final List<m> invoke() {
            return this.this$0.k(kotlin.reflect.jvm.internal.impl.resolve.scopes.d.l, kotlin.reflect.jvm.internal.impl.resolve.scopes.h.a.a());
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final kotlin.reflect.jvm.internal.impl.storage.f<Collection<m>> s() {
        return this.c;
    }

    /* compiled from: LazyJavaScope.kt */
    public static final class g extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<b> {
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(k kVar) {
            super(0);
            this.this$0 = kVar;
        }

        @NotNull
        public final b invoke() {
            return this.this$0.m();
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final kotlin.reflect.jvm.internal.impl.storage.f<b> u() {
        return this.d;
    }

    /* compiled from: LazyJavaScope.kt */
    public static final class f extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.name.f, Collection<? extends n0>> {
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(k kVar) {
            super(1);
            this.this$0 = kVar;
        }

        @NotNull
        public final Collection<n0> invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.f name) {
            kotlin.jvm.internal.k.f(name, "name");
            if (this.this$0.x() != null) {
                return (Collection) this.this$0.x().e.invoke(name);
            }
            List result = new ArrayList();
            for (q method : ((b) this.this$0.u().invoke()).c(name)) {
                kotlin.reflect.jvm.internal.impl.load.java.descriptors.f descriptor = this.this$0.E(method);
                if (this.this$0.C(descriptor)) {
                    this.this$0.t().a().g().c(method, descriptor);
                    result.add(descriptor);
                }
            }
            return result;
        }
    }

    /* compiled from: LazyJavaScope.kt */
    public static final class e extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.name.f, i0> {
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(k kVar) {
            super(1);
            this.this$0 = kVar;
        }

        @Nullable
        public final i0 invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.f name) {
            kotlin.jvm.internal.k.f(name, "name");
            if (this.this$0.x() != null) {
                return (i0) this.this$0.x().f.invoke(name);
            }
            n field = ((b) this.this$0.u().invoke()).d(name);
            if (field == null || field.C()) {
                return null;
            }
            return this.this$0.F(field);
        }
    }

    /* compiled from: LazyJavaScope.kt */
    public static final class i extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.name.f, List<? extends n0>> {
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        i(k kVar) {
            super(1);
            this.this$0 = kVar;
        }

        @NotNull
        public final List<n0> invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.f name) {
            kotlin.jvm.internal.k.f(name, "name");
            LinkedHashSet result = new LinkedHashSet((Collection) this.this$0.e.invoke(name));
            kotlin.reflect.jvm.internal.impl.resolve.j.a(result);
            this.this$0.o(result, name);
            return y.C0(this.this$0.t().a().p().b(this.this$0.t(), result));
        }
    }

    /* access modifiers changed from: protected */
    public boolean C(@NotNull kotlin.reflect.jvm.internal.impl.load.java.descriptors.f $this$isVisibleAsFunction) {
        kotlin.jvm.internal.k.f($this$isVisibleAsFunction, "$this$isVisibleAsFunction");
        return true;
    }

    /* compiled from: LazyJavaScope.kt */
    public static final class a {
        @NotNull
        private final b0 a;
        @Nullable
        private final b0 b;
        @NotNull
        private final List<w0> c;
        @NotNull
        private final List<t0> d;
        private final boolean e;
        @NotNull
        private final List<String> f;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return kotlin.jvm.internal.k.a(this.a, aVar.a) && kotlin.jvm.internal.k.a(this.b, aVar.b) && kotlin.jvm.internal.k.a(this.c, aVar.c) && kotlin.jvm.internal.k.a(this.d, aVar.d) && this.e == aVar.e && kotlin.jvm.internal.k.a(this.f, aVar.f);
        }

        public int hashCode() {
            b0 b0Var = this.a;
            int i = 0;
            int hashCode = (b0Var != null ? b0Var.hashCode() : 0) * 31;
            b0 b0Var2 = this.b;
            int hashCode2 = (hashCode + (b0Var2 != null ? b0Var2.hashCode() : 0)) * 31;
            List<w0> list = this.c;
            int hashCode3 = (hashCode2 + (list != null ? list.hashCode() : 0)) * 31;
            List<t0> list2 = this.d;
            int hashCode4 = (hashCode3 + (list2 != null ? list2.hashCode() : 0)) * 31;
            boolean z = this.e;
            if (z) {
                z = true;
            }
            int i2 = (hashCode4 + (z ? 1 : 0)) * 31;
            List<String> list3 = this.f;
            if (list3 != null) {
                i = list3.hashCode();
            }
            return i2 + i;
        }

        @NotNull
        public String toString() {
            return "MethodSignatureData(returnType=" + this.a + ", receiverType=" + this.b + ", valueParameters=" + this.c + ", typeParameters=" + this.d + ", hasStableParameterNames=" + this.e + ", errors=" + this.f + ")";
        }

        public a(@NotNull b0 returnType, @Nullable b0 receiverType, @NotNull List<? extends w0> valueParameters, @NotNull List<? extends t0> typeParameters, boolean hasStableParameterNames, @NotNull List<String> errors) {
            kotlin.jvm.internal.k.f(returnType, "returnType");
            kotlin.jvm.internal.k.f(valueParameters, "valueParameters");
            kotlin.jvm.internal.k.f(typeParameters, "typeParameters");
            kotlin.jvm.internal.k.f(errors, "errors");
            this.a = returnType;
            this.b = receiverType;
            this.c = valueParameters;
            this.d = typeParameters;
            this.e = hasStableParameterNames;
            this.f = errors;
        }

        @NotNull
        public final b0 d() {
            return this.a;
        }

        @Nullable
        public final b0 c() {
            return this.b;
        }

        @NotNull
        public final List<w0> f() {
            return this.c;
        }

        @NotNull
        public final List<t0> e() {
            return this.d;
        }

        public final boolean b() {
            return this.e;
        }

        @NotNull
        public final List<String> a() {
            return this.f;
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final kotlin.reflect.jvm.internal.impl.load.java.descriptors.f E(@NotNull q method) {
        l0 l0Var;
        Map<K, V> map;
        q qVar = method;
        kotlin.jvm.internal.k.f(qVar, FirebaseAnalytics.Param.METHOD);
        kotlin.reflect.jvm.internal.impl.load.java.descriptors.f h1 = kotlin.reflect.jvm.internal.impl.load.java.descriptors.f.h1(y(), kotlin.reflect.jvm.internal.impl.load.java.lazy.f.a(this.l, qVar), method.getName(), this.l.a().r().a(qVar));
        kotlin.jvm.internal.k.b(h1, "JavaMethodDescriptor.cre….source(method)\n        )");
        kotlin.reflect.jvm.internal.impl.load.java.descriptors.f functionDescriptorImpl = h1;
        kotlin.reflect.jvm.internal.impl.load.java.lazy.h c2 = kotlin.reflect.jvm.internal.impl.load.java.lazy.a.f(this.l, functionDescriptorImpl, method, 0, 4, (Object) null);
        Iterable<w> $this$mapTo$iv$iv = method.getTypeParameters();
        List methodTypeParameters = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (w p : $this$mapTo$iv$iv) {
            t0 a2 = c2.f().a(p);
            if (a2 == null) {
                kotlin.jvm.internal.k.n();
            }
            methodTypeParameters.add(a2);
        }
        b valueParameters = G(c2, functionDescriptorImpl, method.g());
        a effectiveSignature = D(qVar, methodTypeParameters, n(qVar, c2), valueParameters.a());
        b0 it = effectiveSignature.c();
        if (it != null) {
            l0Var = kotlin.reflect.jvm.internal.impl.resolve.b.f(functionDescriptorImpl, it, kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b.b());
        } else {
            l0Var = null;
        }
        l0 l0Var2 = l0Var;
        l0 v = v();
        List<t0> e2 = effectiveSignature.e();
        List<w0> f2 = effectiveSignature.f();
        b0 d2 = effectiveSignature.d();
        kotlin.reflect.jvm.internal.impl.descriptors.w a3 = kotlin.reflect.jvm.internal.impl.descriptors.w.Companion.a(method.isAbstract(), !method.isFinal());
        a1 visibility = method.getVisibility();
        if (effectiveSignature.c() != null) {
            map = k0.c(t.a(kotlin.reflect.jvm.internal.impl.load.java.descriptors.f.O4, y.S(valueParameters.a())));
        } else {
            map = kotlin.collections.l0.f();
        }
        kotlin.reflect.jvm.internal.impl.load.java.descriptors.f functionDescriptorImpl2 = functionDescriptorImpl;
        functionDescriptorImpl.g1(l0Var2, v, e2, f2, d2, a3, visibility, map);
        functionDescriptorImpl2.l1(effectiveSignature.b(), valueParameters.b());
        if (!effectiveSignature.a().isEmpty()) {
            c2.a().q().b(functionDescriptorImpl2, effectiveSignature.a());
        }
        return functionDescriptorImpl2;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final b0 n(@NotNull q method, @NotNull kotlin.reflect.jvm.internal.impl.load.java.lazy.h c2) {
        kotlin.jvm.internal.k.f(method, FirebaseAnalytics.Param.METHOD);
        kotlin.jvm.internal.k.f(c2, "c");
        return c2.g().l(method.getReturnType(), kotlin.reflect.jvm.internal.impl.load.java.lazy.types.d.f(kotlin.reflect.jvm.internal.impl.load.java.components.l.COMMON, method.I().l(), (t0) null, 2, (Object) null));
    }

    /* compiled from: LazyJavaScope.kt */
    public static final class b {
        @NotNull
        private final List<w0> a;
        private final boolean b;

        public b(@NotNull List<? extends w0> descriptors, boolean hasSynthesizedNames) {
            kotlin.jvm.internal.k.f(descriptors, "descriptors");
            this.a = descriptors;
            this.b = hasSynthesizedNames;
        }

        @NotNull
        public final List<w0> a() {
            return this.a;
        }

        public final boolean b() {
            return this.b;
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00d4  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x011e  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0127  */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.k.b G(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.load.java.lazy.h r36, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.u r37, @org.jetbrains.annotations.NotNull java.util.List<? extends kotlin.reflect.jvm.internal.impl.load.java.structure.y> r38) {
        /*
            r35 = this;
            r0 = r36
            java.lang.String r1 = "c"
            kotlin.jvm.internal.k.f(r0, r1)
            java.lang.String r1 = "function"
            r14 = r37
            kotlin.jvm.internal.k.f(r14, r1)
            java.lang.String r1 = "jValueParameters"
            r15 = r38
            kotlin.jvm.internal.k.f(r15, r1)
            r1 = 0
            java.util.LinkedHashSet r2 = new java.util.LinkedHashSet
            r2.<init>()
            r13 = r2
            java.lang.Iterable r12 = kotlin.collections.y.J0(r38)
            r16 = 0
            java.util.ArrayList r2 = new java.util.ArrayList
            r3 = 10
            int r3 = kotlin.collections.r.r(r12, r3)
            r2.<init>(r3)
            r17 = r12
            r11 = r2
            r18 = 0
            java.util.Iterator r19 = r17.iterator()
        L_0x0038:
            boolean r2 = r19.hasNext()
            if (r2 == 0) goto L_0x01bb
            java.lang.Object r20 = r19.next()
            r21 = r20
            kotlin.collections.d0 r21 = (kotlin.collections.d0) r21
            r22 = 0
            int r10 = r21.a()
            java.lang.Object r2 = r21.b()
            kotlin.reflect.jvm.internal.impl.load.java.structure.y r2 = (kotlin.reflect.jvm.internal.impl.load.java.structure.y) r2
            r9 = r2
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r8 = kotlin.reflect.jvm.internal.impl.load.java.lazy.f.a(r0, r9)
            kotlin.reflect.jvm.internal.impl.load.java.components.l r2 = kotlin.reflect.jvm.internal.impl.load.java.components.l.COMMON
            r3 = 3
            r4 = 0
            r5 = 0
            kotlin.reflect.jvm.internal.impl.load.java.lazy.types.a r6 = kotlin.reflect.jvm.internal.impl.load.java.lazy.types.d.f(r2, r4, r5, r3, r5)
            kotlin.reflect.jvm.internal.impl.name.b r2 = kotlin.reflect.jvm.internal.impl.load.java.s.m
            java.lang.String r3 = "JvmAnnotationNames.PARAMETER_NAME_FQ_NAME"
            kotlin.jvm.internal.k.b(r2, r3)
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.c r2 = r8.c(r2)
            if (r2 == 0) goto L_0x008b
            kotlin.reflect.jvm.internal.impl.resolve.constants.g r2 = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.c(r2)
            if (r2 == 0) goto L_0x008b
            r3 = 0
            boolean r7 = r2 instanceof kotlin.reflect.jvm.internal.impl.resolve.constants.w
            if (r7 != 0) goto L_0x007f
            r7 = r5
            goto L_0x0080
        L_0x007f:
            r7 = r2
        L_0x0080:
            kotlin.reflect.jvm.internal.impl.resolve.constants.w r7 = (kotlin.reflect.jvm.internal.impl.resolve.constants.w) r7
            if (r7 == 0) goto L_0x008b
            java.lang.Object r2 = r7.b()
            java.lang.String r2 = (java.lang.String) r2
            goto L_0x008c
        L_0x008b:
            r2 = r5
        L_0x008c:
            r3 = r2
            boolean r2 = r9.z()
            r7 = 1
            if (r2 == 0) goto L_0x00d4
            kotlin.reflect.jvm.internal.impl.load.java.structure.v r2 = r9.getType()
            boolean r4 = r2 instanceof kotlin.reflect.jvm.internal.impl.load.java.structure.f
            if (r4 != 0) goto L_0x009e
            goto L_0x009f
        L_0x009e:
            r5 = r2
        L_0x009f:
            kotlin.reflect.jvm.internal.impl.load.java.structure.f r5 = (kotlin.reflect.jvm.internal.impl.load.java.structure.f) r5
            if (r5 == 0) goto L_0x00bd
            r2 = r5
            kotlin.reflect.jvm.internal.impl.load.java.lazy.types.c r4 = r36.g()
            kotlin.reflect.jvm.internal.impl.types.b0 r4 = r4.i(r2, r6, r7)
            kotlin.reflect.jvm.internal.impl.descriptors.y r5 = r36.d()
            kotlin.reflect.jvm.internal.impl.builtins.g r5 = r5.j()
            kotlin.reflect.jvm.internal.impl.types.b0 r5 = r5.l(r4)
            kotlin.n r2 = kotlin.t.a(r4, r5)
            goto L_0x00e4
        L_0x00bd:
            java.lang.AssertionError r2 = new java.lang.AssertionError
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Vararg parameter should be an array: "
            r4.append(r5)
            r4.append(r9)
            java.lang.String r4 = r4.toString()
            r2.<init>(r4)
            throw r2
        L_0x00d4:
            kotlin.reflect.jvm.internal.impl.load.java.lazy.types.c r2 = r36.g()
            kotlin.reflect.jvm.internal.impl.load.java.structure.v r4 = r9.getType()
            kotlin.reflect.jvm.internal.impl.types.b0 r2 = r2.l(r4, r6)
            kotlin.n r2 = kotlin.t.a(r2, r5)
        L_0x00e4:
            java.lang.Object r4 = r2.component1()
            r5 = r4
            kotlin.reflect.jvm.internal.impl.types.b0 r5 = (kotlin.reflect.jvm.internal.impl.types.b0) r5
            java.lang.Object r2 = r2.component2()
            r24 = r2
            kotlin.reflect.jvm.internal.impl.types.b0 r24 = (kotlin.reflect.jvm.internal.impl.types.b0) r24
            kotlin.reflect.jvm.internal.impl.name.f r2 = r37.getName()
            java.lang.String r2 = r2.b()
            java.lang.String r4 = "equals"
            boolean r2 = kotlin.jvm.internal.k.a(r2, r4)
            if (r2 == 0) goto L_0x0127
            int r2 = r38.size()
            if (r2 != r7) goto L_0x0127
            kotlin.reflect.jvm.internal.impl.descriptors.y r2 = r36.d()
            kotlin.reflect.jvm.internal.impl.builtins.g r2 = r2.j()
            kotlin.reflect.jvm.internal.impl.types.i0 r2 = r2.K()
            boolean r2 = kotlin.jvm.internal.k.a(r2, r5)
            if (r2 == 0) goto L_0x0127
            java.lang.String r2 = "other"
            kotlin.reflect.jvm.internal.impl.name.f r2 = kotlin.reflect.jvm.internal.impl.name.f.f(r2)
            r7 = r2
            goto L_0x0161
        L_0x0127:
            if (r3 == 0) goto L_0x0140
            int r2 = r3.length()
            if (r2 <= 0) goto L_0x0131
            r4 = r7
            goto L_0x0132
        L_0x0131:
            r4 = 0
        L_0x0132:
            if (r4 == 0) goto L_0x0140
            boolean r2 = r13.add(r3)
            if (r2 == 0) goto L_0x0140
            kotlin.reflect.jvm.internal.impl.name.f r2 = kotlin.reflect.jvm.internal.impl.name.f.f(r3)
            r7 = r2
            goto L_0x0161
        L_0x0140:
            kotlin.reflect.jvm.internal.impl.name.f r2 = r9.getName()
            if (r2 != 0) goto L_0x0147
            r1 = 1
        L_0x0147:
            if (r2 == 0) goto L_0x014b
            r7 = r2
            goto L_0x0161
        L_0x014b:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r7 = 112(0x70, float:1.57E-43)
            r4.append(r7)
            r4.append(r10)
            java.lang.String r4 = r4.toString()
            kotlin.reflect.jvm.internal.impl.name.f r4 = kotlin.reflect.jvm.internal.impl.name.f.f(r4)
            r7 = r4
        L_0x0161:
            java.lang.String r2 = "if (function.name.asStri…(\"p$index\")\n            }"
            kotlin.jvm.internal.k.b(r7, r2)
            kotlin.reflect.jvm.internal.impl.descriptors.impl.k0 r4 = new kotlin.reflect.jvm.internal.impl.descriptors.impl.k0
            r23 = 0
            r25 = 0
            r26 = 0
            r27 = 0
            kotlin.reflect.jvm.internal.impl.load.java.lazy.b r2 = r36.a()
            kotlin.reflect.jvm.internal.impl.load.java.sources.b r2 = r2.r()
            kotlin.reflect.jvm.internal.impl.load.java.sources.a r28 = r2.a(r9)
            r2 = r4
            r29 = r3
            r3 = r37
            r30 = r4
            r4 = r23
            r23 = r5
            r5 = r10
            r31 = r6
            r6 = r8
            r32 = r8
            r8 = r23
            r33 = r9
            r9 = r25
            r25 = r10
            r10 = r26
            r34 = r11
            r11 = r27
            r26 = r12
            r12 = r24
            r27 = r13
            r13 = r28
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            r3 = r30
            r2 = r34
            r2.add(r3)
            r11 = r2
            r12 = r26
            r13 = r27
            goto L_0x0038
        L_0x01bb:
            r2 = r11
            java.util.List r2 = kotlin.collections.y.C0(r2)
            kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.k$b r3 = new kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.k$b
            r3.<init>(r2, r1)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.k.G(kotlin.reflect.jvm.internal.impl.load.java.lazy.h, kotlin.reflect.jvm.internal.impl.descriptors.u, java.util.List):kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.k$b");
    }

    /* compiled from: LazyJavaScope.kt */
    public static final class h extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Set<? extends kotlin.reflect.jvm.internal.impl.name.f>> {
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        h(k kVar) {
            super(0);
            this.this$0 = kVar;
        }

        @NotNull
        public final Set<kotlin.reflect.jvm.internal.impl.name.f> invoke() {
            return this.this$0.l(kotlin.reflect.jvm.internal.impl.resolve.scopes.d.s, (kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean>) null);
        }
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.k$k  reason: collision with other inner class name */
    /* compiled from: LazyJavaScope.kt */
    public static final class C0364k extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Set<? extends kotlin.reflect.jvm.internal.impl.name.f>> {
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0364k(k kVar) {
            super(0);
            this.this$0 = kVar;
        }

        @NotNull
        public final Set<kotlin.reflect.jvm.internal.impl.name.f> invoke() {
            return this.this$0.q(kotlin.reflect.jvm.internal.impl.resolve.scopes.d.t, (kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean>) null);
        }
    }

    /* compiled from: LazyJavaScope.kt */
    public static final class d extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Set<? extends kotlin.reflect.jvm.internal.impl.name.f>> {
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(k kVar) {
            super(0);
            this.this$0 = kVar;
        }

        @NotNull
        public final Set<kotlin.reflect.jvm.internal.impl.name.f> invoke() {
            return this.this$0.j(kotlin.reflect.jvm.internal.impl.resolve.scopes.d.q, (kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean>) null);
        }
    }

    @NotNull
    public Set<kotlin.reflect.jvm.internal.impl.name.f> a() {
        return w();
    }

    @NotNull
    public Set<kotlin.reflect.jvm.internal.impl.name.f> f() {
        return z();
    }

    @NotNull
    public Collection<n0> b(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
        if (!a().contains(name)) {
            return kotlin.collections.q.g();
        }
        return this.g.invoke(name);
    }

    /* compiled from: LazyJavaScope.kt */
    public static final class j extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.name.f, List<? extends i0>> {
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        j(k kVar) {
            super(1);
            this.this$0 = kVar;
        }

        @NotNull
        public final List<i0> invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.f name) {
            kotlin.jvm.internal.k.f(name, "name");
            ArrayList properties = new ArrayList();
            kotlin.reflect.jvm.internal.impl.utils.a.a(properties, this.this$0.f.invoke(name));
            this.this$0.p(name, properties);
            if (kotlin.reflect.jvm.internal.impl.resolve.c.t(this.this$0.y())) {
                return y.C0(properties);
            }
            return y.C0(this.this$0.t().a().p().b(this.this$0.t(), properties));
        }
    }

    /* access modifiers changed from: private */
    public final i0 F(n field) {
        kotlin.reflect.jvm.internal.impl.descriptors.impl.b0 propertyDescriptor = r(field);
        propertyDescriptor.N0((c0) null, (kotlin.reflect.jvm.internal.impl.descriptors.k0) null, (s) null, (s) null);
        propertyDescriptor.S0(A(field), kotlin.collections.q.g(), v(), (l0) null);
        if (kotlin.reflect.jvm.internal.impl.resolve.c.K(propertyDescriptor, propertyDescriptor.getType())) {
            propertyDescriptor.l0(this.l.e().e(new l(this, field, propertyDescriptor)));
        }
        this.l.a().g().b(field, propertyDescriptor);
        return propertyDescriptor;
    }

    /* compiled from: LazyJavaScope.kt */
    public static final class l extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<kotlin.reflect.jvm.internal.impl.resolve.constants.g<?>> {
        final /* synthetic */ n $field;
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.descriptors.impl.b0 $propertyDescriptor;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        l(k kVar, n nVar, kotlin.reflect.jvm.internal.impl.descriptors.impl.b0 b0Var) {
            super(0);
            this.this$0 = kVar;
            this.$field = nVar;
            this.$propertyDescriptor = b0Var;
        }

        @Nullable
        public final kotlin.reflect.jvm.internal.impl.resolve.constants.g<?> invoke() {
            return this.this$0.t().a().f().a(this.$field, this.$propertyDescriptor);
        }
    }

    private final kotlin.reflect.jvm.internal.impl.descriptors.impl.b0 r(n field) {
        kotlin.reflect.jvm.internal.impl.load.java.descriptors.g U0 = kotlin.reflect.jvm.internal.impl.load.java.descriptors.g.U0(y(), kotlin.reflect.jvm.internal.impl.load.java.lazy.f.a(this.l, field), kotlin.reflect.jvm.internal.impl.descriptors.w.FINAL, field.getVisibility(), !field.isFinal(), field.getName(), this.l.a().r().a(field), B(field));
        kotlin.jvm.internal.k.b(U0, "JavaPropertyDescriptor.c…d.isFinalStatic\n        )");
        return U0;
    }

    private final boolean B(@NotNull n $this$isFinalStatic) {
        return $this$isFinalStatic.isFinal() && $this$isFinalStatic.i();
    }

    private final b0 A(n field) {
        boolean isNotNullable = false;
        b0 propertyType = this.l.g().l(field.getType(), kotlin.reflect.jvm.internal.impl.load.java.lazy.types.d.f(kotlin.reflect.jvm.internal.impl.load.java.components.l.COMMON, false, (t0) null, 3, (Object) null));
        if ((kotlin.reflect.jvm.internal.impl.builtins.g.C0(propertyType) || kotlin.reflect.jvm.internal.impl.builtins.g.G0(propertyType)) && B(field) && field.H()) {
            isNotNullable = true;
        }
        if (!isNotNullable) {
            return propertyType;
        }
        b0 n = c1.n(propertyType);
        kotlin.jvm.internal.k.b(n, "TypeUtils.makeNotNullable(propertyType)");
        return n;
    }

    @NotNull
    public Collection<i0> e(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
        if (!f().contains(name)) {
            return kotlin.collections.q.g();
        }
        return this.k.invoke(name);
    }

    @NotNull
    public Collection<m> d(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d kindFilter, @NotNull kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean> nameFilter) {
        kotlin.jvm.internal.k.f(kindFilter, "kindFilter");
        kotlin.jvm.internal.k.f(nameFilter, "nameFilter");
        return (Collection) this.c.invoke();
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final List<m> k(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d kindFilter, @NotNull kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean> nameFilter) {
        kotlin.jvm.internal.k.f(kindFilter, "kindFilter");
        kotlin.jvm.internal.k.f(nameFilter, "nameFilter");
        kotlin.reflect.jvm.internal.impl.incremental.components.d location = kotlin.reflect.jvm.internal.impl.incremental.components.d.WHEN_GET_ALL_DESCRIPTORS;
        LinkedHashSet result = new LinkedHashSet();
        if (kindFilter.a(kotlin.reflect.jvm.internal.impl.resolve.scopes.d.x.c())) {
            for (kotlin.reflect.jvm.internal.impl.name.f name : j(kindFilter, nameFilter)) {
                if (nameFilter.invoke(name).booleanValue()) {
                    kotlin.reflect.jvm.internal.impl.utils.a.a(result, c(name, location));
                }
            }
        }
        if (kindFilter.a(kotlin.reflect.jvm.internal.impl.resolve.scopes.d.x.d()) && !kindFilter.l().contains(c.a.b)) {
            for (kotlin.reflect.jvm.internal.impl.name.f name2 : l(kindFilter, nameFilter)) {
                if (nameFilter.invoke(name2).booleanValue()) {
                    result.addAll(b(name2, location));
                }
            }
        }
        if (kindFilter.a(kotlin.reflect.jvm.internal.impl.resolve.scopes.d.x.i()) && !kindFilter.l().contains(c.a.b)) {
            for (kotlin.reflect.jvm.internal.impl.name.f name3 : q(kindFilter, nameFilter)) {
                if (nameFilter.invoke(name3).booleanValue()) {
                    result.addAll(e(name3, location));
                }
            }
        }
        return y.C0(result);
    }

    @NotNull
    public String toString() {
        return "Lazy scope for " + y();
    }
}
