package org.spongycastle.pqc.crypto.rainbow;

import org.spongycastle.crypto.CipherParameters;

public class RainbowParameters implements CipherParameters {
    private final int[] c;
    private int[] d;

    public RainbowParameters() {
        int[] iArr = {6, 12, 17, 22, 33};
        this.c = iArr;
        this.d = iArr;
    }

    public RainbowParameters(int[] vi) {
        this.c = new int[]{6, 12, 17, 22, 33};
        this.d = vi;
        try {
            a();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void a() {
        int[] iArr = this.d;
        if (iArr == null) {
            throw new Exception("no layers defined.");
        } else if (iArr.length > 1) {
            int i = 0;
            while (true) {
                int[] iArr2 = this.d;
                if (i >= iArr2.length - 1) {
                    return;
                }
                if (iArr2[i] < iArr2[i + 1]) {
                    i++;
                } else {
                    throw new Exception("v[i] has to be smaller than v[i+1]");
                }
            }
        } else {
            throw new Exception("Rainbow needs at least 1 layer, such that v1 < v2.");
        }
    }

    public int b() {
        return this.d.length - 1;
    }

    public int[] c() {
        return this.d;
    }
}
