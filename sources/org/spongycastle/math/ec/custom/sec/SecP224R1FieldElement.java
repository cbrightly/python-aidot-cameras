package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Mod;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat224;
import org.spongycastle.util.Arrays;

public class SecP224R1FieldElement extends ECFieldElement {
    public static final BigInteger g = SecP224R1Curve.i;
    protected int[] h;

    public SecP224R1FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.compareTo(g) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP224R1FieldElement");
        }
        this.h = SecP224R1Field.d(x);
    }

    public SecP224R1FieldElement() {
        this.h = Nat224.d();
    }

    protected SecP224R1FieldElement(int[] x) {
        this.h = x;
    }

    public boolean i() {
        return Nat224.k(this.h);
    }

    public boolean h() {
        return Nat224.j(this.h);
    }

    public boolean s() {
        return Nat224.h(this.h, 0) == 1;
    }

    public BigInteger t() {
        return Nat224.t(this.h);
    }

    public int f() {
        return g.bitLength();
    }

    public ECFieldElement a(ECFieldElement b) {
        int[] z = Nat224.d();
        SecP224R1Field.a(this.h, ((SecP224R1FieldElement) b).h, z);
        return new SecP224R1FieldElement(z);
    }

    public ECFieldElement b() {
        int[] z = Nat224.d();
        SecP224R1Field.b(this.h, z);
        return new SecP224R1FieldElement(z);
    }

    public ECFieldElement r(ECFieldElement b) {
        int[] z = Nat224.d();
        SecP224R1Field.m(this.h, ((SecP224R1FieldElement) b).h, z);
        return new SecP224R1FieldElement(z);
    }

    public ECFieldElement j(ECFieldElement b) {
        int[] z = Nat224.d();
        SecP224R1Field.e(this.h, ((SecP224R1FieldElement) b).h, z);
        return new SecP224R1FieldElement(z);
    }

    public ECFieldElement d(ECFieldElement b) {
        int[] z = Nat224.d();
        Mod.d(SecP224R1Field.a, ((SecP224R1FieldElement) b).h, z);
        SecP224R1Field.e(z, this.h, z);
        return new SecP224R1FieldElement(z);
    }

    public ECFieldElement m() {
        int[] z = Nat224.d();
        SecP224R1Field.g(this.h, z);
        return new SecP224R1FieldElement(z);
    }

    public ECFieldElement o() {
        int[] z = Nat224.d();
        SecP224R1Field.j(this.h, z);
        return new SecP224R1FieldElement(z);
    }

    public ECFieldElement g() {
        int[] z = Nat224.d();
        Mod.d(SecP224R1Field.a, this.h, z);
        return new SecP224R1FieldElement(z);
    }

    public ECFieldElement n() {
        int[] c = this.h;
        if (Nat224.k(c) || Nat224.j(c)) {
            return this;
        }
        int[] nc = Nat224.d();
        SecP224R1Field.g(c, nc);
        int[] r = Mod.e(SecP224R1Field.a);
        int[] t = Nat224.d();
        if (!x(c)) {
            return null;
        }
        while (!y(nc, r, t)) {
            SecP224R1Field.b(r, r);
        }
        SecP224R1Field.j(t, r);
        if (Nat224.f(c, r)) {
            return new SecP224R1FieldElement(t);
        }
        return null;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SecP224R1FieldElement)) {
            return false;
        }
        return Nat224.f(this.h, ((SecP224R1FieldElement) other).h);
    }

    public int hashCode() {
        return g.hashCode() ^ Arrays.M(this.h, 0, 7);
    }

    private static boolean x(int[] x) {
        int[] t1 = Nat224.d();
        int[] t2 = Nat224.d();
        Nat224.c(x, t1);
        for (int i = 0; i < 7; i++) {
            Nat224.c(t1, t2);
            SecP224R1Field.k(t1, 1 << i, t1);
            SecP224R1Field.e(t1, t2, t1);
        }
        SecP224R1Field.k(t1, 95, t1);
        return Nat224.j(t1);
    }

    private static void u(int[] nc, int[] d0, int[] e0, int[] d1, int[] e1, int[] f1, int[] t) {
        SecP224R1Field.e(e1, e0, t);
        SecP224R1Field.e(t, nc, t);
        SecP224R1Field.e(d1, d0, f1);
        SecP224R1Field.a(f1, t, f1);
        SecP224R1Field.e(d1, e0, t);
        Nat224.c(f1, d1);
        SecP224R1Field.e(e1, d0, e1);
        SecP224R1Field.a(e1, t, e1);
        SecP224R1Field.j(e1, f1);
        SecP224R1Field.e(f1, nc, f1);
    }

    private static void v(int[] nc, int[] d1, int[] e1, int[] f1, int[] t) {
        Nat224.c(nc, f1);
        int[] d0 = Nat224.d();
        int[] e0 = Nat224.d();
        for (int i = 0; i < 7; i++) {
            Nat224.c(d1, d0);
            Nat224.c(e1, e0);
            int j = 1 << i;
            while (true) {
                int j2 = j - 1;
                if (j2 < 0) {
                    break;
                }
                w(d1, e1, f1, t);
                j = j2;
            }
            u(nc, d0, e0, d1, e1, f1, t);
        }
    }

    private static void w(int[] d, int[] e, int[] f, int[] t) {
        SecP224R1Field.e(e, d, e);
        SecP224R1Field.n(e, e);
        SecP224R1Field.j(d, t);
        SecP224R1Field.a(f, t, d);
        SecP224R1Field.e(f, t, f);
        SecP224R1Field.i(Nat.F(7, f, 2, 0), f);
    }

    private static boolean y(int[] nc, int[] r, int[] t) {
        int[] d1 = Nat224.d();
        Nat224.c(r, d1);
        int[] e1 = Nat224.d();
        e1[0] = 1;
        int[] f1 = Nat224.d();
        v(nc, d1, e1, f1, t);
        int[] d0 = Nat224.d();
        int[] e0 = Nat224.d();
        for (int k = 1; k < 96; k++) {
            Nat224.c(d1, d0);
            Nat224.c(e1, e0);
            w(d1, e1, f1, t);
            if (Nat224.k(d1)) {
                Mod.d(SecP224R1Field.a, e0, t);
                SecP224R1Field.e(t, d0, t);
                return true;
            }
        }
        return false;
    }
}
