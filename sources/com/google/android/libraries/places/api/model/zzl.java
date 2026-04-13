package com.google.android.libraries.places.api.model;

import androidx.annotation.IntRange;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzl extends LocalTime {
    private final int zza;
    private final int zzb;

    zzl(int i, int i2) {
        this.zza = i;
        this.zzb = i2;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof LocalTime) {
            LocalTime localTime = (LocalTime) obj;
            return this.zza == localTime.getHours() && this.zzb == localTime.getMinutes();
        }
    }

    @IntRange(from = 0, to = 23)
    public final int getHours() {
        return this.zza;
    }

    @IntRange(from = 0, to = 59)
    public final int getMinutes() {
        return this.zzb;
    }

    public final int hashCode() {
        return ((this.zza ^ 1000003) * 1000003) ^ this.zzb;
    }

    public final String toString() {
        int i = this.zza;
        int i2 = this.zzb;
        return "LocalTime{hours=" + i + ", minutes=" + i2 + "}";
    }
}
