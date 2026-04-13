package com.leedarson.smarthome.application;

import android.os.MessageQueue;

/* compiled from: lambda */
public final /* synthetic */ class a implements MessageQueue.IdleHandler {
    public static final /* synthetic */ a c = new a();

    private /* synthetic */ a() {
    }

    public final boolean queueIdle() {
        return SmartHomeApplication.H();
    }
}
