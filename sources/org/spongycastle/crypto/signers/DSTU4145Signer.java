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
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECMultiplier;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.FixedPointCombMultiplier;
import org.spongycastle.util.Arrays;

public class DSTU4145Signer implements DSA {
    private static final BigInteger g = BigInteger.valueOf(1);
    private ECKeyParameters h;
    private SecureRandom i;

    public void a(boolean forSigning, CipherParameters param) {
        if (forSigning) {
            if (param instanceof ParametersWithRandom) {
                ParametersWithRandom rParam = (ParametersWithRandom) param;
                this.i = rParam.b();
                param = rParam.a();
            } else {
                this.i = new SecureRandom();
            }
            this.h = (ECPrivateKeyParameters) param;
            return;
        }
        this.h = (ECPublicKeyParameters) param;
    }

    public BigInteger[] b(byte[] message) {
        ECDomainParameters ec = this.h.b();
        ECCurve curve = ec.a();
        ECFieldElement h2 = g(curve, message);
        if (h2.i()) {
            h2 = curve.m(g);
        }
        BigInteger n = ec.d();
        BigInteger d = ((ECPrivateKeyParameters) this.h).c();
        ECMultiplier basePointMultiplier = d();
        while (true) {
            BigInteger e = f(n, this.i);
            ECFieldElement Fe = basePointMultiplier.a(ec.b(), e).y().f();
            if (!Fe.i()) {
                BigInteger r = e(n, h2.j(Fe));
                if (r.signum() != 0) {
                    BigInteger s = r.multiply(d).add(e).mod(n);
                    if (s.signum() != 0) {
                        return new BigInteger[]{r, s};
                    }
                } else {
                    continue;
                }
            }
        }
    }

    public boolean c(byte[] message, BigInteger r, BigInteger s) {
        if (r.signum() <= 0 || s.signum() <= 0) {
            return false;
        }
        ECDomainParameters parameters = this.h.b();
        BigInteger n = parameters.d();
        if (r.compareTo(n) >= 0 || s.compareTo(n) >= 0) {
            return false;
        }
        ECCurve curve = parameters.a();
        ECFieldElement h2 = g(curve, message);
        if (h2.i()) {
            h2 = curve.m(g);
        }
        ECPoint R = ECAlgorithms.o(parameters.b(), s, ((ECPublicKeyParameters) this.h).c(), r).y();
        if (!R.t() && e(n, h2.j(R.f())).compareTo(r) == 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public ECMultiplier d() {
        return new FixedPointCombMultiplier();
    }

    private static BigInteger f(BigInteger n, SecureRandom random) {
        return new BigInteger(n.bitLength() - 1, random);
    }

    private static ECFieldElement g(ECCurve curve, byte[] hash) {
        return curve.m(h(new BigInteger(1, Arrays.T(hash)), curve.t()));
    }

    private static BigInteger e(BigInteger n, ECFieldElement fe) {
        return h(fe.t(), n.bitLength() - 1);
    }

    private static BigInteger h(BigInteger x, int bitLength) {
        if (x.bitLength() > bitLength) {
            return x.mod(g.shiftLeft(bitLength));
        }
        return x;
    }
}
