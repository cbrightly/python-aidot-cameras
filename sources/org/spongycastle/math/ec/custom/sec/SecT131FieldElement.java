package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat192;
import org.spongycastle.util.Arrays;

public class SecT131FieldElement extends ECFieldElement {
    protected long[] g;

    public SecT131FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.bitLength() > 131) {
            throw new IllegalArgumentException("x value invalid for SecT131FieldElement");
        }
        this.g = SecT131Field.d(x);
    }

    public SecT131FieldElement() {
        this.g = Nat192.f();
    }

    protected SecT131FieldElement(long[] x) {
        this.g = x;
    }

    public boolean h() {
        return Nat192.r(this.g);
    }

    public boolean i() {
        return Nat192.t(this.g);
    }

    public boolean s() {
        return (this.g[0] & 1) != 0;
    }

    public BigInteger t() {
        return Nat192.G(this.g);
    }

    public int f() {
        return 131;
    }

    public ECFieldElement a(ECFieldElement b) {
        long[] z = Nat192.f();
        SecT131Field.a(this.g, ((SecT131FieldElement) b).g, z);
        return new SecT131FieldElement(z);
    }

    public ECFieldElement b() {
        long[] z = Nat192.f();
        SecT131Field.c(this.g, z);
        return new SecT131FieldElement(z);
    }

    public ECFieldElement r(ECFieldElement b) {
        return a(b);
    }

    public ECFieldElement j(ECFieldElement b) {
        long[] z = Nat192.f();
        SecT131Field.j(this.g, ((SecT131FieldElement) b).g, z);
        return new SecT131FieldElement(z);
    }

    public ECFieldElement k(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
        return l(b, x, y);
    }

    public ECFieldElement l(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
        long[] ax = this.g;
        long[] bx = ((SecT131FieldElement) b).g;
        long[] xx = ((SecT131FieldElement) x).g;
        long[] yx = ((SecT131FieldElement) y).g;
        long[] tt = Nat.j(5);
        SecT131Field.k(ax, bx, tt);
        SecT131Field.k(xx, yx, tt);
        long[] z = Nat192.f();
        SecT131Field.l(tt, z);
        return new SecT131FieldElement(z);
    }

    public ECFieldElement d(ECFieldElement b) {
        return j(b.g());
    }

    public ECFieldElement m() {
        return this;
    }

    public ECFieldElement o() {
        long[] z = Nat192.f();
        SecT131Field.o(this.g, z);
        return new SecT131FieldElement(z);
    }

    public ECFieldElement p(ECFieldElement x, ECFieldElement y) {
        long[] ax = this.g;
        long[] xx = ((SecT131FieldElement) x).g;
        long[] yx = ((SecT131FieldElement) y).g;
        long[] tt = Nat.j(5);
        SecT131Field.p(ax, tt);
        SecT131Field.k(xx, yx, tt);
        long[] z = Nat192.f();
        SecT131Field.l(tt, z);
        return new SecT131FieldElement(z);
    }

    public ECFieldElement q(int pow) {
        if (pow < 1) {
            return this;
        }
        long[] z = Nat192.f();
        SecT131Field.q(this.g, pow, z);
        return new SecT131FieldElement(z);
    }

    public ECFieldElement g() {
        long[] z = Nat192.f();
        SecT131Field.i(this.g, z);
        return new SecT131FieldElement(z);
    }

    public ECFieldElement n() {
        long[] z = Nat192.f();
        SecT131Field.n(this.g, z);
        return new SecT131FieldElement(z);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SecT131FieldElement)) {
            return false;
        }
        return Nat192.k(this.g, ((SecT131FieldElement) other).g);
    }

    public int hashCode() {
        return Arrays.N(this.g, 0, 3) ^ 131832;
    }
}
