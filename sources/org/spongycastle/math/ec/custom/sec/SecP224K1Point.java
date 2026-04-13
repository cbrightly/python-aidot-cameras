package org.spongycastle.math.ec.custom.sec;

import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat224;

public class SecP224K1Point extends ECPoint.AbstractFp {
    public SecP224K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y) {
        this(curve, x, y, false);
    }

    public SecP224K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression) {
        super(curve, x, y);
        if ((x == null) == (y != null ? false : true)) {
            this.f = withCompression;
            return;
        }
        throw new IllegalArgumentException("Exactly one of the field elements is null");
    }

    SecP224K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        super(curve, x, y, zs);
        this.f = withCompression;
    }

    /* access modifiers changed from: protected */
    public ECPoint d() {
        return new SecP224K1Point((ECCurve) null, f(), g());
    }

    public ECPoint a(ECPoint b) {
        int[] U2;
        int[] S2;
        int[] U1;
        int[] U12;
        ECPoint eCPoint = b;
        if (t()) {
            return eCPoint;
        }
        if (b.t()) {
            return this;
        }
        if (this == eCPoint) {
            return H();
        }
        ECCurve curve = i();
        SecP224K1FieldElement X1 = (SecP224K1FieldElement) this.c;
        SecP224K1FieldElement Y1 = (SecP224K1FieldElement) this.d;
        SecP224K1FieldElement X2 = (SecP224K1FieldElement) b.q();
        SecP224K1FieldElement Y2 = (SecP224K1FieldElement) b.r();
        SecP224K1FieldElement Z1 = (SecP224K1FieldElement) this.e[0];
        SecP224K1FieldElement Z2 = (SecP224K1FieldElement) eCPoint.s(0);
        int[] tt1 = Nat224.e();
        int[] t2 = Nat224.d();
        int[] t3 = Nat224.d();
        int[] t4 = Nat224.d();
        boolean Z1IsOne = Z1.h();
        if (Z1IsOne) {
            int[] U22 = X2.i;
            S2 = Y2.i;
            U2 = U22;
        } else {
            S2 = t3;
            SecP224K1Field.i(Z1.i, S2);
            int[] U23 = t2;
            SecP224K1Field.d(S2, X2.i, U23);
            SecP224K1Field.d(S2, Z1.i, S2);
            SecP224K1Field.d(S2, Y2.i, S2);
            U2 = U23;
        }
        boolean Z2IsOne = Z2.h();
        if (Z2IsOne) {
            U1 = X1.i;
            U12 = Y1.i;
        } else {
            int[] S1 = t4;
            SecP224K1Field.i(Z2.i, S1);
            int[] U13 = tt1;
            SecP224K1Field.d(S1, X1.i, U13);
            SecP224K1Field.d(S1, Z2.i, S1);
            SecP224K1Field.d(S1, Y1.i, S1);
            U1 = U13;
            U12 = S1;
        }
        int[] S12 = Nat224.d();
        SecP224K1Field.k(U1, U2, S12);
        int[] R = t2;
        SecP224K1Field.k(U12, S2, R);
        if (!Nat224.k(S12)) {
            SecP224K1FieldElement secP224K1FieldElement = X1;
            int[] HSquared = t3;
            SecP224K1Field.i(S12, HSquared);
            SecP224K1FieldElement secP224K1FieldElement2 = Y1;
            int[] G = Nat224.d();
            SecP224K1Field.d(HSquared, S12, G);
            SecP224K1FieldElement secP224K1FieldElement3 = X2;
            int[] V = t3;
            SecP224K1Field.d(HSquared, U1, V);
            SecP224K1Field.f(G, G);
            Nat224.l(U12, G, tt1);
            int[] iArr = HSquared;
            SecP224K1Field.h(Nat224.b(V, V, G), G);
            int[] iArr2 = U12;
            SecP224K1FieldElement X3 = new SecP224K1FieldElement(t4);
            int[] iArr3 = U1;
            SecP224K1Field.i(R, X3.i);
            int[] iArr4 = X3.i;
            SecP224K1Field.k(iArr4, G, iArr4);
            SecP224K1FieldElement Y3 = new SecP224K1FieldElement(G);
            int[] iArr5 = S2;
            SecP224K1FieldElement X32 = X3;
            SecP224K1Field.k(V, X3.i, Y3.i);
            SecP224K1Field.e(Y3.i, R, tt1);
            SecP224K1Field.g(tt1, Y3.i);
            SecP224K1FieldElement Z3 = new SecP224K1FieldElement(S12);
            if (!Z1IsOne) {
                int[] iArr6 = Z3.i;
                int[] iArr7 = R;
                SecP224K1Field.d(iArr6, Z1.i, iArr6);
            }
            if (!Z2IsOne) {
                int[] iArr8 = Z3.i;
                SecP224K1Field.d(iArr8, Z2.i, iArr8);
            }
            ECFieldElement[] zs = {Z3};
            int[] iArr9 = S12;
            SecP224K1FieldElement Y32 = Y3;
            SecP224K1FieldElement secP224K1FieldElement4 = Z3;
            int[] iArr10 = U2;
            int[] iArr11 = t4;
            return new SecP224K1Point(curve, X32, Y32, zs, this.f);
        } else if (Nat224.k(R)) {
            return H();
        } else {
            return curve.u();
        }
    }

    public ECPoint H() {
        if (t()) {
            return this;
        }
        ECCurve curve = i();
        SecP224K1FieldElement Y1 = (SecP224K1FieldElement) this.d;
        if (Y1.i()) {
            return curve.u();
        }
        SecP224K1FieldElement X1 = (SecP224K1FieldElement) this.c;
        SecP224K1FieldElement Z1 = (SecP224K1FieldElement) this.e[0];
        int[] Y1Squared = Nat224.d();
        SecP224K1Field.i(Y1.i, Y1Squared);
        int[] T = Nat224.d();
        SecP224K1Field.i(Y1Squared, T);
        int[] M = Nat224.d();
        SecP224K1Field.i(X1.i, M);
        SecP224K1Field.h(Nat224.b(M, M, M), M);
        int[] S = Y1Squared;
        SecP224K1Field.d(Y1Squared, X1.i, S);
        SecP224K1Field.h(Nat.F(7, S, 2, 0), S);
        int[] t1 = Nat224.d();
        int c = Nat.G(7, T, 3, 0, t1);
        SecP224K1Field.h(c, t1);
        SecP224K1FieldElement X3 = new SecP224K1FieldElement(T);
        SecP224K1Field.i(M, X3.i);
        int[] iArr = X3.i;
        SecP224K1Field.k(iArr, S, iArr);
        int[] iArr2 = X3.i;
        SecP224K1Field.k(iArr2, S, iArr2);
        SecP224K1FieldElement Y3 = new SecP224K1FieldElement(S);
        SecP224K1Field.k(S, X3.i, Y3.i);
        int[] iArr3 = Y3.i;
        SecP224K1Field.d(iArr3, M, iArr3);
        int[] iArr4 = Y3.i;
        SecP224K1Field.k(iArr4, t1, iArr4);
        SecP224K1FieldElement Z3 = new SecP224K1FieldElement(M);
        SecP224K1Field.l(Y1.i, Z3.i);
        if (!Z1.h()) {
            int[] iArr5 = Z3.i;
            SecP224K1Field.d(iArr5, Z1.i, iArr5);
        }
        SecP224K1FieldElement secP224K1FieldElement = Z3;
        SecP224K1FieldElement secP224K1FieldElement2 = Y3;
        SecP224K1FieldElement secP224K1FieldElement3 = X3;
        int i = c;
        return new SecP224K1Point(curve, X3, Y3, new ECFieldElement[]{Z3}, this.f);
    }

    public ECPoint I(ECPoint b) {
        if (this == b) {
            return F();
        }
        if (t()) {
            return b;
        }
        if (b.t()) {
            return H();
        }
        if (this.d.i()) {
            return b;
        }
        return H().a(b);
    }

    public ECPoint F() {
        if (t() || this.d.i()) {
            return this;
        }
        return H().a(this);
    }

    public ECPoint x() {
        if (t()) {
            return this;
        }
        return new SecP224K1Point(this.b, this.c, this.d.m(), this.e, this.f);
    }
}
