package kotlin.text;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: StringNumberConversionsJVM.kt */
public class u extends t {
    @Nullable
    public static final Float m(@NotNull String $this$toFloatOrNull) {
        k.e($this$toFloatOrNull, "$this$toFloatOrNull");
        try {
            if (n.a.matches($this$toFloatOrNull)) {
                return Float.valueOf(Float.parseFloat($this$toFloatOrNull));
            }
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Nullable
    public static final Double l(@NotNull String $this$toDoubleOrNull) {
        k.e($this$toDoubleOrNull, "$this$toDoubleOrNull");
        try {
            if (n.a.matches($this$toDoubleOrNull)) {
                return Double.valueOf(Double.parseDouble($this$toDoubleOrNull));
            }
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
