package kotlin.reflect.jvm.internal;

import com.meituan.robust.Constants;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.collections.l0;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.v;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.c;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.f;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.m;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.j;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.n;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.load.kotlin.p;
import kotlin.reflect.jvm.internal.impl.load.kotlin.r;
import kotlin.reflect.jvm.internal.impl.metadata.i;
import kotlin.reflect.jvm.internal.impl.name.a;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.protobuf.o;
import kotlin.reflect.jvm.internal.impl.resolve.constants.g;
import kotlin.reflect.jvm.internal.impl.resolve.constants.r;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.e0;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.l;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.x;
import kotlin.reflect.s;
import kotlin.t;
import kotlin.text.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: util.kt */
public final class h0 {
    @NotNull
    private static final b a = new b("kotlin.jvm.JvmStatic");

    @NotNull
    public static final b g() {
        return a;
    }

    @Nullable
    public static final Class<?> l(@NotNull e $this$toJavaClass) {
        k.f($this$toJavaClass, "$this$toJavaClass");
        o0 source = $this$toJavaClass.n();
        k.b(source, "source");
        if (source instanceof r) {
            p d = ((r) source).d();
            if (d != null) {
                return ((f) d).e();
            }
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.runtime.components.ReflectKotlinClass");
        } else if (source instanceof m.a) {
            n d2 = ((m.a) source).c();
            if (d2 != null) {
                return ((j) d2).n();
            }
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.runtime.structure.ReflectJavaClass");
        } else {
            a classId = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.i($this$toJavaClass);
            if (classId != null) {
                return i(kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b.g($this$toJavaClass.getClass()), classId, 0);
            }
            return null;
        }
    }

    static /* synthetic */ Class j(ClassLoader classLoader, a aVar, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return i(classLoader, aVar, i);
    }

    private static final Class<?> i(ClassLoader classLoader, a kotlinClassId, int arrayDimensions) {
        c cVar = c.m;
        kotlin.reflect.jvm.internal.impl.name.c j = kotlinClassId.b().j();
        k.b(j, "kotlinClassId.asSingleFqName().toUnsafe()");
        a javaClassId = cVar.x(j);
        if (javaClassId == null) {
            javaClassId = kotlinClassId;
        }
        String b = javaClassId.h().b();
        k.b(b, "javaClassId.packageFqName.asString()");
        String b2 = javaClassId.i().b();
        k.b(b2, "javaClassId.relativeClassName.asString()");
        return h(classLoader, b, b2, arrayDimensions);
    }

    private static final Class<?> h(ClassLoader classLoader, String packageName, String className, int arrayDimensions) {
        if (k.a(packageName, "kotlin")) {
            switch (className.hashCode()) {
                case -901856463:
                    if (className.equals("BooleanArray")) {
                        return boolean[].class;
                    }
                    break;
                case -763279523:
                    if (className.equals("ShortArray")) {
                        return short[].class;
                    }
                    break;
                case -755911549:
                    if (className.equals("CharArray")) {
                        return char[].class;
                    }
                    break;
                case -74930671:
                    if (className.equals("ByteArray")) {
                        return byte[].class;
                    }
                    break;
                case 22374632:
                    if (className.equals("DoubleArray")) {
                        return double[].class;
                    }
                    break;
                case 63537721:
                    if (className.equals("Array")) {
                        return Object[].class;
                    }
                    break;
                case 601811914:
                    if (className.equals("IntArray")) {
                        return int[].class;
                    }
                    break;
                case 948852093:
                    if (className.equals("FloatArray")) {
                        return float[].class;
                    }
                    break;
                case 2104330525:
                    if (className.equals("LongArray")) {
                        return long[].class;
                    }
                    break;
            }
        }
        String fqName = packageName + '.' + w.G(className, '.', '$', false, 4, (Object) null);
        if (arrayDimensions > 0) {
            fqName = w.D(Constants.ARRAY_TYPE, arrayDimensions) + Constants.OBJECT_TYPE + fqName + ';';
        }
        return kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.e.a(classLoader, fqName);
    }

    @Nullable
    public static final s m(@NotNull a1 $this$toKVisibility) {
        k.f($this$toKVisibility, "$this$toKVisibility");
        if (k.a($this$toKVisibility, z0.e)) {
            return s.PUBLIC;
        }
        if (k.a($this$toKVisibility, z0.c)) {
            return s.PROTECTED;
        }
        if (k.a($this$toKVisibility, z0.d)) {
            return s.INTERNAL;
        }
        if (!k.a($this$toKVisibility, z0.a) && !k.a($this$toKVisibility, z0.b)) {
            return null;
        }
        return s.PRIVATE;
    }

