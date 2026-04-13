package com.leedarson.smartcamera.kvswebrtc.signaling.tyrus;

import com.leedarson.smartcamera.kvswebrtc.signaling.model.Message;

/* compiled from: lambda */
public final /* synthetic */ class f implements Runnable {
    public final /* synthetic */ n c;
    public final /* synthetic */ Message d;

    public /* synthetic */ f(n nVar, Message message) {
        this.c = nVar;
        this.d = message;
    }

    public final void run() {
        this.c.o(this.d);
    }
}
