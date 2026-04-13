package org.spongycastle.pqc.math.ntru.euclid;

public class IntEuclidean {
    public int a;
    public int b;
    public int c;

    private IntEuclidean() {
    }

    public static IntEuclidean a(int a2, int b2) {
        int x = 0;
        int lastx = 1;
        int y = 1;
        int lasty = 0;
        while (b2 != 0) {
            int quotient = a2 / b2;
            int temp = a2;
            a2 = b2;
            b2 = temp % b2;
            int temp2 = x;
            x = lastx - (quotient * x);
            lastx = temp2;
            int temp3 = y;
            y = lasty - (quotient * y);
            lasty = temp3;
        }
        IntEuclidean result = new IntEuclidean();
        result.a = lastx;
        result.b = lasty;
        result.c = a2;
        return result;
    }
}
