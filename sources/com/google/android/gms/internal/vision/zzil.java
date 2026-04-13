package com.google.android.gms.internal.vision;

import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzil implements zzmr {
    private final zzii zza;

    public static zzil zza(zzii zzii) {
        zzil zzil = zzii.zza;
        if (zzil != null) {
            return zzil;
        }
        return new zzil(zzii);
    }

    private zzil(zzii zzii) {
        zzii zzii2 = (zzii) zzjf.zza(zzii, "output");
        this.zza = zzii2;
        zzii2.zza = this;
    }

    public final int zza() {
        return zzmq.zza;
    }

    public final void zza(int i, int i2) {
        this.zza.zze(i, i2);
    }

    public final void zza(int i, long j) {
        this.zza.zza(i, j);
    }

    public final void zzb(int i, long j) {
        this.zza.zzc(i, j);
    }

    public final void zza(int i, float f) {
        this.zza.zza(i, f);
    }

    public final void zza(int i, double d) {
        this.zza.zza(i, d);
    }

    public final void zzb(int i, int i2) {
        this.zza.zzb(i, i2);
    }

    public final void zzc(int i, long j) {
        this.zza.zza(i, j);
    }

    public final void zzc(int i, int i2) {
        this.zza.zzb(i, i2);
    }

    public final void zzd(int i, long j) {
        this.zza.zzc(i, j);
    }

    public final void zzd(int i, int i2) {
        this.zza.zze(i, i2);
    }

    public final void zza(int i, boolean z) {
        this.zza.zza(i, z);
    }

    public final void zza(int i, String str) {
        this.zza.zza(i, str);
    }

    public final void zza(int i, zzht zzht) {
        this.zza.zza(i, zzht);
    }

    public final void zze(int i, int i2) {
        this.zza.zzc(i, i2);
    }

    public final void zzf(int i, int i2) {
        this.zza.zzd(i, i2);
    }

    public final void zze(int i, long j) {
        this.zza.zzb(i, j);
    }

    public final void zza(int i, Object obj, zzlc zzlc) {
        this.zza.zza(i, (zzkk) obj, zzlc);
    }

    public final void zzb(int i, Object obj, zzlc zzlc) {
        zzii zzii = this.zza;
        zzii.zza(i, 3);
        zzlc.zza((zzkk) obj, (zzmr) zzii.zza);
        zzii.zza(i, 4);
    }

    public final void zza(int i) {
        this.zza.zza(i, 3);
    }

    public final void zzb(int i) {
        this.zza.zza(i, 4);
    }

    public final void zza(int i, Object obj) {
        if (obj instanceof zzht) {
            this.zza.zzb(i, (zzht) obj);
        } else {
            this.zza.zza(i, (zzkk) obj);
        }
    }

    public final void zza(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzii.zzf(list.get(i4).intValue());
            }
            this.zza.zzb(i3);
            while (i2 < list.size()) {
                this.zza.zza(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzb(i, list.get(i2).intValue());
            i2++;
        }
    }

    public final void zzb(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzii.zzi(list.get(i4).intValue());
            }
            this.zza.zzb(i3);
            while (i2 < list.size()) {
                this.zza.zzd(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zze(i, list.get(i2).intValue());
            i2++;
        }
    }

    public final void zzc(int i, List<Long> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzii.zzd(list.get(i4).longValue());
            }
            this.zza.zzb(i3);
            while (i2 < list.size()) {
                this.zza.zza(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zza(i, list.get(i2).longValue());
            i2++;
        }
    }

    public final void zzd(int i, List<Long> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzii.zze(list.get(i4).longValue());
            }
            this.zza.zzb(i3);
            while (i2 < list.size()) {
                this.zza.zza(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zza(i, list.get(i2).longValue());
            i2++;
        }
    }

    public final void zze(int i, List<Long> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzii.zzg(list.get(i4).longValue());
            }
            this.zza.zzb(i3);
            while (i2 < list.size()) {
                this.zza.zzc(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzc(i, list.get(i2).longValue());
            i2++;
        }
    }

    public final void zzf(int i, List<Float> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzii.zzb(list.get(i4).floatValue());
            }
            this.zza.zzb(i3);
            while (i2 < list.size()) {
                this.zza.zza(list.get(i2).floatValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zza(i, list.get(i2).floatValue());
            i2++;
        }
    }

    public final void zzg(int i, List<Double> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzii.zzb(list.get(i4).doubleValue());
            }
            this.zza.zzb(i3);
            while (i2 < list.size()) {
                this.zza.zza(list.get(i2).doubleValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zza(i, list.get(i2).doubleValue());
            i2++;
        }
    }

    public final void zzh(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzii.zzk(list.get(i4).intValue());
            }
            this.zza.zzb(i3);
            while (i2 < list.size()) {
                this.zza.zza(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzb(i, list.get(i2).intValue());
            i2++;
        }
    }

    public final void zzi(int i, List<Boolean> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzii.zzb(list.get(i4).booleanValue());
            }
            this.zza.zzb(i3);
            while (i2 < list.size()) {
                this.zza.zza(list.get(i2).booleanValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zza(i, list.get(i2).booleanValue());
            i2++;
        }
    }

    public final void zza(int i, List<String> list) {
        int i2 = 0;
        if (list instanceof zzjv) {
            zzjv zzjv = (zzjv) list;
            while (i2 < list.size()) {
                Object zzb = zzjv.zzb(i2);
                if (zzb instanceof String) {
                    this.zza.zza(i, (String) zzb);
                } else {
                    this.zza.zza(i, (zzht) zzb);
                }
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zza(i, list.get(i2));
            i2++;
        }
    }

    public final void zzb(int i, List<zzht> list) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.zza.zza(i, list.get(i2));
        }
    }

    public final void zzj(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzii.zzg(list.get(i4).intValue());
            }
            this.zza.zzb(i3);
            while (i2 < list.size()) {
                this.zza.zzb(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzc(i, list.get(i2).intValue());
            i2++;
        }
    }

    public final void zzk(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzii.zzj(list.get(i4).intValue());
            }
            this.zza.zzb(i3);
            while (i2 < list.size()) {
                this.zza.zzd(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zze(i, list.get(i2).intValue());
            i2++;
        }
    }

    public final void zzl(int i, List<Long> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzii.zzh(list.get(i4).longValue());
            }
            this.zza.zzb(i3);
            while (i2 < list.size()) {
                this.zza.zzc(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzc(i, list.get(i2).longValue());
            i2++;
        }
    }

    public final void zzm(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzii.zzh(list.get(i4).intValue());
            }
            this.zza.zzb(i3);
            while (i2 < list.size()) {
                this.zza.zzc(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzd(i, list.get(i2).intValue());
            i2++;
        }
    }

    public final void zzn(int i, List<Long> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzii.zzf(list.get(i4).longValue());
            }
            this.zza.zzb(i3);
            while (i2 < list.size()) {
                this.zza.zzb(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzb(i, list.get(i2).longValue());
            i2++;
        }
    }

    public final void zza(int i, List<?> list, zzlc zzlc) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zza(i, (Object) list.get(i2), zzlc);
        }
    }

    public final void zzb(int i, List<?> list, zzlc zzlc) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzb(i, (Object) list.get(i2), zzlc);
        }
    }

    public final <K, V> void zza(int i, zzkf<K, V> zzkf, Map<K, V> map) {
        for (Map.Entry next : map.entrySet()) {
            this.zza.zza(i, 2);
            this.zza.zzb(zzkc.zza(zzkf, next.getKey(), next.getValue()));
            zzkc.zza(this.zza, zzkf, next.getKey(), next.getValue());
        }
    }
}
