package org.apache.commons.math3.util;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import leedarson.platform.playersdk.PlayerStateDefine;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: FastMath */
public class c {
    private static final double a = StrictMath.log(Double.MAX_VALUE);
    private static final double[][] b = {new double[]{1.0d, 5.669184079525E-24d}, new double[]{-0.25d, -0.25d}, new double[]{0.3333333134651184d, 1.986821492305628E-8d}, new double[]{-0.25d, -6.663542893624021E-14d}, new double[]{0.19999998807907104d, 1.1921056801463227E-8d}, new double[]{-0.1666666567325592d, -7.800414592973399E-9d}, new double[]{0.1428571343421936d, 5.650007086920087E-9d}, new double[]{-0.12502530217170715d, -7.44321345601866E-11d}, new double[]{0.11113807559013367d, 9.219544613762692E-9d}};
    private static final double[][] c = {new double[]{1.0d, -6.032174644509064E-23d}, new double[]{-0.25d, -0.25d}, new double[]{0.3333333134651184d, 1.9868161777724352E-8d}, new double[]{-0.2499999701976776d, -2.957007209750105E-8d}, new double[]{0.19999954104423523d, 1.5830993332061267E-10d}, new double[]{-0.16624879837036133d, -2.6033824355191673E-8d}};
    private static final double[] d = {0.0d, 0.1246747374534607d, 0.24740394949913025d, 0.366272509098053d, 0.4794255495071411d, 0.5850973129272461d, 0.6816387176513672d, 0.7675435543060303d, 0.8414709568023682d, 0.902267575263977d, 0.9489846229553223d, 0.9808930158615112d, 0.9974949359893799d, 0.9985313415527344d};
    private static final double[] e = {0.0d, -4.068233003401932E-9d, 9.755392680573412E-9d, 1.9987994582857286E-8d, -1.0902938113007961E-8d, -3.9986783938944604E-8d, 4.23719669792332E-8d, -5.207000323380292E-8d, 2.800552834259E-8d, 1.883511811213715E-8d, -3.5997360512765566E-9d, 4.116164446561962E-8d, 5.0614674548127384E-8d, -1.0129027912496858E-9d};
    private static final double[] f = {1.0d, 0.9921976327896118d, 0.9689123630523682d, 0.9305076599121094d, 0.8775825500488281d, 0.8109631538391113d, 0.7316888570785522d, 0.6409968137741089d, 0.5403022766113281d, 0.4311765432357788d, 0.3153223395347595d, 0.19454771280288696d, 0.07073719799518585d, -0.05417713522911072d};
    private static final double[] g = {0.0d, 3.4439717236742845E-8d, 5.865827662008209E-8d, -3.7999795083850525E-8d, 1.184154459111628E-8d, -3.43338934259355E-8d, 1.1795268640216787E-8d, 4.438921624363781E-8d, 2.925681159240093E-8d, -2.6437112632041807E-8d, 2.2860509143963117E-8d, -4.813899778443457E-9d, 3.6725170580355583E-9d, 2.0217439756338078E-10d};
    private static final double[] h = {0.0d, 0.1256551444530487d, 0.25534194707870483d, 0.3936265707015991d, 0.5463024377822876d, 0.7214844226837158d, 0.9315965175628662d, 1.1974215507507324d, 1.5574076175689697d, 2.092571258544922d, 3.0095696449279785d, 5.041914939880371d, 14.101419448852539d, -18.430862426757812d};
    private static final double[] i = {0.0d, -7.877917738262007E-9d, -2.5857668567479893E-8d, 5.2240336371356666E-9d, 5.206150291559893E-8d, 1.8307188599677033E-8d, -5.7618793749770706E-8d, 7.848361555046424E-8d, 1.0708593250394448E-7d, 1.7827257129423813E-8d, 2.893485277253286E-8d, 3.1660099222737955E-7d, 4.983191803254889E-7d, -3.356118100840571E-7d};
    private static final long[] j = {2935890503282001226L, 9154082963658192752L, 3952090531849364496L, 9193070505571053912L, 7910884519577875640L, 113236205062349959L, 4577762542105553359L, -5034868814120038111L, 4208363204685324176L, 5648769086999809661L, 2819561105158720014L, -4035746434778044925L, -302932621132653753L, -2644281811660520851L, -3183605296591799669L, 6722166367014452318L, -3512299194304650054L, -7278142539171889152L};
    private static final long[] k = {-3958705157555305932L, -4267615245585081135L};
    private static final double[] l = {0.0d, 0.125d, 0.25d, 0.375d, 0.5d, 0.625d, 0.75d, 0.875d, 1.0d, 1.125d, 1.25d, 1.375d, 1.5d, 1.625d};
    private static final double[] m = {0.6299605249474366d, 0.7937005259840998d, 1.0d, 1.2599210498948732d, 1.5874010519681994d};

    /* compiled from: FastMath */
    public static class b {
        /* access modifiers changed from: private */
        public static final double[] a = d.a();
        /* access modifiers changed from: private */
        public static final double[] b = d.b();
    }

    /* renamed from: org.apache.commons.math3.util.c$c  reason: collision with other inner class name */
    /* compiled from: FastMath */
    public static class C0487c {
        /* access modifiers changed from: private */
        public static final double[] a = d.c();
        /* access modifiers changed from: private */
        public static final double[] b = d.d();
    }

    /* compiled from: FastMath */
    public static class e {
        /* access modifiers changed from: private */
        public static final double[][] a = d.e();
    }

    private static double i(double d2) {
        double d3 = g.b;
        if (d2 <= (-d3) || d2 >= d3) {
            return Double.longBitsToDouble(Double.doubleToRawLongBits(d2) & -1073741824);
        }
        return d2;
    }

    public static double z(double a2) {
        return Math.sqrt(a2);
    }

