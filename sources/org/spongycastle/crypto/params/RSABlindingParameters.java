package org.spongycastle.crypto.params;

import java.math.BigInteger;
import org.spongycastle.crypto.CipherParameters;

public class RSABlindingParameters implements CipherParameters {
    private RSAKeyParameters c;
    private BigInteger d;

    public RSAKeyParameters b() {
        return this.c;
    }

    public BigInteger a() {
        return this.d;
    }
}
