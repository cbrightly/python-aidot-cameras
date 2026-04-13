package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.util.Arrays;

public class PKCS5S2ParametersGenerator extends PBEParametersGenerator {
    private Mac d;
    private byte[] e;

    public PKCS5S2ParametersGenerator() {
        this(DigestFactory.b());
    }

    public PKCS5S2ParametersGenerator(Digest digest) {
        HMac hMac = new HMac(digest);
        this.d = hMac;
        this.e = new byte[hMac.e()];
    }

    private void h(byte[] S, int c, byte[] iBuf, byte[] out, int outOff) {
        if (c != 0) {
            if (S != null) {
                this.d.update(S, 0, S.length);
            }
            this.d.update(iBuf, 0, iBuf.length);
            this.d.c(this.e, 0);
            byte[] bArr = this.e;
            System.arraycopy(bArr, 0, out, outOff, bArr.length);
            for (int count = 1; count < c; count++) {
                Mac mac = this.d;
                byte[] bArr2 = this.e;
                mac.update(bArr2, 0, bArr2.length);
                this.d.c(this.e, 0);
                int j = 0;
                while (true) {
                    byte[] bArr3 = this.e;
                    if (j == bArr3.length) {
                        break;
                    }
                    int i = outOff + j;
                    out[i] = (byte) (bArr3[j] ^ out[i]);
                    j++;
                }
            }
            return;
        }
        throw new IllegalArgumentException("iteration count must be at least 1.");
    }

    private byte[] i(int dkLen) {
        int hLen = this.d.e();
        int l = ((dkLen + hLen) - 1) / hLen;
        byte[] iBuf = new byte[4];
        byte[] outBytes = new byte[(l * hLen)];
        this.d.a(new KeyParameter(this.a));
        int outPos = 0;
        for (int i = 1; i <= l; i++) {
            int pos = 3;
            while (true) {
                byte b = (byte) (iBuf[pos] + 1);
                iBuf[pos] = b;
                if (b != 0) {
                    break;
                }
                pos--;
            }
            h(this.b, this.c, iBuf, outBytes, outPos);
            outPos += hLen;
        }
        return outBytes;
    }

    public CipherParameters e(int keySize) {
        int keySize2 = keySize / 8;
        return new KeyParameter(Arrays.B(i(keySize2), 0, keySize2), 0, keySize2);
    }

    public CipherParameters f(int keySize, int ivSize) {
        int keySize2 = keySize / 8;
        int ivSize2 = ivSize / 8;
        byte[] dKey = i(keySize2 + ivSize2);
        return new ParametersWithIV(new KeyParameter(dKey, 0, keySize2), dKey, keySize2, ivSize2);
    }

    public CipherParameters d(int keySize) {
        return e(keySize);
    }
}
