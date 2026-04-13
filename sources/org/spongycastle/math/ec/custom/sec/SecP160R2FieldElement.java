package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Mod;
import org.spongycastle.math.raw.Nat160;
import org.spongycastle.util.Arrays;

public class SecP160R2FieldElement extends ECFieldElement {
    public static final BigInteger g = SecP160R2Curve.i;
    protected int[] h;

    public SecP160R2FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.compareTo(g) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP160R2FieldElement");
        }
        this.h = SecP160R2Field.c(x);
    }

    public SecP160R2FieldElement() {
        this.h = Nat160.c();
    }

    protected SecP160R2FieldElement(int[] x) {
        this.h = x;
    }

    public boolean i() {
        return Nat160.j(this.h);
    }

    public boolean h() {
        return Nat160.i(this.h);
    }

    public boolean s() {
        return Nat160.g(this.h, 0) == 1;
    }

    public BigInteger t() {
        return Nat160.t(this.h);
    }

    public int f() {
        return g.bitLength();
    }

    public ECFieldElement a(ECFieldElement b) {
        int[] z = Nat160.c();
        SecP160R2Field.a(this.h, ((SecP160R2FieldElement) b).h, z);
        return new SecP160R2FieldElement(z);
    }

    public ECFieldElement b() {
        int[] z = Nat160.c();
        SecP160R2Field.b(this.h, z);
        return new SecP160R2FieldElement(z);
    }

    public ECFieldElement r(ECFieldElement b) {
        int[] z = Nat160.c();
        SecP160R2Field.k(this.h, ((SecP160R2FieldElement) b).h, z);
        return new SecP160R2FieldElement(z);
    }

    public ECFieldElement j(ECFieldElement b) {
        int[] z = Nat160.c();
        SecP160R2Field.d(this.h, ((SecP160R2FieldElement) b).h, z);
        return new SecP160R2FieldElement(z);
    }

    public ECFieldElement d(ECFieldElement b) {
        int[] z = Nat160.c();
        Mod.d(SecP160R2Field.a, ((SecP160R2FieldElement) b).h, z);
        SecP160R2Field.d(z, this.h, z);
        return new SecP160R2FieldElement(z);
    }

    public ECFieldElement m() {
        int[] z = Nat160.c();
        SecP160R2Field.f(this.h, z);
        return new SecP160R2FieldElement(z);
    }

    public ECFieldElement o() {
        int[] z = Nat160.c();
        SecP160R2Field.i(this.h, z);
        return new SecP160R2FieldElement(z);
    }

    public ECFieldElement g() {
        int[] z = Nat160.c();
        Mod.d(SecP160R2Field.a, this.h, z);
        return new SecP160R2FieldElement(z);
    }

    public ECFieldElement n() {
        int[] x1 = this.h;
        if (Nat160.j(x1) || Nat160.i(x1)) {
            return this;
        }
        int[] x2 = Nat160.c();
        SecP160R2Field.i(x1, x2);
        SecP160R2Field.d(x2, x1, x2);
        int[] x3 = Nat160.c();
        SecP160R2Field.i(x2, x3);
        SecP160R2Field.d(x3, x1, x3);
        int[] x4 = Nat160.c();
        SecP160R2Field.i(x3, x4);
        SecP160R2Field.d(x4, x1, x4);
        int[] x7 = Nat160.c();
        SecP160R2Field.j(x4, 3, x7);
        SecP160R2Field.d(x7, x3, x7);
        int[] x14 = x4;
        SecP160R2Field.j(x7, 7, x14);
        SecP160R2Field.d(x14, x7, x14);
        int[] x17 = x7;
        SecP160R2Field.j(x14, 3, x17);
        SecP160R2Field.d(x17, x3, x17);
        int[] x31 = Nat160.c();
        SecP160R2Field.j(x17, 14, x31);
        SecP160R2Field.d(x31, x14, x31);
        int[] x62 = x14;
        SecP160R2Field.j(x31, 31, x62);
        SecP160R2Field.d(x62, x31, x62);
        int[] x124 = x31;
        SecP160R2Field.j(x62, 62, x124);
        SecP160R2Field.d(x124, x62, x124);
        int[] x127 = x62;
        SecP160R2Field.j(x124, 3, x127);
        SecP160R2Field.d(x127, x3, x127);
        int[] t1 = x127;
        SecP160R2Field.j(t1, 18, t1);
        SecP160R2Field.d(t1, x17, t1);
        SecP160R2Field.j(t1, 2, t1);
        SecP160R2Field.d(t1, x1, t1);
        SecP160R2Field.j(t1, 3, t1);
        SecP160R2Field.d(t1, x2, t1);
        SecP160R2Field.j(t1, 6, t1);
        SecP160R2Field.d(t1, x3, t1);
        SecP160R2Field.j(t1, 2, t1);
        SecP160R2Field.d(t1, x1, t1);
        int[] t2 = x2;
        SecP160R2Field.i(t1, t2);
        if (Nat160.e(x1, t2)) {
            return new SecP160R2FieldElement(t1);
        }
        return null;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SecP160R2FieldElement)) {
            return false;
        }
        return Nat160.e(this.h, ((SecP160R2FieldElement) other).h);
    }

    public int hashCode() {
        return g.hashCode() ^ Arrays.M(this.h, 0, 5);
    }
}
