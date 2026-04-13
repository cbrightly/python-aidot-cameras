package com.google.android.libraries.places.internal;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzfz implements Continuation {
    public final /* synthetic */ zzgb zza;
    public final /* synthetic */ TaskCompletionSource zzb;

    public /* synthetic */ zzfz(zzgb zzgb, TaskCompletionSource taskCompletionSource) {
        this.zza = zzgb;
        this.zzb = taskCompletionSource;
    }

    public final Object then(Task task) {
        TaskCompletionSource taskCompletionSource = this.zzb;
        Exception exception = task.getException();
        if (task.isSuccessful()) {
            taskCompletionSource.setResult(task.getResult());
        } else if (!task.isCanceled() && exception != null) {
            taskCompletionSource.setException(exception);
        }
        return taskCompletionSource.getTask();
    }
}
