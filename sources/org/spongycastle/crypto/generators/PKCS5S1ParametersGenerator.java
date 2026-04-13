package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class PKCS5S1ParametersGenerator extends PBEParametersGenerator {
    private Digest d;

    public PKCS5S1ParametersGenerator(Digest digest) {
        this.d = digest;
    }

    private byte[] h() {
        byte[] digestBytes = new byte[this.d.e()];
        Digest digest = this.d;
        byte[] bArr = this.a;
        digest.update(bArr, 0, bArr.length);
        Digest digest2 = this.d;
        byte[] bArr2 = this.b;
        digest2.update(bArr2, 0, bArr2.length);
        this.d.c(digestBytes, 0);
        for (int i = 1; i < this.c; i++) {
            this.d.update(digestBytes, 0, digestBytes.length);
            this.d.c(digestBytes, 0);
        }
        return digestBytes;
    }

    public CipherParameters e(int keySize) {
        int keySize2 = keySize / 8;
        if (keySize2 <= this.d.e()) {
            return new KeyParameter(h(), 0, keySize2);
        }
        throw new IllegalArgumentException("Can't generate a derived key " + keySize2 + " bytes long.");
    }

    public CipherParameters f(int keySize, int ivSize) {
        int keySize2 = keySize / 8;
        int ivSize2 = ivSize / 8;
        if (keySize2 + ivSize2 <= this.d.e()) {
            byte[] dKey = h();
            return new ParametersWithIV(new KeyParameter(dKey, 0, keySize2), dKey, keySize2, ivSize2);
        }
        throw new IllegalArgumentException("Can't generate a derived key " + (keySize2 + ivSize2) + " bytes long.");
    }

    public CipherParameters d(int keySize) {
        return e(keySize);
    }
}
