package com.google.android.libraries.places.internal;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzhs implements Continuation {
    public final /* synthetic */ zzht zza;

    public /* synthetic */ zzhs(zzht zzht) {
        this.zza = zzht;
    }

    public final Object then(Task task) {
        zzht zzht = this.zza;
        int i = zzhx.zza;
        return zzht.zza().getToken().isCancellationRequested() ? Tasks.forCanceled() : task;
    }
}
