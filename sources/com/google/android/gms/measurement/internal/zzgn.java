package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzgn implements Runnable {
    final /* synthetic */ zzq zza;
    final /* synthetic */ zzgw zzb;

    zzgn(zzgw zzgw, zzq zzq) {
        this.zzb = zzgw;
        this.zza = zzq;
    }

    public final void run() {
        this.zzb.zza.zzA();
        zzlg zzc = this.zzb.zza;
        zzq zzq = this.zza;
        zzc.zzaB().zzg();
        zzc.zzB();
        Preconditions.checkNotEmpty(zzq.zza);
        zzc.zzd(zzq);
    }
}
