package org.spongycastle.math.raw;

public abstract class Nat384 {
    public static void a(int[] x, int[] y, int[] zz) {
        int[] iArr = zz;
        Nat192.v(x, y, zz);
        Nat192.u(x, 6, y, 6, zz, 12);
        int c18 = Nat192.d(iArr, 6, iArr, 12);
        int c182 = c18 + Nat192.c(iArr, 18, iArr, 12, Nat192.c(iArr, 0, iArr, 6, 0) + c18);
        int[] dx = Nat192.e();
        int[] dy = Nat192.e();
        boolean neg = Nat192.i(x, 6, x, 0, dx, 0) != Nat192.i(y, 6, y, 0, dy, 0);
        int[] tt = Nat192.g();
        Nat192.v(dx, dy, tt);
        Nat.f(24, c182 + (neg ? Nat.d(12, tt, 0, iArr, 6) : Nat.L(12, tt, 0, iArr, 6)), iArr, 18);
    }

    public static void b(int[] x, int[] zz) {
        Nat192.B(x, zz);
        Nat192.A(x, 6, zz, 12);
        int c18 = Nat192.d(zz, 6, zz, 12);
        int c182 = c18 + Nat192.c(zz, 18, zz, 12, Nat192.c(zz, 0, zz, 6, 0) + c18);
        int[] dx = Nat192.e();
        Nat192.i(x, 6, x, 0, dx, 0);
        int[] tt = Nat192.g();
        Nat192.B(dx, tt);
        Nat.f(24, c182 + Nat.L(12, tt, 0, zz, 6), zz, 18);
    }
}
