package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Mod;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.util.Arrays;

public class SecP521R1FieldElement extends ECFieldElement {
    public static final BigInteger g = SecP521R1Curve.i;
    protected int[] h;

    public SecP521R1FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.compareTo(g) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP521R1FieldElement");
        }
        this.h = SecP521R1Field.c(x);
    }

    public SecP521R1FieldElement() {
        this.h = Nat.i(17);
    }

    protected SecP521R1FieldElement(int[] x) {
        this.h = x;
    }

    public boolean i() {
        return Nat.v(17, this.h);
    }

    public boolean h() {
        return Nat.u(17, this.h);
    }

    public boolean s() {
        return Nat.o(this.h, 0) == 1;
    }

    public BigInteger t() {
        return Nat.O(17, this.h);
    }

    public int f() {
        return g.bitLength();
    }

    public ECFieldElement a(ECFieldElement b) {
        int[] z = Nat.i(17);
        SecP521R1Field.a(this.h, ((SecP521R1FieldElement) b).h, z);
        return new SecP521R1FieldElement(z);
    }

    public ECFieldElement b() {
        int[] z = Nat.i(17);
        SecP521R1Field.b(this.h, z);
        return new SecP521R1FieldElement(z);
    }

    public ECFieldElement r(ECFieldElement b) {
        int[] z = Nat.i(17);
        SecP521R1Field.l(this.h, ((SecP521R1FieldElement) b).h, z);
        return new SecP521R1FieldElement(z);
    }

    public ECFieldElement j(ECFieldElement b) {
        int[] z = Nat.i(17);
        SecP521R1Field.f(this.h, ((SecP521R1FieldElement) b).h, z);
        return new SecP521R1FieldElement(z);
    }

    public ECFieldElement d(ECFieldElement b) {
        int[] z = Nat.i(17);
        Mod.d(SecP521R1Field.a, ((SecP521R1FieldElement) b).h, z);
        SecP521R1Field.f(z, this.h, z);
        return new SecP521R1FieldElement(z);
    }

    public ECFieldElement m() {
        int[] z = Nat.i(17);
        SecP521R1Field.g(this.h, z);
        return new SecP521R1FieldElement(z);
    }

    public ECFieldElement o() {
        int[] z = Nat.i(17);
        SecP521R1Field.j(this.h, z);
        return new SecP521R1FieldElement(z);
    }

    public ECFieldElement g() {
        int[] z = Nat.i(17);
        Mod.d(SecP521R1Field.a, this.h, z);
        return new SecP521R1FieldElement(z);
    }

    public ECFieldElement n() {
        int[] x1 = this.h;
        if (Nat.v(17, x1) || Nat.u(17, x1)) {
            return this;
        }
        int[] t1 = Nat.i(17);
        int[] t2 = Nat.i(17);
        SecP521R1Field.k(x1, 519, t1);
        SecP521R1Field.j(t1, t2);
        if (Nat.m(17, x1, t2)) {
            return new SecP521R1FieldElement(t1);
        }
        return null;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SecP521R1FieldElement)) {
            return false;
        }
        return Nat.m(17, this.h, ((SecP521R1FieldElement) other).h);
    }

    public int hashCode() {
        return g.hashCode() ^ Arrays.M(this.h, 0, 17);
    }
}
