package com.google.android.gms.internal.wearable;

import com.google.android.gms.internal.wearable.zzn;

public abstract class zzn<M extends zzn<M>> extends zzt {
    protected zzp zzhc;

    /* access modifiers changed from: protected */
    public int zzg() {
        if (this.zzhc == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.zzhc.size(); i2++) {
            i += this.zzhc.zzp(i2).zzg();
        }
        return i;
    }

    public void zza(zzl zzl) {
        if (this.zzhc != null) {
            for (int i = 0; i < this.zzhc.size(); i++) {
                this.zzhc.zzp(i).zza(zzl);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final boolean zza(zzk zzk, int i) {
        int position = zzk.getPosition();
        if (!zzk.zzd(i)) {
            return false;
        }
        int i2 = i >>> 3;
        zzv zzv = new zzv(i, zzk.zzb(position, zzk.getPosition() - position));
        zzq zzq = null;
        zzp zzp = this.zzhc;
        if (zzp == null) {
            this.zzhc = new zzp();
        } else {
            zzq = zzp.zzo(i2);
        }
        if (zzq == null) {
            zzq = new zzq();
            this.zzhc.zza(i2, zzq);
        }
        zzq.zza(zzv);
        return true;
    }

    public final /* synthetic */ zzt zzs() {
        return (zzn) clone();
    }

    public /* synthetic */ Object clone() {
        zzn zzn = (zzn) super.clone();
        zzr.zza(this, zzn);
        return zzn;
    }
}
