package kotlin.jvm.internal;

import com.meituan.robust.Constants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.KotlinNothingValueException;
import kotlin.collections.k0;
import kotlin.collections.l0;
import kotlin.collections.q;
import kotlin.jvm.functions.d;
import kotlin.jvm.functions.g;
import kotlin.jvm.functions.h;
import kotlin.jvm.functions.i;
import kotlin.jvm.functions.j;
import kotlin.jvm.functions.k;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.m;
import kotlin.jvm.functions.n;
import kotlin.jvm.functions.o;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.r;
import kotlin.jvm.functions.s;
import kotlin.jvm.functions.t;
import kotlin.jvm.functions.u;
import kotlin.jvm.functions.v;
import kotlin.jvm.functions.w;
import kotlin.reflect.b;
import kotlin.reflect.c;
import kotlin.reflect.f;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ClassReference.kt */
public final class e implements c<Object>, d {
    /* access modifiers changed from: private */
    public static final Map<Class<? extends kotlin.c<?>>, Integer> c;
    private static final HashMap<String, String> d;
    private static final HashMap<String, String> f;
    /* access modifiers changed from: private */
    public static final HashMap<String, String> q;
    private static final Map<String, String> x;
    @NotNull
    public static final a y = new a((DefaultConstructorMarker) null);
    @NotNull
    private final Class<?> z;

    public e(@NotNull Class<?> jClass) {
        k.e(jClass, "jClass");
        this.z = jClass;
    }

    @NotNull
    public Class<?> b() {
        return this.z;
    }

    @Nullable
    public String l() {
        return y.a(b());
    }

    @NotNull
    public Collection<b<?>> g() {
        e();
        throw new KotlinNothingValueException();
    }

    @NotNull
    public Collection<f<Object>> f() {
        e();
        throw new KotlinNothingValueException();
    }

    @NotNull
    public Collection<c<?>> i() {
        e();
        throw new KotlinNothingValueException();
    }

    @Nullable
    public Object j() {
        e();
        throw new KotlinNothingValueException();
    }

    public boolean k(@Nullable Object value) {
        return y.b(value, b());
    }

