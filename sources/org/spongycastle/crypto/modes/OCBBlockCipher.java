package org.spongycastle.crypto.modes;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import java.util.Vector;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.AEADParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;

public class OCBBlockCipher implements AEADBlockCipher {
    private BlockCipher a;
    private BlockCipher b;
    private boolean c;
    private int d;
    private byte[] e;
    private Vector f;
    private byte[] g;
    private byte[] h;
    private byte[] i = null;
    private byte[] j = new byte[24];
    private byte[] k = new byte[16];
    private byte[] l;
    private byte[] m;
    private int n;
    private int o;
    private long p;
    private long q;
    private byte[] r;
    private byte[] s;
    private byte[] t = new byte[16];
    private byte[] u;
    private byte[] v;

    public OCBBlockCipher(BlockCipher hashCipher, BlockCipher mainCipher) {
        if (hashCipher == null) {
            throw new IllegalArgumentException("'hashCipher' cannot be null");
        } else if (hashCipher.c() != 16) {
            throw new IllegalArgumentException("'hashCipher' must have a block size of 16");
        } else if (mainCipher == null) {
            throw new IllegalArgumentException("'mainCipher' cannot be null");
        } else if (mainCipher.c() != 16) {
            throw new IllegalArgumentException("'mainCipher' must have a block size of 16");
        } else if (hashCipher.b().equals(mainCipher.b())) {
            this.a = hashCipher;
            this.b = mainCipher;
        } else {
            throw new IllegalArgumentException("'hashCipher' and 'mainCipher' must be the same algorithm");
        }
    }

    public BlockCipher g() {
        return this.b;
    }

