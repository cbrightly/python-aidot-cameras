package com.google.android.libraries.places.internal;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzed implements Continuation {
    public final /* synthetic */ zzeg zza;
    public final /* synthetic */ FindCurrentPlaceRequest zzb;
    public final /* synthetic */ long zzc;
    public final /* synthetic */ zzcw zzd;

    public /* synthetic */ zzed(zzeg zzeg, FindCurrentPlaceRequest findCurrentPlaceRequest, long j, zzcw zzcw) {
        this.zza = zzeg;
        this.zzb = findCurrentPlaceRequest;
        this.zzc = j;
        this.zzd = zzcw;
    }

    public final Object then(Task task) {
        return this.zza.zzg(this.zzb, this.zzc, this.zzd, task);
    }
}
