package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Mod;
import org.spongycastle.math.raw.Nat160;
import org.spongycastle.util.Arrays;

public class SecP160R1FieldElement extends ECFieldElement {
    public static final BigInteger g = SecP160R1Curve.i;
    protected int[] h;

    public SecP160R1FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.compareTo(g) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP160R1FieldElement");
        }
        this.h = SecP160R1Field.c(x);
    }

    public SecP160R1FieldElement() {
        this.h = Nat160.c();
    }

    protected SecP160R1FieldElement(int[] x) {
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
        SecP160R1Field.a(this.h, ((SecP160R1FieldElement) b).h, z);
        return new SecP160R1FieldElement(z);
    }

    public ECFieldElement b() {
        int[] z = Nat160.c();
        SecP160R1Field.b(this.h, z);
        return new SecP160R1FieldElement(z);
    }

    public ECFieldElement r(ECFieldElement b) {
        int[] z = Nat160.c();
        SecP160R1Field.k(this.h, ((SecP160R1FieldElement) b).h, z);
        return new SecP160R1FieldElement(z);
    }

    public ECFieldElement j(ECFieldElement b) {
        int[] z = Nat160.c();
        SecP160R1Field.d(this.h, ((SecP160R1FieldElement) b).h, z);
        return new SecP160R1FieldElement(z);
    }

    public ECFieldElement d(ECFieldElement b) {
        int[] z = Nat160.c();
        Mod.d(SecP160R1Field.a, ((SecP160R1FieldElement) b).h, z);
        SecP160R1Field.d(z, this.h, z);
        return new SecP160R1FieldElement(z);
    }

    public ECFieldElement m() {
        int[] z = Nat160.c();
        SecP160R1Field.f(this.h, z);
        return new SecP160R1FieldElement(z);
    }

    public ECFieldElement o() {
        int[] z = Nat160.c();
        SecP160R1Field.i(this.h, z);
        return new SecP160R1FieldElement(z);
    }

    public ECFieldElement g() {
        int[] z = Nat160.c();
        Mod.d(SecP160R1Field.a, this.h, z);
        return new SecP160R1FieldElement(z);
    }

    public ECFieldElement n() {
        int[] x1 = this.h;
        if (Nat160.j(x1) || Nat160.i(x1)) {
            return this;
        }
        int[] x2 = Nat160.c();
        SecP160R1Field.i(x1, x2);
        SecP160R1Field.d(x2, x1, x2);
        int[] x4 = Nat160.c();
        SecP160R1Field.j(x2, 2, x4);
        SecP160R1Field.d(x4, x2, x4);
        int[] x8 = x2;
        SecP160R1Field.j(x4, 4, x8);
        SecP160R1Field.d(x8, x4, x8);
        int[] x16 = x4;
        SecP160R1Field.j(x8, 8, x16);
        SecP160R1Field.d(x16, x8, x16);
        int[] x32 = x8;
        SecP160R1Field.j(x16, 16, x32);
        SecP160R1Field.d(x32, x16, x32);
        int[] x64 = x16;
        SecP160R1Field.j(x32, 32, x64);
        SecP160R1Field.d(x64, x32, x64);
        int[] x128 = x32;
        SecP160R1Field.j(x64, 64, x128);
        SecP160R1Field.d(x128, x64, x128);
        int[] x129 = x64;
        SecP160R1Field.i(x128, x129);
        SecP160R1Field.d(x129, x1, x129);
        int[] t1 = x129;
        SecP160R1Field.j(t1, 29, t1);
        int[] t2 = x128;
        SecP160R1Field.i(t1, t2);
        if (Nat160.e(x1, t2)) {
            return new SecP160R1FieldElement(t1);
        }
        return null;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SecP160R1FieldElement)) {
            return false;
        }
        return Nat160.e(this.h, ((SecP160R1FieldElement) other).h);
    }

    public int hashCode() {
        return g.hashCode() ^ Arrays.M(this.h, 0, 5);
    }
}
