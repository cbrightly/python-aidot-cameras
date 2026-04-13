package com.google.android.libraries.places.internal;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzfg implements Continuation {
    public final /* synthetic */ zzfm zza;

    public /* synthetic */ zzfg(zzfm zzfm) {
        this.zza = zzfm;
    }

    public final Object then(Task task) {
        return zzfm.zzi(task);
    }
}
