package com.lzf.easyfloat.anim;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.WindowManager;
import kotlin.s;

/* compiled from: lambda */
public final /* synthetic */ class a implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ s c;
    public final /* synthetic */ WindowManager.LayoutParams d;
    public final /* synthetic */ WindowManager f;
    public final /* synthetic */ View q;
    public final /* synthetic */ ValueAnimator x;

    public /* synthetic */ a(s sVar, WindowManager.LayoutParams layoutParams, WindowManager windowManager, View view, ValueAnimator valueAnimator) {
        this.c = sVar;
        this.d = layoutParams;
        this.f = windowManager;
        this.q = view;
        this.x = valueAnimator;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        DefaultAnimator.m8getAnimator$lambda1$lambda0(this.c, this.d, this.f, this.q, this.x, valueAnimator);
    }
}
