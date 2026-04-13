package org.spongycastle.math.ec.custom.sec;

import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat192;

public class SecP192K1Point extends ECPoint.AbstractFp {
    public SecP192K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y) {
        this(curve, x, y, false);
    }

    public SecP192K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression) {
        super(curve, x, y);
        if ((x == null) == (y != null ? false : true)) {
            this.f = withCompression;
            return;
        }
        throw new IllegalArgumentException("Exactly one of the field elements is null");
    }

    SecP192K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        super(curve, x, y, zs);
        this.f = withCompression;
    }

    /* access modifiers changed from: protected */
    public ECPoint d() {
        return new SecP192K1Point((ECCurve) null, f(), g());
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
        SecP192K1FieldElement X1 = (SecP192K1FieldElement) this.c;
        SecP192K1FieldElement Y1 = (SecP192K1FieldElement) this.d;
        SecP192K1FieldElement X2 = (SecP192K1FieldElement) b.q();
        SecP192K1FieldElement Y2 = (SecP192K1FieldElement) b.r();
        SecP192K1FieldElement Z1 = (SecP192K1FieldElement) this.e[0];
        SecP192K1FieldElement Z2 = (SecP192K1FieldElement) eCPoint.s(0);
        int[] tt1 = Nat192.g();
        int[] t2 = Nat192.e();
        int[] t3 = Nat192.e();
        int[] t4 = Nat192.e();
        boolean Z1IsOne = Z1.h();
        if (Z1IsOne) {
            int[] U22 = X2.h;
            S2 = Y2.h;
            U2 = U22;
        } else {
            S2 = t3;
            SecP192K1Field.i(Z1.h, S2);
            int[] U23 = t2;
            SecP192K1Field.d(S2, X2.h, U23);
            SecP192K1Field.d(S2, Z1.h, S2);
            SecP192K1Field.d(S2, Y2.h, S2);
            U2 = U23;
        }
        boolean Z2IsOne = Z2.h();
        if (Z2IsOne) {
            U1 = X1.h;
            U12 = Y1.h;
        } else {
            int[] S1 = t4;
            SecP192K1Field.i(Z2.h, S1);
            int[] U13 = tt1;
            SecP192K1Field.d(S1, X1.h, U13);
            SecP192K1Field.d(S1, Z2.h, S1);
            SecP192K1Field.d(S1, Y1.h, S1);
            U1 = U13;
            U12 = S1;
        }
        int[] S12 = Nat192.e();
        SecP192K1Field.k(U1, U2, S12);
        int[] R = t2;
        SecP192K1Field.k(U12, S2, R);
        if (!Nat192.s(S12)) {
            SecP192K1FieldElement secP192K1FieldElement = X1;
            int[] HSquared = t3;
            SecP192K1Field.i(S12, HSquared);
            SecP192K1FieldElement secP192K1FieldElement2 = Y1;
            int[] G = Nat192.e();
            SecP192K1Field.d(HSquared, S12, G);
            SecP192K1FieldElement secP192K1FieldElement3 = X2;
            int[] V = t3;
            SecP192K1Field.d(HSquared, U1, V);
            SecP192K1Field.f(G, G);
            Nat192.v(U12, G, tt1);
            int[] iArr = HSquared;
            SecP192K1Field.h(Nat192.b(V, V, G), G);
            int[] iArr2 = U12;
            SecP192K1FieldElement X3 = new SecP192K1FieldElement(t4);
            int[] iArr3 = U1;
            SecP192K1Field.i(R, X3.h);
            int[] iArr4 = X3.h;
            SecP192K1Field.k(iArr4, G, iArr4);
            SecP192K1FieldElement Y3 = new SecP192K1FieldElement(G);
            int[] iArr5 = S2;
            SecP192K1FieldElement X32 = X3;
            SecP192K1Field.k(V, X3.h, Y3.h);
            SecP192K1Field.e(Y3.h, R, tt1);
            SecP192K1Field.g(tt1, Y3.h);
            SecP192K1FieldElement Z3 = new SecP192K1FieldElement(S12);
            if (!Z1IsOne) {
                int[] iArr6 = Z3.h;
                int[] iArr7 = R;
                SecP192K1Field.d(iArr6, Z1.h, iArr6);
            }
            if (!Z2IsOne) {
                int[] iArr8 = Z3.h;
                SecP192K1Field.d(iArr8, Z2.h, iArr8);
            }
            ECFieldElement[] zs = {Z3};
            int[] iArr9 = S12;
            SecP192K1FieldElement Y32 = Y3;
            SecP192K1FieldElement secP192K1FieldElement4 = Z3;
            int[] iArr10 = U2;
            int[] iArr11 = t4;
            return new SecP192K1Point(curve, X32, Y32, zs, this.f);
        } else if (Nat192.s(R)) {
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
        SecP192K1FieldElement Y1 = (SecP192K1FieldElement) this.d;
        if (Y1.i()) {
            return curve.u();
        }
        SecP192K1FieldElement X1 = (SecP192K1FieldElement) this.c;
        SecP192K1FieldElement Z1 = (SecP192K1FieldElement) this.e[0];
        int[] Y1Squared = Nat192.e();
        SecP192K1Field.i(Y1.h, Y1Squared);
        int[] T = Nat192.e();
        SecP192K1Field.i(Y1Squared, T);
        int[] M = Nat192.e();
        SecP192K1Field.i(X1.h, M);
        SecP192K1Field.h(Nat192.b(M, M, M), M);
        int[] S = Y1Squared;
        SecP192K1Field.d(Y1Squared, X1.h, S);
        SecP192K1Field.h(Nat.F(6, S, 2, 0), S);
        int[] t1 = Nat192.e();
        int c = Nat.G(6, T, 3, 0, t1);
        SecP192K1Field.h(c, t1);
        SecP192K1FieldElement X3 = new SecP192K1FieldElement(T);
        SecP192K1Field.i(M, X3.h);
        int[] iArr = X3.h;
        SecP192K1Field.k(iArr, S, iArr);
        int[] iArr2 = X3.h;
        SecP192K1Field.k(iArr2, S, iArr2);
        SecP192K1FieldElement Y3 = new SecP192K1FieldElement(S);
        SecP192K1Field.k(S, X3.h, Y3.h);
        int[] iArr3 = Y3.h;
        SecP192K1Field.d(iArr3, M, iArr3);
        int[] iArr4 = Y3.h;
        SecP192K1Field.k(iArr4, t1, iArr4);
        SecP192K1FieldElement Z3 = new SecP192K1FieldElement(M);
        SecP192K1Field.l(Y1.h, Z3.h);
        if (!Z1.h()) {
            int[] iArr5 = Z3.h;
            SecP192K1Field.d(iArr5, Z1.h, iArr5);
        }
        SecP192K1FieldElement secP192K1FieldElement = Z3;
        SecP192K1FieldElement secP192K1FieldElement2 = Y3;
        SecP192K1FieldElement secP192K1FieldElement3 = X3;
        int i = c;
        return new SecP192K1Point(curve, X3, Y3, new ECFieldElement[]{Z3}, this.f);
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
        return new SecP192K1Point(this.b, this.c, this.d.m(), this.e, this.f);
    }
}
