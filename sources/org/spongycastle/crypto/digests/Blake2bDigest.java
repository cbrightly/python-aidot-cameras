package org.spongycastle.crypto.digests;

import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.util.Arrays;

public class Blake2bDigest implements ExtendedDigest {
    private static final long[] a = {7640891576956012808L, -4942790177534073029L, 4354685564936845355L, -6534734903238641935L, 5840696475078001361L, -7276294671716946913L, 2270897969802886507L, 6620516959819538809L};
    private static final byte[][] b = {new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, new byte[]{14, 10, 4, 8, 9, 15, 13, 6, 1, 12, 0, 2, 11, 7, 5, 3}, new byte[]{11, 8, 12, 0, 5, 2, 15, 13, 10, 14, 3, 6, 7, 1, 9, 4}, new byte[]{7, 9, 3, 1, 13, 12, 11, 14, 2, 6, 5, 10, 4, 0, 15, 8}, new byte[]{9, 0, 5, 7, 2, 4, 10, 15, 14, 1, 11, 12, 6, 8, 3, 13}, new byte[]{2, 12, 6, 10, 0, 11, 8, 3, 4, 13, 7, 5, 15, 14, 1, 9}, new byte[]{12, 5, 1, 15, 14, 13, 4, 10, 0, 7, 6, 3, 9, 2, 8, 11}, new byte[]{13, 11, 7, 14, 12, 1, 3, 9, 5, 0, 15, 4, 8, 6, 2, 10}, new byte[]{6, 15, 14, 9, 11, 3, 0, 8, 12, 2, 13, 7, 1, 4, 10, 5}, new byte[]{10, 2, 8, 4, 7, 6, 1, 5, 15, 11, 9, 14, 3, 12, 13, 0}, new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, new byte[]{14, 10, 4, 8, 9, 15, 13, 6, 1, 12, 0, 2, 11, 7, 5, 3}};
    private static int c = 12;
    private int d;
    private int e;
    private byte[] f;
    private byte[] g;
    private byte[] h;
    private byte[] i;
    private int j;
    private long[] k;
    private long[] l;
    private long m;
    private long n;
    private long o;

    public Blake2bDigest() {
        this(512);
    }

    public Blake2bDigest(Blake2bDigest digest) {
        this.d = 64;
        this.e = 0;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = 0;
        this.k = new long[16];
        this.l = null;
        this.m = 0;
        this.n = 0;
        this.o = 0;
        this.j = digest.j;
        this.i = Arrays.h(digest.i);
        this.e = digest.e;
        this.h = Arrays.h(digest.h);
        this.d = digest.d;
        this.l = Arrays.l(digest.l);
        this.g = Arrays.h(digest.g);
        this.f = Arrays.h(digest.f);
        this.m = digest.m;
        this.n = digest.n;
        this.o = digest.o;
    }

    public Blake2bDigest(int digestSize) {
        this.d = 64;
        this.e = 0;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = 0;
        this.k = new long[16];
        this.l = null;
        this.m = 0;
        this.n = 0;
        this.o = 0;
        if (digestSize == 160 || digestSize == 256 || digestSize == 384 || digestSize == 512) {
            this.i = new byte[128];
            this.e = 0;
            this.d = digestSize / 8;
            p();
            return;
        }
        throw new IllegalArgumentException("Blake2b digest restricted to one of [160, 256, 384, 512]");
    }

