package org.spongycastle.crypto;

public class BufferedAsymmetricBlockCipher {
    protected byte[] a;
    protected int b;
    private final AsymmetricBlockCipher c;

    public BufferedAsymmetricBlockCipher(AsymmetricBlockCipher cipher) {
        this.c = cipher;
    }

    public void d(boolean forEncryption, CipherParameters params) {
        f();
        this.c.a(forEncryption, params);
        this.a = new byte[(this.c.c() + (forEncryption))];
        this.b = 0;
    }

    public int b() {
        return this.c.c();
    }

    public int c() {
        return this.c.b();
    }

    public void e(byte[] in, int inOff, int len) {
        if (len != 0) {
            if (len >= 0) {
                int i = this.b;
                int i2 = i + len;
                byte[] bArr = this.a;
                if (i2 <= bArr.length) {
                    System.arraycopy(in, inOff, bArr, i, len);
                    this.b += len;
                    return;
                }
                throw new DataLengthException("attempt to process message too long for cipher");
            }
            throw new IllegalArgumentException("Can't have a negative input length!");
        }
    }

    public byte[] a() {
        byte[] out = this.c.d(this.a, 0, this.b);
        f();
        return out;
    }

    public void f() {
        if (this.a != null) {
            int i = 0;
            while (true) {
                byte[] bArr = this.a;
                if (i >= bArr.length) {
                    break;
                }
                bArr[i] = 0;
                i++;
            }
        }
        this.b = 0;
    }
}
