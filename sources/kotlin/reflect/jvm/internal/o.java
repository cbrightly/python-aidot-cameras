package kotlin.reflect.jvm.internal;

import java.util.Collection;
import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.u;
import kotlin.n;
import kotlin.reflect.e;
import kotlin.reflect.jvm.internal.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.f;
import kotlin.reflect.jvm.internal.impl.incremental.components.d;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.g;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.i;
import kotlin.reflect.jvm.internal.impl.metadata.l;
import kotlin.reflect.jvm.internal.impl.metadata.t;
import kotlin.reflect.jvm.internal.impl.protobuf.h;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.x;
import kotlin.reflect.jvm.internal.j;
import kotlin.s;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KPackageImpl.kt */
public final class o extends j {
    private final a0.b<a> q;
    @NotNull
    private final Class<?> x;
    @Nullable
    private final String y;

    public o(@NotNull Class<?> jClass, @Nullable String usageModuleName) {
        k.f(jClass, "jClass");
        this.x = jClass;
        this.y = usageModuleName;
        a0.b<a> b2 = a0.b(new b(this));
        k.b(b2, "ReflectProperties.lazy { Data() }");
        this.q = b2;
    }

    @NotNull
    public Class<?> b() {
        return this.x;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ o(Class cls, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(cls, (i & 2) != 0 ? null : str);
    }

    /* compiled from: KPackageImpl.kt */
    public final class a extends j.b {
        static final /* synthetic */ kotlin.reflect.k[] d;
        private final a0.a e = a0.d(new C0435a(this));
        @NotNull
        private final a0.a f = a0.d(new e(this));
        @Nullable
        private final a0.b g = a0.b(new d(this));
        @Nullable
        private final a0.b h = a0.b(new c(this));
        @NotNull
        private final a0.a i = a0.d(new b(this));

        static {
            Class<a> cls = a.class;
            d = new kotlin.reflect.k[]{kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "kotlinClass", "getKotlinClass()Lorg/jetbrains/kotlin/descriptors/runtime/components/ReflectKotlinClass;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "scope", "getScope()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "multifileFacade", "getMultifileFacade()Ljava/lang/Class;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "metadata", "getMetadata()Lkotlin/Triple;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "members", "getMembers()Ljava/util/Collection;"))};
        }

        /* access modifiers changed from: private */
        public final f c() {
            return (f) this.e.b(this, d[0]);
        }

        @NotNull
        public final Collection<e<?>> d() {
            return (Collection) this.i.b(this, d[4]);
        }

        @Nullable
        public final s<g, l, kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.f> e() {
            return (s) this.h.b(this, d[3]);
        }

        @Nullable
        public final Class<?> f() {
            return (Class) this.g.b(this, d[2]);
        }

        @NotNull
        public final h g() {
            return (h) this.f.b(this, d[1]);
        }

        public a() {
            super();
        }

        /* renamed from: kotlin.reflect.jvm.internal.o$a$a  reason: collision with other inner class name */
        /* compiled from: KPackageImpl.kt */
        public static final class C0435a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<f> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0435a(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @Nullable
            public final f invoke() {
                return f.a.a(o.this.b());
            }
        }

        /* compiled from: KPackageImpl.kt */
        public static final class e extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<h> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            e(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final h invoke() {
                f klass = this.this$0.c();
                if (klass != null) {
                    return this.this$0.a().c().a(klass);
                }
                return h.b.b;
            }
        }

        /* compiled from: KPackageImpl.kt */
        public static final class d extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Class<?>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            d(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            /* JADX WARNING: Code restructure failed: missing block: B:2:0x0009, code lost:
                r0 = r0.b();
             */
            @org.jetbrains.annotations.Nullable
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Class<?> invoke() {
                /*
                    r8 = this;
                    kotlin.reflect.jvm.internal.o$a r0 = r8.this$0
                    kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.f r0 = r0.c()
                    r1 = 0
                    if (r0 == 0) goto L_0x0014
                    kotlin.reflect.jvm.internal.impl.load.kotlin.header.a r0 = r0.b()
                    if (r0 == 0) goto L_0x0014
                    java.lang.String r0 = r0.e()
                    goto L_0x0015
                L_0x0014:
                    r0 = r1
                L_0x0015:
                    if (r0 == 0) goto L_0x003f
                    int r2 = r0.length()
                    if (r2 <= 0) goto L_0x001f
                    r2 = 1
                    goto L_0x0020
                L_0x001f:
                    r2 = 0
                L_0x0020:
                    if (r2 == 0) goto L_0x003f
                    kotlin.reflect.jvm.internal.o$a r1 = r8.this$0
                    kotlin.reflect.jvm.internal.o r1 = kotlin.reflect.jvm.internal.o.this
                    java.lang.Class r1 = r1.b()
                    java.lang.ClassLoader r1 = r1.getClassLoader()
                    r3 = 47
                    r4 = 46
                    r5 = 0
                    r6 = 4
                    r7 = 0
                    r2 = r0
                    java.lang.String r2 = kotlin.text.w.G(r2, r3, r4, r5, r6, r7)
                    java.lang.Class r1 = r1.loadClass(r2)
                    goto L_0x0040
                L_0x003f:
                L_0x0040:
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.o.a.d.invoke():java.lang.Class");
            }
        }

        /* compiled from: KPackageImpl.kt */
        public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<s<? extends g, ? extends l, ? extends kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.f>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            c(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @Nullable
            public final s<g, l, kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.f> invoke() {
                kotlin.reflect.jvm.internal.impl.load.kotlin.header.a header;
                f b = this.this$0.c();
                if (b == null || (header = b.b()) == null) {
                    return null;
                }
                String[] data = header.a();
                String[] strings = header.g();
                if (data == null || strings == null) {
                    return null;
                }
                n<g, l> m = i.m(data, strings);
                return new s<>(m.component1(), m.component2(), header.d());
            }
        }

        /* compiled from: KPackageImpl.kt */
        public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Collection<? extends e<?>>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final Collection<e<?>> invoke() {
                a aVar = this.this$0;
                return o.this.y(aVar.g(), j.c.DECLARED);
            }
        }
    }

