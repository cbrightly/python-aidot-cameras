package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzji extends zzap {
    final /* synthetic */ zzjy zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzji(zzjy zzjy, zzgz zzgz) {
        super(zzgz);
        this.zza = zzjy;
    }

    public final void zzc() {
        zzjy zzjy = this.zza;
        zzjy.zzg();
        if (zzjy.zzL()) {
            zzjy.zzt.zzaA().zzj().zza("Inactivity, disconnecting from the service");
            zzjy.zzs();
        }
    }
}
