package com.google.android.libraries.places.internal;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzhr implements Continuation {
    public final /* synthetic */ zzhu zza;

    public /* synthetic */ zzhr(zzhu zzhu) {
        this.zza = zzhu;
    }

    public final Object then(Task task) {
        zzhu zzhu = this.zza;
        int i = zzhx.zza;
        return zzhu.zza().getToken().isCancellationRequested() ? Tasks.forCanceled() : task;
    }
}
