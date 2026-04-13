package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzahl implements zzahs {
    private final zzahh zza;
    private final zzaij zzb;
    private final boolean zzc;
    private final zzafp zzd;

    private zzahl(zzaij zzaij, zzafp zzafp, zzahh zzahh) {
        this.zzb = zzaij;
        this.zzc = zzafp.zzc(zzahh);
        this.zzd = zzafp;
        this.zza = zzahh;
    }

    static zzahl zzi(zzaij zzaij, zzafp zzafp, zzahh zzahh) {
        return new zzahl(zzaij, zzafp, zzahh);
    }

    public final int zza(Object obj) {
        zzaij zzaij = this.zzb;
        int zzb2 = zzaij.zzb(zzaij.zzc(obj));
        if (!this.zzc) {
            return zzb2;
        }
        this.zzd.zza(obj);
        throw null;
    }

    public final int zzb(Object obj) {
        int hashCode = this.zzb.zzc(obj).hashCode();
        if (!this.zzc) {
            return hashCode;
        }
        this.zzd.zza(obj);
        throw null;
    }

    public final Object zzc() {
        zzahh zzahh = this.zza;
        if (zzahh instanceof zzafz) {
            return ((zzafz) zzahh).zzy();
        }
        return zzahh.zzD().zzs();
    }

    public final void zzd(Object obj) {
        this.zzb.zze(obj);
        this.zzd.zzb(obj);
    }

    public final void zze(Object obj, Object obj2) {
        zzahu.zzB(this.zzb, obj, obj2);
        if (this.zzc) {
            this.zzd.zza(obj2);
            throw null;
        }
    }

    public final void zzf(Object obj, zzaja zzaja) {
        this.zzd.zza(obj);
        throw null;
    }

    public final boolean zzg(Object obj, Object obj2) {
        if (!this.zzb.zzc(obj).equals(this.zzb.zzc(obj2))) {
            return false;
        }
        if (!this.zzc) {
            return true;
        }
        this.zzd.zza(obj);
        this.zzd.zza(obj2);
        throw null;
    }

    public final boolean zzh(Object obj) {
        this.zzd.zza(obj);
        throw null;
    }
}
