package com.leedarson.base.application;

import android.app.Activity;

/* compiled from: lambda */
public final /* synthetic */ class b implements Runnable {
    public final /* synthetic */ d c;
    public final /* synthetic */ Activity d;

    public /* synthetic */ b(d dVar, Activity activity) {
        this.c = dVar;
        this.d = activity;
    }

    public final void run() {
        this.c.e(this.d);
    }
}