    private final Void e() {
        throw new kotlin.jvm.b();
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof e) && k.a(kotlin.jvm.a.c(this), kotlin.jvm.a.c((c) other));
    }

    public int hashCode() {
        return kotlin.jvm.a.c(this).hashCode();
    }

    @NotNull
    public String toString() {
        return b().toString() + " (Kotlin reflection is not available)";
    }

    /* compiled from: ClassReference.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @Nullable
        public final String a(@NotNull Class<?> jClass) {
            String str;
            k.e(jClass, "jClass");
            String str2 = null;
            if (jClass.isAnonymousClass() || jClass.isLocalClass()) {
                return null;
            }
            if (jClass.isArray()) {
                Class componentType = jClass.getComponentType();
                k.d(componentType, "componentType");
                if (componentType.isPrimitive() && (str = (String) e.q.get(componentType.getName())) != null) {
                    str2 = str + "Array";
                }
                if (str2 != null) {
                    return str2;
                }
                return "kotlin.Array";
            }
            String str3 = (String) e.q.get(jClass.getName());
            if (str3 != null) {
                return str3;
            }
            return jClass.getCanonicalName();
        }

        public final boolean b(@Nullable Object value, @NotNull Class<?> jClass) {
            k.e(jClass, "jClass");
            Map d = e.c;
            if (d != null) {
                Integer num = (Integer) d.get(jClass);
                if (num != null) {
                    return e0.k(value, num.intValue());
                }
                return (jClass.isPrimitive() != 0 ? kotlin.jvm.a.c(kotlin.jvm.a.e(jClass)) : jClass).isInstance(value);
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
        }
    }

    static {
        Iterable $this$mapIndexedTo$iv$iv = q.j(kotlin.jvm.functions.a.class, l.class, p.class, kotlin.jvm.functions.q.class, r.class, s.class, t.class, u.class, v.class, w.class, kotlin.jvm.functions.b.class, kotlin.jvm.functions.c.class, d.class, kotlin.jvm.functions.e.class, kotlin.jvm.functions.f.class, g.class, h.class, i.class, j.class, k.class, m.class, n.class, o.class);
        Collection destination$iv$iv = new ArrayList(kotlin.collections.r.r($this$mapIndexedTo$iv$iv, 10));
        int index$iv$iv = 0;
        for (Object item$iv$iv : $this$mapIndexedTo$iv$iv) {
            int index$iv$iv2 = index$iv$iv + 1;
            if (index$iv$iv < 0) {
                q.q();
            }
            destination$iv$iv.add(kotlin.t.a((Class) item$iv$iv, Integer.valueOf(index$iv$iv)));
            index$iv$iv = index$iv$iv2;
        }
        c = l0.o(destination$iv$iv);
        HashMap hashMap = new HashMap();
        HashMap $this$apply = hashMap;
        $this$apply.put("boolean", "kotlin.Boolean");
        $this$apply.put(Constants.CHAR, "kotlin.Char");
        $this$apply.put(Constants.BYTE, "kotlin.Byte");
        $this$apply.put(Constants.SHORT, "kotlin.Short");
        $this$apply.put(Constants.INT, "kotlin.Int");
        $this$apply.put("float", "kotlin.Float");
        $this$apply.put(Constants.LONG, "kotlin.Long");
        $this$apply.put(Constants.DOUBLE, "kotlin.Double");
        d = hashMap;
        HashMap hashMap2 = new HashMap();
        HashMap $this$apply2 = hashMap2;
        $this$apply2.put(Constants.LANG_BOOLEAN, "kotlin.Boolean");
        $this$apply2.put("java.lang.Character", "kotlin.Char");
        $this$apply2.put(Constants.LANG_BYTE, "kotlin.Byte");
        $this$apply2.put(Constants.LANG_SHORT, "kotlin.Short");
        $this$apply2.put(Constants.LANG_INT, "kotlin.Int");
        $this$apply2.put(Constants.LANG_FLOAT, "kotlin.Float");
        $this$apply2.put(Constants.LANG_LONG, "kotlin.Long");
        $this$apply2.put(Constants.LANG_DOUBLE, "kotlin.Double");
        f = hashMap2;
        HashMap hashMap3 = new HashMap();
        HashMap $this$apply3 = hashMap3;
        $this$apply3.put("java.lang.Object", "kotlin.Any");
        $this$apply3.put("java.lang.String", "kotlin.String");
        $this$apply3.put("java.lang.CharSequence", "kotlin.CharSequence");
        $this$apply3.put("java.lang.Throwable", "kotlin.Throwable");
        $this$apply3.put("java.lang.Cloneable", "kotlin.Cloneable");
        $this$apply3.put("java.lang.Number", "kotlin.Number");
        $this$apply3.put("java.lang.Comparable", "kotlin.Comparable");
        $this$apply3.put("java.lang.Enum", "kotlin.Enum");
        $this$apply3.put("java.lang.annotation.Annotation", "kotlin.Annotation");
        $this$apply3.put("java.lang.Iterable", "kotlin.collections.Iterable");
        $this$apply3.put("java.util.Iterator", "kotlin.collections.Iterator");
        $this$apply3.put("java.util.Collection", "kotlin.collections.Collection");
        $this$apply3.put("java.util.List", "kotlin.collections.List");
        $this$apply3.put("java.util.Set", "kotlin.collections.Set");
        $this$apply3.put("java.util.ListIterator", "kotlin.collections.ListIterator");
        $this$apply3.put("java.util.Map", "kotlin.collections.Map");
        $this$apply3.put("java.util.Map$Entry", "kotlin.collections.Map.Entry");
        $this$apply3.put("kotlin.jvm.internal.StringCompanionObject", "kotlin.String.Companion");
        $this$apply3.put("kotlin.jvm.internal.EnumCompanionObject", "kotlin.Enum.Companion");
        $this$apply3.putAll(hashMap);
        $this$apply3.putAll(hashMap2);
        Iterable<String> $this$associateTo$iv = hashMap.values();
        k.d($this$associateTo$iv, "primitiveFqNames.values");
        for (String kotlinName : $this$associateTo$iv) {
            StringBuilder sb = new StringBuilder();
            sb.append("kotlin.jvm.internal.");
            k.d(kotlinName, "kotlinName");
            sb.append(x.U0(kotlinName, '.', (String) null, 2, (Object) null));
            sb.append("CompanionObject");
            String sb2 = sb.toString();
            kotlin.n a2 = kotlin.t.a(sb2, kotlinName + ".Companion");
            $this$apply3.put(a2.getFirst(), a2.getSecond());
        }
        for (Map.Entry next : c.entrySet()) {
            int arity = ((Number) next.getValue()).intValue();
            String name = ((Class) next.getKey()).getName();
            $this$apply3.put(name, "kotlin.Function" + arity);
        }
        q = hashMap3;
        Map $this$mapValuesTo$iv$iv = hashMap3;
        Map destination$iv$iv2 = new LinkedHashMap(k0.b($this$mapValuesTo$iv$iv.size()));
        for (T next2 : $this$mapValuesTo$iv$iv.entrySet()) {
            destination$iv$iv2.put(((Map.Entry) next2).getKey(), x.U0((String) ((Map.Entry) next2).getValue(), '.', (String) null, 2, (Object) null));
        }
        x = destination$iv$iv2;
    }
}
