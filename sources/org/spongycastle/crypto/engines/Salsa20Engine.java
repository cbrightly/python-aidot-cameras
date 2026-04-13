package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.MaxBytesExceededException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.SkippingStreamCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Pack;
import org.spongycastle.util.Strings;

public class Salsa20Engine implements SkippingStreamCipher {
    private static final int[] a = Pack.l(Strings.f("expand 16-byte kexpand 32-byte k"), 0, 8);
    protected static final byte[] b = Strings.f("expand 32-byte k");
    protected static final byte[] c = Strings.f("expand 16-byte k");
    protected int d;
    private int e;
    protected int[] f;
    protected int[] g;
    private byte[] h;
    private boolean i;
    private int j;
    private int k;
    private int l;

    /* access modifiers changed from: protected */
    public void l(int keyLength, int[] state, int stateOffset) {
        int tsOff = (keyLength - 16) / 4;
        int[] iArr = a;
        state[stateOffset] = iArr[tsOff];
        state[stateOffset + 1] = iArr[tsOff + 1];
        state[stateOffset + 2] = iArr[tsOff + 2];
        state[stateOffset + 3] = iArr[tsOff + 3];
    }

    public Salsa20Engine() {
        this(20);
    }

    public Salsa20Engine(int rounds) {
        this.e = 0;
        this.f = new int[16];
        this.g = new int[16];
        this.h = new byte[64];
        this.i = false;
        if (rounds <= 0 || (rounds & 1) != 0) {
            throw new IllegalArgumentException("'rounds' must be a positive, even number");
        }
        this.d = rounds;
    }

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof ParametersWithIV) {
            ParametersWithIV ivParams = (ParametersWithIV) params;
            byte[] iv = ivParams.a();
            if (iv == null || iv.length != i()) {
                throw new IllegalArgumentException(b() + " requires exactly " + i() + " bytes of IV");
            }
            CipherParameters keyParam = ivParams.b();
            if (keyParam == null) {
                if (this.i) {
                    s((byte[]) null, iv);
                } else {
                    throw new IllegalStateException(b() + " KeyParameter can not be null for first initialisation");
                }
            } else if (keyParam instanceof KeyParameter) {
                s(((KeyParameter) keyParam).a(), iv);
            } else {
                throw new IllegalArgumentException(b() + " Init parameters must contain a KeyParameter (or null for re-init)");
            }
            reset();
            this.i = true;
            return;
        }
        throw new IllegalArgumentException(b() + " Init parameters must include an IV");
    }

    /* access modifiers changed from: protected */
    public int i() {
        return 8;
    }

    public String b() {
        if (this.d == 20) {
            return "Salsa20";
        }
        return "Salsa20" + "/" + this.d;
    }

    public byte e(byte in) {
        if (!j()) {
            byte[] bArr = this.h;
            int i2 = this.e;
            byte out = (byte) (bArr[i2] ^ in);
            int i3 = (i2 + 1) & 63;
            this.e = i3;
            if (i3 == 0) {
                c();
                g(this.h);
            }
            return out;
        }
        throw new MaxBytesExceededException("2^70 byte limit per IV; Change IV");
    }

    /* access modifiers changed from: protected */
    public void f(long diff) {
        int hi = (int) (diff >>> 32);
        int lo = (int) diff;
        if (hi > 0) {
            int[] iArr = this.f;
            iArr[9] = iArr[9] + hi;
        }
        int[] iArr2 = this.f;
        int oldState = iArr2[8];
        iArr2[8] = iArr2[8] + lo;
        if (oldState != 0 && iArr2[8] < oldState) {
            iArr2[9] = iArr2[9] + 1;
        }
    }

    /* access modifiers changed from: protected */
    public void c() {
        int[] iArr = this.f;
        int i2 = iArr[8] + 1;
        iArr[8] = i2;
        if (i2 == 0) {
            iArr[9] = iArr[9] + 1;
        }
    }

    /* access modifiers changed from: protected */
    public void p(long diff) {
        int hi = (int) (diff >>> 32);
        int lo = (int) diff;
        if (hi != 0) {
            int[] iArr = this.f;
            if ((((long) iArr[9]) & 4294967295L) >= (((long) hi) & 4294967295L)) {
                iArr[9] = iArr[9] - hi;
            } else {
                throw new IllegalStateException("attempt to reduce counter past zero.");
            }
        }
        int[] iArr2 = this.f;
        if ((((long) iArr2[8]) & 4294967295L) >= (4294967295L & ((long) lo))) {
            iArr2[8] = iArr2[8] - lo;
        } else if (iArr2[9] != 0) {
            iArr2[9] = iArr2[9] - 1;
            iArr2[8] = iArr2[8] - lo;
        } else {
            throw new IllegalStateException("attempt to reduce counter past zero.");
        }
    }

    /* access modifiers changed from: protected */
    public void o() {
        int[] iArr = this.f;
        if (iArr[8] == 0 && iArr[9] == 0) {
            throw new IllegalStateException("attempt to reduce counter past zero.");
        }
        int i2 = iArr[8] - 1;
        iArr[8] = i2;
        if (i2 == -1) {
            iArr[9] = iArr[9] - 1;
        }
    }

    public int d(byte[] in, int inOff, int len, byte[] out, int outOff) {
        if (!this.i) {
            throw new IllegalStateException(b() + " not initialised");
        } else if (inOff + len > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + len > out.length) {
            throw new OutputLengthException("output buffer too short");
        } else if (!k(len)) {
            for (int i2 = 0; i2 < len; i2++) {
                byte[] bArr = this.h;
                int i3 = this.e;
                out[i2 + outOff] = (byte) (bArr[i3] ^ in[i2 + inOff]);
                int i4 = (i3 + 1) & 63;
                this.e = i4;
                if (i4 == 0) {
                    c();
                    g(this.h);
                }
            }
            return len;
        } else {
            throw new MaxBytesExceededException("2^70 byte limit per IV would be exceeded; Change IV");
        }
    }

    public long skip(long numberOfBytes) {
        if (numberOfBytes >= 0) {
            long remaining = numberOfBytes;
            if (remaining >= 64) {
                long count = remaining / 64;
                f(count);
                remaining -= 64 * count;
            }
            int oldIndex = this.e;
            int i2 = (this.e + ((int) remaining)) & 63;
            this.e = i2;
            if (i2 < oldIndex) {
                c();
            }
        } else {
            long remaining2 = -numberOfBytes;
            if (remaining2 >= 64) {
                long count2 = remaining2 / 64;
                p(count2);
                remaining2 -= 64 * count2;
            }
            for (long i3 = 0; i3 < remaining2; i3++) {
                if (this.e == 0) {
                    o();
                }
                this.e = (this.e - 1) & 63;
            }
        }
        g(this.h);
        return numberOfBytes;
    }

    public long seekTo(long position) {
        reset();
        return skip(position);
    }

    public long getPosition() {
        return (h() * 64) + ((long) this.e);
    }

    public void reset() {
        this.e = 0;
        n();
        m();
        g(this.h);
    }

    /* access modifiers changed from: protected */
    public long h() {
        int[] iArr = this.f;
        return (((long) iArr[9]) << 32) | (((long) iArr[8]) & 4294967295L);
    }

    /* access modifiers changed from: protected */
    public void m() {
        int[] iArr = this.f;
        iArr[9] = 0;
        iArr[8] = 0;
    }

    /* access modifiers changed from: protected */
    public void s(byte[] keyBytes, byte[] ivBytes) {
        if (keyBytes != null) {
            if (keyBytes.length == 16 || keyBytes.length == 32) {
                int tsOff = (keyBytes.length - 16) / 4;
                int[] iArr = this.f;
                int[] iArr2 = a;
                iArr[0] = iArr2[tsOff];
                iArr[5] = iArr2[tsOff + 1];
                iArr[10] = iArr2[tsOff + 2];
                iArr[15] = iArr2[tsOff + 3];
                Pack.k(keyBytes, 0, iArr, 1, 4);
                Pack.k(keyBytes, keyBytes.length - 16, this.f, 11, 4);
            } else {
                throw new IllegalArgumentException(b() + " requires 128 bit or 256 bit key");
            }
        }
        Pack.k(ivBytes, 0, this.f, 6, 2);
    }

    /* access modifiers changed from: protected */
    public void g(byte[] output) {
        r(this.d, this.f, this.g);
        Pack.i(this.g, output, 0);
    }

    public static void r(int rounds, int[] input, int[] x) {
        int[] iArr = input;
        int[] iArr2 = x;
        if (iArr.length != 16) {
            throw new IllegalArgumentException();
        } else if (iArr2.length != 16) {
            throw new IllegalArgumentException();
        } else if (rounds % 2 == 0) {
            int x00 = iArr[0];
            int x01 = iArr[1];
            int x02 = iArr[2];
            int x03 = iArr[3];
            int x04 = iArr[4];
            int x05 = iArr[5];
            int x06 = iArr[6];
            int i2 = 7;
            int x07 = iArr[7];
            int x08 = iArr[8];
            int i3 = 9;
            int x09 = iArr[9];
            int x10 = iArr[10];
            int x11 = iArr[11];
            int x12 = iArr[12];
            int i4 = 13;
            int x13 = iArr[13];
            int x14 = iArr[14];
            int x15 = iArr[15];
            int i5 = rounds;
            while (i5 > 0) {
                int x042 = q(x00 + x12, i2) ^ x04;
                int x082 = x08 ^ q(x042 + x00, i3);
                int x122 = x12 ^ q(x082 + x042, i4);
                int x002 = x00 ^ q(x122 + x082, 18);
                int x092 = x09 ^ q(x05 + x01, i2);
                int x132 = x13 ^ q(x092 + x05, i3);
                int x012 = x01 ^ q(x132 + x092, i4);
                int x052 = q(x012 + x132, 18) ^ x05;
                int x142 = x14 ^ q(x10 + x06, 7);
                int x022 = x02 ^ q(x142 + x10, 9);
                int x062 = q(x022 + x142, 13) ^ x06;
                int x102 = x10 ^ q(x062 + x022, 18);
                int x032 = x03 ^ q(x15 + x11, 7);
                int x072 = x07 ^ q(x032 + x15, 9);
                int x112 = x11 ^ q(x072 + x032, 13);
                int x133 = x132;
                int x152 = x15 ^ q(x112 + x072, 18);
                int x123 = x122;
                x01 = x012 ^ q(x002 + x032, 7);
                x02 = x022 ^ q(x01 + x002, 9);
                int x033 = x032 ^ q(x02 + x01, 13);
                x00 = x002 ^ q(x033 + x02, 18);
                x06 = x062 ^ q(x052 + x042, 7);
                x07 = x072 ^ q(x06 + x052, 9);
                int x043 = q(x07 + x06, 13) ^ x042;
                x11 = x112 ^ q(x102 + x092, 7);
                int x083 = q(x11 + x102, 9) ^ x082;
                int x093 = x092 ^ q(x083 + x11, 13);
                x10 = x102 ^ q(x093 + x083, 18);
                x12 = x123 ^ q(x152 + x142, 7);
                x13 = x133 ^ q(x12 + x152, 9);
                x14 = x142 ^ q(x13 + x12, 13);
                x15 = x152 ^ q(x14 + x13, 18);
                i5 -= 2;
                x08 = x083;
                x03 = x033;
                x09 = x093;
                x04 = x043;
                x05 = q(x043 + x07, 18) ^ x052;
                i4 = 13;
                i3 = 9;
                i2 = 7;
                int[] iArr3 = input;
                int[] iArr4 = x;
            }
            x[0] = input[0] + x00;
            x[1] = input[1] + x01;
            x[2] = input[2] + x02;
            x[3] = input[3] + x03;
            x[4] = input[4] + x04;
            x[5] = input[5] + x05;
            x[6] = input[6] + x06;
            x[7] = x07 + input[7];
            x[8] = x08 + input[8];
            x[9] = x09 + input[9];
            x[10] = x10 + input[10];
            x[11] = x11 + input[11];
            x[12] = x12 + input[12];
            x[13] = x13 + input[13];
            x[14] = x14 + input[14];
            x[15] = x15 + input[15];
        } else {
            throw new IllegalArgumentException("Number of rounds must be even");
        }
    }

    protected static int q(int x, int y) {
        return (x << y) | (x >>> (-y));
    }

    private void n() {
        this.j = 0;
        this.k = 0;
        this.l = 0;
    }

    private boolean j() {
        int i2 = this.j + 1;
        this.j = i2;
        if (i2 == 0) {
            int i3 = this.k + 1;
            this.k = i3;
            if (i3 == 0) {
                int i4 = this.l + 1;
                this.l = i4;
                if ((i4 & 32) != 0) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private boolean k(int len) {
        int i2 = this.j + len;
        this.j = i2;
        if (i2 < len && i2 >= 0) {
            int i3 = this.k + 1;
            this.k = i3;
            if (i3 == 0) {
                int i4 = this.l + 1;
                this.l = i4;
                if ((i4 & 32) != 0) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
