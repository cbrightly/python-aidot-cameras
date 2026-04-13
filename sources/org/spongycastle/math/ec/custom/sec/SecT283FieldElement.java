package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat320;
import org.spongycastle.util.Arrays;

public class SecT283FieldElement extends ECFieldElement {
    protected long[] g;

    public SecT283FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.bitLength() > 283) {
            throw new IllegalArgumentException("x value invalid for SecT283FieldElement");
        }
        this.g = SecT283Field.d(x);
    }

    public SecT283FieldElement() {
        this.g = Nat320.a();
    }

    protected SecT283FieldElement(long[] x) {
        this.g = x;
    }

    public boolean h() {
        return Nat320.e(this.g);
    }

    public boolean i() {
        return Nat320.f(this.g);
    }

    public boolean s() {
        return (this.g[0] & 1) != 0;
    }

    public BigInteger t() {
        return Nat320.g(this.g);
    }

    public int f() {
        return 283;
    }

    public ECFieldElement a(ECFieldElement b) {
        long[] z = Nat320.a();
        SecT283Field.a(this.g, ((SecT283FieldElement) b).g, z);
        return new SecT283FieldElement(z);
    }

    public ECFieldElement b() {
        long[] z = Nat320.a();
        SecT283Field.c(this.g, z);
        return new SecT283FieldElement(z);
    }

    public ECFieldElement r(ECFieldElement b) {
        return a(b);
    }

    public ECFieldElement j(ECFieldElement b) {
        long[] z = Nat320.a();
        SecT283Field.k(this.g, ((SecT283FieldElement) b).g, z);
        return new SecT283FieldElement(z);
    }

    public ECFieldElement k(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
        return l(b, x, y);
    }

    public ECFieldElement l(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
        long[] ax = this.g;
        long[] bx = ((SecT283FieldElement) b).g;
        long[] xx = ((SecT283FieldElement) x).g;
        long[] yx = ((SecT283FieldElement) y).g;
        long[] tt = Nat.j(9);
        SecT283Field.l(ax, bx, tt);
        SecT283Field.l(xx, yx, tt);
        long[] z = Nat320.a();
        SecT283Field.m(tt, z);
        return new SecT283FieldElement(z);
    }

    public ECFieldElement d(ECFieldElement b) {
        return j(b.g());
    }

    public ECFieldElement m() {
        return this;
    }

    public ECFieldElement o() {
        long[] z = Nat320.a();
        SecT283Field.p(this.g, z);
        return new SecT283FieldElement(z);
    }

    public ECFieldElement p(ECFieldElement x, ECFieldElement y) {
        long[] ax = this.g;
        long[] xx = ((SecT283FieldElement) x).g;
        long[] yx = ((SecT283FieldElement) y).g;
        long[] tt = Nat.j(9);
        SecT283Field.q(ax, tt);
        SecT283Field.l(xx, yx, tt);
        long[] z = Nat320.a();
        SecT283Field.m(tt, z);
        return new SecT283FieldElement(z);
    }

    public ECFieldElement q(int pow) {
        if (pow < 1) {
            return this;
        }
        long[] z = Nat320.a();
        SecT283Field.r(this.g, pow, z);
        return new SecT283FieldElement(z);
    }

    public ECFieldElement g() {
        long[] z = Nat320.a();
        SecT283Field.j(this.g, z);
        return new SecT283FieldElement(z);
    }

    public ECFieldElement n() {
        long[] z = Nat320.a();
        SecT283Field.o(this.g, z);
        return new SecT283FieldElement(z);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SecT283FieldElement)) {
            return false;
        }
        return Nat320.c(this.g, ((SecT283FieldElement) other).g);
    }

    public int hashCode() {
        return Arrays.N(this.g, 0, 5) ^ 2831275;
    }
}
