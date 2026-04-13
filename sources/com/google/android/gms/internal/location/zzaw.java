package com.google.android.gms.internal.location;

import android.location.Location;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final /* synthetic */ class zzaw implements RemoteCall {
    public final /* synthetic */ Location zza;

    public /* synthetic */ zzaw(Location location) {
        this.zza = location;
    }

    public final void accept(Object obj, Object obj2) {
        Api api = zzbp.zzb;
        ((zzda) obj).zzz(this.zza, (TaskCompletionSource) obj2);
    }
}