    public static double h(double x) {
        double x2 = x;
        if (x2 != x2) {
            return x2;
        }
        if (x2 > 20.0d) {
            if (x2 < a) {
                return j(x) * 0.5d;
            }
            double t = j(x2 * 0.5d);
            return 0.5d * t * t;
        } else if (x2 >= -20.0d) {
            double[] hiPrec = new double[2];
            if (x2 < 0.0d) {
                x2 = -x2;
            }
            k(x2, 0.0d, hiPrec);
            double ya = hiPrec[0] + hiPrec[1];
            double yb = -((ya - hiPrec[0]) - hiPrec[1]);
            double temp = ya * 1.073741824E9d;
            double yaa = (ya + temp) - temp;
            double yab = ya - yaa;
            double recip = 1.0d / ya;
            double temp2 = 1.073741824E9d * recip;
            double recipa = (recip + temp2) - temp2;
            double recipb = recip - recipa;
            double recipb2 = recipb + (((((1.0d - (yaa * recipa)) - (yaa * recipb)) - (yab * recipa)) - (yab * recipb)) * recip) + ((-yb) * recip * recip);
            double temp3 = ya + recipa;
            double ya2 = temp3;
            double temp4 = ya2 + recipb2;
            return (temp4 + yb + (-((temp3 - ya) - recipa)) + (-((temp4 - ya2) - recipb2))) * 0.5d;
        } else if (x2 > (-a)) {
            return j(-x2) * 0.5d;
        } else {
            double t2 = j(-0.5d * x2);
            return 0.5d * t2 * t2;
        }
    }

    public static double y(double x) {
        double result;
        double x2 = x;
        boolean negate = false;
        if (x2 != x2) {
            return x2;
        }
        if (x2 > 20.0d) {
            if (x2 < a) {
                return j(x) * 0.5d;
            }
            double t = j(x2 * 0.5d);
            return 0.5d * t * t;
        } else if (x2 < -20.0d) {
            if (x2 > (-a)) {
                return j(-x2) * -0.5d;
            }
            double t2 = j(x2 * -0.5d);
            return -0.5d * t2 * t2;
        } else if (x2 == 0.0d) {
            return x2;
        } else {
            if (x2 < 0.0d) {
                x2 = -x2;
                negate = true;
            }
            if (x2 > 0.25d) {
                double[] hiPrec = new double[2];
                k(x2, 0.0d, hiPrec);
                double ya = hiPrec[0] + hiPrec[1];
                double yb = -((ya - hiPrec[0]) - hiPrec[1]);
                double temp = ya * 1.073741824E9d;
                double yaa = (ya + temp) - temp;
                double yab = ya - yaa;
                double recip = 1.0d / ya;
                double temp2 = 1.073741824E9d * recip;
                double recipa = (recip + temp2) - temp2;
                double recipb = recip - recipa;
                double recipb2 = recipb + (((((1.0d - (yaa * recipa)) - (yaa * recipb)) - (yab * recipa)) - (yab * recipb)) * recip);
                double recipa2 = -recipa;
                double recipb3 = -(((-yb) * recip * recip) + recipb2);
                double temp3 = ya + recipa2;
                double d2 = ya;
                double yb2 = yb + (-((temp3 - ya) - recipa2));
                double ya2 = temp3;
                double temp4 = ya2 + recipb3;
                double[] dArr = hiPrec;
                double d3 = recipa2;
                result = (temp4 + yb2 + (-((temp4 - ya2) - recipb3))) * 0.5d;
                double recipa3 = x2;
            } else {
                double[] hiPrec2 = new double[2];
                l(x2, hiPrec2);
                double ya3 = hiPrec2[0] + hiPrec2[1];
                double yb3 = -((ya3 - hiPrec2[0]) - hiPrec2[1]);
                double denom = ya3 + 1.0d;
                double denomr = 1.0d / denom;
                double ratio = ya3 * denomr;
                double temp5 = ratio * 1.073741824E9d;
                double ra = (ratio + temp5) - temp5;
                double rb = ratio - ra;
                double temp6 = 1.073741824E9d * denom;
                double za = (denom + temp6) - temp6;
                double zb = denom - za;
                double d4 = x2;
                double rb2 = rb + (((((ya3 - (za * ra)) - (za * rb)) - (zb * ra)) - (zb * rb)) * denomr) + (yb3 * denomr) + ((-ya3) * ((-((denom - 1.0d) - ya3)) + yb3) * denomr * denomr);
                double temp7 = ya3 + ra;
                double ya4 = temp7;
                double temp8 = ya4 + rb2;
                result = (temp8 + yb3 + (-((temp7 - ya3) - ra)) + (-((temp8 - ya4) - rb2))) * 0.5d;
            }
            if (negate) {
                return -result;
            }
            return result;
        }
    }

    public static double j(double x) {
        return k(x, 0.0d, (double[]) null);
    }

    private static double k(double x, double extra, double[] hiPrec) {
        double result;
        double d2 = x;
        double d3 = extra;
        double[] dArr = hiPrec;
        int intVal = (int) d2;
        if (d2 < 0.0d) {
            if (d2 < -746.0d) {
                if (dArr != null) {
                    dArr[0] = 0.0d;
                    dArr[1] = 0.0d;
                }
                return 0.0d;
            } else if (intVal < -709) {
                double result2 = k(40.19140625d + d2, d3, dArr) / 2.85040095144011776E17d;
                if (dArr != null) {
                    dArr[0] = dArr[0] / 2.85040095144011776E17d;
                    dArr[1] = dArr[1] / 2.85040095144011776E17d;
                }
                return result2;
            } else if (intVal == -709) {
                double result3 = k(1.494140625d + d2, d3, dArr) / 4.455505956692757d;
                if (dArr != null) {
                    dArr[0] = dArr[0] / 4.455505956692757d;
                    dArr[1] = dArr[1] / 4.455505956692757d;
                }
                return result3;
            } else {
                intVal--;
            }
        } else if (intVal > 709) {
            if (dArr != null) {
                dArr[0] = Double.POSITIVE_INFINITY;
                dArr[1] = 0.0d;
            }
            return Double.POSITIVE_INFINITY;
        }
        double intPartA = C0487c.a[intVal + 750];
        double intPartB = C0487c.b[intVal + 750];
        int intFrac = (int) ((d2 - ((double) intVal)) * 1024.0d);
        double fracPartA = b.a[intFrac];
        double fracPartB = b.b[intFrac];
        double epsilon = d2 - (((double) intVal) + (((double) intFrac) / 1024.0d));
        double z = (((((((0.04168701738764507d * epsilon) + 0.1666666505023083d) * epsilon) + 0.5000000000042687d) * epsilon) + 1.0d) * epsilon) - 1.1409003175371524E20d;
        double tempA = intPartA * fracPartA;
        double tempB = (intPartA * fracPartB) + (intPartB * fracPartA) + (intPartB * fracPartB);
        double tempC = tempB + tempA;
        if (tempC == Double.POSITIVE_INFINITY) {
            return Double.POSITIVE_INFINITY;
        }
        if (d3 != 0.0d) {
            result = (tempC * d3 * z) + (tempC * d3) + (tempC * z) + tempB + tempA;
        } else {
            result = (tempC * z) + tempB + tempA;
        }
        if (dArr != null) {
            dArr[0] = tempA;
            dArr[1] = (tempC * d3 * z) + (tempC * d3) + (tempC * z) + tempB;
        }
        return result;
    }

