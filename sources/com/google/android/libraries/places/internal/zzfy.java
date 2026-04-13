package com.google.android.libraries.places.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzfy implements Runnable {
    public final /* synthetic */ TaskCompletionSource zza;
    public final /* synthetic */ String zzb = "Location timeout.";

    public /* synthetic */ zzfy(TaskCompletionSource taskCompletionSource, String str) {
        this.zza = taskCompletionSource;
    }

    public final void run() {
        this.zza.trySetException(new ApiException(new Status(15, this.zzb)));
    }
}
