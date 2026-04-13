package com.google.firebase.messaging;

import android.content.Intent;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: lambda */
public final /* synthetic */ class c implements Runnable {
    public final /* synthetic */ EnhancedIntentService c;
    public final /* synthetic */ Intent d;
    public final /* synthetic */ TaskCompletionSource f;

    public /* synthetic */ c(EnhancedIntentService enhancedIntentService, Intent intent, TaskCompletionSource taskCompletionSource) {
        this.c = enhancedIntentService;
        this.d = intent;
        this.f = taskCompletionSource;
    }

    public final void run() {
        this.c.b(this.d, this.f);
    }
}
