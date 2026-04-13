package com.google.android.gms.tasks;

import androidx.annotation.NonNull;
import java.util.concurrent.Executor;

/* compiled from: com.google.android.gms:play-services-tasks@@18.0.2 */
public final class zzp<TResult, TContinuationResult> implements OnSuccessListener<TContinuationResult>, OnFailureListener, OnCanceledListener, zzq {
    private final Executor zza;
    /* access modifiers changed from: private */
    public final SuccessContinuation zzb;
    private final zzw zzc;

    public zzp(@NonNull Executor executor, @NonNull SuccessContinuation successContinuation, @NonNull zzw zzw) {
        this.zza = executor;
        this.zzb = successContinuation;
        this.zzc = zzw;
    }

    public final void onCanceled() {
        this.zzc.zzc();
    }

    public final void onFailure(@NonNull Exception exc) {
        this.zzc.zza(exc);
    }

    public final void onSuccess(TContinuationResult tcontinuationresult) {
        this.zzc.zzb(tcontinuationresult);
    }

    public final void zzc() {
        throw new UnsupportedOperationException();
    }

    public final void zzd(@NonNull Task task) {
        this.zza.execute(new zzo(this, task));
    }
}
