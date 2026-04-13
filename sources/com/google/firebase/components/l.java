package com.google.firebase.components;

import com.google.firebase.events.Event;
import com.google.firebase.events.EventHandler;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class l implements Runnable {
    public final /* synthetic */ Map.Entry c;
    public final /* synthetic */ Event d;

    public /* synthetic */ l(Map.Entry entry, Event event) {
        this.c = entry;
        this.d = event;
    }

    public final void run() {
        ((EventHandler) this.c.getKey()).handle(this.d);
    }
}
