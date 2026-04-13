package com.leedarson.smartcamera.kvswebrtc;

import com.leedarson.smartcamera.kvswebrtc.f0;
import com.leedarson.smartcamera.kvswebrtc.signaling.c;
import com.leedarson.smartcamera.kvswebrtc.signaling.model.Event;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ f0.a c;
    public final /* synthetic */ Event d;
    public final /* synthetic */ c f;

    public /* synthetic */ a(f0.a aVar, Event event, c cVar) {
        this.c = aVar;
        this.d = event;
        this.f = cVar;
    }

    public final void run() {
        this.c.j(this.d, this.f);
    }
}
