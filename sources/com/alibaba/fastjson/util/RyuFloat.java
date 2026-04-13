package com.alibaba.fastjson.util;

import com.amazonaws.kinesisvideo.producer.Time;
import net.sqlcipher.database.SQLiteDatabase;

public final class RyuFloat {
    private static final int[][] POW5_INV_SPLIT = {new int[]{SQLiteDatabase.CREATE_IF_NECESSARY, 1}, new int[]{214748364, 1717986919}, new int[]{171798691, 1803886265}, new int[]{137438953, 1013612282}, new int[]{219902325, 1192282922}, new int[]{175921860, 953826338}, new int[]{140737488, 763061070}, new int[]{225179981, 791400982}, new int[]{180143985, 203624056}, new int[]{144115188, 162899245}, new int[]{230584300, 1978625710}, new int[]{184467440, 1582900568}, new int[]{147573952, 1266320455}, new int[]{236118324, 308125809}, new int[]{188894659, 675997377}, new int[]{151115727, 970294631}, new int[]{241785163, 1981968139}, new int[]{193428131, 297084323}, new int[]{154742504, 1955654377}, new int[]{247588007, 1840556814}, new int[]{198070406, 613451992}, new int[]{158456325, 61264864}, new int[]{253530120, 98023782}, new int[]{202824096, 78419026}, new int[]{162259276, 1780722139}, new int[]{259614842, 1990161963}, new int[]{207691874, 733136111}, new int[]{166153499, 1016005619}, new int[]{265845599, 337118801}, new int[]{212676479, 699191770}, new int[]{170141183, 988850146}};
    private static final int[][] POW5_SPLIT = {new int[]{536870912, 0}, new int[]{671088640, 0}, new int[]{838860800, 0}, new int[]{1048576000, 0}, new int[]{655360000, 0}, new int[]{819200000, 0}, new int[]{1024000000, 0}, new int[]{640000000, 0}, new int[]{800000000, 0}, new int[]{1000000000, 0}, new int[]{625000000, 0}, new int[]{781250000, 0}, new int[]{976562500, 0}, new int[]{610351562, 1073741824}, new int[]{762939453, SQLiteDatabase.CREATE_IF_NECESSARY}, new int[]{953674316, 872415232}, new int[]{596046447, 1619001344}, new int[]{745058059, 1486880768}, new int[]{931322574, 1321730048}, new int[]{582076609, 289210368}, new int[]{727595761, 898383872}, new int[]{909494701, 1659850752}, new int[]{568434188, 1305842176}, new int[]{710542735, 1632302720}, new int[]{888178419, 1503507488}, new int[]{555111512, 671256724}, new int[]{693889390, 839070905}, new int[]{867361737, 2122580455}, new int[]{542101086, 521306416}, new int[]{677626357, 1725374844}, new int[]{847032947, 546105819}, new int[]{1058791184, 145761362}, new int[]{661744490, 91100851}, new int[]{827180612, 1187617888}, new int[]{1033975765, 1484522360}, new int[]{646234853, 1196261931}, new int[]{807793566, 2032198326}, new int[]{1009741958, 1466506084}, new int[]{631088724, 379695390}, new int[]{788860905, 474619238}, new int[]{986076131, 1130144959}, new int[]{616297582, 437905143}, new int[]{770371977, 1621123253}, new int[]{962964972, 415791331}, new int[]{601853107, 1333611405}, new int[]{752316384, 1130143345}, new int[]{940395480, 1412679181}};

    public static String toString(float value) {
        char[] result = new char[15];
        return new String(result, 0, toString(value, result, 0));
    }

