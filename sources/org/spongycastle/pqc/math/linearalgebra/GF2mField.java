package org.spongycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;

public class GF2mField {
    private int a = 0;
    private int b;

    public GF2mField(int degree, int poly) {
        if (degree != PolynomialRingGF2.a(poly)) {
            throw new IllegalArgumentException(" Error: the degree is not correct");
        } else if (PolynomialRingGF2.d(poly)) {
            this.a = degree;
            this.b = poly;
        } else {
            throw new IllegalArgumentException(" Error: given polynomial is reducible");
        }
    }

    public GF2mField(byte[] enc) {
        if (enc.length == 4) {
            int d = LittleEndianConversions.d(enc);
            this.b = d;
            if (PolynomialRingGF2.d(d)) {
                this.a = PolynomialRingGF2.a(this.b);
                return;
            }
            throw new IllegalArgumentException("byte array is not an encoded finite field");
        }
        throw new IllegalArgumentException("byte array is not an encoded finite field");
    }

    public int d() {
        return this.a;
    }

    public byte[] e() {
        return LittleEndianConversions.c(this.b);
    }

    public int a(int a2, int b2) {
        return a2 ^ b2;
    }

    public int j(int a2, int b2) {
        return PolynomialRingGF2.e(a2, b2, this.b);
    }

    public int c(int a2, int k) {
        if (k == 0) {
            return 1;
        }
        if (a2 == 0) {
            return 0;
        }
        if (a2 == 1) {
            return 1;
        }
        int result = 1;
        if (k < 0) {
            a2 = h(a2);
            k = -k;
        }
        while (k != 0) {
            if ((k & 1) == 1) {
                result = j(result, a2);
            }
            a2 = j(a2, a2);
            k >>>= 1;
        }
        return result;
    }

    public int h(int a2) {
        return c(a2, (1 << this.a) - 2);
    }

    public int l(int a2) {
        for (int i = 1; i < this.a; i++) {
            a2 = j(a2, a2);
        }
        return a2;
    }

    public int f(SecureRandom sr) {
        return RandUtils.a(sr, 1 << this.a);
    }

    public int g(SecureRandom sr) {
        int count = 0;
        int result = RandUtils.a(sr, 1 << this.a);
        while (result == 0 && count < 1048576) {
            result = RandUtils.a(sr, 1 << this.a);
            count++;
        }
        if (count == 1048576) {
            return 1;
        }
        return result;
    }

    public boolean i(int e) {
        int i = this.a;
        if (i == 31) {
            if (e >= 0) {
                return true;
            }
            return false;
        } else if (e < 0 || e >= (1 << i)) {
            return false;
        } else {
            return true;
        }
    }

    public String b(int a2) {
        String s = "";
        for (int i = 0; i < this.a; i++) {
            if ((((byte) a2) & 1) == 0) {
                s = "0" + s;
            } else {
                s = "1" + s;
            }
            a2 >>>= 1;
        }
        return s;
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof GF2mField)) {
            return false;
        }
        GF2mField otherField = (GF2mField) other;
        if (this.a == otherField.a && this.b == otherField.b) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.b;
    }

    public String toString() {
        return "Finite Field GF(2^" + this.a + ") = GF(2)[X]/<" + k(this.b) + "> ";
    }

    private static String k(int p) {
        String str = "";
        if (p == 0) {
            return "0";
        }
        if (((byte) (p & 1)) == 1) {
            str = "1";
        }
        int p2 = p >>> 1;
        int i = 1;
        while (p2 != 0) {
            if (((byte) (p2 & 1)) == 1) {
                str = str + "+x^" + i;
            }
            p2 >>>= 1;
            i++;
        }
        return str;
    }
}
