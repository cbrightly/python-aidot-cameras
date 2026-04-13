package com.google.android.gms.internal.common;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class zzaa extends zzab {
    Object[] zza = new Object[4];
    int zzb = 0;
    boolean zzc;

    zzaa(int i) {
    }

    @CanIgnoreReturnValue
    public final zzaa zza(Object obj) {
        if (obj != null) {
            int i = this.zzb + 1;
            Object[] objArr = this.zza;
            int length = objArr.length;
            if (length < i) {
                int i2 = length + (length >> 1) + 1;
                if (i2 < i) {
                    int highestOneBit = Integer.highestOneBit(i - 1);
                    i2 = highestOneBit + highestOneBit;
                }
                if (i2 < 0) {
                    i2 = Integer.MAX_VALUE;
                }
                this.zza = Arrays.copyOf(objArr, i2);
                this.zzc = false;
            } else if (this.zzc) {
                this.zza = (Object[]) objArr.clone();
                this.zzc = false;
            }
            Object[] objArr2 = this.zza;
            int i3 = this.zzb;
            this.zzb = i3 + 1;
            objArr2[i3] = obj;
            return this;
        }
        throw null;
    }
}
