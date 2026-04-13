package org.spongycastle.crypto.modes.gcm;

import java.util.Vector;
import org.spongycastle.util.Arrays;

public class Tables1kGCMExponentiator implements GCMExponentiator {
    private Vector a;

    public void a(byte[] x) {
        int[] y = GCMUtil.c(x);
        Vector vector = this.a;
        if (vector == null || !Arrays.d(y, (int[]) vector.elementAt(0))) {
            Vector vector2 = new Vector(8);
            this.a = vector2;
            vector2.addElement(y);
        }
    }

    public void b(long pow, byte[] output) {
        int[] y = GCMUtil.i();
        int bit = 0;
        while (pow > 0) {
            if ((1 & pow) != 0) {
                c(bit);
                GCMUtil.f(y, (int[]) this.a.elementAt(bit));
            }
            bit++;
            pow >>>= 1;
        }
        GCMUtil.a(y, output);
    }

    private void c(int bit) {
        int count = this.a.size();
        if (count <= bit) {
            int[] tmp = (int[]) this.a.elementAt(count - 1);
            do {
                tmp = Arrays.k(tmp);
                GCMUtil.f(tmp, tmp);
                this.a.addElement(tmp);
                count++;
            } while (count <= bit);
        }
    }
}
