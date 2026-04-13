package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.macs.CMac;
import org.spongycastle.crypto.params.AEADParameters;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;

public class EAXBlockCipher implements AEADBlockCipher {
    private SICBlockCipher a;
    private boolean b;
    private int c;
    private Mac d;
    private byte[] e;
    private byte[] f;
    private byte[] g = new byte[this.c];
    private int h;
    private byte[] i;
    private int j;
    private boolean k;
    private byte[] l;

    public EAXBlockCipher(BlockCipher cipher) {
        this.c = cipher.c();
        CMac cMac = new CMac(cipher);
        this.d = cMac;
        this.f = new byte[cMac.e()];
        this.e = new byte[this.d.e()];
        this.a = new SICBlockCipher(cipher);
    }

    public BlockCipher g() {
        return this.a.h();
    }

    public void a(boolean forEncryption, CipherParameters params) {
        byte[] nonce;
        CipherParameters keyParam;
        this.b = forEncryption;
        if (params instanceof AEADParameters) {
            AEADParameters param = (AEADParameters) params;
            nonce = param.d();
            this.l = param.a();
            this.h = param.c() / 8;
            keyParam = param.b();
        } else if (params instanceof ParametersWithIV) {
            ParametersWithIV param2 = (ParametersWithIV) params;
            nonce = param2.a();
            this.l = null;
            this.h = this.d.e() / 2;
            keyParam = param2.b();
        } else {
            throw new IllegalArgumentException("invalid parameters passed to EAX");
        }
        int i2 = this.c;
        if (!forEncryption) {
            i2 += this.h;
        }
        this.i = new byte[i2];
        byte[] tag = new byte[this.c];
        this.d.a(keyParam);
        int i3 = this.c;
        tag[i3 - 1] = 0;
        this.d.update(tag, 0, i3);
        this.d.update(nonce, 0, nonce.length);
        this.d.c(this.e, 0);
        this.a.a(true, new ParametersWithIV((CipherParameters) null, this.e));
        l();
    }

    private void j() {
        if (!this.k) {
            this.k = true;
            this.d.c(this.f, 0);
            int i2 = this.c;
            byte[] tag = new byte[i2];
            tag[i2 - 1] = 2;
            this.d.update(tag, 0, i2);
        }
    }

    private void b() {
        byte[] outC = new byte[this.c];
        this.d.c(outC, 0);
        int i2 = 0;
        while (true) {
            byte[] bArr = this.g;
            if (i2 < bArr.length) {
                bArr[i2] = (byte) ((this.e[i2] ^ this.f[i2]) ^ outC[i2]);
                i2++;
            } else {
                return;
            }
        }
    }

    public void l() {
        m(true);
    }

    private void m(boolean clearMac) {
        this.a.reset();
        this.d.reset();
        this.j = 0;
        Arrays.F(this.i, (byte) 0);
        if (clearMac) {
            Arrays.F(this.g, (byte) 0);
        }
        int i2 = this.c;
        byte[] tag = new byte[i2];
        tag[i2 - 1] = 1;
        this.d.update(tag, 0, i2);
        this.k = false;
        byte[] bArr = this.l;
        if (bArr != null) {
            i(bArr, 0, bArr.length);
        }
    }

    public void i(byte[] in, int inOff, int len) {
        if (!this.k) {
            this.d.update(in, inOff, len);
            return;
        }
        throw new IllegalStateException("AAD data cannot be added after encryption/decryption processing has begun.");
    }

    public int d(byte[] in, int inOff, int len, byte[] out, int outOff) {
        j();
        if (in.length >= inOff + len) {
            int resultLen = 0;
            for (int i2 = 0; i2 != len; i2++) {
                resultLen += k(in[inOff + i2], out, outOff + resultLen);
            }
            return resultLen;
        }
        throw new DataLengthException("Input buffer too short");
    }

    public int c(byte[] out, int outOff) {
        j();
        int extra = this.j;
        byte[] bArr = this.i;
        byte[] tmp = new byte[bArr.length];
        this.j = 0;
        if (!this.b) {
            int i2 = this.h;
            if (extra < i2) {
                throw new InvalidCipherTextException("data too short");
            } else if (out.length >= (outOff + extra) - i2) {
                if (extra > i2) {
                    this.d.update(bArr, 0, extra - i2);
                    this.a.f(this.i, 0, tmp, 0);
                    System.arraycopy(tmp, 0, out, outOff, extra - this.h);
                }
                b();
                if (n(this.i, extra - this.h)) {
                    m(false);
                    return extra - this.h;
                }
                throw new InvalidCipherTextException("mac check in EAX failed");
            } else {
                throw new OutputLengthException("Output buffer too short");
            }
        } else if (out.length >= outOff + extra + this.h) {
            this.a.f(bArr, 0, tmp, 0);
            System.arraycopy(tmp, 0, out, outOff, extra);
            this.d.update(tmp, 0, extra);
            b();
            System.arraycopy(this.g, 0, out, outOff + extra, this.h);
            m(false);
            return this.h + extra;
        } else {
            throw new OutputLengthException("Output buffer too short");
        }
    }

    public byte[] h() {
        int i2 = this.h;
        byte[] mac = new byte[i2];
        System.arraycopy(this.g, 0, mac, 0, i2);
        return mac;
    }

    public int e(int len) {
        int totalData = this.j + len;
        if (!this.b) {
            int i2 = this.h;
            if (totalData < i2) {
                return 0;
            }
            totalData -= i2;
        }
        return totalData - (totalData % this.c);
    }

    public int f(int len) {
        int totalData = this.j + len;
        if (this.b) {
            return this.h + totalData;
        }
        int i2 = this.h;
        if (totalData < i2) {
            return 0;
        }
        return totalData - i2;
    }

    private int k(byte b2, byte[] out, int outOff) {
        int size;
        byte[] bArr = this.i;
        int i2 = this.j;
        int i3 = i2 + 1;
        this.j = i3;
        bArr[i2] = b2;
        if (i3 != bArr.length) {
            return 0;
        }
        int length = out.length;
        int i4 = this.c;
        if (length >= outOff + i4) {
            if (this.b) {
                size = this.a.f(bArr, 0, out, outOff);
                this.d.update(out, outOff, this.c);
            } else {
                this.d.update(bArr, 0, i4);
                size = this.a.f(this.i, 0, out, outOff);
            }
            this.j = 0;
            if (!this.b) {
                byte[] bArr2 = this.i;
                System.arraycopy(bArr2, this.c, bArr2, 0, this.h);
                this.j = this.h;
            }
            return size;
        }
        throw new OutputLengthException("Output buffer is too short");
    }

    private boolean n(byte[] mac, int off) {
        int nonEqual = 0;
        for (int i2 = 0; i2 < this.h; i2++) {
            nonEqual |= this.g[i2] ^ mac[off + i2];
        }
        return nonEqual == 0;
    }
}
