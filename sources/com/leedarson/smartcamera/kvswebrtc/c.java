package com.leedarson.smartcamera.kvswebrtc;

import com.leedarson.smartcamera.kvswebrtc.f0;
import com.leedarson.smartcamera.kvswebrtc.signaling.model.Event;

/* compiled from: lambda */
public final /* synthetic */ class c implements Runnable {
    public final /* synthetic */ f0.d c;
    public final /* synthetic */ Event d;
    public final /* synthetic */ com.leedarson.smartcamera.kvswebrtc.signaling.c f;

    public /* synthetic */ c(f0.d dVar, Event event, com.leedarson.smartcamera.kvswebrtc.signaling.c cVar) {
        this.c = dVar;
        this.d = event;
        this.f = cVar;
    }

    public final void run() {
        this.c.b(this.d, this.f);
    }
}
