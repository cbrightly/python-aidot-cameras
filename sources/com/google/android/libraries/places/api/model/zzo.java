package com.google.android.libraries.places.api.model;

import androidx.annotation.Nullable;
import com.google.android.libraries.places.api.model.Period;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzo extends Period.Builder {
    private TimeOfWeek zza;
    private TimeOfWeek zzb;

    zzo() {
    }

    public final Period build() {
        return new zzau(this.zza, this.zzb);
    }

    @Nullable
    public final TimeOfWeek getClose() {
        return this.zzb;
    }

    @Nullable
    public final TimeOfWeek getOpen() {
        return this.zza;
    }

    public final Period.Builder setClose(@Nullable TimeOfWeek timeOfWeek) {
        this.zzb = timeOfWeek;
        return this;
    }

    public final Period.Builder setOpen(@Nullable TimeOfWeek timeOfWeek) {
        this.zza = timeOfWeek;
        return this;
    }
}
