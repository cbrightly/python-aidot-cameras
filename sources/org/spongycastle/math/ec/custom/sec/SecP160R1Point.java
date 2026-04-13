package org.spongycastle.math.ec.custom.sec;

import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat160;

public class SecP160R1Point extends ECPoint.AbstractFp {
    public SecP160R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y) {
        this(curve, x, y, false);
    }

    public SecP160R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression) {
        super(curve, x, y);
        if ((x == null) == (y != null ? false : true)) {
            this.f = withCompression;
            return;
        }
        throw new IllegalArgumentException("Exactly one of the field elements is null");
    }

    SecP160R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        super(curve, x, y, zs);
        this.f = withCompression;
    }

    /* access modifiers changed from: protected */
    public ECPoint d() {
        return new SecP160R1Point((ECCurve) null, f(), g());
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
        SecP160R1FieldElement X1 = (SecP160R1FieldElement) this.c;
        SecP160R1FieldElement Y1 = (SecP160R1FieldElement) this.d;
        SecP160R1FieldElement X2 = (SecP160R1FieldElement) b.q();
        SecP160R1FieldElement Y2 = (SecP160R1FieldElement) b.r();
        SecP160R1FieldElement Z1 = (SecP160R1FieldElement) this.e[0];
        SecP160R1FieldElement Z2 = (SecP160R1FieldElement) eCPoint.s(0);
        int[] tt1 = Nat160.d();
        int[] t2 = Nat160.c();
        int[] t3 = Nat160.c();
        int[] t4 = Nat160.c();
        boolean Z1IsOne = Z1.h();
        if (Z1IsOne) {
            int[] U22 = X2.h;
            S2 = Y2.h;
            U2 = U22;
        } else {
            S2 = t3;
            SecP160R1Field.i(Z1.h, S2);
            int[] U23 = t2;
            SecP160R1Field.d(S2, X2.h, U23);
            SecP160R1Field.d(S2, Z1.h, S2);
            SecP160R1Field.d(S2, Y2.h, S2);
            U2 = U23;
        }
        boolean Z2IsOne = Z2.h();
        if (Z2IsOne) {
            U1 = X1.h;
            U12 = Y1.h;
        } else {
            int[] S1 = t4;
            SecP160R1Field.i(Z2.h, S1);
            int[] U13 = tt1;
            SecP160R1Field.d(S1, X1.h, U13);
            SecP160R1Field.d(S1, Z2.h, S1);
            SecP160R1Field.d(S1, Y1.h, S1);
            U1 = U13;
            U12 = S1;
        }
        int[] S12 = Nat160.c();
        SecP160R1Field.k(U1, U2, S12);
        int[] R = t2;
        SecP160R1Field.k(U12, S2, R);
        if (!Nat160.j(S12)) {
            SecP160R1FieldElement secP160R1FieldElement = X1;
            int[] HSquared = t3;
            SecP160R1Field.i(S12, HSquared);
            SecP160R1FieldElement secP160R1FieldElement2 = Y1;
            int[] G = Nat160.c();
            SecP160R1Field.d(HSquared, S12, G);
            SecP160R1FieldElement secP160R1FieldElement3 = X2;
            int[] V = t3;
            SecP160R1Field.d(HSquared, U1, V);
            SecP160R1Field.f(G, G);
            Nat160.k(U12, G, tt1);
            int[] iArr = HSquared;
            SecP160R1Field.h(Nat160.b(V, V, G), G);
            int[] iArr2 = U12;
            SecP160R1FieldElement X3 = new SecP160R1FieldElement(t4);
            int[] iArr3 = U1;
            SecP160R1Field.i(R, X3.h);
            int[] iArr4 = X3.h;
            SecP160R1Field.k(iArr4, G, iArr4);
            SecP160R1FieldElement Y3 = new SecP160R1FieldElement(G);
            int[] iArr5 = S2;
            SecP160R1FieldElement X32 = X3;
            SecP160R1Field.k(V, X3.h, Y3.h);
            SecP160R1Field.e(Y3.h, R, tt1);
            SecP160R1Field.g(tt1, Y3.h);
            SecP160R1FieldElement Z3 = new SecP160R1FieldElement(S12);
            if (!Z1IsOne) {
                int[] iArr6 = Z3.h;
                int[] iArr7 = R;
                SecP160R1Field.d(iArr6, Z1.h, iArr6);
            }
            if (!Z2IsOne) {
                int[] iArr8 = Z3.h;
                SecP160R1Field.d(iArr8, Z2.h, iArr8);
            }
            ECFieldElement[] zs = {Z3};
            int[] iArr9 = S12;
            SecP160R1FieldElement Y32 = Y3;
            SecP160R1FieldElement secP160R1FieldElement4 = Z3;
            int[] iArr10 = U2;
            int[] iArr11 = t4;
            return new SecP160R1Point(curve, X32, Y32, zs, this.f);
        } else if (Nat160.j(R)) {
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
        SecP160R1FieldElement Y1 = (SecP160R1FieldElement) this.d;
        if (Y1.i()) {
            return curve.u();
        }
        SecP160R1FieldElement X1 = (SecP160R1FieldElement) this.c;
        SecP160R1FieldElement Z1 = (SecP160R1FieldElement) this.e[0];
        int[] t1 = Nat160.c();
        int[] t2 = Nat160.c();
        int[] Y1Squared = Nat160.c();
        SecP160R1Field.i(Y1.h, Y1Squared);
        int[] T = Nat160.c();
        SecP160R1Field.i(Y1Squared, T);
        boolean Z1IsOne = Z1.h();
        int[] Z1Squared2 = Z1.h;
        if (!Z1IsOne) {
            int[] Z1Squared3 = t2;
            SecP160R1Field.i(Z1.h, Z1Squared3);
            Z1Squared = Z1Squared3;
        } else {
            Z1Squared = Z1Squared2;
        }
        SecP160R1Field.k(X1.h, Z1Squared, t1);
        int[] M = t2;
        SecP160R1Field.a(X1.h, Z1Squared, M);
        SecP160R1Field.d(M, t1, M);
        int c = Nat160.b(M, M, M);
        SecP160R1Field.h(c, M);
        int[] S = Y1Squared;
        SecP160R1Field.d(Y1Squared, X1.h, S);
        int i = c;
        int c2 = Nat.F(5, S, 2, 0);
        SecP160R1Field.h(c2, S);
        int i2 = c2;
        int c3 = Nat.G(5, T, 3, 0, t1);
        SecP160R1Field.h(c3, t1);
        SecP160R1FieldElement X3 = new SecP160R1FieldElement(T);
        SecP160R1Field.i(M, X3.h);
        int[] iArr = X3.h;
        SecP160R1Field.k(iArr, S, iArr);
        int[] iArr2 = X3.h;
        SecP160R1Field.k(iArr2, S, iArr2);
        SecP160R1FieldElement Y3 = new SecP160R1FieldElement(S);
        int i3 = c3;
        SecP160R1FieldElement X32 = X3;
        SecP160R1Field.k(S, X3.h, Y3.h);
        int[] iArr3 = Y3.h;
        SecP160R1Field.d(iArr3, M, iArr3);
        int[] iArr4 = Y3.h;
        SecP160R1Field.k(iArr4, t1, iArr4);
        SecP160R1FieldElement Z3 = new SecP160R1FieldElement(M);
        SecP160R1FieldElement Y32 = Y3;
        SecP160R1Field.l(Y1.h, Z3.h);
        if (!Z1IsOne) {
            int[] iArr5 = Z3.h;
            SecP160R1Field.d(iArr5, Z1.h, iArr5);
        }
        SecP160R1FieldElement secP160R1FieldElement = Z3;
        int[] iArr6 = S;
        int[] iArr7 = M;
        int[] iArr8 = Z1Squared;
        return new SecP160R1Point(curve, X32, Y32, new ECFieldElement[]{Z3}, this.f);
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
        return new SecP160R1Point(this.b, this.c, this.d.m(), this.e, this.f);
    }
}
