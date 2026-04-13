package com.google.android.libraries.places.internal;

import android.location.Location;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzec implements SuccessContinuation {
    public final /* synthetic */ zzeg zza;
    public final /* synthetic */ FindCurrentPlaceRequest zzb;

    public /* synthetic */ zzec(zzeg zzeg, FindCurrentPlaceRequest findCurrentPlaceRequest, String str) {
        this.zza = zzeg;
        this.zzb = findCurrentPlaceRequest;
    }

    public final Task then(Object obj) {
        return this.zza.zzb(this.zzb, (String) null, (Location) obj);
    }
}
