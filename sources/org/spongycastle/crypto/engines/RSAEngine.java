package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;

public class RSAEngine implements AsymmetricBlockCipher {
    private RSACoreEngine a;

    public void a(boolean forEncryption, CipherParameters param) {
        if (this.a == null) {
            this.a = new RSACoreEngine();
        }
        this.a.e(forEncryption, param);
    }

    public int c() {
        return this.a.c();
    }

    public int b() {
        return this.a.d();
    }

    public byte[] d(byte[] in, int inOff, int inLen) {
        RSACoreEngine rSACoreEngine = this.a;
        if (rSACoreEngine != null) {
            return rSACoreEngine.b(rSACoreEngine.f(rSACoreEngine.a(in, inOff, inLen)));
        }
        throw new IllegalStateException("RSA engine not initialised");
    }
}
