package org.spongycastle.math.ec;

import java.math.BigInteger;
import java.util.Random;
import org.spongycastle.math.raw.Mod;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.BigIntegers;

public abstract class ECFieldElement implements ECConstants {
    public abstract ECFieldElement a(ECFieldElement eCFieldElement);

    public abstract ECFieldElement b();

    public abstract ECFieldElement d(ECFieldElement eCFieldElement);

    public abstract int f();

    public abstract ECFieldElement g();

    public abstract ECFieldElement j(ECFieldElement eCFieldElement);

    public abstract ECFieldElement m();

    public abstract ECFieldElement n();

    public abstract ECFieldElement o();

    public abstract ECFieldElement r(ECFieldElement eCFieldElement);

    public abstract BigInteger t();

    public int c() {
        return t().bitLength();
    }

    public boolean h() {
        return c() == 1;
    }

    public boolean i() {
        return t().signum() == 0;
    }

    public ECFieldElement k(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
        return j(b).r(x.j(y));
    }

    public ECFieldElement l(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
        return j(b).a(x.j(y));
    }

    public ECFieldElement p(ECFieldElement x, ECFieldElement y) {
        return o().a(x.j(y));
    }

    public ECFieldElement q(int pow) {
        ECFieldElement r = this;
        for (int i = 0; i < pow; i++) {
            r = r.o();
        }
        return r;
    }

    public boolean s() {
        return t().testBit(0);
    }

    public String toString() {
        return t().toString(16);
    }

    public byte[] e() {
        return BigIntegers.a((f() + 7) / 8, t());
    }

    public static class Fp extends ECFieldElement {
        BigInteger g;
        BigInteger h;
        BigInteger i;

        static BigInteger u(BigInteger p) {
            int bitLength = p.bitLength();
            if (bitLength < 96 || p.shiftRight(bitLength - 64).longValue() != -1) {
                return null;
            }
            return ECConstants.b.shiftLeft(bitLength).subtract(p);
        }

        public Fp(BigInteger q, BigInteger x) {
            this(q, u(q), x);
        }

        Fp(BigInteger q, BigInteger r, BigInteger x) {
            if (x == null || x.signum() < 0 || x.compareTo(q) >= 0) {
                throw new IllegalArgumentException("x value invalid in Fp field element");
            }
            this.g = q;
            this.h = r;
            this.i = x;
        }

        public BigInteger t() {
            return this.i;
        }

        public int f() {
            return this.g.bitLength();
        }

        public ECFieldElement a(ECFieldElement b) {
            return new Fp(this.g, this.h, x(this.i, b.t()));
        }

        public ECFieldElement b() {
            BigInteger x2 = this.i.add(ECConstants.b);
            if (x2.compareTo(this.g) == 0) {
                x2 = ECConstants.a;
            }
            return new Fp(this.g, this.h, x2);
        }

        public ECFieldElement r(ECFieldElement b) {
            return new Fp(this.g, this.h, D(this.i, b.t()));
        }

        public ECFieldElement j(ECFieldElement b) {
            return new Fp(this.g, this.h, B(this.i, b.t()));
        }

        public ECFieldElement k(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
            BigInteger ax = this.i;
            BigInteger bx = b.t();
            BigInteger xx = x.t();
            BigInteger yx = y.t();
            return new Fp(this.g, this.h, C(ax.multiply(bx).subtract(xx.multiply(yx))));
        }

        public ECFieldElement l(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
            BigInteger ax = this.i;
            BigInteger bx = b.t();
            BigInteger xx = x.t();
            BigInteger yx = y.t();
            return new Fp(this.g, this.h, C(ax.multiply(bx).add(xx.multiply(yx))));
        }

        public ECFieldElement d(ECFieldElement b) {
            return new Fp(this.g, this.h, B(this.i, A(b.t())));
        }

