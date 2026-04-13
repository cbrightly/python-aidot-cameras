package org.spongycastle.pqc.crypto.mceliece;

import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;
import org.spongycastle.pqc.math.linearalgebra.GF2Vector;
import org.spongycastle.pqc.math.linearalgebra.GF2mField;
import org.spongycastle.pqc.math.linearalgebra.GoppaCode;
import org.spongycastle.pqc.math.linearalgebra.Permutation;
import org.spongycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;

public final class McElieceCCA2Primitives {
    private McElieceCCA2Primitives() {
    }

    public static GF2Vector b(McElieceCCA2PublicKeyParameters pubKey, GF2Vector m, GF2Vector z) {
        return (GF2Vector) pubKey.c().q(m).a(z);
    }

    public static GF2Vector[] a(McElieceCCA2PrivateKeyParameters privKey, GF2Vector c) {
        int k = privKey.f();
        Permutation p = privKey.h();
        GF2mField field = privKey.c();
        PolynomialGF2mSmallM gp = privKey.d();
        GF2Matrix h = privKey.e();
        PolynomialGF2mSmallM[] q = privKey.i();
        GF2Vector cPInv = (GF2Vector) c.i(p.a());
        GF2Vector errors = GoppaCode.c((GF2Vector) h.t(cPInv), field, gp, q);
        return new GF2Vector[]{((GF2Vector) ((GF2Vector) cPInv.a(errors)).i(p)).d(k), (GF2Vector) errors.i(p)};
    }
}
