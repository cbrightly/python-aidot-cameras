package com.lzf.easyfloat.core;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.IBinder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import androidx.core.view.GravityCompat;
import com.google.android.material.badge.BadgeDrawable;
import com.lzf.easyfloat.EasyFloatMessageKt;
import com.lzf.easyfloat.anim.AnimatorManager;
import com.lzf.easyfloat.data.FloatConfig;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.interfaces.FloatCallbacks;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.utils.DisplayUtils;
import com.lzf.easyfloat.utils.InputMethodUtils;
import com.lzf.easyfloat.utils.LifecycleUtils;
import com.lzf.easyfloat.utils.Logger;
import com.lzf.easyfloat.widget.ParentFrameLayout;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FloatingWindowHelper.kt */
public final class FloatingWindowHelper {
    @NotNull
    private FloatConfig config;
    @NotNull
    private final Context context;
    @Nullable
    private Animator enterAnimator;
    @Nullable
    private ParentFrameLayout frameLayout;
    /* access modifiers changed from: private */
    public int lastLayoutMeasureHeight = -1;
    /* access modifiers changed from: private */
    public int lastLayoutMeasureWidth = -1;
    public WindowManager.LayoutParams params;
    /* access modifiers changed from: private */
    public TouchUtils touchUtils;
    public WindowManager windowManager;

    /* compiled from: FloatingWindowHelper.kt */
    public interface CreateCallback {
        void onCreate(boolean z);
    }

    public FloatingWindowHelper(@NotNull Context context2, @NotNull FloatConfig config2) {
        k.e(context2, "context");
        k.e(config2, "config");
        this.context = context2;
        this.config = config2;
    }

    @NotNull
    public final FloatConfig getConfig() {
        return this.config;
    }

    @NotNull
    public final Context getContext() {
        return this.context;
    }

    public final void setConfig(@NotNull FloatConfig floatConfig) {
        k.e(floatConfig, "<set-?>");
        this.config = floatConfig;
    }

    @NotNull
    public final WindowManager getWindowManager() {
        WindowManager windowManager2 = this.windowManager;
        if (windowManager2 != null) {
            return windowManager2;
        }
        k.t("windowManager");
        return null;
    }

    public final void setWindowManager(@NotNull WindowManager windowManager2) {
        k.e(windowManager2, "<set-?>");
        this.windowManager = windowManager2;
    }

    @NotNull
    public final WindowManager.LayoutParams getParams() {
        WindowManager.LayoutParams layoutParams = this.params;
        if (layoutParams != null) {
            return layoutParams;
        }
        k.t("params");
        return null;
    }

    public final void setParams(@NotNull WindowManager.LayoutParams layoutParams) {
        k.e(layoutParams, "<set-?>");
        this.params = layoutParams;
    }

    @Nullable
    public final ParentFrameLayout getFrameLayout() {
        return this.frameLayout;
    }

    public final void setFrameLayout(@Nullable ParentFrameLayout parentFrameLayout) {
        this.frameLayout = parentFrameLayout;
    }

    public final void createWindow(@NotNull CreateCallback callback) {
        FloatCallbacks.Builder builder;
        q<Boolean, String, View, x> createdResult$easyfloat_release;
        View $this$createWindow_u24lambda_u2d1;
        k.e(callback, "callback");
        if (this.config.getShowPattern() == ShowPattern.CURRENT_ACTIVITY && getToken() == null) {
            Activity activity = getActivity();
            if (activity == null || ($this$createWindow_u24lambda_u2d1 = activity.findViewById(16908290)) == null) {
                callback.onCreate(false);
                OnFloatCallbacks callbacks = this.config.getCallbacks();
                if (callbacks != null) {
                    callbacks.createdResult(false, EasyFloatMessageKt.WARN_ACTIVITY_NULL, (View) null);
                }
                FloatCallbacks floatCallbacks = this.config.getFloatCallbacks();
                if (floatCallbacks != null && (builder = floatCallbacks.getBuilder()) != null && (createdResult$easyfloat_release = builder.getCreatedResult$easyfloat_release()) != null) {
                    createdResult$easyfloat_release.invoke(false, EasyFloatMessageKt.WARN_ACTIVITY_NULL, null);
                    return;
                }
                return;
            }
            $this$createWindow_u24lambda_u2d1.post(new b(callback, this));
            return;
        }
        callback.onCreate(createWindowInner());
    }

