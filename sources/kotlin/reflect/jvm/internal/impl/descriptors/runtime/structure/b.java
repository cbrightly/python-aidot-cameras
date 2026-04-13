package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import androidx.exifinterface.media.ExifInterface;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.collections.l0;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.jvm.functions.d;
import kotlin.jvm.functions.e;
import kotlin.jvm.functions.f;
import kotlin.jvm.functions.g;
import kotlin.jvm.functions.h;
import kotlin.jvm.functions.i;
import kotlin.jvm.functions.j;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.m;
import kotlin.jvm.functions.n;
import kotlin.jvm.functions.o;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.s;
import kotlin.jvm.functions.u;
import kotlin.jvm.functions.v;
import kotlin.jvm.functions.w;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.k;
import kotlin.reflect.c;
import kotlin.t;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: reflectClassUtil.kt */
public final class b {
    private static final List<c<? extends Object>> a;
    private static final Map<Class<? extends Object>, Class<? extends Object>> b;
    private static final Map<Class<? extends Object>, Class<? extends Object>> c;
    private static final Map<Class<? extends kotlin.c<?>>, Integer> d;

    @NotNull
    public static final ClassLoader g(@NotNull Class<?> $this$safeClassLoader) {
        k.f($this$safeClassLoader, "$this$safeClassLoader");
        ClassLoader classLoader = $this$safeClassLoader.getClassLoader();
        if (classLoader != null) {
            return classLoader;
        }
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        k.b(systemClassLoader, "ClassLoader.getSystemClassLoader()");
        return systemClassLoader;
    }

    public static final boolean i(@NotNull Class<?> $this$isEnumClassOrSpecializedEnumEntryClass) {
        k.f($this$isEnumClassOrSpecializedEnumEntryClass, "$this$isEnumClassOrSpecializedEnumEntryClass");
        return Enum.class.isAssignableFrom($this$isEnumClassOrSpecializedEnumEntryClass);
    }

    static {
        List<c<? extends Object>> $this$mapTo$iv$iv = q.j(a0.b(Boolean.TYPE), a0.b(Byte.TYPE), a0.b(Character.TYPE), a0.b(Double.TYPE), a0.b(Float.TYPE), a0.b(Integer.TYPE), a0.b(Long.TYPE), a0.b(Short.TYPE));
        a = $this$mapTo$iv$iv;
        Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (c it : $this$mapTo$iv$iv) {
            destination$iv$iv.add(t.a(kotlin.jvm.a.c(it), kotlin.jvm.a.d(it)));
        }
        b = l0.o(destination$iv$iv);
        Iterable<c> $this$mapTo$iv$iv2 = a;
        Collection destination$iv$iv2 = new ArrayList(r.r($this$mapTo$iv$iv2, 10));
        for (c it2 : $this$mapTo$iv$iv2) {
            destination$iv$iv2.add(t.a(kotlin.jvm.a.d(it2), kotlin.jvm.a.c(it2)));
        }
        c = l0.o(destination$iv$iv2);
        Iterable $this$mapIndexedTo$iv$iv = q.j(kotlin.jvm.functions.a.class, l.class, p.class, kotlin.jvm.functions.q.class, kotlin.jvm.functions.r.class, s.class, kotlin.jvm.functions.t.class, u.class, v.class, w.class, kotlin.jvm.functions.b.class, kotlin.jvm.functions.c.class, d.class, e.class, f.class, g.class, h.class, i.class, j.class, kotlin.jvm.functions.k.class, m.class, n.class, o.class);
        Collection destination$iv$iv3 = new ArrayList(r.r($this$mapIndexedTo$iv$iv, 10));
        int index$iv$iv = 0;
        for (Object item$iv$iv : $this$mapIndexedTo$iv$iv) {
            int index$iv$iv2 = index$iv$iv + 1;
            if (index$iv$iv < 0) {
                q.q();
            }
            destination$iv$iv3.add(t.a((Class) item$iv$iv, Integer.valueOf(index$iv$iv)));
            index$iv$iv = index$iv$iv2;
        }
        d = l0.o(destination$iv$iv3);
    }

    @Nullable
    public static final Class<?> f(@NotNull Class<?> $this$primitiveByWrapper) {
        k.f($this$primitiveByWrapper, "$this$primitiveByWrapper");
        return b.get($this$primitiveByWrapper);
    }

    @Nullable
    public static final Class<?> h(@NotNull Class<?> $this$wrapperByPrimitive) {
        k.f($this$wrapperByPrimitive, "$this$wrapperByPrimitive");
        return c.get($this$wrapperByPrimitive);
    }

    @Nullable
    public static final Integer d(@NotNull Class<?> $this$functionClassArity) {
        k.f($this$functionClassArity, "$this$functionClassArity");
        return d.get($this$functionClassArity);
    }

