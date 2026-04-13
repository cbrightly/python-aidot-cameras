package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECMultiplier;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.FixedPointCombMultiplier;

public class ECGOST3410_2012Signer implements DSA {
    ECKeyParameters g;
    SecureRandom h;

    public void a(boolean forSigning, CipherParameters param) {
        if (!forSigning) {
            this.g = (ECPublicKeyParameters) param;
        } else if (param instanceof ParametersWithRandom) {
            ParametersWithRandom rParam = (ParametersWithRandom) param;
            this.h = rParam.b();
            this.g = (ECPrivateKeyParameters) rParam.a();
        } else {
            this.h = new SecureRandom();
            this.g = (ECPrivateKeyParameters) param;
        }
    }

    public BigInteger[] b(byte[] message) {
        byte[] mRev = new byte[message.length];
        for (int i = 0; i != mRev.length; i++) {
            mRev[i] = message[(mRev.length - 1) - i];
        }
        BigInteger e = new BigInteger(1, mRev);
        ECDomainParameters ec = this.g.b();
        BigInteger n = ec.d();
        BigInteger d = ((ECPrivateKeyParameters) this.g).c();
        ECMultiplier basePointMultiplier = d();
        while (true) {
            BigInteger k = new BigInteger(n.bitLength(), this.h);
            BigInteger bigInteger = ECConstants.a;
            if (!k.equals(bigInteger)) {
                BigInteger r = basePointMultiplier.a(ec.b(), k).y().f().t().mod(n);
                if (!r.equals(bigInteger)) {
                    BigInteger s = k.multiply(e).add(d.multiply(r)).mod(n);
                    if (!s.equals(bigInteger)) {
                        return new BigInteger[]{r, s};
                    }
                } else {
                    continue;
                }
            }
        }
    }

    public boolean c(byte[] message, BigInteger r, BigInteger s) {
        byte[] mRev = new byte[message.length];
        for (int i = 0; i != mRev.length; i++) {
            mRev[i] = message[(mRev.length - 1) - i];
        }
        BigInteger e = new BigInteger(1, mRev);
        BigInteger n = this.g.b().d();
        BigInteger bigInteger = ECConstants.b;
        if (r.compareTo(bigInteger) < 0 || r.compareTo(n) >= 0 || s.compareTo(bigInteger) < 0 || s.compareTo(n) >= 0) {
            return false;
        }
        BigInteger v = e.modInverse(n);
        ECPoint point = ECAlgorithms.o(this.g.b().b(), s.multiply(v).mod(n), ((ECPublicKeyParameters) this.g).c(), n.subtract(r).multiply(v).mod(n)).y();
        if (point.t()) {
            return false;
        }
        return point.f().t().mod(n).equals(r);
    }

    /* access modifiers changed from: protected */
    public ECMultiplier d() {
        return new FixedPointCombMultiplier();
    }
}
