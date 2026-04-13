package com.airbnb.lottie.utils;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.annotation.FloatRange;
import com.airbnb.lottie.animation.content.k;
import com.airbnb.lottie.model.a;
import com.airbnb.lottie.model.content.n;
import com.airbnb.lottie.model.e;
import java.util.List;

/* compiled from: MiscUtils */
public class g {
    private static final PointF a = new PointF();

    public static PointF a(PointF p1, PointF p2) {
        return new PointF(p1.x + p2.x, p1.y + p2.y);
    }

    public static void h(n shapeData, Path outPath) {
        Path path = outPath;
        outPath.reset();
        PointF initialPoint = shapeData.b();
        path.moveTo(initialPoint.x, initialPoint.y);
        a.set(initialPoint.x, initialPoint.y);
        for (int i = 0; i < shapeData.a().size(); i++) {
            a curveData = shapeData.a().get(i);
            PointF cp1 = curveData.a();
            PointF cp2 = curveData.b();
            PointF vertex = curveData.c();
            PointF pointF = a;
            if (!cp1.equals(pointF) || !cp2.equals(vertex)) {
                outPath.cubicTo(cp1.x, cp1.y, cp2.x, cp2.y, vertex.x, vertex.y);
            } else {
                path.lineTo(vertex.x, vertex.y);
            }
            pointF.set(vertex.x, vertex.y);
        }
        if (shapeData.d()) {
            outPath.close();
        }
    }

    public static float i(float a2, float b, @FloatRange(from = 0.0d, to = 1.0d) float percentage) {
        return ((b - a2) * percentage) + a2;
    }

    public static int j(int a2, int b, @FloatRange(from = 0.0d, to = 1.0d) float percentage) {
        return (int) (((float) a2) + (((float) (b - a2)) * percentage));
    }

    static int f(float x, float y) {
        return g((int) x, (int) y);
    }

    private static int g(int x, int y) {
        return x - (e(x, y) * y);
    }

    private static int e(int x, int y) {
        int r = x / y;
        int mod = x % y;
        if (((x ^ y) >= 0) || mod == 0) {
            return r;
        }
        return r - 1;
    }

    public static int c(int number, int min, int max) {
        return Math.max(min, Math.min(max, number));
    }

    public static float b(float number, float min, float max) {
        return Math.max(min, Math.min(max, number));
    }

    public static boolean d(float number, float rangeMin, float rangeMax) {
        return number >= rangeMin && number <= rangeMax;
    }

    public static void k(e keyPath, int depth, List<e> accumulator, e currentPartialKeyPath, k content) {
        if (keyPath.c(content.getName(), depth)) {
            accumulator.add(currentPartialKeyPath.a(content.getName()).i(content));
        }
    }
}
