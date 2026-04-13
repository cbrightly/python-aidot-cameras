package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Mod;
import org.spongycastle.math.raw.Nat224;
import org.spongycastle.util.Arrays;

public class SecP224K1FieldElement extends ECFieldElement {
    public static final BigInteger g = SecP224K1Curve.i;
    private static final int[] h = {868209154, -587542221, 579297866, -1014948952, -1470801668, 514782679, -1897982644};
    protected int[] i;

    public SecP224K1FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.compareTo(g) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP224K1FieldElement");
        }
        this.i = SecP224K1Field.c(x);
    }

    public SecP224K1FieldElement() {
        this.i = Nat224.d();
    }

    protected SecP224K1FieldElement(int[] x) {
        this.i = x;
    }

    public boolean i() {
        return Nat224.k(this.i);
    }

    public boolean h() {
        return Nat224.j(this.i);
    }

    public boolean s() {
        return Nat224.h(this.i, 0) == 1;
    }

    public BigInteger t() {
        return Nat224.t(this.i);
    }

    public int f() {
        return g.bitLength();
    }

    public ECFieldElement a(ECFieldElement b) {
        int[] z = Nat224.d();
        SecP224K1Field.a(this.i, ((SecP224K1FieldElement) b).i, z);
        return new SecP224K1FieldElement(z);
    }

    public ECFieldElement b() {
        int[] z = Nat224.d();
        SecP224K1Field.b(this.i, z);
        return new SecP224K1FieldElement(z);
    }

    public ECFieldElement r(ECFieldElement b) {
        int[] z = Nat224.d();
        SecP224K1Field.k(this.i, ((SecP224K1FieldElement) b).i, z);
        return new SecP224K1FieldElement(z);
    }

    public ECFieldElement j(ECFieldElement b) {
        int[] z = Nat224.d();
        SecP224K1Field.d(this.i, ((SecP224K1FieldElement) b).i, z);
        return new SecP224K1FieldElement(z);
    }

    public ECFieldElement d(ECFieldElement b) {
        int[] z = Nat224.d();
        Mod.d(SecP224K1Field.a, ((SecP224K1FieldElement) b).i, z);
        SecP224K1Field.d(z, this.i, z);
        return new SecP224K1FieldElement(z);
    }

    public ECFieldElement m() {
        int[] z = Nat224.d();
        SecP224K1Field.f(this.i, z);
        return new SecP224K1FieldElement(z);
    }

    public ECFieldElement o() {
        int[] z = Nat224.d();
        SecP224K1Field.i(this.i, z);
        return new SecP224K1FieldElement(z);
    }

    public ECFieldElement g() {
        int[] z = Nat224.d();
        Mod.d(SecP224K1Field.a, this.i, z);
        return new SecP224K1FieldElement(z);
    }

    public ECFieldElement n() {
        int[] x1 = this.i;
        if (Nat224.k(x1) || Nat224.j(x1)) {
            return this;
        }
        int[] x2 = Nat224.d();
        SecP224K1Field.i(x1, x2);
        SecP224K1Field.d(x2, x1, x2);
        int[] x3 = x2;
        SecP224K1Field.i(x2, x3);
        SecP224K1Field.d(x3, x1, x3);
        int[] x4 = Nat224.d();
        SecP224K1Field.i(x3, x4);
        SecP224K1Field.d(x4, x1, x4);
        int[] x8 = Nat224.d();
        SecP224K1Field.j(x4, 4, x8);
        SecP224K1Field.d(x8, x4, x8);
        int[] x11 = Nat224.d();
        SecP224K1Field.j(x8, 3, x11);
        SecP224K1Field.d(x11, x3, x11);
        int[] x19 = x11;
        SecP224K1Field.j(x11, 8, x19);
        SecP224K1Field.d(x19, x8, x19);
        int[] x23 = x8;
        SecP224K1Field.j(x19, 4, x23);
        SecP224K1Field.d(x23, x4, x23);
        int[] x42 = x4;
        SecP224K1Field.j(x23, 19, x42);
        SecP224K1Field.d(x42, x19, x42);
        int[] x84 = Nat224.d();
        SecP224K1Field.j(x42, 42, x84);
        SecP224K1Field.d(x84, x42, x84);
        int[] x107 = x42;
        SecP224K1Field.j(x84, 23, x107);
        SecP224K1Field.d(x107, x23, x107);
        int[] x191 = x23;
        SecP224K1Field.j(x107, 84, x191);
        SecP224K1Field.d(x191, x84, x191);
        int[] t1 = x191;
        SecP224K1Field.j(t1, 20, t1);
        SecP224K1Field.d(t1, x19, t1);
        SecP224K1Field.j(t1, 3, t1);
        SecP224K1Field.d(t1, x1, t1);
        SecP224K1Field.j(t1, 2, t1);
        SecP224K1Field.d(t1, x1, t1);
        SecP224K1Field.j(t1, 4, t1);
        SecP224K1Field.d(t1, x3, t1);
        SecP224K1Field.i(t1, t1);
        int[] t2 = x84;
        SecP224K1Field.i(t1, t2);
        if (Nat224.f(x1, t2)) {
            return new SecP224K1FieldElement(t1);
        }
        SecP224K1Field.d(t1, h, t1);
        SecP224K1Field.i(t1, t2);
        if (Nat224.f(x1, t2)) {
            return new SecP224K1FieldElement(t1);
        }
        return null;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SecP224K1FieldElement)) {
            return false;
        }
        return Nat224.f(this.i, ((SecP224K1FieldElement) other).i);
    }

    public int hashCode() {
        return g.hashCode() ^ Arrays.M(this.i, 0, 7);
    }
}
