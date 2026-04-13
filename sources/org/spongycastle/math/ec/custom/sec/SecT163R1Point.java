package org.spongycastle.math.ec.custom.sec;

import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;

public class SecT163R1Point extends ECPoint.AbstractF2m {
    public SecT163R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y) {
        this(curve, x, y, false);
    }

    public SecT163R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression) {
        super(curve, x, y);
        if ((x == null) == (y != null ? false : true)) {
            this.f = withCompression;
            return;
        }
        throw new IllegalArgumentException("Exactly one of the field elements is null");
    }

    SecT163R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        super(curve, x, y, zs);
        this.f = withCompression;
    }

    /* access modifiers changed from: protected */
    public ECPoint d() {
        return new SecT163R1Point((ECCurve) null, f(), g());
    }

    public ECFieldElement r() {
        ECFieldElement X = this.c;
        ECFieldElement L = this.d;
        if (t() || X.i()) {
            return L;
        }
        ECFieldElement Y = L.a(X).j(X);
        ECFieldElement Z = this.e[0];
        if (!Z.h()) {
            return Y.d(Z);
        }
        return Y;
    }

    /* access modifiers changed from: protected */
    public boolean h() {
        ECFieldElement X = n();
        if (!X.i() && o().s() != X.s()) {
            return true;
        }
        return false;
    }

    public ECPoint a(ECPoint b) {
        ECFieldElement U2;
        ECFieldElement S2;
        ECFieldElement U1;
        ECFieldElement Z3;
        ECFieldElement L3;
        ECFieldElement X3;
        ECPoint eCPoint = b;
        if (t()) {
            return eCPoint;
        }
        if (b.t()) {
            return this;
        }
        ECCurve curve = i();
        ECFieldElement X1 = this.c;
        ECFieldElement X2 = b.n();
        if (!X1.i()) {
            ECFieldElement L1 = this.d;
            ECFieldElement Z1 = this.e[0];
            ECFieldElement L2 = b.o();
            ECFieldElement Z2 = eCPoint.s(0);
            boolean Z1IsOne = Z1.h();
            ECFieldElement U22 = X2;
            ECFieldElement S22 = L2;
            if (!Z1IsOne) {
                U2 = U22.j(Z1);
                S2 = S22.j(Z1);
            } else {
                U2 = U22;
                S2 = S22;
            }
            boolean Z2IsOne = Z2.h();
            ECFieldElement U12 = X1;
            ECFieldElement S1 = L1;
            if (!Z2IsOne) {
                ECFieldElement U13 = U12.j(Z2);
                S1 = S1.j(Z2);
                U1 = U13;
            } else {
                U1 = U12;
            }
            ECFieldElement A = S1.a(S2);
            ECFieldElement B = U1.a(U2);
            if (!B.i()) {
                if (X2.i()) {
                    ECPoint p = y();
                    ECFieldElement X12 = p.q();
                    ECFieldElement Y1 = p.r();
                    ECFieldElement Y2 = L2;
                    ECFieldElement Y22 = S1;
                    ECFieldElement L = Y1.a(Y2).d(X12);
                    ECFieldElement eCFieldElement = Y2;
                    ECFieldElement eCFieldElement2 = S2;
                    X3 = L.o().a(L).a(X12).a(curve.n());
                    if (X3.i()) {
                        ECFieldElement eCFieldElement3 = X2;
                        ECFieldElement eCFieldElement4 = L2;
                        return new SecT163R1Point(curve, X3, curve.o().n(), this.f);
                    }
                    ECFieldElement eCFieldElement5 = L2;
                    L3 = L.j(X12.a(X3)).a(X3).a(Y1).d(X3).a(X3);
                    Z3 = curve.m(ECConstants.b);
                    ECFieldElement eCFieldElement6 = B;
                    ECFieldElement B2 = X12;
                } else {
                    ECFieldElement eCFieldElement7 = S2;
                    ECFieldElement eCFieldElement8 = X2;
                    ECFieldElement eCFieldElement9 = L2;
                    ECFieldElement B3 = B.o();
                    ECFieldElement AU1 = A.j(U1);
                    ECFieldElement AU2 = A.j(U2);
                    ECFieldElement X32 = AU1.j(AU2);
                    if (X32.i()) {
                        ECFieldElement eCFieldElement10 = X1;
                        return new SecT163R1Point(curve, X32, curve.o().n(), this.f);
                    }
                    ECFieldElement ABZ2 = A.j(B3);
                    if (!Z2IsOne) {
                        ABZ2 = ABZ2.j(Z2);
                    }
                    L3 = AU2.a(B3).p(ABZ2, L1.a(Z1));
                    Z3 = ABZ2;
                    if (!Z1IsOne) {
                        Z3 = Z3.j(Z1);
                        ECFieldElement eCFieldElement11 = B3;
                        X3 = X32;
                    } else {
                        ECFieldElement eCFieldElement12 = B3;
                        X3 = X32;
                    }
                }
                ECFieldElement eCFieldElement13 = A;
                ECFieldElement eCFieldElement14 = U1;
                return new SecT163R1Point(curve, X3, L3, new ECFieldElement[]{Z3}, this.f);
            } else if (A.i()) {
                return H();
            } else {
                return curve.u();
            }
        } else if (X2.i()) {
            return curve.u();
        } else {
            return eCPoint.a(this);
        }
    }

    public ECPoint H() {
        if (t()) {
            return this;
        }
        ECCurve curve = i();
        ECFieldElement X1 = this.c;
        if (X1.i()) {
            return curve.u();
        }
        ECFieldElement L1 = this.d;
        ECFieldElement Z1 = this.e[0];
        boolean Z1IsOne = Z1.h();
        ECFieldElement L1Z1 = Z1IsOne ? L1 : L1.j(Z1);
        ECFieldElement Z1Sq = Z1IsOne ? Z1 : Z1.o();
        ECFieldElement a = curve.n();
        ECFieldElement T = L1.o().a(L1Z1).a(Z1IsOne ? a : a.j(Z1Sq));
        if (T.i()) {
            return new SecT163R1Point(curve, T, curve.o().n(), this.f);
        }
        ECFieldElement X3 = T.o();
        ECFieldElement Z3 = Z1IsOne ? T : T.j(Z1Sq);
        ECFieldElement X1Z1 = Z1IsOne ? X1 : X1.j(Z1);
        ECFieldElement eCFieldElement = X1Z1;
        ECFieldElement eCFieldElement2 = Z3;
        ECFieldElement eCFieldElement3 = X3;
        ECFieldElement eCFieldElement4 = T;
        return new SecT163R1Point(curve, X3, X1Z1.p(T, L1Z1).a(X3).a(Z3), new ECFieldElement[]{Z3}, this.f);
    }

    public ECPoint I(ECPoint b) {
        ECPoint eCPoint = b;
        if (t()) {
            return eCPoint;
        }
        if (b.t()) {
            return H();
        }
        ECCurve curve = i();
        ECFieldElement X1 = this.c;
        if (X1.i()) {
            return eCPoint;
        }
        ECFieldElement X2 = b.n();
        ECFieldElement Z2 = eCPoint.s(0);
        if (X2.i()) {
            ECFieldElement eCFieldElement = X2;
            ECFieldElement eCFieldElement2 = Z2;
        } else if (!Z2.h()) {
            ECFieldElement eCFieldElement3 = X1;
            ECFieldElement eCFieldElement4 = X2;
            ECFieldElement eCFieldElement5 = Z2;
        } else {
            ECFieldElement L1 = this.d;
            ECFieldElement Z1 = this.e[0];
            ECFieldElement L2 = b.o();
            ECFieldElement X1Sq = X1.o();
            ECFieldElement L1Sq = L1.o();
            ECFieldElement Z1Sq = Z1.o();
            ECFieldElement L1Z1 = L1.j(Z1);
            ECFieldElement T = curve.n().j(Z1Sq).a(L1Sq).a(L1Z1);
            ECFieldElement L2plus1 = L2.b();
            ECFieldElement A = curve.n().a(L2plus1).j(Z1Sq).a(L1Sq).l(T, X1Sq, Z1Sq);
            ECFieldElement eCFieldElement6 = X1;
            ECFieldElement X12 = X2.j(Z1Sq);
            ECFieldElement eCFieldElement7 = X2;
            ECFieldElement B = X12.a(T).o();
            if (B.i()) {
                if (A.i()) {
                    return b.H();
                }
                return curve.u();
            } else if (A.i()) {
                ECFieldElement eCFieldElement8 = L1Z1;
                ECFieldElement eCFieldElement9 = L1Sq;
                ECFieldElement eCFieldElement10 = Z2;
                return new SecT163R1Point(curve, A, curve.o().n(), this.f);
            } else {
                ECFieldElement L1Sq2 = L1Sq;
                ECFieldElement eCFieldElement11 = Z2;
                ECFieldElement X3 = A.o().j(X12);
                ECFieldElement Z3 = A.j(B).j(Z1Sq);
                ECFieldElement L3 = A.a(B).o().l(T, L2plus1, Z3);
                ECFieldElement eCFieldElement12 = A;
                ECFieldElement eCFieldElement13 = L2plus1;
                ECFieldElement eCFieldElement14 = T;
                ECFieldElement[] eCFieldElementArr = {Z3};
                ECFieldElement eCFieldElement15 = Z1Sq;
                ECFieldElement eCFieldElement16 = L1Sq2;
                ECFieldElement L1Sq3 = Z3;
                ECFieldElement Z32 = eCFieldElement16;
                return new SecT163R1Point(curve, X3, L3, eCFieldElementArr, this.f);
            }
        }
        return H().a(eCPoint);
    }

    public ECPoint x() {
        if (t()) {
            return this;
        }
        ECFieldElement X = this.c;
        if (X.i()) {
            return this;
        }
        ECFieldElement L = this.d;
        ECFieldElement Z = this.e[0];
        ECCurve eCCurve = this.b;
        return new SecT163R1Point(eCCurve, X, L.a(Z), new ECFieldElement[]{Z}, this.f);
    }
}
