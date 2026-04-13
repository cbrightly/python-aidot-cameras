package io.netty.util.internal;

import java.util.HashMap;
import java.util.Map;

public abstract class TypeParameterMatcher {
    private static final TypeParameterMatcher NOOP = new TypeParameterMatcher() {
        public boolean match(Object msg) {
            return true;
        }
    };

    public abstract boolean match(Object obj);

    public static TypeParameterMatcher get(Class<?> parameterType) {
        Map<Class<?>, TypeParameterMatcher> getCache = InternalThreadLocalMap.get().typeParameterMatcherGetCache();
        TypeParameterMatcher matcher = getCache.get(parameterType);
        if (matcher == null) {
            if (parameterType == Object.class) {
                matcher = NOOP;
            } else {
                matcher = new ReflectiveMatcher(parameterType);
            }
            getCache.put(parameterType, matcher);
        }
        return matcher;
    }

    public static TypeParameterMatcher find(Object object, Class<?> parametrizedSuperclass, String typeParamName) {
        Map<Class<?>, Map<String, TypeParameterMatcher>> findCache = InternalThreadLocalMap.get().typeParameterMatcherFindCache();
        Class<?> thisClass = object.getClass();
        Map<String, TypeParameterMatcher> map = findCache.get(thisClass);
        if (map == null) {
            map = new HashMap<>();
            findCache.put(thisClass, map);
        }
        TypeParameterMatcher matcher = map.get(typeParamName);
        if (matcher != null) {
            return matcher;
        }
        TypeParameterMatcher matcher2 = get(find0(object, parametrizedSuperclass, typeParamName));
        map.put(typeParamName, matcher2);
        return matcher2;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: java.lang.Class<?>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: java.lang.Class<?>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.Class<?>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: java.lang.Class<?>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: java.lang.Class<?>} */
    /* JADX WARNING: type inference failed for: r9v2, types: [java.lang.reflect.GenericDeclaration] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Class<?> find0(java.lang.Object r10, java.lang.Class<?> r11, java.lang.String r12) {
        /*
            java.lang.Class<java.lang.Object> r0 = java.lang.Object.class
            java.lang.Class r1 = r10.getClass()
            r2 = r1
        L_0x0007:
            java.lang.Class r3 = r2.getSuperclass()
            if (r3 != r11) goto L_0x00c3
            r3 = -1
            java.lang.Class r4 = r2.getSuperclass()
            java.lang.reflect.TypeVariable[] r4 = r4.getTypeParameters()
            r5 = 0
        L_0x0017:
            int r6 = r4.length
            if (r5 >= r6) goto L_0x002b
            r6 = r4[r5]
            java.lang.String r6 = r6.getName()
            boolean r6 = r12.equals(r6)
            if (r6 == 0) goto L_0x0028
            r3 = r5
            goto L_0x002b
        L_0x0028:
            int r5 = r5 + 1
            goto L_0x0017
        L_0x002b:
            if (r3 < 0) goto L_0x00a3
            java.lang.reflect.Type r5 = r2.getGenericSuperclass()
            boolean r6 = r5 instanceof java.lang.reflect.ParameterizedType
            if (r6 != 0) goto L_0x0036
            return r0
        L_0x0036:
            r6 = r5
            java.lang.reflect.ParameterizedType r6 = (java.lang.reflect.ParameterizedType) r6
            java.lang.reflect.Type[] r6 = r6.getActualTypeArguments()
            r7 = r6[r3]
            boolean r8 = r7 instanceof java.lang.reflect.ParameterizedType
            if (r8 == 0) goto L_0x004a
            r8 = r7
            java.lang.reflect.ParameterizedType r8 = (java.lang.reflect.ParameterizedType) r8
            java.lang.reflect.Type r7 = r8.getRawType()
        L_0x004a:
            boolean r8 = r7 instanceof java.lang.Class
            if (r8 == 0) goto L_0x0052
            r0 = r7
            java.lang.Class r0 = (java.lang.Class) r0
            return r0
        L_0x0052:
            boolean r8 = r7 instanceof java.lang.reflect.GenericArrayType
            if (r8 == 0) goto L_0x0079
            r8 = r7
            java.lang.reflect.GenericArrayType r8 = (java.lang.reflect.GenericArrayType) r8
            java.lang.reflect.Type r8 = r8.getGenericComponentType()
            boolean r9 = r8 instanceof java.lang.reflect.ParameterizedType
            if (r9 == 0) goto L_0x0068
            r9 = r8
            java.lang.reflect.ParameterizedType r9 = (java.lang.reflect.ParameterizedType) r9
            java.lang.reflect.Type r8 = r9.getRawType()
        L_0x0068:
            boolean r9 = r8 instanceof java.lang.Class
            if (r9 == 0) goto L_0x0079
            r0 = r8
            java.lang.Class r0 = (java.lang.Class) r0
            r9 = 0
            java.lang.Object r0 = java.lang.reflect.Array.newInstance(r0, r9)
            java.lang.Class r0 = r0.getClass()
            return r0
        L_0x0079:
            boolean r8 = r7 instanceof java.lang.reflect.TypeVariable
            if (r8 == 0) goto L_0x009e
            r8 = r7
            java.lang.reflect.TypeVariable r8 = (java.lang.reflect.TypeVariable) r8
            r2 = r1
            java.lang.reflect.GenericDeclaration r9 = r8.getGenericDeclaration()
            boolean r9 = r9 instanceof java.lang.Class
            if (r9 != 0) goto L_0x008a
            return r0
        L_0x008a:
            java.lang.reflect.GenericDeclaration r9 = r8.getGenericDeclaration()
            r11 = r9
            java.lang.Class r11 = (java.lang.Class) r11
            java.lang.String r12 = r8.getName()
            boolean r9 = r11.isAssignableFrom(r1)
            if (r9 == 0) goto L_0x009d
            goto L_0x0007
        L_0x009d:
            return r0
        L_0x009e:
            java.lang.Class r0 = fail(r1, r12)
            return r0
        L_0x00a3:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "unknown type parameter '"
            r5.append(r6)
            r5.append(r12)
            java.lang.String r6 = "': "
            r5.append(r6)
            r5.append(r11)
            java.lang.String r5 = r5.toString()
            r0.<init>(r5)
            throw r0
        L_0x00c3:
            java.lang.Class r2 = r2.getSuperclass()
            if (r2 != 0) goto L_0x00ce
            java.lang.Class r0 = fail(r1, r12)
            return r0
        L_0x00ce:
            goto L_0x0007
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.TypeParameterMatcher.find0(java.lang.Object, java.lang.Class, java.lang.String):java.lang.Class");
    }

    private static Class<?> fail(Class<?> type, String typeParamName) {
        throw new IllegalStateException("cannot determine the type of the type parameter '" + typeParamName + "': " + type);
    }

    public static final class ReflectiveMatcher extends TypeParameterMatcher {
        private final Class<?> type;

        ReflectiveMatcher(Class<?> type2) {
            this.type = type2;
        }

        public boolean match(Object msg) {
            return this.type.isInstance(msg);
        }
    }

    TypeParameterMatcher() {
    }
}
