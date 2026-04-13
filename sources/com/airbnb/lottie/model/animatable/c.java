package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.animation.keyframe.e;
import com.airbnb.lottie.model.content.d;
import com.airbnb.lottie.value.a;
import java.util.Arrays;
import java.util.List;

/* compiled from: AnimatableGradientColorValue */
public class c extends n<d, d> {
    public /* bridge */ /* synthetic */ boolean i() {
        return super.i();
    }

    public /* bridge */ /* synthetic */ List k() {
        return super.k();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public c(List<a<d>> keyframes) {
        super(b(keyframes));
    }

    private static List<a<d>> b(List<a<d>> keyframes) {
        for (int i = 0; i < keyframes.size(); i++) {
            keyframes.set(i, a(keyframes.get(i)));
        }
        return keyframes;
    }

    private static a<d> a(a<d> keyframe) {
        d startValue = (d) keyframe.b;
        d endValue = (d) keyframe.c;
        if (startValue == null || endValue == null || startValue.d().length == endValue.d().length) {
            return keyframe;
        }
        float[] mergedPositions = c(startValue.d(), endValue.d());
        return keyframe.b(startValue.a(mergedPositions), endValue.a(mergedPositions));
    }

    static float[] c(float[] startPositions, float[] endPositions) {
        float[] mergedArray = new float[(startPositions.length + endPositions.length)];
        System.arraycopy(startPositions, 0, mergedArray, 0, startPositions.length);
        System.arraycopy(endPositions, 0, mergedArray, startPositions.length, endPositions.length);
        Arrays.sort(mergedArray);
        int uniqueValues = 0;
        float lastValue = Float.NaN;
        for (int i = 0; i < mergedArray.length; i++) {
            if (mergedArray[i] != lastValue) {
                mergedArray[uniqueValues] = mergedArray[i];
                uniqueValues++;
                lastValue = mergedArray[i];
            }
        }
        return Arrays.copyOfRange(mergedArray, 0, uniqueValues);
    }

    public com.airbnb.lottie.animation.keyframe.a<d, d> j() {
        return new e(this.a);
    }
}
