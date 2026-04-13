package androidx.core.util;

import android.util.Half;
import androidx.annotation.RequiresApi;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Half.kt */
public final class HalfKt {
    @RequiresApi(26)
    @NotNull
    public static final Half toHalf(short $this$toHalf) {
        Half valueOf = Half.valueOf($this$toHalf);
        k.d(valueOf, "valueOf(this)");
        return valueOf;
    }

    @RequiresApi(26)
    @NotNull
    public static final Half toHalf(float $this$toHalf) {
        Half valueOf = Half.valueOf($this$toHalf);
        k.d(valueOf, "valueOf(this)");
        return valueOf;
    }

    @RequiresApi(26)
    @NotNull
    public static final Half toHalf(double $this$toHalf) {
        Half valueOf = Half.valueOf((float) $this$toHalf);
        k.d(valueOf, "valueOf(this)");
        return valueOf;
    }

    @RequiresApi(26)
    @NotNull
    public static final Half toHalf(@NotNull String $this$toHalf) {
        k.e($this$toHalf, "<this>");
        Half valueOf = Half.valueOf($this$toHalf);
        k.d(valueOf, "valueOf(this)");
        return valueOf;
    }
}
