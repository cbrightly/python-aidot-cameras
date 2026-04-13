package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.ParserConfig;
import java.lang.reflect.Constructor;

public class ThrowableDeserializer extends JavaBeanDeserializer {
    public ThrowableDeserializer(ParserConfig mapping, Class<?> clazz) {
        super(mapping, clazz, clazz);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v15, resolved type: java.lang.Throwable} */
    /* JADX WARNING: type inference failed for: r8v5, types: [com.alibaba.fastjson.parser.deserializer.ObjectDeserializer] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r17, java.lang.reflect.Type r18, java.lang.Object r19) {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            r3 = r18
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer
            int r0 = r4.token()
            r5 = 0
            r6 = 8
            if (r0 != r6) goto L_0x0015
            r4.nextToken()
            return r5
        L_0x0015:
            int r0 = r17.getResolveStatus()
            r7 = 2
            java.lang.String r8 = "syntax error"
            if (r0 != r7) goto L_0x0024
            r0 = 0
            r2.setResolveStatus(r0)
            goto L_0x002c
        L_0x0024:
            int r0 = r4.token()
            r7 = 12
            if (r0 != r7) goto L_0x019c
        L_0x002c:
            r0 = 0
            r7 = 0
            if (r3 == 0) goto L_0x0040
            boolean r9 = r3 instanceof java.lang.Class
            if (r9 == 0) goto L_0x0040
            r9 = r3
            java.lang.Class r9 = (java.lang.Class) r9
            java.lang.Class<java.lang.Throwable> r10 = java.lang.Throwable.class
            boolean r10 = r10.isAssignableFrom(r9)
            if (r10 == 0) goto L_0x0040
            r7 = r9
        L_0x0040:
            r9 = 0
            r10 = 0
            r11 = 0
        L_0x0043:
            com.alibaba.fastjson.parser.SymbolTable r12 = r17.getSymbolTable()
            java.lang.String r12 = r4.scanSymbol(r12)
            r13 = 13
            r14 = 16
            if (r12 != 0) goto L_0x006c
            int r15 = r4.token()
            if (r15 != r13) goto L_0x005d
            r4.nextToken(r14)
            r5 = r0
            goto L_0x0109
        L_0x005d:
            int r15 = r4.token()
            if (r15 != r14) goto L_0x006c
            com.alibaba.fastjson.parser.Feature r15 = com.alibaba.fastjson.parser.Feature.AllowArbitraryCommas
            boolean r15 = r4.isEnabled((com.alibaba.fastjson.parser.Feature) r15)
            if (r15 == 0) goto L_0x006c
            goto L_0x0043
        L_0x006c:
            r15 = 4
            r4.nextTokenWithColon(r15)
            java.lang.String r13 = com.alibaba.fastjson.JSON.DEFAULT_TYPE_KEY
            boolean r13 = r13.equals(r12)
            if (r13 == 0) goto L_0x009f
            int r13 = r4.token()
            if (r13 != r15) goto L_0x0099
            java.lang.String r13 = r4.stringVal()
            com.alibaba.fastjson.parser.ParserConfig r15 = r17.getConfig()
            java.lang.Class<java.lang.Throwable> r5 = java.lang.Throwable.class
            int r6 = r4.getFeatures()
            java.lang.Class r5 = r15.checkAutoType(r13, r5, r6)
            r4.nextToken(r14)
            r7 = r5
            r6 = 8
            r13 = 0
            goto L_0x00fd
        L_0x0099:
            com.alibaba.fastjson.JSONException r5 = new com.alibaba.fastjson.JSONException
            r5.<init>(r8)
            throw r5
        L_0x009f:
            java.lang.String r5 = "message"
            boolean r5 = r5.equals(r12)
            if (r5 == 0) goto L_0x00c7
            int r5 = r4.token()
            r6 = 8
            if (r5 != r6) goto L_0x00b1
            r5 = 0
            goto L_0x00bb
        L_0x00b1:
            int r5 = r4.token()
            if (r5 != r15) goto L_0x00c1
            java.lang.String r5 = r4.stringVal()
        L_0x00bb:
            r4.nextToken()
            r9 = r5
            r13 = 0
            goto L_0x00fd
        L_0x00c1:
            com.alibaba.fastjson.JSONException r5 = new com.alibaba.fastjson.JSONException
            r5.<init>(r8)
            throw r5
        L_0x00c7:
            r6 = 8
            java.lang.String r5 = "cause"
            boolean r13 = r5.equals(r12)
            if (r13 == 0) goto L_0x00da
            r13 = 0
            java.lang.Object r5 = r1.deserialze(r2, r13, r5)
            r0 = r5
            java.lang.Throwable r0 = (java.lang.Throwable) r0
            goto L_0x00fd
        L_0x00da:
            r13 = 0
            java.lang.String r5 = "stackTrace"
            boolean r5 = r5.equals(r12)
            if (r5 == 0) goto L_0x00ee
            java.lang.Class<java.lang.StackTraceElement[]> r5 = java.lang.StackTraceElement[].class
            java.lang.Object r5 = r2.parseObject(r5)
            java.lang.StackTraceElement[] r5 = (java.lang.StackTraceElement[]) r5
            r10 = r5
            goto L_0x00fd
        L_0x00ee:
            if (r11 != 0) goto L_0x00f6
            java.util.HashMap r5 = new java.util.HashMap
            r5.<init>()
            r11 = r5
        L_0x00f6:
            java.lang.Object r5 = r17.parse()
            r11.put(r12, r5)
        L_0x00fd:
            int r5 = r4.token()
            r15 = 13
            if (r5 != r15) goto L_0x0199
            r4.nextToken(r14)
            r5 = r0
        L_0x0109:
            r6 = 0
            if (r7 != 0) goto L_0x0112
            java.lang.Exception r0 = new java.lang.Exception
            r0.<init>(r9, r5)
            goto L_0x0129
        L_0x0112:
            java.lang.Class<java.lang.Throwable> r0 = java.lang.Throwable.class
            boolean r0 = r0.isAssignableFrom(r7)
            if (r0 == 0) goto L_0x017d
            java.lang.Throwable r0 = r1.createException(r9, r5, r7)     // Catch:{ Exception -> 0x0174 }
            r6 = r0
            if (r6 != 0) goto L_0x0127
            java.lang.Exception r0 = new java.lang.Exception     // Catch:{ Exception -> 0x0174 }
            r0.<init>(r9, r5)     // Catch:{ Exception -> 0x0174 }
            goto L_0x0128
        L_0x0127:
            r0 = r6
        L_0x0128:
        L_0x0129:
            if (r10 == 0) goto L_0x012e
            r0.setStackTrace(r10)
        L_0x012e:
            if (r11 == 0) goto L_0x0173
            r6 = 0
            if (r7 == 0) goto L_0x0149
            java.lang.Class<?> r8 = r1.clazz
            if (r7 != r8) goto L_0x013a
            r6 = r16
            goto L_0x0149
        L_0x013a:
            com.alibaba.fastjson.parser.ParserConfig r8 = r17.getConfig()
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r8 = r8.getDeserializer((java.lang.reflect.Type) r7)
            boolean r12 = r8 instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer
            if (r12 == 0) goto L_0x0149
            r6 = r8
            com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer r6 = (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) r6
        L_0x0149:
            if (r6 == 0) goto L_0x0173
            java.util.Set r8 = r11.entrySet()
            java.util.Iterator r8 = r8.iterator()
        L_0x0153:
            boolean r12 = r8.hasNext()
            if (r12 == 0) goto L_0x0173
            java.lang.Object r12 = r8.next()
            java.util.Map$Entry r12 = (java.util.Map.Entry) r12
            java.lang.Object r13 = r12.getKey()
            java.lang.String r13 = (java.lang.String) r13
            java.lang.Object r14 = r12.getValue()
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer r15 = r6.getFieldDeserializer((java.lang.String) r13)
            if (r15 == 0) goto L_0x0172
            r15.setValue((java.lang.Object) r0, (java.lang.Object) r14)
        L_0x0172:
            goto L_0x0153
        L_0x0173:
            return r0
        L_0x0174:
            r0 = move-exception
            com.alibaba.fastjson.JSONException r8 = new com.alibaba.fastjson.JSONException
            java.lang.String r12 = "create instance error"
            r8.<init>(r12, r0)
            throw r8
        L_0x017d:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r12 = "type not match, not Throwable. "
            r8.append(r12)
            java.lang.String r12 = r7.getName()
            r8.append(r12)
            java.lang.String r8 = r8.toString()
            r0.<init>(r8)
            throw r0
        L_0x0199:
            r5 = r13
            goto L_0x0043
        L_0x019c:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            r0.<init>(r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.ThrowableDeserializer.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object):java.lang.Object");
    }

    private Throwable createException(String message, Throwable cause, Class<?> exClass) {
        Class<String> cls = String.class;
        Constructor<?> defaultConstructor = null;
        Constructor<?> messageConstructor = null;
        Constructor<?> causeConstructor = null;
        for (Constructor<?> constructor : exClass.getConstructors()) {
            Class<?>[] types = constructor.getParameterTypes();
            if (types.length == 0) {
                defaultConstructor = constructor;
            } else if (types.length == 1 && types[0] == cls) {
                messageConstructor = constructor;
            } else if (types.length == 2 && types[0] == cls && types[1] == Throwable.class) {
                causeConstructor = constructor;
            }
        }
        if (causeConstructor != null) {
            return (Throwable) causeConstructor.newInstance(new Object[]{message, cause});
        } else if (messageConstructor != null) {
            return (Throwable) messageConstructor.newInstance(new Object[]{message});
        } else if (defaultConstructor != null) {
            return (Throwable) defaultConstructor.newInstance(new Object[0]);
        } else {
            return null;
        }
    }

    public int getFastMatchToken() {
        return 12;
    }
}