    private static double l(double x, double[] hiPrecOut) {
        double x2 = x;
        if (x2 != x2 || x2 == 0.0d) {
            return x2;
        }
        if (x2 <= -1.0d || x2 >= 1.0d) {
            double[] hiPrec = new double[2];
            k(x2, 0.0d, hiPrec);
            if (x2 > 0.0d) {
                return (hiPrec[0] - 4.0d) + hiPrec[1];
            }
            double ra = hiPrec[0] - 4.0d;
            return ra + (-((1.0d + ra) - hiPrec[0])) + hiPrec[1];
        }
        boolean negative = false;
        if (x2 < 0.0d) {
            x2 = -x2;
            negative = true;
        }
        int intFrac = (int) (x2 * 1024.0d);
        double tempA = b.a[intFrac] - 1.0d;
        double tempB = b.b[intFrac];
        double temp = tempA + tempB;
        double tempB2 = -((temp - tempA) - tempB);
        double tempA2 = temp;
        double temp2 = tempA2 * 1.073741824E9d;
        double baseA = (tempA2 + temp2) - temp2;
        double baseB = tempB2 + (tempA2 - baseA);
        double d2 = tempB2;
        double epsilon = x2 - (((double) intFrac) / 1024.0d);
        double zb = ((((((0.008336750013465571d * epsilon) + 0.041666663879186654d) * epsilon) + 0.16666666666745392d) * epsilon) + 0.49999999999999994d) * epsilon * epsilon;
        double za = epsilon;
        double temp3 = za + zb;
        double zb2 = -((temp3 - za) - zb);
        double za2 = temp3;
        double temp4 = za2 * 1.073741824E9d;
        double temp5 = (za2 + temp4) - temp4;
        double zb3 = zb2 + (za2 - temp5);
        double za3 = temp5;
        double ya = za3 * baseA;
        double temp6 = (za3 * baseB) + ya;
        double ya2 = temp6;
        double temp7 = ya2 + (zb3 * baseA);
        double yb = (-((temp6 - ya) - (za3 * baseB))) + (-((temp7 - ya2) - (zb3 * baseA)));
        double ya3 = temp7;
        double temp8 = (zb3 * baseB) + ya3;
        double d3 = x2;
        double yb2 = yb + (-((temp8 - ya3) - (zb3 * baseB)));
        double ya4 = temp8;
        double ya5 = ya4 + baseA;
        double ya6 = ya5;
        double temp9 = ya6 + za3;
        double ya7 = temp9;
        double temp10 = ya7 + baseB;
        double ya8 = temp10;
        double temp11 = ya8 + zb3;
        double yb3 = yb2 + (-((ya5 - baseA) - ya4)) + (-((temp9 - ya6) - za3)) + (-((temp10 - ya7) - baseB)) + (-((temp11 - ya8) - zb3));
        double ya9 = temp11;
        if (negative) {
            double denom = ya9 + 1.0d;
            double denomr = 1.0d / denom;
            double ratio = ya9 * denomr;
            double temp12 = ratio * 1.073741824E9d;
            double d4 = epsilon;
            double ra2 = (ratio + temp12) - temp12;
            double rb = ratio - ra2;
            double temp13 = denom * 1.073741824E9d;
            double za4 = (denom + temp13) - temp13;
            double zb4 = denom - za4;
            double rb2 = rb + (((((ya9 - (za4 * ra2)) - (za4 * rb)) - (zb4 * ra2)) - (zb4 * rb)) * denomr) + (yb3 * denomr);
            boolean z = negative;
            double d5 = -ya9;
            ya9 = -ra2;
            yb3 = -(rb2 + (d5 * ((-((denom - 1.0d) - ya9)) + yb3) * denomr * denomr));
            double d6 = za4;
        } else {
            boolean z2 = negative;
        }
        if (hiPrecOut != null) {
            hiPrecOut[0] = ya9;
            hiPrecOut[1] = yb3;
        }
        return ya9 + yb3;
    }

    public static double m(double x) {
        return n(x, (double[]) null);
    }

