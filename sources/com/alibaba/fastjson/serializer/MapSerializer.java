package com.alibaba.fastjson.serializer;

import java.lang.reflect.Type;

public class MapSerializer extends SerializeFilterable implements ObjectSerializer {
    private static final int NON_STRINGKEY_AS_STRING = SerializerFeature.of(new SerializerFeature[]{SerializerFeature.BrowserCompatible, SerializerFeature.WriteNonStringKeyAsString, SerializerFeature.BrowserSecure});
    public static MapSerializer instance = new MapSerializer();

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        write(serializer, object, fieldName, fieldType, features, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:170:0x0236 A[SYNTHETIC, Splitter:B:170:0x0236] */
    /* JADX WARNING: Removed duplicated region for block: B:183:0x0253 A[SYNTHETIC, Splitter:B:183:0x0253] */
    /* JADX WARNING: Removed duplicated region for block: B:188:0x0276 A[Catch:{ all -> 0x037c }] */
    /* JADX WARNING: Removed duplicated region for block: B:189:0x027f A[Catch:{ all -> 0x037c }] */
    /* JADX WARNING: Removed duplicated region for block: B:197:0x02ba A[Catch:{ all -> 0x037c }] */
    /* JADX WARNING: Removed duplicated region for block: B:204:0x02d2 A[Catch:{ all -> 0x037c }] */
    /* JADX WARNING: Removed duplicated region for block: B:218:0x0300 A[Catch:{ all -> 0x037c }] */
    /* JADX WARNING: Removed duplicated region for block: B:219:0x030e A[Catch:{ all -> 0x037c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void write(com.alibaba.fastjson.serializer.JSONSerializer r27, java.lang.Object r28, java.lang.Object r29, java.lang.reflect.Type r30, int r31, boolean r32) {
        /*
            r26 = this;
            r8 = r26
            r9 = r27
            r10 = r28
            r11 = r30
            r12 = r31
            com.alibaba.fastjson.serializer.SerializeWriter r13 = r9.out
            if (r10 != 0) goto L_0x0012
            r13.writeNull()
            return
        L_0x0012:
            r0 = r10
            java.util.Map r0 = (java.util.Map) r0
            com.alibaba.fastjson.serializer.SerializerFeature r1 = com.alibaba.fastjson.serializer.SerializerFeature.MapSortField
            int r14 = r1.mask
            int r1 = r13.features
            r1 = r1 & r14
            if (r1 != 0) goto L_0x0025
            r1 = r12 & r14
            if (r1 == 0) goto L_0x0023
            goto L_0x0025
        L_0x0023:
            r15 = r0
            goto L_0x0045
        L_0x0025:
            boolean r1 = r0 instanceof com.alibaba.fastjson.JSONObject
            if (r1 == 0) goto L_0x0032
            r1 = r0
            com.alibaba.fastjson.JSONObject r1 = (com.alibaba.fastjson.JSONObject) r1
            java.util.Map r0 = r1.getInnerMap()
            r1 = r0
            goto L_0x0033
        L_0x0032:
            r1 = r0
        L_0x0033:
            boolean r0 = r1 instanceof java.util.SortedMap
            if (r0 != 0) goto L_0x0044
            boolean r0 = r1 instanceof java.util.LinkedHashMap
            if (r0 != 0) goto L_0x0044
            java.util.TreeMap r0 = new java.util.TreeMap     // Catch:{ Exception -> 0x0043 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x0043 }
            r15 = r0
            goto L_0x0045
        L_0x0043:
            r0 = move-exception
        L_0x0044:
            r15 = r1
        L_0x0045:
            boolean r0 = r27.containsReference(r28)
            if (r0 == 0) goto L_0x004f
            r27.writeReference(r28)
            return
        L_0x004f:
            com.alibaba.fastjson.serializer.SerialContext r7 = r9.context
            r0 = 0
            r6 = r29
            r9.setContext(r7, r10, r6, r0)
            if (r32 != 0) goto L_0x0063
            r1 = 123(0x7b, float:1.72E-43)
            r13.write((int) r1)     // Catch:{ all -> 0x005f }
            goto L_0x0063
        L_0x005f:
            r0 = move-exception
            r8 = r7
            goto L_0x03a1
        L_0x0063:
            r27.incrementIndent()     // Catch:{ all -> 0x039f }
            r1 = 0
            r2 = 0
            r3 = 1
            com.alibaba.fastjson.serializer.SerializerFeature r4 = com.alibaba.fastjson.serializer.SerializerFeature.WriteClassName     // Catch:{ all -> 0x039f }
            boolean r4 = r13.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r4)     // Catch:{ all -> 0x039f }
            if (r4 == 0) goto L_0x00a8
            com.alibaba.fastjson.serializer.SerializeConfig r4 = r9.config     // Catch:{ all -> 0x005f }
            java.lang.String r4 = r4.typeKey     // Catch:{ all -> 0x005f }
            java.lang.Class r16 = r15.getClass()     // Catch:{ all -> 0x005f }
            r17 = r16
            java.lang.Class<com.alibaba.fastjson.JSONObject> r0 = com.alibaba.fastjson.JSONObject.class
            r5 = r17
            if (r5 == r0) goto L_0x0089
            java.lang.Class<java.util.HashMap> r0 = java.util.HashMap.class
            if (r5 == r0) goto L_0x0089
            java.lang.Class<java.util.LinkedHashMap> r0 = java.util.LinkedHashMap.class
            if (r5 != r0) goto L_0x0091
        L_0x0089:
            boolean r0 = r15.containsKey(r4)     // Catch:{ all -> 0x005f }
            if (r0 == 0) goto L_0x0091
            r0 = 1
            goto L_0x0092
        L_0x0091:
            r0 = 0
        L_0x0092:
            if (r0 != 0) goto L_0x00a6
            r13.writeFieldName(r4)     // Catch:{ all -> 0x005f }
            java.lang.Class r17 = r28.getClass()     // Catch:{ all -> 0x005f }
            r19 = r0
            java.lang.String r0 = r17.getName()     // Catch:{ all -> 0x005f }
            r13.writeString((java.lang.String) r0)     // Catch:{ all -> 0x005f }
            r3 = 0
            goto L_0x00a8
        L_0x00a6:
            r19 = r0
        L_0x00a8:
            java.util.Set r0 = r15.entrySet()     // Catch:{ all -> 0x039f }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x039f }
            r4 = r1
            r17 = r2
            r19 = r3
        L_0x00b5:
            boolean r1 = r0.hasNext()     // Catch:{ all -> 0x039f }
            if (r1 == 0) goto L_0x037e
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x039f }
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch:{ all -> 0x039f }
            r20 = r1
            java.lang.Object r1 = r20.getValue()     // Catch:{ all -> 0x039f }
            r3 = r1
            java.lang.Object r1 = r20.getKey()     // Catch:{ all -> 0x039f }
            java.util.List<com.alibaba.fastjson.serializer.PropertyPreFilter> r2 = r9.propertyPreFilters     // Catch:{ all -> 0x039f }
            if (r2 == 0) goto L_0x010c
            int r5 = r2.size()     // Catch:{ all -> 0x005f }
            if (r5 <= 0) goto L_0x010c
            if (r1 == 0) goto L_0x00fc
            boolean r5 = r1 instanceof java.lang.String     // Catch:{ all -> 0x005f }
            if (r5 == 0) goto L_0x00dd
            goto L_0x00fc
        L_0x00dd:
            java.lang.Class r5 = r1.getClass()     // Catch:{ all -> 0x005f }
            boolean r5 = r5.isPrimitive()     // Catch:{ all -> 0x005f }
            if (r5 != 0) goto L_0x00eb
            boolean r5 = r1 instanceof java.lang.Number     // Catch:{ all -> 0x005f }
            if (r5 == 0) goto L_0x010c
        L_0x00eb:
            java.lang.String r5 = com.alibaba.fastjson.JSON.toJSONString(r1)     // Catch:{ all -> 0x005f }
            boolean r21 = r8.applyName(r9, r10, r5)     // Catch:{ all -> 0x005f }
            if (r21 != 0) goto L_0x010c
            r23 = r0
            r25 = r4
            r8 = r7
            goto L_0x02a9
        L_0x00fc:
            r5 = r1
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x005f }
            boolean r5 = r8.applyName(r9, r10, r5)     // Catch:{ all -> 0x005f }
            if (r5 != 0) goto L_0x010c
            r23 = r0
            r25 = r4
            r8 = r7
            goto L_0x02a9
        L_0x010c:
            java.util.List<com.alibaba.fastjson.serializer.PropertyPreFilter> r2 = r8.propertyPreFilters     // Catch:{ all -> 0x039f }
            if (r2 == 0) goto L_0x014c
            int r5 = r2.size()     // Catch:{ all -> 0x005f }
            if (r5 <= 0) goto L_0x014c
            if (r1 == 0) goto L_0x013c
            boolean r5 = r1 instanceof java.lang.String     // Catch:{ all -> 0x005f }
            if (r5 == 0) goto L_0x011d
            goto L_0x013c
        L_0x011d:
            java.lang.Class r5 = r1.getClass()     // Catch:{ all -> 0x005f }
            boolean r5 = r5.isPrimitive()     // Catch:{ all -> 0x005f }
            if (r5 != 0) goto L_0x012b
            boolean r5 = r1 instanceof java.lang.Number     // Catch:{ all -> 0x005f }
            if (r5 == 0) goto L_0x014c
        L_0x012b:
            java.lang.String r5 = com.alibaba.fastjson.JSON.toJSONString(r1)     // Catch:{ all -> 0x005f }
            boolean r21 = r8.applyName(r9, r10, r5)     // Catch:{ all -> 0x005f }
            if (r21 != 0) goto L_0x014c
            r23 = r0
            r25 = r4
            r8 = r7
            goto L_0x02a9
        L_0x013c:
            r5 = r1
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x005f }
            boolean r5 = r8.applyName(r9, r10, r5)     // Catch:{ all -> 0x005f }
            if (r5 != 0) goto L_0x014c
            r23 = r0
            r25 = r4
            r8 = r7
            goto L_0x02a9
        L_0x014c:
            java.util.List<com.alibaba.fastjson.serializer.PropertyFilter> r2 = r9.propertyFilters     // Catch:{ all -> 0x039f }
            if (r2 == 0) goto L_0x018c
            int r5 = r2.size()     // Catch:{ all -> 0x005f }
            if (r5 <= 0) goto L_0x018c
            if (r1 == 0) goto L_0x017c
            boolean r5 = r1 instanceof java.lang.String     // Catch:{ all -> 0x005f }
            if (r5 == 0) goto L_0x015d
            goto L_0x017c
        L_0x015d:
            java.lang.Class r5 = r1.getClass()     // Catch:{ all -> 0x005f }
            boolean r5 = r5.isPrimitive()     // Catch:{ all -> 0x005f }
            if (r5 != 0) goto L_0x016b
            boolean r5 = r1 instanceof java.lang.Number     // Catch:{ all -> 0x005f }
            if (r5 == 0) goto L_0x018c
        L_0x016b:
            java.lang.String r5 = com.alibaba.fastjson.JSON.toJSONString(r1)     // Catch:{ all -> 0x005f }
            boolean r21 = r8.apply(r9, r10, r5, r3)     // Catch:{ all -> 0x005f }
            if (r21 != 0) goto L_0x018c
            r23 = r0
            r25 = r4
            r8 = r7
            goto L_0x02a9
        L_0x017c:
            r5 = r1
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x005f }
            boolean r5 = r8.apply(r9, r10, r5, r3)     // Catch:{ all -> 0x005f }
            if (r5 != 0) goto L_0x018c
            r23 = r0
            r25 = r4
            r8 = r7
            goto L_0x02a9
        L_0x018c:
            java.util.List<com.alibaba.fastjson.serializer.PropertyFilter> r2 = r8.propertyFilters     // Catch:{ all -> 0x039f }
            if (r2 == 0) goto L_0x01cc
            int r5 = r2.size()     // Catch:{ all -> 0x005f }
            if (r5 <= 0) goto L_0x01cc
            if (r1 == 0) goto L_0x01bc
            boolean r5 = r1 instanceof java.lang.String     // Catch:{ all -> 0x005f }
            if (r5 == 0) goto L_0x019d
            goto L_0x01bc
        L_0x019d:
            java.lang.Class r5 = r1.getClass()     // Catch:{ all -> 0x005f }
            boolean r5 = r5.isPrimitive()     // Catch:{ all -> 0x005f }
            if (r5 != 0) goto L_0x01ab
            boolean r5 = r1 instanceof java.lang.Number     // Catch:{ all -> 0x005f }
            if (r5 == 0) goto L_0x01cc
        L_0x01ab:
            java.lang.String r5 = com.alibaba.fastjson.JSON.toJSONString(r1)     // Catch:{ all -> 0x005f }
            boolean r21 = r8.apply(r9, r10, r5, r3)     // Catch:{ all -> 0x005f }
            if (r21 != 0) goto L_0x01cc
            r23 = r0
            r25 = r4
            r8 = r7
            goto L_0x02a9
        L_0x01bc:
            r5 = r1
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x005f }
            boolean r5 = r8.apply(r9, r10, r5, r3)     // Catch:{ all -> 0x005f }
            if (r5 != 0) goto L_0x01cc
            r23 = r0
            r25 = r4
            r8 = r7
            goto L_0x02a9
        L_0x01cc:
            java.util.List<com.alibaba.fastjson.serializer.NameFilter> r2 = r9.nameFilters     // Catch:{ all -> 0x039f }
            if (r2 == 0) goto L_0x01fe
            int r5 = r2.size()     // Catch:{ all -> 0x005f }
            if (r5 <= 0) goto L_0x01fe
            if (r1 == 0) goto L_0x01f6
            boolean r5 = r1 instanceof java.lang.String     // Catch:{ all -> 0x005f }
            if (r5 == 0) goto L_0x01dd
            goto L_0x01f6
        L_0x01dd:
            java.lang.Class r5 = r1.getClass()     // Catch:{ all -> 0x005f }
            boolean r5 = r5.isPrimitive()     // Catch:{ all -> 0x005f }
            if (r5 != 0) goto L_0x01eb
            boolean r5 = r1 instanceof java.lang.Number     // Catch:{ all -> 0x005f }
            if (r5 == 0) goto L_0x01fe
        L_0x01eb:
            java.lang.String r5 = com.alibaba.fastjson.JSON.toJSONString(r1)     // Catch:{ all -> 0x005f }
            java.lang.String r21 = r8.processKey(r9, r10, r5, r3)     // Catch:{ all -> 0x005f }
            r1 = r21
            goto L_0x01fe
        L_0x01f6:
            r5 = r1
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x005f }
            java.lang.String r5 = r8.processKey(r9, r10, r5, r3)     // Catch:{ all -> 0x005f }
            r1 = r5
        L_0x01fe:
            java.util.List<com.alibaba.fastjson.serializer.NameFilter> r2 = r8.nameFilters     // Catch:{ all -> 0x039f }
            if (r2 == 0) goto L_0x0233
            int r5 = r2.size()     // Catch:{ all -> 0x005f }
            if (r5 <= 0) goto L_0x0233
            if (r1 == 0) goto L_0x0229
            boolean r5 = r1 instanceof java.lang.String     // Catch:{ all -> 0x005f }
            if (r5 == 0) goto L_0x020f
            goto L_0x0229
        L_0x020f:
            java.lang.Class r5 = r1.getClass()     // Catch:{ all -> 0x005f }
            boolean r5 = r5.isPrimitive()     // Catch:{ all -> 0x005f }
            if (r5 != 0) goto L_0x021d
            boolean r5 = r1 instanceof java.lang.Number     // Catch:{ all -> 0x005f }
            if (r5 == 0) goto L_0x0233
        L_0x021d:
            java.lang.String r5 = com.alibaba.fastjson.JSON.toJSONString(r1)     // Catch:{ all -> 0x005f }
            java.lang.String r21 = r8.processKey(r9, r10, r5, r3)     // Catch:{ all -> 0x005f }
            r1 = r21
            r2 = r1
            goto L_0x0234
        L_0x0229:
            r5 = r1
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x005f }
            java.lang.String r5 = r8.processKey(r9, r10, r5, r3)     // Catch:{ all -> 0x005f }
            r1 = r5
            r2 = r1
            goto L_0x0234
        L_0x0233:
            r2 = r1
        L_0x0234:
            if (r2 == 0) goto L_0x027f
            boolean r1 = r2 instanceof java.lang.String     // Catch:{ all -> 0x039f }
            if (r1 == 0) goto L_0x0243
            r23 = r0
            r0 = r2
            r24 = r3
            r25 = r4
            r8 = r7
            goto L_0x0287
        L_0x0243:
            boolean r1 = r2 instanceof java.util.Map     // Catch:{ all -> 0x039f }
            if (r1 != 0) goto L_0x024e
            boolean r1 = r2 instanceof java.util.Collection     // Catch:{ all -> 0x005f }
            if (r1 == 0) goto L_0x024c
            goto L_0x024e
        L_0x024c:
            r1 = 0
            goto L_0x024f
        L_0x024e:
            r1 = 1
        L_0x024f:
            r21 = r1
            if (r21 != 0) goto L_0x0276
            java.lang.String r5 = com.alibaba.fastjson.JSON.toJSONString(r2)     // Catch:{ all -> 0x039f }
            r1 = 1
            r18 = 0
            r1 = r26
            r23 = r0
            r0 = r2
            r2 = r27
            r24 = r3
            r3 = r18
            r25 = r4
            r4 = r28
            r6 = r24
            r8 = r7
            r7 = r31
            java.lang.Object r1 = r1.processValue(r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x037c }
            r3 = r1
            r24 = r3
            goto L_0x029c
        L_0x0276:
            r23 = r0
            r0 = r2
            r24 = r3
            r25 = r4
            r8 = r7
            goto L_0x029c
        L_0x027f:
            r23 = r0
            r0 = r2
            r24 = r3
            r25 = r4
            r8 = r7
        L_0x0287:
            r3 = 0
            r5 = r0
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x037c }
            r1 = r26
            r2 = r27
            r4 = r28
            r6 = r24
            r7 = r31
            java.lang.Object r1 = r1.processValue(r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x037c }
            r3 = r1
            r24 = r3
        L_0x029c:
            if (r24 != 0) goto L_0x02b4
            int r1 = r13.features     // Catch:{ all -> 0x037c }
            com.alibaba.fastjson.serializer.SerializerFeature r2 = com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue     // Catch:{ all -> 0x037c }
            boolean r1 = com.alibaba.fastjson.serializer.SerializerFeature.isEnabled(r1, r12, r2)     // Catch:{ all -> 0x037c }
            if (r1 != 0) goto L_0x02b4
        L_0x02a9:
            r6 = r29
            r7 = r8
            r0 = r23
            r4 = r25
            r8 = r26
            goto L_0x00b5
        L_0x02b4:
            boolean r1 = r0 instanceof java.lang.String     // Catch:{ all -> 0x037c }
            r2 = 44
            if (r1 == 0) goto L_0x02d2
            r1 = r0
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x037c }
            if (r19 != 0) goto L_0x02c2
            r13.write((int) r2)     // Catch:{ all -> 0x037c }
        L_0x02c2:
            com.alibaba.fastjson.serializer.SerializerFeature r2 = com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat     // Catch:{ all -> 0x037c }
            boolean r2 = r13.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r2)     // Catch:{ all -> 0x037c }
            if (r2 == 0) goto L_0x02cd
            r27.println()     // Catch:{ all -> 0x037c }
        L_0x02cd:
            r7 = 1
            r13.writeFieldName(r1, r7)     // Catch:{ all -> 0x037c }
            goto L_0x02fc
        L_0x02d2:
            r7 = 1
            if (r19 != 0) goto L_0x02d8
            r13.write((int) r2)     // Catch:{ all -> 0x037c }
        L_0x02d8:
            int r1 = NON_STRINGKEY_AS_STRING     // Catch:{ all -> 0x037c }
            boolean r1 = r13.isEnabled((int) r1)     // Catch:{ all -> 0x037c }
            if (r1 != 0) goto L_0x02e8
            com.alibaba.fastjson.serializer.SerializerFeature r1 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNonStringKeyAsString     // Catch:{ all -> 0x037c }
            boolean r1 = com.alibaba.fastjson.serializer.SerializerFeature.isEnabled(r12, r1)     // Catch:{ all -> 0x037c }
            if (r1 == 0) goto L_0x02f4
        L_0x02e8:
            boolean r1 = r0 instanceof java.lang.Enum     // Catch:{ all -> 0x037c }
            if (r1 != 0) goto L_0x02f4
            java.lang.String r1 = com.alibaba.fastjson.JSON.toJSONString(r0)     // Catch:{ all -> 0x037c }
            r9.write((java.lang.String) r1)     // Catch:{ all -> 0x037c }
            goto L_0x02f7
        L_0x02f4:
            r9.write((java.lang.Object) r0)     // Catch:{ all -> 0x037c }
        L_0x02f7:
            r1 = 58
            r13.write((int) r1)     // Catch:{ all -> 0x037c }
        L_0x02fc:
            r19 = 0
            if (r24 != 0) goto L_0x030e
            r13.writeNull()     // Catch:{ all -> 0x037c }
            r6 = r29
            r7 = r8
            r0 = r23
            r4 = r25
            r8 = r26
            goto L_0x00b5
        L_0x030e:
            java.lang.Class r1 = r24.getClass()     // Catch:{ all -> 0x037c }
            r6 = r1
            r1 = r25
            if (r6 == r1) goto L_0x0320
            r1 = r6
            com.alibaba.fastjson.serializer.ObjectSerializer r2 = r9.getObjectWriter(r6)     // Catch:{ all -> 0x037c }
            r17 = r1
            r5 = r2
            goto L_0x0324
        L_0x0320:
            r5 = r17
            r17 = r1
        L_0x0324:
            com.alibaba.fastjson.serializer.SerializerFeature r1 = com.alibaba.fastjson.serializer.SerializerFeature.WriteClassName     // Catch:{ all -> 0x037c }
            boolean r1 = com.alibaba.fastjson.serializer.SerializerFeature.isEnabled(r12, r1)     // Catch:{ all -> 0x037c }
            if (r1 == 0) goto L_0x035e
            boolean r1 = r5 instanceof com.alibaba.fastjson.serializer.JavaBeanSerializer     // Catch:{ all -> 0x037c }
            if (r1 == 0) goto L_0x035e
            r1 = 0
            boolean r2 = r11 instanceof java.lang.reflect.ParameterizedType     // Catch:{ all -> 0x037c }
            if (r2 == 0) goto L_0x0348
            r2 = r11
            java.lang.reflect.ParameterizedType r2 = (java.lang.reflect.ParameterizedType) r2     // Catch:{ all -> 0x037c }
            java.lang.reflect.Type[] r3 = r2.getActualTypeArguments()     // Catch:{ all -> 0x037c }
            int r4 = r3.length     // Catch:{ all -> 0x037c }
            r7 = 2
            if (r4 != r7) goto L_0x0347
            r7 = 1
            r4 = r3[r7]     // Catch:{ all -> 0x037c }
            r1 = r4
            r18 = r1
            goto L_0x034a
        L_0x0347:
            r7 = 1
        L_0x0348:
            r18 = r1
        L_0x034a:
            r1 = r5
            com.alibaba.fastjson.serializer.JavaBeanSerializer r1 = (com.alibaba.fastjson.serializer.JavaBeanSerializer) r1     // Catch:{ all -> 0x037c }
            r2 = r27
            r3 = r24
            r4 = r0
            r21 = r5
            r5 = r18
            r22 = r6
            r6 = r31
            r1.writeNoneASM(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x037c }
            goto L_0x036f
        L_0x035e:
            r21 = r5
            r22 = r6
            r5 = 0
            r1 = r21
            r2 = r27
            r3 = r24
            r4 = r0
            r6 = r31
            r1.write(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x037c }
        L_0x036f:
            r6 = r29
            r7 = r8
            r4 = r17
            r17 = r21
            r0 = r23
            r8 = r26
            goto L_0x00b5
        L_0x037c:
            r0 = move-exception
            goto L_0x03a1
        L_0x037e:
            r1 = r4
            r8 = r7
            r9.context = r8
            r27.decrementIdent()
            com.alibaba.fastjson.serializer.SerializerFeature r0 = com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat
            boolean r0 = r13.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r0)
            if (r0 == 0) goto L_0x0397
            int r0 = r15.size()
            if (r0 <= 0) goto L_0x0397
            r27.println()
        L_0x0397:
            if (r32 != 0) goto L_0x039e
            r0 = 125(0x7d, float:1.75E-43)
            r13.write((int) r0)
        L_0x039e:
            return
        L_0x039f:
            r0 = move-exception
            r8 = r7
        L_0x03a1:
            r9.context = r8
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.MapSerializer.write(com.alibaba.fastjson.serializer.JSONSerializer, java.lang.Object, java.lang.Object, java.lang.reflect.Type, int, boolean):void");
    }
}
