package com.google.firebase.messaging;

import android.content.Context;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;

/* compiled from: lambda */
public final /* synthetic */ class b0 implements Callable {
    public final /* synthetic */ Context c;
    public final /* synthetic */ ScheduledExecutorService d;
    public final /* synthetic */ FirebaseMessaging f;
    public final /* synthetic */ Metadata q;
    public final /* synthetic */ GmsRpc x;

    public /* synthetic */ b0(Context context, ScheduledExecutorService scheduledExecutorService, FirebaseMessaging firebaseMessaging, Metadata metadata, GmsRpc gmsRpc) {
        this.c = context;
        this.d = scheduledExecutorService;
        this.f = firebaseMessaging;
        this.q = metadata;
        this.x = gmsRpc;
    }

    public final Object call() {
        return TopicsSubscriber.lambda$createInstance$0(this.c, this.d, this.f, this.q, this.x);
    }
}
