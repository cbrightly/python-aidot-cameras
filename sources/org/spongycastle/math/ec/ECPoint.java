package org.spongycastle.math.ec;

import io.netty.util.internal.StringUtil;
import java.math.BigInteger;
import java.util.Hashtable;
import org.spongycastle.math.ec.ECFieldElement;

public abstract class ECPoint {
    protected static ECFieldElement[] a = new ECFieldElement[0];
    protected ECCurve b;
    protected ECFieldElement c;
    protected ECFieldElement d;
    protected ECFieldElement[] e;
    protected boolean f;
    protected Hashtable g;

    /* access modifiers changed from: protected */
    public abstract boolean B();

    public abstract ECPoint E(ECPoint eCPoint);

    public abstract ECPoint H();

    public abstract ECPoint a(ECPoint eCPoint);

    /* access modifiers changed from: protected */
    public abstract ECPoint d();

    /* access modifiers changed from: protected */
    public abstract boolean h();

    public abstract ECPoint x();

    protected static ECFieldElement[] m(ECCurve curve) {
        int coord = curve == null ? 0 : curve.q();
        switch (coord) {
            case 0:
            case 5:
                return a;
            default:
                ECFieldElement one = curve.m(ECConstants.b);
                switch (coord) {
                    case 1:
                    case 2:
                    case 6:
                        return new ECFieldElement[]{one};
                    case 3:
                        return new ECFieldElement[]{one, one, one};
                    case 4:
                        return new ECFieldElement[]{one, curve.n()};
                    default:
                        throw new IllegalArgumentException("unknown coordinate system");
                }
        }
    }

    protected ECPoint(ECCurve curve, ECFieldElement x, ECFieldElement y) {
        this(curve, x, y, m(curve));
    }

    protected ECPoint(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs) {
        this.g = null;
        this.b = curve;
        this.c = x;
        this.d = y;
        this.e = zs;
    }

    /* access modifiers changed from: protected */
    public boolean A() {
        BigInteger h = this.b.p();
        return h == null || h.equals(ECConstants.b) || !ECAlgorithms.n(this, h).t();
    }

    public final ECPoint k() {
        return y().d();
    }

    public ECCurve i() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public int j() {
        ECCurve eCCurve = this.b;
        if (eCCurve == null) {
            return 0;
        }
        return eCCurve.q();
    }

    public ECFieldElement f() {
        b();
        return q();
    }

    public ECFieldElement g() {
        b();
        return r();
    }

    public ECFieldElement q() {
        return this.c;
    }

    public ECFieldElement r() {
        return this.d;
    }

    public ECFieldElement s(int index) {
        if (index >= 0) {
            ECFieldElement[] eCFieldElementArr = this.e;
            if (index < eCFieldElementArr.length) {
                return eCFieldElementArr[index];
            }
        }
        return null;
    }

    public final ECFieldElement n() {
        return this.c;
    }

    public final ECFieldElement o() {
        return this.d;
    }

    /* access modifiers changed from: protected */
    public final ECFieldElement[] p() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    public void b() {
        if (!u()) {
            throw new IllegalStateException("point not in normal form");
        }
    }

    public boolean u() {
        int coord = j();
        if (coord == 0 || coord == 5 || t() || this.e[0].h()) {
            return true;
        }
        return false;
    }

    public ECPoint y() {
        if (t()) {
            return this;
        }
        switch (j()) {
            case 0:
            case 5:
                return this;
            default:
                ECFieldElement Z1 = s(0);
                if (Z1.h()) {
                    return this;
                }
                return z(Z1.g());
        }
    }

    /* access modifiers changed from: package-private */
    public ECPoint z(ECFieldElement zInv) {
        switch (j()) {
            case 1:
            case 6:
                return c(zInv, zInv);
            case 2:
            case 3:
            case 4:
                ECFieldElement zInv2 = zInv.o();
                return c(zInv2, zInv2.j(zInv));
            default:
                throw new IllegalStateException("not a projective coordinate system");
        }
    }

    /* access modifiers changed from: protected */
    public ECPoint c(ECFieldElement sx, ECFieldElement sy) {
        return i().h(n().j(sx), o().j(sy), this.f);
    }

    public boolean t() {
        if (!(this.c == null || this.d == null)) {
            ECFieldElement[] eCFieldElementArr = this.e;
            if (eCFieldElementArr.length <= 0 || !eCFieldElementArr[0].i()) {
                return false;
            }
        }
        return true;
    }

    public boolean v() {
        if (!t() && i() != null && (!B() || !A())) {
            return false;
        }
        return true;
    }

    public ECPoint C(ECFieldElement scale) {
        if (t()) {
            return this;
        }
        return i().i(n().j(scale), o(), p(), this.f);
    }

    public ECPoint D(ECFieldElement scale) {
        if (t()) {
            return this;
        }
        return i().i(n(), o().j(scale), p(), this.f);
    }

