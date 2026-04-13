package org.spongycastle.crypto.digests;

import com.alibaba.fastjson.asm.Opcodes;
import com.leedarson.serviceimpl.business.bean.OTACommand;
import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Pack;

public class KeccakDigest implements ExtendedDigest {
    private static long[] a = x();
    private static int[] b = w();
    protected long[] c;
    protected byte[] d;
    protected int e;
    protected int f;
    protected int g;
    protected boolean h;

    private static long[] x() {
        long[] keccakRoundConstants = new long[24];
        byte[] LFSRstate = {1};
        for (int i = 0; i < 24; i++) {
            keccakRoundConstants[i] = 0;
            for (int j = 0; j < 7; j++) {
                int bitPosition = (1 << j) - 1;
                if (p(LFSRstate)) {
                    keccakRoundConstants[i] = keccakRoundConstants[i] ^ (1 << bitPosition);
                }
            }
        }
        return keccakRoundConstants;
    }

    private static boolean p(byte[] LFSR) {
        boolean result = (LFSR[0] & 1) != 0;
        if ((LFSR[0] & OTACommand.RESP_ID_VERSION) != 0) {
            LFSR[0] = (byte) ((LFSR[0] << 1) ^ 113);
        } else {
            LFSR[0] = (byte) (LFSR[0] << 1);
        }
        return result;
    }

    private static int[] w() {
        int[] keccakRhoOffsets = new int[25];
        keccakRhoOffsets[0] = 0;
        int x = 1;
        int y = 0;
        for (int t = 0; t < 24; t++) {
            keccakRhoOffsets[(x % 5) + ((y % 5) * 5)] = (((t + 1) * (t + 2)) / 2) % 64;
            x = ((x * 0) + (y * 1)) % 5;
            y = ((x * 2) + (y * 3)) % 5;
        }
        return keccakRhoOffsets;
    }

    public KeccakDigest() {
        this(288);
    }

    public KeccakDigest(int bitLength) {
        this.c = new long[25];
        this.d = new byte[Opcodes.CHECKCAST];
        t(bitLength);
    }

    public KeccakDigest(KeccakDigest source) {
        long[] jArr = new long[25];
        this.c = jArr;
        this.d = new byte[Opcodes.CHECKCAST];
        long[] jArr2 = source.c;
        System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
        byte[] bArr = source.d;
        System.arraycopy(bArr, 0, this.d, 0, bArr.length);
        this.e = source.e;
        this.f = source.f;
        this.g = source.g;
        this.h = source.h;
    }

    public String b() {
        return "Keccak-" + this.g;
    }

    public int e() {
        return this.g / 8;
    }

    public void d(byte in) {
        q(new byte[]{in}, 0, 1);
    }

    public void update(byte[] in, int inOff, int len) {
        q(in, inOff, len);
    }

    public int c(byte[] out, int outOff) {
        C(out, outOff, (long) this.g);
        reset();
        return e();
    }

    public void reset() {
        t(this.g);
    }

    public int k() {
        return this.e / 8;
    }

    private void t(int bitLength) {
        switch (bitLength) {
            case 128:
            case 224:
            case 256:
            case 288:
            case 384:
            case 512:
                u(1600 - (bitLength << 1));
                return;
            default:
                throw new IllegalArgumentException("bitLength must be one of 128, 224, 256, 288, 384, or 512.");
        }
    }

