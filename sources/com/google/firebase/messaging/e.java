package com.google.firebase.messaging;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

/* compiled from: lambda */
public final /* synthetic */ class e implements Continuation {
    public static final /* synthetic */ e a = new e();

    private /* synthetic */ e() {
    }

    public final Object then(Task task) {
        return FcmBroadcastProcessor.lambda$startMessagingService$1(task);
    }
}
