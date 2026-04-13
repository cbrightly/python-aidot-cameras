package com.leedarson.serviceimpl.system;

import android.app.Activity;
import android.os.Handler;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    public final /* synthetic */ Handler c;
    public final /* synthetic */ Activity d;

    public /* synthetic */ d(Handler handler, Activity activity) {
        this.c = handler;
        this.d = activity;
    }

    public final void run() {
        h.g(this.c, this.d);
    }
}
