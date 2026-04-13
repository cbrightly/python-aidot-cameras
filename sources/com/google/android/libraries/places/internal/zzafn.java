package com.google.android.libraries.places.internal;

import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzafn implements zzaja {
    private final zzafm zza;

    private zzafn(zzafm zzafm) {
        byte[] bArr = zzagi.zzd;
        this.zza = zzafm;
        zzafm.zza = this;
    }

    public static zzafn zza(zzafm zzafm) {
        zzafn zzafn = zzafm.zza;
        return zzafn != null ? zzafn : new zzafn(zzafm);
    }

    public final void zzB(int i, long j) {
        this.zza.zzr(i, (j >> 63) ^ (j + j));
    }

    public final void zzD(int i, String str) {
        this.zza.zzm(i, str);
    }

    public final void zzE(int i, List list) {
        int i2 = 0;
        if (list instanceof zzagp) {
            zzagp zzagp = (zzagp) list;
            while (i2 < list.size()) {
                Object zze = zzagp.zze(i2);
                if (zze instanceof String) {
                    this.zza.zzm(i, (String) zze);
                } else {
                    this.zza.zze(i, (zzafe) zze);
                }
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzm(i, (String) list.get(i2));
            i2++;
        }
    }

    public final void zzF(int i, int i2) {
        this.zza.zzp(i, i2);
    }

    public final void zzH(int i, long j) {
        this.zza.zzr(i, j);
    }

    public final void zzb(int i, boolean z) {
        this.zza.zzd(i, z);
    }

    public final void zzd(int i, zzafe zzafe) {
        this.zza.zze(i, zzafe);
    }

    public final void zze(int i, List list) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.zza.zze(i, (zzafe) list.get(i2));
        }
    }

    public final void zzf(int i, double d) {
        this.zza.zzh(i, Double.doubleToRawLongBits(d));
    }

    public final void zzh(int i, int i2) {
        this.zza.zzj(i, i2);
    }

    public final void zzj(int i, int i2) {
        this.zza.zzf(i, i2);
    }

    public final void zzl(int i, long j) {
        this.zza.zzh(i, j);
    }

    public final void zzn(int i, float f) {
        this.zza.zzf(i, Float.floatToRawIntBits(f));
    }

    public final void zzp(int i, Object obj, zzahs zzahs) {
        zzafm zzafm = this.zza;
        zzafm.zzo(i, 3);
        zzahs.zzf((zzahh) obj, zzafm.zza);
        zzafm.zzo(i, 4);
    }

    public final void zzq(int i, int i2) {
        this.zza.zzj(i, i2);
    }

    public final void zzs(int i, long j) {
        this.zza.zzr(i, j);
    }

    public final void zzu(int i, Object obj, zzahs zzahs) {
        zzahh zzahh = (zzahh) obj;
        zzafj zzafj = (zzafj) this.zza;
        zzafj.zzq((i << 3) | 2);
        zzafj.zzq(((zzaer) zzahh).zzr(zzahs));
        zzahs.zzf(zzahh, zzafj.zza);
    }

    public final void zzv(int i, int i2) {
        this.zza.zzf(i, i2);
    }

    public final void zzx(int i, long j) {
        this.zza.zzh(i, j);
    }

    public final void zzz(int i, int i2) {
        this.zza.zzp(i, (i2 >> 31) ^ (i2 + i2));
    }

    public final void zzA(int i, List list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zzo(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                int intValue = ((Integer) list.get(i4)).intValue();
                i3 += zzafm.zzx((intValue >> 31) ^ (intValue + intValue));
            }
            this.zza.zzq(i3);
            while (i2 < list.size()) {
                zzafm zzafm = this.zza;
                int intValue2 = ((Integer) list.get(i2)).intValue();
                zzafm.zzq((intValue2 >> 31) ^ (intValue2 + intValue2));
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            zzafm zzafm2 = this.zza;
            int intValue3 = ((Integer) list.get(i2)).intValue();
            zzafm2.zzp(i, (intValue3 >> 31) ^ (intValue3 + intValue3));
            i2++;
        }
    }

    public final void zzC(int i, List list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zzo(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                long longValue = ((Long) list.get(i4)).longValue();
                i3 += zzafm.zzy((longValue >> 63) ^ (longValue + longValue));
            }
            this.zza.zzq(i3);
            while (i2 < list.size()) {
                zzafm zzafm = this.zza;
                long longValue2 = ((Long) list.get(i2)).longValue();
                zzafm.zzs((longValue2 >> 63) ^ (longValue2 + longValue2));
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            zzafm zzafm2 = this.zza;
            long longValue3 = ((Long) list.get(i2)).longValue();
            zzafm2.zzr(i, (longValue3 >> 63) ^ (longValue3 + longValue3));
            i2++;
        }
    }

    public final void zzG(int i, List list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zzo(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzafm.zzx(((Integer) list.get(i4)).intValue());
            }
            this.zza.zzq(i3);
            while (i2 < list.size()) {
                this.zza.zzq(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzp(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzI(int i, List list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zzo(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzafm.zzy(((Long) list.get(i4)).longValue());
            }
            this.zza.zzq(i3);
            while (i2 < list.size()) {
                this.zza.zzs(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzr(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zzc(int i, List list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zzo(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Boolean) list.get(i4)).booleanValue();
                i3++;
            }
            this.zza.zzq(i3);
            while (i2 < list.size()) {
                this.zza.zzb(((Boolean) list.get(i2)).booleanValue() ? (byte) 1 : 0);
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzd(i, ((Boolean) list.get(i2)).booleanValue());
            i2++;
        }
    }

    public final void zzi(int i, List list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zzo(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzafm.zzu(((Integer) list.get(i4)).intValue());
            }
            this.zza.zzq(i3);
            while (i2 < list.size()) {
                this.zza.zzk(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzj(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzk(int i, List list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zzo(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Integer) list.get(i4)).intValue();
                i3 += 4;
            }
            this.zza.zzq(i3);
            while (i2 < list.size()) {
                this.zza.zzg(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzf(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzm(int i, List list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zzo(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Long) list.get(i4)).longValue();
                i3 += 8;
            }
            this.zza.zzq(i3);
            while (i2 < list.size()) {
                this.zza.zzi(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzh(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zzr(int i, List list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zzo(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzafm.zzu(((Integer) list.get(i4)).intValue());
            }
            this.zza.zzq(i3);
            while (i2 < list.size()) {
                this.zza.zzk(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzj(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzt(int i, List list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zzo(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzafm.zzy(((Long) list.get(i4)).longValue());
            }
            this.zza.zzq(i3);
            while (i2 < list.size()) {
                this.zza.zzs(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzr(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zzw(int i, List list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zzo(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Integer) list.get(i4)).intValue();
                i3 += 4;
            }
            this.zza.zzq(i3);
            while (i2 < list.size()) {
                this.zza.zzg(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzf(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzy(int i, List list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zzo(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Long) list.get(i4)).longValue();
                i3 += 8;
            }
            this.zza.zzq(i3);
            while (i2 < list.size()) {
                this.zza.zzi(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzh(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zzg(int i, List list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zzo(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Double) list.get(i4)).doubleValue();
                i3 += 8;
            }
            this.zza.zzq(i3);
            while (i2 < list.size()) {
                this.zza.zzi(Double.doubleToRawLongBits(((Double) list.get(i2)).doubleValue()));
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzh(i, Double.doubleToRawLongBits(((Double) list.get(i2)).doubleValue()));
            i2++;
        }
    }

    public final void zzo(int i, List list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zzo(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Float) list.get(i4)).floatValue();
                i3 += 4;
            }
            this.zza.zzq(i3);
            while (i2 < list.size()) {
                this.zza.zzg(Float.floatToRawIntBits(((Float) list.get(i2)).floatValue()));
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzf(i, Float.floatToRawIntBits(((Float) list.get(i2)).floatValue()));
            i2++;
        }
    }
}
