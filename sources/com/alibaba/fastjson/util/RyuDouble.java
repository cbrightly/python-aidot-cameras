package com.alibaba.fastjson.util;

import com.amazonaws.kinesisvideo.producer.Time;
import java.lang.reflect.Array;
import java.math.BigInteger;
import leedarson.platform.playersdk.PlayerStateDefine;

public final class RyuDouble {
    private static final int[][] POW5_INV_SPLIT;
    private static final int[][] POW5_SPLIT;

    static {
        Class<int> cls = int.class;
        POW5_SPLIT = (int[][]) Array.newInstance(cls, new int[]{326, 4});
        POW5_INV_SPLIT = (int[][]) Array.newInstance(cls, new int[]{291, 4});
        BigInteger bigInteger = BigInteger.ONE;
        BigInteger mask = bigInteger.shiftLeft(31).subtract(bigInteger);
        BigInteger invMask = bigInteger.shiftLeft(31).subtract(bigInteger);
        int i = 0;
        while (i < 326) {
            BigInteger pow = BigInteger.valueOf(5).pow(i);
            int pow5len = pow.bitLength();
            int expectedPow5Bits = i == 0 ? 1 : (int) ((((((long) i) * 23219280) + Time.HUNDREDS_OF_NANOS_IN_A_SECOND) - 1) / Time.HUNDREDS_OF_NANOS_IN_A_SECOND);
            if (expectedPow5Bits == pow5len) {
                if (i < POW5_SPLIT.length) {
                    for (int j = 0; j < 4; j++) {
                        POW5_SPLIT[i][j] = pow.shiftRight((pow5len - 121) + ((3 - j) * 31)).and(mask).intValue();
                    }
                }
                if (i < POW5_INV_SPLIT.length) {
                    BigInteger bigInteger2 = BigInteger.ONE;
                    BigInteger inv = bigInteger2.shiftLeft(pow5len + 121).divide(pow).add(bigInteger2);
                    for (int k = 0; k < 4; k++) {
                        if (k == 0) {
                            POW5_INV_SPLIT[i][k] = inv.shiftRight((3 - k) * 31).intValue();
                        } else {
                            POW5_INV_SPLIT[i][k] = inv.shiftRight((3 - k) * 31).and(invMask).intValue();
                        }
                    }
                }
                i++;
            } else {
                throw new IllegalStateException(pow5len + " != " + expectedPow5Bits);
            }
        }
    }

    public static String toString(double value) {
        char[] result = new char[24];
        return new String(result, 0, toString(value, result, 0));
    }

