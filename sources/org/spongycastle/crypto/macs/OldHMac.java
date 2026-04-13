package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;

public class OldHMac implements Mac {
    private Digest a;
    private int b;
    private byte[] c = new byte[64];
    private byte[] d = new byte[64];

    public OldHMac(Digest digest) {
        this.a = digest;
        this.b = digest.e();
    }

    public String b() {
        return this.a.b() + "/HMAC";
    }

    public void a(CipherParameters params) {
        this.a.reset();
        byte[] key = ((KeyParameter) params).a();
        if (key.length <= 64) {
            System.arraycopy(key, 0, this.c, 0, key.length);
            int i = key.length;
            while (true) {
                byte[] bArr = this.c;
                if (i >= bArr.length) {
                    break;
                }
                bArr[i] = 0;
                i++;
            }
        } else {
            this.a.update(key, 0, key.length);
            this.a.c(this.c, 0);
            int i2 = this.b;
            while (true) {
                byte[] bArr2 = this.c;
                if (i2 >= bArr2.length) {
                    break;
                }
                bArr2[i2] = 0;
                i2++;
            }
        }
        byte[] bArr3 = this.c;
        byte[] bArr4 = new byte[bArr3.length];
        this.d = bArr4;
        System.arraycopy(bArr3, 0, bArr4, 0, bArr3.length);
        int i3 = 0;
        while (true) {
            byte[] bArr5 = this.c;
            if (i3 >= bArr5.length) {
                break;
            }
            bArr5[i3] = (byte) (bArr5[i3] ^ 54);
            i3++;
        }
        int i4 = 0;
        while (true) {
            byte[] bArr6 = this.d;
            if (i4 < bArr6.length) {
                bArr6[i4] = (byte) (bArr6[i4] ^ 92);
                i4++;
            } else {
                Digest digest = this.a;
                byte[] bArr7 = this.c;
                digest.update(bArr7, 0, bArr7.length);
                return;
            }
        }
    }

    public int e() {
        return this.b;
    }

    public void d(byte in) {
        this.a.d(in);
    }

    public void update(byte[] in, int inOff, int len) {
        this.a.update(in, inOff, len);
    }

    public int c(byte[] out, int outOff) {
        byte[] tmp = new byte[this.b];
        this.a.c(tmp, 0);
        Digest digest = this.a;
        byte[] bArr = this.d;
        digest.update(bArr, 0, bArr.length);
        this.a.update(tmp, 0, tmp.length);
        int len = this.a.c(out, outOff);
        reset();
        return len;
    }

    public void reset() {
        this.a.reset();
        Digest digest = this.a;
        byte[] bArr = this.c;
        digest.update(bArr, 0, bArr.length);
    }
}
