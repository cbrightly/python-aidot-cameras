package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzgo implements Runnable {
    final /* synthetic */ zzq zza;
    final /* synthetic */ zzgw zzb;

    zzgo(zzgw zzgw, zzq zzq) {
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
        zzai zzb2 = zzai.zzb(zzq.zzv);
        zzai zzh = zzc.zzh(zzq.zza);
        zzc.zzaA().zzj().zzc("Setting consent, package, consent", zzq.zza, zzb2);
        zzc.zzV(zzq.zza, zzb2);
        if (zzb2.zzk(zzh)) {
            zzc.zzQ(zzq);
        }
    }
}
