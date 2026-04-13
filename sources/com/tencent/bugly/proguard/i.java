package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/* compiled from: BUGLY */
public final class i {
    private ByteBuffer a;
    private String b = "GBK";

    /* compiled from: BUGLY */
    public static final class a {
        public byte a;
        public int b;
    }

    public i() {
    }

    public i(byte[] bArr) {
        this.a = ByteBuffer.wrap(bArr);
    }

    public i(byte[] bArr, int i) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        this.a = wrap;
        wrap.position(4);
    }

    public final void a(byte[] bArr) {
        ByteBuffer byteBuffer = this.a;
        if (byteBuffer != null) {
            byteBuffer.clear();
        }
        this.a = ByteBuffer.wrap(bArr);
    }

    private static int a(a aVar, ByteBuffer byteBuffer) {
        byte b2 = byteBuffer.get();
        aVar.a = (byte) (b2 & 15);
        int i = (b2 & 240) >> 4;
        aVar.b = i;
        if (i != 15) {
            return 1;
        }
        aVar.b = byteBuffer.get();
        return 2;
    }

    private boolean a(int i) {
        int i2;
        try {
            a aVar = new a();
            while (true) {
                int a2 = a(aVar, this.a.duplicate());
                i2 = aVar.b;
                if (i <= i2) {
                    break;
                } else if (aVar.a == 11) {
                    break;
                } else {
                    ByteBuffer byteBuffer = this.a;
                    byteBuffer.position(byteBuffer.position() + a2);
                    a(aVar.a);
                }
            }
            if (i == i2) {
                return true;
            }
            return false;
        } catch (g | BufferUnderflowException e) {
            return false;
        }
    }

    private void a() {
        a aVar = new a();
        do {
            a(aVar, this.a);
            a(aVar.a);
        } while (aVar.a != 11);
    }

    private void a(byte b2) {
        int i = 0;
        switch (b2) {
            case 0:
                ByteBuffer byteBuffer = this.a;
                byteBuffer.position(byteBuffer.position() + 1);
                return;
            case 1:
                ByteBuffer byteBuffer2 = this.a;
                byteBuffer2.position(byteBuffer2.position() + 2);
                return;
            case 2:
                ByteBuffer byteBuffer3 = this.a;
                byteBuffer3.position(byteBuffer3.position() + 4);
                return;
            case 3:
                ByteBuffer byteBuffer4 = this.a;
                byteBuffer4.position(byteBuffer4.position() + 8);
                return;
            case 4:
                ByteBuffer byteBuffer5 = this.a;
                byteBuffer5.position(byteBuffer5.position() + 4);
                return;
            case 5:
                ByteBuffer byteBuffer6 = this.a;
                byteBuffer6.position(byteBuffer6.position() + 8);
                return;
            case 6:
                int i2 = this.a.get();
                if (i2 < 0) {
                    i2 += 256;
                }
                ByteBuffer byteBuffer7 = this.a;
                byteBuffer7.position(byteBuffer7.position() + i2);
                return;
            case 7:
                int i3 = this.a.getInt();
                ByteBuffer byteBuffer8 = this.a;
                byteBuffer8.position(byteBuffer8.position() + i3);
                return;
            case 8:
                int a2 = a(0, 0, true);
                while (i < (a2 << 1)) {
                    a aVar = new a();
                    a(aVar, this.a);
                    a(aVar.a);
                    i++;
                }
                return;
            case 9:
                int a3 = a(0, 0, true);
                while (i < a3) {
                    a aVar2 = new a();
                    a(aVar2, this.a);
                    a(aVar2.a);
                    i++;
                }
                return;
            case 10:
                a();
                return;
            case 11:
            case 12:
                return;
            case 13:
                a aVar3 = new a();
                a(aVar3, this.a);
                if (aVar3.a == 0) {
                    int a4 = a(0, 0, true);
                    ByteBuffer byteBuffer9 = this.a;
                    byteBuffer9.position(byteBuffer9.position() + a4);
                    return;
                }
                throw new g("skipField with invalid type, type value: " + b2 + ", " + aVar3.a);
            default:
                throw new g("invalid type.");
        }
    }

    public final boolean a(int i, boolean z) {
        if (a((byte) 0, i, z) != 0) {
            return true;
        }
        return false;
    }

    public final byte a(byte b2, int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 0:
                    return this.a.get();
                case 12:
                    return 0;
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return b2;
        } else {
            throw new g("require field not exist.");
        }
    }

    public final short a(short s, int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 0:
                    return (short) this.a.get();
                case 1:
                    return this.a.getShort();
                case 12:
                    return 0;
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return s;
        } else {
            throw new g("require field not exist.");
        }
    }

    public final int a(int i, int i2, boolean z) {
        if (a(i2)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 0:
                    return this.a.get();
                case 1:
                    return this.a.getShort();
                case 2:
                    return this.a.getInt();
                case 12:
                    return 0;
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return i;
        } else {
            throw new g("require field not exist.");
        }
    }

    public final long a(long j, int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 0:
                    return (long) this.a.get();
                case 1:
                    return (long) this.a.getShort();
                case 2:
                    return (long) this.a.getInt();
                case 3:
                    return this.a.getLong();
                case 12:
                    return 0;
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return j;
        } else {
            throw new g("require field not exist.");
        }
    }

    private float a(float f, int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 4:
                    return this.a.getFloat();
                case 12:
                    return 0.0f;
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return f;
        } else {
            throw new g("require field not exist.");
        }
    }

    private double a(double d, int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 4:
                    return (double) this.a.getFloat();
                case 5:
                    return this.a.getDouble();
                case 12:
                    return 0.0d;
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return d;
        } else {
            throw new g("require field not exist.");
        }
    }

    public final String b(int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 6:
                    int i2 = this.a.get();
                    if (i2 < 0) {
                        i2 += 256;
                    }
                    byte[] bArr = new byte[i2];
                    this.a.get(bArr);
                    try {
                        return new String(bArr, this.b);
                    } catch (UnsupportedEncodingException e) {
                        return new String(bArr);
                    }
                case 7:
                    int i3 = this.a.getInt();
                    if (i3 > 104857600 || i3 < 0) {
                        throw new g("String too long: " + i3);
                    }
                    byte[] bArr2 = new byte[i3];
                    this.a.get(bArr2);
                    try {
                        return new String(bArr2, this.b);
                    } catch (UnsupportedEncodingException e2) {
                        return new String(bArr2);
                    }
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return null;
        } else {
            throw new g("require field not exist.");
        }
    }

    public final <K, V> HashMap<K, V> a(Map<K, V> map, int i, boolean z) {
        return (HashMap) a(new HashMap(), map, i, z);
    }

    private <K, V> Map<K, V> a(Map<K, V> map, Map<K, V> map2, int i, boolean z) {
        if (map2 == null || map2.isEmpty()) {
            return new HashMap();
        }
        Map.Entry next = map2.entrySet().iterator().next();
        Object key = next.getKey();
        Object value = next.getValue();
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 8:
                    int a2 = a(0, 0, true);
                    if (a2 >= 0) {
                        for (int i2 = 0; i2 < a2; i2++) {
                            map.put(a(key, 0, true), a(value, 1, true));
                        }
                        break;
                    } else {
                        throw new g("size invalid: " + a2);
                    }
                default:
                    throw new g("type mismatch.");
            }
        } else if (z) {
            throw new g("require field not exist.");
        }
        return map;
    }

    private boolean[] d(int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 >= 0) {
                        boolean[] zArr = new boolean[a2];
                        for (int i2 = 0; i2 < a2; i2++) {
                            zArr[i2] = a((byte) 0, 0, true) != 0;
                        }
                        return zArr;
                    }
                    throw new g("size invalid: " + a2);
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return null;
        } else {
            throw new g("require field not exist.");
        }
    }

    public final byte[] c(int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 >= 0) {
                        byte[] bArr = new byte[a2];
                        for (int i2 = 0; i2 < a2; i2++) {
                            bArr[i2] = a(bArr[0], 0, true);
                        }
                        return bArr;
                    }
                    throw new g("size invalid: " + a2);
                case 13:
                    a aVar2 = new a();
                    a(aVar2, this.a);
                    if (aVar2.a == 0) {
                        int a3 = a(0, 0, true);
                        if (a3 >= 0) {
                            byte[] bArr2 = new byte[a3];
                            this.a.get(bArr2);
                            return bArr2;
                        }
                        throw new g("invalid size, tag: " + i + ", type: " + aVar.a + ", " + aVar2.a + ", size: " + a3);
                    }
                    throw new g("type mismatch, tag: " + i + ", type: " + aVar.a + ", " + aVar2.a);
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return null;
        } else {
            throw new g("require field not exist.");
        }
    }

    private short[] e(int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 >= 0) {
                        short[] sArr = new short[a2];
                        for (int i2 = 0; i2 < a2; i2++) {
                            sArr[i2] = a(sArr[0], 0, true);
                        }
                        return sArr;
                    }
                    throw new g("size invalid: " + a2);
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return null;
        } else {
            throw new g("require field not exist.");
        }
    }

    private int[] f(int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 >= 0) {
                        int[] iArr = new int[a2];
                        for (int i2 = 0; i2 < a2; i2++) {
                            iArr[i2] = a(iArr[0], 0, true);
                        }
                        return iArr;
                    }
                    throw new g("size invalid: " + a2);
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return null;
        } else {
            throw new g("require field not exist.");
        }
    }

    private long[] g(int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 >= 0) {
                        long[] jArr = new long[a2];
                        for (int i2 = 0; i2 < a2; i2++) {
                            jArr[i2] = a(jArr[0], 0, true);
                        }
                        return jArr;
                    }
                    throw new g("size invalid: " + a2);
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return null;
        } else {
            throw new g("require field not exist.");
        }
    }

    private float[] h(int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 >= 0) {
                        float[] fArr = new float[a2];
                        for (int i2 = 0; i2 < a2; i2++) {
                            fArr[i2] = a(fArr[0], 0, true);
                        }
                        return fArr;
                    }
                    throw new g("size invalid: " + a2);
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return null;
        } else {
            throw new g("require field not exist.");
        }
    }

    private double[] i(int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 >= 0) {
                        double[] dArr = new double[a2];
                        for (int i2 = 0; i2 < a2; i2++) {
                            dArr[i2] = a(dArr[0], 0, true);
                        }
                        return dArr;
                    }
                    throw new g("size invalid: " + a2);
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return null;
        } else {
            throw new g("require field not exist.");
        }
    }

    private <T> T[] a(T[] tArr, int i, boolean z) {
        if (tArr != null && tArr.length != 0) {
            return b(tArr[0], i, z);
        }
        throw new g("unable to get type of key and value.");
    }

    private <T> T[] b(T t, int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar, this.a);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 >= 0) {
                        T[] tArr = (Object[]) Array.newInstance(t.getClass(), a2);
                        for (int i2 = 0; i2 < a2; i2++) {
                            tArr[i2] = a(t, 0, true);
                        }
                        return tArr;
                    }
                    throw new g("size invalid: " + a2);
                default:
                    throw new g("type mismatch.");
            }
        } else if (!z) {
            return null;
        } else {
            throw new g("require field not exist.");
        }
    }

    public final k a(k kVar, int i, boolean z) {
        if (a(i)) {
            try {
                k kVar2 = (k) kVar.getClass().newInstance();
                a aVar = new a();
                a(aVar, this.a);
                if (aVar.a == 10) {
                    kVar2.a(this);
                    a();
                    return kVar2;
                }
                throw new g("type mismatch.");
            } catch (Exception e) {
                throw new g(e.getMessage());
            }
        } else if (!z) {
            return null;
        } else {
            throw new g("require field not exist.");
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: boolean} */
    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v1, types: [int] */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <T> java.lang.Object a(T r3, int r4, boolean r5) {
        /*
            r2 = this;
            boolean r0 = r3 instanceof java.lang.Byte
            r1 = 0
            if (r0 == 0) goto L_0x000e
            byte r3 = r2.a((byte) r1, (int) r4, (boolean) r5)
            java.lang.Byte r3 = java.lang.Byte.valueOf(r3)
            return r3
        L_0x000e:
            boolean r0 = r3 instanceof java.lang.Boolean
            if (r0 == 0) goto L_0x001e
            byte r3 = r2.a((byte) r1, (int) r4, (boolean) r5)
            if (r3 == 0) goto L_0x0019
            r1 = 1
        L_0x0019:
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r1)
            return r3
        L_0x001e:
            boolean r0 = r3 instanceof java.lang.Short
            if (r0 == 0) goto L_0x002b
            short r3 = r2.a((short) r1, (int) r4, (boolean) r5)
            java.lang.Short r3 = java.lang.Short.valueOf(r3)
            return r3
        L_0x002b:
            boolean r0 = r3 instanceof java.lang.Integer
            if (r0 == 0) goto L_0x0038
            int r3 = r2.a((int) r1, (int) r4, (boolean) r5)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            return r3
        L_0x0038:
            boolean r0 = r3 instanceof java.lang.Long
            if (r0 == 0) goto L_0x0047
            r0 = 0
            long r3 = r2.a((long) r0, (int) r4, (boolean) r5)
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            return r3
        L_0x0047:
            boolean r0 = r3 instanceof java.lang.Float
            if (r0 == 0) goto L_0x0055
            r3 = 0
            float r3 = r2.a((float) r3, (int) r4, (boolean) r5)
            java.lang.Float r3 = java.lang.Float.valueOf(r3)
            return r3
        L_0x0055:
            boolean r0 = r3 instanceof java.lang.Double
            if (r0 == 0) goto L_0x0064
            r0 = 0
            double r3 = r2.a((double) r0, (int) r4, (boolean) r5)
            java.lang.Double r3 = java.lang.Double.valueOf(r3)
            return r3
        L_0x0064:
            boolean r0 = r3 instanceof java.lang.String
            if (r0 == 0) goto L_0x0071
            java.lang.String r3 = r2.b(r4, r5)
            java.lang.String r3 = java.lang.String.valueOf(r3)
            return r3
        L_0x0071:
            boolean r0 = r3 instanceof java.util.Map
            if (r0 == 0) goto L_0x0083
            java.util.Map r3 = (java.util.Map) r3
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            java.util.Map r3 = r2.a(r0, r3, r4, r5)
            java.util.HashMap r3 = (java.util.HashMap) r3
            return r3
        L_0x0083:
            boolean r0 = r3 instanceof java.util.List
            if (r0 == 0) goto L_0x00b5
            java.util.List r3 = (java.util.List) r3
            if (r3 == 0) goto L_0x00af
            boolean r0 = r3.isEmpty()
            if (r0 == 0) goto L_0x0092
            goto L_0x00af
        L_0x0092:
            java.lang.Object r3 = r3.get(r1)
            java.lang.Object[] r3 = r2.b(r3, r4, r5)
            if (r3 != 0) goto L_0x009e
            r3 = 0
            return r3
        L_0x009e:
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
        L_0x00a3:
            int r5 = r3.length
            if (r1 >= r5) goto L_0x00ae
            r5 = r3[r1]
            r4.add(r5)
            int r1 = r1 + 1
            goto L_0x00a3
        L_0x00ae:
            return r4
        L_0x00af:
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            return r3
        L_0x00b5:
            boolean r0 = r3 instanceof com.tencent.bugly.proguard.k
            if (r0 == 0) goto L_0x00c0
            com.tencent.bugly.proguard.k r3 = (com.tencent.bugly.proguard.k) r3
            com.tencent.bugly.proguard.k r3 = r2.a((com.tencent.bugly.proguard.k) r3, (int) r4, (boolean) r5)
            return r3
        L_0x00c0:
            java.lang.Class r0 = r3.getClass()
            boolean r0 = r0.isArray()
            if (r0 == 0) goto L_0x0115
            boolean r0 = r3 instanceof byte[]
            if (r0 != 0) goto L_0x0110
            boolean r0 = r3 instanceof java.lang.Byte[]
            if (r0 == 0) goto L_0x00d3
            goto L_0x0110
        L_0x00d3:
            boolean r0 = r3 instanceof boolean[]
            if (r0 == 0) goto L_0x00dc
            boolean[] r3 = r2.d(r4, r5)
            return r3
        L_0x00dc:
            boolean r0 = r3 instanceof short[]
            if (r0 == 0) goto L_0x00e5
            short[] r3 = r2.e(r4, r5)
            return r3
        L_0x00e5:
            boolean r0 = r3 instanceof int[]
            if (r0 == 0) goto L_0x00ee
            int[] r3 = r2.f(r4, r5)
            return r3
        L_0x00ee:
            boolean r0 = r3 instanceof long[]
            if (r0 == 0) goto L_0x00f7
            long[] r3 = r2.g(r4, r5)
            return r3
        L_0x00f7:
            boolean r0 = r3 instanceof float[]
            if (r0 == 0) goto L_0x0100
            float[] r3 = r2.h(r4, r5)
            return r3
        L_0x0100:
            boolean r0 = r3 instanceof double[]
            if (r0 == 0) goto L_0x0109
            double[] r3 = r2.i(r4, r5)
            return r3
        L_0x0109:
            java.lang.Object[] r3 = (java.lang.Object[]) r3
            java.lang.Object[] r3 = r2.a((T[]) r3, (int) r4, (boolean) r5)
            return r3
        L_0x0110:
            byte[] r3 = r2.c(r4, r5)
            return r3
        L_0x0115:
            com.tencent.bugly.proguard.g r3 = new com.tencent.bugly.proguard.g
            java.lang.String r4 = "read object error: unsupport type."
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.i.a(java.lang.Object, int, boolean):java.lang.Object");
    }

    public final int a(String str) {
        this.b = str;
        return 0;
    }
}
