package com.squareup.moshi.internal;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.h;
import com.squareup.moshi.i;
import com.squareup.moshi.t;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

/* compiled from: Util */
public final class b {
    public static final Set<Annotation> a = Collections.emptySet();
    public static final Type[] b = new Type[0];
    @Nullable
    public static final Class<?> c;
    @Nullable
    private static final Class<? extends Annotation> d;
    private static final Map<Class<?>, Class<?>> e;

    static {
        Class<?> cls = null;
        try {
            cls = Class.forName(getKotlinMetadataClassName());
        } catch (ClassNotFoundException e2) {
        }
        d = cls;
        Class<?> defaultConstructorMarker = null;
        try {
            defaultConstructorMarker = Class.forName("kotlin.jvm.internal.DefaultConstructorMarker");
        } catch (ClassNotFoundException e3) {
        }
        c = defaultConstructorMarker;
        Map<Class<?>, Class<?>> primToWrap = new LinkedHashMap<>(16);
        primToWrap.put(Boolean.TYPE, Boolean.class);
        primToWrap.put(Byte.TYPE, Byte.class);
        primToWrap.put(Character.TYPE, Character.class);
        primToWrap.put(Double.TYPE, Double.class);
        primToWrap.put(Float.TYPE, Float.class);
        primToWrap.put(Integer.TYPE, Integer.class);
        primToWrap.put(Long.TYPE, Long.class);
        primToWrap.put(Short.TYPE, Short.class);
        primToWrap.put(Void.TYPE, Void.class);
        e = Collections.unmodifiableMap(primToWrap);
    }

    private static String getKotlinMetadataClassName() {
        return "kotlin.Metadata";
    }

    public static boolean t(Type pattern, Type candidate) {
        return t.d(pattern, candidate);
    }

    public static Set<? extends Annotation> j(AnnotatedElement annotatedElement) {
        return k(annotatedElement.getAnnotations());
    }

