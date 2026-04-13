package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import com.google.android.datatransport.runtime.TransportContext;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    public final /* synthetic */ Uploader c;
    public final /* synthetic */ TransportContext d;
    public final /* synthetic */ int f;
    public final /* synthetic */ Runnable q;

    public /* synthetic */ d(Uploader uploader, TransportContext transportContext, int i, Runnable runnable) {
        this.c = uploader;
        this.d = transportContext;
        this.f = i;
        this.q = runnable;
    }

    public final void run() {
        this.c.i(this.d, this.f, this.q);
    }
}
