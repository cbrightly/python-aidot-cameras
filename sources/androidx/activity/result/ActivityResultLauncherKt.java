package androidx.activity.result;

import androidx.core.app.ActivityOptionsCompat;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ActivityResultLauncher.kt */
public final class ActivityResultLauncherKt {
    public static /* synthetic */ void launch$default(ActivityResultLauncher activityResultLauncher, ActivityOptionsCompat activityOptionsCompat, int i, Object obj) {
        if ((i & 1) != 0) {
            activityOptionsCompat = null;
        }
        launch(activityResultLauncher, activityOptionsCompat);
    }

    public static final void launch(@NotNull ActivityResultLauncher<Void> $this$launch, @Nullable ActivityOptionsCompat options) {
        k.e($this$launch, "<this>");
        $this$launch.launch(null, options);
    }

    public static /* synthetic */ void launchUnit$default(ActivityResultLauncher activityResultLauncher, ActivityOptionsCompat activityOptionsCompat, int i, Object obj) {
        if ((i & 1) != 0) {
            activityOptionsCompat = null;
        }
        launchUnit(activityResultLauncher, activityOptionsCompat);
    }

    public static final void launchUnit(@NotNull ActivityResultLauncher<x> $this$launch, @Nullable ActivityOptionsCompat options) {
        k.e($this$launch, "<this>");
        $this$launch.launch(x.a, options);
    }
}
