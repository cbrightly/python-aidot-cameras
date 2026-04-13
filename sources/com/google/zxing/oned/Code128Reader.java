package com.google.zxing.oned;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

public final class Code128Reader extends OneDReader {
    private static final int CODE_CODE_A = 101;
    private static final int CODE_CODE_B = 100;
    private static final int CODE_CODE_C = 99;
    private static final int CODE_FNC_1 = 102;
    private static final int CODE_FNC_2 = 97;
    private static final int CODE_FNC_3 = 96;
    private static final int CODE_FNC_4_A = 101;
    private static final int CODE_FNC_4_B = 100;
    static final int[][] CODE_PATTERNS = {new int[]{2, 1, 2, 2, 2, 2}, new int[]{2, 2, 2, 1, 2, 2}, new int[]{2, 2, 2, 2, 2, 1}, new int[]{1, 2, 1, 2, 2, 3}, new int[]{1, 2, 1, 3, 2, 2}, new int[]{1, 3, 1, 2, 2, 2}, new int[]{1, 2, 2, 2, 1, 3}, new int[]{1, 2, 2, 3, 1, 2}, new int[]{1, 3, 2, 2, 1, 2}, new int[]{2, 2, 1, 2, 1, 3}, new int[]{2, 2, 1, 3, 1, 2}, new int[]{2, 3, 1, 2, 1, 2}, new int[]{1, 1, 2, 2, 3, 2}, new int[]{1, 2, 2, 1, 3, 2}, new int[]{1, 2, 2, 2, 3, 1}, new int[]{1, 1, 3, 2, 2, 2}, new int[]{1, 2, 3, 1, 2, 2}, new int[]{1, 2, 3, 2, 2, 1}, new int[]{2, 2, 3, 2, 1, 1}, new int[]{2, 2, 1, 1, 3, 2}, new int[]{2, 2, 1, 2, 3, 1}, new int[]{2, 1, 3, 2, 1, 2}, new int[]{2, 2, 3, 1, 1, 2}, new int[]{3, 1, 2, 1, 3, 1}, new int[]{3, 1, 1, 2, 2, 2}, new int[]{3, 2, 1, 1, 2, 2}, new int[]{3, 2, 1, 2, 2, 1}, new int[]{3, 1, 2, 2, 1, 2}, new int[]{3, 2, 2, 1, 1, 2}, new int[]{3, 2, 2, 2, 1, 1}, new int[]{2, 1, 2, 1, 2, 3}, new int[]{2, 1, 2, 3, 2, 1}, new int[]{2, 3, 2, 1, 2, 1}, new int[]{1, 1, 1, 3, 2, 3}, new int[]{1, 3, 1, 1, 2, 3}, new int[]{1, 3, 1, 3, 2, 1}, new int[]{1, 1, 2, 3, 1, 3}, new int[]{1, 3, 2, 1, 1, 3}, new int[]{1, 3, 2, 3, 1, 1}, new int[]{2, 1, 1, 3, 1, 3}, new int[]{2, 3, 1, 1, 1, 3}, new int[]{2, 3, 1, 3, 1, 1}, new int[]{1, 1, 2, 1, 3, 3}, new int[]{1, 1, 2, 3, 3, 1}, new int[]{1, 3, 2, 1, 3, 1}, new int[]{1, 1, 3, 1, 2, 3}, new int[]{1, 1, 3, 3, 2, 1}, new int[]{1, 3, 3, 1, 2, 1}, new int[]{3, 1, 3, 1, 2, 1}, new int[]{2, 1, 1, 3, 3, 1}, new int[]{2, 3, 1, 1, 3, 1}, new int[]{2, 1, 3, 1, 1, 3}, new int[]{2, 1, 3, 3, 1, 1}, new int[]{2, 1, 3, 1, 3, 1}, new int[]{3, 1, 1, 1, 2, 3}, new int[]{3, 1, 1, 3, 2, 1}, new int[]{3, 3, 1, 1, 2, 1}, new int[]{3, 1, 2, 1, 1, 3}, new int[]{3, 1, 2, 3, 1, 1}, new int[]{3, 3, 2, 1, 1, 1}, new int[]{3, 1, 4, 1, 1, 1}, new int[]{2, 2, 1, 4, 1, 1}, new int[]{4, 3, 1, 1, 1, 1}, new int[]{1, 1, 1, 2, 2, 4}, new int[]{1, 1, 1, 4, 2, 2}, new int[]{1, 2, 1, 1, 2, 4}, new int[]{1, 2, 1, 4, 2, 1}, new int[]{1, 4, 1, 1, 2, 2}, new int[]{1, 4, 1, 2, 2, 1}, new int[]{1, 1, 2, 2, 1, 4}, new int[]{1, 1, 2, 4, 1, 2}, new int[]{1, 2, 2, 1, 1, 4}, new int[]{1, 2, 2, 4, 1, 1}, new int[]{1, 4, 2, 1, 1, 2}, new int[]{1, 4, 2, 2, 1, 1}, new int[]{2, 4, 1, 2, 1, 1}, new int[]{2, 2, 1, 1, 1, 4}, new int[]{4, 1, 3, 1, 1, 1}, new int[]{2, 4, 1, 1, 1, 2}, new int[]{1, 3, 4, 1, 1, 1}, new int[]{1, 1, 1, 2, 4, 2}, new int[]{1, 2, 1, 1, 4, 2}, new int[]{1, 2, 1, 2, 4, 1}, new int[]{1, 1, 4, 2, 1, 2}, new int[]{1, 2, 4, 1, 1, 2}, new int[]{1, 2, 4, 2, 1, 1}, new int[]{4, 1, 1, 2, 1, 2}, new int[]{4, 2, 1, 1, 1, 2}, new int[]{4, 2, 1, 2, 1, 1}, new int[]{2, 1, 2, 1, 4, 1}, new int[]{2, 1, 4, 1, 2, 1}, new int[]{4, 1, 2, 1, 2, 1}, new int[]{1, 1, 1, 1, 4, 3}, new int[]{1, 1, 1, 3, 4, 1}, new int[]{1, 3, 1, 1, 4, 1}, new int[]{1, 1, 4, 1, 1, 3}, new int[]{1, 1, 4, 3, 1, 1}, new int[]{4, 1, 1, 1, 1, 3}, new int[]{4, 1, 1, 3, 1, 1}, new int[]{1, 1, 3, 1, 4, 1}, new int[]{1, 1, 4, 1, 3, 1}, new int[]{3, 1, 1, 1, 4, 1}, new int[]{4, 1, 1, 1, 3, 1}, new int[]{2, 1, 1, 4, 1, 2}, new int[]{2, 1, 1, 2, 1, 4}, new int[]{2, 1, 1, 2, 3, 2}, new int[]{2, 3, 3, 1, 1, 1, 2}};
    private static final int CODE_SHIFT = 98;
    private static final int CODE_START_A = 103;
    private static final int CODE_START_B = 104;
    private static final int CODE_START_C = 105;
    private static final int CODE_STOP = 106;
    private static final float MAX_AVG_VARIANCE = 0.25f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.7f;

