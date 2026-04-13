package androidx.core.view;

import android.os.Build;
import android.view.WindowInsetsAnimationController;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.Insets;

public final class WindowInsetsAnimationControllerCompat {
    private final Impl mImpl;

    WindowInsetsAnimationControllerCompat() {
        if (Build.VERSION.SDK_INT < 30) {
            this.mImpl = new Impl();
            return;
        }
        throw new UnsupportedOperationException("On API 30+, the constructor taking a " + WindowInsetsAnimationController.class.getSimpleName() + " as parameter");
    }

    @RequiresApi(30)
    WindowInsetsAnimationControllerCompat(@NonNull WindowInsetsAnimationController controller) {
        this.mImpl = new Impl30(controller);
    }

    @NonNull
    public Insets getHiddenStateInsets() {
        return this.mImpl.getHiddenStateInsets();
    }

    @NonNull
    public Insets getShownStateInsets() {
        return this.mImpl.getShownStateInsets();
    }

    @NonNull
    public Insets getCurrentInsets() {
        return this.mImpl.getCurrentInsets();
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getCurrentFraction() {
        return this.mImpl.getCurrentFraction();
    }

    public float getCurrentAlpha() {
        return this.mImpl.getCurrentAlpha();
    }

    public int getTypes() {
        return this.mImpl.getTypes();
    }

    public void setInsetsAndAlpha(@Nullable Insets insets, @FloatRange(from = 0.0d, to = 1.0d) float alpha, @FloatRange(from = 0.0d, to = 1.0d) float fraction) {
        this.mImpl.setInsetsAndAlpha(insets, alpha, fraction);
    }

    public void finish(boolean shown) {
        this.mImpl.finish(shown);
    }

    public boolean isReady() {
        return !isFinished() && !isCancelled();
    }

    public boolean isFinished() {
        return this.mImpl.isFinished();
    }

    public boolean isCancelled() {
        return this.mImpl.isCancelled();
    }

    public static class Impl {
        Impl() {
        }

        @NonNull
        public Insets getHiddenStateInsets() {
            return Insets.NONE;
        }

        @NonNull
        public Insets getShownStateInsets() {
            return Insets.NONE;
        }

        @NonNull
        public Insets getCurrentInsets() {
            return Insets.NONE;
        }

        @FloatRange(from = 0.0d, to = 1.0d)
        public float getCurrentFraction() {
            return 0.0f;
        }

        public float getCurrentAlpha() {
            return 0.0f;
        }

        public int getTypes() {
            return 0;
        }

        public void setInsetsAndAlpha(@Nullable Insets insets, @FloatRange(from = 0.0d, to = 1.0d) float alpha, @FloatRange(from = 0.0d, to = 1.0d) float fraction) {
        }

        /* access modifiers changed from: package-private */
        public void finish(boolean shown) {
        }

        public boolean isReady() {
            return false;
        }

        /* access modifiers changed from: package-private */
        public boolean isFinished() {
            return false;
        }

        /* access modifiers changed from: package-private */
        public boolean isCancelled() {
            return true;
        }
    }

    @RequiresApi(30)
    public static class Impl30 extends Impl {
        private final WindowInsetsAnimationController mController;

        Impl30(@NonNull WindowInsetsAnimationController controller) {
            this.mController = controller;
        }

        @NonNull
        public Insets getHiddenStateInsets() {
            return Insets.toCompatInsets(this.mController.getHiddenStateInsets());
        }

        @NonNull
        public Insets getShownStateInsets() {
            return Insets.toCompatInsets(this.mController.getShownStateInsets());
        }

        @NonNull
        public Insets getCurrentInsets() {
            return Insets.toCompatInsets(this.mController.getCurrentInsets());
        }

        public float getCurrentFraction() {
            return this.mController.getCurrentFraction();
        }

        public float getCurrentAlpha() {
            return this.mController.getCurrentAlpha();
        }

        public int getTypes() {
            return this.mController.getTypes();
        }

        public void setInsetsAndAlpha(@Nullable Insets insets, float alpha, float fraction) {
            this.mController.setInsetsAndAlpha(insets == null ? null : insets.toPlatformInsets(), alpha, fraction);
        }

        /* access modifiers changed from: package-private */
        public void finish(boolean shown) {
            this.mController.finish(shown);
        }

        public boolean isReady() {
            return this.mController.isReady();
        }

        /* access modifiers changed from: package-private */
        public boolean isFinished() {
            return this.mController.isFinished();
        }

        /* access modifiers changed from: package-private */
        public boolean isCancelled() {
            return this.mController.isCancelled();
        }
    }
}
