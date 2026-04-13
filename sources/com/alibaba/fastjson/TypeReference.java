package com.alibaba.fastjson;

import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TypeReference<T> {
    public static final Type LIST_STRING = new TypeReference<List<String>>() {
    }.getType();
    static ConcurrentMap<Type, Type> classTypeCache = new ConcurrentHashMap(16, 0.75f, 1);
    protected final Type type;

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: java.lang.reflect.Type} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected TypeReference() {
        /*
            r4 = this;
            r4.<init>()
            java.lang.Class r0 = r4.getClass()
            java.lang.reflect.Type r0 = r0.getGenericSuperclass()
            r1 = r0
            java.lang.reflect.ParameterizedType r1 = (java.lang.reflect.ParameterizedType) r1
            java.lang.reflect.Type[] r1 = r1.getActualTypeArguments()
            r2 = 0
            r1 = r1[r2]
            java.util.concurrent.ConcurrentMap<java.lang.reflect.Type, java.lang.reflect.Type> r2 = classTypeCache
            java.lang.Object r2 = r2.get(r1)
            java.lang.reflect.Type r2 = (java.lang.reflect.Type) r2
            if (r2 != 0) goto L_0x002d
            java.util.concurrent.ConcurrentMap<java.lang.reflect.Type, java.lang.reflect.Type> r3 = classTypeCache
            r3.putIfAbsent(r1, r1)
            java.util.concurrent.ConcurrentMap<java.lang.reflect.Type, java.lang.reflect.Type> r3 = classTypeCache
            java.lang.Object r3 = r3.get(r1)
            r2 = r3
            java.lang.reflect.Type r2 = (java.lang.reflect.Type) r2
        L_0x002d:
            r4.type = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.TypeReference.<init>():void");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v5, resolved type: java.lang.reflect.Type} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected TypeReference(java.lang.reflect.Type... r10) {
        /*
            r9 = this;
            r9.<init>()
            java.lang.Class r0 = r9.getClass()
            java.lang.reflect.Type r1 = r0.getGenericSuperclass()
            r2 = r1
            java.lang.reflect.ParameterizedType r2 = (java.lang.reflect.ParameterizedType) r2
            java.lang.reflect.Type[] r2 = r2.getActualTypeArguments()
            r3 = 0
            r2 = r2[r3]
            java.lang.reflect.ParameterizedType r2 = (java.lang.reflect.ParameterizedType) r2
            java.lang.reflect.Type r3 = r2.getRawType()
            java.lang.reflect.Type[] r4 = r2.getActualTypeArguments()
            r5 = 0
            r6 = 0
        L_0x0021:
            int r7 = r4.length
            if (r6 >= r7) goto L_0x0057
            r7 = r4[r6]
            boolean r7 = r7 instanceof java.lang.reflect.TypeVariable
            if (r7 == 0) goto L_0x0034
            int r7 = r10.length
            if (r5 >= r7) goto L_0x0034
            int r7 = r5 + 1
            r5 = r10[r5]
            r4[r6] = r5
            r5 = r7
        L_0x0034:
            r7 = r4[r6]
            boolean r7 = r7 instanceof java.lang.reflect.GenericArrayType
            if (r7 == 0) goto L_0x0044
            r7 = r4[r6]
            java.lang.reflect.GenericArrayType r7 = (java.lang.reflect.GenericArrayType) r7
            java.lang.reflect.Type r7 = com.alibaba.fastjson.util.TypeUtils.checkPrimitiveArray(r7)
            r4[r6] = r7
        L_0x0044:
            r7 = r4[r6]
            boolean r7 = r7 instanceof java.lang.reflect.ParameterizedType
            if (r7 == 0) goto L_0x0054
            r7 = r4[r6]
            java.lang.reflect.ParameterizedType r7 = (java.lang.reflect.ParameterizedType) r7
            java.lang.reflect.Type r7 = r9.handlerParameterizedType(r7, r10, r5)
            r4[r6] = r7
        L_0x0054:
            int r6 = r6 + 1
            goto L_0x0021
        L_0x0057:
            com.alibaba.fastjson.util.ParameterizedTypeImpl r6 = new com.alibaba.fastjson.util.ParameterizedTypeImpl
            r6.<init>(r4, r0, r3)
            java.util.concurrent.ConcurrentMap<java.lang.reflect.Type, java.lang.reflect.Type> r7 = classTypeCache
            java.lang.Object r7 = r7.get(r6)
            java.lang.reflect.Type r7 = (java.lang.reflect.Type) r7
            if (r7 != 0) goto L_0x0074
            java.util.concurrent.ConcurrentMap<java.lang.reflect.Type, java.lang.reflect.Type> r8 = classTypeCache
            r8.putIfAbsent(r6, r6)
            java.util.concurrent.ConcurrentMap<java.lang.reflect.Type, java.lang.reflect.Type> r8 = classTypeCache
            java.lang.Object r8 = r8.get(r6)
            r7 = r8
            java.lang.reflect.Type r7 = (java.lang.reflect.Type) r7
        L_0x0074:
            r9.type = r7
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.TypeReference.<init>(java.lang.reflect.Type[]):void");
    }

    public static Type intern(ParameterizedTypeImpl type2) {
        Type cachedType = (Type) classTypeCache.get(type2);
        if (cachedType != null) {
            return cachedType;
        }
        classTypeCache.putIfAbsent(type2, type2);
        return (Type) classTypeCache.get(type2);
    }

    private Type handlerParameterizedType(ParameterizedType type2, Type[] actualTypeArguments, int actualIndex) {
        Class<?> thisClass = getClass();
        Type rawType = type2.getRawType();
        Type[] argTypes = type2.getActualTypeArguments();
        for (int i = 0; i < argTypes.length; i++) {
            if ((argTypes[i] instanceof TypeVariable) && actualIndex < actualTypeArguments.length) {
                argTypes[i] = actualTypeArguments[actualIndex];
                actualIndex++;
            }
            if (argTypes[i] instanceof GenericArrayType) {
                argTypes[i] = TypeUtils.checkPrimitiveArray((GenericArrayType) argTypes[i]);
            }
            if (argTypes[i] instanceof ParameterizedType) {
                argTypes[i] = handlerParameterizedType((ParameterizedType) argTypes[i], actualTypeArguments, actualIndex);
            }
        }
        return new ParameterizedTypeImpl(argTypes, thisClass, rawType);
    }

    public Type getType() {
        return this.type;
    }
}
