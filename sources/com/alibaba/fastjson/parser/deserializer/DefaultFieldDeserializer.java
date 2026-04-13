package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.Type;
import java.util.Map;

public class DefaultFieldDeserializer extends FieldDeserializer {
    protected boolean customDeserilizer = false;
    protected ObjectDeserializer fieldValueDeserilizer;

    public DefaultFieldDeserializer(ParserConfig config, Class<?> clazz, FieldInfo fieldInfo) {
        super(clazz, fieldInfo);
        boolean z = false;
        JSONField annotation = fieldInfo.getAnnotation();
        if (annotation != null) {
            Class<?> deserializeUsing = annotation.deserializeUsing();
            if (!(deserializeUsing == null || deserializeUsing == Void.class)) {
                z = true;
            }
            this.customDeserilizer = z;
        }
    }

    public ObjectDeserializer getFieldValueDeserilizer(ParserConfig config) {
        if (this.fieldValueDeserilizer == null) {
            JSONField annotation = this.fieldInfo.getAnnotation();
            if (annotation == null || annotation.deserializeUsing() == Void.class) {
                FieldInfo fieldInfo = this.fieldInfo;
                this.fieldValueDeserilizer = config.getDeserializer(fieldInfo.fieldClass, fieldInfo.fieldType);
            } else {
                try {
                    this.fieldValueDeserilizer = (ObjectDeserializer) annotation.deserializeUsing().newInstance();
                } catch (Exception ex) {
                    throw new JSONException("create deserializeUsing ObjectDeserializer error", ex);
                }
            }
        }
        return this.fieldValueDeserilizer;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0034, code lost:
        r1 = r10.fieldInfo;
     */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0044  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00aa A[Catch:{ IOException -> 0x00b0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00c1  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00a3 A[EDGE_INSN: B:48:0x00a3->B:35:0x00a3 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void parseField(com.alibaba.fastjson.parser.DefaultJSONParser r11, java.lang.Object r12, java.lang.reflect.Type r13, java.util.Map<java.lang.String, java.lang.Object> r14) {
        /*
            r10 = this;
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r0 = r10.fieldValueDeserilizer
            if (r0 != 0) goto L_0x000b
            com.alibaba.fastjson.parser.ParserConfig r0 = r11.getConfig()
            r10.getFieldValueDeserilizer(r0)
        L_0x000b:
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r0 = r10.fieldValueDeserilizer
            com.alibaba.fastjson.util.FieldInfo r1 = r10.fieldInfo
            java.lang.reflect.Type r1 = r1.fieldType
            boolean r2 = r13 instanceof java.lang.reflect.ParameterizedType
            if (r2 == 0) goto L_0x002f
            com.alibaba.fastjson.parser.ParseContext r2 = r11.getContext()
            if (r2 == 0) goto L_0x001d
            r2.type = r13
        L_0x001d:
            if (r1 == r13) goto L_0x002f
            java.lang.Class<?> r3 = r10.clazz
            java.lang.reflect.Type r1 = com.alibaba.fastjson.util.FieldInfo.getFieldType(r3, r13, r1)
            com.alibaba.fastjson.parser.ParserConfig r3 = r11.getConfig()
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r0 = r3.getDeserializer((java.lang.reflect.Type) r1)
            r7 = r1
            goto L_0x0030
        L_0x002f:
            r7 = r1
        L_0x0030:
            boolean r1 = r0 instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer
            if (r1 == 0) goto L_0x0044
            com.alibaba.fastjson.util.FieldInfo r1 = r10.fieldInfo
            int r2 = r1.parserFeatures
            if (r2 == 0) goto L_0x0044
            r3 = r0
            com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer r3 = (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) r3
            java.lang.String r1 = r1.name
            java.lang.Object r1 = r3.deserialze(r11, r7, r1, r2)
            goto L_0x0067
        L_0x0044:
            com.alibaba.fastjson.util.FieldInfo r1 = r10.fieldInfo
            java.lang.String r5 = r1.format
            if (r5 != 0) goto L_0x004e
            int r2 = r1.parserFeatures
            if (r2 == 0) goto L_0x0061
        L_0x004e:
            boolean r2 = r0 instanceof com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer
            if (r2 == 0) goto L_0x0061
            r2 = r0
            com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer r2 = (com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer) r2
            java.lang.String r4 = r1.name
            int r6 = r1.parserFeatures
            r1 = r2
            r2 = r11
            r3 = r7
            java.lang.Object r1 = r1.deserialze(r2, r3, r4, r5, r6)
            goto L_0x0067
        L_0x0061:
            java.lang.String r1 = r1.name
            java.lang.Object r1 = r0.deserialze(r11, r7, r1)
        L_0x0067:
            boolean r2 = r1 instanceof byte[]
            r3 = 0
            if (r2 == 0) goto L_0x00ba
            com.alibaba.fastjson.util.FieldInfo r2 = r10.fieldInfo
            java.lang.String r2 = r2.format
            java.lang.String r4 = "gzip"
            boolean r2 = r4.equals(r2)
            if (r2 != 0) goto L_0x0084
            com.alibaba.fastjson.util.FieldInfo r2 = r10.fieldInfo
            java.lang.String r2 = r2.format
            java.lang.String r4 = "gzip,base64"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x00ba
        L_0x0084:
            r2 = r1
            byte[] r2 = (byte[]) r2
            r4 = 0
            java.util.zip.GZIPInputStream r5 = new java.util.zip.GZIPInputStream     // Catch:{ IOException -> 0x00b0 }
            java.io.ByteArrayInputStream r6 = new java.io.ByteArrayInputStream     // Catch:{ IOException -> 0x00b0 }
            r6.<init>(r2)     // Catch:{ IOException -> 0x00b0 }
            r5.<init>(r6)     // Catch:{ IOException -> 0x00b0 }
            r4 = r5
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x00b0 }
            r5.<init>()     // Catch:{ IOException -> 0x00b0 }
        L_0x0098:
            r6 = 1024(0x400, float:1.435E-42)
            byte[] r6 = new byte[r6]     // Catch:{ IOException -> 0x00b0 }
            int r8 = r4.read(r6)     // Catch:{ IOException -> 0x00b0 }
            r9 = -1
            if (r8 != r9) goto L_0x00aa
            byte[] r6 = r5.toByteArray()     // Catch:{ IOException -> 0x00b0 }
            r1 = r6
            goto L_0x00ba
        L_0x00aa:
            if (r8 <= 0) goto L_0x00af
            r5.write(r6, r3, r8)     // Catch:{ IOException -> 0x00b0 }
        L_0x00af:
            goto L_0x0098
        L_0x00b0:
            r3 = move-exception
            com.alibaba.fastjson.JSONException r5 = new com.alibaba.fastjson.JSONException
            java.lang.String r6 = "unzip bytes error."
            r5.<init>(r6, r3)
            throw r5
        L_0x00ba:
            int r2 = r11.getResolveStatus()
            r4 = 1
            if (r2 != r4) goto L_0x00d1
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r2 = r11.getLastResolveTask()
            r2.fieldDeserializer = r10
            com.alibaba.fastjson.parser.ParseContext r4 = r11.getContext()
            r2.ownerContext = r4
            r11.setResolveStatus(r3)
            goto L_0x00de
        L_0x00d1:
            if (r12 != 0) goto L_0x00db
            com.alibaba.fastjson.util.FieldInfo r2 = r10.fieldInfo
            java.lang.String r2 = r2.name
            r14.put(r2, r1)
            goto L_0x00de
        L_0x00db:
            r10.setValue((java.lang.Object) r12, (java.lang.Object) r1)
        L_0x00de:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer.parseField(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.Object, java.lang.reflect.Type, java.util.Map):void");
    }

    public int getFastMatchToken() {
        ObjectDeserializer objectDeserializer = this.fieldValueDeserilizer;
        if (objectDeserializer != null) {
            return objectDeserializer.getFastMatchToken();
        }
        return 2;
    }

    public void parseFieldUnwrapped(DefaultJSONParser parser, Object object, Type objectType, Map<String, Object> map) {
        throw new JSONException("TODO");
    }
}