    private void p() {
        if (this.l == null) {
            long[] jArr = new long[8];
            this.l = jArr;
            long[] jArr2 = a;
            jArr[0] = jArr2[0] ^ ((long) ((this.d | (this.e << 8)) | 16842752));
            jArr[1] = jArr2[1];
            jArr[2] = jArr2[2];
            jArr[3] = jArr2[3];
            jArr[4] = jArr2[4];
            jArr[5] = jArr2[5];
            byte[] bArr = this.f;
            if (bArr != null) {
                jArr[4] = jArr[4] ^ n(bArr, 0);
                long[] jArr3 = this.l;
                jArr3[5] = jArr3[5] ^ n(this.f, 8);
            }
            long[] jArr4 = this.l;
            jArr4[6] = jArr2[6];
            jArr4[7] = jArr2[7];
            byte[] bArr2 = this.g;
            if (bArr2 != null) {
                jArr4[6] = n(bArr2, 0) ^ jArr4[6];
                long[] jArr5 = this.l;
                jArr5[7] = jArr5[7] ^ n(this.g, 8);
            }
        }
    }

    private void q() {
        long[] jArr = this.l;
        System.arraycopy(jArr, 0, this.k, 0, jArr.length);
        long[] jArr2 = a;
        System.arraycopy(jArr2, 0, this.k, this.l.length, 4);
        long[] jArr3 = this.k;
        jArr3[12] = this.m ^ jArr2[4];
        jArr3[13] = this.n ^ jArr2[5];
        jArr3[14] = this.o ^ jArr2[6];
        jArr3[15] = jArr2[7];
    }

    public void d(byte b2) {
        int i2 = this.j;
        if (128 - i2 == 0) {
            long j2 = this.m + 128;
            this.m = j2;
            if (j2 == 0) {
                this.n++;
            }
            o(this.i, 0);
            Arrays.F(this.i, (byte) 0);
            this.i[0] = b2;
            this.j = 1;
            return;
        }
        this.i[i2] = b2;
        this.j = i2 + 1;
    }

    public void update(byte[] message, int offset, int len) {
        if (message != null && len != 0) {
            int remainingLength = 0;
            int i2 = this.j;
            if (i2 != 0) {
                remainingLength = 128 - i2;
                if (remainingLength < len) {
                    System.arraycopy(message, offset, this.i, i2, remainingLength);
                    long j2 = this.m + 128;
                    this.m = j2;
                    if (j2 == 0) {
                        this.n++;
                    }
                    o(this.i, 0);
                    this.j = 0;
                    Arrays.F(this.i, (byte) 0);
                } else {
                    System.arraycopy(message, offset, this.i, i2, len);
                    this.j += len;
                    return;
                }
            }
            int blockWiseLastPos = (offset + len) - 128;
            int messagePos = offset + remainingLength;
            while (messagePos < blockWiseLastPos) {
                long j3 = this.m + 128;
                this.m = j3;
                if (j3 == 0) {
                    this.n++;
                }
                o(message, messagePos);
                messagePos += 128;
            }
            System.arraycopy(message, messagePos, this.i, 0, (offset + len) - messagePos);
            this.j += (offset + len) - messagePos;
        }
    }

    public int c(byte[] out, int outOffset) {
        long[] jArr;
        this.o = -1;
        long j2 = this.m;
        int i2 = this.j;
        long j3 = j2 + ((long) i2);
        this.m = j3;
        if (j3 < 0 && ((long) i2) > (-j3)) {
            this.n++;
        }
        o(this.i, 0);
        Arrays.F(this.i, (byte) 0);
        Arrays.G(this.k, 0);
        int i3 = 0;
        while (true) {
            jArr = this.l;
            if (i3 >= jArr.length || i3 * 8 >= this.d) {
                Arrays.G(jArr, 0);
                reset();
            } else {
                byte[] bytes = r(jArr[i3]);
                int i4 = i3 * 8;
                int i5 = this.d;
                if (i4 < i5 - 8) {
                    System.arraycopy(bytes, 0, out, (i3 * 8) + outOffset, 8);
                } else {
                    System.arraycopy(bytes, 0, out, (i3 * 8) + outOffset, i5 - (i3 * 8));
                }
                i3++;
            }
        }
        Arrays.G(jArr, 0);
        reset();
        return this.d;
    }

