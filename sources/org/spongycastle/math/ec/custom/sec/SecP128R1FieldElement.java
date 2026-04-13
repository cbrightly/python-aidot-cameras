package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Mod;
import org.spongycastle.math.raw.Nat128;
import org.spongycastle.util.Arrays;

public class SecP128R1FieldElement extends ECFieldElement {
    public static final BigInteger g = SecP128R1Curve.i;
    protected int[] h;

    public SecP128R1FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.compareTo(g) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP128R1FieldElement");
        }
        this.h = SecP128R1Field.d(x);
    }

    public SecP128R1FieldElement() {
        this.h = Nat128.c();
    }

    protected SecP128R1FieldElement(int[] x) {
        this.h = x;
    }

    public boolean i() {
        return Nat128.o(this.h);
    }

    public boolean h() {
        return Nat128.m(this.h);
    }

    public boolean s() {
        return Nat128.k(this.h, 0) == 1;
    }

    public BigInteger t() {
        return Nat128.v(this.h);
    }

    public int f() {
        return g.bitLength();
    }

    public ECFieldElement a(ECFieldElement b) {
        int[] z = Nat128.c();
        SecP128R1Field.a(this.h, ((SecP128R1FieldElement) b).h, z);
        return new SecP128R1FieldElement(z);
    }

    public ECFieldElement b() {
        int[] z = Nat128.c();
        SecP128R1Field.b(this.h, z);
        return new SecP128R1FieldElement(z);
    }

    public ECFieldElement r(ECFieldElement b) {
        int[] z = Nat128.c();
        SecP128R1Field.m(this.h, ((SecP128R1FieldElement) b).h, z);
        return new SecP128R1FieldElement(z);
    }

    public ECFieldElement j(ECFieldElement b) {
        int[] z = Nat128.c();
        SecP128R1Field.e(this.h, ((SecP128R1FieldElement) b).h, z);
        return new SecP128R1FieldElement(z);
    }

    public ECFieldElement d(ECFieldElement b) {
        int[] z = Nat128.c();
        Mod.d(SecP128R1Field.a, ((SecP128R1FieldElement) b).h, z);
        SecP128R1Field.e(z, this.h, z);
        return new SecP128R1FieldElement(z);
    }

    public ECFieldElement m() {
        int[] z = Nat128.c();
        SecP128R1Field.g(this.h, z);
        return new SecP128R1FieldElement(z);
    }

    public ECFieldElement o() {
        int[] z = Nat128.c();
        SecP128R1Field.j(this.h, z);
        return new SecP128R1FieldElement(z);
    }

    public ECFieldElement g() {
        int[] z = Nat128.c();
        Mod.d(SecP128R1Field.a, this.h, z);
        return new SecP128R1FieldElement(z);
    }

    public ECFieldElement n() {
        int[] x1 = this.h;
        if (Nat128.o(x1) || Nat128.m(x1)) {
            return this;
        }
        int[] x2 = Nat128.c();
        SecP128R1Field.j(x1, x2);
        SecP128R1Field.e(x2, x1, x2);
        int[] x4 = Nat128.c();
        SecP128R1Field.k(x2, 2, x4);
        SecP128R1Field.e(x4, x2, x4);
        int[] x8 = Nat128.c();
        SecP128R1Field.k(x4, 4, x8);
        SecP128R1Field.e(x8, x4, x8);
        int[] x10 = x4;
        SecP128R1Field.k(x8, 2, x10);
        SecP128R1Field.e(x10, x2, x10);
        int[] x20 = x2;
        SecP128R1Field.k(x10, 10, x20);
        SecP128R1Field.e(x20, x10, x20);
        int[] x30 = x8;
        SecP128R1Field.k(x20, 10, x30);
        SecP128R1Field.e(x30, x10, x30);
        int[] x31 = x10;
        SecP128R1Field.j(x30, x31);
        SecP128R1Field.e(x31, x1, x31);
        int[] t1 = x31;
        SecP128R1Field.k(t1, 95, t1);
        int[] t2 = x30;
        SecP128R1Field.j(t1, t2);
        if (Nat128.g(x1, t2)) {
            return new SecP128R1FieldElement(t1);
        }
        return null;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SecP128R1FieldElement)) {
            return false;
        }
        return Nat128.g(this.h, ((SecP128R1FieldElement) other).h);
    }

    public int hashCode() {
        return g.hashCode() ^ Arrays.M(this.h, 0, 4);
    }
}
