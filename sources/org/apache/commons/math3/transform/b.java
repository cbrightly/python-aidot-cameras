package org.apache.commons.math3.transform;

import java.io.Serializable;
import java.lang.reflect.Array;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.MathIllegalStateException;
import org.apache.commons.math3.exception.util.d;
import org.apache.commons.math3.util.c;
import org.apache.commons.math3.util.e;

/* compiled from: FastFourierTransformer */
public class b implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final double[] c = {1.0d, -1.0d, 6.123233995736766E-17d, 0.7071067811865476d, 0.9238795325112867d, 0.9807852804032304d, 0.9951847266721969d, 0.9987954562051724d, 0.9996988186962042d, 0.9999247018391445d, 0.9999811752826011d, 0.9999952938095762d, 0.9999988234517019d, 0.9999997058628822d, 0.9999999264657179d, 0.9999999816164293d, 0.9999999954041073d, 0.9999999988510269d, 0.9999999997127567d, 0.9999999999281892d, 0.9999999999820472d, 0.9999999999955118d, 0.999999999998878d, 0.9999999999997194d, 0.9999999999999298d, 0.9999999999999825d, 0.9999999999999957d, 0.9999999999999989d, 0.9999999999999998d, 0.9999999999999999d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d};
    private static final double[] d = {2.4492935982947064E-16d, -1.2246467991473532E-16d, -1.0d, -0.7071067811865475d, -0.3826834323650898d, -0.19509032201612825d, -0.0980171403295606d, -0.049067674327418015d, -0.024541228522912288d, -0.012271538285719925d, -0.006135884649154475d, -0.003067956762965976d, -0.0015339801862847655d, -7.669903187427045E-4d, -3.8349518757139556E-4d, -1.917475973107033E-4d, -9.587379909597734E-5d, -4.793689960306688E-5d, -2.396844980841822E-5d, -1.1984224905069705E-5d, -5.9921124526424275E-6d, -2.996056226334661E-6d, -1.4980281131690111E-6d, -7.490140565847157E-7d, -3.7450702829238413E-7d, -1.8725351414619535E-7d, -9.362675707309808E-8d, -4.681337853654909E-8d, -2.340668926827455E-8d, -1.1703344634137277E-8d, -5.8516723170686385E-9d, -2.9258361585343192E-9d, -1.4629180792671596E-9d, -7.314590396335798E-10d, -3.657295198167899E-10d, -1.8286475990839495E-10d, -9.143237995419748E-11d, -4.571618997709874E-11d, -2.285809498854937E-11d, -1.1429047494274685E-11d, -5.714523747137342E-12d, -2.857261873568671E-12d, -1.4286309367843356E-12d, -7.143154683921678E-13d, -3.571577341960839E-13d, -1.7857886709804195E-13d, -8.928943354902097E-14d, -4.4644716774510487E-14d, -2.2322358387255243E-14d, -1.1161179193627622E-14d, -5.580589596813811E-15d, -2.7902947984069054E-15d, -1.3951473992034527E-15d, -6.975736996017264E-16d, -3.487868498008632E-16d, -1.743934249004316E-16d, -8.71967124502158E-17d, -4.35983562251079E-17d, -2.179917811255395E-17d, -1.0899589056276974E-17d, -5.449794528138487E-18d, -2.7248972640692436E-18d, -1.3624486320346218E-18d};
    static final long serialVersionUID = 20120210;
    private final a normalization;

    static {
        Class<b> cls = b.class;
    }

    public b(a normalization2) {
        this.normalization = normalization2;
    }

    private static void a(double[] a2, double[] b) {
        int n = a2.length;
        if (b.length == n) {
            int halfOfN = n >> 1;
            int j = 0;
            for (int i = 0; i < n; i++) {
                if (i < j) {
                    double temp = a2[i];
                    a2[i] = a2[j];
                    a2[j] = temp;
                    double temp2 = b[i];
                    b[i] = b[j];
                    b[j] = temp2;
                }
                int k = halfOfN;
                while (k <= j && k > 0) {
                    j -= k;
                    k >>= 1;
                }
                j += k;
            }
            return;
        }
        throw new AssertionError();
    }

    private static void c(double[][] dataRI, a normalization2, c type) {
        double[] dataR = dataRI[0];
        double[] dataI = dataRI[1];
        int n = dataR.length;
        if (dataI.length == n) {
            switch (a.a[normalization2.ordinal()]) {
                case 1:
                    if (type == c.INVERSE) {
                        double scaleFactor = 1.0d / ((double) n);
                        for (int i = 0; i < n; i++) {
                            dataR[i] = dataR[i] * scaleFactor;
                            dataI[i] = dataI[i] * scaleFactor;
                        }
                        return;
                    }
                    return;
                case 2:
                    double scaleFactor2 = 1.0d / c.z((double) n);
                    for (int i2 = 0; i2 < n; i2++) {
                        dataR[i2] = dataR[i2] * scaleFactor2;
                        dataI[i2] = dataI[i2] * scaleFactor2;
                    }
                    return;
                default:
                    throw new MathIllegalStateException();
            }
        } else {
            throw new AssertionError();
        }
    }

    /* compiled from: FastFourierTransformer */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[a.values().length];
            a = iArr;
            try {
                iArr[a.STANDARD.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[a.UNITARY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public static void transformInPlace(double[][] dataRI, a normalization2, c type) {
        double[][] dArr = dataRI;
        c cVar = type;
        if (dArr.length == 2) {
            double[] dataR = dArr[0];
            double[] dataI = dArr[1];
            if (dataR.length == dataI.length) {
                int n = dataR.length;
                if (!org.apache.commons.math3.util.a.a((long) n)) {
                    throw new MathIllegalArgumentException(d.NOT_POWER_OF_TWO_CONSIDER_PADDING, Integer.valueOf(n));
                } else if (n != 1) {
                    if (n == 2) {
                        double srcR0 = dataR[0];
                        double srcI0 = dataI[0];
                        double srcR1 = dataR[1];
                        double srcI1 = dataI[1];
                        dataR[0] = srcR0 + srcR1;
                        dataI[0] = srcI0 + srcI1;
                        dataR[1] = srcR0 - srcR1;
                        dataI[1] = srcI0 - srcI1;
                        c(dataRI, normalization2, type);
                        return;
                    }
                    a(dataR, dataI);
                    if (cVar == c.INVERSE) {
                        for (int i0 = 0; i0 < n; i0 += 4) {
                            int i1 = i0 + 1;
                            int i2 = i0 + 2;
                            int i3 = i0 + 3;
                            double srcR02 = dataR[i0];
                            double srcI02 = dataI[i0];
                            double srcR12 = dataR[i2];
                            double srcI12 = dataI[i2];
                            double srcR2 = dataR[i1];
                            double srcI2 = dataI[i1];
                            double srcR3 = dataR[i3];
                            double srcI3 = dataI[i3];
                            dataR[i0] = srcR02 + srcR12 + srcR2 + srcR3;
                            dataI[i0] = srcI02 + srcI12 + srcI2 + srcI3;
                            dataR[i1] = (srcR02 - srcR2) + (srcI3 - srcI12);
                            dataI[i1] = (srcI02 - srcI2) + (srcR12 - srcR3);
                            dataR[i2] = ((srcR02 - srcR12) + srcR2) - srcR3;
                            dataI[i2] = ((srcI02 - srcI12) + srcI2) - srcI3;
                            dataR[i3] = (srcR02 - srcR2) + (srcI12 - srcI3);
                            dataI[i3] = (srcI02 - srcI2) + (srcR3 - srcR12);
                        }
                    } else {
                        for (int i02 = 0; i02 < n; i02 += 4) {
                            int i12 = i02 + 1;
                            int i22 = i02 + 2;
                            int i32 = i02 + 3;
                            double srcR03 = dataR[i02];
                            double srcI03 = dataI[i02];
                            double srcR13 = dataR[i22];
                            double srcI13 = dataI[i22];
                            double srcR22 = dataR[i12];
                            double srcI22 = dataI[i12];
                            double srcR32 = dataR[i32];
                            double srcI32 = dataI[i32];
                            dataR[i02] = srcR03 + srcR13 + srcR22 + srcR32;
                            dataI[i02] = srcI03 + srcI13 + srcI22 + srcI32;
                            dataR[i12] = (srcR03 - srcR22) + (srcI13 - srcI32);
                            dataI[i12] = (srcI03 - srcI22) + (srcR32 - srcR13);
                            dataR[i22] = ((srcR03 - srcR13) + srcR22) - srcR32;
                            dataI[i22] = ((srcI03 - srcI13) + srcI22) - srcI32;
                            dataR[i32] = (srcR03 - srcR22) + (srcI32 - srcI13);
                            dataI[i32] = (srcI03 - srcI22) + (srcR13 - srcR32);
                        }
                    }
                    int lastN0 = 4;
                    int lastLogN0 = 2;
                    while (lastN0 < n) {
                        int n0 = lastN0 << 1;
                        int logN0 = lastLogN0 + 1;
                        double wSubN0R = c[logN0];
                        double wSubN0I = d[logN0];
                        if (cVar == c.INVERSE) {
                            wSubN0I = -wSubN0I;
                        }
                        int destEvenStartIndex = 0;
                        while (destEvenStartIndex < n) {
                            int destOddStartIndex = destEvenStartIndex + lastN0;
                            double wSubN0ToRR = 1.0d;
                            double wSubN0ToRI = 0.0d;
                            for (int r = 0; r < lastN0; r++) {
                                double grR = dataR[destEvenStartIndex + r];
                                double grI = dataI[destEvenStartIndex + r];
                                double hrR = dataR[destOddStartIndex + r];
                                double hrI = dataI[destOddStartIndex + r];
                                dataR[destEvenStartIndex + r] = (grR + (wSubN0ToRR * hrR)) - (wSubN0ToRI * hrI);
                                dataI[destEvenStartIndex + r] = grI + (wSubN0ToRR * hrI) + (wSubN0ToRI * hrR);
                                dataR[destOddStartIndex + r] = grR - ((wSubN0ToRR * hrR) - (wSubN0ToRI * hrI));
                                dataI[destOddStartIndex + r] = grI - ((wSubN0ToRR * hrI) + (wSubN0ToRI * hrR));
                                wSubN0ToRR = (wSubN0ToRR * wSubN0R) - (wSubN0ToRI * wSubN0I);
                                wSubN0ToRI = (wSubN0ToRR * wSubN0I) + (wSubN0ToRI * wSubN0R);
                            }
                            destEvenStartIndex += n0;
                            c cVar2 = type;
                        }
                        lastN0 = n0;
                        lastLogN0 = logN0;
                        cVar = type;
                    }
                    c(dataRI, normalization2, type);
                }
            } else {
                throw new DimensionMismatchException(dataI.length, dataR.length);
            }
        } else {
            throw new DimensionMismatchException(dArr.length, 2);
        }
    }

    public org.apache.commons.math3.complex.a[] transform(double[] f, c type) {
        double[][] dataRI = {e.a(f, f.length), new double[f.length]};
        transformInPlace(dataRI, this.normalization, type);
        return d.a(dataRI);
    }

    public org.apache.commons.math3.complex.a[] transform(org.apache.commons.math3.analysis.c f, double min, double max, int n, c type) {
        return transform(org.apache.commons.math3.analysis.b.b(f, min, max, n), type);
    }

    public org.apache.commons.math3.complex.a[] transform(org.apache.commons.math3.complex.a[] f, c type) {
        double[][] dataRI = d.b(f);
        transformInPlace(dataRI, this.normalization, type);
        return d.a(dataRI);
    }

    @Deprecated
    public Object mdfft(Object mdca, c type) {
        C0486b mdcm = (C0486b) new C0486b(mdca).clone();
        int[] dimensionSize = mdcm.d();
        for (int i = 0; i < dimensionSize.length; i++) {
            b(mdcm, type, i, new int[0]);
        }
        return mdcm.c();
    }

    @Deprecated
    private void b(C0486b mdcm, c type, int d2, int[] subVector) {
        int[] dimensionSize = mdcm.d();
        if (subVector.length == dimensionSize.length) {
            org.apache.commons.math3.complex.a[] temp = new org.apache.commons.math3.complex.a[dimensionSize[d2]];
            for (int i = 0; i < dimensionSize[d2]; i++) {
                subVector[d2] = i;
                temp[i] = mdcm.b(subVector);
            }
            org.apache.commons.math3.complex.a[] temp2 = transform(temp, type);
            for (int i2 = 0; i2 < dimensionSize[d2]; i2++) {
                subVector[d2] = i2;
                mdcm.e(temp2[i2], subVector);
            }
            return;
        }
        int[] vector = new int[(subVector.length + 1)];
        System.arraycopy(subVector, 0, vector, 0, subVector.length);
        if (subVector.length == d2) {
            vector[d2] = 0;
            b(mdcm, type, d2, vector);
            return;
        }
        for (int i3 = 0; i3 < dimensionSize[subVector.length]; i3++) {
            vector[subVector.length] = i3;
            b(mdcm, type, d2, vector);
        }
    }

    @Deprecated
    /* renamed from: org.apache.commons.math3.transform.b$b  reason: collision with other inner class name */
    /* compiled from: FastFourierTransformer */
    public static class C0486b implements Cloneable {
        protected int[] c;
        protected Object d;

        C0486b(Object multiDimensionalComplexArray) {
            this.d = multiDimensionalComplexArray;
            int numOfDimensions = 0;
            for (Object lastDimension = multiDimensionalComplexArray; lastDimension instanceof Object[]; lastDimension = lastDimension[0]) {
                numOfDimensions++;
            }
            this.c = new int[numOfDimensions];
            int numOfDimensions2 = 0;
            Object lastDimension2 = multiDimensionalComplexArray;
            while (lastDimension2 instanceof Object[]) {
                Object[] array = lastDimension2;
                this.c[numOfDimensions2] = array.length;
                lastDimension2 = array[0];
                numOfDimensions2++;
            }
        }

        public org.apache.commons.math3.complex.a b(int... vector) {
            if (vector == null) {
                if (this.c.length <= 0) {
                    return null;
                }
                throw new DimensionMismatchException(0, this.c.length);
            } else if (vector.length == this.c.length) {
                Object lastDimension = this.d;
                for (int i = 0; i < this.c.length; i++) {
                    lastDimension = lastDimension[vector[i]];
                }
                return (org.apache.commons.math3.complex.a) lastDimension;
            } else {
                throw new DimensionMismatchException(vector.length, this.c.length);
            }
        }

        public org.apache.commons.math3.complex.a e(org.apache.commons.math3.complex.a magnitude, int... vector) {
            if (vector == null) {
                if (this.c.length <= 0) {
                    return null;
                }
                throw new DimensionMismatchException(0, this.c.length);
            } else if (vector.length == this.c.length) {
                Object[] lastDimension = (Object[]) this.d;
                int i = 0;
                while (true) {
                    int[] iArr = this.c;
                    if (i < iArr.length - 1) {
                        lastDimension = (Object[]) lastDimension[vector[i]];
                        i++;
                    } else {
                        org.apache.commons.math3.complex.a lastValue = (org.apache.commons.math3.complex.a) lastDimension[vector[iArr.length - 1]];
                        lastDimension[vector[iArr.length - 1]] = magnitude;
                        return lastValue;
                    }
                }
            } else {
                throw new DimensionMismatchException(vector.length, this.c.length);
            }
        }

        public int[] d() {
            return (int[]) this.c.clone();
        }

        public Object c() {
            return this.d;
        }

        public Object clone() {
            C0486b mdcm = new C0486b(Array.newInstance(org.apache.commons.math3.complex.a.class, this.c));
            a(mdcm);
            return mdcm;
        }

        private void a(C0486b mdcm) {
            int[] iArr;
            int[] vector = new int[this.c.length];
            int size = 1;
            int i = 0;
            while (true) {
                iArr = this.c;
                if (i >= iArr.length) {
                    break;
                }
                size *= iArr[i];
                i++;
            }
            int i2 = iArr.length;
            int[] iArr2 = new int[2];
            iArr2[1] = i2;
            iArr2[0] = size;
            int[][] vectorList = (int[][]) Array.newInstance(int.class, iArr2);
            for (int[] nextVector : vectorList) {
                System.arraycopy(vector, 0, nextVector, 0, this.c.length);
                int i3 = 0;
                while (true) {
                    int[] iArr3 = this.c;
                    if (i3 >= iArr3.length) {
                        break;
                    }
                    vector[i3] = vector[i3] + 1;
                    if (vector[i3] < iArr3[i3]) {
                        break;
                    }
                    vector[i3] = 0;
                    i3++;
                }
            }
            for (int[] nextVector2 : vectorList) {
                mdcm.e(b(nextVector2), nextVector2);
            }
        }
    }
}
