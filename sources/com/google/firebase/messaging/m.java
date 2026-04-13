package com.google.firebase.messaging;

import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: lambda */
public final /* synthetic */ class m implements Runnable {
    public final /* synthetic */ FirebaseMessaging c;
    public final /* synthetic */ TaskCompletionSource d;

    public /* synthetic */ m(FirebaseMessaging firebaseMessaging, TaskCompletionSource taskCompletionSource) {
        this.c = firebaseMessaging;
        this.d = taskCompletionSource;
    }

    public final void run() {
        this.c.c(this.d);
    }
}
