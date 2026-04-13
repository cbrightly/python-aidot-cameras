package com.google.android.libraries.places.api.model;

import androidx.annotation.Nullable;
import com.google.android.libraries.places.api.model.TimeOfWeek;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzab extends TimeOfWeek.Builder {
    private LocalDate zza;
    private DayOfWeek zzb;
    private LocalTime zzc;
    private boolean zzd;
    private byte zze;

    zzab() {
    }

    public final TimeOfWeek build() {
        DayOfWeek dayOfWeek;
        LocalTime localTime;
        if (this.zze == 1 && (dayOfWeek = this.zzb) != null && (localTime = this.zzc) != null) {
            return new zzbi(this.zza, dayOfWeek, localTime, this.zzd);
        }
        StringBuilder sb = new StringBuilder();
        if (this.zzb == null) {
            sb.append(" day");
        }
        if (this.zzc == null) {
            sb.append(" time");
        }
        if (this.zze == 0) {
            sb.append(" truncated");
        }
        throw new IllegalStateException("Missing required properties:".concat(sb.toString()));
    }

    @Nullable
    public final LocalDate getDate() {
        return this.zza;
    }

    public final DayOfWeek getDay() {
        DayOfWeek dayOfWeek = this.zzb;
        if (dayOfWeek != null) {
            return dayOfWeek;
        }
        throw new IllegalStateException("Property \"day\" has not been set");
    }

    public final LocalTime getTime() {
        LocalTime localTime = this.zzc;
        if (localTime != null) {
            return localTime;
        }
        throw new IllegalStateException("Property \"time\" has not been set");
    }

    public final boolean isTruncated() {
        if (this.zze != 0) {
            return this.zzd;
        }
        throw new IllegalStateException("Property \"truncated\" has not been set");
    }

    public final TimeOfWeek.Builder setDate(@Nullable LocalDate localDate) {
        this.zza = localDate;
        return this;
    }

    public final TimeOfWeek.Builder setDay(DayOfWeek dayOfWeek) {
        if (dayOfWeek != null) {
            this.zzb = dayOfWeek;
            return this;
        }
        throw new NullPointerException("Null day");
    }

    public final TimeOfWeek.Builder setTime(LocalTime localTime) {
        if (localTime != null) {
            this.zzc = localTime;
            return this;
        }
        throw new NullPointerException("Null time");
    }

    public final TimeOfWeek.Builder setTruncated(boolean z) {
        this.zzd = z;
        this.zze = 1;
        return this;
    }
}
