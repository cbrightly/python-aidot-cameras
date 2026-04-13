package kotlin.math;

/* compiled from: MathJVM.kt */
public class b extends a {
    public static final int a(double $this$roundToInt) {
        if (Double.isNaN($this$roundToInt)) {
            throw new IllegalArgumentException("Cannot round NaN value.");
        } else if ($this$roundToInt > ((double) Integer.MAX_VALUE)) {
            return Integer.MAX_VALUE;
        } else {
            if ($this$roundToInt < ((double) Integer.MIN_VALUE)) {
                return Integer.MIN_VALUE;
            }
            return (int) Math.round($this$roundToInt);
        }
    }

    public static final int b(float $this$roundToInt) {
        if (!Float.isNaN($this$roundToInt)) {
            return Math.round($this$roundToInt);
        }
        throw new IllegalArgumentException("Cannot round NaN value.");
    }
}
