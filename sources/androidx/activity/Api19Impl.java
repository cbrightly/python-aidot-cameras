package androidx.activity;

import android.view.View;
import androidx.annotation.RequiresApi;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

@RequiresApi(19)
/* compiled from: PipHintTracker.kt */
public final class Api19Impl {
    @NotNull
    public static final Api19Impl INSTANCE = new Api19Impl();

    private Api19Impl() {
    }

    public final boolean isAttachedToWindow(@NotNull View view) {
        k.e(view, "view");
        return view.isAttachedToWindow();
    }
}
