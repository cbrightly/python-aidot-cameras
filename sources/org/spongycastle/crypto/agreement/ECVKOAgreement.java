package org.spongycastle.crypto.agreement;

import java.math.BigInteger;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithUKM;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.BigIntegers;

public class ECVKOAgreement {
    private final Digest a;
    private ECPrivateKeyParameters b;
    private BigInteger c;

    public ECVKOAgreement(Digest digest) {
        this.a = digest;
    }

    public void c(CipherParameters key) {
        ParametersWithUKM p = (ParametersWithUKM) key;
        this.b = (ECPrivateKeyParameters) p.a();
        this.c = d(p.b());
    }

    public byte[] a(CipherParameters pubKey) {
        ECPublicKeyParameters pub2 = (ECPublicKeyParameters) pubKey;
        ECDomainParameters params = pub2.b();
        if (params.equals(this.b.b())) {
            ECPoint P = pub2.c().w(params.c().multiply(this.c).multiply(this.b.c()).mod(params.d())).y();
            if (!P.t()) {
                return b(P.y());
            }
            throw new IllegalStateException("Infinity is not a valid agreement value for ECVKO");
        }
        throw new IllegalStateException("ECVKO public key has wrong domain parameters");
    }

    private static BigInteger d(byte[] ukm) {
        byte[] v = new byte[ukm.length];
        for (int i = 0; i != v.length; i++) {
            v[i] = ukm[(ukm.length - i) - 1];
        }
        return new BigInteger(1, v);
    }

    private byte[] b(ECPoint v) {
        int size;
        BigInteger bX = v.f().t();
        BigInteger bY = v.g().t();
        if (bX.toByteArray().length > 33) {
            size = 64;
        } else {
            size = 32;
        }
        byte[] bytes = new byte[(size * 2)];
        byte[] x = BigIntegers.a(size, bX);
        byte[] y = BigIntegers.a(size, bY);
        for (int i = 0; i != size; i++) {
            bytes[i] = x[(size - i) - 1];
        }
        for (int i2 = 0; i2 != size; i2++) {
            bytes[size + i2] = y[(size - i2) - 1];
        }
        this.a.update(bytes, 0, bytes.length);
        byte[] rv = new byte[this.a.e()];
        this.a.c(rv, 0);
        return rv;
    }
}
