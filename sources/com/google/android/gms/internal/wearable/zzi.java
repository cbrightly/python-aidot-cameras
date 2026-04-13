package com.google.android.gms.internal.wearable;

public final class zzi extends zzn<zzi> {
    private static volatile zzi[] zzgb;
    public int type = 1;
    public zzj zzgc = null;

    public static zzi[] zzi() {
        if (zzgb == null) {
            synchronized (zzr.zzhk) {
                if (zzgb == null) {
                    zzgb = new zzi[0];
                }
            }
        }
        return zzgb;
    }

    public zzi() {
        this.zzhc = null;
        this.zzhl = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzi)) {
            return false;
        }
        zzi zzi = (zzi) obj;
        if (this.type != zzi.type) {
            return false;
        }
        zzj zzj = this.zzgc;
        if (zzj == null) {
            if (zzi.zzgc != null) {
                return false;
            }
        } else if (!zzj.equals(zzi.zzgc)) {
            return false;
        }
        zzp zzp = this.zzhc;
        if (zzp != null && !zzp.isEmpty()) {
            return this.zzhc.equals(zzi.zzhc);
        }
        zzp zzp2 = zzi.zzhc;
        if (zzp2 == null || zzp2.isEmpty()) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int hashCode = ((zzi.class.getName().hashCode() + 527) * 31) + this.type;
        zzj zzj = this.zzgc;
        int i = 0;
        int hashCode2 = ((hashCode * 31) + (zzj == null ? 0 : zzj.hashCode())) * 31;
        zzp zzp = this.zzhc;
        if (zzp != null && !zzp.isEmpty()) {
            i = this.zzhc.hashCode();
        }
        return hashCode2 + i;
    }

    public final void zza(zzl zzl) {
        zzl.zzd(1, this.type);
        zzj zzj = this.zzgc;
        if (zzj != null) {
            zzl.zza(2, (zzt) zzj);
        }
        super.zza(zzl);
    }

    /* access modifiers changed from: protected */
    public final int zzg() {
        int zzg = super.zzg() + zzl.zze(1, this.type);
        zzj zzj = this.zzgc;
        if (zzj != null) {
            return zzg + zzl.zzb(2, (zzt) zzj);
        }
        return zzg;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzb */
    public final zzi zza(zzk zzk) {
        int zzk2;
        while (true) {
            int zzj = zzk.zzj();
            switch (zzj) {
                case 0:
                    return this;
                case 8:
                    try {
                        zzk2 = zzk.zzk();
                        if (zzk2 > 0 && zzk2 <= 15) {
                            this.type = zzk2;
                            break;
                        } else {
                            StringBuilder sb = new StringBuilder(36);
                            sb.append(zzk2);
                            sb.append(" is not a valid enum Type");
                            break;
                        }
                    } catch (IllegalArgumentException e) {
                        zzk.zzg(zzk.getPosition());
                        zza(zzk, zzj);
                        break;
                    }
                case 18:
                    if (this.zzgc == null) {
                        this.zzgc = new zzj();
                    }
                    zzk.zza(this.zzgc);
                    break;
                default:
                    if (super.zza(zzk, zzj)) {
                        break;
                    } else {
                        return this;
                    }
            }
        }
        StringBuilder sb2 = new StringBuilder(36);
        sb2.append(zzk2);
        sb2.append(" is not a valid enum Type");
        throw new IllegalArgumentException(sb2.toString());
    }
}
