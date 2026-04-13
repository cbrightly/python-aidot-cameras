package org.spongycastle.math.ec.custom.djb;

import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.raw.Nat256;

public class Curve25519Point extends ECPoint.AbstractFp {
    public Curve25519Point(ECCurve curve, ECFieldElement x, ECFieldElement y) {
        this(curve, x, y, false);
    }

    public Curve25519Point(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression) {
        super(curve, x, y);
        if ((x == null) == (y != null ? false : true)) {
            this.f = withCompression;
            return;
        }
        throw new IllegalArgumentException("Exactly one of the field elements is null");
    }

    Curve25519Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        super(curve, x, y, zs);
        this.f = withCompression;
    }

    /* access modifiers changed from: protected */
    public ECPoint d() {
        return new Curve25519Point((ECCurve) null, f(), g());
    }

    public ECFieldElement s(int index) {
        if (index == 1) {
            return K();
        }
        return super.s(index);
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
        Curve25519FieldElement X1 = (Curve25519FieldElement) this.c;
        Curve25519FieldElement Y1 = (Curve25519FieldElement) this.d;
        Curve25519FieldElement Z1 = (Curve25519FieldElement) this.e[0];
        Curve25519FieldElement X2 = (Curve25519FieldElement) b.q();
        Curve25519FieldElement Y2 = (Curve25519FieldElement) b.r();
        Curve25519FieldElement Z2 = (Curve25519FieldElement) eCPoint.s(0);
        int[] tt1 = Nat256.h();
        int[] t2 = Nat256.f();
        int[] t3 = Nat256.f();
        int[] t4 = Nat256.f();
        boolean Z1IsOne = Z1.h();
        if (Z1IsOne) {
            int[] U22 = X2.i;
            S2 = Y2.i;
            U2 = U22;
        } else {
            S2 = t3;
            Curve25519Field.j(Z1.i, S2);
            int[] U23 = t2;
            Curve25519Field.e(S2, X2.i, U23);
            Curve25519Field.e(S2, Z1.i, S2);
            Curve25519Field.e(S2, Y2.i, S2);
            U2 = U23;
        }
        boolean Z2IsOne = Z2.h();
        if (Z2IsOne) {
            U1 = X1.i;
            U12 = Y1.i;
        } else {
            int[] S1 = t4;
            Curve25519Field.j(Z2.i, S1);
            int[] U13 = tt1;
            Curve25519Field.e(S1, X1.i, U13);
            Curve25519Field.e(S1, Z2.i, S1);
            Curve25519Field.e(S1, Y1.i, S1);
            U1 = U13;
            U12 = S1;
        }
        int[] S12 = Nat256.f();
        Curve25519Field.n(U1, U2, S12);
        int[] R = t2;
        Curve25519Field.n(U12, S2, R);
        if (!Nat256.t(S12)) {
            Curve25519FieldElement curve25519FieldElement = X1;
            int[] HSquared = Nat256.f();
            Curve25519Field.j(S12, HSquared);
            Curve25519FieldElement curve25519FieldElement2 = Y1;
            int[] G = Nat256.f();
            Curve25519Field.e(HSquared, S12, G);
            Curve25519FieldElement curve25519FieldElement3 = X2;
            int[] V = t3;
            Curve25519Field.e(HSquared, U1, V);
            Curve25519Field.g(G, G);
            Nat256.w(U12, G, tt1);
            int[] HSquared2 = HSquared;
            Curve25519Field.i(Nat256.b(V, V, G), G);
            int[] iArr = U12;
            Curve25519FieldElement X3 = new Curve25519FieldElement(t4);
            int[] iArr2 = U1;
            Curve25519Field.j(R, X3.i);
            int[] iArr3 = X3.i;
            Curve25519Field.n(iArr3, G, iArr3);
            Curve25519FieldElement Y3 = new Curve25519FieldElement(G);
            int[] iArr4 = S2;
            Curve25519FieldElement X32 = X3;
            Curve25519Field.n(V, X3.i, Y3.i);
            Curve25519Field.f(Y3.i, R, tt1);
            Curve25519Field.h(tt1, Y3.i);
            Curve25519FieldElement Z3 = new Curve25519FieldElement(S12);
            if (!Z1IsOne) {
                int[] iArr5 = Z3.i;
                int[] iArr6 = R;
                Curve25519Field.e(iArr5, Z1.i, iArr5);
            }
            if (!Z2IsOne) {
                int[] iArr7 = Z3.i;
                Curve25519Field.e(iArr7, Z2.i, iArr7);
            }
            int[] iArr8 = S12;
            Curve25519FieldElement curve25519FieldElement4 = Z3;
            int[] iArr9 = U2;
            int[] iArr10 = t4;
            return new Curve25519Point(curve, X32, Y3, new ECFieldElement[]{Z3, J(Z3, (!Z1IsOne || !Z2IsOne) ? null : HSquared2)}, this.f);
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
        if (this.d.i()) {
            return curve.u();
        }
        return L(true);
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
        return L(false).a(b);
    }

    public ECPoint F() {
        if (!t() && !this.d.i()) {
            return L(false).a(this);
        }
        return this;
    }

    public ECPoint x() {
        if (t()) {
            return this;
        }
        return new Curve25519Point(i(), this.c, this.d.m(), this.e, this.f);
    }

    /* access modifiers changed from: protected */
    public Curve25519FieldElement J(Curve25519FieldElement Z, int[] ZSquared) {
        Curve25519FieldElement a4 = (Curve25519FieldElement) i().n();
        if (Z.h()) {
            return a4;
        }
        Curve25519FieldElement W = new Curve25519FieldElement();
        if (ZSquared == null) {
            ZSquared = W.i;
            Curve25519Field.j(Z.i, ZSquared);
        }
        Curve25519Field.j(ZSquared, W.i);
        int[] iArr = W.i;
        Curve25519Field.e(iArr, a4.i, iArr);
        return W;
    }

    /* access modifiers changed from: protected */
    public Curve25519FieldElement K() {
        ECFieldElement[] eCFieldElementArr = this.e;
        Curve25519FieldElement W = (Curve25519FieldElement) eCFieldElementArr[1];
        if (W != null) {
            return W;
        }
        Curve25519FieldElement J = J((Curve25519FieldElement) eCFieldElementArr[0], (int[]) null);
        Curve25519FieldElement W2 = J;
        eCFieldElementArr[1] = J;
        return W2;
    }

    /* access modifiers changed from: protected */
    public Curve25519Point L(boolean calculateW) {
        Curve25519FieldElement X1 = (Curve25519FieldElement) this.c;
        Curve25519FieldElement Y1 = (Curve25519FieldElement) this.d;
        Curve25519FieldElement Z1 = (Curve25519FieldElement) this.e[0];
        Curve25519FieldElement W1 = K();
        int[] M = Nat256.f();
        Curve25519Field.j(X1.i, M);
        Curve25519Field.i(Nat256.b(M, M, M) + Nat256.d(W1.i, M), M);
        int[] _2Y1 = Nat256.f();
        Curve25519Field.o(Y1.i, _2Y1);
        int[] _2Y1Squared = Nat256.f();
        Curve25519Field.e(_2Y1, Y1.i, _2Y1Squared);
        int[] S = Nat256.f();
        Curve25519Field.e(_2Y1Squared, X1.i, S);
        Curve25519Field.o(S, S);
        int[] _8T = Nat256.f();
        Curve25519Field.j(_2Y1Squared, _8T);
        Curve25519Field.o(_8T, _8T);
        Curve25519FieldElement X3 = new Curve25519FieldElement(_2Y1Squared);
        Curve25519Field.j(M, X3.i);
        int[] iArr = X3.i;
        Curve25519Field.n(iArr, S, iArr);
        int[] iArr2 = X3.i;
        Curve25519Field.n(iArr2, S, iArr2);
        Curve25519FieldElement Y3 = new Curve25519FieldElement(S);
        Curve25519Field.n(S, X3.i, Y3.i);
        int[] iArr3 = Y3.i;
        Curve25519Field.e(iArr3, M, iArr3);
        int[] iArr4 = Y3.i;
        Curve25519Field.n(iArr4, _8T, iArr4);
        Curve25519FieldElement Z3 = new Curve25519FieldElement(_2Y1);
        if (!Nat256.r(Z1.i)) {
            int[] iArr5 = Z3.i;
            Curve25519Field.e(iArr5, Z1.i, iArr5);
        }
        Curve25519FieldElement W3 = null;
        if (calculateW) {
            W3 = new Curve25519FieldElement(_8T);
            int[] iArr6 = W3.i;
            Curve25519FieldElement curve25519FieldElement = X1;
            Curve25519Field.e(iArr6, W1.i, iArr6);
            int[] iArr7 = W3.i;
            Curve25519Field.o(iArr7, iArr7);
        }
        Curve25519FieldElement curve25519FieldElement2 = Y1;
        Curve25519FieldElement curve25519FieldElement3 = Z3;
        return new Curve25519Point(i(), X3, Y3, new ECFieldElement[]{Z3, W3}, this.f);
    }
}
