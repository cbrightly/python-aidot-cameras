package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.location.zzr;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzcp extends zzj {
    final /* synthetic */ TaskCompletionSource zza;
    final /* synthetic */ zzr zzb;

    zzcp(TaskCompletionSource taskCompletionSource, zzr zzr) {
        this.zza = taskCompletionSource;
        this.zzb = zzr;
    }

    public final void zzd(zzg zzg) {
        TaskUtil.setResultOrApiException(zzg.getStatus(), this.zza);
    }

    public final void zze() {
        this.zzb.zzf();
    }
}
