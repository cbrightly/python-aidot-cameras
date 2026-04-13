package com.google.firebase.messaging;

import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: lambda */
public final /* synthetic */ class s implements Runnable {
    public final /* synthetic */ FirebaseMessaging c;
    public final /* synthetic */ TaskCompletionSource d;

    public /* synthetic */ s(FirebaseMessaging firebaseMessaging, TaskCompletionSource taskCompletionSource) {
        this.c = firebaseMessaging;
        this.d = taskCompletionSource;
    }

    public final void run() {
        this.c.d(this.d);
    }
}
