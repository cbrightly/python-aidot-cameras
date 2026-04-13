package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Mod;
import org.spongycastle.math.raw.Nat192;
import org.spongycastle.util.Arrays;

public class SecP192R1FieldElement extends ECFieldElement {
    public static final BigInteger g = SecP192R1Curve.i;
    protected int[] h;

    public SecP192R1FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.compareTo(g) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP192R1FieldElement");
        }
        this.h = SecP192R1Field.d(x);
    }

    public SecP192R1FieldElement() {
        this.h = Nat192.e();
    }

    protected SecP192R1FieldElement(int[] x) {
        this.h = x;
    }

    public boolean i() {
        return Nat192.s(this.h);
    }

    public boolean h() {
        return Nat192.q(this.h);
    }

    public boolean s() {
        return Nat192.n(this.h, 0) == 1;
    }

    public BigInteger t() {
        return Nat192.F(this.h);
    }

    public int f() {
        return g.bitLength();
    }

    public ECFieldElement a(ECFieldElement b) {
        int[] z = Nat192.e();
        SecP192R1Field.a(this.h, ((SecP192R1FieldElement) b).h, z);
        return new SecP192R1FieldElement(z);
    }

    public ECFieldElement b() {
        int[] z = Nat192.e();
        SecP192R1Field.b(this.h, z);
        return new SecP192R1FieldElement(z);
    }

    public ECFieldElement r(ECFieldElement b) {
        int[] z = Nat192.e();
        SecP192R1Field.m(this.h, ((SecP192R1FieldElement) b).h, z);
        return new SecP192R1FieldElement(z);
    }

    public ECFieldElement j(ECFieldElement b) {
        int[] z = Nat192.e();
        SecP192R1Field.e(this.h, ((SecP192R1FieldElement) b).h, z);
        return new SecP192R1FieldElement(z);
    }

    public ECFieldElement d(ECFieldElement b) {
        int[] z = Nat192.e();
        Mod.d(SecP192R1Field.a, ((SecP192R1FieldElement) b).h, z);
        SecP192R1Field.e(z, this.h, z);
        return new SecP192R1FieldElement(z);
    }

    public ECFieldElement m() {
        int[] z = Nat192.e();
        SecP192R1Field.g(this.h, z);
        return new SecP192R1FieldElement(z);
    }

    public ECFieldElement o() {
        int[] z = Nat192.e();
        SecP192R1Field.j(this.h, z);
        return new SecP192R1FieldElement(z);
    }

    public ECFieldElement g() {
        int[] z = Nat192.e();
        Mod.d(SecP192R1Field.a, this.h, z);
        return new SecP192R1FieldElement(z);
    }

    public ECFieldElement n() {
        int[] x1 = this.h;
        if (Nat192.s(x1) || Nat192.q(x1)) {
            return this;
        }
        int[] t1 = Nat192.e();
        int[] t2 = Nat192.e();
        SecP192R1Field.j(x1, t1);
        SecP192R1Field.e(t1, x1, t1);
        SecP192R1Field.k(t1, 2, t2);
        SecP192R1Field.e(t2, t1, t2);
        SecP192R1Field.k(t2, 4, t1);
        SecP192R1Field.e(t1, t2, t1);
        SecP192R1Field.k(t1, 8, t2);
        SecP192R1Field.e(t2, t1, t2);
        SecP192R1Field.k(t2, 16, t1);
        SecP192R1Field.e(t1, t2, t1);
        SecP192R1Field.k(t1, 32, t2);
        SecP192R1Field.e(t2, t1, t2);
        SecP192R1Field.k(t2, 64, t1);
        SecP192R1Field.e(t1, t2, t1);
        SecP192R1Field.k(t1, 62, t1);
        SecP192R1Field.j(t1, t2);
        if (Nat192.j(x1, t2)) {
            return new SecP192R1FieldElement(t1);
        }
        return null;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SecP192R1FieldElement)) {
            return false;
        }
        return Nat192.j(this.h, ((SecP192R1FieldElement) other).h);
    }

    public int hashCode() {
        return g.hashCode() ^ Arrays.M(this.h, 0, 6);
    }
}