        public ECFieldElement m() {
            if (this.i.signum() == 0) {
                return this;
            }
            BigInteger bigInteger = this.g;
            return new Fp(bigInteger, this.h, bigInteger.subtract(this.i));
        }

        public ECFieldElement o() {
            BigInteger bigInteger = this.g;
            BigInteger bigInteger2 = this.h;
            BigInteger bigInteger3 = this.i;
            return new Fp(bigInteger, bigInteger2, B(bigInteger3, bigInteger3));
        }

        public ECFieldElement p(ECFieldElement x, ECFieldElement y) {
            BigInteger ax = this.i;
            BigInteger xx = x.t();
            BigInteger yx = y.t();
            return new Fp(this.g, this.h, C(ax.multiply(ax).add(xx.multiply(yx))));
        }

        public ECFieldElement g() {
            return new Fp(this.g, this.h, A(this.i));
        }

        public ECFieldElement n() {
            if (i() || h()) {
                return this;
            }
            if (!this.g.testBit(0)) {
                throw new RuntimeException("not done yet");
            } else if (this.g.testBit(1)) {
                BigInteger e = this.g.shiftRight(2).add(ECConstants.b);
                BigInteger bigInteger = this.g;
                return v(new Fp(bigInteger, this.h, this.i.modPow(e, bigInteger)));
            } else if (this.g.testBit(2)) {
                BigInteger t1 = this.i.modPow(this.g.shiftRight(3), this.g);
                BigInteger t2 = B(t1, this.i);
                if (B(t2, t1).equals(ECConstants.b)) {
                    return v(new Fp(this.g, this.h, t2));
                }
                return v(new Fp(this.g, this.h, B(t2, ECConstants.c.modPow(this.g.shiftRight(2), this.g))));
            } else {
                BigInteger legendreExponent = this.g.shiftRight(1);
                BigInteger modPow = this.i.modPow(legendreExponent, this.g);
                BigInteger bigInteger2 = ECConstants.b;
                if (!modPow.equals(bigInteger2)) {
                    return null;
                }
                BigInteger X = this.i;
                BigInteger fourX = y(y(X));
                BigInteger k = legendreExponent.add(bigInteger2);
                BigInteger qMinusOne = this.g.subtract(bigInteger2);
                Random rand = new Random();
                while (true) {
                    BigInteger P = new BigInteger(this.g.bitLength(), rand);
                    if (P.compareTo(this.g) < 0 && C(P.multiply(P).subtract(fourX)).modPow(legendreExponent, this.g).equals(qMinusOne)) {
                        BigInteger[] result = w(P, X, k);
                        BigInteger U = result[0];
                        BigInteger V = result[1];
                        if (B(V, V).equals(fourX)) {
                            return new Fp(this.g, this.h, z(V));
                        }
                        if (!U.equals(ECConstants.b) && !U.equals(qMinusOne)) {
                            return null;
                        }
                    }
                }
            }
        }

        private ECFieldElement v(ECFieldElement z) {
            if (z.o().equals(this)) {
                return z;
            }
            return null;
        }

        private BigInteger[] w(BigInteger P, BigInteger Q, BigInteger k) {
            int n = k.bitLength();
            int s = k.getLowestSetBit();
            BigInteger Uh = ECConstants.b;
            BigInteger Vl = ECConstants.c;
            BigInteger Vh = P;
            BigInteger Ql = ECConstants.b;
            BigInteger Qh = ECConstants.b;
            for (int j = n - 1; j >= s + 1; j--) {
                Ql = B(Ql, Qh);
                if (k.testBit(j)) {
                    Qh = B(Ql, Q);
                    Uh = B(Uh, Vh);
                    Vl = C(Vh.multiply(Vl).subtract(P.multiply(Ql)));
                    Vh = C(Vh.multiply(Vh).subtract(Qh.shiftLeft(1)));
                } else {
                    Qh = Ql;
                    Uh = C(Uh.multiply(Vl).subtract(Ql));
                    Vh = C(Vh.multiply(Vl).subtract(P.multiply(Ql)));
                    Vl = C(Vl.multiply(Vl).subtract(Ql.shiftLeft(1)));
                }
            }
            BigInteger Ql2 = B(Ql, Qh);
            BigInteger Qh2 = B(Ql2, Q);
            BigInteger Uh2 = C(Uh.multiply(Vl).subtract(Ql2));
            BigInteger Vl2 = C(Vh.multiply(Vl).subtract(P.multiply(Ql2)));
            BigInteger Ql3 = B(Ql2, Qh2);
            for (int j2 = 1; j2 <= s; j2++) {
                Uh2 = B(Uh2, Vl2);
                Vl2 = C(Vl2.multiply(Vl2).subtract(Ql3.shiftLeft(1)));
                Ql3 = B(Ql3, Ql3);
            }
            return new BigInteger[]{Uh2, Vl2};
        }

