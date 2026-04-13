package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.k0;
import kotlin.collections.l0;
import kotlin.collections.p0;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.u;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.descriptors.s0;
import kotlin.reflect.jvm.internal.impl.metadata.r;
import kotlin.reflect.jvm.internal.impl.protobuf.o;
import kotlin.reflect.jvm.internal.impl.protobuf.q;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.d;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.i;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.n;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.y;
import kotlin.reflect.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DeserializedMemberScope.kt */
public abstract class g extends i {
    static final /* synthetic */ k[] b;
    /* access modifiers changed from: private */
    public final Map<kotlin.reflect.jvm.internal.impl.name.f, byte[]> c;
    /* access modifiers changed from: private */
    public final Map<kotlin.reflect.jvm.internal.impl.name.f, byte[]> d;
    private final Map<kotlin.reflect.jvm.internal.impl.name.f, byte[]> e;
    private final kotlin.reflect.jvm.internal.impl.storage.c<kotlin.reflect.jvm.internal.impl.name.f, Collection<n0>> f;
    private final kotlin.reflect.jvm.internal.impl.storage.c<kotlin.reflect.jvm.internal.impl.name.f, Collection<i0>> g;
    private final kotlin.reflect.jvm.internal.impl.storage.d<kotlin.reflect.jvm.internal.impl.name.f, s0> h;
    private final kotlin.reflect.jvm.internal.impl.storage.f i;
    private final kotlin.reflect.jvm.internal.impl.storage.f j;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.storage.f k;
    /* access modifiers changed from: private */
    @NotNull
    public final n l;

    static {
        Class<g> cls = g.class;
        b = new k[]{a0.h(new u(a0.b(cls), "functionNamesLazy", "getFunctionNamesLazy()Ljava/util/Set;")), a0.h(new u(a0.b(cls), "variableNamesLazy", "getVariableNamesLazy()Ljava/util/Set;")), a0.h(new u(a0.b(cls), "classNames", "getClassNames$deserialization()Ljava/util/Set;"))};
    }

    private final Set<kotlin.reflect.jvm.internal.impl.name.f> C() {
        return (Set) kotlin.reflect.jvm.internal.impl.storage.i.a(this.j, this, b[1]);
    }

