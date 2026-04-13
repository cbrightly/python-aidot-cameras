package org.spongycastle.math.ec.custom.gm;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Mod;
import org.spongycastle.math.raw.Nat256;
import org.spongycastle.util.Arrays;

public class SM2P256V1FieldElement extends ECFieldElement {
    public static final BigInteger g = SM2P256V1Curve.i;
    protected int[] h;

    public SM2P256V1FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.compareTo(g) >= 0) {
            throw new IllegalArgumentException("x value invalid for SM2P256V1FieldElement");
        }
        this.h = SM2P256V1Field.d(x);
    }

    public SM2P256V1FieldElement() {
        this.h = Nat256.f();
    }

    protected SM2P256V1FieldElement(int[] x) {
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
        SM2P256V1Field.a(this.h, ((SM2P256V1FieldElement) b).h, z);
        return new SM2P256V1FieldElement(z);
    }

    public ECFieldElement b() {
        int[] z = Nat256.f();
        SM2P256V1Field.b(this.h, z);
        return new SM2P256V1FieldElement(z);
    }

    public ECFieldElement r(ECFieldElement b) {
        int[] z = Nat256.f();
        SM2P256V1Field.m(this.h, ((SM2P256V1FieldElement) b).h, z);
        return new SM2P256V1FieldElement(z);
    }

    public ECFieldElement j(ECFieldElement b) {
        int[] z = Nat256.f();
        SM2P256V1Field.e(this.h, ((SM2P256V1FieldElement) b).h, z);
        return new SM2P256V1FieldElement(z);
    }

    public ECFieldElement d(ECFieldElement b) {
        int[] z = Nat256.f();
        Mod.d(SM2P256V1Field.a, ((SM2P256V1FieldElement) b).h, z);
        SM2P256V1Field.e(z, this.h, z);
        return new SM2P256V1FieldElement(z);
    }

    public ECFieldElement m() {
        int[] z = Nat256.f();
        SM2P256V1Field.g(this.h, z);
        return new SM2P256V1FieldElement(z);
    }

    public ECFieldElement o() {
        int[] z = Nat256.f();
        SM2P256V1Field.j(this.h, z);
        return new SM2P256V1FieldElement(z);
    }

    public ECFieldElement g() {
        int[] z = Nat256.f();
        Mod.d(SM2P256V1Field.a, this.h, z);
        return new SM2P256V1FieldElement(z);
    }

    public ECFieldElement n() {
        int[] x1 = this.h;
        if (Nat256.t(x1) || Nat256.r(x1)) {
            return this;
        }
        int[] x2 = Nat256.f();
        SM2P256V1Field.j(x1, x2);
        SM2P256V1Field.e(x2, x1, x2);
        int[] x4 = Nat256.f();
        SM2P256V1Field.k(x2, 2, x4);
        SM2P256V1Field.e(x4, x2, x4);
        int[] x6 = Nat256.f();
        SM2P256V1Field.k(x4, 2, x6);
        SM2P256V1Field.e(x6, x2, x6);
        int[] x12 = x2;
        SM2P256V1Field.k(x6, 6, x12);
        SM2P256V1Field.e(x12, x6, x12);
        int[] x24 = Nat256.f();
        SM2P256V1Field.k(x12, 12, x24);
        SM2P256V1Field.e(x24, x12, x24);
        int[] x30 = x12;
        SM2P256V1Field.k(x24, 6, x30);
        SM2P256V1Field.e(x30, x6, x30);
        int[] x31 = x6;
        SM2P256V1Field.j(x30, x31);
        SM2P256V1Field.e(x31, x1, x31);
        int[] t1 = x24;
        SM2P256V1Field.k(x31, 31, t1);
        int[] x62 = x30;
        SM2P256V1Field.e(t1, x31, x62);
        SM2P256V1Field.k(t1, 32, t1);
        SM2P256V1Field.e(t1, x62, t1);
        SM2P256V1Field.k(t1, 62, t1);
        SM2P256V1Field.e(t1, x62, t1);
        SM2P256V1Field.k(t1, 4, t1);
        SM2P256V1Field.e(t1, x4, t1);
        SM2P256V1Field.k(t1, 32, t1);
        SM2P256V1Field.e(t1, x1, t1);
        SM2P256V1Field.k(t1, 62, t1);
        int[] t2 = x4;
        SM2P256V1Field.j(t1, t2);
        if (Nat256.k(x1, t2)) {
            return new SM2P256V1FieldElement(t1);
        }
        return null;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SM2P256V1FieldElement)) {
            return false;
        }
        return Nat256.k(this.h, ((SM2P256V1FieldElement) other).h);
    }

    public int hashCode() {
        return g.hashCode() ^ Arrays.M(this.h, 0, 8);
    }
}
