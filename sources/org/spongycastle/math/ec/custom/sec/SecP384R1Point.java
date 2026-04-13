package org.spongycastle.math.ec.custom.sec;

import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat384;

public class SecP384R1Point extends ECPoint.AbstractFp {
    public SecP384R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y) {
        this(curve, x, y, false);
    }

    public SecP384R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression) {
        super(curve, x, y);
        if ((x == null) == (y != null ? false : true)) {
            this.f = withCompression;
            return;
        }
        throw new IllegalArgumentException("Exactly one of the field elements is null");
    }

    SecP384R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        super(curve, x, y, zs);
        this.f = withCompression;
    }

    /* access modifiers changed from: protected */
    public ECPoint d() {
        return new SecP384R1Point((ECCurve) null, f(), g());
    }

    public ECPoint a(ECPoint b) {
        int[] U2;
        int[] S2;
        int[] S1;
        int[] S12;
        SecP384R1FieldElement Y3;
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
        SecP384R1FieldElement X1 = (SecP384R1FieldElement) this.c;
        SecP384R1FieldElement Y1 = (SecP384R1FieldElement) this.d;
        SecP384R1FieldElement X2 = (SecP384R1FieldElement) b.q();
        SecP384R1FieldElement Y2 = (SecP384R1FieldElement) b.r();
        SecP384R1FieldElement Z1 = (SecP384R1FieldElement) this.e[0];
        SecP384R1FieldElement Z2 = (SecP384R1FieldElement) eCPoint.s(0);
        int[] tt1 = Nat.i(24);
        int[] tt2 = Nat.i(24);
        int[] t3 = Nat.i(12);
        int[] t4 = Nat.i(12);
        boolean Z1IsOne = Z1.h();
        if (Z1IsOne) {
            U2 = X2.h;
            S2 = Y2.h;
        } else {
            int[] S22 = t3;
            SecP384R1Field.j(Z1.h, S22);
            U2 = tt2;
            SecP384R1Field.f(S22, X2.h, U2);
            SecP384R1Field.f(S22, Z1.h, S22);
            SecP384R1Field.f(S22, Y2.h, S22);
            S2 = S22;
        }
        boolean Z2IsOne = Z2.h();
        if (Z2IsOne) {
            int[] U1 = X1.h;
            S12 = Y1.h;
            S1 = U1;
        } else {
            int[] S13 = t4;
            SecP384R1Field.j(Z2.h, S13);
            int[] U12 = tt1;
            SecP384R1Field.f(S13, X1.h, U12);
            SecP384R1Field.f(S13, Z2.h, S13);
            SecP384R1Field.f(S13, Y1.h, S13);
            S12 = S13;
            S1 = U12;
        }
        SecP384R1FieldElement secP384R1FieldElement = X1;
        int[] H = Nat.i(12);
        SecP384R1Field.m(S1, U2, H);
        SecP384R1FieldElement secP384R1FieldElement2 = Y1;
        int[] R = Nat.i(12);
        SecP384R1Field.m(S12, S2, R);
        if (!Nat.v(12, H)) {
            int[] HSquared = t3;
            SecP384R1Field.j(H, HSquared);
            SecP384R1FieldElement secP384R1FieldElement3 = X2;
            int[] G = Nat.i(12);
            SecP384R1Field.f(HSquared, H, G);
            SecP384R1FieldElement secP384R1FieldElement4 = Y2;
            int[] V = t3;
            SecP384R1Field.f(HSquared, S1, V);
            SecP384R1Field.g(G, G);
            Nat384.a(S12, G, tt1);
            int[] iArr = S12;
            int c = Nat.c(12, V, V, G);
            SecP384R1Field.i(c, G);
            int i = c;
            SecP384R1FieldElement X3 = new SecP384R1FieldElement(t4);
            int[] iArr2 = S1;
            SecP384R1Field.j(R, X3.h);
            int[] iArr3 = X3.h;
            SecP384R1Field.m(iArr3, G, iArr3);
            SecP384R1FieldElement Y32 = new SecP384R1FieldElement(G);
            int[] iArr4 = S2;
            int[] iArr5 = U2;
            SecP384R1Field.m(V, X3.h, Y32.h);
            Nat384.a(Y32.h, R, tt2);
            SecP384R1Field.b(tt1, tt2, tt1);
            SecP384R1Field.h(tt1, Y32.h);
            SecP384R1FieldElement Z3 = new SecP384R1FieldElement(H);
            if (!Z1IsOne) {
                int[] iArr6 = Z3.h;
                Y3 = Y32;
                SecP384R1Field.f(iArr6, Z1.h, iArr6);
            } else {
                Y3 = Y32;
            }
            if (!Z2IsOne) {
                int[] iArr7 = Z3.h;
                SecP384R1Field.f(iArr7, Z2.h, iArr7);
            }
            ECFieldElement[] zs = {Z3};
            int[] iArr8 = HSquared;
            SecP384R1FieldElement secP384R1FieldElement5 = Z3;
            int[] iArr9 = t4;
            int[] iArr10 = tt2;
            return new SecP384R1Point(curve, X3, Y3, zs, this.f);
        } else if (Nat.v(12, R)) {
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
        SecP384R1FieldElement Y1 = (SecP384R1FieldElement) this.d;
        if (Y1.i()) {
            return curve.u();
        }
        SecP384R1FieldElement X1 = (SecP384R1FieldElement) this.c;
        SecP384R1FieldElement Z1 = (SecP384R1FieldElement) this.e[0];
        int[] t1 = Nat.i(12);
        int[] t2 = Nat.i(12);
        int[] Y1Squared = Nat.i(12);
        SecP384R1Field.j(Y1.h, Y1Squared);
        int[] T = Nat.i(12);
        SecP384R1Field.j(Y1Squared, T);
        boolean Z1IsOne = Z1.h();
        int[] Z1Squared2 = Z1.h;
        if (!Z1IsOne) {
            int[] Z1Squared3 = t2;
            SecP384R1Field.j(Z1.h, Z1Squared3);
            Z1Squared = Z1Squared3;
        } else {
            Z1Squared = Z1Squared2;
        }
        SecP384R1Field.m(X1.h, Z1Squared, t1);
        int[] M = t2;
        SecP384R1Field.a(X1.h, Z1Squared, M);
        SecP384R1Field.f(M, t1, M);
        int c = Nat.c(12, M, M, M);
        SecP384R1Field.i(c, M);
        int[] S = Y1Squared;
        SecP384R1Field.f(Y1Squared, X1.h, S);
        int i = c;
        int c2 = Nat.F(12, S, 2, 0);
        SecP384R1Field.i(c2, S);
        int i2 = c2;
        int c3 = Nat.G(12, T, 3, 0, t1);
        SecP384R1Field.i(c3, t1);
        SecP384R1FieldElement X3 = new SecP384R1FieldElement(T);
        SecP384R1Field.j(M, X3.h);
        int[] iArr = X3.h;
        SecP384R1Field.m(iArr, S, iArr);
        int[] iArr2 = X3.h;
        SecP384R1Field.m(iArr2, S, iArr2);
        SecP384R1FieldElement Y3 = new SecP384R1FieldElement(S);
        int i3 = c3;
        SecP384R1FieldElement X32 = X3;
        SecP384R1Field.m(S, X3.h, Y3.h);
        int[] iArr3 = Y3.h;
        SecP384R1Field.f(iArr3, M, iArr3);
        int[] iArr4 = Y3.h;
        SecP384R1Field.m(iArr4, t1, iArr4);
        SecP384R1FieldElement Z3 = new SecP384R1FieldElement(M);
        SecP384R1FieldElement Y32 = Y3;
        SecP384R1Field.n(Y1.h, Z3.h);
        if (!Z1IsOne) {
            int[] iArr5 = Z3.h;
            SecP384R1Field.f(iArr5, Z1.h, iArr5);
        }
        SecP384R1FieldElement secP384R1FieldElement = Z3;
        int[] iArr6 = S;
        int[] iArr7 = M;
        int[] iArr8 = Z1Squared;
        return new SecP384R1Point(curve, X32, Y32, new ECFieldElement[]{Z3}, this.f);
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
        return new SecP384R1Point(this.b, this.c, this.d.m(), this.e, this.f);
    }
}
