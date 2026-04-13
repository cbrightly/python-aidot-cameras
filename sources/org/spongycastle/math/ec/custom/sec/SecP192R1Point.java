package org.spongycastle.math.ec.custom.sec;

import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat192;

public class SecP192R1Point extends ECPoint.AbstractFp {
    public SecP192R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y) {
        this(curve, x, y, false);
    }

    public SecP192R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression) {
        super(curve, x, y);
        if ((x == null) == (y != null ? false : true)) {
            this.f = withCompression;
            return;
        }
        throw new IllegalArgumentException("Exactly one of the field elements is null");
    }

    SecP192R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        super(curve, x, y, zs);
        this.f = withCompression;
    }

    /* access modifiers changed from: protected */
    public ECPoint d() {
        return new SecP192R1Point((ECCurve) null, f(), g());
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
        SecP192R1FieldElement X1 = (SecP192R1FieldElement) this.c;
        SecP192R1FieldElement Y1 = (SecP192R1FieldElement) this.d;
        SecP192R1FieldElement X2 = (SecP192R1FieldElement) b.q();
        SecP192R1FieldElement Y2 = (SecP192R1FieldElement) b.r();
        SecP192R1FieldElement Z1 = (SecP192R1FieldElement) this.e[0];
        SecP192R1FieldElement Z2 = (SecP192R1FieldElement) eCPoint.s(0);
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
            SecP192R1Field.j(Z1.h, S2);
            int[] U23 = t2;
            SecP192R1Field.e(S2, X2.h, U23);
            SecP192R1Field.e(S2, Z1.h, S2);
            SecP192R1Field.e(S2, Y2.h, S2);
            U2 = U23;
        }
        boolean Z2IsOne = Z2.h();
        if (Z2IsOne) {
            U1 = X1.h;
            U12 = Y1.h;
        } else {
            int[] S1 = t4;
            SecP192R1Field.j(Z2.h, S1);
            int[] U13 = tt1;
            SecP192R1Field.e(S1, X1.h, U13);
            SecP192R1Field.e(S1, Z2.h, S1);
            SecP192R1Field.e(S1, Y1.h, S1);
            U1 = U13;
            U12 = S1;
        }
        int[] S12 = Nat192.e();
        SecP192R1Field.m(U1, U2, S12);
        int[] R = t2;
        SecP192R1Field.m(U12, S2, R);
        if (!Nat192.s(S12)) {
            SecP192R1FieldElement secP192R1FieldElement = X1;
            int[] HSquared = t3;
            SecP192R1Field.j(S12, HSquared);
            SecP192R1FieldElement secP192R1FieldElement2 = Y1;
            int[] G = Nat192.e();
            SecP192R1Field.e(HSquared, S12, G);
            SecP192R1FieldElement secP192R1FieldElement3 = X2;
            int[] V = t3;
            SecP192R1Field.e(HSquared, U1, V);
            SecP192R1Field.g(G, G);
            Nat192.v(U12, G, tt1);
            int[] iArr = HSquared;
            SecP192R1Field.i(Nat192.b(V, V, G), G);
            int[] iArr2 = U12;
            SecP192R1FieldElement X3 = new SecP192R1FieldElement(t4);
            int[] iArr3 = U1;
            SecP192R1Field.j(R, X3.h);
            int[] iArr4 = X3.h;
            SecP192R1Field.m(iArr4, G, iArr4);
            SecP192R1FieldElement Y3 = new SecP192R1FieldElement(G);
            int[] iArr5 = S2;
            SecP192R1FieldElement X32 = X3;
            SecP192R1Field.m(V, X3.h, Y3.h);
            SecP192R1Field.f(Y3.h, R, tt1);
            SecP192R1Field.h(tt1, Y3.h);
            SecP192R1FieldElement Z3 = new SecP192R1FieldElement(S12);
            if (!Z1IsOne) {
                int[] iArr6 = Z3.h;
                int[] iArr7 = R;
                SecP192R1Field.e(iArr6, Z1.h, iArr6);
            }
            if (!Z2IsOne) {
                int[] iArr8 = Z3.h;
                SecP192R1Field.e(iArr8, Z2.h, iArr8);
            }
            ECFieldElement[] zs = {Z3};
            int[] iArr9 = S12;
            SecP192R1FieldElement Y32 = Y3;
            SecP192R1FieldElement secP192R1FieldElement4 = Z3;
            int[] iArr10 = U2;
            int[] iArr11 = t4;
            return new SecP192R1Point(curve, X32, Y32, zs, this.f);
        } else if (Nat192.s(R)) {
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
        SecP192R1FieldElement Y1 = (SecP192R1FieldElement) this.d;
        if (Y1.i()) {
            return curve.u();
        }
        SecP192R1FieldElement X1 = (SecP192R1FieldElement) this.c;
        SecP192R1FieldElement Z1 = (SecP192R1FieldElement) this.e[0];
        int[] t1 = Nat192.e();
        int[] t2 = Nat192.e();
        int[] Y1Squared = Nat192.e();
        SecP192R1Field.j(Y1.h, Y1Squared);
        int[] T = Nat192.e();
        SecP192R1Field.j(Y1Squared, T);
        boolean Z1IsOne = Z1.h();
        int[] Z1Squared2 = Z1.h;
        if (!Z1IsOne) {
            int[] Z1Squared3 = t2;
            SecP192R1Field.j(Z1.h, Z1Squared3);
            Z1Squared = Z1Squared3;
        } else {
            Z1Squared = Z1Squared2;
        }
        SecP192R1Field.m(X1.h, Z1Squared, t1);
        int[] M = t2;
        SecP192R1Field.a(X1.h, Z1Squared, M);
        SecP192R1Field.e(M, t1, M);
        int c = Nat192.b(M, M, M);
        SecP192R1Field.i(c, M);
        int[] S = Y1Squared;
        SecP192R1Field.e(Y1Squared, X1.h, S);
        int i = c;
        int c2 = Nat.F(6, S, 2, 0);
        SecP192R1Field.i(c2, S);
        int i2 = c2;
        int c3 = Nat.G(6, T, 3, 0, t1);
        SecP192R1Field.i(c3, t1);
        SecP192R1FieldElement X3 = new SecP192R1FieldElement(T);
        SecP192R1Field.j(M, X3.h);
        int[] iArr = X3.h;
        SecP192R1Field.m(iArr, S, iArr);
        int[] iArr2 = X3.h;
        SecP192R1Field.m(iArr2, S, iArr2);
        SecP192R1FieldElement Y3 = new SecP192R1FieldElement(S);
        int i3 = c3;
        SecP192R1FieldElement X32 = X3;
        SecP192R1Field.m(S, X3.h, Y3.h);
        int[] iArr3 = Y3.h;
        SecP192R1Field.e(iArr3, M, iArr3);
        int[] iArr4 = Y3.h;
        SecP192R1Field.m(iArr4, t1, iArr4);
        SecP192R1FieldElement Z3 = new SecP192R1FieldElement(M);
        SecP192R1FieldElement Y32 = Y3;
        SecP192R1Field.n(Y1.h, Z3.h);
        if (!Z1IsOne) {
            int[] iArr5 = Z3.h;
            SecP192R1Field.e(iArr5, Z1.h, iArr5);
        }
        SecP192R1FieldElement secP192R1FieldElement = Z3;
        int[] iArr6 = S;
        int[] iArr7 = M;
        int[] iArr8 = Z1Squared;
        return new SecP192R1Point(curve, X32, Y32, new ECFieldElement[]{Z3}, this.f);
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
        return new SecP192R1Point(this.b, this.c, this.d.m(), this.e, this.f);
    }
}
