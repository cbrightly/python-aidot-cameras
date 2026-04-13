package androidx.activity;

import android.app.Activity;
import android.app.PictureInPictureParams;
import android.graphics.Rect;
import androidx.annotation.RequiresApi;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

@RequiresApi(26)
/* compiled from: PipHintTracker.kt */
public final class Api26Impl {
    @NotNull
    public static final Api26Impl INSTANCE = new Api26Impl();

    private Api26Impl() {
    }

    public final void setPipParamsSourceRectHint(@NotNull Activity activity, @NotNull Rect hint) {
        k.e(activity, "activity");
        k.e(hint, "hint");
        activity.setPictureInPictureParams(new PictureInPictureParams.Builder().setSourceRectHint(hint).build());
    }
}
