package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.location.LastLocationRequest;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final /* synthetic */ class zzbj implements RemoteCall {
    public final /* synthetic */ LastLocationRequest zza;

    public /* synthetic */ zzbj(LastLocationRequest lastLocationRequest) {
        this.zza = lastLocationRequest;
    }

    public final void accept(Object obj, Object obj2) {
        Api api = zzbp.zzb;
        ((zzda) obj).zzt(this.zza, (TaskCompletionSource) obj2);
    }
}
