package androidx.core.location;

import android.location.Location;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Location.kt */
public final class LocationKt {
    public static final double component1(@NotNull Location $this$component1) {
        k.e($this$component1, "<this>");
        return $this$component1.getLatitude();
    }

    public static final double component2(@NotNull Location $this$component2) {
        k.e($this$component2, "<this>");
        return $this$component2.getLongitude();
    }
}
