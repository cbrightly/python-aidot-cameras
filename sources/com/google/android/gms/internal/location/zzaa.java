package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.api.internal.StatusCallback;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final /* synthetic */ class zzaa implements RemoteCall {
    public final /* synthetic */ ActivityTransitionRequest zza;
    public final /* synthetic */ PendingIntent zzb;

    public /* synthetic */ zzaa(ActivityTransitionRequest activityTransitionRequest, PendingIntent pendingIntent) {
        this.zza = activityTransitionRequest;
        this.zzb = pendingIntent;
    }

    public final void accept(Object obj, Object obj2) {
        ActivityTransitionRequest activityTransitionRequest = this.zza;
        PendingIntent pendingIntent = this.zzb;
        Api api = zzag.zzb;
        zzaf zzaf = new zzaf((TaskCompletionSource) obj2);
        Preconditions.checkNotNull(activityTransitionRequest, "activityTransitionRequest must be specified.");
        Preconditions.checkNotNull(pendingIntent, "PendingIntent must be specified.");
        Preconditions.checkNotNull(zzaf, "ResultHolder not provided.");
        ((zzo) ((zzf) obj).getService()).zzq(activityTransitionRequest, pendingIntent, new StatusCallback(zzaf));
    }
}
