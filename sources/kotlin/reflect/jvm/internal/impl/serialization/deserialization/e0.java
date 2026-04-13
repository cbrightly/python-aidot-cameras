package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.l0;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.f;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.t;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.g;
import kotlin.reflect.jvm.internal.impl.metadata.q;
import kotlin.reflect.jvm.internal.impl.metadata.s;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.m0;
import kotlin.reflect.jvm.internal.impl.types.n0;
import kotlin.reflect.jvm.internal.impl.types.u;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.types.w0;
import kotlin.reflect.jvm.internal.impl.types.y0;
import kotlin.sequences.m;
import kotlin.sequences.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TypeDeserializer.kt */
public final class e0 {
    private final l<Integer, kotlin.reflect.jvm.internal.impl.descriptors.e> a;
    private final l<Integer, h> b;
    private final Map<Integer, t0> c;
    /* access modifiers changed from: private */
    public final n d;
    private final e0 e;
    private final String f;
    private final String g;
    private boolean h;

    /* compiled from: TypeDeserializer.kt */
    public static final class e extends kotlin.jvm.internal.l implements l<Integer, kotlin.reflect.jvm.internal.impl.descriptors.e> {
        final /* synthetic */ q $proto;
        final /* synthetic */ e0 this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(e0 e0Var, q qVar) {
            super(1);
            this.this$0 = e0Var;
            this.$proto = qVar;
        }

        /* compiled from: TypeDeserializer.kt */
        public static final class c extends kotlin.jvm.internal.l implements l<q, Integer> {
            public static final c INSTANCE = new c();

            c() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Integer.valueOf(invoke((q) obj));
            }

            public final int invoke(@NotNull q it) {
                k.f(it, "it");
                return it.getArgumentCount();
            }
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return invoke(((Number) obj).intValue());
        }

        /* compiled from: TypeDeserializer.kt */
        public static final class b extends kotlin.jvm.internal.l implements l<q, q> {
            final /* synthetic */ e this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(e eVar) {
                super(1);
                this.this$0 = eVar;
            }

            @Nullable
            public final q invoke(@NotNull q it) {
                k.f(it, "it");
                return g.f(it, this.this$0.this$0.d.j());
            }
        }

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.descriptors.e invoke(int classIdIndex) {
            kotlin.reflect.jvm.internal.impl.name.a classId = y.a(this.this$0.d.g(), classIdIndex);
            List typeParametersCount = o.D(o.w(m.h(this.$proto, new b(this)), c.INSTANCE));
            int classNestingLevel = o.m(m.h(classId, a.INSTANCE));
            while (typeParametersCount.size() < classNestingLevel) {
                typeParametersCount.add(0);
            }
            return this.this$0.d.c().q().d(classId, typeParametersCount);
        }