        /* access modifiers changed from: protected */
        public BigInteger x(BigInteger x1, BigInteger x2) {
            BigInteger x3 = x1.add(x2);
            if (x3.compareTo(this.g) >= 0) {
                return x3.subtract(this.g);
            }
            return x3;
        }

        /* access modifiers changed from: protected */
        public BigInteger y(BigInteger x) {
            BigInteger _2x = x.shiftLeft(1);
            if (_2x.compareTo(this.g) >= 0) {
                return _2x.subtract(this.g);
            }
            return _2x;
        }

        /* access modifiers changed from: protected */
        public BigInteger z(BigInteger x) {
            if (x.testBit(0)) {
                x = this.g.subtract(x);
            }
            return x.shiftRight(1);
        }

        /* access modifiers changed from: protected */
        public BigInteger A(BigInteger x) {
            int bits = f();
            int len = (bits + 31) >> 5;
            int[] p = Nat.n(bits, this.g);
            int[] n = Nat.n(bits, x);
            int[] z = Nat.i(len);
            Mod.d(p, n, z);
            return Nat.O(len, z);
        }

        /* access modifiers changed from: protected */
        public BigInteger B(BigInteger x1, BigInteger x2) {
            return C(x1.multiply(x2));
        }

        /* access modifiers changed from: protected */
        public BigInteger C(BigInteger x) {
            if (this.h == null) {
                return x.mod(this.g);
            }
            boolean negative = x.signum() < 0;
            if (negative) {
                x = x.abs();
            }
            int qLen = this.g.bitLength();
            boolean rIsOne = this.h.equals(ECConstants.b);
            while (x.bitLength() > qLen + 1) {
                BigInteger u = x.shiftRight(qLen);
                BigInteger v = x.subtract(u.shiftLeft(qLen));
                if (!rIsOne) {
                    u = u.multiply(this.h);
                }
                x = u.add(v);
            }
            while (x.compareTo(this.g) >= 0) {
                x = x.subtract(this.g);
            }
            if (!negative || x.signum() == 0) {
                return x;
            }
            return this.g.subtract(x);
        }

