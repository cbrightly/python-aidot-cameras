package com.caverock.androidsvg;

/* compiled from: NumberParser */
public class d {
    private static final float[] a = {1.0f, 10.0f, 100.0f, 1000.0f, 10000.0f, 100000.0f, 1000000.0f, 1.0E7f, 1.0E8f, 1.0E9f, 1.0E10f, 9.9999998E10f, 1.0E12f, 9.9999998E12f, 1.0E14f, 9.9999999E14f, 1.00000003E16f, 9.9999998E16f, 9.9999998E17f, 1.0E19f, 1.0E20f, 1.0E21f, 1.0E22f, 1.0E23f, 1.0E24f, 1.0E25f, 1.0E26f, 1.0E27f, 1.0E28f, 1.0E29f, 1.0E30f, 1.0E31f, 1.0E32f, 1.0E33f, 1.0E34f, 1.0E35f, 1.0E36f, 1.0E37f, 1.0E38f};
    private static final float[] b = {1.0f, 0.1f, 0.01f, 0.001f, 1.0E-4f, 1.0E-5f, 1.0E-6f, 1.0E-7f, 1.0E-8f, 1.0E-9f, 1.0E-10f, 1.0E-11f, 1.0E-12f, 1.0E-13f, 1.0E-14f, 1.0E-15f, 1.0E-16f, 1.0E-17f, 1.0E-18f, 1.0E-19f, 1.0E-20f, 1.0E-21f, 1.0E-22f, 1.0E-23f, 1.0E-24f, 1.0E-25f, 1.0E-26f, 1.0E-27f, 1.0E-28f, 1.0E-29f, 1.0E-30f, 1.0E-31f, 1.0E-32f, 1.0E-33f, 1.0E-34f, 1.0E-35f, 1.0E-36f, 1.0E-37f, 1.0E-38f};
    private int c;

    d() {
    }

