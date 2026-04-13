package androidx.core.view;

import android.os.Build;
import android.os.CancellationSignal;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsAnimationControlListener;
import android.view.WindowInsetsAnimationController;
import android.view.WindowInsetsController;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.collection.SimpleArrayMap;

public final class WindowInsetsControllerCompat {
    public static final int BEHAVIOR_SHOW_BARS_BY_SWIPE = 1;
    public static final int BEHAVIOR_SHOW_BARS_BY_TOUCH = 0;
    public static final int BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE = 2;
    private final Impl mImpl;

    public interface OnControllableInsetsChangedListener {
        void onControllableInsetsChanged(@NonNull WindowInsetsControllerCompat windowInsetsControllerCompat, int i);
    }

    @RequiresApi(30)
    private WindowInsetsControllerCompat(@NonNull WindowInsetsController insetsController) {
        if (Build.VERSION.SDK_INT >= 30) {
            this.mImpl = new Impl30(insetsController, this);
        } else {
            this.mImpl = new Impl();
        }
    }

    public WindowInsetsControllerCompat(@NonNull Window window, @NonNull View view) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 30) {
            this.mImpl = new Impl30(window, this);
        } else if (i >= 26) {
            this.mImpl = new Impl26(window, view);
        } else if (i >= 23) {
            this.mImpl = new Impl23(window, view);
        } else if (i >= 20) {
            this.mImpl = new Impl20(window, view);
        } else {
            this.mImpl = new Impl();
        }
    }

    @RequiresApi(30)
    @NonNull
    public static WindowInsetsControllerCompat toWindowInsetsControllerCompat(@NonNull WindowInsetsController insetsController) {
        return new WindowInsetsControllerCompat(insetsController);
    }

    public void show(int types) {
        this.mImpl.show(types);
    }

    public void hide(int types) {
        this.mImpl.hide(types);
    }

    public boolean isAppearanceLightStatusBars() {
        return this.mImpl.isAppearanceLightStatusBars();
    }

    public void setAppearanceLightStatusBars(boolean isLight) {
        this.mImpl.setAppearanceLightStatusBars(isLight);
    }

    public boolean isAppearanceLightNavigationBars() {
        return this.mImpl.isAppearanceLightNavigationBars();
    }

    public void setAppearanceLightNavigationBars(boolean isLight) {
        this.mImpl.setAppearanceLightNavigationBars(isLight);
    }

    public void controlWindowInsetsAnimation(int types, long durationMillis, @Nullable Interpolator interpolator, @Nullable CancellationSignal cancellationSignal, @NonNull WindowInsetsAnimationControlListenerCompat listener) {
        this.mImpl.controlWindowInsetsAnimation(types, durationMillis, interpolator, cancellationSignal, listener);
    }

    public void setSystemBarsBehavior(int behavior) {
        this.mImpl.setSystemBarsBehavior(behavior);
    }

    public int getSystemBarsBehavior() {
        return this.mImpl.getSystemBarsBehavior();
    }

    public void addOnControllableInsetsChangedListener(@NonNull OnControllableInsetsChangedListener listener) {
        this.mImpl.addOnControllableInsetsChangedListener(listener);
    }

    public void removeOnControllableInsetsChangedListener(@NonNull OnControllableInsetsChangedListener listener) {
        this.mImpl.removeOnControllableInsetsChangedListener(listener);
    }

    public static class Impl {
        Impl() {
        }

        /* access modifiers changed from: package-private */
        public void show(int types) {
        }

        /* access modifiers changed from: package-private */
        public void hide(int types) {
        }

        /* access modifiers changed from: package-private */
        public void controlWindowInsetsAnimation(int types, long durationMillis, Interpolator interpolator, CancellationSignal cancellationSignal, WindowInsetsAnimationControlListenerCompat listener) {
        }

        /* access modifiers changed from: package-private */
        public void setSystemBarsBehavior(int behavior) {
        }

        /* access modifiers changed from: package-private */
        public int getSystemBarsBehavior() {
            return 0;
        }

        public boolean isAppearanceLightStatusBars() {
            return false;
        }

        public void setAppearanceLightStatusBars(boolean isLight) {
        }

        public boolean isAppearanceLightNavigationBars() {
            return false;
        }

        public void setAppearanceLightNavigationBars(boolean isLight) {
        }

        /* access modifiers changed from: package-private */
        public void addOnControllableInsetsChangedListener(OnControllableInsetsChangedListener listener) {
        }

        /* access modifiers changed from: package-private */
        public void removeOnControllableInsetsChangedListener(@NonNull OnControllableInsetsChangedListener listener) {
        }
    }

    @RequiresApi(20)
    public static class Impl20 extends Impl {
        @Nullable
        private final View mView;
        @NonNull
        protected final Window mWindow;

        Impl20(@NonNull Window window, @Nullable View view) {
            this.mWindow = window;
            this.mView = view;
        }

        /* access modifiers changed from: package-private */
        public void show(int typeMask) {
            for (int i = 1; i <= 256; i <<= 1) {
                if ((typeMask & i) != 0) {
                    showForType(i);
                }
            }
        }

        private void showForType(int type) {
            switch (type) {
                case 1:
                    unsetSystemUiFlag(4);
                    unsetWindowFlag(1024);
                    return;
                case 2:
                    unsetSystemUiFlag(2);
                    return;
                case 8:
                    View view = this.mView;
                    if (view == null || (!view.isInEditMode() && !view.onCheckIsTextEditor())) {
                        view = this.mWindow.getCurrentFocus();
                    } else {
                        view.requestFocus();
                    }
                    if (view == null) {
                        view = this.mWindow.findViewById(16908290);
                    }
                    if (view != null && view.hasWindowFocus()) {
                        final View finalView = view;
                        finalView.post(new Runnable() {
                            public void run() {
                                ((InputMethodManager) finalView.getContext().getSystemService("input_method")).showSoftInput(finalView, 0);
                            }
                        });
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        /* access modifiers changed from: package-private */
        public void hide(int typeMask) {
            for (int i = 1; i <= 256; i <<= 1) {
                if ((typeMask & i) != 0) {
                    hideForType(i);
                }
            }
        }

        private void hideForType(int type) {
            switch (type) {
                case 1:
                    setSystemUiFlag(4);
                    return;
                case 2:
                    setSystemUiFlag(2);
                    return;
                case 8:
                    ((InputMethodManager) this.mWindow.getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.mWindow.getDecorView().getWindowToken(), 0);
                    return;
                default:
                    return;
            }
        }

        /* access modifiers changed from: protected */
        public void setSystemUiFlag(int systemUiFlag) {
            View decorView = this.mWindow.getDecorView();
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | systemUiFlag);
        }

        /* access modifiers changed from: protected */
        public void unsetSystemUiFlag(int systemUiFlag) {
            View decorView = this.mWindow.getDecorView();
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & (~systemUiFlag));
        }

        /* access modifiers changed from: protected */
        public void setWindowFlag(int windowFlag) {
            this.mWindow.addFlags(windowFlag);
        }

        /* access modifiers changed from: protected */
        public void unsetWindowFlag(int windowFlag) {
            this.mWindow.clearFlags(windowFlag);
        }

        /* access modifiers changed from: package-private */
        public void controlWindowInsetsAnimation(int types, long durationMillis, Interpolator interpolator, CancellationSignal cancellationSignal, WindowInsetsAnimationControlListenerCompat listener) {
        }

        /* access modifiers changed from: package-private */
        public void setSystemBarsBehavior(int behavior) {
            switch (behavior) {
                case 0:
                    unsetSystemUiFlag(6144);
                    return;
                case 1:
                    unsetSystemUiFlag(4096);
                    setSystemUiFlag(2048);
                    return;
                case 2:
                    unsetSystemUiFlag(2048);
                    setSystemUiFlag(4096);
                    return;
                default:
                    return;
            }
        }

        /* access modifiers changed from: package-private */
        public int getSystemBarsBehavior() {
            return 0;
        }

        /* access modifiers changed from: package-private */
        public void addOnControllableInsetsChangedListener(OnControllableInsetsChangedListener listener) {
        }

        /* access modifiers changed from: package-private */
        public void removeOnControllableInsetsChangedListener(@NonNull OnControllableInsetsChangedListener listener) {
        }
    }

    @RequiresApi(23)
    public static class Impl23 extends Impl20 {
        Impl23(@NonNull Window window, @Nullable View view) {
            super(window, view);
        }

        public boolean isAppearanceLightStatusBars() {
            return (this.mWindow.getDecorView().getSystemUiVisibility() & 8192) != 0;
        }

        public void setAppearanceLightStatusBars(boolean isLight) {
            if (isLight) {
                unsetWindowFlag(67108864);
                setWindowFlag(Integer.MIN_VALUE);
                setSystemUiFlag(8192);
                return;
            }
            unsetSystemUiFlag(8192);
        }
    }

    @RequiresApi(26)
    public static class Impl26 extends Impl23 {
        Impl26(@NonNull Window window, @Nullable View view) {
            super(window, view);
        }

        public boolean isAppearanceLightNavigationBars() {
            return (this.mWindow.getDecorView().getSystemUiVisibility() & 16) != 0;
        }

        public void setAppearanceLightNavigationBars(boolean isLight) {
            if (isLight) {
                unsetWindowFlag(134217728);
                setWindowFlag(Integer.MIN_VALUE);
                setSystemUiFlag(16);
                return;
            }
            unsetSystemUiFlag(16);
        }
    }

    @RequiresApi(30)
    public static class Impl30 extends Impl {
        final WindowInsetsControllerCompat mCompatController;
        final WindowInsetsController mInsetsController;
        private final SimpleArrayMap<OnControllableInsetsChangedListener, WindowInsetsController.OnControllableInsetsChangedListener> mListeners;

        Impl30(@NonNull Window window, @NonNull WindowInsetsControllerCompat compatController) {
            this(window.getInsetsController(), compatController);
        }

        Impl30(@NonNull WindowInsetsController insetsController, @NonNull WindowInsetsControllerCompat compatController) {
            this.mListeners = new SimpleArrayMap<>();
            this.mInsetsController = insetsController;
            this.mCompatController = compatController;
        }

        /* access modifiers changed from: package-private */
        public void show(int types) {
            this.mInsetsController.show(types);
        }

        /* access modifiers changed from: package-private */
        public void hide(int types) {
            this.mInsetsController.hide(types);
        }

        public boolean isAppearanceLightStatusBars() {
            return (this.mInsetsController.getSystemBarsAppearance() & 8) != 0;
        }

        public void setAppearanceLightStatusBars(boolean isLight) {
            if (isLight) {
                this.mInsetsController.setSystemBarsAppearance(8, 8);
            } else {
                this.mInsetsController.setSystemBarsAppearance(0, 8);
            }
        }

        public boolean isAppearanceLightNavigationBars() {
            return (this.mInsetsController.getSystemBarsAppearance() & 16) != 0;
        }

        public void setAppearanceLightNavigationBars(boolean isLight) {
            if (isLight) {
                this.mInsetsController.setSystemBarsAppearance(16, 16);
            } else {
                this.mInsetsController.setSystemBarsAppearance(0, 16);
            }
        }

        /* access modifiers changed from: package-private */
        public void controlWindowInsetsAnimation(int types, long durationMillis, @Nullable Interpolator interpolator, @Nullable CancellationSignal cancellationSignal, @NonNull final WindowInsetsAnimationControlListenerCompat listener) {
            this.mInsetsController.controlWindowInsetsAnimation(types, durationMillis, interpolator, cancellationSignal, new WindowInsetsAnimationControlListener() {
                private WindowInsetsAnimationControllerCompat mCompatAnimController = null;

                public void onReady(@NonNull WindowInsetsAnimationController controller, int types) {
                    WindowInsetsAnimationControllerCompat windowInsetsAnimationControllerCompat = new WindowInsetsAnimationControllerCompat(controller);
                    this.mCompatAnimController = windowInsetsAnimationControllerCompat;
                    listener.onReady(windowInsetsAnimationControllerCompat, types);
                }

                public void onFinished(@NonNull WindowInsetsAnimationController controller) {
                    listener.onFinished(this.mCompatAnimController);
                }

                public void onCancelled(@Nullable WindowInsetsAnimationController controller) {
                    listener.onCancelled(controller == null ? null : this.mCompatAnimController);
                }
            });
        }

        /* access modifiers changed from: package-private */
        public void setSystemBarsBehavior(int behavior) {
            this.mInsetsController.setSystemBarsBehavior(behavior);
        }

        /* access modifiers changed from: package-private */
        public int getSystemBarsBehavior() {
            return this.mInsetsController.getSystemBarsBehavior();
        }

        /* access modifiers changed from: package-private */
        public void addOnControllableInsetsChangedListener(@NonNull final OnControllableInsetsChangedListener listener) {
            if (!this.mListeners.containsKey(listener)) {
                WindowInsetsController.OnControllableInsetsChangedListener fwListener = new WindowInsetsController.OnControllableInsetsChangedListener() {
                    public void onControllableInsetsChanged(@NonNull WindowInsetsController controller, int typeMask) {
                        Impl30 impl30 = Impl30.this;
                        if (impl30.mInsetsController == controller) {
                            listener.onControllableInsetsChanged(impl30.mCompatController, typeMask);
                        }
                    }
                };
                this.mListeners.put(listener, fwListener);
                this.mInsetsController.addOnControllableInsetsChangedListener(fwListener);
            }
        }

        /* access modifiers changed from: package-private */
        public void removeOnControllableInsetsChangedListener(@NonNull OnControllableInsetsChangedListener listener) {
            WindowInsetsController.OnControllableInsetsChangedListener fwListener = this.mListeners.remove(listener);
            if (fwListener != null) {
                this.mInsetsController.removeOnControllableInsetsChangedListener(fwListener);
            }
        }
    }
}
