package com.google.firebase.heartbeatinfo;

import java.util.concurrent.Callable;

/* compiled from: lambda */
public final /* synthetic */ class a implements Callable {
    public final /* synthetic */ DefaultHeartBeatController c;

    public /* synthetic */ a(DefaultHeartBeatController defaultHeartBeatController) {
        this.c = defaultHeartBeatController;
    }

    public final Object call() {
        this.c.b();
        return null;
    }
}
