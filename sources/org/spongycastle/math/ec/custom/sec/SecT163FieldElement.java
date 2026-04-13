package org.spongycastle.math.ec.custom.sec;

import com.alibaba.fastjson.asm.Opcodes;
import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Nat192;
import org.spongycastle.util.Arrays;

public class SecT163FieldElement extends ECFieldElement {
    protected long[] g;

    public SecT163FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.bitLength() > 163) {
            throw new IllegalArgumentException("x value invalid for SecT163FieldElement");
        }
        this.g = SecT163Field.d(x);
    }

    public SecT163FieldElement() {
        this.g = Nat192.f();
    }

    protected SecT163FieldElement(long[] x) {
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
        return Opcodes.IF_ICMPGT;
    }

    public ECFieldElement a(ECFieldElement b) {
        long[] z = Nat192.f();
        SecT163Field.a(this.g, ((SecT163FieldElement) b).g, z);
        return new SecT163FieldElement(z);
    }

    public ECFieldElement b() {
        long[] z = Nat192.f();
        SecT163Field.c(this.g, z);
        return new SecT163FieldElement(z);
    }

    public ECFieldElement r(ECFieldElement b) {
        return a(b);
    }

    public ECFieldElement j(ECFieldElement b) {
        long[] z = Nat192.f();
        SecT163Field.j(this.g, ((SecT163FieldElement) b).g, z);
        return new SecT163FieldElement(z);
    }

    public ECFieldElement k(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
        return l(b, x, y);
    }

    public ECFieldElement l(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
        long[] ax = this.g;
        long[] bx = ((SecT163FieldElement) b).g;
        long[] xx = ((SecT163FieldElement) x).g;
        long[] yx = ((SecT163FieldElement) y).g;
        long[] tt = Nat192.h();
        SecT163Field.k(ax, bx, tt);
        SecT163Field.k(xx, yx, tt);
        long[] z = Nat192.f();
        SecT163Field.l(tt, z);
        return new SecT163FieldElement(z);
    }

    public ECFieldElement d(ECFieldElement b) {
        return j(b.g());
    }

    public ECFieldElement m() {
        return this;
    }

    public ECFieldElement o() {
        long[] z = Nat192.f();
        SecT163Field.o(this.g, z);
        return new SecT163FieldElement(z);
    }

    public ECFieldElement p(ECFieldElement x, ECFieldElement y) {
        long[] ax = this.g;
        long[] xx = ((SecT163FieldElement) x).g;
        long[] yx = ((SecT163FieldElement) y).g;
        long[] tt = Nat192.h();
        SecT163Field.p(ax, tt);
        SecT163Field.k(xx, yx, tt);
        long[] z = Nat192.f();
        SecT163Field.l(tt, z);
        return new SecT163FieldElement(z);
    }

    public ECFieldElement q(int pow) {
        if (pow < 1) {
            return this;
        }
        long[] z = Nat192.f();
        SecT163Field.q(this.g, pow, z);
        return new SecT163FieldElement(z);
    }

    public ECFieldElement g() {
        long[] z = Nat192.f();
        SecT163Field.i(this.g, z);
        return new SecT163FieldElement(z);
    }

    public ECFieldElement n() {
        long[] z = Nat192.f();
        SecT163Field.n(this.g, z);
        return new SecT163FieldElement(z);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SecT163FieldElement)) {
            return false;
        }
        return Nat192.k(this.g, ((SecT163FieldElement) other).g);
    }

    public int hashCode() {
        return Arrays.N(this.g, 0, 3) ^ 163763;
    }
}
