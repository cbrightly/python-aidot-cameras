package org.spongycastle.math.ec;

import java.math.BigInteger;
import java.util.Hashtable;
import java.util.Random;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.endo.ECEndomorphism;
import org.spongycastle.math.ec.endo.GLVEndomorphism;
import org.spongycastle.math.field.FiniteField;
import org.spongycastle.math.field.FiniteFields;
import org.spongycastle.util.BigIntegers;
import org.spongycastle.util.Integers;

public abstract class ECCurve {
    protected FiniteField a;
    protected ECFieldElement b;
    protected ECFieldElement c;
    protected BigInteger d;
    protected BigInteger e;
    protected int f = 0;
    protected ECEndomorphism g = null;
    protected ECMultiplier h = null;

    /* access modifiers changed from: protected */
    public abstract ECCurve c();

    /* access modifiers changed from: protected */
    public abstract ECPoint h(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, boolean z);

    /* access modifiers changed from: protected */
    public abstract ECPoint i(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr, boolean z);

    /* access modifiers changed from: protected */
    public abstract ECPoint k(int i, BigInteger bigInteger);

    public abstract ECFieldElement m(BigInteger bigInteger);

    public abstract int t();

    public abstract ECPoint u();

    public abstract boolean z(BigInteger bigInteger);

    public class Config {
        protected int a;
        protected ECEndomorphism b;
        protected ECMultiplier c;

        Config(int coord, ECEndomorphism endomorphism, ECMultiplier multiplier) {
            this.a = coord;
            this.b = endomorphism;
            this.c = multiplier;
        }

        public Config b(int coord) {
            this.a = coord;
            return this;
        }

        public Config c(ECEndomorphism endomorphism) {
            this.b = endomorphism;
            return this;
        }

        public ECCurve a() {
            if (ECCurve.this.D(this.a)) {
                ECCurve c2 = ECCurve.this.c();
                if (c2 != ECCurve.this) {
                    synchronized (c2) {
                        c2.f = this.a;
                        c2.g = this.b;
                        c2.h = this.c;
                    }
                    return c2;
                }
                throw new IllegalStateException("implementation returned current curve");
            }
            throw new IllegalStateException("unsupported coordinate system");
        }
    }

    protected ECCurve(FiniteField field) {
        this.a = field;
    }

    public synchronized Config d() {
        return new Config(this.f, this.g, this.h);
    }

    public ECPoint E(BigInteger x, BigInteger y) {
        ECPoint p = f(x, y);
        if (p.v()) {
            return p;
        }
        throw new IllegalArgumentException("Invalid point coordinates");
    }

    public ECPoint F(BigInteger x, BigInteger y, boolean withCompression) {
        ECPoint p = g(x, y, withCompression);
        if (p.v()) {
            return p;
        }
        throw new IllegalArgumentException("Invalid point coordinates");
    }

    public ECPoint f(BigInteger x, BigInteger y) {
        return g(x, y, false);
    }

    public ECPoint g(BigInteger x, BigInteger y, boolean withCompression) {
        return h(m(x), m(y), withCompression);
    }

    /* access modifiers changed from: protected */
    public ECMultiplier e() {
        ECEndomorphism eCEndomorphism = this.g;
        if (eCEndomorphism instanceof GLVEndomorphism) {
            return new GLVMultiplier(this, (GLVEndomorphism) eCEndomorphism);
        }
        return new WNafL2RMultiplier();
    }

    public boolean D(int coord) {
        return coord == 0;
    }

    public PreCompInfo x(ECPoint point, String name) {
        PreCompInfo preCompInfo;
        a(point);
        synchronized (point) {
            Hashtable table = point.g;
            preCompInfo = table == null ? null : (PreCompInfo) table.get(name);
        }
        return preCompInfo;
    }

    public void C(ECPoint point, String name, PreCompInfo preCompInfo) {
        a(point);
        synchronized (point) {
            Hashtable table = point.g;
            if (table == null) {
                Hashtable hashtable = new Hashtable(4);
                table = hashtable;
                point.g = hashtable;
            }
            table.put(name, preCompInfo);
        }
    }

    public ECPoint y(ECPoint p) {
        if (this == p.i()) {
            return p;
        }
        if (p.t()) {
            return u();
        }
        ECPoint p2 = p.y();
        return F(p2.q().t(), p2.r().t(), p2.f);
    }

    public void A(ECPoint[] points) {
        B(points, 0, points.length, (ECFieldElement) null);
    }

