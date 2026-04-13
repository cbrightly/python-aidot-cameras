package com.github.ybq.android.spinkit.animation.interpolator;

import android.annotation.TargetApi;
import android.graphics.Path;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

public class PathInterpolatorCompatApi21 {
    private PathInterpolatorCompatApi21() {
    }

    @TargetApi(21)
    public static Interpolator create(Path path) {
        return new PathInterpolator(path);
    }

    @TargetApi(21)
    public static Interpolator create(float controlX, float controlY) {
        return new PathInterpolator(controlX, controlY);
    }

    @TargetApi(21)
    public static Interpolator create(float controlX1, float controlY1, float controlX2, float controlY2) {
        return new PathInterpolator(controlX1, controlY1, controlX2, controlY2);
    }
}