    @NotNull
    public static final List<Annotation> d(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.a $this$computeAnnotations) {
        Iterable $this$mapNotNull$iv;
        k.f($this$computeAnnotations, "$this$computeAnnotations");
        Iterable<kotlin.reflect.jvm.internal.impl.descriptors.annotations.c> $this$mapNotNull$iv2 = $this$computeAnnotations.getAnnotations();
        ArrayList arrayList = new ArrayList();
        for (kotlin.reflect.jvm.internal.impl.descriptors.annotations.c it : $this$mapNotNull$iv2) {
            o0 source = it.n();
            Object it$iv$iv = null;
            if (source instanceof kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.b) {
                it$iv$iv = ((kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.b) source).d();
                $this$mapNotNull$iv = $this$mapNotNull$iv2;
            } else if (source instanceof m.a) {
                n d = ((m.a) source).c();
                $this$mapNotNull$iv = $this$mapNotNull$iv2;
                if (!(d instanceof kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.c)) {
                    d = null;
                }
                kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.c cVar = (kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.c) d;
                if (cVar != null) {
                    it$iv$iv = cVar.k();
                }
            } else {
                $this$mapNotNull$iv = $this$mapNotNull$iv2;
                it$iv$iv = k(it);
            }
            if (it$iv$iv != null) {
                arrayList.add(it$iv$iv);
            }
            $this$mapNotNull$iv2 = $this$mapNotNull$iv;
        }
        return arrayList;
    }

    private static final Annotation k(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.c $this$toAnnotationInstance) {
        e g = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.g($this$toAnnotationInstance);
        Class annotationClass = g != null ? l(g) : null;
        if (!(annotationClass instanceof Class)) {
            annotationClass = null;
        }
        if (annotationClass == null) {
            return null;
        }
        Iterable<Map.Entry> $this$mapNotNull$iv = $this$toAnnotationInstance.a().entrySet();
        int $i$f$mapNotNull = false;
        Collection destination$iv$iv = new ArrayList();
        for (Map.Entry $dstr$name$value : $this$mapNotNull$iv) {
            kotlin.reflect.jvm.internal.impl.name.f name = (kotlin.reflect.jvm.internal.impl.name.f) $dstr$name$value.getKey();
            Iterable $this$mapNotNull$iv2 = $this$mapNotNull$iv;
            ClassLoader classLoader = annotationClass.getClassLoader();
            int $i$f$mapNotNull2 = $i$f$mapNotNull;
            k.b(classLoader, "annotationClass.classLoader");
            Object p1 = n((g) $dstr$name$value.getValue(), classLoader);
            Object p12 = p1 != null ? t.a(name.b(), p1) : null;
            if (p12 != null) {
                destination$iv$iv.add(p12);
            }
            $this$mapNotNull$iv = $this$mapNotNull$iv2;
            $i$f$mapNotNull = $i$f$mapNotNull2;
        }
        int i = $i$f$mapNotNull;
        return (Annotation) kotlin.reflect.jvm.internal.calls.b.d(annotationClass, l0.o(destination$iv$iv), (List) null, 4, (Object) null);
    }

