package org.spongycastle.math.raw;

public abstract class Nat512 {
    public static void a(int[] x, int[] y, int[] zz) {
        int[] iArr = zz;
        Nat256.w(x, y, zz);
        Nat256.v(x, 8, y, 8, zz, 16);
        int c24 = Nat256.e(iArr, 8, iArr, 16);
        int c242 = c24 + Nat256.c(iArr, 24, iArr, 16, Nat256.c(iArr, 0, iArr, 8, 0) + c24);
        int[] dx = Nat256.f();
        int[] dy = Nat256.f();
        boolean neg = Nat256.j(x, 8, x, 0, dx, 0) != Nat256.j(y, 8, y, 0, dy, 0);
        int[] tt = Nat256.h();
        Nat256.w(dx, dy, tt);
        Nat.f(32, c242 + (neg ? Nat.d(16, tt, 0, iArr, 8) : Nat.L(16, tt, 0, iArr, 8)), iArr, 24);
    }

    public static void b(int[] x, int[] zz) {
        Nat256.D(x, zz);
        Nat256.C(x, 8, zz, 16);
        int c24 = Nat256.e(zz, 8, zz, 16);
        int c242 = c24 + Nat256.c(zz, 24, zz, 16, Nat256.c(zz, 0, zz, 8, 0) + c24);
        int[] dx = Nat256.f();
        Nat256.j(x, 8, x, 0, dx, 0);
        int[] tt = Nat256.h();
        Nat256.D(dx, tt);
        Nat.f(32, c242 + Nat.L(16, tt, 0, zz, 8), zz, 24);
    }
}
