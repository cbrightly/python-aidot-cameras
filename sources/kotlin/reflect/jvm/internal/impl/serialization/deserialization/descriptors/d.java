package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.k0;
import kotlin.collections.p0;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.v;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.m0;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.r0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.metadata.c;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.b;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.k;
import kotlin.reflect.jvm.internal.impl.metadata.s;
import kotlin.reflect.jvm.internal.impl.metadata.t;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.j;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.a0;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.c0;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.n;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.x;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.y;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.u0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DeserializedClassDescriptor.kt */
public final class d extends kotlin.reflect.jvm.internal.impl.descriptors.impl.a {
    private final kotlin.reflect.jvm.internal.impl.storage.g<kotlin.reflect.jvm.internal.impl.descriptors.d> A4;
    private final kotlin.reflect.jvm.internal.impl.storage.f<Collection<kotlin.reflect.jvm.internal.impl.descriptors.d>> B4;
    private final kotlin.reflect.jvm.internal.impl.storage.g<kotlin.reflect.jvm.internal.impl.descriptors.e> C4;
    private final kotlin.reflect.jvm.internal.impl.storage.f<Collection<kotlin.reflect.jvm.internal.impl.descriptors.e>> D4;
    @NotNull
    private final a0.a E4;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.descriptors.annotations.g F4;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.metadata.c G4;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.metadata.deserialization.a H4;
    private final o0 I4;
    private final kotlin.reflect.jvm.internal.impl.descriptors.f a1;
    private final kotlin.reflect.jvm.internal.impl.resolve.scopes.i a2;
    private final a1 p0;
    @NotNull
    private final n p1;
    /* access modifiers changed from: private */
    public final b p2;
    private final m0<a> p3;
    /* access modifiers changed from: private */
    public final c p4;
    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.name.a y;
    private final w z;
    private final m z4;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public d(@NotNull n outerContext, @NotNull kotlin.reflect.jvm.internal.impl.metadata.c classProto, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.a metadataVersion, @NotNull o0 sourceElement) {
        super(outerContext.h(), y.a(nameResolver, classProto.getFqName()).j());
        kotlin.reflect.jvm.internal.impl.descriptors.annotations.g gVar;
        k.f(outerContext, "outerContext");
        k.f(classProto, "classProto");
        k.f(nameResolver, "nameResolver");
        k.f(metadataVersion, "metadataVersion");
        k.f(sourceElement, "sourceElement");
        this.G4 = classProto;
        this.H4 = metadataVersion;
        this.I4 = sourceElement;
        this.y = y.a(nameResolver, classProto.getFqName());
        c0 c0Var = c0.a;
        this.z = c0Var.c(kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.d.d(classProto.getFlags()));
        this.p0 = c0Var.f(kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.c.d(classProto.getFlags()));
        kotlin.reflect.jvm.internal.impl.descriptors.f a3 = c0Var.a(kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.e.d(classProto.getFlags()));
        this.a1 = a3;
        List<s> typeParameterList = classProto.getTypeParameterList();
        k.b(typeParameterList, "classProto.typeParameterList");
        t typeTable = classProto.getTypeTable();
        k.b(typeTable, "classProto.typeTable");
        kotlin.reflect.jvm.internal.impl.metadata.deserialization.h hVar = new kotlin.reflect.jvm.internal.impl.metadata.deserialization.h(typeTable);
        k.a aVar = kotlin.reflect.jvm.internal.impl.metadata.deserialization.k.b;
        kotlin.reflect.jvm.internal.impl.metadata.w versionRequirementTable = classProto.getVersionRequirementTable();
        kotlin.jvm.internal.k.b(versionRequirementTable, "classProto.versionRequirementTable");
        n a4 = outerContext.a(this, typeParameterList, nameResolver, hVar, aVar.a(versionRequirementTable), metadataVersion);
        this.p1 = a4;
        kotlin.reflect.jvm.internal.impl.descriptors.f fVar = kotlin.reflect.jvm.internal.impl.descriptors.f.ENUM_CLASS;
        this.a2 = a3 == fVar ? new kotlin.reflect.jvm.internal.impl.resolve.scopes.k(a4.h(), this) : h.b.b;
        this.p2 = new b();
        this.p3 = m0.b.a(this, a4.h(), a4.c().m().c(), new g(this));
        a0.a aVar2 = null;
        this.p4 = a3 == fVar ? new c() : null;
        m e2 = outerContext.e();
        this.z4 = e2;
        this.A4 = a4.h().e(new h(this));
        this.B4 = a4.h().c(new f(this));
        this.C4 = a4.h().e(new e(this));
        this.D4 = a4.h().c(new i(this));
        kotlin.reflect.jvm.internal.impl.metadata.deserialization.c g2 = a4.g();
        kotlin.reflect.jvm.internal.impl.metadata.deserialization.h j = a4.j();
        d dVar = (d) (!(e2 instanceof d) ? null : e2);
        this.E4 = new a0.a(classProto, g2, j, sourceElement, dVar != null ? dVar.E4 : aVar2);
        if (!kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.b.d(classProto.getFlags()).booleanValue()) {
            gVar = kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b.b();
        } else {
            gVar = new m(a4.h(), new C0418d(this));
        }
        this.F4 = gVar;
    }

