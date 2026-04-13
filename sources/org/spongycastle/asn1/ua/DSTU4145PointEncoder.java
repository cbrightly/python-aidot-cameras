package org.spongycastle.asn1.ua;

import java.math.BigInteger;
import java.util.Random;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;

public abstract class DSTU4145PointEncoder {
    private static ECFieldElement d(ECFieldElement fe) {
        ECFieldElement t = fe;
        for (int i = 1; i < fe.f(); i++) {
            t = t.o().a(fe);
        }
        return t;
    }

    private static ECFieldElement c(ECCurve curve, ECFieldElement beta) {
        ECFieldElement z;
        if (beta.i()) {
            return beta;
        }
        ECFieldElement zeroElement = curve.m(ECConstants.a);
        Random rand = new Random();
        int m = beta.f();
        do {
            ECFieldElement t = curve.m(new BigInteger(m, rand));
            z = zeroElement;
            ECFieldElement w = beta;
            for (int i = 1; i <= m - 1; i++) {
                ECFieldElement w2 = w.o();
                z = z.o().a(w2.j(t));
                w = w2.a(beta);
            }
            if (w.i() == 0) {
                return null;
            }
        } while (z.o().a(z).i());
        return z;
    }

    public static byte[] b(ECPoint Q) {
        ECPoint Q2 = Q.y();
        ECFieldElement x = Q2.f();
        byte[] bytes = x.e();
        if (!x.i()) {
            if (d(Q2.g().d(x)).h()) {
                int length = bytes.length - 1;
                bytes[length] = (byte) (bytes[length] | 1);
            } else {
                int length2 = bytes.length - 1;
                bytes[length2] = (byte) (bytes[length2] & 254);
            }
        }
        return bytes;
    }

    public static ECPoint a(ECCurve curve, byte[] bytes) {
        ECFieldElement k = curve.m(BigInteger.valueOf((long) (bytes[bytes.length - 1] & 1)));
        ECFieldElement xp = curve.m(new BigInteger(1, bytes));
        if (!d(xp).equals(curve.n())) {
            xp = xp.b();
        }
        ECFieldElement yp = null;
        if (xp.i()) {
            yp = curve.o().n();
        } else {
            ECFieldElement z = c(curve, xp.o().g().j(curve.o()).a(curve.n()).a(xp));
            if (z != null) {
                if (!d(z).equals(k)) {
                    z = z.b();
                }
                yp = xp.j(z);
            }
        }
        if (yp != null) {
            return curve.E(xp.t(), yp.t());
        }
        throw new IllegalArgumentException("Invalid point compression");
    }
}