    public static int toString(double value, char[] result, int off) {
        long m2;
        int e2;
        int index;
        long dp;
        boolean dvIsTrailingZeros;
        boolean dmIsTrailingZeros;
        int e10;
        long dm;
        long dm2;
        int vplength;
        long output;
        int removed;
        int index2;
        int pow5Factor_mp;
        int pow5Factor_mm;
        int pow5Factor_mv;
        int index3 = off;
        if (Double.isNaN(value)) {
            int index4 = index3 + 1;
            result[index3] = 'N';
            int index5 = index4 + 1;
            result[index4] = 'a';
            result[index5] = 'N';
            return (index5 + 1) - off;
        } else if (value == Double.POSITIVE_INFINITY) {
            int index6 = index3 + 1;
            result[index3] = 'I';
            int index7 = index6 + 1;
            result[index6] = 'n';
            int index8 = index7 + 1;
            result[index7] = 'f';
            int index9 = index8 + 1;
            result[index8] = 'i';
            int index10 = index9 + 1;
            result[index9] = 'n';
            int index11 = index10 + 1;
            result[index10] = 'i';
            int index12 = index11 + 1;
            result[index11] = 't';
            result[index12] = 'y';
            return (index12 + 1) - off;
        } else if (value == Double.NEGATIVE_INFINITY) {
            int index13 = index3 + 1;
            result[index3] = '-';
            int index14 = index13 + 1;
            result[index13] = 'I';
            int index15 = index14 + 1;
            result[index14] = 'n';
            int index16 = index15 + 1;
            result[index15] = 'f';
            int index17 = index16 + 1;
            result[index16] = 'i';
            int index18 = index17 + 1;
            result[index17] = 'n';
            int index19 = index18 + 1;
            result[index18] = 'i';
            int index20 = index19 + 1;
            result[index19] = 't';
            result[index20] = 'y';
            return (index20 + 1) - off;
        } else {
            long bits = Double.doubleToLongBits(value);
            if (bits == 0) {
                int index21 = index3 + 1;
                result[index3] = '0';
                int index22 = index21 + 1;
                result[index21] = '.';
                result[index22] = '0';
                return (index22 + 1) - off;
            } else if (bits == Long.MIN_VALUE) {
                int index23 = index3 + 1;
                result[index3] = '-';
                int index24 = index23 + 1;
                result[index23] = '0';
                int index25 = index24 + 1;
                result[index24] = '.';
                result[index25] = '0';
                return (index25 + 1) - off;
            } else {
                int ieeeExponent = (int) ((bits >>> 52) & 2047);
                long ieeeMantissa = bits & 4503599627370495L;
                if (ieeeExponent == 0) {
                    e2 = -1074;
                    m2 = ieeeMantissa;
                } else {
                    e2 = (ieeeExponent + PlayerStateDefine.EC_NO_INIT) - 52;
                    m2 = ieeeMantissa | 4503599627370496L;
                }
                boolean sign = bits < 0;
                boolean even = (m2 & 1) == 0;
                long mv = 4 * m2;
                long mp = (4 * m2) + 2;
                long j = bits;
                int mmShift = (m2 != 4503599627370496L || ieeeExponent <= 1) ? 1 : 0;
                long mm = ((4 * m2) - 1) - ((long) mmShift);
                int e22 = e2 - 2;
                if (e22 >= 0) {
                    int i = ieeeExponent;
                    int q = Math.max(0, ((int) ((((long) e22) * 3010299) / Time.HUNDREDS_OF_NANOS_IN_A_SECOND)) - 1);
                    int k = ((q == 0 ? 1 : (int) ((((((long) q) * 23219280) + Time.HUNDREDS_OF_NANOS_IN_A_SECOND) - 1) / Time.HUNDREDS_OF_NANOS_IN_A_SECOND)) + 122) - 1;
                    int i2 = (-e22) + q + k;
                    int actualShift = (i2 - 93) - 21;
                    if (actualShift >= 0) {
                        int[] ints = POW5_INV_SPLIT[q];
                        long mHigh = mv >>> 31;
                        long mLow = mv & 2147483647L;
                        int i3 = k;
                        int actualShift2 = actualShift;
                        int i4 = i2;
                        int i5 = e22;
                        int q2 = q;
                        index = index3;
                        long mHigh2 = mp >>> 31;
                        long mLow2 = mp & 2147483647L;
                        long dv = ((((((((((((((long) ints[3]) * mLow) >>> 31) + (((long) ints[2]) * mLow)) + (((long) ints[3]) * mHigh)) >>> 31) + (((long) ints[1]) * mLow)) + (((long) ints[2]) * mHigh)) >>> 31) + (((long) ints[0]) * mLow)) + (((long) ints[1]) * mHigh)) >>> 21) + ((((long) ints[0]) * mHigh) << 10)) >>> actualShift2;
                        long bits11 = ((long) ints[2]) * mHigh2;
                        long bits12 = ((long) ints[1]) * mHigh2;
                        long j2 = mHigh2;
                        long j3 = (((((((((long) ints[3]) * mLow2) >>> 31) + (((long) ints[2]) * mLow2)) + (((long) ints[3]) * mHigh2)) >>> 31) + (((long) ints[1]) * mLow2)) + bits11) >>> 31;
                        long mHigh3 = mm >>> 31;
                        long mLow3 = mm & 2147483647L;
                        dp = ((((j3 + (((long) ints[0]) * mLow2)) + bits12) >>> 21) + ((((long) ints[0]) * mHigh2) << 10)) >>> actualShift2;
                        long j4 = mHigh3;
                        long dm3 = ((((((((((((((long) ints[3]) * mLow3) >>> 31) + (((long) ints[2]) * mLow3)) + (((long) ints[3]) * mHigh3)) >>> 31) + (((long) ints[1]) * mLow3)) + (((long) ints[2]) * mHigh3)) >>> 31) + (((long) ints[0]) * mLow3)) + (((long) ints[1]) * mHigh3)) >>> 21) + ((((long) ints[0]) * mHigh3) << 10)) >>> actualShift2;
                        int e102 = q2;
                        int q3 = q2;
                        if (q3 <= 21) {
                            long j5 = 0;
                            if (mv % 5 == 0) {
                                long v = mv;
                                if (v % 5 != 0) {
                                    pow5Factor_mv = 0;
                                } else if (v % 25 != 0) {
                                    pow5Factor_mv = 1;
                                } else if (v % 125 != 0) {
                                    pow5Factor_mv = 2;
                                } else if (v % 625 != 0) {
                                    pow5Factor_mv = 3;
                                } else {
                                    int pow5Factor_mv2 = 4;
                                    long v2 = v / 625;
                                    while (v2 > j5 && v2 % 5 == j5) {
                                        v2 /= 5;
                                        pow5Factor_mv2++;
                                        j5 = 0;
                                    }
                                    pow5Factor_mv = pow5Factor_mv2;
                                }
                                dvIsTrailingZeros = pow5Factor_mv >= q3;
                                dmIsTrailingZeros = false;
                            } else if (even) {
                                long v3 = mm;
                                long j6 = 0;
                                if (v3 % 5 != 0) {
                                    pow5Factor_mm = 0;
                                } else if (v3 % 25 != 0) {
                                    pow5Factor_mm = 1;
                                } else if (v3 % 125 != 0) {
                                    pow5Factor_mm = 2;
                                } else if (v3 % 625 != 0) {
                                    pow5Factor_mm = 3;
                                } else {
                                    int pow5Factor_mm2 = 4;
                                    long v4 = v3 / 625;
                                    while (v4 > j6 && v4 % 5 == j6) {
                                        v4 /= 5;
                                        pow5Factor_mm2++;
                                        j6 = 0;
                                    }
                                    pow5Factor_mm = pow5Factor_mm2;
                                }
                                dmIsTrailingZeros = pow5Factor_mm >= q3;
                                dvIsTrailingZeros = false;
                            } else {
                                long v5 = mp;
                                long j7 = 0;
                                if (v5 % 5 != 0) {
                                    pow5Factor_mp = 0;
                                } else if (v5 % 25 != 0) {
                                    pow5Factor_mp = 1;
                                } else if (v5 % 125 != 0) {
                                    pow5Factor_mp = 2;
                                } else if (v5 % 625 != 0) {
                                    pow5Factor_mp = 3;
                                } else {
                                    int pow5Factor_mp2 = 4;
                                    long v6 = v5 / 625;
                                    while (v6 > j7 && v6 % 5 == j7) {
                                        v6 /= 5;
                                        pow5Factor_mp2++;
                                        j7 = 0;
                                    }
                                    pow5Factor_mp = pow5Factor_mp2;
                                }
                                if (pow5Factor_mp >= q3) {
                                    dp--;
                                    dmIsTrailingZeros = false;
                                    dvIsTrailingZeros = false;
                                }
                            }
                            e10 = e102;
                            int i6 = mmShift;
                            dm = dm3;
                            dm2 = dv;
                        }
                        dmIsTrailingZeros = false;
                        dvIsTrailingZeros = false;
                        e10 = e102;
                        int i62 = mmShift;
                        dm = dm3;
                        dm2 = dv;
                    } else {
                        int i7 = k;
                        throw new IllegalArgumentException("" + actualShift);
                    }
                } else {
                    index = index3;
                    int i8 = ieeeExponent;
                    int i9 = e22;
                    int q4 = Math.max(0, ((int) ((((long) (-e22)) * 6989700) / Time.HUNDREDS_OF_NANOS_IN_A_SECOND)) - 1);
                    int i10 = (-e22) - q4;
                    int k2 = (i10 == 0 ? 1 : (int) ((((((long) i10) * 23219280) + Time.HUNDREDS_OF_NANOS_IN_A_SECOND) - 1) / Time.HUNDREDS_OF_NANOS_IN_A_SECOND)) - 121;
                    int j8 = q4 - k2;
                    int actualShift3 = (j8 - 93) - 21;
                    if (actualShift3 >= 0) {
                        int[] ints2 = POW5_SPLIT[i10];
                        long mHigh4 = mv >>> 31;
                        long mLow4 = mv & 2147483647L;
                        int i11 = j8;
                        int i12 = i10;
                        int i13 = k2;
                        int mmShift2 = mmShift;
                        int actualShift4 = actualShift3;
                        long j9 = mHigh4;
                        long mHigh5 = mp >>> 31;
                        long mLow5 = mp & 2147483647L;
                        long dv2 = ((((((((((((((long) ints2[3]) * mLow4) >>> 31) + (((long) ints2[2]) * mLow4)) + (((long) ints2[3]) * mHigh4)) >>> 31) + (((long) ints2[1]) * mLow4)) + (((long) ints2[2]) * mHigh4)) >>> 31) + (((long) ints2[0]) * mLow4)) + (((long) ints2[1]) * mHigh4)) >>> 21) + ((((long) ints2[0]) * mHigh4) << 10)) >>> actualShift4;
                        long bits112 = ((long) ints2[2]) * mHigh5;
                        long bits122 = ((long) ints2[1]) * mHigh5;
                        long j10 = mHigh5;
                        long j11 = (((((((((long) ints2[3]) * mLow5) >>> 31) + (((long) ints2[2]) * mLow5)) + (((long) ints2[3]) * mHigh5)) >>> 31) + (((long) ints2[1]) * mLow5)) + bits112) >>> 31;
                        long mHigh6 = mm >>> 31;
                        long mLow6 = mm & 2147483647L;
                        long dp2 = ((((j11 + (((long) ints2[0]) * mLow5)) + bits122) >>> 21) + ((((long) ints2[0]) * mHigh5) << 10)) >>> actualShift4;
                        long j12 = mHigh6;
                        long dm4 = ((((((((((((((long) ints2[3]) * mLow6) >>> 31) + (((long) ints2[2]) * mLow6)) + (((long) ints2[3]) * mHigh6)) >>> 31) + (((long) ints2[1]) * mLow6)) + (((long) ints2[2]) * mHigh6)) >>> 31) + (((long) ints2[0]) * mLow6)) + (((long) ints2[1]) * mHigh6)) >>> 21) + ((((long) ints2[0]) * mHigh6) << 10)) >>> actualShift4;
                        int e103 = q4 + e22;
                        if (q4 <= 1) {
                            dvIsTrailingZeros = true;
                            if (even) {
                                dmIsTrailingZeros = mmShift2 == 1;
                                e10 = e103;
                                dp = dp2;
                                dm = dm4;
                                dm2 = dv2;
                            } else {
                                dp = dp2 - 1;
                                e10 = e103;
                                dmIsTrailingZeros = false;
                                dm = dm4;
                                dm2 = dv2;
                            }
                        } else {
                            if (q4 < 63) {
                                dvIsTrailingZeros = (mv & ((1 << (q4 + -1)) - 1)) == 0;
                                e10 = e103;
                                dp = dp2;
                                dmIsTrailingZeros = false;
                                dm = dm4;
                                dm2 = dv2;
                            } else {
                                e10 = e103;
                                dp = dp2;
                                dmIsTrailingZeros = false;
                                dvIsTrailingZeros = false;
                                dm = dm4;
                                dm2 = dv2;
                            }
                        }
                    } else {
                        int i14 = i10;
                        int i15 = j8;
                        throw new IllegalArgumentException("" + actualShift3);
                    }
                }
                long j13 = 100;
                if (dp >= 1000000000000000000L) {
                    vplength = 19;
                } else if (dp >= 100000000000000000L) {
                    vplength = 18;
                } else if (dp >= 10000000000000000L) {
                    vplength = 17;
                } else if (dp >= 1000000000000000L) {
                    vplength = 16;
                } else if (dp >= 100000000000000L) {
                    vplength = 15;
                } else if (dp >= 10000000000000L) {
                    vplength = 14;
                } else if (dp >= 1000000000000L) {
                    vplength = 13;
                } else if (dp >= 100000000000L) {
                    vplength = 12;
                } else if (dp >= 10000000000L) {
                    vplength = 11;
                } else if (dp >= 1000000000) {
                    vplength = 10;
                } else if (dp >= 100000000) {
                    vplength = 9;
                } else if (dp >= Time.HUNDREDS_OF_NANOS_IN_A_SECOND) {
                    vplength = 8;
                } else if (dp >= Time.NANOS_IN_A_MILLISECOND) {
                    vplength = 7;
                } else if (dp >= 100000) {
                    vplength = 6;
                } else if (dp >= 10000) {
                    vplength = 5;
                } else if (dp >= 1000) {
                    vplength = 4;
                } else if (dp >= 100) {
                    vplength = 3;
                } else if (dp >= 10) {
                    vplength = 2;
                } else {
                    vplength = 1;
                }
                int exp = (e10 + vplength) - 1;
                boolean scientificNotation = exp < -3 || exp >= 7;
                int removed2 = 0;
                int lastRemovedDigit = 0;
                if (dmIsTrailingZeros || dvIsTrailingZeros) {
                    while (dp / 10 > dm / 10 && (dp >= 100 || !scientificNotation)) {
                        dmIsTrailingZeros &= dm % 10 == 0;
                        dvIsTrailingZeros &= lastRemovedDigit == 0;
                        dp /= 10;
                        dm2 /= 10;
                        dm /= 10;
                        removed2++;
                        lastRemovedDigit = (int) (dm2 % 10);
                    }
                    if (dmIsTrailingZeros && even) {
                        while (dm % 10 == 0 && (dp >= 100 || !scientificNotation)) {
                            dvIsTrailingZeros &= lastRemovedDigit == 0;
                            dp /= 10;
                            dm2 /= 10;
                            dm /= 10;
                            removed2++;
                            lastRemovedDigit = (int) (dm2 % 10);
                        }
                    }
                    int lastRemovedDigit2 = lastRemovedDigit;
                    if (dvIsTrailingZeros && lastRemovedDigit2 == 5 && dm2 % 2 == 0) {
                        lastRemovedDigit2 = 4;
                    }
                    output = ((long) (((dm2 != dm || (dmIsTrailingZeros && even)) && lastRemovedDigit2 < 5) ? 0 : 1)) + dm2;
                    removed = removed2;
                } else {
                    int lastRemovedDigit3 = 0;
                    while (dp / 10 > dm / 10 && (dp >= j13 || !scientificNotation)) {
                        lastRemovedDigit3 = (int) (dm2 % 10);
                        dp /= 10;
                        dm2 /= 10;
                        dm /= 10;
                        removed2++;
                        j13 = 100;
                    }
                    output = ((long) ((dm2 == dm || lastRemovedDigit3 >= 5) ? 1 : 0)) + dm2;
                    removed = removed2;
                }
                int removed3 = vplength - removed;
                if (sign) {
                    result[index] = '-';
                    index++;
                }
                if (scientificNotation) {
                    int i16 = 0;
                    while (i16 < removed3 - 1) {
                        output /= 10;
                        result[(index + removed3) - i16] = (char) (((int) (output % 10)) + 48);
                        i16++;
                        dm2 = dm2;
                    }
                    long dv3 = dm2;
                    result[index] = (char) ((int) ((output % 10) + 48));
                    result[index + 1] = '.';
                    int index26 = index + removed3 + 1;
                    if (removed3 == 1) {
                        result[index26] = '0';
                        index26++;
                    }
                    int index27 = index26 + 1;
                    result[index26] = 'E';
                    if (exp < 0) {
                        result[index27] = '-';
                        exp = -exp;
                        index27++;
                    }
                    if (exp >= 100) {
                        int index28 = index27 + 1;
                        result[index27] = (char) ((exp / 100) + 48);
                        exp %= 100;
                        index27 = index28 + 1;
                        result[index28] = (char) ((exp / 10) + 48);
                    } else if (exp >= 10) {
                        result[index27] = (char) ((exp / 10) + 48);
                        index27++;
                    }
                    result[index27] = (char) ((exp % 10) + 48);
                    return (index27 + 1) - off;
                }
                long dv4 = dm2;
                int olength = removed3;
                if (exp < 0) {
                    int index29 = index + 1;
                    result[index] = '0';
                    index2 = index29 + 1;
                    result[index29] = '.';
                    int i17 = -1;
                    while (i17 > exp) {
                        result[index2] = '0';
                        i17--;
                        index2++;
                    }
                    int current = index2;
                    for (int i18 = 0; i18 < olength; i18++) {
                        result[((current + olength) - i18) - 1] = (char) ((int) ((output % 10) + 48));
                        output /= 10;
                        index2++;
                    }
                    int i19 = index2;
                    int index30 = exp;
                } else if (exp + 1 >= olength) {
                    for (int i20 = 0; i20 < olength; i20++) {
                        result[((index + olength) - i20) - 1] = (char) ((int) ((output % 10) + 48));
                        output /= 10;
                    }
                    int index31 = index + olength;
                    int i21 = olength;
                    while (i21 < exp + 1) {
                        result[index31] = '0';
                        i21++;
                        index31++;
                    }
                    int i22 = index31 + 1;
                    result[index31] = '.';
                    index2 = i22 + 1;
                    result[i22] = '0';
                    int i23 = exp;
                } else {
                    int current2 = index + 1;
                    int i24 = 0;
                    while (i24 < olength) {
                        if ((olength - i24) - 1 == exp) {
                            result[((current2 + olength) - i24) - 1] = '.';
                            current2--;
                        }
                        result[((current2 + olength) - i24) - 1] = (char) ((int) ((output % 10) + 48));
                        output /= 10;
                        i24++;
                        exp = exp;
                    }
                    index2 = index + olength + 1;
                }
                return index2 - off;
            }
        }
    }
}
