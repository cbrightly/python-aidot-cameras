package org.spongycastle.crypto.agreement.srp;

import java.math.BigInteger;
import org.spongycastle.crypto.Digest;

public class SRP6VerifierGenerator {
    protected BigInteger a;
    protected BigInteger b;
    protected Digest c;

    public BigInteger a(byte[] salt, byte[] identity, byte[] password) {
        return this.b.modPow(SRP6Util.c(this.c, this.a, salt, identity, password), this.a);
    }
}
