package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.HKDFParameters;
import org.spongycastle.crypto.params.KeyParameter;

public class HKDFBytesGenerator implements DerivationFunction {
    private HMac a;
    private int b;
    private byte[] c;
    private byte[] d;
    private int e;

    public void b(DerivationParameters param) {
        if (param instanceof HKDFParameters) {
            HKDFParameters params = (HKDFParameters) param;
            if (params.d()) {
                this.a.a(new KeyParameter(params.a()));
            } else {
                this.a.a(d(params.c(), params.a()));
            }
            this.c = params.b();
            this.e = 0;
            this.d = new byte[this.b];
            return;
        }
        throw new IllegalArgumentException("HKDF parameters required for HKDFBytesGenerator");
    }

    private KeyParameter d(byte[] salt, byte[] ikm) {
        if (salt == null) {
            this.a.a(new KeyParameter(new byte[this.b]));
        } else {
            this.a.a(new KeyParameter(salt));
        }
        this.a.update(ikm, 0, ikm.length);
        byte[] prk = new byte[this.b];
        this.a.c(prk, 0);
        return new KeyParameter(prk);
    }

    private void c() {
        int i = this.e;
        int i2 = this.b;
        int n = (i / i2) + 1;
        if (n < 256) {
            if (i != 0) {
                this.a.update(this.d, 0, i2);
            }
            HMac hMac = this.a;
            byte[] bArr = this.c;
            hMac.update(bArr, 0, bArr.length);
            this.a.d((byte) n);
            this.a.c(this.d, 0);
            return;
        }
        throw new DataLengthException("HKDF cannot generate more than 255 blocks of HashLen size");
    }

    public int a(byte[] out, int outOff, int len) {
        int i = this.e;
        int i2 = i + len;
        int i3 = this.b;
        if (i2 <= i3 * 255) {
            if (i % i3 == 0) {
                c();
            }
            int toGenerate = len;
            int i4 = this.e;
            int i5 = this.b;
            int posInT = i4 % i5;
            int toCopy = Math.min(i5 - (i4 % i5), toGenerate);
            System.arraycopy(this.d, posInT, out, outOff, toCopy);
            this.e += toCopy;
            int toGenerate2 = toGenerate - toCopy;
            int outOff2 = outOff + toCopy;
            while (toGenerate2 > 0) {
                c();
                int toCopy2 = Math.min(this.b, toGenerate2);
                System.arraycopy(this.d, 0, out, outOff2, toCopy2);
                this.e += toCopy2;
                toGenerate2 -= toCopy2;
                outOff2 += toCopy2;
            }
            return len;
        }
        throw new DataLengthException("HKDF may only be used for 255 * HashLen bytes of output");
    }
}
