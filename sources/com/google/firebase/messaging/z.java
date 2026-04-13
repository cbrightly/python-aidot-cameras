package com.google.firebase.messaging;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

/* compiled from: lambda */
public final /* synthetic */ class z implements Continuation {
    public final /* synthetic */ RequestDeduplicator a;
    public final /* synthetic */ String b;

    public /* synthetic */ z(RequestDeduplicator requestDeduplicator, String str) {
        this.a = requestDeduplicator;
        this.b = str;
    }

    public final Object then(Task task) {
        this.a.a(this.b, task);
        return task;
    }
}
