package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import kotlin.TypeCastException;
import kotlin.collections.l;
import kotlin.jvm.a;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.builtins.h;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b;
import kotlin.reflect.jvm.internal.impl.load.kotlin.p;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.d;
import org.jetbrains.annotations.NotNull;

/* compiled from: ReflectKotlinClass.kt */
public final class c {
    public static final c a = new c();

    private c() {
    }

    public final void b(@NotNull Class<?> klass, @NotNull p.c visitor) {
        k.f(klass, "klass");
        k.f(visitor, "visitor");
        for (Annotation annotation : klass.getDeclaredAnnotations()) {
            k.b(annotation, "annotation");
            f(visitor, annotation);
        }
        visitor.visitEnd();
    }

    public final void i(@NotNull Class<?> klass, @NotNull p.d memberVisitor) {
        k.f(klass, "klass");
        k.f(memberVisitor, "memberVisitor");
        e(klass, memberVisitor);
        c(klass, memberVisitor);
        d(klass, memberVisitor);
    }

    private final void e(Class<?> klass, p.d memberVisitor) {
        int i;
        Method[] methodArr;
        Method[] declaredMethods = klass.getDeclaredMethods();
        int length = declaredMethods.length;
        int i2 = 0;
        while (i2 < length) {
            Method method = declaredMethods[i2];
            k.b(method, FirebaseAnalytics.Param.METHOD);
            f f = f.f(method.getName());
            k.b(f, "Name.identifier(method.name)");
            p.e visitor = memberVisitor.b(f, n.a.c(method));
            if (visitor != null) {
                for (Annotation annotation : method.getDeclaredAnnotations()) {
                    k.b(annotation, "annotation");
                    f(visitor, annotation);
                }
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                k.b(parameterAnnotations, "method.parameterAnnotations");
                int length2 = parameterAnnotations.length;
                for (int parameterIndex = 0; parameterIndex < length2; parameterIndex++) {
                    Annotation[] annotations = parameterAnnotations[parameterIndex];
                    int length3 = annotations.length;
                    int i3 = 0;
                    while (i3 < length3) {
                        Annotation annotation2 = annotations[i3];
                        Class annotationType = a.b(a.a(annotation2));
                        Method[] methodArr2 = declaredMethods;
                        kotlin.reflect.jvm.internal.impl.name.a b = b.b(annotationType);
                        int i4 = length;
                        k.b(annotation2, "annotation");
                        p.a it = visitor.a(parameterIndex, b, new b(annotation2));
                        if (it != null) {
                            a.h(it, annotation2, annotationType);
                        }
                        i3++;
                        declaredMethods = methodArr2;
                        length = i4;
                    }
                    Method[] methodArr3 = declaredMethods;
                    int i5 = length;
                }
                methodArr = declaredMethods;
                i = length;
                visitor.visitEnd();
            } else {
                methodArr = declaredMethods;
                i = length;
            }
            i2++;
            declaredMethods = methodArr;
            length = i;
        }
        p.d dVar = memberVisitor;
    }

