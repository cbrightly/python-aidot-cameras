package com.google.android.libraries.places.internal;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzga implements OnCompleteListener {
    public final /* synthetic */ zzgb zza;
    public final /* synthetic */ TaskCompletionSource zzb;

    public /* synthetic */ zzga(zzgb zzgb, TaskCompletionSource taskCompletionSource) {
        this.zza = zzgb;
        this.zzb = taskCompletionSource;
    }

    public final void onComplete(Task task) {
        this.zza.zzb(this.zzb);
    }
}
