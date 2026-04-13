package org.spongycastle.pqc.crypto.gmss;

import org.spongycastle.util.Arrays;

public class GMSSParameters {
    private int a;
    private int[] b;
    private int[] c;
    private int[] d;

    public GMSSParameters(int layers, int[] heightOfTrees, int[] winternitzParameter, int[] K) {
        e(layers, heightOfTrees, winternitzParameter, K);
    }

    private void e(int layers, int[] heightOfTrees, int[] winternitzParameter, int[] K) {
        boolean valid = true;
        String errMsg = "";
        this.a = layers;
        if (!(layers == winternitzParameter.length && layers == heightOfTrees.length && layers == K.length)) {
            valid = false;
            errMsg = "Unexpected parameterset format";
        }
        for (int i = 0; i < this.a; i++) {
            if (K[i] < 2 || (heightOfTrees[i] - K[i]) % 2 != 0) {
                valid = false;
                errMsg = "Wrong parameter K (K >= 2 and H-K even required)!";
            }
            if (heightOfTrees[i] < 4 || winternitzParameter[i] < 2) {
                valid = false;
                errMsg = "Wrong parameter H or w (H > 3 and w > 1 required)!";
            }
        }
        if (valid) {
            this.b = Arrays.k(heightOfTrees);
            this.c = Arrays.k(winternitzParameter);
            this.d = Arrays.k(K);
            return;
        }
        throw new IllegalArgumentException(errMsg);
    }

    public int c() {
        return this.a;
    }

    public int[] a() {
        return Arrays.k(this.b);
    }

    public int[] d() {
        return Arrays.k(this.c);
    }

    public int[] b() {
        return Arrays.k(this.d);
    }
}
