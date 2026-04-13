package com.google.android.libraries.places.internal;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzef implements Continuation {
    public final /* synthetic */ zzeg zza;
    public final /* synthetic */ FindAutocompletePredictionsRequest zzb;
    public final /* synthetic */ zzcw zzc;

    public /* synthetic */ zzef(zzeg zzeg, FindAutocompletePredictionsRequest findAutocompletePredictionsRequest, zzcw zzcw) {
        this.zza = zzeg;
        this.zzb = findAutocompletePredictionsRequest;
        this.zzc = zzcw;
    }

    public final Object then(Task task) {
        return this.zza.zzf(this.zzb, this.zzc, task);
    }
}
