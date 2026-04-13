package com.google.android.libraries.places.api.model;

import androidx.annotation.IntRange;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzj extends LocalDate {
    private final int zza;
    private final int zzb;
    private final int zzc;

    zzj(int i, int i2, int i3) {
        this.zza = i;
        this.zzb = i2;
        this.zzc = i3;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof LocalDate) {
            LocalDate localDate = (LocalDate) obj;
            return this.zza == localDate.getYear() && this.zzb == localDate.getMonth() && this.zzc == localDate.getDay();
        }
    }

    @IntRange(from = 1, to = 31)
    public final int getDay() {
        return this.zzc;
    }

    @IntRange(from = 1, to = 12)
    public final int getMonth() {
        return this.zzb;
    }

    public final int getYear() {
        return this.zza;
    }

    public final int hashCode() {
        return ((((this.zza ^ 1000003) * 1000003) ^ this.zzb) * 1000003) ^ this.zzc;
    }
}
