package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzcl extends IStatusCallback.Stub {
    final /* synthetic */ Object zza;
    final /* synthetic */ TaskCompletionSource zzb;

    zzcl(zzda zzda, Object obj, TaskCompletionSource taskCompletionSource) {
        this.zza = obj;
        this.zzb = taskCompletionSource;
    }

    public final void onResult(Status status) {
        TaskUtil.setResultOrApiException(status, this.zza, this.zzb);
    }
}
