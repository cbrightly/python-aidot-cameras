package com.leedarson.base.utils;

import android.app.Activity;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ c c;
    public final /* synthetic */ Activity d;
    public final /* synthetic */ Activity f;

    public /* synthetic */ a(c cVar, Activity activity, Activity activity2) {
        this.c = cVar;
        this.d = activity;
        this.f = activity2;
    }

    public final void run() {
        this.c.o(this.d, this.f);
    }
}
