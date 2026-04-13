package com.google.firebase.messaging;

import android.content.Intent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/* compiled from: lambda */
public final /* synthetic */ class c0 implements OnCompleteListener {
    public final /* synthetic */ Intent a;

    public /* synthetic */ c0(Intent intent) {
        this.a = intent;
    }

    public final void onComplete(Task task) {
        WakeLockHolder.completeWakefulIntent(this.a);
    }
}
