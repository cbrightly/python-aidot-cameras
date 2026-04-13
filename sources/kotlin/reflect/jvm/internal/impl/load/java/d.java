package kotlin.reflect.jvm.internal.impl.load.java;

import com.leedarson.bean.Constants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.k0;
import kotlin.collections.l0;
import kotlin.collections.o0;
import kotlin.collections.p0;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.load.kotlin.v;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.t;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: specialBuiltinMembers.kt */
public final class d {
    private static final List<u> a;
    private static final List<String> b;
    @NotNull
    private static final List<String> c;
    private static final Map<u, b> d;
    private static final Map<String, b> e;
    private static final Set<f> f;
    @NotNull
    private static final Set<String> g;
    public static final d h = new d();

    /* compiled from: specialBuiltinMembers.kt */
    public static final class c extends l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.descriptors.b, Boolean> {
        public static final c INSTANCE = new c();

        c() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((kotlin.reflect.jvm.internal.impl.descriptors.b) obj));
        }

        public final boolean invoke(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b it) {
            k.f(it, "it");
            return d.h.b(it);
        }
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.load.java.d$d  reason: collision with other inner class name */
    /* compiled from: specialBuiltinMembers.kt */
    public static final class C0359d extends l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.descriptors.b, Boolean> {
        public static final C0359d INSTANCE = new C0359d();

        C0359d() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((kotlin.reflect.jvm.internal.impl.descriptors.b) obj));
        }

        public final boolean invoke(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b it) {
            k.f(it, "it");
            return (it instanceof u) && d.h.b(it);
        }
    }

    static {
        Iterable<String> $this$mapTo$iv$iv = o0.g("containsAll", "removeAll", "retainAll");
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (String it : $this$mapTo$iv$iv) {
            String desc = kotlin.reflect.jvm.internal.impl.resolve.jvm.d.BOOLEAN.getDesc();
            k.b(desc, "JvmPrimitiveType.BOOLEAN.desc");
            arrayList.add(w.n("java/util/Collection", it, "Ljava/util/Collection;", desc));
        }
        a = arrayList;
        Iterable<u> $this$mapTo$iv$iv2 = arrayList;
        ArrayList arrayList2 = new ArrayList(r.r($this$mapTo$iv$iv2, 10));
        for (u it2 : $this$mapTo$iv$iv2) {
            arrayList2.add(it2.b());
        }
        b = arrayList2;
        Iterable<u> $this$mapTo$iv$iv3 = a;
        ArrayList arrayList3 = new ArrayList(r.r($this$mapTo$iv$iv3, 10));
        for (u it3 : $this$mapTo$iv$iv3) {
            arrayList3.add(it3.a().b());
        }
        c = arrayList3;
        v $this$signatures = v.a;
        String i = $this$signatures.i("Collection");
        kotlin.reflect.jvm.internal.impl.resolve.jvm.d dVar = kotlin.reflect.jvm.internal.impl.resolve.jvm.d.BOOLEAN;
        String desc2 = dVar.getDesc();
        k.b(desc2, "JvmPrimitiveType.BOOLEAN.desc");
        u c2 = w.n(i, "contains", "Ljava/lang/Object;", desc2);
        b bVar = b.FALSE;
        String i2 = $this$signatures.i("Collection");
        String desc3 = dVar.getDesc();
        k.b(desc3, "JvmPrimitiveType.BOOLEAN.desc");
        String i3 = $this$signatures.i(Constants.SERVICE_MAP);
        String desc4 = dVar.getDesc();
        k.b(desc4, "JvmPrimitiveType.BOOLEAN.desc");
        String i4 = $this$signatures.i(Constants.SERVICE_MAP);
        String desc5 = dVar.getDesc();
        k.b(desc5, "JvmPrimitiveType.BOOLEAN.desc");
        String i5 = $this$signatures.i(Constants.SERVICE_MAP);
        String desc6 = dVar.getDesc();
        k.b(desc6, "JvmPrimitiveType.BOOLEAN.desc");
        u c3 = w.n($this$signatures.i(Constants.SERVICE_MAP), "get", "Ljava/lang/Object;", "Ljava/lang/Object;");
        b bVar2 = b.NULL;
        String i6 = $this$signatures.i("List");
        kotlin.reflect.jvm.internal.impl.resolve.jvm.d dVar2 = kotlin.reflect.jvm.internal.impl.resolve.jvm.d.INT;
        String desc7 = dVar2.getDesc();
        k.b(desc7, "JvmPrimitiveType.INT.desc");
        u c4 = w.n(i6, "indexOf", "Ljava/lang/Object;", desc7);
        b bVar3 = b.INDEX;
        String i7 = $this$signatures.i("List");
        String desc8 = dVar2.getDesc();
        k.b(desc8, "JvmPrimitiveType.INT.desc");
        d = l0.h(t.a(c2, bVar), t.a(w.n(i2, "remove", "Ljava/lang/Object;", desc3), bVar), t.a(w.n(i3, "containsKey", "Ljava/lang/Object;", desc4), bVar), t.a(w.n(i4, "containsValue", "Ljava/lang/Object;", desc5), bVar), t.a(w.n(i5, "remove", "Ljava/lang/Object;Ljava/lang/Object;", desc6), bVar), t.a(w.n($this$signatures.i(Constants.SERVICE_MAP), "getOrDefault", "Ljava/lang/Object;Ljava/lang/Object;", "Ljava/lang/Object;"), b.MAP_GET_OR_DEFAULT), t.a(c3, bVar2), t.a(w.n($this$signatures.i(Constants.SERVICE_MAP), "remove", "Ljava/lang/Object;", "Ljava/lang/Object;"), bVar2), t.a(c4, bVar3), t.a(w.n(i7, "lastIndexOf", "Ljava/lang/Object;", desc8), bVar3));
        Map $this$mapKeysTo$iv$iv = d;
        Map destination$iv$iv = new LinkedHashMap(k0.b($this$mapKeysTo$iv$iv.size()));
        for (T next : $this$mapKeysTo$iv$iv.entrySet()) {
            destination$iv$iv.put(((u) ((Map.Entry) next).getKey()).b(), ((Map.Entry) next).getValue());
        }
        e = destination$iv$iv;
        Iterable i8 = p0.i(d.keySet(), a);
        Iterable<u> $this$map$iv = i8;
        Collection destination$iv$iv2 = new ArrayList(r.r($this$map$iv, 10));
        for (u it4 : $this$map$iv) {
            destination$iv$iv2.add(it4.a());
        }
        f = y.H0(destination$iv$iv2);
        Iterable<u> $this$mapTo$iv$iv4 = i8;
        Collection destination$iv$iv3 = new ArrayList(r.r($this$mapTo$iv$iv4, 10));
        for (u it5 : $this$mapTo$iv$iv4) {
            destination$iv$iv3.add(it5.b());
        }
        g = y.H0(destination$iv$iv3);
    }

    private d() {
    }

    /* compiled from: specialBuiltinMembers.kt */
    public enum b {
        NULL((String) null),
        INDEX(-1),
        FALSE(false);
        
        @Nullable
        private final Object defaultValue;

        private b(Object defaultValue2) {
            this.defaultValue = defaultValue2;
        }

        /* compiled from: specialBuiltinMembers.kt */
        public static final class a extends b {
            a(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
                super($enum_name_or_ordinal$0, $enum_name_or_ordinal$1, (Object) null, (DefaultConstructorMarker) null);
            }
        }
    }

    /* access modifiers changed from: private */
    public final boolean b(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b $this$hasErasedValueParametersInJava) {
        return y.M(g, kotlin.reflect.jvm.internal.impl.load.kotlin.t.d($this$hasErasedValueParametersInJava));
    }

    @Nullable
    public static final u c(@NotNull u functionDescriptor) {
        k.f(functionDescriptor, "functionDescriptor");
        d dVar = h;
        f name = functionDescriptor.getName();
        k.b(name, "functionDescriptor.name");
        if (!dVar.d(name)) {
            return null;
        }
        return (u) kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.e(functionDescriptor, false, c.INSTANCE, 1, (Object) null);
    }

    public final boolean d(@NotNull f $this$sameAsBuiltinMethodWithErasedValueParameters) {
        k.f($this$sameAsBuiltinMethodWithErasedValueParameters, "$this$sameAsBuiltinMethodWithErasedValueParameters");
        return f.contains($this$sameAsBuiltinMethodWithErasedValueParameters);
    }

    /* compiled from: specialBuiltinMembers.kt */
    public enum a {
        ONE_COLLECTION_PARAMETER("Ljava/util/Collection<+Ljava/lang/Object;>;", false),
        OBJECT_PARAMETER_NON_GENERIC((String) null, true),
        OBJECT_PARAMETER_GENERIC("Ljava/lang/Object;", true);
        
        private final boolean isObjectReplacedWithTypeParameter;
        @Nullable
        private final String valueParametersSignature;

        private a(String valueParametersSignature2, boolean isObjectReplacedWithTypeParameter2) {
            this.valueParametersSignature = valueParametersSignature2;
            this.isObjectReplacedWithTypeParameter = isObjectReplacedWithTypeParameter2;
        }
    }

    @Nullable
    public static final a e(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b $this$getSpecialSignatureInfo) {
        kotlin.reflect.jvm.internal.impl.descriptors.b e2;
        String builtinSignature;
        k.f($this$getSpecialSignatureInfo, "$this$getSpecialSignatureInfo");
        if (!f.contains($this$getSpecialSignatureInfo.getName()) || (e2 = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.e($this$getSpecialSignatureInfo, false, C0359d.INSTANCE, 1, (Object) null)) == null || (builtinSignature = kotlin.reflect.jvm.internal.impl.load.kotlin.t.d(e2)) == null) {
            return null;
        }
        if (b.contains(builtinSignature)) {
            return a.ONE_COLLECTION_PARAMETER;
        }
        if (((b) l0.g(e, builtinSignature)) == b.NULL) {
            return a.OBJECT_PARAMETER_GENERIC;
        }
        return a.OBJECT_PARAMETER_NON_GENERIC;
    }
}
