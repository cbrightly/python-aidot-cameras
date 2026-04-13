package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.g0;
import kotlin.collections.k0;
import kotlin.collections.l0;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.jvm.internal.k;
import kotlin.ranges.n;
import kotlin.reflect.jvm.internal.impl.descriptors.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.c;
import kotlin.reflect.jvm.internal.impl.descriptors.d;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.t;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.metadata.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.constants.a;
import kotlin.reflect.jvm.internal.impl.resolve.constants.h;
import kotlin.reflect.jvm.internal.impl.resolve.constants.i;
import kotlin.reflect.jvm.internal.impl.resolve.constants.j;
import kotlin.reflect.jvm.internal.impl.resolve.constants.k;
import kotlin.reflect.jvm.internal.impl.resolve.constants.l;
import kotlin.reflect.jvm.internal.impl.resolve.constants.m;
import kotlin.reflect.jvm.internal.impl.resolve.constants.s;
import kotlin.reflect.jvm.internal.impl.resolve.constants.v;
import kotlin.reflect.jvm.internal.impl.resolve.constants.w;
import kotlin.reflect.jvm.internal.impl.resolve.constants.x;
import kotlin.reflect.jvm.internal.impl.resolve.constants.z;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.u;
import org.jetbrains.annotations.NotNull;

/* compiled from: AnnotationDeserializer.kt */
public final class g {
    private final y a;
    private final a0 b;

    public g(@NotNull y module, @NotNull a0 notFoundClasses) {
        k.f(module, "module");
        k.f(notFoundClasses, "notFoundClasses");
        this.a = module;
        this.b = notFoundClasses;
    }

    private final kotlin.reflect.jvm.internal.impl.builtins.g c() {
        return this.a.j();
    }

    @NotNull
    public final c a(@NotNull b proto, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver) {
        kotlin.reflect.jvm.internal.impl.metadata.deserialization.c cVar = nameResolver;
        k.f(proto, "proto");
        k.f(cVar, "nameResolver");
        e annotationClass = e(y.a(cVar, proto.getId()));
        Map arguments = l0.f();
        if (proto.getArgumentCount() != 0 && !u.r(annotationClass) && kotlin.reflect.jvm.internal.impl.resolve.c.t(annotationClass)) {
            Collection<d> f = annotationClass.f();
            k.b(f, "annotationClass.constructors");
            d constructor = (d) kotlin.collections.y.r0(f);
            if (constructor != null) {
                Iterable $this$associateByTo$iv$iv = constructor.g();
                k.b($this$associateByTo$iv$iv, "constructor.valueParameters");
                Map linkedHashMap = new LinkedHashMap(n.b(k0.b(r.r($this$associateByTo$iv$iv, 10)), 16));
                for (T next : $this$associateByTo$iv$iv) {
                    w0 it = (w0) next;
                    k.b(it, "it");
                    linkedHashMap.put(it.getName(), next);
                }
                Map parameterByName = linkedHashMap;
                Iterable<b.C0379b> $this$mapNotNullTo$iv$iv = proto.getArgumentList();
                k.b($this$mapNotNullTo$iv$iv, "proto.argumentList");
                Collection destination$iv$iv = new ArrayList();
                for (b.C0379b it2 : $this$mapNotNullTo$iv$iv) {
                    k.b(it2, "it");
                    Object it$iv$iv = d(it2, parameterByName, cVar);
                    if (it$iv$iv != null) {
                        destination$iv$iv.add(it$iv$iv);
                    }
                    b bVar = proto;
                }
                arguments = l0.o(destination$iv$iv);
            }
        }
        return new kotlin.reflect.jvm.internal.impl.descriptors.annotations.d(annotationClass.m(), arguments, o0.a);
    }

    private final kotlin.n<f, kotlin.reflect.jvm.internal.impl.resolve.constants.g<?>> d(b.C0379b proto, Map<f, ? extends w0> parameterByName, kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver) {
        w0 parameter = (w0) parameterByName.get(y.b(nameResolver, proto.getNameId()));
        if (parameter == null) {
            return null;
        }
        f b2 = y.b(nameResolver, proto.getNameId());
        b0 type = parameter.getType();
        k.b(type, "parameter.type");
        b.C0379b.c value = proto.getValue();
        k.b(value, "proto.value");
        return new kotlin.n<>(b2, g(type, value, nameResolver));
    }

    private final kotlin.reflect.jvm.internal.impl.resolve.constants.g<?> g(b0 expectedType, b.C0379b.c value, kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver) {
        kotlin.reflect.jvm.internal.impl.resolve.constants.g it = f(expectedType, value, nameResolver);
        if (!b(it, expectedType, value)) {
            it = null;
        }
        if (it != null) {
            return it;
        }
        k.a aVar = kotlin.reflect.jvm.internal.impl.resolve.constants.k.b;
        return aVar.a("Unexpected argument value: actual type " + value.getType() + " != expected type " + expectedType);
    }

