package com.google.firebase.messaging;

import android.content.Intent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/* compiled from: lambda */
public final /* synthetic */ class b implements OnCompleteListener {
    public final /* synthetic */ EnhancedIntentService a;
    public final /* synthetic */ Intent b;

    public /* synthetic */ b(EnhancedIntentService enhancedIntentService, Intent intent) {
        this.a = enhancedIntentService;
        this.b = intent;
    }

    public final void onComplete(Task task) {
        this.a.a(this.b, task);
    }
}