    private void u(int rate) {
        if (rate <= 0 || rate >= 1600 || rate % 64 != 0) {
            throw new IllegalStateException("invalid rate value");
        }
        this.e = rate;
        int i = 0;
        while (true) {
            long[] jArr = this.c;
            if (i < jArr.length) {
                jArr[i] = 0;
                i++;
            } else {
                Arrays.F(this.d, (byte) 0);
                this.f = 0;
                this.h = false;
                this.g = (1600 - rate) / 2;
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void q(byte[] data, int off, int len) {
        int i = this.f;
        if (i % 8 != 0) {
            throw new IllegalStateException("attempt to absorb with odd length queue");
        } else if (!this.h) {
            int bytesInQueue = i >> 3;
            int rateBytes = this.e >> 3;
            int count = 0;
            while (count < len) {
                if (bytesInQueue != 0 || count > len - rateBytes) {
                    int partialBlock = Math.min(rateBytes - bytesInQueue, len - count);
                    System.arraycopy(data, off + count, this.d, bytesInQueue, partialBlock);
                    bytesInQueue += partialBlock;
                    count += partialBlock;
                    if (bytesInQueue == rateBytes) {
                        m(this.d, 0);
                        bytesInQueue = 0;
                    }
                } else {
                    do {
                        m(data, off + count);
                        count += rateBytes;
                    } while (count <= len - rateBytes);
                }
            }
            this.f = bytesInQueue << 3;
        } else {
            throw new IllegalStateException("attempt to absorb while squeezing");
        }
    }

    /* access modifiers changed from: protected */
    public void r(int data, int bits) {
        if (bits < 1 || bits > 7) {
            throw new IllegalArgumentException("'bits' must be in the range 1 to 7");
        }
        int i = this.f;
        if (i % 8 != 0) {
            throw new IllegalStateException("attempt to absorb with odd length queue");
        } else if (!this.h) {
            this.d[i >> 3] = (byte) (data & ((1 << bits) - 1));
            this.f = i + bits;
        } else {
            throw new IllegalStateException("attempt to absorb while squeezing");
        }
    }

    private void z() {
        byte[] bArr = this.d;
        int i = this.f;
        int i2 = i >> 3;
        bArr[i2] = (byte) (bArr[i2] | ((byte) ((int) (1 << (i & 7)))));
        int i3 = i + 1;
        this.f = i3;
        if (i3 == this.e) {
            m(bArr, 0);
            this.f = 0;
        }
        int i4 = this.f;
        int full = i4 >> 6;
        int partial = i4 & 63;
        int off = 0;
        for (int i5 = 0; i5 < full; i5++) {
            long[] jArr = this.c;
            jArr[i5] = jArr[i5] ^ Pack.m(this.d, off);
            off += 8;
        }
        if (partial > 0) {
            long[] jArr2 = this.c;
            jArr2[full] = jArr2[full] ^ (Pack.m(this.d, off) & ((1 << partial) - 1));
        }
        long[] jArr3 = this.c;
        int i6 = (this.e - 1) >> 6;
        jArr3[i6] = jArr3[i6] ^ Long.MIN_VALUE;
        o();
        n();
        this.f = this.e;
        this.h = true;
    }

    /* access modifiers changed from: protected */
    public void C(byte[] output, int offset, long outputLength) {
        if (!this.h) {
            z();
        }
        if (outputLength % 8 == 0) {
            long i = 0;
            while (i < outputLength) {
                if (this.f == 0) {
                    o();
                    n();
                    this.f = this.e;
                }
                int partialBlock = (int) Math.min((long) this.f, outputLength - i);
                System.arraycopy(this.d, (this.e - this.f) / 8, output, ((int) (i / 8)) + offset, partialBlock / 8);
                this.f -= partialBlock;
                i += (long) partialBlock;
            }
            return;
        }
        throw new IllegalStateException("outputLength not a multiple of 8");
    }

    private void m(byte[] data, int off) {
        int count = this.e >> 6;
        for (int i = 0; i < count; i++) {
            long[] jArr = this.c;
            jArr[i] = jArr[i] ^ Pack.m(data, off);
            off += 8;
        }
        o();
    }

    private void n() {
        Pack.s(this.c, 0, this.e >> 6, this.d, 0);
    }

    private void o() {
        for (int i = 0; i < 24; i++) {
            D(this.c);
            B(this.c);
            A(this.c);
            s(this.c);
            v(this.c, i);
        }
    }

    private static long y(long v, int r) {
        return (v << r) | (v >>> (-r));
    }

    private static void D(long[] A) {
        long C0 = (((A[0] ^ A[5]) ^ A[10]) ^ A[15]) ^ A[20];
        long C1 = (((A[1] ^ A[6]) ^ A[11]) ^ A[16]) ^ A[21];
        long C2 = (((A[2] ^ A[7]) ^ A[12]) ^ A[17]) ^ A[22];
        long C3 = (((A[3] ^ A[8]) ^ A[13]) ^ A[18]) ^ A[23];
        long C4 = (((A[4] ^ A[9]) ^ A[14]) ^ A[19]) ^ A[24];
        long dX = y(C1, 1) ^ C4;
        A[0] = A[0] ^ dX;
        A[5] = A[5] ^ dX;
        A[10] = A[10] ^ dX;
        A[15] = A[15] ^ dX;
        A[20] = A[20] ^ dX;
        long dX2 = y(C2, 1) ^ C0;
        A[1] = A[1] ^ dX2;
        A[6] = A[6] ^ dX2;
        A[11] = A[11] ^ dX2;
        A[16] = A[16] ^ dX2;
        A[21] = A[21] ^ dX2;
        long C32 = C3;
        long dX3 = y(C32, 1) ^ C1;
        A[2] = A[2] ^ dX3;
        A[7] = A[7] ^ dX3;
        A[12] = A[12] ^ dX3;
        A[17] = A[17] ^ dX3;
        A[22] = A[22] ^ dX3;
        long dX4 = y(C4, 1) ^ C2;
        A[3] = A[3] ^ dX4;
        A[8] = A[8] ^ dX4;
        A[13] = A[13] ^ dX4;
        A[18] = A[18] ^ dX4;
        A[23] = A[23] ^ dX4;
        long dX5 = y(C0, 1) ^ C32;
        A[4] = A[4] ^ dX5;
        A[9] = A[9] ^ dX5;
        A[14] = A[14] ^ dX5;
        A[19] = A[19] ^ dX5;
        A[24] = A[24] ^ dX5;
    }

    private static void B(long[] A) {
        for (int x = 1; x < 25; x++) {
            A[x] = y(A[x], b[x]);
        }
    }

    private static void A(long[] A) {
        long a1 = A[1];
        A[1] = A[6];
        A[6] = A[9];
        A[9] = A[22];
        A[22] = A[14];
        A[14] = A[20];
        A[20] = A[2];
        A[2] = A[12];
        A[12] = A[13];
        A[13] = A[19];
        A[19] = A[23];
        A[23] = A[15];
        A[15] = A[4];
        A[4] = A[24];
        A[24] = A[21];
        A[21] = A[8];
        A[8] = A[16];
        A[16] = A[5];
        A[5] = A[3];
        A[3] = A[18];
        A[18] = A[17];
        A[17] = A[11];
        A[11] = A[7];
        A[7] = A[10];
        A[10] = a1;
    }

    private static void s(long[] A) {
        for (int yBy5 = 0; yBy5 < 25; yBy5 += 5) {
            long chiC0 = A[yBy5 + 0] ^ ((~A[yBy5 + 1]) & A[yBy5 + 2]);
            long chiC1 = A[yBy5 + 1] ^ ((~A[yBy5 + 2]) & A[yBy5 + 3]);
            long chiC2 = A[yBy5 + 2] ^ ((~A[yBy5 + 3]) & A[yBy5 + 4]);
            long chiC3 = A[yBy5 + 3] ^ ((~A[yBy5 + 4]) & A[yBy5 + 0]);
            long chiC4 = A[yBy5 + 4] ^ ((~A[yBy5 + 0]) & A[yBy5 + 1]);
            A[yBy5 + 0] = chiC0;
            A[yBy5 + 1] = chiC1;
            A[yBy5 + 2] = chiC2;
            A[yBy5 + 3] = chiC3;
            A[yBy5 + 4] = chiC4;
        }
    }

    private static void v(long[] A, int indexRound) {
        A[0] = A[0] ^ a[indexRound];
    }
}
