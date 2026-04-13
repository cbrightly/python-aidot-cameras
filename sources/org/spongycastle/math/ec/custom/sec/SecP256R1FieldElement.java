package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Mod;
import org.spongycastle.math.raw.Nat256;
import org.spongycastle.util.Arrays;

public class SecP256R1FieldElement extends ECFieldElement {
    public static final BigInteger g = SecP256R1Curve.i;
    protected int[] h;

    public SecP256R1FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.compareTo(g) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP256R1FieldElement");
        }
        this.h = SecP256R1Field.d(x);
    }

    public SecP256R1FieldElement() {
        this.h = Nat256.f();
    }

    protected SecP256R1FieldElement(int[] x) {
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
        SecP256R1Field.a(this.h, ((SecP256R1FieldElement) b).h, z);
        return new SecP256R1FieldElement(z);
    }

    public ECFieldElement b() {
        int[] z = Nat256.f();
        SecP256R1Field.b(this.h, z);
        return new SecP256R1FieldElement(z);
    }

    public ECFieldElement r(ECFieldElement b) {
        int[] z = Nat256.f();
        SecP256R1Field.m(this.h, ((SecP256R1FieldElement) b).h, z);
        return new SecP256R1FieldElement(z);
    }

    public ECFieldElement j(ECFieldElement b) {
        int[] z = Nat256.f();
        SecP256R1Field.e(this.h, ((SecP256R1FieldElement) b).h, z);
        return new SecP256R1FieldElement(z);
    }

    public ECFieldElement d(ECFieldElement b) {
        int[] z = Nat256.f();
        Mod.d(SecP256R1Field.a, ((SecP256R1FieldElement) b).h, z);
        SecP256R1Field.e(z, this.h, z);
        return new SecP256R1FieldElement(z);
    }

    public ECFieldElement m() {
        int[] z = Nat256.f();
        SecP256R1Field.g(this.h, z);
        return new SecP256R1FieldElement(z);
    }

    public ECFieldElement o() {
        int[] z = Nat256.f();
        SecP256R1Field.j(this.h, z);
        return new SecP256R1FieldElement(z);
    }

    public ECFieldElement g() {
        int[] z = Nat256.f();
        Mod.d(SecP256R1Field.a, this.h, z);
        return new SecP256R1FieldElement(z);
    }

    public ECFieldElement n() {
        int[] x1 = this.h;
        if (Nat256.t(x1) || Nat256.r(x1)) {
            return this;
        }
        int[] t1 = Nat256.f();
        int[] t2 = Nat256.f();
        SecP256R1Field.j(x1, t1);
        SecP256R1Field.e(t1, x1, t1);
        SecP256R1Field.k(t1, 2, t2);
        SecP256R1Field.e(t2, t1, t2);
        SecP256R1Field.k(t2, 4, t1);
        SecP256R1Field.e(t1, t2, t1);
        SecP256R1Field.k(t1, 8, t2);
        SecP256R1Field.e(t2, t1, t2);
        SecP256R1Field.k(t2, 16, t1);
        SecP256R1Field.e(t1, t2, t1);
        SecP256R1Field.k(t1, 32, t1);
        SecP256R1Field.e(t1, x1, t1);
        SecP256R1Field.k(t1, 96, t1);
        SecP256R1Field.e(t1, x1, t1);
        SecP256R1Field.k(t1, 94, t1);
        SecP256R1Field.j(t1, t2);
        if (Nat256.k(x1, t2)) {
            return new SecP256R1FieldElement(t1);
        }
        return null;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SecP256R1FieldElement)) {
            return false;
        }
        return Nat256.k(this.h, ((SecP256R1FieldElement) other).h);
    }

    public int hashCode() {
        return g.hashCode() ^ Arrays.M(this.h, 0, 8);
    }
}