        /* access modifiers changed from: protected */
        public BigInteger D(BigInteger x1, BigInteger x2) {
            BigInteger x3 = x1.subtract(x2);
            if (x3.signum() < 0) {
                return x3.add(this.g);
            }
            return x3;
        }

        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof Fp)) {
                return false;
            }
            Fp o = (Fp) other;
            if (!this.g.equals(o.g) || !this.i.equals(o.i)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.g.hashCode() ^ this.i.hashCode();
        }
    }

    public static class F2m extends ECFieldElement {
        private int g;
        private int h;
        private int[] i;
        private LongArray j;

        public F2m(int m, int k1, int k2, int k3, BigInteger x) {
            if (x == null || x.signum() < 0 || x.bitLength() > m) {
                throw new IllegalArgumentException("x value invalid in F2m field element");
            }
            if (k2 == 0 && k3 == 0) {
                this.g = 2;
                this.i = new int[]{k1};
            } else if (k2 >= k3) {
                throw new IllegalArgumentException("k2 must be smaller than k3");
            } else if (k2 > 0) {
                this.g = 3;
                this.i = new int[]{k1, k2, k3};
            } else {
                throw new IllegalArgumentException("k2 must be larger than 0");
            }
            this.h = m;
            this.j = new LongArray(x);
        }

        private F2m(int m, int[] ks, LongArray x) {
            this.h = m;
            this.g = ks.length == 1 ? 2 : 3;
            this.i = ks;
            this.j = x;
        }

        public int c() {
            return this.j.k();
        }

        public boolean h() {
            return this.j.s();
        }

        public boolean i() {
            return this.j.t();
        }

        public boolean s() {
            return this.j.R();
        }

        public BigInteger t() {
            return this.j.S();
        }

        public int f() {
            return this.h;
        }

        public static void u(ECFieldElement a, ECFieldElement b) {
            if (!(a instanceof F2m) || !(b instanceof F2m)) {
                throw new IllegalArgumentException("Field elements are not both instances of ECFieldElement.F2m");
            }
            F2m aF2m = (F2m) a;
            F2m bF2m = (F2m) b;
            if (aF2m.g != bF2m.g) {
                throw new IllegalArgumentException("One of the F2m field elements has incorrect representation");
            } else if (aF2m.h != bF2m.h || !Arrays.d(aF2m.i, bF2m.i)) {
                throw new IllegalArgumentException("Field elements are not elements of the same field F2m");
            }
        }

        public ECFieldElement a(ECFieldElement b) {
            LongArray iarrClone = (LongArray) this.j.clone();
            iarrClone.f(((F2m) b).j, 0);
            return new F2m(this.h, this.i, iarrClone);
        }

        public ECFieldElement b() {
            return new F2m(this.h, this.i, this.j.d());
        }

        public ECFieldElement r(ECFieldElement b) {
            return a(b);
        }

        public ECFieldElement j(ECFieldElement b) {
            int i2 = this.h;
            int[] iArr = this.i;
            return new F2m(i2, iArr, this.j.v(((F2m) b).j, i2, iArr));
        }

        public ECFieldElement k(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
            return l(b, x, y);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: org.spongycastle.math.ec.LongArray} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public org.spongycastle.math.ec.ECFieldElement l(org.spongycastle.math.ec.ECFieldElement r10, org.spongycastle.math.ec.ECFieldElement r11, org.spongycastle.math.ec.ECFieldElement r12) {
            /*
                r9 = this;
                org.spongycastle.math.ec.LongArray r0 = r9.j
                r1 = r10
                org.spongycastle.math.ec.ECFieldElement$F2m r1 = (org.spongycastle.math.ec.ECFieldElement.F2m) r1
                org.spongycastle.math.ec.LongArray r1 = r1.j
                r2 = r11
                org.spongycastle.math.ec.ECFieldElement$F2m r2 = (org.spongycastle.math.ec.ECFieldElement.F2m) r2
                org.spongycastle.math.ec.LongArray r2 = r2.j
                r3 = r12
                org.spongycastle.math.ec.ECFieldElement$F2m r3 = (org.spongycastle.math.ec.ECFieldElement.F2m) r3
                org.spongycastle.math.ec.LongArray r3 = r3.j
                int r4 = r9.h
                int[] r5 = r9.i
                org.spongycastle.math.ec.LongArray r4 = r0.z(r1, r4, r5)
                int r5 = r9.h
                int[] r6 = r9.i
                org.spongycastle.math.ec.LongArray r5 = r2.z(r3, r5, r6)
                if (r4 == r0) goto L_0x0025
                if (r4 != r1) goto L_0x002c
            L_0x0025:
                java.lang.Object r6 = r4.clone()
                r4 = r6
                org.spongycastle.math.ec.LongArray r4 = (org.spongycastle.math.ec.LongArray) r4
            L_0x002c:
                r6 = 0
                r4.f(r5, r6)
                int r6 = r9.h
                int[] r7 = r9.i
                r4.B(r6, r7)
                org.spongycastle.math.ec.ECFieldElement$F2m r6 = new org.spongycastle.math.ec.ECFieldElement$F2m
                int r7 = r9.h
                int[] r8 = r9.i
                r6.<init>(r7, r8, r4)
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.math.ec.ECFieldElement.F2m.l(org.spongycastle.math.ec.ECFieldElement, org.spongycastle.math.ec.ECFieldElement, org.spongycastle.math.ec.ECFieldElement):org.spongycastle.math.ec.ECFieldElement");
        }

        public ECFieldElement d(ECFieldElement b) {
            return j(b.g());
        }

        public ECFieldElement m() {
            return this;
        }

        public ECFieldElement o() {
            int i2 = this.h;
            int[] iArr = this.i;
            return new F2m(i2, iArr, this.j.w(i2, iArr));
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: org.spongycastle.math.ec.LongArray} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public org.spongycastle.math.ec.ECFieldElement p(org.spongycastle.math.ec.ECFieldElement r9, org.spongycastle.math.ec.ECFieldElement r10) {
            /*
                r8 = this;
                org.spongycastle.math.ec.LongArray r0 = r8.j
                r1 = r9
                org.spongycastle.math.ec.ECFieldElement$F2m r1 = (org.spongycastle.math.ec.ECFieldElement.F2m) r1
                org.spongycastle.math.ec.LongArray r1 = r1.j
                r2 = r10
                org.spongycastle.math.ec.ECFieldElement$F2m r2 = (org.spongycastle.math.ec.ECFieldElement.F2m) r2
                org.spongycastle.math.ec.LongArray r2 = r2.j
                int r3 = r8.h
                int[] r4 = r8.i
                org.spongycastle.math.ec.LongArray r3 = r0.N(r3, r4)
                int r4 = r8.h
                int[] r5 = r8.i
                org.spongycastle.math.ec.LongArray r4 = r1.z(r2, r4, r5)
                if (r3 != r0) goto L_0x0025
                java.lang.Object r5 = r3.clone()
                r3 = r5
                org.spongycastle.math.ec.LongArray r3 = (org.spongycastle.math.ec.LongArray) r3
            L_0x0025:
                r5 = 0
                r3.f(r4, r5)
                int r5 = r8.h
                int[] r6 = r8.i
                r3.B(r5, r6)
                org.spongycastle.math.ec.ECFieldElement$F2m r5 = new org.spongycastle.math.ec.ECFieldElement$F2m
                int r6 = r8.h
                int[] r7 = r8.i
                r5.<init>(r6, r7, r3)
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.math.ec.ECFieldElement.F2m.p(org.spongycastle.math.ec.ECFieldElement, org.spongycastle.math.ec.ECFieldElement):org.spongycastle.math.ec.ECFieldElement");
        }

        public ECFieldElement q(int pow) {
            if (pow < 1) {
                return this;
            }
            int i2 = this.h;
            int[] iArr = this.i;
            return new F2m(i2, iArr, this.j.y(pow, i2, iArr));
        }

        public ECFieldElement g() {
            int i2 = this.h;
            int[] iArr = this.i;
            return new F2m(i2, iArr, this.j.u(i2, iArr));
        }

        public ECFieldElement n() {
            return (this.j.t() || this.j.s()) ? this : q(this.h - 1);
        }

        public boolean equals(Object anObject) {
            if (anObject == this) {
                return true;
            }
            if (!(anObject instanceof F2m)) {
                return false;
            }
            F2m b = (F2m) anObject;
            if (this.h != b.h || this.g != b.g || !Arrays.d(this.i, b.i) || !this.j.equals(b.j)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (this.j.hashCode() ^ this.h) ^ Arrays.L(this.i);
        }
    }
}
