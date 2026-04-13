package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzfd {
    final /* synthetic */ zzfj zza;
    private final String zzb;
    private final boolean zzc;
    private boolean zzd;
    private boolean zze;

    public zzfd(zzfj zzfj, String str, boolean z) {
        this.zza = zzfj;
        Preconditions.checkNotEmpty(str);
        this.zzb = str;
        this.zzc = z;
    }

    @WorkerThread
    public final void zza(boolean z) {
        SharedPreferences.Editor edit = this.zza.zza().edit();
        edit.putBoolean(this.zzb, z);
        edit.apply();
        this.zze = z;
    }

    @WorkerThread
    public final boolean zzb() {
        if (!this.zzd) {
            this.zzd = true;
            this.zze = this.zza.zza().getBoolean(this.zzb, this.zzc);
        }
        return this.zze;
    }
}
