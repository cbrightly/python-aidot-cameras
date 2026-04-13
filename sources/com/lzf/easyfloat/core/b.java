package com.lzf.easyfloat.core;

import com.lzf.easyfloat.core.FloatingWindowHelper;

/* compiled from: lambda */
public final /* synthetic */ class b implements Runnable {
    public final /* synthetic */ FloatingWindowHelper.CreateCallback c;
    public final /* synthetic */ FloatingWindowHelper d;

    public /* synthetic */ b(FloatingWindowHelper.CreateCallback createCallback, FloatingWindowHelper floatingWindowHelper) {
        this.c = createCallback;
        this.d = floatingWindowHelper;
    }

    public final void run() {
        FloatingWindowHelper.m9createWindow$lambda1$lambda0(this.c, this.d);
    }
}
