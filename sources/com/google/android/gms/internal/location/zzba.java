package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final /* synthetic */ class zzba implements RemoteCall {
    public static final /* synthetic */ zzba zza = new zzba();

    private /* synthetic */ zzba() {
    }

    public final void accept(Object obj, Object obj2) {
        Api api = zzbp.zzb;
        ((TaskCompletionSource) obj2).setResult(((zzda) obj).zzp());
    }
}
