package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.g0;
import kotlin.collections.n0;
import kotlin.collections.o0;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.ranges.n;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.g;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.j0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.m;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.h1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: NotFoundClasses.kt */
public final class a0 {
    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.storage.c<kotlin.reflect.jvm.internal.impl.name.b, b0> a;
    private final kotlin.reflect.jvm.internal.impl.storage.c<a, e> b;
    /* access modifiers changed from: private */
    public final j c;
    /* access modifiers changed from: private */
    public final y d;

    public a0(@NotNull j storageManager, @NotNull y module) {
        k.f(storageManager, "storageManager");
        k.f(module, "module");
        this.c = storageManager;
        this.d = module;
        this.a = storageManager.h(new d(this));
        this.b = storageManager.h(new c(this));
    }

    /* compiled from: NotFoundClasses.kt */
    public static final class a {
        @NotNull
        private final kotlin.reflect.jvm.internal.impl.name.a a;
        @NotNull
        private final List<Integer> b;

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.name.a a() {
            return this.a;
        }

        @NotNull
        public final List<Integer> b() {
            return this.b;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return k.a(this.a, aVar.a) && k.a(this.b, aVar.b);
        }

        public int hashCode() {
            kotlin.reflect.jvm.internal.impl.name.a aVar = this.a;
            int i = 0;
            int hashCode = (aVar != null ? aVar.hashCode() : 0) * 31;
            List<Integer> list = this.b;
            if (list != null) {
                i = list.hashCode();
            }
            return hashCode + i;
        }

        @NotNull
        public String toString() {
            return "ClassRequest(classId=" + this.a + ", typeParametersCount=" + this.b + ")";
        }

        public a(@NotNull kotlin.reflect.jvm.internal.impl.name.a classId, @NotNull List<Integer> typeParametersCount) {
            k.f(classId, "classId");
            k.f(typeParametersCount, "typeParametersCount");
            this.a = classId;
            this.b = typeParametersCount;
        }
    }

    /* compiled from: NotFoundClasses.kt */
    public static final class d extends l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.name.b, m> {
        final /* synthetic */ a0 this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(a0 a0Var) {
            super(1);
            this.this$0 = a0Var;
        }