    public void B(ECPoint[] points, int off, int len, ECFieldElement iso) {
        b(points, off, len);
        switch (q()) {
            case 0:
            case 5:
                if (iso != null) {
                    throw new IllegalArgumentException("'iso' not valid for affine coordinates");
                }
                return;
            default:
                ECFieldElement[] zs = new ECFieldElement[len];
                int[] indices = new int[len];
                int count = 0;
                for (int i = 0; i < len; i++) {
                    ECPoint p = points[off + i];
                    if (p != null && (iso != null || !p.u())) {
                        zs[count] = p.s(0);
                        indices[count] = off + i;
                        count++;
                    }
                }
                if (count != 0) {
                    ECAlgorithms.m(zs, 0, count, iso);
                    for (int j = 0; j < count; j++) {
                        int index = indices[j];
                        points[index] = points[index].z(zs[j]);
                    }
                    return;
                }
                return;
        }
    }

    public FiniteField s() {
        return this.a;
    }

    public ECFieldElement n() {
        return this.b;
    }

    public ECFieldElement o() {
        return this.c;
    }

    public BigInteger w() {
        return this.d;
    }

    public BigInteger p() {
        return this.e;
    }

    public int q() {
        return this.f;
    }

    public ECEndomorphism r() {
        return this.g;
    }

    public synchronized ECMultiplier v() {
        if (this.h == null) {
            this.h = e();
        }
        return this.h;
    }

    public ECPoint j(byte[] encoded) {
        ECPoint p;
        int expectedLength = (t() + 7) / 8;
        boolean z = false;
        byte type = encoded[0];
        switch (type) {
            case 0:
                if (encoded.length == 1) {
                    p = u();
                    break;
                } else {
                    throw new IllegalArgumentException("Incorrect length for infinity encoding");
                }
            case 2:
            case 3:
                if (encoded.length == expectedLength + 1) {
                    p = k(type & 1, BigIntegers.d(encoded, 1, expectedLength));
                    if (!p.A()) {
                        throw new IllegalArgumentException("Invalid point");
                    }
                } else {
                    throw new IllegalArgumentException("Incorrect length for compressed encoding");
                }
                break;
            case 4:
                if (encoded.length == (expectedLength * 2) + 1) {
                    p = E(BigIntegers.d(encoded, 1, expectedLength), BigIntegers.d(encoded, expectedLength + 1, expectedLength));
                    break;
                } else {
                    throw new IllegalArgumentException("Incorrect length for uncompressed encoding");
                }
            case 6:
            case 7:
                if (encoded.length == (expectedLength * 2) + 1) {
                    BigInteger X = BigIntegers.d(encoded, 1, expectedLength);
                    BigInteger Y = BigIntegers.d(encoded, expectedLength + 1, expectedLength);
                    boolean testBit = Y.testBit(0);
                    if (type == 7) {
                        z = true;
                    }
                    if (testBit == z) {
                        p = E(X, Y);
                        break;
                    } else {
                        throw new IllegalArgumentException("Inconsistent Y coordinate in hybrid encoding");
                    }
                } else {
                    throw new IllegalArgumentException("Incorrect length for hybrid encoding");
                }
            default:
                throw new IllegalArgumentException("Invalid point encoding 0x" + Integer.toString(type, 16));
        }
        if (type == 0 || !p.t()) {
            return p;
        }
        throw new IllegalArgumentException("Invalid infinity encoding");
    }

    /* access modifiers changed from: protected */
    public void a(ECPoint point) {
        if (point == null || this != point.i()) {
            throw new IllegalArgumentException("'point' must be non-null and on this curve");
        }
    }

    /* access modifiers changed from: protected */
    public void b(ECPoint[] points, int off, int len) {
        if (points == null) {
            throw new IllegalArgumentException("'points' cannot be null");
        } else if (off < 0 || len < 0 || off > points.length - len) {
            throw new IllegalArgumentException("invalid range specified for 'points'");
        } else {
            int i = 0;
            while (i < len) {
                ECPoint point = points[off + i];
                if (point == null || this == point.i()) {
                    i++;
                } else {
                    throw new IllegalArgumentException("'points' entries must be null or on this curve");
                }
            }
        }
    }

