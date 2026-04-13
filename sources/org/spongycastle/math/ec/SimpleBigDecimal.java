package org.spongycastle.math.ec;

import java.math.BigInteger;

public class SimpleBigDecimal {
    private final BigInteger a;
    private final int b;

    public SimpleBigDecimal(BigInteger bigInt, int scale) {
        if (scale >= 0) {
            this.a = bigInt;
            this.b = scale;
            return;
        }
        throw new IllegalArgumentException("scale may not be negative");
    }

    private void c(SimpleBigDecimal b2) {
        if (this.b != b2.b) {
            throw new IllegalArgumentException("Only SimpleBigDecimal of same scale allowed in arithmetic operations");
        }
    }

    public SimpleBigDecimal b(int newScale) {
        if (newScale >= 0) {
            int i = this.b;
            if (newScale == i) {
                return this;
            }
            return new SimpleBigDecimal(this.a.shiftLeft(newScale - i), newScale);
        }
        throw new IllegalArgumentException("scale may not be negative");
    }

    public SimpleBigDecimal a(SimpleBigDecimal b2) {
        c(b2);
        return new SimpleBigDecimal(this.a.add(b2.a), this.b);
    }

    public SimpleBigDecimal g() {
        return new SimpleBigDecimal(this.a.negate(), this.b);
    }

    public SimpleBigDecimal j(SimpleBigDecimal b2) {
        return a(b2.g());
    }

    public SimpleBigDecimal i(BigInteger b2) {
        return new SimpleBigDecimal(this.a.subtract(b2.shiftLeft(this.b)), this.b);
    }

    public int d(BigInteger val) {
        return this.a.compareTo(val.shiftLeft(this.b));
    }

    public BigInteger e() {
        return this.a.shiftRight(this.b);
    }

    public BigInteger h() {
        return a(new SimpleBigDecimal(ECConstants.b, 1).b(this.b)).e();
    }

    public int f() {
        return this.b;
    }

    public String toString() {
        if (this.b == 0) {
            return this.a.toString();
        }
        BigInteger floorBigInt = e();
        BigInteger fract = this.a.subtract(floorBigInt.shiftLeft(this.b));
        if (this.a.signum() == -1) {
            fract = ECConstants.b.shiftLeft(this.b).subtract(fract);
        }
        if (floorBigInt.signum() == -1 && !fract.equals(ECConstants.a)) {
            floorBigInt = floorBigInt.add(ECConstants.b);
        }
        String leftOfPoint = floorBigInt.toString();
        char[] fractCharArr = new char[this.b];
        String fractStr = fract.toString(2);
        int fractLen = fractStr.length();
        int zeroes = this.b - fractLen;
        for (int i = 0; i < zeroes; i++) {
            fractCharArr[i] = '0';
        }
        for (int j = 0; j < fractLen; j++) {
            fractCharArr[zeroes + j] = fractStr.charAt(j);
        }
        String rightOfPoint = new String(fractCharArr);
        StringBuffer sb = new StringBuffer(leftOfPoint);
        sb.append(".");
        sb.append(rightOfPoint);
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SimpleBigDecimal)) {
            return false;
        }
        SimpleBigDecimal other = (SimpleBigDecimal) o;
        if (!this.a.equals(other.a) || this.b != other.b) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.a.hashCode() ^ this.b;
    }
}
