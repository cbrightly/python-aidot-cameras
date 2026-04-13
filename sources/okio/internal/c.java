package okio.internal;

import kotlin.jvm.internal.k;
import okio.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: -ByteString.kt */
public final class c {
    @NotNull
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    @NotNull
    public static final char[] f() {
        return a;
    }

    public static final void d(@NotNull f $this$commonWrite, @NotNull okio.c buffer, int offset, int byteCount) {
        k.e($this$commonWrite, "$this$commonWrite");
        k.e(buffer, "buffer");
        buffer.write($this$commonWrite.getData$okio(), offset, byteCount);
    }

    /* access modifiers changed from: private */
    public static final int e(char c) {
        if ('0' <= c && '9' >= c) {
            return c - '0';
        }
        if ('a' <= c && 'f' >= c) {
            return (c - 'a') + 10;
        }
        if ('A' <= c && 'F' >= c) {
            return (c - 'A') + 10;
        }
        throw new IllegalArgumentException("Unexpected hex digit: " + c);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x0186, code lost:
        if (31 < 65533(0xfffd, float:9.1831E-41)) goto L_0x018b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x0192, code lost:
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L_0x0196;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x0194, code lost:
        r16 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x01c9, code lost:
        if (31 < r14) goto L_0x01ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x01d5, code lost:
        if (159 < r14) goto L_0x01d9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x01d7, code lost:
        r16 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x025c, code lost:
        if (r16 == false) goto L_0x0261;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:203:0x029c, code lost:
        if (31 < 65533(0xfffd, float:9.1831E-41)) goto L_0x02a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:208:0x02a8, code lost:
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L_0x02ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:209:0x02aa, code lost:
        r16 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:232:0x02fc, code lost:
        if (31 < 65533(0xfffd, float:9.1831E-41)) goto L_0x0301;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:237:0x0308, code lost:
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L_0x030c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:238:0x030a, code lost:
        r16 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:258:0x0359, code lost:
        if (31 < 65533(0xfffd, float:9.1831E-41)) goto L_0x035e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:263:0x0365, code lost:
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L_0x0369;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:264:0x0367, code lost:
        r16 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:287:0x03a9, code lost:
        if (31 < 65533(0xfffd, float:9.1831E-41)) goto L_0x03ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:292:0x03b5, code lost:
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L_0x03b9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:293:0x03b7, code lost:
        r16 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:298:0x03c4, code lost:
        if (65533(0xfffd, float:9.1831E-41) < 65536(0x10000, float:9.18355E-41)) goto L_0x03ff;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:309:0x03e2, code lost:
        if (31 < r15) goto L_0x03e7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:314:0x03ee, code lost:
        if (159 < r15) goto L_0x03f2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:315:0x03f0, code lost:
        r16 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:320:0x03fd, code lost:
        if (r15 < 65536) goto L_0x03ff;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:383:0x04ce, code lost:
        if (31 < 65533(0xfffd, float:9.1831E-41)) goto L_0x04d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:388:0x04da, code lost:
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L_0x04de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:389:0x04dc, code lost:
        r16 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:412:0x052c, code lost:
        if (31 < 65533(0xfffd, float:9.1831E-41)) goto L_0x0531;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:417:0x0538, code lost:
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L_0x053c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:418:0x053a, code lost:
        r16 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:441:0x058d, code lost:
        if (31 < 65533(0xfffd, float:9.1831E-41)) goto L_0x0592;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:446:0x0599, code lost:
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L_0x059d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:447:0x059b, code lost:
        r16 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:467:0x05ef, code lost:
        if (31 < 65533(0xfffd, float:9.1831E-41)) goto L_0x05f4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:472:0x05fb, code lost:
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L_0x05ff;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:473:0x05fd, code lost:
        r16 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:496:0x0641, code lost:
        if (31 < 65533(0xfffd, float:9.1831E-41)) goto L_0x0646;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:501:0x064d, code lost:
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L_0x0651;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:502:0x064f, code lost:
        r16 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:507:0x065c, code lost:
        if (65533(0xfffd, float:9.1831E-41) < 65536(0x10000, float:9.18355E-41)) goto L_0x065e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:522:0x0685, code lost:
        if (31 < 65533(0xfffd, float:9.1831E-41)) goto L_0x068a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:527:0x0691, code lost:
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L_0x0695;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:528:0x0693, code lost:
        r16 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:533:0x06a0, code lost:
        if (65533(0xfffd, float:9.1831E-41) < 65536(0x10000, float:9.18355E-41)) goto L_0x065e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:544:0x06be, code lost:
        if (31 < r15) goto L_0x06c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:549:0x06ca, code lost:
        if (159 < r15) goto L_0x06ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:550:0x06cc, code lost:
        r16 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:555:0x06d9, code lost:
        if (r15 < 65536) goto L_0x065e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x012d, code lost:
        if (31 < 65533(0xfffd, float:9.1831E-41)) goto L_0x0132;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0139, code lost:
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L_0x013d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x013b, code lost:
        r16 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final int c(byte[] r29, int r30) {
        /*
            r0 = r30
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = r29
            int r5 = r4.length
            r6 = r29
            r7 = 0
            r8 = r3
        L_0x000c:
            if (r8 >= r5) goto L_0x0724
            byte r9 = r6[r8]
            r10 = 127(0x7f, float:1.78E-43)
            r11 = 159(0x9f, float:2.23E-43)
            r12 = 31
            r14 = 13
            r13 = 10
            r15 = 65536(0x10000, float:9.18355E-41)
            r16 = 0
            r17 = 2
            r18 = 1
            if (r9 < 0) goto L_0x00a0
            r19 = r9
            r20 = 0
            int r21 = r2 + 1
            if (r2 != r0) goto L_0x002e
            return r1
        L_0x002e:
            r2 = r19
            if (r2 == r13) goto L_0x0048
            if (r2 == r14) goto L_0x0048
            r19 = 0
            if (r2 < 0) goto L_0x003c
            if (r12 >= r2) goto L_0x0041
        L_0x003c:
            if (r10 <= r2) goto L_0x003f
            goto L_0x0044
        L_0x003f:
            if (r11 < r2) goto L_0x0044
        L_0x0041:
            r19 = r18
            goto L_0x0046
        L_0x0044:
            r19 = r16
        L_0x0046:
            if (r19 != 0) goto L_0x004d
        L_0x0048:
            r11 = 65533(0xfffd, float:9.1831E-41)
            if (r2 != r11) goto L_0x004f
        L_0x004d:
            r10 = -1
            return r10
        L_0x004f:
            if (r2 >= r15) goto L_0x0054
            r11 = r18
            goto L_0x0056
        L_0x0054:
            r11 = r17
        L_0x0056:
            int r1 = r1 + r11
            int r8 = r8 + 1
            r2 = r21
        L_0x005c:
            if (r8 >= r5) goto L_0x009c
            byte r11 = r6[r8]
            if (r11 < 0) goto L_0x009c
            int r11 = r8 + 1
            byte r8 = r6[r8]
            r20 = 0
            int r21 = r2 + 1
            if (r2 != r0) goto L_0x006d
            return r1
        L_0x006d:
            if (r8 == r13) goto L_0x0086
            if (r8 == r14) goto L_0x0086
            r2 = 0
            if (r8 < 0) goto L_0x0078
            if (r12 >= r8) goto L_0x007f
        L_0x0078:
            if (r10 <= r8) goto L_0x007b
            goto L_0x0082
        L_0x007b:
            r10 = 159(0x9f, float:2.23E-43)
            if (r10 < r8) goto L_0x0082
        L_0x007f:
            r2 = r18
            goto L_0x0084
        L_0x0082:
            r2 = r16
        L_0x0084:
            if (r2 != 0) goto L_0x008b
        L_0x0086:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r8 != r2) goto L_0x008d
        L_0x008b:
            r2 = -1
            return r2
        L_0x008d:
            if (r8 >= r15) goto L_0x0092
            r2 = r18
            goto L_0x0094
        L_0x0092:
            r2 = r17
        L_0x0094:
            int r1 = r1 + r2
            r8 = r11
            r2 = r21
            r10 = 127(0x7f, float:1.78E-43)
            goto L_0x005c
        L_0x009c:
            r26 = r3
            goto L_0x071d
        L_0x00a0:
            r10 = 5
            r11 = r9
            r20 = 0
            int r10 = r11 >> r10
            r11 = -2
            if (r10 != r11) goto L_0x01f8
            r10 = r6
            r11 = 0
            int r15 = r8 + 1
            if (r5 > r15) goto L_0x00ef
            r15 = 65533(0xfffd, float:9.1831E-41)
            r21 = 0
            r22 = r15
            r23 = 0
            int r24 = r2 + 1
            if (r2 != r0) goto L_0x00bd
            return r1
        L_0x00bd:
            r2 = r22
            if (r2 == r13) goto L_0x00d7
            if (r2 == r14) goto L_0x00d7
            r13 = 0
            if (r2 < 0) goto L_0x00ca
            if (r12 >= r2) goto L_0x00d3
        L_0x00ca:
            r12 = 127(0x7f, float:1.78E-43)
            if (r12 <= r2) goto L_0x00cf
            goto L_0x00d5
        L_0x00cf:
            r12 = 159(0x9f, float:2.23E-43)
            if (r12 < r2) goto L_0x00d5
        L_0x00d3:
            r16 = r18
        L_0x00d5:
            if (r16 != 0) goto L_0x00dc
        L_0x00d7:
            r12 = 65533(0xfffd, float:9.1831E-41)
            if (r2 != r12) goto L_0x00de
        L_0x00dc:
            r12 = -1
            return r12
        L_0x00de:
            r12 = 65536(0x10000, float:9.18355E-41)
            if (r2 >= r12) goto L_0x00e4
            r17 = r18
        L_0x00e4:
            int r1 = r1 + r17
            kotlin.x r2 = kotlin.x.a
            r26 = r3
            r17 = r18
            goto L_0x01f2
        L_0x00ef:
            byte r15 = r10[r8]
            int r22 = r8 + 1
            byte r12 = r10[r22]
            r22 = 0
            r24 = 192(0xc0, float:2.69E-43)
            r25 = r12
            r26 = 0
            r14 = r25 & r24
            r13 = 128(0x80, float:1.794E-43)
            if (r14 != r13) goto L_0x0106
            r13 = r18
            goto L_0x0108
        L_0x0106:
            r13 = r16
        L_0x0108:
            if (r13 != 0) goto L_0x0157
            r13 = 65533(0xfffd, float:9.1831E-41)
            r14 = 0
            r21 = r13
            r22 = 0
            int r25 = r2 + 1
            if (r2 != r0) goto L_0x0118
            return r1
        L_0x0118:
            r26 = r3
            r2 = r21
            r3 = 10
            if (r2 == r3) goto L_0x013f
            r3 = 13
            if (r2 == r3) goto L_0x013f
            r3 = 0
            if (r2 < 0) goto L_0x0130
            r21 = r3
            r3 = 31
            if (r3 >= r2) goto L_0x013b
            goto L_0x0132
        L_0x0130:
            r21 = r3
        L_0x0132:
            r3 = 127(0x7f, float:1.78E-43)
            if (r3 <= r2) goto L_0x0137
            goto L_0x013d
        L_0x0137:
            r3 = 159(0x9f, float:2.23E-43)
            if (r3 < r2) goto L_0x013d
        L_0x013b:
            r16 = r18
        L_0x013d:
            if (r16 != 0) goto L_0x0144
        L_0x013f:
            r3 = 65533(0xfffd, float:9.1831E-41)
            if (r2 != r3) goto L_0x0146
        L_0x0144:
            r3 = -1
            return r3
        L_0x0146:
            r3 = 65536(0x10000, float:9.18355E-41)
            if (r2 >= r3) goto L_0x014c
            r17 = r18
        L_0x014c:
            int r1 = r1 + r17
            kotlin.x r2 = kotlin.x.a
            r17 = r18
            r24 = r25
            goto L_0x01f2
        L_0x0157:
            r26 = r3
            r3 = r12 ^ 3968(0xf80, float:5.56E-42)
            int r13 = r15 << 6
            r3 = r3 ^ r13
            r13 = 128(0x80, float:1.794E-43)
            if (r3 >= r13) goto L_0x01ae
            r13 = 65533(0xfffd, float:9.1831E-41)
            r14 = 0
            r21 = r13
            r22 = 0
            int r25 = r2 + 1
            if (r2 != r0) goto L_0x0173
            return r1
        L_0x0173:
            r2 = r21
            r4 = 10
            if (r2 == r4) goto L_0x0198
            r4 = 13
            if (r2 == r4) goto L_0x0198
            r4 = 0
            if (r2 < 0) goto L_0x0189
            r21 = r4
            r4 = 31
            if (r4 >= r2) goto L_0x0194
            goto L_0x018b
        L_0x0189:
            r21 = r4
        L_0x018b:
            r4 = 127(0x7f, float:1.78E-43)
            if (r4 <= r2) goto L_0x0190
            goto L_0x0196
        L_0x0190:
            r4 = 159(0x9f, float:2.23E-43)
            if (r4 < r2) goto L_0x0196
        L_0x0194:
            r16 = r18
        L_0x0196:
            if (r16 != 0) goto L_0x019d
        L_0x0198:
            r4 = 65533(0xfffd, float:9.1831E-41)
            if (r2 != r4) goto L_0x019f
        L_0x019d:
            r4 = -1
            return r4
        L_0x019f:
            r4 = 65536(0x10000, float:9.18355E-41)
            if (r2 >= r4) goto L_0x01a4
            goto L_0x01a6
        L_0x01a4:
            r18 = r17
        L_0x01a6:
            int r1 = r1 + r18
            kotlin.x r2 = kotlin.x.a
            r24 = r25
            goto L_0x01f0
        L_0x01ae:
            r4 = r3
            r13 = 0
            r14 = r4
            r21 = 0
            int r22 = r2 + 1
            if (r2 != r0) goto L_0x01b8
            return r1
        L_0x01b8:
            r2 = 10
            if (r14 == r2) goto L_0x01db
            r2 = 13
            if (r14 == r2) goto L_0x01db
            r2 = 0
            if (r14 < 0) goto L_0x01cc
            r24 = r2
            r2 = 31
            if (r2 >= r14) goto L_0x01d7
            goto L_0x01ce
        L_0x01cc:
            r24 = r2
        L_0x01ce:
            r2 = 127(0x7f, float:1.78E-43)
            if (r2 <= r14) goto L_0x01d3
            goto L_0x01d9
        L_0x01d3:
            r2 = 159(0x9f, float:2.23E-43)
            if (r2 < r14) goto L_0x01d9
        L_0x01d7:
            r16 = r18
        L_0x01d9:
            if (r16 != 0) goto L_0x01e0
        L_0x01db:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r14 != r2) goto L_0x01e2
        L_0x01e0:
            r2 = -1
            return r2
        L_0x01e2:
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r14 >= r2) goto L_0x01e7
            goto L_0x01e9
        L_0x01e7:
            r18 = r17
        L_0x01e9:
            int r1 = r1 + r18
            kotlin.x r2 = kotlin.x.a
            r24 = r22
        L_0x01f0:
        L_0x01f2:
            int r8 = r8 + r17
            r2 = r24
            goto L_0x071d
        L_0x01f8:
            r26 = r3
            r3 = 4
            r4 = r9
            r10 = 0
            int r3 = r4 >> r3
            if (r3 != r11) goto L_0x0410
            r3 = r6
            r11 = 0
            int r13 = r8 + 2
            if (r5 > r13) goto L_0x0265
            r4 = 65533(0xfffd, float:9.1831E-41)
            r10 = 0
            r12 = r4
            r13 = 0
            int r14 = r2 + 1
            if (r2 != r0) goto L_0x0212
            return r1
        L_0x0212:
            r2 = 10
            if (r12 == r2) goto L_0x0233
            r2 = 13
            if (r12 == r2) goto L_0x0233
            r2 = 0
            if (r12 < 0) goto L_0x0223
            r15 = 31
            if (r15 >= r12) goto L_0x022c
        L_0x0223:
            r15 = 127(0x7f, float:1.78E-43)
            if (r15 <= r12) goto L_0x0228
            goto L_0x022f
        L_0x0228:
            r15 = 159(0x9f, float:2.23E-43)
            if (r15 < r12) goto L_0x022f
        L_0x022c:
            r2 = r18
            goto L_0x0231
        L_0x022f:
            r2 = r16
        L_0x0231:
            if (r2 != 0) goto L_0x0238
        L_0x0233:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r12 != r2) goto L_0x023a
        L_0x0238:
            r2 = -1
            return r2
        L_0x023a:
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r12 >= r2) goto L_0x0241
            r2 = r18
            goto L_0x0243
        L_0x0241:
            r2 = r17
        L_0x0243:
            int r1 = r1 + r2
            kotlin.x r2 = kotlin.x.a
            int r2 = r8 + 1
            if (r5 <= r2) goto L_0x0261
            int r2 = r8 + 1
            byte r2 = r3[r2]
            r4 = 0
            r10 = 192(0xc0, float:2.69E-43)
            r12 = r2
            r13 = 0
            r10 = r10 & r12
            r12 = 128(0x80, float:1.794E-43)
            if (r10 != r12) goto L_0x025b
            r16 = r18
        L_0x025b:
            if (r16 != 0) goto L_0x025f
            goto L_0x0261
        L_0x025f:
            goto L_0x040b
        L_0x0261:
            r17 = r18
            goto L_0x040b
        L_0x0265:
            byte r13 = r3[r8]
            int r14 = r8 + 1
            byte r14 = r3[r14]
            r15 = 0
            r22 = 192(0xc0, float:2.69E-43)
            r25 = r14
            r27 = 0
            r12 = r25 & r22
            r10 = 128(0x80, float:1.794E-43)
            if (r12 != r10) goto L_0x027b
            r10 = r18
            goto L_0x027d
        L_0x027b:
            r10 = r16
        L_0x027d:
            if (r10 != 0) goto L_0x02c6
            r4 = 65533(0xfffd, float:9.1831E-41)
            r10 = 0
            r12 = r4
            r15 = 0
            int r21 = r2 + 1
            if (r2 != r0) goto L_0x028b
            return r1
        L_0x028b:
            r2 = 10
            if (r12 == r2) goto L_0x02ae
            r2 = 13
            if (r12 == r2) goto L_0x02ae
            r2 = 0
            if (r12 < 0) goto L_0x029f
            r22 = r2
            r2 = 31
            if (r2 >= r12) goto L_0x02aa
            goto L_0x02a1
        L_0x029f:
            r22 = r2
        L_0x02a1:
            r2 = 127(0x7f, float:1.78E-43)
            if (r2 <= r12) goto L_0x02a6
            goto L_0x02ac
        L_0x02a6:
            r2 = 159(0x9f, float:2.23E-43)
            if (r2 < r12) goto L_0x02ac
        L_0x02aa:
            r16 = r18
        L_0x02ac:
            if (r16 != 0) goto L_0x02b3
        L_0x02ae:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r12 != r2) goto L_0x02b5
        L_0x02b3:
            r2 = -1
            return r2
        L_0x02b5:
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r12 >= r2) goto L_0x02bb
            r17 = r18
        L_0x02bb:
            int r1 = r1 + r17
            kotlin.x r2 = kotlin.x.a
            r17 = r18
            r14 = r21
            goto L_0x040b
        L_0x02c6:
            int r10 = r8 + 2
            byte r10 = r3[r10]
            r12 = 0
            r15 = 192(0xc0, float:2.69E-43)
            r25 = r10
            r27 = 0
            r15 = r25 & r15
            r4 = 128(0x80, float:1.794E-43)
            if (r15 != r4) goto L_0x02da
            r4 = r18
            goto L_0x02dc
        L_0x02da:
            r4 = r16
        L_0x02dc:
            if (r4 != 0) goto L_0x0325
            r4 = 65533(0xfffd, float:9.1831E-41)
            r12 = 0
            r15 = r4
            r21 = 0
            int r22 = r2 + 1
            if (r2 != r0) goto L_0x02eb
            return r1
        L_0x02eb:
            r2 = 10
            if (r15 == r2) goto L_0x030e
            r2 = 13
            if (r15 == r2) goto L_0x030e
            r2 = 0
            if (r15 < 0) goto L_0x02ff
            r24 = r2
            r2 = 31
            if (r2 >= r15) goto L_0x030a
            goto L_0x0301
        L_0x02ff:
            r24 = r2
        L_0x0301:
            r2 = 127(0x7f, float:1.78E-43)
            if (r2 <= r15) goto L_0x0306
            goto L_0x030c
        L_0x0306:
            r2 = 159(0x9f, float:2.23E-43)
            if (r2 < r15) goto L_0x030c
        L_0x030a:
            r16 = r18
        L_0x030c:
            if (r16 != 0) goto L_0x0313
        L_0x030e:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r15 != r2) goto L_0x0315
        L_0x0313:
            r2 = -1
            return r2
        L_0x0315:
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r15 >= r2) goto L_0x031a
            goto L_0x031c
        L_0x031a:
            r18 = r17
        L_0x031c:
            int r1 = r1 + r18
            kotlin.x r2 = kotlin.x.a
            r14 = r22
            goto L_0x040b
        L_0x0325:
            r4 = -123008(0xfffffffffffe1f80, float:NaN)
            r4 = r4 ^ r10
            int r12 = r14 << 6
            r4 = r4 ^ r12
            int r12 = r13 << 12
            r4 = r4 ^ r12
            r12 = 2048(0x800, float:2.87E-42)
            if (r4 >= r12) goto L_0x037f
            r12 = 65533(0xfffd, float:9.1831E-41)
            r15 = 0
            r21 = r12
            r22 = 0
            int r25 = r2 + 1
            if (r2 != r0) goto L_0x0344
            return r1
        L_0x0344:
            r2 = r21
            r21 = r3
            r3 = 10
            if (r2 == r3) goto L_0x036b
            r3 = 13
            if (r2 == r3) goto L_0x036b
            r3 = 0
            if (r2 < 0) goto L_0x035c
            r24 = r3
            r3 = 31
            if (r3 >= r2) goto L_0x0367
            goto L_0x035e
        L_0x035c:
            r24 = r3
        L_0x035e:
            r3 = 127(0x7f, float:1.78E-43)
            if (r3 <= r2) goto L_0x0363
            goto L_0x0369
        L_0x0363:
            r3 = 159(0x9f, float:2.23E-43)
            if (r3 < r2) goto L_0x0369
        L_0x0367:
            r16 = r18
        L_0x0369:
            if (r16 != 0) goto L_0x0370
        L_0x036b:
            r3 = 65533(0xfffd, float:9.1831E-41)
            if (r2 != r3) goto L_0x0372
        L_0x0370:
            r3 = -1
            return r3
        L_0x0372:
            r3 = 65536(0x10000, float:9.18355E-41)
            if (r2 >= r3) goto L_0x0378
            r17 = r18
        L_0x0378:
            int r1 = r1 + r17
        L_0x037b:
            kotlin.x r2 = kotlin.x.a
            goto L_0x0406
        L_0x037f:
            r21 = r3
            r3 = 55296(0xd800, float:7.7486E-41)
            if (r3 <= r4) goto L_0x0387
            goto L_0x03c7
        L_0x0387:
            r3 = 57343(0xdfff, float:8.0355E-41)
            if (r3 < r4) goto L_0x03c7
            r3 = 65533(0xfffd, float:9.1831E-41)
            r12 = 0
            r15 = r3
            r22 = 0
            int r25 = r2 + 1
            if (r2 != r0) goto L_0x0398
            return r1
        L_0x0398:
            r2 = 10
            if (r15 == r2) goto L_0x03bb
            r2 = 13
            if (r15 == r2) goto L_0x03bb
            r2 = 0
            if (r15 < 0) goto L_0x03ac
            r24 = r2
            r2 = 31
            if (r2 >= r15) goto L_0x03b7
            goto L_0x03ae
        L_0x03ac:
            r24 = r2
        L_0x03ae:
            r2 = 127(0x7f, float:1.78E-43)
            if (r2 <= r15) goto L_0x03b3
            goto L_0x03b9
        L_0x03b3:
            r2 = 159(0x9f, float:2.23E-43)
            if (r2 < r15) goto L_0x03b9
        L_0x03b7:
            r16 = r18
        L_0x03b9:
            if (r16 != 0) goto L_0x03c0
        L_0x03bb:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r15 != r2) goto L_0x03c2
        L_0x03c0:
            r2 = -1
            return r2
        L_0x03c2:
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r15 >= r2) goto L_0x0401
            goto L_0x03ff
        L_0x03c7:
            r3 = r4
            r12 = 0
            r15 = r3
            r22 = 0
            int r25 = r2 + 1
            if (r2 != r0) goto L_0x03d1
            return r1
        L_0x03d1:
            r2 = 10
            if (r15 == r2) goto L_0x03f4
            r2 = 13
            if (r15 == r2) goto L_0x03f4
            r2 = 0
            if (r15 < 0) goto L_0x03e5
            r24 = r2
            r2 = 31
            if (r2 >= r15) goto L_0x03f0
            goto L_0x03e7
        L_0x03e5:
            r24 = r2
        L_0x03e7:
            r2 = 127(0x7f, float:1.78E-43)
            if (r2 <= r15) goto L_0x03ec
            goto L_0x03f2
        L_0x03ec:
            r2 = 159(0x9f, float:2.23E-43)
            if (r2 < r15) goto L_0x03f2
        L_0x03f0:
            r16 = r18
        L_0x03f2:
            if (r16 != 0) goto L_0x03f9
        L_0x03f4:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r15 != r2) goto L_0x03fb
        L_0x03f9:
            r2 = -1
            return r2
        L_0x03fb:
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r15 >= r2) goto L_0x0401
        L_0x03ff:
            r17 = r18
        L_0x0401:
            int r1 = r1 + r17
            goto L_0x037b
        L_0x0406:
            r14 = r25
            r17 = 3
        L_0x040b:
            int r8 = r8 + r17
            r2 = r14
            goto L_0x071d
        L_0x0410:
            r3 = 3
            r4 = r9
            r10 = 0
            int r3 = r4 >> r3
            if (r3 != r11) goto L_0x06e3
            r3 = r6
            r4 = 0
            int r10 = r8 + 3
            if (r5 > r10) goto L_0x049a
            r10 = 65533(0xfffd, float:9.1831E-41)
            r11 = 0
            r12 = r10
            r13 = 0
            int r14 = r2 + 1
            if (r2 != r0) goto L_0x0428
            return r1
        L_0x0428:
            r2 = 10
            if (r12 == r2) goto L_0x0449
            r2 = 13
            if (r12 == r2) goto L_0x0449
            r2 = 0
            if (r12 < 0) goto L_0x0439
            r15 = 31
            if (r15 >= r12) goto L_0x0442
        L_0x0439:
            r15 = 127(0x7f, float:1.78E-43)
            if (r15 <= r12) goto L_0x043e
            goto L_0x0445
        L_0x043e:
            r15 = 159(0x9f, float:2.23E-43)
            if (r15 < r12) goto L_0x0445
        L_0x0442:
            r2 = r18
            goto L_0x0447
        L_0x0445:
            r2 = r16
        L_0x0447:
            if (r2 != 0) goto L_0x044e
        L_0x0449:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r12 != r2) goto L_0x0450
        L_0x044e:
            r2 = -1
            return r2
        L_0x0450:
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r12 >= r2) goto L_0x0457
            r2 = r18
            goto L_0x0459
        L_0x0457:
            r2 = r17
        L_0x0459:
            int r1 = r1 + r2
            kotlin.x r2 = kotlin.x.a
            int r2 = r8 + 1
            if (r5 <= r2) goto L_0x0496
            int r2 = r8 + 1
            byte r2 = r3[r2]
            r10 = 0
            r11 = 192(0xc0, float:2.69E-43)
            r12 = r2
            r13 = 0
            r11 = r11 & r12
            r12 = 128(0x80, float:1.794E-43)
            if (r11 != r12) goto L_0x0472
            r11 = r18
            goto L_0x0474
        L_0x0472:
            r11 = r16
        L_0x0474:
            if (r11 != 0) goto L_0x0478
            goto L_0x0496
        L_0x0478:
            int r2 = r8 + 2
            if (r5 <= r2) goto L_0x0494
            int r2 = r8 + 2
            byte r2 = r3[r2]
            r10 = 0
            r11 = 192(0xc0, float:2.69E-43)
            r12 = r2
            r13 = 0
            r11 = r11 & r12
            r12 = 128(0x80, float:1.794E-43)
            if (r11 != r12) goto L_0x048c
            r16 = r18
        L_0x048c:
            if (r16 != 0) goto L_0x0490
            goto L_0x0494
        L_0x0490:
            r17 = 3
            goto L_0x06df
        L_0x0494:
            goto L_0x06df
        L_0x0496:
            r17 = r18
            goto L_0x06df
        L_0x049a:
            byte r10 = r3[r8]
            int r11 = r8 + 1
            byte r11 = r3[r11]
            r12 = 0
            r13 = 192(0xc0, float:2.69E-43)
            r14 = r11
            r15 = 0
            r13 = r13 & r14
            r14 = 128(0x80, float:1.794E-43)
            if (r13 != r14) goto L_0x04ad
            r13 = r18
            goto L_0x04af
        L_0x04ad:
            r13 = r16
        L_0x04af:
            if (r13 != 0) goto L_0x04f8
            r12 = 65533(0xfffd, float:9.1831E-41)
            r13 = 0
            r14 = r12
            r15 = 0
            int r21 = r2 + 1
            if (r2 != r0) goto L_0x04bd
            return r1
        L_0x04bd:
            r2 = 10
            if (r14 == r2) goto L_0x04e0
            r2 = 13
            if (r14 == r2) goto L_0x04e0
            r2 = 0
            if (r14 < 0) goto L_0x04d1
            r22 = r2
            r2 = 31
            if (r2 >= r14) goto L_0x04dc
            goto L_0x04d3
        L_0x04d1:
            r22 = r2
        L_0x04d3:
            r2 = 127(0x7f, float:1.78E-43)
            if (r2 <= r14) goto L_0x04d8
            goto L_0x04de
        L_0x04d8:
            r2 = 159(0x9f, float:2.23E-43)
            if (r2 < r14) goto L_0x04de
        L_0x04dc:
            r16 = r18
        L_0x04de:
            if (r16 != 0) goto L_0x04e5
        L_0x04e0:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r14 != r2) goto L_0x04e7
        L_0x04e5:
            r2 = -1
            return r2
        L_0x04e7:
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r14 >= r2) goto L_0x04ed
            r17 = r18
        L_0x04ed:
            int r1 = r1 + r17
            kotlin.x r2 = kotlin.x.a
            r17 = r18
            r14 = r21
            goto L_0x06df
        L_0x04f8:
            int r12 = r8 + 2
            byte r12 = r3[r12]
            r13 = 0
            r14 = 192(0xc0, float:2.69E-43)
            r15 = r12
            r27 = 0
            r14 = r14 & r15
            r15 = 128(0x80, float:1.794E-43)
            if (r14 != r15) goto L_0x050a
            r14 = r18
            goto L_0x050c
        L_0x050a:
            r14 = r16
        L_0x050c:
            if (r14 != 0) goto L_0x0555
            r13 = 65533(0xfffd, float:9.1831E-41)
            r14 = 0
            r15 = r13
            r21 = 0
            int r22 = r2 + 1
            if (r2 != r0) goto L_0x051b
            return r1
        L_0x051b:
            r2 = 10
            if (r15 == r2) goto L_0x053e
            r2 = 13
            if (r15 == r2) goto L_0x053e
            r2 = 0
            if (r15 < 0) goto L_0x052f
            r24 = r2
            r2 = 31
            if (r2 >= r15) goto L_0x053a
            goto L_0x0531
        L_0x052f:
            r24 = r2
        L_0x0531:
            r2 = 127(0x7f, float:1.78E-43)
            if (r2 <= r15) goto L_0x0536
            goto L_0x053c
        L_0x0536:
            r2 = 159(0x9f, float:2.23E-43)
            if (r2 < r15) goto L_0x053c
        L_0x053a:
            r16 = r18
        L_0x053c:
            if (r16 != 0) goto L_0x0543
        L_0x053e:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r15 != r2) goto L_0x0545
        L_0x0543:
            r2 = -1
            return r2
        L_0x0545:
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r15 >= r2) goto L_0x054a
            goto L_0x054c
        L_0x054a:
            r18 = r17
        L_0x054c:
            int r1 = r1 + r18
            kotlin.x r2 = kotlin.x.a
            r14 = r22
            goto L_0x06df
        L_0x0555:
            int r13 = r8 + 3
            byte r13 = r3[r13]
            r14 = 0
            r15 = 192(0xc0, float:2.69E-43)
            r27 = r13
            r28 = 0
            r15 = r27 & r15
            r27 = r3
            r3 = 128(0x80, float:1.794E-43)
            if (r15 != r3) goto L_0x056b
            r3 = r18
            goto L_0x056d
        L_0x056b:
            r3 = r16
        L_0x056d:
            if (r3 != 0) goto L_0x05b7
            r3 = 65533(0xfffd, float:9.1831E-41)
            r14 = 0
            r15 = r3
            r21 = 0
            int r22 = r2 + 1
            if (r2 != r0) goto L_0x057c
            return r1
        L_0x057c:
            r2 = 10
            if (r15 == r2) goto L_0x059f
            r2 = 13
            if (r15 == r2) goto L_0x059f
            r2 = 0
            if (r15 < 0) goto L_0x0590
            r24 = r2
            r2 = 31
            if (r2 >= r15) goto L_0x059b
            goto L_0x0592
        L_0x0590:
            r24 = r2
        L_0x0592:
            r2 = 127(0x7f, float:1.78E-43)
            if (r2 <= r15) goto L_0x0597
            goto L_0x059d
        L_0x0597:
            r2 = 159(0x9f, float:2.23E-43)
            if (r2 < r15) goto L_0x059d
        L_0x059b:
            r16 = r18
        L_0x059d:
            if (r16 != 0) goto L_0x05a4
        L_0x059f:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r15 != r2) goto L_0x05a6
        L_0x05a4:
            r2 = -1
            return r2
        L_0x05a6:
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r15 >= r2) goto L_0x05ac
            r17 = r18
        L_0x05ac:
            int r1 = r1 + r17
            kotlin.x r2 = kotlin.x.a
            r14 = r22
            r17 = 3
            goto L_0x06df
        L_0x05b7:
            r3 = 3678080(0x381f80, float:5.154088E-39)
            r3 = r3 ^ r13
            int r14 = r12 << 6
            r3 = r3 ^ r14
            int r14 = r11 << 12
            r3 = r3 ^ r14
            int r14 = r10 << 18
            r3 = r3 ^ r14
            r14 = 1114111(0x10ffff, float:1.561202E-39)
            if (r3 <= r14) goto L_0x0617
            r14 = 65533(0xfffd, float:9.1831E-41)
            r15 = 0
            r21 = r14
            r22 = 0
            int r25 = r2 + 1
            if (r2 != r0) goto L_0x05da
            return r1
        L_0x05da:
            r2 = r21
            r21 = r4
            r4 = 10
            if (r2 == r4) goto L_0x0601
            r4 = 13
            if (r2 == r4) goto L_0x0601
            r4 = 0
            if (r2 < 0) goto L_0x05f2
            r24 = r4
            r4 = 31
            if (r4 >= r2) goto L_0x05fd
            goto L_0x05f4
        L_0x05f2:
            r24 = r4
        L_0x05f4:
            r4 = 127(0x7f, float:1.78E-43)
            if (r4 <= r2) goto L_0x05f9
            goto L_0x05ff
        L_0x05f9:
            r4 = 159(0x9f, float:2.23E-43)
            if (r4 < r2) goto L_0x05ff
        L_0x05fd:
            r16 = r18
        L_0x05ff:
            if (r16 != 0) goto L_0x0606
        L_0x0601:
            r4 = 65533(0xfffd, float:9.1831E-41)
            if (r2 != r4) goto L_0x0608
        L_0x0606:
            r4 = -1
            return r4
        L_0x0608:
            r4 = 65536(0x10000, float:9.18355E-41)
            if (r2 >= r4) goto L_0x060e
            r17 = r18
        L_0x060e:
            int r1 = r1 + r17
        L_0x0611:
            kotlin.x r2 = kotlin.x.a
            r14 = r25
            goto L_0x06dc
        L_0x0617:
            r21 = r4
            r4 = 55296(0xd800, float:7.7486E-41)
            if (r4 <= r3) goto L_0x061f
            goto L_0x0664
        L_0x061f:
            r4 = 57343(0xdfff, float:8.0355E-41)
            if (r4 < r3) goto L_0x0664
            r4 = 65533(0xfffd, float:9.1831E-41)
            r14 = 0
            r15 = r4
            r22 = 0
            int r25 = r2 + 1
            if (r2 != r0) goto L_0x0630
            return r1
        L_0x0630:
            r2 = 10
            if (r15 == r2) goto L_0x0653
            r2 = 13
            if (r15 == r2) goto L_0x0653
            r2 = 0
            if (r15 < 0) goto L_0x0644
            r24 = r2
            r2 = 31
            if (r2 >= r15) goto L_0x064f
            goto L_0x0646
        L_0x0644:
            r24 = r2
        L_0x0646:
            r2 = 127(0x7f, float:1.78E-43)
            if (r2 <= r15) goto L_0x064b
            goto L_0x0651
        L_0x064b:
            r2 = 159(0x9f, float:2.23E-43)
            if (r2 < r15) goto L_0x0651
        L_0x064f:
            r16 = r18
        L_0x0651:
            if (r16 != 0) goto L_0x0658
        L_0x0653:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r15 != r2) goto L_0x065a
        L_0x0658:
            r2 = -1
            return r2
        L_0x065a:
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r15 >= r2) goto L_0x0660
        L_0x065e:
            r17 = r18
        L_0x0660:
            int r1 = r1 + r17
            goto L_0x0611
        L_0x0664:
            r4 = 65536(0x10000, float:9.18355E-41)
            if (r3 >= r4) goto L_0x06a3
            r4 = 65533(0xfffd, float:9.1831E-41)
            r14 = 0
            r15 = r4
            r22 = 0
            int r25 = r2 + 1
            if (r2 != r0) goto L_0x0674
            return r1
        L_0x0674:
            r2 = 10
            if (r15 == r2) goto L_0x0697
            r2 = 13
            if (r15 == r2) goto L_0x0697
            r2 = 0
            if (r15 < 0) goto L_0x0688
            r24 = r2
            r2 = 31
            if (r2 >= r15) goto L_0x0693
            goto L_0x068a
        L_0x0688:
            r24 = r2
        L_0x068a:
            r2 = 127(0x7f, float:1.78E-43)
            if (r2 <= r15) goto L_0x068f
            goto L_0x0695
        L_0x068f:
            r2 = 159(0x9f, float:2.23E-43)
            if (r2 < r15) goto L_0x0695
        L_0x0693:
            r16 = r18
        L_0x0695:
            if (r16 != 0) goto L_0x069c
        L_0x0697:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r15 != r2) goto L_0x069e
        L_0x069c:
            r2 = -1
            return r2
        L_0x069e:
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r15 >= r2) goto L_0x0660
            goto L_0x065e
        L_0x06a3:
            r4 = r3
            r14 = 0
            r15 = r4
            r22 = 0
            int r25 = r2 + 1
            if (r2 != r0) goto L_0x06ad
            return r1
        L_0x06ad:
            r2 = 10
            if (r15 == r2) goto L_0x06d0
            r2 = 13
            if (r15 == r2) goto L_0x06d0
            r2 = 0
            if (r15 < 0) goto L_0x06c1
            r24 = r2
            r2 = 31
            if (r2 >= r15) goto L_0x06cc
            goto L_0x06c3
        L_0x06c1:
            r24 = r2
        L_0x06c3:
            r2 = 127(0x7f, float:1.78E-43)
            if (r2 <= r15) goto L_0x06c8
            goto L_0x06ce
        L_0x06c8:
            r2 = 159(0x9f, float:2.23E-43)
            if (r2 < r15) goto L_0x06ce
        L_0x06cc:
            r16 = r18
        L_0x06ce:
            if (r16 != 0) goto L_0x06d5
        L_0x06d0:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r15 != r2) goto L_0x06d7
        L_0x06d5:
            r2 = -1
            return r2
        L_0x06d7:
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r15 >= r2) goto L_0x0660
            goto L_0x065e
        L_0x06dc:
            r17 = 4
        L_0x06df:
            int r8 = r8 + r17
            r2 = r14
            goto L_0x071d
        L_0x06e3:
            r3 = 65533(0xfffd, float:9.1831E-41)
            r4 = 0
            int r10 = r2 + 1
            if (r2 != r0) goto L_0x06ec
            return r1
        L_0x06ec:
            r2 = 10
            if (r3 == r2) goto L_0x070a
            r2 = 13
            if (r3 == r2) goto L_0x070a
            r2 = 0
            if (r3 < 0) goto L_0x06fd
            r11 = 31
            if (r11 >= r3) goto L_0x0706
        L_0x06fd:
            r11 = 127(0x7f, float:1.78E-43)
            if (r11 <= r3) goto L_0x0702
            goto L_0x0708
        L_0x0702:
            r11 = 159(0x9f, float:2.23E-43)
            if (r11 < r3) goto L_0x0708
        L_0x0706:
            r16 = r18
        L_0x0708:
            if (r16 != 0) goto L_0x070f
        L_0x070a:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r3 != r2) goto L_0x0711
        L_0x070f:
            r2 = -1
            return r2
        L_0x0711:
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r3 >= r2) goto L_0x0717
            r17 = r18
        L_0x0717:
            int r1 = r1 + r17
            int r8 = r8 + 1
            r2 = r10
        L_0x071d:
            r4 = r29
            r3 = r26
            goto L_0x000c
        L_0x0724:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.c.c(byte[], int):int");
    }
}
