package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Map;

public class ArrayListTypeFieldDeserializer extends FieldDeserializer {
    private ObjectDeserializer deserializer;
    private int itemFastMatchToken;
    private final Type itemType;

    public ArrayListTypeFieldDeserializer(ParserConfig mapping, Class<?> clazz, FieldInfo fieldInfo) {
        super(clazz, fieldInfo);
        if (fieldInfo.fieldType instanceof ParameterizedType) {
            Type argType = ((ParameterizedType) fieldInfo.fieldType).getActualTypeArguments()[0];
            if (argType instanceof WildcardType) {
                Type[] upperBounds = ((WildcardType) argType).getUpperBounds();
                if (upperBounds.length == 1) {
                    argType = upperBounds[0];
                }
            }
            this.itemType = argType;
            return;
        }
        this.itemType = Object.class;
    }

    public int getFastMatchToken() {
        return 14;
    }

    public void parseField(DefaultJSONParser parser, Object object, Type objectType, Map<String, Object> fieldValues) {
        JSONLexer lexer = parser.lexer;
        int token = lexer.token();
        if (token != 8 && (token != 4 || lexer.stringVal().length() != 0)) {
            ArrayList list = new ArrayList();
            ParseContext context = parser.getContext();
            parser.setContext(context, object, this.fieldInfo.name);
            parseArray(parser, objectType, list);
            parser.setContext(context);
            if (object == null) {
                fieldValues.put(this.fieldInfo.name, list);
            } else {
                setValue(object, (Object) list);
            }
        } else if (object == null) {
            fieldValues.put(this.fieldInfo.name, (Object) null);
        } else {
            setValue(object, (String) null);
        }
    }

