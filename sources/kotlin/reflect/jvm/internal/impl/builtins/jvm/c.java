package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kotlin.TypeCastException;
import kotlin.collections.n0;
import kotlin.collections.o0;
import kotlin.collections.q;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.functions.b;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.e;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.name.h;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.d;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.text.v;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: JavaToKotlinClassMap.kt */
public final class c {
    private static final String a;
    private static final String b;
    private static final String c;
    private static final String d;
    private static final kotlin.reflect.jvm.internal.impl.name.a e;
    @NotNull
    private static final b f;
    private static final kotlin.reflect.jvm.internal.impl.name.a g;
    private static final HashMap<kotlin.reflect.jvm.internal.impl.name.c, kotlin.reflect.jvm.internal.impl.name.a> h = new HashMap<>();
    private static final HashMap<kotlin.reflect.jvm.internal.impl.name.c, kotlin.reflect.jvm.internal.impl.name.a> i = new HashMap<>();
    private static final HashMap<kotlin.reflect.jvm.internal.impl.name.c, b> j = new HashMap<>();
    private static final HashMap<kotlin.reflect.jvm.internal.impl.name.c, b> k = new HashMap<>();
    @NotNull
    private static final List<a> l;
    public static final c m;

    static {
        c this_$iv = new c();
        m = this_$iv;
        StringBuilder sb = new StringBuilder();
        b.d dVar = b.d.Function;
        sb.append(dVar.getPackageFqName().toString());
        sb.append(".");
        sb.append(dVar.getClassNamePrefix());
        a = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        b.d dVar2 = b.d.KFunction;
        sb2.append(dVar2.getPackageFqName().toString());
        sb2.append(".");
        sb2.append(dVar2.getClassNamePrefix());
        b = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        b.d dVar3 = b.d.SuspendFunction;
        sb3.append(dVar3.getPackageFqName().toString());
        sb3.append(".");
        sb3.append(dVar3.getClassNamePrefix());
        c = sb3.toString();
        StringBuilder sb4 = new StringBuilder();
        b.d dVar4 = b.d.KSuspendFunction;
        sb4.append(dVar4.getPackageFqName().toString());
        sb4.append(".");
        sb4.append(dVar4.getClassNamePrefix());
        d = sb4.toString();
        kotlin.reflect.jvm.internal.impl.name.a m2 = kotlin.reflect.jvm.internal.impl.name.a.m(new kotlin.reflect.jvm.internal.impl.name.b("kotlin.jvm.functions.FunctionN"));
        k.b(m2, "ClassId.topLevel(FqName(…vm.functions.FunctionN\"))");
        e = m2;
        kotlin.reflect.jvm.internal.impl.name.b b2 = m2.b();
        k.b(b2, "FUNCTION_N_CLASS_ID.asSingleFqName()");
        f = b2;
        kotlin.reflect.jvm.internal.impl.name.a m3 = kotlin.reflect.jvm.internal.impl.name.a.m(new kotlin.reflect.jvm.internal.impl.name.b("kotlin.reflect.KFunction"));
        k.b(m3, "ClassId.topLevel(FqName(…tlin.reflect.KFunction\"))");
        g = m3;
        g.e eVar = g.h;
        kotlin.reflect.jvm.internal.impl.name.a kotlinReadOnly$iv = kotlin.reflect.jvm.internal.impl.name.a.m(eVar.M);
        k.b(kotlinReadOnly$iv, "ClassId.topLevel(FQ_NAMES.iterable)");
        kotlin.reflect.jvm.internal.impl.name.b kotlinMutable$iv = eVar.U;
        k.b(kotlinMutable$iv, "FQ_NAMES.mutableIterable");
        kotlin.reflect.jvm.internal.impl.name.b h2 = kotlinReadOnly$iv.h();
        kotlin.reflect.jvm.internal.impl.name.b h3 = kotlinReadOnly$iv.h();
        k.b(h3, "kotlinReadOnly.packageFqName");
        int i2 = 0;
        kotlin.reflect.jvm.internal.impl.name.a mutableClassId$iv = new kotlin.reflect.jvm.internal.impl.name.a(h2, e.d(kotlinMutable$iv, h3), false);
        kotlin.reflect.jvm.internal.impl.name.a kotlinReadOnly$iv2 = kotlin.reflect.jvm.internal.impl.name.a.m(eVar.L);
        k.b(kotlinReadOnly$iv2, "ClassId.topLevel(FQ_NAMES.iterator)");
        kotlin.reflect.jvm.internal.impl.name.b kotlinMutable$iv2 = eVar.T;
        k.b(kotlinMutable$iv2, "FQ_NAMES.mutableIterator");
        kotlin.reflect.jvm.internal.impl.name.b h4 = kotlinReadOnly$iv2.h();
        kotlin.reflect.jvm.internal.impl.name.b h5 = kotlinReadOnly$iv2.h();
        k.b(h5, "kotlinReadOnly.packageFqName");
        kotlin.reflect.jvm.internal.impl.name.a mutableClassId$iv2 = new kotlin.reflect.jvm.internal.impl.name.a(h4, e.d(kotlinMutable$iv2, h5), false);
        kotlin.reflect.jvm.internal.impl.name.a kotlinReadOnly$iv3 = kotlin.reflect.jvm.internal.impl.name.a.m(eVar.N);
        k.b(kotlinReadOnly$iv3, "ClassId.topLevel(FQ_NAMES.collection)");
        kotlin.reflect.jvm.internal.impl.name.b kotlinMutable$iv3 = eVar.V;
        k.b(kotlinMutable$iv3, "FQ_NAMES.mutableCollection");
        kotlin.reflect.jvm.internal.impl.name.b h6 = kotlinReadOnly$iv3.h();
        kotlin.reflect.jvm.internal.impl.name.b h7 = kotlinReadOnly$iv3.h();
        k.b(h7, "kotlinReadOnly.packageFqName");
        kotlin.reflect.jvm.internal.impl.name.a mutableClassId$iv3 = new kotlin.reflect.jvm.internal.impl.name.a(h6, e.d(kotlinMutable$iv3, h7), false);
        kotlin.reflect.jvm.internal.impl.name.a kotlinReadOnly$iv4 = kotlin.reflect.jvm.internal.impl.name.a.m(eVar.O);
        k.b(kotlinReadOnly$iv4, "ClassId.topLevel(FQ_NAMES.list)");
        kotlin.reflect.jvm.internal.impl.name.b kotlinMutable$iv4 = eVar.W;
        k.b(kotlinMutable$iv4, "FQ_NAMES.mutableList");
        kotlin.reflect.jvm.internal.impl.name.b h8 = kotlinReadOnly$iv4.h();
        kotlin.reflect.jvm.internal.impl.name.b h9 = kotlinReadOnly$iv4.h();
        k.b(h9, "kotlinReadOnly.packageFqName");
        kotlin.reflect.jvm.internal.impl.name.a mutableClassId$iv4 = new kotlin.reflect.jvm.internal.impl.name.a(h8, e.d(kotlinMutable$iv4, h9), false);
        kotlin.reflect.jvm.internal.impl.name.a kotlinReadOnly$iv5 = kotlin.reflect.jvm.internal.impl.name.a.m(eVar.Q);
        k.b(kotlinReadOnly$iv5, "ClassId.topLevel(FQ_NAMES.set)");
        kotlin.reflect.jvm.internal.impl.name.b kotlinMutable$iv5 = eVar.Y;
        k.b(kotlinMutable$iv5, "FQ_NAMES.mutableSet");
        kotlin.reflect.jvm.internal.impl.name.b h10 = kotlinReadOnly$iv5.h();
        kotlin.reflect.jvm.internal.impl.name.b h11 = kotlinReadOnly$iv5.h();
        k.b(h11, "kotlinReadOnly.packageFqName");
        kotlin.reflect.jvm.internal.impl.name.a mutableClassId$iv5 = new kotlin.reflect.jvm.internal.impl.name.a(h10, e.d(kotlinMutable$iv5, h11), false);
        kotlin.reflect.jvm.internal.impl.name.a kotlinReadOnly$iv6 = kotlin.reflect.jvm.internal.impl.name.a.m(eVar.P);
        k.b(kotlinReadOnly$iv6, "ClassId.topLevel(FQ_NAMES.listIterator)");
        kotlin.reflect.jvm.internal.impl.name.b kotlinMutable$iv6 = eVar.X;
        k.b(kotlinMutable$iv6, "FQ_NAMES.mutableListIterator");
        kotlin.reflect.jvm.internal.impl.name.b h12 = kotlinReadOnly$iv6.h();
        kotlin.reflect.jvm.internal.impl.name.b h13 = kotlinReadOnly$iv6.h();
        k.b(h13, "kotlinReadOnly.packageFqName");
        kotlin.reflect.jvm.internal.impl.name.a mutableClassId$iv6 = new kotlin.reflect.jvm.internal.impl.name.a(h12, e.d(kotlinMutable$iv6, h13), false);
        kotlin.reflect.jvm.internal.impl.name.a kotlinReadOnly$iv7 = kotlin.reflect.jvm.internal.impl.name.a.m(eVar.R);
        k.b(kotlinReadOnly$iv7, "ClassId.topLevel(FQ_NAMES.map)");
        kotlin.reflect.jvm.internal.impl.name.b kotlinMutable$iv7 = eVar.Z;
        k.b(kotlinMutable$iv7, "FQ_NAMES.mutableMap");
        kotlin.reflect.jvm.internal.impl.name.b h14 = kotlinReadOnly$iv7.h();
        kotlin.reflect.jvm.internal.impl.name.b h15 = kotlinReadOnly$iv7.h();
        k.b(h15, "kotlinReadOnly.packageFqName");
        kotlin.reflect.jvm.internal.impl.name.a mutableClassId$iv7 = new kotlin.reflect.jvm.internal.impl.name.a(h14, e.d(kotlinMutable$iv7, h15), false);
        kotlin.reflect.jvm.internal.impl.name.a kotlinReadOnly$iv8 = kotlin.reflect.jvm.internal.impl.name.a.m(eVar.R).d(eVar.S.g());
        k.b(kotlinReadOnly$iv8, "ClassId.topLevel(FQ_NAME…MES.mapEntry.shortName())");
        kotlin.reflect.jvm.internal.impl.name.b kotlinMutable$iv8 = eVar.a0;
        k.b(kotlinMutable$iv8, "FQ_NAMES.mutableMapEntry");
        kotlin.reflect.jvm.internal.impl.name.b h16 = kotlinReadOnly$iv8.h();
        kotlin.reflect.jvm.internal.impl.name.b h17 = kotlinReadOnly$iv8.h();
        k.b(h17, "kotlinReadOnly.packageFqName");
        List<a> j2 = q.j(new a(this_$iv.h(Iterable.class), kotlinReadOnly$iv, mutableClassId$iv), new a(this_$iv.h(Iterator.class), kotlinReadOnly$iv2, mutableClassId$iv2), new a(this_$iv.h(Collection.class), kotlinReadOnly$iv3, mutableClassId$iv3), new a(this_$iv.h(List.class), kotlinReadOnly$iv4, mutableClassId$iv4), new a(this_$iv.h(Set.class), kotlinReadOnly$iv5, mutableClassId$iv5), new a(this_$iv.h(ListIterator.class), kotlinReadOnly$iv6, mutableClassId$iv6), new a(this_$iv.h(Map.class), kotlinReadOnly$iv7, mutableClassId$iv7), new a(this_$iv.h(Map.Entry.class), kotlinReadOnly$iv8, new kotlin.reflect.jvm.internal.impl.name.a(h16, e.d(kotlinMutable$iv8, h17), false)));
        l = j2;
        kotlin.reflect.jvm.internal.impl.name.c cVar = eVar.a;
        k.b(cVar, "FQ_NAMES.any");
        this_$iv.g(Object.class, cVar);
        kotlin.reflect.jvm.internal.impl.name.c cVar2 = eVar.g;
        k.b(cVar2, "FQ_NAMES.string");
        this_$iv.g(String.class, cVar2);
        kotlin.reflect.jvm.internal.impl.name.c cVar3 = eVar.f;
        k.b(cVar3, "FQ_NAMES.charSequence");
        this_$iv.g(CharSequence.class, cVar3);
        kotlin.reflect.jvm.internal.impl.name.b bVar = eVar.t;
        k.b(bVar, "FQ_NAMES.throwable");
        this_$iv.f(Throwable.class, bVar);
        kotlin.reflect.jvm.internal.impl.name.c cVar4 = eVar.c;
        k.b(cVar4, "FQ_NAMES.cloneable");
        this_$iv.g(Cloneable.class, cVar4);
        kotlin.reflect.jvm.internal.impl.name.c cVar5 = eVar.q;
        k.b(cVar5, "FQ_NAMES.number");
        this_$iv.g(Number.class, cVar5);
        kotlin.reflect.jvm.internal.impl.name.b bVar2 = eVar.u;
        k.b(bVar2, "FQ_NAMES.comparable");
        this_$iv.f(Comparable.class, bVar2);
        kotlin.reflect.jvm.internal.impl.name.c cVar6 = eVar.r;
        k.b(cVar6, "FQ_NAMES._enum");
        this_$iv.g(Enum.class, cVar6);
        kotlin.reflect.jvm.internal.impl.name.b bVar3 = eVar.C;
        k.b(bVar3, "FQ_NAMES.annotation");
        this_$iv.f(Annotation.class, bVar3);
        for (a platformCollection : j2) {
            this_$iv.e(platformCollection);
        }
        for (d jvmType : d.values()) {
            kotlin.reflect.jvm.internal.impl.name.a m4 = kotlin.reflect.jvm.internal.impl.name.a.m(jvmType.getWrapperFqName());
            k.b(m4, "ClassId.topLevel(jvmType.wrapperFqName)");
            kotlin.reflect.jvm.internal.impl.name.a m5 = kotlin.reflect.jvm.internal.impl.name.a.m(g.S(jvmType.getPrimitiveType()));
            k.b(m5, "ClassId.topLevel(KotlinB…e(jvmType.primitiveType))");
            this_$iv.b(m4, m5);
        }
        for (kotlin.reflect.jvm.internal.impl.name.a classId : kotlin.reflect.jvm.internal.impl.builtins.c.b.a()) {
            kotlin.reflect.jvm.internal.impl.name.a m6 = kotlin.reflect.jvm.internal.impl.name.a.m(new kotlin.reflect.jvm.internal.impl.name.b("kotlin.jvm.internal." + classId.j().b() + "CompanionObject"));
            k.b(m6, "ClassId.topLevel(FqName(…g() + \"CompanionObject\"))");
            kotlin.reflect.jvm.internal.impl.name.a d2 = classId.d(h.c);
            k.b(d2, "classId.createNestedClas…AME_FOR_COMPANION_OBJECT)");
            this_$iv.b(m6, d2);
        }
        for (int i3 = 0; i3 < 23; i3++) {
            kotlin.reflect.jvm.internal.impl.name.a m7 = kotlin.reflect.jvm.internal.impl.name.a.m(new kotlin.reflect.jvm.internal.impl.name.b("kotlin.jvm.functions.Function" + i3));
            k.b(m7, "ClassId.topLevel(FqName(…m.functions.Function$i\"))");
            kotlin.reflect.jvm.internal.impl.name.a D = g.D(i3);
            k.b(D, "KotlinBuiltIns.getFunctionClassId(i)");
            this_$iv.b(m7, D);
            this_$iv.d(new kotlin.reflect.jvm.internal.impl.name.b(b + i3), g);
        }
        while (i2 < 22) {
            int i4 = i2;
            b.d kSuspendFunction = b.d.KSuspendFunction;
            this_$iv.d(new kotlin.reflect.jvm.internal.impl.name.b((kSuspendFunction.getPackageFqName().toString() + "." + kSuspendFunction.getClassNamePrefix()) + i4), g);
            i2 = i4 + 1;
        }
        kotlin.reflect.jvm.internal.impl.name.b l2 = g.h.b.l();
        k.b(l2, "FQ_NAMES.nothing.toSafe()");
        this_$iv.d(l2, this_$iv.h(Void.class));
    }

