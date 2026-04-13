package kotlin.reflect.jvm.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.e0;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.u;
import kotlin.reflect.jvm.internal.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.d;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.f;
import kotlin.reflect.jvm.internal.impl.descriptors.t;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.metadata.n;
import kotlin.reflect.jvm.internal.impl.protobuf.h;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.j;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.j;
import kotlin.text.w;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KClassImpl.kt */
public final class g<T> extends j implements kotlin.reflect.c<T>, i {
    @NotNull
    private final a0.b<g<T>.defpackage.a> q;
    @NotNull
    private final Class<T> x;

    /* compiled from: KClassImpl.kt */
    public final class a extends j.b {
        static final /* synthetic */ kotlin.reflect.k[] d;
        @NotNull
        private final a0.a e = a0.d(new i(this));
        @NotNull
        private final a0.a f = a0.d(new d(this));
        @Nullable
        private final a0.a g = a0.d(new p(this));
        @Nullable
        private final a0.a h = a0.d(new n(this));
        @NotNull
        private final a0.a i = a0.d(new e(this));
        @NotNull
        private final a0.a j = a0.d(new l(this));
        @Nullable
        private final a0.b k = a0.b(new m(this));
        @NotNull
        private final a0.a l = a0.d(new r(this));
        @NotNull
        private final a0.a m = a0.d(new q(this));
        @NotNull
        private final a0.a n = a0.d(new o(this));
        @NotNull
        private final a0.a o = a0.d(new C0339g(this));
        private final a0.a p = a0.d(new h(this));
        private final a0.a q = a0.d(new j(this));
        private final a0.a r = a0.d(new k(this));
        @NotNull
        private final a0.a s = a0.d(new b(this));
        @NotNull
        private final a0.a t = a0.d(new c(this));
        @NotNull
        private final a0.a u = a0.d(new f(this));
        @NotNull
        private final a0.a v = a0.d(new C0338a(this));

