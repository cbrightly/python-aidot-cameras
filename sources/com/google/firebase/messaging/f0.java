package com.google.firebase.messaging;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.concurrent.ScheduledFuture;

/* compiled from: lambda */
public final /* synthetic */ class f0 implements OnCompleteListener {
    public final /* synthetic */ ScheduledFuture a;

    public /* synthetic */ f0(ScheduledFuture scheduledFuture) {
        this.a = scheduledFuture;
    }

    public final void onComplete(Task task) {
        this.a.cancel(false);
    }
}
