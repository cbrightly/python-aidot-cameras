package com.google.firebase.messaging;

import com.google.firebase.events.Event;
import com.google.firebase.events.EventHandler;
import com.google.firebase.messaging.FirebaseMessaging;

/* compiled from: lambda */
public final /* synthetic */ class k implements EventHandler {
    public final /* synthetic */ FirebaseMessaging.AutoInit a;

    public /* synthetic */ k(FirebaseMessaging.AutoInit autoInit) {
        this.a = autoInit;
    }

    public final void handle(Event event) {
        this.a.a(event);
    }
}