        /* compiled from: TypeDeserializer.kt */
        public static final /* synthetic */ class a extends kotlin.jvm.internal.h implements l<kotlin.reflect.jvm.internal.impl.name.a, kotlin.reflect.jvm.internal.impl.name.a> {
            public static final a INSTANCE = new a();

            a() {
                super(1);
            }

            public final String getName() {
                return "getOuterClassId";
            }

            public final kotlin.reflect.e getOwner() {
                return a0.b(kotlin.reflect.jvm.internal.impl.name.a.class);
            }

            public final String getSignature() {
                return "getOuterClassId()Lorg/jetbrains/kotlin/name/ClassId;";
            }

            @Nullable
            public final kotlin.reflect.jvm.internal.impl.name.a invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.a p1) {
                k.f(p1, "p1");
                return p1.g();
            }
        }
    }

    /* compiled from: TypeDeserializer.kt */
    public static final class a extends kotlin.jvm.internal.l implements l<Integer, kotlin.reflect.jvm.internal.impl.descriptors.e> {
        final /* synthetic */ e0 this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(e0 e0Var) {
            super(1);
            this.this$0 = e0Var;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return invoke(((Number) obj).intValue());
        }

        @Nullable
        public final kotlin.reflect.jvm.internal.impl.descriptors.e invoke(int fqNameIndex) {
            return this.this$0.d(fqNameIndex);
        }
    }

    /* compiled from: TypeDeserializer.kt */
    public static final class d extends kotlin.jvm.internal.l implements l<Integer, h> {
        final /* synthetic */ e0 this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(e0 e0Var) {
            super(1);
            this.this$0 = e0Var;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return invoke(((Number) obj).intValue());
        }

        @Nullable
        public final h invoke(int fqNameIndex) {
            return this.this$0.f(fqNameIndex);
        }
    }

    public e0(@NotNull n c2, @Nullable e0 parent, @NotNull List<s> typeParameterProtos, @NotNull String debugName, @NotNull String containerPresentableName, boolean experimentalSuspendFunctionTypeEncountered) {
        Map<Integer, t0> map;
        k.f(c2, "c");
        k.f(typeParameterProtos, "typeParameterProtos");
        k.f(debugName, "debugName");
        k.f(containerPresentableName, "containerPresentableName");
        this.d = c2;
        this.e = parent;
        this.f = debugName;
        this.g = containerPresentableName;
        this.h = experimentalSuspendFunctionTypeEncountered;
        this.a = c2.h().g(new a(this));
        this.b = c2.h().g(new d(this));
        if (typeParameterProtos.isEmpty()) {
            map = l0.f();
        } else {
            map = new LinkedHashMap<>();
            int index = 0;
            for (s proto : typeParameterProtos) {
                map.put(Integer.valueOf(proto.getId()), new kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.l(this.d, proto, index));
                index++;
            }
        }
        this.c = map;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ e0(n nVar, e0 e0Var, List list, String str, String str2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(nVar, e0Var, list, str, str2, (i & 32) != 0 ? false : z);
    }

    public final boolean j() {
        return this.h;
    }

    @NotNull
    public final List<t0> k() {
        return y.C0(this.c.values());
    }

    @NotNull
    public final b0 n(@NotNull q proto) {
        k.f(proto, "proto");
        if (!proto.hasFlexibleTypeCapabilitiesId()) {
            return l(proto);
        }
        String id = this.d.g().getString(proto.getFlexibleTypeCapabilitiesId());
        i0 lowerBound = l(proto);
        q c2 = g.c(proto, this.d.j());
        if (c2 == null) {
            k.n();
        }
        return this.d.c().l().a(proto, id, lowerBound, l(c2));
    }

    @NotNull
    public final i0 l(@NotNull q proto) {
        i0 localClassifierType;
        i0 simpleType;
        q qVar = proto;
        k.f(qVar, "proto");
        if (proto.hasClassName()) {
            localClassifierType = e(proto.getClassName());
        } else if (proto.hasTypeAliasName()) {
            localClassifierType = e(proto.getTypeAliasName());
        } else {
            localClassifierType = null;
        }
        if (localClassifierType != null) {
            return localClassifierType;
        }
        u0 constructor = p(proto);
        if (u.r(constructor.c())) {
            i0 o = u.o(constructor.toString(), constructor);
            k.b(o, "ErrorUtils.createErrorTy….toString(), constructor)");
            return o;
        }
        kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.a annotations = new kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.a(this.d.h(), new c(this, qVar));
        Iterable $this$mapIndexedTo$iv$iv = new b(this).invoke(qVar);
        Collection destination$iv$iv = new ArrayList(r.r($this$mapIndexedTo$iv$iv, 10));
        int index$iv$iv = 0;
        for (T next : $this$mapIndexedTo$iv$iv) {
            int index$iv$iv2 = index$iv$iv + 1;
            if (index$iv$iv < 0) {
                kotlin.collections.q.q();
            }
            i0 localClassifierType2 = localClassifierType;
            List<t0> parameters = constructor.getParameters();
            k.b(parameters, "constructor.parameters");
            destination$iv$iv.add(o((t0) y.V(parameters, index$iv$iv), (q.b) next));
            index$iv$iv = index$iv$iv2;
            localClassifierType = localClassifierType2;
            $this$mapIndexedTo$iv$iv = $this$mapIndexedTo$iv$iv;
        }
        List<q.b> list = $this$mapIndexedTo$iv$iv;
        List arguments = y.C0(destination$iv$iv);
        Boolean g2 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.a.d(proto.getFlags());
        k.b(g2, "Flags.SUSPEND_TYPE.get(proto.flags)");
        if (g2.booleanValue()) {
            simpleType = h(annotations, constructor, arguments, proto.getNullable());
        } else {
            simpleType = c0.i(annotations, constructor, arguments, proto.getNullable(), (i) null, 16, (Object) null);
        }
        q abbreviatedTypeProto = g.a(qVar, this.d.j());
        if (abbreviatedTypeProto != null) {
            return kotlin.reflect.jvm.internal.impl.types.l0.h(simpleType, l(abbreviatedTypeProto));
        }
        return simpleType;
    }

    /* compiled from: TypeDeserializer.kt */
    public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends kotlin.reflect.jvm.internal.impl.descriptors.annotations.c>> {
        final /* synthetic */ q $proto;
        final /* synthetic */ e0 this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(e0 e0Var, q qVar) {
            super(0);
            this.this$0 = e0Var;
            this.$proto = qVar;
        }

        @NotNull
        public final List<kotlin.reflect.jvm.internal.impl.descriptors.annotations.c> invoke() {
            return this.this$0.d.c().d().c(this.$proto, this.this$0.d.g());
        }
    }

    /* compiled from: TypeDeserializer.kt */
    public static final class b extends kotlin.jvm.internal.l implements l<q, List<? extends q.b>> {
        final /* synthetic */ e0 this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(e0 e0Var) {
            super(1);
            this.this$0 = e0Var;
        }

        @NotNull
        public final List<q.b> invoke(@NotNull q $this$collectAllArguments) {
            k.f($this$collectAllArguments, "$this$collectAllArguments");
            List<q.b> argumentList = $this$collectAllArguments.getArgumentList();
            k.b(argumentList, "argumentList");
            q f = g.f($this$collectAllArguments, this.this$0.d.j());
            List<q.b> invoke = f != null ? invoke(f) : null;
            if (invoke == null) {
                invoke = kotlin.collections.q.g();
            }
            return y.n0(argumentList, invoke);
        }
    }

    private final u0 p(q proto) {
        T t;
        u0 i;
        e $fun$notFoundClass$1 = new e(this, proto);
        if (proto.hasClassName()) {
            kotlin.reflect.jvm.internal.impl.descriptors.e invoke = this.a.invoke(Integer.valueOf(proto.getClassName()));
            if (invoke == null) {
                invoke = $fun$notFoundClass$1.invoke(proto.getClassName());
            }
            u0 i2 = invoke.i();
            k.b(i2, "(classDescriptors(proto.…assName)).typeConstructor");
            return i2;
        } else if (proto.hasTypeParameter()) {
            u0 q = q(proto.getTypeParameter());
            if (q != null) {
                return q;
            }
            u0 k = u.k("Unknown type parameter " + proto.getTypeParameter() + ". Please try recompiling module containing \"" + this.g + StringUtil.DOUBLE_QUOTE);
            k.b(k, "ErrorUtils.createErrorTy…\\\"\"\n                    )");
            return k;
        } else if (proto.hasTypeParameterName()) {
            kotlin.reflect.jvm.internal.impl.descriptors.m container = this.d.e();
            String name = this.d.g().getString(proto.getTypeParameterName());
            Iterator<T> it = k().iterator();
            while (true) {
                if (!it.hasNext()) {
                    t = null;
                    break;
                }
                t = it.next();
                if (k.a(((t0) t).getName().b(), name)) {
                    break;
                }
            }
            t0 parameter = (t0) t;
            if (parameter != null && (i = parameter.i()) != null) {
                return i;
            }
            u0 k2 = u.k("Deserialized type parameter " + name + " in " + container);
            k.b(k2, "ErrorUtils.createErrorTy…ter $name in $container\")");
            return k2;
        } else if (proto.hasTypeAliasName()) {
            h invoke2 = this.b.invoke(Integer.valueOf(proto.getTypeAliasName()));
            if (invoke2 == null) {
                invoke2 = $fun$notFoundClass$1.invoke(proto.getTypeAliasName());
            }
            u0 i3 = invoke2.i();
            k.b(i3, "(typeAliasDescriptors(pr…iasName)).typeConstructor");
            return i3;
        } else {
            u0 k3 = u.k("Unknown type");
            k.b(k3, "ErrorUtils.createErrorTy…nstructor(\"Unknown type\")");
            return k3;
        }
    }

    private final i0 h(kotlin.reflect.jvm.internal.impl.descriptors.annotations.g annotations, u0 functionTypeConstructor, List<? extends w0> arguments, boolean nullable) {
        i0 i0Var = null;
        switch (functionTypeConstructor.getParameters().size() - arguments.size()) {
            case 0:
                i0Var = i(annotations, functionTypeConstructor, arguments, nullable);
                break;
            case 1:
                int arity = arguments.size() - 1;
                if (arity >= 0) {
                    kotlin.reflect.jvm.internal.impl.descriptors.e Z = functionTypeConstructor.j().Z(arity);
                    k.b(Z, "functionTypeConstructor.…getSuspendFunction(arity)");
                    u0 i = Z.i();
                    k.b(i, "functionTypeConstructor.…on(arity).typeConstructor");
                    i0Var = c0.i(annotations, i, arguments, nullable, (i) null, 16, (Object) null);
                    break;
                }
                break;
        }
        i0 result = i0Var;
        if (result != null) {
            return result;
        }
        i0 n = u.n("Bad suspend function in metadata with constructor: " + functionTypeConstructor, arguments);
        k.b(n, "ErrorUtils.createErrorTy…      arguments\n        )");
        return n;
    }

    private final i0 i(kotlin.reflect.jvm.internal.impl.descriptors.annotations.g annotations, u0 functionTypeConstructor, List<? extends w0> arguments, boolean nullable) {
        i0 functionType = c0.i(annotations, functionTypeConstructor, arguments, nullable, (i) null, 16, (Object) null);
        if (!f.l(functionType)) {
            return null;
        }
        return m(functionType);
    }

    private final i0 m(b0 funType) {
        b0 continuationArgumentType;
        boolean isReleaseCoroutines = this.d.c().g().d();
        w0 w0Var = (w0) y.f0(f.i(funType));
        kotlin.reflect.jvm.internal.impl.name.b bVar = null;
        if (w0Var == null || (continuationArgumentType = w0Var.getType()) == null) {
            return null;
        }
        k.b(continuationArgumentType, "funType.getValueParamete…ll()?.type ?: return null");
        h c2 = continuationArgumentType.I0().c();
        kotlin.reflect.jvm.internal.impl.name.b continuationArgumentFqName = c2 != null ? kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.j(c2) : null;
        boolean z = true;
        if (continuationArgumentType.H0().size() != 1 || (!kotlin.reflect.jvm.internal.impl.builtins.k.a(continuationArgumentFqName, true) && !kotlin.reflect.jvm.internal.impl.builtins.k.a(continuationArgumentFqName, false))) {
            return (i0) funType;
        }
        b0 suspendReturnType = ((w0) y.q0(continuationArgumentType.H0())).getType();
        k.b(suspendReturnType, "continuationArgumentType.arguments.single().type");
        Object $this$safeAs$iv = this.d.e();
        kotlin.reflect.jvm.internal.impl.descriptors.a aVar = (kotlin.reflect.jvm.internal.impl.descriptors.a) (!($this$safeAs$iv instanceof kotlin.reflect.jvm.internal.impl.descriptors.a) ? null : $this$safeAs$iv);
        if (aVar != null) {
            bVar = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.f(aVar);
        }
        if (k.a(bVar, d0.a)) {
            return g(funType, suspendReturnType);
        }
        if (!this.h && (!isReleaseCoroutines || !kotlin.reflect.jvm.internal.impl.builtins.k.a(continuationArgumentFqName, !isReleaseCoroutines))) {
            z = false;
        }
        this.h = z;
        return g(funType, suspendReturnType);
    }

    private final i0 g(b0 funType, b0 suspendReturnType) {
        kotlin.reflect.jvm.internal.impl.builtins.g f2 = kotlin.reflect.jvm.internal.impl.types.typeUtil.a.f(funType);
        kotlin.reflect.jvm.internal.impl.descriptors.annotations.g annotations = funType.getAnnotations();
        b0 g2 = f.g(funType);
        Iterable<w0> $this$mapTo$iv$iv = y.P(f.i(funType), 1);
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (w0 p1 : $this$mapTo$iv$iv) {
            arrayList.add(p1.getType());
        }
        return f.a(f2, annotations, g2, arrayList, (List<kotlin.reflect.jvm.internal.impl.name.f>) null, suspendReturnType, true).P0(funType.J0());
    }

    private final u0 q(int typeParameterId) {
        u0 i;
        t0 t0Var = this.c.get(Integer.valueOf(typeParameterId));
        if (t0Var != null && (i = t0Var.i()) != null) {
            return i;
        }
        e0 e0Var = this.e;
        if (e0Var != null) {
            return e0Var.q(typeParameterId);
        }
        return null;
    }

    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.descriptors.e d(int fqNameIndex) {
        kotlin.reflect.jvm.internal.impl.name.a id = y.a(this.d.g(), fqNameIndex);
        if (id.k()) {
            return this.d.c().b(id);
        }
        return t.a(this.d.c().p(), id);
    }

    private final i0 e(int className) {
        if (y.a(this.d.g(), className).k()) {
            return this.d.c().n().a();
        }
        return null;
    }

    /* access modifiers changed from: private */
    public final h f(int fqNameIndex) {
        kotlin.reflect.jvm.internal.impl.name.a id = y.a(this.d.g(), fqNameIndex);
        if (id.k()) {
            return null;
        }
        return t.d(this.d.c().p(), id);
    }

    private final w0 o(t0 parameter, q.b typeArgumentProto) {
        if (typeArgumentProto.getProjection() != q.b.c.STAR) {
            c0 c0Var = c0.a;
            q.b.c projection = typeArgumentProto.getProjection();
            k.b(projection, "typeArgumentProto.projection");
            h1 projection2 = c0Var.d(projection);
            q type = g.l(typeArgumentProto, this.d.j());
            if (type != null) {
                return new y0(projection2, n(type));
            }
            return new y0(u.j("No type recorded"));
        } else if (parameter == null) {
            return new m0(this.d.c().p().j());
        } else {
            return new n0(parameter);
        }
    }

    @NotNull
    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(this.f);
        if (this.e == null) {
            str = "";
        } else {
            str = ". Child of " + this.e.f;
        }
        sb.append(str);
        return sb.toString();
    }
}
