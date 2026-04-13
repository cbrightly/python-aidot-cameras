package com.google.firebase.messaging;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.WithinAppServiceConnection;

/* compiled from: lambda */
public final /* synthetic */ class d0 implements OnCompleteListener {
    public final /* synthetic */ WithinAppServiceConnection.BindRequest a;

    public /* synthetic */ d0(WithinAppServiceConnection.BindRequest bindRequest) {
        this.a = bindRequest;
    }

    public final void onComplete(Task task) {
        this.a.finish();
    }
}
