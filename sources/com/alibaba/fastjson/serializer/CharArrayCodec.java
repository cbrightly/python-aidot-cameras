package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Type;

public class CharArrayCodec implements ObjectDeserializer {
    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        return deserialze(parser);
    }

    /* JADX WARNING: type inference failed for: r4v3, types: [T, char[]] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r11) {
        /*
            com.alibaba.fastjson.parser.JSONLexer r0 = r11.lexer
            int r1 = r0.token()
            r2 = 16
            r3 = 4
            if (r1 != r3) goto L_0x0017
            java.lang.String r1 = r0.stringVal()
            r0.nextToken(r2)
            char[] r2 = r1.toCharArray()
            return r2
        L_0x0017:
            int r1 = r0.token()
            r3 = 2
            if (r1 != r3) goto L_0x002e
            java.lang.Number r1 = r0.integerValue()
            r0.nextToken(r2)
            java.lang.String r2 = r1.toString()
            char[] r2 = r2.toCharArray()
            return r2
        L_0x002e:
            java.lang.Object r1 = r11.parse()
            boolean r2 = r1 instanceof java.lang.String
            if (r2 == 0) goto L_0x003e
            r2 = r1
            java.lang.String r2 = (java.lang.String) r2
            char[] r2 = r2.toCharArray()
            return r2
        L_0x003e:
            boolean r2 = r1 instanceof java.util.Collection
            if (r2 == 0) goto L_0x0093
            r2 = r1
            java.util.Collection r2 = (java.util.Collection) r2
            r3 = 1
            java.util.Iterator r4 = r2.iterator()
        L_0x004a:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x0065
            java.lang.Object r5 = r4.next()
            boolean r6 = r5 instanceof java.lang.String
            if (r6 == 0) goto L_0x0064
            r6 = r5
            java.lang.String r6 = (java.lang.String) r6
            int r6 = r6.length()
            r7 = 1
            if (r6 == r7) goto L_0x0064
            r3 = 0
            goto L_0x0065
        L_0x0064:
            goto L_0x004a
        L_0x0065:
            if (r3 == 0) goto L_0x008b
            int r4 = r2.size()
            char[] r4 = new char[r4]
            r5 = 0
            java.util.Iterator r6 = r2.iterator()
        L_0x0072:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x008a
            java.lang.Object r7 = r6.next()
            int r8 = r5 + 1
            r9 = r7
            java.lang.String r9 = (java.lang.String) r9
            r10 = 0
            char r9 = r9.charAt(r10)
            r4[r5] = r9
            r5 = r8
            goto L_0x0072
        L_0x008a:
            return r4
        L_0x008b:
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException
            java.lang.String r5 = "can not cast to char[]"
            r4.<init>(r5)
            throw r4
        L_0x0093:
            if (r1 != 0) goto L_0x0097
            r2 = 0
            goto L_0x009f
        L_0x0097:
            java.lang.String r2 = com.alibaba.fastjson.JSON.toJSONString(r1)
            char[] r2 = r2.toCharArray()
        L_0x009f:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.CharArrayCodec.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser):java.lang.Object");
    }

    public int getFastMatchToken() {
        return 4;
    }
}
