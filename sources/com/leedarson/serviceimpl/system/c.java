package com.leedarson.serviceimpl.system;

import android.app.Activity;

/* compiled from: lambda */
public final /* synthetic */ class c implements Runnable {
    public final /* synthetic */ Activity c;
    public final /* synthetic */ String d;

    public /* synthetic */ c(Activity activity, String str) {
        this.c = activity;
        this.d = str;
    }

    public final void run() {
        h.f(this.c, this.d);
    }
}