        static {
            Class<a> cls = a.class;
            d = new kotlin.reflect.k[]{kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "descriptor", "getDescriptor()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "annotations", "getAnnotations()Ljava/util/List;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "simpleName", "getSimpleName()Ljava/lang/String;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "qualifiedName", "getQualifiedName()Ljava/lang/String;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "constructors", "getConstructors()Ljava/util/Collection;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "nestedClasses", "getNestedClasses()Ljava/util/Collection;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "objectInstance", "getObjectInstance()Ljava/lang/Object;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "typeParameters", "getTypeParameters()Ljava/util/List;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "supertypes", "getSupertypes()Ljava/util/List;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "sealedSubclasses", "getSealedSubclasses()Ljava/util/List;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "declaredNonStaticMembers", "getDeclaredNonStaticMembers()Ljava/util/Collection;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "declaredStaticMembers", "getDeclaredStaticMembers()Ljava/util/Collection;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "inheritedNonStaticMembers", "getInheritedNonStaticMembers()Ljava/util/Collection;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "inheritedStaticMembers", "getInheritedStaticMembers()Ljava/util/Collection;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "allNonStaticMembers", "getAllNonStaticMembers()Ljava/util/Collection;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "allStaticMembers", "getAllStaticMembers()Ljava/util/Collection;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "declaredMembers", "getDeclaredMembers()Ljava/util/Collection;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "allMembers", "getAllMembers()Ljava/util/Collection;"))};
        }

        /* access modifiers changed from: private */
        public final Collection<e<?>> l() {
            return (Collection) this.p.b(this, d[11]);
        }

        /* access modifiers changed from: private */
        public final Collection<e<?>> n() {
            return (Collection) this.q.b(this, d[12]);
        }

        /* access modifiers changed from: private */
        public final Collection<e<?>> o() {
            return (Collection) this.r.b(this, d[13]);
        }

        @NotNull
        public final Collection<e<?>> g() {
            return (Collection) this.v.b(this, d[17]);
        }

        @NotNull
        public final Collection<e<?>> h() {
            return (Collection) this.s.b(this, d[14]);
        }

        @NotNull
        public final Collection<e<?>> i() {
            return (Collection) this.t.b(this, d[15]);
        }

        @NotNull
        public final Collection<kotlin.reflect.f<T>> j() {
            return (Collection) this.i.b(this, d[4]);
        }

        @NotNull
        public final Collection<e<?>> k() {
            return (Collection) this.o.b(this, d[10]);
        }

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.descriptors.e m() {
            return (kotlin.reflect.jvm.internal.impl.descriptors.e) this.e.b(this, d[0]);
        }

        @NotNull
        public final Collection<kotlin.reflect.c<?>> p() {
            return (Collection) this.j.b(this, d[5]);
        }

        @Nullable
        public final T q() {
            return this.k.b(this, d[6]);
        }

        @Nullable
        public final String r() {
            return (String) this.h.b(this, d[3]);
        }

        public a() {
            super();
        }

        /* compiled from: KClassImpl.kt */
        public static final class i extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<kotlin.reflect.jvm.internal.impl.descriptors.e> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            i(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final kotlin.reflect.jvm.internal.impl.descriptors.e invoke() {
                kotlin.reflect.jvm.internal.impl.descriptors.e descriptor;
                kotlin.reflect.jvm.internal.impl.name.a classId = g.this.J();
                kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.k moduleData = g.this.K().c().a();
                if (classId.k()) {
                    descriptor = moduleData.a().b(classId);
                } else {
                    descriptor = t.a(moduleData.b(), classId);
                }
                if (descriptor != null) {
                    return descriptor;
                }
                Void unused = g.this.O();
                throw null;
            }
        }

        /* compiled from: KClassImpl.kt */
        public static final class d extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends Annotation>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            d(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final List<Annotation> invoke() {
                return h0.d(this.this$0.m());
            }
        }

        /* compiled from: KClassImpl.kt */
        public static final class p extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<String> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            p(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @Nullable
            public final String invoke() {
                if (g.this.b().isAnonymousClass()) {
                    return null;
                }
                kotlin.reflect.jvm.internal.impl.name.a classId = g.this.J();
                if (classId.k()) {
                    a aVar = this.this$0;
                    return aVar.f(g.this.b());
                }
                String b = classId.j().b();
                kotlin.jvm.internal.k.b(b, "classId.shortClassName.asString()");
                return b;
            }
        }

        /* compiled from: KClassImpl.kt */
        public static final class n extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<String> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            n(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @Nullable
            public final String invoke() {
                if (g.this.b().isAnonymousClass()) {
                    return null;
                }
                kotlin.reflect.jvm.internal.impl.name.a classId = g.this.J();
                if (classId.k()) {
                    return null;
                }
                return classId.b().b();
            }
        }

        /* access modifiers changed from: private */
        public final String f(Class<?> jClass) {
            String name = jClass.getSimpleName();
            Method method = jClass.getEnclosingMethod();
            if (method != null) {
                kotlin.jvm.internal.k.b(name, "name");
                return x.R0(name, method.getName() + "$", (String) null, 2, (Object) null);
            }
            Constructor constructor = jClass.getEnclosingConstructor();
            if (constructor != null) {
                kotlin.jvm.internal.k.b(name, "name");
                return x.R0(name, constructor.getName() + "$", (String) null, 2, (Object) null);
            }
            kotlin.jvm.internal.k.b(name, "name");
            return x.Q0(name, '$', (String) null, 2, (Object) null);
        }

        /* compiled from: KClassImpl.kt */
        public static final class e extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends kotlin.reflect.f<? extends T>>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            e(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final List<kotlin.reflect.f<T>> invoke() {
                Iterable<kotlin.reflect.jvm.internal.impl.descriptors.l> $this$mapTo$iv$iv = g.this.v();
                ArrayList arrayList = new ArrayList(kotlin.collections.r.r($this$mapTo$iv$iv, 10));
                for (kotlin.reflect.jvm.internal.impl.descriptors.l descriptor : $this$mapTo$iv$iv) {
                    arrayList.add(new k(g.this, descriptor));
                }
                return arrayList;
            }
        }

        /* compiled from: KClassImpl.kt */
        public static final class l extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends g<? extends Object>>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            l(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final List<g<? extends Object>> invoke() {
                Iterable $this$filterNotTo$iv$iv = j.a.a(this.this$0.m().P(), (kotlin.reflect.jvm.internal.impl.resolve.scopes.d) null, (kotlin.jvm.functions.l) null, 3, (Object) null);
                ArrayList arrayList = new ArrayList();
                for (Object element$iv$iv : $this$filterNotTo$iv$iv) {
                    if (!kotlin.reflect.jvm.internal.impl.resolve.c.B((kotlin.reflect.jvm.internal.impl.descriptors.m) element$iv$iv)) {
                        arrayList.add(element$iv$iv);
                    }
                }
                Iterable<kotlin.reflect.jvm.internal.impl.descriptors.m> $this$mapNotNullTo$iv$iv = arrayList;
                ArrayList arrayList2 = new ArrayList();
                for (kotlin.reflect.jvm.internal.impl.descriptors.m nestedClass : $this$mapNotNullTo$iv$iv) {
                    if (nestedClass != null) {
                        Class jClass = h0.l((kotlin.reflect.jvm.internal.impl.descriptors.e) nestedClass);
                        Object it$iv$iv = jClass != null ? new g(jClass) : null;
                        if (it$iv$iv != null) {
                            arrayList2.add(it$iv$iv);
                        }
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    }
                }
                return arrayList2;
            }
        }

        /* compiled from: KClassImpl.kt */
        public static final class m extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<T> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            m(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @Nullable
            public final T invoke() {
                Field field;
                kotlin.reflect.jvm.internal.impl.descriptors.e descriptor = this.this$0.m();
                if (descriptor.h() != kotlin.reflect.jvm.internal.impl.descriptors.f.OBJECT) {
                    return null;
                }
                if (!descriptor.V() || kotlin.reflect.jvm.internal.impl.builtins.c.b.b(descriptor)) {
                    field = g.this.b().getDeclaredField("INSTANCE");
                } else {
                    field = g.this.b().getEnclosingClass().getDeclaredField(descriptor.getName().b());
                }
                T t = field.get((Object) null);
                if (t != null) {
                    return t;
                }
                throw new TypeCastException("null cannot be cast to non-null type T");
            }
        }

        /* compiled from: KClassImpl.kt */
        public static final class r extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends x>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            r(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final List<x> invoke() {
                Iterable<t0> $this$mapTo$iv$iv = this.this$0.m().o();
                kotlin.jvm.internal.k.b($this$mapTo$iv$iv, "descriptor.declaredTypeParameters");
                ArrayList arrayList = new ArrayList(kotlin.collections.r.r($this$mapTo$iv$iv, 10));
                for (t0 p1 : $this$mapTo$iv$iv) {
                    arrayList.add(new x(p1));
                }
                return arrayList;
            }
        }

        /* compiled from: KClassImpl.kt */
        public static final class q extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends w>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            q(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final List<w> invoke() {
                w it;
                u0 i = this.this$0.m().i();
                kotlin.jvm.internal.k.b(i, "descriptor.typeConstructor");
                Collection<b0> $this$mapTo$iv = i.b();
                kotlin.jvm.internal.k.b($this$mapTo$iv, "descriptor.typeConstructor.supertypes");
                ArrayList result = new ArrayList($this$mapTo$iv.size());
                for (b0 kotlinType : $this$mapTo$iv) {
                    kotlin.jvm.internal.k.b(kotlinType, "kotlinType");
                    result.add(new w(kotlinType, new C0340a(kotlinType, this)));
                }
                if (!kotlin.reflect.jvm.internal.impl.builtins.g.F0(this.this$0.m())) {
                    ArrayList arrayList = result;
                    boolean z = false;
                    if (!arrayList.isEmpty()) {
                        Iterator it2 = arrayList.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                z = true;
                                break;
                            }
                            kotlin.reflect.jvm.internal.impl.descriptors.e e = kotlin.reflect.jvm.internal.impl.resolve.c.e(((w) it2.next()).e());
                            kotlin.jvm.internal.k.b(e, "DescriptorUtils.getClassDescriptorForType(it.type)");
                            kotlin.reflect.jvm.internal.impl.descriptors.f classKind = e.h();
                            kotlin.jvm.internal.k.b(classKind, "DescriptorUtils.getClass…ptorForType(it.type).kind");
                            if (classKind == kotlin.reflect.jvm.internal.impl.descriptors.f.INTERFACE || classKind == kotlin.reflect.jvm.internal.impl.descriptors.f.ANNOTATION_CLASS) {
                                it = 1;
                                continue;
                            } else {
                                it = null;
                                continue;
                            }
                            if (it == null) {
                                break;
                            }
                        }
                    } else {
                        z = true;
                    }
                    if (z) {
                        i0 j = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.h(this.this$0.m()).j();
                        kotlin.jvm.internal.k.b(j, "descriptor.builtIns.anyType");
                        result.add(new w(j, b.INSTANCE));
                    }
                }
                return kotlin.reflect.jvm.internal.impl.utils.a.c(result);
            }