    public void a(boolean forEncryption, CipherParameters parameters) {
        byte[] N;
        KeyParameter keyParameter;
        boolean z = forEncryption;
        CipherParameters cipherParameters = parameters;
        boolean oldForEncryption = this.c;
        this.c = z;
        this.v = null;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aeadParameters = (AEADParameters) cipherParameters;
            N = aeadParameters.d();
            this.e = aeadParameters.a();
            int macSizeBits = aeadParameters.c();
            if (macSizeBits < 64 || macSizeBits > 128 || macSizeBits % 8 != 0) {
                throw new IllegalArgumentException("Invalid value for MAC size: " + macSizeBits);
            }
            this.d = macSizeBits / 8;
            keyParameter = aeadParameters.b();
        } else if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            N = parametersWithIV.a();
            this.e = null;
            this.d = 16;
            keyParameter = (KeyParameter) parametersWithIV.b();
        } else {
            throw new IllegalArgumentException("invalid parameters passed to OCB");
        }
        this.l = new byte[16];
        this.m = new byte[(z ? 16 : this.d + 16)];
        if (N == null) {
            N = new byte[0];
        }
        if (N.length <= 15) {
            if (keyParameter != null) {
                this.a.a(true, keyParameter);
                this.b.a(z, keyParameter);
                this.i = null;
            } else if (oldForEncryption != z) {
                throw new IllegalArgumentException("cannot change encrypting state without providing key.");
            }
            byte[] bArr = new byte[16];
            this.g = bArr;
            this.a.f(bArr, 0, bArr, 0);
            this.h = b(this.g);
            Vector vector = new Vector();
            this.f = vector;
            vector.addElement(b(this.h));
            int bottom = p(N);
            int bits = bottom % 8;
            int bytes = bottom / 8;
            if (bits == 0) {
                System.arraycopy(this.j, bytes, this.k, 0, 16);
            } else {
                for (int i2 = 0; i2 < 16; i2++) {
                    byte[] bArr2 = this.j;
                    bytes++;
                    this.k[i2] = (byte) (((bArr2[bytes] & 255) << bits) | ((bArr2[bytes] & 255) >>> (8 - bits)));
                }
            }
            this.n = 0;
            this.o = 0;
            this.p = 0;
            this.q = 0;
            this.r = new byte[16];
            this.s = new byte[16];
            System.arraycopy(this.k, 0, this.t, 0, 16);
            this.u = new byte[16];
            byte[] bArr3 = this.e;
            if (bArr3 != null) {
                i(bArr3, 0, bArr3.length);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("IV must be no more than 15 bytes");
    }

    /* access modifiers changed from: protected */
    public int p(byte[] N) {
        byte[] nonce = new byte[16];
        System.arraycopy(N, 0, nonce, nonce.length - N.length, N.length);
        nonce[0] = (byte) (this.d << 4);
        int length = 15 - N.length;
        nonce[length] = (byte) (nonce[length] | 1);
        int bottom = nonce[15] & 63;
        nonce[15] = (byte) (nonce[15] & 192);
        byte[] bArr = this.i;
        if (bArr == null || !Arrays.b(nonce, bArr)) {
            byte[] Ktop = new byte[16];
            this.i = nonce;
            this.a.f(nonce, 0, Ktop, 0);
            System.arraycopy(Ktop, 0, this.j, 0, 16);
            for (int i2 = 0; i2 < 8; i2++) {
                this.j[i2 + 16] = (byte) (Ktop[i2] ^ Ktop[i2 + 1]);
            }
        }
        return bottom;
    }

    public byte[] h() {
        byte[] bArr = this.v;
        if (bArr == null) {
            return new byte[this.d];
        }
        return Arrays.h(bArr);
    }

    public int f(int len) {
        int totalData = this.o + len;
        if (this.c) {
            return this.d + totalData;
        }
        int i2 = this.d;
        if (totalData < i2) {
            return 0;
        }
        return totalData - i2;
    }

    public int e(int len) {
        int totalData = this.o + len;
        if (!this.c) {
            int i2 = this.d;
            if (totalData < i2) {
                return 0;
            }
            totalData -= i2;
        }
        return totalData - (totalData % 16);
    }

    public void i(byte[] input, int off, int len) {
        for (int i2 = 0; i2 < len; i2++) {
            byte[] bArr = this.l;
            int i3 = this.n;
            bArr[i3] = input[off + i2];
            int i4 = i3 + 1;
            this.n = i4;
            if (i4 == bArr.length) {
                n();
            }
        }
    }

    public int d(byte[] input, int inOff, int len, byte[] output, int outOff) {
        if (input.length >= inOff + len) {
            int resultLen = 0;
            for (int i2 = 0; i2 < len; i2++) {
                byte[] bArr = this.m;
                int i3 = this.o;
                bArr[i3] = input[inOff + i2];
                int i4 = i3 + 1;
                this.o = i4;
                if (i4 == bArr.length) {
                    o(output, outOff + resultLen);
                    resultLen += 16;
                }
            }
            return resultLen;
        }
        throw new DataLengthException("Input buffer too short");
    }

    public int c(byte[] output, int outOff) {
        byte[] tag = null;
        if (!this.c) {
            int i2 = this.o;
            int i3 = this.d;
            if (i2 >= i3) {
                int i4 = i2 - i3;
                this.o = i4;
                tag = new byte[i3];
                System.arraycopy(this.m, i4, tag, 0, i3);
            } else {
                throw new InvalidCipherTextException("data too short");
            }
        }
        int i5 = this.n;
        if (i5 > 0) {
            j(this.l, i5);
            s(this.g);
        }
        int i6 = this.o;
        if (i6 > 0) {
            if (this.c) {
                j(this.m, i6);
                t(this.u, this.m);
            }
            t(this.t, this.g);
            byte[] Pad = new byte[16];
            this.a.f(this.t, 0, Pad, 0);
            t(this.m, Pad);
            int length = output.length;
            int i7 = this.o;
            if (length >= outOff + i7) {
                System.arraycopy(this.m, 0, output, outOff, i7);
                if (!this.c) {
                    j(this.m, this.o);
                    t(this.u, this.m);
                }
            } else {
                throw new OutputLengthException("Output buffer too short");
            }
        }
        t(this.u, this.t);
        t(this.u, this.h);
        BlockCipher blockCipher = this.a;
        byte[] bArr = this.u;
        blockCipher.f(bArr, 0, bArr, 0);
        t(this.u, this.s);
        int i8 = this.d;
        byte[] bArr2 = new byte[i8];
        this.v = bArr2;
        System.arraycopy(this.u, 0, bArr2, 0, i8);
        int resultLen = this.o;
        if (this.c) {
            int length2 = output.length;
            int i9 = this.d;
            if (length2 >= outOff + resultLen + i9) {
                System.arraycopy(this.v, 0, output, outOff + resultLen, i9);
                resultLen += this.d;
            } else {
                throw new OutputLengthException("Output buffer too short");
            }
        } else if (!Arrays.u(this.v, tag)) {
            throw new InvalidCipherTextException("mac check in OCB failed");
        }
        q(false);
        return resultLen;
    }

    /* access modifiers changed from: protected */
    public void l(byte[] bs) {
        if (bs != null) {
            Arrays.F(bs, (byte) 0);
        }
    }

    /* access modifiers changed from: protected */
    public byte[] m(int n2) {
        while (n2 >= this.f.size()) {
            Vector vector = this.f;
            vector.addElement(b((byte[]) vector.lastElement()));
        }
        return (byte[]) this.f.elementAt(n2);
    }

    /* access modifiers changed from: protected */
    public void n() {
        long j2 = this.p + 1;
        this.p = j2;
        s(m(k(j2)));
        this.n = 0;
    }

    /* access modifiers changed from: protected */
    public void o(byte[] output, int outOff) {
        if (output.length >= outOff + 16) {
            if (this.c) {
                t(this.u, this.m);
                this.o = 0;
            }
            byte[] bArr = this.t;
            long j2 = this.q + 1;
            this.q = j2;
            t(bArr, m(k(j2)));
            t(this.m, this.t);
            BlockCipher blockCipher = this.b;
            byte[] bArr2 = this.m;
            blockCipher.f(bArr2, 0, bArr2, 0);
            t(this.m, this.t);
            System.arraycopy(this.m, 0, output, outOff, 16);
            if (!this.c) {
                t(this.u, this.m);
                byte[] bArr3 = this.m;
                System.arraycopy(bArr3, 16, bArr3, 0, this.d);
                this.o = this.d;
                return;
            }
            return;
        }
        throw new OutputLengthException("Output buffer too short");
    }

    /* access modifiers changed from: protected */
    public void q(boolean clearMac) {
        this.a.reset();
        this.b.reset();
        l(this.l);
        l(this.m);
        this.n = 0;
        this.o = 0;
        this.p = 0;
        this.q = 0;
        l(this.r);
        l(this.s);
        System.arraycopy(this.k, 0, this.t, 0, 16);
        l(this.u);
        if (clearMac) {
            this.v = null;
        }
        byte[] bArr = this.e;
        if (bArr != null) {
            i(bArr, 0, bArr.length);
        }
    }

    /* access modifiers changed from: protected */
    public void s(byte[] LSub) {
        t(this.r, LSub);
        t(this.l, this.r);
        BlockCipher blockCipher = this.a;
        byte[] bArr = this.l;
        blockCipher.f(bArr, 0, bArr, 0);
        t(this.s, this.l);
    }

    protected static byte[] b(byte[] block) {
        byte[] result = new byte[16];
        result[15] = (byte) (result[15] ^ (135 >>> ((1 - r(block, result)) << 3)));
        return result;
    }

    protected static void j(byte[] block, int pos) {
        block[pos] = OTACommand.RESP_ID_VERSION;
        while (true) {
            pos++;
            if (pos < 16) {
                block[pos] = 0;
            } else {
                return;
            }
        }
    }

    protected static int k(long x) {
        if (x == 0) {
            return 64;
        }
        int n2 = 0;
        while ((1 & x) == 0) {
            n2++;
            x >>>= 1;
        }
        return n2;
    }

    protected static int r(byte[] block, byte[] output) {
        int i2 = 16;
        int bit = 0;
        while (true) {
            i2--;
            if (i2 < 0) {
                return bit;
            }
            int b2 = block[i2] & 255;
            output[i2] = (byte) ((b2 << 1) | bit);
            bit = (b2 >>> 7) & 1;
        }
    }

    protected static void t(byte[] block, byte[] val) {
        for (int i2 = 15; i2 >= 0; i2--) {
            block[i2] = (byte) (block[i2] ^ val[i2]);
        }
    }
}
