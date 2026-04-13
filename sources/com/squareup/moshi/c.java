package com.squareup.moshi;

import com.squareup.moshi.ClassJsonAdapter;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.Nullable;

/* compiled from: ClassJsonAdapter */
public final class c<T> extends f<T> {
    public static final f.d a = new a();
    private final b<T> b;
    private final b<?>[] c;
    private final i.a d;

    /* compiled from: ClassJsonAdapter */
    public class a implements f.d {
        a() {
        }

        @Nullable
        public f<?> a(Type type, Set<? extends Annotation> annotations, r moshi) {
            if (!(type instanceof Class) && !(type instanceof ParameterizedType)) {
                return null;
            }
            Class<?> rawType = t.g(type);
            if (rawType.isInterface() || rawType.isEnum() || !annotations.isEmpty()) {
                return null;
            }
            if (com.squareup.moshi.internal.b.i(rawType)) {
                d(type, List.class);
                d(type, Set.class);
                d(type, Map.class);
                d(type, Collection.class);
                String messagePrefix = "Platform " + rawType;
                if (type instanceof ParameterizedType) {
                    messagePrefix = messagePrefix + " in " + type;
                }
                throw new IllegalArgumentException(messagePrefix + " requires explicit JsonAdapter to be registered");
            } else if (rawType.isAnonymousClass()) {
                throw new IllegalArgumentException("Cannot serialize anonymous class " + rawType.getName());
            } else if (rawType.isLocalClass()) {
                throw new IllegalArgumentException("Cannot serialize local class " + rawType.getName());
            } else if (rawType.getEnclosingClass() != null && !Modifier.isStatic(rawType.getModifiers())) {
                throw new IllegalArgumentException("Cannot serialize non-static nested class " + rawType.getName());
            } else if (Modifier.isAbstract(rawType.getModifiers())) {
                throw new IllegalArgumentException("Cannot serialize abstract class " + rawType.getName());
            } else if (!com.squareup.moshi.internal.b.h(rawType)) {
                ClassFactory<Object> classFactory = b.a(rawType);
                Map<String, ClassJsonAdapter.FieldBinding<?>> fields = new TreeMap<>();
                for (Type t = type; t != Object.class; t = t.f(t)) {
                    b(moshi, t, fields);
                }
                return new c(classFactory, fields).f();
            } else {
                throw new IllegalArgumentException("Cannot serialize Kotlin type " + rawType.getName() + ". Reflective serialization of Kotlin classes without using kotlin-reflect has undefined and unexpected behavior. Please use KotlinJsonAdapterFactory from the moshi-kotlin artifact or use code gen from the moshi-kotlin-codegen artifact.");
            }
        }

        private void d(Type type, Class<?> collectionInterface) {
            Class<?> rawClass = t.g(type);
            if (collectionInterface.isAssignableFrom(rawClass)) {
                throw new IllegalArgumentException("No JsonAdapter for " + type + ", you should probably use " + collectionInterface.getSimpleName() + " instead of " + rawClass.getSimpleName() + " (Moshi only supports the collection interfaces by default) or else register a custom JsonAdapter.");
            }
        }

        private void b(r moshi, Type type, Map<String, b<?>> fieldBindings) {
            Class<?> rawType;
            Class<?> rawType2 = t.g(type);
            boolean platformType = com.squareup.moshi.internal.b.i(rawType2);
            Field[] declaredFields = rawType2.getDeclaredFields();
            int length = declaredFields.length;
            int i = 0;
            while (i < length) {
                Field field = declaredFields[i];
                if (!c(platformType, field.getModifiers())) {
                    r rVar = moshi;
                    Type type2 = type;
                    rawType = rawType2;
                } else {
                    Type fieldType = com.squareup.moshi.internal.b.n(type, rawType2, field.getGenericType());
                    Set<? extends Annotation> annotations = com.squareup.moshi.internal.b.j(field);
                    String fieldName = field.getName();
                    JsonAdapter<Object> adapter = moshi.f(fieldType, annotations, fieldName);
                    field.setAccessible(true);
                    e jsonAnnotation = (e) field.getAnnotation(e.class);
                    String name = jsonAnnotation != null ? jsonAnnotation.name() : fieldName;
                    ClassJsonAdapter.FieldBinding<Object> fieldBinding = new b<>(name, field, adapter);
                    rawType = rawType2;
                    ClassJsonAdapter.FieldBinding<?> replaced = (b) fieldBindings.put(name, fieldBinding);
                    if (replaced != null) {
                        throw new IllegalArgumentException("Conflicting fields:\n    " + replaced.b + "\n    " + fieldBinding.b);
                    }
                }
                i++;
                rawType2 = rawType;
            }
        }

        private boolean c(boolean platformType, int modifiers) {
            if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) {
                return false;
            }
            if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers) || !platformType) {
                return true;
            }
            return false;
        }
    }

    c(b<T> classFactory, Map<String, b<?>> fieldsMap) {
        this.b = classFactory;
        this.c = (b[]) fieldsMap.values().toArray(new b[fieldsMap.size()]);
        this.d = i.a.a((String[]) fieldsMap.keySet().toArray(new String[fieldsMap.size()]));
    }

    public T b(i reader) {
        try {
            T result = this.b.b();
            try {
                reader.c();
                while (reader.l()) {
                    int index = reader.J(this.d);
                    if (index == -1) {
                        reader.o0();
                        reader.u0();
                    } else {
                        this.c[index].a(reader, result);
                    }
                }
                reader.i();
                return result;
            } catch (IllegalAccessException e) {
                throw new AssertionError();
            }
        } catch (InstantiationException e2) {
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e3) {
            throw com.squareup.moshi.internal.b.q(e3);
        } catch (IllegalAccessException e4) {
            throw new AssertionError();
        }
    }

    public void i(o writer, T value) {
        try {
            writer.g();
            for (ClassJsonAdapter.FieldBinding<?> fieldBinding : this.c) {
                writer.r(fieldBinding.a);
                fieldBinding.b(writer, value);
            }
            writer.m();
        } catch (IllegalAccessException e) {
            throw new AssertionError();
        }
    }

    public String toString() {
        return "JsonAdapter(" + this.b + ")";
    }

    /* compiled from: ClassJsonAdapter */
    public static class b<T> {
        final String a;
        final Field b;
        final f<T> c;

        b(String name, Field field, f<T> adapter) {
            this.a = name;
            this.b = field;
            this.c = adapter;
        }

        /* access modifiers changed from: package-private */
        public void a(i reader, Object value) {
            this.b.set(value, this.c.b(reader));
        }

        /* access modifiers changed from: package-private */
        public void b(o writer, Object value) {
            this.c.i(writer, this.b.get(value));
        }
    }
}
