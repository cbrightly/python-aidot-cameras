package org.spongycastle.math.ec.custom.djb;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Mod;
import org.spongycastle.math.raw.Nat256;
import org.spongycastle.util.Arrays;

public class Curve25519FieldElement extends ECFieldElement {
    public static final BigInteger g = Curve25519.i;
    private static final int[] h = {1242472624, -991028441, -1389370248, 792926214, 1039914919, 726466713, 1338105611, 730014848};
    protected int[] i;

    public Curve25519FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.compareTo(g) >= 0) {
            throw new IllegalArgumentException("x value invalid for Curve25519FieldElement");
        }
        this.i = Curve25519Field.d(x);
    }

    public Curve25519FieldElement() {
        this.i = Nat256.f();
    }

    protected Curve25519FieldElement(int[] x) {
        this.i = x;
    }

    public boolean i() {
        return Nat256.t(this.i);
    }

    public boolean h() {
        return Nat256.r(this.i);
    }

    public boolean s() {
        return Nat256.o(this.i, 0) == 1;
    }

    public BigInteger t() {
        return Nat256.H(this.i);
    }

    public int f() {
        return g.bitLength();
    }

    public ECFieldElement a(ECFieldElement b) {
        int[] z = Nat256.f();
        Curve25519Field.a(this.i, ((Curve25519FieldElement) b).i, z);
        return new Curve25519FieldElement(z);
    }

    public ECFieldElement b() {
        int[] z = Nat256.f();
        Curve25519Field.b(this.i, z);
        return new Curve25519FieldElement(z);
    }

    public ECFieldElement r(ECFieldElement b) {
        int[] z = Nat256.f();
        Curve25519Field.n(this.i, ((Curve25519FieldElement) b).i, z);
        return new Curve25519FieldElement(z);
    }

    public ECFieldElement j(ECFieldElement b) {
        int[] z = Nat256.f();
        Curve25519Field.e(this.i, ((Curve25519FieldElement) b).i, z);
        return new Curve25519FieldElement(z);
    }

    public ECFieldElement d(ECFieldElement b) {
        int[] z = Nat256.f();
        Mod.d(Curve25519Field.a, ((Curve25519FieldElement) b).i, z);
        Curve25519Field.e(z, this.i, z);
        return new Curve25519FieldElement(z);
    }

    public ECFieldElement m() {
        int[] z = Nat256.f();
        Curve25519Field.g(this.i, z);
        return new Curve25519FieldElement(z);
    }

    public ECFieldElement o() {
        int[] z = Nat256.f();
        Curve25519Field.j(this.i, z);
        return new Curve25519FieldElement(z);
    }

    public ECFieldElement g() {
        int[] z = Nat256.f();
        Mod.d(Curve25519Field.a, this.i, z);
        return new Curve25519FieldElement(z);
    }

    public ECFieldElement n() {
        int[] x1 = this.i;
        if (Nat256.t(x1) || Nat256.r(x1)) {
            return this;
        }
        int[] x2 = Nat256.f();
        Curve25519Field.j(x1, x2);
        Curve25519Field.e(x2, x1, x2);
        int[] x3 = x2;
        Curve25519Field.j(x2, x3);
        Curve25519Field.e(x3, x1, x3);
        int[] x4 = Nat256.f();
        Curve25519Field.j(x3, x4);
        Curve25519Field.e(x4, x1, x4);
        int[] x7 = Nat256.f();
        Curve25519Field.k(x4, 3, x7);
        Curve25519Field.e(x7, x3, x7);
        int[] x11 = x3;
        Curve25519Field.k(x7, 4, x11);
        Curve25519Field.e(x11, x4, x11);
        int[] x15 = x7;
        Curve25519Field.k(x11, 4, x15);
        Curve25519Field.e(x15, x4, x15);
        int[] x30 = x4;
        Curve25519Field.k(x15, 15, x30);
        Curve25519Field.e(x30, x15, x30);
        int[] x60 = x15;
        Curve25519Field.k(x30, 30, x60);
        Curve25519Field.e(x60, x30, x60);
        int[] x120 = x30;
        Curve25519Field.k(x60, 60, x120);
        Curve25519Field.e(x120, x60, x120);
        int[] x131 = x60;
        Curve25519Field.k(x120, 11, x131);
        Curve25519Field.e(x131, x11, x131);
        int[] x251 = x11;
        Curve25519Field.k(x131, 120, x251);
        Curve25519Field.e(x251, x120, x251);
        int[] t1 = x251;
        Curve25519Field.j(t1, t1);
        int[] t2 = x120;
        Curve25519Field.j(t1, t2);
        if (Nat256.k(x1, t2)) {
            return new Curve25519FieldElement(t1);
        }
        Curve25519Field.e(t1, h, t1);
        Curve25519Field.j(t1, t2);
        if (Nat256.k(x1, t2)) {
            return new Curve25519FieldElement(t1);
        }
        return null;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Curve25519FieldElement)) {
            return false;
        }
        return Nat256.k(this.i, ((Curve25519FieldElement) other).i);
    }

    public int hashCode() {
        return g.hashCode() ^ Arrays.M(this.i, 0, 8);
    }
}
