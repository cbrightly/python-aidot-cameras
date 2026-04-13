package org.spongycastle.crypto.tls;

import java.math.BigInteger;
import org.spongycastle.crypto.params.SRP6GroupParameters;

public class TlsSRPLoginParameters {
    protected SRP6GroupParameters a;
    protected BigInteger b;
    protected byte[] c;

    public TlsSRPLoginParameters(SRP6GroupParameters group, BigInteger verifier, byte[] salt) {
        this.a = group;
        this.b = verifier;
        this.c = salt;
    }

    public SRP6GroupParameters a() {
        return this.a;
    }

    public byte[] b() {
        return this.c;
    }

    public BigInteger c() {
        return this.b;
    }
}
