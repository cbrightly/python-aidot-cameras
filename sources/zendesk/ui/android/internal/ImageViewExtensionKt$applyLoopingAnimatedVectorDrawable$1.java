package zendesk.ui.android.internal;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import org.jetbrains.annotations.Nullable;

/* compiled from: ImageViewExtension.kt */
public final class ImageViewExtensionKt$applyLoopingAnimatedVectorDrawable$1 extends Animatable2Compat.AnimationCallback {
    final /* synthetic */ ImageView a;
    final /* synthetic */ AnimatedVectorDrawableCompat b;

    ImageViewExtensionKt$applyLoopingAnimatedVectorDrawable$1(ImageView $receiver, AnimatedVectorDrawableCompat $animated) {
        this.a = $receiver;
        this.b = $animated;
    }

    /* access modifiers changed from: private */
    public static final void b(AnimatedVectorDrawableCompat $animated) {
        $animated.start();
    }

    public void onAnimationEnd(@Nullable Drawable drawable) {
        this.a.post(new b(this.b));
    }
}
