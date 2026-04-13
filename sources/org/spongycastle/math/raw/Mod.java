package org.spongycastle.math.raw;

import java.util.Random;

public abstract class Mod {
    public static void d(int[] p, int[] x, int[] z) {
        int len = p.length;
        if (Nat.v(len, x)) {
            throw new IllegalArgumentException("'x' cannot be 0");
        } else if (Nat.u(len, x)) {
            System.arraycopy(x, 0, z, 0, len);
        } else {
            int[] u = Nat.h(len, x);
            int[] a = Nat.i(len);
            a[0] = 1;
            int ac = 0;
            if ((u[0] & 1) == 0) {
                ac = c(p, u, len, a, 0);
            }
            if (Nat.u(len, u)) {
                b(p, ac, a, z);
                return;
            }
            int[] v = Nat.h(len, p);
            int[] b = Nat.i(len);
            int bc = 0;
            int uvLen = len;
            while (true) {
                if (u[uvLen - 1] == 0 && v[uvLen - 1] == 0) {
                    uvLen--;
                } else if (Nat.p(uvLen, u, v)) {
                    Nat.M(uvLen, v, u);
                    ac = c(p, u, uvLen, a, ac + (Nat.M(len, b, a) - bc));
                    if (Nat.u(uvLen, u)) {
                        b(p, ac, a, z);
                        return;
                    }
                } else {
                    Nat.M(uvLen, u, v);
                    bc = c(p, v, uvLen, b, bc + (Nat.M(len, a, b) - ac));
                    if (Nat.u(uvLen, v)) {
                        b(p, bc, b, z);
                        return;
                    }
                }
            }
        }
    }

    public static int[] e(int[] p) {
        int len = p.length;
        Random rand = new Random();
        int[] s = Nat.i(len);
        int m = p[len - 1];
        int m2 = m | (m >>> 1);
        int m3 = m2 | (m2 >>> 2);
        int m4 = m3 | (m3 >>> 4);
        int m5 = m4 | (m4 >>> 8);
        int m6 = m5 | (m5 >>> 16);
        do {
            for (int i = 0; i != len; i++) {
                s[i] = rand.nextInt();
            }
            int i2 = len - 1;
            s[i2] = s[i2] & m6;
        } while (Nat.p(len, s, p));
        return s;
    }

    private static void b(int[] p, int ac, int[] a, int[] z) {
        if (ac < 0) {
            Nat.a(p.length, a, p, z);
        } else {
            System.arraycopy(a, 0, z, 0, p.length);
        }
    }

    private static int c(int[] p, int[] u, int uLen, int[] x, int xc) {
        int len = p.length;
        int count = 0;
        while (u[0] == 0) {
            Nat.B(uLen, u, 0);
            count += 32;
        }
        int zeroes = a(u[0]);
        if (zeroes > 0) {
            Nat.z(uLen, u, zeroes, 0);
            count += zeroes;
        }
        for (int i = 0; i < count; i++) {
            if ((x[0] & 1) != 0) {
                if (xc < 0) {
                    xc += Nat.e(len, p, x);
                } else {
                    xc += Nat.M(len, p, x);
                }
            }
            Nat.y(len, x, xc);
        }
        return xc;
    }

    private static int a(int x) {
        int count = 0;
        while ((x & 1) == 0) {
            x >>>= 1;
            count++;
        }
        return count;
    }
}
