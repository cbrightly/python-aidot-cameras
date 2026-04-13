package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.api.internal.StatusCallback;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.location.zzb;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final /* synthetic */ class zzz implements RemoteCall {
    public final /* synthetic */ zzb zza;
    public final /* synthetic */ PendingIntent zzb;

    public /* synthetic */ zzz(zzb zzb2, PendingIntent pendingIntent) {
        this.zza = zzb2;
        this.zzb = pendingIntent;
    }

    public final void accept(Object obj, Object obj2) {
        zzb zzb2 = this.zza;
        PendingIntent pendingIntent = this.zzb;
        Api api = zzag.zzb;
        zzaf zzaf = new zzaf((TaskCompletionSource) obj2);
        Preconditions.checkNotNull(zzb2, "ActivityRecognitionRequest can't be null.");
        Preconditions.checkNotNull(pendingIntent, "PendingIntent must be specified.");
        Preconditions.checkNotNull(zzaf, "ResultHolder not provided.");
        ((zzo) ((zzf) obj).getService()).zzs(zzb2, pendingIntent, new StatusCallback(zzaf));
    }
}
