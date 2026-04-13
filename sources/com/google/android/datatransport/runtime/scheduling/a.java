package com.google.android.datatransport.runtime.scheduling;

import com.google.android.datatransport.TransportScheduleCallback;
import com.google.android.datatransport.runtime.EventInternal;
import com.google.android.datatransport.runtime.TransportContext;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ DefaultScheduler c;
    public final /* synthetic */ TransportContext d;
    public final /* synthetic */ TransportScheduleCallback f;
    public final /* synthetic */ EventInternal q;

    public /* synthetic */ a(DefaultScheduler defaultScheduler, TransportContext transportContext, TransportScheduleCallback transportScheduleCallback, EventInternal eventInternal) {
        this.c = defaultScheduler;
        this.d = transportContext;
        this.f = transportScheduleCallback;
        this.q = eventInternal;
    }

    public final void run() {
        this.c.b(this.d, this.f, this.q);
    }
}
