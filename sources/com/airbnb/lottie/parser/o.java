package com.airbnb.lottie.parser;

import android.graphics.Color;
import com.airbnb.lottie.model.content.d;
import com.airbnb.lottie.parser.moshi.a;
import com.airbnb.lottie.utils.b;
import com.airbnb.lottie.utils.g;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: GradientColorParser */
public class o implements n0<d> {
    private int a;

    public o(int colorPoints) {
        this.a = colorPoints;
    }

    /* renamed from: f */
    public d a(a reader, float scale) {
        List<Float> array = new ArrayList<>();
        boolean isArray = reader.u() == a.b.BEGIN_ARRAY;
        if (isArray) {
            reader.c();
        }
        while (reader.l()) {
            array.add(Float.valueOf((float) reader.n()));
        }
        if (array.size() == 4 && array.get(0).floatValue() == 1.0f) {
            array.set(0, Float.valueOf(0.0f));
            array.add(Float.valueOf(1.0f));
            array.add(array.get(1));
            array.add(array.get(2));
            array.add(array.get(3));
            this.a = 2;
        }
        if (isArray) {
            reader.i();
        }
        if (this.a == -1) {
            this.a = array.size() / 4;
        }
        int i = this.a;
        float[] positions = new float[i];
        int[] colors = new int[i];
        int r = 0;
        int g = 0;
        for (int i2 = 0; i2 < this.a * 4; i2++) {
            int colorIndex = i2 / 4;
            double value = (double) array.get(i2).floatValue();
            switch (i2 % 4) {
                case 0:
                    if (colorIndex > 0 && positions[colorIndex - 1] >= ((float) value)) {
                        positions[colorIndex] = ((float) value) + 0.01f;
                        break;
                    } else {
                        positions[colorIndex] = (float) value;
                        break;
                    }
                    break;
                case 1:
                    r = (int) (255.0d * value);
                    break;
                case 2:
                    g = (int) (255.0d * value);
                    break;
                case 3:
                    colors[colorIndex] = Color.argb(255, r, g, (int) (255.0d * value));
                    break;
            }
        }
        return b(new d(positions, colors), array);
    }

    private d b(d gradientColor, List<Float> array) {
        List<Float> list = array;
        int startIndex = this.a * 4;
        if (array.size() <= startIndex) {
            return gradientColor;
        }
        float[] colorStopPositions = gradientColor.d();
        int[] colorStopColors = gradientColor.c();
        int opacityStops = (array.size() - startIndex) / 2;
        float[] opacityStopPositions = new float[opacityStops];
        float[] opacityStopOpacities = new float[opacityStops];
        int j = 0;
        for (int i = startIndex; i < array.size(); i++) {
            if (i % 2 == 0) {
                opacityStopPositions[j] = list.get(i).floatValue();
            } else {
                opacityStopOpacities[j] = list.get(i).floatValue();
                j++;
            }
        }
        float[] newPositions = e(gradientColor.d(), opacityStopPositions);
        int newColorPoints = newPositions.length;
        int[] newColors = new int[newColorPoints];
        for (int i2 = 0; i2 < newColorPoints; i2++) {
            float position = newPositions[i2];
            int colorStopIndex = Arrays.binarySearch(colorStopPositions, position);
            int opacityIndex = Arrays.binarySearch(opacityStopPositions, position);
            if (colorStopIndex < 0 || opacityIndex > 0) {
                if (opacityIndex < 0) {
                    opacityIndex = -(opacityIndex + 1);
                }
                newColors[i2] = c(position, opacityStopOpacities[opacityIndex], colorStopPositions, colorStopColors);
            } else {
                newColors[i2] = d(position, colorStopColors[colorStopIndex], opacityStopPositions, opacityStopOpacities);
            }
        }
        return new d(newPositions, newColors);
    }

    /* access modifiers changed from: package-private */
    public int c(float position, float opacity, float[] colorStopPositions, int[] colorStopColors) {
        float[] fArr = colorStopPositions;
        int[] iArr = colorStopColors;
        if (iArr.length < 2 || position == fArr[0]) {
            return iArr[0];
        }
        for (int i = 1; i < fArr.length; i++) {
            if (fArr[i] >= position || i == fArr.length - 1) {
                float percentage = (position - fArr[i - 1]) / (fArr[i] - fArr[i - 1]);
                int upperColor = iArr[i];
                int lowerColor = iArr[i - 1];
                return Color.argb((int) (255.0f * opacity), b.c(percentage, Color.red(lowerColor), Color.red(upperColor)), b.c(percentage, Color.green(lowerColor), Color.green(upperColor)), b.c(percentage, Color.blue(lowerColor), Color.blue(upperColor)));
            }
        }
        throw new IllegalArgumentException("Unreachable code.");
    }

    private int d(float position, int color, float[] opacityStopPositions, float[] opacityStopOpacities) {
        int a2;
        if (opacityStopOpacities.length < 2 || position <= opacityStopPositions[0]) {
            return Color.argb((int) (opacityStopOpacities[0] * 255.0f), Color.red(color), Color.green(color), Color.blue(color));
        }
        for (int i = 1; i < opacityStopPositions.length; i++) {
            float opacityStopPosition = opacityStopPositions[i];
            if (opacityStopPosition >= position || i == opacityStopPositions.length - 1) {
                if (opacityStopPosition <= position) {
                    a2 = (int) (opacityStopOpacities[i] * 255.0f);
                } else {
                    float distanceBetweenOpacities = opacityStopPositions[i] - opacityStopPositions[i - 1];
                    a2 = (int) (g.i(opacityStopOpacities[i - 1], opacityStopOpacities[i], (position - opacityStopPositions[i - 1]) / distanceBetweenOpacities) * 255.0f);
                }
                return Color.argb(a2, Color.red(color), Color.green(color), Color.blue(color));
            }
        }
        throw new IllegalArgumentException("Unreachable code.");
    }

    protected static float[] e(float[] arrayA, float[] arrayB) {
        if (arrayA.length == 0) {
            return arrayB;
        }
        if (arrayB.length == 0) {
            return arrayA;
        }
        int aIndex = 0;
        int bIndex = 0;
        int numDuplicates = 0;
        float[] mergedNotTruncated = new float[(arrayA.length + arrayB.length)];
        for (int i = 0; i < mergedNotTruncated.length; i++) {
            float b = Float.NaN;
            float a2 = aIndex < arrayA.length ? arrayA[aIndex] : Float.NaN;
            if (bIndex < arrayB.length) {
                b = arrayB[bIndex];
            }
            if (Float.isNaN(b) || a2 < b) {
                mergedNotTruncated[i] = a2;
                aIndex++;
            } else if (Float.isNaN(a2) || b < a2) {
                mergedNotTruncated[i] = b;
                bIndex++;
            } else {
                mergedNotTruncated[i] = a2;
                aIndex++;
                bIndex++;
                numDuplicates++;
            }
        }
        if (numDuplicates == 0) {
            return mergedNotTruncated;
        }
        return Arrays.copyOf(mergedNotTruncated, mergedNotTruncated.length - numDuplicates);
    }
}
