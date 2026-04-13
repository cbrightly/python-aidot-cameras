package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final /* synthetic */ class zzy implements RemoteCall {
    public final /* synthetic */ PendingIntent zza;

    public /* synthetic */ zzy(PendingIntent pendingIntent) {
        this.zza = pendingIntent;
    }

    public final void accept(Object obj, Object obj2) {
        PendingIntent pendingIntent = this.zza;
        Api api = zzag.zzb;
        ((zzf) obj).zzp(pendingIntent);
        ((TaskCompletionSource) obj2).setResult(null);
    }
}
