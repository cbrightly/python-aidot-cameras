package org.spongycastle.crypto.agreement.srp;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.SRP6GroupParameters;

public class SRP6Server {
    protected BigInteger a;
    protected BigInteger b;
    protected BigInteger c;
    protected SecureRandom d;
    protected Digest e;
    protected BigInteger f;
    protected BigInteger g;
    protected BigInteger h;
    protected BigInteger i;
    protected BigInteger j;

    public void d(BigInteger N, BigInteger g2, BigInteger v, Digest digest, SecureRandom random) {
        this.a = N;
        this.b = g2;
        this.c = v;
        this.d = random;
        this.e = digest;
    }

    public void e(SRP6GroupParameters group, BigInteger v, Digest digest, SecureRandom random) {
        d(group.b(), group.a(), v, digest, random);
    }

    public BigInteger c() {
        BigInteger k = SRP6Util.a(this.e, this.a, this.b);
        this.g = f();
        BigInteger mod = k.multiply(this.c).mod(this.a).add(this.b.modPow(this.g, this.a)).mod(this.a);
        this.h = mod;
        return mod;
    }

    public BigInteger b(BigInteger clientA) {
        BigInteger g2 = SRP6Util.g(this.a, clientA);
        this.f = g2;
        this.i = SRP6Util.b(this.e, this.a, g2, this.h);
        BigInteger a2 = a();
        this.j = a2;
        return a2;
    }

    /* access modifiers changed from: protected */
    public BigInteger f() {
        return SRP6Util.d(this.e, this.a, this.b, this.d);
    }

    private BigInteger a() {
        return this.c.modPow(this.i, this.a).multiply(this.f).mod(this.a).modPow(this.g, this.a);
    }
}
