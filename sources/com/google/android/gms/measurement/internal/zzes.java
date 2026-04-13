package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzes {
    final /* synthetic */ zzeu zza;
    private final int zzb;
    private final boolean zzc;
    private final boolean zzd;

    zzes(zzeu zzeu, int i, boolean z, boolean z2) {
        this.zza = zzeu;
        this.zzb = i;
        this.zzc = z;
        this.zzd = z2;
    }

    public final void zza(String str) {
        this.zza.zzu(this.zzb, this.zzc, this.zzd, str, (Object) null, (Object) null, (Object) null);
    }

    public final void zzb(String str, Object obj) {
        this.zza.zzu(this.zzb, this.zzc, this.zzd, str, obj, (Object) null, (Object) null);
    }

    public final void zzc(String str, Object obj, Object obj2) {
        this.zza.zzu(this.zzb, this.zzc, this.zzd, str, obj, obj2, (Object) null);
    }

    public final void zzd(String str, Object obj, Object obj2, Object obj3) {
        this.zza.zzu(this.zzb, this.zzc, this.zzd, str, obj, obj2, obj3);
    }
}
