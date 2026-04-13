package com.google.android.material.snackbar;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import androidx.annotation.IdRes;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.SnackbarManager;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseTransientBottomBar<B extends BaseTransientBottomBar<B>> {
    static final int ANIMATION_DURATION = 250;
    static final int ANIMATION_FADE_DURATION = 180;
    private static final int ANIMATION_FADE_IN_DURATION = 150;
    private static final int ANIMATION_FADE_OUT_DURATION = 75;
    public static final int ANIMATION_MODE_FADE = 1;
    public static final int ANIMATION_MODE_SLIDE = 0;
    private static final float ANIMATION_SCALE_FROM_VALUE = 0.8f;
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_LONG = 0;
    public static final int LENGTH_SHORT = -1;
    static final int MSG_DISMISS = 1;
    static final int MSG_SHOW = 0;
    private static final int[] SNACKBAR_STYLE_ATTR = {R.attr.snackbarStyle};
    /* access modifiers changed from: private */
    public static final String TAG = BaseTransientBottomBar.class.getSimpleName();
    /* access modifiers changed from: private */
    public static final boolean USE_OFFSET_API;
    @NonNull
    static final Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what) {
                case 0:
                    ((BaseTransientBottomBar) message.obj).showView();
                    return true;
                case 1:
                    ((BaseTransientBottomBar) message.obj).hideView(message.arg1);
                    return true;
                default:
                    return false;
            }
        }
    });
    @Nullable
    private final AccessibilityManager accessibilityManager;
    @Nullable
    private View anchorView;
    private final ViewTreeObserver.OnGlobalLayoutListener anchorViewLayoutListener;
    /* access modifiers changed from: private */
    public boolean anchorViewLayoutListenerEnabled;
    private Behavior behavior;
    @RequiresApi(29)
    private final Runnable bottomMarginGestureInsetRunnable;
    private List<BaseCallback<B>> callbacks;
    /* access modifiers changed from: private */
    @NonNull
    public final ContentViewCallback contentViewCallback;
    /* access modifiers changed from: private */
    public final Context context;
    private int duration;
    /* access modifiers changed from: private */
    public int extraBottomMarginAnchorView;
    /* access modifiers changed from: private */
    public int extraBottomMarginGestureInset;
    /* access modifiers changed from: private */
    public int extraBottomMarginWindowInset;
    /* access modifiers changed from: private */
    public int extraLeftMarginWindowInset;
    /* access modifiers changed from: private */
    public int extraRightMarginWindowInset;
    private boolean gestureInsetBottomIgnored;
    @NonNull
    SnackbarManager.Callback managerCallback;
    @Nullable
    private Rect originalMargins;
    @NonNull
    private final ViewGroup targetParent;
    @NonNull
    protected final SnackbarBaseLayout view;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationMode {
    }

    @Deprecated
    public interface ContentViewCallback extends ContentViewCallback {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @IntRange(from = 1)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface OnAttachStateChangeListener {
        void onViewAttachedToWindow(View view);

        void onViewDetachedFromWindow(View view);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface OnLayoutChangeListener {
        void onLayoutChange(View view, int i, int i2, int i3, int i4);
    }

    public static abstract class BaseCallback<B> {
        public static final int DISMISS_EVENT_ACTION = 1;
        public static final int DISMISS_EVENT_CONSECUTIVE = 4;
        public static final int DISMISS_EVENT_MANUAL = 3;
        public static final int DISMISS_EVENT_SWIPE = 0;
        public static final int DISMISS_EVENT_TIMEOUT = 2;

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        @Retention(RetentionPolicy.SOURCE)
        public @interface DismissEvent {
        }

        public void onDismissed(B b, int event) {
        }

        public void onShown(B b) {
        }
    }

    static {
        int i = Build.VERSION.SDK_INT;
        USE_OFFSET_API = i >= 16 && i <= 19;
    }

    protected BaseTransientBottomBar(@NonNull ViewGroup parent, @NonNull View content, @NonNull ContentViewCallback contentViewCallback2) {
        this(parent.getContext(), parent, content, contentViewCallback2);
    }

    protected BaseTransientBottomBar(@NonNull Context context2, @NonNull ViewGroup parent, @NonNull View content, @NonNull ContentViewCallback contentViewCallback2) {
        this.anchorViewLayoutListenerEnabled = false;
        this.anchorViewLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (BaseTransientBottomBar.this.anchorViewLayoutListenerEnabled) {
                    BaseTransientBottomBar baseTransientBottomBar = BaseTransientBottomBar.this;
                    int unused = baseTransientBottomBar.extraBottomMarginAnchorView = baseTransientBottomBar.calculateBottomMarginForAnchorView();
                    BaseTransientBottomBar.this.updateMargins();
                }
            }
        };
        this.bottomMarginGestureInsetRunnable = new Runnable() {
            public void run() {
                int currentInsetBottom;
                BaseTransientBottomBar baseTransientBottomBar = BaseTransientBottomBar.this;
                if (baseTransientBottomBar.view != null && baseTransientBottomBar.context != null && (currentInsetBottom = (BaseTransientBottomBar.this.getScreenHeight() - BaseTransientBottomBar.this.getViewAbsoluteBottom()) + ((int) BaseTransientBottomBar.this.view.getTranslationY())) < BaseTransientBottomBar.this.extraBottomMarginGestureInset) {
                    ViewGroup.LayoutParams layoutParams = BaseTransientBottomBar.this.view.getLayoutParams();
                    if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
                        Log.w(BaseTransientBottomBar.TAG, "Unable to apply gesture inset because layout params are not MarginLayoutParams");
                        return;
                    }
                    ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin += BaseTransientBottomBar.this.extraBottomMarginGestureInset - currentInsetBottom;
                    BaseTransientBottomBar.this.view.requestLayout();
                }
            }
        };
        this.managerCallback = new SnackbarManager.Callback() {
            public void show() {
                Handler handler = BaseTransientBottomBar.handler;
                handler.sendMessage(handler.obtainMessage(0, BaseTransientBottomBar.this));
            }

            public void dismiss(int event) {
                Handler handler = BaseTransientBottomBar.handler;
                handler.sendMessage(handler.obtainMessage(1, event, 0, BaseTransientBottomBar.this));
            }
        };
        if (parent == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null parent");
        } else if (content == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null content");
        } else if (contentViewCallback2 != null) {
            this.targetParent = parent;
            this.contentViewCallback = contentViewCallback2;
            this.context = context2;
            ThemeEnforcement.checkAppCompatTheme(context2);
            SnackbarBaseLayout snackbarBaseLayout = (SnackbarBaseLayout) LayoutInflater.from(context2).inflate(getSnackbarBaseLayoutResId(), parent, false);
            this.view = snackbarBaseLayout;
            if (content instanceof SnackbarContentLayout) {
                ((SnackbarContentLayout) content).updateActionTextColorAlphaIfNeeded(snackbarBaseLayout.getActionTextColorAlpha());
            }
            snackbarBaseLayout.addView(content);
            ViewGroup.LayoutParams layoutParams = snackbarBaseLayout.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) layoutParams;
                this.originalMargins = new Rect(marginParams.leftMargin, marginParams.topMargin, marginParams.rightMargin, marginParams.bottomMargin);
            }
            ViewCompat.setAccessibilityLiveRegion(snackbarBaseLayout, 1);
            ViewCompat.setImportantForAccessibility(snackbarBaseLayout, 1);
            ViewCompat.setFitsSystemWindows(snackbarBaseLayout, true);
            ViewCompat.setOnApplyWindowInsetsListener(snackbarBaseLayout, new OnApplyWindowInsetsListener() {
                @NonNull
                public WindowInsetsCompat onApplyWindowInsets(View v, @NonNull WindowInsetsCompat insets) {
                    int unused = BaseTransientBottomBar.this.extraBottomMarginWindowInset = insets.getSystemWindowInsetBottom();
                    int unused2 = BaseTransientBottomBar.this.extraLeftMarginWindowInset = insets.getSystemWindowInsetLeft();
                    int unused3 = BaseTransientBottomBar.this.extraRightMarginWindowInset = insets.getSystemWindowInsetRight();
                    BaseTransientBottomBar.this.updateMargins();
                    return insets;
                }
            });
            ViewCompat.setAccessibilityDelegate(snackbarBaseLayout, new AccessibilityDelegateCompat() {
                public void onInitializeAccessibilityNodeInfo(View host, @NonNull AccessibilityNodeInfoCompat info) {
                    super.onInitializeAccessibilityNodeInfo(host, info);
                    info.addAction(1048576);
                    info.setDismissable(true);
                }

                public boolean performAccessibilityAction(View host, int action, Bundle args) {
                    if (action != 1048576) {
                        return super.performAccessibilityAction(host, action, args);
                    }
                    BaseTransientBottomBar.this.dismiss();
                    return true;
                }
            });
            this.accessibilityManager = (AccessibilityManager) context2.getSystemService("accessibility");
        } else {
            throw new IllegalArgumentException("Transient bottom bar must have non-null callback");
        }
    }

    /* access modifiers changed from: private */
    public void updateMargins() {
        Rect rect;
        ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams) || (rect = this.originalMargins) == null) {
            Log.w(TAG, "Unable to update margins because layout params are not MarginLayoutParams");
            return;
        }
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) layoutParams;
        marginParams.bottomMargin = rect.bottom + (this.anchorView != null ? this.extraBottomMarginAnchorView : this.extraBottomMarginWindowInset);
        marginParams.leftMargin = rect.left + this.extraLeftMarginWindowInset;
        marginParams.rightMargin = rect.right + this.extraRightMarginWindowInset;
        this.view.requestLayout();
        if (Build.VERSION.SDK_INT >= 29 && shouldUpdateGestureInset()) {
            this.view.removeCallbacks(this.bottomMarginGestureInsetRunnable);
            this.view.post(this.bottomMarginGestureInsetRunnable);
        }
    }

    private boolean shouldUpdateGestureInset() {
        return this.extraBottomMarginGestureInset > 0 && !this.gestureInsetBottomIgnored && isSwipeDismissable();
    }

    private boolean isSwipeDismissable() {
        ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
        return (layoutParams instanceof CoordinatorLayout.LayoutParams) && (((CoordinatorLayout.LayoutParams) layoutParams).getBehavior() instanceof SwipeDismissBehavior);
    }

    /* access modifiers changed from: protected */
    @LayoutRes
    public int getSnackbarBaseLayoutResId() {
        return hasSnackbarStyleAttr() ? R.layout.mtrl_layout_snackbar : R.layout.design_layout_snackbar;
    }

    /* access modifiers changed from: protected */
    public boolean hasSnackbarStyleAttr() {
        TypedArray a = this.context.obtainStyledAttributes(SNACKBAR_STYLE_ATTR);
        int snackbarStyleResId = a.getResourceId(0, -1);
        a.recycle();
        if (snackbarStyleResId != -1) {
            return true;
        }
        return false;
    }

    @NonNull
    public B setDuration(int duration2) {
        this.duration = duration2;
        return this;
    }

    public int getDuration() {
        return this.duration;
    }

    @NonNull
    public B setGestureInsetBottomIgnored(boolean gestureInsetBottomIgnored2) {
        this.gestureInsetBottomIgnored = gestureInsetBottomIgnored2;
        return this;
    }

    public boolean isGestureInsetBottomIgnored() {
        return this.gestureInsetBottomIgnored;
    }

    public int getAnimationMode() {
        return this.view.getAnimationMode();
    }

    @NonNull
    public B setAnimationMode(int animationMode) {
        this.view.setAnimationMode(animationMode);
        return this;
    }

    @Nullable
    public View getAnchorView() {
        return this.anchorView;
    }

    @NonNull
    public B setAnchorView(@Nullable View anchorView2) {
        ViewUtils.removeOnGlobalLayoutListener(this.anchorView, this.anchorViewLayoutListener);
        this.anchorView = anchorView2;
        ViewUtils.addOnGlobalLayoutListener(anchorView2, this.anchorViewLayoutListener);
        return this;
    }

    @NonNull
    public B setAnchorView(@IdRes int anchorViewId) {
        View anchorView2 = this.targetParent.findViewById(anchorViewId);
        if (anchorView2 != null) {
            return setAnchorView(anchorView2);
        }
        throw new IllegalArgumentException("Unable to find anchor view with id: " + anchorViewId);
    }

    public boolean isAnchorViewLayoutListenerEnabled() {
        return this.anchorViewLayoutListenerEnabled;
    }

    public void setAnchorViewLayoutListenerEnabled(boolean anchorViewLayoutListenerEnabled2) {
        this.anchorViewLayoutListenerEnabled = anchorViewLayoutListenerEnabled2;
    }

    @NonNull
    public B setBehavior(Behavior behavior2) {
        this.behavior = behavior2;
        return this;
    }

    public Behavior getBehavior() {
        return this.behavior;
    }

    @NonNull
    public Context getContext() {
        return this.context;
    }

    @NonNull
    public View getView() {
        return this.view;
    }

    public void show() {
        SnackbarManager.getInstance().show(getDuration(), this.managerCallback);
    }

    public void dismiss() {
        dispatchDismiss(3);
    }

    /* access modifiers changed from: protected */
    public void dispatchDismiss(int event) {
        SnackbarManager.getInstance().dismiss(this.managerCallback, event);
    }

    @NonNull
    public B addCallback(@Nullable BaseCallback<B> callback) {
        if (callback == null) {
            return this;
        }
        if (this.callbacks == null) {
            this.callbacks = new ArrayList();
        }
        this.callbacks.add(callback);
        return this;
    }

    @NonNull
    public B removeCallback(@Nullable BaseCallback<B> callback) {
        List<BaseCallback<B>> list;
        if (callback == null || (list = this.callbacks) == null) {
            return this;
        }
        list.remove(callback);
        return this;
    }

    public boolean isShown() {
        return SnackbarManager.getInstance().isCurrent(this.managerCallback);
    }

    public boolean isShownOrQueued() {
        return SnackbarManager.getInstance().isCurrentOrNext(this.managerCallback);
    }

    /* access modifiers changed from: protected */
    @NonNull
    public SwipeDismissBehavior<? extends View> getNewBehavior() {
        return new Behavior();
    }

    /* access modifiers changed from: package-private */
    public final void showView() {
        this.view.setOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            public void onViewAttachedToWindow(View v) {
                WindowInsets insets;
                if (Build.VERSION.SDK_INT >= 29 && (insets = BaseTransientBottomBar.this.view.getRootWindowInsets()) != null) {
                    int unused = BaseTransientBottomBar.this.extraBottomMarginGestureInset = insets.getMandatorySystemGestureInsets().bottom;
                    BaseTransientBottomBar.this.updateMargins();
                }
            }

            public void onViewDetachedFromWindow(View v) {
                if (BaseTransientBottomBar.this.isShownOrQueued()) {
                    BaseTransientBottomBar.handler.post(new Runnable() {
                        public void run() {
                            BaseTransientBottomBar.this.onViewHidden(3);
                        }
                    });
                }
            }
        });
        if (this.view.getParent() == null) {
            ViewGroup.LayoutParams lp = this.view.getLayoutParams();
            if (lp instanceof CoordinatorLayout.LayoutParams) {
                setUpBehavior((CoordinatorLayout.LayoutParams) lp);
            }
            this.extraBottomMarginAnchorView = calculateBottomMarginForAnchorView();
            updateMargins();
            this.view.setVisibility(4);
            this.targetParent.addView(this.view);
        }
        if (ViewCompat.isLaidOut(this.view)) {
            showViewImpl();
        } else {
            this.view.setOnLayoutChangeListener(new OnLayoutChangeListener() {
                public void onLayoutChange(View view, int left, int top, int right, int bottom) {
                    BaseTransientBottomBar.this.view.setOnLayoutChangeListener((OnLayoutChangeListener) null);
                    BaseTransientBottomBar.this.showViewImpl();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void showViewImpl() {
        if (shouldAnimate()) {
            animateViewIn();
            return;
        }
        if (this.view.getParent() != null) {
            this.view.setVisibility(0);
        }
        onViewShown();
    }

    /* access modifiers changed from: private */
    public int getViewAbsoluteBottom() {
        int[] absoluteLocation = new int[2];
        this.view.getLocationOnScreen(absoluteLocation);
        return absoluteLocation[1] + this.view.getHeight();
    }

    /* access modifiers changed from: private */
    @RequiresApi(17)
    public int getScreenHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private void setUpBehavior(CoordinatorLayout.LayoutParams lp) {
        CoordinatorLayout.LayoutParams clp = lp;
        SwipeDismissBehavior<? extends View> behavior2 = this.behavior;
        if (behavior2 == null) {
            behavior2 = getNewBehavior();
        }
        if (behavior2 instanceof Behavior) {
            ((Behavior) behavior2).setBaseTransientBottomBar(this);
        }
        behavior2.setListener(new SwipeDismissBehavior.OnDismissListener() {
            public void onDismiss(@NonNull View view) {
                if (view.getParent() != null) {
                    view.setVisibility(8);
                }
                BaseTransientBottomBar.this.dispatchDismiss(0);
            }

            public void onDragStateChanged(int state) {
                switch (state) {
                    case 0:
                        SnackbarManager.getInstance().restoreTimeoutIfPaused(BaseTransientBottomBar.this.managerCallback);
                        return;
                    case 1:
                    case 2:
                        SnackbarManager.getInstance().pauseTimeout(BaseTransientBottomBar.this.managerCallback);
                        return;
                    default:
                        return;
                }
            }
        });
        clp.setBehavior(behavior2);
        if (this.anchorView == null) {
            clp.insetEdge = 80;
        }
    }

    /* access modifiers changed from: private */
    public int calculateBottomMarginForAnchorView() {
        View view2 = this.anchorView;
        if (view2 == null) {
            return 0;
        }
        int[] anchorViewLocation = new int[2];
        view2.getLocationOnScreen(anchorViewLocation);
        int anchorViewAbsoluteYTop = anchorViewLocation[1];
        int[] targetParentLocation = new int[2];
        this.targetParent.getLocationOnScreen(targetParentLocation);
        return (targetParentLocation[1] + this.targetParent.getHeight()) - anchorViewAbsoluteYTop;
    }

    /* access modifiers changed from: package-private */
    public void animateViewIn() {
        this.view.post(new Runnable() {
            public void run() {
                SnackbarBaseLayout snackbarBaseLayout = BaseTransientBottomBar.this.view;
                if (snackbarBaseLayout != null) {
                    if (snackbarBaseLayout.getParent() != null) {
                        BaseTransientBottomBar.this.view.setVisibility(0);
                    }
                    if (BaseTransientBottomBar.this.view.getAnimationMode() == 1) {
                        BaseTransientBottomBar.this.startFadeInAnimation();
                    } else {
                        BaseTransientBottomBar.this.startSlideInAnimation();
                    }
                }
            }
        });
    }

    private void animateViewOut(int event) {
        if (this.view.getAnimationMode() == 1) {
            startFadeOutAnimation(event);
        } else {
            startSlideOutAnimation(event);
        }
    }

    /* access modifiers changed from: private */
    public void startFadeInAnimation() {
        ValueAnimator alphaAnimator = getAlphaAnimator(0.0f, 1.0f);
        ValueAnimator scaleAnimator = getScaleAnimator(ANIMATION_SCALE_FROM_VALUE, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{alphaAnimator, scaleAnimator});
        animatorSet.setDuration(150);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                BaseTransientBottomBar.this.onViewShown();
            }
        });
        animatorSet.start();
    }

    private void startFadeOutAnimation(final int event) {
        ValueAnimator animator = getAlphaAnimator(1.0f, 0.0f);
        animator.setDuration(75);
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                BaseTransientBottomBar.this.onViewHidden(event);
            }
        });
        animator.start();
    }

    private ValueAnimator getAlphaAnimator(float... alphaValues) {
        ValueAnimator animator = ValueAnimator.ofFloat(alphaValues);
        animator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                BaseTransientBottomBar.this.view.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        return animator;
    }

    private ValueAnimator getScaleAnimator(float... scaleValues) {
        ValueAnimator animator = ValueAnimator.ofFloat(scaleValues);
        animator.setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                float scale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                BaseTransientBottomBar.this.view.setScaleX(scale);
                BaseTransientBottomBar.this.view.setScaleY(scale);
            }
        });
        return animator;
    }

    /* access modifiers changed from: private */
    public void startSlideInAnimation() {
        int translationYBottom = getTranslationYBottom();
        if (USE_OFFSET_API) {
            ViewCompat.offsetTopAndBottom(this.view, translationYBottom);
        } else {
            this.view.setTranslationY((float) translationYBottom);
        }
        ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(new int[]{translationYBottom, 0});
        animator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        animator.setDuration(250);
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                BaseTransientBottomBar.this.contentViewCallback.animateContentIn(70, 180);
            }

            public void onAnimationEnd(Animator animator) {
                BaseTransientBottomBar.this.onViewShown();
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(translationYBottom) {
            private int previousAnimatedIntValue;
            final /* synthetic */ int val$translationYBottom;

            {
                this.val$translationYBottom = r2;
                this.previousAnimatedIntValue = r2;
            }

            public void onAnimationUpdate(@NonNull ValueAnimator animator) {
                int currentAnimatedIntValue = ((Integer) animator.getAnimatedValue()).intValue();
                if (BaseTransientBottomBar.USE_OFFSET_API) {
                    ViewCompat.offsetTopAndBottom(BaseTransientBottomBar.this.view, currentAnimatedIntValue - this.previousAnimatedIntValue);
                } else {
                    BaseTransientBottomBar.this.view.setTranslationY((float) currentAnimatedIntValue);
                }
                this.previousAnimatedIntValue = currentAnimatedIntValue;
            }
        });
        animator.start();
    }

    private void startSlideOutAnimation(final int event) {
        ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(new int[]{0, getTranslationYBottom()});
        animator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        animator.setDuration(250);
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                BaseTransientBottomBar.this.contentViewCallback.animateContentOut(0, 180);
            }

            public void onAnimationEnd(Animator animator) {
                BaseTransientBottomBar.this.onViewHidden(event);
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private int previousAnimatedIntValue = 0;

            public void onAnimationUpdate(@NonNull ValueAnimator animator) {
                int currentAnimatedIntValue = ((Integer) animator.getAnimatedValue()).intValue();
                if (BaseTransientBottomBar.USE_OFFSET_API) {
                    ViewCompat.offsetTopAndBottom(BaseTransientBottomBar.this.view, currentAnimatedIntValue - this.previousAnimatedIntValue);
                } else {
                    BaseTransientBottomBar.this.view.setTranslationY((float) currentAnimatedIntValue);
                }
                this.previousAnimatedIntValue = currentAnimatedIntValue;
            }
        });
        animator.start();
    }

    private int getTranslationYBottom() {
        int translationY = this.view.getHeight();
        ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return translationY + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }
        return translationY;
    }

    /* access modifiers changed from: package-private */
    public final void hideView(int event) {
        if (!shouldAnimate() || this.view.getVisibility() != 0) {
            onViewHidden(event);
        } else {
            animateViewOut(event);
        }
    }

    /* access modifiers changed from: package-private */
    public void onViewShown() {
        SnackbarManager.getInstance().onShown(this.managerCallback);
        List<BaseCallback<B>> list = this.callbacks;
        if (list != null) {
            for (int i = list.size() - 1; i >= 0; i--) {
                this.callbacks.get(i).onShown(this);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onViewHidden(int event) {
        SnackbarManager.getInstance().onDismissed(this.managerCallback);
        List<BaseCallback<B>> list = this.callbacks;
        if (list != null) {
            for (int i = list.size() - 1; i >= 0; i--) {
                this.callbacks.get(i).onDismissed(this, event);
            }
        }
        ViewParent parent = this.view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(this.view);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean shouldAnimate() {
        AccessibilityManager accessibilityManager2 = this.accessibilityManager;
        if (accessibilityManager2 == null) {
            return true;
        }
        List<AccessibilityServiceInfo> serviceList = accessibilityManager2.getEnabledAccessibilityServiceList(1);
        if (serviceList == null || !serviceList.isEmpty()) {
            return false;
        }
        return true;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static class SnackbarBaseLayout extends FrameLayout {
        private static final View.OnTouchListener consumeAllTouchListener = new View.OnTouchListener() {
            @SuppressLint({"ClickableViewAccessibility"})
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        };
        private final float actionTextColorAlpha;
        private int animationMode;
        private final float backgroundOverlayColorAlpha;
        private ColorStateList backgroundTint;
        private PorterDuff.Mode backgroundTintMode;
        private OnAttachStateChangeListener onAttachStateChangeListener;
        private OnLayoutChangeListener onLayoutChangeListener;

        protected SnackbarBaseLayout(@NonNull Context context) {
            this(context, (AttributeSet) null);
        }

        protected SnackbarBaseLayout(@NonNull Context context, AttributeSet attrs) {
            super(MaterialThemeOverlay.wrap(context, attrs, 0, 0), attrs);
            Context context2 = getContext();
            TypedArray a = context2.obtainStyledAttributes(attrs, R.styleable.SnackbarLayout);
            int i = R.styleable.SnackbarLayout_elevation;
            if (a.hasValue(i)) {
                ViewCompat.setElevation(this, (float) a.getDimensionPixelSize(i, 0));
            }
            this.animationMode = a.getInt(R.styleable.SnackbarLayout_animationMode, 0);
            this.backgroundOverlayColorAlpha = a.getFloat(R.styleable.SnackbarLayout_backgroundOverlayColorAlpha, 1.0f);
            setBackgroundTintList(MaterialResources.getColorStateList(context2, a, R.styleable.SnackbarLayout_backgroundTint));
            setBackgroundTintMode(ViewUtils.parseTintMode(a.getInt(R.styleable.SnackbarLayout_backgroundTintMode, -1), PorterDuff.Mode.SRC_IN));
            this.actionTextColorAlpha = a.getFloat(R.styleable.SnackbarLayout_actionTextColorAlpha, 1.0f);
            a.recycle();
            setOnTouchListener(consumeAllTouchListener);
            setFocusable(true);
            if (getBackground() == null) {
                ViewCompat.setBackground(this, createThemedBackground());
            }
        }

        public void setBackground(@Nullable Drawable drawable) {
            setBackgroundDrawable(drawable);
        }

        public void setBackgroundDrawable(@Nullable Drawable drawable) {
            if (!(drawable == null || this.backgroundTint == null)) {
                drawable = DrawableCompat.wrap(drawable.mutate());
                DrawableCompat.setTintList(drawable, this.backgroundTint);
                DrawableCompat.setTintMode(drawable, this.backgroundTintMode);
            }
            super.setBackgroundDrawable(drawable);
        }

        public void setBackgroundTintList(@Nullable ColorStateList backgroundTint2) {
            this.backgroundTint = backgroundTint2;
            if (getBackground() != null) {
                Drawable wrappedBackground = DrawableCompat.wrap(getBackground().mutate());
                DrawableCompat.setTintList(wrappedBackground, backgroundTint2);
                DrawableCompat.setTintMode(wrappedBackground, this.backgroundTintMode);
                if (wrappedBackground != getBackground()) {
                    super.setBackgroundDrawable(wrappedBackground);
                }
            }
        }

        public void setBackgroundTintMode(@Nullable PorterDuff.Mode backgroundTintMode2) {
            this.backgroundTintMode = backgroundTintMode2;
            if (getBackground() != null) {
                Drawable wrappedBackground = DrawableCompat.wrap(getBackground().mutate());
                DrawableCompat.setTintMode(wrappedBackground, backgroundTintMode2);
                if (wrappedBackground != getBackground()) {
                    super.setBackgroundDrawable(wrappedBackground);
                }
            }
        }

        public void setOnClickListener(@Nullable View.OnClickListener onClickListener) {
            setOnTouchListener(onClickListener != null ? null : consumeAllTouchListener);
            super.setOnClickListener(onClickListener);
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            OnLayoutChangeListener onLayoutChangeListener2 = this.onLayoutChangeListener;
            if (onLayoutChangeListener2 != null) {
                onLayoutChangeListener2.onLayoutChange(this, l, t, r, b);
            }
        }

        /* access modifiers changed from: protected */
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            OnAttachStateChangeListener onAttachStateChangeListener2 = this.onAttachStateChangeListener;
            if (onAttachStateChangeListener2 != null) {
                onAttachStateChangeListener2.onViewAttachedToWindow(this);
            }
            ViewCompat.requestApplyInsets(this);
        }

        /* access modifiers changed from: protected */
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            OnAttachStateChangeListener onAttachStateChangeListener2 = this.onAttachStateChangeListener;
            if (onAttachStateChangeListener2 != null) {
                onAttachStateChangeListener2.onViewDetachedFromWindow(this);
            }
        }

        /* access modifiers changed from: package-private */
        public void setOnLayoutChangeListener(OnLayoutChangeListener onLayoutChangeListener2) {
            this.onLayoutChangeListener = onLayoutChangeListener2;
        }

        /* access modifiers changed from: package-private */
        public void setOnAttachStateChangeListener(OnAttachStateChangeListener listener) {
            this.onAttachStateChangeListener = listener;
        }

        /* access modifiers changed from: package-private */
        public int getAnimationMode() {
            return this.animationMode;
        }

        /* access modifiers changed from: package-private */
        public void setAnimationMode(int animationMode2) {
            this.animationMode = animationMode2;
        }

        /* access modifiers changed from: package-private */
        public float getBackgroundOverlayColorAlpha() {
            return this.backgroundOverlayColorAlpha;
        }

        /* access modifiers changed from: package-private */
        public float getActionTextColorAlpha() {
            return this.actionTextColorAlpha;
        }

        @NonNull
        private Drawable createThemedBackground() {
            float cornerRadius = getResources().getDimension(R.dimen.mtrl_snackbar_background_corner_radius);
            GradientDrawable background = new GradientDrawable();
            background.setShape(0);
            background.setCornerRadius(cornerRadius);
            background.setColor(MaterialColors.layer(this, R.attr.colorSurface, R.attr.colorOnSurface, getBackgroundOverlayColorAlpha()));
            if (this.backgroundTint == null) {
                return DrawableCompat.wrap(background);
            }
            Drawable wrappedDrawable = DrawableCompat.wrap(background);
            DrawableCompat.setTintList(wrappedDrawable, this.backgroundTint);
            return wrappedDrawable;
        }
    }

    public static class Behavior extends SwipeDismissBehavior<View> {
        @NonNull
        private final BehaviorDelegate delegate = new BehaviorDelegate(this);

        /* access modifiers changed from: private */
        public void setBaseTransientBottomBar(@NonNull BaseTransientBottomBar<?> baseTransientBottomBar) {
            this.delegate.setBaseTransientBottomBar(baseTransientBottomBar);
        }

        public boolean canSwipeDismissView(View child) {
            return this.delegate.canSwipeDismissView(child);
        }

        public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull MotionEvent event) {
            this.delegate.onInterceptTouchEvent(parent, child, event);
            return super.onInterceptTouchEvent(parent, child, event);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static class BehaviorDelegate {
        private SnackbarManager.Callback managerCallback;

        public BehaviorDelegate(@NonNull SwipeDismissBehavior<?> behavior) {
            behavior.setStartAlphaSwipeDistance(0.1f);
            behavior.setEndAlphaSwipeDistance(0.6f);
            behavior.setSwipeDirection(0);
        }

        public void setBaseTransientBottomBar(@NonNull BaseTransientBottomBar<?> baseTransientBottomBar) {
            this.managerCallback = baseTransientBottomBar.managerCallback;
        }

        public boolean canSwipeDismissView(View child) {
            return child instanceof SnackbarBaseLayout;
        }

        public void onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull MotionEvent event) {
            switch (event.getActionMasked()) {
                case 0:
                    if (parent.isPointInChildBounds(child, (int) event.getX(), (int) event.getY())) {
                        SnackbarManager.getInstance().pauseTimeout(this.managerCallback);
                        return;
                    }
                    return;
                case 1:
                case 3:
                    SnackbarManager.getInstance().restoreTimeoutIfPaused(this.managerCallback);
                    return;
                default:
                    return;
            }
        }
    }
}