    @NotNull
    public final kotlin.reflect.jvm.internal.impl.metadata.c Q0() {
        return this.G4;
    }

    @NotNull
    public final kotlin.reflect.jvm.internal.impl.metadata.deserialization.a S0() {
        return this.H4;
    }

    @NotNull
    public final n P0() {
        return this.p1;
    }

    /* compiled from: DeserializedClassDescriptor.kt */
    public static final /* synthetic */ class g extends kotlin.jvm.internal.h implements l<kotlin.reflect.jvm.internal.impl.types.checker.i, a> {
        g(d dVar) {
            super(1, dVar);
        }

        public final String getName() {
            return "<init>";
        }

        public final kotlin.reflect.e getOwner() {
            return kotlin.jvm.internal.a0.b(a.class);
        }

        public final String getSignature() {
            return "<init>(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedClassDescriptor;Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;)V";
        }

        @NotNull
        public final a invoke(@NotNull kotlin.reflect.jvm.internal.impl.types.checker.i p1) {
            kotlin.jvm.internal.k.f(p1, "p1");
            return new a((d) this.receiver, p1);
        }
    }

    private final a R0() {
        return this.p3.c(this.p1.c().m().c());
    }

    /* compiled from: DeserializedClassDescriptor.kt */
    public static final class h extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<kotlin.reflect.jvm.internal.impl.descriptors.d> {
        final /* synthetic */ d this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        h(d dVar) {
            super(0);
            this.this$0 = dVar;
        }

        @Nullable
        public final kotlin.reflect.jvm.internal.impl.descriptors.d invoke() {
            return this.this$0.M0();
        }
    }

    /* compiled from: DeserializedClassDescriptor.kt */
    public static final class f extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Collection<? extends kotlin.reflect.jvm.internal.impl.descriptors.d>> {
        final /* synthetic */ d this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(d dVar) {
            super(0);
            this.this$0 = dVar;
        }

        @NotNull
        public final Collection<kotlin.reflect.jvm.internal.impl.descriptors.d> invoke() {
            return this.this$0.L0();
        }
    }

    /* compiled from: DeserializedClassDescriptor.kt */
    public static final class e extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<kotlin.reflect.jvm.internal.impl.descriptors.e> {
        final /* synthetic */ d this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(d dVar) {
            super(0);
            this.this$0 = dVar;
        }

        @Nullable
        public final kotlin.reflect.jvm.internal.impl.descriptors.e invoke() {
            return this.this$0.K0();
        }
    }

    /* compiled from: DeserializedClassDescriptor.kt */
    public static final class i extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Collection<? extends kotlin.reflect.jvm.internal.impl.descriptors.e>> {
        final /* synthetic */ d this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        i(d dVar) {
            super(0);
            this.this$0 = dVar;
        }

        @NotNull
        public final Collection<kotlin.reflect.jvm.internal.impl.descriptors.e> invoke() {
            return this.this$0.O0();
        }
    }

