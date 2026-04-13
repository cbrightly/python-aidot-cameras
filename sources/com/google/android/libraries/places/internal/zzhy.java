package com.google.android.libraries.places.internal;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.AutocompletePrediction;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzhy implements OnCompleteListener {
    public final /* synthetic */ zzid zza;
    public final /* synthetic */ AutocompletePrediction zzb;

    public /* synthetic */ zzhy(zzid zzid, AutocompletePrediction autocompletePrediction) {
        this.zza = zzid;
        this.zzb = autocompletePrediction;
    }

    public final void onComplete(Task task) {
        this.zza.zzc(this.zzb, task);
    }
}
