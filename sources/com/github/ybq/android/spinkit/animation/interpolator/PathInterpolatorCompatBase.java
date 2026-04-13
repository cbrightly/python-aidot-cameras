package com.github.ybq.android.spinkit.animation.interpolator;

import android.graphics.Path;
import android.view.animation.Interpolator;

public class PathInterpolatorCompatBase {
    private PathInterpolatorCompatBase() {
    }

    public static Interpolator create(Path path) {
        return new PathInterpolatorDonut(path);
    }

    public static Interpolator create(float controlX, float controlY) {
        return new PathInterpolatorDonut(controlX, controlY);
    }

    public static Interpolator create(float controlX1, float controlY1, float controlX2, float controlY2) {
        return new PathInterpolatorDonut(controlX1, controlY1, controlX2, controlY2);
    }
}
