package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zzmm implements zzmt {
    private final zzmi zza;
    private final zznk zzb;
    private final boolean zzc;
    private final zzko zzd;

    private zzmm(zznk zznk, zzko zzko, zzmi zzmi) {
        this.zzb = zznk;
        this.zzc = zzko.zzc(zzmi);
        this.zzd = zzko;
        this.zza = zzmi;
    }

    static zzmm zzc(zznk zznk, zzko zzko, zzmi zzmi) {
        return new zzmm(zznk, zzko, zzmi);
    }

    public final int zza(Object obj) {
        zznk zznk = this.zzb;
        int zzb2 = zznk.zzb(zznk.zzd(obj));
        if (!this.zzc) {
            return zzb2;
        }
        this.zzd.zza(obj);
        throw null;
    }

    public final int zzb(Object obj) {
        int hashCode = this.zzb.zzd(obj).hashCode();
        if (!this.zzc) {
            return hashCode;
        }
        this.zzd.zza(obj);
        throw null;
    }

    public final Object zze() {
        zzmi zzmi = this.zza;
        if (zzmi instanceof zzlb) {
            return ((zzlb) zzmi).zzbD();
        }
        return zzmi.zzbJ().zzaF();
    }

    public final void zzf(Object obj) {
        this.zzb.zzg(obj);
        this.zzd.zzb(obj);
    }

    public final void zzg(Object obj, Object obj2) {
        zzmv.zzC(this.zzb, obj, obj2);
        if (this.zzc) {
            this.zzd.zza(obj2);
            throw null;
        }
    }

    public final void zzh(Object obj, byte[] bArr, int i, int i2, zzjn zzjn) {
        zzlb zzlb = (zzlb) obj;
        if (zzlb.zzc == zznl.zzc()) {
            zzlb.zzc = zznl.zzf();
        }
        zzky zzky = (zzky) obj;
        throw null;
    }

    public final void zzi(Object obj, zzoc zzoc) {
        this.zzd.zza(obj);
        throw null;
    }

    public final boolean zzj(Object obj, Object obj2) {
        if (!this.zzb.zzd(obj).equals(this.zzb.zzd(obj2))) {
            return false;
        }
        if (!this.zzc) {
            return true;
        }
        this.zzd.zza(obj);
        this.zzd.zza(obj2);
        throw null;
    }

    public final boolean zzk(Object obj) {
        this.zzd.zza(obj);
        throw null;
    }
}