    /* access modifiers changed from: package-private */
    public int a() {
        return this.c;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00f6, code lost:
        r0.c++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00fd, code lost:
        if (r23 != false) goto L_0x0144;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x00ff, code lost:
        r8 = r0.c;
        r25 = r9;
        r9 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0105, code lost:
        r26 = r10;
        r10 = r0.c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0109, code lost:
        if (r10 >= r3) goto L_0x0135;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x010b, code lost:
        r15 = r1.charAt(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0111, code lost:
        if (r15 < '0') goto L_0x0132;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0115, code lost:
        if (r15 > '9') goto L_0x0132;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0117, code lost:
        r20 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x011c, code lost:
        if (((long) r9) <= 922337203685477580L) goto L_0x0121;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x011e, code lost:
        return Float.NaN;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0121, code lost:
        r9 = (r9 * 10) + (r15 - '0');
        r0.c++;
        r11 = r20;
        r10 = r26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0132, code lost:
        r20 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0135, code lost:
        r20 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0139, code lost:
        if (r0.c != r8) goto L_0x013e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x013b, code lost:
        return Float.NaN;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x013e, code lost:
        if (r14 == false) goto L_0x0142;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0140, code lost:
        r2 = r2 - r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0142, code lost:
        r2 = r2 + r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0144, code lost:
        r25 = r9;
        r26 = r10;
        r20 = r11;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public float b(java.lang.String r28, int r29, int r30) {
        /*
            r27 = this;
            r0 = r27
            r1 = r28
            r2 = r29
            r3 = r30
            r4 = 0
            r5 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 922337203685477580(0xccccccccccccccc, double:5.1488004017107686E-247)
            r0.c = r2
            r14 = 2143289344(0x7fc00000, float:NaN)
            if (r2 < r3) goto L_0x001c
            return r14
        L_0x001c:
            char r15 = r28.charAt(r29)
            switch(r15) {
                case 43: goto L_0x0025;
                case 44: goto L_0x0023;
                case 45: goto L_0x0024;
                default: goto L_0x0023;
            }
        L_0x0023:
            goto L_0x002b
        L_0x0024:
            r4 = 1
        L_0x0025:
            int r14 = r0.c
            int r14 = r14 + 1
            r0.c = r14
        L_0x002b:
            int r14 = r0.c
        L_0x002d:
            int r2 = r0.c
            r17 = 0
            r19 = r15
            r15 = 48
            if (r2 >= r3) goto L_0x0094
            char r2 = r1.charAt(r2)
            if (r2 != r15) goto L_0x0045
            if (r7 != 0) goto L_0x0042
            int r8 = r8 + 1
            goto L_0x0088
        L_0x0042:
            int r9 = r9 + 1
            goto L_0x0088
        L_0x0045:
            r15 = 49
            if (r2 < r15) goto L_0x007c
            r15 = 57
            if (r2 > r15) goto L_0x007c
            int r7 = r7 + r9
        L_0x004e:
            r19 = 10
            if (r9 <= 0) goto L_0x0060
            int r15 = (r5 > r12 ? 1 : (r5 == r12 ? 0 : -1))
            if (r15 <= 0) goto L_0x0059
            r15 = 2143289344(0x7fc00000, float:NaN)
            return r15
        L_0x0059:
            r15 = 2143289344(0x7fc00000, float:NaN)
            long r5 = r5 * r19
            int r9 = r9 + -1
            goto L_0x004e
        L_0x0060:
            r15 = 2143289344(0x7fc00000, float:NaN)
            int r16 = (r5 > r12 ? 1 : (r5 == r12 ? 0 : -1))
            if (r16 <= 0) goto L_0x0067
            return r15
        L_0x0067:
            long r19 = r19 * r5
            int r15 = r2 + -48
            r21 = r5
            long r5 = (long) r15
            long r19 = r19 + r5
            int r7 = r7 + 1
            int r5 = (r19 > r17 ? 1 : (r19 == r17 ? 0 : -1))
            if (r5 >= 0) goto L_0x0079
            r5 = 2143289344(0x7fc00000, float:NaN)
            return r5
        L_0x0079:
            r5 = r19
            goto L_0x0088
        L_0x007c:
            r15 = 46
            if (r2 != r15) goto L_0x0092
            if (r10 == 0) goto L_0x0083
            goto L_0x0092
        L_0x0083:
            int r15 = r0.c
            int r15 = r15 - r14
            r10 = 1
            r11 = r15
        L_0x0088:
            int r15 = r0.c
            int r15 = r15 + 1
            r0.c = r15
            r15 = r2
            r2 = r29
            goto L_0x002d
        L_0x0092:
            r15 = r2
            goto L_0x0096
        L_0x0094:
            r15 = r19
        L_0x0096:
            if (r10 == 0) goto L_0x00a6
            int r2 = r0.c
            r19 = r14
            int r14 = r11 + 1
            if (r2 != r14) goto L_0x00a3
            r2 = 2143289344(0x7fc00000, float:NaN)
            return r2
        L_0x00a3:
            r2 = 2143289344(0x7fc00000, float:NaN)
            goto L_0x00aa
        L_0x00a6:
            r19 = r14
            r2 = 2143289344(0x7fc00000, float:NaN)
        L_0x00aa:
            if (r7 != 0) goto L_0x00b0
            if (r8 != 0) goto L_0x00af
            return r2
        L_0x00af:
            r7 = 1
        L_0x00b0:
            if (r10 == 0) goto L_0x00b6
            int r2 = r11 - r8
            int r2 = r2 - r7
            goto L_0x00b7
        L_0x00b6:
            r2 = r9
        L_0x00b7:
            int r14 = r0.c
            if (r14 >= r3) goto L_0x014b
            char r15 = r1.charAt(r14)
            r14 = 69
            if (r15 == r14) goto L_0x00d2
            r14 = 101(0x65, float:1.42E-43)
            if (r15 != r14) goto L_0x00c8
            goto L_0x00d2
        L_0x00c8:
            r24 = r8
            r25 = r9
            r26 = r10
            r20 = r11
            goto L_0x0153
        L_0x00d2:
            r14 = 0
            r22 = 0
            r23 = 0
            r24 = r8
            int r8 = r0.c
            int r8 = r8 + 1
            r0.c = r8
            if (r8 != r3) goto L_0x00e4
            r8 = 2143289344(0x7fc00000, float:NaN)
            return r8
        L_0x00e4:
            char r8 = r1.charAt(r8)
            switch(r8) {
                case 43: goto L_0x00f6;
                case 44: goto L_0x00eb;
                case 45: goto L_0x00f5;
                case 46: goto L_0x00eb;
                case 47: goto L_0x00eb;
                case 48: goto L_0x00f4;
                case 49: goto L_0x00f4;
                case 50: goto L_0x00f4;
                case 51: goto L_0x00f4;
                case 52: goto L_0x00f4;
                case 53: goto L_0x00f4;
                case 54: goto L_0x00f4;
                case 55: goto L_0x00f4;
                case 56: goto L_0x00f4;
                case 57: goto L_0x00f4;
                default: goto L_0x00eb;
            }
        L_0x00eb:
            r23 = 1
            int r8 = r0.c
            int r8 = r8 + -1
            r0.c = r8
            goto L_0x00fd
        L_0x00f4:
            goto L_0x00fd
        L_0x00f5:
            r14 = 1
        L_0x00f6:
            int r8 = r0.c
            int r8 = r8 + 1
            r0.c = r8
        L_0x00fd:
            if (r23 != 0) goto L_0x0144
            int r8 = r0.c
            r25 = r9
            r9 = r22
        L_0x0105:
            r26 = r10
            int r10 = r0.c
            if (r10 >= r3) goto L_0x0135
            char r15 = r1.charAt(r10)
            r10 = 48
            if (r15 < r10) goto L_0x0132
            r10 = 57
            if (r15 > r10) goto L_0x0132
            r20 = r11
            long r10 = (long) r9
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 <= 0) goto L_0x0121
            r10 = 2143289344(0x7fc00000, float:NaN)
            return r10
        L_0x0121:
            int r10 = r9 * 10
            int r11 = r15 + -48
            int r9 = r10 + r11
            int r10 = r0.c
            int r10 = r10 + 1
            r0.c = r10
            r11 = r20
            r10 = r26
            goto L_0x0105
        L_0x0132:
            r20 = r11
            goto L_0x0137
        L_0x0135:
            r20 = r11
        L_0x0137:
            int r10 = r0.c
            if (r10 != r8) goto L_0x013e
            r10 = 2143289344(0x7fc00000, float:NaN)
            return r10
        L_0x013e:
            if (r14 == 0) goto L_0x0142
            int r2 = r2 - r9
            goto L_0x0153
        L_0x0142:
            int r2 = r2 + r9
            goto L_0x0153
        L_0x0144:
            r25 = r9
            r26 = r10
            r20 = r11
            goto L_0x0153
        L_0x014b:
            r24 = r8
            r25 = r9
            r26 = r10
            r20 = r11
        L_0x0153:
            int r8 = r2 + r7
            r9 = 39
            if (r8 > r9) goto L_0x018a
            int r8 = r2 + r7
            r9 = -44
            if (r8 >= r9) goto L_0x0160
            goto L_0x018a
        L_0x0160:
            float r8 = (float) r5
            int r9 = (r5 > r17 ? 1 : (r5 == r17 ? 0 : -1))
            if (r9 == 0) goto L_0x0184
            if (r2 <= 0) goto L_0x016d
            float[] r9 = a
            r9 = r9[r2]
            float r8 = r8 * r9
            goto L_0x0184
        L_0x016d:
            if (r2 >= 0) goto L_0x0184
            r9 = -38
            if (r2 >= r9) goto L_0x017e
            double r9 = (double) r8
            r16 = 4307583784117748259(0x3bc79ca10c924223, double:1.0E-20)
            double r9 = r9 * r16
            float r8 = (float) r9
            int r2 = r2 + 20
        L_0x017e:
            float[] r9 = b
            int r10 = -r2
            r9 = r9[r10]
            float r8 = r8 * r9
        L_0x0184:
            if (r4 == 0) goto L_0x0188
            float r9 = -r8
            goto L_0x0189
        L_0x0188:
            r9 = r8
        L_0x0189:
            return r9
        L_0x018a:
            r8 = 2143289344(0x7fc00000, float:NaN)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.d.b(java.lang.String, int, int):float");
    }
}
