package org.spongycastle.crypto.agreement.srp;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.SRP6GroupParameters;

public class SRP6Client {
    protected BigInteger a;
    protected BigInteger b;
    protected BigInteger c;
    protected BigInteger d;
    protected BigInteger e;
    protected BigInteger f;
    protected BigInteger g;
    protected BigInteger h;
    protected Digest i;
    protected SecureRandom j;

    public void d(BigInteger N, BigInteger g2, Digest digest, SecureRandom random) {
        this.a = N;
        this.b = g2;
        this.i = digest;
        this.j = random;
    }

    public void e(SRP6GroupParameters group, Digest digest, SecureRandom random) {
        d(group.b(), group.a(), digest, random);
    }

    public BigInteger c(byte[] salt, byte[] identity, byte[] password) {
        this.f = SRP6Util.c(this.i, this.a, salt, identity, password);
        BigInteger f2 = f();
        this.c = f2;
        BigInteger modPow = this.b.modPow(f2, this.a);
        this.d = modPow;
        return modPow;
    }

    public BigInteger b(BigInteger serverB) {
        BigInteger g2 = SRP6Util.g(this.a, serverB);
        this.e = g2;
        this.g = SRP6Util.b(this.i, this.a, this.d, g2);
        BigInteger a2 = a();
        this.h = a2;
        return a2;
    }

    /* access modifiers changed from: protected */
    public BigInteger f() {
        return SRP6Util.d(this.i, this.a, this.b, this.j);
    }

    private BigInteger a() {
        BigInteger k = SRP6Util.a(this.i, this.a, this.b);
        return this.e.subtract(this.b.modPow(this.f, this.a).multiply(k).mod(this.a)).mod(this.a).modPow(this.g.multiply(this.f).add(this.c), this.a);
    }
}
