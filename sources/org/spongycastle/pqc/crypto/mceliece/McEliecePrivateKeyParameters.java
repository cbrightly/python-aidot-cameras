package org.spongycastle.pqc.crypto.mceliece;

import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;
import org.spongycastle.pqc.math.linearalgebra.GF2mField;
import org.spongycastle.pqc.math.linearalgebra.GoppaCode;
import org.spongycastle.pqc.math.linearalgebra.Permutation;
import org.spongycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;
import org.spongycastle.pqc.math.linearalgebra.PolynomialRingGF2m;

public class McEliecePrivateKeyParameters extends McElieceKeyParameters {
    private Permutation a1;
    private PolynomialGF2mSmallM[] a2;
    private int f;
    private Permutation p0;
    private GF2Matrix p1;
    private int q;
    private GF2mField x;
    private PolynomialGF2mSmallM y;
    private GF2Matrix z;

    public McEliecePrivateKeyParameters(int n, int k, GF2mField field, PolynomialGF2mSmallM gp, Permutation p12, Permutation p2, GF2Matrix sInv) {
        super(true, (McElieceParameters) null);
        this.q = k;
        this.f = n;
        this.x = field;
        this.y = gp;
        this.z = sInv;
        this.p0 = p12;
        this.a1 = p2;
        this.p1 = GoppaCode.b(field, gp);
        this.a2 = new PolynomialRingGF2m(field, gp).c();
    }

    public int f() {
        return this.f;
    }

    public int e() {
        return this.q;
    }

    public GF2mField b() {
        return this.x;
    }

    public PolynomialGF2mSmallM c() {
        return this.y;
    }

    public GF2Matrix j() {
        return this.z;
    }

    public Permutation g() {
        return this.p0;
    }

    public Permutation h() {
        return this.a1;
    }

    public GF2Matrix d() {
        return this.p1;
    }

    public PolynomialGF2mSmallM[] i() {
        return this.a2;
    }
}
