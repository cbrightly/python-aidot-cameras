package com.google.firebase.messaging;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

/* compiled from: lambda */
public final /* synthetic */ class v implements Continuation {
    public final /* synthetic */ GmsRpc a;

    public /* synthetic */ v(GmsRpc gmsRpc) {
        this.a = gmsRpc;
    }

    public final Object then(Task task) {
        return this.a.a(task);
    }
}
