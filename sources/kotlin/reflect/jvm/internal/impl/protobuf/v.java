package kotlin.reflect.jvm.internal.impl.protobuf;

/* compiled from: Utf8 */
public final class v {
    public static boolean e(byte[] bytes) {
        return f(bytes, 0, bytes.length);
    }

    public static boolean f(byte[] bytes, int index, int limit) {
        return h(bytes, index, limit) == 0;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r1v5, types: [byte, int] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r4v10, types: [byte, int] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r4v4, types: [byte, int] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int g(int r7, byte[] r8, int r9, int r10) {
        /*
            if (r7 == 0) goto L_0x0089
            if (r9 < r10) goto L_0x0005
            return r7
        L_0x0005:
            byte r0 = (byte) r7
            r1 = -32
            r2 = -1
            r3 = -65
            if (r0 >= r1) goto L_0x001d
            r1 = -62
            if (r0 < r1) goto L_0x001c
            int r1 = r9 + 1
            byte r9 = r8[r9]
            if (r9 <= r3) goto L_0x0019
            r9 = r1
            goto L_0x001c
        L_0x0019:
            r9 = r1
            goto L_0x0089
        L_0x001c:
            return r2
        L_0x001d:
            r4 = -16
            if (r0 >= r4) goto L_0x004c
            int r4 = r7 >> 8
            int r4 = ~r4
            byte r4 = (byte) r4
            if (r4 != 0) goto L_0x0033
            int r5 = r9 + 1
            byte r4 = r8[r9]
            if (r5 < r10) goto L_0x0032
            int r9 = b(r0, r4)
            return r9
        L_0x0032:
            r9 = r5
        L_0x0033:
            if (r4 > r3) goto L_0x004b
            r5 = -96
            if (r0 != r1) goto L_0x003b
            if (r4 < r5) goto L_0x004b
        L_0x003b:
            r1 = -19
            if (r0 != r1) goto L_0x0041
            if (r4 >= r5) goto L_0x004b
        L_0x0041:
            int r1 = r9 + 1
            byte r9 = r8[r9]
            if (r9 <= r3) goto L_0x0049
            r9 = r1
            goto L_0x004b
        L_0x0049:
            r9 = r1
            goto L_0x0089
        L_0x004b:
            return r2
        L_0x004c:
            int r1 = r7 >> 8
            int r1 = ~r1
            byte r1 = (byte) r1
            r4 = 0
            if (r1 != 0) goto L_0x0060
            int r5 = r9 + 1
            byte r1 = r8[r9]
            if (r5 < r10) goto L_0x005e
            int r9 = b(r0, r1)
            return r9
        L_0x005e:
            r9 = r5
            goto L_0x0063
        L_0x0060:
            int r5 = r7 >> 16
            byte r4 = (byte) r5
        L_0x0063:
            if (r4 != 0) goto L_0x0071
            int r5 = r9 + 1
            byte r4 = r8[r9]
            if (r5 < r10) goto L_0x0070
            int r9 = c(r0, r1, r4)
            return r9
        L_0x0070:
            r9 = r5
        L_0x0071:
            if (r1 > r3) goto L_0x0088
            int r5 = r0 << 28
            int r6 = r1 + 112
            int r5 = r5 + r6
            int r5 = r5 >> 30
            if (r5 != 0) goto L_0x0088
            if (r4 > r3) goto L_0x0088
            int r5 = r9 + 1
            byte r9 = r8[r9]
            if (r9 <= r3) goto L_0x0086
            r9 = r5
            goto L_0x0088
        L_0x0086:
            r9 = r5
            goto L_0x0089
        L_0x0088:
            return r2
        L_0x0089:
            int r0 = h(r8, r9, r10)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.protobuf.v.g(int, byte[], int, int):int");
    }

    public static int h(byte[] bytes, int index, int limit) {
        while (index < limit && bytes[index] >= 0) {
            index++;
        }
        if (index >= limit) {
            return 0;
        }
        return i(bytes, index, limit);
    }

    private static int i(byte[] bytes, int index, int limit) {
        while (index < limit) {
            int index2 = index + 1;
            byte index3 = bytes[index];
            int byte1 = index3;
            if (index3 >= 0) {
                index = index2;
            } else if (byte1 < -32) {
                if (index2 >= limit) {
                    return byte1;
                }
                if (byte1 >= -62) {
                    index = index2 + 1;
                    if (bytes[index2] > -65) {
                        int i = index;
                    }
                }
                return -1;
            } else if (byte1 < -16) {
                if (index2 >= limit - 1) {
                    return d(bytes, index2, limit);
                }
                int index4 = index2 + 1;
                byte index5 = bytes[index2];
                int byte2 = index5;
                if (index5 <= -65 && ((byte1 != -32 || byte2 >= -96) && (byte1 != -19 || byte2 < -96))) {
                    index = index4 + 1;
                    if (bytes[index4] > -65) {
                        int i2 = index;
                    }
                }
                return -1;
            } else if (index2 >= limit - 2) {
                return d(bytes, index2, limit);
            } else {
                int index6 = index2 + 1;
                byte index7 = bytes[index2];
                int byte22 = index7;
                if (index7 <= -65 && (((byte1 << 28) + (byte22 + 112)) >> 30) == 0) {
                    int index8 = index6 + 1;
                    if (bytes[index6] <= -65) {
                        index = index8 + 1;
                        if (bytes[index8] > -65) {
                        }
                    }
                }
                return -1;
            }
        }
        return 0;
    }

    private static int a(int byte1) {
        if (byte1 > -12) {
            return -1;
        }
        return byte1;
    }

    private static int b(int byte1, int byte2) {
        if (byte1 > -12 || byte2 > -65) {
            return -1;
        }
        return (byte2 << 8) ^ byte1;
    }

    private static int c(int byte1, int byte2, int byte3) {
        if (byte1 > -12 || byte2 > -65 || byte3 > -65) {
            return -1;
        }
        return ((byte2 << 8) ^ byte1) ^ (byte3 << 16);
    }

    private static int d(byte[] bytes, int index, int limit) {
        byte byte1 = bytes[index - 1];
        switch (limit - index) {
            case 0:
                return a(byte1);
            case 1:
                return b(byte1, bytes[index]);
            case 2:
                return c(byte1, bytes[index], bytes[index + 1]);
            default:
                throw new AssertionError();
        }
    }
}
