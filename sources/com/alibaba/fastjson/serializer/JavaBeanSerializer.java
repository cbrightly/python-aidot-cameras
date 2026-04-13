package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JavaBeanSerializer extends SerializeFilterable implements ObjectSerializer {
    protected SerializeBeanInfo beanInfo;
    protected final FieldSerializer[] getters;
    private volatile transient long[] hashArray;
    private volatile transient short[] hashArrayMapping;
    protected final FieldSerializer[] sortedGetters;

    public JavaBeanSerializer(Class<?> beanType) {
        this(beanType, (Map<String, String>) null);
    }

    public JavaBeanSerializer(Class<?> beanType, String... aliasList) {
        this(beanType, createAliasMap(aliasList));
    }

    static Map<String, String> createAliasMap(String... aliasList) {
        Map<String, String> aliasMap = new HashMap<>();
        for (String alias : aliasList) {
            aliasMap.put(alias, alias);
        }
        return aliasMap;
    }

    public Class<?> getType() {
        return this.beanInfo.beanType;
    }

    public JavaBeanSerializer(Class<?> beanType, Map<String, String> aliasMap) {
        this(TypeUtils.buildBeanInfo(beanType, aliasMap, (PropertyNamingStrategy) null));
    }

    public JavaBeanSerializer(SerializeBeanInfo beanInfo2) {
        FieldSerializer[] fieldSerializerArr;
        this.beanInfo = beanInfo2;
        this.sortedGetters = new FieldSerializer[beanInfo2.sortedFields.length];
        int i = 0;
        while (true) {
            fieldSerializerArr = this.sortedGetters;
            if (i >= fieldSerializerArr.length) {
                break;
            }
            fieldSerializerArr[i] = new FieldSerializer(beanInfo2.beanType, beanInfo2.sortedFields[i]);
            i++;
        }
        FieldInfo[] fieldInfoArr = beanInfo2.fields;
        if (fieldInfoArr == beanInfo2.sortedFields) {
            this.getters = fieldSerializerArr;
        } else {
            this.getters = new FieldSerializer[fieldInfoArr.length];
            boolean hashNotMatch = false;
            int i2 = 0;
            while (true) {
                if (i2 >= this.getters.length) {
                    break;
                }
                FieldSerializer fieldSerializer = getFieldSerializer(beanInfo2.fields[i2].name);
                if (fieldSerializer == null) {
                    hashNotMatch = true;
                    break;
                } else {
                    this.getters[i2] = fieldSerializer;
                    i2++;
                }
            }
            if (hashNotMatch) {
                FieldSerializer[] fieldSerializerArr2 = this.sortedGetters;
                System.arraycopy(fieldSerializerArr2, 0, this.getters, 0, fieldSerializerArr2.length);
            }
        }
        JSONType jSONType = beanInfo2.jsonType;
        if (jSONType != null) {
            for (Class<? extends SerializeFilter> filterClass : jSONType.serialzeFilters()) {
                try {
                    addFilter((SerializeFilter) filterClass.getConstructor(new Class[0]).newInstance(new Object[0]));
                } catch (Exception e) {
                }
            }
        }
    }

    public void writeDirectNonContext(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        write(serializer, object, fieldName, fieldType, features);
    }

    public void writeAsArray(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        write(serializer, object, fieldName, fieldType, features);
    }

    public void writeAsArrayNonContext(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        write(serializer, object, fieldName, fieldType, features);
    }

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        write(serializer, object, fieldName, fieldType, features, false);
    }

    public void writeNoneASM(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        write(serializer, object, fieldName, fieldType, features, false);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:242:0x034c, code lost:
        if ((r8.beanInfo.features & r3) == 0) goto L_0x03ee;
     */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0184 A[SYNTHETIC, Splitter:B:112:0x0184] */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x01a1  */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x01a3 A[SYNTHETIC, Splitter:B:120:0x01a3] */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x01be  */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x01cd  */
    /* JADX WARNING: Removed duplicated region for block: B:409:0x05c5  */
    /* JADX WARNING: Removed duplicated region for block: B:410:0x05c8  */
    /* JADX WARNING: Removed duplicated region for block: B:418:0x05d3 A[SYNTHETIC, Splitter:B:418:0x05d3] */
    /* JADX WARNING: Removed duplicated region for block: B:427:0x05f4 A[Catch:{ Exception -> 0x05eb, all -> 0x05e2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:428:0x05fa  */
    /* JADX WARNING: Removed duplicated region for block: B:452:0x0669 A[SYNTHETIC, Splitter:B:452:0x0669] */
    /* JADX WARNING: Removed duplicated region for block: B:456:0x0680 A[ADDED_TO_REGION, Catch:{ all -> 0x067d }] */
    /* JADX WARNING: Removed duplicated region for block: B:465:0x06c4 A[Catch:{ all -> 0x067d }] */
    /* JADX WARNING: Removed duplicated region for block: B:468:0x06e2 A[Catch:{ all -> 0x067d }] */
    /* JADX WARNING: Removed duplicated region for block: B:470:0x06e9 A[Catch:{ all -> 0x067d }] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00ae A[Catch:{ Exception -> 0x006a, all -> 0x0062 }] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00b9  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00c1  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x00da A[Catch:{ Exception -> 0x063d, all -> 0x0635 }] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x00dd A[Catch:{ Exception -> 0x063d, all -> 0x0635 }] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x00f7 A[SYNTHETIC, Splitter:B:77:0x00f7] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void write(com.alibaba.fastjson.serializer.JSONSerializer r39, java.lang.Object r40, java.lang.Object r41, java.lang.reflect.Type r42, int r43, boolean r44) {
        /*
            r38 = this;
            r8 = r38
            r9 = r39
            r10 = r40
            r11 = r41
            r12 = r42
            r13 = r43
            java.lang.Class<java.lang.String> r14 = java.lang.String.class
            com.alibaba.fastjson.serializer.SerializeWriter r15 = r9.out
            if (r10 != 0) goto L_0x0016
            r15.writeNull()
            return
        L_0x0016:
            boolean r0 = r8.writeReference(r9, r10, r13)
            if (r0 == 0) goto L_0x001d
            return
        L_0x001d:
            boolean r0 = r15.sortField
            if (r0 == 0) goto L_0x0025
            com.alibaba.fastjson.serializer.FieldSerializer[] r0 = r8.sortedGetters
            r7 = r0
            goto L_0x0028
        L_0x0025:
            com.alibaba.fastjson.serializer.FieldSerializer[] r0 = r8.getters
            r7 = r0
        L_0x0028:
            com.alibaba.fastjson.serializer.SerialContext r6 = r9.context
            com.alibaba.fastjson.serializer.SerializeBeanInfo r0 = r8.beanInfo
            java.lang.Class<?> r0 = r0.beanType
            boolean r0 = r0.isEnum()
            if (r0 != 0) goto L_0x0046
            com.alibaba.fastjson.serializer.SerializeBeanInfo r0 = r8.beanInfo
            int r5 = r0.features
            r1 = r39
            r2 = r6
            r3 = r40
            r4 = r41
            r11 = r6
            r6 = r43
            r1.setContext(r2, r3, r4, r5, r6)
            goto L_0x0047
        L_0x0046:
            r11 = r6
        L_0x0047:
            boolean r16 = r8.isWriteAsArray(r9, r13)
            r1 = 0
            if (r16 == 0) goto L_0x0051
            r0 = 91
            goto L_0x0053
        L_0x0051:
            r0 = 123(0x7b, float:1.72E-43)
        L_0x0053:
            r6 = r0
            if (r16 == 0) goto L_0x0059
            r0 = 93
            goto L_0x005b
        L_0x0059:
            r0 = 125(0x7d, float:1.75E-43)
        L_0x005b:
            r5 = r0
            if (r44 != 0) goto L_0x0070
            r15.append((char) r6)     // Catch:{ Exception -> 0x006a, all -> 0x0062 }
            goto L_0x0070
        L_0x0062:
            r0 = move-exception
            r4 = r41
            r3 = r7
            r2 = r10
            r7 = r11
            goto L_0x06f4
        L_0x006a:
            r0 = move-exception
            r3 = r7
            r2 = r10
            r4 = r11
            goto L_0x0641
        L_0x0070:
            int r0 = r7.length     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            if (r0 <= 0) goto L_0x0081
            com.alibaba.fastjson.serializer.SerializerFeature r0 = com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat     // Catch:{ Exception -> 0x006a, all -> 0x0062 }
            boolean r0 = r15.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r0)     // Catch:{ Exception -> 0x006a, all -> 0x0062 }
            if (r0 == 0) goto L_0x0081
            r39.incrementIndent()     // Catch:{ Exception -> 0x006a, all -> 0x0062 }
            r39.println()     // Catch:{ Exception -> 0x006a, all -> 0x0062 }
        L_0x0081:
            r0 = 0
            com.alibaba.fastjson.serializer.SerializeBeanInfo r2 = r8.beanInfo     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            int r2 = r2.features     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            com.alibaba.fastjson.serializer.SerializerFeature r3 = com.alibaba.fastjson.serializer.SerializerFeature.WriteClassName     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            int r4 = r3.mask     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            r2 = r2 & r4
            if (r2 != 0) goto L_0x009b
            r2 = r13 & r4
            if (r2 != 0) goto L_0x009b
            boolean r2 = r9.isWriteClassName(r12, r10)     // Catch:{ Exception -> 0x006a, all -> 0x0062 }
            if (r2 == 0) goto L_0x0098
            goto L_0x009b
        L_0x0098:
            r17 = r0
            goto L_0x00bb
        L_0x009b:
            java.lang.Class r2 = r40.getClass()     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            if (r2 == r12) goto L_0x00aa
            boolean r4 = r12 instanceof java.lang.reflect.WildcardType     // Catch:{ Exception -> 0x006a, all -> 0x0062 }
            if (r4 == 0) goto L_0x00aa
            java.lang.Class r4 = com.alibaba.fastjson.util.TypeUtils.getClass(r42)     // Catch:{ Exception -> 0x006a, all -> 0x0062 }
            goto L_0x00ac
        L_0x00aa:
            r4 = r42
        L_0x00ac:
            if (r2 == r4) goto L_0x00b9
            r17 = r0
            com.alibaba.fastjson.serializer.SerializeBeanInfo r0 = r8.beanInfo     // Catch:{ Exception -> 0x006a, all -> 0x0062 }
            java.lang.String r0 = r0.typeKey     // Catch:{ Exception -> 0x006a, all -> 0x0062 }
            r8.writeClassName(r9, r0, r10)     // Catch:{ Exception -> 0x006a, all -> 0x0062 }
            r0 = 1
            goto L_0x00bd
        L_0x00b9:
            r17 = r0
        L_0x00bb:
            r0 = r17
        L_0x00bd:
            r4 = 44
            if (r0 == 0) goto L_0x00c4
            r17 = r4
            goto L_0x00c6
        L_0x00c4:
            r17 = 0
        L_0x00c6:
            r18 = r17
            boolean r3 = r15.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r3)     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            r17 = r3
            r3 = r18
            char r18 = r8.writeBefore(r9, r10, r3)     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            r19 = r18
            r13 = r19
            if (r13 != r4) goto L_0x00dd
            r19 = 1
            goto L_0x00df
        L_0x00dd:
            r19 = 0
        L_0x00df:
            r0 = r19
            com.alibaba.fastjson.serializer.SerializerFeature r2 = com.alibaba.fastjson.serializer.SerializerFeature.SkipTransientField     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            boolean r2 = r15.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r2)     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            r20 = r2
            com.alibaba.fastjson.serializer.SerializerFeature r2 = com.alibaba.fastjson.serializer.SerializerFeature.IgnoreNonFieldGetter     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            boolean r2 = r15.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r2)     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            r21 = r2
            r2 = 0
            r22 = r0
        L_0x00f4:
            int r0 = r7.length     // Catch:{ Exception -> 0x062e, all -> 0x0624 }
            if (r2 >= r0) goto L_0x05b2
            r0 = r7[r2]     // Catch:{ Exception -> 0x05a9, all -> 0x059e }
            r23 = r0
            r24 = r13
            r13 = r23
            com.alibaba.fastjson.util.FieldInfo r0 = r13.fieldInfo     // Catch:{ Exception -> 0x05a9, all -> 0x059e }
            java.lang.reflect.Field r4 = r0.field     // Catch:{ Exception -> 0x05a9, all -> 0x059e }
            r25 = r4
            r4 = r0
            java.lang.String r0 = r4.name     // Catch:{ Exception -> 0x05a9, all -> 0x059e }
            r26 = r0
            java.lang.Class<?> r0 = r4.fieldClass     // Catch:{ Exception -> 0x05a9, all -> 0x059e }
            r27 = r0
            int r0 = r15.features     // Catch:{ Exception -> 0x05a9, all -> 0x059e }
            r28 = r1
            int r1 = r4.serialzeFeatures     // Catch:{ Exception -> 0x0595, all -> 0x058a }
            r29 = r2
            com.alibaba.fastjson.serializer.SerializerFeature r2 = com.alibaba.fastjson.serializer.SerializerFeature.UseSingleQuotes     // Catch:{ Exception -> 0x0595, all -> 0x058a }
            boolean r0 = com.alibaba.fastjson.serializer.SerializerFeature.isEnabled(r0, r1, r2)     // Catch:{ Exception -> 0x0595, all -> 0x058a }
            r30 = r0
            boolean r0 = r15.quoteFieldNames     // Catch:{ Exception -> 0x0595, all -> 0x058a }
            if (r0 == 0) goto L_0x0126
            if (r30 != 0) goto L_0x0126
            r0 = 1
            goto L_0x0127
        L_0x0126:
            r0 = 0
        L_0x0127:
            r31 = r0
            if (r20 == 0) goto L_0x0151
            boolean r0 = r4.fieldTransient     // Catch:{ Exception -> 0x0149, all -> 0x013f }
            if (r0 == 0) goto L_0x0151
            r32 = r3
            r35 = r5
            r36 = r6
            r37 = r7
            r27 = r11
            r19 = r29
            r2 = 0
            goto L_0x0571
        L_0x013f:
            r0 = move-exception
            r4 = r41
            r3 = r7
            r2 = r10
            r7 = r11
            r1 = r28
            goto L_0x06f4
        L_0x0149:
            r0 = move-exception
            r3 = r7
            r2 = r10
            r4 = r11
            r1 = r28
            goto L_0x0641
        L_0x0151:
            if (r21 == 0) goto L_0x0164
            if (r25 != 0) goto L_0x0164
            r32 = r3
            r35 = r5
            r36 = r6
            r37 = r7
            r27 = r11
            r19 = r29
            r2 = 0
            goto L_0x0571
        L_0x0164:
            r0 = 0
            r2 = r26
            boolean r1 = r8.applyName(r9, r10, r2)     // Catch:{ Exception -> 0x0595, all -> 0x058a }
            if (r1 == 0) goto L_0x0179
            java.lang.String r1 = r4.label     // Catch:{ Exception -> 0x0149, all -> 0x013f }
            boolean r1 = r8.applyLabel(r9, r1)     // Catch:{ Exception -> 0x0149, all -> 0x013f }
            if (r1 != 0) goto L_0x0176
            goto L_0x0179
        L_0x0176:
            r26 = r0
            goto L_0x017e
        L_0x0179:
            if (r16 == 0) goto L_0x0560
            r0 = 1
            r26 = r0
        L_0x017e:
            com.alibaba.fastjson.serializer.SerializeBeanInfo r0 = r8.beanInfo     // Catch:{ Exception -> 0x0595, all -> 0x058a }
            java.lang.String r0 = r0.typeKey     // Catch:{ Exception -> 0x0595, all -> 0x058a }
            if (r0 == 0) goto L_0x019f
            boolean r0 = r2.equals(r0)     // Catch:{ Exception -> 0x0149, all -> 0x013f }
            if (r0 == 0) goto L_0x019f
            boolean r0 = r9.isWriteClassName(r12, r10)     // Catch:{ Exception -> 0x0149, all -> 0x013f }
            if (r0 == 0) goto L_0x019f
            r32 = r3
            r35 = r5
            r36 = r6
            r37 = r7
            r27 = r11
            r19 = r29
            r2 = 0
            goto L_0x0571
        L_0x019f:
            if (r26 == 0) goto L_0x01a3
            r0 = 0
            goto L_0x01b8
        L_0x01a3:
            java.lang.Object r0 = r13.getPropertyValueDirect(r10)     // Catch:{ InvocationTargetException -> 0x01a8 }
            goto L_0x01b8
        L_0x01a8:
            r0 = move-exception
            r1 = r0
            r0 = r1
            r1 = r13
            r28 = r1
            com.alibaba.fastjson.serializer.SerializerFeature r1 = com.alibaba.fastjson.serializer.SerializerFeature.IgnoreErrorGetter     // Catch:{ Exception -> 0x0595, all -> 0x058a }
            boolean r1 = r15.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r1)     // Catch:{ Exception -> 0x0595, all -> 0x058a }
            if (r1 == 0) goto L_0x0537
            r1 = 0
            r0 = r1
        L_0x01b8:
            boolean r1 = r8.apply(r9, r10, r2, r0)     // Catch:{ Exception -> 0x0595, all -> 0x058a }
            if (r1 != 0) goto L_0x01cd
            r32 = r3
            r35 = r5
            r36 = r6
            r37 = r7
            r27 = r11
            r19 = r29
            r2 = 0
            goto L_0x0571
        L_0x01cd:
            r1 = r27
            if (r1 != r14) goto L_0x01eb
            r27 = r1
            java.lang.String r1 = "trim"
            r32 = r3
            java.lang.String r3 = r4.format     // Catch:{ Exception -> 0x0149, all -> 0x013f }
            boolean r1 = r1.equals(r3)     // Catch:{ Exception -> 0x0149, all -> 0x013f }
            if (r1 == 0) goto L_0x01ef
            if (r0 == 0) goto L_0x01ef
            r1 = r0
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Exception -> 0x0149, all -> 0x013f }
            java.lang.String r1 = r1.trim()     // Catch:{ Exception -> 0x0149, all -> 0x013f }
            r0 = r1
            goto L_0x01ef
        L_0x01eb:
            r27 = r1
            r32 = r3
        L_0x01ef:
            r1 = r2
            java.lang.String r3 = r8.processKey(r9, r10, r1, r0)     // Catch:{ Exception -> 0x0595, all -> 0x058a }
            r1 = r0
            r33 = r3
            com.alibaba.fastjson.serializer.BeanContext r3 = r13.fieldContext     // Catch:{ Exception -> 0x0595, all -> 0x058a }
            r12 = r27
            r27 = r11
            r11 = r1
            r1 = r38
            r19 = r29
            r34 = 0
            r29 = r2
            r2 = r39
            r10 = r33
            r33 = r11
            r11 = r4
            r4 = r40
            r35 = r5
            r5 = r29
            r36 = r6
            r6 = r0
            r37 = r7
            r7 = r43
            java.lang.Object r1 = r1.processValue(r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r0 = r1
            java.lang.String r1 = ""
            if (r0 != 0) goto L_0x0338
            int r2 = r11.serialzeFeatures     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            com.alibaba.fastjson.annotation.JSONField r3 = r11.getAnnotation()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            com.alibaba.fastjson.serializer.SerializeBeanInfo r4 = r8.beanInfo     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            com.alibaba.fastjson.annotation.JSONType r4 = r4.jsonType     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r4 == 0) goto L_0x0238
            com.alibaba.fastjson.serializer.SerializerFeature[] r4 = r4.serialzeFeatures()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            int r4 = com.alibaba.fastjson.serializer.SerializerFeature.of(r4)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r2 = r2 | r4
        L_0x0238:
            if (r3 == 0) goto L_0x024b
            java.lang.String r4 = r3.defaultValue()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            boolean r4 = r1.equals(r4)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r4 != 0) goto L_0x024b
            java.lang.String r4 = r3.defaultValue()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r0 = r4
            goto L_0x0338
        L_0x024b:
            java.lang.Class<java.lang.Boolean> r4 = java.lang.Boolean.class
            if (r12 != r4) goto L_0x0281
            com.alibaba.fastjson.serializer.SerializerFeature r4 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullBooleanAsFalse     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            int r4 = r4.mask     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            com.alibaba.fastjson.serializer.SerializerFeature r5 = com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            int r5 = r5.mask     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r6 = r4 | r5
            if (r16 != 0) goto L_0x0268
            r7 = r2 & r6
            if (r7 != 0) goto L_0x0268
            int r7 = r15.features     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r7 = r7 & r6
            if (r7 != 0) goto L_0x0268
            r2 = r34
            goto L_0x0571
        L_0x0268:
            r7 = r2 & r4
            if (r7 == 0) goto L_0x0272
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r34)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r0 = r5
            goto L_0x027f
        L_0x0272:
            int r7 = r15.features     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r7 = r7 & r4
            if (r7 == 0) goto L_0x027f
            r5 = r5 & r2
            if (r5 != 0) goto L_0x027f
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r34)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r0 = r5
        L_0x027f:
            goto L_0x0338
        L_0x0281:
            if (r12 != r14) goto L_0x02ad
            com.alibaba.fastjson.serializer.SerializerFeature r4 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullStringAsEmpty     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            int r4 = r4.mask     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            com.alibaba.fastjson.serializer.SerializerFeature r5 = com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            int r5 = r5.mask     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r6 = r4 | r5
            if (r16 != 0) goto L_0x029c
            r7 = r2 & r6
            if (r7 != 0) goto L_0x029c
            int r7 = r15.features     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r7 = r7 & r6
            if (r7 != 0) goto L_0x029c
            r2 = r34
            goto L_0x0571
        L_0x029c:
            r7 = r2 & r4
            if (r7 == 0) goto L_0x02a2
            r0 = r1
            goto L_0x02ab
        L_0x02a2:
            int r7 = r15.features     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r7 = r7 & r4
            if (r7 == 0) goto L_0x02ab
            r5 = r5 & r2
            if (r5 != 0) goto L_0x02ab
            r0 = r1
        L_0x02ab:
            goto L_0x0338
        L_0x02ad:
            java.lang.Class<java.lang.Number> r4 = java.lang.Number.class
            boolean r4 = r4.isAssignableFrom(r12)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r4 == 0) goto L_0x02e6
            com.alibaba.fastjson.serializer.SerializerFeature r4 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullNumberAsZero     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            int r4 = r4.mask     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            com.alibaba.fastjson.serializer.SerializerFeature r5 = com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            int r5 = r5.mask     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r6 = r4 | r5
            if (r16 != 0) goto L_0x02ce
            r7 = r2 & r6
            if (r7 != 0) goto L_0x02ce
            int r7 = r15.features     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r7 = r7 & r6
            if (r7 != 0) goto L_0x02ce
            r2 = r34
            goto L_0x0571
        L_0x02ce:
            r7 = r2 & r4
            if (r7 == 0) goto L_0x02d8
            java.lang.Integer r5 = java.lang.Integer.valueOf(r34)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r0 = r5
            goto L_0x02e5
        L_0x02d8:
            int r7 = r15.features     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r7 = r7 & r4
            if (r7 == 0) goto L_0x02e5
            r5 = r5 & r2
            if (r5 != 0) goto L_0x02e5
            java.lang.Integer r5 = java.lang.Integer.valueOf(r34)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r0 = r5
        L_0x02e5:
            goto L_0x0338
        L_0x02e6:
            java.lang.Class<java.util.Collection> r4 = java.util.Collection.class
            boolean r4 = r4.isAssignableFrom(r12)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r4 == 0) goto L_0x031f
            com.alibaba.fastjson.serializer.SerializerFeature r4 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullListAsEmpty     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            int r4 = r4.mask     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            com.alibaba.fastjson.serializer.SerializerFeature r5 = com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            int r5 = r5.mask     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r6 = r4 | r5
            if (r16 != 0) goto L_0x0307
            r7 = r2 & r6
            if (r7 != 0) goto L_0x0307
            int r7 = r15.features     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r7 = r7 & r6
            if (r7 != 0) goto L_0x0307
            r2 = r34
            goto L_0x0571
        L_0x0307:
            r7 = r2 & r4
            if (r7 == 0) goto L_0x0311
            java.util.List r5 = java.util.Collections.emptyList()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r0 = r5
            goto L_0x031e
        L_0x0311:
            int r7 = r15.features     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r7 = r7 & r4
            if (r7 == 0) goto L_0x031e
            r5 = r5 & r2
            if (r5 != 0) goto L_0x031e
            java.util.List r5 = java.util.Collections.emptyList()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r0 = r5
        L_0x031e:
            goto L_0x0338
        L_0x031f:
            if (r16 != 0) goto L_0x031e
            boolean r4 = r13.writeNull     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r4 != 0) goto L_0x031e
            com.alibaba.fastjson.serializer.SerializerFeature r4 = com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            int r5 = r4.mask     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            boolean r5 = r15.isEnabled((int) r5)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r5 != 0) goto L_0x0338
            int r4 = r4.mask     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r4 = r4 & r2
            if (r4 != 0) goto L_0x0338
            r2 = r34
            goto L_0x0571
        L_0x0338:
            if (r0 == 0) goto L_0x03ee
            boolean r2 = r15.notWriteDefaultValue     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r2 != 0) goto L_0x034e
            int r2 = r11.serialzeFeatures     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            com.alibaba.fastjson.serializer.SerializerFeature r3 = com.alibaba.fastjson.serializer.SerializerFeature.NotWriteDefaultValue     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            int r3 = r3.mask     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r2 = r2 & r3
            if (r2 != 0) goto L_0x034e
            com.alibaba.fastjson.serializer.SerializeBeanInfo r2 = r8.beanInfo     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            int r2 = r2.features     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r2 = r2 & r3
            if (r2 == 0) goto L_0x03ee
        L_0x034e:
            java.lang.Class<?> r2 = r11.fieldClass     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            java.lang.Class r3 = java.lang.Byte.TYPE     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r2 != r3) goto L_0x0365
            boolean r3 = r0 instanceof java.lang.Byte     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r3 == 0) goto L_0x0365
            r3 = r0
            java.lang.Byte r3 = (java.lang.Byte) r3     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            byte r3 = r3.byteValue()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r3 != 0) goto L_0x0365
            r2 = r34
            goto L_0x0571
        L_0x0365:
            java.lang.Class r3 = java.lang.Short.TYPE     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r2 != r3) goto L_0x037a
            boolean r3 = r0 instanceof java.lang.Short     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r3 == 0) goto L_0x037a
            r3 = r0
            java.lang.Short r3 = (java.lang.Short) r3     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            short r3 = r3.shortValue()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r3 != 0) goto L_0x037a
            r2 = r34
            goto L_0x0571
        L_0x037a:
            java.lang.Class r3 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r2 != r3) goto L_0x038f
            boolean r3 = r0 instanceof java.lang.Integer     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r3 == 0) goto L_0x038f
            r3 = r0
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            int r3 = r3.intValue()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r3 != 0) goto L_0x038f
            r2 = r34
            goto L_0x0571
        L_0x038f:
            java.lang.Class r3 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r2 != r3) goto L_0x03a8
            boolean r3 = r0 instanceof java.lang.Long     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r3 == 0) goto L_0x03a8
            r3 = r0
            java.lang.Long r3 = (java.lang.Long) r3     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            long r3 = r3.longValue()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r5 = 0
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 != 0) goto L_0x03a8
            r2 = r34
            goto L_0x0571
        L_0x03a8:
            java.lang.Class r3 = java.lang.Float.TYPE     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r2 != r3) goto L_0x03c0
            boolean r3 = r0 instanceof java.lang.Float     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r3 == 0) goto L_0x03c0
            r3 = r0
            java.lang.Float r3 = (java.lang.Float) r3     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            float r3 = r3.floatValue()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r4 = 0
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 != 0) goto L_0x03c0
            r2 = r34
            goto L_0x0571
        L_0x03c0:
            java.lang.Class r3 = java.lang.Double.TYPE     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r2 != r3) goto L_0x03d9
            boolean r3 = r0 instanceof java.lang.Double     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r3 == 0) goto L_0x03d9
            r3 = r0
            java.lang.Double r3 = (java.lang.Double) r3     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            double r3 = r3.doubleValue()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r5 = 0
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 != 0) goto L_0x03d9
            r2 = r34
            goto L_0x0571
        L_0x03d9:
            java.lang.Class r3 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r2 != r3) goto L_0x03ee
            boolean r3 = r0 instanceof java.lang.Boolean     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r3 == 0) goto L_0x03ee
            r3 = r0
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            boolean r3 = r3.booleanValue()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r3 != 0) goto L_0x03ee
            r2 = r34
            goto L_0x0571
        L_0x03ee:
            if (r22 == 0) goto L_0x0416
            boolean r2 = r11.unwrapped     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r2 == 0) goto L_0x0405
            boolean r2 = r0 instanceof java.util.Map     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r2 == 0) goto L_0x0405
            r2 = r0
            java.util.Map r2 = (java.util.Map) r2     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            int r2 = r2.size()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r2 != 0) goto L_0x0405
            r2 = r34
            goto L_0x0571
        L_0x0405:
            r2 = 44
            r15.write((int) r2)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            com.alibaba.fastjson.serializer.SerializerFeature r3 = com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            boolean r3 = r15.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r3)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r3 == 0) goto L_0x0418
            r39.println()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            goto L_0x0418
        L_0x0416:
            r2 = 44
        L_0x0418:
            r3 = r29
            if (r10 == r3) goto L_0x042d
            if (r16 != 0) goto L_0x0423
            r4 = 1
            r15.writeFieldName(r10, r4)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            goto L_0x0424
        L_0x0423:
            r4 = 1
        L_0x0424:
            r9.write((java.lang.Object) r0)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r5 = r33
            r2 = r34
            goto L_0x04f8
        L_0x042d:
            r4 = 1
            r5 = r33
            if (r5 == r0) goto L_0x043e
            if (r16 != 0) goto L_0x0437
            r13.writePrefix(r9)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
        L_0x0437:
            r9.write((java.lang.Object) r0)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r2 = r34
            goto L_0x04f8
        L_0x043e:
            if (r16 != 0) goto L_0x0483
            java.lang.Class<java.util.Map> r6 = java.util.Map.class
            boolean r6 = r6.isAssignableFrom(r12)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            boolean r7 = r12.isPrimitive()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r7 != 0) goto L_0x0458
            java.lang.String r7 = r12.getName()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            java.lang.String r2 = "java."
            boolean r2 = r7.startsWith(r2)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r2 == 0) goto L_0x045c
        L_0x0458:
            java.lang.Class<java.lang.Object> r2 = java.lang.Object.class
            if (r12 != r2) goto L_0x045e
        L_0x045c:
            r2 = r4
            goto L_0x0460
        L_0x045e:
            r2 = r34
        L_0x0460:
            if (r17 != 0) goto L_0x046e
            boolean r7 = r11.unwrapped     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r7 == 0) goto L_0x046e
            if (r6 != 0) goto L_0x046b
            if (r2 != 0) goto L_0x046b
            goto L_0x046e
        L_0x046b:
            r2 = r34
            goto L_0x0485
        L_0x046e:
            if (r31 == 0) goto L_0x047b
            char[] r7 = r11.name_chars     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            int r4 = r7.length     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r29 = r2
            r2 = r34
            r15.write((char[]) r7, (int) r2, (int) r4)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            goto L_0x0485
        L_0x047b:
            r29 = r2
            r2 = r34
            r13.writePrefix(r9)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            goto L_0x0485
        L_0x0483:
            r2 = r34
        L_0x0485:
            if (r16 != 0) goto L_0x04f5
            com.alibaba.fastjson.annotation.JSONField r4 = r11.getAnnotation()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r12 != r14) goto L_0x04d9
            if (r4 == 0) goto L_0x0497
            java.lang.Class r6 = r4.serializeUsing()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            java.lang.Class<java.lang.Void> r7 = java.lang.Void.class
            if (r6 != r7) goto L_0x04d9
        L_0x0497:
            if (r0 != 0) goto L_0x04ca
            int r6 = r13.features     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            com.alibaba.fastjson.serializer.SerializeBeanInfo r7 = r8.beanInfo     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            com.alibaba.fastjson.annotation.JSONType r7 = r7.jsonType     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r7 == 0) goto L_0x04aa
            com.alibaba.fastjson.serializer.SerializerFeature[] r7 = r7.serialzeFeatures()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            int r7 = com.alibaba.fastjson.serializer.SerializerFeature.of(r7)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r6 = r6 | r7
        L_0x04aa:
            int r7 = r15.features     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            com.alibaba.fastjson.serializer.SerializerFeature r2 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullStringAsEmpty     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            int r2 = r2.mask     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r7 = r7 & r2
            if (r7 == 0) goto L_0x04be
            com.alibaba.fastjson.serializer.SerializerFeature r7 = com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            int r7 = r7.mask     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r7 = r7 & r6
            if (r7 != 0) goto L_0x04be
            r15.writeString((java.lang.String) r1)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            goto L_0x04c8
        L_0x04be:
            r2 = r2 & r6
            if (r2 == 0) goto L_0x04c5
            r15.writeString((java.lang.String) r1)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            goto L_0x04c8
        L_0x04c5:
            r15.writeNull()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
        L_0x04c8:
            r2 = 0
            goto L_0x04f4
        L_0x04ca:
            r1 = r0
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r30 == 0) goto L_0x04d4
            r15.writeStringWithSingleQuote((java.lang.String) r1)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            r2 = 0
            goto L_0x04d8
        L_0x04d4:
            r2 = 0
            r15.writeStringWithDoubleQuote((java.lang.String) r1, (char) r2)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
        L_0x04d8:
            goto L_0x04f4
        L_0x04d9:
            boolean r1 = r11.unwrapped     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r1 == 0) goto L_0x04f1
            boolean r1 = r0 instanceof java.util.Map     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r1 == 0) goto L_0x04f1
            r1 = r0
            java.util.Map r1 = (java.util.Map) r1     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            int r1 = r1.size()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r1 != 0) goto L_0x04f1
            r1 = 0
            r22 = r1
            r1 = r28
            goto L_0x0573
        L_0x04f1:
            r13.writeValue(r9, r0)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
        L_0x04f4:
            goto L_0x04f8
        L_0x04f5:
            r13.writeValue(r9, r0)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
        L_0x04f8:
            r1 = 0
            boolean r4 = r11.unwrapped     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r4 == 0) goto L_0x052f
            boolean r4 = r0 instanceof java.util.Map     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r4 == 0) goto L_0x052f
            r4 = r0
            java.util.Map r4 = (java.util.Map) r4     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            int r6 = r4.size()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r6 != 0) goto L_0x050c
            r1 = 1
            goto L_0x052f
        L_0x050c:
            com.alibaba.fastjson.serializer.SerializerFeature r6 = com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            boolean r6 = r9.isEnabled(r6)     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r6 != 0) goto L_0x052f
            r6 = 0
            java.util.Collection r7 = r4.values()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            java.util.Iterator r7 = r7.iterator()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
        L_0x051d:
            boolean r29 = r7.hasNext()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r29 == 0) goto L_0x052c
            java.lang.Object r29 = r7.next()     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
            if (r29 == 0) goto L_0x052b
            r6 = 1
            goto L_0x052c
        L_0x052b:
            goto L_0x051d
        L_0x052c:
            if (r6 != 0) goto L_0x052f
            r1 = 1
        L_0x052f:
            if (r1 != 0) goto L_0x0571
            r4 = 1
            r22 = r4
            r1 = r28
            goto L_0x0573
        L_0x0537:
            r32 = r3
            r35 = r5
            r36 = r6
            r37 = r7
            r12 = r27
            r19 = r29
            r3 = r2
            r27 = r11
            r11 = r4
            throw r0     // Catch:{ Exception -> 0x0555, all -> 0x0548 }
        L_0x0548:
            r0 = move-exception
            r2 = r40
            r4 = r41
            r7 = r27
            r1 = r28
            r3 = r37
            goto L_0x06f4
        L_0x0555:
            r0 = move-exception
            r2 = r40
            r4 = r27
            r1 = r28
            r3 = r37
            goto L_0x0641
        L_0x0560:
            r32 = r3
            r35 = r5
            r36 = r6
            r37 = r7
            r12 = r27
            r19 = r29
            r3 = r2
            r27 = r11
            r2 = 0
            r11 = r4
        L_0x0571:
            r1 = r28
        L_0x0573:
            int r0 = r19 + 1
            r10 = r40
            r12 = r42
            r2 = r0
            r13 = r24
            r11 = r27
            r3 = r32
            r5 = r35
            r6 = r36
            r7 = r37
            r4 = 44
            goto L_0x00f4
        L_0x058a:
            r0 = move-exception
            r2 = r40
            r4 = r41
            r3 = r7
            r7 = r11
            r1 = r28
            goto L_0x06f4
        L_0x0595:
            r0 = move-exception
            r2 = r40
            r3 = r7
            r4 = r11
            r1 = r28
            goto L_0x0641
        L_0x059e:
            r0 = move-exception
            r28 = r1
            r2 = r40
            r4 = r41
            r3 = r7
            r7 = r11
            goto L_0x06f4
        L_0x05a9:
            r0 = move-exception
            r28 = r1
            r2 = r40
            r3 = r7
            r4 = r11
            goto L_0x0641
        L_0x05b2:
            r28 = r1
            r19 = r2
            r32 = r3
            r35 = r5
            r36 = r6
            r37 = r7
            r27 = r11
            r24 = r13
            r2 = 0
            if (r22 == 0) goto L_0x05c8
            r4 = 44
            goto L_0x05c9
        L_0x05c8:
            r4 = r2
        L_0x05c9:
            r2 = r40
            r8.writeAfter(r9, r2, r4)     // Catch:{ Exception -> 0x061c, all -> 0x0611 }
            r3 = r37
            int r0 = r3.length     // Catch:{ Exception -> 0x060b, all -> 0x0602 }
            if (r0 <= 0) goto L_0x05f2
            com.alibaba.fastjson.serializer.SerializerFeature r0 = com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat     // Catch:{ Exception -> 0x05eb, all -> 0x05e2 }
            boolean r0 = r15.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r0)     // Catch:{ Exception -> 0x05eb, all -> 0x05e2 }
            if (r0 == 0) goto L_0x05f2
            r39.decrementIdent()     // Catch:{ Exception -> 0x05eb, all -> 0x05e2 }
            r39.println()     // Catch:{ Exception -> 0x05eb, all -> 0x05e2 }
            goto L_0x05f2
        L_0x05e2:
            r0 = move-exception
            r4 = r41
            r7 = r27
            r1 = r28
            goto L_0x06f4
        L_0x05eb:
            r0 = move-exception
            r4 = r27
            r1 = r28
            goto L_0x0641
        L_0x05f2:
            if (r44 != 0) goto L_0x05fa
            r1 = r35
            r15.append((char) r1)     // Catch:{ Exception -> 0x05eb, all -> 0x05e2 }
            goto L_0x05fc
        L_0x05fa:
            r1 = r35
        L_0x05fc:
            r4 = r27
            r9.context = r4
            return
        L_0x0602:
            r0 = move-exception
            r4 = r41
            r7 = r27
            r1 = r28
            goto L_0x06f4
        L_0x060b:
            r0 = move-exception
            r4 = r27
            r1 = r28
            goto L_0x0641
        L_0x0611:
            r0 = move-exception
            r3 = r37
            r4 = r41
            r7 = r27
            r1 = r28
            goto L_0x06f4
        L_0x061c:
            r0 = move-exception
            r4 = r27
            r3 = r37
            r1 = r28
            goto L_0x0641
        L_0x0624:
            r0 = move-exception
            r28 = r1
            r3 = r7
            r2 = r10
            r4 = r41
            r7 = r11
            goto L_0x06f4
        L_0x062e:
            r0 = move-exception
            r28 = r1
            r3 = r7
            r2 = r10
            r4 = r11
            goto L_0x0641
        L_0x0635:
            r0 = move-exception
            r3 = r7
            r2 = r10
            r4 = r41
            r7 = r11
            goto L_0x06f4
        L_0x063d:
            r0 = move-exception
            r3 = r7
            r2 = r10
            r4 = r11
        L_0x0641:
            java.lang.String r5 = "write javaBean error, fastjson version 1.2.75"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x06f0 }
            r6.<init>()     // Catch:{ all -> 0x06f0 }
            r6.append(r5)     // Catch:{ all -> 0x06f0 }
            java.lang.String r7 = ", class "
            r6.append(r7)     // Catch:{ all -> 0x06f0 }
            java.lang.Class r7 = r40.getClass()     // Catch:{ all -> 0x06f0 }
            java.lang.String r7 = r7.getName()     // Catch:{ all -> 0x06f0 }
            r6.append(r7)     // Catch:{ all -> 0x06f0 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x06f0 }
            r5 = r6
            java.lang.String r6 = ", fieldName : "
            r7 = r4
            r4 = r41
            if (r4 == 0) goto L_0x0680
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x067d }
            r10.<init>()     // Catch:{ all -> 0x067d }
            r10.append(r5)     // Catch:{ all -> 0x067d }
            r10.append(r6)     // Catch:{ all -> 0x067d }
            r10.append(r4)     // Catch:{ all -> 0x067d }
            java.lang.String r6 = r10.toString()     // Catch:{ all -> 0x067d }
            r5 = r6
            goto L_0x06be
        L_0x067d:
            r0 = move-exception
            goto L_0x06f4
        L_0x0680:
            if (r1 == 0) goto L_0x06be
            com.alibaba.fastjson.util.FieldInfo r10 = r1.fieldInfo     // Catch:{ all -> 0x067d }
            if (r10 == 0) goto L_0x06be
            java.lang.reflect.Method r11 = r10.method     // Catch:{ all -> 0x067d }
            if (r11 == 0) goto L_0x06a7
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x067d }
            r6.<init>()     // Catch:{ all -> 0x067d }
            r6.append(r5)     // Catch:{ all -> 0x067d }
            java.lang.String r11 = ", method : "
            r6.append(r11)     // Catch:{ all -> 0x067d }
            java.lang.reflect.Method r11 = r10.method     // Catch:{ all -> 0x067d }
            java.lang.String r11 = r11.getName()     // Catch:{ all -> 0x067d }
            r6.append(r11)     // Catch:{ all -> 0x067d }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x067d }
            r5 = r6
            goto L_0x06be
        L_0x06a7:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x067d }
            r11.<init>()     // Catch:{ all -> 0x067d }
            r11.append(r5)     // Catch:{ all -> 0x067d }
            r11.append(r6)     // Catch:{ all -> 0x067d }
            com.alibaba.fastjson.util.FieldInfo r6 = r1.fieldInfo     // Catch:{ all -> 0x067d }
            java.lang.String r6 = r6.name     // Catch:{ all -> 0x067d }
            r11.append(r6)     // Catch:{ all -> 0x067d }
            java.lang.String r6 = r11.toString()     // Catch:{ all -> 0x067d }
            r5 = r6
        L_0x06be:
            java.lang.String r6 = r0.getMessage()     // Catch:{ all -> 0x067d }
            if (r6 == 0) goto L_0x06dd
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x067d }
            r6.<init>()     // Catch:{ all -> 0x067d }
            r6.append(r5)     // Catch:{ all -> 0x067d }
            java.lang.String r10 = ", "
            r6.append(r10)     // Catch:{ all -> 0x067d }
            java.lang.String r10 = r0.getMessage()     // Catch:{ all -> 0x067d }
            r6.append(r10)     // Catch:{ all -> 0x067d }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x067d }
            r5 = r6
        L_0x06dd:
            r6 = 0
            boolean r10 = r0 instanceof java.lang.reflect.InvocationTargetException     // Catch:{ all -> 0x067d }
            if (r10 == 0) goto L_0x06e7
            java.lang.Throwable r10 = r0.getCause()     // Catch:{ all -> 0x067d }
            r6 = r10
        L_0x06e7:
            if (r6 != 0) goto L_0x06ea
            r6 = r0
        L_0x06ea:
            com.alibaba.fastjson.JSONException r10 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x067d }
            r10.<init>(r5, r6)     // Catch:{ all -> 0x067d }
            throw r10     // Catch:{ all -> 0x067d }
        L_0x06f0:
            r0 = move-exception
            r7 = r4
            r4 = r41
        L_0x06f4:
            r9.context = r7
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.JavaBeanSerializer.write(com.alibaba.fastjson.serializer.JSONSerializer, java.lang.Object, java.lang.Object, java.lang.reflect.Type, int, boolean):void");
    }

    /* access modifiers changed from: protected */
    public void writeClassName(JSONSerializer serializer, String typeKey, Object object) {
        if (typeKey == null) {
            typeKey = serializer.config.typeKey;
        }
        serializer.out.writeFieldName(typeKey, false);
        String typeName = this.beanInfo.typeName;
        if (typeName == null) {
            Class cls = object.getClass();
            if (TypeUtils.isProxy(cls)) {
                cls = cls.getSuperclass();
            }
            typeName = cls.getName();
        }
        serializer.write(typeName);
    }

    public boolean writeReference(JSONSerializer serializer, Object object, int fieldFeatures) {
        IdentityHashMap<Object, SerialContext> identityHashMap;
        SerialContext context = serializer.context;
        int mask = SerializerFeature.DisableCircularReferenceDetect.mask;
        if (context == null || (context.features & mask) != 0 || (fieldFeatures & mask) != 0 || (identityHashMap = serializer.references) == null || !identityHashMap.containsKey(object)) {
            return false;
        }
        serializer.writeReference(object);
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isWriteAsArray(JSONSerializer serializer) {
        return isWriteAsArray(serializer, 0);
    }

    /* access modifiers changed from: protected */
    public boolean isWriteAsArray(JSONSerializer serializer, int fieldFeatrues) {
        int mask = SerializerFeature.BeanToArray.mask;
        return ((this.beanInfo.features & mask) == 0 && !serializer.out.beanToArray && (fieldFeatrues & mask) == 0) ? false : true;
    }

    public Object getFieldValue(Object object, String key) {
        FieldSerializer fieldDeser = getFieldSerializer(key);
        if (fieldDeser != null) {
            try {
                return fieldDeser.getPropertyValue(object);
            } catch (InvocationTargetException ex) {
                throw new JSONException("getFieldValue error." + key, ex);
            } catch (IllegalAccessException ex2) {
                throw new JSONException("getFieldValue error." + key, ex2);
            }
        } else {
            throw new JSONException("field not found. " + key);
        }
    }

    public Object getFieldValue(Object object, String key, long keyHash, boolean throwFieldNotFoundException) {
        FieldSerializer fieldDeser = getFieldSerializer(keyHash);
        if (fieldDeser != null) {
            try {
                return fieldDeser.getPropertyValue(object);
            } catch (InvocationTargetException ex) {
                throw new JSONException("getFieldValue error." + key, ex);
            } catch (IllegalAccessException ex2) {
                throw new JSONException("getFieldValue error." + key, ex2);
            }
        } else if (!throwFieldNotFoundException) {
            return null;
        } else {
            throw new JSONException("field not found. " + key);
        }
    }

    public FieldSerializer getFieldSerializer(String key) {
        if (key == null) {
            return null;
        }
        int low = 0;
        int high = this.sortedGetters.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int cmp = this.sortedGetters[mid].fieldInfo.name.compareTo(key);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp <= 0) {
                return this.sortedGetters[mid];
            } else {
                high = mid - 1;
            }
        }
        return null;
    }

    public FieldSerializer getFieldSerializer(long hash) {
        int p_t;
        PropertyNamingStrategy[] namingStrategies = null;
        if (this.hashArray == null) {
            namingStrategies = PropertyNamingStrategy.values();
            long[] hashArray2 = new long[(this.sortedGetters.length * namingStrategies.length)];
            int index = 0;
            int i = 0;
            while (true) {
                FieldSerializer[] fieldSerializerArr = this.sortedGetters;
                if (i >= fieldSerializerArr.length) {
                    break;
                }
                String name = fieldSerializerArr[i].fieldInfo.name;
                int index2 = index + 1;
                hashArray2[index] = TypeUtils.fnv1a_64(name);
                for (PropertyNamingStrategy translate : namingStrategies) {
                    String name_t = translate.translate(name);
                    if (!name.equals(name_t)) {
                        hashArray2[index2] = TypeUtils.fnv1a_64(name_t);
                        index2++;
                    }
                }
                i++;
                index = index2;
            }
            Arrays.sort(hashArray2, 0, index);
            this.hashArray = new long[index];
            System.arraycopy(hashArray2, 0, this.hashArray, 0, index);
        }
        int pos = Arrays.binarySearch(this.hashArray, hash);
        if (pos < 0) {
            return null;
        }
        if (this.hashArrayMapping == null) {
            if (namingStrategies == null) {
                namingStrategies = PropertyNamingStrategy.values();
            }
            short[] mapping = new short[this.hashArray.length];
            Arrays.fill(mapping, -1);
            int i2 = 0;
            while (true) {
                FieldSerializer[] fieldSerializerArr2 = this.sortedGetters;
                if (i2 >= fieldSerializerArr2.length) {
                    break;
                }
                String name2 = fieldSerializerArr2[i2].fieldInfo.name;
                int p = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(name2));
                if (p >= 0) {
                    mapping[p] = (short) i2;
                }
                for (PropertyNamingStrategy translate2 : namingStrategies) {
                    String name_t2 = translate2.translate(name2);
                    if (!name2.equals(name_t2) && (p_t = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(name_t2))) >= 0) {
                        mapping[p_t] = (short) i2;
                    }
                }
                i2++;
            }
            this.hashArrayMapping = mapping;
        }
        short getterIndex = this.hashArrayMapping[pos];
        if (getterIndex != -1) {
            return this.sortedGetters[getterIndex];
        }
        return null;
    }

    public List<Object> getFieldValues(Object object) {
        List<Object> fieldValues = new ArrayList<>(this.sortedGetters.length);
        for (FieldSerializer getter : this.sortedGetters) {
            fieldValues.add(getter.getPropertyValue(object));
        }
        return fieldValues;
    }

    public List<Object> getObjectFieldValues(Object object) {
        List<Object> fieldValues = new ArrayList<>(this.sortedGetters.length);
        for (FieldSerializer getter : this.sortedGetters) {
            Class fieldClass = getter.fieldInfo.fieldClass;
            if (!fieldClass.isPrimitive() && !fieldClass.getName().startsWith("java.lang.")) {
                fieldValues.add(getter.getPropertyValue(object));
            }
        }
        return fieldValues;
    }

    public int getSize(Object object) {
        int size = 0;
        for (FieldSerializer getter : this.sortedGetters) {
            if (getter.getPropertyValueDirect(object) != null) {
                size++;
            }
        }
        return size;
    }

    public Set<String> getFieldNames(Object object) {
        Set<String> fieldNames = new HashSet<>();
        for (FieldSerializer getter : this.sortedGetters) {
            if (getter.getPropertyValueDirect(object) != null) {
                fieldNames.add(getter.fieldInfo.name);
            }
        }
        return fieldNames;
    }

    public Map<String, Object> getFieldValuesMap(Object object) {
        Map<String, Object> map = new LinkedHashMap<>(this.sortedGetters.length);
        for (FieldSerializer getter : this.sortedGetters) {
            boolean skipTransient = SerializerFeature.isEnabled(getter.features, SerializerFeature.SkipTransientField);
            FieldInfo fieldInfo = getter.fieldInfo;
            if (!skipTransient || fieldInfo == null || !fieldInfo.fieldTransient) {
                FieldInfo fieldInfo2 = getter.fieldInfo;
                if (fieldInfo2.unwrapped) {
                    Object map1 = JSON.toJSON(getter.getPropertyValue(object));
                    if (map1 instanceof Map) {
                        map.putAll((Map) map1);
                    } else {
                        map.put(getter.fieldInfo.name, getter.getPropertyValue(object));
                    }
                } else {
                    map.put(fieldInfo2.name, getter.getPropertyValue(object));
                }
            }
        }
        return map;
    }

    /* access modifiers changed from: protected */
    public BeanContext getBeanContext(int orinal) {
        return this.sortedGetters[orinal].fieldContext;
    }

    /* access modifiers changed from: protected */
    public Type getFieldType(int ordinal) {
        return this.sortedGetters[ordinal].fieldInfo.fieldType;
    }

    /* access modifiers changed from: protected */
    public char writeBefore(JSONSerializer jsonBeanDeser, Object object, char seperator) {
        List<BeforeFilter> list = jsonBeanDeser.beforeFilters;
        if (list != null) {
            for (BeforeFilter beforeFilter : list) {
                seperator = beforeFilter.writeBefore(jsonBeanDeser, object, seperator);
            }
        }
        List<BeforeFilter> list2 = this.beforeFilters;
        if (list2 != null) {
            for (BeforeFilter beforeFilter2 : list2) {
                seperator = beforeFilter2.writeBefore(jsonBeanDeser, object, seperator);
            }
        }
        return seperator;
    }

    /* access modifiers changed from: protected */
    public char writeAfter(JSONSerializer jsonBeanDeser, Object object, char seperator) {
        List<AfterFilter> list = jsonBeanDeser.afterFilters;
        if (list != null) {
            for (AfterFilter afterFilter : list) {
                seperator = afterFilter.writeAfter(jsonBeanDeser, object, seperator);
            }
        }
        List<AfterFilter> list2 = this.afterFilters;
        if (list2 != null) {
            for (AfterFilter afterFilter2 : list2) {
                seperator = afterFilter2.writeAfter(jsonBeanDeser, object, seperator);
            }
        }
        return seperator;
    }

    /* access modifiers changed from: protected */
    public boolean applyLabel(JSONSerializer jsonBeanDeser, String label) {
        List<LabelFilter> list = jsonBeanDeser.labelFilters;
        if (list != null) {
            for (LabelFilter propertyFilter : list) {
                if (!propertyFilter.apply(label)) {
                    return false;
                }
            }
        }
        List<LabelFilter> list2 = this.labelFilters;
        if (list2 == null) {
            return true;
        }
        for (LabelFilter propertyFilter2 : list2) {
            if (!propertyFilter2.apply(label)) {
                return false;
            }
        }
        return true;
    }
}
