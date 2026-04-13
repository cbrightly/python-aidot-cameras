package retrofit2;

import com.google.maps.android.BuildConfig;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import javax.annotation.Nullable;
import okhttp3.e0;
import okio.e;

/* compiled from: Utils */
public final class x {
    static final Type[] a = new Type[0];

    static RuntimeException m(Method method, String message, Object... args) {
        return n(method, (Throwable) null, message, args);
    }

    static RuntimeException n(Method method, @Nullable Throwable cause, String message, Object... args) {
        String message2 = String.format(message, args);
        return new IllegalArgumentException(message2 + "\n    for method " + method.getDeclaringClass().getSimpleName() + "." + method.getName(), cause);
    }

    static RuntimeException p(Method method, Throwable cause, int p, String message, Object... args) {
        return n(method, cause, message + " (parameter #" + (p + 1) + ")", args);
    }

    static RuntimeException o(Method method, int p, String message, Object... args) {
        return m(method, message + " (parameter #" + (p + 1) + ")", args);
    }

    static Class<?> h(Type type) {
        Objects.requireNonNull(type, "type == null");
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType instanceof Class) {
                return (Class) rawType;
            }
            throw new IllegalArgumentException();
        } else if (type instanceof GenericArrayType) {
            return Array.newInstance(h(((GenericArrayType) type).getGenericComponentType()), 0).getClass();
        } else {
            if (type instanceof TypeVariable) {
                return Object.class;
            }
            if (type instanceof WildcardType) {
                return h(((WildcardType) type).getUpperBounds()[0]);
            }
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + type.getClass().getName());
        }
    }

    static boolean d(Type a2, Type b2) {
        if (a2 == b2) {
            return true;
        }
        if (a2 instanceof Class) {
            return a2.equals(b2);
        }
        if (a2 instanceof ParameterizedType) {
            if (!(b2 instanceof ParameterizedType)) {
                return false;
            }
            ParameterizedType pa = (ParameterizedType) a2;
            ParameterizedType pb = (ParameterizedType) b2;
            Object ownerA = pa.getOwnerType();
            Object ownerB = pb.getOwnerType();
            if ((ownerA == ownerB || (ownerA != null && ownerA.equals(ownerB))) && pa.getRawType().equals(pb.getRawType()) && Arrays.equals(pa.getActualTypeArguments(), pb.getActualTypeArguments())) {
                return true;
            }
            return false;
        } else if (a2 instanceof GenericArrayType) {
            if (!(b2 instanceof GenericArrayType)) {
                return false;
            }
            return d(((GenericArrayType) a2).getGenericComponentType(), ((GenericArrayType) b2).getGenericComponentType());
        } else if (a2 instanceof WildcardType) {
            if (!(b2 instanceof WildcardType)) {
                return false;
            }
            WildcardType wa = (WildcardType) a2;
            WildcardType wb = (WildcardType) b2;
            if (!Arrays.equals(wa.getUpperBounds(), wb.getUpperBounds()) || !Arrays.equals(wa.getLowerBounds(), wb.getLowerBounds())) {
                return false;
            }
            return true;
        } else if (!(a2 instanceof TypeVariable) || !(b2 instanceof TypeVariable)) {
            return false;
        } else {
            TypeVariable<?> va = (TypeVariable) a2;
            TypeVariable<?> vb = (TypeVariable) b2;
            if (va.getGenericDeclaration() != vb.getGenericDeclaration() || !va.getName().equals(vb.getName())) {
                return false;
            }
            return true;
        }
    }

    static Type e(Type context, Class<?> rawType, Class<?> toResolve) {
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

    private static int k(Object[] array, Object toFind) {
        for (int i = 0; i < array.length; i++) {
            if (toFind.equals(array[i])) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    static String t(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
    }

    static Type i(Type context, Class<?> contextRawType, Class<?> supertype) {
        if (supertype.isAssignableFrom(contextRawType)) {
            return q(context, contextRawType, e(context, contextRawType, supertype));
        }
        throw new IllegalArgumentException();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: java.lang.reflect.Type[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.reflect.Type q(java.lang.reflect.Type r9, java.lang.Class<?> r10, java.lang.reflect.Type r11) {
        /*
        L_0x0000:
            boolean r0 = r11 instanceof java.lang.reflect.TypeVariable
            if (r0 == 0) goto L_0x000f
            r0 = r11
            java.lang.reflect.TypeVariable r0 = (java.lang.reflect.TypeVariable) r0
            java.lang.reflect.Type r11 = r(r9, r10, r0)
            if (r11 != r0) goto L_0x000e
            return r11
        L_0x000e:
            goto L_0x0000
        L_0x000f:
            boolean r0 = r11 instanceof java.lang.Class
            if (r0 == 0) goto L_0x0031
            r0 = r11
            java.lang.Class r0 = (java.lang.Class) r0
            boolean r0 = r0.isArray()
            if (r0 == 0) goto L_0x0031
            r0 = r11
            java.lang.Class r0 = (java.lang.Class) r0
            java.lang.Class r1 = r0.getComponentType()
            java.lang.reflect.Type r2 = q(r9, r10, r1)
            if (r1 != r2) goto L_0x002b
            r3 = r0
            goto L_0x0030
        L_0x002b:
            retrofit2.x$a r3 = new retrofit2.x$a
            r3.<init>(r2)
        L_0x0030:
            return r3
        L_0x0031:
            boolean r0 = r11 instanceof java.lang.reflect.GenericArrayType
            if (r0 == 0) goto L_0x004a
            r0 = r11
            java.lang.reflect.GenericArrayType r0 = (java.lang.reflect.GenericArrayType) r0
            java.lang.reflect.Type r1 = r0.getGenericComponentType()
            java.lang.reflect.Type r2 = q(r9, r10, r1)
            if (r1 != r2) goto L_0x0044
            r3 = r0
            goto L_0x0049
        L_0x0044:
            retrofit2.x$a r3 = new retrofit2.x$a
            r3.<init>(r2)
        L_0x0049:
            return r3
        L_0x004a:
            boolean r0 = r11 instanceof java.lang.reflect.ParameterizedType
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x008e
            r0 = r11
            java.lang.reflect.ParameterizedType r0 = (java.lang.reflect.ParameterizedType) r0
            java.lang.reflect.Type r3 = r0.getOwnerType()
            java.lang.reflect.Type r4 = q(r9, r10, r3)
            if (r4 == r3) goto L_0x005e
            goto L_0x005f
        L_0x005e:
            r1 = r2
        L_0x005f:
            java.lang.reflect.Type[] r2 = r0.getActualTypeArguments()
            r5 = 0
            int r6 = r2.length
        L_0x0065:
            if (r5 >= r6) goto L_0x0080
            r7 = r2[r5]
            java.lang.reflect.Type r7 = q(r9, r10, r7)
            r8 = r2[r5]
            if (r7 == r8) goto L_0x007d
            if (r1 != 0) goto L_0x007b
            java.lang.Object r8 = r2.clone()
            r2 = r8
            java.lang.reflect.Type[] r2 = (java.lang.reflect.Type[]) r2
            r1 = 1
        L_0x007b:
            r2[r5] = r7
        L_0x007d:
            int r5 = r5 + 1
            goto L_0x0065
        L_0x0080:
            if (r1 == 0) goto L_0x008c
            retrofit2.x$b r5 = new retrofit2.x$b
            java.lang.reflect.Type r6 = r0.getRawType()
            r5.<init>(r4, r6, r2)
            goto L_0x008d
        L_0x008c:
            r5 = r0
        L_0x008d:
            return r5
        L_0x008e:
            boolean r0 = r11 instanceof java.lang.reflect.WildcardType
            if (r0 == 0) goto L_0x00d6
            r0 = r11
            java.lang.reflect.WildcardType r0 = (java.lang.reflect.WildcardType) r0
            java.lang.reflect.Type[] r3 = r0.getLowerBounds()
            java.lang.reflect.Type[] r4 = r0.getUpperBounds()
            int r5 = r3.length
            if (r5 != r1) goto L_0x00bb
            r5 = r3[r2]
            java.lang.reflect.Type r5 = q(r9, r10, r5)
            r6 = r3[r2]
            if (r5 == r6) goto L_0x00ba
            retrofit2.x$c r6 = new retrofit2.x$c
            java.lang.reflect.Type[] r7 = new java.lang.reflect.Type[r1]
            java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
            r7[r2] = r8
            java.lang.reflect.Type[] r1 = new java.lang.reflect.Type[r1]
            r1[r2] = r5
            r6.<init>(r7, r1)
            return r6
        L_0x00ba:
            goto L_0x00d4
        L_0x00bb:
            int r5 = r4.length
            if (r5 != r1) goto L_0x00d4
            r5 = r4[r2]
            java.lang.reflect.Type r5 = q(r9, r10, r5)     // Catch:{ all -> 0x00d7 }
            r6 = r4[r2]
            if (r5 == r6) goto L_0x00d5
            retrofit2.x$c r6 = new retrofit2.x$c
            java.lang.reflect.Type[] r1 = new java.lang.reflect.Type[r1]
            r1[r2] = r5
            java.lang.reflect.Type[] r2 = a
            r6.<init>(r1, r2)
            return r6
        L_0x00d4:
        L_0x00d5:
            return r0
        L_0x00d6:
            return r11
        L_0x00d7:
            r9 = move-exception
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: retrofit2.x.q(java.lang.reflect.Type, java.lang.Class, java.lang.reflect.Type):java.lang.reflect.Type");
    }

    private static Type r(Type context, Class<?> contextRawType, TypeVariable<?> unknown) {
        Class<?> declaredByRaw = c(unknown);
        if (declaredByRaw == null) {
            return unknown;
        }
        Type declaredBy = e(context, contextRawType, declaredByRaw);
        if (!(declaredBy instanceof ParameterizedType)) {
            return unknown;
        }
        return ((ParameterizedType) declaredBy).getActualTypeArguments()[k(declaredByRaw.getTypeParameters(), unknown)];
    }

    @Nullable
    private static Class<?> c(TypeVariable<?> typeVariable) {
        Object genericDeclaration = typeVariable.getGenericDeclaration();
        if (genericDeclaration instanceof Class) {
            return (Class) genericDeclaration;
        }
        return null;
    }

    static void b(Type type) {
        if ((type instanceof Class) && ((Class) type).isPrimitive()) {
            throw new IllegalArgumentException();
        }
    }

    static boolean l(Annotation[] annotations, Class<? extends Annotation> cls) {
        for (Annotation annotation : annotations) {
            if (cls.isInstance(annotation)) {
                return true;
            }
        }
        return false;
    }

    static e0 a(e0 body) {
        okio.c buffer = new okio.c();
        body.source().M0(buffer);
        return e0.create(body.contentType(), body.contentLength(), (e) buffer);
    }

    static Type g(int index, ParameterizedType type) {
        Type[] types = type.getActualTypeArguments();
        if (index < 0 || index >= types.length) {
            throw new IllegalArgumentException("Index " + index + " not in range [0," + types.length + ") for " + type);
        }
        Type paramType = types[index];
        if (paramType instanceof WildcardType) {
            return ((WildcardType) paramType).getUpperBounds()[0];
        }
        return paramType;
    }

    static Type f(int index, ParameterizedType type) {
        Type paramType = type.getActualTypeArguments()[index];
        if (paramType instanceof WildcardType) {
            return ((WildcardType) paramType).getLowerBounds()[0];
        }
        return paramType;
    }

    static boolean j(@Nullable Type type) {
        if (type instanceof Class) {
            return false;
        }
        if (type instanceof ParameterizedType) {
            for (Type typeArgument : ((ParameterizedType) type).getActualTypeArguments()) {
                if (j(typeArgument)) {
                    return true;
                }
            }
            return false;
        } else if (type instanceof GenericArrayType) {
            return j(((GenericArrayType) type).getGenericComponentType());
        } else {
            if ((type instanceof TypeVariable) || (type instanceof WildcardType)) {
                return true;
            }
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + (type == null ? BuildConfig.TRAVIS : type.getClass().getName()));
        }
    }

    /* compiled from: Utils */
    public static final class b implements ParameterizedType {
        @Nullable
        private final Type c;
        private final Type d;
        private final Type[] f;

        b(@Nullable Type ownerType, Type rawType, Type... typeArguments) {
            if (rawType instanceof Class) {
                if ((ownerType == null) != (((Class) rawType).getEnclosingClass() != null ? false : true)) {
                    throw new IllegalArgumentException();
                }
            }
            for (Type typeArgument : typeArguments) {
                Objects.requireNonNull(typeArgument, "typeArgument == null");
                x.b(typeArgument);
            }
            this.c = ownerType;
            this.d = rawType;
            this.f = (Type[]) typeArguments.clone();
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
            return (other instanceof ParameterizedType) && x.d(this, (ParameterizedType) other);
        }

        public int hashCode() {
            int hashCode = Arrays.hashCode(this.f) ^ this.d.hashCode();
            Type type = this.c;
            return hashCode ^ (type != null ? type.hashCode() : 0);
        }

        public String toString() {
            Type[] typeArr = this.f;
            if (typeArr.length == 0) {
                return x.t(this.d);
            }
            StringBuilder result = new StringBuilder((typeArr.length + 1) * 30);
            result.append(x.t(this.d));
            result.append("<");
            result.append(x.t(this.f[0]));
            for (int i = 1; i < this.f.length; i++) {
                result.append(", ");
                result.append(x.t(this.f[i]));
            }
            result.append(">");
            return result.toString();
        }
    }

    /* compiled from: Utils */
    public static final class a implements GenericArrayType {
        private final Type c;

        a(Type componentType) {
            this.c = componentType;
        }

        public Type getGenericComponentType() {
            return this.c;
        }

        public boolean equals(Object o) {
            return (o instanceof GenericArrayType) && x.d(this, (GenericArrayType) o);
        }

        public int hashCode() {
            return this.c.hashCode();
        }

        public String toString() {
            return x.t(this.c) + "[]";
        }
    }

    /* compiled from: Utils */
    public static final class c implements WildcardType {
        private final Type c;
        @Nullable
        private final Type d;

        c(Type[] upperBounds, Type[] lowerBounds) {
            Class<Object> cls = Object.class;
            if (lowerBounds.length > 1) {
                throw new IllegalArgumentException();
            } else if (upperBounds.length != 1) {
                throw new IllegalArgumentException();
            } else if (lowerBounds.length == 1) {
                if (lowerBounds[0] != null) {
                    x.b(lowerBounds[0]);
                    if (upperBounds[0] == cls) {
                        this.d = lowerBounds[0];
                        this.c = cls;
                        return;
                    }
                    throw new IllegalArgumentException();
                }
                throw new NullPointerException();
            } else if (upperBounds[0] != null) {
                x.b(upperBounds[0]);
                this.d = null;
                this.c = upperBounds[0];
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
                return x.a;
            }
            return new Type[]{type};
        }

        public boolean equals(Object other) {
            return (other instanceof WildcardType) && x.d(this, (WildcardType) other);
        }

        public int hashCode() {
            Type type = this.d;
            return (type != null ? type.hashCode() + 31 : 1) ^ (this.c.hashCode() + 31);
        }

        public String toString() {
            if (this.d != null) {
                return "? super " + x.t(this.d);
            } else if (this.c == Object.class) {
                return "?";
            } else {
                return "? extends " + x.t(this.c);
            }
        }
    }

    static void s(Throwable t) {
        if (t instanceof VirtualMachineError) {
            throw ((VirtualMachineError) t);
        } else if (t instanceof ThreadDeath) {
            throw ((ThreadDeath) t);
        } else if (t instanceof LinkageError) {
            throw ((LinkageError) t);
        }
    }
}