            /* renamed from: kotlin.reflect.jvm.internal.g$a$q$a  reason: collision with other inner class name */
            /* compiled from: KClassImpl.kt */
            public static final class C0340a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Type> {
                final /* synthetic */ b0 $kotlinType;
                final /* synthetic */ q this$0;

                /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                C0340a(b0 b0Var, q qVar) {
                    super(0);
                    this.$kotlinType = b0Var;
                    this.this$0 = qVar;
                }

                @NotNull
                public final Type invoke() {
                    kotlin.reflect.jvm.internal.impl.descriptors.h superClass = this.$kotlinType.I0().c();
                    if (superClass instanceof kotlin.reflect.jvm.internal.impl.descriptors.e) {
                        Class superJavaClass = h0.l((kotlin.reflect.jvm.internal.impl.descriptors.e) superClass);
                        if (superJavaClass == null) {
                            throw new y("Unsupported superclass of " + this.this$0.this$0 + ": " + superClass);
                        } else if (kotlin.jvm.internal.k.a(g.this.b().getSuperclass(), superJavaClass)) {
                            Type genericSuperclass = g.this.b().getGenericSuperclass();
                            kotlin.jvm.internal.k.b(genericSuperclass, "jClass.genericSuperclass");
                            return genericSuperclass;
                        } else {
                            Class[] interfaces = g.this.b().getInterfaces();
                            kotlin.jvm.internal.k.b(interfaces, "jClass.interfaces");
                            int index = kotlin.collections.l.B(interfaces, superJavaClass);
                            if (index >= 0) {
                                Type type = g.this.b().getGenericInterfaces()[index];
                                kotlin.jvm.internal.k.b(type, "jClass.genericInterfaces[index]");
                                return type;
                            }
                            throw new y("No superclass of " + this.this$0.this$0 + " in Java reflection for " + superClass);
                        }
                    } else {
                        throw new y("Supertype not a class: " + superClass);
                    }
                }
            }