        @NotNull
        public final m invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName) {
            k.f(fqName, "fqName");
            return new m(this.this$0.d, fqName);
        }
    }

    /* compiled from: NotFoundClasses.kt */
    public static final class c extends l implements kotlin.jvm.functions.l<a, b> {
        final /* synthetic */ a0 this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(a0 a0Var) {
            super(1);
            this.this$0 = a0Var;
        }

        /* JADX WARNING: Removed duplicated region for block: B:10:0x0063  */
        /* JADX WARNING: Removed duplicated region for block: B:11:0x0068  */
        @org.jetbrains.annotations.NotNull
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final kotlin.reflect.jvm.internal.impl.descriptors.a0.b invoke(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.a0.a r11) {
            /*
                r10 = this;
                java.lang.String r0 = "<name for destructuring parameter 0>"
                kotlin.jvm.internal.k.f(r11, r0)
                kotlin.reflect.jvm.internal.impl.name.a r0 = r11.a()
                java.util.List r1 = r11.b()
                boolean r2 = r0.k()
                if (r2 != 0) goto L_0x0070
                kotlin.reflect.jvm.internal.impl.name.a r2 = r0.g()
                if (r2 == 0) goto L_0x002f
                r3 = 0
                kotlin.reflect.jvm.internal.impl.descriptors.a0 r4 = r10.this$0
                java.lang.String r5 = "outerClassId"
                kotlin.jvm.internal.k.b(r2, r5)
                r5 = 1
                java.util.List r5 = kotlin.collections.y.O(r1, r5)
                kotlin.reflect.jvm.internal.impl.descriptors.e r2 = r4.d(r2, r5)
                if (r2 == 0) goto L_0x002f
                r5 = r2
                goto L_0x0045
            L_0x002f:
                kotlin.reflect.jvm.internal.impl.descriptors.a0 r2 = r10.this$0
                kotlin.reflect.jvm.internal.impl.storage.c r2 = r2.a
                kotlin.reflect.jvm.internal.impl.name.b r3 = r0.h()
                java.lang.String r4 = "classId.packageFqName"
                kotlin.jvm.internal.k.b(r3, r4)
                java.lang.Object r2 = r2.invoke(r3)
                kotlin.reflect.jvm.internal.impl.descriptors.g r2 = (kotlin.reflect.jvm.internal.impl.descriptors.g) r2
                r5 = r2
            L_0x0045:
                boolean r2 = r0.l()
                kotlin.reflect.jvm.internal.impl.descriptors.a0$b r9 = new kotlin.reflect.jvm.internal.impl.descriptors.a0$b
                kotlin.reflect.jvm.internal.impl.descriptors.a0 r3 = r10.this$0
                kotlin.reflect.jvm.internal.impl.storage.j r4 = r3.c
                kotlin.reflect.jvm.internal.impl.name.f r6 = r0.j()
                java.lang.String r3 = "classId.shortClassName"
                kotlin.jvm.internal.k.b(r6, r3)
                java.lang.Object r3 = kotlin.collections.y.U(r1)
                java.lang.Integer r3 = (java.lang.Integer) r3
                if (r3 == 0) goto L_0x0068
                int r3 = r3.intValue()
                goto L_0x0069
            L_0x0068:
                r3 = 0
            L_0x0069:
                r8 = r3
                r3 = r9
                r7 = r2
                r3.<init>(r4, r5, r6, r7, r8)
                return r9
            L_0x0070:
                java.lang.UnsupportedOperationException r2 = new java.lang.UnsupportedOperationException
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r4 = "Unresolved local class: "
                r3.append(r4)
                r3.append(r0)
                java.lang.String r3 = r3.toString()
                r2.<init>(r3)
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.descriptors.a0.c.invoke(kotlin.reflect.jvm.internal.impl.descriptors.a0$a):kotlin.reflect.jvm.internal.impl.descriptors.a0$b");
        }
    }

    /* compiled from: NotFoundClasses.kt */
    public static final class b extends g {
        private final List<t0> a1;
        private final boolean a2;
        private final kotlin.reflect.jvm.internal.impl.types.j p1;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull j storageManager, @NotNull m container, @NotNull f name, boolean isInner, int numberOfDeclaredTypeParameters) {
            super(storageManager, container, name, o0.a, false);
            j jVar = storageManager;
            k.f(jVar, "storageManager");
            k.f(container, "container");
            k.f(name, "name");
            this.a2 = isInner;
            Iterable $this$map$iv = n.l(0, numberOfDeclaredTypeParameters);
            ArrayList arrayList = new ArrayList(r.r($this$map$iv, 10));
            Iterator it = $this$map$iv.iterator();
            while (it.hasNext()) {
                int index = ((g0) it).nextInt();
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.g b = kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b.b();
                h1 h1Var = h1.INVARIANT;
                StringBuilder sb = new StringBuilder();
                sb.append('T');
                sb.append(index);
                int i = index;
                ArrayList arrayList2 = arrayList;
                arrayList2.add(j0.J0(this, b, false, h1Var, f.f(sb.toString()), index, storageManager));
                arrayList = arrayList2;
                m mVar = container;
            }
            ArrayList arrayList3 = arrayList;
            this.a1 = arrayList3;
            this.p1 = new kotlin.reflect.jvm.internal.impl.types.j(this, arrayList3, n0.c(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.m(this).j().j()), jVar);
        }

        @NotNull
        public f h() {
            return f.CLASS;
        }

        @NotNull
        public w p() {
            return w.FINAL;
        }

        @NotNull
        public a1 getVisibility() {
            a1 a1Var = z0.e;
            k.b(a1Var, "Visibilities.PUBLIC");
            return a1Var;
        }

        @NotNull
        /* renamed from: A0 */
        public kotlin.reflect.jvm.internal.impl.types.j i() {
            return this.p1;
        }

        @NotNull
        public List<t0> o() {
            return this.a1;
        }

        public boolean x() {
            return this.a2;
        }

        public boolean V() {
            return false;
        }

        public boolean D0() {
            return false;
        }

        public boolean isInline() {
            return false;
        }

        public boolean d0() {
            return false;
        }

        public boolean S() {
            return false;
        }

        public boolean isExternal() {
            return false;
        }

        @NotNull
        public kotlin.reflect.jvm.internal.impl.descriptors.annotations.g getAnnotations() {
            return kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b.b();
        }

        /* access modifiers changed from: protected */
        @NotNull
        /* renamed from: C0 */
        public h.b a0(@NotNull i kotlinTypeRefiner) {
            k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
            return h.b.b;
        }

        @NotNull
        /* renamed from: l0 */
        public h.b g0() {
            return h.b.b;
        }

        @NotNull
        public Collection<d> f() {
            return o0.d();
        }

        @Nullable
        public d B() {
            return null;
        }

        @Nullable
        public e h0() {
            return null;
        }

        @NotNull
        public Collection<e> v() {
            return q.g();
        }

        @NotNull
        public String toString() {
            return "class " + getName() + " (not found)";
        }
    }

    @NotNull
    public final e d(@NotNull kotlin.reflect.jvm.internal.impl.name.a classId, @NotNull List<Integer> typeParametersCount) {
        k.f(classId, "classId");
        k.f(typeParametersCount, "typeParametersCount");
        return this.b.invoke(new a(classId, typeParametersCount));
    }
}
