package com.google.firebase.messaging;

import android.content.Context;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: lambda */
public final /* synthetic */ class y implements Runnable {
    public final /* synthetic */ Context c;
    public final /* synthetic */ boolean d;
    public final /* synthetic */ TaskCompletionSource f;

    public /* synthetic */ y(Context context, boolean z, TaskCompletionSource taskCompletionSource) {
        this.c = context;
        this.d = z;
        this.f = taskCompletionSource;
    }

    public final void run() {
        ProxyNotificationInitializer.lambda$setEnableProxyNotification$0(this.c, this.d, this.f);
    }
}