            /* compiled from: KClassImpl.kt */
            public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Class<Object>> {
                public static final b INSTANCE = new b();

                b() {
                    super(0);
                }

                @NotNull
                public final Class<Object> invoke() {
                    return Object.class;
                }
            }
        }

        /* compiled from: KClassImpl.kt */
        public static final class o extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends g<? extends T>>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            o(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final List<g<? extends T>> invoke() {
                Iterable<kotlin.reflect.jvm.internal.impl.descriptors.e> $this$mapNotNullTo$iv$iv = this.this$0.m().v();
                kotlin.jvm.internal.k.b($this$mapNotNullTo$iv$iv, "descriptor.sealedSubclasses");
                ArrayList arrayList = new ArrayList();
                for (kotlin.reflect.jvm.internal.impl.descriptors.e subclass : $this$mapNotNullTo$iv$iv) {
                    if (subclass != null) {
                        Class jClass = h0.l(subclass);
                        Object it$iv$iv = jClass != null ? new g(jClass) : null;
                        if (it$iv$iv != null) {
                            arrayList.add(it$iv$iv);
                        }
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    }
                }
                return arrayList;
            }
        }

        /* renamed from: kotlin.reflect.jvm.internal.g$a$g  reason: collision with other inner class name */
        /* compiled from: KClassImpl.kt */
        public static final class C0339g extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Collection<? extends e<?>>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0339g(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final Collection<e<?>> invoke() {
                g gVar = g.this;
                return gVar.y(gVar.M(), j.c.DECLARED);
            }
        }

