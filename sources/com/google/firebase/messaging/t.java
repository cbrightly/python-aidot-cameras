package com.google.firebase.messaging;

import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;

/* compiled from: lambda */
public final /* synthetic */ class t implements SuccessContinuation {
    public final /* synthetic */ String a;

    public /* synthetic */ t(String str) {
        this.a = str;
    }

    public final Task then(Object obj) {
        return ((TopicsSubscriber) obj).subscribeToTopic(this.a);
    }
}
