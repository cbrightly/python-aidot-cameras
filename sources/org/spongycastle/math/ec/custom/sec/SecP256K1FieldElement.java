package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Mod;
import org.spongycastle.math.raw.Nat256;
import org.spongycastle.util.Arrays;

public class SecP256K1FieldElement extends ECFieldElement {
    public static final BigInteger g = SecP256K1Curve.i;
    protected int[] h;

    public SecP256K1FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.compareTo(g) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP256K1FieldElement");
        }
        this.h = SecP256K1Field.c(x);
    }

    public SecP256K1FieldElement() {
        this.h = Nat256.f();
    }

    protected SecP256K1FieldElement(int[] x) {
        this.h = x;
    }

    public boolean i() {
        return Nat256.t(this.h);
    }

    public boolean h() {
        return Nat256.r(this.h);
    }

    public boolean s() {
        return Nat256.o(this.h, 0) == 1;
    }

    public BigInteger t() {
        return Nat256.H(this.h);
    }

    public int f() {
        return g.bitLength();
    }

    public ECFieldElement a(ECFieldElement b) {
        int[] z = Nat256.f();
        SecP256K1Field.a(this.h, ((SecP256K1FieldElement) b).h, z);
        return new SecP256K1FieldElement(z);
    }

    public ECFieldElement b() {
        int[] z = Nat256.f();
        SecP256K1Field.b(this.h, z);
        return new SecP256K1FieldElement(z);
    }

    public ECFieldElement r(ECFieldElement b) {
        int[] z = Nat256.f();
        SecP256K1Field.k(this.h, ((SecP256K1FieldElement) b).h, z);
        return new SecP256K1FieldElement(z);
    }

    public ECFieldElement j(ECFieldElement b) {
        int[] z = Nat256.f();
        SecP256K1Field.d(this.h, ((SecP256K1FieldElement) b).h, z);
        return new SecP256K1FieldElement(z);
    }

    public ECFieldElement d(ECFieldElement b) {
        int[] z = Nat256.f();
        Mod.d(SecP256K1Field.a, ((SecP256K1FieldElement) b).h, z);
        SecP256K1Field.d(z, this.h, z);
        return new SecP256K1FieldElement(z);
    }

    public ECFieldElement m() {
        int[] z = Nat256.f();
        SecP256K1Field.f(this.h, z);
        return new SecP256K1FieldElement(z);
    }

    public ECFieldElement o() {
        int[] z = Nat256.f();
        SecP256K1Field.i(this.h, z);
        return new SecP256K1FieldElement(z);
    }

    public ECFieldElement g() {
        int[] z = Nat256.f();
        Mod.d(SecP256K1Field.a, this.h, z);
        return new SecP256K1FieldElement(z);
    }

    public ECFieldElement n() {
        int[] x1 = this.h;
        if (Nat256.t(x1) || Nat256.r(x1)) {
            return this;
        }
        int[] x2 = Nat256.f();
        SecP256K1Field.i(x1, x2);
        SecP256K1Field.d(x2, x1, x2);
        int[] x3 = Nat256.f();
        SecP256K1Field.i(x2, x3);
        SecP256K1Field.d(x3, x1, x3);
        int[] x6 = Nat256.f();
        SecP256K1Field.j(x3, 3, x6);
        SecP256K1Field.d(x6, x3, x6);
        int[] x9 = x6;
        SecP256K1Field.j(x6, 3, x9);
        SecP256K1Field.d(x9, x3, x9);
        int[] x11 = x9;
        SecP256K1Field.j(x9, 2, x11);
        SecP256K1Field.d(x11, x2, x11);
        int[] x22 = Nat256.f();
        SecP256K1Field.j(x11, 11, x22);
        SecP256K1Field.d(x22, x11, x22);
        int[] x44 = x11;
        SecP256K1Field.j(x22, 22, x44);
        SecP256K1Field.d(x44, x22, x44);
        int[] x88 = Nat256.f();
        SecP256K1Field.j(x44, 44, x88);
        SecP256K1Field.d(x88, x44, x88);
        int[] x176 = Nat256.f();
        SecP256K1Field.j(x88, 88, x176);
        SecP256K1Field.d(x176, x88, x176);
        int[] x220 = x88;
        SecP256K1Field.j(x176, 44, x220);
        SecP256K1Field.d(x220, x44, x220);
        int[] x223 = x44;
        SecP256K1Field.j(x220, 3, x223);
        SecP256K1Field.d(x223, x3, x223);
        int[] t1 = x223;
        SecP256K1Field.j(t1, 23, t1);
        SecP256K1Field.d(t1, x22, t1);
        SecP256K1Field.j(t1, 6, t1);
        SecP256K1Field.d(t1, x2, t1);
        SecP256K1Field.j(t1, 2, t1);
        int[] t2 = x2;
        SecP256K1Field.i(t1, t2);
        if (Nat256.k(x1, t2)) {
            return new SecP256K1FieldElement(t1);
        }
        return null;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SecP256K1FieldElement)) {
            return false;
        }
        return Nat256.k(this.h, ((SecP256K1FieldElement) other).h);
    }

    public int hashCode() {
        return g.hashCode() ^ Arrays.M(this.h, 0, 8);
    }
}
