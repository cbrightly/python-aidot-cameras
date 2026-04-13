package com.tencent.bugly.proguard;

import java.util.HashMap;
import java.util.Set;

/* compiled from: BUGLY */
public class c extends a {
    protected HashMap<String, byte[]> d = null;
    private HashMap<String, Object> e = new HashMap<>();
    private i f = new i();

    public final /* bridge */ /* synthetic */ void a(String str) {
        super.a(str);
    }

    public void c() {
        this.d = new HashMap<>();
    }

    public <T> void a(String str, T t) {
        if (this.d == null) {
            super.a(str, t);
        } else if (str == null) {
            throw new IllegalArgumentException("put key can not is null");
        } else if (t == null) {
            throw new IllegalArgumentException("put value can not is null");
        } else if (!(t instanceof Set)) {
            j jVar = new j();
            jVar.a(this.b);
            jVar.a((Object) t, 0);
            this.d.put(str, l.a(jVar.a()));
        } else {
            throw new IllegalArgumentException("can not support Set");
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: byte[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <T> T b(java.lang.String r6, T r7) {
        /*
            r5 = this;
            java.util.HashMap<java.lang.String, byte[]> r0 = r5.d
            r1 = 1
            r2 = 0
            r3 = 0
            if (r0 == 0) goto L_0x0046
            boolean r0 = r0.containsKey(r6)
            if (r0 != 0) goto L_0x000e
            return r2
        L_0x000e:
            java.util.HashMap<java.lang.String, java.lang.Object> r0 = r5.e
            boolean r0 = r0.containsKey(r6)
            if (r0 == 0) goto L_0x001d
            java.util.HashMap<java.lang.String, java.lang.Object> r7 = r5.e
            java.lang.Object r6 = r7.get(r6)
            return r6
        L_0x001d:
            java.util.HashMap<java.lang.String, byte[]> r0 = r5.d
            java.lang.Object r0 = r0.get(r6)
            byte[] r0 = (byte[]) r0
            com.tencent.bugly.proguard.i r2 = r5.f     // Catch:{ Exception -> 0x003f }
            r2.a((byte[]) r0)     // Catch:{ Exception -> 0x003f }
            com.tencent.bugly.proguard.i r0 = r5.f     // Catch:{ Exception -> 0x003f }
            java.lang.String r2 = r5.b     // Catch:{ Exception -> 0x003f }
            r0.a((java.lang.String) r2)     // Catch:{ Exception -> 0x003f }
            com.tencent.bugly.proguard.i r0 = r5.f     // Catch:{ Exception -> 0x003f }
            java.lang.Object r7 = r0.a(r7, (int) r3, (boolean) r1)     // Catch:{ Exception -> 0x003f }
            if (r7 == 0) goto L_0x003e
            java.util.HashMap<java.lang.String, java.lang.Object> r0 = r5.e     // Catch:{ Exception -> 0x003f }
            r0.put(r6, r7)     // Catch:{ Exception -> 0x003f }
        L_0x003e:
            return r7
        L_0x003f:
            r6 = move-exception
            com.tencent.bugly.proguard.b r7 = new com.tencent.bugly.proguard.b
            r7.<init>((java.lang.Exception) r6)
            throw r7
        L_0x0046:
            java.util.HashMap<java.lang.String, java.util.HashMap<java.lang.String, byte[]>> r0 = r5.a
            boolean r0 = r0.containsKey(r6)
            if (r0 != 0) goto L_0x004f
            return r2
        L_0x004f:
            java.util.HashMap<java.lang.String, java.lang.Object> r0 = r5.e
            boolean r0 = r0.containsKey(r6)
            if (r0 == 0) goto L_0x005e
            java.util.HashMap<java.lang.String, java.lang.Object> r7 = r5.e
            java.lang.Object r6 = r7.get(r6)
            return r6
        L_0x005e:
            java.util.HashMap<java.lang.String, java.util.HashMap<java.lang.String, byte[]>> r0 = r5.a
            java.lang.Object r0 = r0.get(r6)
            java.util.HashMap r0 = (java.util.HashMap) r0
            byte[] r2 = new byte[r3]
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r0 = r0.iterator()
            boolean r4 = r0.hasNext()
            if (r4 == 0) goto L_0x0086
            java.lang.Object r0 = r0.next()
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0
            r0.getKey()
            java.lang.Object r0 = r0.getValue()
            r2 = r0
            byte[] r2 = (byte[]) r2
        L_0x0086:
            com.tencent.bugly.proguard.i r0 = r5.f     // Catch:{ Exception -> 0x009e }
            r0.a((byte[]) r2)     // Catch:{ Exception -> 0x009e }
            com.tencent.bugly.proguard.i r0 = r5.f     // Catch:{ Exception -> 0x009e }
            java.lang.String r2 = r5.b     // Catch:{ Exception -> 0x009e }
            r0.a((java.lang.String) r2)     // Catch:{ Exception -> 0x009e }
            com.tencent.bugly.proguard.i r0 = r5.f     // Catch:{ Exception -> 0x009e }
            java.lang.Object r7 = r0.a(r7, (int) r3, (boolean) r1)     // Catch:{ Exception -> 0x009e }
            java.util.HashMap<java.lang.String, java.lang.Object> r0 = r5.e     // Catch:{ Exception -> 0x009e }
            r0.put(r6, r7)     // Catch:{ Exception -> 0x009e }
            return r7
        L_0x009e:
            r6 = move-exception
            com.tencent.bugly.proguard.b r7 = new com.tencent.bugly.proguard.b
            r7.<init>((java.lang.Exception) r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.c.b(java.lang.String, java.lang.Object):java.lang.Object");
    }

    public byte[] a() {
        if (this.d == null) {
            return super.a();
        }
        j jVar = new j(0);
        jVar.a(this.b);
        jVar.a(this.d, 0);
        return l.a(jVar.a());
    }

    public void a(byte[] bArr) {
        try {
            super.a(bArr);
        } catch (Exception e2) {
            this.f.a(bArr);
            this.f.a(this.b);
            HashMap hashMap = new HashMap(1);
            hashMap.put("", new byte[0]);
            this.d = this.f.a(hashMap, 0, false);
        }
    }
}
