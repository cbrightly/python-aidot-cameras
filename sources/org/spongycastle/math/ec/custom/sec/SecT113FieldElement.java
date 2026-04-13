package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Nat128;
import org.spongycastle.util.Arrays;

public class SecT113FieldElement extends ECFieldElement {
    protected long[] g;

    public SecT113FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.bitLength() > 113) {
            throw new IllegalArgumentException("x value invalid for SecT113FieldElement");
        }
        this.g = SecT113Field.d(x);
    }

    public SecT113FieldElement() {
        this.g = Nat128.d();
    }

    protected SecT113FieldElement(long[] x) {
        this.g = x;
    }

    public boolean h() {
        return Nat128.n(this.g);
    }

    public boolean i() {
        return Nat128.p(this.g);
    }

    public boolean s() {
        return (this.g[0] & 1) != 0;
    }

    public BigInteger t() {
        return Nat128.w(this.g);
    }

    public int f() {
        return 113;
    }

    public ECFieldElement a(ECFieldElement b) {
        long[] z = Nat128.d();
        SecT113Field.a(this.g, ((SecT113FieldElement) b).g, z);
        return new SecT113FieldElement(z);
    }

    public ECFieldElement b() {
        long[] z = Nat128.d();
        SecT113Field.c(this.g, z);
        return new SecT113FieldElement(z);
    }

    public ECFieldElement r(ECFieldElement b) {
        return a(b);
    }

    public ECFieldElement j(ECFieldElement b) {
        long[] z = Nat128.d();
        SecT113Field.i(this.g, ((SecT113FieldElement) b).g, z);
        return new SecT113FieldElement(z);
    }

    public ECFieldElement k(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
        return l(b, x, y);
    }

    public ECFieldElement l(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
        long[] ax = this.g;
        long[] bx = ((SecT113FieldElement) b).g;
        long[] xx = ((SecT113FieldElement) x).g;
        long[] yx = ((SecT113FieldElement) y).g;
        long[] tt = Nat128.f();
        SecT113Field.j(ax, bx, tt);
        SecT113Field.j(xx, yx, tt);
        long[] z = Nat128.d();
        SecT113Field.k(tt, z);
        return new SecT113FieldElement(z);
    }

    public ECFieldElement d(ECFieldElement b) {
        return j(b.g());
    }

    public ECFieldElement m() {
        return this;
    }

    public ECFieldElement o() {
        long[] z = Nat128.d();
        SecT113Field.n(this.g, z);
        return new SecT113FieldElement(z);
    }

    public ECFieldElement p(ECFieldElement x, ECFieldElement y) {
        long[] ax = this.g;
        long[] xx = ((SecT113FieldElement) x).g;
        long[] yx = ((SecT113FieldElement) y).g;
        long[] tt = Nat128.f();
        SecT113Field.o(ax, tt);
        SecT113Field.j(xx, yx, tt);
        long[] z = Nat128.d();
        SecT113Field.k(tt, z);
        return new SecT113FieldElement(z);
    }

    public ECFieldElement q(int pow) {
        if (pow < 1) {
            return this;
        }
        long[] z = Nat128.d();
        SecT113Field.p(this.g, pow, z);
        return new SecT113FieldElement(z);
    }

    public ECFieldElement g() {
        long[] z = Nat128.d();
        SecT113Field.h(this.g, z);
        return new SecT113FieldElement(z);
    }

    public ECFieldElement n() {
        long[] z = Nat128.d();
        SecT113Field.m(this.g, z);
        return new SecT113FieldElement(z);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SecT113FieldElement)) {
            return false;
        }
        return Nat128.h(this.g, ((SecT113FieldElement) other).g);
    }

    public int hashCode() {
        return Arrays.N(this.g, 0, 2) ^ 113009;
    }
}
