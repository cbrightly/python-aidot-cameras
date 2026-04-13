package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import meshsdk.BaseResp;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat448;
import org.spongycastle.util.Arrays;

public class SecT409FieldElement extends ECFieldElement {
    protected long[] g;

    public SecT409FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.bitLength() > 409) {
            throw new IllegalArgumentException("x value invalid for SecT409FieldElement");
        }
        this.g = SecT409Field.d(x);
    }

    public SecT409FieldElement() {
        this.g = Nat448.a();
    }

    protected SecT409FieldElement(long[] x) {
        this.g = x;
    }

    public boolean h() {
        return Nat448.e(this.g);
    }

    public boolean i() {
        return Nat448.f(this.g);
    }

    public boolean s() {
        return (this.g[0] & 1) != 0;
    }

    public BigInteger t() {
        return Nat448.g(this.g);
    }

    public int f() {
        return BaseResp.ERR_DISCONNECT_FAIL;
    }

    public ECFieldElement a(ECFieldElement b) {
        long[] z = Nat448.a();
        SecT409Field.a(this.g, ((SecT409FieldElement) b).g, z);
        return new SecT409FieldElement(z);
    }

    public ECFieldElement b() {
        long[] z = Nat448.a();
        SecT409Field.c(this.g, z);
        return new SecT409FieldElement(z);
    }

    public ECFieldElement r(ECFieldElement b) {
        return a(b);
    }

    public ECFieldElement j(ECFieldElement b) {
        long[] z = Nat448.a();
        SecT409Field.k(this.g, ((SecT409FieldElement) b).g, z);
        return new SecT409FieldElement(z);
    }

    public ECFieldElement k(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
        return l(b, x, y);
    }

    public ECFieldElement l(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
        long[] ax = this.g;
        long[] bx = ((SecT409FieldElement) b).g;
        long[] xx = ((SecT409FieldElement) x).g;
        long[] yx = ((SecT409FieldElement) y).g;
        long[] tt = Nat.j(13);
        SecT409Field.l(ax, bx, tt);
        SecT409Field.l(xx, yx, tt);
        long[] z = Nat448.a();
        SecT409Field.m(tt, z);
        return new SecT409FieldElement(z);
    }

    public ECFieldElement d(ECFieldElement b) {
        return j(b.g());
    }

    public ECFieldElement m() {
        return this;
    }

    public ECFieldElement o() {
        long[] z = Nat448.a();
        SecT409Field.p(this.g, z);
        return new SecT409FieldElement(z);
    }

    public ECFieldElement p(ECFieldElement x, ECFieldElement y) {
        long[] ax = this.g;
        long[] xx = ((SecT409FieldElement) x).g;
        long[] yx = ((SecT409FieldElement) y).g;
        long[] tt = Nat.j(13);
        SecT409Field.q(ax, tt);
        SecT409Field.l(xx, yx, tt);
        long[] z = Nat448.a();
        SecT409Field.m(tt, z);
        return new SecT409FieldElement(z);
    }

    public ECFieldElement q(int pow) {
        if (pow < 1) {
            return this;
        }
        long[] z = Nat448.a();
        SecT409Field.r(this.g, pow, z);
        return new SecT409FieldElement(z);
    }

    public ECFieldElement g() {
        long[] z = Nat448.a();
        SecT409Field.j(this.g, z);
        return new SecT409FieldElement(z);
    }

    public ECFieldElement n() {
        long[] z = Nat448.a();
        SecT409Field.o(this.g, z);
        return new SecT409FieldElement(z);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SecT409FieldElement)) {
            return false;
        }
        return Nat448.c(this.g, ((SecT409FieldElement) other).g);
    }

    public int hashCode() {
        return Arrays.N(this.g, 0, 7) ^ 4090087;
    }
}