    public void reset() {
        this.j = 0;
        this.o = 0;
        this.m = 0;
        this.n = 0;
        this.l = null;
        Arrays.F(this.i, (byte) 0);
        byte[] bArr = this.h;
        if (bArr != null) {
            System.arraycopy(bArr, 0, this.i, 0, bArr.length);
            this.j = 128;
        }
        p();
    }

    private void o(byte[] message, int messagePos) {
        q();
        long[] m2 = new long[16];
        for (int j2 = 0; j2 < 16; j2++) {
            m2[j2] = n(message, (j2 * 8) + messagePos);
        }
        for (int round = 0; round < c; round++) {
            byte[][] bArr = b;
            m(m2[bArr[round][0]], m2[bArr[round][1]], 0, 4, 8, 12);
            m(m2[bArr[round][2]], m2[bArr[round][3]], 1, 5, 9, 13);
            m(m2[bArr[round][4]], m2[bArr[round][5]], 2, 6, 10, 14);
            m(m2[bArr[round][6]], m2[bArr[round][7]], 3, 7, 11, 15);
            m(m2[bArr[round][8]], m2[bArr[round][9]], 0, 5, 10, 15);
            m(m2[bArr[round][10]], m2[bArr[round][11]], 1, 6, 11, 12);
            m(m2[bArr[round][12]], m2[bArr[round][13]], 2, 7, 8, 13);
            m(m2[bArr[round][14]], m2[bArr[round][15]], 3, 4, 9, 14);
        }
        int offset = 0;
        while (true) {
            long[] jArr = this.l;
            if (offset < jArr.length) {
                long j3 = jArr[offset];
                long[] jArr2 = this.k;
                jArr[offset] = (j3 ^ jArr2[offset]) ^ jArr2[offset + 8];
                offset++;
            } else {
                return;
            }
        }
    }

    private void m(long m1, long m2, int posA, int posB, int posC, int posD) {
        long[] jArr = this.k;
        jArr[posA] = jArr[posA] + jArr[posB] + m1;
        jArr[posD] = s(jArr[posD] ^ jArr[posA], 32);
        long[] jArr2 = this.k;
        jArr2[posC] = jArr2[posC] + jArr2[posD];
        jArr2[posB] = s(jArr2[posB] ^ jArr2[posC], 24);
        long[] jArr3 = this.k;
        jArr3[posA] = jArr3[posA] + jArr3[posB] + m2;
        jArr3[posD] = s(jArr3[posD] ^ jArr3[posA], 16);
        long[] jArr4 = this.k;
        jArr4[posC] = jArr4[posC] + jArr4[posD];
        jArr4[posB] = s(jArr4[posB] ^ jArr4[posC], 63);
    }

    private long s(long x, int rot) {
        return (x >>> rot) | (x << (64 - rot));
    }

    private final byte[] r(long longValue) {
        return new byte[]{(byte) ((int) longValue), (byte) ((int) (longValue >> 8)), (byte) ((int) (longValue >> 16)), (byte) ((int) (longValue >> 24)), (byte) ((int) (longValue >> 32)), (byte) ((int) (longValue >> 40)), (byte) ((int) (longValue >> 48)), (byte) ((int) (longValue >> 56))};
    }

    private final long n(byte[] byteArray, int offset) {
        return (((long) byteArray[offset]) & 255) | ((((long) byteArray[offset + 1]) & 255) << 8) | ((((long) byteArray[offset + 2]) & 255) << 16) | ((((long) byteArray[offset + 3]) & 255) << 24) | ((((long) byteArray[offset + 4]) & 255) << 32) | ((((long) byteArray[offset + 5]) & 255) << 40) | ((((long) byteArray[offset + 6]) & 255) << 48) | ((255 & ((long) byteArray[offset + 7])) << 56);
    }

    public String b() {
        return "Blake2b";
    }

    public int e() {
        return this.d;
    }

    public int k() {
        return 128;
    }
}