    private static int[] findStartPattern(BitArray row) {
        int width = row.getSize();
        int rowOffset = row.getNextSet(0);
        int counterPosition = 0;
        int[] counters = new int[6];
        int patternStart = rowOffset;
        boolean isWhite = false;
        int patternLength = counters.length;
        for (int i = rowOffset; i < width; i++) {
            boolean z = true;
            if (row.get(i) != isWhite) {
                counters[counterPosition] = counters[counterPosition] + 1;
            } else {
                if (counterPosition == patternLength - 1) {
                    float bestVariance = MAX_AVG_VARIANCE;
                    int bestMatch = -1;
                    for (int startCode = 103; startCode <= 105; startCode++) {
                        float variance = OneDReader.patternMatchVariance(counters, CODE_PATTERNS[startCode], MAX_INDIVIDUAL_VARIANCE);
                        if (variance < bestVariance) {
                            bestVariance = variance;
                            bestMatch = startCode;
                        }
                    }
                    if (bestMatch < 0 || !row.isRange(Math.max(0, patternStart - ((i - patternStart) / 2)), patternStart, false)) {
                        patternStart += counters[0] + counters[1];
                        System.arraycopy(counters, 2, counters, 0, counterPosition - 1);
                        counters[counterPosition - 1] = 0;
                        counters[counterPosition] = 0;
                        counterPosition--;
                    } else {
                        return new int[]{patternStart, i, bestMatch};
                    }
                } else {
                    counterPosition++;
                }
                counters[counterPosition] = 1;
                if (isWhite) {
                    z = false;
                }
                isWhite = z;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int decodeCode(BitArray row, int[] counters, int rowOffset) {
        OneDReader.recordPattern(row, rowOffset, counters);
        float bestVariance = MAX_AVG_VARIANCE;
        int bestMatch = -1;
        int d = 0;
        while (true) {
            int[][] iArr = CODE_PATTERNS;
            if (d >= iArr.length) {
                break;
            }
            float variance = OneDReader.patternMatchVariance(counters, iArr[d], MAX_INDIVIDUAL_VARIANCE);
            if (variance < bestVariance) {
                bestVariance = variance;
                bestMatch = d;
            }
            d++;
        }
        if (bestMatch >= 0) {
            return bestMatch;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.zxing.Result decodeRow(int r32, com.google.zxing.common.BitArray r33, java.util.Map<com.google.zxing.DecodeHintType, ?> r34) {
        /*
            r31 = this;
            r0 = r32
            r1 = r33
            r2 = r34
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L_0x0014
            com.google.zxing.DecodeHintType r5 = com.google.zxing.DecodeHintType.ASSUME_GS1
            boolean r5 = r2.containsKey(r5)
            if (r5 == 0) goto L_0x0014
            r5 = r4
            goto L_0x0015
        L_0x0014:
            r5 = r3
        L_0x0015:
            int[] r6 = findStartPattern(r33)
            r7 = 2
            r8 = r6[r7]
            java.util.ArrayList r9 = new java.util.ArrayList
            r10 = 20
            r9.<init>(r10)
            byte r11 = (byte) r8
            java.lang.Byte r11 = java.lang.Byte.valueOf(r11)
            r9.add(r11)
            switch(r8) {
                case 103: goto L_0x0039;
                case 104: goto L_0x0036;
                case 105: goto L_0x0033;
                default: goto L_0x002e;
            }
        L_0x002e:
            com.google.zxing.FormatException r1 = com.google.zxing.FormatException.getFormatInstance()
            throw r1
        L_0x0033:
            r11 = 99
            goto L_0x003c
        L_0x0036:
            r11 = 100
            goto L_0x003c
        L_0x0039:
            r11 = 101(0x65, float:1.42E-43)
        L_0x003c:
            r12 = 0
            r13 = 0
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>(r10)
            r10 = r14
            r14 = r6[r3]
            r15 = r6[r4]
            r4 = 6
            int[] r4 = new int[r4]
            r16 = 0
            r17 = 0
            r18 = r8
            r19 = 0
            r20 = 1
            r21 = 0
            r22 = 0
            r23 = r16
            r3 = r21
            r7 = r22
        L_0x005f:
            if (r12 != 0) goto L_0x01cf
            r22 = r13
            r13 = 0
            r23 = r17
            int r2 = decodeCode(r1, r4, r15)
            r24 = r8
            byte r8 = (byte) r2
            java.lang.Byte r8 = java.lang.Byte.valueOf(r8)
            r9.add(r8)
            r8 = 106(0x6a, float:1.49E-43)
            if (r2 == r8) goto L_0x007a
            r20 = 1
        L_0x007a:
            if (r2 == r8) goto L_0x0082
            int r19 = r19 + 1
            int r17 = r19 * r2
            int r18 = r18 + r17
        L_0x0082:
            r14 = r15
            int r8 = r4.length
            r25 = r12
            r12 = 0
        L_0x0087:
            if (r12 >= r8) goto L_0x0090
            r26 = r4[r12]
            int r15 = r15 + r26
            int r12 = r12 + 1
            goto L_0x0087
        L_0x0090:
            switch(r2) {
                case 103: goto L_0x009a;
                case 104: goto L_0x009a;
                case 105: goto L_0x009a;
                default: goto L_0x0093;
            }
        L_0x0093:
            java.lang.String r12 = "]C1"
            switch(r11) {
                case 99: goto L_0x0182;
                case 100: goto L_0x011d;
                case 101: goto L_0x009f;
                default: goto L_0x0098;
            }
        L_0x0098:
            goto L_0x01bc
        L_0x009a:
            com.google.zxing.FormatException r8 = com.google.zxing.FormatException.getFormatInstance()
            throw r8
        L_0x009f:
            r8 = 64
            if (r2 >= r8) goto L_0x00b9
            if (r7 != r3) goto L_0x00ac
            int r8 = r2 + 32
            char r8 = (char) r8
            r10.append(r8)
            goto L_0x00b4
        L_0x00ac:
            int r8 = r2 + 32
            int r8 = r8 + 128
            char r8 = (char) r8
            r10.append(r8)
        L_0x00b4:
            r7 = 0
            r12 = r25
            goto L_0x01be
        L_0x00b9:
            r8 = 96
            if (r2 >= r8) goto L_0x00d1
            if (r7 != r3) goto L_0x00c6
            int r8 = r2 + -64
            char r8 = (char) r8
            r10.append(r8)
            goto L_0x00cc
        L_0x00c6:
            int r8 = r2 + 64
            char r8 = (char) r8
            r10.append(r8)
        L_0x00cc:
            r7 = 0
            r12 = r25
            goto L_0x01be
        L_0x00d1:
            r8 = 106(0x6a, float:1.49E-43)
            if (r2 == r8) goto L_0x00d7
            r20 = 0
        L_0x00d7:
            switch(r2) {
                case 96: goto L_0x0118;
                case 97: goto L_0x0118;
                case 98: goto L_0x0111;
                case 99: goto L_0x010b;
                case 100: goto L_0x0105;
                case 101: goto L_0x00ef;
                case 102: goto L_0x00dd;
                case 103: goto L_0x00da;
                case 104: goto L_0x00da;
                case 105: goto L_0x00da;
                case 106: goto L_0x00db;
                default: goto L_0x00da;
            }
        L_0x00da:
            goto L_0x0119
        L_0x00db:
            r12 = 1
            goto L_0x011b
        L_0x00dd:
            if (r5 == 0) goto L_0x0119
            int r8 = r10.length()
            if (r8 != 0) goto L_0x00e9
            r10.append(r12)
            goto L_0x0119
        L_0x00e9:
            r8 = 29
            r10.append(r8)
            goto L_0x0119
        L_0x00ef:
            if (r3 != 0) goto L_0x00f8
            if (r7 == 0) goto L_0x00f8
            r3 = 1
            r7 = 0
            r12 = r25
            goto L_0x011b
        L_0x00f8:
            if (r3 == 0) goto L_0x0101
            if (r7 == 0) goto L_0x0101
            r3 = 0
            r7 = 0
            r12 = r25
            goto L_0x011b
        L_0x0101:
            r7 = 1
            r12 = r25
            goto L_0x011b
        L_0x0105:
            r8 = 100
            r11 = r8
            r12 = r25
            goto L_0x011b
        L_0x010b:
            r8 = 99
            r11 = r8
            r12 = r25
            goto L_0x011b
        L_0x0111:
            r13 = 1
            r8 = 100
            r11 = r8
            r12 = r25
            goto L_0x011b
        L_0x0118:
        L_0x0119:
            r12 = r25
        L_0x011b:
            goto L_0x01be
        L_0x011d:
            r8 = 96
            if (r2 >= r8) goto L_0x0137
            if (r7 != r3) goto L_0x012a
            int r8 = r2 + 32
            char r8 = (char) r8
            r10.append(r8)
            goto L_0x0132
        L_0x012a:
            int r8 = r2 + 32
            int r8 = r8 + 128
            char r8 = (char) r8
            r10.append(r8)
        L_0x0132:
            r7 = 0
            r12 = r25
            goto L_0x01be
        L_0x0137:
            r8 = 106(0x6a, float:1.49E-43)
            if (r2 == r8) goto L_0x013d
            r20 = 0
        L_0x013d:
            switch(r2) {
                case 96: goto L_0x017e;
                case 97: goto L_0x017e;
                case 98: goto L_0x0177;
                case 99: goto L_0x0171;
                case 100: goto L_0x015b;
                case 101: goto L_0x0155;
                case 102: goto L_0x0143;
                case 103: goto L_0x0140;
                case 104: goto L_0x0140;
                case 105: goto L_0x0140;
                case 106: goto L_0x0141;
                default: goto L_0x0140;
            }
        L_0x0140:
            goto L_0x017f
        L_0x0141:
            r12 = 1
            goto L_0x0181
        L_0x0143:
            if (r5 == 0) goto L_0x017f
            int r8 = r10.length()
            if (r8 != 0) goto L_0x014f
            r10.append(r12)
            goto L_0x017f
        L_0x014f:
            r8 = 29
            r10.append(r8)
            goto L_0x017f
        L_0x0155:
            r8 = 101(0x65, float:1.42E-43)
            r11 = r8
            r12 = r25
            goto L_0x0181
        L_0x015b:
            if (r3 != 0) goto L_0x0164
            if (r7 == 0) goto L_0x0164
            r3 = 1
            r7 = 0
            r12 = r25
            goto L_0x0181
        L_0x0164:
            if (r3 == 0) goto L_0x016d
            if (r7 == 0) goto L_0x016d
            r3 = 0
            r7 = 0
            r12 = r25
            goto L_0x0181
        L_0x016d:
            r7 = 1
            r12 = r25
            goto L_0x0181
        L_0x0171:
            r8 = 99
            r11 = r8
            r12 = r25
            goto L_0x0181
        L_0x0177:
            r13 = 1
            r8 = 101(0x65, float:1.42E-43)
            r11 = r8
            r12 = r25
            goto L_0x0181
        L_0x017e:
        L_0x017f:
            r12 = r25
        L_0x0181:
            goto L_0x01be
        L_0x0182:
            r8 = 100
            if (r2 >= r8) goto L_0x0193
            r12 = 10
            if (r2 >= r12) goto L_0x018f
            r12 = 48
            r10.append(r12)
        L_0x018f:
            r10.append(r2)
            goto L_0x01bc
        L_0x0193:
            r8 = 106(0x6a, float:1.49E-43)
            if (r2 == r8) goto L_0x0199
            r20 = 0
        L_0x0199:
            switch(r2) {
                case 100: goto L_0x01b7;
                case 101: goto L_0x01b2;
                case 102: goto L_0x01a0;
                case 103: goto L_0x019c;
                case 104: goto L_0x019c;
                case 105: goto L_0x019c;
                case 106: goto L_0x019d;
                default: goto L_0x019c;
            }
        L_0x019c:
            goto L_0x01bc
        L_0x019d:
            r8 = 1
            r12 = r8
            goto L_0x01be
        L_0x01a0:
            if (r5 == 0) goto L_0x01bc
            int r8 = r10.length()
            if (r8 != 0) goto L_0x01ac
            r10.append(r12)
            goto L_0x01bc
        L_0x01ac:
            r8 = 29
            r10.append(r8)
            goto L_0x01bc
        L_0x01b2:
            r11 = 101(0x65, float:1.42E-43)
            r12 = r25
            goto L_0x01be
        L_0x01b7:
            r11 = 100
            r12 = r25
            goto L_0x01be
        L_0x01bc:
            r12 = r25
        L_0x01be:
            if (r22 == 0) goto L_0x01c7
            r8 = 101(0x65, float:1.42E-43)
            if (r11 != r8) goto L_0x01c6
            r8 = 100
        L_0x01c6:
            r11 = r8
        L_0x01c7:
            r17 = r2
            r8 = r24
            r2 = r34
            goto L_0x005f
        L_0x01cf:
            r24 = r8
            r25 = r12
            int r2 = r15 - r14
            int r8 = r1.getNextUnset(r15)
            int r12 = r33.getSize()
            int r15 = r8 - r14
            r21 = 2
            int r15 = r15 / 2
            int r15 = r15 + r8
            int r12 = java.lang.Math.min(r12, r15)
            r15 = 0
            boolean r12 = r1.isRange(r8, r12, r15)
            if (r12 == 0) goto L_0x027b
            r12 = r23
            int r23 = r19 * r12
            int r18 = r18 - r23
            int r15 = r18 % 103
            if (r15 != r12) goto L_0x0276
            int r15 = r10.length()
            if (r15 == 0) goto L_0x0271
            if (r15 <= 0) goto L_0x0213
            if (r20 == 0) goto L_0x0213
            r1 = 99
            if (r11 != r1) goto L_0x020e
            int r1 = r15 + -2
            r10.delete(r1, r15)
            goto L_0x0213
        L_0x020e:
            int r1 = r15 + -1
            r10.delete(r1, r15)
        L_0x0213:
            r1 = 1
            r22 = r6[r1]
            r1 = 0
            r23 = r6[r1]
            int r1 = r22 + r23
            float r1 = (float) r1
            r22 = 1073741824(0x40000000, float:2.0)
            float r1 = r1 / r22
            r23 = r3
            float r3 = (float) r14
            r26 = r4
            float r4 = (float) r2
            float r4 = r4 / r22
            float r3 = r3 + r4
            int r4 = r9.size()
            r22 = r2
            byte[] r2 = new byte[r4]
            r27 = 0
            r28 = r5
            r5 = r27
        L_0x0237:
            if (r5 >= r4) goto L_0x0248
            java.lang.Object r27 = r9.get(r5)
            java.lang.Byte r27 = (java.lang.Byte) r27
            byte r27 = r27.byteValue()
            r2[r5] = r27
            int r5 = r5 + 1
            goto L_0x0237
        L_0x0248:
            com.google.zxing.Result r5 = new com.google.zxing.Result
            r27 = r4
            java.lang.String r4 = r10.toString()
            r29 = r6
            r6 = 2
            com.google.zxing.ResultPoint[] r6 = new com.google.zxing.ResultPoint[r6]
            r21 = r7
            com.google.zxing.ResultPoint r7 = new com.google.zxing.ResultPoint
            r30 = r8
            float r8 = (float) r0
            r7.<init>(r1, r8)
            r8 = 0
            r6[r8] = r7
            com.google.zxing.ResultPoint r7 = new com.google.zxing.ResultPoint
            float r8 = (float) r0
            r7.<init>(r3, r8)
            r8 = 1
            r6[r8] = r7
            com.google.zxing.BarcodeFormat r7 = com.google.zxing.BarcodeFormat.CODE_128
            r5.<init>(r4, r2, r6, r7)
            return r5
        L_0x0271:
            com.google.zxing.NotFoundException r1 = com.google.zxing.NotFoundException.getNotFoundInstance()
            throw r1
        L_0x0276:
            com.google.zxing.ChecksumException r1 = com.google.zxing.ChecksumException.getChecksumInstance()
            throw r1
        L_0x027b:
            com.google.zxing.NotFoundException r1 = com.google.zxing.NotFoundException.getNotFoundInstance()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.Code128Reader.decodeRow(int, com.google.zxing.common.BitArray, java.util.Map):com.google.zxing.Result");
    }
}