    @NotNull
    public final kotlin.reflect.jvm.internal.impl.resolve.constants.g<?> f(@NotNull b0 expectedType, @NotNull b.C0379b.c value, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver) {
        b0 b0Var = expectedType;
        kotlin.reflect.jvm.internal.impl.metadata.deserialization.c cVar = nameResolver;
        kotlin.jvm.internal.k.f(b0Var, "expectedType");
        kotlin.jvm.internal.k.f(value, "value");
        kotlin.jvm.internal.k.f(cVar, "nameResolver");
        Boolean g = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.K.d(value.getFlags());
        kotlin.jvm.internal.k.b(g, "Flags.IS_UNSIGNED.get(value.flags)");
        boolean isUnsigned = g.booleanValue();
        b.C0379b.c.C0382c type = value.getType();
        if (type != null) {
            switch (f.a[type.ordinal()]) {
                case 1:
                    byte $this$letIf$iv = (byte) ((int) value.getIntValue());
                    return isUnsigned ? new x($this$letIf$iv) : new kotlin.reflect.jvm.internal.impl.resolve.constants.d($this$letIf$iv);
                case 2:
                    return new kotlin.reflect.jvm.internal.impl.resolve.constants.e((char) ((int) value.getIntValue()));
                case 3:
                    short $this$letIf$iv2 = (short) ((int) value.getIntValue());
                    return isUnsigned ? new kotlin.reflect.jvm.internal.impl.resolve.constants.a0($this$letIf$iv2) : new v($this$letIf$iv2);
                case 4:
                    int $this$letIf$iv3 = (int) value.getIntValue();
                    return isUnsigned ? new kotlin.reflect.jvm.internal.impl.resolve.constants.y($this$letIf$iv3) : new m($this$letIf$iv3);
                case 5:
                    long $this$letIf$iv4 = value.getIntValue();
                    return isUnsigned ? new z($this$letIf$iv4) : new s($this$letIf$iv4);
                case 6:
                    return new l(value.getFloatValue());
                case 7:
                    return new i(value.getDoubleValue());
                case 8:
                    return new kotlin.reflect.jvm.internal.impl.resolve.constants.c(value.getIntValue() != 0);
                case 9:
                    return new w(cVar.getString(value.getStringValue()));
                case 10:
                    return new kotlin.reflect.jvm.internal.impl.resolve.constants.r(y.a(cVar, value.getClassId()), value.getArrayDimensionCount());
                case 11:
                    return new j(y.a(cVar, value.getClassId()), y.b(cVar, value.getEnumValueId()));
                case 12:
                    b annotation = value.getAnnotation();
                    kotlin.jvm.internal.k.b(annotation, "value.annotation");
                    return new a(a(annotation, cVar));
                case 13:
                    h hVar = h.a;
                    Iterable<b.C0379b.c> $this$mapTo$iv$iv = value.getArrayElementList();
                    kotlin.jvm.internal.k.b($this$mapTo$iv$iv, "value.arrayElementList");
                    ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
                    for (b.C0379b.c it : $this$mapTo$iv$iv) {
                        i0 j = c().j();
                        kotlin.jvm.internal.k.b(j, "builtIns.anyType");
                        kotlin.jvm.internal.k.b(it, "it");
                        arrayList.add(f(j, it, cVar));
                        b.C0379b.c cVar2 = value;
                    }
                    return hVar.b(arrayList, b0Var);
            }
        }
        throw new IllegalStateException(("Unsupported annotation argument type: " + value.getType() + " (expected " + b0Var + ')').toString());
    }

    private final boolean b(kotlin.reflect.jvm.internal.impl.resolve.constants.g<?> result, b0 expectedType, b.C0379b.c value) {
        b.C0379b.c.C0382c type = value.getType();
        if (type != null) {
            switch (f.b[type.ordinal()]) {
                case 1:
                    kotlin.reflect.jvm.internal.impl.descriptors.h c = expectedType.I0().c();
                    if (!(c instanceof e)) {
                        c = null;
                    }
                    e expectedClass = (e) c;
                    if (expectedClass == null || kotlin.reflect.jvm.internal.impl.builtins.g.t0(expectedClass)) {
                        return true;
                    }
                    return false;
                case 2:
                    if ((result instanceof kotlin.reflect.jvm.internal.impl.resolve.constants.b) && ((List) ((kotlin.reflect.jvm.internal.impl.resolve.constants.b) result).b()).size() == value.getArrayElementList().size()) {
                        b0 expectedElementType = c().l(expectedType);
                        kotlin.jvm.internal.k.b(expectedElementType, "builtIns.getArrayElementType(expectedType)");
                        kotlin.ranges.i h = q.h((Collection) ((kotlin.reflect.jvm.internal.impl.resolve.constants.b) result).b());
                        if ((h instanceof Collection) && ((Collection) h).isEmpty()) {
                            return true;
                        }
                        Iterator it = h.iterator();
                        while (it.hasNext()) {
                            int i = ((g0) it).nextInt();
                            b.C0379b.c arrayElement = value.getArrayElement(i);
                            kotlin.jvm.internal.k.b(arrayElement, "value.getArrayElement(i)");
                            if (b((kotlin.reflect.jvm.internal.impl.resolve.constants.g) ((List) ((kotlin.reflect.jvm.internal.impl.resolve.constants.b) result).b()).get(i), expectedElementType, arrayElement) == 0) {
                                return false;
                            }
                        }
                        return true;
                    }
                    throw new IllegalStateException(("Deserialized ArrayValue should have the same number of elements as the original array value: " + result).toString());
            }
        }
        return kotlin.jvm.internal.k.a(result.a(this.a), expectedType);
    }

    private final e e(kotlin.reflect.jvm.internal.impl.name.a classId) {
        return t.c(this.a, classId, this.b);
    }
}
