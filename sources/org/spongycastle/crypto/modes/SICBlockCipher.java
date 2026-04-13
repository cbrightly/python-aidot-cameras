package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.SkippingStreamCipher;
import org.spongycastle.crypto.StreamBlockCipher;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Pack;

public class SICBlockCipher extends StreamBlockCipher implements SkippingStreamCipher {
    private final BlockCipher b;
    private final int c;
    private byte[] d;
    private byte[] e;
    private byte[] f;
    private int g = 0;

    public SICBlockCipher(BlockCipher c2) {
        super(c2);
        this.b = c2;
        int c3 = c2.c();
        this.c = c3;
        this.d = new byte[c3];
        this.e = new byte[c3];
        this.f = new byte[c3];
    }

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof ParametersWithIV) {
            ParametersWithIV ivParam = (ParametersWithIV) params;
            byte[] h = Arrays.h(ivParam.a());
            this.d = h;
            int i = this.c;
            if (i >= h.length) {
                int i2 = 8;
                if (8 > i / 2) {
                    i2 = i / 2;
                }
                int maxCounterSize = i2;
                if (i - h.length <= maxCounterSize) {
                    if (ivParam.b() != null) {
                        this.b.a(true, ivParam.b());
                    }
                    reset();
                    return;
                }
                throw new IllegalArgumentException("CTR/SIC mode requires IV of at least: " + (this.c - maxCounterSize) + " bytes.");
            }
            throw new IllegalArgumentException("CTR/SIC mode requires IV no greater than: " + this.c + " bytes.");
        }
        throw new IllegalArgumentException("CTR/SIC mode requires ParametersWithIV");
    }

    public String b() {
        return this.b.b() + "/SIC";
    }

    public int c() {
        return this.b.c();
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        d(in, inOff, this.c, out, outOff);
        return this.c;
    }

    /* access modifiers changed from: protected */
    public byte g(byte in) {
        int i = this.g;
        if (i == 0) {
            this.b.f(this.e, 0, this.f, 0);
            byte[] bArr = this.f;
            int i2 = this.g;
            this.g = i2 + 1;
            return (byte) (bArr[i2] ^ in);
        }
        byte[] bArr2 = this.f;
        int i3 = i + 1;
        this.g = i3;
        byte rv = (byte) (bArr2[i] ^ in);
        if (i3 == this.e.length) {
            this.g = 0;
            m(0);
            j();
        }
        return rv;
    }

    private void j() {
        if (this.d.length < this.c) {
            int i = 0;
            while (true) {
                byte[] bArr = this.d;
                if (i == bArr.length) {
                    return;
                }
                if (this.e[i] == bArr[i]) {
                    i++;
                } else {
                    throw new IllegalStateException("Counter in CTR/SIC mode out of range.");
                }
            }
        }
    }

    private void m(int pos) {
        byte b2;
        int i = this.e.length - pos;
        do {
            i--;
            if (i >= 0) {
                byte[] bArr = this.e;
                b2 = (byte) (bArr[i] + 1);
                bArr[i] = b2;
            } else {
                return;
            }
        } while (b2 == 0);
    }

    private void l(int offSet) {
        byte[] bArr = this.e;
        byte old = bArr[bArr.length - 1];
        int length = bArr.length - 1;
        bArr[length] = (byte) (bArr[length] + offSet);
        if (old != 0 && bArr[bArr.length - 1] < old) {
            m(1);
        }
    }

    private void k(int pos) {
        byte b2;
        int i = this.e.length - pos;
        do {
            i--;
            if (i >= 0) {
                byte[] bArr = this.e;
                b2 = (byte) (bArr[i] - 1);
                bArr[i] = b2;
            } else {
                return;
            }
        } while (b2 == -1);
    }

    private void i(long n) {
        if (n >= 0) {
            long numBlocks = (((long) this.g) + n) / ((long) this.c);
            long rem = numBlocks;
            if (rem > 255) {
                for (int i = 5; i >= 1; i--) {
                    long diff = 1 << (i * 8);
                    while (rem >= diff) {
                        m(i);
                        rem -= diff;
                    }
                }
            }
            l((int) rem);
            this.g = (int) ((((long) this.g) + n) - (((long) this.c) * numBlocks));
            return;
        }
        long numBlocks2 = ((-n) - ((long) this.g)) / ((long) this.c);
        long rem2 = numBlocks2;
        if (rem2 > 255) {
            for (int i2 = 5; i2 >= 1; i2--) {
                long diff2 = 1 << (i2 * 8);
                while (rem2 > diff2) {
                    k(i2);
                    rem2 -= diff2;
                }
            }
        }
        for (long i3 = 0; i3 != rem2; i3++) {
            k(0);
        }
        int gap = (int) (((long) this.g) + n + (((long) this.c) * numBlocks2));
        if (gap >= 0) {
            this.g = 0;
            return;
        }
        k(0);
        this.g = this.c + gap;
    }

    public void reset() {
        Arrays.F(this.e, (byte) 0);
        byte[] bArr = this.d;
        System.arraycopy(bArr, 0, this.e, 0, bArr.length);
        this.b.reset();
        this.g = 0;
    }

    public long skip(long numberOfBytes) {
        i(numberOfBytes);
        j();
        this.b.f(this.e, 0, this.f, 0);
        return numberOfBytes;
    }

    public long seekTo(long position) {
        reset();
        return skip(position);
    }

    public long getPosition() {
        int v;
        byte[] bArr = this.e;
        byte[] res = new byte[bArr.length];
        System.arraycopy(bArr, 0, res, 0, res.length);
        for (int i = res.length - 1; i >= 1; i--) {
            byte[] bArr2 = this.d;
            if (i < bArr2.length) {
                v = (res[i] & 255) - (bArr2[i] & 255);
            } else {
                v = res[i] & 255;
            }
            if (v < 0) {
                int i2 = i - 1;
                res[i2] = (byte) (res[i2] - 1);
                v += 256;
            }
            res[i] = (byte) v;
        }
        return (Pack.c(res, res.length - 8) * ((long) this.c)) + ((long) this.g);
    }
}
