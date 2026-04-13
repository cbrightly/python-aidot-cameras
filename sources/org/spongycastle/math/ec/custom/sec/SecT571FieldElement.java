package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Nat576;
import org.spongycastle.util.Arrays;

public class SecT571FieldElement extends ECFieldElement {
    protected long[] g;

    public SecT571FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.bitLength() > 571) {
            throw new IllegalArgumentException("x value invalid for SecT571FieldElement");
        }
        this.g = SecT571Field.g(x);
    }

    public SecT571FieldElement() {
        this.g = Nat576.a();
    }

    protected SecT571FieldElement(long[] x) {
        this.g = x;
    }

    public boolean h() {
        return Nat576.e(this.g);
    }

    public boolean i() {
        return Nat576.f(this.g);
    }

    public boolean s() {
        return (this.g[0] & 1) != 0;
    }

    public BigInteger t() {
        return Nat576.g(this.g);
    }

    public int f() {
        return 571;
    }

    public ECFieldElement a(ECFieldElement b) {
        long[] z = Nat576.a();
        SecT571Field.b(this.g, ((SecT571FieldElement) b).g, z);
        return new SecT571FieldElement(z);
    }

    public ECFieldElement b() {
        long[] z = Nat576.a();
        SecT571Field.f(this.g, z);
        return new SecT571FieldElement(z);
    }

    public ECFieldElement r(ECFieldElement b) {
        return a(b);
    }

    public ECFieldElement j(ECFieldElement b) {
        long[] z = Nat576.a();
        SecT571Field.l(this.g, ((SecT571FieldElement) b).g, z);
        return new SecT571FieldElement(z);
    }

    public ECFieldElement k(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
        return l(b, x, y);
    }

    public ECFieldElement l(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
        long[] ax = this.g;
        long[] bx = ((SecT571FieldElement) b).g;
        long[] xx = ((SecT571FieldElement) x).g;
        long[] yx = ((SecT571FieldElement) y).g;
        long[] tt = Nat576.b();
        SecT571Field.m(ax, bx, tt);
        SecT571Field.m(xx, yx, tt);
        long[] z = Nat576.a();
        SecT571Field.q(tt, z);
        return new SecT571FieldElement(z);
    }

    public ECFieldElement d(ECFieldElement b) {
        return j(b.g());
    }

    public ECFieldElement m() {
        return this;
    }

    public ECFieldElement o() {
        long[] z = Nat576.a();
        SecT571Field.t(this.g, z);
        return new SecT571FieldElement(z);
    }

    public ECFieldElement p(ECFieldElement x, ECFieldElement y) {
        long[] ax = this.g;
        long[] xx = ((SecT571FieldElement) x).g;
        long[] yx = ((SecT571FieldElement) y).g;
        long[] tt = Nat576.b();
        SecT571Field.u(ax, tt);
        SecT571Field.m(xx, yx, tt);
        long[] z = Nat576.a();
        SecT571Field.q(tt, z);
        return new SecT571FieldElement(z);
    }

    public ECFieldElement q(int pow) {
        if (pow < 1) {
            return this;
        }
        long[] z = Nat576.a();
        SecT571Field.v(this.g, pow, z);
        return new SecT571FieldElement(z);
    }

    public ECFieldElement g() {
        long[] z = Nat576.a();
        SecT571Field.k(this.g, z);
        return new SecT571FieldElement(z);
    }

    public ECFieldElement n() {
        long[] z = Nat576.a();
        SecT571Field.s(this.g, z);
        return new SecT571FieldElement(z);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SecT571FieldElement)) {
            return false;
        }
        return Nat576.c(this.g, ((SecT571FieldElement) other).g);
    }

    public int hashCode() {
        return Arrays.N(this.g, 0, 9) ^ 5711052;
    }
}
