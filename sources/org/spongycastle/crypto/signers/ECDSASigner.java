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
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECMultiplier;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.FixedPointCombMultiplier;

public class ECDSASigner implements ECConstants, DSA {
    private final DSAKCalculator g;
    private ECKeyParameters h;
    private SecureRandom i;

    public ECDSASigner() {
        this.g = new RandomDSAKCalculator();
    }

    public ECDSASigner(DSAKCalculator kCalculator) {
        this.g = kCalculator;
    }

    public void a(boolean forSigning, CipherParameters param) {
        SecureRandom providedRandom = null;
        if (!forSigning) {
            this.h = (ECPublicKeyParameters) param;
        } else if (param instanceof ParametersWithRandom) {
            ParametersWithRandom rParam = (ParametersWithRandom) param;
            this.h = (ECPrivateKeyParameters) rParam.a();
            providedRandom = rParam.b();
        } else {
            this.h = (ECPrivateKeyParameters) param;
        }
        this.i = g(forSigning && !this.g.c(), providedRandom);
    }

    public BigInteger[] b(byte[] message) {
        ECDomainParameters ec = this.h.b();
        BigInteger n = ec.d();
        BigInteger e = d(n, message);
        BigInteger d = ((ECPrivateKeyParameters) this.h).c();
        if (this.g.c()) {
            this.g.d(n, d, message);
        } else {
            this.g.a(n, this.i);
        }
        ECMultiplier basePointMultiplier = e();
        while (true) {
            BigInteger k = this.g.b();
            BigInteger r = basePointMultiplier.a(ec.b(), k).y().f().t().mod(n);
            BigInteger bigInteger = ECConstants.a;
            if (!r.equals(bigInteger)) {
                BigInteger s = k.modInverse(n).multiply(e.add(d.multiply(r))).mod(n);
                if (!s.equals(bigInteger)) {
                    return new BigInteger[]{r, s};
                }
            }
        }
    }

    public boolean c(byte[] message, BigInteger r, BigInteger s) {
        BigInteger cofactor;
        ECFieldElement D;
        BigInteger r2 = r;
        BigInteger bigInteger = s;
        ECDomainParameters ec = this.h.b();
        BigInteger n = ec.d();
        BigInteger e = d(n, message);
        BigInteger bigInteger2 = ECConstants.b;
        if (r2.compareTo(bigInteger2) < 0) {
            return false;
        }
        if (r2.compareTo(n) >= 0) {
            return false;
        }
        if (bigInteger.compareTo(bigInteger2) < 0 || bigInteger.compareTo(n) >= 0) {
            return false;
        }
        BigInteger c = bigInteger.modInverse(n);
        ECPoint point = ECAlgorithms.o(ec.b(), e.multiply(c).mod(n), ((ECPublicKeyParameters) this.h).c(), r2.multiply(c).mod(n));
        if (point.t()) {
            return false;
        }
        ECCurve curve = point.i();
        if (curve == null || (cofactor = curve.p()) == null || cofactor.compareTo(ECConstants.f) > 0 || (D = f(curve.q(), point)) == null || D.i()) {
            return point.y().f().t().mod(n).equals(r2);
        }
        ECFieldElement X = point.q();
        while (curve.z(r2)) {
            if (curve.m(r2).j(D).equals(X)) {
                return true;
            }
            r2 = r2.add(n);
            BigInteger bigInteger3 = s;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public BigInteger d(BigInteger n, byte[] message) {
        int log2n = n.bitLength();
        int messageBitLength = message.length * 8;
        BigInteger e = new BigInteger(1, message);
        if (log2n < messageBitLength) {
            return e.shiftRight(messageBitLength - log2n);
        }
        return e;
    }

    /* access modifiers changed from: protected */
    public ECMultiplier e() {
        return new FixedPointCombMultiplier();
    }

    /* access modifiers changed from: protected */
    public ECFieldElement f(int coordinateSystem, ECPoint p) {
        switch (coordinateSystem) {
            case 1:
            case 6:
            case 7:
                return p.s(0);
            case 2:
            case 3:
            case 4:
                return p.s(0).o();
            default:
                return null;
        }
    }

    /* access modifiers changed from: protected */
    public SecureRandom g(boolean needed, SecureRandom provided) {
        if (!needed) {
            return null;
        }
        return provided != null ? provided : new SecureRandom();
    }
}
