package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.params.GOST3410Parameters;
import org.spongycastle.crypto.params.GOST3410ValidationParameters;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class GOST3410ParametersGenerator {
    private static final BigInteger a = BigInteger.valueOf(1);
    private static final BigInteger b = BigInteger.valueOf(2);
    private int c;
    private int d;
    private SecureRandom e;

    public void b(int size, int typeproc, SecureRandom random) {
        this.c = size;
        this.d = typeproc;
        this.e = random;
    }

    private int c(int x0, int c2, BigInteger[] pq, int size) {
        BigInteger[] y;
        int x02;
        BigInteger C;
        BigInteger constA16;
        int c3;
        int[] t;
        int x03 = x0;
        while (true) {
            if (x03 >= 0 && x03 <= 65536) {
                break;
            }
            x03 = this.e.nextInt() / 32768;
        }
        int c4 = c2;
        while (true) {
            if (c4 >= 0 && c4 <= 65536 && c4 / 2 != 0) {
                break;
            }
            int i = c4;
            c4 = 1 + (this.e.nextInt() / 32768);
            x03 = x03;
        }
        BigInteger C2 = new BigInteger(Integer.toString(c4));
        BigInteger constA162 = new BigInteger("19381");
        int i2 = 0;
        BigInteger[] y2 = {new BigInteger(Integer.toString(x03))};
        int[] t2 = {size};
        int s = 0;
        for (int i3 = 0; t2[i3] >= 17; i3++) {
            int[] tmp_t = new int[(t2.length + 1)];
            System.arraycopy(t2, 0, tmp_t, 0, t2.length);
            t2 = new int[tmp_t.length];
            System.arraycopy(tmp_t, 0, t2, 0, tmp_t.length);
            t2[i3 + 1] = t2[i3] / 2;
            s = i3 + 1;
        }
        BigInteger[] p = new BigInteger[(s + 1)];
        int i4 = 16;
        p[s] = new BigInteger("8003", 16);
        int m = s - 1;
        int i5 = 0;
        while (i5 < s) {
            int rm = t2[m] / i4;
            while (true) {
                BigInteger[] tmp_y = new BigInteger[y2.length];
                System.arraycopy(y2, i2, tmp_y, i2, y2.length);
                y = new BigInteger[(rm + 1)];
                System.arraycopy(tmp_y, i2, y, i2, tmp_y.length);
                int j = 0;
                while (j < rm) {
                    y[j + 1] = y[j].multiply(constA162).add(C2).mod(b.pow(i4));
                    j++;
                    x03 = x03;
                }
                x02 = x03;
                BigInteger Ym = new BigInteger("0");
                int j2 = 0;
                while (j2 < rm) {
                    Ym = Ym.add(y[j2].multiply(b.pow(j2 * 16)));
                    j2++;
                    C2 = C2;
                }
                C = C2;
                y[0] = y[rm];
                BigInteger bigInteger = b;
                BigInteger bigInteger2 = Ym;
                BigInteger N = bigInteger.pow(t2[m] - 1).divide(p[m + 1]).add(bigInteger.pow(t2[m] - 1).multiply(Ym).divide(p[m + 1].multiply(bigInteger.pow(rm * 16))));
                BigInteger mod = N.mod(bigInteger);
                BigInteger bigInteger3 = a;
                if (mod.compareTo(bigInteger3) == 0) {
                    N = N.add(bigInteger3);
                }
                int k = 0;
                while (true) {
                    constA16 = constA162;
                    int c5 = c4;
                    BigInteger multiply = p[m + 1].multiply(N.add(BigInteger.valueOf((long) k)));
                    BigInteger bigInteger4 = a;
                    p[m] = multiply.add(bigInteger4);
                    BigInteger bigInteger5 = p[m];
                    BigInteger bigInteger6 = b;
                    c3 = c5;
                    if (bigInteger5.compareTo(bigInteger6.pow(t2[m])) != 1) {
                        t = t2;
                        if (bigInteger6.modPow(p[m + 1].multiply(N.add(BigInteger.valueOf((long) k))), p[m]).compareTo(bigInteger4) == 0 && bigInteger6.modPow(N.add(BigInteger.valueOf((long) k)), p[m]).compareTo(bigInteger4) != 0) {
                            break;
                        }
                        k += 2;
                        constA162 = constA16;
                        c4 = c3;
                        t2 = t;
                    } else {
                        break;
                    }
                }
                y2 = y;
                constA162 = constA16;
                x03 = x02;
                C2 = C;
                c4 = c3;
                i2 = 0;
                i4 = 16;
            }
            m--;
            if (m >= 0) {
                i5++;
                y2 = y;
                constA162 = constA16;
                x03 = x02;
                C2 = C;
                c4 = c3;
                t2 = t;
                i2 = 0;
                i4 = 16;
            } else {
                pq[0] = p[0];
                pq[1] = p[1];
                return y[0].intValue();
            }
        }
        return y2[0].intValue();
    }

    private long d(long x0, long c2, BigInteger[] pq, int size) {
        long x02;
        BigInteger[] y;
        BigInteger C;
        BigInteger constA32;
        long c3;
        long x03 = x0;
        while (true) {
            if (x03 >= 0 && x03 <= IjkMediaMeta.AV_CH_WIDE_RIGHT) {
                break;
            }
            x03 = (long) (this.e.nextInt() * 2);
        }
        long c4 = c2;
        while (true) {
            if (c4 >= 0 && c4 <= IjkMediaMeta.AV_CH_WIDE_RIGHT && c4 / 2 != 0) {
                break;
            }
            long j = c4;
            c4 = (long) ((this.e.nextInt() * 2) + 1);
            x03 = x03;
        }
        BigInteger C2 = new BigInteger(Long.toString(c4));
        BigInteger constA322 = new BigInteger("97781173");
        int i = 0;
        BigInteger[] y2 = {new BigInteger(Long.toString(x03))};
        int[] t = {size};
        int s = 0;
        for (int i2 = 0; t[i2] >= 33; i2++) {
            int[] tmp_t = new int[(t.length + 1)];
            System.arraycopy(t, 0, tmp_t, 0, t.length);
            t = new int[tmp_t.length];
            System.arraycopy(tmp_t, 0, t, 0, tmp_t.length);
            t[i2 + 1] = t[i2] / 2;
            s = i2 + 1;
        }
        BigInteger[] p = new BigInteger[(s + 1)];
        p[s] = new BigInteger("8000000B", 16);
        int m = s - 1;
        int i3 = 0;
        while (i3 < s) {
            int rm = t[m] / 32;
            while (true) {
                BigInteger[] tmp_y = new BigInteger[y2.length];
                x02 = x03;
                System.arraycopy(y2, i, tmp_y, i, y2.length);
                y = new BigInteger[(rm + 1)];
                System.arraycopy(tmp_y, i, y, i, tmp_y.length);
                int j2 = 0;
                while (j2 < rm) {
                    y[j2 + 1] = y[j2].multiply(constA322).add(C2).mod(b.pow(32));
                    j2++;
                    C2 = C2;
                    constA322 = constA322;
                }
                C = C2;
                constA32 = constA322;
                BigInteger Ym = new BigInteger("0");
                for (int j3 = 0; j3 < rm; j3++) {
                    Ym = Ym.add(y[j3].multiply(b.pow(j3 * 32)));
                }
                y[0] = y[rm];
                BigInteger bigInteger = b;
                BigInteger bigInteger2 = Ym;
                BigInteger N = bigInteger.pow(t[m] - 1).divide(p[m + 1]).add(bigInteger.pow(t[m] - 1).multiply(Ym).divide(p[m + 1].multiply(bigInteger.pow(rm * 32))));
                BigInteger mod = N.mod(bigInteger);
                BigInteger bigInteger3 = a;
                if (mod.compareTo(bigInteger3) == 0) {
                    N = N.add(bigInteger3);
                }
                int k = 0;
                while (true) {
                    c3 = c4;
                    BigInteger multiply = p[m + 1].multiply(N.add(BigInteger.valueOf((long) k)));
                    BigInteger bigInteger4 = a;
                    p[m] = multiply.add(bigInteger4);
                    BigInteger bigInteger5 = p[m];
                    BigInteger bigInteger6 = b;
                    if (bigInteger5.compareTo(bigInteger6.pow(t[m])) != 1) {
                        if (bigInteger6.modPow(p[m + 1].multiply(N.add(BigInteger.valueOf((long) k))), p[m]).compareTo(bigInteger4) == 0 && bigInteger6.modPow(N.add(BigInteger.valueOf((long) k)), p[m]).compareTo(bigInteger4) != 0) {
                            break;
                        }
                        k += 2;
                        c4 = c3;
                    } else {
                        break;
                    }
                }
                C2 = C;
                y2 = y;
                x03 = x02;
                constA322 = constA32;
                c4 = c3;
                i = 0;
            }
            m--;
            if (m >= 0) {
                i3++;
                C2 = C;
                y2 = y;
                x03 = x02;
                constA322 = constA32;
                c4 = c3;
                i = 0;
            } else {
                pq[0] = p[0];
                pq[1] = p[1];
                return y[0].longValue();
            }
        }
        return y2[0].longValue();
    }

    private void e(int x0, int c2, BigInteger[] pq) {
        int i;
        BigInteger N;
        BigInteger[] qp;
        int x02 = x0;
        while (true) {
            if (x02 >= 0 && x02 <= 65536) {
                break;
            }
            x02 = this.e.nextInt() / 32768;
        }
        int c3 = c2;
        while (true) {
            if (c3 >= 0 && c3 <= 65536 && c3 / 2 != 0) {
                break;
            }
            c3 = (this.e.nextInt() / 32768) + 1;
        }
        BigInteger[] qp2 = new BigInteger[2];
        BigInteger C = new BigInteger(Integer.toString(c3));
        BigInteger constA16 = new BigInteger("19381");
        int x03 = c(x02, c3, qp2, 256);
        BigInteger q = qp2[0];
        int x04 = c(x03, c3, qp2, 512);
        BigInteger Q = qp2[0];
        BigInteger[] y = new BigInteger[65];
        y[0] = new BigInteger(Integer.toString(x04));
        while (true) {
            int j = 0;
            while (true) {
                if (j >= 64) {
                    break;
                }
                y[j + 1] = y[j].multiply(constA16).add(C).mod(b.pow(16));
                j++;
            }
            int j2 = 0;
            BigInteger Y = new BigInteger("0");
            for (i = 64; j2 < i; i = 64) {
                Y = Y.add(y[j2].multiply(b.pow(j2 * 16)));
                j2++;
            }
            y[0] = y[64];
            BigInteger bigInteger = b;
            int c4 = c3;
            BigInteger N2 = bigInteger.pow(1024 - 1).divide(q.multiply(Q)).add(bigInteger.pow(1024 - 1).multiply(Y).divide(q.multiply(Q).multiply(bigInteger.pow(1024))));
            BigInteger mod = N2.mod(bigInteger);
            BigInteger bigInteger2 = a;
            if (mod.compareTo(bigInteger2) == 0) {
                N = N2.add(bigInteger2);
            } else {
                N = N2;
            }
            int k = 0;
            while (true) {
                BigInteger Y2 = Y;
                qp = qp2;
                BigInteger multiply = q.multiply(Q).multiply(N.add(BigInteger.valueOf((long) k)));
                BigInteger bigInteger3 = a;
                BigInteger p = multiply.add(bigInteger3);
                BigInteger bigInteger4 = b;
                if (p.compareTo(bigInteger4.pow(1024)) == 1) {
                    break;
                }
                BigInteger C2 = C;
                BigInteger constA162 = constA16;
                if (bigInteger4.modPow(q.multiply(Q).multiply(N.add(BigInteger.valueOf((long) k))), p).compareTo(bigInteger3) != 0 || bigInteger4.modPow(q.multiply(N.add(BigInteger.valueOf((long) k))), p).compareTo(bigInteger3) == 0) {
                    k += 2;
                    C = C2;
                    qp2 = qp;
                    Y = Y2;
                    constA16 = constA162;
                } else {
                    pq[0] = p;
                    pq[1] = q;
                    return;
                }
            }
            qp2 = qp;
            c3 = c4;
        }
    }

    private void f(long x0, long c2, BigInteger[] pq) {
        BigInteger N;
        BigInteger p;
        BigInteger[] y;
        long x02 = x0;
        while (true) {
            if (x02 >= 0 && x02 <= IjkMediaMeta.AV_CH_WIDE_RIGHT) {
                break;
            }
            x02 = (long) (this.e.nextInt() * 2);
        }
        long c3 = c2;
        while (true) {
            if (c3 >= 0 && c3 <= IjkMediaMeta.AV_CH_WIDE_RIGHT && c3 / 2 != 0) {
                break;
            }
            long j = c3;
            c3 = (long) ((this.e.nextInt() * 2) + 1);
            x02 = x02;
        }
        BigInteger[] qp = new BigInteger[2];
        BigInteger C = new BigInteger(Long.toString(c3));
        long j2 = x02;
        long j3 = c3;
        BigInteger constA32 = new BigInteger("97781173");
        BigInteger[] bigIntegerArr = qp;
        long j4 = x02;
        BigInteger C2 = C;
        long x03 = d(j2, j3, bigIntegerArr, 256);
        BigInteger q = qp[0];
        long x04 = d(x03, j3, bigIntegerArr, 512);
        BigInteger Q = qp[0];
        BigInteger[] y2 = new BigInteger[33];
        y2[0] = new BigInteger(Long.toString(x04));
        while (true) {
            for (int j5 = 0; j5 < 32; j5++) {
                y2[j5 + 1] = y2[j5].multiply(constA32).add(C2).mod(b.pow(32));
            }
            BigInteger Y = new BigInteger("0");
            for (int j6 = 0; j6 < 32; j6++) {
                Y = Y.add(y2[j6].multiply(b.pow(j6 * 32)));
            }
            y2[0] = y2[32];
            BigInteger bigInteger = b;
            BigInteger N2 = bigInteger.pow(1024 - 1).divide(q.multiply(Q)).add(bigInteger.pow(1024 - 1).multiply(Y).divide(q.multiply(Q).multiply(bigInteger.pow(1024))));
            BigInteger mod = N2.mod(bigInteger);
            BigInteger bigInteger2 = a;
            if (mod.compareTo(bigInteger2) == 0) {
                N = N2.add(bigInteger2);
            } else {
                N = N2;
            }
            int k = 0;
            while (true) {
                BigInteger multiply = q.multiply(Q).multiply(N.add(BigInteger.valueOf((long) k)));
                BigInteger bigInteger3 = a;
                p = multiply.add(bigInteger3);
                BigInteger bigInteger4 = b;
                BigInteger Y2 = Y;
                y = y2;
                if (p.compareTo(bigInteger4.pow(1024)) == 1) {
                    break;
                }
                long c4 = c3;
                if (bigInteger4.modPow(q.multiply(Q).multiply(N.add(BigInteger.valueOf((long) k))), p).compareTo(bigInteger3) != 0 || bigInteger4.modPow(q.multiply(N.add(BigInteger.valueOf((long) k))), p).compareTo(bigInteger3) == 0) {
                    k += 2;
                    c3 = c4;
                    Y = Y2;
                    y2 = y;
                    BigInteger Y3 = p;
                } else {
                    pq[0] = p;
                    pq[1] = q;
                    return;
                }
            }
            BigInteger Y4 = p;
            y2 = y;
        }
    }

    private BigInteger g(BigInteger p, BigInteger q) {
        BigInteger pSub1 = p.subtract(a);
        BigInteger pSub1DivQ = pSub1.divide(q);
        int length = p.bitLength();
        while (true) {
            BigInteger d2 = new BigInteger(length, this.e);
            BigInteger bigInteger = a;
            if (d2.compareTo(bigInteger) > 0 && d2.compareTo(pSub1) < 0) {
                BigInteger a2 = d2.modPow(pSub1DivQ, p);
                if (a2.compareTo(bigInteger) != 0) {
                    return a2;
                }
            }
        }
    }

    public GOST3410Parameters a() {
        long cL;
        BigInteger[] pq = new BigInteger[2];
        if (this.d == 1) {
            int x0 = this.e.nextInt();
            int c2 = this.e.nextInt();
            switch (this.c) {
                case 512:
                    c(x0, c2, pq, 512);
                    break;
                case 1024:
                    e(x0, c2, pq);
                    break;
                default:
                    throw new IllegalArgumentException("Ooops! key size 512 or 1024 bit.");
            }
            BigInteger p = pq[0];
            BigInteger q = pq[1];
            return new GOST3410Parameters(p, q, g(p, q), new GOST3410ValidationParameters(x0, c2));
        }
        long x0L = this.e.nextLong();
        long cL2 = this.e.nextLong();
        switch (this.c) {
            case 512:
                cL = cL2;
                d(x0L, cL, pq, 512);
                break;
            case 1024:
                cL = cL2;
                f(x0L, cL2, pq);
                break;
            default:
                throw new IllegalStateException("Ooops! key size 512 or 1024 bit.");
        }
        BigInteger p2 = pq[0];
        BigInteger q2 = pq[1];
        return new GOST3410Parameters(p2, q2, g(p2, q2), new GOST3410ValidationParameters(x0L, cL));
    }
}