    private final void c(Class<?> klass, p.d memberVisitor) {
        Constructor[] constructorArr;
        int i;
        Constructor[] declaredConstructors = klass.getDeclaredConstructors();
        int length = declaredConstructors.length;
        int i2 = 0;
        while (i2 < length) {
            Constructor constructor = declaredConstructors[i2];
            f k = f.k("<init>");
            k.b(k, "Name.special(\"<init>\")");
            n nVar = n.a;
            k.b(constructor, "constructor");
            p.e visitor = memberVisitor.b(k, nVar.a(constructor));
            if (visitor != null) {
                for (Annotation annotation : constructor.getDeclaredAnnotations()) {
                    k.b(annotation, "annotation");
                    f(visitor, annotation);
                }
                Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();
                k.b(parameterAnnotations, "parameterAnnotations");
                if (!(parameterAnnotations.length == 0)) {
                    int shift = constructor.getParameterTypes().length - parameterAnnotations.length;
                    int length2 = parameterAnnotations.length;
                    for (int parameterIndex = 0; parameterIndex < length2; parameterIndex++) {
                        Annotation[] annotations = parameterAnnotations[parameterIndex];
                        int length3 = annotations.length;
                        int i3 = 0;
                        while (i3 < length3) {
                            Annotation annotation2 = annotations[i3];
                            Constructor[] constructorArr2 = declaredConstructors;
                            Class annotationType = a.b(a.a(annotation2));
                            int i4 = length;
                            Constructor constructor2 = constructor;
                            kotlin.reflect.jvm.internal.impl.name.a b = b.b(annotationType);
                            Annotation[][] parameterAnnotations2 = parameterAnnotations;
                            k.b(annotation2, "annotation");
                            p.a it = visitor.a(parameterIndex + shift, b, new b(annotation2));
                            if (it != null) {
                                a.h(it, annotation2, annotationType);
                            }
                            i3++;
                            length = i4;
                            declaredConstructors = constructorArr2;
                            constructor = constructor2;
                            parameterAnnotations = parameterAnnotations2;
                        }
                        Constructor[] constructorArr3 = declaredConstructors;
                        int i5 = length;
                        Constructor constructor3 = constructor;
                        Annotation[][] annotationArr = parameterAnnotations;
                    }
                    constructorArr = declaredConstructors;
                    i = length;
                    Constructor constructor4 = constructor;
                    Annotation[][] annotationArr2 = parameterAnnotations;
                } else {
                    constructorArr = declaredConstructors;
                    i = length;
                    Constructor constructor5 = constructor;
                    Annotation[][] annotationArr3 = parameterAnnotations;
                }
                visitor.visitEnd();
            } else {
                constructorArr = declaredConstructors;
                i = length;
                Constructor constructor6 = constructor;
            }
            i2++;
            length = i;
            declaredConstructors = constructorArr;
        }
        p.d dVar = memberVisitor;
    }

    private final void d(Class<?> klass, p.d memberVisitor) {
        for (Field field : klass.getDeclaredFields()) {
            k.b(field, "field");
            f f = f.f(field.getName());
            k.b(f, "Name.identifier(field.name)");
            p.c visitor = memberVisitor.a(f, n.a.b(field), (Object) null);
            if (visitor != null) {
                for (Annotation annotation : field.getDeclaredAnnotations()) {
                    k.b(annotation, "annotation");
                    f(visitor, annotation);
                }
                visitor.visitEnd();
            }
        }
    }

    private final void f(p.c visitor, Annotation annotation) {
        Class annotationType = a.b(a.a(annotation));
        p.a it = visitor.b(b.b(annotationType), new b(annotation));
        if (it != null) {
            a.h(it, annotation, annotationType);
        }
    }

    private final void h(p.a visitor, Annotation annotation, Class<?> annotationType) {
        for (Method method : annotationType.getDeclaredMethods()) {
            try {
                Object value = method.invoke(annotation, new Object[0]);
                if (value == null) {
                    k.n();
                }
                k.b(method, FirebaseAnalytics.Param.METHOD);
                f f = f.f(method.getName());
                k.b(f, "Name.identifier(method.name)");
                g(visitor, f, value);
            } catch (IllegalAccessException e) {
            }
        }
        visitor.visitEnd();
    }