    private static double n(double x, double[] hiPrec) {
        double lnza;
        if (x == 0.0d) {
            return Double.NEGATIVE_INFINITY;
        }
        long bits = Double.doubleToRawLongBits(x);
        if (((Long.MIN_VALUE & bits) != 0 || x != x) && x != 0.0d) {
            if (hiPrec != null) {
                hiPrec[0] = Double.NaN;
            }
            return Double.NaN;
        } else if (x == Double.POSITIVE_INFINITY) {
            if (hiPrec != null) {
                hiPrec[0] = Double.POSITIVE_INFINITY;
            }
            return Double.POSITIVE_INFINITY;
        } else {
            int exp = ((int) (bits >> 52)) + PlayerStateDefine.EC_NO_INIT;
            if ((9218868437227405312L & bits) == 0) {
                if (x == 0.0d) {
                    if (hiPrec != null) {
                        hiPrec[0] = Double.NEGATIVE_INFINITY;
                    }
                    return Double.NEGATIVE_INFINITY;
                }
                bits <<= 1;
                while ((4503599627370496L & bits) == 0) {
                    exp--;
                    bits <<= 1;
                }
            }
            if ((exp == -1 || exp == 0) && x < 1.01d && x > 0.99d && hiPrec == null) {
                double xa = x - 1.0d;
                double d2 = (xa - x) + 1.0d;
                double tmp = xa * 1.073741824E9d;
                double aa = (xa + tmp) - tmp;
                double ab = xa - aa;
                double xa2 = aa;
                double xb = ab;
                double[][] dArr = b;
                double[] lnCoef_last = dArr[dArr.length - 1];
                double yb = lnCoef_last[0];
                double ab2 = lnCoef_last[1];
                int i2 = dArr.length - 2;
                while (i2 >= 0) {
                    double aa2 = yb * xa2;
                    double tmp2 = aa2 * 1.073741824E9d;
                    double ya = (aa2 + tmp2) - tmp2;
                    double ya2 = aa2 - ya;
                    double[] lnCoef_i = b[i2];
                    double aa3 = ya + lnCoef_i[0];
                    double ab3 = ya2 + (yb * xb) + (ab2 * xa2) + (ab2 * xb) + lnCoef_i[1];
                    double tmp3 = aa3 * 1.073741824E9d;
                    double ya3 = (aa3 + tmp3) - tmp3;
                    i2--;
                    double d3 = ya3;
                    double ya4 = ab3;
                    ab2 = (aa3 - ya3) + ab3;
                    yb = d3;
                }
                double aa4 = yb * xa2;
                double tmp4 = aa4 * 1.073741824E9d;
                double ya5 = (aa4 + tmp4) - tmp4;
                return ya5 + (aa4 - ya5) + (yb * xb) + (ab2 * xa2) + (ab2 * xb);
            }
            double[] lnm = e.a[(int) ((bits & 4499201580859392L) >> 42)];
            double epsilon = ((double) (bits & 4398046511103L)) / (((double) (bits & 4499201580859392L)) + 4.503599627370496E15d);
            double lnzb = 0.0d;
            if (hiPrec != null) {
                double tmp5 = epsilon * 1.073741824E9d;
                double aa5 = (epsilon + tmp5) - tmp5;
                double xa3 = aa5;
                double xb2 = epsilon - aa5;
                double numer = (double) (4398046511103L & bits);
                double denom = ((double) (4499201580859392L & bits)) + 4.503599627370496E15d;
                double xb3 = xb2 + (((numer - (xa3 * denom)) - (xb2 * denom)) / denom);
                double[][] dArr2 = c;
                double[] lnCoef_last2 = dArr2[dArr2.length - 1];
                double yb2 = lnCoef_last2[0];
                double yb3 = lnCoef_last2[1];
                int i3 = dArr2.length - 2;
                while (i3 >= 0) {
                    double aa6 = yb2 * xa3;
                    double tmp6 = aa6 * 1.073741824E9d;
                    double ya6 = (aa6 + tmp6) - tmp6;
                    double ya7 = aa6 - ya6;
                    double[] lnCoef_i2 = c[i3];
                    double aa7 = ya6 + lnCoef_i2[0];
                    double ab4 = ya7 + (yb2 * xb3) + (yb3 * xa3) + (yb3 * xb3) + lnCoef_i2[1];
                    double tmp7 = aa7 * 1.073741824E9d;
                    double ya8 = (aa7 + tmp7) - tmp7;
                    i3--;
                    double d4 = ya8;
                    double ya9 = ab4;
                    yb3 = (aa7 - ya8) + ab4;
                    yb2 = d4;
                }
                double aa8 = yb2 * xa3;
                double ab5 = (yb2 * xb3) + (yb3 * xa3) + (yb3 * xb3);
                lnza = aa8 + ab5;
                double d5 = numer;
                lnzb = -((lnza - aa8) - ab5);
            } else {
                lnza = ((((((((((-0.16624882440418567d * epsilon) + 0.19999954120254515d) * epsilon) - 16.00000002972804d) * epsilon) + 0.3333333333332802d) * epsilon) - 8.0d) * epsilon) + 1.0d) * epsilon;
            }
            double a2 = ((double) exp) * 0.6931470632553101d;
            double c2 = lnm[0] + a2;
            long j2 = bits;
            double a3 = c2;
            double b2 = 0.0d + (-((c2 - a2) - lnm[0]));
            double c3 = a3 + lnza;
            double d6 = -((c3 - a3) - lnza);
            double a4 = c3;
            double c4 = (((double) exp) * 1.1730463525082348E-7d) + a4;
            double d7 = lnza;
            double a5 = c4;
            double c5 = lnm[1] + a5;
            double a6 = c5;
            double c6 = a6 + lnzb;
            double a7 = c6;
            double b3 = b2 + d6 + (-((c4 - a4) - (((double) exp) * 1.1730463525082348E-7d))) + (-((c5 - a5) - lnm[1])) + (-((c6 - a6) - lnzb));
            if (hiPrec != null) {
                hiPrec[0] = a7;
                hiPrec[1] = b3;
            }
            return a7 + b3;
        }
    }

    public static double t(double x, double y) {
        double d2 = x;
        if (y == 0.0d) {
            return 1.0d;
        }
        long yBits = Double.doubleToRawLongBits(y);
        int yRawExp = (int) ((yBits & 9218868437227405312L) >> 52);
        long yRawMantissa = yBits & 4503599627370495L;
        long xBits = Double.doubleToRawLongBits(x);
        int xRawExp = (int) ((xBits & 9218868437227405312L) >> 52);
        long xRawMantissa = xBits & 4503599627370495L;
        if (yRawExp <= 1085) {
            if (yRawExp >= 1023) {
                long yFullMantissa = 4503599627370496L | yRawMantissa;
                if (yRawExp >= 1075) {
                    long l2 = yFullMantissa << (yRawExp - 1075);
                    return u(d2, y < 0.0d ? -l2 : l2);
                } else if ((yFullMantissa & (-1 << (1075 - yRawExp))) == yFullMantissa) {
                    long l3 = yFullMantissa >> (1075 - yRawExp);
                    return u(d2, y < 0.0d ? -l3 : l3);
                }
            }
            if (d2 == 0.0d) {
                return y < 0.0d ? Double.POSITIVE_INFINITY : 0.0d;
            }
            if (xRawExp == 2047) {
                if (xRawMantissa == 0) {
                    return y < 0.0d ? 0.0d : Double.POSITIVE_INFINITY;
                }
                return Double.NaN;
            } else if (d2 < 0.0d) {
                return Double.NaN;
            } else {
                double tmp = y * 1.073741824E9d;
                double ya = (y + tmp) - tmp;
                double yb = y - ya;
                double[] lns = new double[2];
                double lores = n(d2, lns);
                if (Double.isInfinite(lores)) {
                    return lores;
                }
                double lna = lns[0];
                double tmp1 = 1.073741824E9d * lna;
                double tmp2 = (lna + tmp1) - tmp1;
                double lnb = lns[1] + (lna - tmp2);
                double lna2 = tmp2;
                double aa = lna2 * ya;
                double ab = (lna2 * yb) + (lnb * ya) + (lnb * yb);
                double lna3 = aa + ab;
                double d3 = tmp1;
                double lnb2 = -((lna3 - aa) - ab);
                int i2 = xRawExp;
                double[] dArr = lns;
                double d4 = lnb2;
                return k(lna3, ((((((((0.008333333333333333d * lnb2) + 0.041666666666666664d) * lnb2) + 0.16666666666666666d) * lnb2) + 0.5d) * lnb2) + 1.0d) * lnb2, (double[]) null);
            }
        } else if ((yRawExp == 2047 && yRawMantissa != 0) || (xRawExp == 2047 && xRawMantissa != 0)) {
            return Double.NaN;
        } else {
            if (xRawExp != 1023 || xRawMantissa != 0) {
                if ((y > 0.0d) ^ (xRawExp < 1023)) {
                    return Double.POSITIVE_INFINITY;
                }
                return 0.0d;
            } else if (yRawExp == 2047) {
                return Double.NaN;
            } else {
                return 1.0d;
            }
        }
    }

