package com.google.android.libraries.places.api.model;

import androidx.annotation.Nullable;
import com.google.android.libraries.places.api.model.OpeningHours;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzm extends OpeningHours.Builder {
    private OpeningHours.HoursType zza;
    private List zzb;
    private List zzc;
    private List zzd;

    zzm() {
    }

    @Nullable
    public final OpeningHours.HoursType getHoursType() {
        return this.zza;
    }

    public final List<Period> getPeriods() {
        List<Period> list = this.zzb;
        if (list != null) {
            return list;
        }
        throw new IllegalStateException("Property \"periods\" has not been set");
    }

    public final List<SpecialDay> getSpecialDays() {
        List<SpecialDay> list = this.zzc;
        if (list != null) {
            return list;
        }
        throw new IllegalStateException("Property \"specialDays\" has not been set");
    }

    public final List<String> getWeekdayText() {
        List<String> list = this.zzd;
        if (list != null) {
            return list;
        }
        throw new IllegalStateException("Property \"weekdayText\" has not been set");
    }

    public final OpeningHours.Builder setHoursType(@Nullable OpeningHours.HoursType hoursType) {
        this.zza = hoursType;
        return this;
    }

    public final OpeningHours.Builder setPeriods(List<Period> list) {
        if (list != null) {
            this.zzb = list;
            return this;
        }
        throw new NullPointerException("Null periods");
    }

    public final OpeningHours.Builder setSpecialDays(List<SpecialDay> list) {
        if (list != null) {
            this.zzc = list;
            return this;
        }
        throw new NullPointerException("Null specialDays");
    }

    public final OpeningHours.Builder setWeekdayText(List<String> list) {
        if (list != null) {
            this.zzd = list;
            return this;
        }
        throw new NullPointerException("Null weekdayText");
    }

    /* access modifiers changed from: package-private */
    public final OpeningHours zza() {
        List list;
        List list2;
        List list3 = this.zzb;
        if (list3 != null && (list = this.zzc) != null && (list2 = this.zzd) != null) {
            return new zzas(this.zza, list3, list, list2);
        }
        StringBuilder sb = new StringBuilder();
        if (this.zzb == null) {
            sb.append(" periods");
        }
        if (this.zzc == null) {
            sb.append(" specialDays");
        }
        if (this.zzd == null) {
            sb.append(" weekdayText");
        }
        throw new IllegalStateException("Missing required properties:".concat(sb.toString()));
    }
}