    private final kotlin.reflect.jvm.internal.impl.resolve.constants.f a(@NotNull Class<?> $this$classLiteralValue) {
        Class currentClass = $this$classLiteralValue;
        int dimensions = 0;
        while (currentClass.isArray()) {
            dimensions++;
            Class componentType = currentClass.getComponentType();
            k.b(componentType, "currentClass.componentType");
            currentClass = componentType;
        }
        if (!currentClass.isPrimitive()) {
            kotlin.reflect.jvm.internal.impl.name.a javaClassId = b.b(currentClass);
            kotlin.reflect.jvm.internal.impl.builtins.jvm.c cVar = kotlin.reflect.jvm.internal.impl.builtins.jvm.c.m;
            kotlin.reflect.jvm.internal.impl.name.b b = javaClassId.b();
            k.b(b, "javaClassId.asSingleFqName()");
            kotlin.reflect.jvm.internal.impl.name.a kotlinClassId = cVar.v(b);
            if (kotlinClassId == null) {
                kotlinClassId = javaClassId;
            }
            return new kotlin.reflect.jvm.internal.impl.resolve.constants.f(kotlinClassId, dimensions);
        } else if (k.a(currentClass, Void.TYPE)) {
            kotlin.reflect.jvm.internal.impl.name.a m = kotlin.reflect.jvm.internal.impl.name.a.m(g.h.e.l());
            k.b(m, "ClassId.topLevel(KotlinB…s.FQ_NAMES.unit.toSafe())");
            return new kotlin.reflect.jvm.internal.impl.resolve.constants.f(m, dimensions);
        } else {
            d dVar = d.get(currentClass.getName());
            k.b(dVar, "JvmPrimitiveType.get(currentClass.name)");
            h primitiveType = dVar.getPrimitiveType();
            k.b(primitiveType, "JvmPrimitiveType.get(cur…Class.name).primitiveType");
            if (dimensions > 0) {
                kotlin.reflect.jvm.internal.impl.name.a m2 = kotlin.reflect.jvm.internal.impl.name.a.m(primitiveType.getArrayTypeFqName());
                k.b(m2, "ClassId.topLevel(primitiveType.arrayTypeFqName)");
                return new kotlin.reflect.jvm.internal.impl.resolve.constants.f(m2, dimensions - 1);
            }
            kotlin.reflect.jvm.internal.impl.name.a m3 = kotlin.reflect.jvm.internal.impl.name.a.m(primitiveType.getTypeFqName());
            k.b(m3, "ClassId.topLevel(primitiveType.typeFqName)");
            return new kotlin.reflect.jvm.internal.impl.resolve.constants.f(m3, dimensions);
        }
    }

    private final void g(p.a visitor, f name, Object value) {
        Class clazz = value.getClass();
        if (k.a(clazz, Class.class)) {
            visitor.c(name, a((Class) value));
        } else if (i.a.contains(clazz)) {
            visitor.d(name, value);
        } else if (b.i(clazz)) {
            Class enclosingClass = clazz.isEnum() ? clazz : clazz.getEnclosingClass();
            k.b(enclosingClass, "(if (clazz.isEnum) clazz…lse clazz.enclosingClass)");
            kotlin.reflect.jvm.internal.impl.name.a classId = b.b(enclosingClass);
            f f = f.f(((Enum) value).name());
            k.b(f, "Name.identifier((value as Enum<*>).name)");
            visitor.a(name, classId, f);
        } else if (Annotation.class.isAssignableFrom(clazz)) {
            Class[] interfaces = clazz.getInterfaces();
            k.b(interfaces, "clazz.interfaces");
            Class annotationClass = (Class) l.J(interfaces);
            k.b(annotationClass, "annotationClass");
            p.a v = visitor.b(name, b.b(annotationClass));
            if (v != null) {
                h(v, (Annotation) value, annotationClass);
            }
        } else if (clazz.isArray()) {
            p.b v2 = visitor.e(name);
            if (v2 != null) {
                Class componentType = clazz.getComponentType();
                k.b(componentType, "componentType");
                int i = 0;
                if (componentType.isEnum()) {
                    kotlin.reflect.jvm.internal.impl.name.a enumClassId = b.b(componentType);
                    Object[] objArr = (Object[]) value;
                    int length = objArr.length;
                    while (i < length) {
                        Object element = objArr[i];
                        if (element != null) {
                            f f2 = f.f(((Enum) element).name());
                            k.b(f2, "Name.identifier((element as Enum<*>).name)");
                            v2.b(enumClassId, f2);
                            i++;
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.Enum<*>");
                        }
                    }
                } else if (k.a(componentType, Class.class)) {
                    Object[] objArr2 = (Object[]) value;
                    int length2 = objArr2.length;
                    while (i < length2) {
                        Object element2 = objArr2[i];
                        if (element2 != null) {
                            v2.c(a((Class) element2));
                            i++;
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<*>");
                        }
                    }
                } else {
                    Object[] objArr3 = (Object[]) value;
                    int length3 = objArr3.length;
                    while (i < length3) {
                        v2.a(objArr3[i]);
                        i++;
                    }
                }
                v2.visitEnd();
            }
        } else {
            throw new UnsupportedOperationException("Unsupported annotation argument value (" + clazz + "): " + value);
        }
    }
}
