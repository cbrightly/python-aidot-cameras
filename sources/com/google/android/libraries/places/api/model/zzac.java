package com.google.android.libraries.places.api.model;

import androidx.annotation.Nullable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzac extends TimeOfWeek {
    private final LocalDate zza;
    private final DayOfWeek zzb;
    private final LocalTime zzc;
    private final boolean zzd;

    zzac(@Nullable LocalDate localDate, DayOfWeek dayOfWeek, LocalTime localTime, boolean z) {
        this.zza = localDate;
        if (dayOfWeek != null) {
            this.zzb = dayOfWeek;
            if (localTime != null) {
                this.zzc = localTime;
                this.zzd = z;
                return;
            }
            throw new NullPointerException("Null time");
        }
        throw new NullPointerException("Null day");
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TimeOfWeek)) {
            return false;
        }
        TimeOfWeek timeOfWeek = (TimeOfWeek) obj;
        LocalDate localDate = this.zza;
        if (localDate != null ? localDate.equals(timeOfWeek.getDate()) : timeOfWeek.getDate() == null) {
            return this.zzb.equals(timeOfWeek.getDay()) && this.zzc.equals(timeOfWeek.getTime()) && this.zzd == timeOfWeek.isTruncated();
        }
    }

    @Nullable
    public final LocalDate getDate() {
        return this.zza;
    }

    public final DayOfWeek getDay() {
        return this.zzb;
    }

    public final LocalTime getTime() {
        return this.zzc;
    }

    public final boolean isTruncated() {
        return this.zzd;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zza);
        String obj = this.zzb.toString();
        String obj2 = this.zzc.toString();
        boolean z = this.zzd;
        return "TimeOfWeek{date=" + valueOf + ", day=" + obj + ", time=" + obj2 + ", truncated=" + z + "}";
    }

    public final int hashCode() {
        int i;
        LocalDate localDate = this.zza;
        if (localDate == null) {
            i = 0;
        } else {
            i = localDate.hashCode();
        }
        return ((((((i ^ 1000003) * 1000003) ^ this.zzb.hashCode()) * 1000003) ^ this.zzc.hashCode()) * 1000003) ^ (true != this.zzd ? 1237 : 1231);
    }
}