    /* compiled from: KPackageImpl.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<a> {
        final /* synthetic */ o this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(o oVar) {
            super(0);
            this.this$0 = oVar;
        }

        @NotNull
        public final a invoke() {
            return new a();
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Class<?> z() {
        Class<?> f = this.q.c().f();
        return f != null ? f : b();
    }

    private final h H() {
        return this.q.c().g();
    }

    @NotNull
    public Collection<kotlin.reflect.b<?>> g() {
        return this.q.c().d();
    }

    @NotNull
    public Collection<kotlin.reflect.jvm.internal.impl.descriptors.l> v() {
        return q.g();
    }

    @NotNull
    public Collection<i0> A(@NotNull kotlin.reflect.jvm.internal.impl.name.f name) {
        k.f(name, "name");
        return H().e(name, d.FROM_REFLECTION);
    }

    @NotNull
    public Collection<kotlin.reflect.jvm.internal.impl.descriptors.u> w(@NotNull kotlin.reflect.jvm.internal.impl.name.f name) {
        k.f(name, "name");
        return H().b(name, d.FROM_REFLECTION);
    }

    @Nullable
    public i0 x(int index) {
        s $dstr$nameResolver$packageProto$metadataVersion = this.q.c().e();
        if ($dstr$nameResolver$packageProto$metadataVersion == null) {
            return null;
        }
        g nameResolver = $dstr$nameResolver$packageProto$metadataVersion.component1();
        l packageProto = $dstr$nameResolver$packageProto$metadataVersion.component2();
        kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.f metadataVersion = $dstr$nameResolver$packageProto$metadataVersion.component3();
        h.f<l, List<kotlin.reflect.jvm.internal.impl.metadata.n>> fVar = kotlin.reflect.jvm.internal.impl.metadata.jvm.a.m;
        k.b(fVar, "JvmProtoBuf.packageLocalVariable");
        kotlin.reflect.jvm.internal.impl.metadata.n proto = (kotlin.reflect.jvm.internal.impl.metadata.n) kotlin.reflect.jvm.internal.impl.metadata.deserialization.f.b(packageProto, fVar, index);
        if (proto == null) {
            return null;
        }
        Class<?> b2 = b();
        t typeTable = packageProto.getTypeTable();
        k.b(typeTable, "packageProto.typeTable");
        return (i0) h0.e(b2, proto, nameResolver, new kotlin.reflect.jvm.internal.impl.metadata.deserialization.h(typeTable), metadataVersion, c.INSTANCE);
    }

    /* compiled from: KPackageImpl.kt */
    public static final /* synthetic */ class c extends kotlin.jvm.internal.h implements p<x, kotlin.reflect.jvm.internal.impl.metadata.n, i0> {
        public static final c INSTANCE = new c();

        c() {
            super(2);
        }

        public final String getName() {
            return "loadProperty";
        }

        public final e getOwner() {
            return kotlin.jvm.internal.a0.b(x.class);
        }

        public final String getSignature() {
            return "loadProperty(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;)Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;";
        }

        @NotNull
        public final i0 invoke(@NotNull x p1, @NotNull kotlin.reflect.jvm.internal.impl.metadata.n p2) {
            k.f(p1, "p1");
            k.f(p2, "p2");
            return p1.p(p2);
        }
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof o) && k.a(b(), ((o) other).b());
    }

    public int hashCode() {
        return b().hashCode();
    }

    @NotNull
    public String toString() {
        return "file class " + kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b.b(b()).b();
    }
}
