package org.spongycastle.math.ec.custom.sec;

import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.raw.Nat;

public class SecP521R1Point extends ECPoint.AbstractFp {
    public SecP521R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y) {
        this(curve, x, y, false);
    }

    public SecP521R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression) {
        super(curve, x, y);
        if ((x == null) == (y != null ? false : true)) {
            this.f = withCompression;
            return;
        }
        throw new IllegalArgumentException("Exactly one of the field elements is null");
    }

    SecP521R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        super(curve, x, y, zs);
        this.f = withCompression;
    }

    /* access modifiers changed from: protected */
    public ECPoint d() {
        return new SecP521R1Point((ECCurve) null, f(), g());
    }

    public ECPoint a(ECPoint b) {
        int[] U2;
        int[] S2;
        int[] S1;
        int[] S12;
        SecP521R1FieldElement Y3;
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
        SecP521R1FieldElement X1 = (SecP521R1FieldElement) this.c;
        SecP521R1FieldElement Y1 = (SecP521R1FieldElement) this.d;
        SecP521R1FieldElement X2 = (SecP521R1FieldElement) b.q();
        SecP521R1FieldElement Y2 = (SecP521R1FieldElement) b.r();
        SecP521R1FieldElement Z1 = (SecP521R1FieldElement) this.e[0];
        SecP521R1FieldElement Z2 = (SecP521R1FieldElement) eCPoint.s(0);
        int[] t1 = Nat.i(17);
        int[] t2 = Nat.i(17);
        int[] t3 = Nat.i(17);
        int[] t4 = Nat.i(17);
        boolean Z1IsOne = Z1.h();
        if (Z1IsOne) {
            U2 = X2.h;
            S2 = Y2.h;
        } else {
            int[] S22 = t3;
            SecP521R1Field.j(Z1.h, S22);
            U2 = t2;
            SecP521R1Field.f(S22, X2.h, U2);
            SecP521R1Field.f(S22, Z1.h, S22);
            SecP521R1Field.f(S22, Y2.h, S22);
            S2 = S22;
        }
        boolean Z2IsOne = Z2.h();
        if (Z2IsOne) {
            int[] U1 = X1.h;
            S12 = Y1.h;
            S1 = U1;
        } else {
            int[] S13 = t4;
            SecP521R1Field.j(Z2.h, S13);
            int[] U12 = t1;
            SecP521R1Field.f(S13, X1.h, U12);
            SecP521R1Field.f(S13, Z2.h, S13);
            SecP521R1Field.f(S13, Y1.h, S13);
            S12 = S13;
            S1 = U12;
        }
        SecP521R1FieldElement secP521R1FieldElement = X1;
        int[] H = Nat.i(17);
        SecP521R1Field.l(S1, U2, H);
        int[] R = t2;
        SecP521R1Field.l(S12, S2, R);
        int[] iArr = S2;
        if (!Nat.v(17, H)) {
            int[] HSquared = t3;
            SecP521R1Field.j(H, HSquared);
            SecP521R1FieldElement secP521R1FieldElement2 = Y1;
            int[] G = Nat.i(17);
            SecP521R1Field.f(HSquared, H, G);
            SecP521R1FieldElement secP521R1FieldElement3 = X2;
            int[] V = t3;
            SecP521R1Field.f(HSquared, S1, V);
            SecP521R1Field.f(S12, G, t1);
            int[] iArr2 = S12;
            SecP521R1FieldElement X3 = new SecP521R1FieldElement(t4);
            int[] iArr3 = S1;
            SecP521R1Field.j(R, X3.h);
            int[] iArr4 = X3.h;
            SecP521R1Field.a(iArr4, G, iArr4);
            int[] iArr5 = X3.h;
            SecP521R1Field.l(iArr5, V, iArr5);
            int[] iArr6 = X3.h;
            SecP521R1Field.l(iArr6, V, iArr6);
            SecP521R1FieldElement Y32 = new SecP521R1FieldElement(G);
            int[] iArr7 = HSquared;
            int[] iArr8 = U2;
            SecP521R1Field.l(V, X3.h, Y32.h);
            SecP521R1Field.f(Y32.h, R, t2);
            SecP521R1Field.l(t2, t1, Y32.h);
            SecP521R1FieldElement Z3 = new SecP521R1FieldElement(H);
            if (!Z1IsOne) {
                int[] iArr9 = Z3.h;
                Y3 = Y32;
                SecP521R1Field.f(iArr9, Z1.h, iArr9);
            } else {
                Y3 = Y32;
            }
            if (!Z2IsOne) {
                int[] iArr10 = Z3.h;
                SecP521R1Field.f(iArr10, Z2.h, iArr10);
            }
            ECFieldElement[] zs = {Z3};
            int[] iArr11 = R;
            SecP521R1FieldElement secP521R1FieldElement4 = Z3;
            int[] iArr12 = t4;
            int[] iArr13 = t2;
            return new SecP521R1Point(curve, X3, Y3, zs, this.f);
        } else if (Nat.v(17, R)) {
            return H();
        } else {
            return curve.u();
        }
    }

    public ECPoint H() {
        int[] Z1Squared;
        if (t()) {
            return this;
        }
        ECCurve curve = i();
        SecP521R1FieldElement Y1 = (SecP521R1FieldElement) this.d;
        if (Y1.i()) {
            return curve.u();
        }
        SecP521R1FieldElement X1 = (SecP521R1FieldElement) this.c;
        SecP521R1FieldElement Z1 = (SecP521R1FieldElement) this.e[0];
        int[] t1 = Nat.i(17);
        int[] t2 = Nat.i(17);
        int[] Y1Squared = Nat.i(17);
        SecP521R1Field.j(Y1.h, Y1Squared);
        int[] T = Nat.i(17);
        SecP521R1Field.j(Y1Squared, T);
        boolean Z1IsOne = Z1.h();
        int[] Z1Squared2 = Z1.h;
        if (!Z1IsOne) {
            int[] Z1Squared3 = t2;
            SecP521R1Field.j(Z1.h, Z1Squared3);
            Z1Squared = Z1Squared3;
        } else {
            Z1Squared = Z1Squared2;
        }
        SecP521R1Field.l(X1.h, Z1Squared, t1);
        int[] M = t2;
        SecP521R1Field.a(X1.h, Z1Squared, M);
        SecP521R1Field.f(M, t1, M);
        Nat.c(17, M, M, M);
        SecP521R1Field.i(M);
        int[] S = Y1Squared;
        SecP521R1Field.f(Y1Squared, X1.h, S);
        Nat.F(17, S, 2, 0);
        SecP521R1Field.i(S);
        Nat.G(17, T, 3, 0, t1);
        SecP521R1Field.i(t1);
        SecP521R1FieldElement X3 = new SecP521R1FieldElement(T);
        SecP521R1Field.j(M, X3.h);
        int[] iArr = X3.h;
        SecP521R1Field.l(iArr, S, iArr);
        int[] iArr2 = X3.h;
        SecP521R1Field.l(iArr2, S, iArr2);
        SecP521R1FieldElement Y3 = new SecP521R1FieldElement(S);
        SecP521R1FieldElement X32 = X3;
        SecP521R1Field.l(S, X3.h, Y3.h);
        int[] iArr3 = Y3.h;
        SecP521R1Field.f(iArr3, M, iArr3);
        int[] iArr4 = Y3.h;
        SecP521R1Field.l(iArr4, t1, iArr4);
        SecP521R1FieldElement Z3 = new SecP521R1FieldElement(M);
        SecP521R1FieldElement Y32 = Y3;
        SecP521R1Field.m(Y1.h, Z3.h);
        if (!Z1IsOne) {
            int[] iArr5 = Z3.h;
            SecP521R1Field.f(iArr5, Z1.h, iArr5);
        }
        SecP521R1FieldElement secP521R1FieldElement = Z3;
        int[] iArr6 = S;
        int[] iArr7 = M;
        int[] iArr8 = Z1Squared;
        return new SecP521R1Point(curve, X32, Y32, new ECFieldElement[]{Z3}, this.f);
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
        return new SecP521R1Point(this.b, this.c, this.d.m(), this.e, this.f);
    }
}
