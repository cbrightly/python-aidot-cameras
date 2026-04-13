package com.google.firebase.messaging;

import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: lambda */
public final /* synthetic */ class w implements Runnable {
    public final /* synthetic */ ImageDownload c;
    public final /* synthetic */ TaskCompletionSource d;

    public /* synthetic */ w(ImageDownload imageDownload, TaskCompletionSource taskCompletionSource) {
        this.c = imageDownload;
        this.d = taskCompletionSource;
    }

    public final void run() {
        this.c.a(this.d);
    }
}
