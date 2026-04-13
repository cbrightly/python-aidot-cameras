package com.alibaba.fastjson.util;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import io.netty.util.internal.StringUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

public class FieldInfo implements Comparable<FieldInfo> {
    public final String[] alternateNames;
    public final Class<?> declaringClass;
    public final Field field;
    public final boolean fieldAccess;
    private final JSONField fieldAnnotation;
    public final Class<?> fieldClass;
    public final boolean fieldTransient;
    public final Type fieldType;
    public final String format;
    public final boolean getOnly;
    public final boolean isEnum;
    public final boolean jsonDirect;
    public final String label;
    public final Method method;
    private final JSONField methodAnnotation;
    public final String name;
    public final long nameHashCode;
    public final char[] name_chars;
    private int ordinal;
    public final int parserFeatures;
    public final int serialzeFeatures;
    public final boolean unwrapped;

    public FieldInfo(String name2, Class<?> declaringClass2, Class<?> fieldClass2, Type fieldType2, Field field2, int ordinal2, int serialzeFeatures2, int parserFeatures2) {
        this.ordinal = 0;
        ordinal2 = ordinal2 < 0 ? 0 : ordinal2;
        this.name = name2;
        this.declaringClass = declaringClass2;
        this.fieldClass = fieldClass2;
        this.fieldType = fieldType2;
        this.method = null;
        this.field = field2;
        this.ordinal = ordinal2;
        this.serialzeFeatures = serialzeFeatures2;
        this.parserFeatures = parserFeatures2;
        this.isEnum = fieldClass2.isEnum();
        if (field2 != null) {
            int modifiers = field2.getModifiers();
            if ((modifiers & 1) == 0) {
            }
            this.fieldAccess = true;
            this.fieldTransient = Modifier.isTransient(modifiers);
        } else {
            this.fieldTransient = false;
            this.fieldAccess = false;
        }
        this.name_chars = genFieldNameChars();
        if (field2 != null) {
            TypeUtils.setAccessible(field2);
        }
        this.label = "";
        JSONField jSONField = field2 == null ? null : (JSONField) TypeUtils.getAnnotation(field2, JSONField.class);
        this.fieldAnnotation = jSONField;
        this.methodAnnotation = null;
        this.getOnly = false;
        this.jsonDirect = false;
        this.unwrapped = false;
        this.format = null;
        this.alternateNames = new String[0];
        this.nameHashCode = nameHashCode64(name2, jSONField);
    }

    public FieldInfo(String name2, Method method2, Field field2, Class<?> clazz, Type type, int ordinal2, int serialzeFeatures2, int parserFeatures2, JSONField fieldAnnotation2, JSONField methodAnnotation2, String label2) {
        this(name2, method2, field2, clazz, type, ordinal2, serialzeFeatures2, parserFeatures2, fieldAnnotation2, methodAnnotation2, label2, (Map<TypeVariable, Type>) null);
    }

