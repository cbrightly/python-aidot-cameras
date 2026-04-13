package com.google.android.libraries.places.api.model;

import androidx.annotation.Nullable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzp extends Period {
    private final TimeOfWeek zza;
    private final TimeOfWeek zzb;

    zzp(@Nullable TimeOfWeek timeOfWeek, @Nullable TimeOfWeek timeOfWeek2) {
        this.zza = timeOfWeek;
        this.zzb = timeOfWeek2;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Period)) {
            return false;
        }
        Period period = (Period) obj;
        TimeOfWeek timeOfWeek = this.zza;
        if (timeOfWeek != null ? timeOfWeek.equals(period.getOpen()) : period.getOpen() == null) {
            TimeOfWeek timeOfWeek2 = this.zzb;
            if (timeOfWeek2 != null ? timeOfWeek2.equals(period.getClose()) : period.getClose() == null) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public final TimeOfWeek getClose() {
        return this.zzb;
    }

    @Nullable
    public final TimeOfWeek getOpen() {
        return this.zza;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zza);
        String valueOf2 = String.valueOf(this.zzb);
        return "Period{open=" + valueOf + ", close=" + valueOf2 + "}";
    }

    public final int hashCode() {
        int i;
        TimeOfWeek timeOfWeek = this.zza;
        int i2 = 0;
        if (timeOfWeek == null) {
            i = 0;
        } else {
            i = timeOfWeek.hashCode();
        }
        TimeOfWeek timeOfWeek2 = this.zzb;
        if (timeOfWeek2 != null) {
            i2 = timeOfWeek2.hashCode();
        }
        return ((i ^ 1000003) * 1000003) ^ i2;
    }
}
