package io.ktor.utils.io.charsets;

import java.nio.ByteBuffer;
import kotlin.jvm.internal.k;
import kotlin.text.a;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: UTF.kt */
public final class c {
    public static final long k(int numberOfChars, int requireBytes) {
        return (((long) numberOfChars) << 32) | (((long) requireBytes) & 4294967295L);
    }

    public static final long h(@NotNull ByteBuffer $this$decodeUTF8Line, @NotNull char[] out, int offset, int length) {
        k.f($this$decodeUTF8Line, "$this$decodeUTF8Line");
        k.f(out, "out");
        if ($this$decodeUTF8Line.hasArray()) {
            return i($this$decodeUTF8Line, out, offset, length);
        }
        return j($this$decodeUTF8Line, out, offset, length);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0224, code lost:
        r5.position((r15 - 4) - r5.arrayOffset());
        r19 = k(r12 - r2, -1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final long i(@org.jetbrains.annotations.NotNull java.nio.ByteBuffer r27, char[] r28, int r29, int r30) {
        /*
            r0 = r27
            r1 = r28
            r2 = r29
            r3 = r30
            r4 = 0
            r5 = r27
            r6 = 0
            byte[] r7 = r5.array()
            if (r7 != 0) goto L_0x0015
            kotlin.jvm.internal.k.n()
        L_0x0015:
            int r8 = r5.arrayOffset()
            int r9 = r5.position()
            int r8 = r8 + r9
            int r9 = r5.remaining()
            int r9 = r9 + r8
            if (r8 > r9) goto L_0x0027
            r12 = 1
            goto L_0x0028
        L_0x0027:
            r12 = 0
        L_0x0028:
            java.lang.String r13 = "Failed requirement."
            if (r12 == 0) goto L_0x02d5
            int r12 = r7.length
            if (r9 > r12) goto L_0x0031
            r12 = 1
            goto L_0x0032
        L_0x0031:
            r12 = 0
        L_0x0032:
            if (r12 == 0) goto L_0x02c7
            r12 = r29
            int r13 = r2 + r3
            int r14 = r1.length
            if (r13 > r14) goto L_0x02bf
        L_0x003b:
            r15 = 13
            if (r8 >= r9) goto L_0x0258
            if (r12 >= r13) goto L_0x0258
            int r10 = r8 + 1
            byte r8 = r7[r8]
            r17 = r8
            if (r8 < 0) goto L_0x0087
            char r11 = (char) r8
            r18 = r11
            r19 = 0
            r14 = r18
            if (r14 != r15) goto L_0x0057
            r4 = 1
            r14 = 1
            goto L_0x0063
        L_0x0057:
            r15 = 10
            if (r14 != r15) goto L_0x005e
            r4 = 0
            r14 = 0
            goto L_0x0063
        L_0x005e:
            if (r4 == 0) goto L_0x0062
            r14 = 0
            goto L_0x0063
        L_0x0062:
            r14 = 1
        L_0x0063:
            if (r14 != 0) goto L_0x0079
            int r14 = r10 + -1
            int r15 = r5.arrayOffset()
            int r14 = r14 - r15
            r5.position(r14)
            int r14 = r12 - r2
            r15 = -1
            long r19 = k(r14, r15)
            goto L_0x0270
        L_0x0079:
            int r14 = r12 + 1
            r1[r12] = r11
            r21 = r6
            r23 = r7
            r25 = r9
            r8 = r10
            r12 = r14
            goto L_0x021c
        L_0x0087:
            r11 = r17
            r14 = r11 & 224(0xe0, float:3.14E-43)
            r15 = 192(0xc0, float:2.69E-43)
            if (r14 != r15) goto L_0x00f1
            if (r10 < r9) goto L_0x00a4
            int r14 = r10 + -1
            int r15 = r5.arrayOffset()
            int r14 = r14 - r15
            r5.position(r14)
            int r14 = r12 - r2
            r15 = 2
            long r19 = k(r14, r15)
            goto L_0x0270
        L_0x00a4:
            int r14 = r10 + 1
            byte r10 = r7[r10]
            r15 = r11 & 31
            int r15 = r15 << 6
            r17 = r10 & 63
            r15 = r15 | r17
            char r15 = (char) r15
            r17 = r15
            r19 = 0
            r21 = r6
            r6 = r17
            r17 = r10
            r10 = 13
            if (r6 != r10) goto L_0x00c3
            r4 = 1
            r6 = 1
            goto L_0x00cf
        L_0x00c3:
            r10 = 10
            if (r6 != r10) goto L_0x00ca
            r4 = 0
            r6 = 0
            goto L_0x00cf
        L_0x00ca:
            if (r4 == 0) goto L_0x00ce
            r6 = 0
            goto L_0x00cf
        L_0x00ce:
            r6 = 1
        L_0x00cf:
            if (r6 != 0) goto L_0x00e5
            int r6 = r14 + -2
            int r10 = r5.arrayOffset()
            int r6 = r6 - r10
            r5.position(r6)
            int r6 = r12 - r2
            r10 = -1
            long r19 = k(r6, r10)
            goto L_0x0270
        L_0x00e5:
            int r6 = r12 + 1
            r1[r12] = r15
            r12 = r6
            r23 = r7
            r25 = r9
            r8 = r14
            goto L_0x021c
        L_0x00f1:
            r21 = r6
            r6 = r11 & 240(0xf0, float:3.36E-43)
            r14 = 3
            r15 = 224(0xe0, float:3.14E-43)
            r17 = 0
            if (r6 != r15) goto L_0x017d
            int r6 = r9 - r10
            r15 = 2
            if (r6 >= r15) goto L_0x0113
            int r6 = r10 + -1
            int r15 = r5.arrayOffset()
            int r6 = r6 - r15
            r5.position(r6)
            int r6 = r12 - r2
            long r19 = k(r6, r14)
            goto L_0x0270
        L_0x0113:
            int r6 = r10 + 1
            byte r10 = r7[r10]
            int r14 = r6 + 1
            byte r6 = r7[r6]
            r15 = r11 & 15
            int r19 = r15 << 12
            r22 = r10 & 63
            int r22 = r22 << 6
            r19 = r19 | r22
            r22 = r6 & 63
            r23 = r6
            r6 = r19 | r22
            if (r15 == 0) goto L_0x0138
            boolean r19 = n(r6)
            if (r19 == 0) goto L_0x0134
            goto L_0x0138
        L_0x0134:
            java.lang.Void unused = q(r6)
            throw r17
        L_0x0138:
            r19 = r10
            char r10 = (char) r6
            r17 = r10
            r22 = 0
            r24 = r6
            r25 = r15
            r6 = r17
            r15 = 13
            if (r6 != r15) goto L_0x014d
            r4 = 1
            r6 = 1
            goto L_0x0159
        L_0x014d:
            r15 = 10
            if (r6 != r15) goto L_0x0154
            r4 = 0
            r6 = 0
            goto L_0x0159
        L_0x0154:
            if (r4 == 0) goto L_0x0158
            r6 = 0
            goto L_0x0159
        L_0x0158:
            r6 = 1
        L_0x0159:
            if (r6 != 0) goto L_0x0171
            int r6 = r14 + -4
            int r15 = r5.arrayOffset()
            int r6 = r6 - r15
            r5.position(r6)
            int r6 = r12 - r2
            r15 = -1
            long r16 = k(r6, r15)
            r19 = r16
            goto L_0x0270
        L_0x0171:
            int r6 = r12 + 1
            r1[r12] = r10
            r12 = r6
            r23 = r7
            r25 = r9
            r8 = r14
            goto L_0x021c
        L_0x017d:
            r6 = r11 & 248(0xf8, float:3.48E-43)
            r15 = 240(0xf0, float:3.36E-43)
            if (r6 != r15) goto L_0x0254
            int r6 = r9 - r10
            if (r6 >= r14) goto L_0x019a
            int r6 = r10 + -1
            int r14 = r5.arrayOffset()
            int r6 = r6 - r14
            r5.position(r6)
            int r6 = r12 - r2
            r14 = 4
            long r19 = k(r6, r14)
            goto L_0x0270
        L_0x019a:
            int r6 = r10 + 1
            byte r10 = r7[r10]
            int r14 = r6 + 1
            byte r6 = r7[r6]
            int r15 = r14 + 1
            byte r14 = r7[r14]
            r19 = r11 & 7
            int r19 = r19 << 18
            r22 = r10 & 63
            int r22 = r22 << 12
            r19 = r19 | r22
            r22 = r6 & 63
            int r22 = r22 << 6
            r19 = r19 | r22
            r22 = r14 & 63
            r19 = r19 | r22
            boolean r22 = o(r19)
            if (r22 == 0) goto L_0x0250
            r22 = r6
            int r6 = r13 - r12
            r23 = r7
            r7 = 2
            if (r6 < r7) goto L_0x0238
            int r6 = l(r19)
            char r6 = (char) r6
            int r7 = p(r19)
            char r7 = (char) r7
            r17 = r6
            r24 = 0
            r25 = r9
            r26 = r10
            r9 = r17
            r10 = 13
            if (r9 != r10) goto L_0x01e7
            r4 = 1
            r9 = 1
            goto L_0x01f3
        L_0x01e7:
            r10 = 10
            if (r9 != r10) goto L_0x01ee
            r4 = 0
            r9 = 0
            goto L_0x01f3
        L_0x01ee:
            if (r4 == 0) goto L_0x01f2
            r9 = 0
            goto L_0x01f3
        L_0x01f2:
            r9 = 1
        L_0x01f3:
            if (r9 == 0) goto L_0x0224
            r9 = r7
            r10 = 0
            r17 = r10
            r10 = 13
            if (r9 != r10) goto L_0x0202
            r4 = 1
            r9 = 1
            goto L_0x020e
        L_0x0202:
            r10 = 10
            if (r9 != r10) goto L_0x0209
            r4 = 0
            r9 = 0
            goto L_0x020e
        L_0x0209:
            if (r4 == 0) goto L_0x020d
            r9 = 0
            goto L_0x020e
        L_0x020d:
            r9 = 1
        L_0x020e:
            if (r9 != 0) goto L_0x0212
            goto L_0x0224
        L_0x0212:
            int r9 = r12 + 1
            r1[r12] = r6
            int r10 = r9 + 1
            r1[r9] = r7
            r12 = r10
            r8 = r15
        L_0x021c:
            r6 = r21
            r7 = r23
            r9 = r25
            goto L_0x003b
        L_0x0224:
            int r9 = r15 + -4
            int r10 = r5.arrayOffset()
            int r9 = r9 - r10
            r5.position(r9)
            int r9 = r12 - r2
            r10 = -1
            long r16 = k(r9, r10)
            r19 = r16
            goto L_0x0270
        L_0x0238:
            r25 = r9
            r26 = r10
            int r6 = r15 + -4
            int r7 = r5.arrayOffset()
            int r6 = r6 - r7
            r5.position(r6)
            int r6 = r12 - r2
            r7 = 0
            long r6 = k(r6, r7)
            r19 = r6
            goto L_0x0270
        L_0x0250:
            java.lang.Void unused = q(r19)
            throw r17
        L_0x0254:
            java.lang.Void unused = r(r8)
            throw r17
        L_0x0258:
            r21 = r6
            r23 = r7
            r25 = r9
            int r6 = r5.arrayOffset()
            int r6 = r8 - r6
            r7 = 1
            int r6 = r6 + r7
            r5.position(r6)
            int r6 = r12 - r2
            r7 = 0
            long r19 = k(r6, r7)
        L_0x0270:
            r5 = r19
            r7 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r7 = r7 & r5
            int r7 = (int) r7
            r8 = 32
            r9 = -1
            if (r7 != r9) goto L_0x02a5
            long r10 = r5 >> r8
            int r8 = (int) r10
            if (r4 == 0) goto L_0x028a
            int r10 = r8 + -1
            long r9 = k(r10, r9)
            return r9
        L_0x028a:
            int r9 = r27.position()
            r10 = 1
            int r9 = r9 + r10
            r0.position(r9)
            if (r8 <= 0) goto L_0x02bd
            int r9 = r8 + -1
            char r9 = r1[r9]
            r10 = 13
            if (r9 != r10) goto L_0x02bd
            int r9 = r8 + -1
            r10 = -1
            long r9 = k(r9, r10)
            return r9
        L_0x02a5:
            if (r7 != 0) goto L_0x02bd
            if (r4 == 0) goto L_0x02bd
            long r8 = r5 >> r8
            int r8 = (int) r8
            int r9 = r27.position()
            r10 = 1
            int r9 = r9 - r10
            r0.position(r9)
            int r9 = r8 + -1
            r10 = 2
            long r9 = k(r9, r10)
            return r9
        L_0x02bd:
            return r5
        L_0x02bf:
            r21 = r6
            int r6 = r1.length
            java.lang.Throwable r6 = m(r2, r3, r6)
            throw r6
        L_0x02c7:
            r21 = r6
            r23 = r7
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.String r7 = r13.toString()
            r6.<init>(r7)
            throw r6
        L_0x02d5:
            r21 = r6
            r23 = r7
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.String r7 = r13.toString()
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.charsets.c.i(java.nio.ByteBuffer, char[], int, int):long");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:88:0x01e5, code lost:
        r5.position(r5.position() - 4);
        r17 = k(r7 - r2, -1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final long j(@org.jetbrains.annotations.NotNull java.nio.ByteBuffer r24, char[] r25, int r26, int r27) {
        /*
            r0 = r24
            r1 = r25
            r2 = r26
            r3 = r27
            r4 = 0
            r5 = r24
            r6 = 0
            r7 = r26
            int r8 = r2 + r3
            int r9 = r1.length
            if (r8 > r9) goto L_0x026d
        L_0x0013:
            boolean r9 = r5.hasRemaining()
            r11 = 13
            r14 = 1
            if (r9 == 0) goto L_0x0213
            if (r7 >= r8) goto L_0x0213
            byte r9 = r5.get()
            r15 = r9
            r13 = 10
            if (r9 < 0) goto L_0x005e
            char r10 = (char) r9
            r17 = r10
            r18 = 0
            r12 = r17
            if (r12 != r11) goto L_0x0035
            r4 = 1
            r13 = r14
            goto L_0x003f
        L_0x0035:
            if (r12 != r13) goto L_0x003a
            r4 = 0
            r13 = 0
            goto L_0x003f
        L_0x003a:
            if (r4 == 0) goto L_0x003e
            r13 = 0
            goto L_0x003f
        L_0x003e:
            r13 = r14
        L_0x003f:
            if (r13 != 0) goto L_0x0053
            int r12 = r5.position()
            int r12 = r12 - r14
            r5.position(r12)
            int r12 = r7 - r2
            r13 = -1
            long r17 = k(r12, r13)
            goto L_0x021e
        L_0x0053:
            int r11 = r7 + 1
            r1[r7] = r10
            r22 = r6
            r23 = r8
            r7 = r11
            goto L_0x01df
        L_0x005e:
            r10 = r15 & 224(0xe0, float:3.14E-43)
            r12 = 192(0xc0, float:2.69E-43)
            if (r10 != r12) goto L_0x00be
            boolean r10 = r5.hasRemaining()
            if (r10 != 0) goto L_0x007b
            int r10 = r5.position()
            int r10 = r10 - r14
            r5.position(r10)
            int r10 = r7 - r2
            r12 = 2
            long r17 = k(r10, r12)
            goto L_0x021e
        L_0x007b:
            byte r10 = r5.get()
            r12 = r15 & 31
            int r12 = r12 << 6
            r17 = r10 & 63
            r12 = r12 | r17
            char r12 = (char) r12
            r17 = r12
            r18 = 0
            r14 = r17
            if (r14 != r11) goto L_0x0094
            r4 = 1
            r13 = 1
            goto L_0x009e
        L_0x0094:
            if (r14 != r13) goto L_0x0099
            r4 = 0
            r13 = 0
            goto L_0x009e
        L_0x0099:
            if (r4 == 0) goto L_0x009d
            r13 = 0
            goto L_0x009e
        L_0x009d:
            r13 = 1
        L_0x009e:
            if (r13 != 0) goto L_0x00b3
            int r13 = r5.position()
            r14 = 2
            int r13 = r13 - r14
            r5.position(r13)
            int r13 = r7 - r2
            r14 = -1
            long r17 = k(r13, r14)
            goto L_0x021e
        L_0x00b3:
            int r11 = r7 + 1
            r1[r7] = r12
            r22 = r6
            r23 = r8
            r7 = r11
            goto L_0x01df
        L_0x00be:
            r10 = r15 & 240(0xf0, float:3.36E-43)
            r12 = 224(0xe0, float:3.14E-43)
            r14 = 3
            r17 = 0
            if (r10 != r12) goto L_0x0141
            int r10 = r5.remaining()
            r12 = 2
            if (r10 >= r12) goto L_0x00df
            int r10 = r5.position()
            r12 = 1
            int r10 = r10 - r12
            r5.position(r10)
            int r10 = r7 - r2
            long r17 = k(r10, r14)
            goto L_0x021e
        L_0x00df:
            byte r10 = r5.get()
            byte r12 = r5.get()
            r18 = r15 & 15
            int r19 = r18 << 12
            r20 = r10 & 63
            int r20 = r20 << 6
            r19 = r19 | r20
            r20 = r12 & 63
            r14 = r19 | r20
            if (r18 == 0) goto L_0x0102
            boolean r19 = n(r14)
            if (r19 == 0) goto L_0x00fe
            goto L_0x0102
        L_0x00fe:
            java.lang.Void unused = q(r14)
            throw r17
        L_0x0102:
            char r13 = (char) r14
            r17 = r13
            r20 = 0
            r22 = r6
            r6 = r17
            if (r6 != r11) goto L_0x0112
            r4 = 1
            r16 = 1
            goto L_0x0121
        L_0x0112:
            r11 = 10
            if (r6 != r11) goto L_0x011a
            r4 = 0
            r16 = 0
            goto L_0x0121
        L_0x011a:
            if (r4 == 0) goto L_0x011f
            r16 = 0
            goto L_0x0121
        L_0x011f:
            r16 = 1
        L_0x0121:
            if (r16 != 0) goto L_0x0138
            int r6 = r5.position()
            r11 = 3
            int r6 = r6 - r11
            r5.position(r6)
            int r6 = r7 - r2
            r11 = -1
            long r19 = k(r6, r11)
            r17 = r19
            goto L_0x021e
        L_0x0138:
            int r6 = r7 + 1
            r1[r7] = r13
            r7 = r6
            r23 = r8
            goto L_0x01df
        L_0x0141:
            r22 = r6
            r6 = r15 & 248(0xf8, float:3.48E-43)
            r10 = 240(0xf0, float:3.36E-43)
            if (r6 != r10) goto L_0x020f
            int r6 = r5.remaining()
            r10 = 4
            r11 = 3
            if (r6 >= r11) goto L_0x0162
            int r6 = r5.position()
            r11 = 1
            int r6 = r6 - r11
            r5.position(r6)
            int r6 = r7 - r2
            long r17 = k(r6, r10)
            goto L_0x021e
        L_0x0162:
            byte r6 = r5.get()
            byte r11 = r5.get()
            byte r12 = r5.get()
            r13 = r15 & 7
            int r13 = r13 << 18
            r14 = r6 & 63
            int r14 = r14 << 12
            r13 = r13 | r14
            r14 = r11 & 63
            int r14 = r14 << 6
            r13 = r13 | r14
            r14 = r12 & 63
            r13 = r13 | r14
            boolean r14 = o(r13)
            if (r14 == 0) goto L_0x020b
            int r14 = r8 - r7
            r10 = 2
            if (r14 < r10) goto L_0x01f6
            int r10 = l(r13)
            char r10 = (char) r10
            int r14 = p(r13)
            char r14 = (char) r14
            r17 = r10
            r20 = 0
            r21 = r6
            r23 = r8
            r6 = r17
            r8 = 13
            if (r6 != r8) goto L_0x01a8
            r4 = 1
            r6 = 1
            goto L_0x01b4
        L_0x01a8:
            r8 = 10
            if (r6 != r8) goto L_0x01af
            r4 = 0
            r6 = 0
            goto L_0x01b4
        L_0x01af:
            if (r4 == 0) goto L_0x01b3
            r6 = 0
            goto L_0x01b4
        L_0x01b3:
            r6 = 1
        L_0x01b4:
            if (r6 == 0) goto L_0x01e5
            r6 = r14
            r8 = 0
            r17 = r8
            r8 = 13
            if (r6 != r8) goto L_0x01c4
            r4 = 1
            r16 = 1
            goto L_0x01d3
        L_0x01c4:
            r8 = 10
            if (r6 != r8) goto L_0x01cc
            r4 = 0
            r16 = 0
            goto L_0x01d3
        L_0x01cc:
            if (r4 == 0) goto L_0x01d1
            r16 = 0
            goto L_0x01d3
        L_0x01d1:
            r16 = 1
        L_0x01d3:
            if (r16 != 0) goto L_0x01d7
            goto L_0x01e5
        L_0x01d7:
            int r6 = r7 + 1
            r1[r7] = r10
            int r7 = r6 + 1
            r1[r6] = r14
        L_0x01df:
            r6 = r22
            r8 = r23
            goto L_0x0013
        L_0x01e5:
            int r6 = r5.position()
            r8 = 4
            int r6 = r6 - r8
            r5.position(r6)
            int r6 = r7 - r2
            r8 = -1
            long r17 = k(r6, r8)
            goto L_0x021e
        L_0x01f6:
            r21 = r6
            r23 = r8
            r8 = 4
            int r6 = r5.position()
            int r6 = r6 - r8
            r5.position(r6)
            int r6 = r7 - r2
            r8 = 0
            long r17 = k(r6, r8)
            goto L_0x021e
        L_0x020b:
            java.lang.Void unused = q(r13)
            throw r17
        L_0x020f:
            java.lang.Void unused = r(r9)
            throw r17
        L_0x0213:
            r22 = r6
            r23 = r8
            int r6 = r7 - r2
            r8 = 0
            long r17 = k(r6, r8)
        L_0x021e:
            r5 = r17
            r7 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r7 = r7 & r5
            int r7 = (int) r7
            r8 = 32
            r9 = -1
            if (r7 != r9) goto L_0x0253
            long r10 = r5 >> r8
            int r8 = (int) r10
            if (r4 == 0) goto L_0x0238
            int r10 = r8 + -1
            long r9 = k(r10, r9)
            return r9
        L_0x0238:
            int r9 = r24.position()
            r10 = 1
            int r9 = r9 + r10
            r0.position(r9)
            if (r8 <= 0) goto L_0x026b
            int r9 = r8 + -1
            char r9 = r1[r9]
            r10 = 13
            if (r9 != r10) goto L_0x026b
            int r9 = r8 + -1
            r10 = -1
            long r9 = k(r9, r10)
            return r9
        L_0x0253:
            if (r7 != 0) goto L_0x026b
            if (r4 == 0) goto L_0x026b
            long r8 = r5 >> r8
            int r8 = (int) r8
            int r9 = r24.position()
            r10 = 1
            int r9 = r9 - r10
            r0.position(r9)
            int r9 = r8 + -1
            r10 = 2
            long r9 = k(r9, r10)
            return r9
        L_0x026b:
            return r5
        L_0x026d:
            r22 = r6
            int r6 = r1.length
            java.lang.Throwable r6 = m(r2, r3, r6)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.charsets.c.j(java.nio.ByteBuffer, char[], int, int):long");
    }

    /* access modifiers changed from: private */
    public static final boolean n(int cp) {
        return (cp >>> 16) == 0;
    }

    /* access modifiers changed from: private */
    public static final boolean o(int codePoint) {
        return codePoint <= 1114111;
    }

    /* access modifiers changed from: private */
    public static final int p(int cp) {
        return (cp & 1023) + 56320;
    }

    /* access modifiers changed from: private */
    public static final int l(int cp) {
        return (cp >>> 10) + 55232;
    }

    /* access modifiers changed from: private */
    public static final Throwable m(int offset, int length, int arrayLength) {
        return new IndexOutOfBoundsException(offset + " (offset) + " + length + " (length) > " + arrayLength + " (array.length)");
    }

    /* access modifiers changed from: private */
    public static final Void q(int value) {
        throw new IllegalArgumentException("Malformed code-point " + Integer.toHexString(value) + " found");
    }

    /* access modifiers changed from: private */
    public static final Void r(byte b) {
        StringBuilder sb = new StringBuilder();
        sb.append("Unsupported byte code, first byte is 0x");
        String num = Integer.toString(b & 255, a.a(16));
        k.b(num, "java.lang.Integer.toStri…(this, checkRadix(radix))");
        sb.append(x.q0(num, 2, '0'));
        throw new IllegalStateException(sb.toString().toString());
    }
}
