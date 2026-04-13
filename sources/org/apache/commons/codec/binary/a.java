package org.apache.commons.codec.binary;

import org.apache.commons.codec.binary.b;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.http.util.Constants;

/* compiled from: Base64 */
public class a extends b {
    static final byte[] f = {13, 10};
    private static final byte[] g = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] h = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
    private static final byte[] i = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 55, 56, 57, 58, Constants.SEMI_COLON, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, MappingData.PATH, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, Constants.COMMA, 45, 46, 47, 48, 49, 50, 51};
    private final byte[] j;
    private final byte[] k;
    private final byte[] l;
    private final int m;
    private final int n;

    public a() {
        this(0);
    }

    public a(boolean urlSafe) {
        this(76, f, urlSafe);
    }

    public a(int lineLength) {
        this(lineLength, f);
    }

    public a(int lineLength, byte[] lineSeparator) {
        this(lineLength, lineSeparator, false);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public a(int lineLength, byte[] lineSeparator, boolean urlSafe) {
        super(3, 4, lineLength, lineSeparator == null ? 0 : lineSeparator.length);
        this.k = i;
        if (lineSeparator == null) {
            this.n = 4;
            this.l = null;
        } else if (b(lineSeparator)) {
            String sep = c.b(lineSeparator);
            throw new IllegalArgumentException("lineSeparator must not contain base64 characters: [" + sep + "]");
        } else if (lineLength > 0) {
            this.n = lineSeparator.length + 4;
            byte[] bArr = new byte[lineSeparator.length];
            this.l = bArr;
            System.arraycopy(lineSeparator, 0, bArr, 0, lineSeparator.length);
        } else {
            this.n = 4;
            this.l = null;
        }
        this.m = this.n - 1;
        this.j = urlSafe ? h : g;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v6, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v7, resolved type: byte} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void e(byte[] r11, int r12, int r13, org.apache.commons.codec.binary.b.a r14) {
        /*
            r10 = this;
            boolean r0 = r14.f
            if (r0 == 0) goto L_0x0005
            return
        L_0x0005:
            r0 = 0
            r1 = 1
            if (r13 >= 0) goto L_0x00bf
            r14.f = r1
            int r1 = r14.h
            if (r1 != 0) goto L_0x0014
            int r1 = r10.d
            if (r1 != 0) goto L_0x0014
            return
        L_0x0014:
            int r1 = r10.n
            byte[] r1 = r10.g(r1, r14)
            int r2 = r14.d
            int r3 = r14.h
            r4 = 61
            switch(r3) {
                case 0: goto L_0x00a0;
                case 1: goto L_0x0071;
                case 2: goto L_0x003c;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Impossible modulus "
            r3.append(r4)
            int r4 = r14.h
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r0.<init>(r3)
            throw r0
        L_0x003c:
            int r3 = r14.d
            int r5 = r3 + 1
            r14.d = r5
            byte[] r6 = r10.j
            int r7 = r14.a
            int r8 = r7 >> 10
            r8 = r8 & 63
            byte r8 = r6[r8]
            r1[r3] = r8
            int r3 = r5 + 1
            r14.d = r3
            int r8 = r7 >> 4
            r8 = r8 & 63
            byte r8 = r6[r8]
            r1[r5] = r8
            int r5 = r3 + 1
            r14.d = r5
            int r7 = r7 << 2
            r7 = r7 & 63
            byte r7 = r6[r7]
            r1[r3] = r7
            byte[] r3 = g
            if (r6 != r3) goto L_0x00a1
            int r3 = r5 + 1
            r14.d = r3
            r1[r5] = r4
            goto L_0x00a1
        L_0x0071:
            int r3 = r14.d
            int r5 = r3 + 1
            r14.d = r5
            byte[] r6 = r10.j
            int r7 = r14.a
            int r8 = r7 >> 2
            r8 = r8 & 63
            byte r8 = r6[r8]
            r1[r3] = r8
            int r3 = r5 + 1
            r14.d = r3
            int r7 = r7 << 4
            r7 = r7 & 63
            byte r7 = r6[r7]
            r1[r5] = r7
            byte[] r5 = g
            if (r6 != r5) goto L_0x00a1
            int r5 = r3 + 1
            r14.d = r5
            r1[r3] = r4
            int r3 = r5 + 1
            r14.d = r3
            r1[r5] = r4
            goto L_0x00a1
        L_0x00a0:
        L_0x00a1:
            int r3 = r14.g
            int r4 = r14.d
            int r5 = r4 - r2
            int r3 = r3 + r5
            r14.g = r3
            int r5 = r10.d
            if (r5 <= 0) goto L_0x00be
            if (r3 <= 0) goto L_0x00be
            byte[] r3 = r10.l
            int r5 = r3.length
            java.lang.System.arraycopy(r3, r0, r1, r4, r5)
            int r0 = r14.d
            byte[] r3 = r10.l
            int r3 = r3.length
            int r0 = r0 + r3
            r14.d = r0
        L_0x00be:
            goto L_0x0132
        L_0x00bf:
            r2 = 0
        L_0x00c0:
            if (r2 >= r13) goto L_0x0132
            int r3 = r10.n
            byte[] r3 = r10.g(r3, r14)
            int r4 = r14.h
            int r4 = r4 + r1
            int r4 = r4 % 3
            r14.h = r4
            int r5 = r12 + 1
            byte r12 = r11[r12]
            if (r12 >= 0) goto L_0x00d7
            int r12 = r12 + 256
        L_0x00d7:
            int r6 = r14.a
            int r6 = r6 << 8
            int r6 = r6 + r12
            r14.a = r6
            if (r4 != 0) goto L_0x012e
            int r4 = r14.d
            int r7 = r4 + 1
            r14.d = r7
            byte[] r8 = r10.j
            int r9 = r6 >> 18
            r9 = r9 & 63
            byte r9 = r8[r9]
            r3[r4] = r9
            int r4 = r7 + 1
            r14.d = r4
            int r9 = r6 >> 12
            r9 = r9 & 63
            byte r9 = r8[r9]
            r3[r7] = r9
            int r7 = r4 + 1
            r14.d = r7
            int r9 = r6 >> 6
            r9 = r9 & 63
            byte r9 = r8[r9]
            r3[r4] = r9
            int r4 = r7 + 1
            r14.d = r4
            r6 = r6 & 63
            byte r6 = r8[r6]
            r3[r7] = r6
            int r6 = r14.g
            int r6 = r6 + 4
            r14.g = r6
            int r7 = r10.d
            if (r7 <= 0) goto L_0x012e
            if (r7 > r6) goto L_0x012e
            byte[] r6 = r10.l
            int r7 = r6.length
            java.lang.System.arraycopy(r6, r0, r3, r4, r7)
            int r4 = r14.d
            byte[] r6 = r10.l
            int r6 = r6.length
            int r4 = r4 + r6
            r14.d = r4
            r14.g = r0
        L_0x012e:
            int r2 = r2 + 1
            r12 = r5
            goto L_0x00c0
        L_0x0132:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.binary.a.e(byte[], int, int, org.apache.commons.codec.binary.b$a):void");
    }

    /* access modifiers changed from: package-private */
    public void c(byte[] in, int inPos, int inAvail, b.a context) {
        byte result;
        if (!context.f) {
            if (inAvail < 0) {
                context.f = true;
            }
            int i2 = 0;
            while (true) {
                if (i2 >= inAvail) {
                    break;
                }
                byte[] buffer = g(this.m, context);
                int inPos2 = inPos + 1;
                byte inPos3 = in[inPos];
                if (inPos3 == 61) {
                    context.f = true;
                    int i3 = inPos2;
                    break;
                }
                if (inPos3 >= 0) {
                    byte[] bArr = i;
                    if (inPos3 < bArr.length && (result = bArr[inPos3]) >= 0) {
                        int i4 = (context.h + 1) % 4;
                        context.h = i4;
                        int i5 = (context.a << 6) + result;
                        context.a = i5;
                        if (i4 == 0) {
                            int i6 = context.d;
                            int i7 = i6 + 1;
                            context.d = i7;
                            buffer[i6] = (byte) ((i5 >> 16) & 255);
                            int i8 = i7 + 1;
                            context.d = i8;
                            buffer[i7] = (byte) ((i5 >> 8) & 255);
                            context.d = i8 + 1;
                            buffer[i8] = (byte) (i5 & 255);
                        }
                    }
                }
                i2++;
                inPos = inPos2;
            }
            if (context.f && context.h != 0) {
                byte[] buffer2 = g(this.m, context);
                switch (context.h) {
                    case 1:
                        return;
                    case 2:
                        int i9 = context.a >> 4;
                        context.a = i9;
                        int i10 = context.d;
                        context.d = i10 + 1;
                        buffer2[i10] = (byte) (i9 & 255);
                        return;
                    case 3:
                        int i11 = context.a >> 2;
                        context.a = i11;
                        int i12 = context.d;
                        int i13 = i12 + 1;
                        context.d = i13;
                        buffer2[i12] = (byte) ((i11 >> 8) & 255);
                        context.d = i13 + 1;
                        buffer2[i13] = (byte) (i11 & 255);
                        return;
                    default:
                        throw new IllegalStateException("Impossible modulus " + context.h);
                }
            }
        }
    }

    public static byte[] n(byte[] binaryData) {
        return o(binaryData, false);
    }

    public static byte[] o(byte[] binaryData, boolean isChunked) {
        return p(binaryData, isChunked, false);
    }

    public static byte[] p(byte[] binaryData, boolean isChunked, boolean urlSafe) {
        return q(binaryData, isChunked, urlSafe, Integer.MAX_VALUE);
    }

    public static byte[] q(byte[] binaryData, boolean isChunked, boolean urlSafe, int maxResultSize) {
        a b64;
        if (binaryData == null || binaryData.length == 0) {
            return binaryData;
        }
        if (!isChunked) {
            b64 = new a(0, f, urlSafe);
        }
        long len = b64.i(binaryData);
        if (len <= ((long) maxResultSize)) {
            return b64.f(binaryData);
        }
        throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + len + ") than the specified maximum size of " + maxResultSize);
    }

    public static byte[] m(byte[] base64Data) {
        return new a().d(base64Data);
    }

    /* access modifiers changed from: protected */
    public boolean j(byte octet) {
        if (octet >= 0) {
            byte[] bArr = this.k;
            return octet < bArr.length && bArr[octet] != -1;
        }
    }
}