    public FieldInfo(String name2, Method method2, Field field2, Class<?> clazz, Type type, int ordinal2, int serialzeFeatures2, int parserFeatures2, JSONField fieldAnnotation2, JSONField methodAnnotation2, String label2, Map<TypeVariable, Type> genericInfo) {
        String name3;
        int ordinal3;
        boolean jsonDirect2;
        boolean z;
        Type fieldType2;
        Class<?> fieldClass2;
        Type genericFieldType;
        Type fieldType3;
        Class<?> fieldClass3;
        Method method3 = method2;
        Field field3 = field2;
        Class<?> cls = clazz;
        Type type2 = type;
        String str = label2;
        Class<Object> cls2 = Object.class;
        Class<String> cls3 = String.class;
        this.ordinal = 0;
        if (field3 != null) {
            String fieldName = field2.getName();
            name3 = name2;
            if (fieldName.equals(name3)) {
                name3 = fieldName;
            }
        } else {
            name3 = name2;
        }
        if (ordinal2 < 0) {
            ordinal3 = 0;
        } else {
            ordinal3 = ordinal2;
        }
        this.name = name3;
        this.method = method3;
        this.field = field3;
        this.ordinal = ordinal3;
        this.serialzeFeatures = serialzeFeatures2;
        this.parserFeatures = parserFeatures2;
        this.fieldAnnotation = fieldAnnotation2;
        this.methodAnnotation = methodAnnotation2;
        if (field3 != null) {
            int modifiers = field2.getModifiers();
            this.fieldAccess = (modifiers & 1) != 0 || method3 == null;
            this.fieldTransient = Modifier.isTransient(modifiers) || TypeUtils.isTransient(method2);
        } else {
            this.fieldAccess = false;
            this.fieldTransient = TypeUtils.isTransient(method2);
        }
        if (str == null || label2.length() <= 0) {
            this.label = "";
        } else {
            this.label = str;
        }
        String format2 = null;
        JSONField annotation = getAnnotation();
        this.nameHashCode = nameHashCode64(name3, annotation);
        if (annotation != null) {
            String format3 = annotation.format();
            if (format3.trim().length() == 0) {
                format2 = null;
            } else {
                format2 = format3;
            }
            jsonDirect2 = annotation.jsonDirect();
            this.unwrapped = annotation.unwrapped();
            this.alternateNames = annotation.alternateNames();
        } else {
            jsonDirect2 = false;
            this.unwrapped = false;
            this.alternateNames = new String[0];
        }
        this.format = format2;
        this.name_chars = genFieldNameChars();
        if (method3 != null) {
            TypeUtils.setAccessible(method2);
        }
        if (field3 != null) {
            TypeUtils.setAccessible(field2);
        }
        boolean getOnly2 = false;
        if (method3 != null) {
            Class<?>[] parameterTypes = method2.getParameterTypes();
            Class<?>[] types = parameterTypes;
            if (parameterTypes.length == 1) {
                fieldClass3 = types[0];
                fieldType3 = method2.getGenericParameterTypes()[0];
                z = true;
            } else {
                Class<?>[] types2 = types;
                if (types2.length == 2) {
                    if (types2[0] == cls3) {
                        z = true;
                        if (types2[1] == cls2) {
                            fieldType3 = types2[0];
                            fieldClass3 = fieldType3;
                        } else {
                            fieldClass3 = method2.getReturnType();
                            fieldType3 = method2.getGenericReturnType();
                            getOnly2 = true;
                        }
                    }
                }
                z = true;
                fieldClass3 = method2.getReturnType();
                fieldType3 = method2.getGenericReturnType();
                getOnly2 = true;
            }
            this.declaringClass = method2.getDeclaringClass();
            fieldClass2 = fieldClass3;
            fieldType2 = fieldType3;
        } else {
            z = true;
            Class<?> fieldClass4 = field2.getType();
            Type fieldType4 = field2.getGenericType();
            this.declaringClass = field2.getDeclaringClass();
            getOnly2 = Modifier.isFinal(field2.getModifiers());
            fieldClass2 = fieldClass4;
            fieldType2 = fieldType4;
        }
        this.getOnly = getOnly2;
        this.jsonDirect = (!jsonDirect2 || fieldClass2 != cls3) ? false : z;
        if (cls == null || fieldClass2 != cls2 || !(fieldType2 instanceof TypeVariable) || (genericFieldType = getInheritGenericType(cls, type2, (TypeVariable) fieldType2)) == null) {
            Type genericFieldType2 = fieldType2;
            if (!(fieldType2 instanceof Class)) {
                genericFieldType2 = getFieldType(cls, type2 != null ? type2 : cls, fieldType2, genericInfo);
                if (genericFieldType2 != fieldType2) {
                    if (genericFieldType2 instanceof ParameterizedType) {
                        fieldClass2 = TypeUtils.getClass(genericFieldType2);
                    } else if (genericFieldType2 instanceof Class) {
                        fieldClass2 = TypeUtils.getClass(genericFieldType2);
                    }
                }
            } else {
                Map<TypeVariable, Type> map = genericInfo;
            }
            this.fieldType = genericFieldType2;
            this.fieldClass = fieldClass2;
            this.isEnum = fieldClass2.isEnum();
            return;
        }
        this.fieldClass = TypeUtils.getClass(genericFieldType);
        this.fieldType = genericFieldType;
        this.isEnum = fieldClass2.isEnum();
    }