    /* access modifiers changed from: private */
    /* renamed from: createWindow$lambda-1$lambda-0  reason: not valid java name */
    public static final void m9createWindow$lambda1$lambda0(CreateCallback $callback, FloatingWindowHelper this$0) {
        k.e($callback, "$callback");
        k.e(this$0, "this$0");
        $callback.onCreate(this$0.createWindowInner());
    }

    private final boolean createWindowInner() {
        FloatCallbacks.Builder builder;
        q<Boolean, String, View, x> createdResult$easyfloat_release;
        try {
            this.touchUtils = new TouchUtils(this.context, this.config);
            initParams();
            addView();
            this.config.setShow(true);
            return true;
        } catch (Exception e) {
            OnFloatCallbacks callbacks = this.config.getCallbacks();
            if (callbacks != null) {
                callbacks.createdResult(false, String.valueOf(e), (View) null);
            }
            FloatCallbacks floatCallbacks = this.config.getFloatCallbacks();
            if (!(floatCallbacks == null || (builder = floatCallbacks.getBuilder()) == null || (createdResult$easyfloat_release = builder.getCreatedResult$easyfloat_release()) == null)) {
                createdResult$easyfloat_release.invoke(false, String.valueOf(e), null);
            }
            return false;
        }
    }

    private final void initParams() {
        int i;
        int i2;
        Object systemService = this.context.getSystemService("window");
        if (systemService != null) {
            setWindowManager((WindowManager) systemService);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            WindowManager.LayoutParams $this$initParams_u24lambda_u2d2 = layoutParams;
            if (getConfig().getShowPattern() == ShowPattern.CURRENT_ACTIVITY) {
                $this$initParams_u24lambda_u2d2.type = 1000;
                $this$initParams_u24lambda_u2d2.token = getToken();
            } else {
                if (Build.VERSION.SDK_INT >= 26) {
                    i2 = 2038;
                } else {
                    i2 = 2002;
                }
                $this$initParams_u24lambda_u2d2.type = i2;
            }
            $this$initParams_u24lambda_u2d2.format = 1;
            $this$initParams_u24lambda_u2d2.gravity = BadgeDrawable.TOP_START;
            if (getConfig().getImmersionStatusBar()) {
                i = 552;
            } else {
                i = 40;
            }
            $this$initParams_u24lambda_u2d2.flags = i;
            int i3 = -1;
            $this$initParams_u24lambda_u2d2.width = getConfig().getWidthMatch() ? -1 : -2;
            if (!getConfig().getHeightMatch()) {
                i3 = -2;
            }
            $this$initParams_u24lambda_u2d2.height = i3;
            if (getConfig().getImmersionStatusBar() && getConfig().getHeightMatch()) {
                $this$initParams_u24lambda_u2d2.height = DisplayUtils.INSTANCE.getScreenHeight(getContext());
            }
            if (!k.a(getConfig().getLocationPair(), new n(0, 0))) {
                $this$initParams_u24lambda_u2d2.x = getConfig().getLocationPair().getFirst().intValue();
                $this$initParams_u24lambda_u2d2.y = getConfig().getLocationPair().getSecond().intValue();
            }
            x xVar = x.a;
            setParams(layoutParams);
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.WindowManager");
    }

    private final Activity getActivity() {
        Context context2 = this.context;
        return context2 instanceof Activity ? (Activity) context2 : LifecycleUtils.INSTANCE.getTopActivity();
    }

    private final IBinder getToken() {
        Window window;
        View decorView;
        Activity activity = getActivity();
        if (activity == null || (window = activity.getWindow()) == null || (decorView = window.getDecorView()) == null) {
            return null;
        }
        return decorView.getWindowToken();
    }

    private final void addView() {
        ParentFrameLayout parentFrameLayout = new ParentFrameLayout(this.context, this.config, (AttributeSet) null, 0, 12, (DefaultConstructorMarker) null);
        this.frameLayout = parentFrameLayout;
        if (parentFrameLayout != null) {
            parentFrameLayout.setTag(this.config.getFloatTag());
        }
        View floatingView = this.config.getLayoutView();
        if (floatingView == null) {
            floatingView = null;
        } else {
            View it = floatingView;
            ParentFrameLayout frameLayout2 = getFrameLayout();
            if (frameLayout2 != null) {
                frameLayout2.addView(it);
            }
        }
        if (floatingView == null) {
            LayoutInflater from = LayoutInflater.from(this.context);
            Integer layoutId = this.config.getLayoutId();
            k.c(layoutId);
            floatingView = from.inflate(layoutId.intValue(), this.frameLayout, true);
        }
        floatingView.setVisibility(4);
        getWindowManager().addView(this.frameLayout, getParams());
        ParentFrameLayout parentFrameLayout2 = this.frameLayout;
        if (parentFrameLayout2 != null) {
            parentFrameLayout2.setTouchListener(new FloatingWindowHelper$addView$1(this));
        }
        ParentFrameLayout parentFrameLayout3 = this.frameLayout;
        if (parentFrameLayout3 != null) {
            parentFrameLayout3.setLayoutListener(new FloatingWindowHelper$addView$2(this, floatingView));
        }
        setChangedListener();
    }

    private final void setChangedListener() {
        ViewTreeObserver viewTreeObserver;
        ParentFrameLayout $this$setChangedListener_u24lambda_u2d5 = this.frameLayout;
        if ($this$setChangedListener_u24lambda_u2d5 != null && (viewTreeObserver = $this$setChangedListener_u24lambda_u2d5.getViewTreeObserver()) != null) {
            viewTreeObserver.addOnGlobalLayoutListener(new a(this, $this$setChangedListener_u24lambda_u2d5));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: setChangedListener$lambda-5$lambda-4  reason: not valid java name */
    public static final void m10setChangedListener$lambda5$lambda4(FloatingWindowHelper this$0, ParentFrameLayout $this_apply) {
        k.e(this$0, "this$0");
        k.e($this_apply, "$this_apply");
        int i = this$0.lastLayoutMeasureWidth;
        boolean z = false;
        boolean filterInvalidVal = i == -1 || this$0.lastLayoutMeasureHeight == -1;
        if (i == $this_apply.getMeasuredWidth() && this$0.lastLayoutMeasureHeight == $this_apply.getMeasuredHeight()) {
            z = true;
        }
        boolean filterEqualVal = z;
        if (!filterInvalidVal && !filterEqualVal) {
            if ((this$0.getConfig().getLayoutChangedGravity() & GravityCompat.START) != 8388611) {
                if ((this$0.getConfig().getLayoutChangedGravity() & GravityCompat.END) == 8388613) {
                    this$0.getParams().x -= $this_apply.getMeasuredWidth() - this$0.lastLayoutMeasureWidth;
                } else if ((this$0.getConfig().getLayoutChangedGravity() & 1) == 1 || (this$0.getConfig().getLayoutChangedGravity() & 17) == 17) {
                    this$0.getParams().x += (this$0.lastLayoutMeasureWidth / 2) - ($this_apply.getMeasuredWidth() / 2);
                }
            }
            if ((this$0.getConfig().getLayoutChangedGravity() & 48) != 48) {
                if ((this$0.getConfig().getLayoutChangedGravity() & 80) == 80) {
                    this$0.getParams().y -= $this_apply.getMeasuredHeight() - this$0.lastLayoutMeasureHeight;
                } else if ((this$0.getConfig().getLayoutChangedGravity() & 16) == 16 || (this$0.getConfig().getLayoutChangedGravity() & 17) == 17) {
                    this$0.getParams().y += (this$0.lastLayoutMeasureHeight / 2) - ($this_apply.getMeasuredHeight() / 2);
                }
            }
            this$0.lastLayoutMeasureWidth = $this_apply.getMeasuredWidth();
            this$0.lastLayoutMeasureHeight = $this_apply.getMeasuredHeight();
            this$0.getWindowManager().updateViewLayout(this$0.getFrameLayout(), this$0.getParams());
        }
    }

    /* access modifiers changed from: private */
    public final void initEditText() {
        ParentFrameLayout it;
        if (this.config.getHasEditText() && (it = this.frameLayout) != null) {
            traverseViewGroup(it);
        }
    }

    private final void traverseViewGroup(View view) {
        if (view != null) {
            View it = view;
            if (it instanceof ViewGroup) {
                int i = 0;
                int childCount = ((ViewGroup) it).getChildCount();
                if (childCount > 0) {
                    do {
                        int i2 = i;
                        i++;
                        View child = ((ViewGroup) it).getChildAt(i2);
                        if (child instanceof ViewGroup) {
                            traverseViewGroup(child);
                            continue;
                        } else {
                            k.d(child, "child");
                            checkEditText(child);
                            continue;
                        }
                    } while (i < childCount);
                    return;
                }
                return;
            }
            checkEditText(it);
        }
    }

    private final void checkEditText(View view) {
        if (view instanceof EditText) {
            InputMethodUtils.INSTANCE.initInputMethod$easyfloat_release((EditText) view, this.config.getFloatTag());
        }
    }

    /* access modifiers changed from: private */
    @SuppressLint({"RtlHardcoded"})
    public final void setGravity(View view) {
        int statusBarHeight = 0;
        if (k.a(this.config.getLocationPair(), new n(0, 0)) && view != null) {
            Rect parentRect = new Rect();
            getWindowManager().getDefaultDisplay().getRectSize(parentRect);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            if (location[1] > getParams().y) {
                statusBarHeight = DisplayUtils.INSTANCE.statusBarHeight(view);
            }
            int parentBottom = this.config.getDisplayHeight().getDisplayRealHeight(this.context) - statusBarHeight;
            switch (this.config.getGravity()) {
                case 1:
                case 49:
                    getParams().x = (parentRect.right - view.getWidth()) >> 1;
                    break;
                case 5:
                case 53:
                case GravityCompat.END:
                case BadgeDrawable.TOP_END:
                    getParams().x = parentRect.right - view.getWidth();
                    break;
                case 16:
                case 19:
                case 8388627:
                    getParams().y = (parentBottom - view.getHeight()) >> 1;
                    break;
                case 17:
                    getParams().x = (parentRect.right - view.getWidth()) >> 1;
                    getParams().y = (parentBottom - view.getHeight()) >> 1;
                    break;
                case 21:
                case 8388629:
                    getParams().x = parentRect.right - view.getWidth();
                    getParams().y = (parentBottom - view.getHeight()) >> 1;
                    break;
                case 80:
                case 83:
                case BadgeDrawable.BOTTOM_START:
                    getParams().y = parentBottom - view.getHeight();
                    break;
                case 81:
                    getParams().x = (parentRect.right - view.getWidth()) >> 1;
                    getParams().y = parentBottom - view.getHeight();
                    break;
                case 85:
                case BadgeDrawable.BOTTOM_END:
                    getParams().x = parentRect.right - view.getWidth();
                    getParams().y = parentBottom - view.getHeight();
                    break;
            }
            getParams().x += this.config.getOffsetPair().getFirst().intValue();
            getParams().y += this.config.getOffsetPair().getSecond().intValue();
            if (this.config.getImmersionStatusBar()) {
                if (this.config.getShowPattern() != ShowPattern.CURRENT_ACTIVITY) {
                    getParams().y -= statusBarHeight;
                }
            } else if (this.config.getShowPattern() == ShowPattern.CURRENT_ACTIVITY) {
                getParams().y += statusBarHeight;
            }
            getWindowManager().updateViewLayout(view, getParams());
        }
    }

    public static /* synthetic */ void setVisible$default(FloatingWindowHelper floatingWindowHelper, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        floatingWindowHelper.setVisible(i, z);
    }

    public final void setVisible(int visible, boolean needShow) {
        FloatCallbacks.Builder builder;
        l<View, x> hide$easyfloat_release;
        FloatCallbacks.Builder builder2;
        l<View, x> show$easyfloat_release;
        ParentFrameLayout parentFrameLayout = this.frameLayout;
        if (parentFrameLayout != null) {
            k.c(parentFrameLayout);
            if (parentFrameLayout.getChildCount() >= 1) {
                this.config.setNeedShow$easyfloat_release(needShow);
                ParentFrameLayout parentFrameLayout2 = this.frameLayout;
                k.c(parentFrameLayout2);
                parentFrameLayout2.setVisibility(visible);
                ParentFrameLayout parentFrameLayout3 = this.frameLayout;
                k.c(parentFrameLayout3);
                View view = parentFrameLayout3.getChildAt(0);
                if (visible == 0) {
                    this.config.setShow(true);
                    OnFloatCallbacks callbacks = this.config.getCallbacks();
                    if (callbacks != null) {
                        k.d(view, "view");
                        callbacks.show(view);
                    }
                    FloatCallbacks floatCallbacks = this.config.getFloatCallbacks();
                    if (floatCallbacks != null && (builder2 = floatCallbacks.getBuilder()) != null && (show$easyfloat_release = builder2.getShow$easyfloat_release()) != null) {
                        k.d(view, "view");
                        show$easyfloat_release.invoke(view);
                        return;
                    }
                    return;
                }
                this.config.setShow(false);
                OnFloatCallbacks callbacks2 = this.config.getCallbacks();
                if (callbacks2 != null) {
                    k.d(view, "view");
                    callbacks2.hide(view);
                }
                FloatCallbacks floatCallbacks2 = this.config.getFloatCallbacks();
                if (floatCallbacks2 != null && (builder = floatCallbacks2.getBuilder()) != null && (hide$easyfloat_release = builder.getHide$easyfloat_release()) != null) {
                    k.d(view, "view");
                    hide$easyfloat_release.invoke(view);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public final void enterAnim(View floatingView) {
        if (this.frameLayout != null && !this.config.isAnim()) {
            ParentFrameLayout parentFrameLayout = this.frameLayout;
            k.c(parentFrameLayout);
            Animator enterAnim = new AnimatorManager(parentFrameLayout, getParams(), getWindowManager(), this.config).enterAnim();
            if (enterAnim == null) {
                enterAnim = null;
            } else {
                Animator $this$enterAnim_u24lambda_u2d8 = enterAnim;
                getParams().flags = 552;
                $this$enterAnim_u24lambda_u2d8.addListener(new FloatingWindowHelper$enterAnim$1$1(this, floatingView));
                $this$enterAnim_u24lambda_u2d8.start();
                x xVar = x.a;
            }
            this.enterAnimator = enterAnim;
            if (enterAnim == null) {
                floatingView.setVisibility(0);
                getWindowManager().updateViewLayout(this.frameLayout, getParams());
            }
        }
    }

    public final void exitAnim() {
        if (this.frameLayout == null) {
            return;
        }
        if (!this.config.isAnim() || this.enterAnimator != null) {
            Animator animator = this.enterAnimator;
            if (animator != null) {
                animator.cancel();
            }
            ParentFrameLayout parentFrameLayout = this.frameLayout;
            k.c(parentFrameLayout);
            Animator animator2 = new AnimatorManager(parentFrameLayout, getParams(), getWindowManager(), this.config).exitAnim();
            if (animator2 == null) {
                remove$default(this, false, 1, (Object) null);
            } else if (!this.config.isAnim()) {
                this.config.setAnim(true);
                getParams().flags = 552;
                animator2.addListener(new FloatingWindowHelper$exitAnim$1(this));
                animator2.start();
            }
        }
    }

    public static /* synthetic */ void remove$default(FloatingWindowHelper floatingWindowHelper, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        floatingWindowHelper.remove(z);
    }

    public final void remove(boolean force) {
        try {
            this.config.setAnim(false);
            FloatingWindowManager.INSTANCE.remove(this.config.getFloatTag());
            WindowManager $this$remove_u24lambda_u2d9 = getWindowManager();
            if (force) {
                $this$remove_u24lambda_u2d9.removeViewImmediate(getFrameLayout());
            } else {
                $this$remove_u24lambda_u2d9.removeView(getFrameLayout());
            }
        } catch (Exception e) {
            Logger.INSTANCE.e(k.l("浮窗关闭出现异常：", e));
        }
    }

    public static /* synthetic */ void updateFloat$default(FloatingWindowHelper floatingWindowHelper, int i, int i2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i = -1;
        }
        if ((i5 & 2) != 0) {
            i2 = -1;
        }
        if ((i5 & 4) != 0) {
            i3 = -1;
        }
        if ((i5 & 8) != 0) {
            i4 = -1;
        }
        floatingWindowHelper.updateFloat(i, i2, i3, i4);
    }

    public final void updateFloat(int x, int y, int width, int height) {
        ParentFrameLayout it = this.frameLayout;
        if (it != null) {
            if (x == -1 && y == -1 && width == -1 && height == -1) {
                it.postDelayed(new c(this, it), 200);
                return;
            }
            if (x != -1) {
                getParams().x = x;
            }
            if (y != -1) {
                getParams().y = y;
            }
            if (width != -1) {
                getParams().width = width;
            }
            if (height != -1) {
                getParams().height = height;
            }
            getWindowManager().updateViewLayout(it, getParams());
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: updateFloat$lambda-11$lambda-10  reason: not valid java name */
    public static final void m11updateFloat$lambda11$lambda10(FloatingWindowHelper this$0, ParentFrameLayout $it) {
        k.e(this$0, "this$0");
        k.e($it, "$it");
        TouchUtils touchUtils2 = this$0.touchUtils;
        if (touchUtils2 == null) {
            k.t("touchUtils");
            touchUtils2 = null;
        }
        touchUtils2.updateFloat($it, this$0.getParams(), this$0.getWindowManager());
    }
}
