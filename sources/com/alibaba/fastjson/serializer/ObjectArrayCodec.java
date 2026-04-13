package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import io.netty.util.internal.StringUtil;
import java.lang.reflect.Type;

public class ObjectArrayCodec implements ObjectSerializer, ObjectDeserializer {
    public static final ObjectArrayCodec instance = new ObjectArrayCodec();

    public final void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        int i;
        char c;
        JSONSerializer jSONSerializer = serializer;
        Object obj = object;
        SerializeWriter out = jSONSerializer.out;
        Object[] array = (Object[]) obj;
        if (obj == null) {
            out.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        int size = array.length;
        int end = size - 1;
        if (end == -1) {
            out.append((CharSequence) "[]");
            return;
        }
        SerialContext context = jSONSerializer.context;
        jSONSerializer.setContext(context, obj, fieldName, 0);
        Class<?> preClazz = null;
        try {
            out.append('[');
            boolean isEnabled = out.isEnabled(SerializerFeature.PrettyFormat);
            char c2 = StringUtil.COMMA;
            if (isEnabled) {
                serializer.incrementIndent();
                serializer.println();
                for (int i2 = 0; i2 < size; i2++) {
                    if (i2 != 0) {
                        out.write(44);
                        serializer.println();
                    }
                    jSONSerializer.write(array[i2]);
                }
                serializer.decrementIdent();
                serializer.println();
                out.write(93);
                return;
            }
            ObjectSerializer preWriter = null;
            int i3 = 0;
            while (i3 < end) {
                Object item = array[i3];
                if (item == null) {
                    out.append((CharSequence) "null,");
                    i = i3;
                    c = c2;
                } else {
                    if (jSONSerializer.containsReference(item)) {
                        jSONSerializer.writeReference(item);
                        Object obj2 = item;
                        i = i3;
                        c = c2;
                    } else {
                        Class<?> clazz = item.getClass();
                        if (clazz == preClazz) {
                            Class<?> cls = clazz;
                            Object obj3 = item;
                            i = i3;
                            c = c2;
                            preWriter.write(serializer, item, Integer.valueOf(i3), (Type) null, 0);
                        } else {
                            Class<?> clazz2 = clazz;
                            i = i3;
                            c = c2;
                            preClazz = clazz2;
                            Class<?> clazz3 = clazz2;
                            ObjectSerializer preWriter2 = jSONSerializer.getObjectWriter(clazz3);
                            Class<?> cls2 = clazz3;
                            preWriter2.write(serializer, item, Integer.valueOf(i), (Type) null, 0);
                            preWriter = preWriter2;
                        }
                    }
                    out.append(c);
                }
                i3 = i + 1;
                c2 = c;
            }
            int i4 = i3;
            Object item2 = array[end];
            if (item2 == null) {
                out.append((CharSequence) "null]");
            } else {
                if (jSONSerializer.containsReference(item2)) {
                    jSONSerializer.writeReference(item2);
                } else {
                    jSONSerializer.writeWithFieldName(item2, Integer.valueOf(end));
                }
                out.append(']');
            }
            jSONSerializer.context = context;
        } finally {
            jSONSerializer.context = context;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v3, resolved type: java.lang.Class<?>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v5, resolved type: java.lang.reflect.Type} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: java.lang.reflect.TypeVariable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v4, resolved type: java.lang.reflect.Type[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: java.lang.Class<?>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v7, resolved type: java.lang.Class<?>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v8, resolved type: java.lang.Class<?>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v9, resolved type: java.lang.Class<?>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v10, resolved type: java.lang.Class<?>} */
    /* JADX WARNING: type inference failed for: r8v0, types: [T, byte[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r16, java.lang.reflect.Type r17, java.lang.Object r18) {
        /*
            r15 = this;
            r0 = r16
            r1 = r17
            com.alibaba.fastjson.parser.JSONLexer r2 = r0.lexer
            int r3 = r2.token()
            r4 = 0
            r5 = 16
            r6 = 8
            if (r3 != r6) goto L_0x0015
            r2.nextToken(r5)
            return r4
        L_0x0015:
            r6 = 4
            if (r3 == r6) goto L_0x00a1
            r6 = 26
            if (r3 != r6) goto L_0x0021
            r7 = r15
            r6 = r18
            goto L_0x00a4
        L_0x0021:
            boolean r4 = r1 instanceof java.lang.reflect.GenericArrayType
            if (r4 == 0) goto L_0x0089
            r4 = r1
            java.lang.reflect.GenericArrayType r4 = (java.lang.reflect.GenericArrayType) r4
            java.lang.reflect.Type r5 = r4.getGenericComponentType()
            boolean r6 = r5 instanceof java.lang.reflect.TypeVariable
            if (r6 == 0) goto L_0x0084
            r6 = r5
            java.lang.reflect.TypeVariable r6 = (java.lang.reflect.TypeVariable) r6
            com.alibaba.fastjson.parser.ParseContext r7 = r16.getContext()
            java.lang.reflect.Type r7 = r7.type
            boolean r8 = r7 instanceof java.lang.reflect.ParameterizedType
            if (r8 == 0) goto L_0x0078
            r8 = r7
            java.lang.reflect.ParameterizedType r8 = (java.lang.reflect.ParameterizedType) r8
            java.lang.reflect.Type r9 = r8.getRawType()
            r10 = 0
            boolean r11 = r9 instanceof java.lang.Class
            if (r11 == 0) goto L_0x006d
            r11 = r9
            java.lang.Class r11 = (java.lang.Class) r11
            java.lang.reflect.TypeVariable[] r11 = r11.getTypeParameters()
            r12 = 0
        L_0x0051:
            int r13 = r11.length
            if (r12 >= r13) goto L_0x006d
            r13 = r11[r12]
            java.lang.String r13 = r13.getName()
            java.lang.String r14 = r6.getName()
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x006a
            java.lang.reflect.Type[] r13 = r8.getActualTypeArguments()
            r10 = r13[r12]
        L_0x006a:
            int r12 = r12 + 1
            goto L_0x0051
        L_0x006d:
            boolean r11 = r10 instanceof java.lang.Class
            if (r11 == 0) goto L_0x0075
            r11 = r10
            java.lang.Class r11 = (java.lang.Class) r11
            goto L_0x0077
        L_0x0075:
            java.lang.Class<java.lang.Object> r11 = java.lang.Object.class
        L_0x0077:
            goto L_0x0083
        L_0x0078:
            java.lang.reflect.Type[] r8 = r6.getBounds()
            r9 = 0
            r8 = r8[r9]
            java.lang.Class r11 = com.alibaba.fastjson.util.TypeUtils.getClass(r8)
        L_0x0083:
            goto L_0x0088
        L_0x0084:
            java.lang.Class r11 = com.alibaba.fastjson.util.TypeUtils.getClass(r5)
        L_0x0088:
            goto L_0x0091
        L_0x0089:
            r4 = r1
            java.lang.Class r4 = (java.lang.Class) r4
            java.lang.Class r5 = r4.getComponentType()
            r11 = r5
        L_0x0091:
            com.alibaba.fastjson.JSONArray r4 = new com.alibaba.fastjson.JSONArray
            r4.<init>()
            r6 = r18
            r0.parseArray(r5, r4, r6)
            r7 = r15
            java.lang.Object r8 = r15.toObjectArray(r0, r11, r4)
            return r8
        L_0x00a1:
            r7 = r15
            r6 = r18
        L_0x00a4:
            byte[] r8 = r2.bytesValue()
            r2.nextToken(r5)
            int r5 = r8.length
            if (r5 != 0) goto L_0x00b3
            java.lang.Class<byte[]> r5 = byte[].class
            if (r1 == r5) goto L_0x00b3
            return r4
        L_0x00b3:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.ObjectArrayCodec.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object):java.lang.Object");
    }

    /* JADX WARNING: type inference failed for: r12v0, types: [java.lang.reflect.Type, java.lang.Class<?>, java.lang.Class] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private <T> T toObjectArray(com.alibaba.fastjson.parser.DefaultJSONParser r11, java.lang.Class<?> r12, com.alibaba.fastjson.JSONArray r13) {
        /*
            r10 = this;
            if (r13 != 0) goto L_0x0004
            r0 = 0
            return r0
        L_0x0004:
            int r0 = r13.size()
            java.lang.Object r1 = java.lang.reflect.Array.newInstance(r12, r0)
            r2 = 0
        L_0x000d:
            if (r2 >= r0) goto L_0x0065
            java.lang.Object r3 = r13.get(r2)
            if (r3 != r13) goto L_0x0019
            java.lang.reflect.Array.set(r1, r2, r1)
            goto L_0x0062
        L_0x0019:
            boolean r4 = r12.isArray()
            if (r4 == 0) goto L_0x0032
            boolean r4 = r12.isInstance(r3)
            if (r4 == 0) goto L_0x0027
            r4 = r3
            goto L_0x002e
        L_0x0027:
            r4 = r3
            com.alibaba.fastjson.JSONArray r4 = (com.alibaba.fastjson.JSONArray) r4
            java.lang.Object r4 = r10.toObjectArray(r11, r12, r4)
        L_0x002e:
            java.lang.reflect.Array.set(r1, r2, r4)
            goto L_0x0062
        L_0x0032:
            r4 = 0
            boolean r5 = r3 instanceof com.alibaba.fastjson.JSONArray
            if (r5 == 0) goto L_0x0055
            r5 = 0
            r6 = r3
            com.alibaba.fastjson.JSONArray r6 = (com.alibaba.fastjson.JSONArray) r6
            int r7 = r6.size()
            r8 = 0
        L_0x0040:
            if (r8 >= r7) goto L_0x004f
            java.lang.Object r9 = r6.get(r8)
            if (r9 != r13) goto L_0x004c
            r6.set(r2, r1)
            r5 = 1
        L_0x004c:
            int r8 = r8 + 1
            goto L_0x0040
        L_0x004f:
            if (r5 == 0) goto L_0x0055
            java.lang.Object[] r4 = r6.toArray()
        L_0x0055:
            if (r4 != 0) goto L_0x005f
            com.alibaba.fastjson.parser.ParserConfig r5 = r11.getConfig()
            java.lang.Object r4 = com.alibaba.fastjson.util.TypeUtils.cast((java.lang.Object) r3, r12, (com.alibaba.fastjson.parser.ParserConfig) r5)
        L_0x005f:
            java.lang.reflect.Array.set(r1, r2, r4)
        L_0x0062:
            int r2 = r2 + 1
            goto L_0x000d
        L_0x0065:
            r13.setRelatedArray(r1)
            r13.setComponentType(r12)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.ObjectArrayCodec.toObjectArray(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.Class, com.alibaba.fastjson.JSONArray):java.lang.Object");
    }

    public int getFastMatchToken() {
        return 14;
    }
}
