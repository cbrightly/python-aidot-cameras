package com.lzf.easyfloat.core;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.WindowManager;

/* compiled from: lambda */
public final /* synthetic */ class d implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ boolean c;
    public final /* synthetic */ WindowManager.LayoutParams d;
    public final /* synthetic */ WindowManager f;
    public final /* synthetic */ View q;
    public final /* synthetic */ ValueAnimator x;

    public /* synthetic */ d(boolean z, WindowManager.LayoutParams layoutParams, WindowManager windowManager, View view, ValueAnimator valueAnimator) {
        this.c = z;
        this.d = layoutParams;
        this.f = windowManager;
        this.q = view;
        this.x = valueAnimator;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        TouchUtils.m12sideAnim$lambda0(this.c, this.d, this.f, this.q, this.x, valueAnimator);
    }
}