    public boolean e(ECPoint other) {
        if (other == null) {
            return false;
        }
        ECCurve c1 = i();
        ECCurve c2 = other.i();
        boolean n1 = c1 == null;
        boolean n2 = c2 == null;
        boolean i1 = t();
        boolean i2 = other.t();
        if (!i1 && !i2) {
            ECPoint p1 = this;
            ECPoint p2 = other;
            if (!n1 || !n2) {
                if (n1) {
                    p2 = p2.y();
                } else if (n2) {
                    p1 = p1.y();
                } else if (!c1.l(c2)) {
                    return false;
                } else {
                    ECPoint[] points = {this, c1.y(p2)};
                    c1.A(points);
                    p1 = points[0];
                    p2 = points[1];
                }
            }
            if (!p1.q().equals(p2.q()) || !p1.r().equals(p2.r())) {
                return false;
            }
            return true;
        } else if (!i1 || !i2) {
            return false;
        } else {
            if (n1 || n2 || c1.l(c2)) {
                return true;
            }
            return false;
        }
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ECPoint)) {
            return false;
        }
        return e((ECPoint) other);
    }

    public int hashCode() {
        ECCurve c2 = i();
        int hc = c2 == null ? 0 : ~c2.hashCode();
        if (t()) {
            return hc;
        }
        ECPoint p = y();
        return (hc ^ (p.q().hashCode() * 17)) ^ (p.r().hashCode() * 257);
    }

    public String toString() {
        if (t()) {
            return "INF";
        }
        StringBuffer sb = new StringBuffer();
        sb.append('(');
        sb.append(n());
        sb.append(StringUtil.COMMA);
        sb.append(o());
        for (ECFieldElement append : this.e) {
            sb.append(StringUtil.COMMA);
            sb.append(append);
        }
        sb.append(')');
        return sb.toString();
    }

    public byte[] l(boolean compressed) {
        if (t()) {
            return new byte[1];
        }
        ECPoint normed = y();
        byte[] X = normed.q().e();
        if (compressed) {
            byte[] PO = new byte[(X.length + 1)];
            PO[0] = (byte) (normed.h() ? 3 : 2);
            System.arraycopy(X, 0, PO, 1, X.length);
            return PO;
        }
        byte[] Y = normed.r().e();
        byte[] PO2 = new byte[(X.length + Y.length + 1)];
        PO2[0] = 4;
        System.arraycopy(X, 0, PO2, 1, X.length);
        System.arraycopy(Y, 0, PO2, X.length + 1, Y.length);
        return PO2;
    }

    public ECPoint G(int e2) {
        if (e2 >= 0) {
            ECPoint p = this;
            while (true) {
                e2--;
                if (e2 < 0) {
                    return p;
                }
                p = p.H();
            }
        } else {
            throw new IllegalArgumentException("'e' cannot be negative");
        }
    }

    public ECPoint I(ECPoint b2) {
        return H().a(b2);
    }

    public ECPoint F() {
        return I(this);
    }

    public ECPoint w(BigInteger k) {
        return i().v().a(this, k);
    }

    public static abstract class AbstractFp extends ECPoint {
        protected AbstractFp(ECCurve curve, ECFieldElement x, ECFieldElement y) {
            super(curve, x, y);
        }

        protected AbstractFp(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs) {
            super(curve, x, y, zs);
        }

        /* access modifiers changed from: protected */
        public boolean h() {
            return g().s();
        }

        /* access modifiers changed from: protected */
        public boolean B() {
            ECFieldElement X = this.c;
            ECFieldElement Y = this.d;
            ECFieldElement A = this.b.n();
            ECFieldElement B = this.b.o();
            ECFieldElement lhs = Y.o();
            switch (j()) {
                case 0:
                    break;
                case 1:
                    ECFieldElement Z = this.e[0];
                    if (!Z.h()) {
                        ECFieldElement Z2 = Z.o();
                        ECFieldElement Z3 = Z.j(Z2);
                        lhs = lhs.j(Z);
                        A = A.j(Z2);
                        B = B.j(Z3);
                        break;
                    }
                    break;
                case 2:
                case 3:
                case 4:
                    ECFieldElement Z4 = this.e[0];
                    if (!Z4.h()) {
                        ECFieldElement Z22 = Z4.o();
                        ECFieldElement Z42 = Z22.o();
                        ECFieldElement Z6 = Z22.j(Z42);
                        A = A.j(Z42);
                        B = B.j(Z6);
                        break;
                    }
                    break;
                default:
                    throw new IllegalStateException("unsupported coordinate system");
            }
            return lhs.equals(X.o().a(A).j(X).a(B));
        }

        public ECPoint E(ECPoint b) {
            if (b.t()) {
                return this;
            }
            return a(b.x());
        }
    }

    public static class Fp extends AbstractFp {
        public Fp(ECCurve curve, ECFieldElement x, ECFieldElement y) {
            this(curve, x, y, false);
        }

        public Fp(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression) {
            super(curve, x, y);
            if ((x == null) == (y != null ? false : true)) {
                this.f = withCompression;
                return;
            }
            throw new IllegalArgumentException("Exactly one of the field elements is null");
        }

        Fp(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
            super(curve, x, y, zs);
            this.f = withCompression;
        }

        /* access modifiers changed from: protected */
        public ECPoint d() {
            return new Fp((ECCurve) null, f(), g());
        }

        public ECFieldElement s(int index) {
            if (index == 1 && 4 == j()) {
                return M();
            }
            return ECPoint.super.s(index);
        }

        public ECPoint a(ECPoint b) {
            ECFieldElement Y1;
            ECFieldElement eCFieldElement;
            ECFieldElement X2;
            ECFieldElement eCFieldElement2;
            ECFieldElement Z3Squared;
            ECFieldElement X22;
            ECFieldElement Y12;
            ECFieldElement X1;
            ECFieldElement Z1Cubed;
            ECFieldElement U2;
            ECFieldElement Z1Squared;
            ECFieldElement U1;
            ECFieldElement Z2Squared;
            ECFieldElement Z1Squared2;
            ECFieldElement Z3;
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
            int coord = curve.q();
            ECFieldElement X12 = this.c;
            ECFieldElement Y13 = this.d;
            ECFieldElement X23 = eCPoint.c;
            ECFieldElement Y2 = eCPoint.d;
            switch (coord) {
                case 0:
                    ECFieldElement X13 = X12;
                    ECFieldElement X24 = X23;
                    ECFieldElement dx = X24.r(X13);
                    ECFieldElement dy = Y2.r(Y13);
                    if (!dx.i()) {
                        ECFieldElement gamma = dy.d(dx);
                        ECFieldElement X3 = gamma.o().r(X13).r(X24);
                        return new Fp(curve, X3, gamma.j(X13.r(X3)).r(Y13), this.f);
                    } else if (dy.i()) {
                        return H();
                    } else {
                        return curve.u();
                    }
                case 1:
                    ECFieldElement X14 = X12;
                    ECFieldElement Y14 = Y13;
                    ECFieldElement X25 = X23;
                    ECFieldElement Z1 = this.e[0];
                    ECFieldElement Z2 = eCPoint.e[0];
                    boolean Z1IsOne = Z1.h();
                    boolean Z2IsOne = Z2.h();
                    ECFieldElement u1 = Z1IsOne ? Y2 : Y2.j(Z1);
                    if (Z2IsOne) {
                        eCFieldElement = Y14;
                        Y1 = eCFieldElement;
                    } else {
                        Y1 = Y14;
                        eCFieldElement = Y1.j(Z2);
                    }
                    ECFieldElement u2 = eCFieldElement;
                    ECFieldElement u = u1.r(u2);
                    if (Z1IsOne) {
                        eCFieldElement2 = X25;
                        X2 = eCFieldElement2;
                    } else {
                        X2 = X25;
                        eCFieldElement2 = X2.j(Z1);
                    }
                    ECFieldElement v1 = eCFieldElement2;
                    ECFieldElement X15 = X14;
                    int i = coord;
                    ECFieldElement v2 = Z2IsOne ? X15 : X15.j(Z2);
                    ECFieldElement v = v1.r(v2);
                    if (!v.i()) {
                        ECFieldElement w = Z1IsOne ? Z2 : Z2IsOne ? Z1 : Z1.j(Z2);
                        ECFieldElement eCFieldElement3 = Z1;
                        ECFieldElement vSquared = v.o();
                        ECFieldElement eCFieldElement4 = Z2;
                        ECFieldElement vCubed = vSquared.j(v);
                        boolean z = Z1IsOne;
                        ECFieldElement vSquaredV2 = vSquared.j(v2);
                        ECFieldElement eCFieldElement5 = v2;
                        ECFieldElement eCFieldElement6 = vSquared;
                        ECFieldElement w2 = w;
                        ECFieldElement X16 = X15;
                        ECFieldElement A = u.o().j(w2).r(vCubed).r(P(vSquaredV2));
                        ECFieldElement X32 = v.j(A);
                        ECFieldElement Y3 = vSquaredV2.r(A).k(u, u2, vCubed);
                        ECFieldElement[] eCFieldElementArr = {vCubed.j(w2)};
                        ECFieldElement eCFieldElement7 = A;
                        ECFieldElement eCFieldElement8 = X16;
                        ECFieldElement X17 = v;
                        ECFieldElement v3 = eCFieldElement8;
                        ECFieldElement eCFieldElement9 = v1;
                        ECFieldElement eCFieldElement10 = w2;
                        ECFieldElement w3 = X2;
                        ECFieldElement eCFieldElement11 = u;
                        ECFieldElement eCFieldElement12 = u2;
                        ECFieldElement eCFieldElement13 = vCubed;
                        ECFieldElement vCubed2 = Y1;
                        return new Fp(curve, X32, Y3, eCFieldElementArr, this.f);
                    } else if (u.i()) {
                        return H();
                    } else {
                        return curve.u();
                    }
                case 2:
                case 4:
                    ECFieldElement Z12 = this.e[0];
                    ECFieldElement Z22 = eCPoint.e[0];
                    boolean Z1IsOne2 = Z12.h();
                    if (Z1IsOne2 || !Z12.equals(Z22)) {
                        if (Z1IsOne2) {
                            Z1Squared = Z12;
                            U2 = X23;
                            Z1Cubed = Y2;
                        } else {
                            Z1Squared = Z12.o();
                            U2 = Z1Squared.j(X23);
                            Z1Cubed = Z1Squared.j(Z12).j(Y2);
                        }
                        boolean Z2IsOne2 = Z22.h();
                        if (Z2IsOne2) {
                            Z2Squared = Z22;
                            U1 = X12;
                            ECFieldElement eCFieldElement14 = Z1Squared;
                            Z1Squared2 = Y13;
                        } else {
                            Z2Squared = Z22.o();
                            U1 = Z2Squared.j(X12);
                            ECFieldElement eCFieldElement15 = Z1Squared;
                            Z1Squared2 = Z2Squared.j(Z22).j(Y13);
                        }
                        ECFieldElement eCFieldElement16 = Z2Squared;
                        ECFieldElement H = U1.r(U2);
                        ECFieldElement eCFieldElement17 = U2;
                        ECFieldElement R = Z1Squared2.r(Z1Cubed);
                        if (!H.i()) {
                            ECFieldElement eCFieldElement18 = Z1Cubed;
                            ECFieldElement HSquared = H.o();
                            ECFieldElement eCFieldElement19 = X12;
                            ECFieldElement X18 = HSquared.j(H);
                            ECFieldElement eCFieldElement20 = X23;
                            ECFieldElement V = HSquared.j(U1);
                            ECFieldElement eCFieldElement21 = U1;
                            ECFieldElement eCFieldElement22 = Y13;
                            ECFieldElement X33 = R.o().a(X18).r(P(V));
                            Y12 = V.r(X33).k(R, X18, Z1Squared2);
                            ECFieldElement Z32 = H;
                            if (!Z1IsOne2) {
                                ECFieldElement eCFieldElement23 = Z1Squared2;
                                Z3 = Z32.j(Z12);
                            } else {
                                ECFieldElement S1 = Z1Squared2;
                                Z3 = Z32;
                            }
                            if (!Z2IsOne2) {
                                Z3 = Z3.j(Z22);
                            }
                            if (Z3 == H) {
                                X1 = Z3;
                                X22 = HSquared;
                                Z3Squared = X33;
                            } else {
                                X1 = Z3;
                                X22 = null;
                                Z3Squared = X33;
                            }
                        } else if (R.i()) {
                            return H();
                        } else {
                            return curve.u();
                        }
                    } else {
                        ECFieldElement dx2 = X12.r(X23);
                        ECFieldElement dy2 = Y13.r(Y2);
                        if (!dx2.i()) {
                            ECFieldElement C = dx2.o();
                            ECFieldElement W1 = X12.j(C);
                            ECFieldElement W2 = X23.j(C);
                            ECFieldElement A1 = W1.r(W2).j(Y13);
                            ECFieldElement eCFieldElement24 = C;
                            ECFieldElement X34 = dy2.o().r(W1).r(W2);
                            ECFieldElement eCFieldElement25 = W2;
                            ECFieldElement Y32 = W1.r(X34).j(dy2).r(A1);
                            ECFieldElement eCFieldElement26 = W1;
                            ECFieldElement eCFieldElement27 = X12;
                            ECFieldElement eCFieldElement28 = Y13;
                            ECFieldElement eCFieldElement29 = X23;
                            X22 = null;
                            X1 = dx2.j(Z12);
                            Y12 = Y32;
                            Z3Squared = X34;
                        } else if (dy2.i()) {
                            return H();
                        } else {
                            return curve.u();
                        }
                    }
                    return new Fp(curve, Z3Squared, Y12, coord == 4 ? new ECFieldElement[]{X1, J(X1, X22)} : new ECFieldElement[]{X1}, this.f);
                default:
                    throw new IllegalStateException("unsupported coordinate system");
            }
        }

        public ECPoint H() {
            ECFieldElement S;
            ECFieldElement M;
            ECFieldElement Z3;
            if (t()) {
                return this;
            }
            ECCurve curve = i();
            ECFieldElement Y1 = this.d;
            if (Y1.i()) {
                return curve.u();
            }
            int coord = curve.q();
            ECFieldElement X1 = this.c;
            switch (coord) {
                case 0:
                    ECFieldElement gamma = N(X1.o()).a(i().n()).d(P(Y1));
                    ECFieldElement X3 = gamma.o().r(P(X1));
                    return new Fp(curve, X3, gamma.j(X1.r(X3)).r(Y1), this.f);
                case 1:
                    ECFieldElement Z1 = this.e[0];
                    boolean Z1IsOne = Z1.h();
                    ECFieldElement w = curve.n();
                    if (!w.i() && !Z1IsOne) {
                        w = w.j(Z1.o());
                    }
                    ECFieldElement w2 = w.a(N(X1.o()));
                    ECFieldElement s = Z1IsOne ? Y1 : Y1.j(Z1);
                    ECFieldElement t = Z1IsOne ? Y1.o() : s.j(Y1);
                    ECFieldElement B = X1.j(t);
                    ECFieldElement _4B = L(B);
                    ECFieldElement h = w2.o().r(P(_4B));
                    ECFieldElement _2s = P(s);
                    ECFieldElement X32 = h.j(_2s);
                    ECFieldElement _2t = P(t);
                    ECFieldElement eCFieldElement = h;
                    ECFieldElement Y3 = _4B.r(h).j(w2).r(P(_2t.o()));
                    ECFieldElement _4sSquared = Z1IsOne ? P(_2t) : _2s.o();
                    ECFieldElement eCFieldElement2 = B;
                    ECFieldElement eCFieldElement3 = _2t;
                    ECFieldElement eCFieldElement4 = _2s;
                    ECFieldElement eCFieldElement5 = _4sSquared;
                    ECFieldElement eCFieldElement6 = _4B;
                    return new Fp(curve, X32, Y3, new ECFieldElement[]{P(_4sSquared).j(s)}, this.f);
                case 2:
                    ECFieldElement Z12 = this.e[0];
                    boolean Z1IsOne2 = Z12.h();
                    ECFieldElement Y1Squared = Y1.o();
                    ECFieldElement T = Y1Squared.o();
                    ECFieldElement a4 = curve.n();
                    ECFieldElement a4Neg = a4.m();
                    if (a4Neg.t().equals(BigInteger.valueOf(3))) {
                        ECFieldElement Z1Squared = Z1IsOne2 ? Z12 : Z12.o();
                        M = N(X1.a(Z1Squared).j(X1.r(Z1Squared)));
                        S = L(Y1Squared.j(X1));
                    } else {
                        ECFieldElement X1Squared = X1.o();
                        M = N(X1Squared);
                        if (Z1IsOne2) {
                            M = M.a(a4);
                            ECFieldElement eCFieldElement7 = X1Squared;
                        } else if (!a4.i()) {
                            ECFieldElement Z1Pow4 = Z12.o().o();
                            ECFieldElement eCFieldElement8 = X1Squared;
                            if (a4Neg.c() < a4.c()) {
                                M = M.r(Z1Pow4.j(a4Neg));
                            } else {
                                M = M.a(Z1Pow4.j(a4));
                            }
                        }
                        S = L(X1.j(Y1Squared));
                    }
                    ECFieldElement X33 = M.o().r(P(S));
                    ECFieldElement Y32 = S.r(X33).j(M).r(K(T));
                    ECFieldElement Z32 = P(Y1);
                    if (!Z1IsOne2) {
                        Z3 = Z32.j(Z12);
                    } else {
                        Z3 = Z32;
                    }
                    ECFieldElement eCFieldElement9 = X33;
                    ECFieldElement eCFieldElement10 = M;
                    ECFieldElement eCFieldElement11 = S;
                    ECFieldElement eCFieldElement12 = a4Neg;
                    return new Fp(curve, X33, Y32, new ECFieldElement[]{Z3}, this.f);
                case 4:
                    return O(true);
                default:
                    throw new IllegalStateException("unsupported coordinate system");
            }
        }

        public ECPoint I(ECPoint b) {
            ECPoint eCPoint = b;
            if (this == eCPoint) {
                return F();
            }
            if (t()) {
                return eCPoint;
            }
            if (b.t()) {
                return H();
            }
            ECFieldElement Y1 = this.d;
            if (Y1.i()) {
                return eCPoint;
            }
            ECCurve curve = i();
            int coord = curve.q();
            switch (coord) {
                case 0:
                    ECFieldElement X1 = this.c;
                    ECFieldElement X2 = eCPoint.c;
                    ECFieldElement Y2 = eCPoint.d;
                    ECFieldElement dx = X2.r(X1);
                    ECFieldElement dy = Y2.r(Y1);
                    if (!dx.i()) {
                        ECFieldElement X = dx.o();
                        ECFieldElement d = X.j(P(X1).a(X2)).r(dy.o());
                        if (d.i()) {
                            return curve.u();
                        }
                        ECFieldElement I = d.j(dx).g();
                        ECFieldElement L1 = d.j(I).j(dy);
                        int i = coord;
                        ECFieldElement L2 = P(Y1).j(X).j(dx).j(I).r(L1);
                        ECFieldElement eCFieldElement = Y2;
                        ECFieldElement eCFieldElement2 = dx;
                        ECFieldElement X4 = L2.r(L1).j(L1.a(L2)).a(X2);
                        ECFieldElement eCFieldElement3 = Y1;
                        ECFieldElement eCFieldElement4 = L2;
                        return new Fp(curve, X4, X1.r(X4).j(L2).r(Y1), this.f);
                    } else if (dy.i()) {
                        return F();
                    } else {
                        return this;
                    }
                case 4:
                    return O(false).a(eCPoint);
                default:
                    ECFieldElement eCFieldElement5 = Y1;
                    return H().a(eCPoint);
            }
        }

        public ECPoint F() {
            if (t()) {
                return this;
            }
            ECFieldElement Y1 = this.d;
            if (Y1.i()) {
                return this;
            }
            ECCurve curve = i();
            int coord = curve.q();
            switch (coord) {
                case 0:
                    ECFieldElement X1 = this.c;
                    ECFieldElement _2Y1 = P(Y1);
                    ECFieldElement X = _2Y1.o();
                    ECFieldElement Z = N(X1.o()).a(i().n());
                    ECFieldElement d = N(X1).j(X).r(Z.o());
                    if (d.i()) {
                        return i().u();
                    }
                    ECFieldElement I = d.j(_2Y1).g();
                    ECFieldElement L1 = d.j(I).j(Z);
                    ECFieldElement L2 = X.o().j(I).r(L1);
                    ECFieldElement X4 = L2.r(L1).j(L1.a(L2)).a(X1);
                    ECFieldElement eCFieldElement = Y1;
                    int i = coord;
                    return new Fp(curve, X4, X1.r(X4).j(L2).r(Y1), this.f);
                case 4:
                    return O(false).a(this);
                default:
                    ECFieldElement eCFieldElement2 = Y1;
                    return H().a(this);
            }
        }

        public ECPoint G(int e) {
            int i = e;
            if (i < 0) {
                throw new IllegalArgumentException("'e' cannot be negative");
            } else if (i == 0 || t()) {
                return this;
            } else {
                if (i == 1) {
                    return H();
                }
                ECCurve curve = i();
                ECFieldElement Y1 = this.d;
                if (Y1.i()) {
                    return curve.u();
                }
                int coord = curve.q();
                ECFieldElement W1 = curve.n();
                ECFieldElement X1 = this.c;
                ECFieldElement[] eCFieldElementArr = this.e;
                ECFieldElement Z1 = eCFieldElementArr.length < 1 ? curve.m(ECConstants.b) : eCFieldElementArr[0];
                if (!Z1.h()) {
                    switch (coord) {
                        case 0:
                            break;
                        case 1:
                            ECFieldElement Z1Sq = Z1.o();
                            X1 = X1.j(Z1);
                            Y1 = Y1.j(Z1Sq);
                            W1 = J(Z1, Z1Sq);
                            break;
                        case 2:
                            W1 = J(Z1, (ECFieldElement) null);
                            break;
                        case 4:
                            W1 = M();
                            break;
                        default:
                            throw new IllegalStateException("unsupported coordinate system");
                    }
                }
                int i2 = 0;
                ECFieldElement Y12 = Y1;
                ECFieldElement W12 = W1;
                ECFieldElement X12 = X1;
                ECFieldElement Z12 = Z1;
                while (i2 < i) {
                    if (Y12.i()) {
                        return curve.u();
                    }
                    ECFieldElement X1Squared = X12.o();
                    ECFieldElement M = N(X1Squared);
                    ECFieldElement _2Y1 = P(Y12);
                    ECFieldElement _2Y1Squared = _2Y1.j(Y12);
                    ECFieldElement S = P(X12.j(_2Y1Squared));
                    ECFieldElement _8T = P(_2Y1Squared.o());
                    if (!W12.i()) {
                        M = M.a(W12);
                        ECFieldElement eCFieldElement = X1Squared;
                        W12 = P(_8T.j(W12));
                    }
                    ECFieldElement eCFieldElement2 = _2Y1Squared;
                    X12 = M.o().r(P(S));
                    Y12 = M.j(S.r(X12)).r(_8T);
                    Z12 = Z12.h() ? _2Y1 : _2Y1.j(Z12);
                    i2++;
                    i = e;
                }
                switch (coord) {
                    case 0:
                        ECFieldElement X13 = Z12.g();
                        ECFieldElement zInv2 = X13.o();
                        return new Fp(curve, X12.j(zInv2), Y12.j(zInv2.j(X13)), this.f);
                    case 1:
                        return new Fp(curve, X12.j(Z12), Y12, new ECFieldElement[]{Z12.j(Z12.o())}, this.f);
                    case 2:
                        return new Fp(curve, X12, Y12, new ECFieldElement[]{Z12}, this.f);
                    case 4:
                        return new Fp(curve, X12, Y12, new ECFieldElement[]{Z12, W12}, this.f);
                    default:
                        throw new IllegalStateException("unsupported coordinate system");
                }
            }
        }

        /* access modifiers changed from: protected */
        public ECFieldElement P(ECFieldElement x) {
            return x.a(x);
        }

        /* access modifiers changed from: protected */
        public ECFieldElement N(ECFieldElement x) {
            return P(x).a(x);
        }

        /* access modifiers changed from: protected */
        public ECFieldElement L(ECFieldElement x) {
            return P(P(x));
        }

        /* access modifiers changed from: protected */
        public ECFieldElement K(ECFieldElement x) {
            return L(P(x));
        }

        public ECPoint x() {
            if (t()) {
                return this;
            }
            ECCurve curve = i();
            if (curve.q() == 0) {
                return new Fp(curve, this.c, this.d.m(), this.f);
            }
            return new Fp(curve, this.c, this.d.m(), this.e, this.f);
        }

        /* access modifiers changed from: protected */
        public ECFieldElement J(ECFieldElement Z, ECFieldElement ZSquared) {
            ECFieldElement a4 = i().n();
            if (a4.i() || Z.h()) {
                return a4;
            }
            if (ZSquared == null) {
                ZSquared = Z.o();
            }
            ECFieldElement W = ZSquared.o();
            ECFieldElement a4Neg = a4.m();
            if (a4Neg.c() < a4.c()) {
                return W.j(a4Neg).m();
            }
            return W.j(a4);
        }

        /* access modifiers changed from: protected */
        public ECFieldElement M() {
            ECFieldElement[] eCFieldElementArr = this.e;
            ECFieldElement W = eCFieldElementArr[1];
            if (W != null) {
                return W;
            }
            ECFieldElement J = J(eCFieldElementArr[0], (ECFieldElement) null);
            ECFieldElement W2 = J;
            eCFieldElementArr[1] = J;
            return W2;
        }

        /* access modifiers changed from: protected */
        public Fp O(boolean calculateW) {
            ECFieldElement X1 = this.c;
            ECFieldElement Y1 = this.d;
            ECFieldElement Z1 = this.e[0];
            ECFieldElement W1 = M();
            ECFieldElement M = N(X1.o()).a(W1);
            ECFieldElement _2Y1 = P(Y1);
            ECFieldElement _2Y1Squared = _2Y1.j(Y1);
            ECFieldElement S = P(X1.j(_2Y1Squared));
            ECFieldElement X3 = M.o().r(P(S));
            ECFieldElement _8T = P(_2Y1Squared.o());
            ECFieldElement eCFieldElement = _8T;
            return new Fp(i(), X3, M.j(S.r(X3)).r(_8T), new ECFieldElement[]{Z1.h() ? _2Y1 : _2Y1.j(Z1), calculateW ? P(_8T.j(W1)) : null}, this.f);
        }
    }

    public static abstract class AbstractF2m extends ECPoint {
        protected AbstractF2m(ECCurve curve, ECFieldElement x, ECFieldElement y) {
            super(curve, x, y);
        }

        protected AbstractF2m(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs) {
            super(curve, x, y, zs);
        }

        /* access modifiers changed from: protected */
        public boolean B() {
            ECFieldElement Z4;
            ECFieldElement Z2;
            ECCurve curve = i();
            ECFieldElement X = this.c;
            ECFieldElement A = curve.n();
            ECFieldElement B = curve.o();
            int coord = curve.q();
            if (coord == 6) {
                ECFieldElement Z = this.e[0];
                boolean ZIsOne = Z.h();
                if (X.i()) {
                    ECFieldElement lhs = this.d.o();
                    ECFieldElement rhs = B;
                    if (!ZIsOne) {
                        rhs = rhs.j(Z.o());
                    }
                    return lhs.equals(rhs);
                }
                ECFieldElement Y = this.d;
                ECFieldElement X2 = X.o();
                if (ZIsOne) {
                    Z2 = Y.o().a(Y).a(A);
                    Z4 = X2.o().a(B);
                } else {
                    ECFieldElement lhs2 = Z.o();
                    ECFieldElement Z42 = lhs2.o();
                    Z2 = Y.a(Z).l(Y, A, lhs2);
                    Z4 = X2.p(B, Z42);
                }
                return Z2.j(X2).equals(Z4);
            }
            ECFieldElement Y2 = this.d;
            ECFieldElement lhs3 = Y2.a(X).j(Y2);
            switch (coord) {
                case 0:
                    break;
                case 1:
                    ECFieldElement Z3 = this.e[0];
                    if (!Z3.h()) {
                        ECFieldElement Z32 = Z3.j(Z3.o());
                        lhs3 = lhs3.j(Z3);
                        A = A.j(Z3);
                        B = B.j(Z32);
                        break;
                    }
                    break;
                default:
                    throw new IllegalStateException("unsupported coordinate system");
            }
            return lhs3.equals(X.a(A).j(X.o()).a(B));
        }

        public ECPoint C(ECFieldElement scale) {
            if (t()) {
                return this;
            }
            switch (j()) {
                case 5:
                    ECFieldElement X = n();
                    ECFieldElement L = o();
                    return i().i(X, L.a(X).d(scale).a(X.j(scale)), p(), this.f);
                case 6:
                    ECFieldElement X2 = n();
                    ECFieldElement L2 = o();
                    ECFieldElement Z = p()[0];
                    ECFieldElement X22 = X2.j(scale.o());
                    ECFieldElement L22 = L2.a(X2).a(X22);
                    ECFieldElement Z2 = Z.j(scale);
                    return i().i(X22, L22, new ECFieldElement[]{Z2}, this.f);
                default:
                    return ECPoint.super.C(scale);
            }
        }

        public ECPoint D(ECFieldElement scale) {
            if (t()) {
                return this;
            }
            switch (j()) {
                case 5:
                case 6:
                    ECFieldElement X = n();
                    return i().i(X, o().a(X).j(scale).a(X), p(), this.f);
                default:
                    return ECPoint.super.D(scale);
            }
        }

        public ECPoint E(ECPoint b) {
            if (b.t()) {
                return this;
            }
            return a(b.x());
        }

        public AbstractF2m J(int pow) {
            if (t()) {
                return this;
            }
            ECCurve curve = i();
            int coord = curve.q();
            ECFieldElement X1 = this.c;
            switch (coord) {
                case 0:
                case 5:
                    return (AbstractF2m) curve.h(X1.q(pow), this.d.q(pow), this.f);
                case 1:
                case 6:
                    ECFieldElement Y1 = this.d;
                    ECFieldElement Z1 = this.e[0];
                    return (AbstractF2m) curve.i(X1.q(pow), Y1.q(pow), new ECFieldElement[]{Z1.q(pow)}, this.f);
                default:
                    throw new IllegalStateException("unsupported coordinate system");
            }
        }
    }

    public static class F2m extends AbstractF2m {
        public F2m(ECCurve curve, ECFieldElement x, ECFieldElement y) {
            this(curve, x, y, false);
        }

        public F2m(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression) {
            super(curve, x, y);
            if ((x == null) == (y != null ? false : true)) {
                if (x != null) {
                    ECFieldElement.F2m.u(this.c, this.d);
                    if (curve != null) {
                        ECFieldElement.F2m.u(this.c, this.b.n());
                    }
                }
                this.f = withCompression;
                return;
            }
            throw new IllegalArgumentException("Exactly one of the field elements is null");
        }

        F2m(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
            super(curve, x, y, zs);
            this.f = withCompression;
        }

        /* access modifiers changed from: protected */
        public ECPoint d() {
            return new F2m((ECCurve) null, f(), g());
        }

        public ECFieldElement r() {
            int coord = j();
            switch (coord) {
                case 5:
                case 6:
                    ECFieldElement X = this.c;
                    ECFieldElement L = this.d;
                    if (t() || X.i()) {
                        return L;
                    }
                    ECFieldElement Y = L.a(X).j(X);
                    if (6 != coord) {
                        return Y;
                    }
                    ECFieldElement Z = this.e[0];
                    if (!Z.h()) {
                        return Y.d(Z);
                    }
                    return Y;
                default:
                    return this.d;
            }
        }

        /* access modifiers changed from: protected */
        public boolean h() {
            ECFieldElement X = n();
            if (X.i()) {
                return false;
            }
            ECFieldElement Y = o();
            switch (j()) {
                case 5:
                case 6:
                    if (Y.s() != X.s()) {
                        return true;
                    }
                    return false;
                default:
                    return Y.d(X).s();
            }
        }

        public ECPoint a(ECPoint b) {
            ECFieldElement U2;
            ECFieldElement S2;
            ECFieldElement S1;
            ECFieldElement U1;
            ECFieldElement S12;
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
            int coord = curve.q();
            ECFieldElement X1 = this.c;
            ECFieldElement X2 = eCPoint.c;
            switch (coord) {
                case 0:
                    ECFieldElement Y1 = this.d;
                    ECFieldElement Y2 = b.d;
                    ECFieldElement dx = X1.a(X2);
                    ECFieldElement dy = Y1.a(Y2);
                    if (!dx.i()) {
                        ECFieldElement L = dy.d(dx);
                        ECFieldElement X32 = L.o().a(L).a(dx).a(curve.n());
                        return new F2m(curve, X32, L.j(X1.a(X32)).a(X32).a(Y1), this.f);
                    } else if (dy.i()) {
                        return H();
                    } else {
                        return curve.u();
                    }
                case 1:
                    ECFieldElement X22 = X2;
                    ECFieldElement Y12 = this.d;
                    ECFieldElement Z1 = this.e[0];
                    ECFieldElement Y22 = eCPoint.d;
                    ECFieldElement Z2 = eCPoint.e[0];
                    boolean Z2IsOne = Z2.h();
                    ECFieldElement U12 = Z1.j(Y22);
                    ECFieldElement U22 = Z2IsOne ? Y12 : Y12.j(Z2);
                    ECFieldElement U = U12.a(U22);
                    ECFieldElement X23 = X22;
                    ECFieldElement V1 = Z1.j(X23);
                    ECFieldElement V2 = Z2IsOne ? X1 : X1.j(Z2);
                    ECFieldElement V = V1.a(V2);
                    if (!V.i()) {
                        ECFieldElement eCFieldElement = Y22;
                        ECFieldElement Y23 = V.o();
                        ECFieldElement eCFieldElement2 = U12;
                        ECFieldElement U13 = Y23.j(V);
                        ECFieldElement W = Z2IsOne ? Z1 : Z1.j(Z2);
                        ECFieldElement eCFieldElement3 = Z1;
                        ECFieldElement uv = U.a(V);
                        ECFieldElement eCFieldElement4 = V2;
                        ECFieldElement W2 = W;
                        ECFieldElement A = uv.l(U, Y23, curve.n()).j(W2).a(U13);
                        ECFieldElement X33 = V.j(A);
                        ECFieldElement VSqZ2 = Z2IsOne ? Y23 : Y23.j(Z2);
                        ECFieldElement eCFieldElement5 = V1;
                        ECFieldElement V12 = U.l(X1, V, Y12);
                        ECFieldElement eCFieldElement6 = Y12;
                        ECFieldElement VSqZ22 = VSqZ2;
                        ECFieldElement Y3 = V12.l(VSqZ22, uv, A);
                        ECFieldElement eCFieldElement7 = W2;
                        ECFieldElement eCFieldElement8 = V;
                        ECFieldElement eCFieldElement9 = A;
                        ECFieldElement eCFieldElement10 = VSqZ22;
                        ECFieldElement VSqZ23 = X23;
                        ECFieldElement eCFieldElement11 = U;
                        ECFieldElement eCFieldElement12 = U22;
                        return new F2m(curve, X33, Y3, new ECFieldElement[]{U13.j(W2)}, this.f);
                    } else if (U.i()) {
                        return H();
                    } else {
                        return curve.u();
                    }
                case 6:
                    if (!X1.i()) {
                        ECFieldElement L1 = this.d;
                        ECFieldElement Z12 = this.e[0];
                        ECFieldElement L2 = eCPoint.d;
                        ECFieldElement Z22 = eCPoint.e[0];
                        boolean Z1IsOne = Z12.h();
                        ECFieldElement U23 = X2;
                        ECFieldElement S22 = L2;
                        if (!Z1IsOne) {
                            U2 = U23.j(Z12);
                            S2 = S22.j(Z12);
                        } else {
                            U2 = U23;
                            S2 = S22;
                        }
                        boolean Z2IsOne2 = Z22.h();
                        ECFieldElement U14 = X1;
                        ECFieldElement S13 = L1;
                        if (!Z2IsOne2) {
                            ECFieldElement U15 = U14.j(Z22);
                            ECFieldElement j = S13.j(Z22);
                            S1 = U15;
                            U1 = j;
                        } else {
                            ECFieldElement eCFieldElement13 = S13;
                            S1 = U14;
                            U1 = eCFieldElement13;
                        }
                        ECFieldElement A2 = U1.a(S2);
                        ECFieldElement B = S1.a(U2);
                        if (!B.i()) {
                            if (X2.i()) {
                                ECPoint p = y();
                                ECFieldElement X12 = p.q();
                                ECFieldElement Y13 = p.r();
                                S12 = U1;
                                ECFieldElement S14 = L2;
                                ECFieldElement Y24 = S2;
                                ECFieldElement L4 = Y13.a(S14).d(X12);
                                ECFieldElement eCFieldElement14 = S14;
                                int i = coord;
                                ECFieldElement X34 = L4.o().a(L4).a(X12).a(curve.n());
                                if (X34.i()) {
                                    ECFieldElement eCFieldElement15 = L2;
                                    ECFieldElement eCFieldElement16 = X2;
                                    return new F2m(curve, X34, curve.o().n(), this.f);
                                }
                                ECFieldElement eCFieldElement17 = L2;
                                L3 = L4.j(X12.a(X34)).a(X34).a(Y13).d(X34).a(X34);
                                Z3 = curve.m(ECConstants.b);
                                X3 = X34;
                            } else {
                                S12 = U1;
                                ECFieldElement eCFieldElement18 = S2;
                                int i2 = coord;
                                ECFieldElement eCFieldElement19 = X2;
                                ECFieldElement eCFieldElement20 = L2;
                                ECFieldElement B2 = B.o();
                                ECFieldElement AU1 = A2.j(S1);
                                ECFieldElement AU2 = A2.j(U2);
                                X3 = AU1.j(AU2);
                                if (X3.i()) {
                                    ECFieldElement eCFieldElement21 = AU1;
                                    return new F2m(curve, X3, curve.o().n(), this.f);
                                }
                                ECFieldElement ABZ2 = A2.j(B2);
                                if (!Z2IsOne2) {
                                    ABZ2 = ABZ2.j(Z22);
                                }
                                L3 = AU2.a(B2).p(ABZ2, L1.a(Z12));
                                Z3 = ABZ2;
                                if (!Z1IsOne) {
                                    Z3 = Z3.j(Z12);
                                    ECFieldElement AU12 = B2;
                                } else {
                                    ECFieldElement AU13 = B2;
                                }
                            }
                            ECFieldElement eCFieldElement22 = A2;
                            ECFieldElement eCFieldElement23 = S12;
                            ECFieldElement eCFieldElement24 = S1;
                            ECFieldElement eCFieldElement25 = U2;
                            return new F2m(curve, X3, L3, new ECFieldElement[]{Z3}, this.f);
                        } else if (A2.i()) {
                            return H();
                        } else {
                            return curve.u();
                        }
                    } else if (X2.i()) {
                        return curve.u();
                    } else {
                        return eCPoint.a(this);
                    }
                default:
                    ECPoint eCPoint2 = eCPoint;
                    throw new IllegalStateException("unsupported coordinate system");
            }
        }

        public ECPoint H() {
            ECFieldElement t1;
            ECFieldElement t2;
            if (t()) {
                return this;
            }
            ECCurve curve = i();
            ECFieldElement X1 = this.c;
            if (X1.i()) {
                return curve.u();
            }
            int coord = curve.q();
            switch (coord) {
                case 0:
                    ECCurve curve2 = curve;
                    ECFieldElement L1 = this.d.d(X1).a(X1);
                    ECFieldElement X3 = L1.o().a(L1).a(curve2.n());
                    return new F2m(curve2, X3, X1.p(X3, L1.b()), this.f);
                case 1:
                    ECCurve curve3 = curve;
                    int i = coord;
                    ECFieldElement Y1 = this.d;
                    ECFieldElement Z1 = this.e[0];
                    boolean Z1IsOne = Z1.h();
                    ECFieldElement X1Z1 = Z1IsOne ? X1 : X1.j(Z1);
                    ECFieldElement Y1Z1 = Z1IsOne ? Y1 : Y1.j(Z1);
                    ECFieldElement X1Sq = X1.o();
                    ECFieldElement S = X1Sq.a(Y1Z1);
                    ECFieldElement V = X1Z1;
                    ECFieldElement vSquared = V.o();
                    ECFieldElement sv = S.a(V);
                    ECFieldElement h = sv.l(S, vSquared, curve3.n());
                    ECFieldElement eCFieldElement = h;
                    ECFieldElement eCFieldElement2 = sv;
                    ECFieldElement eCFieldElement3 = vSquared;
                    return new F2m(curve3, V.j(h), X1Sq.o().l(V, h, sv), new ECFieldElement[]{V.j(vSquared)}, this.f);
                case 6:
                    ECFieldElement L12 = this.d;
                    ECFieldElement Z12 = this.e[0];
                    boolean Z1IsOne2 = Z12.h();
                    ECFieldElement L1Z1 = Z1IsOne2 ? L12 : L12.j(Z12);
                    ECFieldElement Z1Sq = Z1IsOne2 ? Z12 : Z12.o();
                    ECFieldElement a = curve.n();
                    ECFieldElement aZ1Sq = Z1IsOne2 ? a : a.j(Z1Sq);
                    ECFieldElement T = L12.o().a(L1Z1).a(aZ1Sq);
                    if (T.i()) {
                        return new F2m(curve, T, curve.o().n(), this.f);
                    }
                    ECFieldElement X32 = T.o();
                    ECFieldElement Z3 = Z1IsOne2 ? T : T.j(Z1Sq);
                    ECFieldElement b = curve.o();
                    int i2 = coord;
                    ECCurve curve4 = curve;
                    if (b.c() < (curve.t() >> 1)) {
                        ECFieldElement t12 = L12.a(X1).o();
                        if (b.h()) {
                            t2 = aZ1Sq.a(Z1Sq).o();
                        } else {
                            t2 = aZ1Sq.p(b, Z1Sq.o());
                        }
                        ECFieldElement L3 = t12.a(T).a(Z1Sq).j(t12).a(t2).a(X32);
                        if (a.i()) {
                            L3 = L3.a(Z3);
                        } else if (!a.h()) {
                            ECFieldElement eCFieldElement4 = t12;
                            L3 = L3.a(a.b().j(Z3));
                        }
                        t1 = L3;
                    } else {
                        t1 = (Z1IsOne2 ? X1 : X1.j(Z12)).p(T, L1Z1).a(X32).a(Z3);
                    }
                    ECFieldElement eCFieldElement5 = aZ1Sq;
                    ECFieldElement eCFieldElement6 = b;
                    ECFieldElement eCFieldElement7 = Z3;
                    ECFieldElement eCFieldElement8 = X32;
                    ECFieldElement eCFieldElement9 = T;
                    return new F2m(curve4, X32, t1, new ECFieldElement[]{Z3}, this.f);
                default:
                    ECCurve eCCurve = curve;
                    throw new IllegalStateException("unsupported coordinate system");
            }
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
            int coord = curve.q();
            switch (coord) {
                case 6:
                    ECFieldElement X2 = eCPoint.c;
                    ECFieldElement Z2 = eCPoint.e[0];
                    if (X2.i()) {
                        int i = coord;
                        ECFieldElement eCFieldElement = X2;
                    } else if (!Z2.h()) {
                        ECFieldElement eCFieldElement2 = X1;
                        int i2 = coord;
                        ECFieldElement eCFieldElement3 = X2;
                    } else {
                        ECFieldElement L1 = this.d;
                        ECFieldElement Z1 = this.e[0];
                        ECFieldElement L2 = eCPoint.d;
                        ECFieldElement X1Sq = X1.o();
                        ECFieldElement L1Sq = L1.o();
                        ECFieldElement Z1Sq = Z1.o();
                        ECFieldElement L1Z1 = L1.j(Z1);
                        ECFieldElement T = curve.n().j(Z1Sq).a(L1Sq).a(L1Z1);
                        ECFieldElement L2plus1 = L2.b();
                        ECFieldElement eCFieldElement4 = L1Z1;
                        ECFieldElement A = curve.n().a(L2plus1).j(Z1Sq).a(L1Sq).l(T, X1Sq, Z1Sq);
                        ECFieldElement eCFieldElement5 = X1;
                        ECFieldElement X12 = X2.j(Z1Sq);
                        int i3 = coord;
                        ECFieldElement B = X12.a(T).o();
                        if (B.i()) {
                            if (A.i()) {
                                return b.H();
                            }
                            return curve.u();
                        } else if (A.i()) {
                            ECFieldElement eCFieldElement6 = L1Sq;
                            ECFieldElement eCFieldElement7 = X1Sq;
                            ECFieldElement eCFieldElement8 = X2;
                            return new F2m(curve, A, curve.o().n(), this.f);
                        } else {
                            ECFieldElement X1Sq2 = X1Sq;
                            ECFieldElement eCFieldElement9 = X2;
                            ECFieldElement X3 = A.o().j(X12);
                            ECFieldElement Z3 = A.j(B).j(Z1Sq);
                            ECFieldElement L3 = A.a(B).o().l(T, L2plus1, Z3);
                            ECFieldElement eCFieldElement10 = T;
                            ECFieldElement eCFieldElement11 = L2plus1;
                            ECFieldElement eCFieldElement12 = A;
                            ECFieldElement eCFieldElement13 = Z1Sq;
                            ECFieldElement eCFieldElement14 = X1Sq2;
                            ECFieldElement X1Sq3 = Z3;
                            ECFieldElement Z32 = eCFieldElement14;
                            return new F2m(curve, X3, L3, new ECFieldElement[]{Z3}, this.f);
                        }
                    }
                    return H().a(eCPoint);
                default:
                    return H().a(eCPoint);
            }
        }

        public ECPoint x() {
            if (t()) {
                return this;
            }
            ECFieldElement X = this.c;
            if (X.i()) {
                return this;
            }
            switch (j()) {
                case 0:
                    return new F2m(this.b, X, this.d.a(X), this.f);
                case 1:
                    ECFieldElement Y = this.d;
                    ECFieldElement Z = this.e[0];
                    return new F2m(this.b, X, Y.a(X), new ECFieldElement[]{Z}, this.f);
                case 5:
                    return new F2m(this.b, X, this.d.b(), this.f);
                case 6:
                    ECFieldElement L = this.d;
                    ECFieldElement Z2 = this.e[0];
                    return new F2m(this.b, X, L.a(Z2), new ECFieldElement[]{Z2}, this.f);
                default:
                    throw new IllegalStateException("unsupported coordinate system");
            }
        }
    }
}
