package org.spongycastle.pqc.crypto.mceliece;

import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;
import org.spongycastle.pqc.math.linearalgebra.GF2mField;
import org.spongycastle.pqc.math.linearalgebra.GoppaCode;
import org.spongycastle.pqc.math.linearalgebra.Permutation;
import org.spongycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;
import org.spongycastle.pqc.math.linearalgebra.PolynomialRingGF2m;

public class McElieceCCA2PrivateKeyParameters extends McElieceCCA2KeyParameters {
    private PolynomialGF2mSmallM[] a1;
    private int f;
    private GF2Matrix p0;
    private int q;
    private GF2mField x;
    private PolynomialGF2mSmallM y;
    private Permutation z;

    public McElieceCCA2PrivateKeyParameters(int n, int k, GF2mField field, PolynomialGF2mSmallM gp, Permutation p, String digest) {
        this(n, k, field, gp, GoppaCode.b(field, gp), p, digest);
    }

    public McElieceCCA2PrivateKeyParameters(int n, int k, GF2mField field, PolynomialGF2mSmallM gp, GF2Matrix canonicalCheckMatrix, Permutation p, String digest) {
        super(true, digest);
        this.f = n;
        this.q = k;
        this.x = field;
        this.y = gp;
        this.p0 = canonicalCheckMatrix;
        this.z = p;
        this.a1 = new PolynomialRingGF2m(field, gp).c();
    }

    public int g() {
        return this.f;
    }

    public int f() {
        return this.q;
    }

    public int j() {
        return this.y.l();
    }

    public GF2mField c() {
        return this.x;
    }

    public PolynomialGF2mSmallM d() {
        return this.y;
    }

    public Permutation h() {
        return this.z;
    }

    public GF2Matrix e() {
        return this.p0;
    }

    public PolynomialGF2mSmallM[] i() {
        return this.a1;
    }
}