    public static Set<? extends Annotation> k(Annotation[] annotations) {
        Set<Annotation> result = null;
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().isAnnotationPresent(h.class)) {
                if (result == null) {
                    result = new LinkedHashSet<>();
                }
                result.add(annotation);
            }
        }
        return result != null ? Collections.unmodifiableSet(result) : a;
    }

    public static boolean i(Class<?> rawType) {
        String name = rawType.getName();
        return name.startsWith("android.") || name.startsWith("androidx.") || name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("kotlin.") || name.startsWith("kotlinx.") || name.startsWith("scala.");
    }

    public static RuntimeException q(InvocationTargetException e2) {
        Throwable cause = e2.getTargetException();
        if (cause instanceof RuntimeException) {
            throw ((RuntimeException) cause);
        } else if (cause instanceof Error) {
            throw ((Error) cause);
        } else {
            throw new RuntimeException(cause);
        }
    }

    public static Type a(Type type) {
        if (type instanceof Class) {
            Class<?> c2 = (Class) type;
            return c2.isArray() ? new a(a(c2.getComponentType())) : c2;
        } else if (type instanceof ParameterizedType) {
            if (type instanceof C0206b) {
                return type;
            }
            ParameterizedType p = (ParameterizedType) type;
            return new C0206b(p.getOwnerType(), p.getRawType(), p.getActualTypeArguments());
        } else if (type instanceof GenericArrayType) {
            if (type instanceof a) {
                return type;
            }
            return new a(((GenericArrayType) type).getGenericComponentType());
        } else if (!(type instanceof WildcardType) || (type instanceof c)) {
            return type;
        } else {
            WildcardType w = (WildcardType) type;
            return new c(w.getUpperBounds(), w.getLowerBounds());
        }
    }

    public static Type m(Type type) {
        if (!(type instanceof WildcardType) || ((WildcardType) type).getLowerBounds().length != 0) {
            return type;
        }
        Type[] upperBounds = ((WildcardType) type).getUpperBounds();
        if (upperBounds.length == 1) {
            return upperBounds[0];
        }
        throw new IllegalArgumentException();
    }

    public static Type n(Type context, Class<?> contextRawType, Type toResolve) {
        return o(context, contextRawType, toResolve, new LinkedHashSet());
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v9, resolved type: java.lang.reflect.Type[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.reflect.Type o(java.lang.reflect.Type r9, java.lang.Class<?> r10, java.lang.reflect.Type r11, java.util.Collection<java.lang.reflect.TypeVariable<?>> r12) {
        /*
        L_0x0000:
            boolean r0 = r11 instanceof java.lang.reflect.TypeVariable
            if (r0 == 0) goto L_0x0019
            r0 = r11
            java.lang.reflect.TypeVariable r0 = (java.lang.reflect.TypeVariable) r0
            boolean r1 = r12.contains(r0)
            if (r1 == 0) goto L_0x000e
            return r11
        L_0x000e:
            r12.add(r0)
            java.lang.reflect.Type r11 = p(r9, r10, r0)
            if (r11 != r0) goto L_0x0018
            return r11
        L_0x0018:
            goto L_0x0000
        L_0x0019:
            boolean r0 = r11 instanceof java.lang.Class
            if (r0 == 0) goto L_0x003b
            r0 = r11
            java.lang.Class r0 = (java.lang.Class) r0
            boolean r0 = r0.isArray()
            if (r0 == 0) goto L_0x003b
            r0 = r11
            java.lang.Class r0 = (java.lang.Class) r0
            java.lang.Class r1 = r0.getComponentType()
            java.lang.reflect.Type r2 = o(r9, r10, r1, r12)
            if (r1 != r2) goto L_0x0036
            r3 = r0
            goto L_0x003a
        L_0x0036:
            java.lang.reflect.GenericArrayType r3 = com.squareup.moshi.t.b(r2)
        L_0x003a:
            return r3
        L_0x003b:
            boolean r0 = r11 instanceof java.lang.reflect.GenericArrayType
            if (r0 == 0) goto L_0x0054
            r0 = r11
            java.lang.reflect.GenericArrayType r0 = (java.lang.reflect.GenericArrayType) r0
            java.lang.reflect.Type r1 = r0.getGenericComponentType()
            java.lang.reflect.Type r2 = o(r9, r10, r1, r12)
            if (r1 != r2) goto L_0x004f
            r3 = r0
            goto L_0x0053
        L_0x004f:
            java.lang.reflect.GenericArrayType r3 = com.squareup.moshi.t.b(r2)
        L_0x0053:
            return r3
        L_0x0054:
            boolean r0 = r11 instanceof java.lang.reflect.ParameterizedType
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0098
            r0 = r11
            java.lang.reflect.ParameterizedType r0 = (java.lang.reflect.ParameterizedType) r0
            java.lang.reflect.Type r3 = r0.getOwnerType()
            java.lang.reflect.Type r4 = o(r9, r10, r3, r12)
            if (r4 == r3) goto L_0x0068
            goto L_0x0069
        L_0x0068:
            r1 = r2
        L_0x0069:
            java.lang.reflect.Type[] r2 = r0.getActualTypeArguments()
            r5 = 0
            int r6 = r2.length
        L_0x006f:
            if (r5 >= r6) goto L_0x008a
            r7 = r2[r5]
            java.lang.reflect.Type r7 = o(r9, r10, r7, r12)
            r8 = r2[r5]
            if (r7 == r8) goto L_0x0087
            if (r1 != 0) goto L_0x0085
            java.lang.Object r8 = r2.clone()
            r2 = r8
            java.lang.reflect.Type[] r2 = (java.lang.reflect.Type[]) r2
            r1 = 1
        L_0x0085:
            r2[r5] = r7
        L_0x0087:
            int r5 = r5 + 1
            goto L_0x006f
        L_0x008a:
            if (r1 == 0) goto L_0x0096
            com.squareup.moshi.internal.b$b r5 = new com.squareup.moshi.internal.b$b
            java.lang.reflect.Type r6 = r0.getRawType()
            r5.<init>(r4, r6, r2)
            goto L_0x0097
        L_0x0096:
            r5 = r0
        L_0x0097:
            return r5
        L_0x0098:
            boolean r0 = r11 instanceof java.lang.reflect.WildcardType
            if (r0 == 0) goto L_0x00ce
            r0 = r11
            java.lang.reflect.WildcardType r0 = (java.lang.reflect.WildcardType) r0
            java.lang.reflect.Type[] r3 = r0.getLowerBounds()
            java.lang.reflect.Type[] r4 = r0.getUpperBounds()
            int r5 = r3.length
            if (r5 != r1) goto L_0x00ba
            r1 = r3[r2]
            java.lang.reflect.Type r1 = o(r9, r10, r1, r12)
            r2 = r3[r2]
            if (r1 == r2) goto L_0x00b9
            java.lang.reflect.WildcardType r2 = com.squareup.moshi.t.l(r1)
            return r2
        L_0x00b9:
            goto L_0x00cc
        L_0x00ba:
            int r5 = r4.length
            if (r5 != r1) goto L_0x00cc
            r1 = r4[r2]
            java.lang.reflect.Type r1 = o(r9, r10, r1, r12)     // Catch:{ all -> 0x00cf }
            r2 = r4[r2]
            if (r1 == r2) goto L_0x00cd
            java.lang.reflect.WildcardType r2 = com.squareup.moshi.t.k(r1)
            return r2
        L_0x00cc:
        L_0x00cd:
            return r0
        L_0x00ce:
            return r11
        L_0x00cf:
            r9 = move-exception
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.moshi.internal.b.o(java.lang.reflect.Type, java.lang.Class, java.lang.reflect.Type, java.util.Collection):java.lang.reflect.Type");
    }

    static Type p(Type context, Class<?> contextRawType, TypeVariable<?> unknown) {
        Class<?> declaredByRaw = c(unknown);
        if (declaredByRaw == null) {
            return unknown;
        }
        Type declaredBy = e(context, contextRawType, declaredByRaw);
        if (!(declaredBy instanceof ParameterizedType)) {
            return unknown;
        }
        return ((ParameterizedType) declaredBy).getActualTypeArguments()[g(declaredByRaw.getTypeParameters(), unknown)];
    }

    public static Type e(Type context, Class<?> rawType, Class<?> toResolve) {
        if (toResolve == rawType) {
            return context;
        }
        if (toResolve.isInterface()) {
            Class<?>[] interfaces = rawType.getInterfaces();
            int length = interfaces.length;
            for (int i = 0; i < length; i++) {
                if (interfaces[i] == toResolve) {
                    return rawType.getGenericInterfaces()[i];
                }
                if (toResolve.isAssignableFrom(interfaces[i])) {
                    return e(rawType.getGenericInterfaces()[i], interfaces[i], toResolve);
                }
            }
        }
        if (!rawType.isInterface()) {
            while (rawType != Object.class) {
                Class<? super Object> superclass = rawType.getSuperclass();
                if (superclass == toResolve) {
                    return rawType.getGenericSuperclass();
                }
                if (toResolve.isAssignableFrom(superclass)) {
                    return e(rawType.getGenericSuperclass(), superclass, toResolve);
                }
                rawType = superclass;
            }
        }
        return toResolve;
    }

    static int f(@Nullable Object o) {
        if (o != null) {
            return o.hashCode();
        }
        return 0;
    }

    static String s(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
    }

    static int g(Object[] array, Object toFind) {
        for (int i = 0; i < array.length; i++) {
            if (toFind.equals(array[i])) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    @Nullable
    static Class<?> c(TypeVariable<?> typeVariable) {
        Object genericDeclaration = typeVariable.getGenericDeclaration();
        if (genericDeclaration instanceof Class) {
            return (Class) genericDeclaration;
        }
        return null;
    }

    static void b(Type type) {
        if ((type instanceof Class) && ((Class) type).isPrimitive()) {
            throw new IllegalArgumentException("Unexpected primitive " + type + ". Use the boxed type.");
        }
    }

    /* renamed from: com.squareup.moshi.internal.b$b  reason: collision with other inner class name */
    /* compiled from: Util */
    public static final class C0206b implements ParameterizedType {
        @Nullable
        private final Type c;
        private final Type d;
        public final Type[] f;

        public C0206b(@Nullable Type ownerType, Type rawType, Type... typeArguments) {
            if (rawType instanceof Class) {
                Class<?> enclosingClass = ((Class) rawType).getEnclosingClass();
                if (ownerType != null) {
                    if (enclosingClass == null || t.g(ownerType) != enclosingClass) {
                        throw new IllegalArgumentException("unexpected owner type for " + rawType + ": " + ownerType);
                    }
                } else if (enclosingClass != null) {
                    throw new IllegalArgumentException("unexpected owner type for " + rawType + ": null");
                }
            }
            this.c = ownerType == null ? null : b.a(ownerType);
            this.d = b.a(rawType);
            this.f = (Type[]) typeArguments.clone();
            int t = 0;
            while (true) {
                Type[] typeArr = this.f;
                if (t >= typeArr.length) {
                    return;
                }
                if (typeArr[t] != null) {
                    b.b(typeArr[t]);
                    Type[] typeArr2 = this.f;
                    typeArr2[t] = b.a(typeArr2[t]);
                    t++;
                } else {
                    throw new NullPointerException();
                }
            }
        }

        public Type[] getActualTypeArguments() {
            return (Type[]) this.f.clone();
        }

        public Type getRawType() {
            return this.d;
        }

        @Nullable
        public Type getOwnerType() {
            return this.c;
        }

        public boolean equals(Object other) {
            return (other instanceof ParameterizedType) && t.d(this, (ParameterizedType) other);
        }

        public int hashCode() {
            return (Arrays.hashCode(this.f) ^ this.d.hashCode()) ^ b.f(this.c);
        }

        public String toString() {
            StringBuilder result = new StringBuilder((this.f.length + 1) * 30);
            result.append(b.s(this.d));
            if (this.f.length == 0) {
                return result.toString();
            }
            result.append("<");
            result.append(b.s(this.f[0]));
            for (int i = 1; i < this.f.length; i++) {
                result.append(", ");
                result.append(b.s(this.f[i]));
            }
            result.append(">");
            return result.toString();
        }
    }

    /* compiled from: Util */
    public static final class a implements GenericArrayType {
        private final Type c;

        public a(Type componentType) {
            this.c = b.a(componentType);
        }

        public Type getGenericComponentType() {
            return this.c;
        }

        public boolean equals(Object o) {
            return (o instanceof GenericArrayType) && t.d(this, (GenericArrayType) o);
        }

        public int hashCode() {
            return this.c.hashCode();
        }

        public String toString() {
            return b.s(this.c) + "[]";
        }
    }

    /* compiled from: Util */
    public static final class c implements WildcardType {
        private final Type c;
        @Nullable
        private final Type d;

        public c(Type[] upperBounds, Type[] lowerBounds) {
            Class<Object> cls = Object.class;
            if (lowerBounds.length > 1) {
                throw new IllegalArgumentException();
            } else if (upperBounds.length != 1) {
                throw new IllegalArgumentException();
            } else if (lowerBounds.length == 1) {
                if (lowerBounds[0] != null) {
                    b.b(lowerBounds[0]);
                    if (upperBounds[0] == cls) {
                        this.d = b.a(lowerBounds[0]);
                        this.c = cls;
                        return;
                    }
                    throw new IllegalArgumentException();
                }
                throw new NullPointerException();
            } else if (upperBounds[0] != null) {
                b.b(upperBounds[0]);
                this.d = null;
                this.c = b.a(upperBounds[0]);
            } else {
                throw new NullPointerException();
            }
        }

        public Type[] getUpperBounds() {
            return new Type[]{this.c};
        }

        public Type[] getLowerBounds() {
            Type type = this.d;
            if (type == null) {
                return b.b;
            }
            return new Type[]{type};
        }

        public boolean equals(Object other) {
            return (other instanceof WildcardType) && t.d(this, (WildcardType) other);
        }

        public int hashCode() {
            Type type = this.d;
            return (type != null ? type.hashCode() + 31 : 1) ^ (this.c.hashCode() + 31);
        }

        public String toString() {
            if (this.d != null) {
                return "? super " + b.s(this.d);
            } else if (this.c == Object.class) {
                return "?";
            } else {
                return "? extends " + b.s(this.c);
            }
        }
    }

    public static String r(Type type, Set<? extends Annotation> annotations) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(type);
        if (annotations.isEmpty()) {
            str = " (with no annotations)";
        } else {
            str = " annotated " + annotations;
        }
        sb.append(str);
        return sb.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0058, code lost:
        r6 = new java.lang.Object[]{r4};
        r0 = r3.getDeclaredConstructor(new java.lang.Class[]{java.lang.reflect.Type[].class});
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r6 = new java.lang.Object[0];
        r0 = r3.getDeclaredConstructor(new java.lang.Class[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0081, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0086, code lost:
        throw q(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0087, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x009e, code lost:
        throw new java.lang.RuntimeException("Failed to instantiate the generated JsonAdapter for " + r10, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00b6, code lost:
        throw new java.lang.RuntimeException("Failed to access the generated JsonAdapter for " + r10, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0102, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0119, code lost:
        throw new java.lang.RuntimeException("Failed to find the generated JsonAdapter class for " + r10, r0);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0081 A[ExcHandler: InvocationTargetException (r0v7 'e' java.lang.reflect.InvocationTargetException A[CUSTOM_DECLARE]), Splitter:B:5:0x001e] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0087 A[ExcHandler: InstantiationException (r0v6 'e' java.lang.InstantiationException A[CUSTOM_DECLARE]), Splitter:B:5:0x001e] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x009f A[ExcHandler: IllegalAccessException (r0v5 'e' java.lang.IllegalAccessException A[CUSTOM_DECLARE]), Splitter:B:5:0x001e] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0102 A[ExcHandler: ClassNotFoundException (r0v3 'e' java.lang.ClassNotFoundException A[CUSTOM_DECLARE]), Splitter:B:5:0x001e] */
    @javax.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.squareup.moshi.f<?> d(com.squareup.moshi.r r9, java.lang.reflect.Type r10, java.lang.Class<?> r11) {
        /*
            java.lang.Class<com.squareup.moshi.r> r0 = com.squareup.moshi.r.class
            java.lang.Class<com.squareup.moshi.g> r1 = com.squareup.moshi.g.class
            java.lang.annotation.Annotation r1 = r11.getAnnotation(r1)
            com.squareup.moshi.g r1 = (com.squareup.moshi.g) r1
            if (r1 == 0) goto L_0x011a
            boolean r2 = r1.generateAdapter()
            if (r2 != 0) goto L_0x0014
            goto L_0x011a
        L_0x0014:
            java.lang.String r2 = r11.getName()
            java.lang.String r2 = com.squareup.moshi.t.e(r2)
            r3 = 0
            java.lang.ClassLoader r4 = r11.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x0102, NoSuchMethodException -> 0x00b7, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            r5 = 1
            java.lang.Class r4 = java.lang.Class.forName(r2, r5, r4)     // Catch:{ ClassNotFoundException -> 0x0102, NoSuchMethodException -> 0x00b7, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            r3 = r4
            boolean r4 = r10 instanceof java.lang.reflect.ParameterizedType     // Catch:{ ClassNotFoundException -> 0x0102, NoSuchMethodException -> 0x00b7, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            r6 = 0
            if (r4 == 0) goto L_0x005b
            r4 = r10
            java.lang.reflect.ParameterizedType r4 = (java.lang.reflect.ParameterizedType) r4     // Catch:{ ClassNotFoundException -> 0x0102, NoSuchMethodException -> 0x00b7, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            java.lang.reflect.Type[] r4 = r4.getActualTypeArguments()     // Catch:{ ClassNotFoundException -> 0x0102, NoSuchMethodException -> 0x00b7, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            r7 = 2
            java.lang.Class[] r8 = new java.lang.Class[r7]     // Catch:{ NoSuchMethodException -> 0x0049, ClassNotFoundException -> 0x0102, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            r8[r6] = r0     // Catch:{ NoSuchMethodException -> 0x0049, ClassNotFoundException -> 0x0102, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            java.lang.Class<java.lang.reflect.Type[]> r0 = java.lang.reflect.Type[].class
            r8[r5] = r0     // Catch:{ NoSuchMethodException -> 0x0049, ClassNotFoundException -> 0x0102, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            java.lang.reflect.Constructor r0 = r3.getDeclaredConstructor(r8)     // Catch:{ NoSuchMethodException -> 0x0049, ClassNotFoundException -> 0x0102, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ NoSuchMethodException -> 0x0049, ClassNotFoundException -> 0x0102, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            r7[r6] = r9     // Catch:{ NoSuchMethodException -> 0x0049, ClassNotFoundException -> 0x0102, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            r7[r5] = r4     // Catch:{ NoSuchMethodException -> 0x0049, ClassNotFoundException -> 0x0102, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            r6 = r7
            goto L_0x005a
        L_0x0049:
            r0 = move-exception
            java.lang.Class[] r7 = new java.lang.Class[r5]     // Catch:{ ClassNotFoundException -> 0x0102, NoSuchMethodException -> 0x00b7, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            java.lang.Class<java.lang.reflect.Type[]> r8 = java.lang.reflect.Type[].class
            r7[r6] = r8     // Catch:{ ClassNotFoundException -> 0x0102, NoSuchMethodException -> 0x00b7, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            java.lang.reflect.Constructor r7 = r3.getDeclaredConstructor(r7)     // Catch:{ ClassNotFoundException -> 0x0102, NoSuchMethodException -> 0x00b7, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            java.lang.Object[] r8 = new java.lang.Object[r5]     // Catch:{ ClassNotFoundException -> 0x0102, NoSuchMethodException -> 0x00b7, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            r8[r6] = r4     // Catch:{ ClassNotFoundException -> 0x0102, NoSuchMethodException -> 0x00b7, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            r6 = r8
            r0 = r7
        L_0x005a:
            goto L_0x0073
        L_0x005b:
            java.lang.Class[] r4 = new java.lang.Class[r5]     // Catch:{ NoSuchMethodException -> 0x0069, ClassNotFoundException -> 0x0102, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            r4[r6] = r0     // Catch:{ NoSuchMethodException -> 0x0069, ClassNotFoundException -> 0x0102, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            java.lang.reflect.Constructor r0 = r3.getDeclaredConstructor(r4)     // Catch:{ NoSuchMethodException -> 0x0069, ClassNotFoundException -> 0x0102, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            java.lang.Object[] r4 = new java.lang.Object[r5]     // Catch:{ NoSuchMethodException -> 0x0069, ClassNotFoundException -> 0x0102, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            r4[r6] = r9     // Catch:{ NoSuchMethodException -> 0x0069, ClassNotFoundException -> 0x0102, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            r6 = r4
            goto L_0x0073
        L_0x0069:
            r0 = move-exception
            java.lang.Class[] r4 = new java.lang.Class[r6]     // Catch:{ ClassNotFoundException -> 0x0102, NoSuchMethodException -> 0x00b7, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            java.lang.reflect.Constructor r4 = r3.getDeclaredConstructor(r4)     // Catch:{ ClassNotFoundException -> 0x0102, NoSuchMethodException -> 0x00b7, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ ClassNotFoundException -> 0x0102, NoSuchMethodException -> 0x00b7, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            r0 = r4
        L_0x0073:
            r0.setAccessible(r5)     // Catch:{ ClassNotFoundException -> 0x0102, NoSuchMethodException -> 0x00b7, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            java.lang.Object r4 = r0.newInstance(r6)     // Catch:{ ClassNotFoundException -> 0x0102, NoSuchMethodException -> 0x00b7, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            com.squareup.moshi.f r4 = (com.squareup.moshi.f) r4     // Catch:{ ClassNotFoundException -> 0x0102, NoSuchMethodException -> 0x00b7, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            com.squareup.moshi.f r4 = r4.f()     // Catch:{ ClassNotFoundException -> 0x0102, NoSuchMethodException -> 0x00b7, IllegalAccessException -> 0x009f, InstantiationException -> 0x0087, InvocationTargetException -> 0x0081 }
            return r4
        L_0x0081:
            r0 = move-exception
            java.lang.RuntimeException r4 = q(r0)
            throw r4
        L_0x0087:
            r0 = move-exception
            java.lang.RuntimeException r4 = new java.lang.RuntimeException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Failed to instantiate the generated JsonAdapter for "
            r5.append(r6)
            r5.append(r10)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5, r0)
            throw r4
        L_0x009f:
            r0 = move-exception
            java.lang.RuntimeException r4 = new java.lang.RuntimeException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Failed to access the generated JsonAdapter for "
            r5.append(r6)
            r5.append(r10)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5, r0)
            throw r4
        L_0x00b7:
            r0 = move-exception
            boolean r4 = r10 instanceof java.lang.reflect.ParameterizedType
            if (r4 != 0) goto L_0x00eb
            java.lang.reflect.TypeVariable[] r4 = r3.getTypeParameters()
            int r4 = r4.length
            if (r4 == 0) goto L_0x00eb
            java.lang.RuntimeException r4 = new java.lang.RuntimeException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Failed to find the generated JsonAdapter constructor for '"
            r5.append(r6)
            r5.append(r10)
            java.lang.String r6 = "'. Suspiciously, the type was not parameterized but the target class '"
            r5.append(r6)
            java.lang.String r6 = r3.getCanonicalName()
            r5.append(r6)
            java.lang.String r6 = "' is generic. Consider using Types#newParameterizedType() to define these missing type variables."
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5, r0)
            throw r4
        L_0x00eb:
            java.lang.RuntimeException r4 = new java.lang.RuntimeException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Failed to find the generated JsonAdapter constructor for "
            r5.append(r6)
            r5.append(r10)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5, r0)
            throw r4
        L_0x0102:
            r0 = move-exception
            java.lang.RuntimeException r4 = new java.lang.RuntimeException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Failed to find the generated JsonAdapter class for "
            r5.append(r6)
            r5.append(r10)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5, r0)
            throw r4
        L_0x011a:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.moshi.internal.b.d(com.squareup.moshi.r, java.lang.reflect.Type, java.lang.Class):com.squareup.moshi.f");
    }

    public static boolean h(Class<?> targetClass) {
        Class<? extends Annotation> cls = d;
        return cls != null && targetClass.isAnnotationPresent(cls);
    }

    public static JsonDataException l(String propertyName, String jsonName, i reader) {
        String message;
        String path = reader.getPath();
        if (jsonName.equals(propertyName)) {
            message = String.format("Required value '%s' missing at %s", new Object[]{propertyName, path});
        } else {
            message = String.format("Required value '%s' (JSON name '%s') missing at %s", new Object[]{propertyName, jsonName, path});
        }
        return new JsonDataException(message);
    }

    public static JsonDataException u(String propertyName, String jsonName, i reader) {
        String message;
        String path = reader.getPath();
        if (jsonName.equals(propertyName)) {
            message = String.format("Non-null value '%s' was null at %s", new Object[]{propertyName, path});
        } else {
            message = String.format("Non-null value '%s' (JSON name '%s') was null at %s", new Object[]{propertyName, jsonName, path});
        }
        return new JsonDataException(message);
    }
}
