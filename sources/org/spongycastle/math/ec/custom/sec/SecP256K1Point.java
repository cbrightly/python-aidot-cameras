package org.spongycastle.math.ec.custom.sec;

import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat256;

public class SecP256K1Point extends ECPoint.AbstractFp {
    public SecP256K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y) {
        this(curve, x, y, false);
    }

    public SecP256K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression) {
        super(curve, x, y);
        if ((x == null) == (y != null ? false : true)) {
            this.f = withCompression;
            return;
        }
        throw new IllegalArgumentException("Exactly one of the field elements is null");
    }

    SecP256K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        super(curve, x, y, zs);
        this.f = withCompression;
    }

    /* access modifiers changed from: protected */
    public ECPoint d() {
        return new SecP256K1Point((ECCurve) null, f(), g());
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
        SecP256K1FieldElement X1 = (SecP256K1FieldElement) this.c;
        SecP256K1FieldElement Y1 = (SecP256K1FieldElement) this.d;
        SecP256K1FieldElement X2 = (SecP256K1FieldElement) b.q();
        SecP256K1FieldElement Y2 = (SecP256K1FieldElement) b.r();
        SecP256K1FieldElement Z1 = (SecP256K1FieldElement) this.e[0];
        SecP256K1FieldElement Z2 = (SecP256K1FieldElement) eCPoint.s(0);
        int[] tt1 = Nat256.h();
        int[] t2 = Nat256.f();
        int[] t3 = Nat256.f();
        int[] t4 = Nat256.f();
        boolean Z1IsOne = Z1.h();
        if (Z1IsOne) {
            int[] U22 = X2.h;
            S2 = Y2.h;
            U2 = U22;
        } else {
            S2 = t3;
            SecP256K1Field.i(Z1.h, S2);
            int[] U23 = t2;
            SecP256K1Field.d(S2, X2.h, U23);
            SecP256K1Field.d(S2, Z1.h, S2);
            SecP256K1Field.d(S2, Y2.h, S2);
            U2 = U23;
        }
        boolean Z2IsOne = Z2.h();
        if (Z2IsOne) {
            U1 = X1.h;
            U12 = Y1.h;
        } else {
            int[] S1 = t4;
            SecP256K1Field.i(Z2.h, S1);
            int[] U13 = tt1;
            SecP256K1Field.d(S1, X1.h, U13);
            SecP256K1Field.d(S1, Z2.h, S1);
            SecP256K1Field.d(S1, Y1.h, S1);
            U1 = U13;
            U12 = S1;
        }
        int[] S12 = Nat256.f();
        SecP256K1Field.k(U1, U2, S12);
        int[] R = t2;
        SecP256K1Field.k(U12, S2, R);
        if (!Nat256.t(S12)) {
            SecP256K1FieldElement secP256K1FieldElement = X1;
            int[] HSquared = t3;
            SecP256K1Field.i(S12, HSquared);
            SecP256K1FieldElement secP256K1FieldElement2 = Y1;
            int[] G = Nat256.f();
            SecP256K1Field.d(HSquared, S12, G);
            SecP256K1FieldElement secP256K1FieldElement3 = X2;
            int[] V = t3;
            SecP256K1Field.d(HSquared, U1, V);
            SecP256K1Field.f(G, G);
            Nat256.w(U12, G, tt1);
            int[] iArr = HSquared;
            SecP256K1Field.h(Nat256.b(V, V, G), G);
            int[] iArr2 = U12;
            SecP256K1FieldElement X3 = new SecP256K1FieldElement(t4);
            int[] iArr3 = U1;
            SecP256K1Field.i(R, X3.h);
            int[] iArr4 = X3.h;
            SecP256K1Field.k(iArr4, G, iArr4);
            SecP256K1FieldElement Y3 = new SecP256K1FieldElement(G);
            int[] iArr5 = S2;
            SecP256K1FieldElement X32 = X3;
            SecP256K1Field.k(V, X3.h, Y3.h);
            SecP256K1Field.e(Y3.h, R, tt1);
            SecP256K1Field.g(tt1, Y3.h);
            SecP256K1FieldElement Z3 = new SecP256K1FieldElement(S12);
            if (!Z1IsOne) {
                int[] iArr6 = Z3.h;
                int[] iArr7 = R;
                SecP256K1Field.d(iArr6, Z1.h, iArr6);
            }
            if (!Z2IsOne) {
                int[] iArr8 = Z3.h;
                SecP256K1Field.d(iArr8, Z2.h, iArr8);
            }
            ECFieldElement[] zs = {Z3};
            int[] iArr9 = S12;
            SecP256K1FieldElement Y32 = Y3;
            SecP256K1FieldElement secP256K1FieldElement4 = Z3;
            int[] iArr10 = U2;
            int[] iArr11 = t4;
            return new SecP256K1Point(curve, X32, Y32, zs, this.f);
        } else if (Nat256.t(R)) {
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
        SecP256K1FieldElement Y1 = (SecP256K1FieldElement) this.d;
        if (Y1.i()) {
            return curve.u();
        }
        SecP256K1FieldElement X1 = (SecP256K1FieldElement) this.c;
        SecP256K1FieldElement Z1 = (SecP256K1FieldElement) this.e[0];
        int[] Y1Squared = Nat256.f();
        SecP256K1Field.i(Y1.h, Y1Squared);
        int[] T = Nat256.f();
        SecP256K1Field.i(Y1Squared, T);
        int[] M = Nat256.f();
        SecP256K1Field.i(X1.h, M);
        SecP256K1Field.h(Nat256.b(M, M, M), M);
        int[] S = Y1Squared;
        SecP256K1Field.d(Y1Squared, X1.h, S);
        SecP256K1Field.h(Nat.F(8, S, 2, 0), S);
        int[] t1 = Nat256.f();
        int c = Nat.G(8, T, 3, 0, t1);
        SecP256K1Field.h(c, t1);
        SecP256K1FieldElement X3 = new SecP256K1FieldElement(T);
        SecP256K1Field.i(M, X3.h);
        int[] iArr = X3.h;
        SecP256K1Field.k(iArr, S, iArr);
        int[] iArr2 = X3.h;
        SecP256K1Field.k(iArr2, S, iArr2);
        SecP256K1FieldElement Y3 = new SecP256K1FieldElement(S);
        SecP256K1Field.k(S, X3.h, Y3.h);
        int[] iArr3 = Y3.h;
        SecP256K1Field.d(iArr3, M, iArr3);
        int[] iArr4 = Y3.h;
        SecP256K1Field.k(iArr4, t1, iArr4);
        SecP256K1FieldElement Z3 = new SecP256K1FieldElement(M);
        SecP256K1Field.l(Y1.h, Z3.h);
        if (!Z1.h()) {
            int[] iArr5 = Z3.h;
            SecP256K1Field.d(iArr5, Z1.h, iArr5);
        }
        SecP256K1FieldElement secP256K1FieldElement = Z3;
        SecP256K1FieldElement secP256K1FieldElement2 = Y3;
        SecP256K1FieldElement secP256K1FieldElement3 = X3;
        int i = c;
        return new SecP256K1Point(curve, X3, Y3, new ECFieldElement[]{Z3}, this.f);
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
        return new SecP256K1Point(this.b, this.c, this.d.m(), this.e, this.f);
    }
}
