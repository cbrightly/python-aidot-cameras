package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Mod;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.util.Arrays;

public class SecP384R1FieldElement extends ECFieldElement {
    public static final BigInteger g = SecP384R1Curve.i;
    protected int[] h;

    public SecP384R1FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.compareTo(g) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP384R1FieldElement");
        }
        this.h = SecP384R1Field.e(x);
    }

    public SecP384R1FieldElement() {
        this.h = Nat.i(12);
    }

    protected SecP384R1FieldElement(int[] x) {
        this.h = x;
    }

    public boolean i() {
        return Nat.v(12, this.h);
    }

    public boolean h() {
        return Nat.u(12, this.h);
    }

    public boolean s() {
        return Nat.o(this.h, 0) == 1;
    }

    public BigInteger t() {
        return Nat.O(12, this.h);
    }

    public int f() {
        return g.bitLength();
    }

    public ECFieldElement a(ECFieldElement b) {
        int[] z = Nat.i(12);
        SecP384R1Field.a(this.h, ((SecP384R1FieldElement) b).h, z);
        return new SecP384R1FieldElement(z);
    }

    public ECFieldElement b() {
        int[] z = Nat.i(12);
        SecP384R1Field.c(this.h, z);
        return new SecP384R1FieldElement(z);
    }

    public ECFieldElement r(ECFieldElement b) {
        int[] z = Nat.i(12);
        SecP384R1Field.m(this.h, ((SecP384R1FieldElement) b).h, z);
        return new SecP384R1FieldElement(z);
    }

    public ECFieldElement j(ECFieldElement b) {
        int[] z = Nat.i(12);
        SecP384R1Field.f(this.h, ((SecP384R1FieldElement) b).h, z);
        return new SecP384R1FieldElement(z);
    }

    public ECFieldElement d(ECFieldElement b) {
        int[] z = Nat.i(12);
        Mod.d(SecP384R1Field.a, ((SecP384R1FieldElement) b).h, z);
        SecP384R1Field.f(z, this.h, z);
        return new SecP384R1FieldElement(z);
    }

    public ECFieldElement m() {
        int[] z = Nat.i(12);
        SecP384R1Field.g(this.h, z);
        return new SecP384R1FieldElement(z);
    }

    public ECFieldElement o() {
        int[] z = Nat.i(12);
        SecP384R1Field.j(this.h, z);
        return new SecP384R1FieldElement(z);
    }

    public ECFieldElement g() {
        int[] z = Nat.i(12);
        Mod.d(SecP384R1Field.a, this.h, z);
        return new SecP384R1FieldElement(z);
    }

    public ECFieldElement n() {
        int[] x1 = this.h;
        if (Nat.v(12, x1) || Nat.u(12, x1)) {
            return this;
        }
        int[] t1 = Nat.i(12);
        int[] t2 = Nat.i(12);
        int[] t3 = Nat.i(12);
        int[] t4 = Nat.i(12);
        SecP384R1Field.j(x1, t1);
        SecP384R1Field.f(t1, x1, t1);
        SecP384R1Field.k(t1, 2, t2);
        SecP384R1Field.f(t2, t1, t2);
        SecP384R1Field.j(t2, t2);
        SecP384R1Field.f(t2, x1, t2);
        SecP384R1Field.k(t2, 5, t3);
        SecP384R1Field.f(t3, t2, t3);
        SecP384R1Field.k(t3, 5, t4);
        SecP384R1Field.f(t4, t2, t4);
        SecP384R1Field.k(t4, 15, t2);
        SecP384R1Field.f(t2, t4, t2);
        SecP384R1Field.k(t2, 2, t3);
        SecP384R1Field.f(t1, t3, t1);
        SecP384R1Field.k(t3, 28, t3);
        SecP384R1Field.f(t2, t3, t2);
        SecP384R1Field.k(t2, 60, t3);
        SecP384R1Field.f(t3, t2, t3);
        int[] r = t2;
        SecP384R1Field.k(t3, 120, r);
        SecP384R1Field.f(r, t3, r);
        SecP384R1Field.k(r, 15, r);
        SecP384R1Field.f(r, t4, r);
        SecP384R1Field.k(r, 33, r);
        SecP384R1Field.f(r, t1, r);
        SecP384R1Field.k(r, 64, r);
        SecP384R1Field.f(r, x1, r);
        SecP384R1Field.k(r, 30, t1);
        SecP384R1Field.j(t1, t2);
        if (Nat.m(12, x1, t2)) {
            return new SecP384R1FieldElement(t1);
        }
        return null;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SecP384R1FieldElement)) {
            return false;
        }
        return Nat.m(12, this.h, ((SecP384R1FieldElement) other).h);
    }

    public int hashCode() {
        return g.hashCode() ^ Arrays.M(this.h, 0, 12);
    }
}
