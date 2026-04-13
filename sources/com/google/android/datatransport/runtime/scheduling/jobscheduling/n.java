package com.google.android.datatransport.runtime.scheduling.jobscheduling;

/* compiled from: lambda */
public final /* synthetic */ class n implements Runnable {
    public final /* synthetic */ WorkInitializer c;

    public /* synthetic */ n(WorkInitializer workInitializer) {
        this.c = workInitializer;
    }

    public final void run() {
        this.c.b();
    }
}