    /* JADX WARNING: type inference failed for: r12v7, types: [java.lang.reflect.Type] */
    /* JADX WARNING: type inference failed for: r11v14, types: [java.lang.reflect.Type] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0136  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x018f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void parseArray(com.alibaba.fastjson.parser.DefaultJSONParser r18, java.lang.reflect.Type r19, java.util.Collection r20) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            java.lang.reflect.Type r4 = r0.itemType
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r5 = r0.deserializer
            boolean r6 = r2 instanceof java.lang.reflect.ParameterizedType
            r8 = 1
            if (r6 == 0) goto L_0x00eb
            boolean r6 = r4 instanceof java.lang.reflect.TypeVariable
            r9 = -1
            if (r6 == 0) goto L_0x006a
            r6 = r4
            java.lang.reflect.TypeVariable r6 = (java.lang.reflect.TypeVariable) r6
            r8 = r2
            java.lang.reflect.ParameterizedType r8 = (java.lang.reflect.ParameterizedType) r8
            r10 = 0
            java.lang.reflect.Type r11 = r8.getRawType()
            boolean r11 = r11 instanceof java.lang.Class
            if (r11 == 0) goto L_0x002c
            java.lang.reflect.Type r11 = r8.getRawType()
            r10 = r11
            java.lang.Class r10 = (java.lang.Class) r10
        L_0x002c:
            r11 = -1
            if (r10 == 0) goto L_0x0050
            r12 = 0
            java.lang.reflect.TypeVariable[] r13 = r10.getTypeParameters()
            int r13 = r13.length
        L_0x0035:
            if (r12 >= r13) goto L_0x0050
            java.lang.reflect.TypeVariable[] r14 = r10.getTypeParameters()
            r14 = r14[r12]
            java.lang.String r15 = r14.getName()
            java.lang.String r7 = r6.getName()
            boolean r7 = r15.equals(r7)
            if (r7 == 0) goto L_0x004d
            r11 = r12
            goto L_0x0050
        L_0x004d:
            int r12 = r12 + 1
            goto L_0x0035
        L_0x0050:
            if (r11 == r9) goto L_0x0068
            java.lang.reflect.Type[] r7 = r8.getActualTypeArguments()
            r4 = r7[r11]
            java.lang.reflect.Type r7 = r0.itemType
            boolean r7 = r4.equals(r7)
            if (r7 != 0) goto L_0x0068
            com.alibaba.fastjson.parser.ParserConfig r7 = r18.getConfig()
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r5 = r7.getDeserializer((java.lang.reflect.Type) r4)
        L_0x0068:
            goto L_0x012c
        L_0x006a:
            boolean r6 = r4 instanceof java.lang.reflect.ParameterizedType
            if (r6 == 0) goto L_0x00e8
            r6 = r4
            java.lang.reflect.ParameterizedType r6 = (java.lang.reflect.ParameterizedType) r6
            java.lang.reflect.Type[] r7 = r6.getActualTypeArguments()
            int r10 = r7.length
            if (r10 != r8) goto L_0x00e3
            r8 = 0
            r10 = r7[r8]
            boolean r10 = r10 instanceof java.lang.reflect.TypeVariable
            if (r10 == 0) goto L_0x00e3
            r10 = r7[r8]
            r8 = r10
            java.lang.reflect.TypeVariable r8 = (java.lang.reflect.TypeVariable) r8
            r10 = r2
            java.lang.reflect.ParameterizedType r10 = (java.lang.reflect.ParameterizedType) r10
            r11 = 0
            java.lang.reflect.Type r12 = r10.getRawType()
            boolean r12 = r12 instanceof java.lang.Class
            if (r12 == 0) goto L_0x0097
            java.lang.reflect.Type r12 = r10.getRawType()
            r11 = r12
            java.lang.Class r11 = (java.lang.Class) r11
        L_0x0097:
            r12 = -1
            if (r11 == 0) goto L_0x00c3
            r13 = 0
            java.lang.reflect.TypeVariable[] r14 = r11.getTypeParameters()
            int r14 = r14.length
        L_0x00a0:
            if (r13 >= r14) goto L_0x00c0
            java.lang.reflect.TypeVariable[] r15 = r11.getTypeParameters()
            r15 = r15[r13]
            java.lang.String r9 = r15.getName()
            r16 = r5
            java.lang.String r5 = r8.getName()
            boolean r5 = r9.equals(r5)
            if (r5 == 0) goto L_0x00ba
            r12 = r13
            goto L_0x00c5
        L_0x00ba:
            int r13 = r13 + 1
            r5 = r16
            r9 = -1
            goto L_0x00a0
        L_0x00c0:
            r16 = r5
            goto L_0x00c5
        L_0x00c3:
            r16 = r5
        L_0x00c5:
            r5 = -1
            if (r12 == r5) goto L_0x00e5
            java.lang.reflect.Type[] r5 = r10.getActualTypeArguments()
            r5 = r5[r12]
            r9 = 0
            r7[r9] = r5
            com.alibaba.fastjson.util.ParameterizedTypeImpl r5 = new com.alibaba.fastjson.util.ParameterizedTypeImpl
            java.lang.reflect.Type r9 = r6.getOwnerType()
            java.lang.reflect.Type r13 = r6.getRawType()
            r5.<init>(r7, r9, r13)
            java.lang.reflect.Type r4 = com.alibaba.fastjson.TypeReference.intern(r5)
            goto L_0x00e5
        L_0x00e3:
            r16 = r5
        L_0x00e5:
            r5 = r16
            goto L_0x012c
        L_0x00e8:
            r16 = r5
            goto L_0x012a
        L_0x00eb:
            r16 = r5
            boolean r5 = r4 instanceof java.lang.reflect.TypeVariable
            if (r5 == 0) goto L_0x012a
            boolean r5 = r2 instanceof java.lang.Class
            if (r5 == 0) goto L_0x012a
            r5 = r2
            java.lang.Class r5 = (java.lang.Class) r5
            r6 = r4
            java.lang.reflect.TypeVariable r6 = (java.lang.reflect.TypeVariable) r6
            r5.getTypeParameters()
            r7 = 0
            java.lang.reflect.TypeVariable[] r9 = r5.getTypeParameters()
            int r9 = r9.length
        L_0x0104:
            if (r7 >= r9) goto L_0x012a
            java.lang.reflect.TypeVariable[] r10 = r5.getTypeParameters()
            r10 = r10[r7]
            java.lang.String r11 = r10.getName()
            java.lang.String r12 = r6.getName()
            boolean r11 = r11.equals(r12)
            if (r11 == 0) goto L_0x0127
            java.lang.reflect.Type[] r11 = r10.getBounds()
            int r12 = r11.length
            if (r12 != r8) goto L_0x012a
            r8 = 0
            r4 = r11[r8]
            r5 = r16
            goto L_0x012c
        L_0x0127:
            int r7 = r7 + 1
            goto L_0x0104
        L_0x012a:
            r5 = r16
        L_0x012c:
            com.alibaba.fastjson.parser.JSONLexer r6 = r1.lexer
            int r7 = r6.token()
            r8 = 14
            if (r7 != r8) goto L_0x018f
            if (r5 != 0) goto L_0x014b
            com.alibaba.fastjson.parser.ParserConfig r8 = r18.getConfig()
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r8 = r8.getDeserializer((java.lang.reflect.Type) r4)
            r0.deserializer = r8
            r5 = r8
            int r8 = r8.getFastMatchToken()
            r0.itemFastMatchToken = r8
            r8 = r5
            goto L_0x014c
        L_0x014b:
            r8 = r5
        L_0x014c:
            int r5 = r0.itemFastMatchToken
            r6.nextToken(r5)
            r5 = 0
        L_0x0152:
            com.alibaba.fastjson.parser.Feature r9 = com.alibaba.fastjson.parser.Feature.AllowArbitraryCommas
            boolean r9 = r6.isEnabled((com.alibaba.fastjson.parser.Feature) r9)
            r10 = 16
            if (r9 == 0) goto L_0x0166
        L_0x015c:
            int r9 = r6.token()
            if (r9 != r10) goto L_0x0166
            r6.nextToken()
            goto L_0x015c
        L_0x0166:
            int r9 = r6.token()
            r11 = 15
            if (r9 != r11) goto L_0x0173
            r6.nextToken(r10)
            goto L_0x01ac
        L_0x0173:
            java.lang.Integer r9 = java.lang.Integer.valueOf(r5)
            java.lang.Object r9 = r8.deserialze(r1, r4, r9)
            r3.add(r9)
            r1.checkListResolve(r3)
            int r11 = r6.token()
            if (r11 != r10) goto L_0x018c
            int r10 = r0.itemFastMatchToken
            r6.nextToken(r10)
        L_0x018c:
            int r5 = r5 + 1
            goto L_0x0152
        L_0x018f:
            if (r5 != 0) goto L_0x019c
            com.alibaba.fastjson.parser.ParserConfig r8 = r18.getConfig()
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r8 = r8.getDeserializer((java.lang.reflect.Type) r4)
            r0.deserializer = r8
            r5 = r8
        L_0x019c:
            r8 = 0
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            java.lang.Object r8 = r5.deserialze(r1, r4, r8)
            r3.add(r8)
            r1.checkListResolve(r3)
            r8 = r5
        L_0x01ac:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.ArrayListTypeFieldDeserializer.parseArray(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.util.Collection):void");
    }
}
