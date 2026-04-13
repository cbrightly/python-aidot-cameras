package com.alibaba.fastjson.serializer;

public class ClobSeriliazer implements ObjectSerializer {
    public static final ClobSeriliazer instance = new ClobSeriliazer();

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0031, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003a, code lost:
        throw new com.alibaba.fastjson.JSONException("read string from reader error", r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0044, code lost:
        throw new java.io.IOException("write clob error", r0);
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:1:0x0002, B:6:0x0014] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void write(com.alibaba.fastjson.serializer.JSONSerializer r7, java.lang.Object r8, java.lang.Object r9, java.lang.reflect.Type r10, int r11) {
        /*
            r6 = this;
            if (r8 != 0) goto L_0x0006
            r7.writeNull()     // Catch:{ SQLException -> 0x003b }
            return
        L_0x0006:
            r0 = r8
            java.sql.Clob r0 = (java.sql.Clob) r0     // Catch:{ SQLException -> 0x003b }
            java.io.Reader r1 = r0.getCharacterStream()     // Catch:{ SQLException -> 0x003b }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ SQLException -> 0x003b }
            r2.<init>()     // Catch:{ SQLException -> 0x003b }
            r3 = 2048(0x800, float:2.87E-42)
            char[] r3 = new char[r3]     // Catch:{ Exception -> 0x0031 }
        L_0x0016:
            int r4 = r3.length     // Catch:{ Exception -> 0x0031 }
            r5 = 0
            int r4 = r1.read(r3, r5, r4)     // Catch:{ Exception -> 0x0031 }
            if (r4 >= 0) goto L_0x002c
            java.lang.String r3 = r2.toString()     // Catch:{ SQLException -> 0x003b }
            r1.close()     // Catch:{ SQLException -> 0x003b }
            r7.write((java.lang.String) r3)     // Catch:{ SQLException -> 0x003b }
            return
        L_0x002c:
            r2.append(r3, r5, r4)     // Catch:{ Exception -> 0x0031 }
            goto L_0x0016
        L_0x0031:
            r3 = move-exception
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ SQLException -> 0x003b }
            java.lang.String r5 = "read string from reader error"
            r4.<init>(r5, r3)     // Catch:{ SQLException -> 0x003b }
            throw r4     // Catch:{ SQLException -> 0x003b }
        L_0x003b:
            r0 = move-exception
            java.io.IOException r1 = new java.io.IOException
            java.lang.String r2 = "write clob error"
            r1.<init>(r2, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.ClobSeriliazer.write(com.alibaba.fastjson.serializer.JSONSerializer, java.lang.Object, java.lang.Object, java.lang.reflect.Type, int):void");
    }
}
