package com.google.android.gms.measurement.internal;

import android.content.Intent;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final /* synthetic */ class zzkc implements Runnable {
    public final /* synthetic */ zzkf zza;
    public final /* synthetic */ int zzb;
    public final /* synthetic */ zzeu zzc;
    public final /* synthetic */ Intent zzd;

    public /* synthetic */ zzkc(zzkf zzkf, int i, zzeu zzeu, Intent intent) {
        this.zza = zzkf;
        this.zzb = i;
        this.zzc = zzeu;
        this.zzd = intent;
    }

    public final void run() {
        this.zza.zzc(this.zzb, this.zzc, this.zzd);
    }
}