    private c() {
    }

    @NotNull
    public final kotlin.reflect.jvm.internal.impl.name.b l() {
        return f;
    }

    /* compiled from: JavaToKotlinClassMap.kt */
    public static final class a {
        @NotNull
        private final kotlin.reflect.jvm.internal.impl.name.a a;
        @NotNull
        private final kotlin.reflect.jvm.internal.impl.name.a b;
        @NotNull
        private final kotlin.reflect.jvm.internal.impl.name.a c;

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.name.a a() {
            return this.a;
        }

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.name.a b() {
            return this.b;
        }

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.name.a c() {
            return this.c;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return k.a(this.a, aVar.a) && k.a(this.b, aVar.b) && k.a(this.c, aVar.c);
        }

        public int hashCode() {
            kotlin.reflect.jvm.internal.impl.name.a aVar = this.a;
            int i = 0;
            int hashCode = (aVar != null ? aVar.hashCode() : 0) * 31;
            kotlin.reflect.jvm.internal.impl.name.a aVar2 = this.b;
            int hashCode2 = (hashCode + (aVar2 != null ? aVar2.hashCode() : 0)) * 31;
            kotlin.reflect.jvm.internal.impl.name.a aVar3 = this.c;
            if (aVar3 != null) {
                i = aVar3.hashCode();
            }
            return hashCode2 + i;
        }

