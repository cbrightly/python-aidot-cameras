package zendesk.ui.android.internal;

import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ImageViewExtension.kt */
public final class ImageViewExtensionKt {
    public static final void a(@NotNull ImageView $this$applyLoopingAnimatedVectorDrawable, @DrawableRes int avdResId) {
        k.e($this$applyLoopingAnimatedVectorDrawable, "<this>");
        AnimatedVectorDrawableCompat animated = AnimatedVectorDrawableCompat.create($this$applyLoopingAnimatedVectorDrawable.getContext(), avdResId);
        if (animated != null) {
            animated.registerAnimationCallback(new ImageViewExtensionKt$applyLoopingAnimatedVectorDrawable$1($this$applyLoopingAnimatedVectorDrawable, animated));
        }
        $this$applyLoopingAnimatedVectorDrawable.setImageDrawable(animated);
        if (animated != null) {
            animated.start();
        }
    }
}
