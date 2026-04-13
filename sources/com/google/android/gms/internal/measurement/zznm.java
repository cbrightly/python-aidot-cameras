package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zznm extends zznk {
    zznm() {
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ int zza(Object obj) {
        return ((zznl) obj).zza();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ int zzb(Object obj) {
        return ((zznl) obj).zzb();
    }

    /* access modifiers changed from: package-private */
    public final /* bridge */ /* synthetic */ Object zzc(Object obj) {
        zzlb zzlb = (zzlb) obj;
        zznl zznl = zzlb.zzc;
        if (zznl != zznl.zzc()) {
            return zznl;
        }
        zznl zzf = zznl.zzf();
        zzlb.zzc = zzf;
        return zzf;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object zzd(Object obj) {
        return ((zzlb) obj).zzc;
    }

    /* access modifiers changed from: package-private */
    public final /* bridge */ /* synthetic */ Object zze(Object obj, Object obj2) {
        if (zznl.zzc().equals(obj2)) {
            return obj;
        }
        if (zznl.zzc().equals(obj)) {
            return zznl.zze((zznl) obj, (zznl) obj2);
        }
        ((zznl) obj).zzd((zznl) obj2);
        return obj;
    }

    /* access modifiers changed from: package-private */
    public final /* bridge */ /* synthetic */ void zzf(Object obj, int i, long j) {
        ((zznl) obj).zzj(i << 3, Long.valueOf(j));
    }

    /* access modifiers changed from: package-private */
    public final void zzg(Object obj) {
        ((zzlb) obj).zzc.zzh();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzh(Object obj, Object obj2) {
        ((zzlb) obj).zzc = (zznl) obj2;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzi(Object obj, zzoc zzoc) {
        ((zznl) obj).zzk(zzoc);
    }
}
