package kotlin.reflect.jvm.internal.impl.builtins.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.builtins.j;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.b0;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.j0;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.r0;
import kotlin.reflect.jvm.internal.impl.descriptors.t;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.types.y0;
import kotlin.text.w;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FunctionClassDescriptor.kt */
public final class b extends kotlin.reflect.jvm.internal.impl.descriptors.impl.a {
    public static final C0344b p0 = new C0344b((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static final kotlin.reflect.jvm.internal.impl.name.a y = new kotlin.reflect.jvm.internal.impl.name.a(g.b, f.f("Function"));
    /* access modifiers changed from: private */
    public static final kotlin.reflect.jvm.internal.impl.name.a z = new kotlin.reflect.jvm.internal.impl.name.a(j.a(), f.f("KFunction"));
    private final c a1 = new c();
    /* access modifiers changed from: private */
    public final List<t0> a2;
    private final e p1;
    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.storage.j p2;
    /* access modifiers changed from: private */
    public final b0 p3;
    @NotNull
    private final d p4;
    private final int z4;

    /* compiled from: FunctionClassDescriptor.kt */
    public static final class a extends l implements p<h1, String, x> {
        final /* synthetic */ ArrayList $result;
        final /* synthetic */ b this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(b bVar, ArrayList arrayList) {
            super(2);
            this.this$0 = bVar;
            this.$result = arrayList;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            invoke((h1) obj, (String) obj2);
            return x.a;
        }

        public final void invoke(@NotNull h1 variance, @NotNull String name) {
            k.f(variance, "variance");
            k.f(name, "name");
            this.$result.add(j0.J0(this.this$0, kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b.b(), false, variance, f.f(name), this.$result.size(), this.this$0.p2));
        }
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public b(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.storage.j r17, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.b0 r18, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.builtins.functions.b.d r19, int r20) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            java.lang.String r5 = "storageManager"
            kotlin.jvm.internal.k.f(r1, r5)
            java.lang.String r5 = "containingDeclaration"
            kotlin.jvm.internal.k.f(r2, r5)
            java.lang.String r5 = "functionKind"
            kotlin.jvm.internal.k.f(r3, r5)
            kotlin.reflect.jvm.internal.impl.name.f r5 = r19.numberedClassName(r20)
            r0.<init>(r1, r5)
            r0.p2 = r1
            r0.p3 = r2
            r0.p4 = r3
            r0.z4 = r4
            kotlin.reflect.jvm.internal.impl.builtins.functions.b$c r5 = new kotlin.reflect.jvm.internal.impl.builtins.functions.b$c
            r5.<init>()
            r0.a1 = r5
            kotlin.reflect.jvm.internal.impl.builtins.functions.e r5 = new kotlin.reflect.jvm.internal.impl.builtins.functions.e
            r5.<init>(r1, r0)
            r0.p1 = r5
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            kotlin.reflect.jvm.internal.impl.builtins.functions.b$a r6 = new kotlin.reflect.jvm.internal.impl.builtins.functions.b$a
            r6.<init>(r0, r5)
            kotlin.ranges.i r7 = new kotlin.ranges.i
            r8 = 1
            r7.<init>(r8, r4)
            r8 = 0
            java.util.ArrayList r9 = new java.util.ArrayList
            r10 = 10
            int r10 = kotlin.collections.r.r(r7, r10)
            r9.<init>(r10)
            r10 = r7
            r11 = 0
            java.util.Iterator r12 = r10.iterator()
        L_0x005b:
            boolean r13 = r12.hasNext()
            if (r13 == 0) goto L_0x008c
            r13 = r12
            kotlin.collections.g0 r13 = (kotlin.collections.g0) r13
            int r13 = r13.nextInt()
            r14 = r13
            r15 = 0
            kotlin.reflect.jvm.internal.impl.types.h1 r1 = kotlin.reflect.jvm.internal.impl.types.h1.IN_VARIANCE
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r3 = 80
            r2.append(r3)
            r2.append(r14)
            java.lang.String r2 = r2.toString()
            r6.invoke((kotlin.reflect.jvm.internal.impl.types.h1) r1, (java.lang.String) r2)
            kotlin.x r1 = kotlin.x.a
            r9.add(r1)
            r1 = r17
            r2 = r18
            r3 = r19
            goto L_0x005b
        L_0x008c:
            kotlin.reflect.jvm.internal.impl.types.h1 r1 = kotlin.reflect.jvm.internal.impl.types.h1.OUT_VARIANCE
            java.lang.String r2 = "R"
            r6.invoke((kotlin.reflect.jvm.internal.impl.types.h1) r1, (java.lang.String) r2)
            java.util.List r1 = kotlin.collections.y.C0(r5)
            r0.a2 = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.builtins.functions.b.<init>(kotlin.reflect.jvm.internal.impl.storage.j, kotlin.reflect.jvm.internal.impl.descriptors.b0, kotlin.reflect.jvm.internal.impl.builtins.functions.b$d, int):void");
    }

    public /* bridge */ /* synthetic */ kotlin.reflect.jvm.internal.impl.descriptors.d B() {
        return (kotlin.reflect.jvm.internal.impl.descriptors.d) Q0();
    }

    public /* bridge */ /* synthetic */ e h0() {
        return (e) J0();
    }

    @NotNull
    public final d M0() {
        return this.p4;
    }

    public final int I0() {
        return this.z4;
    }

    /* compiled from: FunctionClassDescriptor.kt */
    public enum d {
        Function(r2, "Function"),
        SuspendFunction(r2, "SuspendFunction"),
        KFunction(j.a(), "KFunction"),
        KSuspendFunction(j.a(), "KSuspendFunction");
        
        public static final a Companion = null;
        @NotNull
        private final String classNamePrefix;
        @NotNull
        private final kotlin.reflect.jvm.internal.impl.name.b packageFqName;

        private d(kotlin.reflect.jvm.internal.impl.name.b packageFqName2, String classNamePrefix2) {
            this.packageFqName = packageFqName2;
            this.classNamePrefix = classNamePrefix2;
        }

        @NotNull
        public final String getClassNamePrefix() {
            return this.classNamePrefix;
        }

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.name.b getPackageFqName() {
            return this.packageFqName;
        }

        static {
            Companion = new a((DefaultConstructorMarker) null);
        }

        @NotNull
        public final f numberedClassName(int arity) {
            f f = f.f(this.classNamePrefix + arity);
            k.b(f, "Name.identifier(\"$classNamePrefix$arity\")");
            return f;
        }

        /* compiled from: FunctionClassDescriptor.kt */
        public static final class a {
            private a() {
            }

            public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
                this();
            }

            @Nullable
            public final d a(@NotNull kotlin.reflect.jvm.internal.impl.name.b packageFqName, @NotNull String className) {
                k.f(packageFqName, "packageFqName");
                k.f(className, "className");
                for (d dVar : d.values()) {
                    d it = dVar;
                    if (k.a(it.getPackageFqName(), packageFqName) && w.N(className, it.getClassNamePrefix(), false, 2, (Object) null)) {
                        return dVar;
                    }
                }
                return null;
            }
        }
    }

    @NotNull
    /* renamed from: L0 */
    public b0 b() {
        return this.p3;
    }

    @NotNull
    /* renamed from: O0 */
    public h.b g0() {
        return h.b.b;
    }

    @NotNull
    public u0 i() {
        return this.a1;
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: P0 */
    public e a0(@NotNull i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this.p1;
    }

    @Nullable
    public Void J0() {
        return null;
    }

    @NotNull
    /* renamed from: K0 */
    public List<kotlin.reflect.jvm.internal.impl.descriptors.d> f() {
        return q.g();
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.f h() {
        return kotlin.reflect.jvm.internal.impl.descriptors.f.INTERFACE;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.w p() {
        return kotlin.reflect.jvm.internal.impl.descriptors.w.ABSTRACT;
    }

    @Nullable
    public Void Q0() {
        return null;
    }

    @NotNull
    public a1 getVisibility() {
        a1 a1Var = z0.e;
        k.b(a1Var, "Visibilities.PUBLIC");
        return a1Var;
    }

    public boolean V() {
        return false;
    }

    public boolean x() {
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

    @NotNull
    public o0 n() {
        o0 o0Var = o0.a;
        k.b(o0Var, "SourceElement.NO_SOURCE");
        return o0Var;
    }

    @NotNull
    /* renamed from: N0 */
    public List<e> v() {
        return q.g();
    }

    @NotNull
    public List<t0> o() {
        return this.a2;
    }

    /* compiled from: FunctionClassDescriptor.kt */
    public final class c extends kotlin.reflect.jvm.internal.impl.types.b {
        public c() {
            super(b.this.p2);
        }

        /* access modifiers changed from: protected */
        @NotNull
        public Collection<kotlin.reflect.jvm.internal.impl.types.b0> g() {
            Iterable supertypes;
            switch (c.a[b.this.M0().ordinal()]) {
                case 1:
                    supertypes = kotlin.collections.p.b(b.y);
                    break;
                case 2:
                    supertypes = q.j(b.z, new kotlin.reflect.jvm.internal.impl.name.a(g.b, d.Function.numberedClassName(b.this.I0())));
                    break;
                case 3:
                    supertypes = kotlin.collections.p.b(b.y);
                    break;
                case 4:
                    supertypes = q.j(b.z, new kotlin.reflect.jvm.internal.impl.name.a(kotlin.reflect.jvm.internal.impl.resolve.c.c, d.SuspendFunction.numberedClassName(b.this.I0())));
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            y moduleDescriptor = b.this.p3.b();
            Iterable<kotlin.reflect.jvm.internal.impl.name.a> $this$map$iv = supertypes;
            Collection destination$iv$iv = new ArrayList(r.r($this$map$iv, 10));
            for (kotlin.reflect.jvm.internal.impl.name.a id : $this$map$iv) {
                e descriptor = t.a(moduleDescriptor, id);
                if (descriptor != null) {
                    List<t0> parameters = getParameters();
                    u0 i = descriptor.i();
                    k.b(i, "descriptor.typeConstructor");
                    Iterable<t0> $this$mapTo$iv$iv = kotlin.collections.y.x0(parameters, i.getParameters().size());
                    List supertypes2 = supertypes;
                    List arguments = new ArrayList(r.r($this$mapTo$iv$iv, 10));
                    for (t0 it : $this$mapTo$iv$iv) {
                        arguments.add(new y0(it.m()));
                        moduleDescriptor = moduleDescriptor;
                    }
                    destination$iv$iv.add(c0.g(kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b.b(), descriptor, arguments));
                    supertypes = supertypes2;
                } else {
                    throw new IllegalStateException(("Built-in class " + id + " not found").toString());
                }
            }
            return kotlin.collections.y.C0(destination$iv$iv);
        }

        @NotNull
        public List<t0> getParameters() {
            return b.this.a2;
        }

        @NotNull
        /* renamed from: s */
        public b q() {
            return b.this;
        }

        public boolean d() {
            return true;
        }

        @NotNull
        public String toString() {
            return q().toString();
        }

        /* access modifiers changed from: protected */
        @NotNull
        public r0 k() {
            return r0.a.a;
        }
    }

    @NotNull
    public String toString() {
        String b = getName().b();
        k.b(b, "name.asString()");
        return b;
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.builtins.functions.b$b  reason: collision with other inner class name */
    /* compiled from: FunctionClassDescriptor.kt */
    public static final class C0344b {
        private C0344b() {
        }

        public /* synthetic */ C0344b(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
