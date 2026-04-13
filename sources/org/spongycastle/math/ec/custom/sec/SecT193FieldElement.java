package org.spongycastle.math.ec.custom.sec;

import com.alibaba.fastjson.asm.Opcodes;
import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Nat256;
import org.spongycastle.util.Arrays;

public class SecT193FieldElement extends ECFieldElement {
    protected long[] g;

    public SecT193FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.bitLength() > 193) {
            throw new IllegalArgumentException("x value invalid for SecT193FieldElement");
        }
        this.g = SecT193Field.d(x);
    }

    public SecT193FieldElement() {
        this.g = Nat256.g();
    }

    protected SecT193FieldElement(long[] x) {
        this.g = x;
    }

    public boolean h() {
        return Nat256.s(this.g);
    }

    public boolean i() {
        return Nat256.u(this.g);
    }

    public boolean s() {
        return (this.g[0] & 1) != 0;
    }

    public BigInteger t() {
        return Nat256.I(this.g);
    }

    public int f() {
        return Opcodes.INSTANCEOF;
    }

    public ECFieldElement a(ECFieldElement b) {
        long[] z = Nat256.g();
        SecT193Field.a(this.g, ((SecT193FieldElement) b).g, z);
        return new SecT193FieldElement(z);
    }

    public ECFieldElement b() {
        long[] z = Nat256.g();
        SecT193Field.c(this.g, z);
        return new SecT193FieldElement(z);
    }

    public ECFieldElement r(ECFieldElement b) {
        return a(b);
    }

    public ECFieldElement j(ECFieldElement b) {
        long[] z = Nat256.g();
        SecT193Field.k(this.g, ((SecT193FieldElement) b).g, z);
        return new SecT193FieldElement(z);
    }

    public ECFieldElement k(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
        return l(b, x, y);
    }

    public ECFieldElement l(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
        long[] ax = this.g;
        long[] bx = ((SecT193FieldElement) b).g;
        long[] xx = ((SecT193FieldElement) x).g;
        long[] yx = ((SecT193FieldElement) y).g;
        long[] tt = Nat256.i();
        SecT193Field.l(ax, bx, tt);
        SecT193Field.l(xx, yx, tt);
        long[] z = Nat256.g();
        SecT193Field.m(tt, z);
        return new SecT193FieldElement(z);
    }

    public ECFieldElement d(ECFieldElement b) {
        return j(b.g());
    }

    public ECFieldElement m() {
        return this;
    }

    public ECFieldElement o() {
        long[] z = Nat256.g();
        SecT193Field.p(this.g, z);
        return new SecT193FieldElement(z);
    }

    public ECFieldElement p(ECFieldElement x, ECFieldElement y) {
        long[] ax = this.g;
        long[] xx = ((SecT193FieldElement) x).g;
        long[] yx = ((SecT193FieldElement) y).g;
        long[] tt = Nat256.i();
        SecT193Field.q(ax, tt);
        SecT193Field.l(xx, yx, tt);
        long[] z = Nat256.g();
        SecT193Field.m(tt, z);
        return new SecT193FieldElement(z);
    }

    public ECFieldElement q(int pow) {
        if (pow < 1) {
            return this;
        }
        long[] z = Nat256.g();
        SecT193Field.r(this.g, pow, z);
        return new SecT193FieldElement(z);
    }

    public ECFieldElement g() {
        long[] z = Nat256.g();
        SecT193Field.j(this.g, z);
        return new SecT193FieldElement(z);
    }

    public ECFieldElement n() {
        long[] z = Nat256.g();
        SecT193Field.o(this.g, z);
        return new SecT193FieldElement(z);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SecT193FieldElement)) {
            return false;
        }
        return Nat256.l(this.g, ((SecT193FieldElement) other).g);
    }

    public int hashCode() {
        return Arrays.N(this.g, 0, 4) ^ 1930015;
    }
}
