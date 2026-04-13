package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.w;
import kotlin.reflect.jvm.internal.impl.load.java.structure.a;
import kotlin.reflect.jvm.internal.impl.load.java.structure.i;
import kotlin.reflect.jvm.internal.impl.load.java.structure.j;
import kotlin.reflect.jvm.internal.impl.load.java.structure.v;
import kotlin.reflect.jvm.internal.impl.name.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectJavaClassifierType.kt */
public final class l extends w implements j {
    @NotNull
    private final i b;
    @NotNull
    private final Type c;

    public l(@NotNull Type reflectType) {
        i classifier;
        k.f(reflectType, "reflectType");
        this.c = reflectType;
        Type type = J();
        if (type instanceof Class) {
            classifier = new j((Class) type);
        } else if (type instanceof TypeVariable) {
            classifier = new x((TypeVariable) type);
        } else if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType != null) {
                classifier = new j((Class) rawType);
            } else {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<*>");
            }
        } else {
            throw new IllegalStateException("Not a classifier type (" + type.getClass() + "): " + type);
        }
        this.b = classifier;
    }

    @NotNull
    public Type J() {
        return this.c;
    }

    @NotNull
    public i a() {
        return this.b;
    }

    @NotNull
    public String A() {
        throw new UnsupportedOperationException("Type not found: " + J());
    }

    @NotNull
    public String x() {
        return J().toString();
    }

    public boolean o() {
        Type $this$with = J();
        if (!($this$with instanceof Class)) {
            return false;
        }
        TypeVariable[] typeParameters = ((Class) $this$with).getTypeParameters();
        k.b(typeParameters, "getTypeParameters()");
        return (typeParameters.length == 0) ^ true;
    }

    @NotNull
    public List<v> t() {
        Iterable<Type> $this$mapTo$iv$iv = b.e(J());
        w.a aVar = w.a;
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (Type p1 : $this$mapTo$iv$iv) {
            arrayList.add(aVar.a(p1));
        }
        return arrayList;
    }

    @NotNull
    public Collection<a> getAnnotations() {
        return q.g();
    }

    @Nullable
    public a c(@NotNull b fqName) {
        k.f(fqName, "fqName");
        return null;
    }

    public boolean w() {
        return false;
    }
}
