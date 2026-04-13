package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.params.DSAKeyParameters;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAPrivateKeyParameters;
import org.spongycastle.crypto.params.DSAPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;

public class DSASigner implements DSA {
    private final DSAKCalculator g;
    private DSAKeyParameters h;
    private SecureRandom i;

    public DSASigner() {
        this.g = new RandomDSAKCalculator();
    }

    public DSASigner(DSAKCalculator kCalculator) {
        this.g = kCalculator;
    }

    public void a(boolean forSigning, CipherParameters param) {
        SecureRandom providedRandom = null;
        if (!forSigning) {
            this.h = (DSAPublicKeyParameters) param;
        } else if (param instanceof ParametersWithRandom) {
            ParametersWithRandom rParam = (ParametersWithRandom) param;
            this.h = (DSAPrivateKeyParameters) rParam.a();
            providedRandom = rParam.b();
        } else {
            this.h = (DSAPrivateKeyParameters) param;
        }
        this.i = f(forSigning && !this.g.c(), providedRandom);
    }

    public BigInteger[] b(byte[] message) {
        DSAParameters params = this.h.b();
        BigInteger q = params.c();
        BigInteger m = d(q, message);
        BigInteger x = ((DSAPrivateKeyParameters) this.h).c();
        if (this.g.c()) {
            this.g.d(q, x, message);
        } else {
            this.g.a(q, this.i);
        }
        BigInteger k = this.g.b();
        BigInteger r = params.a().modPow(k.add(e(q, this.i)), params.b()).mod(q);
        return new BigInteger[]{r, k.modInverse(q).multiply(m.add(x.multiply(r))).mod(q)};
    }

    public boolean c(byte[] message, BigInteger r, BigInteger s) {
        DSAParameters params = this.h.b();
        BigInteger q = params.c();
        BigInteger m = d(q, message);
        BigInteger zero = BigInteger.valueOf(0);
        if (zero.compareTo(r) >= 0 || q.compareTo(r) <= 0 || zero.compareTo(s) >= 0 || q.compareTo(s) <= 0) {
            return false;
        }
        BigInteger w = s.modInverse(q);
        BigInteger u1 = m.multiply(w).mod(q);
        BigInteger u2 = r.multiply(w).mod(q);
        BigInteger p = params.b();
        return params.a().modPow(u1, p).multiply(((DSAPublicKeyParameters) this.h).c().modPow(u2, p)).mod(p).mod(q).equals(r);
    }

    private BigInteger d(BigInteger n, byte[] message) {
        if (n.bitLength() >= message.length * 8) {
            return new BigInteger(1, message);
        }
        byte[] trunc = new byte[(n.bitLength() / 8)];
        System.arraycopy(message, 0, trunc, 0, trunc.length);
        return new BigInteger(1, trunc);
    }

    /* access modifiers changed from: protected */
    public SecureRandom f(boolean needed, SecureRandom provided) {
        if (!needed) {
            return null;
        }
        return provided != null ? provided : new SecureRandom();
    }

    private BigInteger e(BigInteger q, SecureRandom provided) {
        return new BigInteger(7, provided != null ? provided : new SecureRandom()).add(BigInteger.valueOf(128)).multiply(q);
    }
}