        /* compiled from: KClassImpl.kt */
        public static final class h extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Collection<? extends e<?>>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            h(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final Collection<e<?>> invoke() {
                g gVar = g.this;
                return gVar.y(gVar.N(), j.c.DECLARED);
            }
        }

        /* compiled from: KClassImpl.kt */
        public static final class j extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Collection<? extends e<?>>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            j(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final Collection<e<?>> invoke() {
                g gVar = g.this;
                return gVar.y(gVar.M(), j.c.INHERITED);
            }
        }

        /* compiled from: KClassImpl.kt */
        public static final class k extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Collection<? extends e<?>>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            k(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final Collection<e<?>> invoke() {
                g gVar = g.this;
                return gVar.y(gVar.N(), j.c.INHERITED);
            }
        }

        /* compiled from: KClassImpl.kt */
        public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends e<?>>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final List<e<?>> invoke() {
                return y.n0(this.this$0.k(), this.this$0.n());
            }
        }

        /* compiled from: KClassImpl.kt */
        public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends e<?>>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            c(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final List<e<?>> invoke() {
                return y.n0(this.this$0.l(), this.this$0.o());
            }
        }

        /* compiled from: KClassImpl.kt */
        public static final class f extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends e<?>>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            f(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final List<e<?>> invoke() {
                return y.n0(this.this$0.k(), this.this$0.l());
            }
        }

        /* renamed from: kotlin.reflect.jvm.internal.g$a$a  reason: collision with other inner class name */
        /* compiled from: KClassImpl.kt */
        public static final class C0338a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends e<?>>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0338a(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final List<e<?>> invoke() {
                return y.n0(this.this$0.h(), this.this$0.i());
            }
        }
    }

    public g(@NotNull Class<T> jClass) {
        k.f(jClass, "jClass");
        this.x = jClass;
        a0.b<g<T>.defpackage.a> b2 = a0.b(new b(this));
        k.b(b2, "ReflectProperties.lazy { Data() }");
        this.q = b2;
    }

    @NotNull
    public Class<T> b() {
        return this.x;
    }

    /* compiled from: KClassImpl.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<g<T>.defpackage.a> {
        final /* synthetic */ g this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(g gVar) {
            super(0);
            this.this$0 = gVar;
        }

        @NotNull
        public final g<T>.defpackage.a invoke() {
            return new a();
        }
    }

    @NotNull
    public final a0.b<g<T>.defpackage.a> K() {
        return this.q;
    }

    @NotNull
    /* renamed from: L */
    public e c() {
        return this.q.c().m();
    }

    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.name.a J() {
        return e0.b.c(b());
    }

    @NotNull
    public final h M() {
        return c().m().l();
    }

    @NotNull
    public final h N() {
        h g0 = c().g0();
        k.b(g0, "descriptor.staticScope");
        return g0;
    }

    @NotNull
    public Collection<kotlin.reflect.b<?>> g() {
        return this.q.c().g();
    }

    @NotNull
    public Collection<kotlin.reflect.jvm.internal.impl.descriptors.l> v() {
        e descriptor = c();
        if (descriptor.h() == f.INTERFACE || descriptor.h() == f.OBJECT) {
            return q.g();
        }
        Collection<d> f = descriptor.f();
        k.b(f, "descriptor.constructors");
        return f;
    }

    @NotNull
    public Collection<kotlin.reflect.jvm.internal.impl.descriptors.i0> A(@NotNull kotlin.reflect.jvm.internal.impl.name.f name) {
        k.f(name, "name");
        h M = M();
        kotlin.reflect.jvm.internal.impl.incremental.components.d dVar = kotlin.reflect.jvm.internal.impl.incremental.components.d.FROM_REFLECTION;
        return y.n0(M.e(name, dVar), N().e(name, dVar));
    }

    @NotNull
    public Collection<kotlin.reflect.jvm.internal.impl.descriptors.u> w(@NotNull kotlin.reflect.jvm.internal.impl.name.f name) {
        k.f(name, "name");
        h M = M();
        kotlin.reflect.jvm.internal.impl.incremental.components.d dVar = kotlin.reflect.jvm.internal.impl.incremental.components.d.FROM_REFLECTION;
        return y.n0(M.b(name, dVar), N().b(name, dVar));
    }

    @Nullable
    public kotlin.reflect.jvm.internal.impl.descriptors.i0 x(int index) {
        Class interfaceClass;
        if (!k.a(b().getSimpleName(), "DefaultImpls") || (interfaceClass = b().getDeclaringClass()) == null || !interfaceClass.isInterface()) {
            e L = c();
            if (!(L instanceof kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d)) {
                L = null;
            }
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d descriptor = (kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d) L;
            if (descriptor == null) {
                return null;
            }
            kotlin.reflect.jvm.internal.impl.metadata.c Q0 = descriptor.Q0();
            h.f<kotlin.reflect.jvm.internal.impl.metadata.c, List<n>> fVar = kotlin.reflect.jvm.internal.impl.metadata.jvm.a.j;
            k.b(fVar, "JvmProtoBuf.classLocalVariable");
            n proto = (n) kotlin.reflect.jvm.internal.impl.metadata.deserialization.f.b(Q0, fVar, index);
            if (proto != null) {
                return (kotlin.reflect.jvm.internal.impl.descriptors.i0) h0.e(b(), proto, descriptor.P0().g(), descriptor.P0().j(), descriptor.S0(), c.INSTANCE);
            }
            return null;
        }
        kotlin.reflect.c<?> e = kotlin.jvm.a.e(interfaceClass);
        if (e != null) {
            return ((g) e).x(index);
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.reflect.jvm.internal.KClassImpl<*>");
    }

    /* compiled from: KClassImpl.kt */
    public static final /* synthetic */ class c extends kotlin.jvm.internal.h implements p<kotlin.reflect.jvm.internal.impl.serialization.deserialization.x, n, kotlin.reflect.jvm.internal.impl.descriptors.i0> {
        public static final c INSTANCE = new c();

        c() {
            super(2);
        }

        public final String getName() {
            return "loadProperty";
        }

        public final kotlin.reflect.e getOwner() {
            return kotlin.jvm.internal.a0.b(kotlin.reflect.jvm.internal.impl.serialization.deserialization.x.class);
        }

        public final String getSignature() {
            return "loadProperty(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;)Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;";
        }

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.descriptors.i0 invoke(@NotNull kotlin.reflect.jvm.internal.impl.serialization.deserialization.x p1, @NotNull n p2) {
            k.f(p1, "p1");
            k.f(p2, "p2");
            return p1.p(p2);
        }
    }

    @Nullable
    public String l() {
        return this.q.c().r();
    }

    @NotNull
    public Collection<kotlin.reflect.f<T>> f() {
        return this.q.c().j();
    }

    @NotNull
    public Collection<kotlin.reflect.c<?>> i() {
        return this.q.c().p();
    }

    @Nullable
    public T j() {
        return this.q.c().q();
    }

    public boolean k(@Nullable Object value) {
        Integer d = kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b.d(b());
        if (d != null) {
            return e0.k(value, d.intValue());
        }
        Class<?> h = kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b.h(b());
        if (h == null) {
            h = b();
        }
        return h.isInstance(value);
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof g) && k.a(kotlin.jvm.a.c(this), kotlin.jvm.a.c((kotlin.reflect.c) other));
    }

    public int hashCode() {
        return kotlin.jvm.a.c(this).hashCode();
    }

    @NotNull
    public String toString() {
        String packagePrefix;
        StringBuilder sb = new StringBuilder();
        sb.append("class ");
        kotlin.reflect.jvm.internal.impl.name.a classId = J();
        kotlin.reflect.jvm.internal.impl.name.b packageFqName = classId.h();
        k.b(packageFqName, "classId.packageFqName");
        if (packageFqName.d()) {
            packagePrefix = "";
        } else {
            packagePrefix = packageFqName.b() + ".";
        }
        String b2 = classId.i().b();
        k.b(b2, "classId.relativeClassName.asString()");
        sb.append(packagePrefix + w.G(b2, '.', '$', false, 4, (Object) null));
        return sb.toString();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000c, code lost:
        r0 = r0.b();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Void O() {
        /*
            r4 = this;
            kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.f$a r0 = kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.f.a
            java.lang.Class r1 = r4.b()
            kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.f r0 = r0.a(r1)
            if (r0 == 0) goto L_0x0017
            kotlin.reflect.jvm.internal.impl.load.kotlin.header.a r0 = r0.b()
            if (r0 == 0) goto L_0x0017
            kotlin.reflect.jvm.internal.impl.load.kotlin.header.a$a r0 = r0.c()
            goto L_0x0018
        L_0x0017:
            r0 = 0
        L_0x0018:
            if (r0 == 0) goto L_0x0093
            int[] r1 = kotlin.reflect.jvm.internal.h.a
            int r2 = r0.ordinal()
            r1 = r1[r2]
            switch(r1) {
                case 1: goto L_0x0073;
                case 2: goto L_0x0073;
                case 3: goto L_0x0073;
                case 4: goto L_0x0053;
                case 5: goto L_0x002b;
                case 6: goto L_0x0093;
                default: goto L_0x0025;
            }
        L_0x0025:
            kotlin.NoWhenBranchMatchedException r1 = new kotlin.NoWhenBranchMatchedException
            r1.<init>()
            throw r1
        L_0x002b:
            kotlin.reflect.jvm.internal.y r1 = new kotlin.reflect.jvm.internal.y
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Unknown class: "
            r2.append(r3)
            java.lang.Class r3 = r4.b()
            r2.append(r3)
            java.lang.String r3 = " (kind = "
            r2.append(r3)
            r2.append(r0)
            r3 = 41
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0053:
            java.lang.UnsupportedOperationException r1 = new java.lang.UnsupportedOperationException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "This class is an internal synthetic class generated by the Kotlin compiler, such as an anonymous class for a lambda, a SAM wrapper, a callable reference, etc. It's not a Kotlin class or interface, so the reflection "
            r2.append(r3)
            java.lang.String r3 = "library has no idea what declarations does it have. Please use Java reflection to inspect this class: "
            r2.append(r3)
            java.lang.Class r3 = r4.b()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0073:
            java.lang.UnsupportedOperationException r1 = new java.lang.UnsupportedOperationException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Packages and file facades are not yet supported in Kotlin reflection. "
            r2.append(r3)
            java.lang.String r3 = "Meanwhile please use Java reflection to inspect this class: "
            r2.append(r3)
            java.lang.Class r3 = r4.b()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0093:
            kotlin.reflect.jvm.internal.y r1 = new kotlin.reflect.jvm.internal.y
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Unresolved class: "
            r2.append(r3)
            java.lang.Class r3 = r4.b()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.g.O():java.lang.Void");
    }
}