    private final Set<kotlin.reflect.jvm.internal.impl.name.f> y() {
        return (Set) kotlin.reflect.jvm.internal.impl.storage.i.a(this.i, this, b[0]);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public abstract Set<kotlin.reflect.jvm.internal.impl.name.f> A();

    /* access modifiers changed from: protected */
    public abstract void m(@NotNull Collection<m> collection, @NotNull l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean> lVar);

    /* access modifiers changed from: protected */
    @NotNull
    public abstract kotlin.reflect.jvm.internal.impl.name.a t(@NotNull kotlin.reflect.jvm.internal.impl.name.f fVar);

    @NotNull
    public final Set<kotlin.reflect.jvm.internal.impl.name.f> x() {
        return (Set) kotlin.reflect.jvm.internal.impl.storage.i.a(this.k, this, b[2]);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public abstract Set<kotlin.reflect.jvm.internal.impl.name.f> z();

    protected g(@NotNull n c2, @NotNull Collection<kotlin.reflect.jvm.internal.impl.metadata.i> functionList, @NotNull Collection<kotlin.reflect.jvm.internal.impl.metadata.n> propertyList, @NotNull Collection<r> typeAliasList, @NotNull kotlin.jvm.functions.a<? extends Collection<kotlin.reflect.jvm.internal.impl.name.f>> classNames) {
        Map<kotlin.reflect.jvm.internal.impl.name.f, byte[]> map;
        int $i$f$groupByName;
        int $i$f$groupByName2;
        n nVar = c2;
        kotlin.jvm.functions.a<? extends Collection<kotlin.reflect.jvm.internal.impl.name.f>> aVar = classNames;
        kotlin.jvm.internal.k.f(nVar, "c");
        kotlin.jvm.internal.k.f(functionList, "functionList");
        kotlin.jvm.internal.k.f(propertyList, "propertyList");
        kotlin.jvm.internal.k.f(typeAliasList, "typeAliasList");
        kotlin.jvm.internal.k.f(aVar, "classNames");
        this.l = nVar;
        Collection $this$groupByName$iv = functionList;
        Map linkedHashMap = new LinkedHashMap();
        for (T next : $this$groupByName$iv) {
            Collection $this$groupByName$iv2 = $this$groupByName$iv;
            Object key$iv$iv$iv = y.b(this.l.g(), ((kotlin.reflect.jvm.internal.impl.metadata.i) ((o) next)).getName());
            Map $this$getOrPut$iv$iv$iv$iv = linkedHashMap;
            Object value$iv$iv$iv$iv = $this$getOrPut$iv$iv$iv$iv.get(key$iv$iv$iv);
            if (value$iv$iv$iv$iv == null) {
                Object answer$iv$iv$iv$iv = new ArrayList();
                $this$getOrPut$iv$iv$iv$iv.put(key$iv$iv$iv, answer$iv$iv$iv$iv);
                value$iv$iv$iv$iv = answer$iv$iv$iv$iv;
            }
            ((List) value$iv$iv$iv$iv).add(next);
            Object key$iv$iv$iv2 = c2;
            Collection<kotlin.reflect.jvm.internal.impl.metadata.i> collection = functionList;
            $this$groupByName$iv = $this$groupByName$iv2;
        }
        this.c = E(linkedHashMap);
        Object key$iv$iv$iv3 = propertyList;
        g this_$iv = this;
        int $i$f$groupByName3 = false;
        Map linkedHashMap2 = new LinkedHashMap();
        for (T next2 : key$iv$iv$iv3) {
            Object $this$groupByName$iv3 = key$iv$iv$iv3;
            g this_$iv2 = this_$iv;
            Object key$iv$iv$iv4 = y.b(this_$iv.l.g(), ((kotlin.reflect.jvm.internal.impl.metadata.n) ((o) next2)).getName());
            Map $this$getOrPut$iv$iv$iv$iv2 = linkedHashMap2;
            Object value$iv$iv$iv$iv2 = $this$getOrPut$iv$iv$iv$iv2.get(key$iv$iv$iv4);
            if (value$iv$iv$iv$iv2 == null) {
                $i$f$groupByName2 = $i$f$groupByName3;
                Object answer$iv$iv$iv$iv2 = new ArrayList();
                $this$getOrPut$iv$iv$iv$iv2.put(key$iv$iv$iv4, answer$iv$iv$iv$iv2);
                value$iv$iv$iv$iv2 = answer$iv$iv$iv$iv2;
            } else {
                $i$f$groupByName2 = $i$f$groupByName3;
            }
            ((List) value$iv$iv$iv$iv2).add(next2);
            key$iv$iv$iv3 = $this$groupByName$iv3;
            $i$f$groupByName3 = $i$f$groupByName2;
            this_$iv = this_$iv2;
        }
        Object $this$groupByName$iv4 = key$iv$iv$iv3;
        g gVar = this_$iv;
        int i2 = $i$f$groupByName3;
        this.d = E(linkedHashMap2);
        if (this.l.c().g().a()) {
            Object key$iv$iv$iv5 = typeAliasList;
            g this_$iv3 = this;
            int $i$f$groupByName4 = false;
            Map linkedHashMap3 = new LinkedHashMap();
            for (T next3 : key$iv$iv$iv5) {
                Object $this$groupByName$iv5 = key$iv$iv$iv5;
                g this_$iv4 = this_$iv3;
                Object key$iv$iv$iv6 = y.b(this_$iv3.l.g(), ((r) ((o) next3)).getName());
                Map $this$getOrPut$iv$iv$iv$iv3 = linkedHashMap3;
                Object value$iv$iv$iv$iv3 = $this$getOrPut$iv$iv$iv$iv3.get(key$iv$iv$iv6);
                if (value$iv$iv$iv$iv3 == null) {
                    $i$f$groupByName = $i$f$groupByName4;
                    Object answer$iv$iv$iv$iv3 = new ArrayList();
                    $this$getOrPut$iv$iv$iv$iv3.put(key$iv$iv$iv6, answer$iv$iv$iv$iv3);
                    value$iv$iv$iv$iv3 = answer$iv$iv$iv$iv3;
                } else {
                    $i$f$groupByName = $i$f$groupByName4;
                }
                ((List) value$iv$iv$iv$iv3).add(next3);
                key$iv$iv$iv5 = $this$groupByName$iv5;
                $i$f$groupByName4 = $i$f$groupByName;
                this_$iv3 = this_$iv4;
            }
            Object $this$groupByName$iv6 = key$iv$iv$iv5;
            g gVar2 = this_$iv3;
            int i3 = $i$f$groupByName4;
            map = E(linkedHashMap3);
        } else {
            map = l0.f();
        }
        this.e = map;
        this.f = this.l.h().h(new e(this));
        this.g = this.l.h().h(new f(this));
        this.h = this.l.h().g(new C0419g(this));
        this.i = this.l.h().c(new d(this));
        this.j = this.l.h().c(new h(this));
        this.k = this.l.h().c(new a(aVar));
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final n w() {
        return this.l;
    }

    private final Map<kotlin.reflect.jvm.internal.impl.name.f, byte[]> E(@NotNull Map<kotlin.reflect.jvm.internal.impl.name.f, ? extends Collection<? extends kotlin.reflect.jvm.internal.impl.protobuf.a>> $this$packToByteArray) {
        Map $this$mapValues$iv = $this$packToByteArray;
        int $i$f$mapValues = 0;
        Map destination$iv$iv = new LinkedHashMap(k0.b($this$mapValues$iv.size()));
        for (T next : $this$mapValues$iv.entrySet()) {
            Object key = ((Map.Entry) next).getKey();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Iterable<kotlin.reflect.jvm.internal.impl.protobuf.a> $this$map$iv = (Iterable) ((Map.Entry) next).getValue();
            Map $this$mapValues$iv2 = $this$mapValues$iv;
            Collection destination$iv$iv2 = new ArrayList(kotlin.collections.r.r($this$map$iv, 10));
            for (kotlin.reflect.jvm.internal.impl.protobuf.a proto : $this$map$iv) {
                proto.writeDelimitedTo(byteArrayOutputStream);
                destination$iv$iv2.add(x.a);
                $i$f$mapValues = $i$f$mapValues;
            }
            destination$iv$iv.put(key, byteArrayOutputStream.toByteArray());
            $this$mapValues$iv = $this$mapValues$iv2;
        }
        return destination$iv$iv;
    }

    /* compiled from: DeserializedMemberScope.kt */
    public static final class e extends kotlin.jvm.internal.l implements l<kotlin.reflect.jvm.internal.impl.name.f, Collection<? extends n0>> {
        final /* synthetic */ g this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(g gVar) {
            super(1);
            this.this$0 = gVar;
        }

        @NotNull
        public final Collection<n0> invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.f it) {
            kotlin.jvm.internal.k.f(it, "it");
            return this.this$0.p(it);
        }
    }

    /* compiled from: DeserializedMemberScope.kt */
    public static final class f extends kotlin.jvm.internal.l implements l<kotlin.reflect.jvm.internal.impl.name.f, Collection<? extends i0>> {
        final /* synthetic */ g this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(g gVar) {
            super(1);
            this.this$0 = gVar;
        }

        @NotNull
        public final Collection<i0> invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.f it) {
            kotlin.jvm.internal.k.f(it, "it");
            return this.this$0.s(it);
        }
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.g$g  reason: collision with other inner class name */
    /* compiled from: DeserializedMemberScope.kt */
    public static final class C0419g extends kotlin.jvm.internal.l implements l<kotlin.reflect.jvm.internal.impl.name.f, s0> {
        final /* synthetic */ g this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0419g(g gVar) {
            super(1);
            this.this$0 = gVar;
        }

        @Nullable
        public final s0 invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.f it) {
            kotlin.jvm.internal.k.f(it, "it");
            return this.this$0.u(it);
        }
    }

    /* compiled from: DeserializedMemberScope.kt */
    public static final class d extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Set<? extends kotlin.reflect.jvm.internal.impl.name.f>> {
        final /* synthetic */ g this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(g gVar) {
            super(0);
            this.this$0 = gVar;
        }

        @NotNull
        public final Set<kotlin.reflect.jvm.internal.impl.name.f> invoke() {
            return p0.i(this.this$0.c.keySet(), this.this$0.z());
        }
    }

    /* compiled from: DeserializedMemberScope.kt */
    public static final class h extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Set<? extends kotlin.reflect.jvm.internal.impl.name.f>> {
        final /* synthetic */ g this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        h(g gVar) {
            super(0);
            this.this$0 = gVar;
        }

        @NotNull
        public final Set<kotlin.reflect.jvm.internal.impl.name.f> invoke() {
            return p0.i(this.this$0.d.keySet(), this.this$0.A());
        }
    }

    private final Set<kotlin.reflect.jvm.internal.impl.name.f> B() {
        return this.e.keySet();
    }

    /* compiled from: DeserializedMemberScope.kt */
    public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Set<? extends kotlin.reflect.jvm.internal.impl.name.f>> {
        final /* synthetic */ kotlin.jvm.functions.a $classNames;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(kotlin.jvm.functions.a aVar) {
            super(0);
            this.$classNames = aVar;
        }

        @NotNull
        public final Set<kotlin.reflect.jvm.internal.impl.name.f> invoke() {
            return kotlin.collections.y.H0((Iterable) this.$classNames.invoke());
        }
    }

    @NotNull
    public Set<kotlin.reflect.jvm.internal.impl.name.f> a() {
        return y();
    }

    @NotNull
    public Set<kotlin.reflect.jvm.internal.impl.name.f> f() {
        return C();
    }

    /* access modifiers changed from: private */
    public final Collection<n0> p(kotlin.reflect.jvm.internal.impl.name.f name) {
        List<kotlin.reflect.jvm.internal.impl.metadata.i> protos$iv$iv;
        kotlin.reflect.jvm.internal.impl.name.f fVar = name;
        Map bytesByName$iv = this.c;
        q parser$iv = kotlin.reflect.jvm.internal.impl.metadata.i.PARSER;
        kotlin.jvm.internal.k.b(parser$iv, "ProtoBuf.Function.PARSER");
        byte[] it$iv = bytesByName$iv.get(fVar);
        if (it$iv == null || (protos$iv$iv = kotlin.sequences.o.C(kotlin.sequences.m.i(new b(new ByteArrayInputStream(it$iv), this, parser$iv)))) == null) {
            protos$iv$iv = kotlin.collections.q.g();
        }
        ArrayList arrayList = new ArrayList();
        for (kotlin.reflect.jvm.internal.impl.metadata.i it : protos$iv$iv) {
            Map bytesByName$iv2 = bytesByName$iv;
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.x f2 = this.l.f();
            kotlin.jvm.internal.k.b(it, "it");
            arrayList.add(f2.n(it));
            bytesByName$iv = bytesByName$iv2;
            parser$iv = parser$iv;
        }
        q qVar = parser$iv;
        ArrayList descriptors$iv$iv = arrayList;
        q(fVar, descriptors$iv$iv);
        return kotlin.reflect.jvm.internal.impl.utils.a.c(descriptors$iv$iv);
    }

    /* compiled from: DeserializedMemberScope.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<M> {
        final /* synthetic */ ByteArrayInputStream $inputStream;
        final /* synthetic */ q $parser$inlined;
        final /* synthetic */ g this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(ByteArrayInputStream byteArrayInputStream, g gVar, q qVar) {
            super(0);
            this.$inputStream = byteArrayInputStream;
            this.this$0 = gVar;
            this.$parser$inlined = qVar;
        }

        public final M invoke() {
            return (o) this.$parser$inlined.d(this.$inputStream, this.this$0.w().c().j());
        }
    }

