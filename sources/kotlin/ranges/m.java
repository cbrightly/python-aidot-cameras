package kotlin.ranges;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Ranges.kt */
public class m {
    public static final void a(boolean isPositive, @NotNull Number step) {
        k.e(step, "step");
        if (!isPositive) {
            throw new IllegalArgumentException("Step must be positive, was: " + step + '.');
        }
    }
}
