package com.google.firebase.messaging;

import android.content.Intent;

/* compiled from: lambda */
public final /* synthetic */ class g implements Runnable {
    public final /* synthetic */ FcmLifecycleCallbacks c;
    public final /* synthetic */ Intent d;

    public /* synthetic */ g(FcmLifecycleCallbacks fcmLifecycleCallbacks, Intent intent) {
        this.c = fcmLifecycleCallbacks;
        this.d = intent;
    }

    public final void run() {
        this.c.a(this.d);
    }
}