    public static double u(double d2, long e2) {
        if (e2 == 0) {
            return 1.0d;
        }
        if (e2 > 0) {
            return new d(d2).d(e2).d;
        }
        return new d(d2).e().d(-e2).d;
    }

    /* compiled from: FastMath */
    public static class d {
        public static final d a = new d(Double.NaN, 0.0d);
        public static final d b = new d(Double.POSITIVE_INFINITY, 0.0d);
        public static final d c = new d(Double.NEGATIVE_INFINITY, 0.0d);
        /* access modifiers changed from: private */
        public final double d;
        private final double e;
        private final double f;

        d(double x) {
            this.d = x;
            double longBitsToDouble = Double.longBitsToDouble(Double.doubleToRawLongBits(x) & -134217728);
            this.e = longBitsToDouble;
            this.f = x - longBitsToDouble;
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        d(double r10, double r12) {
            /*
                r9 = this;
                r0 = 0
                int r2 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
                if (r2 != 0) goto L_0x0019
                int r0 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1))
                if (r0 != 0) goto L_0x0017
                long r0 = java.lang.Double.doubleToRawLongBits(r10)
                r2 = -9223372036854775808
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r0 != 0) goto L_0x0017
                r0 = -9223372036854775808
                goto L_0x001b
            L_0x0017:
                r3 = r12
                goto L_0x001c
            L_0x0019:
                double r0 = r10 + r12
            L_0x001b:
                r3 = r0
            L_0x001c:
                r2 = r9
                r5 = r10
                r7 = r12
                r2.<init>(r3, r5, r7)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math3.util.c.d.<init>(double, double):void");
        }

        d(double full, double high, double low) {
            this.d = full;
            this.e = high;
            this.f = low;
        }

        public d c(d b2) {
            d dVar = b2;
            d mulBasic = new d(this.d * dVar.d);
            double d2 = this.f;
            double d3 = dVar.f;
            double d4 = mulBasic.d;
            double d5 = this.e;
            double d6 = dVar.e;
            return new d(mulBasic.e, mulBasic.f + ((d2 * d3) - (((d4 - (d5 * d6)) - (d2 * d6)) - (d5 * d3))));
        }

        public d e() {
            d splitInv = new d(1.0d / this.d);
            d product = c(splitInv);
            double error = (product.e - 1.0d) + product.f;
            return Double.isNaN(error) ? splitInv : new d(splitInv.e, splitInv.f - (error / this.d));
        }

        /* access modifiers changed from: private */
        public d d(long e2) {
            d result = new d(1.0d);
            d d2p = new d(this.d, this.e, this.f);
            for (long p = e2; p != 0; p >>>= 1) {
                if ((p & 1) != 0) {
                    result = result.c(d2p);
                }
                d2p = d2p.c(d2p);
            }
            if (!Double.isNaN(result.d)) {
                return result;
            }
            if (Double.isNaN(this.d)) {
                return a;
            }
            if (c.a(this.d) < 1.0d) {
                return new d(c.e(0.0d, this.d), 0.0d);
            }
            if (this.d >= 0.0d || (e2 & 1) != 1) {
                return b;
            }
            return c;
        }
    }

    private static double s(double x) {
        double x2 = x * x;
        return ((((((2.7553817452272217E-6d * x2) - 22521.49865654966d) * x2) + 0.008333333333329196d) * x2) - 26.666666666666668d) * x2 * x;
    }

    private static double r(double x) {
        double x2 = x * x;
        return ((((((2.479773539153719E-5d * x2) - 3231.288930800263d) * x2) + 0.041666666666621166d) * x2) - 8.000000000000002d) * x2;
    }

    private static double x(double xa, double xb) {
        int idx = (int) ((8.0d * xa) + 0.5d);
        double epsilon = xa - l[idx];
        double sintA = d[idx];
        double sintB = e[idx];
        double costA = f[idx];
        double costB = g[idx];
        double sinEpsA = epsilon;
        double sinEpsB = s(epsilon);
        double cosEpsB = r(epsilon);
        double temp = 1.073741824E9d * sinEpsA;
        double temp2 = (sinEpsA + temp) - temp;
        double sinEpsB2 = sinEpsB + (sinEpsA - temp2);
        double sinEpsA2 = temp2;
        double t = sintA;
        double c2 = 0.0d + t;
        int i2 = idx;
        double d2 = epsilon;
        double d3 = -((c2 - 0.0d) - t);
        double a2 = c2;
        double b2 = 0.0d + d3;
        double t2 = costA * sinEpsA2;
        double c3 = a2 + t2;
        double d4 = d3;
        double d5 = -((c3 - a2) - t2);
        double a3 = c3;
        double b3 = b2 + d5 + (sintA * cosEpsB) + (costA * sinEpsB2) + sintB + (costB * sinEpsA2) + (sintB * cosEpsB) + (costB * sinEpsB2);
        if (xb != 0.0d) {
            double t3 = (((costA + costB) * (cosEpsB + 1.0d)) - ((sintA + sintB) * (sinEpsA2 + sinEpsB2))) * xb;
            double c4 = a3 + t3;
            double d6 = d5;
            a3 = c4;
            b3 += -((c4 - a3) - t3);
        } else {
            double d7 = d5;
        }
        return a3 + b3;
    }

    private static double g(double xa, double xb) {
        double a2 = 1.5707963267948966d - xa;
        return x(a2, (-((a2 - 1.5707963267948966d) + xa)) + (6.123233995736766E-17d - xb));
    }

    private static void v(double x, double[] result) {
        long shpiB;
        long shpiA;
        long shpi0;
        long inbits = Double.doubleToRawLongBits(x);
        int exponent = ((int) ((inbits >> 52) & 2047)) + PlayerStateDefine.EC_NO_INIT + 1;
        long inbits2 = ((inbits & 4503599627370495L) | 4503599627370496L) << 11;
        int idx = exponent >> 6;
        int shift = exponent - (idx << 6);
        if (shift != 0) {
            long shpi02 = idx == 0 ? 0 : j[idx - 1] << shift;
            long[] jArr = j;
            shpi0 = shpi02 | (jArr[idx] >>> (64 - shift));
            shpiA = (jArr[idx] << shift) | (jArr[idx + 1] >>> (64 - shift));
            shpiB = (jArr[idx + 1] << shift) | (jArr[idx + 2] >>> (64 - shift));
        } else {
            shpi0 = idx == 0 ? 0 : j[idx - 1];
            long[] jArr2 = j;
            shpiA = jArr2[idx];
            shpiB = jArr2[idx + 1];
        }
        long a2 = inbits2 >>> 32;
        long b2 = inbits2 & 4294967295L;
        long c2 = shpiA >>> 32;
        long d2 = shpiA & 4294967295L;
        long bd = b2 * d2;
        long bc = b2 * c2;
        long ad = a2 * d2;
        long prodB = bd + (ad << 32);
        long prodA = (a2 * c2) + (ad >>> 32);
        boolean bita = (bd & Long.MIN_VALUE) != 0;
        boolean bitb = (ad & IjkMediaMeta.AV_CH_WIDE_LEFT) != 0;
        boolean bitsum = (prodB & Long.MIN_VALUE) != 0;
        if ((bita && bitb) || ((bita || bitb) && !bitsum)) {
            prodA++;
        }
        boolean bita2 = (prodB & Long.MIN_VALUE) != 0;
        boolean bitb2 = (bc & IjkMediaMeta.AV_CH_WIDE_LEFT) != 0;
        long prodB2 = prodB + (bc << 32);
        long prodA2 = prodA + (bc >>> 32);
        boolean bitsum2 = (prodB2 & Long.MIN_VALUE) != 0;
        if ((bita2 && bitb2) || ((bita2 || bitb2) && !bitsum2)) {
            prodA2++;
        }
        long c3 = shpiB >>> 32;
        long ac = (a2 * c3) + (((b2 * c3) + (a2 * (shpiB & 4294967295L))) >>> 32);
        boolean bita3 = (prodB2 & Long.MIN_VALUE) != 0;
        boolean bitb3 = (ac & Long.MIN_VALUE) != 0;
        long prodB3 = prodB2 + ac;
        boolean bitsum3 = (prodB3 & Long.MIN_VALUE) != 0;
        if ((bita3 && bitb3) || ((bita3 || bitb3) && !bitsum3)) {
            prodA2++;
        }
        long d3 = shpi0 & 4294967295L;
        long prodA3 = prodA2 + (b2 * d3) + (((b2 * (shpi0 >>> 32)) + (a2 * d3)) << 32);
        int i2 = idx;
        int intPart = (int) (prodA3 >>> 62);
        long prodA4 = (prodA3 << 2) | (prodB3 >>> 62);
        long prodB4 = prodB3 << 2;
        long a3 = prodA4 >>> 32;
        long b3 = prodA4 & 4294967295L;
        long[] jArr3 = k;
        long c4 = jArr3[0] >>> 32;
        long d4 = jArr3[0] & 4294967295L;
        long bd2 = b3 * d4;
        long bc2 = b3 * c4;
        long ad2 = a3 * d4;
        long prod2B = bd2 + (ad2 << 32);
        long prod2A = (a3 * c4) + (ad2 >>> 32);
        boolean bita4 = (bd2 & Long.MIN_VALUE) != 0;
        boolean bitb4 = (ad2 & IjkMediaMeta.AV_CH_WIDE_LEFT) != 0;
        boolean bitsum4 = (prod2B & Long.MIN_VALUE) != 0;
        if ((bita4 && bitb4) || ((bita4 || bitb4) && !bitsum4)) {
            prod2A++;
        }
        boolean bita5 = (prod2B & Long.MIN_VALUE) != 0;
        boolean bitb5 = (bc2 & IjkMediaMeta.AV_CH_WIDE_LEFT) != 0;
        long prod2B2 = prod2B + (bc2 << 32);
        long prod2A2 = prod2A + (bc2 >>> 32);
        boolean bitsum5 = (prod2B2 & Long.MIN_VALUE) != 0;
        if ((bita5 && bitb5) || ((bita5 || bitb5) && !bitsum5)) {
            prod2A2++;
        }
        long c5 = jArr3[1] >>> 32;
        long ac2 = (a3 * c5) + (((b3 * c5) + (a3 * (jArr3[1] & 4294967295L))) >>> 32);
        boolean bita6 = (prod2B2 & Long.MIN_VALUE) != 0;
        boolean bitb6 = (ac2 & Long.MIN_VALUE) != 0;
        long prod2B3 = prod2B2 + ac2;
        boolean bitsum6 = (prod2B3 & Long.MIN_VALUE) != 0;
        if ((bita6 && bitb6) || ((bita6 || bitb6) && !bitsum6)) {
            prod2A2++;
        }
        long a4 = prodB4 >>> 32;
        long c6 = jArr3[0] >>> 32;
        long ac3 = (a4 * c6) + ((((prodB4 & 4294967295L) * c6) + (a4 * (jArr3[0] & 4294967295L))) >>> 32);
        boolean bita7 = (prod2B3 & Long.MIN_VALUE) != 0;
        boolean bitb7 = (ac3 & Long.MIN_VALUE) != 0;
        long prod2B4 = prod2B3 + ac3;
        boolean bitsum7 = (prod2B4 & Long.MIN_VALUE) != 0;
        if ((bita7 && bitb7) || ((bita7 || bitb7) && !bitsum7)) {
            prod2A2++;
        }
        int i3 = shift;
        double tmpA = ((double) (prod2A2 >>> 12)) / 4.503599627370496E15d;
        long j2 = inbits2;
        double tmpB = (((double) (((prod2A2 & 4095) << 40) + (prod2B4 >>> 24))) / 4.503599627370496E15d) / 4.503599627370496E15d;
        double sumA = tmpA + tmpB;
        double d5 = tmpA;
        double d6 = tmpB;
        result[0] = (double) intPart;
        result[1] = sumA * 2.0d;
        result[2] = 2.0d * (-((sumA - tmpA) - tmpB));
    }

    public static double w(double x) {
        boolean negative = false;
        int quadrant = 0;
        double xb = 0.0d;
        double xa = x;
        if (x < 0.0d) {
            negative = true;
            xa = -xa;
        }
        if (xa == 0.0d) {
            if (Double.doubleToRawLongBits(x) < 0) {
                return -0.0d;
            }
            return 0.0d;
        } else if (xa != xa || xa == Double.POSITIVE_INFINITY) {
            return Double.NaN;
        } else {
            if (xa > 3294198.0d) {
                double[] reduceResults = new double[3];
                v(xa, reduceResults);
                quadrant = ((int) reduceResults[0]) & 3;
                xa = reduceResults[1];
                xb = reduceResults[2];
            } else if (xa > 1.5707963267948966d) {
                a cw = new a(xa);
                quadrant = cw.a() & 3;
                xa = cw.b();
                xb = cw.c();
            }
            if (negative) {
                quadrant ^= 2;
            }
            switch (quadrant) {
                case 0:
                    return x(xa, xb);
                case 1:
                    return g(xa, xb);
                case 2:
                    return -x(xa, xb);
                case 3:
                    return -g(xa, xb);
                default:
                    return Double.NaN;
            }
        }
    }

    public static double f(double x) {
        int quadrant = 0;
        double xa = x;
        if (x < 0.0d) {
            xa = -xa;
        }
        if (xa != xa || xa == Double.POSITIVE_INFINITY) {
            return Double.NaN;
        }
        double xb = 0.0d;
        if (xa > 3294198.0d) {
            double[] reduceResults = new double[3];
            v(xa, reduceResults);
            quadrant = ((int) reduceResults[0]) & 3;
            xa = reduceResults[1];
            xb = reduceResults[2];
        } else if (xa > 1.5707963267948966d) {
            a cw = new a(xa);
            quadrant = cw.a() & 3;
            xa = cw.b();
            xb = cw.c();
        }
        switch (quadrant) {
            case 0:
                return g(xa, xb);
            case 1:
                return -x(xa, xb);
            case 2:
                return -g(xa, xb);
            case 3:
                return x(xa, xb);
            default:
                return Double.NaN;
        }
    }

    private static double c(double xa, double xb, boolean leftPlane) {
        boolean negate;
        double xb2;
        int idx;
        int idx2;
        double ya;
        double denom;
        double xa2 = xa;
        if (xa2 == 0.0d) {
            return leftPlane ? e(3.141592653589793d, xa2) : xa2;
        }
        if (xa2 < 0.0d) {
            xa2 = -xa2;
            xb2 = -xb;
            negate = true;
        } else {
            xb2 = xb;
            negate = false;
        }
        if (xa2 > 1.633123935319537E16d) {
            return negate ^ leftPlane ? -1.5707963267948966d : 1.5707963267948966d;
        }
        if (xa2 < 1.0d) {
            idx = (int) ((((-1.7168146928204135d * xa2 * xa2) + 8.0d) * xa2) + 0.5d);
        } else {
            double oneOverXa = 1.0d / xa2;
            idx = (int) ((-(((-1.7168146928204135d * oneOverXa * oneOverXa) + 8.0d) * oneOverXa)) + 13.07d);
        }
        double ttA = h[idx];
        double ttB = i[idx];
        double epsA = xa2 - ttA;
        double epsB = (-((epsA - xa2) + ttA)) + (xb2 - ttB);
        double temp = epsA + epsB;
        double epsB2 = -((temp - epsA) - epsB);
        double epsA2 = temp;
        double temp2 = xa2 * 1.073741824E9d;
        double ya2 = (xa2 + temp2) - temp2;
        double xa3 = ya2;
        double xb3 = xb2 + ((xb2 + xa2) - ya2);
        if (idx == 0) {
            double denom2 = 1.0d / (((xa3 + xb3) * (ttA + ttB)) + 1.0d);
            ya = epsA2 * denom2;
            denom = denom2 * epsB2;
            double d2 = xa3;
            double d3 = xb3;
            idx2 = idx;
        } else {
            double temp22 = xa3 * ttA;
            double za = temp22 + 1.0d;
            idx2 = idx;
            double temp23 = (xb3 * ttA) + (xa3 * ttB);
            double temp3 = za + temp23;
            double d4 = xa3;
            double za2 = temp3;
            ya = epsA2 / za2;
            double temp4 = ya * 1.073741824E9d;
            double yaa = (ya + temp4) - temp4;
            double yab = ya - yaa;
            double temp5 = za2 * 1.073741824E9d;
            double zaa = (za2 + temp5) - temp5;
            double zab = za2 - zaa;
            double yb = xb3;
            denom = (((((epsA2 - (yaa * zaa)) - (yaa * zab)) - (yab * zaa)) - (yab * zab)) / za2) + ((((-epsA2) * (((-((za - 1.0d) - temp22)) + (-((temp3 - za) - temp23))) + (xb3 * ttB))) / za2) / za2) + (epsB2 / za2);
        }
        double epsA3 = ya;
        double epsB3 = denom;
        double epsB4 = epsA3 * epsA3;
        double yb2 = ((((((((((0.07490822288864472d * epsB4) - 0.09088450866185192d) * epsB4) + 0.11111095942313305d) * epsB4) - 0.1428571423679182d) * epsB4) + 0.19999999999923582d) * epsB4) - 0.33333333333333287d) * epsB4 * epsA3;
        double ya3 = epsA3;
        double temp6 = ya3 + yb2;
        double d5 = epsB4;
        double ya4 = temp6;
        double yb3 = (-((temp6 - ya3) - yb2)) + (epsB3 / ((epsA3 * epsA3) + 1.0d));
        double eighths = l[idx2];
        double za3 = eighths + ya4;
        double d6 = epsA3;
        double temp7 = za3 + yb3;
        double d7 = epsB3;
        double zb = (-((za3 - eighths) - ya4)) + (-((temp7 - za3) - yb3));
        double za4 = temp7;
        double result = za4 + zb;
        if (leftPlane) {
            double d8 = za4;
            double za5 = 3.141592653589793d - result;
            double d9 = zb;
            result = za5 + (-((za5 - 3.141592653589793d) + result)) + (1.2246467991473532E-16d - (-((result - za4) - zb)));
            double resultb = za5;
        } else {
            double d10 = zb;
            double d11 = za4;
        }
        if (negate ^ leftPlane) {
            return -result;
        }
        return result;
    }

    public static double d(double y, double x) {
        double d2 = y;
        if (x != x || d2 != d2) {
            return Double.NaN;
        }
        if (d2 == 0.0d) {
            double result = x * d2;
            double invx = 1.0d / x;
            double invy = 1.0d / d2;
            if (invx == 0.0d) {
                if (x > 0.0d) {
                    return d2;
                }
                return e(3.141592653589793d, d2);
            } else if (x < 0.0d || invx < 0.0d) {
                return (d2 < 0.0d || invy < 0.0d) ? -3.141592653589793d : 3.141592653589793d;
            } else {
                return result;
            }
        } else if (d2 == Double.POSITIVE_INFINITY) {
            if (x == Double.POSITIVE_INFINITY) {
                return 0.7853981633974483d;
            }
            if (x == Double.NEGATIVE_INFINITY) {
                return 2.356194490192345d;
            }
            return 1.5707963267948966d;
        } else if (d2 != Double.NEGATIVE_INFINITY) {
            if (x == Double.POSITIVE_INFINITY) {
                if (d2 > 0.0d || 1.0d / d2 > 0.0d) {
                    return 0.0d;
                }
                if (d2 < 0.0d || 1.0d / d2 < 0.0d) {
                    return -0.0d;
                }
            }
            if (x == Double.NEGATIVE_INFINITY) {
                if (d2 > 0.0d || 1.0d / d2 > 0.0d) {
                    return 3.141592653589793d;
                }
                if (d2 < 0.0d || 1.0d / d2 < 0.0d) {
                    return -3.141592653589793d;
                }
            }
            if (x == 0.0d) {
                if (d2 > 0.0d || 1.0d / d2 > 0.0d) {
                    return 1.5707963267948966d;
                }
                if (d2 < 0.0d || 1.0d / d2 < 0.0d) {
                    return -1.5707963267948966d;
                }
            }
            double r = d2 / x;
            if (Double.isInfinite(r)) {
                return c(r, 0.0d, x < 0.0d);
            }
            double ra = i(r);
            double rb = r - ra;
            double xa = i(x);
            double xb = x - xa;
            double rb2 = rb + (((((d2 - (ra * xa)) - (ra * xb)) - (rb * xa)) - (rb * xb)) / x);
            double temp = ra + rb2;
            double rb3 = -((temp - ra) - rb2);
            double ra2 = temp;
            if (ra2 == 0.0d) {
                ra2 = e(0.0d, d2);
            }
            return c(ra2, rb3, x < 0.0d);
        } else if (x == Double.POSITIVE_INFINITY) {
            return -0.7853981633974483d;
        } else {
            if (x == Double.NEGATIVE_INFINITY) {
                return -2.356194490192345d;
            }
            return -1.5707963267948966d;
        }
    }

    public static long b(long x) {
        long l2 = x >>> 63;
        return (((~l2) + 1) ^ x) + l2;
    }

    public static double a(double x) {
        return Double.longBitsToDouble(Double.doubleToRawLongBits(x) & DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
    }

    public static int q(int a2, int b2) {
        return a2 <= b2 ? a2 : b2;
    }

    public static int p(int a2, int b2) {
        return a2 <= b2 ? b2 : a2;
    }

    public static double o(double a2, double b2) {
        if (a2 > b2) {
            return a2;
        }
        if (a2 < b2) {
            return b2;
        }
        if (a2 != b2) {
            return Double.NaN;
        }
        if (Double.doubleToRawLongBits(a2) == Long.MIN_VALUE) {
            return b2;
        }
        return a2;
    }

    public static double e(double magnitude, double sign) {
        if ((Double.doubleToRawLongBits(magnitude) ^ Double.doubleToRawLongBits(sign)) >= 0) {
            return magnitude;
        }
        return -magnitude;
    }

    /* compiled from: FastMath */
    public static class a {
        private final int a;
        private final double b;
        private final double c;

        a(double xa) {
            int k = (int) (0.6366197723675814d * xa);
            while (true) {
                double a2 = ((double) (-k)) * 1.570796251296997d;
                double remA = xa + a2;
                double a3 = ((double) (-k)) * 7.549789948768648E-8d;
                double a4 = remA;
                double remA2 = a3 + a4;
                double remB = (-((remA - xa) - a2)) + (-((remA2 - a4) - a3));
                double a5 = ((double) (-k)) * 6.123233995736766E-17d;
                double b2 = remA2;
                double remA3 = a5 + b2;
                double remB2 = remB + (-((remA3 - b2) - a5));
                if (remA3 > 0.0d) {
                    this.a = k;
                    this.b = remA3;
                    this.c = remB2;
                    return;
                }
                k--;
            }
        }

        /* access modifiers changed from: package-private */
        public int a() {
            return this.a;
        }

        /* access modifiers changed from: package-private */
        public double b() {
            return this.b;
        }

        /* access modifiers changed from: package-private */
        public double c() {
            return this.c;
        }
    }
}
