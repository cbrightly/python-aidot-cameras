package com.lzf.easyfloat.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.lzf.easyfloat.data.FloatConfig;
import com.lzf.easyfloat.interfaces.FloatCallbacks;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.interfaces.OnFloatTouchListener;
import com.lzf.easyfloat.utils.InputMethodUtils;
import kotlin.jvm.functions.a;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressLint({"ViewConstructor"})
/* compiled from: ParentFrameLayout.kt */
public final class ParentFrameLayout extends FrameLayout {
    @NotNull
    private final FloatConfig config;
    private boolean isCreated;
    @Nullable
    private OnLayoutListener layoutListener;
    @Nullable
    private OnFloatTouchListener touchListener;

    /* compiled from: ParentFrameLayout.kt */
    public interface OnLayoutListener {
        void onLayout();
    }

    public void _$_clearFindViewByIdCache() {
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ParentFrameLayout(Context context, FloatConfig floatConfig, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, floatConfig, (i2 & 4) != 0 ? null : attributeSet, (i2 & 8) != 0 ? 0 : i);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ParentFrameLayout(@NotNull Context context, @NotNull FloatConfig config2, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        k.e(context, "context");
        k.e(config2, "config");
        this.config = config2;
    }

    @Nullable
    public final OnFloatTouchListener getTouchListener() {
        return this.touchListener;
    }

    public final void setTouchListener(@Nullable OnFloatTouchListener onFloatTouchListener) {
        this.touchListener = onFloatTouchListener;
    }

    @Nullable
    public final OnLayoutListener getLayoutListener() {
        return this.layoutListener;
    }

    public final void setLayoutListener(@Nullable OnLayoutListener onLayoutListener) {
        this.layoutListener = onLayoutListener;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!this.isCreated) {
            this.isCreated = true;
            OnLayoutListener onLayoutListener = this.layoutListener;
            if (onLayoutListener != null) {
                onLayoutListener.onLayout();
            }
        }
    }

    public boolean onInterceptTouchEvent(@Nullable MotionEvent event) {
        OnFloatTouchListener onFloatTouchListener;
        if (!(event == null || (onFloatTouchListener = this.touchListener) == null)) {
            onFloatTouchListener.onTouch(event);
        }
        return this.config.isDrag() || super.onInterceptTouchEvent(event);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(@Nullable MotionEvent event) {
        OnFloatTouchListener onFloatTouchListener;
        if (!(event == null || (onFloatTouchListener = this.touchListener) == null)) {
            onFloatTouchListener.onTouch(event);
        }
        return this.config.isDrag() || super.onTouchEvent(event);
    }

    public boolean dispatchKeyEventPreIme(@Nullable KeyEvent event) {
        if (this.config.getHasEditText()) {
            boolean z = false;
            if (event != null && event.getAction() == 0) {
                z = true;
            }
            if (z && event.getKeyCode() == 4) {
                InputMethodUtils.closedInputMethod(this.config.getFloatTag());
            }
        }
        return super.dispatchKeyEventPreIme(event);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        FloatCallbacks.Builder builder;
        a<x> dismiss$easyfloat_release;
        super.onDetachedFromWindow();
        OnFloatCallbacks callbacks = this.config.getCallbacks();
        if (callbacks != null) {
            callbacks.dismiss();
        }
        FloatCallbacks floatCallbacks = this.config.getFloatCallbacks();
        if (floatCallbacks != null && (builder = floatCallbacks.getBuilder()) != null && (dismiss$easyfloat_release = builder.getDismiss$easyfloat_release()) != null) {
            dismiss$easyfloat_release.invoke();
        }
    }
}
