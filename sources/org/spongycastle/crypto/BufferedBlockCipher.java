package org.spongycastle.crypto;

public class BufferedBlockCipher {
    protected byte[] a;
    protected int b;
    protected boolean c;
    protected BlockCipher d;
    protected boolean e;
    protected boolean f;

    protected BufferedBlockCipher() {
    }

    public BufferedBlockCipher(BlockCipher cipher) {
        this.d = cipher;
        this.a = new byte[cipher.c()];
        boolean z = false;
        this.b = 0;
        String name = cipher.b();
        int idx = name.indexOf(47) + 1;
        boolean z2 = idx > 0 && name.startsWith("PGP", idx);
        this.f = z2;
        if (z2 || (cipher instanceof StreamCipher)) {
            this.e = true;
            return;
        }
        if (idx > 0 && name.startsWith("OpenPGP", idx)) {
            z = true;
        }
        this.e = z;
    }

    public BlockCipher d() {
        return this.d;
    }

    public void f(boolean forEncryption, CipherParameters params) {
        this.c = forEncryption;
        h();
        this.d.a(forEncryption, params);
    }

    public int b() {
        return this.d.c();
    }

    public int e(int len) {
        int leftOver;
        int total = this.b + len;
        if (!this.f) {
            leftOver = total % this.a.length;
        } else if (this.c) {
            leftOver = (total % this.a.length) - (this.d.c() + 2);
        } else {
            leftOver = total % this.a.length;
        }
        return total - leftOver;
    }

    public int c(int length) {
        return this.b + length;
    }

    public int g(byte[] in, int inOff, int len, byte[] out, int outOff) {
        if (len >= 0) {
            int blockSize = b();
            int length = e(len);
            if (length <= 0 || outOff + length <= out.length) {
                int resultLen = 0;
                byte[] bArr = this.a;
                int length2 = bArr.length;
                int i = this.b;
                int gapLen = length2 - i;
                if (len > gapLen) {
                    System.arraycopy(in, inOff, bArr, i, gapLen);
                    resultLen = 0 + this.d.f(this.a, 0, out, outOff);
                    this.b = 0;
                    len -= gapLen;
                    inOff += gapLen;
                    while (len > this.a.length) {
                        resultLen += this.d.f(in, inOff, out, outOff + resultLen);
                        len -= blockSize;
                        inOff += blockSize;
                    }
                }
                System.arraycopy(in, inOff, this.a, this.b, len);
                int i2 = this.b + len;
                this.b = i2;
                byte[] bArr2 = this.a;
                if (i2 != bArr2.length) {
                    return resultLen;
                }
                int resultLen2 = resultLen + this.d.f(bArr2, 0, out, outOff + resultLen);
                this.b = 0;
                return resultLen2;
            }
            throw new OutputLengthException("output buffer too short");
        }
        throw new IllegalArgumentException("Can't have a negative input length!");
    }

    public int a(byte[] out, int outOff) {
        int resultLen = 0;
        try {
            int i = this.b;
            if (outOff + i <= out.length) {
                if (i != 0) {
                    if (this.e) {
                        BlockCipher blockCipher = this.d;
                        byte[] bArr = this.a;
                        blockCipher.f(bArr, 0, bArr, 0);
                        resultLen = this.b;
                        this.b = 0;
                        System.arraycopy(this.a, 0, out, outOff, resultLen);
                    } else {
                        throw new DataLengthException("data not block size aligned");
                    }
                }
                return resultLen;
            }
            throw new OutputLengthException("output buffer too short for doFinal()");
        } finally {
            h();
        }
    }

    public void h() {
        int i = 0;
        while (true) {
            byte[] bArr = this.a;
            if (i < bArr.length) {
                bArr[i] = 0;
                i++;
            } else {
                this.b = 0;
                this.d.reset();
                return;
            }
        }
    }
}
