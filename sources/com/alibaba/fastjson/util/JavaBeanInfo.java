package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class JavaBeanInfo {
    public final Method buildMethod;
    public final Class<?> builderClass;
    public final Class<?> clazz;
    public final Constructor<?> creatorConstructor;
    public Type[] creatorConstructorParameterTypes;
    public String[] creatorConstructorParameters;
    public final Constructor<?> defaultConstructor;
    public final int defaultConstructorParameterSize;
    public final Method factoryMethod;
    public final FieldInfo[] fields;
    public final JSONType jsonType;

    /* renamed from: kotlin  reason: collision with root package name */
    public boolean f32kotlin;
    public Constructor<?> kotlinDefaultConstructor;
    public String[] orders;
    public final int parserFeatures;
    public final FieldInfo[] sortedFields;
    public final String typeKey;
    public final String typeName;

    public JavaBeanInfo(Class<?> clazz2, Class<?> builderClass2, Constructor<?> defaultConstructor2, Constructor<?> creatorConstructor2, Method factoryMethod2, Method buildMethod2, JSONType jsonType2, List<FieldInfo> fieldList) {
        boolean match;
        Annotation[][] paramAnnotationArrays;
        Class<?> cls = clazz2;
        Constructor<?> constructor = defaultConstructor2;
        Constructor<?> constructor2 = creatorConstructor2;
        Method method = factoryMethod2;
        JSONType jSONType = jsonType2;
        this.clazz = cls;
        this.builderClass = builderClass2;
        this.defaultConstructor = constructor;
        this.creatorConstructor = constructor2;
        this.factoryMethod = method;
        this.parserFeatures = TypeUtils.getParserFeatures(clazz2);
        this.buildMethod = buildMethod2;
        this.jsonType = jSONType;
        String[] strArr = null;
        if (jSONType != null) {
            String typeName2 = jsonType2.typeName();
            String typeKey2 = jsonType2.typeKey();
            this.typeKey = typeKey2.length() > 0 ? typeKey2 : null;
            if (typeName2.length() != 0) {
                this.typeName = typeName2;
            } else {
                this.typeName = clazz2.getName();
            }
            String[] orders2 = jsonType2.orders();
            this.orders = orders2.length != 0 ? orders2 : strArr;
        } else {
            this.typeName = clazz2.getName();
            this.typeKey = null;
            this.orders = null;
        }
        FieldInfo[] fieldInfoArr = new FieldInfo[fieldList.size()];
        this.fields = fieldInfoArr;
        fieldList.toArray(fieldInfoArr);
        FieldInfo[] sortedFields2 = new FieldInfo[fieldInfoArr.length];
        if (this.orders != null) {
            LinkedHashMap<String, FieldInfo> map = new LinkedHashMap<>(fieldList.size());
            for (FieldInfo field : fieldInfoArr) {
                map.put(field.name, field);
            }
            int i = 0;
            for (String item : this.orders) {
                FieldInfo field2 = map.get(item);
                if (field2 != null) {
                    sortedFields2[i] = field2;
                    map.remove(item);
                    i++;
                }
            }
            for (FieldInfo field3 : map.values()) {
                sortedFields2[i] = field3;
                i++;
            }
        } else {
            System.arraycopy(fieldInfoArr, 0, sortedFields2, 0, fieldInfoArr.length);
            Arrays.sort(sortedFields2);
        }
        this.sortedFields = Arrays.equals(this.fields, sortedFields2) ? this.fields : sortedFields2;
        if (constructor != null) {
            this.defaultConstructorParameterSize = defaultConstructor2.getParameterTypes().length;
        } else if (method != null) {
            this.defaultConstructorParameterSize = factoryMethod2.getParameterTypes().length;
        } else {
            this.defaultConstructorParameterSize = 0;
        }
        if (constructor2 != null) {
            this.creatorConstructorParameterTypes = creatorConstructor2.getParameterTypes();
            boolean isKotlin = TypeUtils.isKotlin(clazz2);
            this.f32kotlin = isKotlin;
            if (isKotlin) {
                this.creatorConstructorParameters = TypeUtils.getKoltinConstructorParameters(clazz2);
                int i2 = 0;
                try {
                    this.kotlinDefaultConstructor = cls.getConstructor(new Class[0]);
                } catch (Throwable th) {
                }
                Annotation[][] paramAnnotationArrays2 = TypeUtils.getParameterAnnotations((Constructor) creatorConstructor2);
                int i3 = 0;
                while (i3 < this.creatorConstructorParameters.length && i3 < paramAnnotationArrays2.length) {
                    Annotation[] paramAnnotations = paramAnnotationArrays2[i3];
                    JSONField fieldAnnotation = null;
                    int length = paramAnnotations.length;
                    while (true) {
                        if (i2 >= length) {
                            paramAnnotationArrays = paramAnnotationArrays2;
                            break;
                        }
                        paramAnnotationArrays = paramAnnotationArrays2;
                        Annotation paramAnnotation = paramAnnotations[i2];
                        if (paramAnnotation instanceof JSONField) {
                            fieldAnnotation = (JSONField) paramAnnotation;
                            break;
                        }
                        i2++;
                        Class<?> cls2 = clazz2;
                        paramAnnotationArrays2 = paramAnnotationArrays;
                    }
                    if (fieldAnnotation != null) {
                        String fieldAnnotationName = fieldAnnotation.name();
                        if (fieldAnnotationName.length() > 0) {
                            this.creatorConstructorParameters[i3] = fieldAnnotationName;
                        }
                    }
                    i3++;
                    Class<?> cls3 = clazz2;
                    paramAnnotationArrays2 = paramAnnotationArrays;
                    i2 = 0;
                }
                return;
            }
            if (this.creatorConstructorParameterTypes.length != this.fields.length) {
                match = false;
            } else {
                match = true;
                int i4 = 0;
                while (true) {
                    Type[] typeArr = this.creatorConstructorParameterTypes;
                    if (i4 >= typeArr.length) {
                        break;
                    } else if (typeArr[i4] != this.fields[i4].fieldClass) {
                        match = false;
                        break;
                    } else {
                        i4++;
                    }
                }
            }
            if (!match) {
                this.creatorConstructorParameters = ASMUtils.lookupParameterNames(creatorConstructor2);
            }
        }
    }

    private static FieldInfo getField(List<FieldInfo> fieldList, String propertyName) {
        for (FieldInfo item : fieldList) {
            if (item.name.equals(propertyName)) {
                return item;
            }
            Field field = item.field;
            if (field != null && item.getAnnotation() != null && field.getName().equals(propertyName)) {
                return item;
            }
        }
        return null;
    }

    static boolean add(List<FieldInfo> fieldList, FieldInfo field) {
        int i = fieldList.size() - 1;
        while (i >= 0) {
            FieldInfo item = fieldList.get(i);
            if (!item.name.equals(field.name) || (item.getOnly && !field.getOnly)) {
                i--;
            } else if (item.fieldClass.isAssignableFrom(field.fieldClass)) {
                fieldList.set(i, field);
                return true;
            } else if (item.compareTo(field) >= 0) {
                return false;
            } else {
                fieldList.set(i, field);
                return true;
            }
        }
        fieldList.add(field);
        return true;
    }

    public static JavaBeanInfo build(Class<?> clazz2, Type type, PropertyNamingStrategy propertyNamingStrategy) {
        return build(clazz2, type, propertyNamingStrategy, false, TypeUtils.compatibleWithJavaBean, false);
    }

    /* JADX WARNING: type inference failed for: r8v0, types: [java.lang.Class<?>, java.lang.Class] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.Map<java.lang.reflect.TypeVariable, java.lang.reflect.Type> buildGenericInfo(java.lang.Class<?> r8) {
        /*
            r0 = r8
            java.lang.Class r1 = r8.getSuperclass()
            if (r1 != 0) goto L_0x0009
            r2 = 0
            return r2
        L_0x0009:
            r2 = 0
        L_0x000a:
            if (r1 == 0) goto L_0x0058
            java.lang.Class<java.lang.Object> r3 = java.lang.Object.class
            if (r1 == r3) goto L_0x0058
            java.lang.reflect.Type r3 = r0.getGenericSuperclass()
            boolean r3 = r3 instanceof java.lang.reflect.ParameterizedType
            if (r3 == 0) goto L_0x0052
            java.lang.reflect.Type r3 = r0.getGenericSuperclass()
            java.lang.reflect.ParameterizedType r3 = (java.lang.reflect.ParameterizedType) r3
            java.lang.reflect.Type[] r3 = r3.getActualTypeArguments()
            java.lang.reflect.TypeVariable[] r4 = r1.getTypeParameters()
            r5 = 0
        L_0x0027:
            int r6 = r3.length
            if (r5 >= r6) goto L_0x0052
            if (r2 != 0) goto L_0x0032
            java.util.HashMap r6 = new java.util.HashMap
            r6.<init>()
            r2 = r6
        L_0x0032:
            r6 = r3[r5]
            boolean r6 = r2.containsKey(r6)
            if (r6 == 0) goto L_0x0048
            r6 = r3[r5]
            java.lang.Object r6 = r2.get(r6)
            java.lang.reflect.Type r6 = (java.lang.reflect.Type) r6
            r7 = r4[r5]
            r2.put(r7, r6)
            goto L_0x004f
        L_0x0048:
            r6 = r4[r5]
            r7 = r3[r5]
            r2.put(r6, r7)
        L_0x004f:
            int r5 = r5 + 1
            goto L_0x0027
        L_0x0052:
            r0 = r1
            java.lang.Class r1 = r1.getSuperclass()
            goto L_0x000a
        L_0x0058:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.JavaBeanInfo.buildGenericInfo(java.lang.Class):java.util.Map");
    }

    public static JavaBeanInfo build(Class<?> clazz2, Type type, PropertyNamingStrategy propertyNamingStrategy, boolean fieldBased, boolean compatibleWithJavaBean) {
        return build(clazz2, type, propertyNamingStrategy, fieldBased, compatibleWithJavaBean, false);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: java.lang.reflect.Constructor[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v0, resolved type: java.lang.reflect.Constructor} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r64v1, resolved type: java.lang.reflect.Constructor[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v1, resolved type: java.lang.reflect.Constructor} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v2, resolved type: java.lang.reflect.Constructor} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v48, resolved type: java.lang.reflect.Constructor} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v19, resolved type: java.lang.reflect.Constructor[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v77, resolved type: java.lang.reflect.Constructor} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v88, resolved type: java.lang.Class<?>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v89, resolved type: java.lang.Class<?>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v91, resolved type: java.lang.Class<?>} */
    /* JADX WARNING: type inference failed for: r2v21, types: [java.lang.annotation.Annotation] */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x0437, code lost:
        if (r12.length <= r1.length) goto L_0x0448;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00bd, code lost:
        if (r3 != null) goto L_0x00c2;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:191:0x0462  */
    /* JADX WARNING: Removed duplicated region for block: B:192:0x0468  */
    /* JADX WARNING: Removed duplicated region for block: B:196:0x046f  */
    /* JADX WARNING: Removed duplicated region for block: B:231:0x057b  */
    /* JADX WARNING: Removed duplicated region for block: B:237:0x05c3  */
    /* JADX WARNING: Removed duplicated region for block: B:240:0x05cd  */
    /* JADX WARNING: Removed duplicated region for block: B:287:0x0744  */
    /* JADX WARNING: Removed duplicated region for block: B:288:0x0747  */
    /* JADX WARNING: Removed duplicated region for block: B:313:0x07ee  */
    /* JADX WARNING: Removed duplicated region for block: B:317:0x0801  */
    /* JADX WARNING: Removed duplicated region for block: B:419:0x0b53  */
    /* JADX WARNING: Removed duplicated region for block: B:421:0x0b59  */
    /* JADX WARNING: Removed duplicated region for block: B:424:0x0b84  */
    /* JADX WARNING: Removed duplicated region for block: B:428:0x0b89  */
    /* JADX WARNING: Removed duplicated region for block: B:438:0x0c35  */
    /* JADX WARNING: Removed duplicated region for block: B:441:0x0c50  */
    /* JADX WARNING: Removed duplicated region for block: B:442:0x0c56  */
    /* JADX WARNING: Removed duplicated region for block: B:448:0x0cd3  */
    /* JADX WARNING: Removed duplicated region for block: B:507:0x0ed3  */
    /* JADX WARNING: Removed duplicated region for block: B:517:0x0f02  */
    /* JADX WARNING: Removed duplicated region for block: B:556:0x0cb3 A[EDGE_INSN: B:556:0x0cb3->B:446:0x0cb3 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.alibaba.fastjson.util.JavaBeanInfo build(java.lang.Class<?> r62, java.lang.reflect.Type r63, com.alibaba.fastjson.PropertyNamingStrategy r64, boolean r65, boolean r66, boolean r67) {
        /*
            r14 = r62
            r13 = r63
            r12 = r67
            java.lang.Class<java.lang.String> r11 = java.lang.String.class
            java.lang.Class<java.lang.Object> r10 = java.lang.Object.class
            java.lang.Class<com.alibaba.fastjson.annotation.JSONType> r0 = com.alibaba.fastjson.annotation.JSONType.class
            java.lang.annotation.Annotation r0 = com.alibaba.fastjson.util.TypeUtils.getAnnotation((java.lang.Class<?>) r14, r0)
            r15 = r0
            com.alibaba.fastjson.annotation.JSONType r15 = (com.alibaba.fastjson.annotation.JSONType) r15
            if (r15 == 0) goto L_0x0022
            com.alibaba.fastjson.PropertyNamingStrategy r0 = r15.naming()
            if (r0 == 0) goto L_0x0022
            com.alibaba.fastjson.PropertyNamingStrategy r1 = com.alibaba.fastjson.PropertyNamingStrategy.CamelCase
            if (r0 == r1) goto L_0x0022
            r1 = r0
            r9 = r1
            goto L_0x0024
        L_0x0022:
            r9 = r64
        L_0x0024:
            java.lang.Class r8 = getBuilderClass(r14, r15)
            java.lang.reflect.Field[] r7 = r62.getDeclaredFields()
            java.lang.reflect.Method[] r6 = r62.getMethods()
            java.util.Map r19 = buildGenericInfo(r62)
            boolean r20 = com.alibaba.fastjson.util.TypeUtils.isKotlin(r62)
            java.lang.reflect.Constructor[] r5 = r62.getDeclaredConstructors()
            r0 = 0
            r4 = 1
            if (r20 == 0) goto L_0x0047
            int r1 = r5.length
            if (r1 != r4) goto L_0x0044
            goto L_0x0047
        L_0x0044:
            r21 = r0
            goto L_0x005a
        L_0x0047:
            if (r8 != 0) goto L_0x0050
            java.lang.reflect.Constructor r0 = getDefaultConstructor(r14, r5)
            r21 = r0
            goto L_0x005a
        L_0x0050:
            java.lang.reflect.Constructor[] r1 = r8.getDeclaredConstructors()
            java.lang.reflect.Constructor r0 = getDefaultConstructor(r8, r1)
            r21 = r0
        L_0x005a:
            r16 = 0
            r22 = 0
            r17 = 0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r3 = r0
            if (r65 == 0) goto L_0x009a
            r0 = r62
        L_0x006a:
            if (r0 == 0) goto L_0x0078
            java.lang.reflect.Field[] r1 = r0.getDeclaredFields()
            computeFields(r14, r13, r9, r3, r1)
            java.lang.Class r0 = r0.getSuperclass()
            goto L_0x006a
        L_0x0078:
            if (r21 == 0) goto L_0x007d
            com.alibaba.fastjson.util.TypeUtils.setAccessible(r21)
        L_0x007d:
            com.alibaba.fastjson.util.JavaBeanInfo r0 = new com.alibaba.fastjson.util.JavaBeanInfo
            r10 = 0
            r1 = r0
            r2 = r62
            r11 = r3
            r3 = r8
            r4 = r21
            r64 = r5
            r5 = r10
            r10 = r6
            r6 = r17
            r13 = r7
            r7 = r22
            r23 = r8
            r8 = r15
            r24 = r9
            r9 = r11
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9)
            return r0
        L_0x009a:
            r64 = r5
            r13 = r7
            r23 = r8
            r24 = r9
            r8 = r3
            r9 = r6
            boolean r0 = r62.isInterface()
            if (r0 != 0) goto L_0x00b6
            int r0 = r62.getModifiers()
            boolean r0 = java.lang.reflect.Modifier.isAbstract(r0)
            if (r0 == 0) goto L_0x00b4
            goto L_0x00b6
        L_0x00b4:
            r0 = 0
            goto L_0x00b7
        L_0x00b6:
            r0 = r4
        L_0x00b7:
            r25 = r0
            if (r21 != 0) goto L_0x00c0
            r3 = r23
            if (r3 == 0) goto L_0x00c4
            goto L_0x00c2
        L_0x00c0:
            r3 = r23
        L_0x00c2:
            if (r25 == 0) goto L_0x05af
        L_0x00c4:
            java.lang.reflect.Type r2 = com.alibaba.fastjson.JSON.getMixInAnnotations(r62)
            boolean r0 = r2 instanceof java.lang.Class
            if (r0 == 0) goto L_0x00e5
            r0 = r2
            java.lang.Class r0 = (java.lang.Class) r0
            java.lang.reflect.Constructor[] r1 = r0.getConstructors()
            java.lang.reflect.Constructor r18 = getCreatorConstructor(r1)
            if (r18 == 0) goto L_0x00e5
            java.lang.Class[] r0 = r18.getParameterTypes()     // Catch:{ NoSuchMethodException -> 0x00e4 }
            java.lang.reflect.Constructor r0 = r14.getConstructor(r0)     // Catch:{ NoSuchMethodException -> 0x00e4 }
            r16 = r0
            goto L_0x00e5
        L_0x00e4:
            r0 = move-exception
        L_0x00e5:
            if (r16 != 0) goto L_0x00ec
            java.lang.reflect.Constructor r0 = getCreatorConstructor(r64)
            goto L_0x00ee
        L_0x00ec:
            r0 = r16
        L_0x00ee:
            if (r0 == 0) goto L_0x0221
            if (r25 != 0) goto L_0x0221
            com.alibaba.fastjson.util.TypeUtils.setAccessible(r0)
            java.lang.Class[] r1 = r0.getParameterTypes()
            r16 = 0
            int r4 = r1.length
            if (r4 <= 0) goto L_0x0203
            java.lang.annotation.Annotation[][] r4 = com.alibaba.fastjson.util.TypeUtils.getParameterAnnotations((java.lang.reflect.Constructor) r0)
            r23 = 0
            r61 = r23
            r23 = r15
            r15 = r61
        L_0x010a:
            int r5 = r1.length
            if (r15 >= r5) goto L_0x01f4
            int r5 = r4.length
            if (r15 >= r5) goto L_0x01f4
            r5 = r4[r15]
            r27 = 0
            int r6 = r5.length
            r7 = 0
        L_0x0116:
            if (r7 >= r6) goto L_0x012e
            r30 = r2
            r2 = r5[r7]
            r31 = r3
            boolean r3 = r2 instanceof com.alibaba.fastjson.annotation.JSONField
            if (r3 == 0) goto L_0x0127
            r27 = r2
            com.alibaba.fastjson.annotation.JSONField r27 = (com.alibaba.fastjson.annotation.JSONField) r27
            goto L_0x0132
        L_0x0127:
            int r7 = r7 + 1
            r2 = r30
            r3 = r31
            goto L_0x0116
        L_0x012e:
            r30 = r2
            r31 = r3
        L_0x0132:
            r32 = r1[r15]
            java.lang.reflect.Type[] r2 = r0.getGenericParameterTypes()
            r33 = r2[r15]
            r2 = 0
            r3 = 0
            r6 = 0
            r7 = 0
            r34 = 0
            if (r27 == 0) goto L_0x016b
            r35 = r1
            java.lang.String r1 = r27.name()
            java.lang.reflect.Field r3 = com.alibaba.fastjson.util.TypeUtils.getField(r14, r1, r13)
            int r6 = r27.ordinal()
            com.alibaba.fastjson.serializer.SerializerFeature[] r1 = r27.serialzeFeatures()
            int r7 = com.alibaba.fastjson.serializer.SerializerFeature.of(r1)
            com.alibaba.fastjson.parser.Feature[] r1 = r27.parseFeatures()
            int r34 = com.alibaba.fastjson.parser.Feature.of(r1)
            java.lang.String r2 = r27.name()
            r36 = r7
            r37 = r34
            r34 = r6
            goto L_0x0173
        L_0x016b:
            r35 = r1
            r36 = r7
            r37 = r34
            r34 = r6
        L_0x0173:
            if (r2 == 0) goto L_0x0181
            int r1 = r2.length()
            if (r1 != 0) goto L_0x017c
            goto L_0x0181
        L_0x017c:
            r1 = r16
            r16 = r2
            goto L_0x018f
        L_0x0181:
            if (r16 != 0) goto L_0x0189
            java.lang.String[] r1 = com.alibaba.fastjson.util.ASMUtils.lookupParameterNames(r0)
            r16 = r1
        L_0x0189:
            r2 = r16[r15]
            r1 = r16
            r16 = r2
        L_0x018f:
            if (r3 != 0) goto L_0x01b1
            if (r1 != 0) goto L_0x019e
            if (r20 == 0) goto L_0x019a
            java.lang.String[] r1 = com.alibaba.fastjson.util.TypeUtils.getKoltinConstructorParameters(r62)
            goto L_0x019e
        L_0x019a:
            java.lang.String[] r1 = com.alibaba.fastjson.util.ASMUtils.lookupParameterNames(r0)
        L_0x019e:
            int r2 = r1.length
            if (r2 <= r15) goto L_0x01ac
            r2 = r1[r15]
            java.lang.reflect.Field r3 = com.alibaba.fastjson.util.TypeUtils.getField(r14, r2, r13)
            r39 = r1
            r38 = r3
            goto L_0x01b5
        L_0x01ac:
            r39 = r1
            r38 = r3
            goto L_0x01b5
        L_0x01b1:
            r39 = r1
            r38 = r3
        L_0x01b5:
            com.alibaba.fastjson.util.FieldInfo r40 = new com.alibaba.fastjson.util.FieldInfo
            r1 = r40
            r2 = r16
            r7 = r31
            r3 = r62
            r18 = r4
            r6 = 1
            r4 = r32
            r26 = r5
            r31 = r10
            r10 = 3
            r5 = r33
            r10 = 2
            r6 = r38
            r28 = r7
            r7 = r34
            r29 = r11
            r11 = r8
            r8 = r36
            r10 = r9
            r9 = r37
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9)
            add(r11, r1)
            int r15 = r15 + 1
            r9 = r10
            r8 = r11
            r4 = r18
            r3 = r28
            r11 = r29
            r2 = r30
            r10 = r31
            r1 = r35
            r16 = r39
            goto L_0x010a
        L_0x01f4:
            r35 = r1
            r30 = r2
            r28 = r3
            r18 = r4
            r31 = r10
            r29 = r11
            r11 = r8
            r10 = r9
            goto L_0x0211
        L_0x0203:
            r35 = r1
            r30 = r2
            r28 = r3
            r31 = r10
            r29 = r11
            r23 = r15
            r11 = r8
            r10 = r9
        L_0x0211:
            r43 = r64
            r16 = r0
            r45 = r13
            r12 = r14
            r13 = r29
            r27 = 2
            r29 = 0
            r14 = r10
            goto L_0x05c1
        L_0x0221:
            r30 = r2
            r28 = r3
            r31 = r10
            r29 = r11
            r23 = r15
            r11 = r8
            r10 = r9
            java.lang.reflect.Method r1 = getFactoryMethod(r14, r10, r12)
            r15 = r1
            if (r1 == 0) goto L_0x0320
            com.alibaba.fastjson.util.TypeUtils.setAccessible(r15)
            r1 = 0
            java.lang.Class[] r9 = r15.getParameterTypes()
            int r2 = r9.length
            if (r2 <= 0) goto L_0x030a
            java.lang.annotation.Annotation[][] r26 = com.alibaba.fastjson.util.TypeUtils.getParameterAnnotations((java.lang.reflect.Method) r15)
            r2 = 0
            r8 = r2
        L_0x0245:
            int r2 = r9.length
            if (r8 >= r2) goto L_0x02eb
            r7 = r26[r8]
            r2 = 0
            int r3 = r7.length
            r4 = 0
        L_0x024d:
            if (r4 >= r3) goto L_0x025e
            r5 = r7[r4]
            boolean r6 = r5 instanceof com.alibaba.fastjson.annotation.JSONField
            if (r6 == 0) goto L_0x025b
            r2 = r5
            com.alibaba.fastjson.annotation.JSONField r2 = (com.alibaba.fastjson.annotation.JSONField) r2
            r16 = r2
            goto L_0x0260
        L_0x025b:
            int r4 = r4 + 1
            goto L_0x024d
        L_0x025e:
            r16 = r2
        L_0x0260:
            if (r16 != 0) goto L_0x0273
            if (r12 == 0) goto L_0x026b
            boolean r2 = com.alibaba.fastjson.util.TypeUtils.isJacksonCreator(r15)
            if (r2 == 0) goto L_0x026b
            goto L_0x0273
        L_0x026b:
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException
            java.lang.String r3 = "illegal json creator"
            r2.<init>(r3)
            throw r2
        L_0x0273:
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            if (r16 == 0) goto L_0x0298
            java.lang.String r2 = r16.name()
            int r3 = r16.ordinal()
            com.alibaba.fastjson.serializer.SerializerFeature[] r6 = r16.serialzeFeatures()
            int r4 = com.alibaba.fastjson.serializer.SerializerFeature.of(r6)
            com.alibaba.fastjson.parser.Feature[] r6 = r16.parseFeatures()
            int r5 = com.alibaba.fastjson.parser.Feature.of(r6)
            r17 = r3
            r18 = r4
            r27 = r5
            goto L_0x029e
        L_0x0298:
            r17 = r3
            r18 = r4
            r27 = r5
        L_0x029e:
            if (r2 == 0) goto L_0x02ab
            int r3 = r2.length()
            if (r3 != 0) goto L_0x02a7
            goto L_0x02ab
        L_0x02a7:
            r29 = r1
            r6 = r2
            goto L_0x02b6
        L_0x02ab:
            if (r1 != 0) goto L_0x02b1
            java.lang.String[] r1 = com.alibaba.fastjson.util.ASMUtils.lookupParameterNames(r15)
        L_0x02b1:
            r2 = r1[r8]
            r29 = r1
            r6 = r2
        L_0x02b6:
            r31 = r9[r8]
            java.lang.reflect.Type[] r1 = r15.getGenericParameterTypes()
            r32 = r1[r8]
            java.lang.reflect.Field r33 = com.alibaba.fastjson.util.TypeUtils.getField(r14, r6, r13)
            com.alibaba.fastjson.util.FieldInfo r34 = new com.alibaba.fastjson.util.FieldInfo
            r1 = r34
            r2 = r6
            r3 = r62
            r4 = r31
            r5 = r32
            r35 = r6
            r6 = r33
            r36 = r7
            r7 = r17
            r37 = r8
            r8 = r18
            r38 = r9
            r9 = r27
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9)
            add(r11, r1)
            int r8 = r37 + 1
            r1 = r29
            r9 = r38
            goto L_0x0245
        L_0x02eb:
            r37 = r8
            r38 = r9
            com.alibaba.fastjson.util.JavaBeanInfo r2 = new com.alibaba.fastjson.util.JavaBeanInfo
            r3 = 0
            r4 = 0
            r16 = 0
            r9 = r10
            r10 = r2
            r8 = r11
            r11 = r62
            r12 = r28
            r7 = r63
            r6 = r13
            r13 = r3
            r5 = r14
            r14 = r4
            r17 = r23
            r18 = r8
            r10.<init>(r11, r12, r13, r14, r15, r16, r17, r18)
            return r2
        L_0x030a:
            r7 = r63
            r38 = r9
            r9 = r10
            r8 = r11
            r6 = r13
            r5 = r14
            r43 = r64
            r12 = r5
            r45 = r6
            r14 = r9
            r13 = r29
            r27 = 2
            r29 = 0
            goto L_0x05aa
        L_0x0320:
            r7 = r63
            r9 = r10
            r8 = r11
            r6 = r13
            r5 = r14
            if (r25 != 0) goto L_0x059d
            java.lang.String r10 = r62.getName()
            r1 = 0
            if (r20 == 0) goto L_0x034a
            r14 = r64
            int r2 = r14.length
            if (r2 <= 0) goto L_0x034c
            java.lang.String[] r1 = com.alibaba.fastjson.util.TypeUtils.getKoltinConstructorParameters(r62)
            java.lang.reflect.Constructor r0 = com.alibaba.fastjson.util.TypeUtils.getKotlinConstructor(r14, r1)
            com.alibaba.fastjson.util.TypeUtils.setAccessible(r0)
            r16 = r0
            r0 = r1
            r13 = r29
            r27 = 2
            r29 = 0
            goto L_0x045f
        L_0x034a:
            r14 = r64
        L_0x034c:
            int r2 = r14.length
            r3 = 0
        L_0x034e:
            if (r3 >= r2) goto L_0x0454
            r4 = r14[r3]
            java.lang.Class[] r11 = r4.getParameterTypes()
            java.lang.String r12 = "org.springframework.security.web.authentication.WebAuthenticationDetails"
            boolean r12 = r10.equals(r12)
            if (r12 == 0) goto L_0x0391
            int r12 = r11.length
            r13 = 2
            if (r12 != r13) goto L_0x0384
            r13 = 0
            r12 = r11[r13]
            r13 = r29
            if (r12 != r13) goto L_0x0381
            r16 = r2
            r12 = 1
            r2 = r11[r12]
            if (r2 != r13) goto L_0x0389
            r0 = r4
            r0.setAccessible(r12)
            java.lang.String[] r1 = com.alibaba.fastjson.util.ASMUtils.lookupParameterNames(r4)
            r16 = r0
            r0 = r1
            r27 = 2
            r29 = 0
            goto L_0x045f
        L_0x0381:
            r16 = r2
            goto L_0x0388
        L_0x0384:
            r16 = r2
            r13 = r29
        L_0x0388:
            r12 = 1
        L_0x0389:
            r64 = r0
            r27 = 2
            r29 = 0
            goto L_0x0448
        L_0x0391:
            r16 = r2
            r13 = r29
            r12 = 1
            java.lang.String r2 = "org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken"
            boolean r2 = r10.equals(r2)
            if (r2 == 0) goto L_0x03e0
            int r2 = r11.length
            r12 = 3
            if (r2 != r12) goto L_0x03d6
            r2 = 0
            r12 = r11[r2]
            r2 = r31
            if (r12 != r2) goto L_0x03cf
            r12 = 1
            r7 = r11[r12]
            if (r7 != r2) goto L_0x03cf
            r27 = 2
            r7 = r11[r27]
            java.lang.Class<java.util.Collection> r12 = java.util.Collection.class
            if (r7 != r12) goto L_0x03d1
            r0 = r4
            r3 = 1
            r0.setAccessible(r3)
            java.lang.String r3 = "principal"
            java.lang.String r7 = "credentials"
            java.lang.String r12 = "authorities"
            java.lang.String[] r3 = new java.lang.String[]{r3, r7, r12}
            r1 = r3
            r16 = r0
            r0 = r1
            r31 = r2
            r29 = 0
            goto L_0x045f
        L_0x03cf:
            r27 = 2
        L_0x03d1:
            r64 = r0
            r31 = r2
            goto L_0x03dc
        L_0x03d6:
            r2 = r31
            r27 = 2
            r64 = r0
        L_0x03dc:
            r29 = 0
            goto L_0x0448
        L_0x03e0:
            r2 = r31
            r27 = 2
            java.lang.String r7 = "org.springframework.security.core.authority.SimpleGrantedAuthority"
            boolean r7 = r10.equals(r7)
            if (r7 == 0) goto L_0x040c
            int r7 = r11.length
            r12 = 1
            if (r7 != r12) goto L_0x0405
            r29 = 0
            r7 = r11[r29]
            if (r7 != r13) goto L_0x0407
            r0 = r4
            java.lang.String r3 = "authority"
            java.lang.String[] r3 = new java.lang.String[]{r3}
            r1 = r3
            r16 = r0
            r0 = r1
            r31 = r2
            goto L_0x045f
        L_0x0405:
            r29 = 0
        L_0x0407:
            r64 = r0
            r31 = r2
            goto L_0x0448
        L_0x040c:
            r29 = 0
            int r7 = r4.getModifiers()
            r12 = 1
            r7 = r7 & r12
            if (r7 == 0) goto L_0x0418
            r7 = r12
            goto L_0x041a
        L_0x0418:
            r7 = r29
        L_0x041a:
            if (r7 != 0) goto L_0x0421
            r64 = r0
            r31 = r2
            goto L_0x0448
        L_0x0421:
            java.lang.String[] r12 = com.alibaba.fastjson.util.ASMUtils.lookupParameterNames(r4)
            if (r12 == 0) goto L_0x0444
            r31 = r2
            int r2 = r12.length
            if (r2 != 0) goto L_0x042f
            r64 = r0
            goto L_0x0448
        L_0x042f:
            if (r0 == 0) goto L_0x043a
            if (r1 == 0) goto L_0x043a
            int r2 = r12.length
            r64 = r0
            int r0 = r1.length
            if (r2 > r0) goto L_0x043c
            goto L_0x0448
        L_0x043a:
            r64 = r0
        L_0x043c:
            r0 = r12
            r1 = r4
            r61 = r1
            r1 = r0
            r0 = r61
            goto L_0x044a
        L_0x0444:
            r64 = r0
            r31 = r2
        L_0x0448:
            r0 = r64
        L_0x044a:
            int r3 = r3 + 1
            r7 = r63
            r29 = r13
            r2 = r16
            goto L_0x034e
        L_0x0454:
            r64 = r0
            r13 = r29
            r27 = 2
            r29 = 0
            r16 = r64
            r0 = r1
        L_0x045f:
            r1 = 0
            if (r0 == 0) goto L_0x0468
            java.lang.Class[] r1 = r16.getParameterTypes()
            r11 = r1
            goto L_0x0469
        L_0x0468:
            r11 = r1
        L_0x0469:
            if (r0 == 0) goto L_0x057b
            int r1 = r11.length
            int r2 = r0.length
            if (r1 != r2) goto L_0x057b
            java.lang.annotation.Annotation[][] r12 = com.alibaba.fastjson.util.TypeUtils.getParameterAnnotations((java.lang.reflect.Constructor) r16)
            r1 = 0
            r7 = r1
        L_0x0475:
            int r1 = r11.length
            if (r7 >= r1) goto L_0x0548
            r4 = r12[r7]
            r1 = r0[r7]
            r2 = 0
            int r3 = r4.length
            r64 = r0
            r0 = r29
        L_0x0482:
            if (r0 >= r3) goto L_0x049a
            r17 = r2
            r2 = r4[r0]
            r32 = r3
            boolean r3 = r2 instanceof com.alibaba.fastjson.annotation.JSONField
            if (r3 == 0) goto L_0x0493
            r0 = r2
            com.alibaba.fastjson.annotation.JSONField r0 = (com.alibaba.fastjson.annotation.JSONField) r0
            r2 = r0
            goto L_0x049c
        L_0x0493:
            int r0 = r0 + 1
            r2 = r17
            r3 = r32
            goto L_0x0482
        L_0x049a:
            r17 = r2
        L_0x049c:
            r0 = r11[r7]
            java.lang.reflect.Type[] r3 = r16.getGenericParameterTypes()
            r17 = r3[r7]
            java.lang.reflect.Field r3 = com.alibaba.fastjson.util.TypeUtils.getField(r5, r1, r6)
            if (r3 == 0) goto L_0x04bc
            if (r2 != 0) goto L_0x04b9
            r32 = r2
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r2 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r2 = com.alibaba.fastjson.util.TypeUtils.getAnnotation((java.lang.reflect.Field) r3, r2)
            com.alibaba.fastjson.annotation.JSONField r2 = (com.alibaba.fastjson.annotation.JSONField) r2
            r32 = r2
            goto L_0x04be
        L_0x04b9:
            r32 = r2
            goto L_0x04be
        L_0x04bc:
            r32 = r2
        L_0x04be:
            if (r32 != 0) goto L_0x04e8
            r2 = 0
            r33 = 0
            r34 = r2
            java.lang.String r2 = "org.springframework.security.core.userdetails.User"
            boolean r2 = r2.equals(r10)
            if (r2 == 0) goto L_0x04e0
            java.lang.String r2 = "password"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x04e0
            com.alibaba.fastjson.parser.Feature r2 = com.alibaba.fastjson.parser.Feature.InitStringFieldAsEmpty
            int r2 = r2.mask
            r35 = r2
            r36 = r33
            r33 = r1
            goto L_0x050d
        L_0x04e0:
            r2 = 0
            r35 = r2
            r36 = r33
            r33 = r1
            goto L_0x050d
        L_0x04e8:
            java.lang.String r2 = r32.name()
            int r33 = r2.length()
            if (r33 == 0) goto L_0x04f3
            r1 = r2
        L_0x04f3:
            int r33 = r32.ordinal()
            com.alibaba.fastjson.serializer.SerializerFeature[] r34 = r32.serialzeFeatures()
            int r34 = com.alibaba.fastjson.serializer.SerializerFeature.of(r34)
            com.alibaba.fastjson.parser.Feature[] r35 = r32.parseFeatures()
            int r35 = com.alibaba.fastjson.parser.Feature.of(r35)
            r36 = r34
            r34 = r33
            r33 = r1
        L_0x050d:
            com.alibaba.fastjson.util.FieldInfo r37 = new com.alibaba.fastjson.util.FieldInfo
            r1 = r37
            r2 = r33
            r38 = r3
            r3 = r62
            r39 = r4
            r4 = r0
            r40 = r12
            r12 = r5
            r5 = r17
            r45 = r6
            r6 = r38
            r41 = r7
            r7 = r34
            r42 = r11
            r11 = r8
            r8 = r36
            r43 = r14
            r14 = r9
            r9 = r35
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9)
            add(r11, r1)
            int r7 = r41 + 1
            r0 = r64
            r8 = r11
            r5 = r12
            r9 = r14
            r12 = r40
            r11 = r42
            r14 = r43
            r6 = r45
            goto L_0x0475
        L_0x0548:
            r64 = r0
            r45 = r6
            r41 = r7
            r42 = r11
            r40 = r12
            r43 = r14
            r12 = r5
            r11 = r8
            r14 = r9
            if (r20 != 0) goto L_0x0578
            java.lang.String r0 = r62.getName()
            java.lang.String r1 = "javax.servlet.http.Cookie"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0578
            com.alibaba.fastjson.util.JavaBeanInfo r0 = new com.alibaba.fastjson.util.JavaBeanInfo
            r4 = 0
            r6 = 0
            r7 = 0
            r1 = r0
            r2 = r62
            r3 = r28
            r5 = r16
            r8 = r23
            r9 = r11
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9)
            return r0
        L_0x0578:
            r17 = r15
            goto L_0x05c1
        L_0x057b:
            r64 = r0
            r12 = r5
            r45 = r6
            r42 = r11
            r43 = r14
            r11 = r8
            r14 = r9
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "default constructor not found. "
            r1.append(r2)
            r1.append(r12)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x059d:
            r43 = r64
            r12 = r5
            r45 = r6
            r11 = r8
            r14 = r9
            r13 = r29
            r27 = 2
            r29 = 0
        L_0x05aa:
            r16 = r0
            r17 = r15
            goto L_0x05c1
        L_0x05af:
            r43 = r64
            r28 = r3
            r31 = r10
            r45 = r13
            r12 = r14
            r23 = r15
            r27 = 2
            r29 = 0
            r14 = r9
            r13 = r11
            r11 = r8
        L_0x05c1:
            if (r21 == 0) goto L_0x05c6
            com.alibaba.fastjson.util.TypeUtils.setAccessible(r21)
        L_0x05c6:
            java.lang.String r15 = "set"
            r10 = r28
            if (r10 == 0) goto L_0x07ee
            r0 = 0
            java.lang.Class<com.alibaba.fastjson.annotation.JSONPOJOBuilder> r1 = com.alibaba.fastjson.annotation.JSONPOJOBuilder.class
            java.lang.annotation.Annotation r1 = com.alibaba.fastjson.util.TypeUtils.getAnnotation((java.lang.Class<?>) r10, r1)
            r28 = r1
            com.alibaba.fastjson.annotation.JSONPOJOBuilder r28 = (com.alibaba.fastjson.annotation.JSONPOJOBuilder) r28
            if (r28 == 0) goto L_0x05de
            java.lang.String r0 = r28.withPrefix()
        L_0x05de:
            if (r0 != 0) goto L_0x05e5
            java.lang.String r0 = "with"
            r9 = r0
            goto L_0x05e6
        L_0x05e5:
            r9 = r0
        L_0x05e6:
            java.lang.reflect.Method[] r0 = r10.getMethods()
            int r8 = r0.length
            r7 = r29
        L_0x05ed:
            if (r7 >= r8) goto L_0x0797
            r6 = r0[r7]
            int r1 = r6.getModifiers()
            boolean r1 = java.lang.reflect.Modifier.isStatic(r1)
            if (r1 == 0) goto L_0x060c
            r39 = r7
            r40 = r8
            r36 = r9
            r47 = r10
            r49 = r13
            r18 = r14
            r48 = r31
            r14 = r11
            goto L_0x0780
        L_0x060c:
            java.lang.Class r1 = r6.getReturnType()
            boolean r1 = r1.equals(r10)
            if (r1 != 0) goto L_0x0627
            r39 = r7
            r40 = r8
            r36 = r9
            r47 = r10
            r49 = r13
            r18 = r14
            r48 = r31
            r14 = r11
            goto L_0x0780
        L_0x0627:
            r1 = 0
            r2 = 0
            r3 = 0
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r4 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r4 = com.alibaba.fastjson.util.TypeUtils.getAnnotation((java.lang.reflect.Method) r6, r4)
            com.alibaba.fastjson.annotation.JSONField r4 = (com.alibaba.fastjson.annotation.JSONField) r4
            if (r4 != 0) goto L_0x063b
            com.alibaba.fastjson.annotation.JSONField r4 = com.alibaba.fastjson.util.TypeUtils.getSuperMethodAnnotation(r12, r6)
            r30 = r4
            goto L_0x063d
        L_0x063b:
            r30 = r4
        L_0x063d:
            if (r30 == 0) goto L_0x06c7
            boolean r4 = r30.deserialize()
            if (r4 != 0) goto L_0x0656
            r39 = r7
            r40 = r8
            r36 = r9
            r47 = r10
            r49 = r13
            r18 = r14
            r48 = r31
            r14 = r11
            goto L_0x0780
        L_0x0656:
            int r32 = r30.ordinal()
            com.alibaba.fastjson.serializer.SerializerFeature[] r1 = r30.serialzeFeatures()
            int r33 = com.alibaba.fastjson.serializer.SerializerFeature.of(r1)
            com.alibaba.fastjson.parser.Feature[] r1 = r30.parseFeatures()
            int r34 = com.alibaba.fastjson.parser.Feature.of(r1)
            java.lang.String r1 = r30.name()
            int r1 = r1.length()
            if (r1 == 0) goto L_0x06b5
            java.lang.String r35 = r30.name()
            com.alibaba.fastjson.util.FieldInfo r5 = new com.alibaba.fastjson.util.FieldInfo
            r4 = 0
            r36 = 0
            r37 = 0
            r1 = r5
            r2 = r35
            r3 = r6
            r46 = r5
            r5 = r62
            r38 = r6
            r6 = r63
            r39 = r7
            r7 = r32
            r40 = r8
            r8 = r33
            r64 = r9
            r9 = r34
            r47 = r10
            r48 = r31
            r10 = r30
            r49 = r13
            r13 = r11
            r11 = r36
            r12 = r37
            r18 = r14
            r14 = r13
            r13 = r19
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            r1 = r46
            add(r14, r1)
            r36 = r64
            goto L_0x0780
        L_0x06b5:
            r38 = r6
            r39 = r7
            r40 = r8
            r64 = r9
            r47 = r10
            r49 = r13
            r18 = r14
            r48 = r31
            r14 = r11
            goto L_0x06de
        L_0x06c7:
            r38 = r6
            r39 = r7
            r40 = r8
            r64 = r9
            r47 = r10
            r49 = r13
            r18 = r14
            r48 = r31
            r14 = r11
            r32 = r1
            r33 = r2
            r34 = r3
        L_0x06de:
            java.lang.String r13 = r38.getName()
            boolean r1 = r13.startsWith(r15)
            if (r1 == 0) goto L_0x06fc
            int r1 = r13.length()
            r12 = 3
            if (r1 <= r12) goto L_0x06fd
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = r13.substring(r12)
            r1.<init>(r2)
            r11 = r64
            r10 = r1
            goto L_0x0733
        L_0x06fc:
            r12 = 3
        L_0x06fd:
            int r1 = r64.length()
            if (r1 != 0) goto L_0x070c
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>(r13)
            r11 = r64
            r10 = r1
            goto L_0x0733
        L_0x070c:
            r11 = r64
            boolean r1 = r13.startsWith(r11)
            if (r1 != 0) goto L_0x0718
            r36 = r11
            goto L_0x0780
        L_0x0718:
            int r1 = r13.length()
            int r2 = r11.length()
            if (r1 > r2) goto L_0x0725
            r36 = r11
            goto L_0x0780
        L_0x0725:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            int r2 = r11.length()
            java.lang.String r2 = r13.substring(r2)
            r1.<init>(r2)
            r10 = r1
        L_0x0733:
            r9 = 0
            char r26 = r10.charAt(r9)
            int r1 = r11.length()
            if (r1 == 0) goto L_0x0747
            boolean r1 = java.lang.Character.isUpperCase(r26)
            if (r1 != 0) goto L_0x0747
            r36 = r11
            goto L_0x0780
        L_0x0747:
            char r1 = java.lang.Character.toLowerCase(r26)
            r10.setCharAt(r9, r1)
            java.lang.String r27 = r10.toString()
            com.alibaba.fastjson.util.FieldInfo r8 = new com.alibaba.fastjson.util.FieldInfo
            r4 = 0
            r29 = 0
            r31 = 0
            r1 = r8
            r2 = r27
            r3 = r38
            r5 = r62
            r6 = r63
            r7 = r32
            r50 = r8
            r8 = r33
            r9 = r34
            r35 = r10
            r10 = r30
            r36 = r11
            r11 = r29
            r12 = r31
            r29 = r13
            r13 = r19
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            r1 = r50
            add(r14, r1)
        L_0x0780:
            int r7 = r39 + 1
            r12 = r62
            r11 = r14
            r14 = r18
            r9 = r36
            r8 = r40
            r10 = r47
            r31 = r48
            r13 = r49
            r27 = 2
            r29 = 0
            goto L_0x05ed
        L_0x0797:
            r36 = r9
            r47 = r10
            r49 = r13
            r18 = r14
            r48 = r31
            r14 = r11
            java.lang.Class<com.alibaba.fastjson.annotation.JSONPOJOBuilder> r0 = com.alibaba.fastjson.annotation.JSONPOJOBuilder.class
            r13 = r47
            java.lang.annotation.Annotation r0 = com.alibaba.fastjson.util.TypeUtils.getAnnotation((java.lang.Class<?>) r13, r0)
            r1 = r0
            com.alibaba.fastjson.annotation.JSONPOJOBuilder r1 = (com.alibaba.fastjson.annotation.JSONPOJOBuilder) r1
            r0 = 0
            if (r1 == 0) goto L_0x07b4
            java.lang.String r0 = r1.buildMethod()
        L_0x07b4:
            if (r0 == 0) goto L_0x07bf
            int r2 = r0.length()
            if (r2 != 0) goto L_0x07bd
            goto L_0x07bf
        L_0x07bd:
            r2 = r0
            goto L_0x07c2
        L_0x07bf:
            java.lang.String r0 = "build"
            r2 = r0
        L_0x07c2:
            r12 = 0
            java.lang.Class[] r0 = new java.lang.Class[r12]     // Catch:{ NoSuchMethodException -> 0x07ce, SecurityException -> 0x07cc }
            java.lang.reflect.Method r0 = r13.getMethod(r2, r0)     // Catch:{ NoSuchMethodException -> 0x07ce, SecurityException -> 0x07cc }
            r22 = r0
        L_0x07cb:
            goto L_0x07d0
        L_0x07cc:
            r0 = move-exception
            goto L_0x07d0
        L_0x07ce:
            r0 = move-exception
            goto L_0x07cb
        L_0x07d0:
            if (r22 != 0) goto L_0x07e0
            java.lang.String r0 = "create"
            java.lang.Class[] r3 = new java.lang.Class[r12]     // Catch:{ NoSuchMethodException -> 0x07df, SecurityException -> 0x07dd }
            java.lang.reflect.Method r0 = r13.getMethod(r0, r3)     // Catch:{ NoSuchMethodException -> 0x07df, SecurityException -> 0x07dd }
            r22 = r0
            goto L_0x07e0
        L_0x07dd:
            r0 = move-exception
            goto L_0x07e0
        L_0x07df:
            r0 = move-exception
        L_0x07e0:
            if (r22 == 0) goto L_0x07e6
            com.alibaba.fastjson.util.TypeUtils.setAccessible(r22)
            goto L_0x07f8
        L_0x07e6:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.String r3 = "buildMethod not found."
            r0.<init>(r3)
            throw r0
        L_0x07ee:
            r49 = r13
            r18 = r14
            r12 = r29
            r48 = r31
            r13 = r10
            r14 = r11
        L_0x07f8:
            r11 = r18
            int r0 = r11.length
            r10 = r12
        L_0x07fc:
            java.lang.String r9 = "get"
            r8 = 4
            if (r10 >= r0) goto L_0x0cb3
            r7 = r11[r10]
            r18 = 0
            r26 = 0
            r27 = 0
            java.lang.String r6 = r7.getName()
            int r1 = r7.getModifiers()
            boolean r1 = java.lang.reflect.Modifier.isStatic(r1)
            if (r1 == 0) goto L_0x0831
            r40 = r0
            r35 = r10
            r42 = r12
            r30 = r13
            r64 = r15
            r57 = r24
            r56 = r45
            r33 = r48
            r29 = r49
            r32 = 2
            r41 = 1
            r45 = r11
            goto L_0x0c9d
        L_0x0831:
            java.lang.Class r5 = r7.getReturnType()
            java.lang.Class r1 = java.lang.Void.TYPE
            boolean r1 = r5.equals(r1)
            if (r1 != 0) goto L_0x0861
            java.lang.Class r1 = r7.getDeclaringClass()
            boolean r1 = r5.equals(r1)
            if (r1 != 0) goto L_0x0861
            r40 = r0
            r35 = r10
            r42 = r12
            r30 = r13
            r64 = r15
            r57 = r24
            r56 = r45
            r33 = r48
            r29 = r49
            r32 = 2
            r41 = 1
            r45 = r11
            goto L_0x0c9d
        L_0x0861:
            java.lang.Class r1 = r7.getDeclaringClass()
            r4 = r48
            if (r1 != r4) goto L_0x0883
            r40 = r0
            r33 = r4
            r35 = r10
            r42 = r12
            r30 = r13
            r64 = r15
            r57 = r24
            r56 = r45
            r29 = r49
            r32 = 2
            r41 = 1
            r45 = r11
            goto L_0x0c9d
        L_0x0883:
            java.lang.Class[] r3 = r7.getParameterTypes()
            int r1 = r3.length
            if (r1 == 0) goto L_0x0c7d
            int r1 = r3.length
            r2 = 2
            if (r1 <= r2) goto L_0x08a8
            r40 = r0
            r32 = r2
            r33 = r4
            r35 = r10
            r42 = r12
            r30 = r13
            r64 = r15
            r57 = r24
            r56 = r45
            r29 = r49
            r41 = 1
            r45 = r11
            goto L_0x0c9d
        L_0x08a8:
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r1 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r1 = com.alibaba.fastjson.util.TypeUtils.getAnnotation((java.lang.reflect.Method) r7, r1)
            r28 = r1
            com.alibaba.fastjson.annotation.JSONField r28 = (com.alibaba.fastjson.annotation.JSONField) r28
            if (r28 == 0) goto L_0x0939
            int r1 = r3.length
            if (r1 != r2) goto L_0x0939
            r1 = r3[r12]
            r12 = r49
            if (r1 != r12) goto L_0x0924
            r1 = 1
            r2 = r3[r1]
            if (r2 != r4) goto L_0x090f
            com.alibaba.fastjson.util.FieldInfo r9 = new com.alibaba.fastjson.util.FieldInfo
            r8 = 0
            r30 = 0
            r31 = 0
            java.lang.String r2 = ""
            r1 = r9
            r32 = 2
            r51 = r3
            r3 = r7
            r33 = r4
            r4 = r8
            r34 = r5
            r5 = r62
            r8 = r6
            r6 = r63
            r52 = r7
            r7 = r18
            r64 = r8
            r8 = r26
            r53 = r9
            r9 = r27
            r35 = r10
            r10 = r28
            r54 = r11
            r11 = r30
            r29 = r12
            r12 = r31
            r30 = r13
            r13 = r19
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            r1 = r53
            add(r14, r1)
            r40 = r0
            r64 = r15
            r57 = r24
            r56 = r45
            r45 = r54
            r41 = 1
            r42 = 0
            goto L_0x0c9d
        L_0x090f:
            r51 = r3
            r33 = r4
            r34 = r5
            r64 = r6
            r52 = r7
            r35 = r10
            r54 = r11
            r29 = r12
            r30 = r13
            r32 = 2
            goto L_0x094d
        L_0x0924:
            r32 = r2
            r51 = r3
            r33 = r4
            r34 = r5
            r64 = r6
            r52 = r7
            r35 = r10
            r54 = r11
            r29 = r12
            r30 = r13
            goto L_0x094d
        L_0x0939:
            r32 = r2
            r51 = r3
            r33 = r4
            r34 = r5
            r64 = r6
            r52 = r7
            r35 = r10
            r54 = r11
            r30 = r13
            r29 = r49
        L_0x094d:
            r13 = r51
            int r1 = r13.length
            r12 = 1
            if (r1 == r12) goto L_0x0963
            r40 = r0
            r41 = r12
            r64 = r15
            r57 = r24
            r56 = r45
            r45 = r54
            r42 = 0
            goto L_0x0c9d
        L_0x0963:
            if (r28 != 0) goto L_0x096e
            r11 = r62
            r10 = r52
            com.alibaba.fastjson.annotation.JSONField r28 = com.alibaba.fastjson.util.TypeUtils.getSuperMethodAnnotation(r11, r10)
            goto L_0x0972
        L_0x096e:
            r11 = r62
            r10 = r52
        L_0x0972:
            if (r28 != 0) goto L_0x098a
            int r1 = r64.length()
            if (r1 >= r8) goto L_0x098a
            r40 = r0
            r41 = r12
            r64 = r15
            r57 = r24
            r56 = r45
            r45 = r54
            r42 = 0
            goto L_0x0c9d
        L_0x098a:
            if (r28 == 0) goto L_0x0a04
            boolean r1 = r28.deserialize()
            if (r1 != 0) goto L_0x09a2
            r40 = r0
            r41 = r12
            r64 = r15
            r57 = r24
            r56 = r45
            r45 = r54
            r42 = 0
            goto L_0x0c9d
        L_0x09a2:
            int r18 = r28.ordinal()
            com.alibaba.fastjson.serializer.SerializerFeature[] r1 = r28.serialzeFeatures()
            int r26 = com.alibaba.fastjson.serializer.SerializerFeature.of(r1)
            com.alibaba.fastjson.parser.Feature[] r1 = r28.parseFeatures()
            int r27 = com.alibaba.fastjson.parser.Feature.of(r1)
            java.lang.String r1 = r28.name()
            int r1 = r1.length()
            if (r1 == 0) goto L_0x09ff
            java.lang.String r31 = r28.name()
            com.alibaba.fastjson.util.FieldInfo r9 = new com.alibaba.fastjson.util.FieldInfo
            r4 = 0
            r36 = 0
            r37 = 0
            r1 = r9
            r2 = r31
            r3 = r10
            r5 = r62
            r6 = r63
            r7 = r18
            r8 = r26
            r55 = r9
            r9 = r27
            r38 = r10
            r10 = r28
            r11 = r36
            r12 = r37
            r36 = r13
            r13 = r19
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            r1 = r55
            add(r14, r1)
            r40 = r0
            r64 = r15
            r57 = r24
            r56 = r45
            r45 = r54
            r41 = 1
            r42 = 0
            goto L_0x0c9d
        L_0x09ff:
            r38 = r10
            r36 = r13
            goto L_0x0a08
        L_0x0a04:
            r38 = r10
            r36 = r13
        L_0x0a08:
            if (r28 != 0) goto L_0x0a23
            r13 = r64
            boolean r1 = r13.startsWith(r15)
            if (r1 == 0) goto L_0x0a13
            goto L_0x0a25
        L_0x0a13:
            r40 = r0
            r64 = r15
            r57 = r24
            r56 = r45
            r45 = r54
            r41 = 1
            r42 = 0
            goto L_0x0c9d
        L_0x0a23:
            r13 = r64
        L_0x0a25:
            if (r30 == 0) goto L_0x0a37
            r40 = r0
            r64 = r15
            r57 = r24
            r56 = r45
            r45 = r54
            r41 = 1
            r42 = 0
            goto L_0x0c9d
        L_0x0a37:
            r12 = 3
            char r11 = r13.charAt(r12)
            r1 = 0
            r2 = 0
            if (r20 == 0) goto L_0x0a68
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r2 = r3
            r3 = 0
        L_0x0a47:
            r10 = r54
            int r4 = r10.length
            if (r3 >= r4) goto L_0x0a66
            r4 = r10[r3]
            java.lang.String r4 = r4.getName()
            boolean r4 = r4.startsWith(r9)
            if (r4 == 0) goto L_0x0a61
            r4 = r10[r3]
            java.lang.String r4 = r4.getName()
            r2.add(r4)
        L_0x0a61:
            int r3 = r3 + 1
            r54 = r10
            goto L_0x0a47
        L_0x0a66:
            r9 = r2
            goto L_0x0a6b
        L_0x0a68:
            r10 = r54
            r9 = r2
        L_0x0a6b:
            boolean r2 = java.lang.Character.isUpperCase(r11)
            java.lang.String r3 = "is"
            java.lang.String r4 = "g"
            if (r2 != 0) goto L_0x0b21
            r2 = 512(0x200, float:7.175E-43)
            if (r11 <= r2) goto L_0x0a80
            r7 = 1
            r6 = r62
            r5 = r45
            goto L_0x0b26
        L_0x0a80:
            r2 = 95
            if (r11 != r2) goto L_0x0adf
            if (r20 == 0) goto L_0x0ac2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r4)
            r7 = 1
            java.lang.String r4 = r13.substring(r7)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            boolean r4 = r9.contains(r2)
            if (r4 == 0) goto L_0x0aa5
            java.lang.String r4 = r13.substring(r12)
            goto L_0x0ab8
        L_0x0aa5:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r3)
            java.lang.String r5 = r13.substring(r12)
            r4.append(r5)
            java.lang.String r4 = r4.toString()
        L_0x0ab8:
            r6 = r62
            r5 = r45
            java.lang.reflect.Field r1 = com.alibaba.fastjson.util.TypeUtils.getField(r6, r4, r5)
            goto L_0x0b51
        L_0x0ac2:
            r7 = 1
            r6 = r62
            r5 = r45
            java.lang.String r4 = r13.substring(r8)
            java.lang.reflect.Field r1 = com.alibaba.fastjson.util.TypeUtils.getField(r6, r4, r5)
            if (r1 != 0) goto L_0x0b51
            r2 = r4
            java.lang.String r4 = r13.substring(r12)
            java.lang.reflect.Field r1 = com.alibaba.fastjson.util.TypeUtils.getField(r6, r4, r5)
            if (r1 != 0) goto L_0x0add
            r4 = r2
        L_0x0add:
            goto L_0x0b51
        L_0x0adf:
            r7 = 1
            r6 = r62
            r5 = r45
            r2 = 102(0x66, float:1.43E-43)
            if (r11 != r2) goto L_0x0aed
            java.lang.String r4 = r13.substring(r12)
            goto L_0x0b51
        L_0x0aed:
            int r2 = r13.length()
            r4 = 5
            if (r2 < r4) goto L_0x0b07
            char r2 = r13.charAt(r8)
            boolean r2 = java.lang.Character.isUpperCase(r2)
            if (r2 == 0) goto L_0x0b07
            java.lang.String r2 = r13.substring(r12)
            java.lang.String r4 = com.alibaba.fastjson.util.TypeUtils.decapitalize(r2)
            goto L_0x0b51
        L_0x0b07:
            java.lang.String r4 = r13.substring(r12)
            java.lang.reflect.Field r1 = com.alibaba.fastjson.util.TypeUtils.getField(r6, r4, r5)
            if (r1 != 0) goto L_0x0b51
            r40 = r0
            r56 = r5
            r41 = r7
            r45 = r10
            r64 = r15
            r57 = r24
            r42 = 0
            goto L_0x0c9d
        L_0x0b21:
            r7 = 1
            r6 = r62
            r5 = r45
        L_0x0b26:
            if (r20 == 0) goto L_0x0b40
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r4)
            java.lang.String r4 = r13.substring(r7)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            java.lang.String r4 = com.alibaba.fastjson.util.TypeUtils.getPropertyNameByMethodName(r2)
            goto L_0x0b51
        L_0x0b40:
            boolean r2 = com.alibaba.fastjson.util.TypeUtils.compatibleWithJavaBean
            if (r2 == 0) goto L_0x0b4d
            java.lang.String r2 = r13.substring(r12)
            java.lang.String r4 = com.alibaba.fastjson.util.TypeUtils.decapitalize(r2)
            goto L_0x0b51
        L_0x0b4d:
            java.lang.String r4 = com.alibaba.fastjson.util.TypeUtils.getPropertyNameByMethodName(r13)
        L_0x0b51:
            if (r1 != 0) goto L_0x0b57
            java.lang.reflect.Field r1 = com.alibaba.fastjson.util.TypeUtils.getField(r6, r4, r5)
        L_0x0b57:
            if (r1 != 0) goto L_0x0b84
            r8 = 0
            r2 = r36[r8]
            java.lang.Class r12 = java.lang.Boolean.TYPE
            if (r2 != r12) goto L_0x0b85
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r3)
            char r3 = r4.charAt(r8)
            char r3 = java.lang.Character.toUpperCase(r3)
            r2.append(r3)
            java.lang.String r3 = r4.substring(r7)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.reflect.Field r1 = com.alibaba.fastjson.util.TypeUtils.getField(r6, r2, r5)
            r12 = r1
            goto L_0x0b86
        L_0x0b84:
            r8 = 0
        L_0x0b85:
            r12 = r1
        L_0x0b86:
            r1 = 0
            if (r12 == 0) goto L_0x0c35
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r2 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r2 = com.alibaba.fastjson.util.TypeUtils.getAnnotation((java.lang.reflect.Field) r12, r2)
            r31 = r2
            com.alibaba.fastjson.annotation.JSONField r31 = (com.alibaba.fastjson.annotation.JSONField) r31
            if (r31 == 0) goto L_0x0c1f
            boolean r1 = r31.deserialize()
            if (r1 != 0) goto L_0x0bab
            r40 = r0
            r56 = r5
            r41 = r7
            r42 = r8
            r45 = r10
            r64 = r15
            r57 = r24
            goto L_0x0c9d
        L_0x0bab:
            int r18 = r31.ordinal()
            com.alibaba.fastjson.serializer.SerializerFeature[] r1 = r31.serialzeFeatures()
            int r26 = com.alibaba.fastjson.serializer.SerializerFeature.of(r1)
            com.alibaba.fastjson.parser.Feature[] r1 = r31.parseFeatures()
            int r27 = com.alibaba.fastjson.parser.Feature.of(r1)
            java.lang.String r1 = r31.name()
            int r1 = r1.length()
            if (r1 == 0) goto L_0x0c09
            java.lang.String r37 = r31.name()
            com.alibaba.fastjson.util.FieldInfo r4 = new com.alibaba.fastjson.util.FieldInfo
            r39 = 0
            r1 = r4
            r2 = r37
            r3 = r38
            r40 = r0
            r0 = r4
            r4 = r12
            r56 = r5
            r5 = r62
            r6 = r63
            r41 = r7
            r7 = r18
            r42 = r8
            r8 = r26
            r44 = r9
            r9 = r27
            r45 = r10
            r10 = r28
            r46 = r11
            r11 = r31
            r47 = r12
            r64 = r15
            r15 = 3
            r12 = r39
            r39 = r13
            r13 = r19
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            add(r14, r0)
            r57 = r24
            goto L_0x0c9d
        L_0x0c09:
            r40 = r0
            r56 = r5
            r41 = r7
            r42 = r8
            r44 = r9
            r45 = r10
            r46 = r11
            r47 = r12
            r39 = r13
            r64 = r15
            r15 = 3
            goto L_0x0c4c
        L_0x0c1f:
            r40 = r0
            r56 = r5
            r41 = r7
            r42 = r8
            r44 = r9
            r45 = r10
            r46 = r11
            r47 = r12
            r39 = r13
            r64 = r15
            r15 = 3
            goto L_0x0c4c
        L_0x0c35:
            r40 = r0
            r56 = r5
            r41 = r7
            r42 = r8
            r44 = r9
            r45 = r10
            r46 = r11
            r47 = r12
            r39 = r13
            r64 = r15
            r15 = 3
            r31 = r1
        L_0x0c4c:
            r13 = r24
            if (r13 == 0) goto L_0x0c56
            java.lang.String r4 = r13.translate(r4)
            r0 = r4
            goto L_0x0c57
        L_0x0c56:
            r0 = r4
        L_0x0c57:
            com.alibaba.fastjson.util.FieldInfo r12 = new com.alibaba.fastjson.util.FieldInfo
            r24 = 0
            r1 = r12
            r2 = r0
            r3 = r38
            r4 = r47
            r5 = r62
            r6 = r63
            r7 = r18
            r8 = r26
            r9 = r27
            r10 = r28
            r11 = r31
            r15 = r12
            r12 = r24
            r57 = r13
            r13 = r19
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            add(r14, r15)
            goto L_0x0c9d
        L_0x0c7d:
            r40 = r0
            r36 = r3
            r33 = r4
            r34 = r5
            r39 = r6
            r38 = r7
            r35 = r10
            r42 = r12
            r30 = r13
            r64 = r15
            r57 = r24
            r56 = r45
            r29 = r49
            r32 = 2
            r41 = 1
            r45 = r11
        L_0x0c9d:
            int r10 = r35 + 1
            r15 = r64
            r49 = r29
            r13 = r30
            r48 = r33
            r0 = r40
            r12 = r42
            r11 = r45
            r45 = r56
            r24 = r57
            goto L_0x07fc
        L_0x0cb3:
            r42 = r12
            r30 = r13
            r57 = r24
            r56 = r45
            r45 = r11
            java.lang.reflect.Field[] r0 = r62.getFields()
            r15 = r62
            r13 = r63
            r12 = r57
            computeFields(r15, r13, r12, r14, r0)
            java.lang.reflect.Method[] r11 = r62.getMethods()
            int r10 = r11.length
            r7 = r42
        L_0x0cd1:
            if (r7 >= r10) goto L_0x0ec6
            r6 = r11[r7]
            java.lang.String r5 = r6.getName()
            int r1 = r5.length()
            if (r1 >= r8) goto L_0x0cf4
            r24 = r0
            r42 = r7
            r29 = r8
            r31 = r9
            r32 = r10
            r37 = r11
            r60 = r12
            r15 = r13
            r58 = r56
            r26 = 3
            goto L_0x0eb1
        L_0x0cf4:
            int r1 = r6.getModifiers()
            boolean r1 = java.lang.reflect.Modifier.isStatic(r1)
            if (r1 == 0) goto L_0x0d13
            r24 = r0
            r42 = r7
            r29 = r8
            r31 = r9
            r32 = r10
            r37 = r11
            r60 = r12
            r15 = r13
            r58 = r56
            r26 = 3
            goto L_0x0eb1
        L_0x0d13:
            if (r30 != 0) goto L_0x0e9a
            boolean r1 = r5.startsWith(r9)
            if (r1 == 0) goto L_0x0e9a
            r4 = 3
            char r1 = r5.charAt(r4)
            boolean r1 = java.lang.Character.isUpperCase(r1)
            if (r1 == 0) goto L_0x0e82
            java.lang.Class[] r1 = r6.getParameterTypes()
            int r1 = r1.length
            if (r1 == 0) goto L_0x0d42
            r24 = r0
            r26 = r4
            r42 = r7
            r29 = r8
            r31 = r9
            r32 = r10
            r37 = r11
            r60 = r12
            r15 = r13
            r58 = r56
            goto L_0x0eb1
        L_0x0d42:
            java.lang.Class<java.util.Collection> r1 = java.util.Collection.class
            java.lang.Class r2 = r6.getReturnType()
            boolean r1 = r1.isAssignableFrom(r2)
            if (r1 != 0) goto L_0x0d88
            java.lang.Class<java.util.Map> r1 = java.util.Map.class
            java.lang.Class r2 = r6.getReturnType()
            boolean r1 = r1.isAssignableFrom(r2)
            if (r1 != 0) goto L_0x0d88
            java.lang.Class<java.util.concurrent.atomic.AtomicBoolean> r1 = java.util.concurrent.atomic.AtomicBoolean.class
            java.lang.Class r2 = r6.getReturnType()
            if (r1 == r2) goto L_0x0d88
            java.lang.Class<java.util.concurrent.atomic.AtomicInteger> r1 = java.util.concurrent.atomic.AtomicInteger.class
            java.lang.Class r2 = r6.getReturnType()
            if (r1 == r2) goto L_0x0d88
            java.lang.Class<java.util.concurrent.atomic.AtomicLong> r1 = java.util.concurrent.atomic.AtomicLong.class
            java.lang.Class r2 = r6.getReturnType()
            if (r1 != r2) goto L_0x0d73
            goto L_0x0d88
        L_0x0d73:
            r24 = r0
            r26 = r4
            r42 = r7
            r29 = r8
            r31 = r9
            r32 = r10
            r37 = r11
            r60 = r12
            r15 = r13
            r58 = r56
            goto L_0x0eb1
        L_0x0d88:
            r1 = 0
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r2 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r2 = com.alibaba.fastjson.util.TypeUtils.getAnnotation((java.lang.reflect.Method) r6, r2)
            r18 = r2
            com.alibaba.fastjson.annotation.JSONField r18 = (com.alibaba.fastjson.annotation.JSONField) r18
            if (r18 == 0) goto L_0x0db0
            boolean r2 = r18.deserialize()
            if (r2 == 0) goto L_0x0db0
            r24 = r0
            r26 = r4
            r42 = r7
            r29 = r8
            r31 = r9
            r32 = r10
            r37 = r11
            r60 = r12
            r15 = r13
            r58 = r56
            goto L_0x0eb1
        L_0x0db0:
            if (r18 == 0) goto L_0x0dc7
            java.lang.String r2 = r18.name()
            int r2 = r2.length()
            if (r2 <= 0) goto L_0x0dc7
            java.lang.String r2 = r18.name()
            r24 = r0
            r27 = r1
            r3 = r56
            goto L_0x0e1c
        L_0x0dc7:
            java.lang.String r2 = com.alibaba.fastjson.util.TypeUtils.getPropertyNameByMethodName(r5)
            r3 = r56
            java.lang.reflect.Field r4 = com.alibaba.fastjson.util.TypeUtils.getField(r15, r2, r3)
            if (r4 == 0) goto L_0x0e18
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r8 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r8 = com.alibaba.fastjson.util.TypeUtils.getAnnotation((java.lang.reflect.Field) r4, r8)
            com.alibaba.fastjson.annotation.JSONField r8 = (com.alibaba.fastjson.annotation.JSONField) r8
            if (r8 == 0) goto L_0x0df8
            boolean r24 = r8.deserialize()
            if (r24 != 0) goto L_0x0df8
            r24 = r0
            r58 = r3
            r42 = r7
            r31 = r9
            r32 = r10
            r37 = r11
            r60 = r12
            r15 = r13
            r26 = 3
            r29 = 4
            goto L_0x0eb1
        L_0x0df8:
            r24 = r0
            java.lang.Class<java.util.Collection> r0 = java.util.Collection.class
            r27 = r1
            java.lang.Class r1 = r6.getReturnType()
            boolean r0 = r0.isAssignableFrom(r1)
            if (r0 != 0) goto L_0x0e14
            java.lang.Class<java.util.Map> r0 = java.util.Map.class
            java.lang.Class r1 = r6.getReturnType()
            boolean r0 = r0.isAssignableFrom(r1)
            if (r0 == 0) goto L_0x0e1c
        L_0x0e14:
            r1 = r4
            r27 = r1
            goto L_0x0e1c
        L_0x0e18:
            r24 = r0
            r27 = r1
        L_0x0e1c:
            if (r12 == 0) goto L_0x0e24
            java.lang.String r2 = r12.translate(r2)
            r0 = r2
            goto L_0x0e25
        L_0x0e24:
            r0 = r2
        L_0x0e25:
            com.alibaba.fastjson.util.FieldInfo r28 = getField(r14, r0)
            if (r28 == 0) goto L_0x0e3e
            r58 = r3
            r42 = r7
            r31 = r9
            r32 = r10
            r37 = r11
            r60 = r12
            r15 = r13
            r26 = 3
            r29 = 4
            goto L_0x0eb1
        L_0x0e3e:
            com.alibaba.fastjson.util.FieldInfo r8 = new com.alibaba.fastjson.util.FieldInfo
            r29 = 0
            r31 = 0
            r32 = 0
            r33 = 0
            r34 = 0
            r1 = r8
            r2 = r0
            r4 = r3
            r3 = r6
            r58 = r4
            r26 = 3
            r4 = r27
            r35 = r5
            r5 = r62
            r36 = r6
            r6 = r63
            r42 = r7
            r7 = r29
            r59 = r8
            r29 = 4
            r8 = r31
            r31 = r9
            r9 = r32
            r32 = r10
            r10 = r18
            r37 = r11
            r11 = r33
            r60 = r12
            r12 = r34
            r15 = r13
            r13 = r19
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            r1 = r59
            add(r14, r1)
            goto L_0x0eb1
        L_0x0e82:
            r24 = r0
            r26 = r4
            r35 = r5
            r36 = r6
            r42 = r7
            r29 = r8
            r31 = r9
            r32 = r10
            r37 = r11
            r60 = r12
            r15 = r13
            r58 = r56
            goto L_0x0eb1
        L_0x0e9a:
            r24 = r0
            r35 = r5
            r36 = r6
            r42 = r7
            r29 = r8
            r31 = r9
            r32 = r10
            r37 = r11
            r60 = r12
            r15 = r13
            r58 = r56
            r26 = 3
        L_0x0eb1:
            int r7 = r42 + 1
            r13 = r15
            r0 = r24
            r8 = r29
            r9 = r31
            r10 = r32
            r11 = r37
            r56 = r58
            r12 = r60
            r15 = r62
            goto L_0x0cd1
        L_0x0ec6:
            r24 = r0
            r60 = r12
            r15 = r13
            r58 = r56
            int r0 = r14.size()
            if (r0 != 0) goto L_0x0f02
            boolean r0 = com.alibaba.fastjson.util.TypeUtils.isXmlField(r62)
            if (r0 == 0) goto L_0x0edb
            r0 = 1
            goto L_0x0edd
        L_0x0edb:
            r0 = r65
        L_0x0edd:
            if (r0 == 0) goto L_0x0efa
            r1 = r62
        L_0x0ee1:
            if (r1 == 0) goto L_0x0ef2
            r10 = r62
            r11 = r15
            r12 = r58
            r13 = r60
            computeFields(r10, r11, r13, r14, r12)
            java.lang.Class r1 = r1.getSuperclass()
            goto L_0x0ee1
        L_0x0ef2:
            r10 = r62
            r11 = r15
            r12 = r58
            r13 = r60
            goto L_0x0f0b
        L_0x0efa:
            r10 = r62
            r11 = r15
            r12 = r58
            r13 = r60
            goto L_0x0f0b
        L_0x0f02:
            r10 = r62
            r11 = r15
            r12 = r58
            r13 = r60
            r0 = r65
        L_0x0f0b:
            com.alibaba.fastjson.util.JavaBeanInfo r15 = new com.alibaba.fastjson.util.JavaBeanInfo
            r1 = r15
            r2 = r62
            r3 = r30
            r4 = r21
            r5 = r16
            r6 = r17
            r7 = r22
            r8 = r23
            r9 = r14
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9)
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.JavaBeanInfo.build(java.lang.Class, java.lang.reflect.Type, com.alibaba.fastjson.PropertyNamingStrategy, boolean, boolean, boolean):com.alibaba.fastjson.util.JavaBeanInfo");
    }

    private static void computeFields(Class<?> clazz2, Type type, PropertyNamingStrategy propertyNamingStrategy, List<FieldInfo> fieldList, Field[] fields2) {
        int i;
        int i2;
        boolean contains;
        int parserFeatures2;
        int serialzeFeatures;
        int ordinal;
        String propertyName;
        PropertyNamingStrategy propertyNamingStrategy2 = propertyNamingStrategy;
        Field[] fieldArr = fields2;
        Map<TypeVariable, Type> genericInfo = buildGenericInfo(clazz2);
        int length = fieldArr.length;
        int i3 = 0;
        while (i3 < length) {
            Field field = fieldArr[i3];
            int modifiers = field.getModifiers();
            if ((modifiers & 8) != 0) {
                List<FieldInfo> list = fieldList;
                i = i3;
                i2 = length;
            } else {
                if ((modifiers & 16) != 0) {
                    Class<?> fieldType = field.getType();
                    if (!(Map.class.isAssignableFrom(fieldType) || Collection.class.isAssignableFrom(fieldType) || AtomicLong.class.equals(fieldType) || AtomicInteger.class.equals(fieldType) || AtomicBoolean.class.equals(fieldType))) {
                        List<FieldInfo> list2 = fieldList;
                        i = i3;
                        i2 = length;
                    }
                }
                Iterator<FieldInfo> it = fieldList.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (it.next().name.equals(field.getName())) {
                            contains = true;
                            break;
                        }
                    } else {
                        contains = false;
                        break;
                    }
                }
                if (contains) {
                    List<FieldInfo> list3 = fieldList;
                    i = i3;
                    i2 = length;
                } else {
                    String propertyName2 = field.getName();
                    JSONField fieldAnnotation = (JSONField) TypeUtils.getAnnotation(field, JSONField.class);
                    if (fieldAnnotation == null) {
                        ordinal = 0;
                        serialzeFeatures = 0;
                        parserFeatures2 = 0;
                    } else if (!fieldAnnotation.deserialize()) {
                        List<FieldInfo> list4 = fieldList;
                        i = i3;
                        i2 = length;
                    } else {
                        int ordinal2 = fieldAnnotation.ordinal();
                        int serialzeFeatures2 = SerializerFeature.of(fieldAnnotation.serialzeFeatures());
                        int parserFeatures3 = Feature.of(fieldAnnotation.parseFeatures());
                        if (fieldAnnotation.name().length() != 0) {
                            propertyName2 = fieldAnnotation.name();
                            ordinal = ordinal2;
                            serialzeFeatures = serialzeFeatures2;
                            parserFeatures2 = parserFeatures3;
                        } else {
                            ordinal = ordinal2;
                            serialzeFeatures = serialzeFeatures2;
                            parserFeatures2 = parserFeatures3;
                        }
                    }
                    if (propertyNamingStrategy2 != null) {
                        propertyName = propertyNamingStrategy2.translate(propertyName2);
                    } else {
                        propertyName = propertyName2;
                    }
                    FieldInfo fieldInfo = r2;
                    Field field2 = field;
                    i = i3;
                    i2 = length;
                    FieldInfo fieldInfo2 = new FieldInfo(propertyName, (Method) null, field, clazz2, type, ordinal, serialzeFeatures, parserFeatures2, (JSONField) null, fieldAnnotation, (String) null, genericInfo);
                    add(fieldList, fieldInfo);
                }
            }
            i3 = i + 1;
            length = i2;
        }
        List<FieldInfo> list5 = fieldList;
    }

    static Constructor<?> getDefaultConstructor(Class<?> clazz2, Constructor<?>[] constructors) {
        if (Modifier.isAbstract(clazz2.getModifiers())) {
            return null;
        }
        Constructor<?> defaultConstructor2 = null;
        int length = constructors.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            Constructor<?> constructor = constructors[i];
            if (constructor.getParameterTypes().length == 0) {
                defaultConstructor2 = constructor;
                break;
            }
            i++;
        }
        if (defaultConstructor2 != null || !clazz2.isMemberClass() || Modifier.isStatic(clazz2.getModifiers())) {
            return defaultConstructor2;
        }
        for (Constructor<?> constructor2 : constructors) {
            Class<?>[] parameterTypes = constructor2.getParameterTypes();
            Class<?>[] types = parameterTypes;
            if (parameterTypes.length == 1 && types[0].equals(clazz2.getDeclaringClass())) {
                return constructor2;
            }
        }
        return defaultConstructor2;
    }

    public static Constructor<?> getCreatorConstructor(Constructor[] constructors) {
        Constructor[] constructorArr = constructors;
        Constructor constructor = null;
        for (Constructor constructor2 : constructorArr) {
            if (((JSONCreator) constructor2.getAnnotation(JSONCreator.class)) != null) {
                if (constructor == null) {
                    constructor = constructor2;
                } else {
                    throw new JSONException("multi-JSONCreator");
                }
            }
        }
        if (constructor != null) {
            return constructor;
        }
        for (Constructor constructor3 : constructorArr) {
            Annotation[][] paramAnnotationArrays = TypeUtils.getParameterAnnotations(constructor3);
            if (paramAnnotationArrays.length != 0) {
                boolean match = true;
                int length = paramAnnotationArrays.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    Annotation[] paramAnnotationArray = paramAnnotationArrays[i];
                    boolean paramMatch = false;
                    int length2 = paramAnnotationArray.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length2) {
                            break;
                        } else if (paramAnnotationArray[i2] instanceof JSONField) {
                            paramMatch = true;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (!paramMatch) {
                        match = false;
                        break;
                    }
                    i++;
                }
                if (!match) {
                    continue;
                } else if (constructor == null) {
                    constructor = constructor3;
                } else {
                    throw new JSONException("multi-JSONCreator");
                }
            }
        }
        if (constructor != null) {
            return constructor;
        }
        return constructor;
    }

    private static Method getFactoryMethod(Class<?> clazz2, Method[] methods, boolean jacksonCompatible) {
        Method factoryMethod2 = null;
        for (Method method : methods) {
            if (Modifier.isStatic(method.getModifiers()) && clazz2.isAssignableFrom(method.getReturnType()) && ((JSONCreator) TypeUtils.getAnnotation(method, JSONCreator.class)) != null) {
                if (factoryMethod2 == null) {
                    factoryMethod2 = method;
                } else {
                    throw new JSONException("multi-JSONCreator");
                }
            }
        }
        if (factoryMethod2 != null || !jacksonCompatible) {
            return factoryMethod2;
        }
        for (Method method2 : methods) {
            if (TypeUtils.isJacksonCreator(method2)) {
                return method2;
            }
        }
        return factoryMethod2;
    }

    public static Class<?> getBuilderClass(JSONType type) {
        return getBuilderClass((Class<?>) null, type);
    }

    public static Class<?> getBuilderClass(Class<?> clazz2, JSONType type) {
        Class<?> builderClass2;
        if (clazz2 != null && clazz2.getName().equals("org.springframework.security.web.savedrequest.DefaultSavedRequest")) {
            return TypeUtils.loadClass("org.springframework.security.web.savedrequest.DefaultSavedRequest$Builder");
        }
        if (type == null || (builderClass2 = type.builder()) == Void.class) {
            return null;
        }
        return builderClass2;
    }
}