    /* compiled from: DeserializedMemberScope.kt */
    public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<M> {
        final /* synthetic */ ByteArrayInputStream $inputStream;
        final /* synthetic */ q $parser$inlined;
        final /* synthetic */ g this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public c(ByteArrayInputStream byteArrayInputStream, g gVar, q qVar) {
            super(0);
            this.$inputStream = byteArrayInputStream;
            this.this$0 = gVar;
            this.$parser$inlined = qVar;
        }

        public final M invoke() {
            return (o) this.$parser$inlined.d(this.$inputStream, this.this$0.w().c().j());
        }
    }

    /* access modifiers changed from: protected */
    public void q(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull Collection<n0> functions) {
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(functions, "functions");
    }

    @NotNull
    public Collection<n0> b(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
        if (!a().contains(name)) {
            return kotlin.collections.q.g();
        }
        return this.f.invoke(name);
    }

    /* access modifiers changed from: private */
    public final Collection<i0> s(kotlin.reflect.jvm.internal.impl.name.f name) {
        List<kotlin.reflect.jvm.internal.impl.metadata.n> protos$iv$iv;
        kotlin.reflect.jvm.internal.impl.name.f fVar = name;
        Map bytesByName$iv = this.d;
        q parser$iv = kotlin.reflect.jvm.internal.impl.metadata.n.PARSER;
        kotlin.jvm.internal.k.b(parser$iv, "ProtoBuf.Property.PARSER");
        byte[] it$iv = bytesByName$iv.get(fVar);
        if (it$iv == null || (protos$iv$iv = kotlin.sequences.o.C(kotlin.sequences.m.i(new c(new ByteArrayInputStream(it$iv), this, parser$iv)))) == null) {
            protos$iv$iv = kotlin.collections.q.g();
        }
        ArrayList arrayList = new ArrayList();
        for (kotlin.reflect.jvm.internal.impl.metadata.n it : protos$iv$iv) {
            Map bytesByName$iv2 = bytesByName$iv;
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.x f2 = this.l.f();
            kotlin.jvm.internal.k.b(it, "it");
            arrayList.add(f2.p(it));
            bytesByName$iv = bytesByName$iv2;
            parser$iv = parser$iv;
        }
        q qVar = parser$iv;
        ArrayList descriptors$iv$iv = arrayList;
        r(fVar, descriptors$iv$iv);
        return kotlin.reflect.jvm.internal.impl.utils.a.c(descriptors$iv$iv);
    }

