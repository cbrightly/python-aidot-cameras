package com.lzf.easyfloat.core;

import com.lzf.easyfloat.widget.ParentFrameLayout;

/* compiled from: lambda */
public final /* synthetic */ class c implements Runnable {
    public final /* synthetic */ FloatingWindowHelper c;
    public final /* synthetic */ ParentFrameLayout d;

    public /* synthetic */ c(FloatingWindowHelper floatingWindowHelper, ParentFrameLayout parentFrameLayout) {
        this.c = floatingWindowHelper;
        this.d = parentFrameLayout;
    }

    public final void run() {
        FloatingWindowHelper.m11updateFloat$lambda11$lambda10(this.c, this.d);
    }
}