    @NotNull
    public static final kotlin.reflect.jvm.internal.impl.name.a b(@NotNull Class<?> $this$classId) {
        kotlin.reflect.jvm.internal.impl.name.a b2;
        kotlin.reflect.jvm.internal.impl.name.a d2;
        k.f($this$classId, "$this$classId");
        if ($this$classId.isPrimitive()) {
            throw new IllegalArgumentException("Can't compute ClassId for primitive type: " + $this$classId);
        } else if (!$this$classId.isArray()) {
            if ($this$classId.getEnclosingMethod() == null && $this$classId.getEnclosingConstructor() == null) {
                String simpleName = $this$classId.getSimpleName();
                k.b(simpleName, "simpleName");
                if (!(simpleName.length() == 0)) {
                    Class<?> declaringClass = $this$classId.getDeclaringClass();
                    if (declaringClass != null && (b2 = b(declaringClass)) != null && (d2 = b2.d(kotlin.reflect.jvm.internal.impl.name.f.f($this$classId.getSimpleName()))) != null) {
                        return d2;
                    }
                    kotlin.reflect.jvm.internal.impl.name.a m = kotlin.reflect.jvm.internal.impl.name.a.m(new kotlin.reflect.jvm.internal.impl.name.b($this$classId.getName()));
                    k.b(m, "ClassId.topLevel(FqName(name))");
                    return m;
                }
            }
            kotlin.reflect.jvm.internal.impl.name.b fqName = new kotlin.reflect.jvm.internal.impl.name.b($this$classId.getName());
            return new kotlin.reflect.jvm.internal.impl.name.a(fqName.e(), kotlin.reflect.jvm.internal.impl.name.b.k(fqName.g()), true);
        } else {
            throw new IllegalArgumentException("Can't compute ClassId for array type: " + $this$classId);
        }
    }

    @NotNull
    public static final String c(@NotNull Class<?> $this$desc) {
        k.f($this$desc, "$this$desc");
        if (k.a($this$desc, Void.TYPE)) {
            return ExifInterface.GPS_MEASUREMENT_INTERRUPTED;
        }
        String name = a($this$desc).getName();
        k.b(name, "createArrayType().name");
        String substring = name.substring(1);
        k.b(substring, "(this as java.lang.String).substring(startIndex)");
        return kotlin.text.w.G(substring, '.', '/', false, 4, (Object) null);
    }

    @NotNull
    public static final Class<?> a(@NotNull Class<?> $this$createArrayType) {
        k.f($this$createArrayType, "$this$createArrayType");
        return Array.newInstance($this$createArrayType, 0).getClass();
    }

    @NotNull
    public static final List<Type> e(@NotNull Type $this$parameterizedTypeArguments) {
        k.f($this$parameterizedTypeArguments, "$this$parameterizedTypeArguments");
        if (!($this$parameterizedTypeArguments instanceof ParameterizedType)) {
            return q.g();
        }
        if (((ParameterizedType) $this$parameterizedTypeArguments).getOwnerType() != null) {
            return kotlin.sequences.o.C(kotlin.sequences.o.s(kotlin.sequences.m.h($this$parameterizedTypeArguments, a.INSTANCE), C0355b.INSTANCE));
        }
        Type[] actualTypeArguments = ((ParameterizedType) $this$parameterizedTypeArguments).getActualTypeArguments();
        k.b(actualTypeArguments, "actualTypeArguments");
        return kotlin.collections.l.U(actualTypeArguments);
    }

    /* compiled from: reflectClassUtil.kt */
    public static final class a extends kotlin.jvm.internal.l implements l<ParameterizedType, ParameterizedType> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @Nullable
        public final ParameterizedType invoke(@NotNull ParameterizedType it) {
            k.f(it, "it");
            Type ownerType = it.getOwnerType();
            if (!(ownerType instanceof ParameterizedType)) {
                ownerType = null;
            }
            return (ParameterizedType) ownerType;
        }
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b$b  reason: collision with other inner class name */
    /* compiled from: reflectClassUtil.kt */
    public static final class C0355b extends kotlin.jvm.internal.l implements l<ParameterizedType, kotlin.sequences.h<? extends Type>> {
        public static final C0355b INSTANCE = new C0355b();

        C0355b() {
            super(1);
        }

        @NotNull
        public final kotlin.sequences.h<Type> invoke(@NotNull ParameterizedType it) {
            k.f(it, "it");
            Type[] actualTypeArguments = it.getActualTypeArguments();
            k.b(actualTypeArguments, "it.actualTypeArguments");
            return kotlin.collections.l.p(actualTypeArguments);
        }
    }
}
