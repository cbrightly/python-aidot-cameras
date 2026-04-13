package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.location.SleepSegmentRequest;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final /* synthetic */ class zzac implements RemoteCall {
    public final /* synthetic */ zzag zza;
    public final /* synthetic */ PendingIntent zzb;
    public final /* synthetic */ SleepSegmentRequest zzc;

    public /* synthetic */ zzac(zzag zzag, PendingIntent pendingIntent, SleepSegmentRequest sleepSegmentRequest) {
        this.zza = zzag;
        this.zzb = pendingIntent;
        this.zzc = sleepSegmentRequest;
    }

    public final void accept(Object obj, Object obj2) {
        zzag zzag = this.zza;
        ((zzo) ((zzf) obj).getService()).zzt(this.zzb, this.zzc, new zzae(zzag, (TaskCompletionSource) obj2));
    }
}
