package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zaac implements OnCompleteListener {
    final /* synthetic */ TaskCompletionSource zaa;
    final /* synthetic */ zaad zab;

    zaac(zaad zaad, TaskCompletionSource taskCompletionSource) {
        this.zab = zaad;
        this.zaa = taskCompletionSource;
    }

    public final void onComplete(@NonNull Task task) {
        this.zab.zab.remove(this.zaa);
    }
}
