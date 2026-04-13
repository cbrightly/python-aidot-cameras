package com.google.android.gms.internal.wearable;

public final class zzh extends zzn<zzh> {
    private static volatile zzh[] zzfz;
    public String name = "";
    public zzi zzga = null;

    public static zzh[] zzh() {
        if (zzfz == null) {
            synchronized (zzr.zzhk) {
                if (zzfz == null) {
                    zzfz = new zzh[0];
                }
            }
        }
        return zzfz;
    }

    public zzh() {
        this.zzhc = null;
        this.zzhl = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzh)) {
            return false;
        }
        zzh zzh = (zzh) obj;
        String str = this.name;
        if (str == null) {
            if (zzh.name != null) {
                return false;
            }
        } else if (!str.equals(zzh.name)) {
            return false;
        }
        zzi zzi = this.zzga;
        if (zzi == null) {
            if (zzh.zzga != null) {
                return false;
            }
        } else if (!zzi.equals(zzh.zzga)) {
            return false;
        }
        zzp zzp = this.zzhc;
        if (zzp != null && !zzp.isEmpty()) {
            return this.zzhc.equals(zzh.zzhc);
        }
        zzp zzp2 = zzh.zzhc;
        if (zzp2 == null || zzp2.isEmpty()) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int hashCode = (zzh.class.getName().hashCode() + 527) * 31;
        String str = this.name;
        int i = 0;
        int hashCode2 = hashCode + (str == null ? 0 : str.hashCode());
        zzi zzi = this.zzga;
        int hashCode3 = ((hashCode2 * 31) + (zzi == null ? 0 : zzi.hashCode())) * 31;
        zzp zzp = this.zzhc;
        if (zzp != null && !zzp.isEmpty()) {
            i = this.zzhc.hashCode();
        }
        return hashCode3 + i;
    }

    public final void zza(zzl zzl) {
        zzl.zza(1, this.name);
        zzi zzi = this.zzga;
        if (zzi != null) {
            zzl.zza(2, (zzt) zzi);
        }
        super.zza(zzl);
    }

    /* access modifiers changed from: protected */
    public final int zzg() {
        int zzg = super.zzg() + zzl.zzb(1, this.name);
        zzi zzi = this.zzga;
        if (zzi != null) {
            return zzg + zzl.zzb(2, (zzt) zzi);
        }
        return zzg;
    }

    public final /* synthetic */ zzt zza(zzk zzk) {
        while (true) {
            int zzj = zzk.zzj();
            switch (zzj) {
                case 0:
                    return this;
                case 10:
                    this.name = zzk.readString();
                    break;
                case 18:
                    if (this.zzga == null) {
                        this.zzga = new zzi();
                    }
                    zzk.zza(this.zzga);
                    break;
                default:
                    if (super.zza(zzk, zzj)) {
                        break;
                    } else {
                        return this;
                    }
            }
        }
    }
}
