package org.spongycastle.pqc.math.linearalgebra;

public final class PolynomialRingGF2 {
    private PolynomialRingGF2() {
    }

    public static int e(int a, int b, int r) {
        int result = 0;
        int p = f(a, r);
        int q = f(b, r);
        if (q != 0) {
            int d = 1 << a(r);
            while (p != 0) {
                if (((byte) (p & 1)) == 1) {
                    result ^= q;
                }
                p >>>= 1;
                q <<= 1;
                if (q >= d) {
                    q ^= r;
                }
            }
        }
        return result;
    }

    public static int a(int p) {
        int result = -1;
        while (p != 0) {
            result++;
            p >>>= 1;
        }
        return result;
    }

    public static int f(int p, int q) {
        int result = p;
        if (q == 0) {
            System.err.println("Error: to be divided by 0");
            return 0;
        }
        while (a(result) >= a(q)) {
            result ^= q << (a(result) - a(q));
        }
        return result;
    }

    public static int b(int p, int q) {
        int a = p;
        int b = q;
        while (b != 0) {
            int c = f(a, b);
            a = b;
            b = c;
        }
        return a;
    }

    public static boolean d(int p) {
        if (p == 0) {
            return false;
        }
        int d = a(p) >>> 1;
        int u = 2;
        for (int i = 0; i < d; i++) {
            u = e(u, u, p);
            if (b(u ^ 2, p) != 1) {
                return false;
            }
        }
        return true;
    }

    public static int c(int deg) {
        if (deg < 0) {
            System.err.println("The Degree is negative");
            return 0;
        } else if (deg > 31) {
            System.err.println("The Degree is more then 31");
            return 0;
        } else if (deg == 0) {
            return 1;
        } else {
            int b = 1 << (deg + 1);
            for (int i = (1 << deg) + 1; i < b; i += 2) {
                if (d(i)) {
                    return i;
                }
            }
            return 0;
        }
    }
}
