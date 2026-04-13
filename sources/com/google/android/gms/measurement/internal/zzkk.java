package com.google.android.gms.measurement.internal;

import androidx.annotation.WorkerThread;
import meshsdk.cache.CacheHandler;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzkk {
    final /* synthetic */ zzko zza;
    private zzkj zzb;

    zzkk(zzko zzko) {
        this.zza = zzko;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zza(long j) {
        this.zzb = new zzkj(this, this.zza.zzt.zzax().currentTimeMillis(), j);
        this.zza.zzd.postDelayed(this.zzb, CacheHandler.delayTime);
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzb() {
        this.zza.zzg();
        zzkj zzkj = this.zzb;
        if (zzkj != null) {
            this.zza.zzd.removeCallbacks(zzkj);
        }
        this.zza.zzt.zzm().zzm.zza(false);
    }
}
