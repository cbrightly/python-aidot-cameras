package com.google.firebase.messaging;

import com.google.firebase.messaging.WithinAppServiceConnection;

/* compiled from: lambda */
public final /* synthetic */ class e0 implements Runnable {
    public final /* synthetic */ WithinAppServiceConnection.BindRequest c;

    public /* synthetic */ e0(WithinAppServiceConnection.BindRequest bindRequest) {
        this.c = bindRequest;
    }

    public final void run() {
        this.c.a();
    }
}