        @NotNull
        public String toString() {
            return "PlatformMutabilityMapping(javaClass=" + this.a + ", kotlinReadOnly=" + this.b + ", kotlinMutable=" + this.c + ")";
        }

        public a(@NotNull kotlin.reflect.jvm.internal.impl.name.a javaClass, @NotNull kotlin.reflect.jvm.internal.impl.name.a kotlinReadOnly, @NotNull kotlin.reflect.jvm.internal.impl.name.a kotlinMutable) {
            k.f(javaClass, "javaClass");
            k.f(kotlinReadOnly, "kotlinReadOnly");
            k.f(kotlinMutable, "kotlinMutable");
            this.a = javaClass;
            this.b = kotlinReadOnly;
            this.c = kotlinMutable;
        }

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.name.a d() {
            return this.a;
        }
    }

    @NotNull
    public final List<a> m() {
        return l;
    }

    @Nullable
    public final kotlin.reflect.jvm.internal.impl.name.a v(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName) {
        k.f(fqName, "fqName");
        return h.get(fqName.j());
    }

    public static /* synthetic */ kotlin.reflect.jvm.internal.impl.descriptors.e w(c cVar, kotlin.reflect.jvm.internal.impl.name.b bVar, g gVar, Integer num, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            num = null;
        }
        return cVar.u(bVar, gVar, num);
    }

    @Nullable
    public final kotlin.reflect.jvm.internal.impl.descriptors.e u(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName, @NotNull g builtIns, @Nullable Integer functionTypeArity) {
        kotlin.reflect.jvm.internal.impl.name.a kotlinClassId;
        k.f(fqName, "fqName");
        k.f(builtIns, "builtIns");
        if (functionTypeArity == null || !k.a(fqName, f)) {
            kotlinClassId = v(fqName);
        } else {
            kotlinClassId = g.D(functionTypeArity.intValue());
        }
        if (kotlinClassId != null) {
            return builtIns.o(kotlinClassId.b());
        }
        return null;
    }

    @Nullable
    public final kotlin.reflect.jvm.internal.impl.name.a x(@NotNull kotlin.reflect.jvm.internal.impl.name.c kotlinFqName) {
        k.f(kotlinFqName, "kotlinFqName");
        if (n(kotlinFqName, a)) {
            return e;
        }
        if (n(kotlinFqName, c)) {
            return e;
        }
        if (n(kotlinFqName, b)) {
            return g;
        }
        if (n(kotlinFqName, d)) {
            return g;
        }
        return i.get(kotlinFqName);
    }

    private final boolean n(kotlin.reflect.jvm.internal.impl.name.c kotlinFqName, String prefix) {
        String b2 = kotlinFqName.b();
        k.b(b2, "kotlinFqName.asString()");
        String arityString = x.P0(b2, prefix, "");
        if (!(arityString.length() > 0) || x.L0(arityString, '0', false, 2, (Object) null)) {
            return false;
        }
        Integer arity = v.o(arityString);
        if (arity == null || arity.intValue() < 23) {
            return false;
        }
        return true;
    }

    private final void e(a platformMutabilityMapping) {
        kotlin.reflect.jvm.internal.impl.name.a javaClassId = platformMutabilityMapping.a();
        kotlin.reflect.jvm.internal.impl.name.a readOnlyClassId = platformMutabilityMapping.b();
        kotlin.reflect.jvm.internal.impl.name.a mutableClassId = platformMutabilityMapping.c();
        b(javaClassId, readOnlyClassId);
        kotlin.reflect.jvm.internal.impl.name.b b2 = mutableClassId.b();
        k.b(b2, "mutableClassId.asSingleFqName()");
        d(b2, javaClassId);
        kotlin.reflect.jvm.internal.impl.name.b readOnlyFqName = readOnlyClassId.b();
        k.b(readOnlyFqName, "readOnlyClassId.asSingleFqName()");
        kotlin.reflect.jvm.internal.impl.name.b b3 = mutableClassId.b();
        k.b(b3, "mutableClassId.asSingleFqName()");
        kotlin.reflect.jvm.internal.impl.name.b mutableFqName = b3;
        HashMap<kotlin.reflect.jvm.internal.impl.name.c, kotlin.reflect.jvm.internal.impl.name.b> hashMap = j;
        kotlin.reflect.jvm.internal.impl.name.c j2 = mutableClassId.b().j();
        k.b(j2, "mutableClassId.asSingleFqName().toUnsafe()");
        hashMap.put(j2, readOnlyFqName);
        HashMap<kotlin.reflect.jvm.internal.impl.name.c, kotlin.reflect.jvm.internal.impl.name.b> hashMap2 = k;
        kotlin.reflect.jvm.internal.impl.name.c j3 = readOnlyFqName.j();
        k.b(j3, "readOnlyFqName.toUnsafe()");
        hashMap2.put(j3, mutableFqName);
    }

    private final void b(kotlin.reflect.jvm.internal.impl.name.a javaClassId, kotlin.reflect.jvm.internal.impl.name.a kotlinClassId) {
        c(javaClassId, kotlinClassId);
        kotlin.reflect.jvm.internal.impl.name.b b2 = kotlinClassId.b();
        k.b(b2, "kotlinClassId.asSingleFqName()");
        d(b2, javaClassId);
    }

    private final void g(Class<?> javaClass, kotlin.reflect.jvm.internal.impl.name.c kotlinFqName) {
        kotlin.reflect.jvm.internal.impl.name.b l2 = kotlinFqName.l();
        k.b(l2, "kotlinFqName.toSafe()");
        f(javaClass, l2);
    }

    private final void f(Class<?> javaClass, kotlin.reflect.jvm.internal.impl.name.b kotlinFqName) {
        kotlin.reflect.jvm.internal.impl.name.a h2 = h(javaClass);
        kotlin.reflect.jvm.internal.impl.name.a m2 = kotlin.reflect.jvm.internal.impl.name.a.m(kotlinFqName);
        k.b(m2, "ClassId.topLevel(kotlinFqName)");
        b(h2, m2);
    }

    private final void c(kotlin.reflect.jvm.internal.impl.name.a javaClassId, kotlin.reflect.jvm.internal.impl.name.a kotlinClassId) {
        HashMap<kotlin.reflect.jvm.internal.impl.name.c, kotlin.reflect.jvm.internal.impl.name.a> hashMap = h;
        kotlin.reflect.jvm.internal.impl.name.c j2 = javaClassId.b().j();
        k.b(j2, "javaClassId.asSingleFqName().toUnsafe()");
        hashMap.put(j2, kotlinClassId);
    }

    private final void d(kotlin.reflect.jvm.internal.impl.name.b kotlinFqNameUnsafe, kotlin.reflect.jvm.internal.impl.name.a javaClassId) {
        HashMap<kotlin.reflect.jvm.internal.impl.name.c, kotlin.reflect.jvm.internal.impl.name.a> hashMap = i;
        kotlin.reflect.jvm.internal.impl.name.c j2 = kotlinFqNameUnsafe.j();
        k.b(j2, "kotlinFqNameUnsafe.toUnsafe()");
        hashMap.put(j2, javaClassId);
    }

    @NotNull
    public final Collection<kotlin.reflect.jvm.internal.impl.descriptors.e> y(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName, @NotNull g builtIns) {
        k.f(fqName, "fqName");
        k.f(builtIns, "builtIns");
        kotlin.reflect.jvm.internal.impl.descriptors.e kotlinAnalog = w(this, fqName, builtIns, (Integer) null, 4, (Object) null);
        if (kotlinAnalog == null) {
            return o0.d();
        }
        kotlin.reflect.jvm.internal.impl.name.b kotlinMutableAnalogFqName = k.get(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.k(kotlinAnalog));
        if (kotlinMutableAnalogFqName == null) {
            return n0.c(kotlinAnalog);
        }
        k.b(kotlinMutableAnalogFqName, "readOnlyToMutable[kotlin…eturn setOf(kotlinAnalog)");
        kotlin.reflect.jvm.internal.impl.descriptors.e o = builtIns.o(kotlinMutableAnalogFqName);
        k.b(o, "builtIns.getBuiltInClass…otlinMutableAnalogFqName)");
        return q.j(kotlinAnalog, o);
    }

    public final boolean p(@Nullable kotlin.reflect.jvm.internal.impl.name.c fqNameUnsafe) {
        HashMap<kotlin.reflect.jvm.internal.impl.name.c, kotlin.reflect.jvm.internal.impl.name.b> hashMap = j;
        if (hashMap != null) {
            return hashMap.containsKey(fqNameUnsafe);
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, *>");
    }

    public final boolean o(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e mutable) {
        k.f(mutable, "mutable");
        return p(kotlin.reflect.jvm.internal.impl.resolve.c.m(mutable));
    }

    public final boolean q(@NotNull b0 type) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        kotlin.reflect.jvm.internal.impl.descriptors.e classDescriptor = c1.f(type);
        return classDescriptor != null && o(classDescriptor);
    }

    public final boolean s(@Nullable kotlin.reflect.jvm.internal.impl.name.c fqNameUnsafe) {
        HashMap<kotlin.reflect.jvm.internal.impl.name.c, kotlin.reflect.jvm.internal.impl.name.b> hashMap = k;
        if (hashMap != null) {
            return hashMap.containsKey(fqNameUnsafe);
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, *>");
    }

    public final boolean r(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e readOnly) {
        k.f(readOnly, "readOnly");
        return s(kotlin.reflect.jvm.internal.impl.resolve.c.m(readOnly));
    }

    public final boolean t(@NotNull b0 type) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        kotlin.reflect.jvm.internal.impl.descriptors.e classDescriptor = c1.f(type);
        return classDescriptor != null && r(classDescriptor);
    }

    @NotNull
    public final kotlin.reflect.jvm.internal.impl.descriptors.e i(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e mutable) {
        k.f(mutable, "mutable");
        return k(mutable, j, "mutable");
    }

    @NotNull
    public final kotlin.reflect.jvm.internal.impl.descriptors.e j(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e readOnly) {
        k.f(readOnly, "readOnly");
        return k(readOnly, k, "read-only");
    }

    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.name.a h(Class<?> clazz) {
        if (!clazz.isPrimitive() && !clazz.isArray()) {
            Class outer = clazz.getDeclaringClass();
            if (outer == null) {
                kotlin.reflect.jvm.internal.impl.name.a m2 = kotlin.reflect.jvm.internal.impl.name.a.m(new kotlin.reflect.jvm.internal.impl.name.b(clazz.getCanonicalName()));
                k.b(m2, "ClassId.topLevel(FqName(clazz.canonicalName))");
                return m2;
            }
            kotlin.reflect.jvm.internal.impl.name.a d2 = h(outer).d(f.f(clazz.getSimpleName()));
            k.b(d2, "classId(outer).createNes…tifier(clazz.simpleName))");
            return d2;
        }
        throw new AssertionError("Invalid class: " + clazz);
    }

    private final kotlin.reflect.jvm.internal.impl.descriptors.e k(kotlin.reflect.jvm.internal.impl.descriptors.e descriptor, Map<kotlin.reflect.jvm.internal.impl.name.c, kotlin.reflect.jvm.internal.impl.name.b> map, String mutabilityKindName) {
        kotlin.reflect.jvm.internal.impl.name.b oppositeClassFqName = map.get(kotlin.reflect.jvm.internal.impl.resolve.c.m(descriptor));
        if (oppositeClassFqName != null) {
            kotlin.reflect.jvm.internal.impl.descriptors.e o = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.h(descriptor).o(oppositeClassFqName);
            k.b(o, "descriptor.builtIns.getB…Name(oppositeClassFqName)");
            return o;
        }
        throw new IllegalArgumentException("Given class " + descriptor + " is not a " + mutabilityKindName + " collection");
    }
}
