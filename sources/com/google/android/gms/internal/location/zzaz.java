package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final /* synthetic */ class zzaz implements RemoteCall {
    public final /* synthetic */ PendingIntent zza;
    public final /* synthetic */ LocationRequest zzb;

    public /* synthetic */ zzaz(PendingIntent pendingIntent, LocationRequest locationRequest) {
        this.zza = pendingIntent;
        this.zzb = locationRequest;
    }

    public final void accept(Object obj, Object obj2) {
        Api api = zzbp.zzb;
        ((zzda) obj).zzw(this.zza, this.zzb, (TaskCompletionSource) obj2);
    }
}