    private long nameHashCode64(String name2, JSONField annotation) {
        if (annotation == null || annotation.name().length() == 0) {
            return TypeUtils.fnv1a_64_extract(name2);
        }
        return TypeUtils.fnv1a_64_lower(name2);
    }

    /* access modifiers changed from: protected */
    public char[] genFieldNameChars() {
        int nameLen = this.name.length();
        char[] name_chars2 = new char[(nameLen + 3)];
        String str = this.name;
        str.getChars(0, str.length(), name_chars2, 1);
        name_chars2[0] = StringUtil.DOUBLE_QUOTE;
        name_chars2[nameLen + 1] = StringUtil.DOUBLE_QUOTE;
        name_chars2[nameLen + 2] = ':';
        return name_chars2;
    }

    public <T extends Annotation> T getAnnation(Class<T> annotationClass) {
        Field field2;
        if (annotationClass == JSONField.class) {
            return getAnnotation();
        }
        T annotatition = null;
        Method method2 = this.method;
        if (method2 != null) {
            annotatition = TypeUtils.getAnnotation(method2, annotationClass);
        }
        if (annotatition != null || (field2 = this.field) == null) {
            return annotatition;
        }
        return TypeUtils.getAnnotation(field2, annotationClass);
    }

    public static Type getFieldType(Class<?> clazz, Type type, Type fieldType2) {
        return getFieldType(clazz, type, fieldType2, (Map<TypeVariable, Type>) null);
    }

