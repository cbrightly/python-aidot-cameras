package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Mod;
import org.spongycastle.math.raw.Nat192;
import org.spongycastle.util.Arrays;

public class SecP192K1FieldElement extends ECFieldElement {
    public static final BigInteger g = SecP192K1Curve.i;
    protected int[] h;

    public SecP192K1FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.compareTo(g) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP192K1FieldElement");
        }
        this.h = SecP192K1Field.c(x);
    }

    public SecP192K1FieldElement() {
        this.h = Nat192.e();
    }

    protected SecP192K1FieldElement(int[] x) {
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
        SecP192K1Field.a(this.h, ((SecP192K1FieldElement) b).h, z);
        return new SecP192K1FieldElement(z);
    }

    public ECFieldElement b() {
        int[] z = Nat192.e();
        SecP192K1Field.b(this.h, z);
        return new SecP192K1FieldElement(z);
    }

    public ECFieldElement r(ECFieldElement b) {
        int[] z = Nat192.e();
        SecP192K1Field.k(this.h, ((SecP192K1FieldElement) b).h, z);
        return new SecP192K1FieldElement(z);
    }

    public ECFieldElement j(ECFieldElement b) {
        int[] z = Nat192.e();
        SecP192K1Field.d(this.h, ((SecP192K1FieldElement) b).h, z);
        return new SecP192K1FieldElement(z);
    }

    public ECFieldElement d(ECFieldElement b) {
        int[] z = Nat192.e();
        Mod.d(SecP192K1Field.a, ((SecP192K1FieldElement) b).h, z);
        SecP192K1Field.d(z, this.h, z);
        return new SecP192K1FieldElement(z);
    }

    public ECFieldElement m() {
        int[] z = Nat192.e();
        SecP192K1Field.f(this.h, z);
        return new SecP192K1FieldElement(z);
    }

    public ECFieldElement o() {
        int[] z = Nat192.e();
        SecP192K1Field.i(this.h, z);
        return new SecP192K1FieldElement(z);
    }

    public ECFieldElement g() {
        int[] z = Nat192.e();
        Mod.d(SecP192K1Field.a, this.h, z);
        return new SecP192K1FieldElement(z);
    }

    public ECFieldElement n() {
        int[] x1 = this.h;
        if (Nat192.s(x1) || Nat192.q(x1)) {
            return this;
        }
        int[] x2 = Nat192.e();
        SecP192K1Field.i(x1, x2);
        SecP192K1Field.d(x2, x1, x2);
        int[] x3 = Nat192.e();
        SecP192K1Field.i(x2, x3);
        SecP192K1Field.d(x3, x1, x3);
        int[] x6 = Nat192.e();
        SecP192K1Field.j(x3, 3, x6);
        SecP192K1Field.d(x6, x3, x6);
        int[] x8 = x6;
        SecP192K1Field.j(x6, 2, x8);
        SecP192K1Field.d(x8, x2, x8);
        int[] x16 = x2;
        SecP192K1Field.j(x8, 8, x16);
        SecP192K1Field.d(x16, x8, x16);
        int[] x19 = x8;
        SecP192K1Field.j(x16, 3, x19);
        SecP192K1Field.d(x19, x3, x19);
        int[] x35 = Nat192.e();
        SecP192K1Field.j(x19, 16, x35);
        SecP192K1Field.d(x35, x16, x35);
        int[] x70 = x16;
        SecP192K1Field.j(x35, 35, x70);
        SecP192K1Field.d(x70, x35, x70);
        int[] x140 = x35;
        SecP192K1Field.j(x70, 70, x140);
        SecP192K1Field.d(x140, x70, x140);
        int[] x159 = x70;
        SecP192K1Field.j(x140, 19, x159);
        SecP192K1Field.d(x159, x19, x159);
        int[] t1 = x159;
        SecP192K1Field.j(t1, 20, t1);
        SecP192K1Field.d(t1, x19, t1);
        SecP192K1Field.j(t1, 4, t1);
        SecP192K1Field.d(t1, x3, t1);
        SecP192K1Field.j(t1, 6, t1);
        SecP192K1Field.d(t1, x3, t1);
        SecP192K1Field.i(t1, t1);
        int[] t2 = x3;
        SecP192K1Field.i(t1, t2);
        if (Nat192.j(x1, t2)) {
            return new SecP192K1FieldElement(t1);
        }
        return null;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SecP192K1FieldElement)) {
            return false;
        }
        return Nat192.j(this.h, ((SecP192K1FieldElement) other).h);
    }

    public int hashCode() {
        return g.hashCode() ^ Arrays.M(this.h, 0, 6);
    }
}
