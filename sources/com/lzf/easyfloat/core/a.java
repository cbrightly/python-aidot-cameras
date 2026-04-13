package com.lzf.easyfloat.core;

import android.view.ViewTreeObserver;
import com.lzf.easyfloat.widget.ParentFrameLayout;

/* compiled from: lambda */
public final /* synthetic */ class a implements ViewTreeObserver.OnGlobalLayoutListener {
    public final /* synthetic */ FloatingWindowHelper c;
    public final /* synthetic */ ParentFrameLayout d;

    public /* synthetic */ a(FloatingWindowHelper floatingWindowHelper, ParentFrameLayout parentFrameLayout) {
        this.c = floatingWindowHelper;
        this.d = parentFrameLayout;
    }

    public final void onGlobalLayout() {
        FloatingWindowHelper.m10setChangedListener$lambda5$lambda4(this.c, this.d);
    }
}
