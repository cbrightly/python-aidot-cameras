package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.util.DigestFactory;

public class OpenSSLPBEParametersGenerator extends PBEParametersGenerator {
    private Digest d = DigestFactory.a();

    public void i(byte[] password, byte[] salt) {
        super.g(password, salt, 1);
    }

    private byte[] h(int bytesNeeded) {
        byte[] buf = new byte[this.d.e()];
        byte[] key = new byte[bytesNeeded];
        int offset = 0;
        while (true) {
            Digest digest = this.d;
            byte[] bArr = this.a;
            digest.update(bArr, 0, bArr.length);
            Digest digest2 = this.d;
            byte[] bArr2 = this.b;
            digest2.update(bArr2, 0, bArr2.length);
            this.d.c(buf, 0);
            int len = bytesNeeded > buf.length ? buf.length : bytesNeeded;
            System.arraycopy(buf, 0, key, offset, len);
            offset += len;
            bytesNeeded -= len;
            if (bytesNeeded == 0) {
                return key;
            }
            this.d.reset();
            this.d.update(buf, 0, buf.length);
        }
    }

    public CipherParameters e(int keySize) {
        int keySize2 = keySize / 8;
        return new KeyParameter(h(keySize2), 0, keySize2);
    }

    public CipherParameters f(int keySize, int ivSize) {
        int keySize2 = keySize / 8;
        int ivSize2 = ivSize / 8;
        byte[] dKey = h(keySize2 + ivSize2);
        return new ParametersWithIV(new KeyParameter(dKey, 0, keySize2), dKey, keySize2, ivSize2);
    }

    public CipherParameters d(int keySize) {
        return e(keySize);
    }
}