    private static final Object n(@NotNull g<?> $this$toRuntimeValue, ClassLoader classLoader) {
        if ($this$toRuntimeValue instanceof kotlin.reflect.jvm.internal.impl.resolve.constants.a) {
            return k((kotlin.reflect.jvm.internal.impl.descriptors.annotations.c) ((kotlin.reflect.jvm.internal.impl.resolve.constants.a) $this$toRuntimeValue).b());
        }
        if ($this$toRuntimeValue instanceof kotlin.reflect.jvm.internal.impl.resolve.constants.b) {
            Iterable<g> $this$map$iv = (Iterable) ((kotlin.reflect.jvm.internal.impl.resolve.constants.b) $this$toRuntimeValue).b();
            ArrayList arrayList = new ArrayList(kotlin.collections.r.r($this$map$iv, 10));
            for (g it : $this$map$iv) {
                arrayList.add(n(it, classLoader));
            }
            Iterable $this$map$iv2 = arrayList;
            Object[] array = arrayList.toArray(new Object[0]);
            if (array != null) {
                return array;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        } else if ($this$toRuntimeValue instanceof kotlin.reflect.jvm.internal.impl.resolve.constants.j) {
            kotlin.n nVar = (kotlin.n) ((kotlin.reflect.jvm.internal.impl.resolve.constants.j) $this$toRuntimeValue).b();
            kotlin.reflect.jvm.internal.impl.name.f entryName = (kotlin.reflect.jvm.internal.impl.name.f) nVar.component2();
            Class enumClass = j(classLoader, (a) nVar.component1(), 0, 4, (Object) null);
            if (enumClass != null) {
                return g0.a(enumClass, entryName.b());
            }
            return null;
        } else if ($this$toRuntimeValue instanceof kotlin.reflect.jvm.internal.impl.resolve.constants.r) {
            r.b classValue = (r.b) ((kotlin.reflect.jvm.internal.impl.resolve.constants.r) $this$toRuntimeValue).b();
            if (classValue instanceof r.b.C0408b) {
                return i(classLoader, ((r.b.C0408b) classValue).b(), ((r.b.C0408b) classValue).a());
            }
            if (classValue instanceof r.b.a) {
                h c = ((r.b.a) classValue).a().I0().c();
                if (!(c instanceof e)) {
                    c = null;
                }
                e eVar = (e) c;
                if (eVar != null) {
                    return l(eVar);
                }
                return null;
            }
            throw new NoWhenBranchMatchedException();
        } else if (!($this$toRuntimeValue instanceof kotlin.reflect.jvm.internal.impl.resolve.constants.k) && !($this$toRuntimeValue instanceof kotlin.reflect.jvm.internal.impl.resolve.constants.t)) {
            return $this$toRuntimeValue.b();
        } else {
            return null;
        }
    }

    @Nullable
    public static final k b(@Nullable Object $this$asKFunctionImpl) {
        kotlin.reflect.a aVar = null;
        k kVar = (k) (!($this$asKFunctionImpl instanceof k) ? null : $this$asKFunctionImpl);
        if (kVar != null) {
            return kVar;
        }
        kotlin.jvm.internal.h hVar = (kotlin.jvm.internal.h) (!($this$asKFunctionImpl instanceof kotlin.jvm.internal.h) ? null : $this$asKFunctionImpl);
        kotlin.reflect.a compute = hVar != null ? hVar.compute() : null;
        if (compute instanceof k) {
            aVar = compute;
        }
        return (k) aVar;
    }

    @Nullable
    public static final t<?> c(@Nullable Object $this$asKPropertyImpl) {
        kotlin.reflect.a aVar = null;
        t<?> tVar = (t) (!($this$asKPropertyImpl instanceof t) ? null : $this$asKPropertyImpl);
        if (tVar != null) {
            return tVar;
        }
        v vVar = (v) (!($this$asKPropertyImpl instanceof v) ? null : $this$asKPropertyImpl);
        kotlin.reflect.a compute = vVar != null ? vVar.compute() : null;
        if (compute instanceof t) {
            aVar = compute;
        }
        return (t) aVar;
    }

    @Nullable
    public static final e<?> a(@Nullable Object $this$asKCallableImpl) {
        e<?> eVar = (e) (!($this$asKCallableImpl instanceof e) ? null : $this$asKCallableImpl);
        if (eVar == null) {
            eVar = b($this$asKCallableImpl);
        }
        return eVar != null ? eVar : c($this$asKCallableImpl);
    }

    @Nullable
    public static final kotlin.reflect.jvm.internal.impl.descriptors.l0 f(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a $this$instanceReceiverParameter) {
        k.f($this$instanceReceiverParameter, "$this$instanceReceiverParameter");
        if ($this$instanceReceiverParameter.I() == null) {
            return null;
        }
        kotlin.reflect.jvm.internal.impl.descriptors.m b = $this$instanceReceiverParameter.b();
        if (b != null) {
            return ((e) b).F0();
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
    }

    @Nullable
    public static final <M extends o, D extends kotlin.reflect.jvm.internal.impl.descriptors.a> D e(@NotNull Class<?> moduleAnchor, @NotNull M proto, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.h typeTable, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.a metadataVersion, @NotNull kotlin.jvm.functions.p<? super x, ? super M, ? extends D> createDescriptor) {
        List list;
        M m = proto;
        kotlin.jvm.functions.p<? super x, ? super M, ? extends D> pVar = createDescriptor;
        k.f(moduleAnchor, "moduleAnchor");
        k.f(m, "proto");
        k.f(nameResolver, "nameResolver");
        k.f(typeTable, "typeTable");
        k.f(metadataVersion, "metadataVersion");
        k.f(pVar, "createDescriptor");
        kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.k moduleData = z.a(moduleAnchor);
        if (m instanceof i) {
            list = ((i) m).getTypeParameterList();
        } else if (m instanceof kotlin.reflect.jvm.internal.impl.metadata.n) {
            list = ((kotlin.reflect.jvm.internal.impl.metadata.n) m).getTypeParameterList();
        } else {
            throw new IllegalStateException(("Unsupported message: " + m).toString());
        }
        List typeParameters = list;
        l a2 = moduleData.a();
        y b = moduleData.b();
        kotlin.reflect.jvm.internal.impl.metadata.deserialization.k b2 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.k.b.b();
        k.b(typeParameters, "typeParameters");
        return (kotlin.reflect.jvm.internal.impl.descriptors.a) pVar.invoke(new x(new kotlin.reflect.jvm.internal.impl.serialization.deserialization.n(a2, nameResolver, b, typeTable, b2, metadataVersion, (kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.e) null, (e0) null, typeParameters)), m);
    }
}
