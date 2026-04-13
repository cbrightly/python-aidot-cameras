package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.TypeCastException;
import kotlin.collections.o0;
import kotlin.collections.p;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.g;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.m0;
import kotlin.reflect.jvm.internal.impl.descriptors.r0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.h;
import kotlin.reflect.jvm.internal.impl.load.java.q;
import kotlin.reflect.jvm.internal.impl.load.java.s;
import kotlin.reflect.jvm.internal.impl.load.java.structure.j;
import kotlin.reflect.jvm.internal.impl.load.java.structure.v;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.u0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LazyJavaClassDescriptor.kt */
public final class f extends g implements kotlin.reflect.jvm.internal.impl.load.java.descriptors.d {
    private static final Set<String> a1 = o0.g("equals", "hashCode", "getClass", "wait", "notify", "notifyAll", "toString");
    public static final a p1 = new a((DefaultConstructorMarker) null);
    private final b A4;
    /* access modifiers changed from: private */
    public final g B4;
    private final m0<g> C4;
    private final kotlin.reflect.jvm.internal.impl.resolve.scopes.f D4;
    private final l E4;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.descriptors.annotations.g F4;
    private final kotlin.reflect.jvm.internal.impl.storage.f<List<t0>> G4;
    @NotNull
    private final h H4;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.load.java.structure.g I4;
    /* access modifiers changed from: private */
    public final e J4;
    /* access modifiers changed from: private */
    public final h a2;
    private final kotlin.reflect.jvm.internal.impl.descriptors.f p2;
    private final w p3;
    private final a1 p4;
    private final boolean z4;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public f(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.load.java.lazy.h r16, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.m r17, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.load.java.structure.g r18, @org.jetbrains.annotations.Nullable kotlin.reflect.jvm.internal.impl.descriptors.e r19) {
        /*
            r15 = this;
            r8 = r15
            r9 = r16
            r10 = r18
            r11 = r19
            java.lang.String r0 = "outerContext"
            kotlin.jvm.internal.k.f(r9, r0)
            java.lang.String r0 = "containingDeclaration"
            r12 = r17
            kotlin.jvm.internal.k.f(r12, r0)
            java.lang.String r0 = "jClass"
            kotlin.jvm.internal.k.f(r10, r0)
            kotlin.reflect.jvm.internal.impl.storage.j r1 = r16.e()
            kotlin.reflect.jvm.internal.impl.name.f r3 = r18.getName()
            kotlin.reflect.jvm.internal.impl.load.java.lazy.b r0 = r16.a()
            kotlin.reflect.jvm.internal.impl.load.java.sources.b r0 = r0.r()
            kotlin.reflect.jvm.internal.impl.load.java.sources.a r4 = r0.a(r10)
            r5 = 0
            r0 = r15
            r2 = r17
            r0.<init>(r1, r2, r3, r4, r5)
            r8.H4 = r9
            r8.I4 = r10
            r8.J4 = r11
            r3 = 0
            r4 = 4
            r5 = 0
            r0 = r16
            r1 = r15
            r2 = r18
            kotlin.reflect.jvm.internal.impl.load.java.lazy.h r13 = kotlin.reflect.jvm.internal.impl.load.java.lazy.a.d(r0, r1, r2, r3, r4, r5)
            r8.a2 = r13
            kotlin.reflect.jvm.internal.impl.load.java.lazy.b r0 = r13.a()
            kotlin.reflect.jvm.internal.impl.load.java.components.g r0 = r0.g()
            r0.e(r10, r15)
            kotlin.reflect.jvm.internal.impl.load.java.structure.a0 r0 = r18.E()
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x0060
            r0 = r2
            goto L_0x0061
        L_0x0060:
            r0 = r1
        L_0x0061:
            if (r0 == 0) goto L_0x0125
            boolean r0 = r18.l()
            if (r0 == 0) goto L_0x006e
            kotlin.reflect.jvm.internal.impl.descriptors.f r0 = kotlin.reflect.jvm.internal.impl.descriptors.f.ANNOTATION_CLASS
            goto L_0x0082
        L_0x006e:
            boolean r0 = r18.D()
            if (r0 == 0) goto L_0x0077
            kotlin.reflect.jvm.internal.impl.descriptors.f r0 = kotlin.reflect.jvm.internal.impl.descriptors.f.INTERFACE
            goto L_0x0082
        L_0x0077:
            boolean r0 = r18.q()
            if (r0 == 0) goto L_0x0080
            kotlin.reflect.jvm.internal.impl.descriptors.f r0 = kotlin.reflect.jvm.internal.impl.descriptors.f.ENUM_CLASS
            goto L_0x0082
        L_0x0080:
            kotlin.reflect.jvm.internal.impl.descriptors.f r0 = kotlin.reflect.jvm.internal.impl.descriptors.f.CLASS
        L_0x0082:
            r8.p2 = r0
            boolean r0 = r18.l()
            if (r0 != 0) goto L_0x00ad
            boolean r0 = r18.q()
            if (r0 == 0) goto L_0x0091
            goto L_0x00ad
        L_0x0091:
            kotlin.reflect.jvm.internal.impl.descriptors.w$a r0 = kotlin.reflect.jvm.internal.impl.descriptors.w.Companion
            boolean r3 = r18.isAbstract()
            if (r3 != 0) goto L_0x00a2
            boolean r3 = r18.D()
            if (r3 == 0) goto L_0x00a0
            goto L_0x00a2
        L_0x00a0:
            r3 = r1
            goto L_0x00a3
        L_0x00a2:
            r3 = r2
        L_0x00a3:
            boolean r4 = r18.isFinal()
            r4 = r4 ^ r2
            kotlin.reflect.jvm.internal.impl.descriptors.w r0 = r0.a(r3, r4)
            goto L_0x00af
        L_0x00ad:
            kotlin.reflect.jvm.internal.impl.descriptors.w r0 = kotlin.reflect.jvm.internal.impl.descriptors.w.FINAL
        L_0x00af:
            r8.p3 = r0
            kotlin.reflect.jvm.internal.impl.descriptors.a1 r0 = r18.getVisibility()
            r8.p4 = r0
            kotlin.reflect.jvm.internal.impl.load.java.structure.g r0 = r18.j()
            if (r0 == 0) goto L_0x00c5
            boolean r0 = r18.i()
            if (r0 != 0) goto L_0x00c5
            r0 = r2
            goto L_0x00c6
        L_0x00c5:
            r0 = r1
        L_0x00c6:
            r8.z4 = r0
            kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f$b r0 = new kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f$b
            r0.<init>()
            r8.A4 = r0
            kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.g r14 = new kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.g
            if (r11 == 0) goto L_0x00d5
            r4 = r2
            goto L_0x00d6
        L_0x00d5:
            r4 = r1
        L_0x00d6:
            r5 = 0
            r6 = 16
            r7 = 0
            r0 = r14
            r1 = r13
            r2 = r15
            r3 = r18
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            r8.B4 = r14
            kotlin.reflect.jvm.internal.impl.descriptors.m0$a r0 = kotlin.reflect.jvm.internal.impl.descriptors.m0.b
            kotlin.reflect.jvm.internal.impl.storage.j r1 = r13.e()
            kotlin.reflect.jvm.internal.impl.load.java.lazy.b r2 = r13.a()
            kotlin.reflect.jvm.internal.impl.types.checker.n r2 = r2.i()
            kotlin.reflect.jvm.internal.impl.types.checker.i r2 = r2.c()
            kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f$d r3 = new kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f$d
            r3.<init>(r15)
            kotlin.reflect.jvm.internal.impl.descriptors.m0 r0 = r0.a(r15, r1, r2, r3)
            r8.C4 = r0
            kotlin.reflect.jvm.internal.impl.resolve.scopes.f r0 = new kotlin.reflect.jvm.internal.impl.resolve.scopes.f
            r0.<init>(r14)
            r8.D4 = r0
            kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.l r0 = new kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.l
            r0.<init>(r13, r10, r15)
            r8.E4 = r0
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r0 = kotlin.reflect.jvm.internal.impl.load.java.lazy.f.a(r13, r10)
            r8.F4 = r0
            kotlin.reflect.jvm.internal.impl.storage.j r0 = r13.e()
            kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f$c r1 = new kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f$c
            r1.<init>(r15)
            kotlin.reflect.jvm.internal.impl.storage.f r0 = r0.c(r1)
            r8.G4 = r0
            return
        L_0x0125:
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Creating LazyJavaClassDescriptor for light class "
            r1.append(r2)
            r1.append(r10)
            java.lang.String r0 = r1.toString()
            java.lang.AssertionError r1 = new java.lang.AssertionError
            r1.<init>(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f.<init>(kotlin.reflect.jvm.internal.impl.load.java.lazy.h, kotlin.reflect.jvm.internal.impl.descriptors.m, kotlin.reflect.jvm.internal.impl.load.java.structure.g, kotlin.reflect.jvm.internal.impl.descriptors.e):void");
    }

    @NotNull
    public final kotlin.reflect.jvm.internal.impl.load.java.structure.g I0() {
        return this.I4;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ f(h hVar, m mVar, kotlin.reflect.jvm.internal.impl.load.java.structure.g gVar, e eVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(hVar, mVar, gVar, (i & 8) != 0 ? null : eVar);
    }

    /* compiled from: LazyJavaClassDescriptor.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.f h() {
        return this.p2;
    }

    @NotNull
    public w p() {
        return this.p3;
    }

    @NotNull
    public a1 getVisibility() {
        a1 a1Var = (!k.a(this.p4, z0.a) || this.I4.j() != null) ? this.p4 : q.a;
        k.b(a1Var, "if (visibility == Visibi…ISIBILITY else visibility");
        return a1Var;
    }

    public boolean x() {
        return this.z4;
    }

    public boolean D0() {
        return false;
    }

    public boolean isInline() {
        return false;
    }

    public boolean V() {
        return false;
    }

    public boolean d0() {
        return false;
    }

    public boolean S() {
        return false;
    }

    @NotNull
    public u0 i() {
        return this.A4;
    }

    /* compiled from: LazyJavaClassDescriptor.kt */
    public static final class d extends l implements kotlin.jvm.functions.l<i, g> {
        final /* synthetic */ f this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(f fVar) {
            super(1);
            this.this$0 = fVar;
        }

        @NotNull
        public final g invoke(@NotNull i kotlinTypeRefiner) {
            k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
            h A0 = this.this$0.a2;
            f fVar = this.this$0;
            return new g(A0, fVar, fVar.I0(), this.this$0.J4 != null, this.this$0.B4);
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: K0 */
    public g a0(@NotNull i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this.C4.c(kotlinTypeRefiner);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.resolve.scopes.h P() {
        return this.D4;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.resolve.scopes.h g0() {
        return this.E4;
    }

    @Nullable
    public kotlin.reflect.jvm.internal.impl.descriptors.d B() {
        return null;
    }

    @Nullable
    public e h0() {
        return null;
    }

    @NotNull
    /* renamed from: J0 */
    public g R() {
        kotlin.reflect.jvm.internal.impl.resolve.scopes.h R = super.R();
        if (R != null) {
            return (g) R;
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaClassMemberScope");
    }

    @NotNull
    /* renamed from: H0 */
    public List<kotlin.reflect.jvm.internal.impl.descriptors.d> f() {
        return (List) this.B4.o0().invoke();
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.annotations.g getAnnotations() {
        return this.F4;
    }

    /* compiled from: LazyJavaClassDescriptor.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<List<? extends t0>> {
        final /* synthetic */ f this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(f fVar) {
            super(0);
            this.this$0 = fVar;
        }

        @NotNull
        public final List<t0> invoke() {
            Iterable<kotlin.reflect.jvm.internal.impl.load.java.structure.w> $this$mapTo$iv$iv = this.this$0.I0().getTypeParameters();
            ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (kotlin.reflect.jvm.internal.impl.load.java.structure.w p : $this$mapTo$iv$iv) {
                t0 a = this.this$0.a2.f().a(p);
                if (a != null) {
                    arrayList.add(a);
                } else {
                    throw new AssertionError("Parameter " + p + " surely belongs to class " + this.this$0.I0() + ", so it must be resolved");
                }
            }
            return arrayList;
        }
    }

    @NotNull
    public List<t0> o() {
        return (List) this.G4.invoke();
    }

    @NotNull
    public Collection<e> v() {
        return kotlin.collections.q.g();
    }

    @NotNull
    public String toString() {
        return "Lazy Java class " + kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.k(this);
    }

    /* compiled from: LazyJavaClassDescriptor.kt */
    public final class b extends kotlin.reflect.jvm.internal.impl.types.b {
        private final kotlin.reflect.jvm.internal.impl.storage.f<List<t0>> c;

        public b() {
            super(f.this.a2.e());
            this.c = f.this.a2.e().c(new a(this));
        }

        /* compiled from: LazyJavaClassDescriptor.kt */
        public static final class a extends l implements kotlin.jvm.functions.a<List<? extends t0>> {
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(b bVar) {
                super(0);
                this.this$0 = bVar;
            }

            @NotNull
            public final List<t0> invoke() {
                return kotlin.reflect.jvm.internal.impl.descriptors.u0.d(f.this);
            }
        }

        @NotNull
        public List<t0> getParameters() {
            return (List) this.c.invoke();
        }

        /* access modifiers changed from: protected */
        @NotNull
        public Collection<b0> g() {
            Object obj;
            Collection javaTypes = f.this.I0().b();
            ArrayList result = new ArrayList(javaTypes.size());
            ArrayList incomplete = new ArrayList(0);
            b0 purelyImplementedSupertype = s();
            Iterator<j> it = javaTypes.iterator();
            while (true) {
                obj = null;
                if (!it.hasNext()) {
                    break;
                }
                j javaType = it.next();
                b0 kotlinType = f.this.a2.g().l(javaType, kotlin.reflect.jvm.internal.impl.load.java.lazy.types.d.f(kotlin.reflect.jvm.internal.impl.load.java.components.l.SUPERTYPE, false, (t0) null, 3, (Object) null));
                if (kotlinType.I0().c() instanceof a0.b) {
                    incomplete.add(javaType);
                }
                u0 I0 = kotlinType.I0();
                if (purelyImplementedSupertype != null) {
                    obj = purelyImplementedSupertype.I0();
                }
                if (!k.a(I0, obj) && !kotlin.reflect.jvm.internal.impl.builtins.g.d0(kotlinType)) {
                    result.add(kotlinType);
                }
            }
            e it2 = f.this.J4;
            if (it2 != null) {
                obj = kotlin.reflect.jvm.internal.impl.builtins.jvm.j.a(it2, f.this).c().n(it2.m(), h1.INVARIANT);
            }
            kotlin.reflect.jvm.internal.impl.utils.a.a(result, obj);
            kotlin.reflect.jvm.internal.impl.utils.a.a(result, purelyImplementedSupertype);
            if (!incomplete.isEmpty()) {
                kotlin.reflect.jvm.internal.impl.serialization.deserialization.r c2 = f.this.a2.a().c();
                e q = c();
                Iterable<v> $this$mapTo$iv$iv = incomplete;
                ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
                for (v javaType2 : $this$mapTo$iv$iv) {
                    if (javaType2 != null) {
                        arrayList.add(((j) javaType2).x());
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.load.java.structure.JavaClassifierType");
                    }
                }
                c2.b(q, arrayList);
            }
            return result.isEmpty() ^ true ? y.C0(result) : p.b(f.this.a2.d().j().j());
        }

        /* JADX WARNING: Code restructure failed: missing block: B:8:0x001d, code lost:
            if ((!r4.d() && r4.i(kotlin.reflect.jvm.internal.impl.builtins.g.a)) != false) goto L_0x0022;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private final kotlin.reflect.jvm.internal.impl.types.b0 s() {
            /*
                r18 = this;
                r0 = r18
                kotlin.reflect.jvm.internal.impl.name.b r1 = r18.t()
                r2 = 1
                r3 = 0
                if (r1 == 0) goto L_0x0020
                r4 = r1
                r5 = 0
                boolean r6 = r4.d()
                if (r6 != 0) goto L_0x001c
                kotlin.reflect.jvm.internal.impl.name.f r6 = kotlin.reflect.jvm.internal.impl.builtins.g.a
                boolean r6 = r4.i(r6)
                if (r6 == 0) goto L_0x001c
                r6 = r2
                goto L_0x001d
            L_0x001c:
                r6 = 0
            L_0x001d:
                if (r6 == 0) goto L_0x0020
                goto L_0x0021
            L_0x0020:
                r1 = r3
            L_0x0021:
                if (r1 == 0) goto L_0x0026
                r4 = r1
                goto L_0x0032
            L_0x0026:
                kotlin.reflect.jvm.internal.impl.load.java.k r4 = kotlin.reflect.jvm.internal.impl.load.java.k.b
                kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f r5 = kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f.this
                kotlin.reflect.jvm.internal.impl.name.b r5 = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.j(r5)
                kotlin.reflect.jvm.internal.impl.name.b r4 = r4.b(r5)
            L_0x0032:
                if (r4 == 0) goto L_0x010b
                kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f r5 = kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f.this
                kotlin.reflect.jvm.internal.impl.load.java.lazy.h r5 = r5.a2
                kotlin.reflect.jvm.internal.impl.descriptors.y r5 = r5.d()
                kotlin.reflect.jvm.internal.impl.incremental.components.d r6 = kotlin.reflect.jvm.internal.impl.incremental.components.d.FROM_JAVA_LOADER
                kotlin.reflect.jvm.internal.impl.descriptors.e r5 = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.s(r5, r4, r6)
                if (r5 == 0) goto L_0x010a
                kotlin.reflect.jvm.internal.impl.types.u0 r6 = r5.i()
                java.lang.String r7 = "classDescriptor.typeConstructor"
                kotlin.jvm.internal.k.b(r6, r7)
                java.util.List r6 = r6.getParameters()
                int r6 = r6.size()
                kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f r7 = kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f.this
                kotlin.reflect.jvm.internal.impl.types.u0 r7 = r7.i()
                java.util.List r7 = r7.getParameters()
                java.lang.String r8 = "getTypeConstructor().parameters"
                kotlin.jvm.internal.k.b(r7, r8)
                int r8 = r7.size()
                r9 = 10
                if (r8 != r6) goto L_0x00b4
                r2 = r7
                r3 = 0
                java.util.ArrayList r10 = new java.util.ArrayList
                int r9 = kotlin.collections.r.r(r2, r9)
                r10.<init>(r9)
                r9 = r10
                r10 = r2
                r11 = 0
                java.util.Iterator r12 = r10.iterator()
            L_0x0082:
                boolean r13 = r12.hasNext()
                if (r13 == 0) goto L_0x00af
                java.lang.Object r13 = r12.next()
                r14 = r13
                kotlin.reflect.jvm.internal.impl.descriptors.t0 r14 = (kotlin.reflect.jvm.internal.impl.descriptors.t0) r14
                r15 = 0
                kotlin.reflect.jvm.internal.impl.types.y0 r0 = new kotlin.reflect.jvm.internal.impl.types.y0
                r16 = r2
                kotlin.reflect.jvm.internal.impl.types.h1 r2 = kotlin.reflect.jvm.internal.impl.types.h1.INVARIANT
                r17 = r3
                java.lang.String r3 = "parameter"
                kotlin.jvm.internal.k.b(r14, r3)
                kotlin.reflect.jvm.internal.impl.types.i0 r3 = r14.m()
                r0.<init>(r2, r3)
                r9.add(r0)
                r0 = r18
                r2 = r16
                r3 = r17
                goto L_0x0082
            L_0x00af:
                r16 = r2
                r17 = r3
                goto L_0x00fd
            L_0x00b4:
                if (r8 != r2) goto L_0x0109
                if (r6 <= r2) goto L_0x0109
                if (r1 != 0) goto L_0x0109
                kotlin.reflect.jvm.internal.impl.types.y0 r0 = new kotlin.reflect.jvm.internal.impl.types.y0
                kotlin.reflect.jvm.internal.impl.types.h1 r3 = kotlin.reflect.jvm.internal.impl.types.h1.INVARIANT
                java.lang.Object r10 = kotlin.collections.y.q0(r7)
                java.lang.String r11 = "typeParameters.single()"
                kotlin.jvm.internal.k.b(r10, r11)
                kotlin.reflect.jvm.internal.impl.descriptors.t0 r10 = (kotlin.reflect.jvm.internal.impl.descriptors.t0) r10
                kotlin.reflect.jvm.internal.impl.types.i0 r10 = r10.m()
                r0.<init>(r3, r10)
                kotlin.ranges.i r3 = new kotlin.ranges.i
                r3.<init>(r2, r6)
                r2 = r3
                r3 = 0
                java.util.ArrayList r10 = new java.util.ArrayList
                int r9 = kotlin.collections.r.r(r2, r9)
                r10.<init>(r9)
                r9 = r10
                r10 = r2
                r11 = 0
                java.util.Iterator r12 = r10.iterator()
            L_0x00e8:
                boolean r13 = r12.hasNext()
                if (r13 == 0) goto L_0x00fc
                r13 = r12
                kotlin.collections.g0 r13 = (kotlin.collections.g0) r13
                int r13 = r13.nextInt()
                r14 = r13
                r15 = 0
                r9.add(r0)
                goto L_0x00e8
            L_0x00fc:
            L_0x00fd:
                r0 = r9
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.g$a r2 = kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r2 = r2.b()
                kotlin.reflect.jvm.internal.impl.types.i0 r2 = kotlin.reflect.jvm.internal.impl.types.c0.g(r2, r5, r0)
                return r2
            L_0x0109:
                return r3
            L_0x010a:
                return r3
            L_0x010b:
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.f.b.s():kotlin.reflect.jvm.internal.impl.types.b0");
        }

        private final kotlin.reflect.jvm.internal.impl.name.b t() {
            String fqNameString;
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g annotations = f.this.getAnnotations();
            kotlin.reflect.jvm.internal.impl.name.b bVar = s.j;
            k.b(bVar, "JvmAnnotationNames.PURELY_IMPLEMENTS_ANNOTATION");
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.c annotation = annotations.c(bVar);
            if (annotation == null) {
                return null;
            }
            Object r0 = y.r0(annotation.a().values());
            if (!(r0 instanceof kotlin.reflect.jvm.internal.impl.resolve.constants.w)) {
                r0 = null;
            }
            kotlin.reflect.jvm.internal.impl.resolve.constants.w wVar = (kotlin.reflect.jvm.internal.impl.resolve.constants.w) r0;
            if (wVar == null || (fqNameString = (String) wVar.b()) == null || !kotlin.reflect.jvm.internal.impl.name.e.c(fqNameString)) {
                return null;
            }
            return new kotlin.reflect.jvm.internal.impl.name.b(fqNameString);
        }

        /* access modifiers changed from: protected */
        @NotNull
        public r0 k() {
            return f.this.a2.a().t();
        }

        public boolean d() {
            return true;
        }

        @NotNull
        /* renamed from: q */
        public e c() {
            return f.this;
        }

        @NotNull
        public String toString() {
            String b = f.this.getName().b();
            k.b(b, "name.asString()");
            return b;
        }
    }

    @NotNull
    public final f G0(@NotNull kotlin.reflect.jvm.internal.impl.load.java.components.g javaResolverCache, @Nullable e additionalSupertypeClassDescriptor) {
        k.f(javaResolverCache, "javaResolverCache");
        h hVar = this.a2;
        h j = kotlin.reflect.jvm.internal.impl.load.java.lazy.a.j(hVar, hVar.a().u(javaResolverCache));
        m b2 = b();
        k.b(b2, "containingDeclaration");
        return new f(j, b2, this.I4, additionalSupertypeClassDescriptor);
    }
}
