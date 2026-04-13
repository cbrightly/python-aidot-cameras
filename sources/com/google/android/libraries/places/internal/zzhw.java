package com.google.android.libraries.places.internal;

import androidx.annotation.Nullable;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzhw {
    private Task zza;

    /* synthetic */ zzhw(zzhv zzhv) {
    }

    public abstract CancellationTokenSource zza();

    @Nullable
    public final Task zzc() {
        return this.zza;
    }

    public final void zzd(Task task) {
        this.zza = task;
    }
}
