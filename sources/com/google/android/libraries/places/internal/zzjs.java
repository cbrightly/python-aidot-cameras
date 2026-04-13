package com.google.android.libraries.places.internal;

import java.util.Arrays;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzjs {
    Object[] zza = new Object[8];
    int zzb = 0;
    zzjr zzc;

    public final zzjs zza(Object obj, Object obj2) {
        int i = this.zzb + 1;
        Object[] objArr = this.zza;
        int length = objArr.length;
        int i2 = i + i;
        if (i2 > length) {
            int i3 = length + (length >> 1) + 1;
            if (i3 < i2) {
                int highestOneBit = Integer.highestOneBit(i2 - 1);
                i3 = highestOneBit + highestOneBit;
            }
            if (i3 < 0) {
                i3 = Integer.MAX_VALUE;
            }
            this.zza = Arrays.copyOf(objArr, i3);
        }
        zzjf.zza(obj, obj2);
        Object[] objArr2 = this.zza;
        int i4 = this.zzb;
        int i5 = i4 + i4;
        objArr2[i5] = obj;
        objArr2[i5 + 1] = obj2;
        this.zzb = i4 + 1;
        return this;
    }

    public final zzjt zzb() {
        zzjr zzjr = this.zzc;
        if (zzjr == null) {
            zzkj zzf = zzkj.zzf(this.zzb, this.zza, this);
            zzjr zzjr2 = this.zzc;
            if (zzjr2 == null) {
                return zzf;
            }
            throw zzjr2.zza();
        }
        throw zzjr.zza();
    }
}
