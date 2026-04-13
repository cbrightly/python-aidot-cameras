package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.q;
import kotlin.reflect.jvm.internal.impl.load.java.structure.k;
import kotlin.reflect.jvm.internal.impl.load.java.structure.y;
import org.jetbrains.annotations.NotNull;

/* compiled from: ReflectJavaConstructor.kt */
public final class m extends r implements k {
    @NotNull
    private final Constructor<?> a;

    public m(@NotNull Constructor<?> member) {
        kotlin.jvm.internal.k.f(member, "member");
        this.a = member;
    }

    @NotNull
    /* renamed from: L */
    public Constructor<?> J() {
        return this.a;
    }

    @NotNull
    public List<y> g() {
        Type[] realTypes;
        Annotation[][] realAnnotations;
        Type[] types = J().getGenericParameterTypes();
        kotlin.jvm.internal.k.b(types, "types");
        if (types.length == 0) {
            return q.g();
        }
        Class klass = J().getDeclaringClass();
        kotlin.jvm.internal.k.b(klass, "klass");
        if (klass.getDeclaringClass() == null || Modifier.isStatic(klass.getModifiers())) {
            realTypes = types;
        } else {
            realTypes = (Type[]) kotlin.collections.k.i(types, 1, types.length);
        }
        Annotation[][] annotations = J().getParameterAnnotations();
        if (annotations.length >= realTypes.length) {
            if (annotations.length > realTypes.length) {
                kotlin.jvm.internal.k.b(annotations, "annotations");
                realAnnotations = (Annotation[][]) kotlin.collections.k.i(annotations, annotations.length - realTypes.length, annotations.length);
            } else {
                realAnnotations = annotations;
            }
            kotlin.jvm.internal.k.b(realTypes, "realTypes");
            kotlin.jvm.internal.k.b(realAnnotations, "realAnnotations");
            return K(realTypes, realAnnotations, J().isVarArgs());
        }
        throw new IllegalStateException("Illegal generic signature: " + J());
    }

    @NotNull
    public List<x> getTypeParameters() {
        TypeVariable[] typeParameters = J().getTypeParameters();
        kotlin.jvm.internal.k.b(typeParameters, "member.typeParameters");
        ArrayList arrayList = new ArrayList(typeParameters.length);
        for (TypeVariable p1 : typeParameters) {
            arrayList.add(new x(p1));
        }
        return arrayList;
    }
}
