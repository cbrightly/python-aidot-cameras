package org.spongycastle.crypto.engines;

import java.math.BigInteger;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.params.RSAKeyParameters;

public class RSABlindingEngine implements AsymmetricBlockCipher {
    private RSACoreEngine a = new RSACoreEngine();
    private RSAKeyParameters b;
    private BigInteger c;
    private boolean d;

    /* JADX WARNING: type inference failed for: r1v3, types: [org.spongycastle.crypto.CipherParameters] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(boolean r4, org.spongycastle.crypto.CipherParameters r5) {
        /*
            r3 = this;
            boolean r0 = r5 instanceof org.spongycastle.crypto.params.ParametersWithRandom
            if (r0 == 0) goto L_0x000f
            r0 = r5
            org.spongycastle.crypto.params.ParametersWithRandom r0 = (org.spongycastle.crypto.params.ParametersWithRandom) r0
            org.spongycastle.crypto.CipherParameters r1 = r0.a()
            r0 = r1
            org.spongycastle.crypto.params.RSABlindingParameters r0 = (org.spongycastle.crypto.params.RSABlindingParameters) r0
            goto L_0x0012
        L_0x000f:
            r0 = r5
            org.spongycastle.crypto.params.RSABlindingParameters r0 = (org.spongycastle.crypto.params.RSABlindingParameters) r0
        L_0x0012:
            org.spongycastle.crypto.engines.RSACoreEngine r1 = r3.a
            org.spongycastle.crypto.params.RSAKeyParameters r2 = r0.b()
            r1.e(r4, r2)
            r3.d = r4
            org.spongycastle.crypto.params.RSAKeyParameters r1 = r0.b()
            r3.b = r1
            java.math.BigInteger r1 = r0.a()
            r3.c = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.crypto.engines.RSABlindingEngine.a(boolean, org.spongycastle.crypto.CipherParameters):void");
    }

    public int c() {
        return this.a.c();
    }

    public int b() {
        return this.a.d();
    }

    public byte[] d(byte[] in, int inOff, int inLen) {
        BigInteger msg;
        BigInteger msg2 = this.a.a(in, inOff, inLen);
        if (this.d) {
            msg = e(msg2);
        } else {
            msg = f(msg2);
        }
        return this.a.b(msg);
    }

    private BigInteger e(BigInteger msg) {
        return msg.multiply(this.c.modPow(this.b.b(), this.b.c())).mod(this.b.c());
    }

    private BigInteger f(BigInteger blindedMsg) {
        BigInteger m = this.b.c();
        return blindedMsg.multiply(this.c.modInverse(m)).mod(m);
    }
}
