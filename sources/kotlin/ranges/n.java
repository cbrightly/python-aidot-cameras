package kotlin.ranges;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: _Ranges.kt */
public class n extends m {
    @NotNull
    public static final g j(int $this$downTo, int to) {
        return g.c.a($this$downTo, to, -1);
    }

    @NotNull
    public static final g k(@NotNull g $this$step, int step) {
        k.e($this$step, "$this$step");
        m.a(step > 0, Integer.valueOf(step));
        return g.c.a($this$step.a(), $this$step.b(), $this$step.e() > 0 ? step : -step);
    }

    @NotNull
    public static final i l(int $this$until, int to) {
        if (to <= Integer.MIN_VALUE) {
            return i.y.a();
        }
        return new i($this$until, to - 1);
    }

    public static final int b(int $this$coerceAtLeast, int minimumValue) {
        return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
    }

    public static final long c(long $this$coerceAtLeast, long minimumValue) {
        return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
    }

    public static final int e(int $this$coerceAtMost, int maximumValue) {
        return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
    }

    public static final long f(long $this$coerceAtMost, long maximumValue) {
        return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
    }

    public static final double d(double $this$coerceAtMost, double maximumValue) {
        return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
    }

    public static final int h(int $this$coerceIn, int minimumValue, int maximumValue) {
        if (minimumValue > maximumValue) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
        } else if ($this$coerceIn < minimumValue) {
            return minimumValue;
        } else {
            if ($this$coerceIn > maximumValue) {
                return maximumValue;
            }
            return $this$coerceIn;
        }
    }

    public static final long i(long $this$coerceIn, long minimumValue, long maximumValue) {
        if (minimumValue > maximumValue) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
        } else if ($this$coerceIn < minimumValue) {
            return minimumValue;
        } else {
            if ($this$coerceIn > maximumValue) {
                return maximumValue;
            }
            return $this$coerceIn;
        }
    }

    public static final double g(double $this$coerceIn, double minimumValue, double maximumValue) {
        if (minimumValue > maximumValue) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
        } else if ($this$coerceIn < minimumValue) {
            return minimumValue;
        } else {
            if ($this$coerceIn > maximumValue) {
                return maximumValue;
            }
            return $this$coerceIn;
        }
    }
}
