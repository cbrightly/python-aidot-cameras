package org.spongycastle.pqc.math.ntru.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.spongycastle.pqc.math.ntru.euclid.IntEuclidean;
import org.spongycastle.pqc.math.ntru.polynomial.DenseTernaryPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.SparseTernaryPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.TernaryPolynomial;
import org.spongycastle.util.Integers;

public class Util {
    private static volatile boolean a;
    private static volatile boolean b;

    public static int c(int n, int modulus) {
        int n2 = n % modulus;
        if (n2 < 0) {
            n2 += modulus;
        }
        return IntEuclidean.a(n2, modulus).a;
    }

    public static int e(int a2, int b2, int modulus) {
        int p = 1;
        for (int i = 0; i < b2; i++) {
            p = (p * a2) % modulus;
        }
        return p;
    }

    public static TernaryPolynomial a(int N, int numOnes, int numNegOnes, boolean sparse, SecureRandom random) {
        if (sparse) {
            return SparseTernaryPolynomial.g(N, numOnes, numNegOnes, random);
        }
        return DenseTernaryPolynomial.S(N, numOnes, numNegOnes, random);
    }

    public static int[] b(int N, int numOnes, int numNegOnes, SecureRandom random) {
        Integer one = Integers.b(1);
        Integer minusOne = Integers.b(-1);
        Integer zero = Integers.b(0);
        List list = new ArrayList();
        for (int i = 0; i < numOnes; i++) {
            list.add(one);
        }
        for (int i2 = 0; i2 < numNegOnes; i2++) {
            list.add(minusOne);
        }
        while (list.size() < N) {
            list.add(zero);
        }
        Collections.shuffle(list, random);
        int[] arr = new int[N];
        for (int i3 = 0; i3 < N; i3++) {
            arr[i3] = ((Integer) list.get(i3)).intValue();
        }
        return arr;
    }

    public static boolean d() {
        if (!a) {
            String arch = System.getProperty("os.arch");
            b = "amd64".equals(arch) || "x86_64".equals(arch) || "ppc64".equals(arch) || "64".equals(System.getProperty("sun.arch.data.model"));
            a = true;
        }
        return b;
    }
}
