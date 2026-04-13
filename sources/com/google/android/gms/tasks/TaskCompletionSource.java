package com.google.android.gms.tasks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-tasks@@18.0.2 */
public class TaskCompletionSource<TResult> {
    /* access modifiers changed from: private */
    public final zzw zza = new zzw();

    public TaskCompletionSource() {
    }

    public TaskCompletionSource(@NonNull CancellationToken cancellationToken) {
        cancellationToken.onCanceledRequested(new zzs(this));
    }

    @NonNull
    public Task<TResult> getTask() {
        return this.zza;
    }

    public void setException(@NonNull Exception e) {
        this.zza.zza(e);
    }

    public void setResult(@Nullable TResult result) {
        this.zza.zzb(result);
    }

    public boolean trySetException(@NonNull Exception e) {
        return this.zza.zzd(e);
    }

    public boolean trySetResult(@Nullable TResult result) {
        return this.zza.zze(result);
    }
}
