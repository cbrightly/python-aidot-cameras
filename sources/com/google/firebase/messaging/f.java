package com.google.firebase.messaging;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

/* compiled from: lambda */
public final /* synthetic */ class f implements Continuation {
    public final /* synthetic */ Context a;
    public final /* synthetic */ Intent b;

    public /* synthetic */ f(Context context, Intent intent) {
        this.a = context;
        this.b = intent;
    }

    public final Object then(Task task) {
        return FcmBroadcastProcessor.lambda$startMessagingService$2(this.a, this.b, task);
    }
}
