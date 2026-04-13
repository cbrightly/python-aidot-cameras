package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public class zzlo {
    private static final zzkn zzb = zzkn.zza;
    protected volatile zzmi zza;
    private volatile zzka zzc;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzlo)) {
            return false;
        }
        zzlo zzlo = (zzlo) obj;
        zzmi zzmi = this.zza;
        zzmi zzmi2 = zzlo.zza;
        if (zzmi == null && zzmi2 == null) {
            return zzb().equals(zzlo.zzb());
        }
        if (zzmi != null && zzmi2 != null) {
            return zzmi.equals(zzmi2);
        }
        if (zzmi != null) {
            zzlo.zzc(zzmi.zzbV());
            return zzmi.equals(zzlo.zza);
        }
        zzc(zzmi2.zzbV());
        return this.zza.equals(zzmi2);
    }

    public int hashCode() {
        return 1;
    }

    public final int zza() {
        if (this.zzc != null) {
            return ((zzjx) this.zzc).zza.length;
        }
        if (this.zza != null) {
            return this.zza.zzbz();
        }
        return 0;
    }

    public final zzka zzb() {
        if (this.zzc != null) {
            return this.zzc;
        }
        synchronized (this) {
            if (this.zzc != null) {
                zzka zzka = this.zzc;
                return zzka;
            }
            if (this.zza == null) {
                this.zzc = zzka.zzb;
            } else {
                this.zzc = this.zza.zzbv();
            }
            zzka zzka2 = this.zzc;
            return zzka2;
        }
    }

    /* access modifiers changed from: protected */
    public final void zzc(zzmi zzmi) {
        if (this.zza == null) {
            synchronized (this) {
                if (this.zza == null) {
                    try {
                        this.zza = zzmi;
                        this.zzc = zzka.zzb;
                    } catch (zzll e) {
                        this.zza = zzmi;
                        this.zzc = zzka.zzb;
                    }
                }
            }
        }
    }
}
