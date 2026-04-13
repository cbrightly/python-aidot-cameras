package com.google.firebase.heartbeatinfo;

import java.util.concurrent.Callable;

/* compiled from: lambda */
public final /* synthetic */ class b implements Callable {
    public final /* synthetic */ DefaultHeartBeatController c;

    public /* synthetic */ b(DefaultHeartBeatController defaultHeartBeatController) {
        this.c = defaultHeartBeatController;
    }

    public final Object call() {
        return this.c.a();
    }
}
