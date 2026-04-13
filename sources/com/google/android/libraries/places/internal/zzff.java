package com.google.android.libraries.places.internal;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzff implements Continuation {
    public final /* synthetic */ zzfm zza;
    public final /* synthetic */ long zzb;

    public /* synthetic */ zzff(zzfm zzfm, long j) {
        this.zza = zzfm;
        this.zzb = j;
    }

    public final Object then(Task task) {
        return this.zza.zzg(this.zzb, task);
    }
}
