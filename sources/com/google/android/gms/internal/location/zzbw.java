package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final /* synthetic */ class zzbw implements RemoteCall {
    public final /* synthetic */ GeofencingRequest zza;
    public final /* synthetic */ PendingIntent zzb;

    public /* synthetic */ zzbw(GeofencingRequest geofencingRequest, PendingIntent pendingIntent) {
        this.zza = geofencingRequest;
        this.zzb = pendingIntent;
    }

    public final void accept(Object obj, Object obj2) {
        ((zzda) obj).zzq(this.zza, this.zzb, (TaskCompletionSource) obj2);
    }
}