    public static Type getFieldType(Class<?> clazz, Type type, Type fieldType2, Map<TypeVariable, Type> genericInfo) {
        TypeVariable<?>[] typeVariables;
        ParameterizedType paramType;
        if (clazz == null || type == null) {
            return fieldType2;
        }
        if (fieldType2 instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) fieldType2).getGenericComponentType();
            Type componentTypeX = getFieldType(clazz, type, componentType, genericInfo);
            if (componentType != componentTypeX) {
                return Array.newInstance(TypeUtils.getClass(componentTypeX), 0).getClass();
            }
            return fieldType2;
        } else if (!TypeUtils.isGenericParamType(type)) {
            return fieldType2;
        } else {
            if (fieldType2 instanceof TypeVariable) {
                ParameterizedType paramType2 = (ParameterizedType) TypeUtils.getGenericParamType(type);
                TypeVariable<?> typeVar = (TypeVariable) fieldType2;
                TypeVariable<?>[] typeVariables2 = TypeUtils.getClass(paramType2).getTypeParameters();
                for (int i = 0; i < typeVariables2.length; i++) {
                    if (typeVariables2[i].getName().equals(typeVar.getName())) {
                        return paramType2.getActualTypeArguments()[i];
                    }
                }
            }
            if (fieldType2 instanceof ParameterizedType) {
                ParameterizedType parameterizedFieldType = (ParameterizedType) fieldType2;
                Type[] arguments = parameterizedFieldType.getActualTypeArguments();
                boolean changed = getArgument(arguments, genericInfo);
                if (!changed) {
                    if (type instanceof ParameterizedType) {
                        paramType = (ParameterizedType) type;
                        typeVariables = clazz.getTypeParameters();
                    } else if (clazz.getGenericSuperclass() instanceof ParameterizedType) {
                        paramType = (ParameterizedType) clazz.getGenericSuperclass();
                        typeVariables = clazz.getSuperclass().getTypeParameters();
                    } else {
                        paramType = parameterizedFieldType;
                        typeVariables = type.getClass().getTypeParameters();
                    }
                    changed = getArgument(arguments, typeVariables, paramType.getActualTypeArguments());
                }
                if (changed) {
                    return TypeReference.intern(new ParameterizedTypeImpl(arguments, parameterizedFieldType.getOwnerType(), parameterizedFieldType.getRawType()));
                }
            }
            return fieldType2;
        }
    }

    private static boolean getArgument(Type[] typeArgs, Map<TypeVariable, Type> genericInfo) {
        if (genericInfo == null || genericInfo.size() == 0) {
            return false;
        }
        boolean changed = false;
        for (int i = 0; i < typeArgs.length; i++) {
            ParameterizedType parameterizedType = typeArgs[i];
            if (parameterizedType instanceof ParameterizedType) {
                ParameterizedType p_typeArg = parameterizedType;
                Type[] p_typeArg_args = p_typeArg.getActualTypeArguments();
                if (getArgument(p_typeArg_args, genericInfo)) {
                    typeArgs[i] = TypeReference.intern(new ParameterizedTypeImpl(p_typeArg_args, p_typeArg.getOwnerType(), p_typeArg.getRawType()));
                    changed = true;
                }
            } else if ((parameterizedType instanceof TypeVariable) && genericInfo.containsKey(parameterizedType)) {
                typeArgs[i] = genericInfo.get(parameterizedType);
                changed = true;
            }
        }
        return changed;
    }

    private static boolean getArgument(Type[] typeArgs, TypeVariable[] typeVariables, Type[] arguments) {
        if (arguments == null || typeVariables.length == 0) {
            return false;
        }
        boolean changed = false;
        for (int i = 0; i < typeArgs.length; i++) {
            ParameterizedType parameterizedType = typeArgs[i];
            if (parameterizedType instanceof ParameterizedType) {
                ParameterizedType p_typeArg = parameterizedType;
                Type[] p_typeArg_args = p_typeArg.getActualTypeArguments();
                if (getArgument(p_typeArg_args, typeVariables, arguments)) {
                    typeArgs[i] = TypeReference.intern(new ParameterizedTypeImpl(p_typeArg_args, p_typeArg.getOwnerType(), p_typeArg.getRawType()));
                    changed = true;
                }
            } else if (parameterizedType instanceof TypeVariable) {
                for (int j = 0; j < typeVariables.length; j++) {
                    if (parameterizedType.equals(typeVariables[j])) {
                        typeArgs[i] = arguments[j];
                        changed = true;
                    }
                }
            }
        }
        return changed;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: java.lang.reflect.GenericDeclaration} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: java.lang.Class<?>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.lang.Class<?>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: java.lang.Class<?>} */
    /* JADX WARNING: type inference failed for: r2v7, types: [java.lang.reflect.GenericDeclaration] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.reflect.Type getInheritGenericType(java.lang.Class<?> r8, java.lang.reflect.Type r9, java.lang.reflect.TypeVariable<?> r10) {
        /*
            java.lang.reflect.GenericDeclaration r0 = r10.getGenericDeclaration()
            r1 = 0
            boolean r2 = r0 instanceof java.lang.Class
            if (r2 == 0) goto L_0x0010
            java.lang.reflect.GenericDeclaration r2 = r10.getGenericDeclaration()
            r1 = r2
            java.lang.Class r1 = (java.lang.Class) r1
        L_0x0010:
            r2 = 0
            if (r1 != r8) goto L_0x001f
            boolean r3 = r9 instanceof java.lang.reflect.ParameterizedType
            if (r3 == 0) goto L_0x0044
            r3 = r9
            java.lang.reflect.ParameterizedType r3 = (java.lang.reflect.ParameterizedType) r3
            java.lang.reflect.Type[] r2 = r3.getActualTypeArguments()
            goto L_0x0044
        L_0x001f:
            r3 = r8
        L_0x0020:
            if (r3 == 0) goto L_0x0044
            java.lang.Class<java.lang.Object> r4 = java.lang.Object.class
            if (r3 == r4) goto L_0x0044
            if (r3 == r1) goto L_0x0044
            java.lang.reflect.Type r4 = r3.getGenericSuperclass()
            boolean r5 = r4 instanceof java.lang.reflect.ParameterizedType
            if (r5 == 0) goto L_0x003f
            r5 = r4
            java.lang.reflect.ParameterizedType r5 = (java.lang.reflect.ParameterizedType) r5
            java.lang.reflect.Type[] r6 = r5.getActualTypeArguments()
            java.lang.reflect.TypeVariable[] r7 = r3.getTypeParameters()
            getArgument(r6, r7, r2)
            r2 = r6
        L_0x003f:
            java.lang.Class r3 = r3.getSuperclass()
            goto L_0x0020
        L_0x0044:
            if (r2 == 0) goto L_0x0061
            if (r1 != 0) goto L_0x0049
            goto L_0x0061
        L_0x0049:
            r3 = 0
            java.lang.reflect.TypeVariable[] r4 = r1.getTypeParameters()
            r5 = 0
        L_0x004f:
            int r6 = r4.length
            if (r5 >= r6) goto L_0x0060
            r6 = r4[r5]
            boolean r6 = r10.equals(r6)
            if (r6 == 0) goto L_0x005d
            r3 = r2[r5]
            goto L_0x0060
        L_0x005d:
            int r5 = r5 + 1
            goto L_0x004f
        L_0x0060:
            return r3
        L_0x0061:
            r3 = 0
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.FieldInfo.getInheritGenericType(java.lang.Class, java.lang.reflect.Type, java.lang.reflect.TypeVariable):java.lang.reflect.Type");
    }

    public String toString() {
        return this.name;
    }

    public Member getMember() {
        Method method2 = this.method;
        if (method2 != null) {
            return method2;
        }
        return this.field;
    }

    /* access modifiers changed from: protected */
    public Class<?> getDeclaredClass() {
        Method method2 = this.method;
        if (method2 != null) {
            return method2.getDeclaringClass();
        }
        Field field2 = this.field;
        if (field2 != null) {
            return field2.getDeclaringClass();
        }
        return null;
    }

    public int compareTo(FieldInfo o) {
        Method method2 = o.method;
        if (method2 != null && this.method != null && method2.isBridge() && !this.method.isBridge() && o.method.getName().equals(this.method.getName())) {
            return 1;
        }
        int i = this.ordinal;
        int i2 = o.ordinal;
        if (i < i2) {
            return -1;
        }
        if (i > i2) {
            return 1;
        }
        int result = this.name.compareTo(o.name);
        if (result != 0) {
            return result;
        }
        Class<?> thisDeclaringClass = getDeclaredClass();
        Class<?> otherDeclaringClass = o.getDeclaredClass();
        if (!(thisDeclaringClass == null || otherDeclaringClass == null || thisDeclaringClass == otherDeclaringClass)) {
            if (thisDeclaringClass.isAssignableFrom(otherDeclaringClass)) {
                return -1;
            }
            if (otherDeclaringClass.isAssignableFrom(thisDeclaringClass)) {
                return 1;
            }
        }
        Field field2 = this.field;
        boolean oSameType = false;
        boolean isSampeType = field2 != null && field2.getType() == this.fieldClass;
        Field field3 = o.field;
        if (field3 != null && field3.getType() == o.fieldClass) {
            oSameType = true;
        }
        if (isSampeType && !oSameType) {
            return 1;
        }
        if (oSameType && !isSampeType) {
            return -1;
        }
        if (o.fieldClass.isPrimitive() && !this.fieldClass.isPrimitive()) {
            return 1;
        }
        if (this.fieldClass.isPrimitive() && !o.fieldClass.isPrimitive()) {
            return -1;
        }
        if (o.fieldClass.getName().startsWith("java.") && !this.fieldClass.getName().startsWith("java.")) {
            return 1;
        }
        if (!this.fieldClass.getName().startsWith("java.") || o.fieldClass.getName().startsWith("java.")) {
            return this.fieldClass.getName().compareTo(o.fieldClass.getName());
        }
        return -1;
    }

    public JSONField getAnnotation() {
        JSONField jSONField = this.fieldAnnotation;
        if (jSONField != null) {
            return jSONField;
        }
        return this.methodAnnotation;
    }

    public String getFormat() {
        return this.format;
    }

    public Object get(Object javaObject) {
        Method method2 = this.method;
        if (method2 != null) {
            return method2.invoke(javaObject, new Object[0]);
        }
        return this.field.get(javaObject);
    }

    public void set(Object javaObject, Object value) {
        Method method2 = this.method;
        if (method2 != null) {
            method2.invoke(javaObject, new Object[]{value});
            return;
        }
        this.field.set(javaObject, value);
    }

    public void setAccessible() {
        Method method2 = this.method;
        if (method2 != null) {
            TypeUtils.setAccessible(method2);
        } else {
            TypeUtils.setAccessible(this.field);
        }
    }
}