    public static int toString(float value, char[] result, int off) {
        int e2;
        int m2;
        int k;
        int dm;
        int dv;
        int dp;
        boolean dvIsTrailingZeros;
        boolean dpIsTrailingZeros;
        int dm2;
        boolean dmIsTrailingZeros;
        int index;
        int index2;
        int dp2;
        int dm3;
        int bits;
        int index3 = off;
        if (Float.isNaN(value)) {
            int index4 = index3 + 1;
            result[index3] = 'N';
            int index5 = index4 + 1;
            result[index4] = 'a';
            result[index5] = 'N';
            return (index5 + 1) - off;
        } else if (value == Float.POSITIVE_INFINITY) {
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
        } else if (value == Float.NEGATIVE_INFINITY) {
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
            int bits2 = Float.floatToIntBits(value);
            if (bits2 == 0) {
                int index21 = index3 + 1;
                result[index3] = '0';
                int index22 = index21 + 1;
                result[index21] = '.';
                result[index22] = '0';
                return (index22 + 1) - off;
            } else if (bits2 == Integer.MIN_VALUE) {
                int index23 = index3 + 1;
                result[index3] = '-';
                int index24 = index23 + 1;
                result[index23] = '0';
                int index25 = index24 + 1;
                result[index24] = '.';
                result[index25] = '0';
                return (index25 + 1) - off;
            } else {
                int ieeeExponent = (bits2 >> 23) & 255;
                int ieeeMantissa = 8388607 & bits2;
                if (ieeeExponent == 0) {
                    e2 = -149;
                    m2 = ieeeMantissa;
                } else {
                    e2 = (ieeeExponent - 127) - 23;
                    m2 = ieeeMantissa | 8388608;
                }
                int i = 0;
                boolean sign = bits2 < 0;
                boolean even = (m2 & 1) == 0;
                int mv = m2 * 4;
                int i2 = 2;
                int mp = (m2 * 4) + 2;
                int i3 = m2 * 4;
                if (((long) m2) == 8388608 && ieeeExponent > 1) {
                    i2 = 1;
                }
                int mm = i3 - i2;
                int e22 = e2 - 2;
                int lastRemovedDigit = 0;
                if (e22 >= 0) {
                    int q = (int) ((((long) e22) * 3010299) / Time.HUNDREDS_OF_NANOS_IN_A_SECOND);
                    int k2 = ((q == 0 ? 1 : (int) ((((((long) q) * 23219280) + Time.HUNDREDS_OF_NANOS_IN_A_SECOND) - 1) / Time.HUNDREDS_OF_NANOS_IN_A_SECOND)) + 59) - 1;
                    int i4 = (-e22) + q + k2;
                    int[][] iArr = POW5_INV_SPLIT;
                    long pis0 = (long) iArr[q][0];
                    int i5 = k2;
                    long pis1 = (long) iArr[q][1];
                    int bits3 = bits2;
                    int i6 = m2;
                    int i7 = ieeeExponent;
                    int i8 = ieeeMantissa;
                    int dv2 = (int) (((((long) mv) * pis0) + ((((long) mv) * pis1) >> 31)) >> (i4 - 31));
                    dp = (int) (((((long) mp) * pis0) + ((((long) mp) * pis1) >> 31)) >> (i4 - 31));
                    long j = pis0;
                    int dm4 = (int) (((((long) mm) * pis0) + ((((long) mm) * pis1) >> 31)) >> (i4 - 31));
                    if (q == 0 || (dp - 1) / 10 > dm4 / 10) {
                        dm3 = dm4;
                        int i9 = i4;
                        long j2 = pis1;
                        bits = bits3;
                    } else {
                        int e = q - 1;
                        int qx = q - 1;
                        dm3 = dm4;
                        int i10 = e;
                        int i11 = i4;
                        long j3 = pis1;
                        bits = bits3;
                        lastRemovedDigit = (int) ((((((long) mv) * ((long) iArr[qx][0])) + ((((long) mv) * ((long) iArr[qx][1])) >> 31)) >> (((((-e22) + q) - 1) + (((e == 0 ? 1 : (int) ((((((long) e) * 23219280) + Time.HUNDREDS_OF_NANOS_IN_A_SECOND) - 1) / Time.HUNDREDS_OF_NANOS_IN_A_SECOND)) + 59) - 1)) - 31)) % 10);
                    }
                    dm2 = q;
                    int pow5Factor_mp = 0;
                    int v = mp;
                    while (v > 0 && v % 5 == 0) {
                        v /= 5;
                        pow5Factor_mp++;
                    }
                    int pow5Factor_mv = 0;
                    int v2 = mv;
                    while (v2 > 0 && v2 % 5 == 0) {
                        v2 /= 5;
                        pow5Factor_mv++;
                    }
                    int pow5Factor_mm = 0;
                    int v3 = mm;
                    while (v3 > 0 && v3 % 5 == 0) {
                        v3 /= 5;
                        pow5Factor_mm++;
                    }
                    dpIsTrailingZeros = pow5Factor_mp >= q;
                    dvIsTrailingZeros = pow5Factor_mv >= q;
                    dmIsTrailingZeros = pow5Factor_mm >= q;
                    k = index3;
                    int index26 = mv;
                    dv = dv2;
                    dm = dm3;
                    int mv2 = mp;
                    int dm5 = bits;
                } else {
                    int i12 = m2;
                    int i13 = ieeeExponent;
                    int i14 = ieeeMantissa;
                    int ieeeMantissa2 = bits2;
                    int q2 = (int) ((((long) (-e22)) * 6989700) / Time.HUNDREDS_OF_NANOS_IN_A_SECOND);
                    int i15 = (-e22) - q2;
                    int k3 = (i15 == 0 ? 1 : (int) ((((((long) i15) * 23219280) + Time.HUNDREDS_OF_NANOS_IN_A_SECOND) - 1) / Time.HUNDREDS_OF_NANOS_IN_A_SECOND)) - 61;
                    int j4 = q2 - k3;
                    int[][] iArr2 = POW5_SPLIT;
                    long ps0 = (long) iArr2[i15][0];
                    long ps1 = (long) iArr2[i15][1];
                    int j31 = j4 - 31;
                    int i16 = k3;
                    k = index3;
                    int j5 = j4;
                    int[][] iArr3 = iArr2;
                    int dv3 = (int) (((((long) mv) * ps0) + ((((long) mv) * ps1) >> 31)) >> j31);
                    int i17 = ieeeMantissa2;
                    int mv3 = mv;
                    int dp3 = (int) (((((long) mp) * ps0) + ((((long) mp) * ps1) >> 31)) >> j31);
                    long j6 = ps0;
                    int dm6 = (int) (((((long) mm) * ps0) + ((((long) mm) * ps1) >> 31)) >> j31);
                    if (q2 == 0 || (dp3 - 1) / 10 > dm6 / 10) {
                        long j7 = ps1;
                        dv = dv3;
                        dp2 = dp3;
                        dm = dm6;
                        int i18 = mp;
                        int dv4 = j5;
                    } else {
                        int e3 = i15 + 1;
                        int i19 = mp;
                        int j8 = (q2 - 1) - ((e3 == 0 ? 1 : (int) ((((((long) e3) * 23219280) + Time.HUNDREDS_OF_NANOS_IN_A_SECOND) - 1) / Time.HUNDREDS_OF_NANOS_IN_A_SECOND)) - 61);
                        int ix = i15 + 1;
                        int i20 = i15;
                        long j9 = ps1;
                        dv = dv3;
                        dp2 = dp3;
                        dm = dm6;
                        lastRemovedDigit = (int) ((((((long) mv3) * ((long) iArr3[ix][0])) + ((((long) mv3) * ((long) iArr3[ix][1])) >> 31)) >> (j8 - 31)) % 10);
                        int i21 = j8;
                    }
                    dm2 = q2 + e22;
                    dpIsTrailingZeros = 1 >= q2;
                    dvIsTrailingZeros = q2 < 23 && (((1 << (q2 + -1)) - 1) & mv3) == 0;
                    dmIsTrailingZeros = (mm % 2 == 1 ? 0 : 1) >= q2;
                    dp = dp2;
                }
                int dplength = 10;
                int factor = 1000000000;
                while (dplength > 0 && dp < factor) {
                    factor /= 10;
                    dplength--;
                }
                int exp = (dm2 + dplength) - 1;
                boolean scientificNotation = exp < -3 || exp >= 7;
                int removed = 0;
                if (dpIsTrailingZeros && !even) {
                    dp--;
                }
                while (dp / 10 > dm / 10 && (dp >= 100 || !scientificNotation)) {
                    dmIsTrailingZeros &= dm % 10 == 0;
                    dp /= 10;
                    lastRemovedDigit = dv % 10;
                    dv /= 10;
                    dm /= 10;
                    removed++;
                }
                if (dmIsTrailingZeros && even) {
                    while (dm % 10 == 0 && (dp >= 100 || !scientificNotation)) {
                        dp /= 10;
                        lastRemovedDigit = dv % 10;
                        dv /= 10;
                        dm /= 10;
                        removed++;
                    }
                }
                int lastRemovedDigit2 = lastRemovedDigit;
                int dv5 = dv;
                int lastRemovedDigit3 = mm;
                int dm7 = dm;
                int i22 = e22;
                if (dvIsTrailingZeros && lastRemovedDigit2 == 5 && dv5 % 2 == 0) {
                    lastRemovedDigit2 = 4;
                }
                if ((dv5 == dm7 && (!dmIsTrailingZeros || !even)) || lastRemovedDigit2 >= 5) {
                    i = 1;
                }
                int output = dv5 + i;
                int olength = dplength - removed;
                if (sign) {
                    index = k + 1;
                    result[k] = '-';
                } else {
                    index = k;
                }
                if (scientificNotation) {
                    int i23 = dm7;
                    int i24 = 0;
                    while (true) {
                        boolean dmIsTrailingZeros2 = dmIsTrailingZeros;
                        if (i24 >= olength - 1) {
                            break;
                        }
                        int c = output % 10;
                        output /= 10;
                        result[(index + olength) - i24] = (char) (c + 48);
                        i24++;
                        dmIsTrailingZeros = dmIsTrailingZeros2;
                        dm2 = dm2;
                    }
                    int e10 = dm2;
                    result[index] = (char) ((output % 10) + 48);
                    result[index + 1] = '.';
                    int index27 = index + olength + 1;
                    if (olength == 1) {
                        result[index27] = '0';
                        index27++;
                    }
                    int index28 = index27 + 1;
                    result[index27] = 'E';
                    if (exp < 0) {
                        result[index28] = '-';
                        exp = -exp;
                        index28++;
                    }
                    if (exp >= 10) {
                        result[index28] = (char) ((exp / 10) + 48);
                        index28++;
                    }
                    index2 = index28 + 1;
                    result[index28] = (char) ((exp % 10) + 48);
                } else {
                    boolean z = dmIsTrailingZeros;
                    int i25 = dm2;
                    if (exp < 0) {
                        int index29 = index + 1;
                        result[index] = '0';
                        index2 = index29 + 1;
                        result[index29] = '.';
                        int i26 = -1;
                        while (i26 > exp) {
                            result[index2] = '0';
                            i26--;
                            index2++;
                        }
                        int current = index2;
                        int i27 = 0;
                        while (i27 < olength) {
                            result[((current + olength) - i27) - 1] = (char) ((output % 10) + 48);
                            output /= 10;
                            index2++;
                            i27++;
                            current = current;
                        }
                    } else if (exp + 1 >= olength) {
                        for (int i28 = 0; i28 < olength; i28++) {
                            result[((index + olength) - i28) - 1] = (char) ((output % 10) + 48);
                            output /= 10;
                        }
                        int index30 = index + olength;
                        int i29 = olength;
                        while (i29 < exp + 1) {
                            result[index30] = '0';
                            i29++;
                            index30++;
                        }
                        int i30 = index30 + 1;
                        result[index30] = '.';
                        index2 = i30 + 1;
                        result[i30] = '0';
                    } else {
                        int current2 = index + 1;
                        int i31 = 0;
                        while (i31 < olength) {
                            if ((olength - i31) - 1 == exp) {
                                result[((current2 + olength) - i31) - 1] = '.';
                                current2--;
                            }
                            result[((current2 + olength) - i31) - 1] = (char) ((output % 10) + 48);
                            output /= 10;
                            i31++;
                            current2 = current2;
                        }
                        index2 = index + olength + 1;
                    }
                }
                return index2 - off;
            }
        }
    }
}