    public boolean l(ECCurve other) {
        return this == other || (other != null && s().equals(other.s()) && n().t().equals(other.n().t()) && o().t().equals(other.o().t()));
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof ECCurve) && l((ECCurve) obj));
    }

    public int hashCode() {
        return (s().hashCode() ^ Integers.a(n().t().hashCode(), 8)) ^ Integers.a(o().t().hashCode(), 16);
    }

    public static abstract class AbstractFp extends ECCurve {
        protected AbstractFp(BigInteger q) {
            super(FiniteFields.b(q));
        }

        public boolean z(BigInteger x) {
            return x != null && x.signum() >= 0 && x.compareTo(s().b()) < 0;
        }

        /* access modifiers changed from: protected */
        public ECPoint k(int yTilde, BigInteger X1) {
            ECFieldElement x = m(X1);
            ECFieldElement y = x.o().a(this.b).j(x).a(this.c).n();
            if (y != null) {
                if (y.s() != (yTilde == 1)) {
                    y = y.m();
                }
                return h(x, y, true);
            }
            throw new IllegalArgumentException("Invalid point compression");
        }
    }

    public static class Fp extends AbstractFp {
        BigInteger i;
        BigInteger j;
        ECPoint.Fp k;

        public Fp(BigInteger q, BigInteger a, BigInteger b) {
            this(q, a, b, (BigInteger) null, (BigInteger) null);
        }

        public Fp(BigInteger q, BigInteger a, BigInteger b, BigInteger order, BigInteger cofactor) {
            super(q);
            this.i = q;
            this.j = ECFieldElement.Fp.u(q);
            this.k = new ECPoint.Fp(this, (ECFieldElement) null, (ECFieldElement) null);
            this.b = m(a);
            this.c = m(b);
            this.d = order;
            this.e = cofactor;
            this.f = 4;
        }

        protected Fp(BigInteger q, BigInteger r, ECFieldElement a, ECFieldElement b, BigInteger order, BigInteger cofactor) {
            super(q);
            this.i = q;
            this.j = r;
            this.k = new ECPoint.Fp(this, (ECFieldElement) null, (ECFieldElement) null);
            this.b = a;
            this.c = b;
            this.d = order;
            this.e = cofactor;
            this.f = 4;
        }

        /* access modifiers changed from: protected */
        public ECCurve c() {
            return new Fp(this.i, this.j, this.b, this.c, this.d, this.e);
        }

        public boolean D(int coord) {
            switch (coord) {
                case 0:
                case 1:
                case 2:
                case 4:
                    return true;
                default:
                    return false;
            }
        }

        public int t() {
            return this.i.bitLength();
        }

        public ECFieldElement m(BigInteger x) {
            return new ECFieldElement.Fp(this.i, this.j, x);
        }

        /* access modifiers changed from: protected */
        public ECPoint h(ECFieldElement x, ECFieldElement y, boolean withCompression) {
            return new ECPoint.Fp(this, x, y, withCompression);
        }

        /* access modifiers changed from: protected */
        public ECPoint i(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
            return new ECPoint.Fp(this, x, y, zs, withCompression);
        }

        public ECPoint y(ECPoint p) {
            if (this != p.i() && q() == 2 && !p.t()) {
                switch (p.i().q()) {
                    case 2:
                    case 3:
                    case 4:
                        return new ECPoint.Fp(this, m(p.c.t()), m(p.d.t()), new ECFieldElement[]{m(p.e[0].t())}, p.f);
                }
            }
            return ECCurve.super.y(p);
        }

        public ECPoint u() {
            return this.k;
        }
    }

    public static abstract class AbstractF2m extends ECCurve {
        private BigInteger[] i = null;

        private static FiniteField G(int m, int k1, int k2, int k3) {
            if (k1 == 0) {
                throw new IllegalArgumentException("k1 must be > 0");
            } else if (k2 == 0) {
                if (k3 == 0) {
                    return FiniteFields.a(new int[]{0, k1, m});
                }
                throw new IllegalArgumentException("k3 must be 0 if k2 == 0");
            } else if (k2 <= k1) {
                throw new IllegalArgumentException("k2 must be > k1");
            } else if (k3 > k2) {
                return FiniteFields.a(new int[]{0, k1, k2, k3, m});
            } else {
                throw new IllegalArgumentException("k3 must be > k2");
            }
        }

        protected AbstractF2m(int m, int k1, int k2, int k3) {
            super(G(m, k1, k2, k3));
        }

        public boolean z(BigInteger x) {
            return x != null && x.signum() >= 0 && x.bitLength() <= t();
        }

        public ECPoint g(BigInteger x, BigInteger y, boolean withCompression) {
            ECFieldElement X = m(x);
            ECFieldElement Y = m(y);
            switch (q()) {
                case 5:
                case 6:
                    if (!X.i()) {
                        Y = Y.d(X).a(X);
                        break;
                    } else if (!Y.o().equals(o())) {
                        throw new IllegalArgumentException();
                    }
                    break;
            }
            return h(X, Y, withCompression);
        }

        /* access modifiers changed from: protected */
        public ECPoint k(int yTilde, BigInteger X1) {
            ECFieldElement x = m(X1);
            ECFieldElement y = null;
            if (!x.i()) {
                ECFieldElement z = J(x.o().g().j(o()).a(n()).a(x));
                if (z != null) {
                    if (z.s() != (yTilde == 1)) {
                        z = z.b();
                    }
                    switch (q()) {
                        case 5:
                        case 6:
                            y = z.a(x);
                            break;
                        default:
                            y = z.j(x);
                            break;
                    }
                }
            } else {
                y = o().n();
            }
            if (y != null) {
                return h(x, y, true);
            }
            throw new IllegalArgumentException("Invalid point compression");
        }

        private ECFieldElement J(ECFieldElement beta) {
            ECFieldElement z;
            if (beta.i()) {
                return beta;
            }
            ECFieldElement zeroElement = m(ECConstants.a);
            int m = t();
            Random rand = new Random();
            do {
                ECFieldElement t = m(new BigInteger(m, rand));
                z = zeroElement;
                ECFieldElement w = beta;
                for (int i2 = 1; i2 < m; i2++) {
                    ECFieldElement w2 = w.o();
                    z = z.o().a(w2.j(t));
                    w = w2.a(beta);
                }
                if (w.i() == 0) {
                    return null;
                }
            } while (z.o().a(z).i());
            return z;
        }

        /* access modifiers changed from: package-private */
        public synchronized BigInteger[] H() {
            if (this.i == null) {
                this.i = Tnaf.f(this);
            }
            return this.i;
        }

        public boolean I() {
            return this.d != null && this.e != null && this.c.h() && (this.b.i() || this.b.h());
        }
    }

    public static class F2m extends AbstractF2m {
        private int j;
        private int k;
        private int l;
        private int m;
        private ECPoint.F2m n;

        public F2m(int m2, int k2, BigInteger a, BigInteger b, BigInteger order, BigInteger cofactor) {
            this(m2, k2, 0, 0, a, b, order, cofactor);
        }

        public F2m(int m2, int k1, int k2, int k3, BigInteger a, BigInteger b) {
            this(m2, k1, k2, k3, a, b, (BigInteger) null, (BigInteger) null);
        }

        public F2m(int m2, int k1, int k2, int k3, BigInteger a, BigInteger b, BigInteger order, BigInteger cofactor) {
            super(m2, k1, k2, k3);
            this.j = m2;
            this.k = k1;
            this.l = k2;
            this.m = k3;
            this.d = order;
            this.e = cofactor;
            this.n = new ECPoint.F2m(this, (ECFieldElement) null, (ECFieldElement) null);
            this.b = m(a);
            this.c = m(b);
            this.f = 6;
        }

        protected F2m(int m2, int k1, int k2, int k3, ECFieldElement a, ECFieldElement b, BigInteger order, BigInteger cofactor) {
            super(m2, k1, k2, k3);
            this.j = m2;
            this.k = k1;
            this.l = k2;
            this.m = k3;
            this.d = order;
            this.e = cofactor;
            this.n = new ECPoint.F2m(this, (ECFieldElement) null, (ECFieldElement) null);
            this.b = a;
            this.c = b;
            this.f = 6;
        }

        /* access modifiers changed from: protected */
        public ECCurve c() {
            return new F2m(this.j, this.k, this.l, this.m, this.b, this.c, this.d, this.e);
        }

        public boolean D(int coord) {
            switch (coord) {
                case 0:
                case 1:
                case 6:
                    return true;
                default:
                    return false;
            }
        }

        /* access modifiers changed from: protected */
        public ECMultiplier e() {
            if (I()) {
                return new WTauNafMultiplier();
            }
            return ECCurve.super.e();
        }

        public int t() {
            return this.j;
        }

        public ECFieldElement m(BigInteger x) {
            return new ECFieldElement.F2m(this.j, this.k, this.l, this.m, x);
        }

        /* access modifiers changed from: protected */
        public ECPoint h(ECFieldElement x, ECFieldElement y, boolean withCompression) {
            return new ECPoint.F2m(this, x, y, withCompression);
        }

        /* access modifiers changed from: protected */
        public ECPoint i(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
            return new ECPoint.F2m(this, x, y, zs, withCompression);
        }

        public ECPoint u() {
            return this.n;
        }
    }
}
