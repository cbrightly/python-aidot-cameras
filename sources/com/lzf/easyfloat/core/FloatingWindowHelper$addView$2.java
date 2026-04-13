package com.lzf.easyfloat.core;

import android.view.View;
import com.lzf.easyfloat.data.FloatConfig;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.interfaces.FloatCallbacks;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import com.lzf.easyfloat.utils.LifecycleUtils;
import com.lzf.easyfloat.widget.ParentFrameLayout;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.k;
import kotlin.x;

/* compiled from: FloatingWindowHelper.kt */
public final class FloatingWindowHelper$addView$2 implements ParentFrameLayout.OnLayoutListener {
    final /* synthetic */ View $floatingView;
    final /* synthetic */ FloatingWindowHelper this$0;

    FloatingWindowHelper$addView$2(FloatingWindowHelper $receiver, View $floatingView2) {
        this.this$0 = $receiver;
        this.$floatingView = $floatingView2;
    }

    public void onLayout() {
        FloatCallbacks.Builder builder;
        q<Boolean, String, View, x> createdResult$easyfloat_release;
        FloatingWindowHelper floatingWindowHelper = this.this$0;
        floatingWindowHelper.setGravity(floatingWindowHelper.getFrameLayout());
        FloatingWindowHelper floatingWindowHelper2 = this.this$0;
        ParentFrameLayout frameLayout = floatingWindowHelper2.getFrameLayout();
        int i = -1;
        floatingWindowHelper2.lastLayoutMeasureWidth = frameLayout == null ? -1 : frameLayout.getMeasuredWidth();
        FloatingWindowHelper floatingWindowHelper3 = this.this$0;
        ParentFrameLayout frameLayout2 = floatingWindowHelper3.getFrameLayout();
        if (frameLayout2 != null) {
            i = frameLayout2.getMeasuredHeight();
        }
        floatingWindowHelper3.lastLayoutMeasureHeight = i;
        FloatConfig $this$onLayout_u24lambda_u2d0 = this.this$0.getConfig();
        FloatingWindowHelper floatingWindowHelper4 = this.this$0;
        View view = this.$floatingView;
        if ($this$onLayout_u24lambda_u2d0.getFilterSelf$easyfloat_release() || (($this$onLayout_u24lambda_u2d0.getShowPattern() == ShowPattern.BACKGROUND && LifecycleUtils.INSTANCE.isForeground()) || ($this$onLayout_u24lambda_u2d0.getShowPattern() == ShowPattern.FOREGROUND && !LifecycleUtils.INSTANCE.isForeground()))) {
            FloatingWindowHelper.setVisible$default(floatingWindowHelper4, 8, false, 2, (Object) null);
            floatingWindowHelper4.initEditText();
        } else {
            k.d(view, "floatingView");
            floatingWindowHelper4.enterAnim(view);
        }
        $this$onLayout_u24lambda_u2d0.setLayoutView(view);
        OnInvokeView invokeView = $this$onLayout_u24lambda_u2d0.getInvokeView();
        if (invokeView != null) {
            invokeView.invoke(view);
        }
        OnFloatCallbacks callbacks = $this$onLayout_u24lambda_u2d0.getCallbacks();
        if (callbacks != null) {
            callbacks.createdResult(true, (String) null, view);
        }
        FloatCallbacks floatCallbacks = $this$onLayout_u24lambda_u2d0.getFloatCallbacks();
        if (floatCallbacks != null && (builder = floatCallbacks.getBuilder()) != null && (createdResult$easyfloat_release = builder.getCreatedResult$easyfloat_release()) != null) {
            createdResult$easyfloat_release.invoke(true, null, view);
        }
    }
}
