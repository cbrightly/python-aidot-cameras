package com.google.android.gms.measurement.internal;

import android.app.job.JobParameters;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final /* synthetic */ class zzkb implements Runnable {
    public final /* synthetic */ zzkf zza;
    public final /* synthetic */ zzeu zzb;
    public final /* synthetic */ JobParameters zzc;

    public /* synthetic */ zzkb(zzkf zzkf, zzeu zzeu, JobParameters jobParameters) {
        this.zza = zzkf;
        this.zzb = zzeu;
        this.zzc = jobParameters;
    }

    public final void run() {
        this.zza.zzd(this.zzb, this.zzc);
    }
}
