package com.airbnb.lottie.model.content;

import com.airbnb.lottie.utils.b;
import com.airbnb.lottie.utils.g;
import java.util.Arrays;

/* compiled from: GradientColor */
public class d {
    private final float[] a;
    private final int[] b;

    public d(float[] positions, int[] colors) {
        this.a = positions;
        this.b = colors;
    }

    public float[] d() {
        return this.a;
    }

    public int[] c() {
        return this.b;
    }

    public int e() {
        return this.b.length;
    }

    public void f(d gc1, d gc2, float progress) {
        if (gc1.b.length == gc2.b.length) {
            for (int i = 0; i < gc1.b.length; i++) {
                this.a[i] = g.i(gc1.a[i], gc2.a[i], progress);
                this.b[i] = b.c(progress, gc1.b[i], gc2.b[i]);
            }
            return;
        }
        throw new IllegalArgumentException("Cannot interpolate between gradients. Lengths vary (" + gc1.b.length + " vs " + gc2.b.length + ")");
    }

    public d a(float[] positions) {
        int[] colors = new int[positions.length];
        for (int i = 0; i < positions.length; i++) {
            colors[i] = b(positions[i]);
        }
        return new d(positions, colors);
    }

    private int b(float position) {
        int existingIndex = Arrays.binarySearch(this.a, position);
        if (existingIndex >= 0) {
            return this.b[existingIndex];
        }
        int insertionPoint = -(existingIndex + 1);
        if (insertionPoint == 0) {
            return this.b[0];
        }
        int[] iArr = this.b;
        if (insertionPoint == iArr.length - 1) {
            return iArr[iArr.length - 1];
        }
        float[] fArr = this.a;
        float startPosition = fArr[insertionPoint - 1];
        return b.c((position - startPosition) / (fArr[insertionPoint] - startPosition), iArr[insertionPoint - 1], iArr[insertionPoint]);
    }
}