    @NotNull
    public final a0.a U0() {
        return this.E4;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.annotations.g getAnnotations() {
        return this.F4;
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d$d  reason: collision with other inner class name */
    /* compiled from: DeserializedClassDescriptor.kt */
    public static final class C0418d extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends kotlin.reflect.jvm.internal.impl.descriptors.annotations.c>> {
        final /* synthetic */ d this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0418d(d dVar) {
            super(0);
            this.this$0 = dVar;
        }

        @NotNull
        public final List<kotlin.reflect.jvm.internal.impl.descriptors.annotations.c> invoke() {
            return kotlin.collections.y.C0(this.this$0.P0().c().d().b(this.this$0.U0()));
        }
    }

    @NotNull
    public m b() {
        return this.z4;
    }

    @NotNull
    public u0 i() {
        return this.p2;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.f h() {
        return this.a1;
    }

    @NotNull
    public w p() {
        return this.z;
    }

    @NotNull
    public a1 getVisibility() {
        return this.p0;
    }

    public boolean x() {
        Boolean g2 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.f.d(this.G4.getFlags());
        kotlin.jvm.internal.k.b(g2, "Flags.IS_INNER.get(classProto.flags)");
        return g2.booleanValue();
    }

    public boolean D0() {
        Boolean g2 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.g.d(this.G4.getFlags());
        kotlin.jvm.internal.k.b(g2, "Flags.IS_DATA.get(classProto.flags)");
        return g2.booleanValue();
    }

    public boolean isInline() {
        Boolean g2 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.j.d(this.G4.getFlags());
        kotlin.jvm.internal.k.b(g2, "Flags.IS_INLINE_CLASS.get(classProto.flags)");
        return g2.booleanValue();
    }

    public boolean d0() {
        Boolean g2 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.i.d(this.G4.getFlags());
        kotlin.jvm.internal.k.b(g2, "Flags.IS_EXPECT_CLASS.get(classProto.flags)");
        return g2.booleanValue();
    }

    public boolean S() {
        return false;
    }

    public boolean isExternal() {
        Boolean g2 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.h.d(this.G4.getFlags());
        kotlin.jvm.internal.k.b(g2, "Flags.IS_EXTERNAL_CLASS.get(classProto.flags)");
        return g2.booleanValue();
    }

    /* access modifiers changed from: protected */
    @NotNull
    public kotlin.reflect.jvm.internal.impl.resolve.scopes.h a0(@NotNull kotlin.reflect.jvm.internal.impl.types.checker.i kotlinTypeRefiner) {
        kotlin.jvm.internal.k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this.p3.c(kotlinTypeRefiner);
    }

    @NotNull
    /* renamed from: T0 */
    public kotlin.reflect.jvm.internal.impl.resolve.scopes.i g0() {
        return this.a2;
    }

    public boolean V() {
        return kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.e.d(this.G4.getFlags()) == c.C0384c.COMPANION_OBJECT;
    }

    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.descriptors.d M0() {
        Object element$iv;
        if (this.a1.isSingleton()) {
            kotlin.reflect.jvm.internal.impl.descriptors.impl.f $this$apply = kotlin.reflect.jvm.internal.impl.resolve.b.i(this, o0.a);
            $this$apply.Y0(m());
            return $this$apply;
        }
        Iterable $this$firstOrNull$iv = this.G4.getConstructorList();
        kotlin.jvm.internal.k.b($this$firstOrNull$iv, "classProto.constructorList");
        Iterator<T> it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it.next();
            kotlin.reflect.jvm.internal.impl.metadata.d it2 = (kotlin.reflect.jvm.internal.impl.metadata.d) element$iv;
            b.C0386b bVar = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.k;
            kotlin.jvm.internal.k.b(it2, "it");
            if (!bVar.d(it2.getFlags()).booleanValue()) {
                break;
            }
        }
        kotlin.reflect.jvm.internal.impl.metadata.d constructorProto = (kotlin.reflect.jvm.internal.impl.metadata.d) element$iv;
        if (constructorProto == null) {
            return null;
        }
        return this.p1.f().m(constructorProto, true);
    }

    @Nullable
    public kotlin.reflect.jvm.internal.impl.descriptors.d B() {
        return (kotlin.reflect.jvm.internal.impl.descriptors.d) this.A4.invoke();
    }

    /* access modifiers changed from: private */
    public final Collection<kotlin.reflect.jvm.internal.impl.descriptors.d> L0() {
        return kotlin.collections.y.n0(kotlin.collections.y.n0(N0(), q.k(B())), this.p1.c().c().c(this));
    }

    private final List<kotlin.reflect.jvm.internal.impl.descriptors.d> N0() {
        Iterable $this$filterTo$iv$iv = this.G4.getConstructorList();
        kotlin.jvm.internal.k.b($this$filterTo$iv$iv, "classProto.constructorList");
        ArrayList arrayList = new ArrayList();
        for (T next : $this$filterTo$iv$iv) {
            kotlin.reflect.jvm.internal.impl.metadata.d it = (kotlin.reflect.jvm.internal.impl.metadata.d) next;
            b.C0386b bVar = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.k;
            kotlin.jvm.internal.k.b(it, "it");
            Boolean g2 = bVar.d(it.getFlags());
            kotlin.jvm.internal.k.b(g2, "Flags.IS_SECONDARY.get(it.flags)");
            if (g2.booleanValue()) {
                arrayList.add(next);
            }
        }
        Iterable<kotlin.reflect.jvm.internal.impl.metadata.d> $this$mapTo$iv$iv = arrayList;
        ArrayList arrayList2 = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (kotlin.reflect.jvm.internal.impl.metadata.d it2 : $this$mapTo$iv$iv) {
            x f2 = this.p1.f();
            kotlin.jvm.internal.k.b(it2, "it");
            arrayList2.add(f2.m(it2, false));
        }
        return arrayList2;
    }

    @NotNull
    public Collection<kotlin.reflect.jvm.internal.impl.descriptors.d> f() {
        return (Collection) this.B4.invoke();
    }

    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.descriptors.e K0() {
        kotlin.reflect.jvm.internal.impl.descriptors.e eVar = null;
        if (!this.G4.hasCompanionObjectName()) {
            return null;
        }
        kotlin.reflect.jvm.internal.impl.descriptors.h c2 = R0().c(y.b(this.p1.g(), this.G4.getCompanionObjectName()), kotlin.reflect.jvm.internal.impl.incremental.components.d.FROM_DESERIALIZATION);
        if (c2 instanceof kotlin.reflect.jvm.internal.impl.descriptors.e) {
            eVar = c2;
        }
        return eVar;
    }

    @Nullable
    public kotlin.reflect.jvm.internal.impl.descriptors.e h0() {
        return (kotlin.reflect.jvm.internal.impl.descriptors.e) this.C4.invoke();
    }

    public final boolean V0(@NotNull kotlin.reflect.jvm.internal.impl.name.f name) {
        kotlin.jvm.internal.k.f(name, "name");
        return R0().x().contains(name);
    }

    /* access modifiers changed from: private */
    public final Collection<kotlin.reflect.jvm.internal.impl.descriptors.e> O0() {
        d dVar = this;
        if (dVar.z != w.SEALED) {
            return q.g();
        }
        List<Integer> fqNames = dVar.G4.getSealedSubclassFqNameList();
        kotlin.jvm.internal.k.b(fqNames, "fqNames");
        if (!(!fqNames.isEmpty())) {
            return kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.a(this);
        }
        Collection destination$iv$iv = new ArrayList();
        for (Integer index : fqNames) {
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.l c2 = dVar.p1.c();
            List fqNames2 = fqNames;
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.c g2 = dVar.p1.g();
            kotlin.jvm.internal.k.b(index, FirebaseAnalytics.Param.INDEX);
            Object it$iv$iv = c2.b(y.a(g2, index.intValue()));
            if (it$iv$iv != null) {
                destination$iv$iv.add(it$iv$iv);
            }
            dVar = this;
            fqNames = fqNames2;
        }
        return destination$iv$iv;
    }

    @NotNull
    public Collection<kotlin.reflect.jvm.internal.impl.descriptors.e> v() {
        return (Collection) this.D4.invoke();
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("deserialized ");
        sb.append(d0() ? "expect" : "");
        sb.append(" class ");
        sb.append(getName());
        return sb.toString();
    }

    @NotNull
    public o0 n() {
        return this.I4;
    }

    @NotNull
    public List<t0> o() {
        return this.p1.i().k();
    }

    /* compiled from: DeserializedClassDescriptor.kt */
    public final class b extends kotlin.reflect.jvm.internal.impl.types.b {
        private final kotlin.reflect.jvm.internal.impl.storage.f<List<t0>> c;

        public b() {
            super(d.this.P0().h());
            this.c = d.this.P0().h().c(new a(this));
        }

        /* compiled from: DeserializedClassDescriptor.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends t0>> {
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(b bVar) {
                super(0);
                this.this$0 = bVar;
            }

            @NotNull
            public final List<t0> invoke() {
                return kotlin.reflect.jvm.internal.impl.descriptors.u0.d(d.this);
            }
        }

        /* access modifiers changed from: protected */
        @NotNull
        public Collection<b0> g() {
            String str;
            kotlin.reflect.jvm.internal.impl.name.b b;
            Iterable<kotlin.reflect.jvm.internal.impl.metadata.q> $this$mapTo$iv$iv = kotlin.reflect.jvm.internal.impl.metadata.deserialization.g.k(d.this.Q0(), d.this.P0().j());
            Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (kotlin.reflect.jvm.internal.impl.metadata.q supertypeProto : $this$mapTo$iv$iv) {
                destination$iv$iv.add(d.this.P0().i().n(supertypeProto));
            }
            Iterable n0 = kotlin.collections.y.n0(destination$iv$iv, d.this.P0().c().c().d(d.this));
            Iterable<b0> $this$mapNotNull$iv = n0;
            List arrayList = new ArrayList();
            for (b0 supertype : $this$mapNotNull$iv) {
                Object c2 = supertype.I0().c();
                Iterable $this$mapNotNull$iv2 = $this$mapNotNull$iv;
                if (!(c2 instanceof a0.b)) {
                    c2 = null;
                }
                Object it$iv$iv = (a0.b) c2;
                if (it$iv$iv != null) {
                    arrayList.add(it$iv$iv);
                }
                $this$mapNotNull$iv = $this$mapNotNull$iv2;
            }
            List unresolved = arrayList;
            if (!unresolved.isEmpty()) {
                kotlin.reflect.jvm.internal.impl.serialization.deserialization.r i = d.this.P0().c().i();
                d dVar = d.this;
                Iterable<a0.b> $this$mapTo$iv$iv2 = unresolved;
                ArrayList arrayList2 = new ArrayList(r.r($this$mapTo$iv$iv2, 10));
                for (a0.b it : $this$mapTo$iv$iv2) {
                    kotlin.reflect.jvm.internal.impl.name.a i2 = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.i(it);
                    if (i2 == null || (b = i2.b()) == null || (str = b.b()) == null) {
                        str = it.getName().b();
                    }
                    arrayList2.add(str);
                }
                i.b(dVar, arrayList2);
            }
            return kotlin.collections.y.C0(n0);
        }

        @NotNull
        public List<t0> getParameters() {
            return (List) this.c.invoke();
        }

        public boolean d() {
            return true;
        }

        @NotNull
        /* renamed from: s */
        public d q() {
            return d.this;
        }

        @NotNull
        public String toString() {
            String fVar = d.this.getName().toString();
            kotlin.jvm.internal.k.b(fVar, "name.toString()");
            return fVar;
        }

        /* access modifiers changed from: protected */
        @NotNull
        public r0 k() {
            return r0.a.a;
        }
    }

    /* compiled from: DeserializedClassDescriptor.kt */
    public final class a extends g {
        private final kotlin.reflect.jvm.internal.impl.storage.f<Collection<m>> m;
        private final kotlin.reflect.jvm.internal.impl.storage.f<Collection<b0>> n;
        /* access modifiers changed from: private */
        public final kotlin.reflect.jvm.internal.impl.types.checker.i o;
        final /* synthetic */ d p;

        /* compiled from: DeserializedClassDescriptor.kt */
        public static final class c extends kotlin.jvm.internal.l implements l<n0, Boolean> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            c(a aVar) {
                super(1);
                this.this$0 = aVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Boolean.valueOf(invoke((n0) obj));
            }

            public final boolean invoke(@NotNull n0 it) {
                kotlin.jvm.internal.k.f(it, "it");
                return this.this$0.w().c().s().b(this.this$0.p, it);
            }
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public a(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d r18, kotlin.reflect.jvm.internal.impl.types.checker.i r19) {
            /*
                r17 = this;
                r6 = r17
                r7 = r19
                java.lang.String r0 = "kotlinTypeRefiner"
                kotlin.jvm.internal.k.f(r7, r0)
                r8 = r18
                r6.p = r8
                kotlin.reflect.jvm.internal.impl.serialization.deserialization.n r1 = r18.P0()
                kotlin.reflect.jvm.internal.impl.metadata.c r0 = r18.Q0()
                java.util.List r2 = r0.getFunctionList()
                java.lang.String r0 = "classProto.functionList"
                kotlin.jvm.internal.k.b(r2, r0)
                kotlin.reflect.jvm.internal.impl.metadata.c r0 = r18.Q0()
                java.util.List r3 = r0.getPropertyList()
                java.lang.String r0 = "classProto.propertyList"
                kotlin.jvm.internal.k.b(r3, r0)
                kotlin.reflect.jvm.internal.impl.metadata.c r0 = r18.Q0()
                java.util.List r4 = r0.getTypeAliasList()
                java.lang.String r0 = "classProto.typeAliasList"
                kotlin.jvm.internal.k.b(r4, r0)
                kotlin.reflect.jvm.internal.impl.metadata.c r0 = r18.Q0()
                java.util.List r0 = r0.getNestedClassNameList()
                java.lang.String r5 = "classProto.nestedClassNameList"
                kotlin.jvm.internal.k.b(r0, r5)
                kotlin.reflect.jvm.internal.impl.serialization.deserialization.n r5 = r18.P0()
                kotlin.reflect.jvm.internal.impl.metadata.deserialization.c r5 = r5.g()
                r9 = 0
                java.util.ArrayList r10 = new java.util.ArrayList
                r11 = 10
                int r11 = kotlin.collections.r.r(r0, r11)
                r10.<init>(r11)
                r11 = r0
                r12 = 0
                java.util.Iterator r13 = r11.iterator()
            L_0x005f:
                boolean r14 = r13.hasNext()
                if (r14 == 0) goto L_0x007a
                java.lang.Object r14 = r13.next()
                r15 = r14
                java.lang.Number r15 = (java.lang.Number) r15
                int r15 = r15.intValue()
                r16 = 0
                kotlin.reflect.jvm.internal.impl.name.f r15 = kotlin.reflect.jvm.internal.impl.serialization.deserialization.y.b(r5, r15)
                r10.add(r15)
                goto L_0x005f
            L_0x007a:
                r0 = r10
                r5 = 0
                kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d$a$a r9 = new kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d$a$a
                r9.<init>(r0)
                r0 = r17
                r5 = r9
                r0.<init>(r1, r2, r3, r4, r5)
                r6.o = r7
                kotlin.reflect.jvm.internal.impl.serialization.deserialization.n r0 = r17.w()
                kotlin.reflect.jvm.internal.impl.storage.j r0 = r0.h()
                kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d$a$b r1 = new kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d$a$b
                r1.<init>(r6)
                kotlin.reflect.jvm.internal.impl.storage.f r0 = r0.c(r1)
                r6.m = r0
                kotlin.reflect.jvm.internal.impl.serialization.deserialization.n r0 = r17.w()
                kotlin.reflect.jvm.internal.impl.storage.j r0 = r0.h()
                kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d$a$e r1 = new kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d$a$e
                r1.<init>(r6)
                kotlin.reflect.jvm.internal.impl.storage.f r0 = r0.c(r1)
                r6.n = r0
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d.a.<init>(kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d, kotlin.reflect.jvm.internal.impl.types.checker.i):void");
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d$a$a  reason: collision with other inner class name */
        /* compiled from: DeserializedClassDescriptor.kt */
        public static final class C0415a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends kotlin.reflect.jvm.internal.impl.name.f>> {
            final /* synthetic */ List $it;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0415a(List list) {
                super(0);
                this.$it = list;
            }

            @NotNull
            public final List<kotlin.reflect.jvm.internal.impl.name.f> invoke() {
                return this.$it;
            }
        }

        /* access modifiers changed from: private */
        public final d I() {
            return this.p;
        }

        /* compiled from: DeserializedClassDescriptor.kt */
        public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Collection<? extends m>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final Collection<m> invoke() {
                return this.this$0.o(kotlin.reflect.jvm.internal.impl.resolve.scopes.d.l, kotlin.reflect.jvm.internal.impl.resolve.scopes.h.a.a(), kotlin.reflect.jvm.internal.impl.incremental.components.d.WHEN_GET_ALL_DESCRIPTORS);
            }
        }

        /* compiled from: DeserializedClassDescriptor.kt */
        public static final class e extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Collection<? extends b0>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            e(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final Collection<b0> invoke() {
                return this.this$0.o.f(this.this$0.I());
            }
        }

        @NotNull
        public Collection<m> d(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d kindFilter, @NotNull l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean> nameFilter) {
            kotlin.jvm.internal.k.f(kindFilter, "kindFilter");
            kotlin.jvm.internal.k.f(nameFilter, "nameFilter");
            return (Collection) this.m.invoke();
        }

        @NotNull
        public Collection<n0> b(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
            kotlin.jvm.internal.k.f(name, "name");
            kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
            J(name, location);
            return super.b(name, location);
        }

        @NotNull
        public Collection<i0> e(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
            kotlin.jvm.internal.k.f(name, "name");
            kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
            J(name, location);
            return super.e(name, location);
        }

        /* access modifiers changed from: protected */
        public void q(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull Collection<n0> functions) {
            kotlin.jvm.internal.k.f(name, "name");
            kotlin.jvm.internal.k.f(functions, "functions");
            ArrayList fromSupertypes = new ArrayList();
            for (b0 supertype : (Collection) this.n.invoke()) {
                fromSupertypes.addAll(supertype.l().b(name, kotlin.reflect.jvm.internal.impl.incremental.components.d.FOR_ALREADY_TRACKED));
            }
            v.D(functions, new c(this));
            functions.addAll(w().c().c().a(name, this.p));
            H(name, fromSupertypes, functions);
        }

        /* access modifiers changed from: protected */
        public void r(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull Collection<i0> descriptors) {
            kotlin.jvm.internal.k.f(name, "name");
            kotlin.jvm.internal.k.f(descriptors, "descriptors");
            ArrayList fromSupertypes = new ArrayList();
            for (b0 supertype : (Collection) this.n.invoke()) {
                fromSupertypes.addAll(supertype.l().e(name, kotlin.reflect.jvm.internal.impl.incremental.components.d.FOR_ALREADY_TRACKED));
            }
            H(name, fromSupertypes, descriptors);
        }

        private final <D extends kotlin.reflect.jvm.internal.impl.descriptors.b> void H(kotlin.reflect.jvm.internal.impl.name.f name, Collection<? extends D> fromSupertypes, Collection<D> result) {
            kotlin.reflect.jvm.internal.impl.name.f fVar = name;
            Collection<? extends D> collection = fromSupertypes;
            w().c().m().a().w(fVar, collection, new ArrayList(result), I(), new C0416d(result));
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d$a$d  reason: collision with other inner class name */
        /* compiled from: DeserializedClassDescriptor.kt */
        public static final class C0416d extends kotlin.reflect.jvm.internal.impl.resolve.g {
            final /* synthetic */ Collection a;

            C0416d(Collection $captured_local_variable$0) {
                this.a = $captured_local_variable$0;
            }

            public void a(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b fakeOverride) {
                kotlin.jvm.internal.k.f(fakeOverride, "fakeOverride");
                kotlin.reflect.jvm.internal.impl.resolve.i.L(fakeOverride, (l<kotlin.reflect.jvm.internal.impl.descriptors.b, kotlin.x>) null);
                this.a.add(fakeOverride);
            }

            /* access modifiers changed from: protected */
            public void e(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b fromSuper, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.b fromCurrent) {
                kotlin.jvm.internal.k.f(fromSuper, "fromSuper");
                kotlin.jvm.internal.k.f(fromCurrent, "fromCurrent");
            }
        }

        /* access modifiers changed from: protected */
        @NotNull
        public Set<kotlin.reflect.jvm.internal.impl.name.f> z() {
            Iterable<b0> $this$flatMapTo$iv = I().p2.b();
            LinkedHashSet $this$apply = new LinkedHashSet();
            for (b0 it : $this$flatMapTo$iv) {
                v.x($this$apply, it.l().a());
            }
            $this$apply.addAll(w().c().c().e(this.p));
            return $this$apply;
        }

        /* access modifiers changed from: protected */
        @NotNull
        public Set<kotlin.reflect.jvm.internal.impl.name.f> A() {
            Iterable<b0> $this$flatMapTo$iv = I().p2.b();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (b0 it : $this$flatMapTo$iv) {
                v.x(linkedHashSet, it.l().f());
            }
            return linkedHashSet;
        }

        @Nullable
        public kotlin.reflect.jvm.internal.impl.descriptors.h c(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
            kotlin.reflect.jvm.internal.impl.descriptors.e it;
            kotlin.jvm.internal.k.f(name, "name");
            kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
            J(name, location);
            c I0 = I().p4;
            if (I0 == null || (it = I0.f(name)) == null) {
                return super.c(name, location);
            }
            return it;
        }

        /* access modifiers changed from: protected */
        @NotNull
        public kotlin.reflect.jvm.internal.impl.name.a t(@NotNull kotlin.reflect.jvm.internal.impl.name.f name) {
            kotlin.jvm.internal.k.f(name, "name");
            kotlin.reflect.jvm.internal.impl.name.a d = this.p.y.d(name);
            kotlin.jvm.internal.k.b(d, "classId.createNestedClassId(name)");
            return d;
        }

        /* access modifiers changed from: protected */
        public void m(@NotNull Collection<m> result, @NotNull l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean> nameFilter) {
            kotlin.jvm.internal.k.f(result, "result");
            kotlin.jvm.internal.k.f(nameFilter, "nameFilter");
            c I0 = I().p4;
            Collection d = I0 != null ? I0.d() : null;
            if (d == null) {
                d = q.g();
            }
            result.addAll(d);
        }

        public void J(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
            kotlin.jvm.internal.k.f(name, "name");
            kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
            kotlin.reflect.jvm.internal.impl.incremental.a.a(w().c().o(), location, I(), name);
        }
    }

    /* compiled from: DeserializedClassDescriptor.kt */
    public final class c {
        /* access modifiers changed from: private */
        public final Map<kotlin.reflect.jvm.internal.impl.name.f, kotlin.reflect.jvm.internal.impl.metadata.g> a;
        private final kotlin.reflect.jvm.internal.impl.storage.d<kotlin.reflect.jvm.internal.impl.name.f, kotlin.reflect.jvm.internal.impl.descriptors.e> b;
        /* access modifiers changed from: private */
        public final kotlin.reflect.jvm.internal.impl.storage.f<Set<kotlin.reflect.jvm.internal.impl.name.f>> c;

        public c() {
            Iterable $this$associateByTo$iv$iv = d.this.Q0().getEnumEntryList();
            kotlin.jvm.internal.k.b($this$associateByTo$iv$iv, "classProto.enumEntryList");
            Map destination$iv$iv = new LinkedHashMap(kotlin.ranges.n.b(k0.b(r.r($this$associateByTo$iv$iv, 10)), 16));
            for (T next : $this$associateByTo$iv$iv) {
                kotlin.reflect.jvm.internal.impl.metadata.g it = (kotlin.reflect.jvm.internal.impl.metadata.g) next;
                kotlin.reflect.jvm.internal.impl.metadata.deserialization.c g = d.this.P0().g();
                kotlin.jvm.internal.k.b(it, "it");
                destination$iv$iv.put(y.b(g, it.getName()), next);
            }
            this.a = destination$iv$iv;
            this.b = d.this.P0().h().g(new a(this));
            this.c = d.this.P0().h().c(new b(this));
        }

        /* compiled from: DeserializedClassDescriptor.kt */
        public static final class a extends kotlin.jvm.internal.l implements l<kotlin.reflect.jvm.internal.impl.name.f, kotlin.reflect.jvm.internal.impl.descriptors.impl.n> {
            final /* synthetic */ c this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(c cVar) {
                super(1);
                this.this$0 = cVar;
            }

            @Nullable
            public final kotlin.reflect.jvm.internal.impl.descriptors.impl.n invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.f name) {
                kotlin.jvm.internal.k.f(name, "name");
                kotlin.reflect.jvm.internal.impl.metadata.g proto = (kotlin.reflect.jvm.internal.impl.metadata.g) this.this$0.a.get(name);
                if (proto == null) {
                    return null;
                }
                j h = d.this.P0().h();
                c cVar = this.this$0;
                return kotlin.reflect.jvm.internal.impl.descriptors.impl.n.A0(h, d.this, name, cVar.c, new a(d.this.P0().h(), new C0417a(proto, this, name)), o0.a);
            }

            /* renamed from: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d$c$a$a  reason: collision with other inner class name */
            /* compiled from: DeserializedClassDescriptor.kt */
            public static final class C0417a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends kotlin.reflect.jvm.internal.impl.descriptors.annotations.c>> {
                final /* synthetic */ kotlin.reflect.jvm.internal.impl.name.f $name$inlined;
                final /* synthetic */ kotlin.reflect.jvm.internal.impl.metadata.g $proto;
                final /* synthetic */ a this$0;

                /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                C0417a(kotlin.reflect.jvm.internal.impl.metadata.g gVar, a aVar, kotlin.reflect.jvm.internal.impl.name.f fVar) {
                    super(0);
                    this.$proto = gVar;
                    this.this$0 = aVar;
                    this.$name$inlined = fVar;
                }

                @NotNull
                public final List<kotlin.reflect.jvm.internal.impl.descriptors.annotations.c> invoke() {
                    return kotlin.collections.y.C0(d.this.P0().c().d().d(d.this.U0(), this.$proto));
                }
            }
        }

        /* compiled from: DeserializedClassDescriptor.kt */
        public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Set<? extends kotlin.reflect.jvm.internal.impl.name.f>> {
            final /* synthetic */ c this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(c cVar) {
                super(0);
                this.this$0 = cVar;
            }

            @NotNull
            public final Set<kotlin.reflect.jvm.internal.impl.name.f> invoke() {
                return this.this$0.e();
            }
        }

        @Nullable
        public final kotlin.reflect.jvm.internal.impl.descriptors.e f(@NotNull kotlin.reflect.jvm.internal.impl.name.f name) {
            kotlin.jvm.internal.k.f(name, "name");
            return this.b.invoke(name);
        }

        /* access modifiers changed from: private */
        public final Set<kotlin.reflect.jvm.internal.impl.name.f> e() {
            HashSet result = new HashSet();
            for (b0 supertype : d.this.i().b()) {
                for (m descriptor : j.a.a(supertype.l(), (kotlin.reflect.jvm.internal.impl.resolve.scopes.d) null, (l) null, 3, (Object) null)) {
                    if ((descriptor instanceof n0) || (descriptor instanceof i0)) {
                        result.add(descriptor.getName());
                    }
                }
            }
            Iterable<kotlin.reflect.jvm.internal.impl.metadata.i> $this$mapTo$iv = d.this.Q0().getFunctionList();
            kotlin.jvm.internal.k.b($this$mapTo$iv, "classProto.functionList");
            for (kotlin.reflect.jvm.internal.impl.metadata.i it : $this$mapTo$iv) {
                kotlin.reflect.jvm.internal.impl.metadata.deserialization.c g = d.this.P0().g();
                kotlin.jvm.internal.k.b(it, "it");
                result.add(y.b(g, it.getName()));
            }
            Iterable<kotlin.reflect.jvm.internal.impl.metadata.n> $this$mapTo$iv2 = d.this.Q0().getPropertyList();
            kotlin.jvm.internal.k.b($this$mapTo$iv2, "classProto.propertyList");
            for (kotlin.reflect.jvm.internal.impl.metadata.n it2 : $this$mapTo$iv2) {
                kotlin.reflect.jvm.internal.impl.metadata.deserialization.c g2 = d.this.P0().g();
                kotlin.jvm.internal.k.b(it2, "it");
                result.add(y.b(g2, it2.getName()));
            }
            return p0.i(result, result);
        }

        @NotNull
        public final Collection<kotlin.reflect.jvm.internal.impl.descriptors.e> d() {
            Iterable<kotlin.reflect.jvm.internal.impl.name.f> $this$mapNotNullTo$iv$iv = this.a.keySet();
            Collection destination$iv$iv = new ArrayList();
            for (kotlin.reflect.jvm.internal.impl.name.f name : $this$mapNotNullTo$iv$iv) {
                Object it$iv$iv = f(name);
                if (it$iv$iv != null) {
                    destination$iv$iv.add(it$iv$iv);
                }
            }
            return destination$iv$iv;
        }
    }
}
