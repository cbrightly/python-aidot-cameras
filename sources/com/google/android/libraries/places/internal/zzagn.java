package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public class zzagn {
    private static final zzafo zzb = zzafo.zza;
    protected volatile zzahh zza;
    private volatile zzafe zzc;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzagn)) {
            return false;
        }
        zzagn zzagn = (zzagn) obj;
        zzahh zzahh = this.zza;
        zzahh zzahh2 = zzagn.zza;
        if (zzahh == null && zzahh2 == null) {
            return zzb().equals(zzagn.zzb());
        }
        if (zzahh != null && zzahh2 != null) {
            return zzahh.equals(zzahh2);
        }
        if (zzahh != null) {
            zzagn.zzc(zzahh.zzt());
            return zzahh.equals(zzagn.zza);
        }
        zzc(zzahh2.zzt());
        return this.zza.equals(zzahh2);
    }

    public int hashCode() {
        return 1;
    }

    public final int zza() {
        if (this.zzc != null) {
            return ((zzafb) this.zzc).zza.length;
        }
        if (this.zza != null) {
            return this.zza.zzv();
        }
        return 0;
    }

    public final zzafe zzb() {
        if (this.zzc != null) {
            return this.zzc;
        }
        synchronized (this) {
            if (this.zzc != null) {
                zzafe zzafe = this.zzc;
                return zzafe;
            }
            if (this.zza == null) {
                this.zzc = zzafe.zzb;
            } else {
                this.zzc = this.zza.zzs();
            }
            zzafe zzafe2 = this.zzc;
            return zzafe2;
        }
    }

    /* access modifiers changed from: protected */
    public final void zzc(zzahh zzahh) {
        if (this.zza == null) {
            synchronized (this) {
                if (this.zza == null) {
                    try {
                        this.zza = zzahh;
                        this.zzc = zzafe.zzb;
                    } catch (zzagk e) {
                        this.zza = zzahh;
                        this.zzc = zzafe.zzb;
                    }
                }
            }
        }
    }
}
