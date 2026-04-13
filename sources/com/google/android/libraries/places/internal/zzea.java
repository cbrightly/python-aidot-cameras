package com.google.android.libraries.places.internal;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzea implements Continuation {
    public final /* synthetic */ zzeg zza;
    public final /* synthetic */ FetchPlaceRequest zzb;
    public final /* synthetic */ zzcw zzc;

    public /* synthetic */ zzea(zzeg zzeg, FetchPlaceRequest fetchPlaceRequest, zzcw zzcw) {
        this.zza = zzeg;
        this.zzb = fetchPlaceRequest;
        this.zzc = zzcw;
    }

    public final Object then(Task task) {
        return this.zza.zze(this.zzb, this.zzc, task);
    }
}