    /* access modifiers changed from: protected */
    public void r(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull Collection<i0> descriptors) {
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(descriptors, "descriptors");
    }

    /* access modifiers changed from: private */
    public final s0 u(kotlin.reflect.jvm.internal.impl.name.f name) {
        r proto;
        byte[] byteArray = this.e.get(name);
        if (byteArray == null || (proto = r.parseDelimitedFrom(new ByteArrayInputStream(byteArray), this.l.c().j())) == null) {
            return null;
        }
        return this.l.f().q(proto);
    }

    @NotNull
    public Collection<i0> e(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
        if (!f().contains(name)) {
            return kotlin.collections.q.g();
        }
        return this.g.invoke(name);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final Collection<m> o(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d kindFilter, @NotNull l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean> nameFilter, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        kotlin.jvm.internal.k.f(kindFilter, "kindFilter");
        kotlin.jvm.internal.k.f(nameFilter, "nameFilter");
        kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
        ArrayList result = new ArrayList(0);
        d.a aVar = kotlin.reflect.jvm.internal.impl.resolve.scopes.d.x;
        if (kindFilter.a(aVar.g())) {
            m(result, nameFilter);
        }
        n(result, kindFilter, nameFilter, location);
        if (kindFilter.a(aVar.c())) {
            for (kotlin.reflect.jvm.internal.impl.name.f className : x()) {
                if (nameFilter.invoke(className).booleanValue()) {
                    kotlin.reflect.jvm.internal.impl.utils.a.a(result, v(className));
                }
            }
        }
        if (kindFilter.a(kotlin.reflect.jvm.internal.impl.resolve.scopes.d.x.h())) {
            for (kotlin.reflect.jvm.internal.impl.name.f typeAliasName : B()) {
                if (nameFilter.invoke(typeAliasName).booleanValue()) {
                    kotlin.reflect.jvm.internal.impl.utils.a.a(result, this.h.invoke(typeAliasName));
                }
            }
        }
        return kotlin.reflect.jvm.internal.impl.utils.a.c(result);
    }

    private final void n(Collection<m> result, kotlin.reflect.jvm.internal.impl.resolve.scopes.d kindFilter, l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean> nameFilter, kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        if (kindFilter.a(kotlin.reflect.jvm.internal.impl.resolve.scopes.d.x.i())) {
            Collection names$iv = f();
            ArrayList subResult$iv = new ArrayList();
            for (kotlin.reflect.jvm.internal.impl.name.f name$iv : names$iv) {
                if (nameFilter.invoke(name$iv).booleanValue()) {
                    subResult$iv.addAll(e(name$iv, location));
                }
            }
            kotlin.reflect.jvm.internal.impl.resolve.f fVar = kotlin.reflect.jvm.internal.impl.resolve.f.c;
            kotlin.jvm.internal.k.b(fVar, "MemberComparator.NameAnd…MemberComparator.INSTANCE");
            kotlin.collections.u.w(subResult$iv, fVar);
            result.addAll(subResult$iv);
        }
        if (kindFilter.a(kotlin.reflect.jvm.internal.impl.resolve.scopes.d.x.d())) {
            Collection names$iv2 = a();
            ArrayList subResult$iv2 = new ArrayList();
            for (kotlin.reflect.jvm.internal.impl.name.f name$iv2 : names$iv2) {
                if (nameFilter.invoke(name$iv2).booleanValue()) {
                    subResult$iv2.addAll(b(name$iv2, location));
                }
            }
            kotlin.reflect.jvm.internal.impl.resolve.f fVar2 = kotlin.reflect.jvm.internal.impl.resolve.f.c;
            kotlin.jvm.internal.k.b(fVar2, "MemberComparator.NameAnd…MemberComparator.INSTANCE");
            kotlin.collections.u.w(subResult$iv2, fVar2);
            result.addAll(subResult$iv2);
        }
    }

    @Nullable
    public kotlin.reflect.jvm.internal.impl.descriptors.h c(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
        if (D(name)) {
            return v(name);
        }
        if (B().contains(name)) {
            return this.h.invoke(name);
        }
        return null;
    }

    private final kotlin.reflect.jvm.internal.impl.descriptors.e v(kotlin.reflect.jvm.internal.impl.name.f name) {
        return this.l.c().b(t(name));
    }

    /* access modifiers changed from: protected */
    public boolean D(@NotNull kotlin.reflect.jvm.internal.impl.name.f name) {
        kotlin.jvm.internal.k.f(name, "name");
        return x().contains(name);
    }
}
