package kotlin.reflect.jvm.internal.calls;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AnnotationConstructorCaller.kt */
public final class b {
    /* access modifiers changed from: private */
    public static final Object f(@Nullable Object $this$transformKotlinToJvm, Class<?> expectedType) {
        Object result;
        if ($this$transformKotlinToJvm instanceof Class) {
            return null;
        }
        if ($this$transformKotlinToJvm instanceof kotlin.reflect.c) {
            result = kotlin.jvm.a.b((kotlin.reflect.c) $this$transformKotlinToJvm);
        } else if (!($this$transformKotlinToJvm instanceof Object[])) {
            result = $this$transformKotlinToJvm;
        } else if (((Object[]) $this$transformKotlinToJvm) instanceof Class[]) {
            return null;
        } else {
            if (!(((Object[]) $this$transformKotlinToJvm) instanceof kotlin.reflect.c[])) {
                result = (Object[]) $this$transformKotlinToJvm;
            } else if ($this$transformKotlinToJvm != null) {
                kotlin.reflect.c[] cVarArr = (kotlin.reflect.c[]) $this$transformKotlinToJvm;
                ArrayList arrayList = new ArrayList(cVarArr.length);
                for (kotlin.reflect.c b : cVarArr) {
                    arrayList.add(kotlin.jvm.a.b(b));
                }
                ArrayList arrayList2 = arrayList;
                Object[] array = arrayList.toArray(new Class[0]);
                if (array != null) {
                    result = array;
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.reflect.KClass<*>>");
            }
        }
        if (expectedType.isInstance(result)) {
            return result;
        }
        return null;
    }

    /* access modifiers changed from: private */
    public static final Void e(int index, String name, Class<?> expectedJvmType) {
        kotlin.reflect.c kotlinClass;
        String typeString;
        if (k.a(expectedJvmType, Class.class)) {
            kotlinClass = a0.b(kotlin.reflect.c.class);
        } else if (!expectedJvmType.isArray() || !k.a(expectedJvmType.getComponentType(), Class.class)) {
            kotlinClass = kotlin.jvm.a.e(expectedJvmType);
        } else {
            kotlinClass = a0.b(kotlin.reflect.c[].class);
        }
        if (k.a(kotlinClass.l(), a0.b(Object[].class).l())) {
            StringBuilder sb = new StringBuilder();
            sb.append(kotlinClass.l());
            sb.append('<');
            Class<?> componentType = kotlin.jvm.a.b(kotlinClass).getComponentType();
            k.b(componentType, "kotlinClass.java.componentType");
            sb.append(kotlin.jvm.a.e(componentType).l());
            sb.append('>');
            typeString = sb.toString();
        } else {
            typeString = kotlinClass.l();
        }
        throw new IllegalArgumentException("Argument #" + index + ' ' + name + " is not of the required type " + typeString);
    }

    public static /* synthetic */ Object d(Class cls, Map map, List $this$map$iv, int i, Object obj) {
        if ((i & 4) != 0) {
            Iterable<String> $this$mapTo$iv$iv = map.keySet();
            ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (String name : $this$mapTo$iv$iv) {
                arrayList.add(cls.getDeclaredMethod(name, new Class[0]));
            }
            $this$map$iv = arrayList;
        }
        return c(cls, map, $this$map$iv);
    }

    /* compiled from: AnnotationConstructorCaller.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<Object, Boolean> {
        final /* synthetic */ Class $annotationClass;
        final /* synthetic */ List $methods;
        final /* synthetic */ Map $values;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(Class cls, List list, Map map) {
            super(1);
            this.$annotationClass = cls;
            this.$methods = list;
            this.$values = map;
        }

        public final boolean invoke(@Nullable Object other) {
            Iterable $this$all$iv;
            boolean z;
            kotlin.reflect.c a;
            Class cls = null;
            Annotation annotation = (Annotation) (!(other instanceof Annotation) ? null : other);
            if (!(annotation == null || (a = kotlin.jvm.a.a(annotation)) == null)) {
                cls = kotlin.jvm.a.b(a);
            }
            if (k.a(cls, this.$annotationClass)) {
                List list = this.$methods;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator it = list.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            $this$all$iv = 1;
                            break;
                        }
                        Method method = (Method) it.next();
                        Object ours = this.$values.get(method.getName());
                        Object theirs = method.invoke(other, new Object[0]);
                        if (ours instanceof boolean[]) {
                            boolean[] zArr = (boolean[]) ours;
                            if (theirs != null) {
                                z = Arrays.equals(zArr, (boolean[]) theirs);
                                continue;
                            } else {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.BooleanArray");
                            }
                        } else if (ours instanceof char[]) {
                            char[] cArr = (char[]) ours;
                            if (theirs != null) {
                                z = Arrays.equals(cArr, (char[]) theirs);
                                continue;
                            } else {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharArray");
                            }
                        } else if (ours instanceof byte[]) {
                            byte[] bArr = (byte[]) ours;
                            if (theirs != null) {
                                z = Arrays.equals(bArr, (byte[]) theirs);
                                continue;
                            } else {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.ByteArray");
                            }
                        } else if (ours instanceof short[]) {
                            short[] sArr = (short[]) ours;
                            if (theirs != null) {
                                z = Arrays.equals(sArr, (short[]) theirs);
                                continue;
                            } else {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.ShortArray");
                            }
                        } else if (ours instanceof int[]) {
                            int[] iArr = (int[]) ours;
                            if (theirs != null) {
                                z = Arrays.equals(iArr, (int[]) theirs);
                                continue;
                            } else {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.IntArray");
                            }
                        } else if (ours instanceof float[]) {
                            float[] fArr = (float[]) ours;
                            if (theirs != null) {
                                z = Arrays.equals(fArr, (float[]) theirs);
                                continue;
                            } else {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.FloatArray");
                            }
                        } else if (ours instanceof long[]) {
                            long[] jArr = (long[]) ours;
                            if (theirs != null) {
                                z = Arrays.equals(jArr, (long[]) theirs);
                                continue;
                            } else {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.LongArray");
                            }
                        } else if (ours instanceof double[]) {
                            double[] dArr = (double[]) ours;
                            if (theirs != null) {
                                z = Arrays.equals(dArr, (double[]) theirs);
                                continue;
                            } else {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.DoubleArray");
                            }
                        } else if (ours instanceof Object[]) {
                            Object[] objArr = (Object[]) ours;
                            if (theirs != null) {
                                z = Arrays.equals(objArr, (Object[]) theirs);
                                continue;
                            } else {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<*>");
                            }
                        } else {
                            z = k.a(ours, theirs);
                            continue;
                        }
                        if (!z) {
                            $this$all$iv = null;
                            break;
                        }
                    }
                } else {
                    $this$all$iv = 1;
                }
                if ($this$all$iv != null) {
                    return true;
                }
            }
            return false;
        }
    }

    @NotNull
    public static final <T> T c(@NotNull Class<T> annotationClass, @NotNull Map<String, ? extends Object> values, @NotNull List<Method> methods) {
        k.f(annotationClass, "annotationClass");
        k.f(values, "values");
        k.f(methods, "methods");
        a $fun$equals$2 = new a(annotationClass, methods, values);
        g hashCode = i.b(new C0331b(values));
        g toString = i.b(new d(annotationClass, values));
        Object result = Proxy.newProxyInstance(annotationClass.getClassLoader(), new Class[]{annotationClass}, new c(annotationClass, toString, (kotlin.reflect.k) null, hashCode, (kotlin.reflect.k) null, $fun$equals$2, values));
        if (result != null) {
            return result;
        }
        throw new TypeCastException("null cannot be cast to non-null type T");
    }

    /* renamed from: kotlin.reflect.jvm.internal.calls.b$b  reason: collision with other inner class name */
    /* compiled from: AnnotationConstructorCaller.kt */
    public static final class C0331b extends l implements kotlin.jvm.functions.a<Integer> {
        final /* synthetic */ Map $values;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0331b(Map map) {
            super(0);
            this.$values = map;
        }

        public final int invoke() {
            int valueHash;
            int sum$iv = 0;
            for (Map.Entry entry : this.$values.entrySet()) {
                String key = (String) entry.getKey();
                Object value = entry.getValue();
                if (value instanceof boolean[]) {
                    valueHash = Arrays.hashCode((boolean[]) value);
                } else if (value instanceof char[]) {
                    valueHash = Arrays.hashCode((char[]) value);
                } else if (value instanceof byte[]) {
                    valueHash = Arrays.hashCode((byte[]) value);
                } else if (value instanceof short[]) {
                    valueHash = Arrays.hashCode((short[]) value);
                } else if (value instanceof int[]) {
                    valueHash = Arrays.hashCode((int[]) value);
                } else if (value instanceof float[]) {
                    valueHash = Arrays.hashCode((float[]) value);
                } else if (value instanceof long[]) {
                    valueHash = Arrays.hashCode((long[]) value);
                } else if (value instanceof double[]) {
                    valueHash = Arrays.hashCode((double[]) value);
                } else if (value instanceof Object[]) {
                    valueHash = Arrays.hashCode((Object[]) value);
                } else {
                    valueHash = value.hashCode();
                }
                sum$iv += (key.hashCode() * NeedPermissionEvent.PER_IPC_SPEAK_PERM) ^ valueHash;
            }
            return sum$iv;
        }
    }

    /* compiled from: AnnotationConstructorCaller.kt */
    public static final class d extends l implements kotlin.jvm.functions.a<String> {
        final /* synthetic */ Class $annotationClass;
        final /* synthetic */ Map $values;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(Class cls, Map map) {
            super(0);
            this.$annotationClass = cls;
            this.$values = map;
        }

        @NotNull
        public final String invoke() {
            StringBuilder sb = new StringBuilder();
            StringBuilder $this$buildString = sb;
            $this$buildString.append('@');
            $this$buildString.append(this.$annotationClass.getCanonicalName());
            y.Z(this.$values.entrySet(), $this$buildString, ", ", "(", ")", 0, (CharSequence) null, a.INSTANCE, 48, (Object) null);
            String sb2 = sb.toString();
            k.b(sb2, "StringBuilder().apply(builderAction).toString()");
            return sb2;
        }

        /* compiled from: AnnotationConstructorCaller.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<Map.Entry<? extends String, ? extends Object>, String> {
            public static final a INSTANCE = new a();

            a() {
                super(1);
            }

            @NotNull
            public final String invoke(@NotNull Map.Entry<String, ? extends Object> entry) {
                String valueString;
                k.f(entry, "entry");
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof boolean[]) {
                    valueString = Arrays.toString((boolean[]) value);
                } else if (value instanceof char[]) {
                    valueString = Arrays.toString((char[]) value);
                } else if (value instanceof byte[]) {
                    valueString = Arrays.toString((byte[]) value);
                } else if (value instanceof short[]) {
                    valueString = Arrays.toString((short[]) value);
                } else if (value instanceof int[]) {
                    valueString = Arrays.toString((int[]) value);
                } else if (value instanceof float[]) {
                    valueString = Arrays.toString((float[]) value);
                } else if (value instanceof long[]) {
                    valueString = Arrays.toString((long[]) value);
                } else if (value instanceof double[]) {
                    valueString = Arrays.toString((double[]) value);
                } else if (value instanceof Object[]) {
                    valueString = Arrays.toString((Object[]) value);
                } else {
                    valueString = value.toString();
                }
                return key + '=' + valueString;
            }
        }
    }

    /* compiled from: AnnotationConstructorCaller.kt */
    public static final class c implements InvocationHandler {
        final /* synthetic */ Class c;
        final /* synthetic */ g d;
        final /* synthetic */ kotlin.reflect.k f;
        final /* synthetic */ g q;
        final /* synthetic */ kotlin.reflect.k x;
        final /* synthetic */ a y;
        final /* synthetic */ Map z;

        c(Class cls, g gVar, kotlin.reflect.k kVar, g gVar2, kotlin.reflect.k kVar2, a aVar, Map map) {
            this.c = cls;
            this.d = gVar;
            this.f = kVar;
            this.q = gVar2;
            this.x = kVar2;
            this.y = aVar;
            this.z = map;
        }

        @Nullable
        public final Object invoke(Object $noName_0, Method method, Object[] args) {
            k.b(method, FirebaseAnalytics.Param.METHOD);
            String name = method.getName();
            if (name != null) {
                switch (name.hashCode()) {
                    case -1776922004:
                        if (name.equals("toString")) {
                            return this.d.getValue();
                        }
                        break;
                    case 147696667:
                        if (name.equals("hashCode")) {
                            return this.q.getValue();
                        }
                        break;
                    case 1444986633:
                        if (name.equals("annotationType")) {
                            return this.c;
                        }
                        break;
                }
            }
            if (k.a(name, "equals") && args != null && args.length == 1) {
                return Boolean.valueOf(this.y.invoke(kotlin.collections.l.J(args)));
            }
            if (this.z.containsKey(name)) {
                return this.z.get(name);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Method is not supported: ");
            sb.append(method);
            sb.append(" (args: ");
            Object[] $this$orEmpty$iv = args;
            if ($this$orEmpty$iv == null) {
                $this$orEmpty$iv = new Object[0];
            }
            sb.append(kotlin.collections.l.U($this$orEmpty$iv));
            sb.append(')');
            throw new kotlin.reflect.jvm.internal.y(sb.toString());
        }
    }
}
